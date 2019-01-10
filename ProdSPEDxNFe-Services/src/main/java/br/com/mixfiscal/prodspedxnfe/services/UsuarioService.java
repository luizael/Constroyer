package br.com.mixfiscal.prodspedxnfe.services;

import java.io.Serializable;
import br.com.mixfiscal.prodspedxnfe.domain.own.Usuario;
import br.com.mixfiscal.prodspedxnfe.dao.own.UsuarioDAO;
import br.com.mixfiscal.prodspedxnfe.services.ex.ServiceException;
//import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UsuarioService {
	private UsuarioDAO usuarioDao;
	private Logger log;

	public UsuarioService() {
		log = LogManager.getLogger(UsuarioService.class);
		usuarioDao = new UsuarioDAO();
	}

	public void salvar(Usuario obj) throws ServiceException {
		try {
			if (obj != null)
				usuarioDao.salvar(obj);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	public void excluir(Usuario obj) throws ServiceException {
		try {
			usuarioDao.excluir(obj);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	public void atualizar(Usuario obj) throws ServiceException {
		try {
			if (obj != null)
				usuarioDao.atualizar(obj);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	public Usuario selecionarUm(Usuario obj) throws ServiceException {
		try {
			if (obj != null)
				obj = usuarioDao.selecionarUm(obj);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
		return obj;
	}

	public List<Usuario> listar(Usuario obj, int indicePrimeiroRegistro, int qtdRegistros, String orderProp,
			String orderDir) throws ServiceException {
		List<Usuario> lista = new ArrayList<Usuario>();
		try {
			if (obj != null)
				lista = usuarioDao.listar(obj);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
		return lista;
	}

}
