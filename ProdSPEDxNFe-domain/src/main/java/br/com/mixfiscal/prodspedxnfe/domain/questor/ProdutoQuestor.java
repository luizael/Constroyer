package br.com.mixfiscal.prodspedxnfe.domain.questor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PRODUTO")
public class ProdutoQuestor implements Serializable {
    private static final long serialVersionUID = 1711972724209475067L;   
    
    @Id
    @Column(name = "CODIGOEMPRESA")
    private Integer codigoEmpresa;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ProdutoSeq")
    @GenericGenerator(name = "ProdutoSeq", strategy = "br.com.mixfiscal.prodspedxnfe.dao.questor.seq.ProdutoCodigoProdutoSeq")
    @Column(name = "CODIGOPRODUTO")
    private Integer codigoProduto;
    @Column(name = "REFERENPRODUTO")
    private String referenProduto;    
    @Column(name = "CODIGOGRUPOPRODUTO")
    private Short codigoGrupoProduto;
    @Column(name = "DESCRPRODUTO")
    private String descricao;
    @Column(name = "CODIGONCM")
    private String codigoNcm;
    @Column(name = "UNIDADEMEDIDA")
    private String unidadeMedida;
    @Column(name = "INDICADORTIPO")
    private Short indicadorTipo;
    @Column(name = "DATAATUALIZACAO")
    private Date dataAtualizacao;
    @Column(name = "CODIGOUSUARIO")
    private Short codigoUsuario;
    @Column(name = "DNF")
    private Character dnf;
    @Column(name = "DNFDESCRUNIDMEDIDA")
    private String dnfDescrUnidMedida;
    @Column(name = "DNFCODIGOESPEC")
    private Short dnfCodigoEspec;
    @Column(name = "DNFFATCONVUNIDMEDIDA")
    private BigDecimal dnfFatConvunidMedida;
    @Column(name = "DNFCAPACIDVOLUMETRICA")
    private Integer dnfCapacidVolumetrica;
    @Column(name = "DNFALIQIPI")
    private BigDecimal dnfAliqIPI;
    @Column(name = "DACON")
    private Character dacon;
    @Column(name = "DACONTIPORECEITA")
    private Short daconTipoReceita;
    @Column(name = "DACONCODIGOESPEC")
    private String daconCodigoEspec;
    @Column(name = "DACONALIQPISENT")
    private BigDecimal daconAliqPisEnt; 
    @Column(name = "DACONALIQPISSAI")
    private BigDecimal daconAliqPisSai;
    @Column(name = "DACONALIQCOFINSENT")
    private BigDecimal daconAliqCofinsEnt;
    @Column(name = "DACONALIQCOFINSSAI")
    private BigDecimal daconAliqCofinsSai;
    @Column(name = "CSTENTRADA")
    private Integer cstEntrada;
    @Column(name = "CSTSAIDA")
    private Integer cstSaida;
    @Column(name = "TIPOCREDITO")
    private String tipoCredito;
    @Column(name = "CODIGORECEITA")
    private Integer codigoReceita;
    @Column(name = "TIPODEBITO")
    private String tipoDebito;
    @Column(name = "ALIQIPI")
    private BigDecimal aliqIpi;
    @Column(name = "ALIQICMS")
    private BigDecimal aliqIcms;
    @Column(name = "PERCREDUCAOICMS")
    private BigDecimal percReducaoIcms;
    @Column(name = "SUBSTTRIBUTARIA")
    private BigDecimal substTributaria;
    @Column(name = "CODBARRA")
    private String codBarra;
    @Column(name = "EXIPI")
    private String exIpi;
    @Column(name = "CODGEN")
    private Short codGen;
    @Column(name = "CODLST")
    private Integer codLst;
    @Column(name = "CODENQIPI")
    private String codEnqIpi;
    @Column(name = "CODSELOIPI")
    private String codSeloIpi;            
    @Column(name = "CLASSENQIPI")
    private String classEnqIpi;
    @Column(name = "TIPOMEDICAMENTO")
    private Character tipoMedicamento;
    @Column(name = "BASESUBSTTRIBUTARIA")
    private String baseSubstTributaria;
    @Column(name = "VALORSUBSTTRIBUTARIA")
    private String valorSubstTributaria;
    @Column(name = "CODCOMB")
    private Integer codComb;
    @Column(name = "CDANTITEM")
    private String cdAntItem;
    @Column(name = "CONTACTB")
    private Integer contaCtb;
    @Column(name = "IDTABINCID")
    private String idTabIncid;
    @Column(name = "IDTABINCIDGRUPO")
    private String idTabIncidGrupo;
    @Column(name = "IDTABINCIDMARCA")
    private Integer idTabIncidMarca;
    @Column(name = "INCENTIVOFIS")
    private Character incentivoFis;
    @Column(name = "CODIGOPORTARIA")
    private Short codigoPortaria;
    @Column(name = "CODIGOCENTROCUSTO")
    private Integer codigoCentroCusto;

    public Integer getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(Integer codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public Integer getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Integer codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getReferenProduto() {
        return referenProduto;
    }

    public void setReferenProduto(String referenProduto) {
        this.referenProduto = referenProduto;
    }

    public Short getCodigoGrupoProduto() {
        return codigoGrupoProduto;
    }

    public void setCodigoGrupoProduto(Short codigoGrupoProduto) {
        this.codigoGrupoProduto = codigoGrupoProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigoNcm() {
        return codigoNcm;
    }

    public void setCodigoNcm(String codigoNcm) {
        this.codigoNcm = codigoNcm;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Short getIndicadorTipo() {
        return indicadorTipo;
    }

    public void setIndicadorTipo(Short indicadorTipo) {
        this.indicadorTipo = indicadorTipo;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Short getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Short codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public Character getDnf() {
        return dnf;
    }

    public void setDnf(Character dnf) {
        this.dnf = dnf;
    }

    public String getDnfDescrUnidMedida() {
        return dnfDescrUnidMedida;
    }

    public void setDnfDescrUnidMedida(String dnfDescrUnidMedida) {
        this.dnfDescrUnidMedida = dnfDescrUnidMedida;
    }

    public Short getDnfCodigoEspec() {
        return dnfCodigoEspec;
    }

    public void setDnfCodigoEspec(Short dnfCodigoEspec) {
        this.dnfCodigoEspec = dnfCodigoEspec;
    }

    public BigDecimal getDnfFatConvunidMedida() {
        return dnfFatConvunidMedida;
    }

    public void setDnfFatConvunidMedida(BigDecimal dnfFatConvunidMedida) {
        this.dnfFatConvunidMedida = dnfFatConvunidMedida;
    }

    public Integer getDnfCapacidVolumetrica() {
        return dnfCapacidVolumetrica;
    }

    public void setDnfCapacidVolumetrica(Integer dnfCapacidVolumetrica) {
        this.dnfCapacidVolumetrica = dnfCapacidVolumetrica;
    }

    public BigDecimal getDnfAliqIPI() {
        return dnfAliqIPI;
    }

    public void setDnfAliqIPI(BigDecimal dnfAliqIPI) {
        this.dnfAliqIPI = dnfAliqIPI;
    }

    public Character getDacon() {
        return dacon;
    }

    public void setDacon(Character dacon) {
        this.dacon = dacon;
    }

    public Short getDaconTipoReceita() {
        return daconTipoReceita;
    }

    public void setDaconTipoReceita(Short daconTipoReceita) {
        this.daconTipoReceita = daconTipoReceita;
    }

    public String getDaconCodigoEspec() {
        return daconCodigoEspec;
    }

    public void setDaconCodigoEspec(String daconCodigoEspec) {
        this.daconCodigoEspec = daconCodigoEspec;
    }

    public BigDecimal getDaconAliqPisEnt() {
        return daconAliqPisEnt;
    }

    public void setDaconAliqPisEnt(BigDecimal daconAliqPisEnt) {
        this.daconAliqPisEnt = daconAliqPisEnt;
    }

    public BigDecimal getDaconAliqPisSai() {
        return daconAliqPisSai;
    }

    public void setDaconAliqPisSai(BigDecimal daconAliqPisSai) {
        this.daconAliqPisSai = daconAliqPisSai;
    }

    public BigDecimal getDaconAliqCofinsEnt() {
        return daconAliqCofinsEnt;
    }

    public void setDaconAliqCofinsEnt(BigDecimal daconAliqCofinsEnt) {
        this.daconAliqCofinsEnt = daconAliqCofinsEnt;
    }

    public BigDecimal getDaconAliqCofinsSai() {
        return daconAliqCofinsSai;
    }

    public void setDaconAliqCofinsSai(BigDecimal daconAliqCofinsSai) {
        this.daconAliqCofinsSai = daconAliqCofinsSai;
    }

    public Integer getCstEntrada() {
        return cstEntrada;
    }

    public void setCstEntrada(Integer cstEntrada) {
        this.cstEntrada = cstEntrada;
    }

    public Integer getCstSaida() {
        return cstSaida;
    }

    public void setCstSaida(Integer cstSaida) {
        this.cstSaida = cstSaida;
    }

    public String getTipoCredito() {
        return tipoCredito;
    }

    public void setTipoCredito(String tipoCredito) {
        this.tipoCredito = tipoCredito;
    }

    public Integer getCodigoReceita() {
        return codigoReceita;
    }

    public void setCodigoReceita(Integer codigoReceita) {
        this.codigoReceita = codigoReceita;
    }

    public String getTipoDebito() {
        return tipoDebito;
    }

    public void setTipoDebito(String tipoDebito) {
        this.tipoDebito = tipoDebito;
    }

    public BigDecimal getAliqIpi() {
        return aliqIpi;
    }

    public void setAliqIpi(BigDecimal aliqIpi) {
        this.aliqIpi = aliqIpi;
    }

    public BigDecimal getAliqIcms() {
        return aliqIcms;
    }

    public void setAliqIcms(BigDecimal aliqIcms) {
        this.aliqIcms = aliqIcms;
    }

    public BigDecimal getPercReducaoIcms() {
        return percReducaoIcms;
    }

    public void setPercReducaoIcms(BigDecimal percReducaoIcms) {
        this.percReducaoIcms = percReducaoIcms;
    }

    public BigDecimal getSubstTributaria() {
        return substTributaria;
    }

    public void setSubstTributaria(BigDecimal substTributaria) {
        this.substTributaria = substTributaria;
    }

    public String getCodBarra() {
        return codBarra;
    }

    public void setCodBarra(String codBarra) {
        this.codBarra = codBarra;
    }

    public String getExIpi() {
        return exIpi;
    }

    public void setExIpi(String exIpi) {
        this.exIpi = exIpi;
    }

    public Short getCodGen() {
        return codGen;
    }

    public void setCodGen(Short codGen) {
        this.codGen = codGen;
    }

    public Integer getCodLst() {
        return codLst;
    }

    public void setCodLst(Integer codLst) {
        this.codLst = codLst;
    }

    public String getCodEnqIpi() {
        return codEnqIpi;
    }

    public void setCodEnqIpi(String codEnqIpi) {
        this.codEnqIpi = codEnqIpi;
    }

    public String getCodSeloIpi() {
        return codSeloIpi;
    }

    public void setCodSeloIpi(String codSeloIpi) {
        this.codSeloIpi = codSeloIpi;
    }

    public String getClassEnqIpi() {
        return classEnqIpi;
    }

    public void setClassEnqIpi(String classEnqIpi) {
        this.classEnqIpi = classEnqIpi;
    }

    public Character getTipoMedicamento() {
        return tipoMedicamento;
    }

    public void setTipoMedicamento(Character tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
    }

    public String getBaseSubstTributaria() {
        return baseSubstTributaria;
    }

    public void setBaseSubstTributaria(String baseSubstTributaria) {
        this.baseSubstTributaria = baseSubstTributaria;
    }

    public String getValorSubstTributaria() {
        return valorSubstTributaria;
    }

    public void setValorSubstTributaria(String valorSubstTributaria) {
        this.valorSubstTributaria = valorSubstTributaria;
    }

    public Integer getCodComb() {
        return codComb;
    }

    public void setCodComb(Integer codComb) {
        this.codComb = codComb;
    }

    public String getCdAntItem() {
        return cdAntItem;
    }

    public void setCdAntItem(String cdAntItem) {
        this.cdAntItem = cdAntItem;
    }

    public Integer getContaCtb() {
        return contaCtb;
    }

    public void setContaCtb(Integer contaCtb) {
        this.contaCtb = contaCtb;
    }

    public String getIdTabIncid() {
        return idTabIncid;
    }

    public void setIdTabIncid(String idTabIncid) {
        this.idTabIncid = idTabIncid;
    }

    public String getIdTabIncidGrupo() {
        return idTabIncidGrupo;
    }

    public void setIdTabIncidGrupo(String idTabIncidGrupo) {
        this.idTabIncidGrupo = idTabIncidGrupo;
    }

    public Integer getIdTabIncidMarca() {
        return idTabIncidMarca;
    }

    public void setIdTabIncidMarca(Integer idTabIncidMarca) {
        this.idTabIncidMarca = idTabIncidMarca;
    }

    public Character getIncentivoFis() {
        return incentivoFis;
    }

    public void setIncentivoFis(Character incentivoFis) {
        this.incentivoFis = incentivoFis;
    }

    public Short getCodigoPortaria() {
        return codigoPortaria;
    }

    public void setCodigoPortaria(Short codigoPortaria) {
        this.codigoPortaria = codigoPortaria;
    }

    public Integer getCodigoCentroCusto() {
        return codigoCentroCusto;
    }

    public void setCodigoCentroCusto(Integer codigoCentroCusto) {
        this.codigoCentroCusto = codigoCentroCusto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.codigoEmpresa);
        hash = 37 * hash + Objects.hashCode(this.codigoProduto);
        hash = 37 * hash + Objects.hashCode(this.referenProduto);
        hash = 37 * hash + Objects.hashCode(this.codigoGrupoProduto);
        hash = 37 * hash + Objects.hashCode(this.descricao);
        hash = 37 * hash + Objects.hashCode(this.codigoNcm);
        hash = 37 * hash + Objects.hashCode(this.unidadeMedida);
        hash = 37 * hash + Objects.hashCode(this.indicadorTipo);
        hash = 37 * hash + Objects.hashCode(this.dataAtualizacao);
        hash = 37 * hash + Objects.hashCode(this.codigoUsuario);
        hash = 37 * hash + Objects.hashCode(this.dnf);
        hash = 37 * hash + Objects.hashCode(this.dnfDescrUnidMedida);
        hash = 37 * hash + Objects.hashCode(this.dnfCodigoEspec);
        hash = 37 * hash + Objects.hashCode(this.dnfFatConvunidMedida);
        hash = 37 * hash + Objects.hashCode(this.dnfCapacidVolumetrica);
        hash = 37 * hash + Objects.hashCode(this.dnfAliqIPI);
        hash = 37 * hash + Objects.hashCode(this.dacon);
        hash = 37 * hash + Objects.hashCode(this.daconTipoReceita);
        hash = 37 * hash + Objects.hashCode(this.daconCodigoEspec);
        hash = 37 * hash + Objects.hashCode(this.daconAliqPisEnt);
        hash = 37 * hash + Objects.hashCode(this.daconAliqPisSai);
        hash = 37 * hash + Objects.hashCode(this.daconAliqCofinsEnt);
        hash = 37 * hash + Objects.hashCode(this.daconAliqCofinsSai);
        hash = 37 * hash + Objects.hashCode(this.cstEntrada);
        hash = 37 * hash + Objects.hashCode(this.cstSaida);
        hash = 37 * hash + Objects.hashCode(this.tipoCredito);
        hash = 37 * hash + Objects.hashCode(this.codigoReceita);
        hash = 37 * hash + Objects.hashCode(this.tipoDebito);
        hash = 37 * hash + Objects.hashCode(this.aliqIpi);
        hash = 37 * hash + Objects.hashCode(this.aliqIcms);
        hash = 37 * hash + Objects.hashCode(this.percReducaoIcms);
        hash = 37 * hash + Objects.hashCode(this.substTributaria);
        hash = 37 * hash + Objects.hashCode(this.codBarra);
        hash = 37 * hash + Objects.hashCode(this.exIpi);
        hash = 37 * hash + Objects.hashCode(this.codGen);
        hash = 37 * hash + Objects.hashCode(this.codLst);
        hash = 37 * hash + Objects.hashCode(this.codEnqIpi);
        hash = 37 * hash + Objects.hashCode(this.codSeloIpi);
        hash = 37 * hash + Objects.hashCode(this.classEnqIpi);
        hash = 37 * hash + Objects.hashCode(this.tipoMedicamento);
        hash = 37 * hash + Objects.hashCode(this.baseSubstTributaria);
        hash = 37 * hash + Objects.hashCode(this.valorSubstTributaria);
        hash = 37 * hash + Objects.hashCode(this.codComb);
        hash = 37 * hash + Objects.hashCode(this.cdAntItem);
        hash = 37 * hash + Objects.hashCode(this.contaCtb);
        hash = 37 * hash + Objects.hashCode(this.idTabIncid);
        hash = 37 * hash + Objects.hashCode(this.idTabIncidGrupo);
        hash = 37 * hash + Objects.hashCode(this.idTabIncidMarca);
        hash = 37 * hash + Objects.hashCode(this.incentivoFis);
        hash = 37 * hash + Objects.hashCode(this.codigoPortaria);
        hash = 37 * hash + Objects.hashCode(this.codigoCentroCusto);
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
        final ProdutoQuestor other = (ProdutoQuestor) obj;
        if (!Objects.equals(this.referenProduto, other.referenProduto)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.codigoNcm, other.codigoNcm)) {
            return false;
        }
        if (!Objects.equals(this.unidadeMedida, other.unidadeMedida)) {
            return false;
        }
        if (!Objects.equals(this.dnfDescrUnidMedida, other.dnfDescrUnidMedida)) {
            return false;
        }
        if (!Objects.equals(this.daconCodigoEspec, other.daconCodigoEspec)) {
            return false;
        }
        if (!Objects.equals(this.tipoCredito, other.tipoCredito)) {
            return false;
        }
        if (!Objects.equals(this.tipoDebito, other.tipoDebito)) {
            return false;
        }
        if (!Objects.equals(this.codBarra, other.codBarra)) {
            return false;
        }
        if (!Objects.equals(this.exIpi, other.exIpi)) {
            return false;
        }
        if (!Objects.equals(this.codEnqIpi, other.codEnqIpi)) {
            return false;
        }
        if (!Objects.equals(this.codSeloIpi, other.codSeloIpi)) {
            return false;
        }
        if (!Objects.equals(this.classEnqIpi, other.classEnqIpi)) {
            return false;
        }
        if (!Objects.equals(this.baseSubstTributaria, other.baseSubstTributaria)) {
            return false;
        }
        if (!Objects.equals(this.valorSubstTributaria, other.valorSubstTributaria)) {
            return false;
        }
        if (!Objects.equals(this.cdAntItem, other.cdAntItem)) {
            return false;
        }
        if (!Objects.equals(this.idTabIncid, other.idTabIncid)) {
            return false;
        }
        if (!Objects.equals(this.idTabIncidGrupo, other.idTabIncidGrupo)) {
            return false;
        }
        if (!Objects.equals(this.codigoEmpresa, other.codigoEmpresa)) {
            return false;
        }
        if (!Objects.equals(this.codigoProduto, other.codigoProduto)) {
            return false;
        }
        if (!Objects.equals(this.codigoGrupoProduto, other.codigoGrupoProduto)) {
            return false;
        }
        if (!Objects.equals(this.indicadorTipo, other.indicadorTipo)) {
            return false;
        }
        if (!Objects.equals(this.dataAtualizacao, other.dataAtualizacao)) {
            return false;
        }
        if (!Objects.equals(this.codigoUsuario, other.codigoUsuario)) {
            return false;
        }
        if (!Objects.equals(this.dnf, other.dnf)) {
            return false;
        }
        if (!Objects.equals(this.dnfCodigoEspec, other.dnfCodigoEspec)) {
            return false;
        }
        if (!Objects.equals(this.dnfFatConvunidMedida, other.dnfFatConvunidMedida)) {
            return false;
        }
        if (!Objects.equals(this.dnfCapacidVolumetrica, other.dnfCapacidVolumetrica)) {
            return false;
        }
        if (!Objects.equals(this.dnfAliqIPI, other.dnfAliqIPI)) {
            return false;
        }
        if (!Objects.equals(this.dacon, other.dacon)) {
            return false;
        }
        if (!Objects.equals(this.daconTipoReceita, other.daconTipoReceita)) {
            return false;
        }
        if (!Objects.equals(this.daconAliqPisEnt, other.daconAliqPisEnt)) {
            return false;
        }
        if (!Objects.equals(this.daconAliqPisSai, other.daconAliqPisSai)) {
            return false;
        }
        if (!Objects.equals(this.daconAliqCofinsEnt, other.daconAliqCofinsEnt)) {
            return false;
        }
        if (!Objects.equals(this.daconAliqCofinsSai, other.daconAliqCofinsSai)) {
            return false;
        }
        if (!Objects.equals(this.cstEntrada, other.cstEntrada)) {
            return false;
        }
        if (!Objects.equals(this.cstSaida, other.cstSaida)) {
            return false;
        }
        if (!Objects.equals(this.codigoReceita, other.codigoReceita)) {
            return false;
        }
        if (!Objects.equals(this.aliqIpi, other.aliqIpi)) {
            return false;
        }
        if (!Objects.equals(this.aliqIcms, other.aliqIcms)) {
            return false;
        }
        if (!Objects.equals(this.percReducaoIcms, other.percReducaoIcms)) {
            return false;
        }
        if (!Objects.equals(this.substTributaria, other.substTributaria)) {
            return false;
        }
        if (!Objects.equals(this.codGen, other.codGen)) {
            return false;
        }
        if (!Objects.equals(this.codLst, other.codLst)) {
            return false;
        }
        if (!Objects.equals(this.tipoMedicamento, other.tipoMedicamento)) {
            return false;
        }
        if (!Objects.equals(this.codComb, other.codComb)) {
            return false;
        }
        if (!Objects.equals(this.contaCtb, other.contaCtb)) {
            return false;
        }
        if (!Objects.equals(this.idTabIncidMarca, other.idTabIncidMarca)) {
            return false;
        }
        if (!Objects.equals(this.incentivoFis, other.incentivoFis)) {
            return false;
        }
        if (!Objects.equals(this.codigoPortaria, other.codigoPortaria)) {
            return false;
        }
        if (!Objects.equals(this.codigoCentroCusto, other.codigoCentroCusto)) {
            return false;
        }
        return true;
    }

    

    
}
