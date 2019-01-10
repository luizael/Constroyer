package br.com.mixfiscal.prodspedxnfe.services.relatorio;

import br.com.mixfiscal.prodspedxnfe.dao.own.ClassificacaoTributariaDAO;
import br.com.mixfiscal.prodspedxnfe.dao.own.ClassificacaoTributariaProdutoDAO;
import br.com.mixfiscal.prodspedxnfe.dao.own.IcmsEntradaDAO;
import br.com.mixfiscal.prodspedxnfe.dao.own.IcmsSaidaDAO;
import br.com.mixfiscal.prodspedxnfe.dao.own.RequisicaoAtualizacaoInfoFiscalDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;


import br.com.mixfiscal.prodspedxnfe.domain.own.CFOP;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOPDePara;
import br.com.mixfiscal.prodspedxnfe.domain.own.CST;
import br.com.mixfiscal.prodspedxnfe.domain.own.CSTDePara;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributaria;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributariaProduto;
import br.com.mixfiscal.prodspedxnfe.domain.own.Cliente;
import br.com.mixfiscal.prodspedxnfe.domain.own.Empresa;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsEntrada;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsSaida;
import br.com.mixfiscal.prodspedxnfe.domain.own.PisCofins;
import br.com.mixfiscal.prodspedxnfe.domain.own.Produto;
import br.com.mixfiscal.prodspedxnfe.domain.own.RelacaoProdutoFornecedor;
import br.com.mixfiscal.prodspedxnfe.domain.own.RequisicaoAtualizacaoInfoFiscal;
import br.com.mixfiscal.prodspedxnfe.domain.own.Usuario;
import br.com.mixfiscal.prodspedxnfe.domain.own.custom.ClassificacaoTributariaProdutoCustom;
import br.com.mixfiscal.prodspedxnfe.domain.own.custom.IcmsEntradaCustom;
import br.com.mixfiscal.prodspedxnfe.domain.own.custom.IcmsSaidaCustom;
import br.com.mixfiscal.prodspedxnfe.domain.questor.EmpresaQuestor;
import br.com.mixfiscal.prodspedxnfe.domain.questor.Estabelecimento;
import br.com.mixfiscal.prodspedxnfe.domain.questor.FatorConversaoUnidade;
import br.com.mixfiscal.prodspedxnfe.domain.questor.Municipio;
import br.com.mixfiscal.prodspedxnfe.domain.questor.Pessoa;
import br.com.mixfiscal.prodspedxnfe.domain.questor.ProdutoQuestor;
import br.com.mixfiscal.prodspedxnfe.domain.questor.RelacProdutoFornecedor;
import br.com.mixfiscal.prodspedxnfe.domain.questor.UnidadeMedida;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPED0000;
import br.com.mixfiscal.prodspedxnfe.services.ClassificacaoTributariaService;
import br.com.mixfiscal.prodspedxnfe.services.ex.ServiceException;
import br.com.mixfiscal.prodspedxnfe.services.relatorio.RelatorioDebitoCredito.Fornecedor;
import br.com.mixfiscal.prodspedxnfe.services.relatorio.ImportarExcelParaAtualizarBase;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.EntityMode;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class RelatorioDebitoCreditoTest {
    private static Session sessao;
    
    public RelatorioDebitoCreditoTest() {}
    
    @BeforeClass
    public static void setUpClass() {}
    
    @AfterClass
    public static void tearDownClass() {}
    
    @Before
    public void setUp() {}
    
    @After
    public void tearDown() {}

    
    @Test
    public void testGetCaminhoArquivoSped() {
        System.out.println("getCaminhoArquivoSped");
        RelatorioDebitoCredito instance = new RelatorioDebitoCredito();
        String expResult = "";
        String result = instance.getCaminhoArquivoSped();
        assertEquals(expResult, result);       
    }
    
    @Test
    public void testSetCaminhoArquivoSped() throws Exception {
        System.out.println("setCaminhoArquivoSped");
        String caminhoArquivoSped = "";
        RelatorioDebitoCredito instance = new RelatorioDebitoCredito();
        instance.setCaminhoArquivoSped(caminhoArquivoSped);
    }
    
    @Test
    public void testGetCaminhoDirXMLsNFes() {
        System.out.println("getCaminhoDirXMLsNFes");
        RelatorioDebitoCredito instance = new RelatorioDebitoCredito();
        String expResult = "";
        String result = instance.getCaminhoDirXMLsNFes();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSetCaminhoDirXMLsNFes() {
        System.out.println("setCaminhoDirXMLsNFes");
        String caminhoDirXMLsNFes = "";
        RelatorioDebitoCredito instance = new RelatorioDebitoCredito();
        instance.setCaminhoDirXMLsNFes(caminhoDirXMLsNFes);
    }
    
    @Test
    public void testRetornarRelatorioCreditoIndevido() throws Exception {
        System.out.println("retornarRelatorioCreditoIndevido");
        String caminhoSped = "";
        RelatorioDebitoCredito instance = new RelatorioDebitoCredito();        
        
        instance.setCaminhoArquivoSped("D:\\LULU SOFTWARES\\MIX_FISCAL\\JUNIT_TESTE\\SpedFiscal_LJ001_0104201707042017.txt");                
        List<Fornecedor> result = instance.retornarRelatorioCreditoIndevido();
    }
    
    @Test
    public void testArredondamento(){
        DecimalFormat decimal2casas = new DecimalFormat("0.0");
        BigDecimal bg = new BigDecimal(18.500);
        System.out.println("--->"+ new BigDecimal(decimal2casas.format(bg).replace(",",".")));
    }
    
    @Test
    public void testarAtualizarRegistros(){
        
    }
    @Test
    public void testarInsercaoNoBancoDedados() throws Exception {
        String line = "";
        ClassificacaoTributaria clTributaria = null;
        try{  
           sessao = ConstroyerHibernateUtil.getSessionFactory().openSession();
        }catch(Exception  ex){ 
            ex.getMessage();
        }      
        ClassificacaoTributariaDAO clTribDao = new ClassificacaoTributariaDAO(sessao);
        ClassificacaoTributariaProdutoDAO prodDao = new ClassificacaoTributariaProdutoDAO(sessao);
        IcmsEntradaDAO icmEntradaDao = new IcmsEntradaDAO(sessao);
        IcmsSaidaDAO icmsSaidaDao = new IcmsSaidaDAO(sessao);
        int cont = 0;
        try {
            sessao.beginTransaction().begin();
            String caminho_arquivo_xls = "D:\\LULU SOFTWARES\\MIX_FISCAL\\ProdSPEDxNFe\\DOCUMENTOS_PARA_TESTE\\CASTELI\\produtos_mxf.csv";
            BufferedReader br = new BufferedReader(new FileReader(caminho_arquivo_xls));
            while ((line = br.readLine()) != null) {
                cont++;
                if (cont > 2) {
                    clTributaria = new ClassificacaoTributaria();
                    String[] row = line.split(";");
                    
                    Cliente cliente = new Cliente();
                    cliente.setCnpj("05587850000151");
                    
                    clTributaria.setCliente(cliente);
                    carregarObjetoDaImportacaoDoExelCompleto(clTributaria, row,null,"");
                    //try{
                    clTribDao.salvar(clTributaria);
                    prodDao.salvar(clTributaria.getProduto());
                    icmEntradaDao.salvar(clTributaria.getProduto().getIcmsEntrada());
                    icmsSaidaDao.salvar(clTributaria.getProduto().getIcmsSaida());
                    System.out.println("--------------------------");
                    //}catch(Exception ex){
                    //    System.out.println(ex.getMessage());
                    //}
                }
            }
            sessao.getTransaction().commit();
        } catch (FileNotFoundException fe) {
            sessao.getTransaction().rollback();
            System.out.println(fe.getMessage());
        } catch (IOException Ie) {
            sessao.getTransaction().rollback();
            System.out.println(Ie.getMessage());
        } catch (NumberFormatException Ne) {
            sessao.getTransaction().rollback();
            System.out.println(Ne.getMessage());
        } catch (Exception e) {
            sessao.getTransaction().rollback();
            System.out.println("Erro ao ler e gravar" + e.getMessage() + "" + clTribDao);
        } finally {
            sessao.close();
        }
    }
    private void carregarObjetoDaImportacaoDoExelCompleto(ClassificacaoTributaria clTrib, String[] row, SPED0000 sped0000,
        String formatarCodigoInterno){
        String codigoProduto = "campo vazio";
        ClassificacaoTributariaProduto produto = new ClassificacaoTributariaProduto();
        IcmsEntrada icmsEntrada = new IcmsEntrada();
        IcmsSaida icmsSaida = new IcmsSaida();
        PisCofins pisCofins = new PisCofins();
        
        try{
            codigoProduto = row[0].equals(null) ? codigoProduto : row[0];  
            produto.setCodigoProduto(codigoProduto);
            //produto.setCnpjCliente(clTrib.getCnpjTrib());
            produto.setEan(StringUtil.isNullOrEmpty(row[1]) ? "campo vazio" : row[1]);
            produto.setDescricao(row[2].isEmpty() ? "campo vazio" : row[2]);
            
            if(row.length >= 18){
                pisCofins.setNcm(row[9].isEmpty() ? "campo vazio" : row[9]);
                pisCofins.setNcmEx(row[10].isEmpty() ? "campo vazio" : row[10]);
                pisCofins.setCodNaturezaReceita(row[11].isEmpty() ? "campo vazio" : row[11]);
                pisCofins.setPisCstE(row[12].isEmpty() ? "campo vazio" : row[12]);
                pisCofins.setPisCstS(row[13].isEmpty() ? "campo vazio" : row[13]);
                pisCofins.setPisAlqE(row[14].isEmpty() ? "campo vazio" : row[14]);
                pisCofins.setPisAlqS(row[15].isEmpty() ? "campo vazio" : row[15]);
                pisCofins.setCofinsAlqE(row[16].isEmpty() ? "campo vazio" : row[16]);
                pisCofins.setCofinsAlqS(row[17].isEmpty() ? "campo vazio" : row[17]);
                //pisCofins.setCnpjCliente(produto.getCnpjCliente());
                pisCofins.setProduto(produto);
            }
            if(row.length >= 41){
                icmsSaida.setSacCst(row[19].isEmpty() ? "campo vazio" : row[19]);
                icmsSaida.setSacAlq(row[20].isEmpty() ? "campo vazio" : row[20]);
                icmsSaida.setSacAlqst(row[21].isEmpty() ? "campo vazio" : row[21]);
                icmsSaida.setSacRbc(row[22].isEmpty() ? "campo vazio" : row[22]);
                icmsSaida.setSacRbcst(row[23].isEmpty() ? "campo vazio" : row[23]);
                icmsSaida.setSasCst(row[24].isEmpty() ? "campo vazio" : row[24]);
                icmsSaida.setSasAlq(row[25].isEmpty() ? "campo vazio" : row[25]);
                icmsSaida.setSasAlqst(row[26].isEmpty() ? "campo vazio" : row[26]);
                icmsSaida.setSasRbc(row[27].isEmpty() ? "campo vazio" : row[27]);
                icmsSaida.setSasRbcst(row[28].isEmpty() ? "campo vazio" : row[28]);
                icmsSaida.setSvcCst(row[29].isEmpty() ? "campo vazio" : row[29]);
                icmsSaida.setSvcAlq(row[30].isEmpty() ? "campo vazio" : row[30]);
                icmsSaida.setSvcAlqst(row[31].isEmpty() ? "campo vazio" : row[31]);
                icmsSaida.setSvcRbc(row[32].isEmpty() ? "campo vazio" : row[32]);
                icmsSaida.setSvcRbcst(row[33].isEmpty() ? "campo vazio" : row[33]);
                icmsSaida.setSncCst(row[34].isEmpty() ? "campo vazio" : row[34].toString());
                icmsSaida.setSncAlq(row[35].isEmpty() ? "campo vazio" : row[35].toString());
                icmsSaida.setSncAlqst(row[36].isEmpty() ? "campo vazio" : row[36].toString());
                icmsSaida.setSncRbc(row[37].isEmpty() ? "campo vazio" : row[37].toString());
                icmsSaida.setSncRbcst(row[38].isEmpty() ? "campo vazio" : row[38].toString());
                icmsSaida.setFundamentoLegal(row[40].isEmpty() ? "campo vazio" : row[40].toString());
                //icmsSaida.setCnjpCliente(produto.getCnpjCliente());
                icmsSaida.setProduto(produto);
            }
            if(row.length >= 66){                
                icmsEntrada.setEiCst(row[41].isEmpty() ? "campo vazio" : row[41].toString());
                icmsEntrada.setEiAlq(row[42].isEmpty() ? "campo vazio" : row[42].toString());
                icmsEntrada.setEiAlqst(row[43].isEmpty() ? "campo vazio" : row[43].toString());
                icmsEntrada.setEiRbc(row[44].isEmpty() ? "campo vazio" : row[44].toString());
                icmsEntrada.setEiRbcst(row[45].isEmpty() ? "campo vazio" : row[45].toString());
                icmsEntrada.setEdCst(row[46].isEmpty() ? "campo vazio" : row[46].toString());
                icmsEntrada.setEdAlq(row[47].isEmpty() ? "campo vazio" : row[47].toString());
                icmsEntrada.setEdAlqst(row[48].isEmpty() ? "campo vazio" : row[48].toString());
                icmsEntrada.setEdRbc(row[49].isEmpty() ? "campo vazio" : row[49].toString());
                icmsEntrada.setEdRbcst(row[50].isEmpty() ? "campo vazio" : row[50].toString());
                icmsEntrada.setEsCst(row[51].isEmpty() ? "campo vazio" : row[51].toString());
                icmsEntrada.setEsAlq(row[52].isEmpty() ? "campo vazio" : row[52].toString());
                icmsEntrada.setEsAlqst(row[53].isEmpty() ? "campo vazio" : row[53].toString());
                icmsEntrada.setEsRbc(row[54].isEmpty() ? "campo vazio" : row[54].toString());
                icmsEntrada.setEsRbcst(row[55].isEmpty() ? "campo vazio" : row[55].toString());
                icmsEntrada.setNfiCst(row[56].isEmpty() ? "campo vazio" : row[56].toString());
                icmsEntrada.setNfdCst(row[57].isEmpty() ? "campo vazio" : row[57].toString());
                icmsEntrada.setNfsCsosn(row[58].isEmpty() ? "campo vazio" : row[58].toString());
                icmsEntrada.setNfAlq(row[59].isEmpty() ? "campo vazio" : row[59].toString());
                icmsEntrada.setFundamentoLegal(row[39].isEmpty() ? "campo vazio" : row[39].toString());
                icmsEntrada.setTipoMva(row[60].isEmpty() ? "campo vazio" : row[60].toString());
                icmsEntrada.setMva(row[61].isEmpty() ? "campo vazio" : row[61].toString());
                icmsEntrada.setMvaDataIni(row[63].isEmpty() ? "campo vazio" : row[63].toString());
                icmsEntrada.setMvaDataFim(row[64].isEmpty() ? "campo vazio" : row[64].toString());
                icmsEntrada.setCreditoOutorgado(row[65].isEmpty() ? "campo vazio" : row[65].toString());
                //icmsEntrada.setCnpjCliente(produto.getCnpjCliente());
            }
            
            produto.setPisCofins(pisCofins);            
            pisCofins.setProduto(produto);
            
            produto.setIcmsEntrada(icmsEntrada);
            icmsEntrada.setProduto(produto);
            
            produto.setIcmsSaida(icmsSaida);
            icmsSaida.setProduto(produto);
            
            produto.setClassificacaoTributaria(clTrib);
            clTrib.setProduto(produto);
        } catch (Exception ex) {           
             System.out.println(ex);
        }        
    }
    
    @Test
    public void testarBuscaMixFiscal(){ 
       /* ClassificacaoTributariaProdutoDAO produtoDao = new ClassificacaoTributariaProdutoDAO(sessao);
        try{  
           sessao = ConstroyerHibernateUtil.getSessionFactory().openSession();
        }catch(Exception  ex){ 
            ex.getMessage();
        }      
        try {
            sessao.beginTransaction().begin();
                ClassificacaoTributariaProduto prod = new ClassificacaoTributariaProduto();
                prod.setCodigoProduto("30388");
                prod.setCnpjCliente("05587850000151");
                prod = produtoDao.selecionarUmProduto(prod,sessao);
                System.out.println("PRODUTO: "+ prod.getDescricao() + ", EAN:" + prod.getEan());
                System.out.println("imposto");
                if(prod.getIcmsEntrada()!= null){
                    System.out.println("ICMS ENTRADA cnpj:"+ prod.getIcmsEntrada().getCnpjCliente());
                }
                if(prod.getIcmsSaida() != null){
                    System.out.println("ICMS SAIDA cnpj:" + prod.getIcmsSaida().getCnpjCliente());
                }
            sessao.getTransaction().commit();       
        } catch (Exception e) {
            System.out.println("" +e.getMessage());
            sessao.getTransaction().rollback();
        } finally {
            sessao.close();
        }*/
    }
    
    @Test
    public void testarMetodoPercorrerPlanoExcelParaSalvarNoBD(){
         try{  
           sessao = ConstroyerHibernateUtil.getSessionFactory().openSession();
        }catch(Exception  ex){ 
            ex.getMessage();
        }
        String path ="C:\\Constroyer\\05-12-2017-SO OFERTAS BUZIOS SUPERMERCADOS LTDA-07970113000169-teste.csv";
        ImportarExcelParaAtualizarBase excelParaDb = new ImportarExcelParaAtualizarBase();
        RequisicaoAtualizacaoInfoFiscalDAO reqsicaoDao = new RequisicaoAtualizacaoInfoFiscalDAO(sessao);
        List<RequisicaoAtualizacaoInfoFiscal> requisicoes = new ArrayList<>();
        
        try{
            requisicoes = reqsicaoDao.retornarProximosDaFila();
            RequisicaoAtualizacaoInfoFiscal requisicao = requisicoes.get(0);
            /*for(RequisicaoAtualizacaoInfoFiscal requisicao : requisicoes){
                System.out.println("id-->"+requisicao.getIdRequisicaoAtualizacaoInfoFiscal());
                System.out.println("arquivo->"+requisicao.getNomeArquivo());
                System.out.println("formatar codigo-->"+requisicao.getFormatarCodigo());
                System.out.println("Status-->"+requisicao.getStatusRequisicao());
                System.out.println("id cliente-->"+requisicao.getCliente().getId());                
                System.out.println("nome do cliente-->"+requisicao.getCliente().getNome());
                System.out.println("cnpj do cliente-->"+requisicao.getCliente().getCnpj());
                requisicao.getCliente().getClassificacaoTributaria().forEach(cl ->{
                    System.out.println(" produto --> "+ cl.getProduto().getDescricao());
                });
                ClassificacaoTributariaDAO cl = new ClassificacaoTributariaDAO(sessao);
                excelParaDb.percorrerPlanoExcelParaSalvarNoBD(path, requisicao);
            }*/
            
        int cont = 0;
        String line;        
        BufferedReader br = null;
        ClassificacaoTributariaService classTribService = new ClassificacaoTributariaService();
        ClassificacaoTributaria objCT = null;        
        try {
           
            br = new BufferedReader(new FileReader(path));           
            while ((line = br.readLine()) != null) {
                cont++;
                try {
                    if (cont >= 2) {
                        // "," ou ";" de acordo com o arquivo
                        String[] row = line.split(";");
                        if (row.length == 0) {
                            continue;
                        }                        

                        objCT = classTribService.selecionarPorCNPJClienteECodigoProduto(requisicao.getCliente().getCnpj(), row[0]);
                        if (objCT == null || objCT.getId() == null) {
                            objCT = new ClassificacaoTributaria();                         
                            objCT.setCliente(requisicao.getCliente());                        
                        }
                        
                        // cria meu objeto que recebe as linhas
                        excelParaDb.carregarObjetoDaImportacaoDoExelCompleto(objCT, row, requisicao.getFormatarCodigo());

                        try {
                            classTribService.salvar(objCT);
                            //salvarClassificacaoTributaria(objCT);
                           
                        } catch (Exception ex) {
                            System.out.println(""+ ex.getMessage());
                        }
                    }
                } catch (Exception ex) {
                   System.out.println(""+ ex.getMessage());
                }
            }
        } catch (FileNotFoundException fe) {
            //this.log.error(fe.getStackTrace(), fe);
            throw new ServiceException(fe);           
        } catch (IOException Ie) {
            ///this.log.error(Ie.getMessage(), Ie);
            throw new ServiceException(Ie);
        } catch (NumberFormatException Ne) {
            //this.log.error("erro na formatação de numero " + Ne.getMessage(), Ne);
            throw new ServiceException(Ne);
        } catch (Exception e) {
            //this.log.error("Erro ao ler e gravar" + e.getMessage() + "" + objCT, e);
            throw new ServiceException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception ex) {
                    System.out.println(""+ ex.getMessage());
                    //log.error(ex.getMessage(), ex);
                }
            }
        }
           
        }catch(Exception ex){
            System.out.println(""+ex.getMessage());
        }
    }
    
    @Test
    public void testarIteracaoComCsv()throws FileNotFoundException{
        //File file = new File("D:\\LULU SOFTWARES\\MIX_FISCAL\\ProdSPEDxNFe\\DOCUMENTOS_PARA_TESTE\\PAKER\\produtos_mxf_resumido .csv");
        File file = new File("D:\\LULU SOFTWARES\\MIX_FISCAL\\ProdSPEDxNFe\\DOCUMENTOS_PARA_TESTE\\CASTELI\\produtos_mxf.csv");
        String linha ="";
        String[] row;
        int count = 0;
        int totColuns = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            while((linha = br.readLine()) != null){
                if(count > 0)
                    break;
                
                row = linha.split(";");
                totColuns = row.length;
                count++;
            }
        }catch(FileNotFoundException fnex){
        }catch(IOException ioex){
        }
        System.out.println("coluns " + totColuns);
    }
    
    
}
