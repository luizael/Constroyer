package br.com.mixfiscal.prodspedxnfe.domain.questor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "CFOP")
@IdClass(value = CFOPPK.class)
public class CFOP implements Serializable {
    private static final long serialVersionUID = 6959037985072832L;    
    
    @Id
    @Column(name = "CODIGOEMPRESA")
    private Integer codigoEmpresa;    
    @Id
    @Column(name = "CODIGOESTAB")
    private Integer codigoEstab;
    @Id
    @Column(name = "CODIGOCFOP")
    private Integer codigoCFOP;
    @Column(name = "DESCRCFOP", length = 50)
    private String descricao;
    @Column(name = "APURAICMS")
    private Character apuraICMS;
    @Column(name = "CODIGOTABCTBFISICMS")
    private Integer codigoTabCtbFisICMS;
    @Column(name = "APURAISS")
    private Character apuraISS;
    @Column(name = "CODIGOTABCTBFISISS")
    private Integer codigoTabCtbFisISS;
    @Column(name = "APURAIPI")
    private Character apuraIPI;
    @Column(name = "CODIGOTABCTBFISIPI")
    private Integer codigoTabCtbFisIPI;
    @Column(name = "APURAFUNRURAL")
    private Character apuraFunRural;
    @Column(name = "CODIGOTABCTBFISFUNRURAL")
    private Integer codigoTabCtbFisFuneral;
    @Column(name = "APURASUBTRIBUT")
    private Character apuraSubTribut;
    @Column(name = "CODIGOTABCTBFISSUBTRIBUT")
    private Integer codigoTabCtbFisSubTribut;
    @Column(name = "APURAPISCOFINSOUTROS")
    private Character apuraPisCofinsOutros;
    @Column(name = "CODIGOTABCTBFISPIS")
    private Integer codigoTabCtbFisPIS;
    @Column(name = "CODIGOTABCTBFISCOFINS")
    private Integer codigoTabCtbFisCofins;
    @Column(name = "APURAIRRFRET")
    private Character apuraIrrFret;
    @Column(name = "ALIQIRRFRET")
    private BigDecimal aliqIrrFret;
    @Column(name = "CODIGOTABCTBFISIRRFRET")
    private Integer codigoTabCtbFisIrrFret;
    @Column(name = "APURAIRPJRET")
    private Character apuraIrPjRet;
    @Column(name = "ALIQIRPJRET")
    private BigDecimal aliqIrPjRet;
    @Column(name = "CODIGOTABCTBFISIRPJRET")
    private Integer codigoTabCtbFisIrPjRet;
    @Column(name = "APURAINSSRET")
    private Character apuraInssRet;
    @Column(name = "ALIQINSSRET")
    private BigDecimal aliqInnRet;
    @Column(name = "CODIGOTABCTBFISINSSRET")
    private Integer codigoTabCtbFisInssRet;
    @Column(name = "APURAISSQNRET")
    private Character apuraIssqnRet;
    @Column(name = "ALIQISSQNRET")
    private BigDecimal aliqIssqnRet;
    @Column(name = "CODIGOTABCTBFISISSQNRET")
    private Integer codigoTabCtbFisIssqnRet;
    @Column(name = "APURAPISCOFINSCSLLRET")
    private Character apuraPisCofinsCsllRet;
    @Column(name = "ALIQPISRET")
    private BigDecimal aliqPisRet;
    @Column(name = "CODIGOTABCTBFISPISRET")
    private Integer codigoTabCtbFisPisRet;
    @Column(name = "ALIQCOFINSRET")
    private BigDecimal aliqCofinsRet;
    @Column(name = "CODIGOTABCTBFISCOFINSRET")
    private Integer codigoTabCtbFisCofinsRet;
    @Column(name = "ALIQCSLLRET")
    private BigDecimal aliqCsllRet;
    @Column(name = "CODIGOTABCTBFISCSLLRET")
    private Integer codigoTabCtbFisCsllRet;
    @Column(name = "DETALHARDMED")
    private Character detalharMed;
    @Column(name = "ENTRABASEVALORADICIONAL")
    private Integer entraBaseValorAdicional;
    @Column(name = "EXVALORADICIONAL")
    private Integer exValorAdicional;
    @Column(name = "REGRABASEEXVALORADICIONAL")
    private String regraBaseExValorAdicional;
    @Column(name = "CODIGOTABCTBFISVLRCONTABIL")
    private Integer codigoTabCtbFisVlrContabil;
    @Column(name = "DETALHARITEMNF")
    private Character detalharItemNf;
    @Column(name = "DETALHARDUPLICATA")
    private Character detalharDuplicata;
    @Column(name = "DIFERIDA")
    private Character diferida;
    @Column(name = "CSTPISCOFINS")
    private Integer cstPisCofins;
    @Column(name = "FINALIDADE")
    private Integer finalidade;
    @Column(name = "CONTACTBLIVRO")
    private BigDecimal contaCtbLivro;
    @Column(name = "DESCRCOMPLCFOP")
    private String descrComplCFOP;
    @Column(name = "APURACAOSSIMPLESFEDERAL")
    private Character apuracaoSimplesFederal;

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

    public Integer getCodigoCFOP() {
        return codigoCFOP;
    }

    public void setCodigoCFOP(Integer codigoCFOP) {
        this.codigoCFOP = codigoCFOP;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Character getApuraICMS() {
        return apuraICMS;
    }

    public void setApuraICMS(Character apuraICMS) {
        this.apuraICMS = apuraICMS;
    }

    public Integer getCodigoTabCtbFisICMS() {
        return codigoTabCtbFisICMS;
    }

    public void setCodigoTabCtbFisICMS(Integer codigoTabCtbFisICMS) {
        this.codigoTabCtbFisICMS = codigoTabCtbFisICMS;
    }

    public Character getApuraISS() {
        return apuraISS;
    }

    public void setApuraISS(Character apuraISS) {
        this.apuraISS = apuraISS;
    }

    public Integer getCodigoTabCtbFisISS() {
        return codigoTabCtbFisISS;
    }

    public void setCodigoTabCtbFisISS(Integer codigoTabCtbFisISS) {
        this.codigoTabCtbFisISS = codigoTabCtbFisISS;
    }

    public Character getApuraIPI() {
        return apuraIPI;
    }

    public void setApuraIPI(Character apuraIPI) {
        this.apuraIPI = apuraIPI;
    }

    public Integer getCodigoTabCtbFisIPI() {
        return codigoTabCtbFisIPI;
    }

    public void setCodigoTabCtbFisIPI(Integer codigoTabCtbFisIPI) {
        this.codigoTabCtbFisIPI = codigoTabCtbFisIPI;
    }

    public Character getApuraFunRural() {
        return apuraFunRural;
    }

    public void setApuraFunRural(Character apuraFunRural) {
        this.apuraFunRural = apuraFunRural;
    }

    public Integer getCodigoTabCtbFisFuneral() {
        return codigoTabCtbFisFuneral;
    }

    public void setCodigoTabCtbFisFuneral(Integer codigoTabCtbFisFuneral) {
        this.codigoTabCtbFisFuneral = codigoTabCtbFisFuneral;
    }

    public Character getApuraSubTribut() {
        return apuraSubTribut;
    }

    public void setApuraSubTribut(Character apuraSubTribut) {
        this.apuraSubTribut = apuraSubTribut;
    }

    public Integer getCodigoTabCtbFisSubTribut() {
        return codigoTabCtbFisSubTribut;
    }

    public void setCodigoTabCtbFisSubTribut(Integer codigoTabCtbFisSubTribut) {
        this.codigoTabCtbFisSubTribut = codigoTabCtbFisSubTribut;
    }

    public Character getApuraPisCofinsOutros() {
        return apuraPisCofinsOutros;
    }

    public void setApuraPisCofinsOutros(Character apuraPisCofinsOutros) {
        this.apuraPisCofinsOutros = apuraPisCofinsOutros;
    }

    public Integer getCodigoTabCtbFisPIS() {
        return codigoTabCtbFisPIS;
    }

    public void setCodigoTabCtbFisPIS(Integer codigoTabCtbFisPIS) {
        this.codigoTabCtbFisPIS = codigoTabCtbFisPIS;
    }

    public Integer getCodigoTabCtbFisCofins() {
        return codigoTabCtbFisCofins;
    }

    public void setCodigoTabCtbFisCofins(Integer codigoTabCtbFisCofins) {
        this.codigoTabCtbFisCofins = codigoTabCtbFisCofins;
    }

    public Character getApuraIrrFret() {
        return apuraIrrFret;
    }

    public void setApuraIrrFret(Character apuraIrrFret) {
        this.apuraIrrFret = apuraIrrFret;
    }

    public BigDecimal getAliqIrrFret() {
        return aliqIrrFret;
    }

    public void setAliqIrrFret(BigDecimal aliqIrrFret) {
        this.aliqIrrFret = aliqIrrFret;
    }

    public Integer getCodigoTabCtbFisIrrFret() {
        return codigoTabCtbFisIrrFret;
    }

    public void setCodigoTabCtbFisIrrFret(Integer codigoTabCtbFisIrrFret) {
        this.codigoTabCtbFisIrrFret = codigoTabCtbFisIrrFret;
    }

    public Character getApuraIrPjRet() {
        return apuraIrPjRet;
    }

    public void setApuraIrPjRet(Character apuraIrPjRet) {
        this.apuraIrPjRet = apuraIrPjRet;
    }

    public BigDecimal getAliqIrPjRet() {
        return aliqIrPjRet;
    }

    public void setAliqIrPjRet(BigDecimal aliqIrPjRet) {
        this.aliqIrPjRet = aliqIrPjRet;
    }

    public Integer getCodigoTabCtbFisIrPjRet() {
        return codigoTabCtbFisIrPjRet;
    }

    public void setCodigoTabCtbFisIrPjRet(Integer codigoTabCtbFisIrPjRet) {
        this.codigoTabCtbFisIrPjRet = codigoTabCtbFisIrPjRet;
    }

    public Character getApuraInssRet() {
        return apuraInssRet;
    }

    public void setApuraInssRet(Character apuraInssRet) {
        this.apuraInssRet = apuraInssRet;
    }

    public BigDecimal getAliqInnRet() {
        return aliqInnRet;
    }

    public void setAliqInnRet(BigDecimal aliqInnRet) {
        this.aliqInnRet = aliqInnRet;
    }

    public Integer getCodigoTabCtbFisInssRet() {
        return codigoTabCtbFisInssRet;
    }

    public void setCodigoTabCtbFisInssRet(Integer codigoTabCtbFisInssRet) {
        this.codigoTabCtbFisInssRet = codigoTabCtbFisInssRet;
    }

    public Character getApuraIssqnRet() {
        return apuraIssqnRet;
    }

    public void setApuraIssqnRet(Character apuraIssqnRet) {
        this.apuraIssqnRet = apuraIssqnRet;
    }

    public BigDecimal getAliqIssqnRet() {
        return aliqIssqnRet;
    }

    public void setAliqIssqnRet(BigDecimal aliqIssqnRet) {
        this.aliqIssqnRet = aliqIssqnRet;
    }

    public Integer getCodigoTabCtbFisIssqnRet() {
        return codigoTabCtbFisIssqnRet;
    }

    public void setCodigoTabCtbFisIssqnRet(Integer codigoTabCtbFisIssqnRet) {
        this.codigoTabCtbFisIssqnRet = codigoTabCtbFisIssqnRet;
    }

    public Character getApuraPisCofinsCsllRet() {
        return apuraPisCofinsCsllRet;
    }

    public void setApuraPisCofinsCsllRet(Character apuraPisCofinsCsllRet) {
        this.apuraPisCofinsCsllRet = apuraPisCofinsCsllRet;
    }

    public BigDecimal getAliqPisRet() {
        return aliqPisRet;
    }

    public void setAliqPisRet(BigDecimal aliqPisRet) {
        this.aliqPisRet = aliqPisRet;
    }

    public Integer getCodigoTabCtbFisPisRet() {
        return codigoTabCtbFisPisRet;
    }

    public void setCodigoTabCtbFisPisRet(Integer codigoTabCtbFisPisRet) {
        this.codigoTabCtbFisPisRet = codigoTabCtbFisPisRet;
    }

    public BigDecimal getAliqCofinsRet() {
        return aliqCofinsRet;
    }

    public void setAliqCofinsRet(BigDecimal aliqCofinsRet) {
        this.aliqCofinsRet = aliqCofinsRet;
    }

    public Integer getCodigoTabCtbFisCofinsRet() {
        return codigoTabCtbFisCofinsRet;
    }

    public void setCodigoTabCtbFisCofinsRet(Integer codigoTabCtbFisCofinsRet) {
        this.codigoTabCtbFisCofinsRet = codigoTabCtbFisCofinsRet;
    }

    public BigDecimal getAliqCsllRet() {
        return aliqCsllRet;
    }

    public void setAliqCsllRet(BigDecimal aliqCsllRet) {
        this.aliqCsllRet = aliqCsllRet;
    }

    public Integer getCodigoTabCtbFisCsllRet() {
        return codigoTabCtbFisCsllRet;
    }

    public void setCodigoTabCtbFisCsllRet(Integer codigoTabCtbFisCsllRet) {
        this.codigoTabCtbFisCsllRet = codigoTabCtbFisCsllRet;
    }

    public Character getDetalharMed() {
        return detalharMed;
    }

    public void setDetalharMed(Character detalharMed) {
        this.detalharMed = detalharMed;
    }

    public Integer getEntraBaseValorAdicional() {
        return entraBaseValorAdicional;
    }

    public void setEntraBaseValorAdicional(Integer entraBaseValorAdicional) {
        this.entraBaseValorAdicional = entraBaseValorAdicional;
    }

    public Integer getExValorAdicional() {
        return exValorAdicional;
    }

    public void setExValorAdicional(Integer exValorAdicional) {
        this.exValorAdicional = exValorAdicional;
    }

    public String getRegraBaseExValorAdicional() {
        return regraBaseExValorAdicional;
    }

    public void setRegraBaseExValorAdicional(String regraBaseExValorAdicional) {
        this.regraBaseExValorAdicional = regraBaseExValorAdicional;
    }

    public Integer getCodigoTabCtbFisVlrContabil() {
        return codigoTabCtbFisVlrContabil;
    }

    public void setCodigoTabCtbFisVlrContabil(Integer codigoTabCtbFisVlrContabil) {
        this.codigoTabCtbFisVlrContabil = codigoTabCtbFisVlrContabil;
    }

    public Character getDetalharItemNf() {
        return detalharItemNf;
    }

    public void setDetalharItemNf(Character detalharItemNf) {
        this.detalharItemNf = detalharItemNf;
    }

    public Character getDetalharDuplicata() {
        return detalharDuplicata;
    }

    public void setDetalharDuplicata(Character detalharDuplicata) {
        this.detalharDuplicata = detalharDuplicata;
    }

    public Character getDiferida() {
        return diferida;
    }

    public void setDiferida(Character diferida) {
        this.diferida = diferida;
    }

    public Integer getCstPisCofins() {
        return cstPisCofins;
    }

    public void setCstPisCofins(Integer cstPisCofins) {
        this.cstPisCofins = cstPisCofins;
    }

    public Integer getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(Integer finalidade) {
        this.finalidade = finalidade;
    }

    public BigDecimal getContaCtbLivro() {
        return contaCtbLivro;
    }

    public void setContaCtbLivro(BigDecimal contaCtbLivro) {
        this.contaCtbLivro = contaCtbLivro;
    }

    public String getDescrComplCFOP() {
        return descrComplCFOP;
    }

    public void setDescrComplCFOP(String descrComplCFOP) {
        this.descrComplCFOP = descrComplCFOP;
    }

    public Character getApuracaoSimplesFederal() {
        return apuracaoSimplesFederal;
    }

    public void setApuracaoSimplesFederal(Character apuracaoSimplesFederal) {
        this.apuracaoSimplesFederal = apuracaoSimplesFederal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.codigoEmpresa;
        hash = 29 * hash + this.codigoEstab;
        hash = 29 * hash + this.codigoCFOP;
        hash = 29 * hash + Objects.hashCode(this.descricao);
        hash = 29 * hash + Objects.hashCode(this.apuraICMS);
        hash = 29 * hash + Objects.hashCode(this.codigoTabCtbFisICMS);
        hash = 29 * hash + Objects.hashCode(this.apuraISS);
        hash = 29 * hash + Objects.hashCode(this.codigoTabCtbFisISS);
        hash = 29 * hash + Objects.hashCode(this.apuraIPI);
        hash = 29 * hash + Objects.hashCode(this.codigoTabCtbFisIPI);
        hash = 29 * hash + Objects.hashCode(this.apuraFunRural);
        hash = 29 * hash + Objects.hashCode(this.codigoTabCtbFisFuneral);
        hash = 29 * hash + Objects.hashCode(this.apuraSubTribut);
        hash = 29 * hash + Objects.hashCode(this.codigoTabCtbFisSubTribut);
        hash = 29 * hash + Objects.hashCode(this.apuraPisCofinsOutros);
        hash = 29 * hash + Objects.hashCode(this.codigoTabCtbFisPIS);
        hash = 29 * hash + Objects.hashCode(this.codigoTabCtbFisCofins);
        hash = 29 * hash + Objects.hashCode(this.apuraIrrFret);
        hash = 29 * hash + Objects.hashCode(this.aliqIrrFret);
        hash = 29 * hash + Objects.hashCode(this.codigoTabCtbFisIrrFret);
        hash = 29 * hash + Objects.hashCode(this.apuraIrPjRet);
        hash = 29 * hash + Objects.hashCode(this.aliqIrPjRet);
        hash = 29 * hash + Objects.hashCode(this.codigoTabCtbFisIrPjRet);
        hash = 29 * hash + Objects.hashCode(this.apuraInssRet);
        hash = 29 * hash + Objects.hashCode(this.aliqInnRet);
        hash = 29 * hash + Objects.hashCode(this.codigoTabCtbFisInssRet);
        hash = 29 * hash + Objects.hashCode(this.apuraIssqnRet);
        hash = 29 * hash + Objects.hashCode(this.aliqIssqnRet);
        hash = 29 * hash + Objects.hashCode(this.codigoTabCtbFisIssqnRet);
        hash = 29 * hash + Objects.hashCode(this.apuraPisCofinsCsllRet);
        hash = 29 * hash + Objects.hashCode(this.aliqPisRet);
        hash = 29 * hash + Objects.hashCode(this.codigoTabCtbFisPisRet);
        hash = 29 * hash + Objects.hashCode(this.aliqCofinsRet);
        hash = 29 * hash + Objects.hashCode(this.codigoTabCtbFisCofinsRet);
        hash = 29 * hash + Objects.hashCode(this.aliqCsllRet);
        hash = 29 * hash + Objects.hashCode(this.codigoTabCtbFisCsllRet);
        hash = 29 * hash + Objects.hashCode(this.detalharMed);
        hash = 29 * hash + Objects.hashCode(this.entraBaseValorAdicional);
        hash = 29 * hash + Objects.hashCode(this.exValorAdicional);
        hash = 29 * hash + Objects.hashCode(this.regraBaseExValorAdicional);
        hash = 29 * hash + Objects.hashCode(this.codigoTabCtbFisVlrContabil);
        hash = 29 * hash + Objects.hashCode(this.detalharItemNf);
        hash = 29 * hash + Objects.hashCode(this.detalharDuplicata);
        hash = 29 * hash + Objects.hashCode(this.diferida);
        hash = 29 * hash + Objects.hashCode(this.cstPisCofins);
        hash = 29 * hash + Objects.hashCode(this.finalidade);
        hash = 29 * hash + Objects.hashCode(this.contaCtbLivro);
        hash = 29 * hash + Objects.hashCode(this.descrComplCFOP);
        hash = 29 * hash + Objects.hashCode(this.apuracaoSimplesFederal);
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
        final CFOP other = (CFOP) obj;
        if (this.codigoEmpresa != other.codigoEmpresa) {
            return false;
        }
        if (this.codigoEstab != other.codigoEstab) {
            return false;
        }
        if (this.codigoCFOP != other.codigoCFOP) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.regraBaseExValorAdicional, other.regraBaseExValorAdicional)) {
            return false;
        }
        if (!Objects.equals(this.descrComplCFOP, other.descrComplCFOP)) {
            return false;
        }
        if (!Objects.equals(this.apuraICMS, other.apuraICMS)) {
            return false;
        }
        if (!Objects.equals(this.codigoTabCtbFisICMS, other.codigoTabCtbFisICMS)) {
            return false;
        }
        if (!Objects.equals(this.apuraISS, other.apuraISS)) {
            return false;
        }
        if (!Objects.equals(this.codigoTabCtbFisISS, other.codigoTabCtbFisISS)) {
            return false;
        }
        if (!Objects.equals(this.apuraIPI, other.apuraIPI)) {
            return false;
        }
        if (!Objects.equals(this.codigoTabCtbFisIPI, other.codigoTabCtbFisIPI)) {
            return false;
        }
        if (!Objects.equals(this.apuraFunRural, other.apuraFunRural)) {
            return false;
        }
        if (!Objects.equals(this.codigoTabCtbFisFuneral, other.codigoTabCtbFisFuneral)) {
            return false;
        }
        if (!Objects.equals(this.apuraSubTribut, other.apuraSubTribut)) {
            return false;
        }
        if (!Objects.equals(this.codigoTabCtbFisSubTribut, other.codigoTabCtbFisSubTribut)) {
            return false;
        }
        if (!Objects.equals(this.apuraPisCofinsOutros, other.apuraPisCofinsOutros)) {
            return false;
        }
        if (!Objects.equals(this.codigoTabCtbFisPIS, other.codigoTabCtbFisPIS)) {
            return false;
        }
        if (!Objects.equals(this.codigoTabCtbFisCofins, other.codigoTabCtbFisCofins)) {
            return false;
        }
        if (!Objects.equals(this.apuraIrrFret, other.apuraIrrFret)) {
            return false;
        }
        if (!Objects.equals(this.aliqIrrFret, other.aliqIrrFret)) {
            return false;
        }
        if (!Objects.equals(this.codigoTabCtbFisIrrFret, other.codigoTabCtbFisIrrFret)) {
            return false;
        }
        if (!Objects.equals(this.apuraIrPjRet, other.apuraIrPjRet)) {
            return false;
        }
        if (!Objects.equals(this.aliqIrPjRet, other.aliqIrPjRet)) {
            return false;
        }
        if (!Objects.equals(this.codigoTabCtbFisIrPjRet, other.codigoTabCtbFisIrPjRet)) {
            return false;
        }
        if (!Objects.equals(this.apuraInssRet, other.apuraInssRet)) {
            return false;
        }
        if (!Objects.equals(this.aliqInnRet, other.aliqInnRet)) {
            return false;
        }
        if (!Objects.equals(this.codigoTabCtbFisInssRet, other.codigoTabCtbFisInssRet)) {
            return false;
        }
        if (!Objects.equals(this.apuraIssqnRet, other.apuraIssqnRet)) {
            return false;
        }
        if (!Objects.equals(this.aliqIssqnRet, other.aliqIssqnRet)) {
            return false;
        }
        if (!Objects.equals(this.codigoTabCtbFisIssqnRet, other.codigoTabCtbFisIssqnRet)) {
            return false;
        }
        if (!Objects.equals(this.apuraPisCofinsCsllRet, other.apuraPisCofinsCsllRet)) {
            return false;
        }
        if (!Objects.equals(this.aliqPisRet, other.aliqPisRet)) {
            return false;
        }
        if (!Objects.equals(this.codigoTabCtbFisPisRet, other.codigoTabCtbFisPisRet)) {
            return false;
        }
        if (!Objects.equals(this.aliqCofinsRet, other.aliqCofinsRet)) {
            return false;
        }
        if (!Objects.equals(this.codigoTabCtbFisCofinsRet, other.codigoTabCtbFisCofinsRet)) {
            return false;
        }
        if (!Objects.equals(this.aliqCsllRet, other.aliqCsllRet)) {
            return false;
        }
        if (!Objects.equals(this.codigoTabCtbFisCsllRet, other.codigoTabCtbFisCsllRet)) {
            return false;
        }
        if (!Objects.equals(this.detalharMed, other.detalharMed)) {
            return false;
        }
        if (!Objects.equals(this.entraBaseValorAdicional, other.entraBaseValorAdicional)) {
            return false;
        }
        if (!Objects.equals(this.exValorAdicional, other.exValorAdicional)) {
            return false;
        }
        if (!Objects.equals(this.codigoTabCtbFisVlrContabil, other.codigoTabCtbFisVlrContabil)) {
            return false;
        }
        if (!Objects.equals(this.detalharItemNf, other.detalharItemNf)) {
            return false;
        }
        if (!Objects.equals(this.detalharDuplicata, other.detalharDuplicata)) {
            return false;
        }
        if (!Objects.equals(this.diferida, other.diferida)) {
            return false;
        }
        if (!Objects.equals(this.cstPisCofins, other.cstPisCofins)) {
            return false;
        }
        if (!Objects.equals(this.finalidade, other.finalidade)) {
            return false;
        }
        if (!Objects.equals(this.contaCtbLivro, other.contaCtbLivro)) {
            return false;
        }
        if (!Objects.equals(this.apuracaoSimplesFederal, other.apuracaoSimplesFederal)) {
            return false;
        }
        return true;
    }
    
    
}