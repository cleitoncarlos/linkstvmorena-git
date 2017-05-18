package br.com.linkstvmorena.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Local implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private String nome;
	private String logradouro;
	private String cep;
	private String numero;
	private String bairro;
	private String complemento;
	private String observacao;

	@ManyToMany(cascade = CascadeType.MERGE, fetch=FetchType.EAGER)
	private Set<Contato> contato = new HashSet<>();
	@ManyToMany(cascade = CascadeType.MERGE, fetch=FetchType.EAGER)
	private Set<Categoria> categoria = new HashSet<>();

	@ManyToOne
	private StatusLocal statuslocal;

	@OneToMany(cascade = CascadeType.MERGE,fetch=FetchType.EAGER)
	private Set<Ponto> ponto;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private Status status = Status.ATIVO;
	
	public void adicionaCategorias(List<Categoria> categorias) {
		this.categoria.addAll(categorias);
		//categorias.forEach(c -> c.getLocal().add(this));
	}

	public void adicionaContatos(Set<Contato> contatos) {
		this.contato.addAll(contatos);
		//contatos.forEach(c -> c.getLocais().add(this));
	}

	public void adicionaPonto(Set<Ponto> pontos) {
		this.ponto = new HashSet<>();
		this.ponto.addAll(pontos);
		//pontos.forEach(p -> p.setLocal(this));
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<Ponto> getPonto() {
		return ponto;
	}

	public void setPonto(Set<Ponto> ponto) {
		this.ponto = ponto;
	}

	public Set<Contato> getContato() {
		return contato;
	}

	public void setContato(Set<Contato> contato) {
		this.contato = contato;
	}

	public Integer getId() {
		return id;
	}

	public Set<Categoria> getCategoria() {
		return categoria;
	}

	public void setCategoria(Set<Categoria> categoria) {
		this.categoria = categoria;
	}

	public StatusLocal getStatuslocal() {
		return statuslocal;
	}

	public void setStatuslocal(StatusLocal statuslocal) {
		this.statuslocal = statuslocal;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public String toString() {
		return "Local [id=" + id + ", logradouro=" + logradouro + ", cep=" + cep + ", numero=" + numero + ", bairro="
				+ bairro + ", complemento=" + complemento + ", nome=" + nome + ", observacao=" + observacao
				+ ", statuslocal=" + statuslocal + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((statuslocal == null) ? 0 : statuslocal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Local other = (Local) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (complemento == null) {
			if (other.complemento != null)
				return false;
		} else if (!complemento.equals(other.complemento))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (logradouro == null) {
			if (other.logradouro != null)
				return false;
		} else if (!logradouro.equals(other.logradouro))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (statuslocal == null) {
			if (other.statuslocal != null)
				return false;
		} else if (!statuslocal.equals(other.statuslocal))
			return false;
		return true;
	}

}