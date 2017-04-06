package br.com.linkstvmorena.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.linkstvmorena.dao.StatusPontoDAO;
import br.com.linkstvmorena.dao.exception.DAOException;
import br.com.linkstvmorena.model.StatusPonto;
import br.com.linkstvmorena.service.exception.ServiceException;

@Service
public class StatusPontoService {

	@Autowired
	@Qualifier("statusDAO")
	private StatusPontoDAO statusDAO;

	public List<StatusPonto> buscarTodos() {
		return statusDAO.buscarTodos();
	}

	public void salvar(StatusPonto statusponto) throws Exception {
		try {
			if (statusponto.getId() == null) {
				StatusPonto statusBusca = statusDAO.buscaPorNome(statusponto.getNome());
				if (statusBusca != null) {
					System.out.println(statusBusca.getId());
					throw new ServiceException("Categoria ja  Cadastrada!", null);
				}
			}
			statusDAO.salvar(statusponto);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public StatusPonto buscarPorId(StatusPonto statusponto) throws ServiceException {
		try {
			return statusDAO.bucaPorId(statusponto.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void excluir(StatusPonto statusponto) throws ServiceException {
		
		try {
			statusDAO.excluir(statusponto);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
