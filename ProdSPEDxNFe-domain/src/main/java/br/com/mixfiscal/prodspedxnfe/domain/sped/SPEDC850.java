package br.com.mixfiscal.prodspedxnfe.domain.sped;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
public class SPEDC850 extends SPEDC170 implements Serializable {
    private static final long serialVersionUID = -1228832807186258183L; 
    
    private BigDecimal cstIcms;//CST_ICMS
    private String cfop;// CFOP
    private BigDecimal aliqIcms;//ALIQ_ICMS
    private BigDecimal vlOpr;//VL_OPR
    private BigDecimal vlBcIcms;//VL_BC_ICMS
    private BigDecimal vlIcms;//VL_ICMS
    private String codObs;//COD_OBS
    private SPED0200 spd0200;
    private SPEDC800 spdc800; 

    public BigDecimal getCstIcms() {
        return cstIcms;
    }

    public void setCstIcms(BigDecimal cstIcms) {
        this.cstIcms = cstIcms;
    }

    public String getCfop() {
        return cfop;
    }

    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

    public BigDecimal getAliqIcms() {
        return aliqIcms;
    }

    public void setAliqIcms(BigDecimal aliqIcms) {
        this.aliqIcms = aliqIcms;
    }

    public BigDecimal getVlOpr() {
        return vlOpr;
    }

    public void setVlOpr(BigDecimal vlOpr) {
        this.vlOpr = vlOpr;
    }

    public BigDecimal getVlBcIcms() {
        return vlBcIcms;
    }

    public void setVlBcIcms(BigDecimal vlBcIcms) {
        this.vlBcIcms = vlBcIcms;
    }

    public BigDecimal getVlIcms() {
        return vlIcms;
    }

    public void setVlIcms(BigDecimal vlIcms) {
        this.vlIcms = vlIcms;
    }

    public String getCodObs() {
        return codObs;
    }

    public void setCodObs(String codObs) {
        this.codObs = codObs;
    }

    public SPED0200 getSpd0200() {
        return spd0200;
    }

    public void setSpd0200(SPED0200 spd0200) {
        this.spd0200 = spd0200;
    }

    public SPEDC800 getSpdc800() {
        return spdc800;
    }

    public void setSpdc800(SPEDC800 spdc800) {
        this.spdc800 = spdc800;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.cstIcms);
        hash = 89 * hash + Objects.hashCode(this.cfop);
        hash = 89 * hash + Objects.hashCode(this.aliqIcms);
        hash = 89 * hash + Objects.hashCode(this.vlOpr);
        hash = 89 * hash + Objects.hashCode(this.vlBcIcms);
        hash = 89 * hash + Objects.hashCode(this.vlIcms);
        hash = 89 * hash + Objects.hashCode(this.codObs);
        hash = 89 * hash + Objects.hashCode(this.spd0200);
        hash = 89 * hash + Objects.hashCode(this.spdc800);
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
        final SPEDC850 other = (SPEDC850) obj;
        if (!Objects.equals(this.cfop, other.cfop)) {
            return false;
        }
        if (!Objects.equals(this.codObs, other.codObs)) {
            return false;
        }
        if (!Objects.equals(this.cstIcms, other.cstIcms)) {
            return false;
        }
        if (!Objects.equals(this.aliqIcms, other.aliqIcms)) {
            return false;
        }
        if (!Objects.equals(this.vlOpr, other.vlOpr)) {
            return false;
        }
        if (!Objects.equals(this.vlBcIcms, other.vlBcIcms)) {
            return false;
        }
        if (!Objects.equals(this.vlIcms, other.vlIcms)) {
            return false;
        }
        if (!Objects.equals(this.spd0200, other.spd0200)) {
            return false;
        }
        if (!Objects.equals(this.spdc800, other.spdc800)) {
            return false;
        }
        return true;
    }
    
    
    
}
