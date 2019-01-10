package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.util.ProdSPEDXNFeHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.NFeProcessadaItem;
import java.util.List;
import org.hibernate.Criteria;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class NFeProcessadaItemDAOTest {
    
    public NFeProcessadaItemDAOTest() {
    } 
    
    @Test
    public void testAddFiltros() throws Exception {
        try {
            ProdSPEDXNFeHibernateUtil.beginTransaction();
            
            NFeProcessadaItemDAO nfeProcItemDao = new NFeProcessadaItemDAO();
            List<NFeProcessadaItem> lista = nfeProcItemDao.listar();
            
            ProdSPEDXNFeHibernateUtil.commitCurrentTransaction();
            assertTrue(lista.size() > 0);
        } catch(Exception ex) {            
            ProdSPEDXNFeHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
}
