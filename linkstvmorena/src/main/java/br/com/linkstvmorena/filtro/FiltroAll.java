package br.com.linkstvmorena.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.linkstvmorena.controllers.UsuarioController;
import br.com.linkstvmorena.msg.util.MenssagemUtil;

@WebFilter(servletNames = { "Faces Servlet" })
public class FiltroAll implements Filter {

	/**
	 * Default constructor.
	 */
	public FiltroAll() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		
		HttpServletRequest req = (HttpServletRequest) request;

		HttpServletResponse res = (HttpServletResponse) response;

		/*
		 * if (req.getRequestURI().equals("/linkstvmorena/home.xhtml") ||
		 * req.getRequestURI().equals("/linkstvmorena/"))
		 * chain.doFilter(request, response);
		 * 
		 * else { System.out.println("Entrou no Filtro!" + req.getRequestURI());
		 * res.sendRedirect("/linkstvmorena/"); }
		 */

		HttpSession session = req.getSession();

		if ((session.getAttribute("UsuarioLogado") != null) || (req.getRequestURI().endsWith("/linkstvmorena/"))
				|| (req.getRequestURI().endsWith("home.xhtml"))
				|| (req.getRequestURI().endsWith("usuario.xhtml"))
				|| (req.getRequestURI().endsWith("login.xhtml"))
				|| (req.getRequestURI().contains("javax.faces.resource/"))) {

			// redireciona("/Logado.xhtml", response);
			
			System.out.println("URI : " + req.getRequestURI());

			chain.doFilter(request, response);
		}

		else {
			System.out.println("Acesso Negado !!");
			System.out.println("URI : " + req.getRequestURI());
			redireciona("/linkstvmorena/", response);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

	private void redireciona(String url, ServletResponse response) throws IOException {
		HttpServletResponse res = (HttpServletResponse) response;
		res.sendRedirect(url);
	}

}
