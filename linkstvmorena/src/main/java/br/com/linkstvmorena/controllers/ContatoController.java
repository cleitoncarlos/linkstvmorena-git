package br.com.linkstvmorena.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.linkstvmorena.model.Contato;
import br.com.linkstvmorena.model.Local;
import br.com.linkstvmorena.model.Status_Contato;
import br.com.linkstvmorena.msg.util.MenssagemUtil;
import br.com.linkstvmorena.service.ContatoService;

/*@Controller
@ViewScoped*/
public class ContatoController {

	@Autowired
	private LocalController localController;
	@Autowired
	private ContatoService contatoService;
	private Local local;
	private Contato contato;
	private List<Status_Contato> listadestatus;
	private List<Contato> listadecontato;

	@PostConstruct
	private void init() {
		this.local = new Local();
		this.contato = new Contato();
		listadecontato = new ArrayList<Contato>();
		listadestatus = new ArrayList<Status_Contato>();
		for (Status_Contato lc : Status_Contato.values()) {
			listadestatus.add(lc);
		}

	}

	public void adicionaContato() {
		this.listadecontato.add(contato);
		localController.recebeContato(this.listadecontato);
		System.out.println("ContatoController - Contatos: " + this.contato);
		System.out.println("ContatoController - Lista de Contatos: " + this.listadecontato);
		this.contato = new Contato();
	}

	public void adicionaLocal(Local l){
		System.out.println("Entrou, ContatoController-adicionaLocal.");
		//this.contato.setLocal(l);
		System.out.println("Saiu, ContatoController-adicionaLocal");
	}
	public void salvar(Contato c) {
		try {
			System.out.println("ContatoController: " + c);
			this.contatoService.salvar(contato);
			MenssagemUtil.mensagemInfo("Salvo com Sucesso!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}
	}

	public List<Status_Contato> getListadestatus() {
		return listadestatus;
	}

	public void setListadestatus(List<Status_Contato> listadestatus) {
		this.listadestatus = listadestatus;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
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

	public List<Contato> getListadecontato() {
		return listadecontato;
	}

	public void setListadecontato(List<Contato> listadecontato) {
		this.listadecontato = listadecontato;
	}

}
