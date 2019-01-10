//
//package br.com.mixfiscal.prodspedxnfe.services;
//
//
//import br.com.mixfiscal.prodspedxnfe.domain.own.CFOP;
//import br.com.mixfiscal.prodspedxnfe.domain.own.CFOPDePara;
//import java.util.List;
//import static org.junit.Assert.*;
//import org.junit.Test;
//public class CFOPDeParaServiceTeste {
//    
//    @Test
//    public void salvarTeste(){
//        CFOPDePara dePara = new CFOPDePara();
//        
//        CFOP de = new CFOP();
//        de.setId(54);
//        de.setCodigo("1502");
//        de.setNome("Opa é um teste de ");
//        dePara.setCfopDe(de);
//        
//        CFOP para = new CFOP();
//        para.setId(54);
//        para.setCodigo("1502");
//        para.setNome("Opa é um teste para ");
//        dePara.setCfopDe(de);
//        dePara.setCfopPara(para);
//        
//        
//        
//        try{
//            CFOPDeParaService deParaService = new CFOPDeParaService();
//            deParaService.salvar(dePara);
//            
//        }catch(Exception ex){}
//       
//    }
//}
