package br.com.mixfiscal.prodspedxnfe.domain.own;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto implements Serializable {
    private static final long serialVersionUID = 4434542602215643993L;    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
    @Column(name = "referencia_produto")
    private String referenciaProduto;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "unidade_medida")
    private String unidadeMedida;
    @Column(name = "ean")
    private String ean;
    @Column(name = "ncm")
    private String ncm;
    @Column(name = "aliq_icms")
    private BigDecimal aliqICMS;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produto")
    private Set<RelacaoProdutoFornecedor> produtosDeFornecedores;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getReferenciaProduto() {
        return referenciaProduto;
    }

    public void setReferenciaProduto(String referenciaProduto) {
        this.referenciaProduto = referenciaProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public BigDecimal getAliqICMS() {
        return aliqICMS;
    }

    public void setAliqICMS(BigDecimal aliqICMS) {
        this.aliqICMS = aliqICMS;
    }

    public Set<RelacaoProdutoFornecedor> getProdutosDeFornecedores() {
        return produtosDeFornecedores;
    }

    public void setProdutosDeFornecedores(Set<RelacaoProdutoFornecedor> produtosDeFornecedores) {
        this.produtosDeFornecedores = produtosDeFornecedores;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.empresa);
        hash = 71 * hash + Objects.hashCode(this.referenciaProduto);
        hash = 71 * hash + Objects.hashCode(this.descricao);
        hash = 71 * hash + Objects.hashCode(this.unidadeMedida);
        hash = 71 * hash + Objects.hashCode(this.ean);
        hash = 71 * hash + Objects.hashCode(this.ncm);
        hash = 71 * hash + Objects.hashCode(this.aliqICMS);
        hash = 71 * hash + Objects.hashCode(this.produtosDeFornecedores);
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
        final Produto other = (Produto) obj;
        if (!Objects.equals(this.referenciaProduto, other.referenciaProduto)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.unidadeMedida, other.unidadeMedida)) {
            return false;
        }
        if (!Objects.equals(this.ean, other.ean)) {
            return false;
        }
        if (!Objects.equals(this.ncm, other.ncm)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.empresa, other.empresa)) {
            return false;
        }
        if (!Objects.equals(this.aliqICMS, other.aliqICMS)) {
            return false;
        }
        if (!Objects.equals(this.produtosDeFornecedores, other.produtosDeFornecedores)) {
            return false;
        }
        return true;
    }
}
