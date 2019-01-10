package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFeItem;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC170;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
/*
D:\LULU SOFTWARES\MIX_FISCAL\JUNIT_TESTE
*/
public class AssociadorProdutoFornecedorTest {
    
    public AssociadorProdutoFornecedorTest() {
    }

    @Test
    public void testProcessar() throws Exception {
        System.out.println("processar");
        try {            
//            AssociadorProdutoFornecedor instance = new AssociadorProdutoFornecedor(
//                    "C:\\Users\\Lucas\\Documents\\Projetos\\LuluSoftwares\\MixFiscal\\exemplos\\M. de Souza ( Romiedo)\\2016.05\\SPED FISCAL\\Sped_Fiscal - 05-16.txtcompleto.txt",
//                    "C:\\Users\\Lucas\\Documents\\Projetos\\LuluSoftwares\\MixFiscal\\exemplos\\M. de Souza ( Romiedo)\\2016.05\\unico");
//            AssociadorProdutoFornecedor instance = new AssociadorProdutoFornecedor(
//                    "C:\\Users\\Lucas\\Documents\\Projetos\\LuluSoftwares\\MixFiscal\\exemplos\\M. de Souza ( Romiedo)\\2016.05\\SPED FISCAL\\Sped_Fiscal - 05-16.txtcompleto.txt",
//                    "C:\\Users\\Lucas\\Documents\\Projetos\\LuluSoftwares\\MixFiscal\\exemplos\\M. de Souza ( Romiedo)\\2016.05\\unico");
//            AssociadorProdutoFornecedor instance = new AssociadorProdutoFornecedor(
//                    "F:\\Projetos\\LuluSoftwares\\MixFiscal\\Exemplos\\M. de Souza ( Romiedo)\\2016.05\\SPED FISCAL\\SPED_Modificado.txt",        
//                    "F:\\Projetos\\LuluSoftwares\\MixFiscal\\Exemplos\\M. de Souza ( Romiedo)\\2016.05\\XML ENTRADA");

            AssociadorProdutoFornecedor instance = new AssociadorProdutoFornecedor(
                    "D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\SpedFiscal_LJ001_0104201707042017.txt",        
                    "D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE");

            instance.setExecutarTransacoes(true);
            instance.setMoverXMLsNaoEncSped(true);
            instance.setAnalisarOrdemItensAuto(true);
         
            instance.processar();
            
            assertTrue(true);
        } catch(Exception ex) {            
            fail(ex.getMessage());
            throw ex;
        }    
    }
    
    @Test
    public void testProcessarItensIdentificadosPeloUsuario() throws Exception {
        System.out.println("processar");
        try {            
//            AssociadorProdutoFornecedor instance = new AssociadorProdutoFornecedor(
//                    "C:\\Users\\Lucas\\Documents\\Projetos\\LuluSoftwares\\MixFiscal\\exemplos\\M. de Souza ( Romiedo)\\2016.05\\SPED FISCAL\\Sped_Fiscal - 05-16.txtcompleto.txt",
//                    "C:\\Users\\Lucas\\Documents\\Projetos\\LuluSoftwares\\MixFiscal\\exemplos\\M. de Souza ( Romiedo)\\2016.05\\unico");
//            AssociadorProdutoFornecedor instance = new AssociadorProdutoFornecedor(
//                    "F:\\Projetos\\LuluSoftwares\\MixFiscal\\Exemplos\\M. de Souza ( Romiedo)\\2016.05\\SPED FISCAL\\SPED_Modificado.txt",        
//                    "F:\\Projetos\\LuluSoftwares\\MixFiscal\\Exemplos\\M. de Souza ( Romiedo)\\2016.05\\XML ENTRADA");
             AssociadorProdutoFornecedor instance = new AssociadorProdutoFornecedor(
                    "D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\SpedFiscal_LJ001_0104201707042017.txt",        
                    "D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE");
             
            instance.setExecutarTransacoes(true);
            instance.setMoverXMLsNaoEncSped(true);
            instance.setAnalisarOrdemItensAuto(true);
            instance.processar();
            
            Map<SPEDC170, Map<NFeItem, Float>> itensPreAss =  instance.getItensPreAssociados();
            if (itensPreAss.size() > 0) {
                for (byte i = 0; i < itensPreAss.size(); i++) {                    
                    SPEDC170 key = (SPEDC170)itensPreAss.keySet().toArray()[i];
                    Map<NFeItem, Float> provaveisKey1 = itensPreAss.get(key);

                    NFeItem escolhida = null;
                    Optional<Entry<NFeItem, Float>> optEscohida = provaveisKey1.entrySet()
                                                                               .stream()
                                                                               .max((e1, e2) -> Float.compare(e1.getValue(), e2.getValue()));
                    if (optEscohida.isPresent()) {
                        escolhida = optEscohida.get().getKey();
                        instance.getItensAssociadosManualmente().put(key, escolhida);                        
                    }
                }
                instance.processarItensIdentificadosPeloUsuario();
            }
            
            assertTrue(true);
        } catch(Exception ex) {            
            fail(ex.getMessage());
            throw ex;
        }    
    }
    
    @Test
    public void testLog() {
        Logger log = LogManager.getLogger(AssociadorProdutoFornecedorTest.class);
        log.info("Testando log");
        log.debug("Testando log", new Exception("DEBUG: Exceção de teste"));
        log.error("Testando log", new Exception("ERROR: Exceção de teste"));
        log.trace("Testando log");
    }
    @Test
    public void removerBassra(){
        String caminho = "\\teste\\opa\\ola mundo.txt";
        String arquivo = caminho.substring(caminho.lastIndexOf("\\")+1);
        System.out.println("teste "+caminho.substring(caminho.lastIndexOf("\\")+1));
        System.out.println("teste "+caminho.replace(arquivo,""));
    }
    
}
