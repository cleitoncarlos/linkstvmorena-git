package br.com.linkstvmorena.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
import br.com.linkstvmorena.model.Status;
import br.com.linkstvmorena.msg.util.MenssagemUtil;
import br.com.linkstvmorena.service.Servico;

@Controller
@ViewScoped
public class Controlador {

	@Autowired
	private Servico servico;

	private Local local;
	private Local selectedLocal;
	private Ponto ponto;
	private Categoria categoria;
	private Contato contato;
	private StatusLocal statuslocal;
	private StatusPonto statusPonto;
	private List<Local> listadelocais;
	private List<Status> listadestatus;
	private List<Ponto> listaponto;
	private Set<Contato> listadecontato;
	private List<StatusLocal> liststatuslocal;
	private List<StatusPonto> liststatusponto;
	private Set<Ponto> listagemdeponto;
	private DualListModel<Categoria> listcategorias;
	private List<Local> listalocal;
	private List<Categoria> fonte;
	private List<Categoria> alvo;
	private String busca;
	private boolean painel;
	private List<Local> listSearch;

	@PostConstruct
	public void init() {
		
		painel = false;
		listSearch = new ArrayList<Local>();
		local = new Local();
		contato = new Contato();
		ponto = new Ponto();
		categoria = new Categoria();
		statuslocal = new StatusLocal();
		statusPonto = new StatusPonto();
		listadelocais = new ArrayList<Local>();
		listadecontato = new HashSet<Contato>();
		liststatusponto = new ArrayList<StatusPonto>();
		listaponto = new ArrayList<Ponto>();
		listagemdeponto = new HashSet<>();
		liststatuslocal = new ArrayList<>();

		listadestatus = new ArrayList<Status>();
		for (Status lc : Status.values()) {
			listadestatus.add(lc);
		}

		try {
			listadelocais = servico.buscarLocal(Status.ATIVO);
			liststatuslocal = servico.buscarStatusLocal();
			liststatusponto = servico.buscarStatusPonto();
			fonte = new ArrayList<Categoria>();
			fonte = servico.buscarCategorias();
			alvo = new ArrayList<Categoria>();
			System.out.println("Fonte: " + fonte);
			listcategorias = new DualListModel<Categoria>(fonte, alvo);

		} catch (Exception e) {
			MenssagemUtil.mensagemErro("Nao Foi Possivel Carregar a Lista!!" + e.getMessage());
		}
	}

	public void selecaoLocal(Local l) {
		selectedLocal = new Local();
		selectedLocal = l;
		System.out.println("Entrou!!");
	}

	public List<Local> search() {
		listSearch = new ArrayList<Local>();
		
		try {
			if (busca != "") {
				painel=true;
				listSearch = servico.buscaLocalTela(busca);
			}else
				return null;

			System.out.println("StringBusca: " + busca);
			System.out.println("Busca Local: " + listSearch);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public String salvar() {
		try {
			servico.salvar(local);
			init();
			MenssagemUtil.mensagemInfo("Salvo com Sucesso!!");
			return "localteste";
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}
		return "";
	}

	public Local editar(Local l) {
		try {
			init();
			listalocal = new ArrayList<Local>();
			listalocal = servico.buscarPorId(l.getId());
			for (Local ls : listalocal) {
				this.local = ls;
				this.listagemdeponto = ls.getPonto();
				this.listadecontato = ls.getContato();
				this.statuslocal = ls.getStatuslocal();
				Iterator<Categoria> it = ls.getCategoria().iterator();
				while (it.hasNext()) {
					Categoria posicao = it.next();
					this.fonte.remove(posicao);
					this.alvo.add(posicao);
				}
			}
			return this.local;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public void remover(Local l) {
		try {
			l.setStatus(Status.INATIVO);
			servico.salvar(l);
			// servico.remover(l);
			init();
			MenssagemUtil.mensagemInfo("Excluido com Sucesso!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}
	}

	public Ponto editarPonto(Ponto p) {
		this.listagemdeponto.remove(p);
		this.ponto = p;
		this.statusPonto = p.getStatusponto();
		return this.ponto;
	}

	public Ponto excluirPonto(Ponto c) {
		this.listagemdeponto.remove(c);
		return this.ponto;
	}

	public Contato editarContato(Contato c) {
		this.contato = c;
		return this.contato;
	}

	public Contato excluirContato(Contato c) {
		this.listadecontato.remove(c);
		return this.contato;
	}

	public void adicionaCategoria() {
		this.local.adicionaCategorias(listcategorias.getTarget());
	}

	public void adicionaContato() {
		listadecontato.add(contato);
		local.adicionaContatos(listadecontato);
		contato = new Contato();
	}

	public void carregaCategoria() {
		List<Categoria> fonte = servico.buscarCategorias();
		List<Categoria> alvo = new ArrayList<Categoria>();
		listcategorias = new DualListModel<Categoria>(fonte, alvo);
	}

	public void adicionaPonto() {
		listagemdeponto.add(ponto);
		local.adicionaPonto(listagemdeponto);
		/*
		 * this.local.setPonto(listagemdeponto); this.ponto.setLocal(local);
		 */
		this.ponto = new Ponto();
		this.statusPonto = new StatusPonto();
	}

	public void adicionaStatusPonto() {
		this.ponto.setStatusponto(statusPonto);
	}

	public void adicionaStatusLocal() {
		this.local.setStatuslocal(this.statuslocal);
	}

	public String onFlowProcess(FlowEvent event) {

		/*if(this.statuslocal.getNome().equals("Não Fecha Link")){
			skip = false; // reset in case user goes back
			init();
			return "confirm";
		}
		 else {
				return event.getNewStep();
			}*/
		
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}

	public List<Local> getListSearch() {
		return listSearch;
	}

	public void setListSearch(List<Local> listSearch) {
		this.listSearch = listSearch;
	}

	public String getBusca() {
		return busca;
	}

	public void setBusca(String busca) {
		this.busca = busca;
	}

	public List<Local> getListadelocais() {
		return listadelocais;
	}

	public void setListadelocais(List<Local> listadelocais) {
		this.listadelocais = listadelocais;
	}

	public Set<Contato> getListadecontato() {
		return listadecontato;
	}

	public void setListadecontato(Set<Contato> listadecontato) {
		this.listadecontato = listadecontato;
	}

	public List<Status> getListadestatus() {
		return listadestatus;
	}

	public void setListadestatus(List<Status> listadestatus) {
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

	public Local getSelectedLocal() {
		return selectedLocal;
	}

	public void setSelectedLocal(Local selectedLocal) {
		this.selectedLocal = selectedLocal;
	}

	public List<Local> getListalocal() {
		return listalocal;
	}

	public void setListalocal(List<Local> listalocal) {
		this.listalocal = listalocal;
	}

	public void onTransfer(TransferEvent event) {
		/*
		 * StringBuilder builder = new StringBuilder(); for (Object item :
		 * event.getItems()) { builder.append(((Categoria)
		 * item).getNome()).append("<br />"); }
		 */
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

	
	public boolean isPainel() {
		return painel;
	}

	public void setPainel(boolean painel) {
		this.painel = painel;
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