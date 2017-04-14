package br.com.linkstvmorena.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.linkstvmorena.dao.exception.DAOException;
import br.com.linkstvmorena.model.Contato;

@Repository("contatoDAO")
public class ContatoDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void salvar(Contato contato) throws DAOException {
		try {System.out.println("ContatoDAO: "+contato);
			em.merge(contato);
		} catch (Exception e) {
			throw new DAOException("Erro ao Cadastrar Contato! ", e);
		}
	}

	public Contato buscaNumeroContato(String celular) throws Exception {
		try {
			String jpql = "Select c from Contato c where lower(c.celular)=lower(:contParam)";

			Query consulta = em.createQuery(jpql);

			consulta.setParameter("contParam", celular);
			consulta.setMaxResults(1);
			return (Contato) consulta.getSingleResult();
		} catch (NoResultException e) {
			// engolir a exception
			return null;
		} catch (Exception causa) {
			throw new Exception("Erro ao Buscar Numero do Contato!! " + causa);

		}
	}

}
