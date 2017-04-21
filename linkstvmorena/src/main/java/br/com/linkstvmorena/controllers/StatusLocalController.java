package br.com.linkstvmorena.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FlowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.linkstvmorena.model.StatusLocal;
import br.com.linkstvmorena.msg.util.MenssagemUtil;
import br.com.linkstvmorena.service.StatusLocalService;

@ViewScoped
@Controller
public class StatusLocalController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private StatusLocalService statuslocalService;
	//@Autowired
	private LocalController localcontroller;
	private StatusLocal statuslocal;
	private Integer statusid;
	private List<StatusLocal> liststatuslocal;

	public StatusLocalController() {
	}

	@PostConstruct
	public void init() {
		this.statuslocal = new StatusLocal();
		//this.localcontroller = new LocalController();
		this.liststatuslocal = new ArrayList<>();
		try {
			this.liststatuslocal = statuslocalService.buscarTodos();
			System.out.println(liststatuslocal);
		} catch (Exception e) {
			MenssagemUtil.mensagemErro("Nao Foi Possivel Carregar a Lista!!" + e.getMessage());
		}
	}

	public void salvar() {

		try {
			this.statuslocalService.salvar(this.statuslocal);
			init();
			MenssagemUtil.mensagemInfo("Salvo com Sucesso!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}
	}

	public StatusLocal editar(StatusLocal statuslocal) {
		try {
			this.statuslocal = statuslocalService.buscarPorId(statuslocal);
			System.out.println(this.statuslocal);
			return this.statuslocal;
		} catch (Exception e) {
			return null;
		}
	}

	public void excluir(StatusLocal statuslocal) {
		try {
			System.out.println(statuslocal);
			statuslocalService.excluir(statuslocal);
			init();
			MenssagemUtil.mensagemInfo("Excluido com Sucesso!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}
	}

	public void verifica(FlowEvent event) {

		if (event.getNewStep().equals("1") && this.getStatuslocal().getId() == 20) {
			System.out.println("Step Ok!");

		}
	}

	public void teste() {
		System.out.println("StatusLocalController-teste: "+this.statuslocal);
		localcontroller.recebeStatusLocal(this.statuslocal);
	}

	public StatusLocal getStatuslocal() {
		return statuslocal;
	}

	public void setStatuslocal(StatusLocal statuslocal) {
		this.statuslocal = statuslocal;
	}

	public List<StatusLocal> getListstatuslocal() {
		return liststatuslocal;
	}

	public void setListstatuslocal(List<StatusLocal> liststatuslocal) {
		this.liststatuslocal = liststatuslocal;
	}

	public LocalController getLocalcontroller() {
		return localcontroller;
	}

	public void setLocalcontroller(LocalController localcontroller) {
		this.localcontroller = localcontroller;
	}

	public Integer getStatusid() {
		return statusid;
	}

	public void setStatusid(Integer statusid) {
		this.statusid = statusid;
	}
}
