package br.com.linkstvmorena.controllers;

import java.util.ArrayList;
import java.util.List;

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
	private List<Status_Contato> listadestatus;
	private List<Categoria> listadecategorias;
	private List<Ponto> listaponto;
	private List<Local> listlocal;
	private List<Contato> listadecontato;
	private List<StatusLocal> liststatuslocal;
	private List<StatusPonto> liststatusponto;
	private List<Ponto> listagemdeponto;
	private DualListModel<Categoria> listcategorias;
	private List<Categoria> lcat;
	@PostConstruct
	public void init() {
		local = new Local();
		contato = new Contato();
		ponto = new Ponto();
		categoria = new Categoria();
		statuslocal = new StatusLocal();
		statusPonto = new StatusPonto();
		listadecategorias = new ArrayList<Categoria>();
		listadecontato = new ArrayList<Contato>();
		liststatusponto = new ArrayList<StatusPonto>();
		listlocal = new ArrayList<Local>();
		listaponto = new ArrayList<Ponto>();
		listagemdeponto = new ArrayList<Ponto>();
		liststatuslocal = new ArrayList<>();
		lcat = new ArrayList<Categoria>();

		listadestatus = new ArrayList<Status_Contato>();
		for (Status_Contato lc : Status_Contato.values()) {
			listadestatus.add(lc);
		}

		try {
			liststatuslocal = servico.buscarStatusLocal();
			liststatusponto = servico.buscarStatusPonto();
			List<Categoria> fonte = servico.buscarCategorias();
			List<Categoria> alvo = new ArrayList<Categoria>();
			listcategorias = new DualListModel<Categoria>(fonte, alvo);

		} catch (Exception e) {
			MenssagemUtil.mensagemErro("Nao Foi Possivel Carregar a Lista!!" + e.getMessage());
		}
	}

	public void salvar() {
		try {
			servico.salvar(local);
			init();
			MenssagemUtil.mensagemInfo("Salvo com Sucesso!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}
	}
	
	public void adicionaCategoria() {
		this.local.adicionaCategorias(this.listcategorias.getTarget());
	}

	public void adicionaContato() {
		/*this.listadecontato.add(this.contato);
		this.local.adicionaContatos(listadecontato);
	//	this.contato.setLocal(listlocal);

		this.contato = new Contato();
		this.listadecontato.add(contato)
	this.local.setContato(listadecontato);
		this.contato.setLocal(listlocal);

	this.contato = new Contato();*/
		this.listadecontato.add(contato);
		this.local.setContato(listadecontato);
		this.contato.setLocal(listlocal);

		this.contato = new Contato();
}


//	public void adicionaCategoria() {
//		lcat = this.listcategorias.getTarget();
//		this.local.setCategoria(lcat);
//		this.listlocal.add(local);
//		this.categoria.setLocal(listlocal);
//	}
//
/*public void adicionaContato() {
		this.listadecontato.add(contato);
		this.local.setContato(listadecontato);
		this.contato.setLocal(listlocal);

		this.contato = new Contato();
	}*/

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
		System.out.println("Categoria: "+categoria.getLocal());
		System.out.println("\nWizard-Local: " + local);
		System.out.println("Wizard-Contato: " + contato);
		System.out.println("Wizard-Ponto: " + ponto);
		System.out.println("Wizard-Categoria: " + categoria);
		System.out.println("Wizard-StatusLocal: " + statuslocal);
		System.out.println("\nWizard-Local: " + local.getCategoria());
		
		if(this.statuslocal.getId() == 9){
			System.out.println("N�o Fecha Link!!");
			//return "confirm";
		}
		
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {

			return event.getNewStep();
		}
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

	public List<Ponto> getListagemdeponto() {
		return listagemdeponto;
	}

	public void setListagemdeponto(List<Ponto> listagemdeponto) {
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

	public List<Local> getListlocal() {
		return listlocal;
	}

	public void setListlocal(List<Local> listlocal) {
		this.listlocal = listlocal;
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

	public List<Categoria> getListadecategorias() {
		return listadecategorias;
	}

	public void setListadecategorias(List<Categoria> listadecategorias) {
		this.listadecategorias = listadecategorias;
	}

	public void onTransfer(TransferEvent event) {
		StringBuilder builder = new StringBuilder();
		for (Object item : event.getItems()) {
			builder.append(((Categoria) item).getNome()).append("<br />");
		}
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
