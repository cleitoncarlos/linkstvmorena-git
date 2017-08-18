package br.com.linkstvmorena.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.linkstvmorena.model.UsuarioLogado;
import br.com.linkstvmorena.service.exception.ServiceException;

@Service
public class UsuarioService {
	/*
	 * @Autowired
	 * 
	 * @Qualifier("categoriaDAO") private CategoriaDAO categoriaDAO;
	 */

	@PersistenceContext
	private EntityManager em;

	@Transactional
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

	public UsuarioLogado buscarPorNome(UsuarioLogado usuario) throws Exception {

		try {
			String jpql = "Select u from UsuarioLogado u where lower(u.username)=lower(:userParam)";

			Query consulta = em.createQuery(jpql);

			consulta.setParameter("userParam", usuario.getUsername());
			consulta.setMaxResults(1);

			return (UsuarioLogado) consulta.getSingleResult();

		} catch (NoResultException e) {
			// engolir a exception
			return null;
		} catch (Exception causa) {
			throw new Exception("Erro ao Buscar Usuario!! " + causa);

		}

	}

	public UsuarioLogado verificaLogin(UsuarioLogado usuario) throws Exception {

		try {
			String jpql = "Select u from UsuarioLogado u where u.username = :userParam $$ u.senha = :senhaParam";

			Query consulta = em.createQuery(jpql);

			String senhaMD5 = convertPasswordToMD5(usuario.getSenha());

			consulta.setParameter("userParam", usuario.getUsername());
			consulta.setParameter("senhaParam", senhaMD5);
			consulta.setMaxResults(1);

			return (UsuarioLogado) consulta.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Exception causa) {
			throw new Exception("Erro ao Buscar Usuario!! " + causa);

		}

	}

	public static String convertPasswordToMD5(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");

		BigInteger hash = new BigInteger(1, md.digest(password.getBytes()));

		return String.format("%32x", hash);
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioLogado> buscarTodos() {

		Query consulta = em.createQuery("select u from UsuarioLogado u order by u.nome ASC");
		return consulta.getResultList();

	}

	@Transactional
	public void excluir(UsuarioLogado usuario) throws ServiceException {

		try {
			em.remove(buscarPorId(usuario.getId()));
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