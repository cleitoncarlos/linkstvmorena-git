package br.com.linkstvmorena.controllers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.linkstvmorena.model.Permissao;
import br.com.linkstvmorena.model.UsuarioLogado;
import br.com.linkstvmorena.msg.util.MenssagemUtil;
import br.com.linkstvmorena.service.UsuarioService;
import br.com.linkstvmorena.session.SessionUtil;

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
			String senha = convertPasswordToMD5(usuario.getSenha());
			usuario.setSenha(senha);
			this.usuarioService.salvar(usuario);
			init();
			MenssagemUtil.mensagemInfo("Salvo com Sucesso!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro(e.getMessage());
		}

	}

	public static String convertPasswordToMD5(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");

		BigInteger hash = new BigInteger(1, md.digest(password.getBytes()));

		return String.format("%32x", hash);
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

	public void cancelar() {
		usuario = new UsuarioLogado();
	}

	public void autenticaUsuario() {

		try {
			usuarioService.verificaLogin(this.usuario);
			SessionUtil.setParam("UsuarioLogado", usuario);
			MenssagemUtil.mensagemInfo("Usuario Autenticado!!");
		} catch (Exception e) {
			MenssagemUtil.mensagemErro("Usuario nao Autenticado!");
		}
	}

	public boolean verificaUsuarioLogado() {

		System.out.println("Entrou Verifica UsuaruioLogado!");
		
		if (SessionUtil.getParam("UsuarioLogado") != null) {
			MenssagemUtil.mensagemInfo("Usuario Autenticado!!");
			return true;
		} else {
			MenssagemUtil.mensagemErro("Usuario nao Autenticado!");
			return false;
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
