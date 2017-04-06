package br.com.linkstvmorena.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.linkstvmorena.model.Local;
import br.com.linkstvmorena.model.Ponto;
import br.com.linkstvmorena.model.StatusPonto;
import br.com.linkstvmorena.msg.util.MenssagemUtil;
import br.com.linkstvmorena.service.PontoService;

@Controller
@ViewScoped
public class PontoController {

	@Autowired
	private PontoService pontoService;
	private Ponto ponto;
	private StatusPonto statusPonto;
	private Local local;
	@Autowired
	private LocalController localController;
	@Autowired
	private StatusPontoController statusPontoController;
	private List<Ponto> listadeponto;

	@PostConstruct
	private void init() {
		this.ponto = new Ponto();
		this.statusPonto = new StatusPonto();
		this.listadeponto = new ArrayList<Ponto>();

	}
	
	public void recebeStatusPonto(StatusPonto sp){
		//this.statusPonto = sp;
		ponto.setStatusponto(sp);
		System.out.println("PontoController - Ponto: " +ponto);
		System.out.println("PontoController - StatusPonto: " +sp);
	}

	public void adicionaPonto() {
		this.listadeponto.add(this.ponto);
		localController.recebePonto(this.listadeponto);
		System.out.println("PontoController-AdicionaPonto: voltou do LocalController!");
		statusPontoController.adicionaPonto(listadeponto);
		System.out.println("PontoController-Adicionaponto-Ponto: " + this.ponto);
		System.out.println("PontoController-Adicionaponto-Lista de Ponto: " + this.listadeponto);
		this.ponto = new Ponto();
		//localController.teste();
	}
	public void adicionaLocal(Local l){
		System.out.println("Entrou, PontoController-adicionaLocal.");
		ponto.setLocal(l);
		System.out.println("PontoController - Ponto: " +ponto);
		System.out.println("PontoController - Local: " +l);
		System.out.println("Saiu, PontoController-adicionaLocal.");
	}
	
	public void salvar(Ponto p){
		try {
			System.out.println("Ponto Controller: "+p);
			this.pontoService.salvar(p);
			init();
			MenssagemUtil.mensagemInfo("Salvo com Sucesso!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}
	}
	public void teste() {
		// localController = new LocalController();
		System.out.println("\nPonto: " + this.statusPonto);
		this.ponto.setStatusponto(statusPonto);
		System.out.println("\nPonto: " + this.ponto);
		// localController.getStatuslocal().getNome();
	}

	public List<Ponto> getListadeponto() {
		return listadeponto;
	}

	public void setListadeponto(List<Ponto> listadeponto) {
		this.listadeponto = listadeponto;
	}

	public LocalController getLocalController() {
		return localController;
	}

	public void setLocalController(LocalController localController) {
		this.localController = localController;
	}

	public StatusPonto getStatusPonto() {
		return statusPonto;
	}

	public void setStatusPonto(StatusPonto statusPonto) {
		this.statusPonto = statusPonto;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public Ponto getPonto() {
		return ponto;
	}

	public void setPonto(Ponto ponto) {
		this.ponto = ponto;
	}

}
