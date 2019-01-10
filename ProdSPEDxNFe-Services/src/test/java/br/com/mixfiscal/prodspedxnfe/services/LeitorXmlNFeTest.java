


































































package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFe;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

public class LeitorXmlNFeTest {
    
    public LeitorXmlNFeTest() {
    }    
   
    @Test
    public void testLerXmlNFe() throws Exception {
        System.out.println("lerXmlNFe");
        LeitorXmlNFe instance = new LeitorXmlNFe("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\35170454505052000904550010013363121675825184.xml");
        
        instance.lerXmlNFe();
        NFe nfe = instance.getNFe();
        
        assertTrue(nfe.getEmitente() != null && !nfe.getEmitente().getCNPJ().isEmpty() &&
                   nfe.getDestinatario() != null && !nfe.getDestinatario().getCNPJ().isEmpty() &&
                   nfe.getItens().size() > 0);
    }
    
    @Test
    public void testLerXmlsNFes() throws Exception {
        System.out.println("lerXmlNFe");
        LeitorXmlNFe instance = new LeitorXmlNFe();
        
        Map<String, NFe> listaNFes = instance.lerXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\35170454505052000904550010013363121675825184.xml");
        //Map<String, NFe> listaNFes = instance.lerXmlsNFes("F:\\Projetos\\LuluSoftwares\\MixFiscal\\Exemplos\\M. de Souza (Romiedo)\\2016.05\\unico");
                
        assertTrue(!listaNFes.isEmpty());
    }
    
}
