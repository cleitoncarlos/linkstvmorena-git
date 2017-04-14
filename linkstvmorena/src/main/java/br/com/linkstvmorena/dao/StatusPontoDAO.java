package br.com.linkstvmorena.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.linkstvmorena.dao.exception.DAOException;
import br.com.linkstvmorena.model.StatusPonto;

@Repository("statusDAO")
public class StatusPontoDAO {
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<StatusPonto> buscarTodos() {
		Query consulta = em.createQuery("select s from StatusPonto s order by nome ASC");
		return consulta.getResultList();
	}

	@Transactional
	public void salvar(StatusPonto statusponto) throws DAOException {
		try {
			em.merge(statusponto);
		} catch (Exception e) {
			throw new DAOException("Erro: ", e);
		}
	}

	public StatusPonto buscaPorNome(String nome) throws Exception {
		try {
			String jpqlnome = "Select s from StatusPonto s Where lower(s.nome)=lower(:pNome)";
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

	public StatusPonto bucaPorId(Integer id) {
		return em.find(StatusPonto.class, id);
	}
	
	@Transactional
	public void excluir(StatusPonto statusponto) throws DAOException{
		try {
			em.remove(bucaPorId(statusponto.getId()));
		} catch (Exception e) {
			throw new DAOException("Nao foi possivel Excluir!", e.getCause());
		}
	}

}
