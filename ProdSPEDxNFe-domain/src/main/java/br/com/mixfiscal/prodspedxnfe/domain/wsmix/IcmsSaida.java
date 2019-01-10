package br.com.mixfiscal.prodspedxnfe.domain.wsmix;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.google.gson.annotations.SerializedName;
public class IcmsSaida implements Serializable {
    private static final long serialVersionUID = 9102210977180133548L;
    
    @SerializedName("sac_cst")
    private String sacCst;
    @SerializedName("sac_alq")
    private BigDecimal sacAlq;
    @SerializedName("sac_alqst")
    private BigDecimal sacAlqst;
    @SerializedName("sac_rbc")
    private BigDecimal sacRbc;
    @SerializedName("sac_rbcst")
    private BigDecimal sacRbcst;
    @SerializedName("sas_cst")
    private String sasCst;
    @SerializedName("sas_alq")
    private BigDecimal sasAlq;
    @SerializedName("sas_alqst")
    private BigDecimal sasAlqst;
    @SerializedName("sas_rbc")
    private BigDecimal sasRbc;
    @SerializedName("sas_rbcst")
    private BigDecimal sasRbcst;
    @SerializedName("svc_cst")
    private String svcCst;
    @SerializedName("svc_alq")
    private BigDecimal svcAlq;
    @SerializedName("svc_alqst")
    private BigDecimal svcAlqst;
    @SerializedName("svc_rbc")
    private BigDecimal svcRbc;
    @SerializedName("svc_rbcst")
    private BigDecimal svcRbcst;
    @SerializedName("snc_cst")
    private BigDecimal sncCst;
    @SerializedName("snc_alq")
    private BigDecimal sncAlq;
    @SerializedName("snc_alqst")
    private BigDecimal sncAlqst;
    @SerializedName("snc_rbc")
    private BigDecimal sncRbc;
    @SerializedName("snc_rbcst")
    private BigDecimal sncRbcst;
    @SerializedName("fundamento_legal")
    private String fundamentoLegal;
     @SerializedName("cest")
    private String ceSt;
     
    public String getSacCst() {
        return sacCst;
    }

    public void setSacCst(String sacCst) {
        this.sacCst = sacCst;
    }

    public BigDecimal getSacAlq() {
        return sacAlq;
    }

    public void setSacAlq(BigDecimal sacAlq) {
        this.sacAlq = sacAlq;
    }

    public BigDecimal getSacAlqst() {
        return sacAlqst;
    }

    public void setSacAlqst(BigDecimal sacAlqst) {
        this.sacAlqst = sacAlqst;
    }

    public BigDecimal getSacRbc() {
        return sacRbc;
    }

    public void setSacRbc(BigDecimal sacRbc) {
        this.sacRbc = sacRbc;
    }

    public BigDecimal getSacRbcst() {
        return sacRbcst;
    }

    public void setSacRbcst(BigDecimal sacRbcst) {
        this.sacRbcst = sacRbcst;
    }

    public String getSasCst() {
        return sasCst;
    }

    public void setSasCst(String sasCst) {
        this.sasCst = sasCst;
    }

    public BigDecimal getSasAlq() {
        return sasAlq;
    }

    public void setSasAlq(BigDecimal sasAlq) {
        this.sasAlq = sasAlq;
    }

    public BigDecimal getSasAlqst() {
        return sasAlqst;
    }

    public void setSasAlqst(BigDecimal sasAlqst) {
        this.sasAlqst = sasAlqst;
    }

    public BigDecimal getSasRbc() {
        return sasRbc;
    }

    public void setSasRbc(BigDecimal sasRbc) {
        this.sasRbc = sasRbc;
    }

    public BigDecimal getSasRbcst() {
        return sasRbcst;
    }

    public void setSasRbcst(BigDecimal sasRbcst) {
        this.sasRbcst = sasRbcst;
    }

    public String getSvcCst() {
        return svcCst;
    }

    public void setSvcCst(String svcCst) {
        this.svcCst = svcCst;
    }

    public BigDecimal getSvcAlq() {
        return svcAlq;
    }

    public void setSvcAlq(BigDecimal svcAlq) {
        this.svcAlq = svcAlq;
    }

    public BigDecimal getSvcAlqst() {
        return svcAlqst;
    }

    public void setSvcAlqst(BigDecimal svcAlqst) {
        this.svcAlqst = svcAlqst;
    }

    public BigDecimal getSvcRbc() {
        return svcRbc;
    }

    public void setSvcRbc(BigDecimal svcRbc) {
        this.svcRbc = svcRbc;
    }

    public BigDecimal getSvcRbcst() {
        return svcRbcst;
    }

    public void setSvcRbcst(BigDecimal svcRbcst) {
        this.svcRbcst = svcRbcst;
    }

    public BigDecimal getSncCst() {
        return sncCst;
    }

    public void setSncCst(BigDecimal sncCst) {
        this.sncCst = sncCst;
    }

    public BigDecimal getSncAlq() {
        return sncAlq;
    }

    public void setSncAlq(BigDecimal sncAlq) {
        this.sncAlq = sncAlq;
    }

    public BigDecimal getSncAlqst() {
        return sncAlqst;
    }

    public void setSncAlqst(BigDecimal sncAlqst) {
        this.sncAlqst = sncAlqst;
    }

    public BigDecimal getSncRbc() {
        return sncRbc;
    }

    public void setSncRbc(BigDecimal sncRbc) {
        this.sncRbc = sncRbc;
    }

    public BigDecimal getSncRbcst() {
        return sncRbcst;
    }

    public void setSncRbcst(BigDecimal sncRbcst) {
        this.sncRbcst = sncRbcst;
    }

    public String getFundamentoLegal() {
        return fundamentoLegal;
    }

    public void setFundamentoLegal(String fundamentoLegal) {
        this.fundamentoLegal = fundamentoLegal;
    }

    public String getCeSt() {
        return ceSt;
    }

    public void setCeSt(String ceSt) {
        this.ceSt = ceSt;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.sacCst);
        hash = 97 * hash + Objects.hashCode(this.sacAlq);
        hash = 97 * hash + Objects.hashCode(this.sacAlqst);
        hash = 97 * hash + Objects.hashCode(this.sacRbc);
        hash = 97 * hash + Objects.hashCode(this.sacRbcst);
        hash = 97 * hash + Objects.hashCode(this.sasCst);
        hash = 97 * hash + Objects.hashCode(this.sasAlq);
        hash = 97 * hash + Objects.hashCode(this.sasAlqst);
        hash = 97 * hash + Objects.hashCode(this.sasRbc);
        hash = 97 * hash + Objects.hashCode(this.sasRbcst);
        hash = 97 * hash + Objects.hashCode(this.svcCst);
        hash = 97 * hash + Objects.hashCode(this.svcAlq);
        hash = 97 * hash + Objects.hashCode(this.svcAlqst);
        hash = 97 * hash + Objects.hashCode(this.svcRbc);
        hash = 97 * hash + Objects.hashCode(this.svcRbcst);
        hash = 97 * hash + Objects.hashCode(this.sncCst);
        hash = 97 * hash + Objects.hashCode(this.sncAlq);
        hash = 97 * hash + Objects.hashCode(this.sncAlqst);
        hash = 97 * hash + Objects.hashCode(this.sncRbc);
        hash = 97 * hash + Objects.hashCode(this.sncRbcst);
        hash = 97 * hash + Objects.hashCode(this.fundamentoLegal);
        hash = 97 * hash + Objects.hashCode(this.ceSt);
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
        final IcmsSaida other = (IcmsSaida) obj;
        if (!Objects.equals(this.sacCst, other.sacCst)) {
            return false;
        }
        if (!Objects.equals(this.sasCst, other.sasCst)) {
            return false;
        }
        if (!Objects.equals(this.svcCst, other.svcCst)) {
            return false;
        }
        if (!Objects.equals(this.fundamentoLegal, other.fundamentoLegal)) {
            return false;
        }
        if (!Objects.equals(this.sacAlq, other.sacAlq)) {
            return false;
        }
        if (!Objects.equals(this.sacAlqst, other.sacAlqst)) {
            return false;
        }
        if (!Objects.equals(this.sacRbc, other.sacRbc)) {
            return false;
        }
        if (!Objects.equals(this.sacRbcst, other.sacRbcst)) {
            return false;
        }
        if (!Objects.equals(this.sasAlq, other.sasAlq)) {
            return false;
        }
        if (!Objects.equals(this.sasAlqst, other.sasAlqst)) {
            return false;
        }
        if (!Objects.equals(this.sasRbc, other.sasRbc)) {
            return false;
        }
        if (!Objects.equals(this.sasRbcst, other.sasRbcst)) {
            return false;
        }
        if (!Objects.equals(this.svcAlq, other.svcAlq)) {
            return false;
        }
        if (!Objects.equals(this.svcAlqst, other.svcAlqst)) {
            return false;
        }
        if (!Objects.equals(this.svcRbc, other.svcRbc)) {
            return false;
        }
        if (!Objects.equals(this.svcRbcst, other.svcRbcst)) {
            return false;
        }
        if (!Objects.equals(this.sncCst, other.sncCst)) {
            return false;
        }
        if (!Objects.equals(this.sncAlq, other.sncAlq)) {
            return false;
        }
        if (!Objects.equals(this.sncAlqst, other.sncAlqst)) {
            return false;
        }
        if (!Objects.equals(this.sncRbc, other.sncRbc)) {
            return false;
        }
        if (!Objects.equals(this.sncRbcst, other.sncRbcst)) {
            return false;
        }
        return true;
    }
    
    
}
