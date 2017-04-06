package br.com.linkstvmorena.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.linkstvmorena.dao.StatusLocalDAO;
import br.com.linkstvmorena.dao.exception.DAOException;
import br.com.linkstvmorena.model.StatusLocal;
import br.com.linkstvmorena.model.StatusPonto;
import br.com.linkstvmorena.service.exception.ServiceException;

@Service
public class StatusLocalService {

	@Autowired
	@Qualifier("statusLocalDAO")
	private StatusLocalDAO statuslocalDAO;

	public List<StatusLocal> buscarTodos() {
		return statuslocalDAO.buscarTodos();
	}

	public void salvar(StatusLocal statuslocal) throws Exception {
		try {
			if (statuslocal.getId() == null) {
				StatusPonto statuslocalBusca = statuslocalDAO.buscaPorNome(statuslocal.getNome());
				if (statuslocalBusca != null) {
					System.out.println(statuslocalBusca.getId());
					throw new ServiceException("Categoria ja  Cadastrada!", null);
				}
			}
			statuslocalDAO.salvar(statuslocal);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public StatusLocal buscarPorId(StatusLocal statuslocal) throws ServiceException {
		try {
			return statuslocalDAO.bucaPorId(statuslocal.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void excluir(StatusLocal statuslocal) throws ServiceException {
		
		try {
			statuslocalDAO.excluir(statuslocal);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

}
