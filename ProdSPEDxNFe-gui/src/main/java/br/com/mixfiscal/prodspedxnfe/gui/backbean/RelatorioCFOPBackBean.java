package br.com.mixfiscal.prodspedxnfe.gui.backbean;

import br.com.mixfiscal.prodspedxnfe.gui.util.Constantes;
import br.com.mixfiscal.prodspedxnfe.gui.util.Utils;
import br.com.mixfiscal.prodspedxnfe.gui.ws.RelatorioCFOPWS.CarregarSPEDRetorno;
import br.com.mixfiscal.prodspedxnfe.gui.ws.RelatorioCFOPWS.RelatorioCFOPItem;
import br.com.mixfiscal.prodspedxnfe.gui.ws.RelatorioCFOPWS.RelatorioItem;
import br.com.mixfiscal.prodspedxnfe.services.LeitorSpedFiscal;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import java.util.function.Predicate;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ManagedBean(name = "relCFOPBackBean")
@SessionScoped
public class RelatorioCFOPBackBean {
    ///<editor-fold desc="Membros Privados" defaultstate="collapsed">
    private LeitorSpedFiscal leitorSpd;
    private List<RelatorioCFOPItem> listaRelatorioCFOPItemOriginal;
    private List<RelatorioCFOPItem> listaRelatorioCFOPItem;  
    private Logger log;
    ///</editor-fold>
    
    // <editor-fold desc="Membros Privados Getter e Setters" defaultstate="collapsed">
    @ManagedProperty(value = "#{indexBackBean}")
    private IndexBackBean indexBackBean;      
    private String chaveNFe;
    private String numNF;
    private String cfop;
    private String codProduto;
    private String codBarras;
    private String nomeProduto;
    private String NCM;
    private String CSTICMS;
    private String CSTIPI;
    private String CSTPIS;
    private String CSTCOFINS;
    private String unidMedida;
    private boolean mostrarFiltro;
    private String mensagem;
    // </editor-fold>
    
    public RelatorioCFOPBackBean() {
        this.leitorSpd = new LeitorSpedFiscal();
        this.listaRelatorioCFOPItemOriginal = new ArrayList<>();
        this.listaRelatorioCFOPItem = new ArrayList<>(); 
        this.log = LogManager.getLogger(RelatorioCFOPBackBean.class);
    }

    @PostConstruct
    public void init() {
        if (this.indexBackBean != null) {
            this.leitorSpd = this.indexBackBean.getProcessadorSPED().getLeitorSped();        
            carregarSPED();
        }
    }
    
    // <editor-fold desc="Getters e Setters" defaultstate="collapsed">
    public void setIndexBackBean(IndexBackBean indexBackBean) {
        this.indexBackBean = indexBackBean;
    }   

    public String getCaminhoArquivoSPED() {
        return this.indexBackBean.getCaminhoArquivoSPED();
    }
    public void setCaminhoArquivoSPED(String caminhoArquivoSPED) {
        this.indexBackBean.setCaminhoArquivoSPED(caminhoArquivoSPED);
    }
    
    public String getCaminhoDirXMLsNFes() {
        return this.indexBackBean.getCaminhoDirXMLsNFes();
    }
    
    public void setCaminhoDirXMLsNFes(String caminhoDirXMLsNFes) {
        this.indexBackBean.setCaminhoDirXMLsNFes(caminhoDirXMLsNFes);
    }
    
    public String getChaveNFe() {
        return chaveNFe;
    }

    public void setChaveNFe(String chaveNFe) {
        this.chaveNFe = chaveNFe;
    }

    public String getNumNF() {
        return numNF;
    }

    public void setNumNF(String numNF) {
        this.numNF = numNF;
    }
    
    public String getCfop() {
        return cfop;
    }

    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

    public String getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(String codProduto) {
        this.codProduto = codProduto;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getNCM() {
        return NCM;
    }

    public void setNCM(String NCM) {
        this.NCM = NCM;
    }

    public String getCSTICMS() {
        return CSTICMS;
    }

    public void setCSTICMS(String CSTICMS) {
        this.CSTICMS = CSTICMS;
    }

    public String getCSTIPI() {
        return CSTIPI;
    }

    public void setCSTIPI(String CSTIPI) {
        this.CSTIPI = CSTIPI;
    }

    public String getCSTPIS() {
        return CSTPIS;
    }

    public void setCSTPIS(String CSTPIS) {
        this.CSTPIS = CSTPIS;
    }

    public String getCSTCOFINS() {
        return CSTCOFINS;
    }

    public void setCSTCOFINS(String CSTCOFINS) {
        this.CSTCOFINS = CSTCOFINS;
    }

    public String getUnidMedida() {
        return unidMedida;
    }

    public void setUnidMedida(String unidMedida) {
        this.unidMedida = unidMedida;
    }

    public boolean isMostrarFiltro() {
        return mostrarFiltro;
    }

    public void setMostrarFiltro(boolean mostrarFiltro) {
        this.mostrarFiltro = mostrarFiltro;
    }
    
    public LeitorSpedFiscal getLeitorSpd() {
        return this.leitorSpd;
    }

    public void setLeitorSpd(LeitorSpedFiscal leitorSpd) {
        this.leitorSpd = leitorSpd;
    }
    
    public List<RelatorioCFOPItem> getListaRelatorioCFOPItem() {
        return listaRelatorioCFOPItem;
    }

    public void setListaRelatorioCFOPItem(List<RelatorioCFOPItem> listaRelatorioCFOPItem) {
        this.listaRelatorioCFOPItem = listaRelatorioCFOPItem;
    }
    
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    // </editor-fold>
    
    // <editor-fold desc="Métodos Públicos" defaultstate="collpased">
    public void lnkExportarCsv() throws Exception {
        if (this.listaRelatorioCFOPItem == null)
            return;
        
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            StringBuilder sbCsv = new StringBuilder();
            List<RelatorioCFOPItem> lista = (List<RelatorioCFOPItem>)request.getSession().getAttribute(Constantes.SESSION_LISTA_REL_CFOP_FILTRADA);            
            CarregarSPEDRetorno header = (CarregarSPEDRetorno)request.getSession().getAttribute(Constantes.SESSION_LEITOR_SPD);
            
            this.mensagem = "";
            
            sbCsv.append(String.format("Nome: %s;\r\n", header.getNome()));
            sbCsv.append(String.format("CNPJ: %s;\r\n", header.getCNPJ()));
            sbCsv.append(String.format("Período: %s a %s;\r\n", header.getDataIni(), header.getDataFin()));
            sbCsv.append("\r\n");
            sbCsv.append("Chave NFe;Número NF;CFOP;Cod. Prod.;Cod. Barras;Nome do Produto;NCM;CST ICMS;CST IPI;CST PIS;CST COFINS;Unidade de Medida;Qtd;Valor Total\r\n");
            lista.stream().forEach(item -> {            
                sbCsv.append(String.join(";", "'" + item.getChaveNFe(), "'" + item.getNumeroNF(), "'" + item.getCFOP(), "'" + item.getCodProd(), 
                                         "'" + item.getCodBarras(), item.getNomeProduto(), "'" + item.getNCM(), "'" + item.getCSTICMS(),
                                         "'" + item.getCSTIPI(), "'" + item.getCSTPIS(), "'" + item.getCSTCOFINS(), item.getUnidadeMedida(),
                                         NumberFormat.getCurrencyInstance().format(item.getQtd()), NumberFormat.getCurrencyInstance().format(item.getValorTotal())));
                sbCsv.append("\r\n");            
            });

            Utils.forcarDownload("text/csv", sbCsv.toString().getBytes(), "RelatorioCFOP.csv");
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            this.mensagem = ex.getMessage();
        }
    }
    
    public void lnkBaixarXMls() throws Exception {
        this.baixarXmlsSelecionados(Constantes.SESSION_LISTA_REL_CFOP);
    }
    
    public void lnkXmlProcessadosExportarCsv() {
    }
    
    public void lnkXmlProcessadosBaixarXMLs() {
        this.baixarXmlsSelecionados(Constantes.SESSION_LISTA_XML_PROCESSADOS);
    }
    
    public void btBuscar() {
        if (this.listaRelatorioCFOPItem == null)
            return;
                
        this.listaRelatorioCFOPItem.clear();
        
        this.listaRelatorioCFOPItemOriginal.stream()
                .filter(i -> (((!StringUtil.isNullOrEmpty(this.chaveNFe) && i.getChaveNFe().contains(this.chaveNFe)) || StringUtil.isNullOrEmpty(this.chaveNFe)) 
                              && ((!StringUtil.isNullOrEmpty(this.numNF) && i.getNumeroNF().contains(this.numNF)) || StringUtil.isNullOrEmpty(this.numNF))
                              && ((!StringUtil.isNullOrEmpty(this.cfop) && i.getCFOP().contains(this.cfop)) || StringUtil.isNullOrEmpty(this.cfop))
                              && ((!StringUtil.isNullOrEmpty(this.codProduto) && i.getCodProd().contains(this.codProduto)) || StringUtil.isNullOrEmpty(this.codProduto))
                              && ((!StringUtil.isNullOrEmpty(this.codBarras) && i.getCodBarras().contains(this.codBarras)) || StringUtil.isNullOrEmpty(this.codBarras))
                              && ((!StringUtil.isNullOrEmpty(this.nomeProduto) && i.getNomeProduto().contains(this.nomeProduto)) || StringUtil.isNullOrEmpty(this.nomeProduto))
                              && ((!StringUtil.isNullOrEmpty(this.NCM) && i.getNCM().contains(this.NCM)) || StringUtil.isNullOrEmpty(this.NCM))
                              && ((!StringUtil.isNullOrEmpty(this.CSTICMS) && i.getCSTICMS().contains(this.CSTICMS)) || StringUtil.isNullOrEmpty(this.CSTICMS))
                              && ((!StringUtil.isNullOrEmpty(this.CSTIPI) && i.getCSTIPI().contains(this.CSTIPI)) || StringUtil.isNullOrEmpty(this.CSTIPI))
                              && ((!StringUtil.isNullOrEmpty(this.CSTPIS) && i.getCSTPIS().contains(this.CSTPIS)) || StringUtil.isNullOrEmpty(this.CSTPIS))
                              && ((!StringUtil.isNullOrEmpty(this.CSTCOFINS) && i.getCSTCOFINS().contains(this.CSTCOFINS)) || StringUtil.isNullOrEmpty(this.CSTCOFINS))
                              && ((!StringUtil.isNullOrEmpty(this.unidMedida) && i.getUnidadeMedida().contains(this.unidMedida)) || StringUtil.isNullOrEmpty(this.unidMedida))
                            ))
                .forEach(i -> {
                    this.listaRelatorioCFOPItem.add(i);
                });
    }
    
    public void btCarregarSPED() {
        carregarSPED();
    }
    // </editor-fold>
    
    // <editor-fold desc="Métodos Privados" defaultstate="collapsed">
    private <T extends RelatorioItem> void baixarXmlsSelecionados(String nomeSessao) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)ctx.getExternalContext().getRequest();
        List<T> lista = (List<T>)request.getSession().getAttribute(nomeSessao);
        
        if (lista == null)
            return;
        
        try {            
            String serverPath = ctx.getExternalContext().getInitParameter(Constantes.PARAM_SERVER_PATH);                
            String caminhoXmls = String.format("%s%s", serverPath, Utils.removerPrefixo(this.getCaminhoDirXMLsNFes()));
            File dir = new File(caminhoXmls);
            String nomeArquivoZip = "XMLs.zip";
            List<File> arquivos = new ArrayList<>(Arrays.asList(dir.listFiles()));
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            final ZipOutputStream zos = new ZipOutputStream(bos);          
            final Set<String> setAqvsNEncontrados = new HashSet<>();

            this.mensagem = "";    

            try {        
                lista.stream().filter(i -> i.isSelecionado()).forEach(i -> {
                    Optional<File> arquivosEncontrados = arquivos.stream()
                                                                 .filter(a -> a.getName().contains(i.getChaveNFe()))
                                                                 .findFirst();
                    if (!arquivosEncontrados.isPresent())                    
                        setAqvsNEncontrados.add(String.format("%s\r\n", i.getChaveNFe()));
                    else {
                        File f = arquivosEncontrados.get();
                        FileInputStream finstr = null;
                        ZipEntry zentry = null;

                        try {                    
                            finstr = new FileInputStream(f);
                            addZipEntry(zos, f.getName(), finstr);                        
                        } catch (ZipException zex) {
                        } catch (Exception ex) {
                            this.mensagem += ex.getMessage() + "<br/>";
                        } finally {
                            try {
                                zos.closeEntry();
                                finstr.close();
                            } catch(IOException ex) {
                                this.mensagem += ex.getMessage() + "<br/>";
                            }
                        }
                    }
                });   

                if (setAqvsNEncontrados.size() > 0) {
                    StringBuilder sbAqvsNEncontrados = new StringBuilder();
                    setAqvsNEncontrados.stream().forEach(s -> { sbAqvsNEncontrados.append(s); });
                    ByteArrayInputStream bisAne = new ByteArrayInputStream(sbAqvsNEncontrados.toString().getBytes());
                    addZipEntry(zos, "XMLsNaoEncontrados.txt", bisAne);
                }         
            } finally {        
                zos.close();
                bos.close();        
            }

            Utils.forcarDownload("application/zip", bos.toByteArray(), nomeArquivoZip);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
    
    private void addZipEntry(ZipOutputStream zos, String nomeEntrada, InputStream is) throws Exception {                
        ZipEntry zentry = new ZipEntry(nomeEntrada);
        zos.putNextEntry(zentry);                    
        byte[] bytes = new byte[1024];
        int length;
        while ((length = is.read(bytes)) >= 0)
            zos.write(bytes, 0, length);
    }
    // </editor-fold>
    
    // <editor-fold desc="Métodos Privados" defaultstate="collapsed">
    private void carregarSPED() {
        if (StringUtil.isNullOrEmpty(this.getCaminhoArquivoSPED()))
            return;
        
        try {
            FacesContext cx = FacesContext.getCurrentInstance(); 
            FacesContext ctx = FacesContext.getCurrentInstance();
            String serverPath = ctx.getExternalContext().getInitParameter(Constantes.PARAM_SERVER_PATH);        
            String caminhoSPED = String.format("%s%s", serverPath, Utils.removerPrefixo(this.getCaminhoArquivoSPED()));
            
            this.leitorSpd.lerArquivoSped(caminhoSPED);
            this.listaRelatorioCFOPItemOriginal.clear();
            this.listaRelatorioCFOPItem.clear();
            this.mensagem = "";
            
            this.leitorSpd.getListaSPEDC100().stream().forEach(sc100 -> {
                sc100.getListaSPEDC170().stream().forEach(sc170 -> {
                    RelatorioCFOPItem item = new RelatorioCFOPItem();
                    item.setChaveNFe(sc100.getChaveNFe());
                    item.setNumeroNF(sc100.getNumeroNF());
                    item.setCFOP(sc170.getCFOP());
                    item.setCodProd(sc170.getSPED0200().getCodItem());
                    item.setCodBarras(sc170.getSPED0200().getCodBarra());
                    item.setNomeProduto(sc170.getSPED0200().getDescrItem());
                    item.setNCM(sc170.getSPED0200().getCodNCM());
                    item.setCSTICMS(sc170.getCSTICMS());
                    item.setCSTIPI(sc170.getCSTIPI());
                    item.setCSTPIS(sc170.getCSTPIS());
                    item.setCSTCOFINS(sc170.getCSTCOFINS());
                    item.setUnidadeMedida(sc170.getUnid());
                    item.setQtd(sc170.getQtd());
                    item.setValorTotal(sc170.getValor());
                    //this.listaRelatorioCFOPItemOriginal.add(item);
                    //this.listaRelatorioCFOPItem.add(item);
                });
            });            
        } catch(Exception ex) {
            mensagem = "Houve um erro ao tentar realzar a busca desejada. Mensagem: " + ex.getMessage();
        }
    }
    // </editor-fold>    
}
