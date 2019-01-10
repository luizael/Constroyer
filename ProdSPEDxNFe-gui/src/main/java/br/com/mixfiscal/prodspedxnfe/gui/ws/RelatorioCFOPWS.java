package br.com.mixfiscal.prodspedxnfe.gui.ws;

import br.com.lulusoftwares.luluframework.jquery.JQueryGridParam;
import br.com.lulusoftwares.luluframework.jquery.JQueryGridData;
import br.com.lulusoftwares.luluframework.jquery.JQueryResponse;
import br.com.lulusoftwares.luluframework.jquery.JQueryResult;
import br.com.mixfiscal.prodspedxnfe.gui.util.Constantes;
import br.com.mixfiscal.prodspedxnfe.gui.backbean.IndexBackBean;
import br.com.mixfiscal.prodspedxnfe.gui.util.Utils;
import br.com.mixfiscal.prodspedxnfe.services.LeitorSpedFiscal;
import br.com.mixfiscal.prodspedxnfe.services.LeitorXmlNFe;
import br.com.mixfiscal.prodspedxnfe.services.ex.ErroAoCarregarDadosNFeException;
import br.com.mixfiscal.prodspedxnfe.util.Masc;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import com.google.gson.Gson;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/relCFOPWS")
public class RelatorioCFOPWS {
    @Context
    private HttpServletRequest request;
    @Context 
    private ServletContext servletContext;
    private Gson gson;
        
    public RelatorioCFOPWS() {
        gson = new Gson();
    }
    
    @POST
    @Path("/carregarSPED")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse carregarSPED(CarregarSPEDParam param) {
        JQueryResponse r = new JQueryResponse();
        
        try {
            IndexBackBean ibb = (IndexBackBean)request.getSession().getAttribute("indexBackBean");
            ibb.setCaminhoArquivoSPED(param.getCaminhoSPED());
            ibb.setCaminhoDirXMLsNFes(param.getCaminhoDirXMLs());
            
            LeitorSpedFiscal leitorSpd = new LeitorSpedFiscal();            
            carregarSPED(leitorSpd);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            CarregarSPEDRetorno ret = new CarregarSPEDRetorno();
            ret.setNome(leitorSpd.getSped0000().getNomeEmpresarialEntidade());
            ret.setCNPJ(Masc.mascararCNPJ(leitorSpd.getSped0000().getCNPJ()));
            ret.setDataIni(sdf.format(leitorSpd.getSped0000().getDataIni()));
            ret.setDataFin(sdf.format(leitorSpd.getSped0000().getDataFin()));
            request.getSession().setAttribute(Constantes.SESSION_LEITOR_SPD, ret);
            
            r.setMessage("Arquivo SPED carregado com sucesso");
            r.setObject(ret);
            r.setResult(JQueryResult.Success);            
        } catch(Exception ex) {
            r.returnException(ex);
        }
        
        return r;
    }
    
    @POST
    @Path("/listarCFOP")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryGridData<RelatorioCFOPItem> listarCFOP(JQueryGridParam jqParam) {
        List<RelatorioCFOPItem> lista = (List<RelatorioCFOPItem>)request.getSession().getAttribute(Constantes.SESSION_LISTA_REL_CFOP);               
        List<RelatorioCFOPItem> listaFiltrada = new ArrayList<>();
        RelatorioCFOPItem filtro = gson.fromJson(jqParam.getFilters(), RelatorioCFOPItem.class);
        
        lista.stream()
        .filter(i -> (
                ((!StringUtil.isNullOrEmpty(filtro.getChaveNFe()) && i.getChaveNFe().contains(filtro.getChaveNFe())) || StringUtil.isNullOrEmpty(filtro.getChaveNFe()))
                && ((!StringUtil.isNullOrEmpty(filtro.getNumeroNF()) && i.getNumeroNF().contains(filtro.getNumeroNF())) || StringUtil.isNullOrEmpty(filtro.getNumeroNF()))
                && ((!StringUtil.isNullOrEmpty(filtro.getCFOP()) && filtro.getCFOP().contains(i.getCFOP())) || StringUtil.isNullOrEmpty(filtro.getCFOP()))
                && ((!StringUtil.isNullOrEmpty(filtro.getCodProd()) && i.getCodProd().contains(filtro.getCodProd())) || StringUtil.isNullOrEmpty(filtro.getCodProd()))
                && ((!StringUtil.isNullOrEmpty(filtro.getCodBarras()) && i.getCodBarras().contains(filtro.getCodBarras())) || StringUtil.isNullOrEmpty(filtro.getCodBarras()))
                && ((!StringUtil.isNullOrEmpty(filtro.getNomeProduto()) && i.getNomeProduto().contains(filtro.getNomeProduto())) || StringUtil.isNullOrEmpty(filtro.getNomeProduto()))
                && ((!StringUtil.isNullOrEmpty(filtro.getNCM()) && i.getNCM().contains(filtro.getNCM())) || StringUtil.isNullOrEmpty(filtro.getNCM()))
                && ((!StringUtil.isNullOrEmpty(filtro.getCSTICMS()) && i.getCSTICMS().contains(filtro.getCSTICMS())) || StringUtil.isNullOrEmpty(filtro.getCSTICMS()))
                && ((!StringUtil.isNullOrEmpty(filtro.getCSTIPI()) && i.getCSTIPI().contains(filtro.getCSTIPI())) || StringUtil.isNullOrEmpty(filtro.getCSTIPI()))
                && ((!StringUtil.isNullOrEmpty(filtro.getCSTPIS()) && i.getCSTPIS().contains(filtro.getCSTPIS())) || StringUtil.isNullOrEmpty(filtro.getCSTPIS()))
                && ((!StringUtil.isNullOrEmpty(filtro.getCSTCOFINS()) && i.getCSTCOFINS().contains(filtro.getCSTCOFINS())) || StringUtil.isNullOrEmpty(filtro.getCSTCOFINS()))
                && ((!StringUtil.isNullOrEmpty(filtro.getUnidadeMedida()) && i.getUnidadeMedida().contains(filtro.getUnidadeMedida())) || StringUtil.isNullOrEmpty(filtro.getUnidadeMedida()))
        )).forEach(i -> {
            listaFiltrada.add(i);
        });
        
        request.getSession().setAttribute(Constantes.SESSION_LISTA_REL_CFOP_FILTRADA, listaFiltrada);
        
        List<RelatorioCFOPItem> listaPaginada = Utils.paginarLista(listaFiltrada, jqParam.getPage(), jqParam.getRows());        
        JQueryGridData<RelatorioCFOPItem> data = new JQueryGridData<>();
        data.setPage(jqParam.getPage());
        data.setRegPerPag(jqParam.getRows());
        data.setRows(listaPaginada);
        data.setRecords(listaFiltrada.size());        
        return data;
    }
    
    @POST
    @Path("/listarXmlProcessados")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryGridData<RelatorioXmlProcessadoItem> listarXmlProcessados(JQueryGridParam jqParam) {                
        List<RelatorioXmlProcessadoItem> lista = (List<RelatorioXmlProcessadoItem>)request.getSession().getAttribute(Constantes.SESSION_LISTA_XML_PROCESSADOS);
        List<RelatorioXmlProcessadoItem> listaPaginada = Utils.paginarLista(lista, jqParam.getPage(), jqParam.getRows());
        JQueryGridData<RelatorioXmlProcessadoItem> data = new JQueryGridData<>();
        data.setPage(jqParam.getPage());
        data.setRegPerPag(jqParam.getRows());
        data.setRows(lista);
        data.setRecords(lista.size());        
        return data;
    }
    
    @POST
    @Path("/selecionarItemRelCFOP")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse selecionarItemRelCFOP(SelecionarItemParam param) {
        JQueryResponse resp = new JQueryResponse();        
        
        try {            
            this.<RelatorioCFOPItem>selecionarItem(param, Constantes.SESSION_LISTA_REL_CFOP);
            resp.setResult(JQueryResult.Success);
        } catch(Exception ex) {
            resp.returnException(ex);
        }
        
        return resp;
    }
    
    @POST
    @Path("/selecionarItemXmlProc")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse selecionarItemXmlProc(SelecionarItemParam param) {
        JQueryResponse resp = new JQueryResponse();        
        
        try {            
            this.<RelatorioXmlProcessadoItem>selecionarItem(param, Constantes.SESSION_LISTA_XML_PROCESSADOS);
            resp.setResult(JQueryResult.Success);
        } catch(Exception ex) {
            resp.returnException(ex);
        }
        
        return resp;
    }
    
    // <editor-fold desc="Métodos Privados" defaultstate="collapsed">
    private <T extends RelatorioItem> void selecionarItem(SelecionarItemParam param, String nomeSessao) {
        List<T> lista = (List<T>)request.getSession().getAttribute(nomeSessao);
        Optional<T> item = lista.stream().filter(i -> i.getIndex() == param.getIndex()).findFirst();
        if (item.isPresent()) {                
            lista.stream().filter(i -> i.getChaveNFe().equals(item.get().getChaveNFe())).forEach(i -> {
                i.setSelecionado(param.isSelecionado());
            });
        }
    }
    
    private void carregarSPED(LeitorSpedFiscal leitorSpd) {
        List<RelatorioCFOPItem> lista = new ArrayList<>();
        IndexBackBean ibb = (IndexBackBean)request.getSession().getAttribute("indexBackBean");
        if (StringUtil.isNullOrEmpty(ibb.getCaminhoArquivoSPED()))
            return;
        
        try {            
            String serverPath = servletContext.getInitParameter(Constantes.PARAM_SERVER_PATH);        
            String caminhoSPED = String.format("%s%s", serverPath, Utils.removerPrefixo(ibb.getCaminhoArquivoSPED()));            
            final AtomicInteger index = new AtomicInteger(-1);
            
            //LeitorSpedFiscal leitorSpd = new LeitorSpedFiscal();
            
            leitorSpd.lerArquivoSped(caminhoSPED);            
           
            leitorSpd.getListaSPEDC100().stream().forEach(sc100 -> {
                sc100.getListaSPEDC170().stream().forEach(sc170 -> {
                    RelatorioCFOPItem item = new RelatorioCFOPItem();
                    item.setIndex(index.incrementAndGet());
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
                    lista.add(item);
                });
            });
            
            request.getSession().setAttribute(Constantes.SESSION_LISTA_REL_CFOP, lista);
            
            carregarXmlProcessados();
        } catch(Exception ex) {
            
        }
    }
    
    private void carregarXmlProcessados() throws ErroAoCarregarDadosNFeException {
        IndexBackBean ibb = (IndexBackBean)request.getSession().getAttribute("indexBackBean");
        LeitorXmlNFe leitorXml = new LeitorXmlNFe();                
        String serverPath = servletContext.getInitParameter(Constantes.PARAM_SERVER_PATH);        
        String caminhoDirXMLs = String.format("%s%s", serverPath, Utils.removerPrefixo(ibb.getCaminhoDirXMLsNFes()));
        final List<RelatorioXmlProcessadoItem> lista = new ArrayList<>();
        final AtomicInteger contador = new AtomicInteger(-1);
        
        leitorXml.listarNFesComICMSSTDifTotal(caminhoDirXMLs).stream().forEach(nfe -> {
            RelatorioXmlProcessadoItem item = new RelatorioXmlProcessadoItem();
            item.setIndex(contador.incrementAndGet());
            item.setChaveNFe(nfe.getChaveNFe());
            item.setRazaoSocialEmitente(nfe.getEmitente().getRazaoSocial());
            item.setRazaoSocialDestinatario(nfe.getDestinatario().getRazaoSocial());
            // TODO rever esse método
            //item.setValorNotaFiscal(new BigDecimal(nfe.getValorNotaFiscal()));
            lista.add(item);
        });
        
        try {
            request.getSession().setAttribute(Constantes.SESSION_LISTA_XML_PROCESSADOS, lista);
        } catch(Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    // </editor-fold>
    
    // <editor-fold desc="classes internas" defaultstate="collapsed">
    public static class CarregarSPEDParam {
        private String caminhoSPED;
        private String caminhoDirXMLs;

        public CarregarSPEDParam() {}

        public String getCaminhoSPED() {
            return caminhoSPED;
        }

        public void setCaminhoSPED(String caminhoSPED) {
            this.caminhoSPED = caminhoSPED;
        }

        public String getCaminhoDirXMLs() {
            return caminhoDirXMLs;
        }

        public void setCaminhoDirXMLs(String caminhoDirXMLs) {
            this.caminhoDirXMLs = caminhoDirXMLs;
        }
    }

    public static class CarregarSPEDRetorno {
        private String nome;
        private String CNPJ;
        private String dataIni;
        private String dataFin;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCNPJ() {
            return CNPJ;
        }

        public void setCNPJ(String CNPJ) {
            this.CNPJ = CNPJ;
        }

        public String getDataIni() {
            return dataIni;
        }

        public void setDataIni(String dataIni) {
            this.dataIni = dataIni;
        }

        public String getDataFin() {
            return dataFin;
        }

        public void setDataFin(String dataFin) {
            this.dataFin = dataFin;
        }
    }
    
    public static class SelecionarItemParam {
        private int index;
        private boolean selecionado;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public boolean isSelecionado() {
            return selecionado;
        }

        public void setSelecionado(boolean selecionado) {
            this.selecionado = selecionado;
        }
    }
    
    public static class RelatorioXmlProcessadoItem extends RelatorioItem implements Serializable {
        private String razaoSocialEmitente;
        private String razaoSocialDestinatario;
        private BigDecimal valorNotaFiscal;

        public String getRazaoSocialEmitente() {
            return razaoSocialEmitente;
        }

        public void setRazaoSocialEmitente(String razaoSocialEmitente) {
            this.razaoSocialEmitente = razaoSocialEmitente;
        }

        public String getRazaoSocialDestinatario() {
            return razaoSocialDestinatario;
        }

        public void setRazaoSocialDestinatario(String razaoSocialDestinatario) {
            this.razaoSocialDestinatario = razaoSocialDestinatario;
        }

        public BigDecimal getValorNotaFiscal() {
            return valorNotaFiscal;
        }

        public void setValorNotaFiscal(BigDecimal valorNotaFiscal) {
            this.valorNotaFiscal = valorNotaFiscal;
        }
    }
    
    public static class RelatorioCFOPItem extends RelatorioItem implements Serializable {
        private String CFOP;
        private String codProd;
        private String codBarras;
        private String nomeProduto;
        private String NCM;
        private String CSTICMS;
        private String CSTIPI;
        private String CSTPIS;
        private String CSTCOFINS;        
        private String unidadeMedida;
        private BigDecimal qtd;
        private BigDecimal valorUnitario;
        private BigDecimal valorTotal;        

        public String getCFOP() {
            return CFOP;
        }

        public void setCFOP(String CFOP) {
            this.CFOP = CFOP;
        }

        public String getCodProd() {
            return codProd;
        }

        public void setCodProd(String codProd) {
            this.codProd = codProd;
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
        
        public String getUnidadeMedida() {
            return unidadeMedida;
        }

        public void setUnidadeMedida(String unidadeMedida) {
            this.unidadeMedida = unidadeMedida;
        }

        public BigDecimal getQtd() {
            return qtd;
        }

        public void setQtd(BigDecimal qtd) {
            this.qtd = qtd;
        }

        public BigDecimal getValorUnitario() {
            return valorUnitario;
        }

        public void setValorUnitario(BigDecimal valorUnitario) {
            this.valorUnitario = valorUnitario;
        }

        public BigDecimal getValorTotal() {
            return valorTotal;
        }

        public void setValorTotal(BigDecimal valorTotal) {
            this.valorTotal = valorTotal;
        }
    }
    
    public static abstract class RelatorioItem {
        private boolean selecionado;
        private int index;
        private String chaveNFe;
        private String numeroNF;

        public boolean isSelecionado() {
            return selecionado;
        }

        public void setSelecionado(boolean selecionado) {
            this.selecionado = selecionado;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getChaveNFe() {
            return chaveNFe;
        }

        public void setChaveNFe(String chaveNFe) {
            this.chaveNFe = chaveNFe;
        }

        public String getNumeroNF() {
            return numeroNF;
        }

        public void setNumeroNF(String numeroNF) {
            this.numeroNF = numeroNF;
        }
    }
    // </editor-fold>
}
