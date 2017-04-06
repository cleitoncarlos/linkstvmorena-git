package br.com.linkstvmorena.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.linkstvmorena.dao.PontoDAO;
import br.com.linkstvmorena.dao.exception.DAOException;
import br.com.linkstvmorena.model.Ponto;
import br.com.linkstvmorena.service.exception.ServiceException;

@Service
public class PontoService {
	@Autowired
	@Qualifier("pontoDAO")
	private PontoDAO pontoDAO;
	
	public void salvar(Ponto ponto) throws Exception {

		try {System.out.println("Service - PontoContoller: "+ponto);
			if (ponto.getId() == null) {
				Ponto pontoBusca = buscarPorNome(ponto);
				if (pontoBusca != null) {
					System.out.println(pontoBusca.getId());
					throw new ServiceException("Ponto de Link ja  Cadastrado!", null);
				}
			}
			pontoDAO.salvar(ponto);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private Ponto buscarPorNome(Ponto ponto) {
		try {
			return pontoDAO.buscaPorNome(ponto.getDescricao());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
