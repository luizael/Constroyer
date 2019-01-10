package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ICMS51 extends ICMS implements Serializable {
    private static final long serialVersionUID = -3461587765157305603L;
    
    private int modalidadeBaseCalculo;//<modBc>
    private BigDecimal valorBC;//<vBc>
    private BigDecimal aliquotaICMS;//<pIcms>
    private BigDecimal valorICMS;//<vIcms>
    private BigDecimal percentualReducaoBC;//<pPredBc>
    private BigDecimal percentualDiferimento;//<pDif>
    private BigDecimal valorICMSDiferimento;//<vIcmsDif>
    private BigDecimal valorICMSOperacao;//<vIcmsOp>;

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

    public BigDecimal getPercentualDiferimento() {
        return percentualDiferimento;
    }

    public void setPercentualDiferimento(BigDecimal percentualDiferimento) {
        this.percentualDiferimento = percentualDiferimento;
    }

    public BigDecimal getValorICMSDiferimento() {
        return valorICMSDiferimento;
    }

    public void setValorICMSDiferimento(BigDecimal valorICMSDiferimento) {
        this.valorICMSDiferimento = valorICMSDiferimento;
    }

    public BigDecimal getValorICMSOperacao() {
        return valorICMSOperacao;
    }

    public void setValorICMSOperacao(BigDecimal valorICMSOperacao) {
        this.valorICMSOperacao = valorICMSOperacao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.modalidadeBaseCalculo;
        hash = 23 * hash + Objects.hashCode(this.valorBC);
        hash = 23 * hash + Objects.hashCode(this.aliquotaICMS);
        hash = 23 * hash + Objects.hashCode(this.valorICMS);
        hash = 23 * hash + Objects.hashCode(this.percentualReducaoBC);
        hash = 23 * hash + Objects.hashCode(this.percentualDiferimento);
        hash = 23 * hash + Objects.hashCode(this.valorICMSDiferimento);
        hash = 23 * hash + Objects.hashCode(this.valorICMSOperacao);
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
        final ICMS51 other = (ICMS51) obj;
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
        if (!Objects.equals(this.percentualDiferimento, other.percentualDiferimento)) {
            return false;
        }
        if (!Objects.equals(this.valorICMSDiferimento, other.valorICMSDiferimento)) {
            return false;
        }
        if (!Objects.equals(this.valorICMSOperacao, other.valorICMSOperacao)) {
            return false;
        }
        return true;
    }
}
