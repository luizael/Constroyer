package br.com.mixfiscal.prodspedxnfe.dao.mixfiscalamazon;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.util.MixFiscalAmazonHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.mixfiscalamazon.CmfConstroyerProduto;
import java.util.List;
import org.hibernate.Criteria;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CmfConstroyerProdutoDAOTest {
    
    public CmfConstroyerProdutoDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void listarTest() {
        try {
            MixFiscalAmazonHibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            CmfConstroyerProdutoDAO cmfDao = new CmfConstroyerProdutoDAO();
            List<CmfConstroyerProduto> listaCmf = cmfDao.listar(null, 1, 10);
            for (CmfConstroyerProduto obj : listaCmf) {
                System.out.println(obj.getId() + " -- " + obj.getCodigoProduto());
            }
            MixFiscalAmazonHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch(Throwable ex) {
            MixFiscalAmazonHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
        } finally {
            
        }
    }
    
}
