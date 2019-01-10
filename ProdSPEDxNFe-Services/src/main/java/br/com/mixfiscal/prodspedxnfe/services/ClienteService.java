package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.dao.own.ClienteDAO;
import br.com.mixfiscal.prodspedxnfe.domain.own.Cliente;
import br.com.mixfiscal.prodspedxnfe.services.ex.ServiceException;
import br.com.mixfiscal.prodspedxnfe.util.FileInfo;
import java.io.InputStream;
import br.com.mixfiscal.prodspedxnfe.util.FileUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClienteService {
    private Logger log;
    private ClienteDAO clienteDao;

    public ClienteService(){
    	log = LogManager.getLogger(ClienteService.class);
        clienteDao = new ClienteDAO();
    }
    
    public void salvar(Cliente obj)throws ServiceException{
       
        try{
            if(obj != null){
                clienteDao.salvar(obj);
            }
        }catch(Exception ex){
        	log.error("Erro ao salvar Cliente", ex);
            throw new ServiceException(ex.getMessage());
        }
    }
    
    public void excluir(Cliente obj)throws ServiceException{
        try{
            clienteDao.excluir(obj);
        }catch(Exception ex){
        	log.error("Erro ao excluir Cliente", ex);
        	throw new ServiceException(ex.getMessage());
        }
    }
    
    public void atualizar(Cliente obj)throws ServiceException{
        try{
            if(obj != null)
                clienteDao.atualizar(obj);
        }catch(Exception ex){
        	log.error("Erro ao atualizar Cliente", ex);
        	throw new ServiceException(ex.getMessage());
        }
    }
    
    public Cliente selecionarUm(Cliente obj)throws ServiceException{
        try{
            if(obj != null){
                obj = clienteDao.selecionarUm(obj);
            }
        }catch(Exception ex){
            log.error("Erro ao selecionar um Cliente", ex);
            throw new ServiceException(ex.getMessage());
        }
        return obj;
    }
    
    public  List<Cliente> listar(Cliente obj, int indicePrimeiroRegistro, int qtdRegistros, String orderProp, String orderDir)throws ServiceException{
        List<Cliente> lista = new ArrayList<Cliente>();
        try{
            lista = clienteDao.listar();
            //lista = clienteDao.listar(obj, qtdRegistros, qtdRegistros, orderProp, orderDir);
        }catch(Exception ex){
        	log.error("Erro ao listar Cliente", ex);
        	throw new ServiceException(ex.getMessage());
        }
        return lista;
    }
    
    public Long getQtdTotalRegistros(){
        return clienteDao.getQtdTotalRegistros();
    }    
    
    public Cliente selecionarPorCNPJ(String cnpj)throws ServiceException{
        try {            
            return clienteDao.selecionarPorCNPJ(cnpj);            
        }catch(Exception ex){
            log.error("Erro ao selecionar um Cliente", ex);
            throw new ServiceException(ex.getMessage());
        }        
    }
    
}
