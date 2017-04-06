package br.com.linkstvmorena.service.exception;


public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(Throwable e) {
		super(e);
	}
	public ServiceException(String msg,Throwable e) {
		super(msg ,e);
	}

}
