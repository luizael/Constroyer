package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class PISQtde extends PIS implements Serializable {
    private static final long serialVersionUID = -1957329767140518024L;    
    
    private BigDecimal quantidadeBCProd; // <qBCProd>
    private BigDecimal valorAliquotaProd; // <vAliqProd>
    private BigDecimal valorPIS; // <vPIS>

    public BigDecimal getQuantidadeBCProd() {
        return quantidadeBCProd;
    }

    public void setQuantidadeBCProd(BigDecimal quantidadeBCProd) {
        this.quantidadeBCProd = quantidadeBCProd;
    }

    public BigDecimal getValorAliquotaProd() {
        return valorAliquotaProd;
    }

    public void setValorAliquotaProd(BigDecimal valorAliquotaProd) {
        this.valorAliquotaProd = valorAliquotaProd;
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
        hash = 23 * hash + Objects.hashCode(this.quantidadeBCProd);
        hash = 23 * hash + Objects.hashCode(this.valorAliquotaProd);
        hash = 23 * hash + Objects.hashCode(this.valorPIS);
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
        final PISQtde other = (PISQtde) obj;
        if (!Objects.equals(this.quantidadeBCProd, other.quantidadeBCProd)) {
            return false;
        }
        if (!Objects.equals(this.valorAliquotaProd, other.valorAliquotaProd)) {
            return false;
        }
        if (!Objects.equals(this.valorPIS, other.valorPIS)) {
            return false;
        }
        return true;
    }
}
