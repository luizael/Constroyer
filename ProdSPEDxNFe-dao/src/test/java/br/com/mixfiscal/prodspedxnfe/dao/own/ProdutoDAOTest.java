package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.Empresa;
import br.com.mixfiscal.prodspedxnfe.domain.own.Produto;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProdutoDAOTest {
    private ProdutoDAO prodDao;
    
    public ProdutoDAOTest() {
        prodDao = new ProdutoDAO();
    }    
      
    @Test
    public void testSalvar() throws Exception {
        try {
            //ConstroyerHibernateUtil.beginTransaction();
            
            Empresa emp = new Empresa();
            emp.setCnpj("1231321");
            EmpresaDAO empDao = new EmpresaDAO();
            emp = empDao.selecionarUm(emp);
            
            Produto prod = new Produto();
            prod.setEmpresa(emp);
            prod.setReferenciaProduto("123456");
            prod.setDescricao("Produto Teste");
            prod.setUnidadeMedida("CX");
            prod.setEan("123456");
            prod.setNcm("123456");
            prod.setAliqICMS(new BigDecimal(1.45));
                        
            prodDao.salvar(prod);
            
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
            List<Produto> lista = prodDao.listar();            
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
            
            Produto prod = new Produto();
            prod.setReferenciaProduto("123456");
            
            prod = prodDao.selecionarUm(prod);
            prodDao.excluir(prod);
            
            //ConstroyerHibernateUtil.commitCurrentTransaction();
            
            assertTrue(true);
        } catch(Exception ex) {
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
    @Test
    public void retornarProdutoPorCNPJECodigoItemTest() throws Exception {
        try {
            //ConstroyerHibernateUtil.beginTransaction();
            
            Produto prod = prodDao.retornarProdutoPorCNPJECodigoItem("07.970.113/0001-69", "9698");
                        
            //ConstroyerHibernateUtil.commitCurrentTransaction();
            
            assertTrue(prod != null && prod.getId() != null);
        } catch(Exception ex) {
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
}
