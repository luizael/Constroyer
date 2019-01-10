package br.com.mixfiscal.prodspedxnfe.dao.questor;

import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.Pessoa;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class PessoaDAOTest {
    
    public PessoaDAOTest() {
    }
    
    @Test
    public void testListar() throws Exception {      
        QuestorHibernateUtil.beginTransaction();
        PessoaDAO instance = new PessoaDAO();
        List<Pessoa> result = instance.listar();
        QuestorHibernateUtil.commitCurrentTransaction();
        assertTrue(result.size() > 0);
    }
    
    @Test
    public void testSalvar() throws Exception {
        try {
            QuestorHibernateUtil.beginTransaction();
            
            Pessoa pessoa = new Pessoa();            
            pessoa.setNome("Pessoa Teste");                    
            pessoa.setTipoInscr(2);
            pessoa.setInscrFederal("123456");
            pessoa.setEndereco("Logradouro teste");
            pessoa.setNumEndereco(123);
            pessoa.setBairro("Bairro Teste");
            pessoa.setCodigoMunicipio((short)9999);
            pessoa.setSiglaEstado("SP");
            pessoa.setCep("00000-000");
            pessoa.setInscrEstad("123456");
            pessoa.setInscrMunic("123456");
            pessoa.setProdutoRural('0');
            pessoa.setSequencia((short)0);
            
            PessoaDAO pessoaDao = new PessoaDAO();
            pessoaDao.salvar(pessoa);
            
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
            
            PessoaDAO pessoaDao = new PessoaDAO();
            Pessoa p = new Pessoa();
            p.setNome("Pessoa Teste");                    
            p.setTipoInscr(2);
            p.setInscrFederal("123456");
            p.setEndereco("Logradouro teste");
            p.setNumEndereco(123);
            p = pessoaDao.selecionarUm(p);
            pessoaDao.excluir(p);
            
            QuestorHibernateUtil.commitCurrentTransaction();
            
            assertTrue(true);
        } catch(Exception ex) {
            QuestorHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }    
}
