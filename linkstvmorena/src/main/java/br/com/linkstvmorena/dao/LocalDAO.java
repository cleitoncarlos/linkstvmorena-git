package br.com.linkstvmorena.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.linkstvmorena.dao.exception.DAOException;
import br.com.linkstvmorena.model.Local;

@Repository("localDAO")
public class LocalDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void salvar(Local local) throws DAOException {
		try {
			em.merge(local);
		} catch (Exception e) {
			throw new DAOException("Erro ao Cadastrar Local! ", e);
		}
	}

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

}
