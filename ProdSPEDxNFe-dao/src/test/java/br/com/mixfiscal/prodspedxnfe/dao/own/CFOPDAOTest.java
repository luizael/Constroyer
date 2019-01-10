package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOP;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class CFOPDAOTest {
    
    public CFOPDAOTest() {
    }

    @Test
    public void testListar() throws Exception {
         try{
           // ConstroyerHibernateUtil.beginTransaction();
            CFOPDAO operacao = new CFOPDAO();
            
            List<CFOP> lista = operacao.listar();
                        
            //ConstroyerHibernateUtil.commitCurrentTransaction();
        }catch(Exception ex){
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
    @Test
    public void testSelecionrUm() throws Exception {
         try{
            //ConstroyerHibernateUtil.beginTransaction();
            CFOPDAO operacao = new CFOPDAO();
            
            CFOP filtro = new CFOP();
            filtro.setCodigo("5101");
            CFOP cfop = operacao.selecionarUm(filtro);
                        
            //ConstroyerHibernateUtil.commitCurrentTransaction();
        }catch(Exception ex){
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
}
