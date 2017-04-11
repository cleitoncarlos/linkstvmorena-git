package br.com.linkstvmorena.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.linkstvmorena.model.Categoria;
import br.com.linkstvmorena.model.Local;
import br.com.linkstvmorena.msg.util.MenssagemUtil;
import br.com.linkstvmorena.service.CategoriaService;
import br.com.linkstvmorena.service.exception.ServiceException;

@Controller
@ViewScoped
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private LocalController localController;
	private Categoria categoria;
	private Local local;
	private List<Categoria> listcat;
	private DualListModel<Categoria> listcategorias;
	
	public CategoriaController() {
	}

	@PostConstruct
	public void init() {
		categoria = new Categoria();
		listcat = new ArrayList<>();
		try {
			listcat = categoriaService.buscarTodos();
			List<Categoria> fonte = listcat;
			List<Categoria> alvo = new ArrayList<Categoria>();
			listcategorias = new DualListModel<Categoria>(fonte, alvo);
		} catch (Exception e) {
			MenssagemUtil.mensagemErro("Nao Foi Possivel Carregar a Lista!!" + e.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	public void adicionaCategoria() {
		this.localController.recebeCategoria((Set<Categoria>) this.listcategorias.getTarget());
	}
	
	public void adicionaLocal(Local l){
		System.out.println("Entrou, categoriaController-adicionaLocal.");
		//this.categoria.setLocal(l);
		System.out.println("Saiu, categoriaController-adicionaLocal." + categoria);
	}

	public void salvar() {

		try {
			this.categoriaService.salvar(categoria);
			init();
//			localController = new LocalController();
//			this.localController.init();
			MenssagemUtil.mensagemInfo("Salvo com Sucesso!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}

	}

	public Categoria preparaExcluir(Categoria categoria){
		try {System.out.println(categoria);
			this.categoria = categoriaService.buscarPorId(categoria);
			excluir(categoria);
			return categoria;
		} catch (ServiceException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Categoria editar(Categoria categoria){
		try {
			this.categoria=categoriaService.buscarPorId(categoria);
			System.out.println(this.categoria);
			return this.categoria;
		} catch (Exception e) {
			return null;
		}
		
	}
	public void excluir(Categoria categoria) {
		try {System.out.println(categoria);
			categoriaService.excluir(categoria);
			init();
			MenssagemUtil.mensagemInfo("Excluido com Sucesso!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}
	}

	

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public LocalController getLocalController() {
		return localController;
	}

	public void setLocalController(LocalController localController) {
		this.localController = localController;
	}

	public DualListModel<Categoria> getListcategorias() {
		return listcategorias;
	}

	public void setListcategorias(DualListModel<Categoria> listcategorias) {
		this.listcategorias = listcategorias;
	}

	public void cancelar(){
		setCategoria(new Categoria());
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getListcat() {
		return listcat;
	}

	public void setListcat(List<Categoria> listcat) {
		this.listcat = listcat;
	}

	public void destroyWorld() {
		addMessage("System Error", "Please try again later.");
	}

	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
