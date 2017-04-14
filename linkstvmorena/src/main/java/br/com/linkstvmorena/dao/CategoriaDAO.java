package br.com.linkstvmorena.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.linkstvmorena.dao.exception.DAOException;
import br.com.linkstvmorena.model.Categoria;

@Repository("categoriaDAO")
public class CategoriaDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void salvar(Categoria categoria) throws DAOException {
		try {
			em.merge(categoria);
		} catch (Exception e) {
			throw new DAOException("Erro: ", e);
		}	
	}

	@SuppressWarnings("unchecked")
	public List<Categoria> buscarTodos() {
		Query consulta = em.createQuery("select c from Categoria c order by nome ASC");
		return consulta.getResultList();
	}

	public Categoria buscaPorNome(String nome) throws Exception  {

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
			throw new Exception ("Erro ao Buscar nome!! " + causa);
			
		}
	}
	
	@Transactional
	public void excluir(Categoria categoria) throws DAOException {
		try {System.out.println("DAO: "+ categoria);
			em.remove(buscarPorId(categoria.getId()));
		} catch (Exception e) {
			throw new DAOException("Nao Foi Possivel Excluir!" , e.getCause());
		}
	}
	
	public Categoria buscarPorId(Integer id) {
		return em.find(Categoria.class, id);
	}

}
