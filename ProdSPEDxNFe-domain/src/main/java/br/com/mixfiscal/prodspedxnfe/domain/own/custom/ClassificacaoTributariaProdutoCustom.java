package br.com.mixfiscal.prodspedxnfe.domain.own.custom;

import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributaria;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsEntrada;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsSaida;
import br.com.mixfiscal.prodspedxnfe.domain.own.PisCofins;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="classificacao_tributaria_produto")
public class ClassificacaoTributariaProdutoCustom implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_class_tributaria_prod")    
    private Integer idProduto;
   
    @Column(name = "codigo_produto")
    private String codigoProduto;
    
    @Column(name = "ean")
    private String ean;
    
    @Column(name = "piscofins_id_clas_tributaria_prod_pis_cofins")
    private Integer idPisCofins;
    
    @Column(name = "icmsentrada_id_class_tributaria_prod_icms_entrada")
    private Integer idIcmsEntrada;
    
    @Column(name = "icmssaida_id_class_tributaria_prod_icms_saida")
    private Integer idIcmsSaida; 
    
    @Column(name = "classificacao_tributaria_id")
    private Integer idClassificacaoTributariaId; 
    
    @Column(name = "descricao")
    private String descricao; 
    
    @Column(name = "cnpj_cliente")
    private String cnpj;

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public Integer getIdPisCofins() {
        return idPisCofins;
    }

    public void setIdPisCofins(Integer idPisCofins) {
        this.idPisCofins = idPisCofins;
    }

    public Integer getIdIcmsEntrada() {
        return idIcmsEntrada;
    }

    public void setIdIcmsEntrada(Integer idIcmsEntrada) {
        this.idIcmsEntrada = idIcmsEntrada;
    }

    public Integer getIdIcmsSaida() {
        return idIcmsSaida;
    }

    public void setIdIcmsSaida(Integer idIcmsSaida) {
        this.idIcmsSaida = idIcmsSaida;
    }

    public Integer getIdClassificacaoTributariaId() {
        return idClassificacaoTributariaId;
    }

    public void setIdClassificacaoTributariaId(Integer idClassificacaoTributariaId) {
        this.idClassificacaoTributariaId = idClassificacaoTributariaId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.idProduto);
        hash = 19 * hash + Objects.hashCode(this.codigoProduto);
        hash = 19 * hash + Objects.hashCode(this.ean);
        hash = 19 * hash + Objects.hashCode(this.idPisCofins);
        hash = 19 * hash + Objects.hashCode(this.idIcmsEntrada);
        hash = 19 * hash + Objects.hashCode(this.idIcmsSaida);
        hash = 19 * hash + Objects.hashCode(this.idClassificacaoTributariaId);
        hash = 19 * hash + Objects.hashCode(this.descricao);
        hash = 19 * hash + Objects.hashCode(this.cnpj);
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
        final ClassificacaoTributariaProdutoCustom other = (ClassificacaoTributariaProdutoCustom) obj;
        if (!Objects.equals(this.codigoProduto, other.codigoProduto)) {
            return false;
        }
        if (!Objects.equals(this.ean, other.ean)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        if (!Objects.equals(this.idProduto, other.idProduto)) {
            return false;
        }
        if (!Objects.equals(this.idPisCofins, other.idPisCofins)) {
            return false;
        }
        if (!Objects.equals(this.idIcmsEntrada, other.idIcmsEntrada)) {
            return false;
        }
        if (!Objects.equals(this.idIcmsSaida, other.idIcmsSaida)) {
            return false;
        }
        if (!Objects.equals(this.idClassificacaoTributariaId, other.idClassificacaoTributariaId)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
