package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.Fornecedor;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class FornecedorDAOTest {
    private FornecedorDAO fornecDao;    
    
    public FornecedorDAOTest() {
        fornecDao = new FornecedorDAO();
    } 
    
    @Test
    public void testSalvar() throws Exception {
        try {
            //ConstroyerHibernateUtil.beginTransaction();
            
            Fornecedor fornec = new Fornecedor();
            fornec.setNome("Fornecedor Teste");
            fornec.setCnpj("123456");
                        
            fornecDao.salvar(fornec);
            
            //ConstroyerHibernateUtil.commitCurrentTransaction();
            
            assertTrue(true);
        } catch(Exception ex) {
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
    @Test
    public void testListar() throws Exception {
        try {
            //ConstroyerHibernateUtil.beginTransaction();            
            List<Fornecedor> lista = fornecDao.listar();            
            //ConstroyerHibernateUtil.commitCurrentTransaction();            
            assertTrue(lista.size() > 0);
        } catch(Exception ex) {
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
    @Test
    public void testExcluir() throws Exception {
        try {
            //ConstroyerHibernateUtil.beginTransaction();
            
            Fornecedor filtro = new Fornecedor();
            filtro.setCnpj("123456");
            
            Fornecedor fornec = fornecDao.selecionarUm(filtro);
            fornecDao.excluir(fornec);
            
            //ConstroyerHibernateUtil.commitCurrentTransaction();
            
            assertTrue(true);
        } catch(Exception ex) {
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
}
