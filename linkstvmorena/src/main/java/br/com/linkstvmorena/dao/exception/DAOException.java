package br.com.linkstvmorena.dao.exception;

public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DAOException(String msg, Throwable e) {
		
		super(msg,e);
	}

	public DAOException(String msg){
		super(msg);
	}
	public DAOException(Throwable e){
		super(e);
	}

}
