package br.com.mixfiscal.prodspedxnfe.domain.questor;

import java.io.Serializable;
import java.util.Objects;

public class RelacProdutoFornecedorPK implements Serializable {    
    private Integer codigoEmpresa;        
    private Integer codigoEstab;        
    private Integer codigoPessoa;            
    private String referItemFornecedor;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.codigoEmpresa);
        hash = 37 * hash + Objects.hashCode(this.codigoEstab);
        hash = 37 * hash + Objects.hashCode(this.codigoPessoa);
        hash = 37 * hash + Objects.hashCode(this.referItemFornecedor);
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
        final RelacProdutoFornecedorPK other = (RelacProdutoFornecedorPK) obj;
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
        return true;
    }    
}
