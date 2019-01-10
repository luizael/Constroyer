package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.dao.ex.DAOException;
import br.com.mixfiscal.prodspedxnfe.dao.own.RelacaoProdutoFornecedorDAO;
//import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.RelacaoProdutoFornecedor;
import br.com.mixfiscal.prodspedxnfe.services.ex.ServiceException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RelacaoService implements Serializable {
    
    private final RelacaoProdutoFornecedorDAO relacaoDao;
    private Logger log;
    public RelacaoService(){
    	log = LogManager.getLogger(RelacaoService.class);
      relacaoDao = new RelacaoProdutoFornecedorDAO();
    }
     
    public List<RelacaoProdutoFornecedor> listar(RelacaoProdutoFornecedor obj, int indicePrimeiroRegistro, int qtdRegistros, String orderProp, String orderDir) throws ServiceException{
        List<RelacaoProdutoFornecedor> lista = new ArrayList<>();
            
        try{
            lista = relacaoDao.listarTudo();            
			// lista = relacaoDao.listar();
        } catch (Exception e){
        	log.error(e.getMessage(), e);
            throw new ServiceException();
        }
            
        return lista;
    }    
    
    public Long getQtdTotalRegistros(){
        return relacaoDao.getQtdTotalRegistros();
    }    
}