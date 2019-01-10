package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class COFINSOutr extends COFINS implements Serializable {
    private static final long serialVersionUID = -9026550884132791462L;    
    
    private BigDecimal valorBC; // <vBC>
    private BigDecimal percentualCOFINS; // <pCOFINS>
    private BigDecimal quantidadeBCProd; // <qBCProd>
    private BigDecimal valorAliquotaProd; // <vAliqProd>
    private BigDecimal valorCOFINS; // <vCOFINS>

    public BigDecimal getValorBC() {
        return valorBC;
    }

    public void setValorBC(BigDecimal valorBC) {
        this.valorBC = valorBC;
    }

    public BigDecimal getPercentualCOFINS() {
        return percentualCOFINS;
    }

    public void setPercentualCOFINS(BigDecimal percentualCOFINS) {
        this.percentualCOFINS = percentualCOFINS;
    }

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

    public BigDecimal getValorCOFINS() {
        return valorCOFINS;
    }

    public void setValorCOFINS(BigDecimal valorCOFINS) {
        this.valorCOFINS = valorCOFINS;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.valorBC);
        hash = 79 * hash + Objects.hashCode(this.percentualCOFINS);
        hash = 79 * hash + Objects.hashCode(this.quantidadeBCProd);
        hash = 79 * hash + Objects.hashCode(this.valorAliquotaProd);
        hash = 79 * hash + Objects.hashCode(this.valorCOFINS);
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
        final COFINSOutr other = (COFINSOutr) obj;
        if (!Objects.equals(this.valorBC, other.valorBC)) {
            return false;
        }
        if (!Objects.equals(this.percentualCOFINS, other.percentualCOFINS)) {
            return false;
        }
        if (!Objects.equals(this.quantidadeBCProd, other.quantidadeBCProd)) {
            return false;
        }
        if (!Objects.equals(this.valorAliquotaProd, other.valorAliquotaProd)) {
            return false;
        }
        if (!Objects.equals(this.valorCOFINS, other.valorCOFINS)) {
            return false;
        }
        return true;
    }
}
