package br.com.mixfiscal.prodspedxnfe.dao.questor;

import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.CFOP;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class CFOPDAOTest {
    public CFOPDAOTest() {
    }
    
    @Test
    public void testSalvar() throws Exception {
        try {
            CFOPDAO cfopDao = new CFOPDAO();
            CFOP cfop = new CFOP();
            cfop.setCodigoEmpresa(2);
            cfop.setCodigoEstab(2);
            cfop.setCodigoCFOP(2);
            cfop.setDescricao("Testando " + Calendar.getInstance().getTime());
            cfopDao.salvar(cfop);
            assertTrue(true);
        } catch (Exception ex) {
            fail(ex.getMessage());
            throw ex;
        }
    }
    
    @Test
    public void testAtualizar() throws Exception {
        try {
            CFOPDAO cfopDao = new CFOPDAO();
            CFOP cfop = new CFOP();
            cfop.setCodigoEmpresa(2);
            cfop.setCodigoEstab(2);
            cfop.setCodigoCFOP(2);
            cfop.setDescricao("Atualizando Teste CFOP " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime()));
            cfopDao.atualizar(cfop);
            assertTrue(true);
        } catch(Exception ex) {
            fail(ex.getMessage());
            throw ex;
        }
    }
    
    @Test
    public void testExcluir() throws Exception {
        try {
            CFOPDAO cfopDao = new CFOPDAO();
            CFOP cfop = new CFOP();
            cfop.setCodigoEmpresa(2);
            cfop.setCodigoEstab(2);
            cfop.setCodigoCFOP(2);            
            cfopDao.excluir(cfop);
            assertTrue(true);
        } catch(Exception ex) {
            fail(ex.getMessage());
            throw ex;
        }
    }
    
    @Test
    public void testListar() throws Exception {
        QuestorHibernateUtil.beginTransaction();
        CFOPDAO cfopDao = new CFOPDAO();        
        List<CFOP> lista = cfopDao.listar();
        assertTrue(lista.size() > 0);
        QuestorHibernateUtil.commitCurrentTransaction();
    }    
}
