package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ICMS30 extends ICMS implements Serializable {
    private static final long serialVersionUID = -6382517831939574808L;
    
    private int modalidadeBaseCalculoST;//<modBcSt>
    private BigDecimal valorBCST;//<vBcSt>
    private BigDecimal aliquotaICMSST;//<pIcmsSt>
    private BigDecimal valorICMSST;//<vIcmsSt>
    private BigDecimal percentualMargemValorAdicionadoST;//<pMvaSt>
    private BigDecimal motivoDesoneracaoICMS;//<motDesIcms>
    private BigDecimal valorICMSDesoneracao;//<vIcmsDeson>;
    private BigDecimal percentualReducaoBCST;//<pRedBcSt>    

    public int getModalidadeBaseCalculoST() {
        return modalidadeBaseCalculoST;
    }

    public void setModalidadeBaseCalculoST(int modalidadeBaseCalculoST) {
        this.modalidadeBaseCalculoST = modalidadeBaseCalculoST;
    }

    public BigDecimal getValorBCST() {
        return valorBCST;
    }

    public void setValorBCST(BigDecimal valorBCST) {
        this.valorBCST = valorBCST;
    }

    public BigDecimal getAliquotaICMSST() {
        return aliquotaICMSST;
    }

    public void setAliquotaICMSST(BigDecimal aliquotaICMSST) {
        this.aliquotaICMSST = aliquotaICMSST;
    }

    public BigDecimal getValorICMSST() {
        return valorICMSST;
    }

    public void setValorICMSST(BigDecimal valorICMSST) {
        this.valorICMSST = valorICMSST;
    }

    public BigDecimal getPercentualMargemValorAdicionadoST() {
        return percentualMargemValorAdicionadoST;
    }

    public void setPercentualMargemValorAdicionadoST(BigDecimal percentualMargemValorAdicionadoST) {
        this.percentualMargemValorAdicionadoST = percentualMargemValorAdicionadoST;
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

    public BigDecimal getPercentualReducaoBCST() {
        return percentualReducaoBCST;
    }

    public void setPercentualReducaoBCST(BigDecimal percentualReducaoBCST) {
        this.percentualReducaoBCST = percentualReducaoBCST;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + this.modalidadeBaseCalculoST;
        hash = 73 * hash + Objects.hashCode(this.valorBCST);
        hash = 73 * hash + Objects.hashCode(this.aliquotaICMSST);
        hash = 73 * hash + Objects.hashCode(this.valorICMSST);
        hash = 73 * hash + Objects.hashCode(this.percentualMargemValorAdicionadoST);
        hash = 73 * hash + Objects.hashCode(this.motivoDesoneracaoICMS);
        hash = 73 * hash + Objects.hashCode(this.valorICMSDesoneracao);
        hash = 73 * hash + Objects.hashCode(this.percentualReducaoBCST);
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
        final ICMS30 other = (ICMS30) obj;
        if (this.modalidadeBaseCalculoST != other.modalidadeBaseCalculoST) {
            return false;
        }
        if (!Objects.equals(this.valorBCST, other.valorBCST)) {
            return false;
        }
        if (!Objects.equals(this.aliquotaICMSST, other.aliquotaICMSST)) {
            return false;
        }
        if (!Objects.equals(this.valorICMSST, other.valorICMSST)) {
            return false;
        }
        if (!Objects.equals(this.percentualMargemValorAdicionadoST, other.percentualMargemValorAdicionadoST)) {
            return false;
        }
        if (!Objects.equals(this.motivoDesoneracaoICMS, other.motivoDesoneracaoICMS)) {
            return false;
        }
        if (!Objects.equals(this.valorICMSDesoneracao, other.valorICMSDesoneracao)) {
            return false;
        }
        if (!Objects.equals(this.percentualReducaoBCST, other.percentualReducaoBCST)) {
            return false;
        }
        return true;
    }
}
