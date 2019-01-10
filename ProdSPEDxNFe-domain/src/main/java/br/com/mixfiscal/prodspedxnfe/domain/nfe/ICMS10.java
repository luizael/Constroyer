package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ICMS10 extends ICMS implements Serializable {
    private static final long serialVersionUID = 8072134380693730615L;
    
    private int modalidadeBaseCalculo;//<modBc>
    private BigDecimal valorBC;//<vBc>
    private BigDecimal aliquotaICMS;//<pIcms>
    private BigDecimal valorICMS;//<vIcms>
    private String modalidadeBCST; //<modBcSt>
    private BigDecimal valorBCST;//<vBcSt>
    private BigDecimal aliquotaICMSST;//<pIcmsSt>
    private BigDecimal valorICMSST;//<vIcmsSt>
    private BigDecimal percentualMargemValAdicionadoST;//<pMvaSt>
    private BigDecimal percentualReducaoBCST;//<pRedBcSt>    

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

    public String getModalidadeBCST() {
        return modalidadeBCST;
    }

    public void setModalidadeBCST(String modalidadeBCST) {
        this.modalidadeBCST = modalidadeBCST;
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

    public BigDecimal getPercentualMargemValAdicionadoST() {
        return percentualMargemValAdicionadoST;
    }

    public void setPercentualMargemValAdicionadoST(BigDecimal percentualMargemValAdicionadoST) {
        this.percentualMargemValAdicionadoST = percentualMargemValAdicionadoST;
    }

    public BigDecimal getPercentualReducaoBCST() {
        return percentualReducaoBCST;
    }

    public void setPercentualReducaoBCST(BigDecimal percentualReducaoBCST) {
        this.percentualReducaoBCST = percentualReducaoBCST;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.modalidadeBaseCalculo;
        hash = 97 * hash + Objects.hashCode(this.valorBC);
        hash = 97 * hash + Objects.hashCode(this.aliquotaICMS);
        hash = 97 * hash + Objects.hashCode(this.valorICMS);
        hash = 97 * hash + Objects.hashCode(this.modalidadeBCST);
        hash = 97 * hash + Objects.hashCode(this.valorBCST);
        hash = 97 * hash + Objects.hashCode(this.aliquotaICMSST);
        hash = 97 * hash + Objects.hashCode(this.valorICMSST);
        hash = 97 * hash + Objects.hashCode(this.percentualMargemValAdicionadoST);
        hash = 97 * hash + Objects.hashCode(this.percentualReducaoBCST);
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
        final ICMS10 other = (ICMS10) obj;
        if (this.modalidadeBaseCalculo != other.modalidadeBaseCalculo) {
            return false;
        }
        if (!Objects.equals(this.modalidadeBCST, other.modalidadeBCST)) {
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
        if (!Objects.equals(this.valorBCST, other.valorBCST)) {
            return false;
        }
        if (!Objects.equals(this.aliquotaICMSST, other.aliquotaICMSST)) {
            return false;
        }
        if (!Objects.equals(this.valorICMSST, other.valorICMSST)) {
            return false;
        }
        if (!Objects.equals(this.percentualMargemValAdicionadoST, other.percentualMargemValAdicionadoST)) {
            return false;
        }
        if (!Objects.equals(this.percentualReducaoBCST, other.percentualReducaoBCST)) {
            return false;
        }
        return true;
    }
}
