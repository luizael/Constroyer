package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class PISAliq extends PIS implements Serializable {
    private static final long serialVersionUID = -2384361994781202576L;    
    
    private BigDecimal valorBC; // <vBC>
    private BigDecimal percentualPIS; // <pPIS>
    private BigDecimal valorPIS; // <vPIS>

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

    public BigDecimal getValorPIS() {
        return valorPIS;
    }

    public void setValorPIS(BigDecimal valorPIS) {
        this.valorPIS = valorPIS;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.valorBC);
        hash = 83 * hash + Objects.hashCode(this.percentualPIS);
        hash = 83 * hash + Objects.hashCode(this.valorPIS);
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
        final PISAliq other = (PISAliq) obj;
        if (!Objects.equals(this.valorBC, other.valorBC)) {
            return false;
        }
        if (!Objects.equals(this.percentualPIS, other.percentualPIS)) {
            return false;
        }
        if (!Objects.equals(this.valorPIS, other.valorPIS)) {
            return false;
        }
        return true;
    }
}
