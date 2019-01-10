package br.com.mixfiscal.prodspedxnfe.domain.nfe;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ICMS70 extends ICMS implements Serializable {  
    private static final long serialVersionUID = -315436285397382417L;    
    
    private int modalidadeBaseCalculo;//<modBc>
    private BigDecimal valorBC;//<vBc>
    private BigDecimal aliquotaICMS;//<pIcms>
    private BigDecimal valorICMS;//<vIcms>
    private BigDecimal percentualReducaoBC;//<pRedBc>
    private int modalidadeBCST;//<modBcSt>
    private BigDecimal aliquotaICMSST;
    private BigDecimal valorICMSST;
    private BigDecimal percentualMVAST;
    private BigDecimal valorBCST;
    private int motivoDesoneracaoICMS;
    private BigDecimal valorICMSDesonerado;
    private BigDecimal percentualReducaoBCST;

    public int getModalidadeBaseCalculo() {
        return modalidadeBaseCalculo;
    }

    public void setModalidadeBaseCalculo(int modalidadeBaseCalculo) {
        this.modalidadeBaseCalculo = modalidadeBaseCalculo;
    }

    public BigDecimal getValorBC() {
        return valorBC;
    }

    public void setValorBC(BigDecimal valorBC) {
        this.valorBC = valorBC;
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

    public BigDecimal getPercentualReducaoBC() {
        return percentualReducaoBC;
    }

    public void setPercentualReducaoBC(BigDecimal percentualReducaoBC) {
        this.percentualReducaoBC = percentualReducaoBC;
    }

    public int getModalidadeBCST() {
        return modalidadeBCST;
    }

    public void setModalidadeBCST(int modalidadeBCST) {
        this.modalidadeBCST = modalidadeBCST;
    }

    public BigDecimal getAliquotaICMSST() {
        return aliquotaICMSST;
    }

    public void setAliquotaICMSST(BigDecimal aliquotaICMSST) {
        this.aliquotaICMSST = aliquotaICMSST;
    }

    public BigDecimal getValorICMSST() {
        return valorICMSST;
    }

    public void setValorICMSST(BigDecimal valorICMSST) {
        this.valorICMSST = valorICMSST;
    }

    public BigDecimal getPercentualMVAST() {
        return percentualMVAST;
    }

    public void setPercentualMVAST(BigDecimal percentualMVAST) {
        this.percentualMVAST = percentualMVAST;
    }

    public BigDecimal getValorBCST() {
        return valorBCST;
    }

    public void setValorBCST(BigDecimal valorBCST) {
        this.valorBCST = valorBCST;
    }

    public int getMotivoDesoneracaoICMS() {
        return motivoDesoneracaoICMS;
    }

    public void setMotivoDesoneracaoICMS(int motivoDesoneracaoICMS) {
        this.motivoDesoneracaoICMS = motivoDesoneracaoICMS;
    }

    public BigDecimal getValorICMSDesonerado() {
        return valorICMSDesonerado;
    }

    public void setValorICMSDesonerado(BigDecimal valorICMSDesonerado) {
        this.valorICMSDesonerado = valorICMSDesonerado;
    }

    public BigDecimal getPercentualReducaoBCST() {
        return percentualReducaoBCST;
    }

    public void setPercentualReducaoBCST(BigDecimal percentualReducaoBCST) {
        this.percentualReducaoBCST = percentualReducaoBCST;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + this.modalidadeBaseCalculo;
        hash = 43 * hash + Objects.hashCode(this.valorBC);
        hash = 43 * hash + Objects.hashCode(this.aliquotaICMS);
        hash = 43 * hash + Objects.hashCode(this.valorICMS);
        hash = 43 * hash + Objects.hashCode(this.percentualReducaoBC);
        hash = 43 * hash + this.modalidadeBCST;
        hash = 43 * hash + Objects.hashCode(this.aliquotaICMSST);
        hash = 43 * hash + Objects.hashCode(this.valorICMSST);
        hash = 43 * hash + Objects.hashCode(this.percentualMVAST);
        hash = 43 * hash + Objects.hashCode(this.valorBCST);
        hash = 43 * hash + Objects.hashCode(this.motivoDesoneracaoICMS);
        hash = 43 * hash + Objects.hashCode(this.valorICMSDesonerado);
        hash = 43 * hash + Objects.hashCode(this.percentualReducaoBCST);
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
        final ICMS70 other = (ICMS70) obj;
        if (this.modalidadeBaseCalculo != other.modalidadeBaseCalculo) {
            return false;
        }
        if (this.modalidadeBCST != other.modalidadeBCST) {
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
        if (!Objects.equals(this.percentualReducaoBC, other.percentualReducaoBC)) {
            return false;
        }
        if (!Objects.equals(this.aliquotaICMSST, other.aliquotaICMSST)) {
            return false;
        }
        if (!Objects.equals(this.valorICMSST, other.valorICMSST)) {
            return false;
        }
        if (!Objects.equals(this.percentualMVAST, other.percentualMVAST)) {
            return false;
        }
        if (!Objects.equals(this.valorBCST, other.valorBCST)) {
            return false;
        }
        if (!Objects.equals(this.motivoDesoneracaoICMS, other.motivoDesoneracaoICMS)) {
            return false;
        }
        if (!Objects.equals(this.valorICMSDesonerado, other.valorICMSDesonerado)) {
            return false;
        }
        if (!Objects.equals(this.percentualReducaoBCST, other.percentualReducaoBCST)) {
            return false;
        }
        return true;
    }
}
