package br.com.mixfiscal.prodspedxnfe.domain.sped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

public class SPEDC460 extends SPEDBase implements Serializable {
   private static final long serialVersionUID = -8669901757712677798L;
   
   private int sequencia;
   private String codMod;//COD_MOD
   private String codSit;//COD_SIT
   private String numDoc;//NUM_DOC
   private String dtDoc;//DT_DOC
   private String vlDoc;//VL_DOC
   private String vlPis;//VL_PIS
   private String vlCofins;//VL_COFINS
   private String cpfCnpj;//CPF_CNPJ
   private String nomAdq;//NOM_ADQ
   private SPEDC100 sc100;
   private final List<SPEDC470>listaSPEDC470;

    public SPEDC460() {
        this.listaSPEDC470 = new ArrayList<>();
    }
    // <editor-fold defaultstate="collapsed" desc="Get e Set">  
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

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getDtDoc() {
        return dtDoc;
    }

    public void setDtDoc(String dtDoc) {
        this.dtDoc = dtDoc;
    }

    public String getVlDoc() {
        return vlDoc;
    }

    public void setVlDoc(String vlDoc) {
        this.vlDoc = vlDoc;
    }

    public String getVlPis() {
        return vlPis;
    }

    public void setVlPis(String vlPis) {
        this.vlPis = vlPis;
    }

    public String getVlCofins() {
        return vlCofins;
    }

    public void setVlCofins(String vlCofins) {
        this.vlCofins = vlCofins;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getNomAdq() {
        return nomAdq;
    }

    public void setNomAdq(String nomAdq) {
        this.nomAdq = nomAdq;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public SPEDC100 getSc100() {
        return sc100;
    }

    public void setSc100(SPEDC100 sc100) {
        this.sc100 = sc100;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<SPEDC470> getListaSPEDC470() {
        return listaSPEDC470;
    }   

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.sequencia;
        hash = 37 * hash + Objects.hashCode(this.codMod);
        hash = 37 * hash + Objects.hashCode(this.codSit);
        hash = 37 * hash + Objects.hashCode(this.numDoc);
        hash = 37 * hash + Objects.hashCode(this.dtDoc);
        hash = 37 * hash + Objects.hashCode(this.vlDoc);
        hash = 37 * hash + Objects.hashCode(this.vlPis);
        hash = 37 * hash + Objects.hashCode(this.vlCofins);
        hash = 37 * hash + Objects.hashCode(this.cpfCnpj);
        hash = 37 * hash + Objects.hashCode(this.nomAdq);
        hash = 37 * hash + Objects.hashCode(this.sc100);
        hash = 37 * hash + Objects.hashCode(this.listaSPEDC470);
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
        final SPEDC460 other = (SPEDC460) obj;
        if (this.sequencia != other.sequencia) {
            return false;
        }
        if (!Objects.equals(this.codMod, other.codMod)) {
            return false;
        }
        if (!Objects.equals(this.codSit, other.codSit)) {
            return false;
        }
        if (!Objects.equals(this.numDoc, other.numDoc)) {
            return false;
        }
        if (!Objects.equals(this.dtDoc, other.dtDoc)) {
            return false;
        }
        if (!Objects.equals(this.vlDoc, other.vlDoc)) {
            return false;
        }
        if (!Objects.equals(this.vlPis, other.vlPis)) {
            return false;
        }
        if (!Objects.equals(this.vlCofins, other.vlCofins)) {
            return false;
        }
        if (!Objects.equals(this.cpfCnpj, other.cpfCnpj)) {
            return false;
        }
        if (!Objects.equals(this.nomAdq, other.nomAdq)) {
            return false;
        }
        if (!Objects.equals(this.sc100, other.sc100)) {
            return false;
        }
        if (!Objects.equals(this.listaSPEDC470, other.listaSPEDC470)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
   // </editor-fold>
    
    public void addSPEDC470(SPEDC470 sc470){
        this.listaSPEDC470.add(sc470);
    }
    public List<SPEDC470> getSPEDC470(){
        return this.listaSPEDC470;
    }    
    public void removerSPEDC470(SPEDC470 sc470){
        this.listaSPEDC470.remove(sc470);
    }
}
