package br.com.linkstvmorena.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.linkstvmorena.dao.exception.DAOException;
import br.com.linkstvmorena.model.Ponto;

@Repository("pontoDAO")
public class PontoDAO {
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void salvar(Ponto ponto) throws DAOException {
		try {
			System.out.println("PontoDAO: "+ponto);
			em.merge(ponto);
		} catch (Exception e) {
			throw new DAOException("Erro ao Salvar Ponto de Link: ", e);
		}
	}

	public Ponto buscaPorNome(String descricao) throws Exception {

		try {
			String jpql = "Select p from Ponto p where lower(p.descricao)=lower(:descParam)";

			Query consulta = em.createQuery(jpql);

			consulta.setParameter("descParam", descricao);
			consulta.setMaxResults(1);
			return (Ponto) consulta.getSingleResult();
		} catch (NoResultException e) {
			// engolir a exception
			return null;
		} catch (Exception causa) {
			throw new Exception("Erro ao Buscar nome!! " + causa);

		}
	}

}
