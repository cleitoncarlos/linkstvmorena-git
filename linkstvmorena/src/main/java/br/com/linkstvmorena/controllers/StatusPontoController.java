package br.com.linkstvmorena.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.linkstvmorena.model.Ponto;
import br.com.linkstvmorena.model.StatusPonto;
import br.com.linkstvmorena.msg.util.MenssagemUtil;
import br.com.linkstvmorena.service.StatusPontoService;

@Controller
@ViewScoped
public class StatusPontoController implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private StatusPontoService statusPontoService;
	private StatusPonto statusPonto;
	private Ponto ponto;
	private List<StatusPonto> liststatus;
	//@Autowired
	private PontoController pontoController;
	
	
	public StatusPontoController() {
	}

	@PostConstruct
	public void init() {
		this.statusPonto= new StatusPonto();
		this.liststatus = new ArrayList<>();
		try {
			liststatus = statusPontoService.buscarTodos();
		} catch (Exception e) {
			MenssagemUtil.mensagemErro("Nao Foi Possivel Carregar a Lista!!" + e.getMessage());
		}
	}

	public void adicionaStatus(){
		//System.out.println("StatusPontoController - StatusPonto: "+this.statusPonto);
		pontoController.recebeStatusPonto(this.statusPonto);
	}
	public void adicionaPonto(List<Ponto> p){
	/*	System.out.println("Entrou: StatusPontoController-adicionaPonto: "+p);
		statusPonto.setPonto(p);*/
		System.out.println("StatusPontoController-adicionaPonto: "+statusPonto);
	}
	public void salvar() {

		try {
			this.statusPontoService.salvar(this.statusPonto);
			init();
			MenssagemUtil.mensagemInfo("Salvo com Sucesso!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}
	}
	
	public StatusPonto editar(StatusPonto statuslocal){
		try {
			this.statusPonto=statusPontoService.buscarPorId(statuslocal);
			System.out.println(this.statusPonto);
			return this.statusPonto;
		} catch (Exception e) {
			return null;
		}
	}
	
	public void excluir(StatusPonto statusponto) {
		try {System.out.println(statusponto);
			statusPontoService.excluir(statusponto);
			init();
			MenssagemUtil.mensagemInfo("Excluido com Sucesso!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}
	}

	

	
	public Ponto getPonto() {
		return ponto;
	}

	public void setPonto(Ponto ponto) {
		this.ponto = ponto;
	}

	public StatusPonto getStatusPonto() {
		return statusPonto;
	}

	public void setStatusPonto(StatusPonto statusPonto) {
		this.statusPonto = statusPonto;
	}

	public PontoController getPontoController() {
		return pontoController;
	}

	public void setPontoController(PontoController pontoController) {
		this.pontoController = pontoController;
	}

	public StatusPonto getStatusponto() {
		return statusPonto;
	}

	public void setStatusponto(StatusPonto statusponto) {
		this.statusPonto = statusponto;
	}

	public List<StatusPonto> getListstatus() {
		return liststatus;
	}

	public void setListstatus(List<StatusPonto> listcat) {
		this.liststatus = listcat;
	}
}
