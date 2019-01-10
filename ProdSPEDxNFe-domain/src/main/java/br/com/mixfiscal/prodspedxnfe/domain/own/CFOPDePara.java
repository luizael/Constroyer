package br.com.mixfiscal.prodspedxnfe.domain.own;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Table(name = "cfop_de_para")
public class CFOPDePara implements Serializable {
    private static final long serialVersionUID = -4280024345574856244L;        
    
    @Id
    @OneToOne
    @JoinColumn(name = "id_cfop_de")
    private CFOP cfopDe;
        
    @OneToOne
    @JoinColumn(name = "id_cfop_para")
    private CFOP cfopPara;

    public CFOP getCfopDe() {
        return cfopDe;
    }

    public void setCfopDe(CFOP cfopDe) {
        this.cfopDe = cfopDe;
    }

    public CFOP getCfopPara() {
        return cfopPara;
    }

    public void setCfopPara(CFOP cfopPara) {
        this.cfopPara = cfopPara;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.cfopDe);
        hash = 11 * hash + Objects.hashCode(this.cfopPara);
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
        final CFOPDePara other = (CFOPDePara) obj;
        if (!Objects.equals(this.cfopDe, other.cfopDe)) {
            return false;
        }
        if (!Objects.equals(this.cfopPara, other.cfopPara)) {
            return false;
        }
        return true;
    }
}
