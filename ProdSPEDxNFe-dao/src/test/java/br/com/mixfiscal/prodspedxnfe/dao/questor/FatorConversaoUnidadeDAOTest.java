package br.com.mixfiscal.prodspedxnfe.dao.questor;

import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.FatorConversaoUnidade;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class FatorConversaoUnidadeDAOTest {
    
    public FatorConversaoUnidadeDAOTest() {
    }

    @Test
    public void testListar() throws Exception {
        try {
            QuestorHibernateUtil.beginTransaction();            
            FatorConversaoUnidadeDAO fcuDao = new FatorConversaoUnidadeDAO();
            List<FatorConversaoUnidade> list = fcuDao.listar();            
            QuestorHibernateUtil.commitCurrentTransaction();
            assertTrue(list.size() > 0);
        } catch(Exception ex) {
            fail(ex.getMessage());
            throw ex;
        }
    }
    
    @Test
    public void testSalvar() throws Exception {
        try {
            QuestorHibernateUtil.beginTransaction();
            
            FatorConversaoUnidade fcu = new FatorConversaoUnidade();
            fcu.setCodigoEmpresa(9999);
            fcu.setCodigoProduto(0);
            fcu.setCodigoUnidadeMedida("CX");
            fcu.setFator(new BigDecimal(55.55));
            
            FatorConversaoUnidadeDAO fcuDao = new FatorConversaoUnidadeDAO();
            fcuDao.salvar(fcu);
            
            QuestorHibernateUtil.commitCurrentTransaction();
        } catch(Exception ex) {
            QuestorHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
    @Test
    public void testExcluir() throws Exception {
        try {
            QuestorHibernateUtil.beginTransaction();
            
            FatorConversaoUnidade fcu = new FatorConversaoUnidade();
            fcu.setCodigoEmpresa(9999);
            fcu.setCodigoProduto(0);
            fcu.setCodigoUnidadeMedida("CX");            
            
            FatorConversaoUnidadeDAO fcuDao = new FatorConversaoUnidadeDAO();
            fcuDao.excluir(fcu);
            
            QuestorHibernateUtil.commitCurrentTransaction();
        } catch(Exception ex) {
            QuestorHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
}
