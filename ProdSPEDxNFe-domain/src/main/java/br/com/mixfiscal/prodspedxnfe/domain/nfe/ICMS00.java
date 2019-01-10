package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Objects;

public class ICMS00 extends ICMS implements Serializable{
    private static final long serialVersionUID = -6574529195309438393L;    
    
    private int modalidadeBaseCalculo;//<modBc>
    private BigDecimal valorBC;//<vBc>
    private BigDecimal aliquotaICMS;//<pIcms>
    private BigDecimal valorICMS;//<vIcms> 

    public int getModalidadeBaseCalculo() {
        return modalidadeBaseCalculo;
    }

    public void setModalidadeBaseCalculo(int modalidadeBaseCalculo) {
        this.modalidadeBaseCalculo = modalidadeBaseCalculo;
    }

    public BigDecimal getValorBC() {
        return valorBC;
    }

    public void setValorBC(BigDecimal valorBaseCalculo) {
        this.valorBC = valorBaseCalculo;
    }

    public BigDecimal getAliquotaICMS() {
        return aliquotaICMS;
    }

    public void setAliquotaICMS(BigDecimal aliquotaICMS) {
        this.aliquotaICMS = aliquotaICMS;
    }

    public BigDecimal getValorICMS() {
        return valorICMS;
    }

    public void setValorICMS(BigDecimal valorICMS) {
        this.valorICMS = valorICMS;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.modalidadeBaseCalculo;
        hash = 67 * hash + Objects.hashCode(this.valorBC);
        hash = 67 * hash + Objects.hashCode(this.aliquotaICMS);
        hash = 67 * hash + Objects.hashCode(this.valorICMS);
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
        final ICMS00 other = (ICMS00) obj;
        if (this.modalidadeBaseCalculo != other.modalidadeBaseCalculo) {
            return false;
        }
        if (!Objects.equals(this.valorBC, other.valorBC)) {
            return false;
        }
        if (!Objects.equals(this.aliquotaICMS, other.aliquotaICMS)) {
            return false;
        }
        if (!Objects.equals(this.valorICMS, other.valorICMS)) {
            return false;
        }
        return true;
    }
}
