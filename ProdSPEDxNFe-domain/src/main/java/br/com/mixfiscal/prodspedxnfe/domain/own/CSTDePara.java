package br.com.mixfiscal.prodspedxnfe.domain.own;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name="cst_de_para")
public class CSTDePara implements Serializable {
    private static final long serialVersionUID = 5586027428984035705L;
    
    @Id
    @OneToOne
    @JoinColumn(name = "id_cst_de")
    private CST cstDe;
    
    @OneToOne
    @JoinColumn(name = "id_cst_para")
    private CST cstPara;

    public CST getCstDe() {
        return cstDe;
    }

    public void setCstDe(CST cstDe) {
        this.cstDe = cstDe;
    }

    public CST getCstPara() {
        return cstPara;
    }

    public void setCstPara(CST cstPara) {
        this.cstPara = cstPara;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.cstDe);
        hash = 53 * hash + Objects.hashCode(this.cstPara);
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
        final CSTDePara other = (CSTDePara) obj;
        if (!Objects.equals(this.cstDe, other.cstDe)) {
            return false;
        }
        if (!Objects.equals(this.cstPara, other.cstPara)) {
            return false;
        }
        return true;
    }
    
}
