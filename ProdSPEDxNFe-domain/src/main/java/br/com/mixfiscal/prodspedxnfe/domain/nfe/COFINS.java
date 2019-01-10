package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;

public class COFINS implements Serializable {
    private static final long serialVersionUID = 83328469683909865L;    
    
    private int CST;

    public int getCST() {
        return CST;
    }

    public void setCST(int CST) {
        this.CST = CST;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.CST;
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
        final COFINS other = (COFINS) obj;
        if (this.CST != other.CST) {
            return false;
        }
        return true;
    }
}
