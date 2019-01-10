package br.com.mixfiscal.prodspedxnfe.domain.sped;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SPEDBase implements Serializable {
    private static final long serialVersionUID = -7775954106701220305L;    
    
    private Map<String, Integer> indicesColunas;
    private int numLinhaSPED;    

    public SPEDBase() {
        indicesColunas = new HashMap<>();
    }
    
    public int getNumLinhaSPED() {
        return numLinhaSPED;
    }

    public void setNumLinhaSPED(int numLinhaSPED) {
        this.numLinhaSPED = numLinhaSPED;
    }

    protected final Map<String, Integer> getIndicesColunas() {
        return indicesColunas;
    }    

    public int getIndiceColuna(String coluna) {
        try {
            return indicesColunas.get(coluna);
        } catch(NullPointerException nex) {
            throw new NullPointerException(String.format("A coluna '%s' retornou NULL", coluna));
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.indicesColunas);
        hash = 79 * hash + this.numLinhaSPED;
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
        final SPEDBase other = (SPEDBase) obj;
        if (this.numLinhaSPED != other.numLinhaSPED) {
            return false;
        }
        if (!Objects.equals(this.indicesColunas, other.indicesColunas)) {
            return false;
        }
        return true;
    }
}
