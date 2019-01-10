package br.com.mixfiscal.prodspedxnfe.domain.sped;

import br.com.mixfiscal.prodspedxnfe.domain.nfe.CFe;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
public class SPEDC800 extends SPEDBase implements Serializable {
    private static final long serialVersionUID = -1445993105278051069L;
    
    private String codMod;//COD_MOD
    private String codSit;//COD_SIT
    private String numCfe;//NUM_CFE
    private Date dtDoc;//DT_DOC
    private BigDecimal vlCfe;//VL_CFE
    private BigDecimal vlPis;//VL_PIS
    private BigDecimal vlCofins;//VL_COFINS
    private String cnpjCpf;//CNPJ_CPF
    private String nrSat;//NR_SAT
    private String chvCfe;//CHV_CFE
    private BigDecimal vlDesc;//VL_DESC
    private BigDecimal vlMerc;//VL_MERC
    private BigDecimal vlOutDa;//VL_OUT_DA
    private BigDecimal vlIcms;//VL_ICMS
    private BigDecimal vlPisSt;//VL_PIS_ST
    private BigDecimal vlCofinsSt;//VL_COFINS_ST
    private SPED0200 spd0200;
    private List<SPEDC850>listaSpedC850;
    private CFe xmlReferente;
    
    public SPEDC800(){
        this.listaSpedC850 = new ArrayList<>();
    }
    // <editor-fold desc="Getters e Setters" defaultstate="collapsed">
    public String getCodMod() {
        return codMod;
    }

    public void setCodMod(String codMod) {
        this.codMod = codMod;
    }

    public String getCodSit() {
        return codSit;
    }

    public void setCodSit(String codSit) {
        this.codSit = codSit;
    }

    public String getNumCfe() {
        return numCfe;
    }

    public void setNumCfe(String numCfe) {
        this.numCfe = numCfe;
    }

    public Date getDtDoc() {
        return dtDoc;
    }

    public void setDtDoc(Date dtDoc) {
        this.dtDoc = dtDoc;
    }

    public BigDecimal getVlCfe() {
        return vlCfe;
    }

    public void setVlCfe(BigDecimal vlCfe) {
        this.vlCfe = vlCfe;
    }

    public BigDecimal getVlPis() {
        return vlPis;
    }

    public void setVlPis(BigDecimal vlPis) {
        this.vlPis = vlPis;
    }

    public BigDecimal getVlCofins() {
        return vlCofins;
    }

    public void setVlCofins(BigDecimal vlCofins) {
        this.vlCofins = vlCofins;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }

    public String getNrSat() {
        return nrSat;
    }

    public void setNrSat(String nrSat) {
        this.nrSat = nrSat;
    }

    public String getChvCfe() {
        return chvCfe;
    }

    public void setChvCfe(String chvCfe) {
        this.chvCfe = chvCfe;
    }

    public BigDecimal getVlDesc() {
        return vlDesc;
    }

    public void setVlDesc(BigDecimal vlDesc) {
        this.vlDesc = vlDesc;
    }

    public BigDecimal getVlMerc() {
        return vlMerc;
    }

    public void setVlMerc(BigDecimal vlMerc) {
        this.vlMerc = vlMerc;
    }

    public BigDecimal getVlOutDa() {
        return vlOutDa;
    }

    public void setVlOutDa(BigDecimal vlOutDa) {
        this.vlOutDa = vlOutDa;
    }

    public BigDecimal getVlIcms() {
        return vlIcms;
    }

    public void setVlIcms(BigDecimal vlIcms) {
        this.vlIcms = vlIcms;
    }

    public BigDecimal getVlPisSt() {
        return vlPisSt;
    }

    public void setVlPisSt(BigDecimal vlPisSt) {
        this.vlPisSt = vlPisSt;
    }

    public BigDecimal getVlCofinsSt() {
        return vlCofinsSt;
    }

    public void setVlCofinsSt(BigDecimal vlCofinsSt) {
        this.vlCofinsSt = vlCofinsSt;
    }

    public SPED0200 getSpd0200() {
        return spd0200;
    }

    public void setSpd0200(SPED0200 spd0200) {
        this.spd0200 = spd0200;
    }

    public CFe getXmlReferente() {
        return xmlReferente;
    }

    public void setXmlReferente(CFe xmlReferente) {
        this.xmlReferente = xmlReferente;
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.codMod);
        hash = 17 * hash + Objects.hashCode(this.codSit);
        hash = 17 * hash + Objects.hashCode(this.numCfe);
        hash = 17 * hash + Objects.hashCode(this.dtDoc);
        hash = 17 * hash + Objects.hashCode(this.vlCfe);
        hash = 17 * hash + Objects.hashCode(this.vlPis);
        hash = 17 * hash + Objects.hashCode(this.vlCofins);
        hash = 17 * hash + Objects.hashCode(this.cnpjCpf);
        hash = 17 * hash + Objects.hashCode(this.nrSat);
        hash = 17 * hash + Objects.hashCode(this.chvCfe);
        hash = 17 * hash + Objects.hashCode(this.vlDesc);
        hash = 17 * hash + Objects.hashCode(this.vlMerc);
        hash = 17 * hash + Objects.hashCode(this.vlOutDa);
        hash = 17 * hash + Objects.hashCode(this.vlIcms);
        hash = 17 * hash + Objects.hashCode(this.vlPisSt);
        hash = 17 * hash + Objects.hashCode(this.vlCofinsSt);
        hash = 17 * hash + Objects.hashCode(this.spd0200);
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
        final SPEDC800 other = (SPEDC800) obj;
        if (!Objects.equals(this.codMod, other.codMod)) {
            return false;
        }
        if (!Objects.equals(this.codSit, other.codSit)) {
            return false;
        }
        if (!Objects.equals(this.numCfe, other.numCfe)) {
            return false;
        }
        if (!Objects.equals(this.cnpjCpf, other.cnpjCpf)) {
            return false;
        }
        if (!Objects.equals(this.nrSat, other.nrSat)) {
            return false;
        }
        if (!Objects.equals(this.chvCfe, other.chvCfe)) {
            return false;
        }
        if (!Objects.equals(this.dtDoc, other.dtDoc)) {
            return false;
        }
        if (!Objects.equals(this.vlCfe, other.vlCfe)) {
            return false;
        }
        if (!Objects.equals(this.vlPis, other.vlPis)) {
            return false;
        }
        if (!Objects.equals(this.vlCofins, other.vlCofins)) {
            return false;
        }
        if (!Objects.equals(this.vlDesc, other.vlDesc)) {
            return false;
        }
        if (!Objects.equals(this.vlMerc, other.vlMerc)) {
            return false;
        }
        if (!Objects.equals(this.vlOutDa, other.vlOutDa)) {
            return false;
        }
        if (!Objects.equals(this.vlIcms, other.vlIcms)) {
            return false;
        }
        if (!Objects.equals(this.vlPisSt, other.vlPisSt)) {
            return false;
        }
        if (!Objects.equals(this.vlCofinsSt, other.vlCofinsSt)) {
            return false;
        }
        if (!Objects.equals(this.spd0200, other.spd0200)) {
            return false;
        }
        return true;
    }
    // </editor-fold>

    public void addSPEDC850(SPEDC850 sped ){
        this.listaSpedC850.add(sped);
    }
    
    public List<SPEDC850> getSPEDC850(){
        return this.listaSpedC850;
    }
        
}
