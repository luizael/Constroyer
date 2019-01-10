package br.com.mixfiscal.prodspedxnfe.domain.sped;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class SPEDC170 extends SPEDBase implements Serializable {

    private static final long serialVersionUID = -8161805449579758729L;
        
    private int sequencia;
    private String numItem; // NUM_ITEM
    private String codItem; // COD_ITEM
    private String descricaoComplementar; // DESCR_COMPL
    private BigDecimal qtd; // QTD
    private String unid; // UNID
    private BigDecimal valor; // VL_ITEM
    private BigDecimal valorDesconto; // VL_DESC
    private boolean indMovimentacao; // IND_MOV     
    private String CSTICMS; // CST_ICMS
    private String CFOP; // CFOP
    private String codigoNatureza; // COD_NAT
    private BigDecimal valorBCICMS; // VL_BC_ICMS
    private BigDecimal aliqICMS; // ALIQ_ICMS
    private BigDecimal valorICMS; // VL_ICMS
    private BigDecimal valorBCICMSST; // VL_BC_ICMS_ST    
    private BigDecimal aliqICMSST; // ALIQ_ST
    private BigDecimal valorICMSST; // VL_ICMS_ST
    private BigDecimal valorBCSTRet; // vBCSTRet
    private String indicadorApuracao; // IND_APUR
    private String CSTIPI; // CST_IPI
    private String codEnquadramentoIPI; // COD_ENQ
    private BigDecimal valorBCIPI; // VL_BC_IPI
    private BigDecimal aliqIPI; // ALIQ_IPI
    private BigDecimal valorIPI; // VL_IPI    
    private String CSTPIS; // CST_PIS
    private BigDecimal valorBCPIS; // VL_BC_PIS
    private BigDecimal aliqPISPercentual; // ALIQ_PIS
    private BigDecimal quantidadeBCPIS; // QUANT_BC_PIS
    private BigDecimal aliqPISReais; // ALIQ_PIS
    private BigDecimal valorPIS; // VL_PIS    
    private String CSTCOFINS; // CST_COFINS
    private BigDecimal valorBCCOFINS; // VL_BC_CONFINS
    private BigDecimal aliqCOFINSPercentual; // ALIQ_COFINS
    private BigDecimal quantidadeBCCOFINS; // QUANT_BC_COFINS
    private BigDecimal aliqCOFINSReais; // ALIQ_COFINS
    private BigDecimal valorCOFINS; // VL_COFINS
    private String codigoCTA; // COD_CTA
    private SPED0200 SPED0200;   
    private SPEDC100 SPEDC100;
    private String filtroDeRelacionamento; 
    private BigDecimal idReferenciaComNFe;
    private String linhaOriginal;
    private String linhaComModificacoes;

    public SPEDC170() {
        this.getIndicesColunas().put("NumItem", 2);
        this.getIndicesColunas().put("CodItem", 3);
        this.getIndicesColunas().put("DescricaoComplementar", 4);        
        this.getIndicesColunas().put("Qtd", 5);
        this.getIndicesColunas().put("Unid", 6);        
        this.getIndicesColunas().put("Valor", 7);
        this.getIndicesColunas().put("ValorDesconto", 8);
        this.getIndicesColunas().put("IndMovimentacao", 9);
        this.getIndicesColunas().put("CSTICMS", 10);
        this.getIndicesColunas().put("CFOP", 11);
        this.getIndicesColunas().put("CodigoNatureza", 12);
        this.getIndicesColunas().put("ValorBCICMS", 13);
        this.getIndicesColunas().put("AliqICMS", 14);
        this.getIndicesColunas().put("ValorICMS", 15);
        this.getIndicesColunas().put("ValorBCICMSST", 16);
        this.getIndicesColunas().put("AliqICMSST", 17);
        this.getIndicesColunas().put("ValorICMSST", 18);
        this.getIndicesColunas().put("IndicadorApuracao", 19);
        this.getIndicesColunas().put("CSTIPI", 20);
        this.getIndicesColunas().put("CodEnquadramentoIPI", 21);
        this.getIndicesColunas().put("ValorBCIPI", 22);
        this.getIndicesColunas().put("AliqIPI", 23);
        this.getIndicesColunas().put("ValorIPI", 24);
        this.getIndicesColunas().put("CSTPIS", 25);
        this.getIndicesColunas().put("ValorBCPIS", 26);
        this.getIndicesColunas().put("AliqPISPercentual", 27);
        this.getIndicesColunas().put("QuantidadeBCPIS", 28);
        this.getIndicesColunas().put("AliqPISReais", 29);
        this.getIndicesColunas().put("ValorPIS", 30);
        this.getIndicesColunas().put("CSTCOFINS", 31);
        this.getIndicesColunas().put("ValorBCCOFINS", 32);
        this.getIndicesColunas().put("AliqCOFINSPercentual", 33);
        this.getIndicesColunas().put("QuantidadeBCCOFINS", 34);
        this.getIndicesColunas().put("AliqCOFINSReais", 35);
        this.getIndicesColunas().put("ValorCOFINS", 36);
        this.getIndicesColunas().put("CodigoCTA", 37);
    }
    
    // <editor-fold desc="Getters e Setters" defaultstate="collapsed">
    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public String getNumItem() {
        return numItem;
    }

    public void setNumItem(String numItem) {
        this.numItem = numItem;
    }

    public String getCodItem() {
        return codItem;
    }

    public void setCodItem(String codItem) {
        this.codItem = codItem;
    }

    public String getDescricaoComplementar() {
        return descricaoComplementar;
    }

    public void setDescricaoComplementar(String descricaoComplementar) {
        this.descricaoComplementar = descricaoComplementar;
    }

    public BigDecimal getQtd() {
        return qtd;
    }

    public void setQtd(BigDecimal qtd) {
        this.qtd = qtd;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public boolean isIndMovimentacao() {
        return indMovimentacao;
    }

    public void setIndMovimentacao(boolean indMovimentacao) {
        this.indMovimentacao = indMovimentacao;
    }

    public String getCSTICMS() {
        return CSTICMS;
    }

    public void setCSTICMS(String CSTICMS) {
        this.CSTICMS = CSTICMS;
    }

    public String getCFOP() {
        return CFOP;
    }

    public void setCFOP(String CFOP) {
        this.CFOP = CFOP;
    }

    public String getCodigoNatureza() {
        return codigoNatureza;
    }

    public void setCodigoNatureza(String codigoNatureza) {
        this.codigoNatureza = codigoNatureza;
    }

    public BigDecimal getValorBCICMS() {
        return valorBCICMS;
    }

    public void setValorBCICMS(BigDecimal valorBCICMS) {
        this.valorBCICMS = valorBCICMS;
    }

    public BigDecimal getAliqICMS() {
        return aliqICMS;
    }

    public void setAliqICMS(BigDecimal aliqICMS) {
        this.aliqICMS = aliqICMS;
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

    public BigDecimal getAliqICMSST() {
        return aliqICMSST;
    }

    public void setAliqICMSST(BigDecimal aliqICMSST) {
        this.aliqICMSST = aliqICMSST;
    }

    public BigDecimal getValorICMSST() {
        return valorICMSST;
    }

    public void setValorICMSST(BigDecimal valorICMSST) {
        this.valorICMSST = valorICMSST;
    }

    public String getIndicadorApuracao() {
        return indicadorApuracao;
    }

    public void setIndicadorApuracao(String indicadorApuracao) {
        this.indicadorApuracao = indicadorApuracao;
    }

    public String getCSTIPI() {
        return CSTIPI;
    }

    public void setCSTIPI(String CSTIPI) {
        this.CSTIPI = CSTIPI;
    }

    public String getCodEnquadramentoIPI() {
        return codEnquadramentoIPI;
    }

    public void setCodEnquadramentoIPI(String codEnquadramentoIPI) {
        this.codEnquadramentoIPI = codEnquadramentoIPI;
    }

    public BigDecimal getValorBCIPI() {
        return valorBCIPI;
    }

    public void setValorBCIPI(BigDecimal valorBCIPI) {
        this.valorBCIPI = valorBCIPI;
    }

    public BigDecimal getAliqIPI() {
        return aliqIPI;
    }

    public void setAliqIPI(BigDecimal aliqIPI) {
        this.aliqIPI = aliqIPI;
    }

    public BigDecimal getValorIPI() {
        return valorIPI;
    }

    public void setValorIPI(BigDecimal valorIPI) {
        this.valorIPI = valorIPI;
    }

    public String getCSTPIS() {
        return CSTPIS;
    }

    public void setCSTPIS(String CSTPIS) {
        this.CSTPIS = CSTPIS;
    }

    public BigDecimal getValorBCPIS() {
        return valorBCPIS;
    }

    public void setValorBCPIS(BigDecimal valorBCPIS) {
        this.valorBCPIS = valorBCPIS;
    }

    public BigDecimal getAliqPISPercentual() {
        return aliqPISPercentual;
    }

    public void setAliqPISPercentual(BigDecimal aliqPISPercentual) {
        this.aliqPISPercentual = aliqPISPercentual;
    }

    public BigDecimal getQuantidadeBCPIS() {
        return quantidadeBCPIS;
    }

    public void setQuantidadeBCPIS(BigDecimal quantidadeBCPIS) {
        this.quantidadeBCPIS = quantidadeBCPIS;
    }

    public BigDecimal getAliqPISReais() {
        return aliqPISReais;
    }

    public void setAliqPISReais(BigDecimal aliqPISReais) {
        this.aliqPISReais = aliqPISReais;
    }

    public BigDecimal getValorPIS() {
        return valorPIS;
    }

    public void setValorPIS(BigDecimal valorPIS) {
        this.valorPIS = valorPIS;
    }

    public String getCSTCOFINS() {
        return CSTCOFINS;
    }

    public void setCSTCOFINS(String CSTCOFINS) {
        this.CSTCOFINS = CSTCOFINS;
    }

    public BigDecimal getValorBCCOFINS() {
        return valorBCCOFINS;
    }

    public void setValorBCCOFINS(BigDecimal valorBCCOFINS) {
        this.valorBCCOFINS = valorBCCOFINS;
    }

    public BigDecimal getAliqCOFINSPercentual() {
        return aliqCOFINSPercentual;
    }

    public void setAliqCOFINSPercentual(BigDecimal aliqCOFINSPercentual) {
        this.aliqCOFINSPercentual = aliqCOFINSPercentual;
    }

    public BigDecimal getQuantidadeBCCOFINS() {
        return quantidadeBCCOFINS;
    }

    public void setQuantidadeBCCOFINS(BigDecimal quantidadeBCCOFINS) {
        this.quantidadeBCCOFINS = quantidadeBCCOFINS;
    }

    public BigDecimal getAliqCOFINSReais() {
        return aliqCOFINSReais;
    }

    public void setAliqCOFINSReais(BigDecimal aliqCOFINSReais) {
        this.aliqCOFINSReais = aliqCOFINSReais;
    }

    public BigDecimal getValorCOFINS() {
        return valorCOFINS;
    }

    public void setValorCOFINS(BigDecimal valorCOFINS) {
        this.valorCOFINS = valorCOFINS;
    }

    public String getCodigoCTA() {
        return codigoCTA;
    }

    public void setCodigoCTA(String codigoCTA) {
        this.codigoCTA = codigoCTA;
    }

    public SPED0200 getSPED0200() {
        return SPED0200;
    }

    public void setSPED0200(SPED0200 SPED0200) {
        this.SPED0200 = SPED0200;
    }

    public SPEDC100 getSPEDC100() {
        return SPEDC100;
    }

    public void setSPEDC100(SPEDC100 SPEDC100) {
        this.SPEDC100 = SPEDC100;
    }

    /**
     * @return the idReferenciaComNFe
     */
    public BigDecimal getIdReferenciaComNFe() {
        return idReferenciaComNFe;
    }

    /**
     * @param idReferenciaComNFe the idReferenciaComNFe to set
     */
    public void setIdReferenciaComNFe(BigDecimal idReferenciaComNFe) {
        this.idReferenciaComNFe = idReferenciaComNFe;
    }

    /**
     * @return the filtroDeRelacionamento
     */
    public String getFiltroDeRelacionamento() {
        return filtroDeRelacionamento;
    }

    /**
     * @param filtroDeRelacionamento the filtroDeRelacionamento to set
     */
    public void setFiltroDeRelacionamento(String filtroDeRelacionamento) {
        this.filtroDeRelacionamento = filtroDeRelacionamento;
    }
    
    // </editor-fold>
    
    // <editor-fold desc="equals e hashCode" defaultstate="collapsed">
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.sequencia;
        hash = 47 * hash + Objects.hashCode(this.numItem);
        hash = 47 * hash + Objects.hashCode(this.codItem);
        hash = 47 * hash + Objects.hashCode(this.descricaoComplementar);
        hash = 47 * hash + Objects.hashCode(this.qtd);
        hash = 47 * hash + Objects.hashCode(this.unid);
        hash = 47 * hash + Objects.hashCode(this.valor);
        hash = 47 * hash + Objects.hashCode(this.valorDesconto);
        hash = 47 * hash + (this.indMovimentacao ? 1 : 0);
        hash = 47 * hash + Objects.hashCode(this.CSTICMS);
        hash = 47 * hash + Objects.hashCode(this.CFOP);
        hash = 47 * hash + Objects.hashCode(this.codigoNatureza);
        hash = 47 * hash + Objects.hashCode(this.valorBCICMS);
        hash = 47 * hash + Objects.hashCode(this.aliqICMS);
        hash = 47 * hash + Objects.hashCode(this.valorICMS);
        hash = 47 * hash + Objects.hashCode(this.valorBCICMSST);
        hash = 47 * hash + Objects.hashCode(this.aliqICMSST);
        hash = 47 * hash + Objects.hashCode(this.valorICMSST);
        hash = 47 * hash + Objects.hashCode(this.indicadorApuracao);
        hash = 47 * hash + Objects.hashCode(this.CSTIPI);
        hash = 47 * hash + Objects.hashCode(this.codEnquadramentoIPI);
        hash = 47 * hash + Objects.hashCode(this.valorBCIPI);
        hash = 47 * hash + Objects.hashCode(this.aliqIPI);
        hash = 47 * hash + Objects.hashCode(this.valorIPI);
        hash = 47 * hash + Objects.hashCode(this.CSTPIS);
        hash = 47 * hash + Objects.hashCode(this.valorBCPIS);
        hash = 47 * hash + Objects.hashCode(this.aliqPISPercentual);
        hash = 47 * hash + Objects.hashCode(this.quantidadeBCPIS);
        hash = 47 * hash + Objects.hashCode(this.aliqPISReais);
        hash = 47 * hash + Objects.hashCode(this.valorPIS);
        hash = 47 * hash + Objects.hashCode(this.CSTCOFINS);
        hash = 47 * hash + Objects.hashCode(this.valorBCCOFINS);
        hash = 47 * hash + Objects.hashCode(this.aliqCOFINSPercentual);
        hash = 47 * hash + Objects.hashCode(this.quantidadeBCCOFINS);
        hash = 47 * hash + Objects.hashCode(this.aliqCOFINSReais);
        hash = 47 * hash + Objects.hashCode(this.valorCOFINS);
        hash = 47 * hash + Objects.hashCode(this.codigoCTA);
        hash = 47 * hash + Objects.hashCode(this.SPED0200);
        hash = 47 * hash + Objects.hashCode(this.SPEDC100);        
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
        final SPEDC170 other = (SPEDC170) obj;
        if (this.sequencia != other.sequencia) {
            return false;
        }
        if (this.indMovimentacao != other.indMovimentacao) {
            return false;
        }
        if (!Objects.equals(this.numItem, other.numItem)) {
            return false;
        }
        if (!Objects.equals(this.codItem, other.codItem)) {
            return false;
        }
        if (!Objects.equals(this.descricaoComplementar, other.descricaoComplementar)) {
            return false;
        }
        if (!Objects.equals(this.unid, other.unid)) {
            return false;
        }
        if (!Objects.equals(this.CSTICMS, other.CSTICMS)) {
            return false;
        }
        if (!Objects.equals(this.CFOP, other.CFOP)) {
            return false;
        }
        if (!Objects.equals(this.codigoNatureza, other.codigoNatureza)) {
            return false;
        }
        if (!Objects.equals(this.indicadorApuracao, other.indicadorApuracao)) {
            return false;
        }
        if (!Objects.equals(this.CSTIPI, other.CSTIPI)) {
            return false;
        }
        if (!Objects.equals(this.codEnquadramentoIPI, other.codEnquadramentoIPI)) {
            return false;
        }
        if (!Objects.equals(this.CSTPIS, other.CSTPIS)) {
            return false;
        }
        if (!Objects.equals(this.CSTCOFINS, other.CSTCOFINS)) {
            return false;
        }
        if (!Objects.equals(this.codigoCTA, other.codigoCTA)) {
            return false;
        }
        if (!Objects.equals(this.qtd, other.qtd)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.valorDesconto, other.valorDesconto)) {
            return false;
        }
        if (!Objects.equals(this.valorBCICMS, other.valorBCICMS)) {
            return false;
        }
        if (!Objects.equals(this.aliqICMS, other.aliqICMS)) {
            return false;
        }
        if (!Objects.equals(this.valorICMS, other.valorICMS)) {
            return false;
        }
        if (!Objects.equals(this.valorBCICMSST, other.valorBCICMSST)) {
            return false;
        }
        if (!Objects.equals(this.aliqICMSST, other.aliqICMSST)) {
            return false;
        }
        if (!Objects.equals(this.valorICMSST, other.valorICMSST)) {
            return false;
        }
        if (!Objects.equals(this.valorBCIPI, other.valorBCIPI)) {
            return false;
        }
        if (!Objects.equals(this.aliqIPI, other.aliqIPI)) {
            return false;
        }
        if (!Objects.equals(this.valorIPI, other.valorIPI)) {
            return false;
        }
        if (!Objects.equals(this.valorBCPIS, other.valorBCPIS)) {
            return false;
        }
        if (!Objects.equals(this.aliqPISPercentual, other.aliqPISPercentual)) {
            return false;
        }
        if (!Objects.equals(this.quantidadeBCPIS, other.quantidadeBCPIS)) {
            return false;
        }
        if (!Objects.equals(this.aliqPISReais, other.aliqPISReais)) {
            return false;
        }
        if (!Objects.equals(this.valorPIS, other.valorPIS)) {
            return false;
        }
        if (!Objects.equals(this.valorBCCOFINS, other.valorBCCOFINS)) {
            return false;
        }
        if (!Objects.equals(this.aliqCOFINSPercentual, other.aliqCOFINSPercentual)) {
            return false;
        }
        if (!Objects.equals(this.quantidadeBCCOFINS, other.quantidadeBCCOFINS)) {
            return false;
        }
        if (!Objects.equals(this.aliqCOFINSReais, other.aliqCOFINSReais)) {
            return false;
        }
        if (!Objects.equals(this.valorCOFINS, other.valorCOFINS)) {
            return false;
        }
        if (!Objects.equals(this.SPED0200, other.SPED0200)) {
            return false;
        }        
        return true;
    }
    // </editor-fold>

    /**
     * @return the linhaOriginal
     */
    public String getLinhaOriginal() {
        return linhaOriginal;
    }

    /**
     * @param linhaOriginal the linhaOriginal to set
     */
    public void setLinhaOriginal(String linhaOriginal) {
        this.linhaOriginal = linhaOriginal;
    }

    /**
     * @return the linhaComModificacoes
     */
    public String getLinhaComModificacoes() {
        return linhaComModificacoes;
    }

    /**
     * @param linhaComModificacoes the linhaComModificacoes to set
     */
    public void setLinhaComModificacoes(String linhaComModificacoes) {
        this.linhaComModificacoes = linhaComModificacoes;
    }
}
