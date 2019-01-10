package br.com.mixfiscal.prodspedxnfe.dao.questor;

import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.EmpresaQuestor;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmpresaDAOTest {
    
    public EmpresaDAOTest() {
    }
    
    @Test
    public void testSalvar() throws Exception {
        try {
            EmpresaQuestor e = new EmpresaQuestor();
            e.setNome("Empresa Teste");
            e.setLogotipo("teste");
            EmpresaQuestorDAO empDao = new EmpresaQuestorDAO();
            empDao.salvar(e);
            assertTrue(true);
        } catch(Exception ex) {
            fail(ex.getMessage());
            throw ex;
        }
    }

    @Test
    public void testAtualizar() throws Exception {
        try {
            EmpresaQuestor e = new EmpresaQuestor();
            e.setCodigo(1);
            e.setNome("Empresa Teste Atualizada");
            e.setLogotipo("teste atualizado");
            EmpresaQuestorDAO empDao = new EmpresaQuestorDAO();
            empDao.atualizar(e);
            assertTrue(true);
        } catch(Exception ex) {
            fail(ex.getMessage());
            throw ex;
        }
    }
        
    @Test
    public void testSelecionarUm() throws Exception {
        System.out.println("selecionarUm");
        EmpresaQuestor empresa = null;
        EmpresaQuestorDAO instance = new EmpresaQuestorDAO();
        EmpresaQuestor expResult = null;
        EmpresaQuestor result = instance.selecionarUm(empresa);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testListar() throws Exception {
        QuestorHibernateUtil.beginTransaction();
        EmpresaQuestorDAO instance = new EmpresaQuestorDAO();
        List<EmpresaQuestor> result = instance.listar();
        QuestorHibernateUtil.commitCurrentTransaction();
        assertTrue(result.size() > 0);
    }
    
    @Test
    public void testListarComFiltro() throws Exception {
        EmpresaQuestor filtro = new EmpresaQuestor();
        filtro.getEstabelecimento().setInscricaoFederal("123456");
        EmpresaQuestorDAO instance = new EmpresaQuestorDAO();
        List<EmpresaQuestor> result = instance.listar(filtro);
        assertTrue(result.size() > 0);
    }
    
}
