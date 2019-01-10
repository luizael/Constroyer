package br.com.mixfiscal.prodspedxnfe.domain.sped;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class SPEDC470 extends SPEDC170 implements Serializable{
    private static final long serialVersionUID = 4132703572620500982L;
       
    private BigDecimal qtdCanc;
    private SPED0200 SPED0200;  
    private SPEDC460 SPED460;

    public BigDecimal getQtdCanc() {
        return qtdCanc;
    }

    public void setQtdCanc(BigDecimal qtdCanc) {
        this.qtdCanc = qtdCanc;
    }
    
    
    public SPED0200 getSPED0200() {
        return SPED0200;
    }

    public void setSPED0200(SPED0200 SPED0200) {
        this.SPED0200 = SPED0200;
    }

    public SPEDC460 getSPED460() {
        return SPED460;
    }

    public void setSPED460(SPEDC460 SPED460) {
        this.SPED460 = SPED460;
    }   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.qtdCanc);
        hash = 67 * hash + Objects.hashCode(this.SPED0200);
        hash = 67 * hash + Objects.hashCode(this.SPED460);
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
        final SPEDC470 other = (SPEDC470) obj;
        if (!Objects.equals(this.qtdCanc, other.qtdCanc)) {
            return false;
        }
        if (!Objects.equals(this.SPED0200, other.SPED0200)) {
            return false;
        }
        if (!Objects.equals(this.SPED460, other.SPED460)) {
            return false;
        }
        return true;
    }
    
}
