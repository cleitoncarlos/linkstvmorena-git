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
import br.com.linkstvmorena.model.Local;
import br.com.linkstvmorena.model.StatusLocal;
import br.com.linkstvmorena.model.StatusPonto;
import br.com.linkstvmorena.service.exception.ServiceException;

@Service
public class Servico {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void salvar(Local local) throws Exception {
		if (local.getId() == null) {
			Local localBusca = buscaNomeLocal(local.getNome());
			if (localBusca != null) {
				throw new ServiceException("Local ja  Cadastrado!", null);
			} 
			
		}else {
				try {
					em.merge(local);
				} catch (Exception e) {
					throw new Exception("Erro ao cadastrar!: " + e);
				}
			}
		
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
	public List<Local> buscarLocal() {
		
		Query consulta = em
				.createQuery("select DISTINCT l from Local l "+
						" left Join fetch l.ponto "+
						" left Join fetch l.contato "+
						" Join fetch l.categoria "+
						"order by l.nome ASC");
		return consulta.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Local> buscarPorId(Integer id)  throws ServiceException{
		String busca ="select DISTINCT l from Local l "+
						" left Join fetch l.ponto "+
						" left Join fetch l.contato "+
						" Join fetch l.categoria "+
						" Where l.id=:idParam "+
						"order by l.nome ASC";
		
		Query consulta = em.createQuery(busca);
		consulta.setParameter("idParam", id);
		return consulta.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<StatusPonto> buscarStatusPonto() {
		Query consulta = em.createQuery("select s from StatusPonto s order by nome ASC");
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Categoria> buscarCategorias() {
		Query consulta = em.createQuery("select c from Categoria c left Join fetch c.locais order by c.nome ASC");
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<StatusLocal> buscarStatusLocal() {
		Query consulta = em.createQuery("select s from StatusLocal s order by nome ASC");
		return consulta.getResultList();
	}

	

}