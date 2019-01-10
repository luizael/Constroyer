package br.com.mixfiscal.prodspedxnfe.dao.questor;

import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.ProdutoQuestor;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProdutoDAOTest {
    
    public ProdutoDAOTest() {
    }    
    
    @Test
    public void testListar() throws Exception {      
        ProdutoDAO instance = new ProdutoDAO();
        List<ProdutoQuestor> result = instance.listar();
        assertTrue(result.size() > 0);
    }
    
    @Test
    public void testSalvar() throws Exception {
        try {
            QuestorHibernateUtil.beginTransaction();
            
            ProdutoQuestor prod = new ProdutoQuestor();
            prod.setCodigoEmpresa(16);
            prod.setReferenProduto("9999.9999");
            prod.setCodigoGrupoProduto((short)1);
            prod.setDescricao("testSalvar()");
            prod.setUnidadeMedida("UN");
            prod.setIndicadorTipo((short)0);
            prod.setDataAtualizacao(Calendar.getInstance().getTime());
            prod.setDnf('0');
            prod.setDacon('1');
            prod.setCstEntrada(0);
            prod.setCstSaida(0);
            prod.setTipoCredito("4.3.07.01");
            prod.setTipoDebito("4.3.07.01");
            prod.setCodigoNcm("9999.9999");
            prod.setAliqIcms(BigDecimal.ZERO);
            
            ProdutoDAO instance = new ProdutoDAO();
            instance.salvar(prod);
            
            QuestorHibernateUtil.commitCurrentTransaction();
            assertTrue(true);
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
            
            ProdutoDAO prodDao = new ProdutoDAO();
            ProdutoQuestor prod = new ProdutoQuestor();
            prod.setCodigoEmpresa(16);
            prod.setReferenProduto("9999.9999");
            prod.setDescricao("testSalvar()");  
            
            prod = prodDao.selecionarUm(prod);            
            prodDao.excluir(prod);
            
            QuestorHibernateUtil.commitCurrentTransaction();
            assertTrue(true);
        } catch(Exception ex) {
            QuestorHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }    
}
