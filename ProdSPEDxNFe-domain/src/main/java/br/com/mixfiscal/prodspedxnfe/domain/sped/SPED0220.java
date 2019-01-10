package br.com.mixfiscal.prodspedxnfe.domain.sped;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class SPED0220 extends SPEDBase implements Serializable {
    private static final long serialVersionUID = -8298070573395387159L;    
    
    public String nomeUnidadeMedida;
    public BigDecimal fator;

    public SPED0220() {
        this.getIndicesColunas().put("nomeUnidadeMedida", 2);
        this.getIndicesColunas().put("fator", 3);
    }
    
    // <editor-fold desc="Getter e Setter" defaultstate="collapsed">
    public String getNomeUnidadeMedida() {
        return nomeUnidadeMedida;
    }

    public void setNomeUnidadeMedida(String nomeUnidadeMedida) {
        this.nomeUnidadeMedida = nomeUnidadeMedida;
    }

    public BigDecimal getFator() {
        return fator;
    }

    public void setFator(BigDecimal fator) {
        this.fator = fator;
    }
    // </editor-fold>

    // <editor-fold desc="equals e hashCode" defaultstate="collapsed">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.nomeUnidadeMedida);
        hash = 67 * hash + Objects.hashCode(this.fator);
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
        final SPED0220 other = (SPED0220) obj;
        if (!Objects.equals(this.nomeUnidadeMedida, other.nomeUnidadeMedida)) {
            return false;
        }
        if (!Objects.equals(this.fator, other.fator)) {
            return false;
        }
        return true;
    }
    // </editor-fold>
}
