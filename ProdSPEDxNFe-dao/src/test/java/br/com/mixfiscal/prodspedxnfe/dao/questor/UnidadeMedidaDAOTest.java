package br.com.mixfiscal.prodspedxnfe.dao.questor;

import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.UnidadeMedida;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class UnidadeMedidaDAOTest {
    
    public UnidadeMedidaDAOTest() {
    }
        
    @Test
    public void testListar() {
       try {
           QuestorHibernateUtil.beginTransaction();
           UnidadeMedidaDAO umDao = new UnidadeMedidaDAO();
           List<UnidadeMedida> lista = umDao.listar();
           QuestorHibernateUtil.commitCurrentTransaction();
           assertTrue(lista.size() > 0);
       } catch(Exception ex) {           
           fail(ex.getMessage());
       }
    }
    
    @Test
    public void testSalvar() {
        try {
           QuestorHibernateUtil.beginTransaction();
           
           UnidadeMedida um = new UnidadeMedida();
           um.setCodigoEmpresa(9999);
           um.setCodigoUnidadeMedida("TS");
           um.setNome("Unidade Medida Teste");
           
           UnidadeMedidaDAO umDao = new UnidadeMedidaDAO();
           umDao.salvar(um);
        
           QuestorHibernateUtil.commitCurrentTransaction();
           assertTrue(true);
       } catch(Exception ex) {           
           QuestorHibernateUtil.rollbackCurrentTransaction();
           fail(ex.getMessage());
       }
    }
    
    @Test
    public void testExcluir() {
        try {
           QuestorHibernateUtil.beginTransaction();
           
           UnidadeMedida um = new UnidadeMedida();
           um.setCodigoEmpresa(9999);
           um.setCodigoUnidadeMedida("TS");
           
           UnidadeMedidaDAO umDao = new UnidadeMedidaDAO();
           umDao.excluir(um);
        
           QuestorHibernateUtil.commitCurrentTransaction();
           assertTrue(true);
       } catch(Exception ex) {           
           QuestorHibernateUtil.rollbackCurrentTransaction();
           fail(ex.getMessage());
       }
    }
    
}
