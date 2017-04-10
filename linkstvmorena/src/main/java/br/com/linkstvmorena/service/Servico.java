package br.com.linkstvmorena.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.linkstvmorena.model.Categoria;
import br.com.linkstvmorena.model.Local;
import br.com.linkstvmorena.model.StatusLocal;
import br.com.linkstvmorena.model.StatusPonto;
import br.com.linkstvmorena.service.exception.ServiceException;

@Service
public class Servico {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void salvar(Local local) throws Exception {
		if (local.getId() == null) {
			Local localBusca = buscaLogradouro(local.getLogradouro());
			if (localBusca != null) {
				throw new ServiceException("Local ja  Cadastrado!", null);
			} else {
				try {
					em.merge(local);
				} catch (Exception e) {
					throw new Exception("Erro ao cadastrar!: "+e);
				}
			}
		}
	}
	@Transactional
	public Local buscaLogradouro(String logradouro) throws Exception {
		try {
			String jpql = "Select l from Local l where lower(l.logradouro)=lower(:logParam)";

			Query consulta = em.createQuery(jpql);

			consulta.setParameter("logParam", logradouro);
			consulta.setMaxResults(1);
			return (Local) consulta.getSingleResult();
		} catch (NoResultException e) {
			// engolir a exception
			return null;
		} catch (Exception causa) {
			throw new Exception("Erro ao Buscar Logradouro!! " + causa);

		}
	}
	@SuppressWarnings("unchecked")
	public List<StatusPonto> buscarStatusPonto() {
		Query consulta = em.createQuery("select s from StatusPonto s order by nome ASC");
		return consulta.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Categoria> buscarCategorias() {
		Query consulta = em.createQuery("select c from Categoria c order by nome ASC");
		return consulta.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<StatusLocal> buscarStatusLocal() {
		Query consulta = em.createQuery("select s from StatusLocal s order by nome ASC");
		return consulta.getResultList();
	}

}
