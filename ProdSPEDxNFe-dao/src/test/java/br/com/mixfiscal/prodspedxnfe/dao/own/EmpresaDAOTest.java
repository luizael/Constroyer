package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.Empresa;
import br.com.mixfiscal.prodspedxnfe.domain.own.Produto;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmpresaDAOTest {
    private EmpresaDAO empresaDao;
    
    public EmpresaDAOTest() {
        empresaDao = new EmpresaDAO();
    } 
   
    @Test
    public void testSelecionarUm() throws Exception {
        try {
            //ConstroyerHibernateUtil.beginTransaction();
            
            Empresa filtro = new Empresa();
            filtro.setId(5);
            
            Empresa empresa = empresaDao.selecionarUm(filtro);            
            Set<Produto> produtos = empresa.getProdutos();
            
            assertTrue(empresa != null && produtos != null);
            
            //ConstroyerHibernateUtil.commitCurrentTransaction();
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
            List<Empresa> lista = empresaDao.listar();            
            //ConstroyerHibernateUtil.commitCurrentTransaction();
            
            assertTrue(lista.size() > 0);
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
            
            Empresa empresa = new Empresa();
            empresa.setNome("Empresa Teste");
            empresa.setCnpj("1231321");
            empresa.setInscricaoEstadual("123123");
            empresa.setInscricaoMunicipal("343434");
            empresa.setUf("SP");            
            
            empresaDao.salvar(empresa);
            
            //ConstroyerHibernateUtil.commitCurrentTransaction();
            
            assertTrue(true);
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
                        
            Empresa filtro = new Empresa();
            filtro.setCnpj("1231321");
            Empresa empresa = empresaDao.selecionarUm(filtro);
                        
            empresaDao.excluir(empresa);
            
            //ConstroyerHibernateUtil.commitCurrentTransaction();
            
            assertTrue(true);
        } catch(Exception ex) {
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
}
