package br.com.mixfiscal.prodspedxnfe.domain.own;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "relacao_produto_fornecedor")
public class RelacaoProdutoFornecedor implements Serializable {
    private static final long serialVersionUID = -138426280235315930L;    
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto")
    private Produto produto;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;
    
    @Id
    @Column(name = "referencia_fornecedor")
    private String referenciaFornecedor;
    
    @Column(name = "chave_nf")
    private String chaveNf;
    
    @Column(name = "prod_sped")
    private String prodSped;
    
    @Column(name = "prod_xml")
    private String prodXml;
    
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getReferenciaFornecedor() {
        return referenciaFornecedor;
    }

    public void setReferenciaFornecedor(String referenciaFornecedor) {
        this.referenciaFornecedor = referenciaFornecedor;
    }

    public String getChaveNf() {
        return chaveNf;
    }

    public void setChaveNf(String chaveNf) {
        this.chaveNf = chaveNf;
    }

    public String getProdSped() {
        return prodSped;
    }

    public void setProdSped(String prodSped) {
        this.prodSped = prodSped;
    }

    public String getProdXml() {
        return prodXml;
    }

    public void setProdXml(String prodXml) {
        this.prodXml = prodXml;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.produto);
        hash = 23 * hash + Objects.hashCode(this.fornecedor);
        hash = 23 * hash + Objects.hashCode(this.referenciaFornecedor);
        hash = 23 * hash + Objects.hashCode(this.chaveNf);
        hash = 23 * hash + Objects.hashCode(this.prodSped);
        hash = 23 * hash + Objects.hashCode(this.prodXml);
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
        final RelacaoProdutoFornecedor other = (RelacaoProdutoFornecedor) obj;
        if (!Objects.equals(this.referenciaFornecedor, other.referenciaFornecedor)) {
            return false;
        }
        if (!Objects.equals(this.produto, other.produto)) {
            return false;
        }
        if (!Objects.equals(this.fornecedor, other.fornecedor)) {
            return false;
        }
        if(!Objects.equals(this.chaveNf, other.chaveNf)){
            return false;
        }
        if(!Objects.equals(this.prodSped, other.prodSped)){
            return false;
        }
        if(!Objects.equals(this.prodXml, other.prodXml)){
            return false;
        }
        return true;
    }
}
