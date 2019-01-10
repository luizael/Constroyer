
package br.com.mixfiscal.prodspedxnfe.domain.own;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;
import javax.persistence.Column;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.OneToOne;

@Entity
@Table(name = "classificacao_tributaria_produto_pis_cofins")
public class PisCofins implements Serializable{
    private static final long serialVersionUID = 1028180207329629975L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clas_tributaria_prod_pis_cofins")
    private Integer id;
    
    @Column(name = "ncm")
    @SerializedName("ncm")
    private String ncm;
    
    @Column(name = "ncm_ex")
    @SerializedName("ncm_ex")
    private String ncmEx;
    
    @Column(name = "cod_natureza_receita")
    @SerializedName("cod_natureza_receita")
    private String codNaturezaReceita;
    
    @Column(name = "credito_presumido")
    @SerializedName("credito_presumido")
    private String creditoPresumido;
    
    @Column(name = "pis_cst_e")
    @SerializedName("pis_cst_e")
    private String pisCstE;
    
    @Column(name = "pis_cst_s")
    @SerializedName("pis_cst_s")
    private String pisCstS;
    
    @Column(name = "pis_alq_e")
    @SerializedName("pis_alq_e")
    private String pisAlqE;
    
    @Column(name = "pis_alq_s")
    @SerializedName("pis_alq_s")
    private String pisAlqS;
    
    @Column(name = "cofins_cst_e")
    @SerializedName("cofins_cst_e")
    private String cofinsCstE;
    
    @Column(name = "cofins_cst_s")
    @SerializedName("cofins_cst_s")
    private String cofinsCstS;
    
    @Column(name = "cofins_alq_e")
    @SerializedName("cofins_alq_e")
    private String cofinsAlqE;
    
    @Column(name = "cofins_alq_s")
    @SerializedName("cofins_alq_s")    
    private String cofinsAlqS;
    
    @OneToOne( fetch=FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "id_class_tributaria_prod")
    private ClassificacaoTributariaProduto produto;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }    
    
    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public String getNcmEx() {
        return ncmEx;
    }

    public void setNcmEx(String ncmEx) {
        this.ncmEx = ncmEx;
    }

    public String getCodNaturezaReceita() {
        return codNaturezaReceita;
    }

    public void setCodNaturezaReceita(String codNaturezaReceita) {
        this.codNaturezaReceita = codNaturezaReceita;
    }

    public String getCreditoPresumido() {
        return creditoPresumido;
    }

    public void setCreditoPresumido(String creditoPresumido) {
        this.creditoPresumido = creditoPresumido;
    }

    public String getPisCstE() {
        return pisCstE;
    }

    public void setPisCstE(String pisCstE) {
        this.pisCstE = pisCstE;
    }

    public String getPisCstS() {
        return pisCstS;
    }

    public void setPisCstS(String pisCstS) {
        this.pisCstS = pisCstS;
    }

    public String getPisAlqE() {
        return pisAlqE;
    }

    public void setPisAlqE(String pisAlqE) {
        this.pisAlqE = pisAlqE;
    }

    public String getPisAlqS() {
        return pisAlqS;
    }

    public void setPisAlqS(String pisAlqS) {
        this.pisAlqS = pisAlqS;
    }

    public String getCofinsCstE() {
        return cofinsCstE;
    }

    public void setCofinsCstE(String cofinsCstE) {
        this.cofinsCstE = cofinsCstE;
    }

    public String getCofinsCstS() {
        return cofinsCstS;
    }

    public void setCofinsCstS(String cofinsCstS) {
        this.cofinsCstS = cofinsCstS;
    }

    public String getCofinsAlqE() {
        return cofinsAlqE;
    }

    public void setCofinsAlqE(String cofinsAlqE) {
        this.cofinsAlqE = cofinsAlqE;
    }

    public String getCofinsAlqS() {
        return cofinsAlqS;
    }

    public void setCofinsAlqS(String cofinsAlqS) {
        this.cofinsAlqS = cofinsAlqS;
    }

    public ClassificacaoTributariaProduto getProduto() {
        return produto;
    }

    public void setProduto(ClassificacaoTributariaProduto produto) {
        this.produto = produto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.ncm);
        hash = 37 * hash + Objects.hashCode(this.ncmEx);
        hash = 37 * hash + Objects.hashCode(this.codNaturezaReceita);
        hash = 37 * hash + Objects.hashCode(this.creditoPresumido);
        hash = 37 * hash + Objects.hashCode(this.pisCstE);
        hash = 37 * hash + Objects.hashCode(this.pisCstS);
        hash = 37 * hash + Objects.hashCode(this.pisAlqE);
        hash = 37 * hash + Objects.hashCode(this.pisAlqS);
        hash = 37 * hash + Objects.hashCode(this.cofinsCstE);
        hash = 37 * hash + Objects.hashCode(this.cofinsCstS);
        hash = 37 * hash + Objects.hashCode(this.cofinsAlqE);
        hash = 37 * hash + Objects.hashCode(this.cofinsAlqS);
        hash = 37 * hash + Objects.hashCode(this.produto);
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
        final PisCofins other = (PisCofins) obj;
        if (!Objects.equals(this.ncm, other.ncm)) {
            return false;
        }
        if (!Objects.equals(this.ncmEx, other.ncmEx)) {
            return false;
        }
        if (!Objects.equals(this.codNaturezaReceita, other.codNaturezaReceita)) {
            return false;
        }
        if (!Objects.equals(this.creditoPresumido, other.creditoPresumido)) {
            return false;
        }
        if (!Objects.equals(this.pisCstE, other.pisCstE)) {
            return false;
        }
        if (!Objects.equals(this.pisCstS, other.pisCstS)) {
            return false;
        }
        if (!Objects.equals(this.pisAlqE, other.pisAlqE)) {
            return false;
        }
        if (!Objects.equals(this.pisAlqS, other.pisAlqS)) {
            return false;
        }
        if (!Objects.equals(this.cofinsCstE, other.cofinsCstE)) {
            return false;
        }
        if (!Objects.equals(this.cofinsCstS, other.cofinsCstS)) {
            return false;
        }
        if (!Objects.equals(this.cofinsAlqE, other.cofinsAlqE)) {
            return false;
        }
        if (!Objects.equals(this.cofinsAlqS, other.cofinsAlqS)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.produto, other.produto)) {
            return false;
        }
        return true;
    }
}
