package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ICMS20 extends ICMS implements Serializable{
    private static final long serialVersionUID = -7852675656462895120L;
    
    private int modalidadeBaseCalculo;//<modBc>
    private BigDecimal valorBC;//<vBc>
    private BigDecimal aliquotaICMS;//<pIcms>
    private BigDecimal valorICMS;//<vIcms>
    private BigDecimal percentualReducaoBC;//<pRedBc>
    private BigDecimal motivoDesoneracaoICMS;//<motDesIcms>
    private BigDecimal valorICMSDesoneracao;//<vIcmsDeson>

    public int getModalidadeBaseCalculo() {
        return modalidadeBaseCalculo;
    }

    public void setModalidadeBaseCalculo(int modalidadeBaseCalculo) {
        this.modalidadeBaseCalculo = modalidadeBaseCalculo;
    }

    public BigDecimal getValorBC() {
        return valorBC;
    }

    public void setValorBC(BigDecimal valorBC) {
        this.valorBC = valorBC;
    }

    public BigDecimal getAliquotaICMS() {
        return aliquotaICMS;
    }

    public void setAliquotaICMS(BigDecimal aliquotaICMS) {
        this.aliquotaICMS = aliquotaICMS;
    }

    public BigDecimal getValorICMS() {
        return valorICMS;
    }

    public void setValorICMS(BigDecimal valorICMS) {
        this.valorICMS = valorICMS;
    }

    public BigDecimal getPercentualReducaoBC() {
        return percentualReducaoBC;
    }

    public void setPercentualReducaoBC(BigDecimal percentualReducaoBC) {
        this.percentualReducaoBC = percentualReducaoBC;
    }

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
        int hash = 3;
        hash = 97 * hash + this.modalidadeBaseCalculo;
        hash = 97 * hash + Objects.hashCode(this.valorBC);
        hash = 97 * hash + Objects.hashCode(this.aliquotaICMS);
        hash = 97 * hash + Objects.hashCode(this.valorICMS);
        hash = 97 * hash + Objects.hashCode(this.percentualReducaoBC);
        hash = 97 * hash + Objects.hashCode(this.motivoDesoneracaoICMS);
        hash = 97 * hash + Objects.hashCode(this.valorICMSDesoneracao);
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
        final ICMS20 other = (ICMS20) obj;
        if (this.modalidadeBaseCalculo != other.modalidadeBaseCalculo) {
            return false;
        }
        if (!Objects.equals(this.valorBC, other.valorBC)) {
            return false;
        }
        if (!Objects.equals(this.aliquotaICMS, other.aliquotaICMS)) {
            return false;
        }
        if (!Objects.equals(this.valorICMS, other.valorICMS)) {
            return false;
        }
        if (!Objects.equals(this.percentualReducaoBC, other.percentualReducaoBC)) {
            return false;
        }
        if (!Objects.equals(this.motivoDesoneracaoICMS, other.motivoDesoneracaoICMS)) {
            return false;
        }
        if (!Objects.equals(this.valorICMSDesoneracao, other.valorICMSDesoneracao)) {
            return false;
        }
        return true;
    }

   
    
    
}
