package br.com.mixfiscal.prodspedxnfe.services;

import java.io.Serializable;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOP;
import br.com.mixfiscal.prodspedxnfe.dao.own.CFOPDAO;
//import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.services.ex.ServiceException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CFOPService implements Serializable {
    private CFOPDAO cfopDao;
    private Logger log;
    public CFOPService(){
    	log = LogManager.getLogger(CFOPDAO.class);
      cfopDao = new   CFOPDAO();
    }
     
    //<editor-fold defaultstate="collapsed" desc="Metodos Basico">
    public void salvar(CFOP obj) throws ServiceException {
        try{
            if(obj != null) 
                cfopDao.salvar(obj);
         
        }catch(Exception ex){
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
    }
    
    public void atualizar(CFOP obj) throws ServiceException{
        try{
            if(obj != null) 
                cfopDao.atualizar(obj);
           
        }catch(Exception ex){
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
    }
    
    public boolean excluir(CFOP obj) throws ServiceException{ 
        CFOP cfopEquiv = new CFOP();
        boolean sucesso = true;
        try{
            if(obj != null){
                cfopEquiv = cfopDao.selecionarUm(obj);
                if(cfopEquiv.getCFOPEquivalente() == null){
                    cfopDao.excluir(obj);
                }else{
                    sucesso = false;
                }
            }
        }catch(Exception ex){
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
        return sucesso;
    }
    
    public CFOP selecionarUm(CFOP obj) throws ServiceException {    
        CFOP cfop = new  CFOP();
        try{
            if(obj != null)
                cfop = cfopDao.selecionarUm(obj);
      
        }catch(Exception ex){
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
        return cfop;
    }
    
    public List<CFOP> listar() throws ServiceException{   
        List<CFOP> lista = new ArrayList<>();
        try{
                lista = cfopDao.listar();                
        }catch(Exception ex){
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
        return  lista;
    }
    
    public List<CFOP> listar(CFOP obj) throws ServiceException{   
        List<CFOP> lista = new ArrayList<>();
        try {
            lista = cfopDao.listar(obj);
        }catch(Exception ex) {
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
        return  lista;
    }
    
    public List<CFOP> listar(CFOP obj,int indicePrimeiroRegistro, int qtdRegistros)throws ServiceException{
        List<CFOP> lista = new ArrayList<>();
        try{
            lista = cfopDao.listar(obj,indicePrimeiroRegistro,qtdRegistros);
        }catch(Exception ex){
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
        return  lista;
    }
    
    public List<CFOP> listar(CFOP obj, int indicePrimeiroRegistro, int qtdRegistros, String orderProp, String orderDir) throws ServiceException{
        List<CFOP> lista = new ArrayList<>();
        try{
                lista = cfopDao.listar(obj, indicePrimeiroRegistro, qtdRegistros, orderProp, orderDir);
        }catch(Exception ex) {
        	log.error(ex.getMessage());
            throw new ServiceException(ex.getMessage(), ex);
        }
        return  lista;
    }

    public Long getQtdTotalRegistros(){
        return cfopDao.getQtdTotalRegistros();
    }
    //</editor-fold>   
}