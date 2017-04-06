package br.com.linkstvmorena.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.linkstvmorena.dao.ContatoDAO;
import br.com.linkstvmorena.dao.exception.DAOException;
import br.com.linkstvmorena.model.Contato;
import br.com.linkstvmorena.service.exception.ServiceException;

@Service
public class ContatoService {

	@Autowired
	@Qualifier("contatoDAO")
	private ContatoDAO contatoDAO;

	public void salvar(Contato contato) throws Exception {
		try {System.out.println("ContatoService: "+contato);
			if (contato.getId() == null) {
				Contato contatoBusca = buscaNumero(contato);
				if (contatoBusca != null) {
					System.out.println(contatoBusca.getId());
					throw new ServiceException("Local ja  Cadastrado!", null);
				}
			}
			contatoDAO.salvar(contato);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public Contato buscaNumero(Contato contato) {
		try {
			return contatoDAO.buscaNumeroContato(contato.getCelular());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
