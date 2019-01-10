package br.com.mixfiscal.prodspedxnfe.domain.own;

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
import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "classificacao_tributaria_produto")
@BatchSize(size=100)
public class ClassificacaoTributariaProduto implements Serializable {
    private static final long serialVersionUID = 8817089223728489676L;
    
    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_class_tributaria_prod")    
    private Integer idProduto;
   
    @Column(name = "codigo_produto")
    @SerializedName("codigo_produto")
    private String codigoProduto;
    
    @Column(name = "ean")
    @SerializedName("ean")
    private String ean;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_class_tributaria")
    private ClassificacaoTributaria classificacaoTributaria;
  
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "produto", cascade = CascadeType.ALL)
    @SerializedName("icms_entrada")
    private IcmsEntrada icmsEntrada;
    
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "produto", cascade = CascadeType.ALL)
    @SerializedName("icms_saida")
    private IcmsSaida icmsSaida; 
    
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "produto", cascade = CascadeType.ALL)
    @SerializedName("pis_cofins")
    private PisCofins pisCofins;
    
    @Column(name = "descricao")
    private String descricao;   
    
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

    public PisCofins getPisCofins() {
        return pisCofins;
    }

    public void setPisCofins(PisCofins pisCofins) {
        this.pisCofins = pisCofins;
    }

    public IcmsEntrada getIcmsEntrada() {
        return icmsEntrada;
    }

    public void setIcmsEntrada(IcmsEntrada icmsEntrada) {
        this.icmsEntrada = icmsEntrada;        
    }

    public IcmsSaida getIcmsSaida() {
        return icmsSaida;        
    }

    public void setIcmsSaida(IcmsSaida icmsSaida) {
        this.icmsSaida = icmsSaida;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ClassificacaoTributaria getClassificacaoTributaria() {
        return classificacaoTributaria;
    }

    public void setClassificacaoTributaria(ClassificacaoTributaria classificacaoTributaria) {
        this.classificacaoTributaria = classificacaoTributaria;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idProduto);
        hash = 67 * hash + Objects.hashCode(this.codigoProduto);
        hash = 67 * hash + Objects.hashCode(this.ean);
        hash = 67 * hash + Objects.hashCode(this.classificacaoTributaria);
        hash = 67 * hash + Objects.hashCode(this.icmsEntrada);
        hash = 67 * hash + Objects.hashCode(this.icmsSaida);
        hash = 67 * hash + Objects.hashCode(this.pisCofins);
        hash = 67 * hash + Objects.hashCode(this.descricao);
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
        final ClassificacaoTributariaProduto other = (ClassificacaoTributariaProduto) obj;
        if (!Objects.equals(this.codigoProduto, other.codigoProduto)) {
            return false;
        }
        if (!Objects.equals(this.ean, other.ean)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.idProduto, other.idProduto)) {
            return false;
        }
        if (!Objects.equals(this.classificacaoTributaria, other.classificacaoTributaria)) {
            return false;
        }
        if (!Objects.equals(this.icmsEntrada, other.icmsEntrada)) {
            return false;
        }
        if (!Objects.equals(this.icmsSaida, other.icmsSaida)) {
            return false;
        }
        if (!Objects.equals(this.pisCofins, other.pisCofins)) {
            return false;
        }
        return true;
    }
    
}
