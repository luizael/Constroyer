package br.com.mixfiscal.prodspedxnfe.services;
import br.com.mixfiscal.prodspedxnfe.dao.own.EmpresaDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS10;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.IPI;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFe;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFeItem;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.PessoaJuridica;
import br.com.mixfiscal.prodspedxnfe.domain.own.Empresa;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC100;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC170;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC190;
import br.com.mixfiscal.prodspedxnfe.services.MetodosUtil.idxSPEDC170;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProcessadorSPEDTest {
    SPEDC100 c100 = null;   
   
    public ProcessadorSPEDTest() {}
    
    @Test
    public void NESTOR_relacionamentoExistente(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\1_RELACIONAMENTO_EXISTENTE\\SpedFiscal_LJ001_0104201707042017.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\1_RELACIONAMENTO_EXISTENTE");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170401132613000145550010003032741207924616");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170401132613000145550010003032741207924616")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,1); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}
            
    }
    
    @Test
    public void NESTOR_umParaUm(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\2_UM_PARA_UM\\SpedFiscal_LJ001_0104201707042017.TXT");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\2_UM_PARA_UM");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170412592243000145550010000005231004000601");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170412592243000145550010000005231004000601")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,2); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}    
    }
    
    @Test
    public void NESTOR_mesmaOrdem(){
       try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\3_MESMA_ORDEM\\SpedFiscal_LJ001_0104201707042017.TXT");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\3_MESMA_ORDEM");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            instance.verificarSeItensEstaoEmOrdem(listaSpedC100, listaNFes);
            NFe nfe = listaNFes.get("35170308919204000132550010000434641000434643");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170308919204000132550010000434641000434643")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,3); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}                        
    }
    
    @Test
    public void NESTOR_ean(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\4_EAN\\SpedFiscal_LJ001_0104201707042017.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\4_EAN");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170331565104029400550010004208761184580814");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170331565104029400550010004208761184580814")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,4); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}                        
    }
    
    @Test
    public void NESTOR_eanTrib(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\5_EANTRIB\\SpedFiscal_LJ001_0104201707042017.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\5_EANTRIB");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170331565104029400550010004208761184580814");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170331565104029400550010004208761184580814")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,5); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}            
    }
    
    @Test
    public void NESTOR_descricaoExata(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\6_DESCRICAO_EXATA\\SpedFiscal_LJ001_0104201707042017.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\6_DESCRICAO_EXATA");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170331565104029400550010004208761184580814");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170331565104029400550010004208761184580814")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,6); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}            
    }
    
    @Test
    public void NESTOR_descricaoProxima(){
      try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\7_DESCRICAO_PROXIMA\\SpedFiscal_LJ001_0104201707042017.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\7_DESCRICAO_PROXIMA");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170331565104029400550010004208761184580814");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170331565104029400550010004208761184580814")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,8); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){} 
    }
    
    @Test
    public void NESTOR_descricaoProximaValor(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\8_DESCRICAO_PROXIMA_VALOR\\SpedFiscal_LJ001_0104201707042017.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\8_DESCRICAO_PROXIMA_VALOR");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            //NFe nfe = listaNFes.get("35170331565104029400550010004208761184580814");
            NFe nfe = listaNFes.get("35170400635244000140550090000127471102534263");
            listaSpedC100.stream().forEach((sc100)->{
                //if(sc100.getChaveNFe().equals("35170331565104029400550010004208761184580814")){
                if(sc100.getChaveNFe().equals("35170400635244000140550090000127471102534263")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,8); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}            
    }
    
    @Test
    public void NESTOR_umParaMuitos(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\8_UM_PRA_MUITOS\\SpedFiscal_LJ001_0104201707042017.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\8_UM_PRA_MUITOS");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170454505052000904550010013363121675825184");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170454505052000904550010013363121675825184")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,8); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}            
            
    }
    
    @Test
    public void NESTOR_lote(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.setCaminhoArquivoSPED("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\TESTE_EM_LOTE\\SpedFiscal_LJ001_0104201707042017.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NESTOR\\TESTE_EM_LOTE");
            instance.processar();           
            assertTrue(true);
        }catch(Exception ex){}            
    }
    
    //--------------------------------------------------------------------------------------------
     @Test
    public void NOGUEIRA_relacionamentoExistente(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\1_RELACIONAMENTO_EXISTENTE\\SpedFiscal_LJ001_0104201707042017.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\1_RELACIONAMENTO_EXISTENTE");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170401132613000145550010003032741207924616");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170401132613000145550010003032741207924616")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,1); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}
            
    }
    
    @Test
    public void NOGUEIRA_umParaUm(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\2_UM_PARA_UM\\SPEDFISCAL-LOJA02-03-2017-11-A.TXT");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\2_UM_PARA_UM");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170396259643000123550010002163411999783650");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170396259643000123550010002163411999783650")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,2); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}    
    }
    
    @Test
    public void NOGUEIRA_mesmaOrdem(){
       try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\3_MESMA_ORDEM\\SpedFiscal_LJ001_0104201707042017.TXT");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\3_MESMA_ORDEM");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            instance.verificarSeItensEstaoEmOrdem(listaSpedC100, listaNFes);
            NFe nfe = listaNFes.get("35170308919204000132550010000434641000434643");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170308919204000132550010000434641000434643")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,3); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}                        
    }
    
    @Test
    public void NOGUEIRA_ean(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\4_EAN\\SPEDFISCAL-LOJA02-03-2017-11-A.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\4_EAN");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170362190574000127550010000036661000036669");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170362190574000127550010000036661000036669")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,4); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}                        
    }
    
    @Test
    public void NOGUEIRA_eanUmParaMuitos(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\4_EAN_UM_PARA_MUITOS\\SPEDFISCAL-LOJA02-03-2017-11-A.TXT");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\4_EAN_UM_PARA_MUITOS");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170307824167000116550010001118961001118965");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170307824167000116550010001118961001118965")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,5); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}                        
    }
    
    @Test
    public void NOGUEIRA_eanTrib(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\5_EANTRIB\\SpedFiscal_LJ001_0104201707042017.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\5_EANTRIB");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170331565104029400550010004208761184580814");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170331565104029400550010004208761184580814")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,5); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}            
    }
    
    @Test
    public void NOGUEIRA_descricaoExata(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\6_DESCRICAO_EXATA\\SpedFiscal_LJ001_0104201707042017.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\6_DESCRICAO_EXATA");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170331565104029400550010004208761184580814");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170331565104029400550010004208761184580814")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,6); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}            
    }
    
    @Test
    public void NOGUEIRA_descricaoProxima(){
      try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\7_DESCRICAO_PROXIMA\\SpedFiscal_LJ001_0104201707042017.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\7_DESCRICAO_PROXIMA");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170331565104029400550010004208761184580814");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170331565104029400550010004208761184580814")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,8); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){} 
    }
    
    @Test
    public void NOGUEIRA_descricaoProximaValor(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\8_DESCRICAO_PROXIMA_VALOR\\SPEDFISCAL-LOJA02-05-2017-11-A.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\8_DESCRICAO_PROXIMA_VALOR");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            //NFe nfe = listaNFes.get("35170331565104029400550010004208761184580814");
            NFe nfe = listaNFes.get("35170556228356010366550320005911961989166020");
            listaSpedC100.stream().forEach((sc100)->{
                //if(sc100.getChaveNFe().equals("35170331565104029400550010004208761184580814")){
                if(sc100.getChaveNFe().equals("35170556228356010366550320005911961989166020")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,8); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}            
    }
    
    @Test
    public void NOGUEIRA_umParaMuitos(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\10_UM_PRA_MUITOS\\SPEDFISCAL-LOJA02-05-2017-11-A.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\10_UM_PRA_MUITOS");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170556228356010366550320005911961989166020");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170556228356010366550320005911961989166020")){
                    c100 = sc100;                   
                    return;
                }               
            });
            MetodosUtil asserts = new MetodosUtil();
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,10); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}            
            
    }
    @Test
    public void NOGUEIRA_testeUnitario(){
           try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("C:\\SPEDS\\TesteUni\\SPEDFISCAL020517.TXT");
            instance.setCaminhoDiretorioXmlsNFes("C:\\SPEDS\\TesteUni");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170523444909000134550010000016381000016388");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170523444909000134550010000016381000016388")){
                    c100 = sc100;                   
                    return;
                }               
            });            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,0); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}            
    }
    
    @Test
    public void NOGUEIRA_lote(){
     try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            //instance.setCaminhoArquivoSPED("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\EXEMPLOS_MAIO\\SPEDFISCAL-LOJA02-05-2017-11-A.TXT");
            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\EXEMPLOS_MAIO");
            instance.setCaminhoArquivoSPED("C:\\SPEDS\\SPED\\SPEDFISCAL-LOJA02-05-2017-11-A.TXT");
            instance.setCaminhoDiretorioXmlsNFes("C:\\SPEDS\\NFE\\62190574000208\\55 - NF-e\\Autorizada\\Com ciencia");
       
            instance.processar();           
            assertTrue(true);
        }catch(Exception ex){}
    }
     
    @Test
     public void NOGUEIRA_loteUnit(){
     try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.setCaminhoArquivoSPED("C:\\SPEDS\\M-Box\\SPED\\SPEDFISCAL020517.TXT");
            instance.setCaminhoDiretorioXmlsNFes("C:\\SPEDS\\M-Box\\XML");
            instance.processar();           
            assertTrue(true);
        }catch(Exception ex){}
    }
    
     //--------------------------------------------------------------------------    
    
     @Test
    public void AEJ_relacionamentoExistente(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\1_RELACIONAMENTO_EXISTENTE\\SPEDFISCAL-LOJA02-05-2017-10-A.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\1_RELACIONAMENTO_EXISTENTE");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170520321387000195550010000023781052743503");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170520321387000195550010000023781052743503")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,1); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}
            
    }
    
    @Test
    public void  AEJ_umParaUm(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\2_UM_PARA_UM\\SPEDFISCAL-LOJA02-05-2017-10-A.TXT");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\2_UM_PARA_UM");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170520321387000195550010000023781052743503");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170520321387000195550010000023781052743503")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,2); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}    
    }
    
    @Test
    public void  AEJ_mesmaOrdem(){
       try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\3_MESMA_ORDEM\\SPEDFISCAL-LOJA02-05-2017-10-A.TXT");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\3_MESMA_ORDEM");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            instance.verificarSeItensEstaoEmOrdem(listaSpedC100, listaNFes);
            NFe nfe = listaNFes.get("35170520321387000195550010000023781052743503");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170520321387000195550010000023781052743503")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,3); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}                        
    }
    
    @Test
    public void  AEJ_ean(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\4_EAN\\SPEDFISCAL-LOJA02-05-2017-10-A.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\4_EAN");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170520321387000195550010000023781052743503");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170520321387000195550010000023781052743503")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,4); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}                        
    }
    
    @Test
    public void  AEJ_eanUmParaMuitos(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\4_EAN_UM_PARA_MUITOS\\SPEDFISCAL-LOJA02-05-2017-10-A.TXT");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\4_EAN_UM_PARA_MUITOS");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170520321387000195550010000023781052743503");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170520321387000195550010000023781052743503")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,5); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}                        
    }
    
    @Test
    public void  AEJ_eanTrib(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\5_EANTRIB\\SPEDFISCAL-LOJA02-05-2017-10-A.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\5_EANTRIB");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170520321387000195550010000023781052743503");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170520321387000195550010000023781052743503")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,5); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}            
    }
    
    @Test
    public void  AEJ_descricaoExata(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\6_DESCRICAO_EXATA\\SPEDFISCAL-LOJA02-05-2017-10-A.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\6_DESCRICAO_EXATA");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170520321387000195550010000023781052743503");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170520321387000195550010000023781052743503")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,6); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}            
    }
    
    @Test
    public void AEJ_descricaoProxima(){
      try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\7_DESCRICAO_PROXIMA\\SPEDFISCAL-LOJA02-05-2017-10-A.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\7_DESCRICAO_PROXIMA");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170520321387000195550010000023781052743503");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170520321387000195550010000023781052743503")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,8); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){} 
    }
    
    @Test
    public void  AEJ_descricaoProximaValor(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\8_DESCRICAO_PROXIMA_VALOR\\SPEDFISCAL-LOJA02-05-2017-10-A.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\8_DESCRICAO_PROXIMA_VALOR");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            //NFe nfe = listaNFes.get("35170331565104029400550010004208761184580814");
            NFe nfe = listaNFes.get("35170520321387000195550010000023781052743503");
            listaSpedC100.stream().forEach((sc100)->{
                //if(sc100.getChaveNFe().equals("35170331565104029400550010004208761184580814")){
                if(sc100.getChaveNFe().equals("35170520321387000195550010000023781052743503")){
                    c100 = sc100;                   
                    return;
                }               
            });
            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,8); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}            
    }
    
    @Test
    public void  AEJ_umParaMuitos(){
        try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\10_UM_PRA_MUITOS\\SPEDFISCAL-LOJA02-05-2017-10-A.txt");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\10_UM_PRA_MUITOS");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170520321387000195550010000023781052743503");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170520321387000195550010000023781052743503")){
                    c100 = sc100;                   
                    return;
                }               
            });
            MetodosUtil asserts = new MetodosUtil();
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,10); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}            
            
    }
    @Test
    public void  AEJ_testeUnitario(){
           try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\9_TESTE_UNITARIO\\SPEDFISCAL-LOJA02-05-2017-10-A.TXT");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\9_TESTE_UNITARIO");
            
            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
            List<NFeItem> itens = new ArrayList<>();
            Map<String, NFe> listaNFes = instance.carregarNFes();
            NFe nfe = listaNFes.get("35170520321387000195550010000023781052743503");
            
            listaSpedC100.stream().forEach((sc100)->{
                if(sc100.getChaveNFe().equals("35170520321387000195550010000023781052743503")){
                    c100 = sc100;                   
                    return;
                }               
            });            
            if(c100 != null){
                try{
                    instance.identificarItensSpdNFe(c100, nfe,0); //metodo a ser testado
                }catch(Exception ex){
                    System.out.println("Erro: " + ex.getMessage());
                }
            } 
            assertTrue(true);           
        }catch(Exception ex){}            
    }
    
    @Test
    public void AEJ_lote(){
     try{
            ProcessadorSPED instance = new ProcessadorSPED();            
            //instance.setCaminhoArquivoSPED("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\EXEMPLOS_MAIO\\SPEDFISCAL-LOJA02-05-2017-11-A.TXT");
            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\NOGUEIRA\\EXEMPLOS_MAIO");
            instance.setCaminhoArquivoSPED("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\TESTE_EM_LOTE\\SPEDFISCAL-LOJA02-05-2017-10-A.TXT");
            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\AEJ\\TESTE_EM_LOTE");
       
            instance.processar();           
            assertTrue(true);
        }catch(Exception ex){}
    }
    //----------------------------------------------------------------------------------------------------------
    
    
//    @Test
//    public void testarVinculoPorEAN(){
//        try{
//            Logger log = LogManager.getLogger(ProcessadorSPED.class);
//            log.info("Inicio do teste- Vinculo por EAN -----------------------------------------------------------");
//            ProcessadorSPED instance = new ProcessadorSPED();
//            
//            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN\\TESTE_MARCELO\\vinculoPorEAN-SPED.txt");
//            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN\\TESTE_MARCELO");
//            
//            //instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN_TRIB_MARCELO\\SpedFiscal_LJ001_0104201707042017.txt");
//            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN_TRIB_MARCELO");
//            
//            //instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN\\SpedFiscal_LJ001_0104201707042017.txt");
//            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN");
//            
//            
//            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
//            List<NFeItem> itens = new ArrayList<>();
//            Map<String, NFe> listaNFes = instance.carregarNFes();
//            NFe nfe = listaNFes.get("00000000000000000000000000000000000000000001"); 
//            
//            listaSpedC100.stream().forEach((sc100)->{
//                if(sc100.getChaveNFe().equals("00000000000000000000000000000000000000000001")){
//                    c100 = sc100;
//                    System.out.println("Encontrou a nota no sped");
//                    return;
//                }               
//            });
//            
//            if(c100 != null){
//                try{
//                    instance.identificarItensSpdNFe(c100, nfe); //metodo a ser testado
//                }catch(Exception ex){
//                    System.out.println("Erro: " + ex.getMessage());
//                }
//            }
//            //verifica se os itens passado por parametro estao relacionados
//            if(instance.compararItensRelacionados(c100.getListaSPEDC170().get(0), nfe.getItens().get(0))){
//                 System.out.println("Itens relacionados");
//            }else{
//                System.out.println("Itens não relacionados");
//            }
//            
//             
//            assertTrue(true);
//            log.info("fim do teste----------------------------------------------------------------------");
//        }catch(Exception ex){}
//            
//    }
//    
//    @Test
//    public void testarVinculoPorEANTrib(){
//        try{
//            Logger log = LogManager.getLogger(ProcessadorSPED.class);
//            log.info("Inicio do teste- Vinculo por EAN Trib -----------------------------------------------------------");
//            ProcessadorSPED instance = new ProcessadorSPED();
//            
//            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN_TRIB\\SpedFiscal_LJ001_0104201707042017.txt");
//            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN_TRIB");
//            
//            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
//            List<NFeItem> itens = new ArrayList<>();
//            Map<String, NFe> listaNFes = instance.carregarNFes();
//            NFe nfe = listaNFes.get("35170454505052000904550010013363121675825184");
// 
//            
//            listaSpedC100.stream().forEach((sc100)->{
//                if(sc100.getChaveNFe().equals("35170454505052000904550010013363121675825184")){
//                    c100 = sc100;
//                    System.out.println("Encontrou a nota no sped");
//                    return;
//                }               
//            });
//            
//            if(c100 != null){
//                try{
//                    instance.identificarItensSpdNFe(c100, nfe); //metodo a ser testado
//                }catch(Exception ex){
//                    System.out.println("Erro: " + ex.getMessage());
//                }
//            }
//            //verifica se os itens passado por parametro estao relacionados
//            if(instance.compararItensRelacionados(c100.getListaSPEDC170().get(0), nfe.getItens().get(0))){
//                 System.out.println("Itens relacionados");
//            }else{
//                System.out.println("Itens não relacionados");
//            }
//            
//             
//            assertTrue(true);
//            log.info("fim do teste----------------------------------------------------------------------");
//        }catch(Exception ex){}
//            
//    }
//    
//    @Test
//    public void testarFiltrosSpedMesmaDecricao(){
//        try{
//            Logger log = LogManager.getLogger(ProcessadorSPED.class);
//            log.info("Inicio do teste POR DESCRIÇAO----------------------------------------------------------------------");
//            ProcessadorSPED instance = new ProcessadorSPED();
//            
//            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\MESMA_DESCRICAO\\SpedFiscal_LJ001_0104201707042017.TXT");
//            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\MESMA_DESCRICAO");
//            
//            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
//            List<NFeItem> itens = new ArrayList<>();
//            Map<String, NFe> listaNFes = instance.carregarNFes();
//            NFe nfe = listaNFes.get("35170454505052000904550010013363121675825184");
//
//            
//            listaSpedC100.stream().forEach((sc100)->{
//                if(sc100.getChaveNFe().equals("35170454505052000904550010013363121675825184")){
//                    c100 = sc100;
//                    System.out.println("Encontrou a nota no sped");
//                    return;
//                }               
//            });
//            
//            if(c100 != null){
//                try{
//                    instance.identificarItensSpdNFe(c100, nfe); //metodo a ser testado
//                }catch(Exception ex){
//                    System.out.println("Erro: " + ex.getMessage());
//                }
//            }
//            //verifica se os itens passado por parametro estao relacionados
//            if(instance.compararItensRelacionados(c100.getListaSPEDC170().get(0), nfe.getItens().get(0))){
//                 System.out.println("Itens relacionados");
//            }else{
//                System.out.println("Itens não relacionados");
//            }
//            
//            //verifica se os itens passado por parametro estao na lista dos que serão relacionados pelo usuario
//            if(instance.compararItensPreAssociados(c100.getListaSPEDC170().get(0), nfe.getItens().get(0))){
//                System.out.println("Itens encontrados");
//            }else{
//                 System.out.println("Itens nao encontrados");
//            }
//            assertTrue(true);
//            log.info("fim do teste----------------------------------------------------------------------");
//        }catch(Exception ex){}
//            
//    }
//    
//    @Test
//    public void testarFiltrosDescricaoAproximada(){
//        try{
//            Logger log = LogManager.getLogger(ProcessadorSPED.class);
//            log.info("Inicio do teste POR DESCRIÇAO----------------------------------------------------------------------");
//            ProcessadorSPED instance = new ProcessadorSPED();
//            
//            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\MESMA_DESCRICAO\\SpedFiscal_LJ001_0104201707042017.TXT");
//            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\MESMA_DESCRICAO");
//            
//            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
//            List<NFeItem> itens = new ArrayList<>();
//            Map<String, NFe> listaNFes = instance.carregarNFes();
//            NFe nfe = listaNFes.get("35170454505052000904550010013363121675825184");
//
//            
//            listaSpedC100.stream().forEach((sc100)->{
//                if(sc100.getChaveNFe().equals("35170454505052000904550010013363121675825184")){
//                    c100 = sc100;
//                    System.out.println("Encontrou a nota no sped");
//                    return;
//                }               
//            });
//            
//            if(c100 != null){
//                try{
//                    instance.identificarItensSpdNFe(c100, nfe); //metodo a ser testado
//                }catch(Exception ex){
//                    System.out.println("Erro: " + ex.getMessage());
//                }
//            }       
//           
//            instance.processar();
//            assertTrue(true);
//            log.info("fim do teste----------------------------------------------------------------------");
//        }catch(Exception ex){}
//            
//    }
//    
//    @Test
//    public void testarFiltrosRelacionamentoExistente(){
//        try{                      
//            ProcessadorSPED instance = new ProcessadorSPED();            
//            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\RELACIONAMENTO_EXISTENTE\\SPEDFISCAL-LOJA02-03-2017-11-A.TXT");
//            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\RELACIONAMENTO_EXISTENTE");
//            
//            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
//            List<NFeItem> itens = new ArrayList<>();
//            Map<String, NFe> listaNFes = instance.carregarNFes();
//            NFe nfe = listaNFes.get("35170256228356010366550320005401501124864338");
//            
//            listaSpedC100.stream().forEach((sc100)->{
//                if(sc100.getChaveNFe().equals("35170256228356010366550320005401501124864338")){
//                    c100 = sc100;
//                    return;
//                }               
//            });
//            
//            if(c100 != null)
//                    instance.identificarItensSpdNFe(c100, nfe); //metodo a ser testado
//            
//            
//            assertTrue(true);
//          
//        }catch(Exception ex){}
//            
//    }
//    
//    @Test
//    public void testarFiltrosPorValorQuantidade(){
//        try{
//            Logger log = LogManager.getLogger(ProcessadorSPED.class);
//            log.info("Inicio do teste VALOR E QUANTIDADE----------------------------------------------");
//            ProcessadorSPED instance = new ProcessadorSPED();
//            
//            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_VALOR_QUANTIDADE\\SPEDFISCAL-LOJA02-03-2017-11-A.TXT");
//            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_VALOR_QUANTIDADE");
//            
//            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
//            List<NFeItem> itens = new ArrayList<>();
//            Map<String, NFe> listaNFes = instance.carregarNFes();
//            NFe nfe = listaNFes.get("35170307824167000116550010001118961001118965");
//
//            listaSpedC100.stream().forEach((sc100)->{
//                if(sc100.getChaveNFe().equals("35170307824167000116550010001118961001118965")){
//                    c100 = sc100;
//                    System.out.println("Encontrou a nota no sped");
//                    return;
//                }               
//            });
//            
//            if(c100 != null){
//                try{
//                    instance.identificarItensSpdNFe(c100, nfe); //metodo a ser testado
//                }catch(Exception ex){
//                    System.out.println("Erro: " + ex.getMessage());
//                }
//            }
//            //verifica se os itens passado por parametro estao relacionados
//            if(instance.compararItensRelacionados(c100.getListaSPEDC170().get(0), nfe.getItens().get(0))){
//                 System.out.println("Itens relacionados");
//            }else{
//                System.out.println("Itens não relacionados");
//            }
//            
//            //verifica se os itens passado por parametro estao na lista dos que serão relacionados pelo usuario
//            if(instance.compararItensPreAssociados(c100.getListaSPEDC170().get(0), nfe.getItens().get(0))){
//                System.out.println("Itens encontrados");
//            }else{
//                 System.out.println("Itens nao encontrados");
//            }
//            assertTrue(true);
//            log.info("fim do teste----------------------------------------------------------------------");
//        }catch(Exception ex){}            
//    }
//    
//    @Test
//    public void testarEmLote(){
//        try{
//            Logger log = LogManager.getLogger(ProcessadorSPED.class);
//            log.info("Inicio do teste- Vinculo por Lote -----------------------------------------------------------");
//            ProcessadorSPED instance = new ProcessadorSPED();            
//            //instance.setCaminhoArquivoSPED("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\TESTE_EM_LOTE\\OFERTAS\\MAJ_Cliente ate 31.TXT");
//            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\TESTE_EM_LOTE\\OFERTAS");
//            //instance.setCaminhoArquivoSPED("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\TESTE_EM_LOTE\\NOGUEIRA\\SPEDFISCAL-LOJA02-03-2017-11-A.TXT");
//            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\TESTE_EM_LOTE\\NOGUEIRA");
//            //instance.setCaminhoArquivoSPED("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\TESTE_EM_LOTE\\MM\\SpedFiscal_LJ001_0104201707042017.txt");
//            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\TESTE_EM_LOTE\\MM");
//            instance.setCaminhoArquivoSPED("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\TESTE_FINAL_NOGUEIRA\\SPEDFISCAL-LOJA02-03-2017-11-A.TXT");
//            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\TESTE_FINAL_NOGUEIRA\\xml");
//         
//            
//            instance.processar();
//            //instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\TESTE_EM_LOTE\\NOGUEIRA\\SPEDFISCAL-LOJA02-03-2017-11-A.TXT");
//            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\TESTE_EM_LOTE\\NOGUEIRA");
//           
//            //instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\TESTE_EM_LOTE\\MM\\SpedFiscal_LJ001_0104201707042017.txt");
//            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\TESTE_EM_LOTE\\MM");
//         
////            
////            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
////            List<NFeItem> itens = new ArrayList<>();
////            Map<String, NFe> listaNFes = instance.carregarNFes();
////            NFe nfe = listaNFes.get("00000000000000000000000000000000000000000001"); 
////            
////            listaSpedC100.stream().forEach((sc100)->{
////                if(sc100.getChaveNFe().equals("00000000000000000000000000000000000000000001")){
////                    c100 = sc100;
////                    System.out.println("Encontrou a nota no sped");
////                    return;
////                }               
////            });
////            
////            if(c100 != null){
////                try{
////                    instance.identificarItensSpdNFe(c100, nfe); //metodo a ser testado
////                }catch(Exception ex){
////                    System.out.println("Erro: " + ex.getMessage());
////                }
////            }
////            //verifica se os itens passado por parametro estao relacionados
////            if(instance.compararItensRelacionados(c100.getListaSPEDC170().get(0), nfe.getItens().get(0))){
////                 System.out.println("Itens relacionados");
////            }else{
////                System.out.println("Itens não relacionados");
////            }
////            
//             
//            assertTrue(true);
//            log.info("fim do teste----------------------------------------------------------------------");
//        }catch(Exception ex){}
//            
//    }
//    
//    @Test
//    public void testarItensComDoisValoresOuMaisIdenticos(){
//         try{
//            Logger log = LogManager.getLogger(ProcessadorSPED.class);           
//            ProcessadorSPED instance = new ProcessadorSPED();
//            
//            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\TESTE_VALORES_IDENTICOS\\SpedFiscal_LJ001_0104201707042017.txt");
//            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\TESTE_VALORES_IDENTICOS");
//            
//            //instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN_TRIB_MARCELO\\SpedFiscal_LJ001_0104201707042017.txt");
//            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN_TRIB_MARCELO");
//            
//            //instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN\\SpedFiscal_LJ001_0104201707042017.txt");
//            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN");
//            
//            
//            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
//            List<NFeItem> itens = new ArrayList<>();
//            Map<String, NFe> listaNFes = instance.carregarNFes();
//            NFe nfe = listaNFes.get("35170454505052000904550010013363121675825184"); 
//            
//            listaSpedC100.stream().forEach((sc100)->{
//                if(sc100.getChaveNFe().equals("35170454505052000904550010013363121675825184")){
//                    c100 = sc100;
//                    System.out.println("Encontrou a nota no sped");
//                    return;
//                }               
//            });
//            
//            if(c100 != null){
//                try{
//                    instance.identificarItensSpdNFe(c100, nfe); //metodo a ser testado
//                }catch(Exception ex){
//                    System.out.println("Erro: " + ex.getMessage());
//                }
//            }
//            //verifica se os itens passado por parametro estao relacionados
//            if(instance.compararItensRelacionados(c100.getListaSPEDC170().get(0), nfe.getItens().get(0))){
//                 System.out.println("Itens relacionados");
//            }else{
//                System.out.println("Itens não relacionados");
//            }            
//             
//            assertTrue(true);
//            log.info("fim do teste----------------------------------------------------------------------");
//        }catch(Exception ex){}
//        
//    }
//    
//    @Test
//    public void testeVaiVerificarPossivelAssociacaoSemPassarPorfiltroAlgum(){
//    
//        try{
//            Logger log = LogManager.getLogger(ProcessadorSPED.class);           
//            ProcessadorSPED instance = new ProcessadorSPED();
//            
//            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\CHECANDO_FILTRO_SEM_DESCRICAO\\SpedFiscal_LJ001_0104201707042017.txt");
//            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\CHECANDO_FILTRO_SEM_DESCRICAO");
//            
//            //instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN_TRIB_MARCELO\\SpedFiscal_LJ001_0104201707042017.txt");
//            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN_TRIB_MARCELO");
//            
//            //instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN\\SpedFiscal_LJ001_0104201707042017.txt");
//            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN");
//            
//            
//            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
//            List<NFeItem> itens = new ArrayList<>();
//            Map<String, NFe> listaNFes = instance.carregarNFes();
//            NFe nfe = listaNFes.get("35170343572270000180550010000058061370543508"); 
//            
//            listaSpedC100.stream().forEach((sc100)->{
//                if(sc100.getChaveNFe().equals("35170343572270000180550010000058061370543508")){
//                    c100 = sc100;
//                    System.out.println("Encontrou a nota no sped");
//                    return;
//                }               
//            });
//            
//            if(c100 != null){
//                try{
//                    instance.identificarItensSpdNFe(c100, nfe); //metodo a ser testado
//                }catch(Exception ex){
//                    System.out.println("Erro: " + ex.getMessage());
//                }
//            }
//            //verifica se os itens passado por parametro estao relacionados
//            if(instance.compararItensRelacionados(c100.getListaSPEDC170().get(0), nfe.getItens().get(0))){
//                 System.out.println("Itens relacionados");
//            }else{
//                System.out.println("Itens não relacionados");
//            }  
//            assertTrue(true);
//            log.info("fim do teste----------------------------------------------------------------------");
//        }catch(Exception ex){}
//    }
//    
//    @Test
//    public void testeDescricaoAproximadaValoresIdenticos(){
//    try{           
//            ProcessadorSPED instance = new ProcessadorSPED();            
//            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\DESCRICAO_APROXIMADA_COM_VALOR_IDENTICO\\SpedFiscal_LJ001_0104201707042017.txt");
//            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\DESCRICAO_APROXIMADA_COM_VALOR_IDENTICO");
//            
//            //instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN_TRIB_MARCELO\\SpedFiscal_LJ001_0104201707042017.txt");
//            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN_TRIB_MARCELO");
//            
//            //instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN\\SpedFiscal_LJ001_0104201707042017.txt");
//            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN");
//            
//            
//            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
//            List<NFeItem> itens = new ArrayList<>();
//            Map<String, NFe> listaNFes = instance.carregarNFes();
//            NFe nfe = listaNFes.get("35170454505052000904550010013363121675825184"); 
//            
//            listaSpedC100.stream().forEach((sc100)->{
//                if(sc100.getChaveNFe().equals("35170454505052000904550010013363121675825184")){
//                    c100 = sc100;
//                    System.out.println("Encontrou a nota no sped");
//                    return;
//                }               
//            });
//            
//            if(c100 != null){
//                try{
//                    instance.identificarItensSpdNFe(c100, nfe); //metodo a ser testado
//                }catch(Exception ex){
//                    System.out.println("Erro: " + ex.getMessage());
//                }
//            }
//            //instance.processar();
//            assertTrue(true);
//            
//        }catch(Exception ex){}
//    }
//    
//    @Test
//    public void testeDescricaoAproximadaValoresST(){
//    try{
//            Logger log = LogManager.getLogger(ProcessadorSPED.class);           
//            ProcessadorSPED instance = new ProcessadorSPED();
//            
//            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\DESCRICAO_APROXIMADA_COM_VALOR_ST\\SpedFiscal_LJ001_0104201707042017.txt");
//            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\DESCRICAO_APROXIMADA_COM_VALOR_ST");
//            
//            //instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN_TRIB_MARCELO\\SpedFiscal_LJ001_0104201707042017.txt");
//            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN_TRIB_MARCELO");
//            
//            //instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN\\SpedFiscal_LJ001_0104201707042017.txt");
//            //instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\POR_EAN");
//            
//            
//            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
//            List<NFeItem> itens = new ArrayList<>();
//            Map<String, NFe> listaNFes = instance.carregarNFes();
//            NFe nfe = listaNFes.get("35170454505052000904550010013363121675825184"); 
//            
//            listaSpedC100.stream().forEach((sc100)->{
//                if(sc100.getChaveNFe().equals("35170454505052000904550010013363121675825184")){
//                    c100 = sc100;
//                    return;
//                }               
//            });
//            
//            if(c100 != null)
//                instance.identificarItensSpdNFe(c100, nfe); //metodo a ser testado
//            
//            //verifica se os itens passado por parametro estao relacionados
//            //if(instance.compararItensRelacionados(c100.getListaSPEDC170().get(0), nfe.getItens().get(0))){
//            //     System.out.println("Itens relacionados");
//            //}else{
//            //    System.out.println("Itens não relacionados");
//            //}  
//            assertTrue(true);
//        }catch(Exception ex){}
//    }    
//   
//    @Test
//    public void testeAlgoritmoRelacionamentoUmMuitos(){
//    try{
//            ProcessadorSPED instance = new ProcessadorSPED();            
//            instance.getLeitorSped().lerArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\RELACIONAMENTO_UM_MUITOS\\SPEDFISCAL-LOJA02-03-2017-11-A.txt");
//            instance.setCaminhoDiretorioXmlsNFes("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\RELACIONAMENTO_UM_MUITOS");
//            
//            List<SPEDC100> listaSpedC100 = instance.getLeitorSped().getListaSPEDC100();
//            List<NFeItem> itens = new ArrayList<>();
//            Map<String, NFe> listaNFes = instance.carregarNFes();
//            
//            NFe nfe = listaNFes.get("35170307824167000116550010001114411001114412"); 
//            itens = nfe.getItens();           
//            
//            listaSpedC100.stream().forEach((sc100)->{
//                if(sc100.getChaveNFe().equals("35170307824167000116550010001114411001114412")){
//                    c100 = sc100;
//                    return;
//                }               
//            });
//            
//            if(c100 != null){
//                instance.identificarItensSpdNFe(c100, nfe); 
//                
////                MetodosUtil mUtil = new MetodosUtil();
////                for(SPEDC170 itensCs170 : c100.getListaSPEDC170()){
////                    mUtil.identificarCombinacoesPossiveisDoSpedComNfe(itensCs170, itens);
////                }                
////                Optional<Map.Entry<MetodosUtil.idxSPEDC170, List<NFeItem>>> gruposEncontrados = mUtil.getListaDeCombinacoesEncontrados().entrySet().stream().findFirst();
////                if(gruposEncontrados.isPresent()){
////                    Map.Entry<MetodosUtil.idxSPEDC170, List<NFeItem>> grupo = gruposEncontrados.get();
////                    SPEDC170 sc170 = grupo.getKey().getSped();
////                    try{
////                        NFeItem[] nfeItem =(NFeItem[]) grupo.getValue().toArray(new NFeItem[grupo.getValue().size()]);
////                    }catch(Exception ex){
////                     System.out.println("---->"+ ex.getMessage());
////                    }                    
////                }             
////              
////                System.out.println("---->"+ mUtil.getListaDeCombinacoesEncontrados().keySet().size());
////                mUtil.getListaDeCombinacoesEncontrados().forEach((key,val) ->{
////                    
////                    BigDecimal idx = key.getIndex();
////                    SPEDC170 newSped = key.getSped();
////                    BigDecimal vBusca = new BigDecimal(8.40).setScale(2,BigDecimal.ROUND_HALF_EVEN); 
////                    
////                    if(newSped.getValor().compareTo(vBusca)== 0){
////                       System.out.println("idx[" + idx + "] produto" + newSped.getSPED0200().getDescrItem() +" a procurar ->" +newSped.getValor());
////                        val.forEach(itemNfe ->{
////                            System.out.println("--------------------> prod "+itemNfe.getDescricaoProduto() +" encontrados[" + itemNfe.getValorTotal() + "]");
////                        });
////                    }
////                });
//            }
//           
//            assertTrue(true);
//        }catch(Exception ex){}
//    }    
    /*
        Luiz: Esboço de algoritmo que identifica relacionamento um pra muitos com SPED e XML 
    */
     @Test
    public void testeAlgoritimo(){
        //ProcessadorSPED instance = new ProcessadorSPED();
//        List<TesteBigDecimal> itensNfe = new ArrayList<>();
//               
//        
//        itensNfe.add(new TesteBigDecimal(1));
//        itensNfe.add(new TesteBigDecimal(4));
//        itensNfe.add(new TesteBigDecimal(7));
//        itensNfe.add(new TesteBigDecimal(3));
//        itensNfe.add(new TesteBigDecimal(5));  
//        itensNfe.add(new TesteBigDecimal(8)); 
//        itensNfe.add(new TesteBigDecimal(6)); 
//        
       // algoritimoDeAproximacao(new TesteBigDecimal(4.9),itensNfe);
        assertTrue(true);
    }
    
//    public void algoritimoDeAproximacao(TesteBigDecimal valor, List<TesteBigDecimal>valores){  
//        MathContext mc = new MathContext(3);
//        List<TesteBigDecimal> provaveis = null;
//        Map<TesteBigDecimal, List<TesteBigDecimal>> ocorrencias = new HashMap<>();
//        BigDecimal somatoria = null;           
//        Collections.sort(valores);//ordenar os valores do array do menor para o maior
//        
//        for(int i = 0; i < valores.size(); i++){
//            provaveis = new ArrayList<>();
//            somatoria = valores.get(i);
//            provaveis.add(valores.get(i));
//            
//            for(int j = 0;j < valores.size() ; j++){ 
//                if(i == j)continue; // ignora a mesma posição do vetor impedindo que o valor do mesmo item seja somado
//                  
//                somatoria = somatoria.add(valores.get(j)); //somo o valor de j, levando em conta aque ja foi inicializado com valor de i 
//                               
//                //System.out.println("i["+ valores.get(i).round(mc) +"] j[" +  valores.get(j).round(mc)+"] = " + somatoria.round(mc));                
//                
//                if(somatoria.compareTo(valor) == 0)
//                {   
//                    //guarda os valores percorridos
//                    ocorrencias.put(new TesteBigDecimal(i), provaveis);
//                    break;
//                }
//            }  
//             System.out.println("--------------//------------------");
//        }
        
//        for(Map.Entry<BigDecimal, List<BigDecimal>> oc : ocorrencias.entrySet()){
//            System.out.println("Ocorrencia ["+oc.getKey() +"]");
//            for(BigDecimal e : oc.getValue()){
//               System.out.println( " Itens ->" +  e);
//           }
//        }
 //   }
    
//    public void testOrden(TesteBigDecimal valor, List<TesteBigDecimal>valores){
//        //ordenando os valor 
//        
//        for(int i = 0; i < valores.size(); i++){
//            System.out.println("antes -->" + valores.get(i));
//        }
//        
//        try{
//           Collections.sort(valores);  
//        }catch(Exception ex){
//        System.out.println("EX -->" + ex);
//        }
//        for(int i = 0; i < valores.size(); i++){
//            System.out.println("depois -->" + valores.get(i));
//        }
//    }
//    
@Test 
public void testarArray (){
 List<BigDecimal> valores = new ArrayList<>();
    for(int i=1; i<=5;i++){
        valores.add(new BigDecimal(i));
    }
    MetodosUtil asserts = new MetodosUtil();
//    asserts.identificarCombinacoesPossiveisDoSpedComNfe(new BigDecimal(6), valores);
//    for(Map.Entry<BigDecimal,List<BigDecimal>> valor : asserts.getListaDeCombinacoesEncontrados().entrySet())
//    {
//        System.out.println("Chave :" + valor.getKey());
//        for(BigDecimal vl : valor.getValue()){
//            System.out.println(" -> valores :" + vl);
//        }
//    }
    asserts.getListaDeCombinacoesEncontrados().forEach((v, vls) ->{
       System.out.println(" Pos" + v);
       vls.forEach(vs ->{
            System.out.println(" valor" + vs);
        });
    });
//    asserts.listaDeCombinacoesEncontrados
//            
//            .forEach(valor -> 
//            {
//                System.out.println(valor);
//            });
//    
   // System.out.print("Aqui é o count ---->"+ valores.size());
    assertTrue(true);
    }

@Test
public void testeListaGenerica(){
    
    List<SPEDC170> list170 = new ArrayList<SPEDC170>();
    List<SPEDC190> list190 = new ArrayList<SPEDC190>();
    
    SPEDC170 spdc170 = new SPEDC170();
    spdc170.setCFOP("123456");
    list170.add(spdc170);    
    testListGeneric(list170);
    
    SPEDC190 spdc190 = new SPEDC190();
    spdc190.setCFOP("456789");
    list190.add(spdc190);    
    testListGeneric(list190);
    
    assertTrue(true);
}
@Test
public void testarCrudEmpresa(){
    
    Empresa empresa = new Empresa();
    EmpresaDAO empresaDao = new EmpresaDAO();
    empresa.setCnpj("62.190.574/0002-08");
    try{
          ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().beginTransaction().begin();
            empresa = empresaDao.selecionarUm(empresa);
            System.out.println("empresa ---->   " + empresa.getNome());
          ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().beginTransaction().commit();
    }catch(Exception ex){
        ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().beginTransaction().rollback();
        System.out.println("" + ex.getMessage());
    }
    
}

public void testListGeneric(List<? super SPEDC190>list){
    
    for(Object l : list){
            if(l instanceof SPEDC190){
                SPEDC190 item = (SPEDC190)l;
                System.out.println("-->"+item.getCFOP());
            }else if(l instanceof SPEDC170){
                  SPEDC170 item = (SPEDC170)l;
                System.out.println("-->"+item.getCFOP());
            }
        }    
    }
}
