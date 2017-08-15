package br.com.linkstvmorena.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.linkstvmorena.model.Permissao;
import br.com.linkstvmorena.model.UsuarioLogado;
import br.com.linkstvmorena.msg.util.MenssagemUtil;
import br.com.linkstvmorena.service.UsuarioService;

@Controller
@ViewScoped
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	private UsuarioLogado usuario;
	private List<UsuarioLogado> listagemUsuario;
	private List<Permissao> listagemPermissao;

	public UsuarioController() {
	}

	@PostConstruct
	public void init() {

		usuario = new UsuarioLogado();

		listagemUsuario = new ArrayList<>();
		try {
			listagemUsuario = usuarioService.buscarTodos();

		} catch (Exception e) {
			MenssagemUtil.mensagemErro("Nao Foi Possivel Carregar a Lista!!" + e.getMessage());
		}

		listagemPermissao = new ArrayList<Permissao>();
		for (Permissao p : Permissao.values()) {
			listagemPermissao.add(p);
		}

	}

	public void salvar() {

		try {
			usuario.setDataCadastro(Calendar.getInstance());
			this.usuarioService.salvar(usuario);
			init();
			MenssagemUtil.mensagemInfo("Salvo com Sucesso!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}

	}

	public UsuarioLogado editar(UsuarioLogado usuario) {
		try {
			this.usuario = usuarioService.buscarPorId(usuario.getId());
			return this.usuario;
		} catch (Exception e) {
			return null;
		}

	}

	public void excluir(UsuarioLogado usuario) {
		try {
			usuarioService.excluir(usuario);
			init();
			MenssagemUtil.mensagemInfo("Excluido com Sucesso!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}
	}

	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public UsuarioLogado getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioLogado usuario) {
		this.usuario = usuario;
	}

	public List<UsuarioLogado> getListagemUsuario() {
		return listagemUsuario;
	}

	public void setListagemUsuario(List<UsuarioLogado> listagemUsuario) {
		this.listagemUsuario = listagemUsuario;
	}

	public List<Permissao> getListagemPermissao() {
		return listagemPermissao;
	}

	public void setListagemPermissao(List<Permissao> listagemPermissao) {
		this.listagemPermissao = listagemPermissao;
	}
	

}
