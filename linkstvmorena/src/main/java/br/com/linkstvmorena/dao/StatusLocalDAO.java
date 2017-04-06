package br.com.linkstvmorena.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.stereotype.Repository;

import br.com.linkstvmorena.dao.exception.DAOException;
import br.com.linkstvmorena.model.StatusLocal;
import br.com.linkstvmorena.model.StatusPonto;

@Repository("statusLocalDAO")
public class StatusLocalDAO {
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<StatusLocal> buscarTodos() {
		Query consulta = em.createQuery("select s from StatusLocal s order by nome ASC");
		return consulta.getResultList();
	}

	@Transactional
	public void salvar(StatusLocal statuslocal) throws DAOException {
		try {
			em.merge(statuslocal);
		} catch (Exception e) {
			throw new DAOException("Erro: ", e);
		}
	}

	public StatusPonto buscaPorNome(String nome) throws Exception {
		try {
			String jpqlnome = "Select s from StatusLocal s Where lower(s.nome)=lower(:pNome)";
			Query consulta = em.createQuery(jpqlnome);
			consulta.setParameter("pNome", nome);
			consulta.setMaxResults(1);
			return (StatusPonto) consulta.getSingleResult();
		} catch (NoResultException e) {
			// engolir a exception
			return null;
		} catch (Exception causa) {
			throw new Exception("Erro ao Buscar nome!! " + causa);
		}
	}
	
	public StatusLocal bucaPorId(Integer id)throws DAOException {
		try {
			return em.find(StatusLocal.class, id);
		} catch (Exception e) {
			throw new DAOException("Nao foi possivel Buscar!", e.getCause());
			
		}
	}
	
	@Transactional
	public void excluir(StatusLocal statuslocal) throws DAOException{
		try {
			em.remove(bucaPorId(statuslocal.getId()));
		} catch (Exception e) {
			throw new DAOException("Nao foi possivel Excluir!", e.getCause());
		}
	}

}
