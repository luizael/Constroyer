
package br.com.mixfiscal.prodspedxnfe.domain.own;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import com.google.gson.annotations.SerializedName;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Version;

@Entity
@Table(name = "classificacao_tributaria_produto_icms_entrada")
public class IcmsEntrada  implements Serializable{

    private static final long serialVersionUID = -2498845841695041821L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_class_tributaria_prod_icms_entrada")
    private Integer id;
     
    @Column(name = "tipo_mva")
    @SerializedName("tipo_mva")
    private String tipoMva;
    
    @Column(name = "mva")
    @SerializedName("mva")
    private String mva;
    
    @Column(name = "mva_data_ini") 
    @SerializedName("mva_data_ini")
    private String mvaDataIni;
    
    @Column(name = "mva_data_fim") 
    @SerializedName("mva_data_fim")
    private String mvaDataFim;
    
    @Column(name = "credito_outorgado")    
    @SerializedName("credito_outorgado")
    private String creditoOutorgado;
    
    @Column(name = "gera_debito")    
    @SerializedName("gera_debito")
    private String geraDebito;
    
    @Column(name = "sub_rbc_alq")    
    @SerializedName("sub_rbc_alq")
    private String subRbcAlq;
    
    @Column(name = "ei_cst")
    @SerializedName("ei_cst")    
    private String eiCst;
    
    @Column(name = "ei_alq")
    @SerializedName("ei_alq")
    private String eiAlq;
    
    @Column(name = "ei_alqst")    
    @SerializedName("ei_alqst")
    private String eiAlqst;
    
    @Column(name = "ei_rbc")    
    @SerializedName("ei_rbc")
    private String eiRbc;
    
    @Column(name = "ei_rbcst")    
    @SerializedName("ei_rbcst")
    private String eiRbcst;
   
    @Column(name = "ed_cst")    
    @SerializedName("ed_cst")
    private String edCst;
    
    @Column(name = "ed_alq")    
    @SerializedName("ed_alq")
    private String edAlq;
    
    @Column(name = "ed_alqst") 
    @SerializedName("ed_alqst")
    private String edAlqst;
    
    @Column(name = "ed_rbc")    
    @SerializedName("ed_rbc")
    private String edRbc;
    
    @Column(name = "ed_rbcst")    
    @SerializedName("ed_rbcst")
    private String edRbcst;
    
    @Column(name = "es_cst")    
    @SerializedName("es_cst")
    private String esCst;
    
    @Column(name = "es_alq")    
    @SerializedName("es_alq")
    private String esAlq;
    
    @Column(name = "es_alqst")    
    @SerializedName("es_alqst")
    private String esAlqst;
    
    @Column(name = "es_rbc")    
    @SerializedName("es_rbc")
    private String esRbc;
    
    @Column(name = "es_rbcst")    
    @SerializedName("es_rbcst")
    private String esRbcst;
    
    @Column(name = "nfi_cst")    
    @SerializedName("nfi_cst")
    private String nfiCst;
    
    @Column(name = "nfd_cst")    
    @SerializedName("nfd_cst")
    private String nfdCst;
     
    @Column(name = "nfs_csosn")    
    @SerializedName("nfs_csosn")
    private String nfsCsosn;
    
    @Column(name = "nf_alq")    
    @SerializedName("nf_alq")
    private String nfAlq;
    
    @Column(name = "fundamento_legal")    
    @SerializedName("fundamento_legal")
    private String fundamentoLegal;
    
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "id_class_tributaria_prod")    
    private ClassificacaoTributariaProduto produto;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
   
    public String getTipoMva() {
        return tipoMva;
    }

    public void setTipoMva(String tipoMva) {
        this.tipoMva = tipoMva;
    }

    public String getMva() {
        return mva;
    }

    public void setMva(String mva) {
        this.mva = mva;
    }

    public String getMvaDataIni() {
        return mvaDataIni;
    }

    public void setMvaDataIni(String mvaDataIni) {
        this.mvaDataIni = mvaDataIni;
    }

    public String getMvaDataFim() {
        return mvaDataFim;
    }

    public void setMvaDataFim(String mvaDataFim) {
        this.mvaDataFim = mvaDataFim;
    }

    public String getCreditoOutorgado() {
        return creditoOutorgado;
    }

    public void setCreditoOutorgado(String creditoOutorgado) {
        this.creditoOutorgado = creditoOutorgado;
    }

    public String getGeraDebito() {
        return geraDebito;
    }

    public void setGeraDebito(String geraDebito) {
        this.geraDebito = geraDebito;
    }

    public String getSubRbcAlq() {
        return subRbcAlq;
    }

    public void setSubRbcAlq(String subRbcAlq) {
        this.subRbcAlq = subRbcAlq;
    }

    public String getEiCst() {
        return eiCst;
    }

    public void setEiCst(String eiCst) {
        this.eiCst = eiCst;
    }

    public String getEiAlq() {
        return eiAlq;
    }

    public void setEiAlq(String eiAlq) {
        this.eiAlq = eiAlq;
    }

    public String getEiAlqst() {
        return eiAlqst;
    }

    public void setEiAlqst(String eiAlqst) {
        this.eiAlqst = eiAlqst;
    }

    public String getEiRbc() {
        return eiRbc;
    }

    public void setEiRbc(String eiRbc) {
        this.eiRbc = eiRbc;
    }

    public String getEiRbcst() {
        return eiRbcst;
    }

    public void setEiRbcst(String eiRbcst) {
        this.eiRbcst = eiRbcst;
    }

    public String getEdCst() {
        return edCst;
    }

    public void setEdCst(String edCst) {
        this.edCst = edCst;
    }

    public String getEdAlq() {
        return edAlq;
    }

    public void setEdAlq(String edAlq) {
        this.edAlq = edAlq;
    }

    public String getEdAlqst() {
        return edAlqst;
    }

    public void setEdAlqst(String edAlqst) {
        this.edAlqst = edAlqst;
    }

    public String getEdRbc() {
        return edRbc;
    }

    public void setEdRbc(String edRbc) {
        this.edRbc = edRbc;
    }

    public String getEdRbcst() {
        return edRbcst;
    }

    public void setEdRbcst(String edRbcst) {
        this.edRbcst = edRbcst;
    }

    public String getEsCst() {
        return esCst;
    }

    public void setEsCst(String esCst) {
        this.esCst = esCst;
    }

    public String getEsAlq() {
        return esAlq;
    }

    public void setEsAlq(String esAlq) {
        this.esAlq = esAlq;
    }

    public String getEsAlqst() {
        return esAlqst;
    }

    public void setEsAlqst(String esAlqst) {
        this.esAlqst = esAlqst;
    }

    public String getEsRbc() {
        return esRbc;
    }

    public void setEsRbc(String esRbc) {
        this.esRbc = esRbc;
    }

    public String getEsRbcst() {
        return esRbcst;
    }

    public void setEsRbcst(String esRbcst) {
        this.esRbcst = esRbcst;
    }

    public String getNfiCst() {
        return nfiCst;
    }

    public void setNfiCst(String nfiCst) {
        this.nfiCst = nfiCst;
    }

    public String getNfdCst() {
        return nfdCst;
    }

    public void setNfdCst(String nfdCst) {
        this.nfdCst = nfdCst;
    }

    public String getNfsCsosn() {
        return nfsCsosn;
    }

    public void setNfsCsosn(String nfsCsosn) {
        this.nfsCsosn = nfsCsosn;
    }

    public String getNfAlq() {
        return nfAlq;
    }

    public void setNfAlq(String nfAlq) {
        this.nfAlq = nfAlq;
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
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.tipoMva);
        hash = 89 * hash + Objects.hashCode(this.mva);
        hash = 89 * hash + Objects.hashCode(this.mvaDataIni);
        hash = 89 * hash + Objects.hashCode(this.mvaDataFim);
        hash = 89 * hash + Objects.hashCode(this.creditoOutorgado);
        hash = 89 * hash + Objects.hashCode(this.geraDebito);
        hash = 89 * hash + Objects.hashCode(this.subRbcAlq);
        hash = 89 * hash + Objects.hashCode(this.eiCst);
        hash = 89 * hash + Objects.hashCode(this.eiAlq);
        hash = 89 * hash + Objects.hashCode(this.eiAlqst);
        hash = 89 * hash + Objects.hashCode(this.eiRbc);
        hash = 89 * hash + Objects.hashCode(this.eiRbcst);
        hash = 89 * hash + Objects.hashCode(this.edCst);
        hash = 89 * hash + Objects.hashCode(this.edAlq);
        hash = 89 * hash + Objects.hashCode(this.edAlqst);
        hash = 89 * hash + Objects.hashCode(this.edRbc);
        hash = 89 * hash + Objects.hashCode(this.edRbcst);
        hash = 89 * hash + Objects.hashCode(this.esCst);
        hash = 89 * hash + Objects.hashCode(this.esAlq);
        hash = 89 * hash + Objects.hashCode(this.esAlqst);
        hash = 89 * hash + Objects.hashCode(this.esRbc);
        hash = 89 * hash + Objects.hashCode(this.esRbcst);
        hash = 89 * hash + Objects.hashCode(this.nfiCst);
        hash = 89 * hash + Objects.hashCode(this.nfdCst);
        hash = 89 * hash + Objects.hashCode(this.nfsCsosn);
        hash = 89 * hash + Objects.hashCode(this.nfAlq);
        hash = 89 * hash + Objects.hashCode(this.fundamentoLegal);
        hash = 89 * hash + Objects.hashCode(this.produto);
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
        final IcmsEntrada other = (IcmsEntrada) obj;
        if (!Objects.equals(this.tipoMva, other.tipoMva)) {
            return false;
        }
        if (!Objects.equals(this.mva, other.mva)) {
            return false;
        }
        if (!Objects.equals(this.mvaDataIni, other.mvaDataIni)) {
            return false;
        }
        if (!Objects.equals(this.mvaDataFim, other.mvaDataFim)) {
            return false;
        }
        if (!Objects.equals(this.creditoOutorgado, other.creditoOutorgado)) {
            return false;
        }
        if (!Objects.equals(this.geraDebito, other.geraDebito)) {
            return false;
        }
        if (!Objects.equals(this.subRbcAlq, other.subRbcAlq)) {
            return false;
        }
        if (!Objects.equals(this.eiCst, other.eiCst)) {
            return false;
        }
        if (!Objects.equals(this.eiAlq, other.eiAlq)) {
            return false;
        }
        if (!Objects.equals(this.eiAlqst, other.eiAlqst)) {
            return false;
        }
        if (!Objects.equals(this.eiRbc, other.eiRbc)) {
            return false;
        }
        if (!Objects.equals(this.eiRbcst, other.eiRbcst)) {
            return false;
        }
        if (!Objects.equals(this.edCst, other.edCst)) {
            return false;
        }
        if (!Objects.equals(this.edAlq, other.edAlq)) {
            return false;
        }
        if (!Objects.equals(this.edAlqst, other.edAlqst)) {
            return false;
        }
        if (!Objects.equals(this.edRbc, other.edRbc)) {
            return false;
        }
        if (!Objects.equals(this.edRbcst, other.edRbcst)) {
            return false;
        }
        if (!Objects.equals(this.esCst, other.esCst)) {
            return false;
        }
        if (!Objects.equals(this.esAlq, other.esAlq)) {
            return false;
        }
        if (!Objects.equals(this.esAlqst, other.esAlqst)) {
            return false;
        }
        if (!Objects.equals(this.esRbc, other.esRbc)) {
            return false;
        }
        if (!Objects.equals(this.esRbcst, other.esRbcst)) {
            return false;
        }
        if (!Objects.equals(this.nfiCst, other.nfiCst)) {
            return false;
        }
        if (!Objects.equals(this.nfdCst, other.nfdCst)) {
            return false;
        }
        if (!Objects.equals(this.nfsCsosn, other.nfsCsosn)) {
            return false;
        }
        if (!Objects.equals(this.nfAlq, other.nfAlq)) {
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
