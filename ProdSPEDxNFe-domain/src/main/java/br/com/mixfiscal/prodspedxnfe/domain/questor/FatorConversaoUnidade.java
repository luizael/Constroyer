package br.com.mixfiscal.prodspedxnfe.domain.questor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SPEDFIS0220")
public class FatorConversaoUnidade implements Serializable {
    private static final long serialVersionUID = -4614240093761523313L;    
    
    @Id
    @Column(name = "CODIGOEMPRESA")
    private Integer codigoEmpresa;
    @Id
    @Column(name = "CODIGOPRODUTO")
    private Integer codigoProduto;
    @Id
    @Column(name = "CODIGOUN")
    private String codigoUnidadeMedida;
    @Column(name = "FATOR")
    private BigDecimal fator;

    public Integer getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(Integer codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public Integer getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Integer codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getCodigoUnidadeMedida() {
        return codigoUnidadeMedida;
    }

    public void setCodigoUnidadeMedida(String codigoUnidadeMedida) {
        this.codigoUnidadeMedida = codigoUnidadeMedida;
    }

    public BigDecimal getFator() {
        return fator;
    }

    public void setFator(BigDecimal fator) {
        this.fator = fator;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.codigoEmpresa);
        hash = 79 * hash + Objects.hashCode(this.codigoProduto);
        hash = 79 * hash + Objects.hashCode(this.codigoUnidadeMedida);
        hash = 79 * hash + Objects.hashCode(this.fator);
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
        final FatorConversaoUnidade other = (FatorConversaoUnidade) obj;
        if (!Objects.equals(this.codigoUnidadeMedida, other.codigoUnidadeMedida)) {
            return false;
        }
        if (!Objects.equals(this.codigoEmpresa, other.codigoEmpresa)) {
            return false;
        }
        if (!Objects.equals(this.codigoProduto, other.codigoProduto)) {
            return false;
        }
        if (!Objects.equals(this.fator, other.fator)) {
            return false;
        }
        return true;
    }
}
