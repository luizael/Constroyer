package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class TotalImposto implements Serializable { 
    private static final long serialVersionUID = -4353819997199861693L;   
    
    private BigDecimal valorBCICMS;
    private BigDecimal aliqICMS;
    private BigDecimal valorICMS;
    private BigDecimal valorBCICMSST;
    private BigDecimal aliqICMSST;
    private BigDecimal valorICMSST; 
    private BigDecimal valorBCSTRet;
    private BigDecimal valorICMSSTRet;
    private NFeItem nfeItem;
    
    public BigDecimal getValorBCICMS() {
        return valorBCICMS;
    }

    public void setValorBCICMS(BigDecimal valorBCICMS) {
        this.valorBCICMS = valorBCICMS;
    }

    public BigDecimal getAliqICMS() {
        return aliqICMS;
    }

    public void setAliqICMS(BigDecimal aliqICMS) {
        this.aliqICMS = aliqICMS;
    }

    public BigDecimal getValorICMS() {
        return valorICMS;
    }

    public void setValorICMS(BigDecimal valorICMS) {
        this.valorICMS = valorICMS;
    }

    public BigDecimal getValorBCICMSST() {
        return valorBCICMSST;
    }

    public void setValorBCICMSST(BigDecimal valorBCICMSST) {
        this.valorBCICMSST = valorBCICMSST;
    }

    public BigDecimal getAliqICMSST() {
        return aliqICMSST;
    }

    public void setAliqICMSST(BigDecimal aliqICMSST) {
        this.aliqICMSST = aliqICMSST;
    }

    public BigDecimal getValorICMSST() {
        return valorICMSST;
    }

    public void setValorICMSST(BigDecimal valorICMSST) {
        this.valorICMSST = valorICMSST;
    }

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

    public NFeItem getNfeItem() {
        return nfeItem;
    }

    public void setNfeItem(NFeItem nfeItem) {
        this.nfeItem = nfeItem;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.valorBCICMS);
        hash = 89 * hash + Objects.hashCode(this.aliqICMS);
        hash = 89 * hash + Objects.hashCode(this.valorICMS);
        hash = 89 * hash + Objects.hashCode(this.valorBCICMSST);
        hash = 89 * hash + Objects.hashCode(this.aliqICMSST);
        hash = 89 * hash + Objects.hashCode(this.valorICMSST);
        hash = 89 * hash + Objects.hashCode(this.valorBCSTRet);
        hash = 89 * hash + Objects.hashCode(this.valorICMSSTRet);
        hash = 89 * hash + Objects.hashCode(this.nfeItem);
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
        final TotalImposto other = (TotalImposto) obj;
        if (!Objects.equals(this.valorBCICMS, other.valorBCICMS)) {
            return false;
        }
        if (!Objects.equals(this.aliqICMS, other.aliqICMS)) {
            return false;
        }
        if (!Objects.equals(this.valorICMS, other.valorICMS)) {
            return false;
        }
        if (!Objects.equals(this.valorBCICMSST, other.valorBCICMSST)) {
            return false;
        }
        if (!Objects.equals(this.aliqICMSST, other.aliqICMSST)) {
            return false;
        }
        if (!Objects.equals(this.valorICMSST, other.valorICMSST)) {
            return false;
        }
        if (!Objects.equals(this.valorBCSTRet, other.valorBCSTRet)) {
            return false;
        }
        if (!Objects.equals(this.valorICMSSTRet, other.valorICMSSTRet)) {
            return false;
        }
        if (!Objects.equals(this.nfeItem, other.nfeItem)) {
           return false;
        }
        return true;
    }
    
    
}
