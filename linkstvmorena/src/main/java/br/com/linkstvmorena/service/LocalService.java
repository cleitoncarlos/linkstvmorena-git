package br.com.linkstvmorena.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.linkstvmorena.dao.LocalDAO;
import br.com.linkstvmorena.dao.exception.DAOException;
import br.com.linkstvmorena.model.Local;
import br.com.linkstvmorena.service.exception.ServiceException;

@Service
public class LocalService {
	@Autowired
	@Qualifier("localDAO")
	private LocalDAO localDAO;

	public void salvar(Local local) throws Exception {
		try {
			if (local.getId() == null) {
				Local localBusca = buscaLogradouro(local);
				if (localBusca != null) {
					System.out.println(localBusca.getId());
					throw new ServiceException("Local ja  Cadastrado!", null);
				}
			}
			localDAO.salvar(local);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public Local buscaLogradouro(Local local) {
		try {
			return localDAO.buscaLogradouro(local.getLogradouro());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
