package br.com.mixfiscal.prodspedxnfe.domain.sped;

import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoItem;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.io.Serializable;
import java.util.Objects;

public class SPED0200 extends SPEDBase implements Serializable {
    private static final long serialVersionUID = -8187919208181050113L;
    
    private String codItem;
    private String descrItem;
    private String descrItemCustom;
    private String codBarra;
    private ETipoItem tipoItem;
    private String codNCM;
    private SPED0220 sped0220;
        
    public SPED0200() {
        sped0220 = new SPED0220();                
        this.getIndicesColunas().put("codItem", 2);
        this.getIndicesColunas().put("descrItem", 3);
        this.getIndicesColunas().put("codBarra", 4);
        this.getIndicesColunas().put("tipoItem", 7);
        this.getIndicesColunas().put("codNCM", 8);
    }
    
    // <editor-fold desc="Getters e Setters" defaultstate="collapsed">
    public String getCodItem() {
        return codItem;
    }

    public void setCodItem(String codItem) {
        this.codItem = codItem;
    }

    public String getDescrItem() {
        return descrItem;
    }

    public void setDescrItem(String descrItem) {
        this.descrItem = descrItem;
    }
    public String getDescricaoCustom(){
        return this.descrItemCustom = StringUtil.removerAcentos(this.descrItem);
        
    }
    public String getCodBarra() {
        return codBarra;
    }

    public void setCodBarra(String codBarra) {
        this.codBarra = codBarra;
    }

    public ETipoItem getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(ETipoItem tipoItem) {
        this.tipoItem = tipoItem;
    }

    public String getCodNCM() {
        return codNCM;
    }

    public void setCodNCM(String codNCM) {
        this.codNCM = codNCM;
    }

    public SPED0220 getSPED0220() {
        return sped0220;
    }

    public void setSPED0220(SPED0220 sped0220) {
        this.sped0220 = sped0220;
    }
    // </editor-fold>
    
    // <editor-fold desc="equals e hashCode" defaultstate="collapsed">
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.codItem);
        hash = 61 * hash + Objects.hashCode(this.descrItem);
        hash = 61 * hash + Objects.hashCode(this.codBarra);
        hash = 61 * hash + Objects.hashCode(this.tipoItem);
        hash = 61 * hash + Objects.hashCode(this.codNCM);
        hash = 61 * hash + Objects.hashCode(this.sped0220);
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
        final SPED0200 other = (SPED0200) obj;
        if (!Objects.equals(this.codItem, other.codItem)) {
            return false;
        }
        if (!Objects.equals(this.descrItem, other.descrItem)) {
            return false;
        }
        if (!Objects.equals(this.codBarra, other.codBarra)) {
            return false;
        }
        if (!Objects.equals(this.codNCM, other.codNCM)) {
            return false;
        }
        if (this.tipoItem != other.tipoItem) {
            return false;
        }
        if (!Objects.equals(this.sped0220, other.sped0220)) {
            return false;
        }
        return true;
    }
    // </editor-fold>
}
