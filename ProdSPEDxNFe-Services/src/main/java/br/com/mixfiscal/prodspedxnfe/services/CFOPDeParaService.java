package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.dao.own.CFOPDeParaDAO;
//import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOP;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOPDePara;
import br.com.mixfiscal.prodspedxnfe.services.ex.ServiceException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CFOPDeParaService {
    private CFOPDeParaDAO cfopDeParaDAO;    
    private Logger log;   
    public CFOPDeParaService() {
    	log = LogManager.getLogger(CFOPDeParaService.class);
        cfopDeParaDAO = new CFOPDeParaDAO();
    }
    
    public Long getQtdTotalRegistros() {
        return cfopDeParaDAO.getQtdTotalRegistros();
    }
    
    public void salvar(CFOPDePara obj) throws ServiceException {
        try {
                cfopDeParaDAO.salvar(obj);
        } catch(Exception ex) {
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    public void atualizar(CFOPDePara obj) throws ServiceException {
        try {
            cfopDeParaDAO.atualizar(obj);
        } catch(Exception ex) {
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    public void excluir(CFOPDePara obj) throws ServiceException {
        try {
            cfopDeParaDAO.excluir(obj);
        } catch(Exception ex) {
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
    }
    
    public CFOPDePara selecionarUm(CFOPDePara obj) throws ServiceException {
        CFOPDePara cfopDePara = null;
        try {
            cfopDePara = cfopDeParaDAO.selecionarUm(obj);
        } catch(Exception ex) {
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
        return cfopDePara;
    }

    public List<CFOPDePara> listar() throws ServiceException {
        List<CFOPDePara> lista = null;
        try {
            lista = cfopDeParaDAO.listar();
        } catch(Exception ex) {
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
        return lista;
    }
    
    public List<CFOPDePara> listar(CFOPDePara obj) throws ServiceException {
        List<CFOPDePara> lista = null;
        try {
            lista = cfopDeParaDAO.listar(obj);
        } catch(Exception ex) {
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
        return lista;
    }
    
    public List<CFOPDePara> listar(CFOPDePara obj, int indicePrimeiroRegistro, int qtdRegistros) throws ServiceException {
        List<CFOPDePara> lista = null;
        try {
            lista = cfopDeParaDAO.listar(obj, indicePrimeiroRegistro, qtdRegistros);
        } catch(Exception ex) {
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
        return lista;
    }
    
    public List<CFOPDePara> listar(CFOPDePara obj, String orderProp, String orderDir) throws ServiceException {
        List<CFOPDePara> lista = null;
        try {
            lista = cfopDeParaDAO.listar(obj, orderProp, orderDir);
        } catch(Exception ex) {
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
        return lista;
    }
    
    public List<CFOPDePara> listar(CFOPDePara obj, int indicePrimeiroRegistro, int qtdRegistros, String orderProp, String orderDir) throws ServiceException {
        List<CFOPDePara> lista = null;
        try {
            lista = cfopDeParaDAO.listar(obj, indicePrimeiroRegistro, qtdRegistros, orderProp, orderDir);
        } catch(Exception ex) {
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
        return lista;
    }
    
    public List<CFOP> listarCFOPAutoComplete(String codigo) throws ServiceException {
        CFOPService cfopServ = new CFOPService();
        CFOP filtro = new CFOP();
        filtro.setCodigo(codigo);
        return cfopServ.listar(filtro);
    }
}
