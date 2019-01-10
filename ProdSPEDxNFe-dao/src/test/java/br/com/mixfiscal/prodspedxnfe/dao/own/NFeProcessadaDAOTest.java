package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.util.ProdSPEDXNFeHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.NFeProcessada;
import br.com.mixfiscal.prodspedxnfe.domain.own.NFeProcessadaItem;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class NFeProcessadaDAOTest {
    
    public NFeProcessadaDAOTest() {
    }
    
    /**
     * Test of addFiltros method, of class NFeProcessadaDAO.
     */
    @Test
    public void testListar() throws Exception {
        try {
            ProdSPEDXNFeHibernateUtil.beginTransaction();
            
            NFeProcessadaDAO nfeProcDao = new NFeProcessadaDAO();
            List<NFeProcessada> lista = nfeProcDao.listar();            
            ProdSPEDXNFeHibernateUtil.commitCurrentTransaction();
            
            assertTrue(lista.size() > 0);
        } catch(Exception ex) {
            ProdSPEDXNFeHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
    @Test
    public void testSalvar() throws Exception {
        try {
            ProdSPEDXNFeHibernateUtil.beginTransaction();
            
            NFeProcessada nfeProc = new NFeProcessada();
            nfeProc.setChave("teste123");
            nfeProc.setDataHoraEmissao(new Timestamp(Calendar.getInstance().getTime().getTime()));
            nfeProc.setNomeEmitente("Teste");
            nfeProc.setNumeroNotaFiscal("123456");
            nfeProc.setValorTotalNotaFiscal(new BigDecimal(1234.45));
            
            for (byte i = 0; i < 2; i++) {
                NFeProcessadaItem item = new NFeProcessadaItem();
                item.setCodigoProduto("1231 - " + i);
                item.setEAN("323");
                item.setNomeProduto("Produto Teste - " + i);
                item.setNCM("323" + i);
                item.setCFOP("232" + i);
                item.setUnidadeMedida("CX");
                item.setQtd(new BigDecimal(3.0));

                item.setNFeProcessada(nfeProc);
                nfeProc.getItens().add(item);
            }
            
            NFeProcessadaDAO nfeProcDao = new NFeProcessadaDAO();
            nfeProcDao.salvar(nfeProc);
            
            ProdSPEDXNFeHibernateUtil.commitCurrentTransaction();
            
            assertTrue(true);
        } catch(Exception ex) {
            ProdSPEDXNFeHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
    @Test
    public void testExcluir() throws Exception {
        try {
            ProdSPEDXNFeHibernateUtil.beginTransaction();
            
            NFeProcessadaDAO nfeProcDao = new NFeProcessadaDAO();
            NFeProcessada nfeProc = new NFeProcessada();
            nfeProc.setChave("teste123");            
            nfeProc.setNomeEmitente("Teste");
            nfeProc.setNumeroNotaFiscal("123456");            
            nfeProc = nfeProcDao.selecionarUm(nfeProc);
            
            nfeProcDao.excluir(nfeProc);
            
            ProdSPEDXNFeHibernateUtil.commitCurrentTransaction();
            
            assertTrue(true);
        } catch(Exception ex) {
            ProdSPEDXNFeHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
}
