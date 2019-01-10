package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class COFINSQtde extends COFINS implements Serializable {
    private static final long serialVersionUID = -8660792550064614117L;    
    
    private BigDecimal quantidadeBCProd;
    private BigDecimal valorAliquotaProd;
    private BigDecimal valorCOFINS;

    public BigDecimal getQuantidadeBCProd() {
        return quantidadeBCProd;
    }

    public void setQuantidadeBCProd(BigDecimal quantidadeBCProd) {
        this.quantidadeBCProd = quantidadeBCProd;
    }

    public BigDecimal getValorAliquotaProd() {
        return valorAliquotaProd;
    }

    public void setValorAliquotaProd(BigDecimal valorAliquotaProd) {
        this.valorAliquotaProd = valorAliquotaProd;
    }

    public BigDecimal getValorCOFINS() {
        return valorCOFINS;
    }

    public void setValorCOFINS(BigDecimal valorCOFINS) {
        this.valorCOFINS = valorCOFINS;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.quantidadeBCProd);
        hash = 61 * hash + Objects.hashCode(this.valorAliquotaProd);
        hash = 61 * hash + Objects.hashCode(this.valorCOFINS);
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
        final COFINSQtde other = (COFINSQtde) obj;
        if (!Objects.equals(this.quantidadeBCProd, other.quantidadeBCProd)) {
            return false;
        }
        if (!Objects.equals(this.valorAliquotaProd, other.valorAliquotaProd)) {
            return false;
        }
        if (!Objects.equals(this.valorCOFINS, other.valorCOFINS)) {
            return false;
        }
        return true;
    }
}
