package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class COFINSAliq extends COFINS implements Serializable {
    private static final long serialVersionUID = 1638400702077419896L;    
    
    private BigDecimal valorBC;
    private BigDecimal percentualCOFINS;
    private BigDecimal valorCOFINS;

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

    public BigDecimal getValorCOFINS() {
        return valorCOFINS;
    }

    public void setValorCOFINS(BigDecimal valorCOFINS) {
        this.valorCOFINS = valorCOFINS;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.valorBC);
        hash = 89 * hash + Objects.hashCode(this.percentualCOFINS);
        hash = 89 * hash + Objects.hashCode(this.valorCOFINS);
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
        final COFINSAliq other = (COFINSAliq) obj;
        if (!Objects.equals(this.valorBC, other.valorBC)) {
            return false;
        }
        if (!Objects.equals(this.percentualCOFINS, other.percentualCOFINS)) {
            return false;
        }
        if (!Objects.equals(this.valorCOFINS, other.valorCOFINS)) {
            return false;
        }
        return true;
    }
}
