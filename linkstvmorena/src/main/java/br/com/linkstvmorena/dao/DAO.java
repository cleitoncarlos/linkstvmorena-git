package br.com.linkstvmorena.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.transaction.annotation.Transactional;

import br.com.linkstvmorena.dao.exception.DAOException;
import br.com.linkstvmorena.model.Categoria;


public class DAO<T> {

	private final Class<T> classe;
	@PersistenceContext
	private EntityManager em;

	public DAO(Class<T> classe) {
		this.classe = classe;
	}

	@Transactional
	public void salvar(T t) throws DAOException {
		try {
			em.merge(t);
		} catch (Exception e) {
			throw new DAOException("Erro ao Cadastrar Local! ", e);
		}
	}

	@Transactional
	public void excluir(T t) throws DAOException {
		try {
			em.remove(em.find(classe, t));
		} catch (Exception e) {
			throw new DAOException("Nao Foi Possivel Excluir!", e.getCause());
		}
	}

	public List<T> buscaTodos() {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();
		return lista;
	}

	public T buscaPorId(Integer id) {
		return  em.find(classe, id);
	}

	public int contaTodos() {
		EntityManager em = new JPAUtil().getEntityManager();
		long result = (Long) em.createQuery("select count(n) from livro n").getSingleResult();
		em.close();

		return (int) result;
	}
	/*Busca categoria por nome*/
	public Categoria buscaCategoriaPorNome(String nome) throws Exception  {

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
	
	
}






/*
 * public List<T> listaTodosPaginada(int firstResult, int maxResults) {
 * EntityManager em = new JPAUtil().getEntityManager(); CriteriaQuery<T> query =
 * em.getCriteriaBuilder().createQuery(classe);
 * query.select(query.from(classe));
 * 
 * List<T> lista =
 * em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults).
 * getResultList();
 * 
 * em.close(); return lista; }
 */

/*
 * public List<T> buscaString(String st){
 * 
 * EntityManager manager = new JPAUtil().getEntityManager();
 * 
 * Query query =
 * manager.createQuery("SELECT l  FROM Livro l where  l.titulo like :plivro" );
 * 
 * query.setParameter("plivro","%"+st+"%"); //query.setParameter("pautor","a");
 * 
 * List<T> livros = query.getResultList(); if(livros.isEmpty()){ return null;
 * }else{ return livros;
 * 
 * } }
 */

/*
 * public List<Despesa> buscaPorMes(String mes) throws ParseException { // mes =
 * ""; if (mes == null) { return null; } else { Calendar c =
 * Calendar.getInstance(); SimpleDateFormat sdf = new SimpleDateFormat("MM");
 * EntityManager manager = new JPAUtil().getEntityManager(); SimpleDateFormat
 * sdf = new SimpleDateFormat("MM");
 * 
 * if (mes == null){ }
 * 
 * Date mesdata = sdf.parse(mes); c.setTime(mesdata); String s =
 * sdf.format(c.getTime()); // Calendar c = Calendar.getInstance(); //
 * SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyy");
 * 
 * if(mes==8){ mes = this.mes.get(Calendar.SEPTEMBER); }
 * 
 * // c.setTime(data.parse(mes)); // String r = data.format(c.getTime());
 * 
 * Query query = manager .createQuery("SELECT d from Despesa d");
 * 
 * Query query = manager
 * .createQuery("SELECT d from Despesa d where Month(d.data) = :pdata");
 * query.setParameter("pdata", mesdata.getMonth() + 1); //
 * query.setParameter("pautor","a"); List<Despesa> despesas =
 * query.getResultList(); for (Despesa despesa : despesas) {
 * System.out.println("ok." + despesa.getDescricao() + "\n" +
 * sdf.format(despesa.getData().getTime()) + "\n"); }
 * 
 * 
 * if (query.getResultList() == null) { System.out.println("Query Vazia!"); }
 * 
 * 
 * for (Despesa despesa : despesas) { System.out.println("entrou..." +
 * query.getResultList()); }
 * 
 * if (despesas.isEmpty()) { System.out.println("Query Vazia!" +
 * query.getParameterValue("pdata")); return null; } else { System.out
 * .println("entrou." + query.getParameterValue("pdata")); return despesas; }
 * 
 * } }
 */
