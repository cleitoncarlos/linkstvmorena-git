package br.com.linkstvmorena.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.linkstvmorena.dao.exception.DAOException;
import br.com.linkstvmorena.model.Categoria;
import br.com.linkstvmorena.model.Foto;
import br.com.linkstvmorena.model.Local;
import br.com.linkstvmorena.model.Status;
import br.com.linkstvmorena.model.StatusLocal;
import br.com.linkstvmorena.model.StatusPonto;
import br.com.linkstvmorena.service.exception.ServiceException;

@Service
public class Servico {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void salvar(Local local) throws Exception {

		try {
			if (local.getId() == null) {
				Local localBusca = buscaNomeLocal(local.getNome());
				if (localBusca != null) {
					throw new ServiceException("Local ja  Cadastrado!", null);
				}
			}
			em.merge(local);
		} catch (Exception e) {
			throw new Exception("Erro ao cadastrar!: " + e);
		}
	}

	@Transactional
	public void salvarFoto(Foto foto) {
		em.merge(foto);
	}

	@SuppressWarnings("unchecked")
	public List<Foto> buscaFotos() {
		Query consulta = em.createQuery("select f from Foto f");
		return consulta.getResultList();
	}

	@Transactional
	public Local buscaNomeLocal(String nome) throws Exception {
		try {
			String jpql = "Select l from Local l where lower(l.nome)=lower(:nomeParam)";
			Query consulta = em.createQuery(jpql);
			consulta.setParameter("nomeParam", nome);
			consulta.setMaxResults(1);
			return (Local) consulta.getSingleResult();
		} catch (NoResultException e) {
			// engolir a exception
			return null;
		} catch (Exception causa) {
			throw new Exception("Erro ao Buscar Nome do Local!! " + causa);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Local> buscaLocalTela(String search) {
		String busca = "select DISTINCT l from Local l " + " where lower(l.nome) like lower(:nomeParam) "
				+ "order by l.nome ASC";

		Query consulta = em.createQuery(busca);
		consulta.setParameter("nomeParam", "%" + search + "%");
		return consulta.getResultList();
	}

	@Transactional
	public void remover(Local local) throws DAOException {
		try {
			em.remove(buscaParaExcluir(local.getId()));
		} catch (Exception e) {
			throw new DAOException("Nao Foi Possivel Excluir!", e.getCause());
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Local> buscarLocal(Status t) {
		System.out.println("Status: " + t);
		// t = Status.INATIVO;
		String busca = "select DISTINCT l from Local l " + " left Join fetch l.ponto " + " left Join fetch l.contato "
				+ " left Join fetch l.categoria " + " where l.status=:statusP " + "order by l.nome ASC";

		Query consulta = em.createQuery(busca);
		consulta.setParameter("statusP", t);
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Local> buscarPorId(Integer id) {
		try {

			System.out.println("Entrou BuscaPorId!");

			String busca = "select DISTINCT l from Local l " + " left Join fetch l.ponto p "
					+ "  left Join fetch l.contato ct" + " left Join fetch l.categoria c" + " Where l.id=:idParam "
					+ "order by l.nome ASC";

			Query consulta = em.createQuery(busca);
			consulta.setParameter("idParam", id);
			return consulta.getResultList();
		} catch (NoResultException e) {
			return null;
		}

	}

	public Local buscaParaExcluir(Integer id) throws ServiceException {
		String busca = "select DISTINCT l from Local l " + " left Join fetch l.ponto p "
				+ "  left Join fetch l.contato ct" + " left Join fetch l.categoria c" + " Where l.id=:idParam "
				+ "order by l.nome ASC";

		Query consulta = em.createQuery(busca);
		consulta.setParameter("idParam", id);
		return (Local) consulta.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<StatusPonto> buscarStatusPonto() {
		Query consulta = em.createQuery("select s from StatusPonto s order by nome ASC");
		return consulta.getResultList();
	}

	@Transactional
	public void salvarCategoria(Categoria categoria) throws Exception {

		try {
			if (categoria.getId() == null) {
				Categoria categoriaBusca = buscarPorNome(categoria);
				if (categoriaBusca != null) {
					throw new ServiceException("Categoria ja  Cadastrada!", null);
				}
			}

			em.merge(categoria);

		} catch (ServiceException e) {
			throw new ServiceException(e);
		}
	}

	public Categoria buscarPorNome(Categoria categoria) {
		try {
			return buscaPorNome(categoria.getNome());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Categoria buscaPorNome(String nome) throws Exception {

		try {
			String jpql = "Select c from Categoria c where lower(c.nome)=lower(:nomeParam)";

			Query consulta = em.createQuery(jpql);

			consulta.setParameter("nomeParam", nome);
			consulta.setMaxResults(1);
			return (Categoria) consulta.getSingleResult();
		} catch (NoResultException e) {
			// engolir a exception
			return null;
		} catch (Exception causa) {
			throw new Exception("Erro ao Buscar nome!! " + causa);

		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Categoria> buscarCategorias() {
		Query consulta = em.createQuery("select c from Categoria c order by c.nome ASC");
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<StatusLocal> buscarStatusLocal() {
		Query consulta = em.createQuery("select s from StatusLocal s order by nome ASC");
		return consulta.getResultList();
	}

}