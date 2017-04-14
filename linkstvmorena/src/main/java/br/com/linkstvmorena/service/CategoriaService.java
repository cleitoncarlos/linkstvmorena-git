package br.com.linkstvmorena.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.linkstvmorena.dao.CategoriaDAO;
import br.com.linkstvmorena.dao.exception.DAOException;
import br.com.linkstvmorena.model.Categoria;
import br.com.linkstvmorena.service.exception.ServiceException;

@Service
public class CategoriaService {
	@Autowired
	@Qualifier("categoriaDAO")
	private CategoriaDAO categoriaDAO;

	public void salvar(Categoria categoria) throws Exception {

		try {
			if (categoria.getId() == null) {
				Categoria categoriaBusca = buscarPorNome(categoria);
				if (categoriaBusca != null) {
					System.out.println(categoriaBusca.getId());
					throw new ServiceException("Categoria ja  Cadastrada!", null);
				}
			}
			categoriaDAO.salvar(categoria);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public Categoria buscarPorNome(Categoria categoria) {
		try {
			return categoriaDAO.buscaPorNome(categoria.getNome());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Categoria> buscarTodos() {
		return categoriaDAO.buscarTodos();
	}

	public void excluir(Categoria categoria) throws ServiceException {

		try {
			categoriaDAO.excluir(categoria);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public Categoria buscarPorId(Categoria categoria) throws ServiceException {
		try {
			return categoriaDAO.buscarPorId(categoria.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}