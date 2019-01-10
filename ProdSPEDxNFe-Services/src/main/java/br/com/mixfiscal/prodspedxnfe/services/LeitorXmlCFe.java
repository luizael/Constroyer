
package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.domain.enums.ELocalDestino;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoNotaFiscal;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoPagamento;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.CFe;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.CFeItem;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.COFINSAliq;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.COFINSNT;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.COFINSOutr;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.COFINSQtde;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS00;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS10;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS20;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS30;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS40;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS41;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS50;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS51;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS60;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS70;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS90;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFe;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFeItem;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.PISAliq;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.PISNT;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.PISOutr;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.PISQtde;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.PessoaJuridica;
import br.com.mixfiscal.prodspedxnfe.services.ex.ErroAoCarregarDadosNFeException;
import br.com.mixfiscal.prodspedxnfe.services.util.MetodosUtil;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LeitorXmlCFe {
    private String caminhoXmlCFe;
    private CFe cFe;
    private boolean arquivoNfCeCarregado;
    private StringBuilder sbReporte;
    private Logger log;
    private MetodosUtil mtUtil;

    public LeitorXmlCFe(){
        this.cFe = new CFe();
        this.arquivoNfCeCarregado = false;
        mtUtil = new MetodosUtil();
    }
    public LeitorXmlCFe(String caminhoXmlCFe){
       this();
       this.setCaminhoXmlCFe(caminhoXmlCFe);
        
    }
    public String getCaminhoXmlCFe() {
        return caminhoXmlCFe;
    }
    public void setCaminhoXmlCFe(String caminhoXmlCFe) {
        this.caminhoXmlCFe = caminhoXmlCFe;
    }
    public CFe getcFe() {
        return cFe;
    }
    public void setcFe(CFe cFe) {
        this.cFe = cFe;
    }
    public boolean isArquivoNfCeCarregado() {
        return arquivoNfCeCarregado;
    }
    public void setArquivoNfCeCarregado(boolean arquivoNfCeCarregado) {
        this.arquivoNfCeCarregado = arquivoNfCeCarregado;
    }
    public StringBuilder getSbReporte() {
        return sbReporte;
    }
    public void setSbReporte(StringBuilder sbReporte) {
        this.sbReporte = sbReporte;
    }
    public Logger getLog() {
        return log;
    }
    public void setLog(Logger log) {
        this.log = log;
    }
    
    
     public Map<String, CFe> lerXmlCFes(String caminhoCFe) throws ErroAoCarregarDadosNFeException{
        Map<String, CFe> listaCFe = new HashMap<>();
        File arquivos = new File(caminhoCFe);
        CFe cupomCorrente = null;
        try{
            for(File arquivo : arquivos.listFiles()){
                if (arquivo.isDirectory() || !arquivo.getName().contains(".xml"))
                {
                    sbReporte.append(String.format("O arquivo '%s' foi ignorado pois não é um arquivo XML.", arquivo.getName())).append("\r\n");
                    continue;
                }
                if (arquivo.length() == 0) {
                    String msg = String.format("O arquivo '%s' está vazio.", arquivo.getName());
                    log.info(msg);
                    sbReporte.append(msg).append("\r\n");
                    continue;
                }
                try {
                    cupomCorrente = this.lerXmlCFe(arquivo.getAbsolutePath());
                    listaCFe.put(cupomCorrente.getChaveNFe(), cupomCorrente);
                } catch(Exception ex) {
                    String msg = String.format("Houve um erro ao tentar processar o arquivo '%s'. Mensagem: %s", arquivo.getName(), ex.getMessage());
                    log.error(msg, ex);
                    sbReporte.append(msg).append("\r\n");
                }
            }
        }catch(Exception ex){
            log.error(ex.getMessage());
        }
        return listaCFe;
    }
    
    public CFe lerXmlCFe(String caminhoArquivoXmlCFe) throws ErroAoCarregarDadosNFeException {
         this.setCaminhoXmlCFe(caminhoArquivoXmlCFe);
        return this.obterXmlCFe(null);
    }
    public CFe lerXmlCFe(String caminhoArquivoXmlCFe,String chaveNfe) throws ErroAoCarregarDadosNFeException {
         this.setCaminhoXmlCFe(caminhoArquivoXmlCFe);
        return this.lerXmlCFe(null);
    }
    public CFe buscarXmlCFe(String caminhoArquivoXmlCFe, String chaveNfe)throws ErroAoCarregarDadosNFeException{
        File diretorio = new File(caminhoArquivoXmlCFe);
        CFe cfe = null;
        for(File arquivo : diretorio.listFiles()){
            if (arquivo.isDirectory() || !arquivo.getName().contains(".xml"))
            {
                sbReporte.append(String.format("O arquivo '%s' foi ignorado pois não é um arquivo XML.", arquivo.getName())).append("\r\n");
                continue;
            }
            if (arquivo.length() == 0) {
                String msg = String.format("O arquivo '%s' está vazio.", arquivo.getName());
                log.info(msg);
                sbReporte.append(msg).append("\r\n");
                continue;
            }
            if(arquivo.getName().contains(chaveNfe)){
               cfe = this.obterXmlCFe(arquivo);
               break;
            }
        }        
        diretorio = null;
        return cfe;
    }
    
    //SOLUÇÃO IMEDIATA :)
    public CFe obterXmlCFe(File arquivo) throws ErroAoCarregarDadosNFeException {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document docXmlNFe = docBuilder.parse(arquivo);             

            // Carrega os elementos que serão utilizados da NFe
            Element root = docXmlNFe.getDocumentElement();  
            
            if (!root.getNodeName().equals("CFe"))
                throw new Exception(String.format("O arquivo '%s' não é um arquivo NFe válido.", this.getCaminhoXmlCFe()));
            
            //if (root.getNodeName().equals("retDownloadNFe"))
            //    root.getElementsByTagName("CFe");
            root.getElementsByTagName("CFe");
            Element dadosCFe = (Element)root.getElementsByTagName("infCFe").item(0);
            Element dadosIde = (Element)root.getElementsByTagName("ide").item(0);
            Element dadosEmitente = (Element)root.getElementsByTagName("emit").item(0);
            Element dadosDestinatario = (Element)root.getElementsByTagName("dest").item(0);
            Element dadosTotal = (Element)root.getElementsByTagName("total").item(0);
            NodeList itensCupomFiscal = root.getElementsByTagName("det");
            
            // Extrai os dados dos elementos               
            String dataHoraEmissao = extrairConteudo(dadosIde.getElementsByTagName("dhEmi"));
            if (dataHoraEmissao == null || dataHoraEmissao.isEmpty())
                dataHoraEmissao = extrairConteudo(dadosIde.getElementsByTagName("dEmi"));
            
            this.cFe = new CFe();            
            this.cFe.setChaveNFe(dadosCFe.getAttribute("Id").replace("CFe", "")); 
            this.cFe.setNumeroNotaFiscal(extrairConteudo(dadosIde.getElementsByTagName("nCFe"))); // tag <nNF>
            this.cFe.setNserieSAT(extrairConteudo(dadosIde.getElementsByTagName("nserieSAT")));                                    
            this.cFe.setNatOp(extrairConteudo(dadosIde.getElementsByTagName("natOp")));
            this.cFe.setModelo(extrairConteudo(dadosIde.getElementsByTagName("mod")));
            this.cFe.setCnpj(extrairConteudo(dadosIde.getElementsByTagName("CNPJ")));
            this.cFe.setDataHoraEmissao(mtUtil.converterData(dataHoraEmissao));
          
            this.cFe.setEmitente(extrairPessoaJuridica(dadosEmitente));            
            this.cFe.setDestinatario(extrairPessoaJuridica(dadosDestinatario));

            // <total>
            //      <ICMSTot>
            this.cFe.setValorTotalBCICMS(BigDecimal.ZERO); // <vBC>
            this.cFe.setValorTotalICMS(BigDecimal.ZERO); // <vICMS>
            this.cFe.setValorTotalICMSDesonerado(BigDecimal.ZERO); // <vICMSDeson>
            this.cFe.setValorTotalBCICMSST(BigDecimal.ZERO); // <vBCST>
            try { this.cFe.setValorTotalICMSST(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vST")))); } catch(Exception ex) {}
            this.cFe.setValorTotalProdServ(BigDecimal.ZERO); // <vProd>
            this.cFe.setValorTotalFrete(BigDecimal.ZERO); // <vFrete>
            this.cFe.setValorTotalSeguro(BigDecimal.ZERO); // <vSeg>
            this.cFe.setValorTotalDesconto(BigDecimal.ZERO); // <vDesc>
            this.cFe.setValorTotalII(BigDecimal.ZERO); // <vII>
            this.cFe.setValorTotalIPI(BigDecimal.ZERO); // <vIPI>
            this.cFe.setValorPIS(BigDecimal.ZERO); // <vPIS>
            this.cFe.setValorCOFINS(BigDecimal.ZERO); // <vCOFINS>
            this.cFe.setValorOutrasDespesas(BigDecimal.ZERO); // <vOutro>
            try { this.cFe.setValorNotaFiscal(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vNF")))); } catch(Exception ex) {}
            this.cFe.setValorTotalTributos(BigDecimal.ZERO); // <vTotTrib>  
                        
            List<CFeItem> listaItensCFe = new ArrayList<>();

            for (int i = 0; i < itensCupomFiscal.getLength(); i++) {
                Element detItem = (Element)itensCupomFiscal.item(i);
                Element prod = (Element)detItem.getElementsByTagName("prod").item(0);
                Element imposto = (Element)detItem.getElementsByTagName("imposto").item(0);                
                CFeItem cfeItem = new CFeItem();
                
                cfeItem.setSequencia(i);
                cfeItem.setNumeroItem(Integer.parseInt(detItem.getAttribute("nItem")));
                cfeItem.setCodigoProduto(extrairConteudo(prod.getElementsByTagName("cProd")));
                cfeItem.setDescricaoProduto(extrairConteudo(prod.getElementsByTagName("xProd")));
                cfeItem.setCodigoEAN(extrairConteudo(prod.getElementsByTagName("cEAN")));
                cfeItem.setCodigoNCM(extrairConteudo(prod.getElementsByTagName("NCM")));
                cfeItem.setUnidade(extrairConteudo(prod.getElementsByTagName("uCom")));
                cfeItem.setQuantidade(new BigDecimal(extrairConteudo(prod.getElementsByTagName("qCom"))));
                cfeItem.setValorUnitario(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vUnCom"))));
                cfeItem.setValorProduto(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vProd"))));
                cfeItem.setValorFrete(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vFrete"), "0")));
                cfeItem.setValorSeguro(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vSeg"), "0")));
                cfeItem.setValorDesconto(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vDesc"), "0")));
                cfeItem.setValorOutros(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vOutro"), "0")));
                cfeItem.setIndicaTotal(Integer.parseInt(extrairConteudo(prod.getElementsByTagName("intTot"), "0")));
                cfeItem.setValorItem(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vItem"),"0")));
                cfeItem.setCfop(extrairConteudo(prod.getElementsByTagName("CFOP")));
                
                // <editor-fold desc="ICMS" defaultstate="collapsed">
                Element tagICMS = null;
                if (imposto.getElementsByTagName("ICMS00").getLength() > 0) {
                    ICMS00 icms = new ICMS00();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS00").item(0);
                    icms.setCST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("CST"),"0")));
                    icms.setModalidadeBaseCalculo(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBC"), "0")));                    
                    icms.setValorBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBC"), "0")));
                    icms.setAliquotaICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMS"), "0")));
                    icms.setValorICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMS"), "0")));
                    
                    cfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS10").getLength() > 0) {
                    ICMS10 icms = new ICMS10();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS10").item(0);
                    icms.setCST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("CST"),"0")));
                    icms.setModalidadeBaseCalculo(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBC"), "0")));//<modBc>
                    icms.setValorBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBC"), "0")));//<vBc>
                    icms.setAliquotaICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMS"), "0")));//<pIcms>
                    icms.setValorICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMS"), "0")));//<vIcms>
                    icms.setModalidadeBCST(extrairConteudo(tagICMS.getElementsByTagName("modBCST"), "0")); //<modBcSt>
                    icms.setValorBCST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBCST"), "0")));//<vBcSt>
                    icms.setAliquotaICMSST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMSST"), "0")));//<pIcmsSt>
                    icms.setValorICMSST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSST"), "0")));//<vIcmsSt>
                    icms.setPercentualMargemValAdicionadoST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pMVAST"), "0")));//<pMvaSt>
                    icms.setPercentualReducaoBCST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pRedBCST"), "0")));//<pRedBcSt> 
                    cfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS20").getLength() > 0) {  
                    ICMS20 icms = new ICMS20();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS20").item(0);
                    icms.setCST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("CST"),"0")));
                    icms.setModalidadeBaseCalculo(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBC"), "0")));//<modBc>
                    icms.setValorBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBC"), "0")));//<vBc>
                    icms.setAliquotaICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMS"), "0")));//<pIcms>
                    icms.setValorICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMS"), "0")));//<vIcms>
                    icms.setPercentualReducaoBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pRedBC"), "0")));//<pRedBc>
                    icms.setMotivoDesoneracaoICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("motDesICMS"), "0")));//<motDesIcms>
                    icms.setValorICMSDesoneracao(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSDeson"), "0")));//<vIcmsDeson>
                    cfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS30").getLength() > 0) {
                    ICMS30 icms = new ICMS30();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS30").item(0);
                    icms.setCST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("CST"),"0")));
                    icms.setModalidadeBaseCalculoST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBC"), "0")));//<modBcSt>
                    icms.setValorBCST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBCST"), "0")));//<vBcSt>
                    icms.setAliquotaICMSST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMSST"), "0")));//<pIcmsSt>
                    icms.setValorICMSST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSST"), "0")));//<vIcmsSt>
                    icms.setPercentualMargemValorAdicionadoST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pMVAST"), "0")));//<pMvaSt>
                    icms.setMotivoDesoneracaoICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("motDesICMS"), "0")));//<motDesIcms>
                    icms.setValorICMSDesoneracao(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSDeson"), "0")));//<vIcmsDeson>;
                    icms.setPercentualReducaoBCST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pRedBCST"), "0")));//<pRedBcSt> 
                    cfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS40").getLength() > 0 ||
                           imposto.getElementsByTagName("ICMS41").getLength() > 0 ||
                           imposto.getElementsByTagName("ICMS50").getLength() > 0) {
                    ICMS40 icms = null;
                    
                    if (imposto.getElementsByTagName("ICMS40").getLength() > 0) {
                        icms = new ICMS40();                        
                        tagICMS = (Element)imposto.getElementsByTagName("ICMS40").item(0);
                        icms.setCST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("CST"),"0")));
                    }
                    else if (imposto.getElementsByTagName("ICMS41").getLength() > 0) {
                        icms = new ICMS41();                        
                        tagICMS = (Element)imposto.getElementsByTagName("ICMS41").item(0);
                        icms.setCST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("CST"),"0")));
                    }
                    else if (imposto.getElementsByTagName("ICMS50").getLength() > 0) {
                        icms = new ICMS50();
                        tagICMS = (Element)imposto.getElementsByTagName("ICMS50").item(0);
                        icms.setCST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("CST"),"0")));

                    }
                    
                    if (icms != null) {
                        ((ICMS40)icms).setMotivoDesoneracaoICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("motDesICMS"), "0")));//<motDesIcms>
                        ((ICMS40)icms).setValorICMSDesoneracao(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSDeson"), "0")));//<vIcmsDeson>
                    }
                    cfeItem.setICMS(icms);                
                } else if (imposto.getElementsByTagName("ICMS51").getLength() > 0) {
                    ICMS51 icms = new ICMS51();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS51").item(0);
                    icms.setCST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("CST"),"0")));
                    icms.setModalidadeBaseCalculo(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBC"), "0")));//<modBc>
                    icms.setValorBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBC"), "0")));//<vBc>
                    icms.setAliquotaICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMS"), "0")));//<pIcms>
                    icms.setValorICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMS"), "0")));//<vIcms>
                    icms.setPercentualReducaoBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pRedBC"), "0")));//<pPredBc>
                    icms.setPercentualDiferimento(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pDif"), "0")));//<pDif>
                    icms.setValorICMSDiferimento(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSDif"), "0")));//<vIcmsDif>
                    icms.setValorICMSOperacao(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSOp"), "0")));//<vIcmsOp>;
                    cfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS60").getLength() > 0) {
                    ICMS60 icms = new ICMS60();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS60").item(0);
                    icms.setCST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("CST"),"0")));
                    icms.setValorBCSTRet(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBCSTRet"), "0")));
                    icms.setValorICMSSTRet(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSSTRet"), "0")));
                    cfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS70").getLength() > 0) {
                    ICMS70 icms = new ICMS70();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS70").item(0);
                    icms.setCST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("CST"),"0")));
                    icms.setModalidadeBaseCalculo(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBC"), "0"))); //<modBc>
                    icms.setValorBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBC"), "0")));//<vBc>
                    icms.setAliquotaICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMS"), "0")));//<pIcms>
                    icms.setValorICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMS"), "0")));//<vIcms>
                    icms.setPercentualReducaoBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pRedBC"), "0")));//<pRedBc>
                    icms.setModalidadeBCST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBCST"), "0")));//<modBcSt>
                    icms.setAliquotaICMSST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMSST"), "0")));
                    icms.setValorICMSST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSST"), "0")));
                    icms.setPercentualMVAST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pMVAST"), "0")));
                    icms.setValorBCST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBCST"), "0")));
                    icms.setMotivoDesoneracaoICMS(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("motDesICMS"), "0")));
                    icms.setValorICMSDesonerado(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSDeson"), "0")));
                    icms.setPercentualReducaoBCST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pRedBCST"), "0")));
                    cfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS90").getLength() > 0) {
                    ICMS90 icms = new ICMS90();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS90").item(0);
                    icms.setCST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("CST"),"0")));
                    icms.setModalidadeBaseCalculo(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBC"), "0")));
                    icms.setValorBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBC"), "0")));
                    icms.setAliquotaICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMS"), "0")));
                    icms.setValorICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMS"), "0")));
                    icms.setPercentualReducaoBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pRedBC"), "0")));
                    icms.setModalidadeBCST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBCST"), "0")));
                    icms.setAliquotaICMSST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMSST"), "0")));
                    icms.setValorICMSST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSST"), "0")));
                    icms.setPercentualMVAST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pMVAST"), "0")));
                    icms.setValorBCST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBCST"), "0")));
                    icms.setMotivoDesoneracaoICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("motDesICMS"), "0")));
                    icms.setValorICMSDesonerado(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSDeson"), "0")));
                    icms.setPercentualReducaoBCST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pRedBCST"), "0")));
                    cfeItem.setICMS(icms);
                }
                
                if (cfeItem.getICMS() != null && tagICMS != null) {
                    cfeItem.getICMS().setCST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("CST"))));
                    cfeItem.getICMS().setOrigem(extrairConteudo(tagICMS.getElementsByTagName("orig")));
                }
                // </editor-fold>
                
                // <editor-fold desc="PIS" defaultstate="collapsed">
                Element tagPIS = null;
                if (imposto.getElementsByTagName("PISAliq").getLength() > 0) {
                    PISAliq pis = new PISAliq();
                    tagPIS = (Element)imposto.getElementsByTagName("PISAliq").item(0);
                    pis.setValorBC(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("vBC"), "0")));
                    pis.setPercentualPIS(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("pPIS"), "0")));
                    pis.setValorPIS(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("vPIS"), "0")));
                    cfeItem.setPIS(pis);
                } else if (imposto.getElementsByTagName("PISQtde").getLength() > 0) {
                    PISQtde pis = new PISQtde();
                    tagPIS = (Element)imposto.getElementsByTagName("PISQtde").item(0);
                    pis.setQuantidadeBCProd(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("qBCProd"), "0")));
                    pis.setValorAliquotaProd(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("vAliqProd"), "0")));
                    pis.setValorPIS(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("vPIS"), "0")));
                    cfeItem.setPIS(pis);
                } else if (imposto.getElementsByTagName("PISNT").getLength() > 0) {
                    PISNT pis = new PISNT();                    
                    tagPIS = (Element)imposto.getElementsByTagName("PISNT").item(0);
                    cfeItem.setPIS(pis);  
                } else if (imposto.getElementsByTagName("PISOutr").getLength() > 0) {
                    PISOutr pis = new PISOutr();
                    tagPIS = (Element)imposto.getElementsByTagName("PISOutr").item(0);                    
                    if (tagPIS.getElementsByTagName("vBC").getLength() > 0) {
                        pis.setValorBC(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("vBC"), "0")));
                        pis.setPercentualPIS(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("pPIS"), "0")));
                    }
                    if (tagPIS.getElementsByTagName("qBCProd").getLength() > 0) {
                        pis.setQuantidadeBCProd(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("qBCProd"), "0")));
                        pis.setValorAliqProd(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("vAliqProd"), "0")));                       
                    }                    
                    pis.setValorPIS(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("vPIS"), "0")));
                    cfeItem.setPIS(pis);
                }   
                
                if (cfeItem.getPIS() != null && tagPIS != null)
                    cfeItem.getPIS().setCST(Integer.parseInt(extrairConteudo(tagPIS.getElementsByTagName("CST"))));
                // </editor-fold>
                
                // <editor-fold desc="COFINS" defaultstate="collapsed">
                Element tagCOFINS = null;
                if (imposto.getElementsByTagName("COFINSAliq").getLength() > 0) {
                    COFINSAliq cofins = new COFINSAliq();
                    tagCOFINS = (Element)imposto.getElementsByTagName("COFINSAliq").item(0);
                    cofins.setValorBC(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("vBC"), "0")));
                    cofins.setPercentualCOFINS(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("pCOFINS"), "0")));
                    cofins.setValorCOFINS(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("vCOFINS"), "0")));
                    cfeItem.setCOFINS(cofins);
                } else if (imposto.getElementsByTagName("COFINSQtde").getLength() > 0) {
                    COFINSQtde cofins = new COFINSQtde();
                    tagCOFINS = (Element)imposto.getElementsByTagName("COFINSQtde").item(0);
                    cofins.setQuantidadeBCProd(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("qBCProd"), "0")));
                    cofins.setValorAliquotaProd(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("vAliqProd"), "0")));
                    cofins.setValorCOFINS(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("vCOFINS"), "0")));
                    cfeItem.setCOFINS(cofins);
                } else if (imposto.getElementsByTagName("COFINSNT").getLength() > 0) {
                    COFINSNT cofins = new COFINSNT();
                    tagCOFINS = (Element)imposto.getElementsByTagName("COFINSNT").item(0);                    
                    cfeItem.setCOFINS(cofins);
                } else if (imposto.getElementsByTagName("COFINSOutr").getLength() > 0) {
                    COFINSOutr cofins = new COFINSOutr();
                    tagCOFINS = (Element)imposto.getElementsByTagName("COFINSOutr").item(0);                    
                    if (tagCOFINS.getElementsByTagName("vBC").getLength() > 0) {
                        cofins.setValorBC(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("vBC"), "0")));
                        cofins.setPercentualCOFINS(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("pCOFINS"), "0")));
                    }
                    if (tagCOFINS.getElementsByTagName("qBCProd").getLength() > 0) {
                        cofins.setQuantidadeBCProd(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("qBCProd"), "0")));
                        cofins.setValorAliquotaProd(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("vAliqProd"), "0")));
                    }                    
                    cofins.setValorCOFINS(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("vCOFINS"), "0")));
                    cfeItem.setCOFINS(cofins);
                }
                
                if (cfeItem.getCOFINS() != null && tagCOFINS != null)
                    cfeItem.getCOFINS().setCST(Integer.parseInt(extrairConteudo(tagCOFINS.getElementsByTagName("CST"))));
                // </editor-fold>
                
                listaItensCFe.add(cfeItem);
            }                     
           
            this.cFe.setItensCfe(listaItensCFe);
            
            //listaItensCFe.stream().forEach(i -> {
             //   i.setNFe(this.cFe);
            //});
            
            //this.arquivoNfCeCarregado = true;
            
            return this.cFe;
        } catch(SAXException ex) {
            log.error(ex.getMessage());
            throw new ErroAoCarregarDadosNFeException(String.format("Houve uma exceção SAXException ao tentar carregar os dados do Xml '%s'", this.getCaminhoXmlCFe()), ex);
            
        } catch(IOException ex) {
            log.error(ex.getMessage());
            throw new ErroAoCarregarDadosNFeException(String.format("Houve uma exceção de I/O ao tentar ler o arquivo '%s'. %s", this.getCaminhoXmlCFe(), ex.getMessage()), ex);
        } catch(ParserConfigurationException ex) {
            log.error(ex.getMessage());
            throw new ErroAoCarregarDadosNFeException(String.format("Houve uma exceção de Parser ao tentar carregar os dados do Xml da NFe '%s'. Mensagem: %s", this.getCaminhoXmlCFe(), ex.getMessage()), ex);
        } catch(Exception ex) {
            log.error(ex.getMessage());
            throw new ErroAoCarregarDadosNFeException(String.format("Houve uma exceção de desconhecida ao carregar os dados do Xml da NFe '%s'. Mensagem: %s", this.getCaminhoXmlCFe(), ex.getMessage()), ex);
        }
    } 
    
    private PessoaJuridica extrairPessoaJuridica(Element el) {
        PessoaJuridica pj = new PessoaJuridica();
                
        if (el.getElementsByTagName("CNPJ").getLength() > 0)
            pj.setCNPJ(el.getElementsByTagName("CNPJ").item(0).getTextContent());
        if (el.getElementsByTagName("xNome").getLength() > 0)
            pj.setRazaoSocial(el.getElementsByTagName("xNome").item(0).getTextContent());
        if (el.getElementsByTagName("xFant").getLength() > 0)
            pj.setNomeFantasia(el.getElementsByTagName("xFant").item(0).getTextContent());
        if (el.getElementsByTagName("xLgr").getLength() > 0) 
            pj.setLogrdouro(el.getElementsByTagName("xLgr").item(0).getTextContent());
        if (el.getElementsByTagName("nro").getLength() > 0) {
            try {
                pj.setNumero(Integer.parseInt(el.getElementsByTagName("nro").item(0).getTextContent()));
            } catch(Exception ex) {}
        }
        if (el.getElementsByTagName("xBairro").getLength() > 0) 
            pj.setBairro(el.getElementsByTagName("xBairro").item(0).getTextContent());
        if (el.getElementsByTagName("cMun").getLength() > 0) {
            try {
                pj.setCodigoMunicipio(Integer.parseInt(el.getElementsByTagName("cMun").item(0).getTextContent()));
            } catch(Exception ex) {}
        }
        if (el.getElementsByTagName("xMun").getLength() > 0) 
            pj.setNomeMunicipio(el.getElementsByTagName("xMun").item(0).getTextContent());
        if (el.getElementsByTagName("UF").getLength() > 0) 
            pj.setSiglaUF(el.getElementsByTagName("UF").item(0).getTextContent());
        if (el.getElementsByTagName("CEP").getLength() > 0) 
            pj.setCep(el.getElementsByTagName("CEP").item(0).getTextContent());
        if (el.getElementsByTagName("cPais").getLength() > 0) {
            try {
                pj.setCodigoPais(Integer.parseInt(el.getElementsByTagName("cPais").item(0).getTextContent()));
            } catch(Exception ex) {}
        }
        if (el.getElementsByTagName("xPais").getLength() > 0) 
            pj.setNomePais(el.getElementsByTagName("xPais").item(0).getTextContent());
        if (el.getElementsByTagName("IE").getLength() > 0) 
            pj.setIE(el.getElementsByTagName("IE").item(0).getTextContent());
        if (el.getElementsByTagName("IM").getLength() > 0) 
            pj.setIM(el.getElementsByTagName("IM").item(0).getTextContent());
        if (el.getElementsByTagName("CNAE").getLength() > 0) {
            try {
                pj.setCNAE(Integer.parseInt(el.getElementsByTagName("CNAE").item(0).getTextContent()));
            } catch(Exception ex) {}
        }
        if (el.getElementsByTagName("CRT").getLength() > 0) {
            try {
                pj.setCRT(Integer.parseInt(el.getElementsByTagName("CRT").item(0).getTextContent()));
            } catch(Exception ex) {}
        }
        if (el.getElementsByTagName("indIEDest").getLength() > 0) {
            try {
                pj.setIndiIEDest(Integer.parseInt(el.getElementsByTagName("indIEDest").item(0).getTextContent()));
            } catch(Exception ex) {}
        }
        if (el.getElementsByTagName("email").getLength() > 0) 
            pj.setEmail(el.getElementsByTagName("email").item(0).getTextContent());
            
        return pj;
    }
    
    private String extrairConteudo(NodeList nos) {
        return (String)extrairConteudo(nos, "");
    }    
    private String extrairConteudo(NodeList nos, String retValQdoVazio) {
        if (nos != null && nos.getLength() > 0)
            return nos.item(0).getTextContent();
        return retValQdoVazio;
    }

    
}
