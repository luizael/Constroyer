package br.com.mixfiscal.prodspedxnfe.domain.mixfiscalamazon;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "cmf_controyer_produtos")
@Immutable
@IdClass(CmfConstroyerProduto.CmfConstroyerProdutoPK.class)
public class CmfConstroyerProduto implements Serializable {   
    @Id
    @Column(name = "cmf_cli_id")
    private Long id;    
    @Id
    @Column(name = "codigo_produto")
    private Long codigoProduto;
    @Column(name = "ean")
    private Long ean;
    @Column(name = "descritivo_produto")
    private String descritivoProduto;
    @Column(name = "mxf_piscofins_cst_e")
    private String pisCofinsCstE;
    @Column(name = "mxf_piscofins_cst_s")
    private String pisCofinsCstS;
    @Column(name = "mxf_pis_alq_e")
    private Integer pisAlqE;
    @Column(name = "mxf_pis_alq_s")
    private Integer pisAlqS;
    @Column(name = "mxf_cofins_alq_e")
    private Integer cofinsAlqE;
    @Column(name = "mxf_cofins_alq_s")
    private Integer cofinsAlqS;
    @Column(name = "mxf_sac_cst")
    private String sacCst;
    @Column(name = "mxf_sac_alq")
    private Integer sacAlq;
    @Column(name = "mxf_sac_alqst")
    private Integer sacAlqSt;
    @Column(name = "mxf_sac_rbc")
    private Integer sacRbc;
    @Column(name = "mxf_sac_rbcst")
    private Integer sacRbCst;
    @Column(name = "mxf_sas_cst")
    private String sasCst;
    @Column(name = "mxf_sas_alq")
    private Integer sasAlq;
    @Column(name = "mxf_sas_alqst")
    private Integer sasAlqSt;
    @Column(name = "mxf_sas_rbc")
    private Integer sasRbc;
    @Column(name = "mxf_sas_rbcst")
    private Integer sasRbCst;
    @Column(name = "mxf_svc_cst")
    private String svcCst;
    @Column(name = "mxf_svc_alq")
    private Integer svcAlq;
    @Column(name = "mxf_svc_alqst")
    private Integer svcAlqSt;
    @Column(name = "mxf_svc_rbc")
    private Integer svcRbc;
    @Column(name = "mxf_svc_rbcst")
    private Integer svcRbCst;
    @Column(name = "mxf_snc_cst")
    private String sncCst;
    @Column(name = "mxf_snc_alq")
    private Integer sncAlq;
    @Column(name = "mxf_snc_alqst")
    private Integer sncAlqSt;
    @Column(name = "mxf_snc_rbc")
    private Integer sncRbc;
    @Column(name = "mxf_snc_rbcst")
    private Integer sncRbCst;
    @Column(name = "mxf_ei_cst")
    private String eiCst;
    @Column(name = "mxf_ei_alq")
    private Integer eiAlq;
    @Column(name = "mxf_ei_alqst")
    private Integer eiAlqSt;
    @Column(name = "mxf_ei_rbc")
    private Integer eiRbc;
    @Column(name = "mxf_ei_rbcst")
    private Integer eiRbCst;
    @Column(name = "mxf_ed_cst")
    private String edCst;
    @Column(name = "mxf_ed_alq")
    private Integer edAlq;
    @Column(name = "mxf_ed_alqst")
    private Integer edAlqSt;
    @Column(name = "mxf_ed_rbc")
    private Integer edRbc;
    @Column(name = "mxf_ed_rbcst")
    private Integer edRbcCst;
    @Column(name = "mxf_es_cst")
    private String esCst;
    @Column(name = "mxf_es_alq")
    private Integer esAlq;
    @Column(name = "mxf_es_alqst")
    private Integer esAlqSt;
    @Column(name = "mxf_es_rbc")
    private Integer esRbc;
    @Column(name = "mxf_es_rbcst")
    private Integer esRbcSt;
    @Column(name = "mxf_nfi_cst")
    private String nfiCst;
    @Column(name = "mxf_nfd_cst")
    private String nfdCst;
    @Column(name = "mxf_nfs_csosn")
    private String nfsCsosn;
    @Column(name = "mxf_nf_alq")
    private Integer nfAlq;
    @Column(name = "mxf_icms_tipo_mva_rotulo")
    private String icmsTipoMvaRotulo;
    @Column(name = "mxf_icms_tipo_mva")
    private String icmsTipoMva;
    @Column(name = "mxf_icms_mva_valor")
    private Integer icmsMvaValor;
    @Column(name = "mxf_mva_data_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mvaDataIni;
    @Column(name = "mxf_mva_data_fim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mvaDataFim;
    @Column(name = "mxf_credito_outorgado")
    private String creditoOutorgado;    
    @Column(name = "mxf_icms_mva_distribuidor_valor")
    private Integer icmsMvaDistribuidorValor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Long codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public Long getEan() {
        return ean;
    }

    public void setEan(Long ean) {
        this.ean = ean;
    }

    public String getDescritivoProduto() {
        return descritivoProduto;
    }

    public void setDescritivoProduto(String descritivoProduto) {
        this.descritivoProduto = descritivoProduto;
    }

    public String getPisCofinsCstE() {
        return pisCofinsCstE;
    }

    public void setPisCofinsCstE(String pisCofinsCstE) {
        this.pisCofinsCstE = pisCofinsCstE;
    }

    public String getPisCofinsCstS() {
        return pisCofinsCstS;
    }

    public void setPisCofinsCstS(String pisCofinsCstS) {
        this.pisCofinsCstS = pisCofinsCstS;
    }

    public Integer getPisAlqE() {
        return pisAlqE;
    }

    public void setPisAlqE(Integer pisAlqE) {
        this.pisAlqE = pisAlqE;
    }

    public Integer getPisAlqS() {
        return pisAlqS;
    }

    public void setPisAlqS(Integer pisAlqS) {
        this.pisAlqS = pisAlqS;
    }

    public Integer getCofinsAlqE() {
        return cofinsAlqE;
    }

    public void setCofinsAlqE(Integer cofinsAlqE) {
        this.cofinsAlqE = cofinsAlqE;
    }

    public Integer getCofinsAlqS() {
        return cofinsAlqS;
    }

    public void setCofinsAlqS(Integer cofinsAlqS) {
        this.cofinsAlqS = cofinsAlqS;
    }

    public String getSacCst() {
        return sacCst;
    }

    public void setSacCst(String sacCst) {
        this.sacCst = sacCst;
    }

    public Integer getSacAlq() {
        return sacAlq;
    }

    public void setSacAlq(Integer sacAlq) {
        this.sacAlq = sacAlq;
    }

    public Integer getSacAlqSt() {
        return sacAlqSt;
    }

    public void setSacAlqSt(Integer sacAlqSt) {
        this.sacAlqSt = sacAlqSt;
    }

    public Integer getSacRbc() {
        return sacRbc;
    }

    public void setSacRbc(Integer sacRbc) {
        this.sacRbc = sacRbc;
    }

    public Integer getSacRbCst() {
        return sacRbCst;
    }

    public void setSacRbCst(Integer sacRbCst) {
        this.sacRbCst = sacRbCst;
    }

    public String getSasCst() {
        return sasCst;
    }

    public void setSasCst(String sasCst) {
        this.sasCst = sasCst;
    }

    public Integer getSasAlq() {
        return sasAlq;
    }

    public void setSasAlq(Integer sasAlq) {
        this.sasAlq = sasAlq;
    }

    public Integer getSasAlqSt() {
        return sasAlqSt;
    }

    public void setSasAlqSt(Integer sasAlqSt) {
        this.sasAlqSt = sasAlqSt;
    }

    public Integer getSasRbc() {
        return sasRbc;
    }

    public void setSasRbc(Integer sasRbc) {
        this.sasRbc = sasRbc;
    }

    public Integer getSasRbCst() {
        return sasRbCst;
    }

    public void setSasRbCst(Integer sasRbCst) {
        this.sasRbCst = sasRbCst;
    }

    public String getSvcCst() {
        return svcCst;
    }

    public void setSvcCst(String svcCst) {
        this.svcCst = svcCst;
    }

    public Integer getSvcAlq() {
        return svcAlq;
    }

    public void setSvcAlq(Integer svcAlq) {
        this.svcAlq = svcAlq;
    }

    public Integer getSvcAlqSt() {
        return svcAlqSt;
    }

    public void setSvcAlqSt(Integer svcAlqSt) {
        this.svcAlqSt = svcAlqSt;
    }

    public Integer getSvcRbc() {
        return svcRbc;
    }

    public void setSvcRbc(Integer svcRbc) {
        this.svcRbc = svcRbc;
    }

    public Integer getSvcRbCst() {
        return svcRbCst;
    }

    public void setSvcRbCst(Integer svcRbCst) {
        this.svcRbCst = svcRbCst;
    }

    public String getSncCst() {
        return sncCst;
    }

    public void setSncCst(String sncCst) {
        this.sncCst = sncCst;
    }

    public Integer getSncAlq() {
        return sncAlq;
    }

    public void setSncAlq(Integer sncAlq) {
        this.sncAlq = sncAlq;
    }

    public Integer getSncAlqSt() {
        return sncAlqSt;
    }

    public void setSncAlqSt(Integer sncAlqSt) {
        this.sncAlqSt = sncAlqSt;
    }

    public Integer getSncRbc() {
        return sncRbc;
    }

    public void setSncRbc(Integer sncRbc) {
        this.sncRbc = sncRbc;
    }

    public Integer getSncRbCst() {
        return sncRbCst;
    }

    public void setSncRbCst(Integer sncRbCst) {
        this.sncRbCst = sncRbCst;
    }

    public String getEiCst() {
        return eiCst;
    }

    public void setEiCst(String eiCst) {
        this.eiCst = eiCst;
    }

    public Integer getEiAlq() {
        return eiAlq;
    }

    public void setEiAlq(Integer eiAlq) {
        this.eiAlq = eiAlq;
    }

    public Integer getEiAlqSt() {
        return eiAlqSt;
    }

    public void setEiAlqSt(Integer eiAlqSt) {
        this.eiAlqSt = eiAlqSt;
    }

    public Integer getEiRbc() {
        return eiRbc;
    }

    public void setEiRbc(Integer eiRbc) {
        this.eiRbc = eiRbc;
    }

    public Integer getEiRbCst() {
        return eiRbCst;
    }

    public void setEiRbCst(Integer eiRbCst) {
        this.eiRbCst = eiRbCst;
    }

    public String getEdCst() {
        return edCst;
    }

    public void setEdCst(String edCst) {
        this.edCst = edCst;
    }

    public Integer getEdAlq() {
        return edAlq;
    }

    public void setEdAlq(Integer edAlq) {
        this.edAlq = edAlq;
    }

    public Integer getEdAlqSt() {
        return edAlqSt;
    }

    public void setEdAlqSt(Integer edAlqSt) {
        this.edAlqSt = edAlqSt;
    }

    public Integer getEdRbc() {
        return edRbc;
    }

    public void setEdRbc(Integer edRbc) {
        this.edRbc = edRbc;
    }

    public Integer getEdRbcCst() {
        return edRbcCst;
    }

    public void setEdRbcCst(Integer edRbcCst) {
        this.edRbcCst = edRbcCst;
    }

    public String getEsCst() {
        return esCst;
    }

    public void setEsCst(String esCst) {
        this.esCst = esCst;
    }

    public Integer getEsAlq() {
        return esAlq;
    }

    public void setEsAlq(Integer esAlq) {
        this.esAlq = esAlq;
    }

    public Integer getEsAlqSt() {
        return esAlqSt;
    }

    public void setEsAlqSt(Integer esAlqSt) {
        this.esAlqSt = esAlqSt;
    }

    public Integer getEsRbc() {
        return esRbc;
    }

    public void setEsRbc(Integer esRbc) {
        this.esRbc = esRbc;
    }

    public Integer getEsRbcSt() {
        return esRbcSt;
    }

    public void setEsRbcSt(Integer esRbcSt) {
        this.esRbcSt = esRbcSt;
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

    public Integer getNfAlq() {
        return nfAlq;
    }

    public void setNfAlq(Integer nfAlq) {
        this.nfAlq = nfAlq;
    }

    public String getIcmsTipoMvaRotulo() {
        return icmsTipoMvaRotulo;
    }

    public void setIcmsTipoMvaRotulo(String icmsTipoMvaRotulo) {
        this.icmsTipoMvaRotulo = icmsTipoMvaRotulo;
    }

    public String getIcmsTipoMva() {
        return icmsTipoMva;
    }

    public void setIcmsTipoMva(String icmsTipoMva) {
        this.icmsTipoMva = icmsTipoMva;
    }

    public Integer getIcmsMvaValor() {
        return icmsMvaValor;
    }

    public void setIcmsMvaValor(Integer icmsMvaValor) {
        this.icmsMvaValor = icmsMvaValor;
    }

    public Date getMvaDataIni() {
        return mvaDataIni;
    }

    public void setMvaDataIni(Date mvaDataIni) {
        this.mvaDataIni = mvaDataIni;
    }

    public Date getMvaDataFim() {
        return mvaDataFim;
    }

    public void setMvaDataFim(Date mvaDataFim) {
        this.mvaDataFim = mvaDataFim;
    }

    public String getCreditoOutorgado() {
        return creditoOutorgado;
    }

    public void setCreditoOutorgado(String creditoOutorgado) {
        this.creditoOutorgado = creditoOutorgado;
    }

    public Integer getIcmsMvaDistribuidorValor() {
        return icmsMvaDistribuidorValor;
    }

    public void setIcmsMvaDistribuidorValor(Integer icmsMvaDistribuidorValor) {
        this.icmsMvaDistribuidorValor = icmsMvaDistribuidorValor;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.codigoProduto);
        hash = 97 * hash + Objects.hashCode(this.ean);
        hash = 97 * hash + Objects.hashCode(this.descritivoProduto);
        hash = 97 * hash + Objects.hashCode(this.pisCofinsCstE);
        hash = 97 * hash + Objects.hashCode(this.pisCofinsCstS);
        hash = 97 * hash + Objects.hashCode(this.pisAlqE);
        hash = 97 * hash + Objects.hashCode(this.pisAlqS);
        hash = 97 * hash + Objects.hashCode(this.cofinsAlqE);
        hash = 97 * hash + Objects.hashCode(this.cofinsAlqS);
        hash = 97 * hash + Objects.hashCode(this.sacCst);
        hash = 97 * hash + Objects.hashCode(this.sacAlq);
        hash = 97 * hash + Objects.hashCode(this.sacAlqSt);
        hash = 97 * hash + Objects.hashCode(this.sacRbc);
        hash = 97 * hash + Objects.hashCode(this.sacRbCst);
        hash = 97 * hash + Objects.hashCode(this.sasCst);
        hash = 97 * hash + Objects.hashCode(this.sasAlq);
        hash = 97 * hash + Objects.hashCode(this.sasAlqSt);
        hash = 97 * hash + Objects.hashCode(this.sasRbc);
        hash = 97 * hash + Objects.hashCode(this.sasRbCst);
        hash = 97 * hash + Objects.hashCode(this.svcCst);
        hash = 97 * hash + Objects.hashCode(this.svcAlq);
        hash = 97 * hash + Objects.hashCode(this.svcAlqSt);
        hash = 97 * hash + Objects.hashCode(this.svcRbc);
        hash = 97 * hash + Objects.hashCode(this.svcRbCst);
        hash = 97 * hash + Objects.hashCode(this.sncCst);
        hash = 97 * hash + Objects.hashCode(this.sncAlq);
        hash = 97 * hash + Objects.hashCode(this.sncAlqSt);
        hash = 97 * hash + Objects.hashCode(this.sncRbc);
        hash = 97 * hash + Objects.hashCode(this.sncRbCst);
        hash = 97 * hash + Objects.hashCode(this.eiCst);
        hash = 97 * hash + Objects.hashCode(this.eiAlq);
        hash = 97 * hash + Objects.hashCode(this.eiAlqSt);
        hash = 97 * hash + Objects.hashCode(this.eiRbc);
        hash = 97 * hash + Objects.hashCode(this.eiRbCst);
        hash = 97 * hash + Objects.hashCode(this.edCst);
        hash = 97 * hash + Objects.hashCode(this.edAlq);
        hash = 97 * hash + Objects.hashCode(this.edAlqSt);
        hash = 97 * hash + Objects.hashCode(this.edRbc);
        hash = 97 * hash + Objects.hashCode(this.edRbcCst);
        hash = 97 * hash + Objects.hashCode(this.esCst);
        hash = 97 * hash + Objects.hashCode(this.esAlq);
        hash = 97 * hash + Objects.hashCode(this.esAlqSt);
        hash = 97 * hash + Objects.hashCode(this.esRbc);
        hash = 97 * hash + Objects.hashCode(this.esRbcSt);
        hash = 97 * hash + Objects.hashCode(this.nfiCst);
        hash = 97 * hash + Objects.hashCode(this.nfdCst);
        hash = 97 * hash + Objects.hashCode(this.nfsCsosn);
        hash = 97 * hash + Objects.hashCode(this.nfAlq);
        hash = 97 * hash + Objects.hashCode(this.icmsTipoMvaRotulo);
        hash = 97 * hash + Objects.hashCode(this.icmsTipoMva);
        hash = 97 * hash + Objects.hashCode(this.icmsMvaValor);
        hash = 97 * hash + Objects.hashCode(this.mvaDataIni);
        hash = 97 * hash + Objects.hashCode(this.mvaDataFim);
        hash = 97 * hash + Objects.hashCode(this.creditoOutorgado);
        hash = 97 * hash + Objects.hashCode(this.icmsMvaDistribuidorValor);
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
        final CmfConstroyerProduto other = (CmfConstroyerProduto) obj;
        if (!Objects.equals(this.descritivoProduto, other.descritivoProduto)) {
            return false;
        }
        if (!Objects.equals(this.pisCofinsCstE, other.pisCofinsCstE)) {
            return false;
        }
        if (!Objects.equals(this.pisCofinsCstS, other.pisCofinsCstS)) {
            return false;
        }
        if (!Objects.equals(this.sacCst, other.sacCst)) {
            return false;
        }
        if (!Objects.equals(this.sasCst, other.sasCst)) {
            return false;
        }
        if (!Objects.equals(this.svcCst, other.svcCst)) {
            return false;
        }
        if (!Objects.equals(this.sncCst, other.sncCst)) {
            return false;
        }
        if (!Objects.equals(this.eiCst, other.eiCst)) {
            return false;
        }
        if (!Objects.equals(this.edCst, other.edCst)) {
            return false;
        }
        if (!Objects.equals(this.esCst, other.esCst)) {
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
        if (!Objects.equals(this.icmsTipoMvaRotulo, other.icmsTipoMvaRotulo)) {
            return false;
        }
        if (!Objects.equals(this.icmsTipoMva, other.icmsTipoMva)) {
            return false;
        }
        if (!Objects.equals(this.creditoOutorgado, other.creditoOutorgado)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.codigoProduto, other.codigoProduto)) {
            return false;
        }
        if (!Objects.equals(this.ean, other.ean)) {
            return false;
        }
        if (!Objects.equals(this.pisAlqE, other.pisAlqE)) {
            return false;
        }
        if (!Objects.equals(this.pisAlqS, other.pisAlqS)) {
            return false;
        }
        if (!Objects.equals(this.cofinsAlqE, other.cofinsAlqE)) {
            return false;
        }
        if (!Objects.equals(this.cofinsAlqS, other.cofinsAlqS)) {
            return false;
        }
        if (!Objects.equals(this.sacAlq, other.sacAlq)) {
            return false;
        }
        if (!Objects.equals(this.sacAlqSt, other.sacAlqSt)) {
            return false;
        }
        if (!Objects.equals(this.sacRbc, other.sacRbc)) {
            return false;
        }
        if (!Objects.equals(this.sacRbCst, other.sacRbCst)) {
            return false;
        }
        if (!Objects.equals(this.sasAlq, other.sasAlq)) {
            return false;
        }
        if (!Objects.equals(this.sasAlqSt, other.sasAlqSt)) {
            return false;
        }
        if (!Objects.equals(this.sasRbc, other.sasRbc)) {
            return false;
        }
        if (!Objects.equals(this.sasRbCst, other.sasRbCst)) {
            return false;
        }
        if (!Objects.equals(this.svcAlq, other.svcAlq)) {
            return false;
        }
        if (!Objects.equals(this.svcAlqSt, other.svcAlqSt)) {
            return false;
        }
        if (!Objects.equals(this.svcRbc, other.svcRbc)) {
            return false;
        }
        if (!Objects.equals(this.svcRbCst, other.svcRbCst)) {
            return false;
        }
        if (!Objects.equals(this.sncAlq, other.sncAlq)) {
            return false;
        }
        if (!Objects.equals(this.sncAlqSt, other.sncAlqSt)) {
            return false;
        }
        if (!Objects.equals(this.sncRbc, other.sncRbc)) {
            return false;
        }
        if (!Objects.equals(this.sncRbCst, other.sncRbCst)) {
            return false;
        }
        if (!Objects.equals(this.eiAlq, other.eiAlq)) {
            return false;
        }
        if (!Objects.equals(this.eiAlqSt, other.eiAlqSt)) {
            return false;
        }
        if (!Objects.equals(this.eiRbc, other.eiRbc)) {
            return false;
        }
        if (!Objects.equals(this.eiRbCst, other.eiRbCst)) {
            return false;
        }
        if (!Objects.equals(this.edAlq, other.edAlq)) {
            return false;
        }
        if (!Objects.equals(this.edAlqSt, other.edAlqSt)) {
            return false;
        }
        if (!Objects.equals(this.edRbc, other.edRbc)) {
            return false;
        }
        if (!Objects.equals(this.edRbcCst, other.edRbcCst)) {
            return false;
        }
        if (!Objects.equals(this.esAlq, other.esAlq)) {
            return false;
        }
        if (!Objects.equals(this.esAlqSt, other.esAlqSt)) {
            return false;
        }
        if (!Objects.equals(this.esRbc, other.esRbc)) {
            return false;
        }
        if (!Objects.equals(this.esRbcSt, other.esRbcSt)) {
            return false;
        }
        if (!Objects.equals(this.nfAlq, other.nfAlq)) {
            return false;
        }
        if (!Objects.equals(this.icmsMvaValor, other.icmsMvaValor)) {
            return false;
        }
        if (!Objects.equals(this.mvaDataIni, other.mvaDataIni)) {
            return false;
        }
        if (!Objects.equals(this.mvaDataFim, other.mvaDataFim)) {
            return false;
        }
        if (!Objects.equals(this.icmsMvaDistribuidorValor, other.icmsMvaDistribuidorValor)) {
            return false;
        }
        return true;
    }
    
    public static class CmfConstroyerProdutoPK implements Serializable {        
        protected Long id;    
        protected Long codigoProduto;
        public CmfConstroyerProdutoPK() {            
        }  

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 53 * hash + Objects.hashCode(this.id);
            hash = 53 * hash + Objects.hashCode(this.codigoProduto);
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
            final CmfConstroyerProdutoPK other = (CmfConstroyerProdutoPK) obj;
            if (!Objects.equals(this.id, other.id)) {
                return false;
            }
            if (!Objects.equals(this.codigoProduto, other.codigoProduto)) {
                return false;
            }
            return true;
        }
    }
}
