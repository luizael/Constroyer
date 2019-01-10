package br.com.mixfiscal.prodspedxnfe.dao.questor;

import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.Municipio;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class MunicipioDAOTest {
    
    public MunicipioDAOTest() {
    }
        
    @Test
    public void testListar() throws Exception {
        try {
            QuestorHibernateUtil.beginTransaction();
            MunicipioDAO instance = new MunicipioDAO();
            List<Municipio> result = instance.listar();
            QuestorHibernateUtil.commitCurrentTransaction();
            assertTrue(result.size() > 0);
        } catch(Exception ex) {
            fail(ex.getMessage());
            throw ex;
        }
    }
    
    @Test
    public void testSalvar() throws Exception {
        try {
            QuestorHibernateUtil.beginTransaction();
            
            Municipio m = new Municipio();
            m.setSiglaEstado("SP");            
            m.setNome("Teste");
            m.setCep("teste");
            m.setValorMinISSRetido(BigDecimal.ONE);
            m.setTaxaRecISSQN(BigDecimal.ONE);
            m.setCodigoRais(1234321);
            
            MunicipioDAO instance = new MunicipioDAO();
            instance.salvar(m);
            
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
            
            MunicipioDAO instance = new MunicipioDAO();
            Municipio m = new Municipio();
            m.setSiglaEstado("SP");            
            m.setNome("Teste");
            m.setCep("teste");
            m.setValorMinISSRetido(BigDecimal.ONE);
            m.setTaxaRecISSQN(BigDecimal.ONE);
            m.setCodigoRais(1234321);
            m = instance.selecionarUm(m);
            instance.excluir(m);
            
            QuestorHibernateUtil.commitCurrentTransaction();
            assertTrue(true);
        } catch(Exception ex) {
            QuestorHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
}
