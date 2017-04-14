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
import br.com.linkstvmorena.msg.util.MenssagemUtil;
import br.com.linkstvmorena.service.CategoriaService;
import br.com.linkstvmorena.service.LocalService;

/*@Controller
@ViewScoped*/
public class LocalController {

	@Autowired
	private LocalService localService;
	@Autowired
	private CategoriaService categoriaService;
	private Local local;
	private Ponto ponto;
	private List<Categoria> listadecategorias;
	private Contato contato;
	private StatusLocal statuslocal;
	private List<Ponto> listaponto;
	private List<Local> listlocal;
	@Autowired
	private PontoController pontoController;
	@Autowired
	private ContatoController contatoController;
	@Autowired
	private CategoriaController categoriaController;
	private List<Contato> listcontato;
	private DualListModel<Categoria> listcategorias;

	@PostConstruct
	public void init() {
		local = new Local();
		listadecategorias = new ArrayList<Categoria>();
		listcontato = new ArrayList<Contato>();
		contato = new Contato();
		ponto = new Ponto();
		statuslocal = new StatusLocal();
		listcontato = new ArrayList<Contato>();
		listaponto = new ArrayList<Ponto>();
		try {
			List<Categoria> fonte = categoriaService.buscarTodos();
			List<Categoria> alvo = new ArrayList<Categoria>();
			listcategorias = new DualListModel<Categoria>(fonte, alvo);

		} catch (Exception e) {
			MenssagemUtil.mensagemErro("Nao Foi Possivel Carregar a Lista!!" + e.getMessage());
		}
	}

	public void salvar() {

		try {
			// this.local.setStatuslocal(statuslocal);
			this.localService.salvar(local);
			/*for (Ponto p : listaponto) {
				 p.setLocal(this.local);

				this.pontoController.salvar(p);
			}
			for (Contato c : listcontato) {
				c.setLocal(this.local);
				this.contatoController.salvar(c);
			}
*/
			init();
			MenssagemUtil.mensagemInfo("Salvo com Sucesso!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}

	}

	// adiciona varios contatos na lista antes de inserir no local
	public void adicionaContato() {
		this.listcontato.add(this.contato);
		System.out.println("Conatos: " + this.contato);
		System.out.println("Lista de Contatos: " + this.listcontato);
		this.contato = new Contato();
	}

	public void recebeCategoria(List<Categoria> listcategoria) {
		this.listadecategorias = listcategoria;

		System.out.println("LocalController - Lista de Categoria: " + listadecategorias);
	}

	public void recebeContato(List<Contato> listadecontato) {
		this.listcontato = listadecontato;
		System.out.println("LocalController - Contato: " + this.contato + "\n Local: " + local);
	}

	public void recebePonto(List<Ponto> listadeponto) {
		this.listaponto = listadeponto;
		System.out.println("\nLocalConrtoller-RecebePonto: " + this.ponto+"ListadePonto: "+listaponto);
	}

	public void recebeStatusLocal(StatusLocal sl) {
		this.statuslocal = sl;
		this.local.setStatuslocal(this.statuslocal);
		System.out.println("\nLocal: " + this.local);
		System.out.println("LocalController - StatusLocal: " + this.statuslocal);
	}

	public void teste() {
		// pontoController = new PontoController();
		System.out.println("StatusLcoal: " + this.statuslocal);
		System.out.println("\nLocal: " + this.local);

		// pontoController.teste();

	}

	public String onFlowProcess(FlowEvent event) {
	/*	System.out.println("LocalController - StatusLocal: " + this.statuslocal);
		if (pontoController.getLocal() == null) {
			System.out.println("Entrou, LocalController-onFlowProcess: pontoController.adicionaLocal");
			pontoController.adicionaLocal(this.local);
			System.out.println("Saiu, LocalController-onFlowProcess: pontoController.adicionaLocal");
		}
		if (categoriaController.getLocal() == null) {
			System.out.println("Entrou, LocalController-onFlowProcess: categoriaController.adicionaLocal");
			categoriaController.adicionaLocal(this.local);
			System.out.println("Saiu, LocalController-onFlowProcess: categoriaController.adicionaLocal");
		}
		if (contatoController.getLocal() == null) {
			System.out.println("Entrou, LocalController-onFlowProcess: contatoController.adicionaLocal");
			contatoController.adicionaLocal(this.local);
			System.out.println("Saiu, LocalController-onFlowProcess: contatoController.adicionaLocal");
		}*/
		/*this.local.setCategoria(this.listadecategorias);
		this.local.setContato(listcontato);
		this.local.setPonto(listaponto);*/

		System.out.println("\nWizard-Local: " + local);
		System.out.println("Wizard-Contato: " + contato);
		System.out.println("Wizard-Ponto: " + ponto);

		if (skip) {

			skip = false; // reset in case user goes back
			return "confirm";
		} else {

			return event.getNewStep();
		}
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

	public List<Contato> getListcontato() {
		return listcontato;
	}

	public void setListcontato(List<Contato> listcontato) {
		this.listcontato = listcontato;
	}

	public PontoController getPontoController() {
		return pontoController;
	}

	public void setPontoController(PontoController pontoController) {
		this.pontoController = pontoController;
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

		/*
		 * FacesMessage msg = new FacesMessage();
		 * msg.setSeverity(FacesMessage.SEVERITY_INFO);
		 * msg.setSummary("Items Transferred");
		 * msg.setDetail(builder.toString());
		 * 
		 * FacesContext.getCurrentInstance().addMessage(null, msg); }
		 */

		/*
		 * public void onTransfer(TransferEvent event) { StringBuilder builder =
		 * new StringBuilder();
		 * 
		 * for (Object item : event.getItems()) { Integer id =
		 * Integer.valueOf(item.toString());
		 * System.out.println("Metodo onTransfer-iD: " + id); Categoria c =
		 * this.listcategorias.getSource().get(id - 1);
		 * System.out.println("Metodo onTransfer-c: " + c);
		 * builder.append(c.getNome()).toString(); //
		 * event.getItems().toString(); // event.isAdd();
		 * 
		 * // builder.append(((Categoria) item).getNome()).append("<br />"); }
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
