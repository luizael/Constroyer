package br.com.mixfiscal.prodspedxnfe.dao.questor;

import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.RelacProdutoFornecedor;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class RelacProdutoFornecedorDAOTest {
    
    public RelacProdutoFornecedorDAOTest() {
    }
    
    @Test
    public void testListar() throws Exception {      
        QuestorHibernateUtil.beginTransaction();
        RelacProdutoFornecedorDAO instance = new RelacProdutoFornecedorDAO();
        List<RelacProdutoFornecedor> result = instance.listar();
        assertTrue(result.size() > 0);
        QuestorHibernateUtil.commitCurrentTransaction();
    }
    
    @Test
    public void testSalvar() throws Exception {
        try {
            QuestorHibernateUtil.beginTransaction();
            RelacProdutoFornecedor relProdFor = new RelacProdutoFornecedor();
            relProdFor.setCodigoEmpresa(16);
            relProdFor.setCodigoEstab(1);
            relProdFor.setCodigoPessoa(8510);
            relProdFor.setCodigoProduto(4076);
            relProdFor.setReferItemFornecedor("8896");
            RelacProdutoFornecedorDAO instance = new RelacProdutoFornecedorDAO();            
            instance.salvar(relProdFor);
            assertTrue(true);
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
            RelacProdutoFornecedor relProdFor = new RelacProdutoFornecedor();
            relProdFor.setCodigoEmpresa(16);
            relProdFor.setCodigoEstab(1);
            relProdFor.setCodigoPessoa(8510);
            relProdFor.setCodigoProduto(4076);
            relProdFor.setReferItemFornecedor("8896");
            RelacProdutoFornecedorDAO instance = new RelacProdutoFornecedorDAO();            
            instance.excluir(relProdFor);
            assertTrue(true);
            QuestorHibernateUtil.commitCurrentTransaction();
        } catch(Exception ex) {
            QuestorHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
}
