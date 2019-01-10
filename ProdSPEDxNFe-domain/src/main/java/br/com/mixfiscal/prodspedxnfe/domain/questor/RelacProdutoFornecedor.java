package br.com.mixfiscal.prodspedxnfe.domain.questor;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "RELACPRODUTOFORNEC")
@IdClass(RelacProdutoFornecedorPK.class)
public class RelacProdutoFornecedor implements Serializable {
    private static final long serialVersionUID = 6039772996733390185L;    
    
    @Id
    @Column(name = "CODIGOEMPRESA")
    private Integer codigoEmpresa;    
    @Id
    @Column(name = "CODIGOESTAB")
    private Integer codigoEstab;    
    @Id
    @Column(name = "CODIGOPESSOA")
    private Integer codigoPessoa;
    @Id
    @Column(name = "REFERITEMFORNEC")
    private String referItemFornecedor;    
    @Column(name = "CODIGOPRODUTO")
    private Integer codigoProduto;    
    @Column(name = "CODIGOCFOP")
    private Integer codigoCFOP;    
    @Column(name = "CSTICMS")
    private Short cstIcms;    
    @Column(name = "CSTIPI")
    private Short cstIpi;
    
    public Integer getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(Integer codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public Integer getCodigoEstab() {
        return codigoEstab;
    }

    public void setCodigoEstab(Integer codigoEstab) {
        this.codigoEstab = codigoEstab;
    }

    public Integer getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Integer codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public String getReferItemFornecedor() {
        return referItemFornecedor;
    }

    public void setReferItemFornecedor(String referItemFornecedor) {
        this.referItemFornecedor = referItemFornecedor;
    }

    public Integer getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Integer codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public Integer getCodigoCFOP() {
        return codigoCFOP;
    }

    public void setCodigoCFOP(Integer codigoCFOP) {
        this.codigoCFOP = codigoCFOP;
    }

    public Short getCstIcms() {
        return cstIcms;
    }

    public void setCstIcms(Short cstIcms) {
        this.cstIcms = cstIcms;
    }

    public Short getCstIpi() {
        return cstIpi;
    }

    public void setCstIpi(Short cstIpi) {
        this.cstIpi = cstIpi;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.codigoEmpresa);
        hash = 47 * hash + Objects.hashCode(this.codigoEstab);
        hash = 47 * hash + Objects.hashCode(this.codigoPessoa);
        hash = 47 * hash + Objects.hashCode(this.referItemFornecedor);
        hash = 47 * hash + Objects.hashCode(this.codigoProduto);
        hash = 47 * hash + Objects.hashCode(this.codigoCFOP);
        hash = 47 * hash + Objects.hashCode(this.cstIcms);
        hash = 47 * hash + Objects.hashCode(this.cstIpi);
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
        final RelacProdutoFornecedor other = (RelacProdutoFornecedor) obj;
        if (!Objects.equals(this.referItemFornecedor, other.referItemFornecedor)) {
            return false;
        }
        if (!Objects.equals(this.codigoEmpresa, other.codigoEmpresa)) {
            return false;
        }
        if (!Objects.equals(this.codigoEstab, other.codigoEstab)) {
            return false;
        }
        if (!Objects.equals(this.codigoPessoa, other.codigoPessoa)) {
            return false;
        }
        if (!Objects.equals(this.codigoProduto, other.codigoProduto)) {
            return false;
        }
        if (!Objects.equals(this.codigoCFOP, other.codigoCFOP)) {
            return false;
        }
        if (!Objects.equals(this.cstIcms, other.cstIcms)) {
            return false;
        }
        if (!Objects.equals(this.cstIpi, other.cstIpi)) {
            return false;
        }
        return true;
    }
}
