package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;

public abstract class PIS implements Serializable {
    private static final long serialVersionUID = 1518479336189472023L;    
    
    private int CST;

    public int getCST() {
        return CST;
    }

    public void setCST(int CST) {
        this.CST = CST;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.CST;
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
        final PIS other = (PIS) obj;
        if (this.CST != other.CST) {
            return false;
        }
        return true;
    }
}
