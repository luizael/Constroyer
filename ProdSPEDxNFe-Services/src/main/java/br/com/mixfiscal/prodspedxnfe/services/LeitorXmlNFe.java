package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.domain.enums.ELocalDestino;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoNotaFiscal;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoPagamento;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.CFe;
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
import br.com.mixfiscal.prodspedxnfe.domain.nfe.IPI;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFe;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFeItem;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.PISAliq;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.PISNT;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.PISOutr;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.PISQtde;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.PessoaJuridica;
import br.com.mixfiscal.prodspedxnfe.services.ex.DadosNFeNaoCarregadosException;
import br.com.mixfiscal.prodspedxnfe.services.ex.ErroAoCarregarDadosNFeException;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class LeitorXmlNFe implements Reportavel {
    // <editor-fold defaultstate="collapsed" desc="Membros Privados">
    private String caminhoArquivoXmlNFe;
    private File arquivoSelecionado;
    private NFe nfe;
    private boolean arquivoNFeCarregado;
    private StringBuilder sbReporte;
    private Logger log;
    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="Construtores">
    public LeitorXmlNFe() {        
        this.nfe = new NFe();
        this.arquivoNFeCarregado = false;
        this.sbReporte = new StringBuilder();
        this.log = LogManager.getLogger(LeitorXmlNFe.class);
        
    }
    
    public LeitorXmlNFe(String caminhoArquivoXmlNFe) {
        this();
        this.setCaminhoArquivoXmlNFe(caminhoArquivoXmlNFe);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters e Setters">
    public final String getCaminhoArquivoXmlNFe() {
        return this.caminhoArquivoXmlNFe;
    }
    
    public final void setCaminhoArquivoXmlNFe(String caminhoArquivoXmlNFe) {
        this.caminhoArquivoXmlNFe = caminhoArquivoXmlNFe;
    }
    
    public NFe getNFe() throws DadosNFeNaoCarregadosException {
        if (!this.arquivoNFeCarregado)
            throw new DadosNFeNaoCarregadosException("O método lerXmlNFe() deve ser chamado antes de tentar usar getNFe()");
        return this.nfe;
    }    
    // </editor-fold>
    
    @Override
    public String getReporte() {
        return this.sbReporte.toString();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Públicos">
    //SOLUÇÃO IMEDIADA :)
    public NFe buscarXmlNFe(String caminhoDirXmlsNFes, String chaveNfe)throws ErroAoCarregarDadosNFeException{
        File dirXml = new File(caminhoDirXmlsNFes);     
        NFe nfe = null;        
        for (File arquivoXmlNFe : dirXml.listFiles()){           
            if (arquivoXmlNFe.isDirectory() || !arquivoXmlNFe.getName().contains(".xml"))
            {
                sbReporte.append(String.format("<div class='resumoProcessamento'>O arquivo '%s' foi ignorado pois não é um arquivo XML.</div>", arquivoXmlNFe.getName())).append("");
                continue;
            }
            if (arquivoXmlNFe.length() == 0) {
                String msg = String.format("O arquivo '%s' está vazio.", arquivoXmlNFe.getName());
                log.info(msg);
                sbReporte.append("<div class='resumoProcessamento'>" + msg + "</div>").append("");
                continue;
            }
            if(!arquivoXmlNFe.getName().contains(chaveNfe))continue;
            try{
                if(arquivoXmlNFe.getName().contains(chaveNfe)){
                nfe = this.obterXmlNFe(arquivoXmlNFe);
                break;
                }
            }catch(Exception ex){
                String msg = String.format("Houve um erro ao tentar processar o arquivo '%s'. Mensagem: %s", arquivoXmlNFe.getName(), ex.getMessage());
                log.error(msg, ex);
                sbReporte.append("<div class='resumoProcessamento'>" + msg + "</div>").append("\r\n");
            }
        }
        dirXml = null;
        return nfe;
    }   
    
    public Map<String, NFe> lerXmlsNFes(String caminhoDirXmlsNFes) throws ErroAoCarregarDadosNFeException {
        Map<String, NFe> listaXmlsNFes = new HashMap<>();
        File dirXmls = new File(caminhoDirXmlsNFes);
        NFe nfeCorrente = null;
        
        for (File arquivoXmlNFe : dirXmls.listFiles()) {
            if (arquivoXmlNFe.isDirectory() || !arquivoXmlNFe.getName().contains(".xml"))
            {
                sbReporte.append(String.format("<div class='resumoProcessamento'>O arquivo '%s' foi ignorado pois não é um arquivo XML.</div>", arquivoXmlNFe.getName())).append("");
                continue;
            }
            
            if (arquivoXmlNFe.length() == 0) {
                String msg = String.format("O arquivo '%s' está vazio.", arquivoXmlNFe.getName());
                log.info(msg);                
                sbReporte.append("<div class='resumoProcessamento'>" + msg + "</div>");
                continue;
            }
            
            try {
                nfeCorrente = this.lerXmlNFe(arquivoXmlNFe.getAbsolutePath());
                listaXmlsNFes.put(nfeCorrente.getChaveNFe(), nfeCorrente);
            } catch(Exception ex) {
                String msg = String.format("Houve um erro ao tentar processar o arquivo '%s'. Mensagem: %s", arquivoXmlNFe.getName(), ex.getMessage());
                log.error(msg, ex);
                sbReporte.append("<div class='resumoProcessamento'>" + msg + "</div>");
            }            
        }        
        return listaXmlsNFes;
    }
    
    public NFe lerXmlNFe(String caminhoArquivoXmlNFe) throws ErroAoCarregarDadosNFeException {
        this.setCaminhoArquivoXmlNFe(caminhoArquivoXmlNFe);
        return this.lerXmlNFe();
    }  
    
    public NFe lerXmlNFe() throws ErroAoCarregarDadosNFeException {
        File arquivoXmlNFe;
        if(this.arquivoSelecionado == null){
            arquivoXmlNFe = new File(this.getCaminhoArquivoXmlNFe());
        }else{
            arquivoXmlNFe = this.arquivoSelecionado;
        }
        try {            
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setNamespaceAware(true);            
            InputStream is = new FileInputStream(arquivoXmlNFe);
            Reader reader = new InputStreamReader(is, "UTF-8");
            InputSource inpSrc = new InputSource(reader);
            inpSrc.setEncoding("UTF-8");
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document docXmlNFe = docBuilder.parse(inpSrc);

            // Carrega os elementos que serão utilizados da NFe
            Element root = docXmlNFe.getDocumentElement();  
            
            if (!root.getNodeName().equals("nfeProc") && !root.getNodeName().equals("retDownloadNFe"))
                throw new Exception(String.format("O arquivo '%s' não é um arquivo NFe válido.", this.getCaminhoArquivoXmlNFe()));
            
            if (root.getNodeName().equals("retDownloadNFe"))
                root.getElementsByTagName("nfeProc");
            
            //root = this.identificarElementoRaiz(root);
            //root = this.identificarChave(root);
            
            Element dadosNFe = (Element)root.getElementsByTagName("infNFe").item(0);
            //Element dadosNFe = identificarChave(root);
            Element dadosIde = (Element)root.getElementsByTagName("ide").item(0);
            Element dadosEmitente = (Element)root.getElementsByTagName("emit").item(0);
            Element dadosDestinatario = (Element)root.getElementsByTagName("dest").item(0);
            Element dadosTotal = (Element)root.getElementsByTagName("total").item(0);
            NodeList itensNotaFiscal = root.getElementsByTagName("det");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            
            // Extrai os dados dos elementos   
            
            String dataHoraEmissao = extrairConteudo(dadosIde.getElementsByTagName("dhEmi"));
            if (dataHoraEmissao == null || dataHoraEmissao.isEmpty())
                dataHoraEmissao = extrairConteudo(dadosIde.getElementsByTagName("dEmi"));
            
            this.nfe = new NFe();
            
            this.nfe.setChaveNFe(dadosNFe.getAttribute("Id").replace("NFe", "")); 
            //this.nfe.setChaveNFe(this.retornarChave(root));
            
            this.nfe.setNumeroNotaFiscal(extrairConteudo(dadosIde.getElementsByTagName("nNF")));  
            //this.nfe.setNumeroNotaFiscal(retornarNumeroNota(dadosIde));
            this.nfe.setSerie(extrairConteudo(dadosIde.getElementsByTagName("serie")));       
            //this.nfe.setSerie(retornarSerieNota(dadosIde));
            
            this.nfe.setNatOp(extrairConteudo(dadosIde.getElementsByTagName("natOp")));
            try{
                this.nfe.setIndicadorPagamento(ETipoPagamento.deCodigo(extrairConteudo(dadosIde.getElementsByTagName("indPag"))));
            }catch(Exception ex){log.error(ex.getMessage());}
            this.nfe.setModelo(extrairConteudo(dadosIde.getElementsByTagName("mod")));
           // this.nfe.setSerie(extrairConteudo(dadosIde.getElementsByTagName("serie"))); // tag <serie>
            //this.nfe.setNumeroNotaFiscal(extrairConteudo(dadosIde.getElementsByTagName("cNF"))); // tag <nNF>
            try {
                this.nfe.setDataHoraEmissao(sdf.parse(dataHoraEmissao));
            } catch(Exception ex) {
                try {
                    this.nfe.setDataHoraEmissao(sdf2.parse(dataHoraEmissao));
                } catch(Exception ex2) {
                    log.error(String.format("Não foi possível realizar o Parse da data dhEmi/dEmi = '%s'", dataHoraEmissao), ex2);
                }
            }
            
            String dataHoraSaida = extrairConteudo(dadosIde.getElementsByTagName("dhSaiEnt"));
            if (!StringUtil.isNullOrEmpty(dataHoraSaida)) {
                try {
                    this.nfe.setDataHoraSaiEnt(sdf.parse(dataHoraSaida));
                } catch(Exception ex) {
                    try {
                        this.nfe.setDataHoraSaiEnt(sdf2.parse(dataHoraSaida));
                    } catch(Exception ex2) {
                        log.error(String.format("Não foi possível realizar o Parse da data dhSaiEnt = '%s'", dataHoraSaida), ex2);
                    }
                }
            }
            try{
                this.nfe.setTipoNotaFiscal(ETipoNotaFiscal.deCodigo(extrairConteudo(dadosIde.getElementsByTagName("tpNF"))));
            }catch(Exception ex){
                log.error(ex.getMessage());
            }
            try {
                this.nfe.setIdDestino(ELocalDestino.deCodigo(extrairConteudo(dadosIde.getElementsByTagName("idDest"))));            
            } catch(Exception ex) {
                log.error(String.format("Houve um erro ao tentar realizar o Parse de idDest = '%s'. Mensagem: %s", extrairConteudo(dadosIde.getElementsByTagName("tpNF")), ex.getMessage(), ex));
            }

            this.nfe.setEmitente(extrairPessoaJuridica(dadosEmitente));  
            try{
                this.nfe.setDestinatario(extrairPessoaJuridica(dadosDestinatario));
            }catch(Exception ex){log.error(ex.getMessage());}
            // <total>
            // <ICMSTot>
            try{ this.nfe.setValorTotalBCICMS(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vBC"))));}catch(Exception ex){}//<vBC>
            try{ this.nfe.setValorTotalICMS(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vICMS"))));}catch(Exception ex){}// <vICMS>
            try{ this.nfe.setValorTotalICMSDesonerado(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vICMSDeson"))));}catch(Exception ex){} // <vICMSDeson>
            try{ this.nfe.setValorTotalBCICMSST(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vBCST"))));}catch(Exception ex){}  // <vBCST>
            try{ this.nfe.setValorTotalICMSST(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vST")))); } catch(Exception ex) {}
            
            try{ this.nfe.setValorTotalProdServ(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vProd"))));}catch(Exception ex){} //<vProd>            
            try{ this.nfe.setValorTotalFrete(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vFrete"))));}catch(Exception ex){} // <vFrete>
            try{ this.nfe.setValorTotalSeguro(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vSeg"))));}catch(Exception ex){} // <vSeg>
            try{ this.nfe.setValorTotalDesconto(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vDesc"))));}catch(Exception ex){} // <vDesc>
            try{ this.nfe.setValorOutrasDespesas(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vOutro"))));}catch(Exception ex){} // <vOutro>
            
            try{ this.nfe.setValorTotalII(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vII"))));}catch(Exception ex){} // <vII>
            try{ this.nfe.setValorTotalIPI(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vIPI"))));}catch(Exception ex){} // <vIPI>
            try{ this.nfe.setValorPIS(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vPIS"))));}catch(Exception ex){} // <vPIS>
            try{ this.nfe.setValorCOFINS(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vCOFINS"))));}catch(Exception ex){} // <vCOFINS>
            
            try{ this.nfe.setValorNotaFiscal(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vNF")))); } catch(Exception ex) {}
            try{ this.nfe.setValorTotalTributos(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vTotTrib"))));}catch(Exception ex){} // <vTotTrib>  
            
                        
            List<NFeItem> listaItensNFe = new ArrayList<>();

            for (int i = 0; i < itensNotaFiscal.getLength(); i++) {
                Element detItem = (Element)itensNotaFiscal.item(i);
                Element prod = (Element)detItem.getElementsByTagName("prod").item(0);
                Element imposto = (Element)detItem.getElementsByTagName("imposto").item(0);
                NFeItem nfeItem = new NFeItem();
                
                nfeItem.setSequencia(i);
                nfeItem.setNumeroItem(Integer.parseInt(detItem.getAttribute("nItem")));
                nfeItem.setCodigoProduto(extrairConteudo(prod.getElementsByTagName("cProd")));
                nfeItem.setDescricaoProduto(extrairConteudo(prod.getElementsByTagName("xProd")));                
                nfeItem.setCodigoEAN(this.corrigirEAN(extrairConteudo(prod.getElementsByTagName("cEAN"))));
                 try{
                    nfeItem.setCodigoEANTrib(extrairConteudo(prod.getElementsByTagName("cEANTrib")));
                }catch(Exception ex){nfeItem.setCodigoEANTrib("0");};
                nfeItem.setCodigoNCM(extrairConteudo(prod.getElementsByTagName("NCM")));
                nfeItem.setUnidade(extrairConteudo(prod.getElementsByTagName("uCom")));
                nfeItem.setQuantidade(new BigDecimal(extrairConteudo(prod.getElementsByTagName("qCom"))));
                nfeItem.setValorUnitario(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vUnCom"),"0")));
                nfeItem.setValorProduto(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vProd"),"0")));
                nfeItem.setValorFrete(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vFrete"), "0")));
                nfeItem.setValorSeguro(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vSeg"), "0")));
                nfeItem.setValorDesconto(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vDesc"), "0")));
                nfeItem.setValorOutros(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vOutro"), "0")));
                nfeItem.setIndicaTotal(Integer.parseInt(extrairConteudo(prod.getElementsByTagName("indTot"), "0")));
                nfeItem.setCfop(extrairConteudo(prod.getElementsByTagName("CFOP")));
                
                // <editor-fold desc="ICMS" defaultstate="collapsed">
                Element tagICMS = null;
                if (imposto.getElementsByTagName("ICMS00").getLength() > 0) {
                    ICMS00 icms = new ICMS00();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS00").item(0);
                    icms.setModalidadeBaseCalculo(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBC"), "0")));
                    icms.setValorBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBC"), "0")));
                    icms.setAliquotaICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMS"), "0")));
                    icms.setValorICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMS"), "0")));
                    nfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS10").getLength() > 0) {
                    ICMS10 icms = new ICMS10();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS10").item(0);
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
                    nfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS20").getLength() > 0) {  
                    ICMS20 icms = new ICMS20();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS20").item(0);
                    icms.setModalidadeBaseCalculo(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBC"), "0")));//<modBc>
                    icms.setValorBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBC"), "0")));//<vBc>
                    icms.setAliquotaICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMS"), "0")));//<pIcms>
                    icms.setValorICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMS"), "0")));//<vIcms>
                    icms.setPercentualReducaoBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pRedBC"), "0")));//<pRedBc>
                    icms.setMotivoDesoneracaoICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("motDesICMS"), "0")));//<motDesIcms>
                    icms.setValorICMSDesoneracao(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSDeson"), "0")));//<vIcmsDeson>
                    nfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS30").getLength() > 0) {
                    ICMS30 icms = new ICMS30();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS30").item(0);
                    icms.setModalidadeBaseCalculoST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBC"), "0")));//<modBcSt>
                    icms.setValorBCST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBCST"), "0")));//<vBcSt>
                    icms.setAliquotaICMSST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMSST"), "0")));//<pIcmsSt>
                    icms.setValorICMSST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSST"), "0")));//<vIcmsSt>
                    icms.setPercentualMargemValorAdicionadoST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pMVAST"), "0")));//<pMvaSt>
                    icms.setMotivoDesoneracaoICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("motDesICMS"), "0")));//<motDesIcms>
                    icms.setValorICMSDesoneracao(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSDeson"), "0")));//<vIcmsDeson>;
                    icms.setPercentualReducaoBCST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pRedBCST"), "0")));//<pRedBcSt> 
                    nfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS40").getLength() > 0 ||
                           imposto.getElementsByTagName("ICMS41").getLength() > 0 ||
                           imposto.getElementsByTagName("ICMS50").getLength() > 0) {
                    ICMS40 icms = null;
                    
                    if (imposto.getElementsByTagName("ICMS40").getLength() > 0) {
                        icms = new ICMS40();
                        tagICMS = (Element)imposto.getElementsByTagName("ICMS40").item(0);
                    }
                    else if (imposto.getElementsByTagName("ICMS41").getLength() > 0) {
                        icms = new ICMS41();
                        tagICMS = (Element)imposto.getElementsByTagName("ICMS41").item(0);
                    }
                    else if (imposto.getElementsByTagName("ICMS50").getLength() > 0) {
                        icms = new ICMS50();
                        tagICMS = (Element)imposto.getElementsByTagName("ICMS50").item(0);
                    }
                    
                    if (icms != null) {
                        ((ICMS40)icms).setMotivoDesoneracaoICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("motDesICMS"), "0")));//<motDesIcms>
                        ((ICMS40)icms).setValorICMSDesoneracao(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSDeson"), "0")));//<vIcmsDeson>
                    }
                    nfeItem.setICMS(icms);                
                } else if (imposto.getElementsByTagName("ICMS51").getLength() > 0) {
                    ICMS51 icms = new ICMS51();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS51").item(0);
                    icms.setModalidadeBaseCalculo(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBC"), "0")));//<modBc>
                    icms.setValorBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBC"), "0")));//<vBc>
                    icms.setAliquotaICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMS"), "0")));//<pIcms>
                    icms.setValorICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMS"), "0")));//<vIcms>
                    icms.setPercentualReducaoBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pRedBC"), "0")));//<pPredBc>
                    icms.setPercentualDiferimento(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pDif"), "0")));//<pDif>
                    icms.setValorICMSDiferimento(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSDif"), "0")));//<vIcmsDif>
                    icms.setValorICMSOperacao(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSOp"), "0")));//<vIcmsOp>;
                    nfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS60").getLength() > 0) {
                    ICMS60 icms = new ICMS60();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS60").item(0);
                    icms.setValorBCSTRet(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBCSTRet"), "0")));
                    icms.setValorICMSSTRet(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSSTRet"), "0")));
                    nfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS70").getLength() > 0) {
                    ICMS70 icms = new ICMS70();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS70").item(0);
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
                    nfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS90").getLength() > 0) {
                    ICMS90 icms = new ICMS90();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS90").item(0);
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
                    nfeItem.setICMS(icms);
                }
                
                if (nfeItem.getICMS() != null && tagICMS != null) {
                    nfeItem.getICMS().setCST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("CST"))));
                    nfeItem.getICMS().setOrigem(extrairConteudo(tagICMS.getElementsByTagName("orig")));
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
                    nfeItem.setPIS(pis);
                } else if (imposto.getElementsByTagName("PISQtde").getLength() > 0) {
                    PISQtde pis = new PISQtde();
                    tagPIS = (Element)imposto.getElementsByTagName("PISQtde").item(0);
                    pis.setQuantidadeBCProd(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("qBCProd"), "0")));
                    pis.setValorAliquotaProd(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("vAliqProd"), "0")));
                    pis.setValorPIS(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("vPIS"), "0")));
                    nfeItem.setPIS(pis);
                } else if (imposto.getElementsByTagName("PISNT").getLength() > 0) {
                    PISNT pis = new PISNT();                    
                    tagPIS = (Element)imposto.getElementsByTagName("PISNT").item(0);
                    nfeItem.setPIS(pis);  
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
                    nfeItem.setPIS(pis);
                }   
                
                if (nfeItem.getPIS() != null && tagPIS != null)
                    nfeItem.getPIS().setCST(Integer.parseInt(extrairConteudo(tagPIS.getElementsByTagName("CST"))));
                // </editor-fold>
                
                // <editor-fold desc="COFINS" defaultstate="collapsed">
                Element tagCOFINS = null;
                if (imposto.getElementsByTagName("COFINSAliq").getLength() > 0) {
                    COFINSAliq cofins = new COFINSAliq();
                    tagCOFINS = (Element)imposto.getElementsByTagName("COFINSAliq").item(0);
                    cofins.setValorBC(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("vBC"), "0")));
                    cofins.setPercentualCOFINS(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("pCOFINS"), "0")));
                    cofins.setValorCOFINS(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("vCOFINS"), "0")));
                    nfeItem.setCOFINS(cofins);
                } else if (imposto.getElementsByTagName("COFINSQtde").getLength() > 0) {
                    COFINSQtde cofins = new COFINSQtde();
                    tagCOFINS = (Element)imposto.getElementsByTagName("COFINSQtde").item(0);
                    cofins.setQuantidadeBCProd(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("qBCProd"), "0")));
                    cofins.setValorAliquotaProd(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("vAliqProd"), "0")));
                    cofins.setValorCOFINS(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("vCOFINS"), "0")));
                    nfeItem.setCOFINS(cofins);
                } else if (imposto.getElementsByTagName("COFINSNT").getLength() > 0) {
                    COFINSNT cofins = new COFINSNT();
                    tagCOFINS = (Element)imposto.getElementsByTagName("COFINSNT").item(0);                    
                    nfeItem.setCOFINS(cofins);
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
                    nfeItem.setCOFINS(cofins);
                }
                
                if (nfeItem.getCOFINS() != null && tagCOFINS != null)
                    nfeItem.getCOFINS().setCST(Integer.parseInt(extrairConteudo(tagCOFINS.getElementsByTagName("CST"))));
                // </editor-fold>
                
                // <editor-fold desc="IPI" defaultstate="collapsed">
                Element tagIPI = null;
                IPI ipi = null;
                if (imposto.getElementsByTagName("IPI").getLength() > 0) {
                    ipi = new IPI();
                    tagIPI = (Element)imposto.getElementsByTagName("IPI").item(0);
                    if(tagIPI.getElementsByTagName("cIEnq").getLength() > 0){
                        ipi.setcEnq(extrairConteudo(tagIPI.getElementsByTagName("cIEnq"), "0"));
                    }
                    if(tagIPI.getElementsByTagName("CNPJProd").getLength() > 0){
                        ipi.setCNPJProd(extrairConteudo(tagIPI.getElementsByTagName("CNPJProd"), "0"));
                    }
                    if(tagIPI.getElementsByTagName("cSelo").getLength() > 0){
                        ipi.setcSelo(extrairConteudo(tagIPI.getElementsByTagName("cSelo"), "0"));
                    }
                    if(tagIPI.getElementsByTagName("qSelo").getLength() > 0){
                        ipi.setqSelo(extrairConteudo(tagIPI.getElementsByTagName("qSelo"), "0"));
                    }
                    if(tagIPI.getElementsByTagName("cEnq").getLength() > 0){
                        ipi.setcEnq(extrairConteudo(tagIPI.getElementsByTagName("cEnq"), "0"));
                    }
                    
                    if (tagIPI.getElementsByTagName("IPINT").getLength() > 0) {
                        tagIPI = (Element)imposto.getElementsByTagName("IPINT").item(0);
                        ipi.setCST(extrairConteudo(tagIPI.getElementsByTagName("CST"), "0"));
                    }
                    if(tagIPI.getElementsByTagName("vBC").getLength() > 0){
                        ipi.setvBC(new BigDecimal(extrairConteudo(tagIPI.getElementsByTagName("vBC"), "0")));
                    }
                    if(tagIPI.getElementsByTagName("pIPI").getLength() > 0){
                        ipi.setpIPI(new BigDecimal(extrairConteudo(tagIPI.getElementsByTagName("pIPI"), "0")));
                    }
                    if(tagIPI.getElementsByTagName("qUnid").getLength() > 0){
                         ipi.setqUnid(new BigDecimal(extrairConteudo(tagIPI.getElementsByTagName("qUnid"), "0")));
                    }
                    if(tagIPI.getElementsByTagName("vUnid").getLength() > 0){
                        ipi.setvUnid(new BigDecimal(extrairConteudo(tagIPI.getElementsByTagName("vUnid"), "0")));
                    }
                    if(tagIPI.getElementsByTagName("vIPI").getLength() > 0){
                         ipi.setvIPI(new BigDecimal(extrairConteudo(tagIPI.getElementsByTagName("vIPI"), "0")));
                    }
                }
                nfeItem.setIPI(ipi);
                // </editor-fold>
                try{
                    Element infAdProd = (Element)detItem.getElementsByTagName("infAdProd").item(0);
                    nfeItem.setInfAdProd(infAdProd.getTextContent());
                }catch(Exception ex){
                    nfeItem.setInfAdProd("N/D");
                }
                listaItensNFe.add(nfeItem);
            }                     
           
            this.nfe.setItens(listaItensNFe);
            
            listaItensNFe.stream().forEach(i -> {
                i.setNFe(this.nfe);
            });
            
            this.arquivoNFeCarregado = true;
            
            return this.nfe;
        } catch(SAXException ex) {
            log.error(ex.getMessage());
            throw new ErroAoCarregarDadosNFeException(String.format("Houve uma exceção SAXException ao tentar carregar os dados do Xml '%s'", this.getCaminhoArquivoXmlNFe()), ex);
        } catch(IOException ex) {
            log.error(ex.getMessage());
            throw new ErroAoCarregarDadosNFeException(String.format("Houve uma exceção de I/O ao tentar ler o arquivo '%s'. %s", this.getCaminhoArquivoXmlNFe(), ex.getMessage()), ex);
        } catch(ParserConfigurationException ex) {
            throw new ErroAoCarregarDadosNFeException(String.format("Houve uma exceção de Parser ao tentar carregar os dados do Xml da NFe '%s'. Mensagem: %s", this.getCaminhoArquivoXmlNFe(), ex.getMessage()), ex);
        } catch(Exception ex) {
            log.error(ex.getMessage());
            throw new ErroAoCarregarDadosNFeException(String.format("Houve uma exceção de desconhecida ao carregar os dados do Xml da NFe '%s'. Mensagem: %s", this.getCaminhoArquivoXmlNFe(), ex.getMessage()), ex);
        }
    } 
    
    //SOLUÇÃO IMEDIATA :)  
    public NFe obterXmlNFe(File arquivo) throws ErroAoCarregarDadosNFeException {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document docXmlNFe = docBuilder.parse(arquivo);
        
            // Carrega os elementos que serão utilizados da NFe
            Element root = docXmlNFe.getDocumentElement();  
            
            if (!root.getNodeName().equals("nfeProc") && !root.getNodeName().equals("retDownloadNFe"))
                throw new Exception(String.format("O arquivo '%s' não é um arquivo NFe válido.", this.getCaminhoArquivoXmlNFe()));
            
            if (root.getNodeName().equals("retDownloadNFe"))
                root.getElementsByTagName("nfeProc");
            
            //root = this.identificarElementoRaiz(root);
            //root = this.identificarChave(root);
            
            Element dadosNFe = (Element)root.getElementsByTagName("infNFe").item(0);
            //Element dadosNFe = identificarChave(root);
            Element dadosIde = (Element)root.getElementsByTagName("ide").item(0);
            Element dadosEmitente = (Element)root.getElementsByTagName("emit").item(0);
            Element dadosDestinatario = (Element)root.getElementsByTagName("dest").item(0);
            Element dadosTotal = (Element)root.getElementsByTagName("total").item(0);
            NodeList itensNotaFiscal = root.getElementsByTagName("det");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            
            // Extrai os dados dos elementos   
            
            String dataHoraEmissao = extrairConteudo(dadosIde.getElementsByTagName("dhEmi"));
            if (dataHoraEmissao == null || dataHoraEmissao.isEmpty())
                dataHoraEmissao = extrairConteudo(dadosIde.getElementsByTagName("dEmi"));
            
            this.nfe = new NFe();
            
            this.nfe.setChaveNFe(dadosNFe.getAttribute("Id").replace("NFe", "")); 
            //this.nfe.setChaveNFe(this.retornarChave(root));
            
            this.nfe.setNumeroNotaFiscal(extrairConteudo(dadosIde.getElementsByTagName("nNF")));  
            //this.nfe.setNumeroNotaFiscal(retornarNumeroNota(dadosIde));
            this.nfe.setSerie(extrairConteudo(dadosIde.getElementsByTagName("serie")));       
            //this.nfe.setSerie(retornarSerieNota(dadosIde));
            
            this.nfe.setNatOp(extrairConteudo(dadosIde.getElementsByTagName("natOp")));
            try{
                this.nfe.setIndicadorPagamento(ETipoPagamento.deCodigo(extrairConteudo(dadosIde.getElementsByTagName("indPag"))));
            }catch(Exception ex){}
            this.nfe.setModelo(extrairConteudo(dadosIde.getElementsByTagName("mod")));
           // this.nfe.setSerie(extrairConteudo(dadosIde.getElementsByTagName("serie"))); // tag <serie>
            //this.nfe.setNumeroNotaFiscal(extrairConteudo(dadosIde.getElementsByTagName("cNF"))); // tag <nNF>
            try {
                this.nfe.setDataHoraEmissao(sdf.parse(dataHoraEmissao));
            } catch(Exception ex) {
                try {
                    this.nfe.setDataHoraEmissao(sdf2.parse(dataHoraEmissao));
                } catch(Exception ex2) {
                    log.error(String.format("Não foi possível realizar o Parse da data dhEmi/dEmi = '%s'", dataHoraEmissao), ex2);
                }
            }
            
            String dataHoraSaida = extrairConteudo(dadosIde.getElementsByTagName("dhSaiEnt"));
            if (!StringUtil.isNullOrEmpty(dataHoraSaida)) {
                try {
                    this.nfe.setDataHoraSaiEnt(sdf.parse(dataHoraSaida));
                } catch(Exception ex) {
                    try {
                        this.nfe.setDataHoraSaiEnt(sdf2.parse(dataHoraSaida));
                    } catch(Exception ex2) {
                        log.error(String.format("Não foi possível realizar o Parse da data dhSaiEnt = '%s'", dataHoraSaida), ex2);
                    }
                }
            }
            try{
                this.nfe.setTipoNotaFiscal(ETipoNotaFiscal.deCodigo(extrairConteudo(dadosIde.getElementsByTagName("tpNF"))));
            }catch(Exception ex){
            
            }
            try {
                this.nfe.setIdDestino(ELocalDestino.deCodigo(extrairConteudo(dadosIde.getElementsByTagName("idDest"))));            
            } catch(Exception ex) {
                log.error(String.format("Houve um erro ao tentar realizar o Parse de idDest = '%s'. Mensagem: %s", extrairConteudo(dadosIde.getElementsByTagName("tpNF")), ex.getMessage(), ex));
            }

            this.nfe.setEmitente(extrairPessoaJuridica(dadosEmitente));  
            try{
                this.nfe.setDestinatario(extrairPessoaJuridica(dadosDestinatario));
            }catch(Exception ex){}
            // <total>
            //      <ICMSTot>
            this.nfe.setValorTotalBCICMS(BigDecimal.ZERO); // <vBC>
            this.nfe.setValorTotalICMS(BigDecimal.ZERO); // <vICMS>
            this.nfe.setValorTotalICMSDesonerado(BigDecimal.ZERO); // <vICMSDeson>
            this.nfe.setValorTotalBCICMSST(BigDecimal.ZERO); // <vBCST>
            try { this.nfe.setValorTotalICMSST(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vST")))); } catch(Exception ex) {}
            this.nfe.setValorTotalProdServ(BigDecimal.ZERO); // <vProd>
            this.nfe.setValorTotalFrete(BigDecimal.ZERO); // <vFrete>
            this.nfe.setValorTotalSeguro(BigDecimal.ZERO); // <vSeg>
            this.nfe.setValorTotalDesconto(BigDecimal.ZERO); // <vDesc>
            this.nfe.setValorTotalII(BigDecimal.ZERO); // <vII>
            this.nfe.setValorTotalIPI(BigDecimal.ZERO); // <vIPI>
            this.nfe.setValorPIS(BigDecimal.ZERO); // <vPIS>
            this.nfe.setValorCOFINS(BigDecimal.ZERO); // <vCOFINS>
            this.nfe.setValorOutrasDespesas(BigDecimal.ZERO); // <vOutro>
            try { this.nfe.setValorNotaFiscal(new BigDecimal(extrairConteudo(dadosTotal.getElementsByTagName("vNF")))); } catch(Exception ex) {}
            this.nfe.setValorTotalTributos(BigDecimal.ZERO); // <vTotTrib>  
                        
            List<NFeItem> listaItensNFe = new ArrayList<>();

            for (int i = 0; i < itensNotaFiscal.getLength(); i++) {
                Element detItem = (Element)itensNotaFiscal.item(i);
                Element prod = (Element)detItem.getElementsByTagName("prod").item(0);
                Element imposto = (Element)detItem.getElementsByTagName("imposto").item(0);                
                NFeItem nfeItem = new NFeItem();
                
                nfeItem.setSequencia(i);
                nfeItem.setNumeroItem(Integer.parseInt(detItem.getAttribute("nItem")));
                nfeItem.setCodigoProduto(extrairConteudo(prod.getElementsByTagName("cProd")));
                nfeItem.setDescricaoProduto(extrairConteudo(prod.getElementsByTagName("xProd")));
                nfeItem.setCodigoEAN(this.corrigirEAN(extrairConteudo(prod.getElementsByTagName("cEAN"))));
                try{
                    nfeItem.setCodigoEANTrib(this.corrigirEAN(extrairConteudo(prod.getElementsByTagName("cEANTrib"))));
                }catch(Exception ex){nfeItem.setCodigoEANTrib("0");};
                nfeItem.setCodigoNCM(extrairConteudo(prod.getElementsByTagName("NCM")));
                nfeItem.setUnidade(extrairConteudo(prod.getElementsByTagName("uCom")));
                nfeItem.setQuantidade(new BigDecimal(extrairConteudo(prod.getElementsByTagName("qCom"))));
                nfeItem.setValorUnitario(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vUnCom"),"0")));
                nfeItem.setValorProduto(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vProd"),"0")));
                nfeItem.setValorFrete(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vFrete"), "0")));
                nfeItem.setValorSeguro(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vSeg"), "0")));
                nfeItem.setValorDesconto(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vDesc"), "0")));
                nfeItem.setValorOutros(new BigDecimal(extrairConteudo(prod.getElementsByTagName("vOutro"), "0")));
                nfeItem.setIndicaTotal(Integer.parseInt(extrairConteudo(prod.getElementsByTagName("intTot"), "0")));
                nfeItem.setCfop(extrairConteudo(prod.getElementsByTagName("CFOP")));
                
                // <editor-fold desc="ICMS" defaultstate="collapsed">
                Element tagICMS = null;
                if (imposto.getElementsByTagName("ICMS00").getLength() > 0) {
                    ICMS00 icms = new ICMS00();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS00").item(0);
                    icms.setModalidadeBaseCalculo(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBC"), "0")));
                    icms.setValorBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBC"), "0")));
                    icms.setAliquotaICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMS"), "0")));
                    icms.setValorICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMS"), "0")));
                    nfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS10").getLength() > 0) {
                    ICMS10 icms = new ICMS10();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS10").item(0);
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
                    nfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS20").getLength() > 0) {  
                    ICMS20 icms = new ICMS20();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS20").item(0);
                    icms.setModalidadeBaseCalculo(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBC"), "0")));//<modBc>
                    icms.setValorBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBC"), "0")));//<vBc>
                    icms.setAliquotaICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMS"), "0")));//<pIcms>
                    icms.setValorICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMS"), "0")));//<vIcms>
                    icms.setPercentualReducaoBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pRedBC"), "0")));//<pRedBc>
                    icms.setMotivoDesoneracaoICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("motDesICMS"), "0")));//<motDesIcms>
                    icms.setValorICMSDesoneracao(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSDeson"), "0")));//<vIcmsDeson>
                    nfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS30").getLength() > 0) {
                    ICMS30 icms = new ICMS30();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS30").item(0);
                    icms.setModalidadeBaseCalculoST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBC"), "0")));//<modBcSt>
                    icms.setValorBCST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBCST"), "0")));//<vBcSt>
                    icms.setAliquotaICMSST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMSST"), "0")));//<pIcmsSt>
                    icms.setValorICMSST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSST"), "0")));//<vIcmsSt>
                    icms.setPercentualMargemValorAdicionadoST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pMVAST"), "0")));//<pMvaSt>
                    icms.setMotivoDesoneracaoICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("motDesICMS"), "0")));//<motDesIcms>
                    icms.setValorICMSDesoneracao(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSDeson"), "0")));//<vIcmsDeson>;
                    icms.setPercentualReducaoBCST(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pRedBCST"), "0")));//<pRedBcSt> 
                    nfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS40").getLength() > 0 ||
                           imposto.getElementsByTagName("ICMS41").getLength() > 0 ||
                           imposto.getElementsByTagName("ICMS50").getLength() > 0) {
                    ICMS40 icms = null;
                    
                    if (imposto.getElementsByTagName("ICMS40").getLength() > 0) {
                        icms = new ICMS40();
                        tagICMS = (Element)imposto.getElementsByTagName("ICMS40").item(0);
                    }
                    else if (imposto.getElementsByTagName("ICMS41").getLength() > 0) {
                        icms = new ICMS41();
                        tagICMS = (Element)imposto.getElementsByTagName("ICMS41").item(0);
                    }
                    else if (imposto.getElementsByTagName("ICMS50").getLength() > 0) {
                        icms = new ICMS50();
                        tagICMS = (Element)imposto.getElementsByTagName("ICMS50").item(0);
                    }
                    
                    if (icms != null) {
                        ((ICMS40)icms).setMotivoDesoneracaoICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("motDesICMS"), "0")));//<motDesIcms>
                        ((ICMS40)icms).setValorICMSDesoneracao(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSDeson"), "0")));//<vIcmsDeson>
                    }
                    nfeItem.setICMS(icms);                
                } else if (imposto.getElementsByTagName("ICMS51").getLength() > 0) {
                    ICMS51 icms = new ICMS51();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS51").item(0);
                    icms.setModalidadeBaseCalculo(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("modBC"), "0")));//<modBc>
                    icms.setValorBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBC"), "0")));//<vBc>
                    icms.setAliquotaICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pICMS"), "0")));//<pIcms>
                    icms.setValorICMS(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMS"), "0")));//<vIcms>
                    icms.setPercentualReducaoBC(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pRedBC"), "0")));//<pPredBc>
                    icms.setPercentualDiferimento(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("pDif"), "0")));//<pDif>
                    icms.setValorICMSDiferimento(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSDif"), "0")));//<vIcmsDif>
                    icms.setValorICMSOperacao(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSOp"), "0")));//<vIcmsOp>;
                    nfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS60").getLength() > 0) {
                    ICMS60 icms = new ICMS60();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS60").item(0);
                    icms.setValorBCSTRet(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vBCSTRet"), "0")));
                    icms.setValorICMSSTRet(new BigDecimal(extrairConteudo(tagICMS.getElementsByTagName("vICMSSTRet"), "0")));
                    nfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS70").getLength() > 0) {
                    ICMS70 icms = new ICMS70();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS70").item(0);
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
                    nfeItem.setICMS(icms);
                } else if (imposto.getElementsByTagName("ICMS90").getLength() > 0) {
                    ICMS90 icms = new ICMS90();
                    tagICMS = (Element)imposto.getElementsByTagName("ICMS90").item(0);
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
                    nfeItem.setICMS(icms);
                }
                
                if (nfeItem.getICMS() != null && tagICMS != null) {
                    nfeItem.getICMS().setCST(Integer.parseInt(extrairConteudo(tagICMS.getElementsByTagName("CST"))));
                    nfeItem.getICMS().setOrigem(extrairConteudo(tagICMS.getElementsByTagName("orig")));
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
                    nfeItem.setPIS(pis);
                } else if (imposto.getElementsByTagName("PISQtde").getLength() > 0) {
                    PISQtde pis = new PISQtde();
                    tagPIS = (Element)imposto.getElementsByTagName("PISQtde").item(0);
                    pis.setQuantidadeBCProd(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("qBCProd"), "0")));
                    pis.setValorAliquotaProd(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("vAliqProd"), "0")));
                    pis.setValorPIS(new BigDecimal(extrairConteudo(tagPIS.getElementsByTagName("vPIS"), "0")));
                    nfeItem.setPIS(pis);
                } else if (imposto.getElementsByTagName("PISNT").getLength() > 0) {
                    PISNT pis = new PISNT();                    
                    tagPIS = (Element)imposto.getElementsByTagName("PISNT").item(0);
                    nfeItem.setPIS(pis);  
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
                    nfeItem.setPIS(pis);
                }   
                
                if (nfeItem.getPIS() != null && tagPIS != null)
                    nfeItem.getPIS().setCST(Integer.parseInt(extrairConteudo(tagPIS.getElementsByTagName("CST"))));
                // </editor-fold>
                
                // <editor-fold desc="COFINS" defaultstate="collapsed">
                Element tagCOFINS = null;
                if (imposto.getElementsByTagName("COFINSAliq").getLength() > 0) {
                    COFINSAliq cofins = new COFINSAliq();
                    tagCOFINS = (Element)imposto.getElementsByTagName("COFINSAliq").item(0);
                    cofins.setValorBC(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("vBC"), "0")));
                    cofins.setPercentualCOFINS(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("pCOFINS"), "0")));
                    cofins.setValorCOFINS(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("vCOFINS"), "0")));
                    nfeItem.setCOFINS(cofins);
                } else if (imposto.getElementsByTagName("COFINSQtde").getLength() > 0) {
                    COFINSQtde cofins = new COFINSQtde();
                    tagCOFINS = (Element)imposto.getElementsByTagName("COFINSQtde").item(0);
                    cofins.setQuantidadeBCProd(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("qBCProd"), "0")));
                    cofins.setValorAliquotaProd(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("vAliqProd"), "0")));
                    cofins.setValorCOFINS(new BigDecimal(extrairConteudo(tagCOFINS.getElementsByTagName("vCOFINS"), "0")));
                    nfeItem.setCOFINS(cofins);
                } else if (imposto.getElementsByTagName("COFINSNT").getLength() > 0) {
                    COFINSNT cofins = new COFINSNT();
                    tagCOFINS = (Element)imposto.getElementsByTagName("COFINSNT").item(0);                    
                    nfeItem.setCOFINS(cofins);
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
                    nfeItem.setCOFINS(cofins);
                }
                
                if (nfeItem.getCOFINS() != null && tagCOFINS != null)
                    nfeItem.getCOFINS().setCST(Integer.parseInt(extrairConteudo(tagCOFINS.getElementsByTagName("CST"))));
                // </editor-fold>
                
                listaItensNFe.add(nfeItem);
            }                     
           
            this.nfe.setItens(listaItensNFe);
            
            listaItensNFe.stream().forEach(i -> {
                i.setNFe(this.nfe);
            });
            
            this.arquivoNFeCarregado = true;
            
            return this.nfe;
        } catch(SAXException ex) {
            log.error(ex.getMessage());
            throw new ErroAoCarregarDadosNFeException(String.format("Houve uma exceção SAXException ao tentar carregar os dados do Xml '%s'", this.getCaminhoArquivoXmlNFe()), ex);
        } catch(IOException ex) {
            log.error(ex.getMessage());
            throw new ErroAoCarregarDadosNFeException(String.format("Houve uma exceção de I/O ao tentar ler o arquivo '%s'. %s", this.getCaminhoArquivoXmlNFe(), ex.getMessage()), ex);
        } catch(ParserConfigurationException ex) {
            throw new ErroAoCarregarDadosNFeException(String.format("Houve uma exceção de Parser ao tentar carregar os dados do Xml da NFe '%s'. Mensagem: %s", this.getCaminhoArquivoXmlNFe(), ex.getMessage()), ex);
        } catch(Exception ex) {
            log.error(ex.getMessage());
            throw new ErroAoCarregarDadosNFeException(String.format("Houve uma exceção de desconhecida ao carregar os dados do Xml da NFe '%s'. Mensagem: %s", this.getCaminhoArquivoXmlNFe(), ex.getMessage()), ex);
        }
    } 
    private String corrigirEAN(String ean){
        if(ean.length() > 13)
             ean = ean.substring(1);
       
        return ean;
    }
    
    public List<NFe> listarNFesComICMSSTDifTotal(String caminhoDirXmlsNFes) throws ErroAoCarregarDadosNFeException {
        Map<String, NFe> mapNFes = this.lerXmlsNFes(caminhoDirXmlsNFes);
        final List<NFe> lista = new ArrayList<>();
        /*
        mapNFes.forEach((chaveNFe, n) -> {            
            BigDecimal somaICMSST = BigDecimal.ZERO;
            
            for (NFeItem i : n.getItens()) {
                if (i.getValorICMSST() != null)
                    somaICMSST = somaICMSST.add(i.getValorICMSST());
            }
                
            if (somaICMSST.compareTo(BigDecimal.ZERO) == 1 &&
                n.getValorTotalICMSST().compareTo(somaICMSST) != 0)
                lista.add(n);
        });
        */
        return lista;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Privados">
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
            } catch(Exception ex) {log.error(ex.getMessage());}
        }
        if (el.getElementsByTagName("xBairro").getLength() > 0) 
            pj.setBairro(el.getElementsByTagName("xBairro").item(0).getTextContent());
        if (el.getElementsByTagName("cMun").getLength() > 0) {
            try {
                pj.setCodigoMunicipio(Integer.parseInt(el.getElementsByTagName("cMun").item(0).getTextContent()));
            } catch(Exception ex) {log.error(ex.getMessage());}
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
            } catch(Exception ex) {log.error(ex.getMessage());}
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
            } catch(Exception ex) {log.error(ex.getMessage());}
        }
        if (el.getElementsByTagName("CRT").getLength() > 0) {
            try {
                pj.setCRT(Integer.parseInt(el.getElementsByTagName("CRT").item(0).getTextContent()));
            } catch(Exception ex) {log.error(ex.getMessage());}
        }
        if (el.getElementsByTagName("indIEDest").getLength() > 0) {
            try {
                pj.setIndiIEDest(Integer.parseInt(el.getElementsByTagName("indIEDest").item(0).getTextContent()));
            } catch(Exception ex) {log.error(ex.getMessage());}
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
    // </editor-fold>
    
    private Element identificarElementoRaiz(Element root){
        // Carrega os elementos que serão utilizados da NFe
        try{
        if (!root.getNodeName().equals("nfeProc") && !root.getNodeName().equals("retDownloadNFe") && ! root.getNodeName().equals("CFe")){
                throw new Exception(String.format("O arquivo '%s' não é um arquivo NFe válido.", this.getCaminhoArquivoXmlNFe()));
        }
        if (root.getNodeName().equals("retDownloadNFe"))
            root.getElementsByTagName("nfeProc");
        
        if(root.getNodeName().equals("CFe")){
            root.getElementsByTagName("infCFe");
        }
       
        }catch(Exception ex){log.error(ex.getMessage());}
        return root;
    }
    
    private Element identificarChave(Element root){
        Element e = null;
        e = (Element)root.getElementsByTagName("infNFe").item(0);
        if(e == null)
            e = (Element)root.getElementsByTagName("infCFe").item(0);
        
        return root;
    }
    
    private String retornarChave(Element root){
        String id = root.getAttribute("Id").replace("NFe", "").replace("CFe", "");
        return id;
    }
    
    private String retornarNumeroNota(Element e){
        String numero = "";
        if(!StringUtil.isNullOrEmpty(this.extrairConteudo(e.getElementsByTagName("nNF")))){
            numero = this.extrairConteudo(e.getElementsByTagName("nNF"));
        }else{
            numero = this.extrairConteudo(e.getElementsByTagName("nCFe"));
        }
        return numero;
    }
    
    private String retornarSerieNota(Element e){
        String serie = "";
        if(StringUtil.isNullOrEmpty(this.extrairConteudo(e.getElementsByTagName(serie))))
            serie = this.extrairConteudo(e.getElementsByTagName("serie"));
        else
             serie = this.extrairConteudo(e.getElementsByTagName("nserieSAT"));
        
        return serie;
    }
}
