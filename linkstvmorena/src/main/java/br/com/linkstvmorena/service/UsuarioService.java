package br.com.linkstvmorena.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import br.com.linkstvmorena.model.UsuarioLogado;
import br.com.linkstvmorena.service.exception.ServiceException;

@Service
public class UsuarioService {
	/*@Autowired
	@Qualifier("categoriaDAO")
	private CategoriaDAO categoriaDAO;*/
	
	@PersistenceContext
	private EntityManager em;

	public void salvar(UsuarioLogado usuario) throws Exception {

		try {
			if (usuario.getId() == null) {
				UsuarioLogado usuarioBusca = buscarPorNome(usuario);
				if (usuarioBusca != null) {
					System.out.println(usuarioBusca.getId());
					throw new ServiceException("Usuario ja  Cadastrado!", null);
				}
			}
			em.merge(usuario);

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public UsuarioLogado buscarPorNome(UsuarioLogado usuario) throws Exception{
		
		
		try {
			String jpql = "Select c from UsuarioLogado u where lower(u.username)=lower(:userParam)";
			
			Query consulta = em.createQuery(jpql);

			consulta.setParameter("userParam", usuario.getUsername());
			consulta.setMaxResults(1);
			
			return (UsuarioLogado) consulta.getSingleResult();
			
		} catch (NoResultException e) {
			// engolir a exception
			return null;
		} catch (Exception causa) {
			throw new Exception ("Erro ao Buscar Usuario!! " + causa);
			
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioLogado> buscarTodos() {
		
		Query consulta = em.createQuery("select u from UsuarioLogado u order by nome ASC");
		return consulta.getResultList();
		
	}

	public void excluir(UsuarioLogado usuario) throws ServiceException {

		try {
			em.remove(usuario);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public UsuarioLogado buscarPorId(Integer id) throws ServiceException {
		try {
			return em.find(UsuarioLogado.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}