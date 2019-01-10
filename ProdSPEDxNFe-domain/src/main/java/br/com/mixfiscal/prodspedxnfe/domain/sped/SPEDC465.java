package br.com.mixfiscal.prodspedxnfe.domain.sped;

import java.io.Serializable;
import java.util.Objects;

public class SPEDC465 extends SPEDC460 implements Serializable {
    private static final long serialVersionUID = -3003757811243686919L;
    private SPEDC460 sc460;
    private String chvCfe;
    private String numCcf;

    public String getChvCfe() {
        return chvCfe;
    }

    public void setChvCfe(String chvCfe) {
        this.chvCfe = chvCfe;
    }

    public String getNumCcf() {
        return numCcf;
    }

    public void setNumCcf(String numCcf) {
        this.numCcf = numCcf;
    }

    public SPEDC460 getSc460() {
        return sc460;
    }

    public void setSc460(SPEDC460 sc460) {
        this.sc460 = sc460;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.sc460);
        hash = 97 * hash + Objects.hashCode(this.chvCfe);
        hash = 97 * hash + Objects.hashCode(this.numCcf);
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
        final SPEDC465 other = (SPEDC465) obj;
        if (!Objects.equals(this.chvCfe, other.chvCfe)) {
            return false;
        }
        if (!Objects.equals(this.numCcf, other.numCcf)) {
            return false;
        }
        if (!Objects.equals(this.sc460, other.sc460)) {
            return false;
        }
        return true;
    }
    
}
