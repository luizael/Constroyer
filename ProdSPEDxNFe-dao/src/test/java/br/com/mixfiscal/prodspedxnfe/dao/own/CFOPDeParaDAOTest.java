package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOP;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOPDePara;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class CFOPDeParaDAOTest {
    
    public CFOPDeParaDAOTest() {
    }
   
    @Test
    public void testeSalvar() throws Exception {
        try {
            //ConstroyerHibernateUtil.beginTransaction();

            CFOPDePara equivalente = new CFOPDePara();
            CFOPDeParaDAO operacao = new CFOPDeParaDAO();

            CFOP cfopEntrada = new  CFOP();
            CFOP cfopSaida = new  CFOP();
            cfopEntrada.setId(1);
            cfopSaida.setId(2);

            equivalente.setCfopDe(cfopEntrada);
            equivalente.setCfopPara(cfopSaida);

            operacao.salvar(equivalente) ;

            System.out.println("Inserindo na dao");
            //ConstroyerHibernateUtil.commitCurrentTransaction();
       
        } catch(Exception ex) {
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
   
    @Test
    public void testExcluir()throws Exception {
        try{
            //ConstroyerHibernateUtil.beginTransaction();
            
            CFOPDePara equivalente = new    CFOPDePara();
            CFOPDeParaDAO operacao = new CFOPDeParaDAO();
            
            CFOP cfopEntrada = new  CFOP();
            CFOP cfopSaida = new  CFOP();
            cfopEntrada.setId(1);
            cfopSaida.setId(2);
            
            equivalente.setCfopDe( cfopEntrada);
            equivalente.setCfopPara(cfopSaida);
            
            operacao.excluir(equivalente) ;
            
            System.out.println("Excluido com sucesso");
            
            //ConstroyerHibernateUtil.commitCurrentTransaction();
        }catch(Exception ex){
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
    @Test
    public void testSelecionar()throws Exception {
        try{
            //ConstroyerHibernateUtil.beginTransaction();
            CFOPDeParaDAO operacao = new CFOPDeParaDAO();
            
            List<CFOPDePara> lista = operacao.listar();
                        
            //ConstroyerHibernateUtil.commitCurrentTransaction();
        }catch(Exception ex){
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
            fail(ex.getMessage());
            throw ex;
        }
    }
    
}
