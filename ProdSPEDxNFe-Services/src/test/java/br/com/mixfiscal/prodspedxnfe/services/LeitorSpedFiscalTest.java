package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.domain.sped.SPED0200;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC100;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC150;   
import java.util.List;
import static org.junit.Assert.*;

public class LeitorSpedFiscalTest {
    
    public LeitorSpedFiscalTest() {
    }
    
    @org.junit.Test
    public void testLerArquivoSped() throws Exception {
        System.out.println("lerArquivoSped");
        //LeitorSpedFiscal instance = new LeitorSpedFiscal("F:\\Projetos\\LuluSoftwares\\MixFiscal\\ProdSPEDxNFe\\trunk\\DOCUMENTOS\\documentacao\\lider\\SUPERMERCADO LIDER DE CAMPINAS EIRELI ME012016.SPED"); 
        //LeitorSpedFiscal instance = new LeitorSpedFiscal("C:\\Users\\Lucas\\Documents\\Projetos\\LuluSoftwares\\MixFiscal\\ProdSPEDxNFe\\trunk\\DOCUMENTOS\\documentacao\\lider\\SUPERMERCADO LIDER DE CAMPINAS EIRELI ME012016.SPED");  
        LeitorSpedFiscal instance = new LeitorSpedFiscal("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\SpedFiscal_LJ001_0104201707042017.txt");        
        //LeitorSpedFiscal instance = new LeitorSpedFiscal("D:\\LÚLU SOFTWARES\\MIX_FISCAL\\ProdSPEDxNFe\\trunk\\DOCUMENTOS\\documentacao\\exemplos\\lucas-soofertas\\buzios-12-15_Cliente.TXT");
        instance.lerArquivoSped();
        List<SPEDC100> listaSpedC100 = instance.getListaSPEDC100();
        List<SPED0200> listaSped0200 = instance.getListaSPED0200();
        List<SPEDC150> listaSpedC150 = instance.getListaSPEDC150();
        assertTrue(listaSpedC100.size() > 0 && listaSped0200.size() > 0);        
    }    
}
