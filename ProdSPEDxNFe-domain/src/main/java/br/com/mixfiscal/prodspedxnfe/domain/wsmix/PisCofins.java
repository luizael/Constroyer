package br.com.mixfiscal.prodspedxnfe.domain.wsmix;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.google.gson.annotations.SerializedName;
public class PisCofins implements Serializable{
    private static final long serialVersionUID = -681298280238275539L;
    
    private String ncm;
    @SerializedName("ncm_ex")
    private String ncmEx;
    @SerializedName("cod_natureza_receita")
    private String codNaturezaReceita;
    @SerializedName("credito_presumido")
    private Boolean creditoPresumido;
    @SerializedName("pis_cst_e")
    private String pisCstE;
    @SerializedName("pis_cst_s")
    private String pisCstS;
    @SerializedName("pis_alq_e")
    private BigDecimal pisAlqE;
    @SerializedName("pis_alq_s")
    private BigDecimal pisAlqS;
    @SerializedName("cofins_cst_e")
    private String cofinsCstE;
    @SerializedName("cofins_cst_s")
    private String cofinsCstS;
    @SerializedName("cofins_alq_e")
    private BigDecimal cofinsAlqE;
    @SerializedName("cofins_alq_s")
    private BigDecimal cofinsAlqS;
    @SerializedName("fundamento_legal")
    private String fundamentoLegal;

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public String getNcmEx() {
        return ncmEx;
    }

    public void setNcmEx(String ncmEx) {
        this.ncmEx = ncmEx;
    }

    public String getCodNaturezaReceita() {
        return codNaturezaReceita;
    }

    public void setCodNaturezaReceita(String codNaturezaReceita) {
        this.codNaturezaReceita = codNaturezaReceita;
    }

    public Boolean getCreditoPresumido() {
        return creditoPresumido;
    }

    public void setCreditoPresumido(Boolean creditoPresumido) {
        this.creditoPresumido = creditoPresumido;
    }

    public String getPisCstE() {
        return pisCstE;
    }

    public void setPisCstE(String pisCstE) {
        this.pisCstE = pisCstE;
    }

    public String getPisCstS() {
        return pisCstS;
    }

    public void setPisCstS(String pisCstS) {
        this.pisCstS = pisCstS;
    }

    public BigDecimal getPisAlqE() {
        return pisAlqE;
    }

    public void setPisAlqE(BigDecimal pisAlqE) {
        this.pisAlqE = pisAlqE;
    }

    public BigDecimal getPisAlqS() {
        return pisAlqS;
    }

    public void setPisAlqS(BigDecimal pisAlqS) {
        this.pisAlqS = pisAlqS;
    }

    public String getCofinsCstE() {
        return cofinsCstE;
    }

    public void setCofinsCstE(String cofinsCstE) {
        this.cofinsCstE = cofinsCstE;
    }

    public String getCofinsCstS() {
        return cofinsCstS;
    }

    public void setCofinsCstS(String cofinsCstS) {
        this.cofinsCstS = cofinsCstS;
    }

    public BigDecimal getCofinsAlqE() {
        return cofinsAlqE;
    }

    public void setCofinsAlqE(BigDecimal cofinsAlqE) {
        this.cofinsAlqE = cofinsAlqE;
    }

    public BigDecimal getCofinsAlqS() {
        return cofinsAlqS;
    }

    public void setCofinsAlqS(BigDecimal cofinsAlqS) {
        this.cofinsAlqS = cofinsAlqS;
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
        hash = 89 * hash + Objects.hashCode(this.ncm);
        hash = 89 * hash + Objects.hashCode(this.ncmEx);
        hash = 89 * hash + Objects.hashCode(this.codNaturezaReceita);
        hash = 89 * hash + Objects.hashCode(this.creditoPresumido);
        hash = 89 * hash + Objects.hashCode(this.pisCstE);
        hash = 89 * hash + Objects.hashCode(this.pisCstS);
        hash = 89 * hash + Objects.hashCode(this.pisAlqE);
        hash = 89 * hash + Objects.hashCode(this.pisAlqS);
        hash = 89 * hash + Objects.hashCode(this.cofinsCstE);
        hash = 89 * hash + Objects.hashCode(this.cofinsCstS);
        hash = 89 * hash + Objects.hashCode(this.cofinsAlqE);
        hash = 89 * hash + Objects.hashCode(this.cofinsAlqS);
        hash = 89 * hash + Objects.hashCode(this.fundamentoLegal);
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
        final PisCofins other = (PisCofins) obj;
        if (!Objects.equals(this.ncm, other.ncm)) {
            return false;
        }
        if (!Objects.equals(this.ncmEx, other.ncmEx)) {
            return false;
        }
        if (!Objects.equals(this.codNaturezaReceita, other.codNaturezaReceita)) {
            return false;
        }
        if (!Objects.equals(this.pisCstE, other.pisCstE)) {
            return false;
        }
        if (!Objects.equals(this.pisCstS, other.pisCstS)) {
            return false;
        }
        if (!Objects.equals(this.cofinsCstE, other.cofinsCstE)) {
            return false;
        }
        if (!Objects.equals(this.cofinsCstS, other.cofinsCstS)) {
            return false;
        }
        if (!Objects.equals(this.fundamentoLegal, other.fundamentoLegal)) {
            return false;
        }
        if (!Objects.equals(this.creditoPresumido, other.creditoPresumido)) {
            return false;
        }
        if (!Objects.equals(this.pisAlqE, other.pisAlqE)) {
            return false;
        }
        if (!Objects.equals(this.pisAlqS, other.pisAlqS)) {
            return false;
        }
        if (!Objects.equals(this.cofinsAlqE, other.cofinsAlqE)) {
            return false;
        }
        if (!Objects.equals(this.cofinsAlqS, other.cofinsAlqS)) {
            return false;
        }
        return true;
    }
    
    
}
