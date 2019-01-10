package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class PISOutr extends PIS implements Serializable {
    private static final long serialVersionUID = -4943033908668465164L;    
    
    private BigDecimal valorBC;
    private BigDecimal percentualPIS;
    private BigDecimal quantidadeBCProd;
    private BigDecimal valorAliqProd;
    private BigDecimal valorPIS;

    public BigDecimal getValorBC() {
        return valorBC;
    }

    public void setValorBC(BigDecimal valorBC) {
        this.valorBC = valorBC;
    }

    public BigDecimal getPercentualPIS() {
        return percentualPIS;
    }

    public void setPercentualPIS(BigDecimal percentualPIS) {
        this.percentualPIS = percentualPIS;
    }

    public BigDecimal getQuantidadeBCProd() {
        return quantidadeBCProd;
    }

    public void setQuantidadeBCProd(BigDecimal quantidadeBCProd) {
        this.quantidadeBCProd = quantidadeBCProd;
    }

    public BigDecimal getValorAliqProd() {
        return valorAliqProd;
    }

    public void setValorAliqProd(BigDecimal valorAliqProd) {
        this.valorAliqProd = valorAliqProd;
    }

    public BigDecimal getValorPIS() {
        return valorPIS;
    }

    public void setValorPIS(BigDecimal valorPIS) {
        this.valorPIS = valorPIS;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.valorBC);
        hash = 97 * hash + Objects.hashCode(this.percentualPIS);
        hash = 97 * hash + Objects.hashCode(this.quantidadeBCProd);
        hash = 97 * hash + Objects.hashCode(this.valorAliqProd);
        hash = 97 * hash + Objects.hashCode(this.valorPIS);
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
        final PISOutr other = (PISOutr) obj;
        if (!Objects.equals(this.valorBC, other.valorBC)) {
            return false;
        }
        if (!Objects.equals(this.percentualPIS, other.percentualPIS)) {
            return false;
        }
        if (!Objects.equals(this.quantidadeBCProd, other.quantidadeBCProd)) {
            return false;
        }
        if (!Objects.equals(this.valorAliqProd, other.valorAliqProd)) {
            return false;
        }
        if (!Objects.equals(this.valorPIS, other.valorPIS)) {
            return false;
        }
        return true;
    }
}
