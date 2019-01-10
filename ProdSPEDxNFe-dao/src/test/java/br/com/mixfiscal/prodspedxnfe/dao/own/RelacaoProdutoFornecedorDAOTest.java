package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.Fornecedor;
import br.com.mixfiscal.prodspedxnfe.domain.own.Produto;
import br.com.mixfiscal.prodspedxnfe.domain.own.RelacaoProdutoFornecedor;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class RelacaoProdutoFornecedorDAOTest {    
    private RelacaoProdutoFornecedorDAO relProdForDao;
    private Produto prod;
    private Fornecedor fornec;
    
    public RelacaoProdutoFornecedorDAOTest() throws Exception {
        relProdForDao = new RelacaoProdutoFornecedorDAO();
        try {
            //ConstroyerHibernateUtil.beginTransaction();
            
            ProdutoDAO prodDao = new ProdutoDAO();
            prod = new Produto();
            prod.setReferenciaProduto("1231321");        
            prod = prodDao.selecionarUm(prod);

            FornecedorDAO fornecDao = new FornecedorDAO();
            fornec = new Fornecedor();
            fornec.setCnpj("123456");        
            fornec = fornecDao.selecionarUm(fornec);
            
            //ConstroyerHibernateUtil.commitCurrentTransaction();
        } catch(Exception ex) {
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }     
   
    @Test
    public void testSalvar() throws Exception {
        try {
            //ConstroyerHibernateUtil.beginTransaction();
            
            RelacaoProdutoFornecedor rel = new RelacaoProdutoFornecedor();
            rel.setProduto(prod);
            rel.setFornecedor(fornec);
            rel.setReferenciaFornecedor("123456");
                        
            relProdForDao.salvar(rel);
            
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
            List<RelacaoProdutoFornecedor> lista = relProdForDao.listar();            
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
            
            RelacaoProdutoFornecedor rel = new RelacaoProdutoFornecedor();
            rel.setReferenciaFornecedor("123456");
            rel = relProdForDao.selecionarUm(rel);
            relProdForDao.excluir(rel);
            
            //ConstroyerHibernateUtil.commitCurrentTransaction();
            
            assertTrue(true);
        } catch(Exception ex) {
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
    @Test
    public void testSelecionarUm() throws Exception {
        try {
            //ConstroyerHibernateUtil.beginTransaction();
            
            Produto p = new Produto();
            p.setReferenciaProduto("45609");
            
            RelacaoProdutoFornecedor filtro = new RelacaoProdutoFornecedor();
            filtro.setProduto(p);
            filtro.setReferenciaFornecedor("50");
            
            RelacaoProdutoFornecedor relProdFor = relProdForDao.selecionarUm(filtro);
                        
            //ConstroyerHibernateUtil.commitCurrentTransaction();
            
            assertTrue(relProdFor != null);
        } catch(Exception ex) {
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
    @Test
    public void testVerificarExistencia() throws Exception {
        try {
            //ConstroyerHibernateUtil.beginTransaction();
            
           
                        
            //ConstroyerHibernateUtil.commitCurrentTransaction();
            
           // assertTrue(relProdFor != null);
        } catch(Exception ex) {
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
    @Test
    public void testVerificarOutrasAssociacoes() throws Exception {
        try {
            //ConstroyerHibernateUtil.beginTransaction();
            
            List<RelacaoProdutoFornecedor> relProdFor = relProdForDao.verificarOutrasAssociacoes(1, "22.343.834/0001-32", "45609", "50");
                        
            //ConstroyerHibernateUtil.commitCurrentTransaction();
            
            assertTrue(relProdFor != null);
        } catch(Exception ex) {
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
}
