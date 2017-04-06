package br.com.linkstvmorena.msg.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MenssagemUtil {

	public static void mensagemInfo(String msg) {
		/*
		 * //acessando Contexto FacesContext fc =
		 * FacesContext.getCurrentInstance(); //Criando Objeto mensagem
		 * FacesMessage msg = new
		 * FacesMessage(FacesMessage.SEVERITY_INFO,"Salvo!", null);
		 * fc.addMessage(null, msg);
		 */

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, msg,"Info"));

	}

	public static void mensagemErro(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, msg,"Error!"));
	}

}
