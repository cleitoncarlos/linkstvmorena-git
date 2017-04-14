package br.com.linkstvmorena.backinbean;

import java.util.List;

import javax.faces.bean.ViewScoped;

import org.springframework.stereotype.Controller;

import br.com.linkstvmorena.controllers.CategoriaController;
import br.com.linkstvmorena.controllers.CategoriaLocalController;
import br.com.linkstvmorena.controllers.LocalController;
import br.com.linkstvmorena.controllers.StatusLocalController;
import br.com.linkstvmorena.model.StatusLocal;


public class BackingbeanWizard {
	
	private CategoriaController categoriaController;
	private CategoriaLocalController catLocalController;
	private LocalController localController;
	private StatusLocalController statusLocalController;
	
	private List<StatusLocal> listadestatus;
	/*@PostConstruct
	public void init(){
		listadestatus = new ArrayList<>();
		this.listadestatus = statusLocalController.getListstatuslocal();
		System.out.println("Lista de Status: " +listadestatus);
	}*/
	
	public List<StatusLocal> getListadestatus() {
		return listadestatus;
	}

	public void setListadestatus(List<StatusLocal> listadestatus) {
		this.listadestatus = listadestatus;
	}

	public CategoriaController getCategoriaController() {
		return categoriaController;
	}
	public void setCategoriaController(CategoriaController categoriaController) {
		this.categoriaController = categoriaController;
	}
	public CategoriaLocalController getCatLocalController() {
		return catLocalController;
	}
	public void setCatLocalController(CategoriaLocalController catLocalController) {
		this.catLocalController = catLocalController;
	}
	public LocalController getLocalController() {
		return localController;
	}
	public void setLocalController(LocalController localController) {
		this.localController = localController;
	}
	public StatusLocalController getStatusLocalController() {
		return statusLocalController;
	}
	public void setStatusLocalController(StatusLocalController statuslocalController) {
		this.statusLocalController = statuslocalController;
	}
}
