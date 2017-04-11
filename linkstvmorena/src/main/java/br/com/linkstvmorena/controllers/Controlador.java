package br.com.linkstvmorena.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.linkstvmorena.model.Categoria;
import br.com.linkstvmorena.model.Contato;
import br.com.linkstvmorena.model.Local;
import br.com.linkstvmorena.model.Ponto;
import br.com.linkstvmorena.model.StatusLocal;
import br.com.linkstvmorena.model.StatusPonto;
import br.com.linkstvmorena.model.Status_Contato;
import br.com.linkstvmorena.msg.util.MenssagemUtil;
import br.com.linkstvmorena.service.Servico;

@Controller
@ViewScoped
public class Controlador {

	@Autowired
	private Servico servico;

	private Local local;
	private Ponto ponto;
	private Categoria categoria;
	private Contato contato;
	private StatusLocal statuslocal;
	private StatusPonto statusPonto;
	private List<Local> listadelocais;
	private List<Status_Contato> listadestatus;
	private List<Ponto> listaponto;
	private List<Contato> listadecontato;
	private List<StatusLocal> liststatuslocal;
	private List<StatusPonto> liststatusponto;
	private Set<Ponto> listagemdeponto;
	private DualListModel<Categoria> listcategorias;

	@PostConstruct
	public void init() {
		local = new Local();
		contato = new Contato();
		ponto = new Ponto();
		categoria = new Categoria();
		statuslocal = new StatusLocal();
		statusPonto = new StatusPonto();
		listadelocais = new ArrayList<Local>();
		listadecontato = new ArrayList<Contato>();
		liststatusponto = new ArrayList<StatusPonto>();
		listaponto = new ArrayList<Ponto>();
		listagemdeponto = new HashSet<>();
		liststatuslocal = new ArrayList<>();

		listadestatus = new ArrayList<Status_Contato>();
		for (Status_Contato lc : Status_Contato.values()) {
			listadestatus.add(lc);
		}

		try {
			listadelocais = servico.buscarLocal();
			System.out.println("Lista de Locais: "+listadelocais);
			liststatuslocal = servico.buscarStatusLocal();
			liststatusponto = servico.buscarStatusPonto();
			List<Categoria> fonte = servico.buscarCategorias();
			System.out.println("Lista de Categorias: "+fonte);
			List<Categoria> alvo = new ArrayList<Categoria>();
			listcategorias = new DualListModel<Categoria>(fonte, alvo);

		} catch (Exception e) {
			MenssagemUtil.mensagemErro("Nao Foi Possivel Carregar a Lista!!" + e.getMessage());
		}
	}

	public String salvar() {
		try {
			servico.salvar(local);
			init();
			MenssagemUtil.mensagemInfo("Salvo com Sucesso!!");
			return "home";
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}
		return"";
	}

	public void adicionaCategoria() {
		this.local.adicionaCategorias(this.listcategorias.getTarget());
	}

	public void adicionaContato() {
		this.listadecontato.add(this.contato);
		this.local.adicionaContatos(listadecontato);
		
	//	this.contato.setLocal(listlocal);

		this.contato = new Contato();
		/*this.listadecontato.add(contato);
		this.local.setContato(listadecontato);
		this.contato.setLocal(listlocal);

		this.contato = new Contato();*/
	}

	public void carregaCategoria(){
		List<Categoria> fonte = servico.buscarCategorias();
		System.out.println("Lista de Categorias: "+fonte);
		List<Categoria> alvo = new ArrayList<Categoria>();
		listcategorias = new DualListModel<Categoria>(fonte, alvo);
	}
	
	public void adicionaPonto() {
		this.listagemdeponto.add(this.ponto);
		this.local.setPonto(listagemdeponto);
		this.ponto.setLocal(local);
		System.out.println("Local: " + local + " Ponto: " + ponto);
		this.ponto = new Ponto();
	}

	public void adicionaStatusPonto() {
		this.ponto.setStatusponto(statusPonto);
		System.out.println("\nLocal: " + this.local);
		System.out.println("Local- StatusLocal: " + this.statuslocal);
	}

	public void adicionaStatusLocal() {
		this.local.setStatuslocal(this.statuslocal);
		System.out.println("\nLocal: " + this.local);
		System.out.println("Local- StatusLocal: " + this.statuslocal);
	}

	public String onFlowProcess(FlowEvent event) {
		
		/*if(this.local.getCategoria().isEmpty())
			this.local.adicionaCategorias(this.listcategorias.getTarget());*/
		
		System.out.println("\nWizard-Local: " + local);
		System.out.println("Wizard-Contato: " + contato);
		System.out.println("Wizard-Ponto: " + ponto);
		System.out.println("Wizard-Categoria: " + categoria);
		System.out.println("Wizard-StatusLocal: " + statuslocal);

		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}

	

	public List<Local> getListadelocais() {
		return listadelocais;
	}

	public void setListadelocais(List<Local> listadelocais) {
		this.listadelocais = listadelocais;
	}

	public List<Contato> getListadecontato() {
		return listadecontato;
	}

	public void setListadecontato(List<Contato> listadecontato) {
		this.listadecontato = listadecontato;
	}

	public List<Status_Contato> getListadestatus() {
		return listadestatus;
	}

	public void setListadestatus(List<Status_Contato> listadestatus) {
		this.listadestatus = listadestatus;
	}

	public Set<Ponto> getListagemdeponto() {
		return listagemdeponto;
	}

	public void setListagemdeponto(Set<Ponto> listagemdeponto) {
		this.listagemdeponto = listagemdeponto;
	}

	public StatusPonto getStatusPonto() {
		return statusPonto;
	}

	public void setStatusPonto(StatusPonto statusPonto) {
		this.statusPonto = statusPonto;
	}

	public List<StatusPonto> getListstatusponto() {
		return liststatusponto;
	}

	public void setListstatusponto(List<StatusPonto> liststatusponto) {
		this.liststatusponto = liststatusponto;
	}

	public List<StatusLocal> getListstatuslocal() {
		return liststatuslocal;
	}

	public void setListstatuslocal(List<StatusLocal> liststatuslocal) {
		this.liststatuslocal = liststatuslocal;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Ponto getPonto() {
		return ponto;
	}

	public void setPonto(Ponto ponto) {
		this.ponto = ponto;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public StatusLocal getStatuslocal() {
		return statuslocal;
	}

	public void setStatuslocal(StatusLocal statuslocal) {
		this.statuslocal = statuslocal;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public DualListModel<Categoria> getListcategorias() {
		return listcategorias;
	}

	public void setListcategorias(DualListModel<Categoria> listcategorias) {
		this.listcategorias = listcategorias;
	}

	public List<Ponto> getListaponto() {
		return listaponto;
	}

	public void setListaponto(List<Ponto> listaponto) {
		this.listaponto = listaponto;
	}

	public void onTransfer(TransferEvent event) {
		/*StringBuilder builder = new StringBuilder();
		for (Object item : event.getItems()) {
			builder.append(((Categoria) item).getNome()).append("<br />");
		}*/
		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		// msg.setSummary("Items Transferred");
		msg.setDetail(event.getItems().toString());
		if (event.isAdd()) {
			msg.setSummary("Items Transferred");
		} else {
			msg.setSummary("Removidos");
		}

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onSelect(SelectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
	}

	public void onUnselect(UnselectEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
	}

	public void onReorder() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
	}

	private boolean skip;
	private int step;

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

}
