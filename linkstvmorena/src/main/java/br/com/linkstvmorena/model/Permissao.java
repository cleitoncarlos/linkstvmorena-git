package br.com.linkstvmorena.model;

public enum Permissao {

	ADMINISTRADOR (1,"Administrador"),
	USUARIO (2, "Usuario"); 
	
	
	private Permissao(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	private final int id;
	private String nome;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getId() {
		return id;
	}
	
	
}
