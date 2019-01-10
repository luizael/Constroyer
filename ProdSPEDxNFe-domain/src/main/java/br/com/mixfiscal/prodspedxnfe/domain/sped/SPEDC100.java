package br.com.mixfiscal.prodspedxnfe.domain.sped;

import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoPagamento;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoEmissao;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoFrete;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoNotaFiscal;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFe;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SPEDC100 extends SPEDBase implements Serializable {    
    private static final long serialVersionUID = -6859866143100359534L;    
        
    private ETipoNotaFiscal tipoNotaFiscal; // IND_OPER
    private ETipoEmissao tipoEmissao; // IND_EMIT    
    private String codFornecedor; // COD_PART
    private SPEDC150 fornecedor; 
    private String modeloDocumento; // COD_MOD
    private String codigoSituacao; // COD_SIT
    private String serie; // SER
    private String numeroNF; // NUM_DOC
    private String chaveNFe; // CHV_NFE
    private Date dataEmissao; // DT_DOC
    private Date dataEntradaSaida; // DT_E_S
    private BigDecimal valorTotalDocumento; // VL_DOC
    private ETipoPagamento tipoPagamento; // IND_PGTO
    private BigDecimal valorTotalDesconto; // VL_DESC
    private BigDecimal valorAbatNaoTribut; // VL_ABAT_NT
    private BigDecimal valorTotalMercadorias; // VL_MERC
    private ETipoFrete tipoFrete; // IND_FRT
    private BigDecimal valorFrete; // VL_FRT
    private BigDecimal valorSeguro; // VL_SEG
    private BigDecimal valorOutrasDespesas; // VL_OUT_DA
    private BigDecimal valorBCICMS; // VL_BC_ICMS
    private BigDecimal valorICMS; // VL_ICMS
    private BigDecimal valorBCICMSST; // VL_BC_ICMS_ST
    private BigDecimal valorICMSST; // VL_ICMS_ST
    private BigDecimal valorIPI; // VL_IPI
    private BigDecimal valorPIS; // VL_PIS
    private BigDecimal valorCOFINS; // VL_COFINS
    private BigDecimal valorPISST; // VL_PIS_ST
    private BigDecimal valorCOFINSST; // VL_COFINS_ST
    
    private final List<SPEDC170> listaSPEDC170;
    private final Map<String, SPEDC170> mapSPEDC170;
    private final List<SPEDC470> listaSPEDC470;
    private final List<SPEDC190> listaSPEDC190;
    
    private NFe xmlReferente;

    public SPEDC100() {
        listaSPEDC170 = new ArrayList<>();
        listaSPEDC470 = new ArrayList<>();
        mapSPEDC170 = new HashMap<>();
        listaSPEDC190 = new ArrayList<>();
        
        this.getIndicesColunas().put("tipoNotaFiscal", 2);
        this.getIndicesColunas().put("tipoEmissao", 3);
        this.getIndicesColunas().put("codFornecedor", 4);
        this.getIndicesColunas().put("modeloDocumento", 5);
        this.getIndicesColunas().put("codigoSituacao", 6);
        this.getIndicesColunas().put("serie", 7);
        this.getIndicesColunas().put("numeroNF", 8);        
        this.getIndicesColunas().put("chaveNFe", 9);        
        this.getIndicesColunas().put("dataEmissao", 10);
        this.getIndicesColunas().put("dataEntradaSaida", 11);
        this.getIndicesColunas().put("valorTotalDocumento", 12);
        this.getIndicesColunas().put("tipoPagamento", 13);
        this.getIndicesColunas().put("valorTotalDesconto", 14);
        this.getIndicesColunas().put("valorAbatNaoTribut", 15);
        this.getIndicesColunas().put("valorTotalMercadorias", 16);
        this.getIndicesColunas().put("tipoFrete", 17);
        this.getIndicesColunas().put("valorFrete", 18);
        this.getIndicesColunas().put("valorSeguro", 19);
        this.getIndicesColunas().put("valorOutrasDespesas", 20);
        this.getIndicesColunas().put("valorBCICMS", 21);
        this.getIndicesColunas().put("valorICMS", 22);
        this.getIndicesColunas().put("valorBCICMSST", 23);
        this.getIndicesColunas().put("valorICMSST", 24);
        this.getIndicesColunas().put("valorIPI", 25);
        this.getIndicesColunas().put("valorPIS", 26);
        this.getIndicesColunas().put("valorCOFINS", 27);
        this.getIndicesColunas().put("valorPISST", 28);
        this.getIndicesColunas().put("valorCOFINSST", 29);
    }
    
    // <editor-fold desc="Getters e Setters" defaultstate="collapsed">
    public ETipoNotaFiscal getTipoNotaFiscal() {
        return tipoNotaFiscal;
    }

    public void setTipoNotaFiscal(ETipoNotaFiscal tipoNotaFiscal) {
        this.tipoNotaFiscal = tipoNotaFiscal;
    }

    public ETipoEmissao getTipoEmissao() {
        return tipoEmissao;
    }

    public void setTipoEmissao(ETipoEmissao tipoEmissao) {
        this.tipoEmissao = tipoEmissao;
    }

    public String getCodFornecedor() {
        return codFornecedor;
    }

    public void setCodFornecedor(String codFornecedor) {
        this.codFornecedor = codFornecedor;
    }

    public SPEDC150 getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(SPEDC150 fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getModeloDocumento() {
        return modeloDocumento;
    }

    public void setModeloDocumento(String modeloDocumento) {
        this.modeloDocumento = modeloDocumento;
    }

    public String getCodigoSituacao() {
        return codigoSituacao;
    }

    public void setCodigoSituacao(String codigoSituacao) {
        this.codigoSituacao = codigoSituacao;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNumeroNF() {
        return numeroNF;
    }

    public void setNumeroNF(String numeroNF) {
        this.numeroNF = numeroNF;
    }

    public String getChaveNFe() {
        return chaveNFe;
    }

    public void setChaveNFe(String chaveNFe) {
        this.chaveNFe = chaveNFe;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataEntradaSaida() {
        return dataEntradaSaida;
    }

    public void setDataEntradaSaida(Date dataEntradaSaida) {
        this.dataEntradaSaida = dataEntradaSaida;
    }

    public BigDecimal getValorTotalDocumento() {
        return valorTotalDocumento;
    }

    public void setValorTotalDocumento(BigDecimal valorTotalDocumento) {
        this.valorTotalDocumento = valorTotalDocumento;
    }

    public ETipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(ETipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public BigDecimal getValorTotalDesconto() {
        return valorTotalDesconto;
    }

    public void setValorTotalDesconto(BigDecimal valorTotalDesconto) {
        this.valorTotalDesconto = valorTotalDesconto;
    }

    public BigDecimal getValorAbatNaoTribut() {
        return valorAbatNaoTribut;
    }

    public void setValorAbatNaoTribut(BigDecimal valorAbatNaoTribut) {
        this.valorAbatNaoTribut = valorAbatNaoTribut;
    }

    public BigDecimal getValorTotalMercadorias() {
        return valorTotalMercadorias;
    }

    public void setValorTotalMercadorias(BigDecimal valorTotalMercadorias) {
        this.valorTotalMercadorias = valorTotalMercadorias;
    }

    public ETipoFrete getTipoFrete() {
        return tipoFrete;
    }

    public void setTipoFrete(ETipoFrete tipoFrete) {
        this.tipoFrete = tipoFrete;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public BigDecimal getValorSeguro() {
        return valorSeguro;
    }

    public void setValorSeguro(BigDecimal valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    public BigDecimal getValorOutrasDespesas() {
        return valorOutrasDespesas;
    }

    public void setValorOutrasDespesas(BigDecimal valorOutrasDespesas) {
        this.valorOutrasDespesas = valorOutrasDespesas;
    }

    public BigDecimal getValorBCICMS() {
        return valorBCICMS;
    }

    public void setValorBCICMS(BigDecimal valorBCICMS) {
        this.valorBCICMS = valorBCICMS;
    }

    public BigDecimal getValorICMS() {
        return valorICMS;
    }

    public void setValorICMS(BigDecimal valorICMS) {
        this.valorICMS = valorICMS;
    }

    public BigDecimal getValorBCICMSST() {
        return valorBCICMSST;
    }

    public void setValorBCICMSST(BigDecimal valorBCICMSST) {
        this.valorBCICMSST = valorBCICMSST;
    }

    public BigDecimal getValorICMSST() {
        return valorICMSST;
    }

    public void setValorICMSST(BigDecimal valorICMSST) {
        this.valorICMSST = valorICMSST;
    }

    public BigDecimal getValorIPI() {
        return valorIPI;
    }

    public void setValorIPI(BigDecimal valorIPI) {
        this.valorIPI = valorIPI;
    }

    public BigDecimal getValorPIS() {
        return valorPIS;
    }

    public void setValorPIS(BigDecimal valorPIS) {
        this.valorPIS = valorPIS;
    }

    public BigDecimal getValorCOFINS() {
        return valorCOFINS;
    }

    public void setValorCOFINS(BigDecimal valorCOFINS) {
        this.valorCOFINS = valorCOFINS;
    }

    public BigDecimal getValorPISST() {
        return valorPISST;
    }

    public void setValorPISST(BigDecimal valorPISST) {
        this.valorPISST = valorPISST;
    }

    public BigDecimal getValorCOFINSST() {
        return valorCOFINSST;
    }

    public void setValorCOFINSST(BigDecimal valorCOFINSST) {
        this.valorCOFINSST = valorCOFINSST;
    }

    public List<SPEDC170> getListaSPEDC170() {
        return listaSPEDC170;
    }

    public NFe getXmlReferente() {
        return xmlReferente;
    }

    public void setXmlReferente(NFe xmlReferente) {
        this.xmlReferente = xmlReferente;
    }
    
    
    // </editor-fold>
    
    // <editor-fold desc="Métodos" defaultstate="collapsed">
    public void addSPEDC170(SPEDC170 spd) {
        this.listaSPEDC170.add(spd);
        this.mapSPEDC170.put(spd.getCodItem(), spd);
    }
    
    public SPEDC170 retornarSPEDC170PorCProd(String cProd) {
        return this.mapSPEDC170.get(cProd);
    }
    public void addSPEDC470(SPEDC470 spd){
        this.listaSPEDC470.add(spd);
    }
    
    public List<SPEDC470>getSPEDC470(){
        return this.listaSPEDC470;
    }
    
    public void addSPEDC190(SPEDC190 spd){
        this.listaSPEDC190.add(spd);
    }
    public List<SPEDC190>getSPEDC190(){
        return this.listaSPEDC190;
    }
    // </editor-fold>
    
    // <editor-fold desc="equals e hashCode" defaultstate="collapsed">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.tipoNotaFiscal);
        hash = 13 * hash + Objects.hashCode(this.tipoEmissao);
        hash = 13 * hash + Objects.hashCode(this.codFornecedor);
        hash = 13 * hash + Objects.hashCode(this.fornecedor);
        hash = 13 * hash + Objects.hashCode(this.modeloDocumento);
        hash = 13 * hash + Objects.hashCode(this.codigoSituacao);
        hash = 13 * hash + Objects.hashCode(this.serie);
        hash = 13 * hash + Objects.hashCode(this.numeroNF);
        hash = 13 * hash + Objects.hashCode(this.chaveNFe);
        hash = 13 * hash + Objects.hashCode(this.dataEmissao);
        hash = 13 * hash + Objects.hashCode(this.dataEntradaSaida);
        hash = 13 * hash + Objects.hashCode(this.valorTotalDocumento);
        hash = 13 * hash + Objects.hashCode(this.tipoPagamento);
        hash = 13 * hash + Objects.hashCode(this.valorTotalDesconto);
        hash = 13 * hash + Objects.hashCode(this.valorAbatNaoTribut);
        hash = 13 * hash + Objects.hashCode(this.valorTotalMercadorias);
        hash = 13 * hash + Objects.hashCode(this.tipoFrete);
        hash = 13 * hash + Objects.hashCode(this.valorFrete);
        hash = 13 * hash + Objects.hashCode(this.valorSeguro);
        hash = 13 * hash + Objects.hashCode(this.valorOutrasDespesas);
        hash = 13 * hash + Objects.hashCode(this.valorBCICMS);
        hash = 13 * hash + Objects.hashCode(this.valorICMS);
        hash = 13 * hash + Objects.hashCode(this.valorBCICMSST);
        hash = 13 * hash + Objects.hashCode(this.valorICMSST);
        hash = 13 * hash + Objects.hashCode(this.valorIPI);
        hash = 13 * hash + Objects.hashCode(this.valorPIS);
        hash = 13 * hash + Objects.hashCode(this.valorCOFINS);
        hash = 13 * hash + Objects.hashCode(this.valorPISST);
        hash = 13 * hash + Objects.hashCode(this.valorCOFINSST);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SPEDC100 other = (SPEDC100) obj;
        if (!Objects.equals(this.codFornecedor, other.codFornecedor)) {
            return false;
        }
        if (!Objects.equals(this.modeloDocumento, other.modeloDocumento)) {
            return false;
        }
        if (!Objects.equals(this.codigoSituacao, other.codigoSituacao)) {
            return false;
        }
        if (!Objects.equals(this.serie, other.serie)) {
            return false;
        }
        if (!Objects.equals(this.numeroNF, other.numeroNF)) {
            return false;
        }
        if (!Objects.equals(this.chaveNFe, other.chaveNFe)) {
            return false;
        }
        if (this.tipoNotaFiscal != other.tipoNotaFiscal) {
            return false;
        }
        if (this.tipoEmissao != other.tipoEmissao) {
            return false;
        }
        if (!Objects.equals(this.fornecedor, other.fornecedor)) {
            return false;
        }
        if (!Objects.equals(this.dataEmissao, other.dataEmissao)) {
            return false;
        }
        if (!Objects.equals(this.dataEntradaSaida, other.dataEntradaSaida)) {
            return false;
        }
        if (!Objects.equals(this.valorTotalDocumento, other.valorTotalDocumento)) {
            return false;
        }
        if (this.tipoPagamento != other.tipoPagamento) {
            return false;
        }
        if (!Objects.equals(this.valorTotalDesconto, other.valorTotalDesconto)) {
            return false;
        }
        if (!Objects.equals(this.valorAbatNaoTribut, other.valorAbatNaoTribut)) {
            return false;
        }
        if (!Objects.equals(this.valorTotalMercadorias, other.valorTotalMercadorias)) {
            return false;
        }
        if (this.tipoFrete != other.tipoFrete) {
            return false;
        }
        if (!Objects.equals(this.valorFrete, other.valorFrete)) {
            return false;
        }
        if (!Objects.equals(this.valorSeguro, other.valorSeguro)) {
            return false;
        }
        if (!Objects.equals(this.valorOutrasDespesas, other.valorOutrasDespesas)) {
            return false;
        }
        if (!Objects.equals(this.valorBCICMS, other.valorBCICMS)) {
            return false;
        }
        if (!Objects.equals(this.valorICMS, other.valorICMS)) {
            return false;
        }
        if (!Objects.equals(this.valorBCICMSST, other.valorBCICMSST)) {
            return false;
        }
        if (!Objects.equals(this.valorICMSST, other.valorICMSST)) {
            return false;
        }
        if (!Objects.equals(this.valorIPI, other.valorIPI)) {
            return false;
        }
        if (!Objects.equals(this.valorPIS, other.valorPIS)) {
            return false;
        }
        if (!Objects.equals(this.valorCOFINS, other.valorCOFINS)) {
            return false;
        }
        if (!Objects.equals(this.valorPISST, other.valorPISST)) {
            return false;
        }
        if (!Objects.equals(this.valorCOFINSST, other.valorCOFINSST)) {
            return false;
        }
        if (!Objects.equals(this.listaSPEDC170, other.listaSPEDC170)) {
            return false;
        }
        if (!Objects.equals(this.mapSPEDC170, other.mapSPEDC170)) {
            return false;
        }
        return true;
    }
    // </editor-fold>
}
