package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.domain.own.CST;
import br.com.mixfiscal.prodspedxnfe.dao.own.CSTDAO;
//import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.services.ex.ServiceException;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CSTService {
	private CSTDAO cstDao;
	private Logger log;

	public CSTService() {
		log = LogManager.getLogger(CSTService.class);
		cstDao = new CSTDAO();
	}

	// <editor-fold defaultstate="collapsed" desc="Metodos Basico">
	public void salvar(CST obj) throws ServiceException {
		try {
			if (obj != null)
				cstDao.salvar(obj);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	public void atualizar(CST obj) throws ServiceException {
		try {
			if (obj != null)
				cstDao.atualizar(obj);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	public boolean excluir(CST obj) throws ServiceException {
		boolean sucesso = false;
		try {
			cstDao.excluir(obj);
			sucesso = true;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
		return sucesso;
	}

	public CST selecionarUm(CST obj) throws ServiceException {
		try {
			if (obj != null)
				obj = cstDao.selecionarUm(obj);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
		return obj;
	}

	public List<CST> listar() throws ServiceException {
		List<CST> lista = new ArrayList<>();
		try {
			lista = cstDao.listar();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
		return lista;
	}

	public List<CST> listar(CST obj) throws ServiceException {
		List<CST> lista = new ArrayList<>();
		try {
			lista = cstDao.listar(obj);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
		return lista;
	}

	public List<CST> listar(CST obj, int indicePrimeiroRegistro, int qtdRegistros) throws ServiceException {
		List<CST> lista = new ArrayList<>();
		try {
			lista = cstDao.listar(obj, indicePrimeiroRegistro, qtdRegistros);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
		return lista;
	}

	public List<CST> listar(CST obj, int indicePrimeiroRegistro, int qtdRegistros, String orderProp, String orderDir)
			throws ServiceException {
		List<CST> lista = new ArrayList<>();
		try {
			lista = cstDao.listar(obj, indicePrimeiroRegistro, qtdRegistros, orderProp, orderDir);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
		return lista;
	}

	public Long getQtdTotalRegistros() {
		return cstDao.getQtdTotalRegistros();
	}
	// </editor-fold>
}
