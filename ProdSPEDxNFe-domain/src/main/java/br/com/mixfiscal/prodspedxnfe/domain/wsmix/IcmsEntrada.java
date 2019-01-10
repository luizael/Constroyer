package br.com.mixfiscal.prodspedxnfe.domain.wsmix;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import com.google.gson.annotations.SerializedName;
public class IcmsEntrada implements Serializable {
    private static final long serialVersionUID = 2389354778699744511L;
    
    @SerializedName("tipo_mva")
    private String tipoMva;
    @SerializedName("mva")
    private BigDecimal mva;
    @SerializedName("mva_data_ini")
    private String mvaDataIni;
    @SerializedName("mva_data_fim")
    private String mvaDataFim;
    @SerializedName("credito_outorgado")
    private Boolean creditoOutorgado;
    @SerializedName("gera_debito")
    private String geraDebito;
    @SerializedName("sub_rbc_alq")
    private String subRbcAlq;
    @SerializedName("ei_cst")
    private String eiCst;
    @SerializedName("ei_alq")
    private BigDecimal eiAlq;
    @SerializedName("ei_alqst")
    private BigDecimal eiAlqst;
    @SerializedName("ei_rbc")
    private BigDecimal eiRbc;
    @SerializedName("ei_rbcst")
    private BigDecimal eiRbcst;
    @SerializedName("ed_cst")
    private BigDecimal edCst;
    @SerializedName("ed_alq")
    private BigDecimal edAlq;
    @SerializedName("ed_alqst")
    private BigDecimal edAlqst;
    @SerializedName("ed_rbc")
    private BigDecimal edRbc;
    @SerializedName("ed_rbcst")
    private BigDecimal edRbcst;
    @SerializedName("es_cst")
    private String esCst;
    @SerializedName("es_alq")
    private BigDecimal esAlq;
    @SerializedName("es_alqst")
    private BigDecimal esAlqst;
    @SerializedName("es_rbc")
    private BigDecimal esRbc;
    @SerializedName("es_rbcst")
    private BigDecimal esRbcst;
    @SerializedName("nfi_cst")
    private String nfiCst;
    @SerializedName("nfd_cst")
    private String nfdCst;
    @SerializedName("nfs_csosn")
    private String nfsCsosn;
    @SerializedName("nf_alq")
    private BigDecimal nfAlq;
    @SerializedName("fundamento_legal")
    private String fundamentoLegal;

    public String getTipoMva() {
        return tipoMva;
    }

    public void setTipoMva(String tipoMva) {
        this.tipoMva = tipoMva;
    }

    public BigDecimal getMva() {
        return mva;
    }

    public void setMva(BigDecimal mva) {
        this.mva = mva;
    }

    public String getMvaDataIni() {
        return mvaDataIni;
    }

    public void setMvaDataIni(String mvaDataIni) {
        this.mvaDataIni = mvaDataIni;
    }

    public String getMvaDataFim() {
        return mvaDataFim;
    }

    public void setMvaDataFim(String mvaDataFim) {
        this.mvaDataFim = mvaDataFim;
    }

    public Boolean getCreditoOutorgado() {
        return creditoOutorgado;
    }

    public void setCreditoOutorgado(Boolean creditoOutorgado) {
        this.creditoOutorgado = creditoOutorgado;
    }

    public String getGeraDebito() {
        return geraDebito;
    }

    public void setGeraDebito(String geraDebito) {
        this.geraDebito = geraDebito;
    }

    public String getSubRbcAlq() {
        return subRbcAlq;
    }

    public void setSubRbcAlq(String subRbcAlq) {
        this.subRbcAlq = subRbcAlq;
    }

    public String getEiCst() {
        return eiCst;
    }

    public void setEiCst(String eiCst) {
        this.eiCst = eiCst;
    }

    public BigDecimal getEiAlq() {
        return eiAlq;
    }

    public void setEiAlq(BigDecimal eiAlq) {
        this.eiAlq = eiAlq;
    }

    public BigDecimal getEiAlqst() {
        return eiAlqst;
    }

    public void setEiAlqst(BigDecimal eiAlqst) {
        this.eiAlqst = eiAlqst;
    }

    public BigDecimal getEiRbc() {
        return eiRbc;
    }

    public void setEiRbc(BigDecimal eiRbc) {
        this.eiRbc = eiRbc;
    }

    public BigDecimal getEiRbcst() {
        return eiRbcst;
    }

    public void setEiRbcst(BigDecimal eiRbcst) {
        this.eiRbcst = eiRbcst;
    }

    public BigDecimal getEdCst() {
        return edCst;
    }

    public void setEdCst(BigDecimal edCst) {
        this.edCst = edCst;
    }

    public BigDecimal getEdAlq() {
        return edAlq;
    }

    public void setEdAlq(BigDecimal edAlq) {
        this.edAlq = edAlq;
    }

    public BigDecimal getEdAlqst() {
        return edAlqst;
    }

    public void setEdAlqst(BigDecimal edAlqst) {
        this.edAlqst = edAlqst;
    }

    public BigDecimal getEdRbc() {
        return edRbc;
    }

    public void setEdRbc(BigDecimal edRbc) {
        this.edRbc = edRbc;
    }

    public BigDecimal getEdRbcst() {
        return edRbcst;
    }

    public void setEdRbcst(BigDecimal edRbcst) {
        this.edRbcst = edRbcst;
    }

    public String getEsCst() {
        return esCst;
    }

    public void setEsCst(String esCst) {
        this.esCst = esCst;
    }

    public BigDecimal getEsAlq() {
        return esAlq;
    }

    public void setEsAlq(BigDecimal esAlq) {
        this.esAlq = esAlq;
    }

    public BigDecimal getEsAlqst() {
        return esAlqst;
    }

    public void setEsAlqst(BigDecimal esAlqst) {
        this.esAlqst = esAlqst;
    }

    public BigDecimal getEsRbc() {
        return esRbc;
    }

    public void setEsRbc(BigDecimal esRbc) {
        this.esRbc = esRbc;
    }

    public BigDecimal getEsRbcst() {
        return esRbcst;
    }

    public void setEsRbcst(BigDecimal esRbcst) {
        this.esRbcst = esRbcst;
    }

    public String getNfiCst() {
        return nfiCst;
    }

    public void setNfiCst(String nfiCst) {
        this.nfiCst = nfiCst;
    }

    public String getNfdCst() {
        return nfdCst;
    }

    public void setNfdCst(String nfdCst) {
        this.nfdCst = nfdCst;
    }

    public String getNfsCsosn() {
        return nfsCsosn;
    }

    public void setNfsCsosn(String nfsCsosn) {
        this.nfsCsosn = nfsCsosn;
    }

    public BigDecimal getNfAlq() {
        return nfAlq;
    }

    public void setNfAlq(BigDecimal nfAlq) {
        this.nfAlq = nfAlq;
    }

    public String getFundamentoLegal() {
        return fundamentoLegal;
    }

    public void setFundamentoLegal(String fundamentoLegal) {
        this.fundamentoLegal = fundamentoLegal;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.tipoMva);
        hash = 37 * hash + Objects.hashCode(this.mva);
        hash = 37 * hash + Objects.hashCode(this.mvaDataIni);
        hash = 37 * hash + Objects.hashCode(this.mvaDataFim);
        hash = 37 * hash + Objects.hashCode(this.creditoOutorgado);
        hash = 37 * hash + Objects.hashCode(this.geraDebito);
        hash = 37 * hash + Objects.hashCode(this.subRbcAlq);
        hash = 37 * hash + Objects.hashCode(this.eiCst);
        hash = 37 * hash + Objects.hashCode(this.eiAlq);
        hash = 37 * hash + Objects.hashCode(this.eiAlqst);
        hash = 37 * hash + Objects.hashCode(this.eiRbc);
        hash = 37 * hash + Objects.hashCode(this.eiRbcst);
        hash = 37 * hash + Objects.hashCode(this.edCst);
        hash = 37 * hash + Objects.hashCode(this.edAlq);
        hash = 37 * hash + Objects.hashCode(this.edAlqst);
        hash = 37 * hash + Objects.hashCode(this.edRbc);
        hash = 37 * hash + Objects.hashCode(this.edRbcst);
        hash = 37 * hash + Objects.hashCode(this.esCst);
        hash = 37 * hash + Objects.hashCode(this.esAlq);
        hash = 37 * hash + Objects.hashCode(this.esAlqst);
        hash = 37 * hash + Objects.hashCode(this.esRbc);
        hash = 37 * hash + Objects.hashCode(this.esRbcst);
        hash = 37 * hash + Objects.hashCode(this.nfiCst);
        hash = 37 * hash + Objects.hashCode(this.nfdCst);
        hash = 37 * hash + Objects.hashCode(this.nfsCsosn);
        hash = 37 * hash + Objects.hashCode(this.nfAlq);
        hash = 37 * hash + Objects.hashCode(this.fundamentoLegal);
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
        final IcmsEntrada other = (IcmsEntrada) obj;
        if (!Objects.equals(this.tipoMva, other.tipoMva)) {
            return false;
        }
        if (!Objects.equals(this.geraDebito, other.geraDebito)) {
            return false;
        }
        if (!Objects.equals(this.subRbcAlq, other.subRbcAlq)) {
            return false;
        }
        if (!Objects.equals(this.eiCst, other.eiCst)) {
            return false;
        }
        if (!Objects.equals(this.esCst, other.esCst)) {
            return false;
        }
        if (!Objects.equals(this.nfiCst, other.nfiCst)) {
            return false;
        }
        if (!Objects.equals(this.nfdCst, other.nfdCst)) {
            return false;
        }
        if (!Objects.equals(this.nfsCsosn, other.nfsCsosn)) {
            return false;
        }
        if (!Objects.equals(this.fundamentoLegal, other.fundamentoLegal)) {
            return false;
        }
        if (!Objects.equals(this.mva, other.mva)) {
            return false;
        }
        if (!Objects.equals(this.mvaDataIni, other.mvaDataIni)) {
            return false;
        }
        if (!Objects.equals(this.mvaDataFim, other.mvaDataFim)) {
            return false;
        }
        if (!Objects.equals(this.creditoOutorgado, other.creditoOutorgado)) {
            return false;
        }
        if (!Objects.equals(this.eiAlq, other.eiAlq)) {
            return false;
        }
        if (!Objects.equals(this.eiAlqst, other.eiAlqst)) {
            return false;
        }
        if (!Objects.equals(this.eiRbc, other.eiRbc)) {
            return false;
        }
        if (!Objects.equals(this.eiRbcst, other.eiRbcst)) {
            return false;
        }
        if (!Objects.equals(this.edCst, other.edCst)) {
            return false;
        }
        if (!Objects.equals(this.edAlq, other.edAlq)) {
            return false;
        }
        if (!Objects.equals(this.edAlqst, other.edAlqst)) {
            return false;
        }
        if (!Objects.equals(this.edRbc, other.edRbc)) {
            return false;
        }
        if (!Objects.equals(this.edRbcst, other.edRbcst)) {
            return false;
        }
        if (!Objects.equals(this.esAlq, other.esAlq)) {
            return false;
        }
        if (!Objects.equals(this.esAlqst, other.esAlqst)) {
            return false;
        }
        if (!Objects.equals(this.esRbc, other.esRbc)) {
            return false;
        }
        if (!Objects.equals(this.esRbcst, other.esRbcst)) {
            return false;
        }
        if (!Objects.equals(this.nfAlq, other.nfAlq)) {
            return false;
        }
        return true;
    }
    
    
    
}
