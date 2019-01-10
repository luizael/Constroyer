package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.util.Objects;

public abstract class ICMS implements Serializable{
    private static final long serialVersionUID = -7327132501215844194L;
   
    private int CST;
    private String origem;
   
    public int getCST() {
        return CST;
    }   
    public void setCST(int cst) {
        this.CST = cst;
    }    
    public String getOrigem() {
        return origem;
    }    
    public void setOrigem(String origem) {
        this.origem = origem;
    }  

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.CST;
        hash = 83 * hash + Objects.hashCode(this.origem);
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
        final ICMS other = (ICMS) obj;
        if (this.CST != other.CST) {
            return false;
        }
        if (!Objects.equals(this.origem, other.origem)) {
            return false;
        }
        return true;
    }
    
    
}
