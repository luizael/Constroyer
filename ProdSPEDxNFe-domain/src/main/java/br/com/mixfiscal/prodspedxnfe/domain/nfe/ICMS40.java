package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ICMS40 extends ICMS implements Serializable {
    private static final long serialVersionUID = 154953817200476882L;
        
    private BigDecimal motivoDesoneracaoICMS;//<motDesIcms>
    private BigDecimal valorICMSDesoneracao;//<vIcmsDeson>

    public BigDecimal getMotivoDesoneracaoICMS() {
        return motivoDesoneracaoICMS;
    }

    public void setMotivoDesoneracaoICMS(BigDecimal motivoDesoneracaoICMS) {
        this.motivoDesoneracaoICMS = motivoDesoneracaoICMS;
    }

    public BigDecimal getValorICMSDesoneracao() {
        return valorICMSDesoneracao;
    }

    public void setValorICMSDesoneracao(BigDecimal valorICMSDesoneracao) {
        this.valorICMSDesoneracao = valorICMSDesoneracao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.motivoDesoneracaoICMS);
        hash = 47 * hash + Objects.hashCode(this.valorICMSDesoneracao);
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
        final ICMS40 other = (ICMS40) obj;
        if (!Objects.equals(this.motivoDesoneracaoICMS, other.motivoDesoneracaoICMS)) {
            return false;
        }
        if (!Objects.equals(this.valorICMSDesoneracao, other.valorICMSDesoneracao)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
