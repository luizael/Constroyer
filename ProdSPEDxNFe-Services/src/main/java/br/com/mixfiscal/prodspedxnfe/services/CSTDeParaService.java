package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.domain.own.CSTDePara;
import br.com.mixfiscal.prodspedxnfe.dao.own.CSTDeParaDAO;
import br.com.mixfiscal.prodspedxnfe.services.ex.ServiceException;
//import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.CST;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CSTDeParaService {
	private Logger log;
	private CSTDeParaDAO cstDeParaDAO;

	public CSTDeParaService() {
		log = LogManager.getLogger(CSTDeParaService.class);
		cstDeParaDAO = new CSTDeParaDAO();
	}

	public Long getQtdTotalRegistros() {
		return cstDeParaDAO.getQtdTotalRegistros();
	}

	public void salvar(CSTDePara obj)throws ServiceException {
        try {
            if (obj != null) 
                cstDeParaDAO.salvar(obj);            
        }catch(Exception ex){
            log.error("Erro ao salvar CSTDepara", ex);
        	throw new ServiceException(ex.getMessage(),ex);
        }
    }

	public void atualizar(CSTDePara obj) throws ServiceException {
		try {
			cstDeParaDAO.atualizar(obj);
		} catch (Exception ex) {
			log.error("Erro ao atualizar CSTDepara", ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	public void excluir(CSTDePara obj) throws ServiceException {
		try {
			cstDeParaDAO.excluir(obj);
		} catch (Exception ex) {
			log.error("Erro ao excluir CSTDePara", ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	public CSTDePara selecionarUm(CSTDePara obj) throws ServiceException {
		try {
			obj = cstDeParaDAO.selecionarUm(obj);
		} catch (Exception ex) {
			log.error("Erro ao selecionar um CSTDepara", ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
		return obj;
	}

	public List<CSTDePara> listar() throws ServiceException {
		List<CSTDePara> lista = null;
		try {
			lista = cstDeParaDAO.listar();
		} catch (Exception ex) {
			log.error("Erro ao listar CSTDepara", ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
		return lista;
	}

	public List<CSTDePara> listar(CSTDePara obj) throws ServiceException {
		List<CSTDePara> lista = null;
		try {
			lista = cstDeParaDAO.listar(obj);
		} catch (Exception ex) {
			log.error("Erro ao listar CSTDePara", ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
		return lista;
	}

	public List<CSTDePara> listar(CSTDePara obj, int indicePrimeiroRegistro, int qtdRegistros) throws ServiceException {
		List<CSTDePara> lista = null;
		try {
			lista = cstDeParaDAO.listar(obj, indicePrimeiroRegistro, qtdRegistros);
		} catch (Exception ex) {
			log.error("Erro ao listar CSTDepara", ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
		return lista;
	}

	public List<CSTDePara> listar(CSTDePara obj, String orderProp, String orderDir) throws ServiceException {
		List<CSTDePara> lista = null;
		try {
			lista = cstDeParaDAO.listar(obj, orderProp, orderDir);
		} catch (Exception ex) {
			log.error("Erro ao listar CSTDePara", ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
		return lista;
	}

	public List<CSTDePara> listar(CSTDePara obj, int indicePrimeiroRegistro, int qtdRegistros, String orderProp,
			String orderDir) throws ServiceException {
		List<CSTDePara> lista = null;
		try {
			lista = cstDeParaDAO.listar(obj, indicePrimeiroRegistro, qtdRegistros, orderProp, orderDir);
		} catch (Exception ex) {
			log.error("Erro ao listar CSTDePara", ex);
			throw new ServiceException(ex.getMessage(), ex);
		}
		return lista;
	}

	public List<CST> listarCSTAutoComplete(String codigo) throws ServiceException {
		CSTService cstServ = new CSTService();
		CST filtro = new CST();
		filtro.setCodigo(codigo);
		return cstServ.listar(filtro);
	}
}
