
package br.com.mixfiscal.prodspedxnfe.domain.own;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Version;

@Entity
@Table(name = "classificacao_tributaria_produto_icms_saida")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class IcmsSaida implements Serializable{
    private static final long serialVersionUID = 5049372152905575958L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_class_tributaria_prod_icms_saida")
    private Integer id;
        
    @Column(name = "sac_cst")
    @SerializedName("sac_cst")    
    private String sacCst;
    
    @Column(name = "sac_alq")    
    @SerializedName("sac_alq")    
    private String sacAlq;
 
    @Column(name = "sac_alqst")    
    @SerializedName("sac_alqst")    
    private String sacAlqst;
    
    @Column(name = "sac_rbc")    
    @SerializedName("sac_rbc")    
    private String  sacRbc;
    
    @Column(name = "sac_rbcst")    
    @SerializedName("sac_rbcst")    
    private String sacRbcst;
   
    @Column(name = "sas_cst")    
    @SerializedName("sas_cst")    
    private String sasCst;
    
    @Column(name = "sas_alq")    
    @SerializedName("sas_alq")    
    private String sasAlq;
   
    @Column(name = "sas_alqst")    
    @SerializedName("sas_alqst")    
    private String sasAlqst;
   
    @Column(name = "sas_rbc")    
    @SerializedName("sas_rbc")    
    private String sasRbc;
    
    @Column(name = "sas_rbcst")    
    @SerializedName("sas_rbcst")    
    private String sasRbcst;
   
    @Column(name = "svc_cst")    
    @SerializedName("svc_cst")    
    private String svcCst;
    
    @Column(name = "svc_alq")    
    @SerializedName("svc_alq")    
    private String svcAlq;
   
    @Column(name = "svc_alqst")    
    @SerializedName("svc_alqst")    
    private String svcAlqst;
   
    @Column(name = "svc_rbc")
    @SerializedName("svc_rbc")    
    private String svcRbc;
   
    @Column(name = "svc_rbcst")
    @SerializedName("svc_rbcst")    
    private String svcRbcst;
    
    @Column(name = "snc_cst")
    @SerializedName("snc_cst")    
    private String sncCst;
    
    @Column(name = "snc_alq")
    @SerializedName("snc_alq")    
    private String sncAlq;

    @Column(name = "snc_alqst")
    @SerializedName("snc_alqst")    
    private String sncAlqst;
    
    @Column(name = "snc_rbc")
    @SerializedName("snc_rbc")    
    private String sncRbc;
    
    @Column(name = "snc_rbcst")    
    @SerializedName("snc_rbcst")    
    private String sncRbcst;
    
    @Column(name = "fundamento_legal")    
    @SerializedName("fundamento_legal")    
    private String fundamentoLegal;

    @OneToOne( fetch=FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "id_class_tributaria_prod")
    private ClassificacaoTributariaProduto produto;
                
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSacCst() {
        return sacCst;
    }

    public void setSacCst(String sacCst) {
        this.sacCst = sacCst;
    }

    public String getSacAlq() {
        return sacAlq;
    }

    public void setSacAlq(String sacAlq) {
        this.sacAlq = sacAlq;
    }

    public String getSacAlqst() {
        return sacAlqst;
    }

    public void setSacAlqst(String sacAlqst) {
        this.sacAlqst = sacAlqst;
    }

    public String getSacRbc() {
        return sacRbc;
    }

    public void setSacRbc(String sacRbc) {
        this.sacRbc = sacRbc;
    }

    public String getSacRbcst() {
        return sacRbcst;
    }

    public void setSacRbcst(String sacRbcst) {
        this.sacRbcst = sacRbcst;
    }

    public String getSasCst() {
        return sasCst;
    }

    public void setSasCst(String sasCst) {
        this.sasCst = sasCst;
    }

    public String getSasAlq() {
        return sasAlq;
    }

    public void setSasAlq(String sasAlq) {
        this.sasAlq = sasAlq;
    }

    public String getSasAlqst() {
        return sasAlqst;
    }

    public void setSasAlqst(String sasAlqst) {
        this.sasAlqst = sasAlqst;
    }

    public String getSasRbc() {
        return sasRbc;
    }

    public void setSasRbc(String sasRbc) {
        this.sasRbc = sasRbc;
    }

    public String getSasRbcst() {
        return sasRbcst;
    }

    public void setSasRbcst(String sasRbcst) {
        this.sasRbcst = sasRbcst;
    }

    public String getSvcCst() {
        return svcCst;
    }

    public void setSvcCst(String svcCst) {
        this.svcCst = svcCst;
    }

    public String getSvcAlq() {
        return svcAlq;
    }

    public void setSvcAlq(String svcAlq) {
        this.svcAlq = svcAlq;
    }

    public String getSvcAlqst() {
        return svcAlqst;
    }

    public void setSvcAlqst(String svcAlqst) {
        this.svcAlqst = svcAlqst;
    }

    public String getSvcRbc() {
        return svcRbc;
    }

    public void setSvcRbc(String svcRbc) {
        this.svcRbc = svcRbc;
    }

    public String getSvcRbcst() {
        return svcRbcst;
    }

    public void setSvcRbcst(String svcRbcst) {
        this.svcRbcst = svcRbcst;
    }

    public String getSncCst() {
        return sncCst;
    }

    public void setSncCst(String sncCst) {
        this.sncCst = sncCst;
    }

    public String getSncAlq() {
        return sncAlq;
    }

    public void setSncAlq(String sncAlq) {
        this.sncAlq = sncAlq;
    }

    public String getSncAlqst() {
        return sncAlqst;
    }

    public void setSncAlqst(String sncAlqst) {
        this.sncAlqst = sncAlqst;
    }

    public String getSncRbc() {
        return sncRbc;
    }

    public void setSncRbc(String sncRbc) {
        this.sncRbc = sncRbc;
    }

    public String getSncRbcst() {
        return sncRbcst;
    }

    public void setSncRbcst(String sncRbcst) {
        this.sncRbcst = sncRbcst;
    }

    public String getFundamentoLegal() {
        return fundamentoLegal;
    }

    public void setFundamentoLegal(String fundamentoLegal) {
        this.fundamentoLegal = fundamentoLegal;
    }

    public ClassificacaoTributariaProduto getProduto() {
        return produto;
    }

    public void setProduto(ClassificacaoTributariaProduto produto) {
        this.produto = produto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.sacCst);
        hash = 47 * hash + Objects.hashCode(this.sacAlq);
        hash = 47 * hash + Objects.hashCode(this.sacAlqst);
        hash = 47 * hash + Objects.hashCode(this.sacRbc);
        hash = 47 * hash + Objects.hashCode(this.sacRbcst);
        hash = 47 * hash + Objects.hashCode(this.sasCst);
        hash = 47 * hash + Objects.hashCode(this.sasAlq);
        hash = 47 * hash + Objects.hashCode(this.sasAlqst);
        hash = 47 * hash + Objects.hashCode(this.sasRbc);
        hash = 47 * hash + Objects.hashCode(this.sasRbcst);
        hash = 47 * hash + Objects.hashCode(this.svcCst);
        hash = 47 * hash + Objects.hashCode(this.svcAlq);
        hash = 47 * hash + Objects.hashCode(this.svcAlqst);
        hash = 47 * hash + Objects.hashCode(this.svcRbc);
        hash = 47 * hash + Objects.hashCode(this.svcRbcst);
        hash = 47 * hash + Objects.hashCode(this.sncCst);
        hash = 47 * hash + Objects.hashCode(this.sncAlq);
        hash = 47 * hash + Objects.hashCode(this.sncAlqst);
        hash = 47 * hash + Objects.hashCode(this.sncRbc);
        hash = 47 * hash + Objects.hashCode(this.sncRbcst);
        hash = 47 * hash + Objects.hashCode(this.fundamentoLegal);
        hash = 47 * hash + Objects.hashCode(this.produto);
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
        final IcmsSaida other = (IcmsSaida) obj;
        if (!Objects.equals(this.sacCst, other.sacCst)) {
            return false;
        }
        if (!Objects.equals(this.sacAlq, other.sacAlq)) {
            return false;
        }
        if (!Objects.equals(this.sacAlqst, other.sacAlqst)) {
            return false;
        }
        if (!Objects.equals(this.sacRbc, other.sacRbc)) {
            return false;
        }
        if (!Objects.equals(this.sacRbcst, other.sacRbcst)) {
            return false;
        }
        if (!Objects.equals(this.sasCst, other.sasCst)) {
            return false;
        }
        if (!Objects.equals(this.sasAlq, other.sasAlq)) {
            return false;
        }
        if (!Objects.equals(this.sasAlqst, other.sasAlqst)) {
            return false;
        }
        if (!Objects.equals(this.sasRbc, other.sasRbc)) {
            return false;
        }
        if (!Objects.equals(this.sasRbcst, other.sasRbcst)) {
            return false;
        }
        if (!Objects.equals(this.svcCst, other.svcCst)) {
            return false;
        }
        if (!Objects.equals(this.svcAlq, other.svcAlq)) {
            return false;
        }
        if (!Objects.equals(this.svcAlqst, other.svcAlqst)) {
            return false;
        }
        if (!Objects.equals(this.svcRbc, other.svcRbc)) {
            return false;
        }
        if (!Objects.equals(this.svcRbcst, other.svcRbcst)) {
            return false;
        }
        if (!Objects.equals(this.sncCst, other.sncCst)) {
            return false;
        }
        if (!Objects.equals(this.sncAlq, other.sncAlq)) {
            return false;
        }
        if (!Objects.equals(this.sncAlqst, other.sncAlqst)) {
            return false;
        }
        if (!Objects.equals(this.sncRbc, other.sncRbc)) {
            return false;
        }
        if (!Objects.equals(this.sncRbcst, other.sncRbcst)) {
            return false;
        }
        if (!Objects.equals(this.fundamentoLegal, other.fundamentoLegal)) {
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
