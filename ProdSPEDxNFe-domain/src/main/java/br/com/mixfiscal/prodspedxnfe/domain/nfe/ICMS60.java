package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ICMS60 extends ICMS implements Serializable {
    private static final long serialVersionUID = -210698550572558929L;
    
    private BigDecimal valorBCSTRet;
    private BigDecimal valorICMSSTRet;

    public BigDecimal getValorBCSTRet() {
        return valorBCSTRet;
    }

    public void setValorBCSTRet(BigDecimal valorBCSTRet) {
        this.valorBCSTRet = valorBCSTRet;
    }

    public BigDecimal getValorICMSSTRet() {
        return valorICMSSTRet;
    }

    public void setValorICMSSTRet(BigDecimal valorICMSSTRet) {
        this.valorICMSSTRet = valorICMSSTRet;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.valorBCSTRet);
        hash = 97 * hash + Objects.hashCode(this.valorICMSSTRet);
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
        final ICMS60 other = (ICMS60) obj;
        if (!Objects.equals(this.valorBCSTRet, other.valorBCSTRet)) {
            return false;
        }
        if (!Objects.equals(this.valorICMSSTRet, other.valorICMSSTRet)) {
            return false;
        }
        return true;
    }
}
