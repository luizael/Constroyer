package br.com.mixfiscal.prodspedxnfe.domain.sped;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class SPED0000 extends SPEDBase implements Serializable {
    private static final long serialVersionUID = -7224068271691261662L;
    
    private String codVer;
    private String codFin;    
    private Date dataIni;
    private Date dataFin;
    private String nomeEmpresarialEntidade;
    private String CNPJ;
    private String CPF;
    private String UF;
    private String IE;
    private String codMun;
    private String IM;
    private String suframa;
    private String indPerfil;
    private String indAtiv;

    public SPED0000() {        
        this.getIndicesColunas().put("codVer", 2);
        this.getIndicesColunas().put("codFin", 3);
        this.getIndicesColunas().put("dataIni", 4);
        this.getIndicesColunas().put("dataFin", 5);
        this.getIndicesColunas().put("nomeEmpresarialEntidade", 6);
        this.getIndicesColunas().put("CNPJ", 7);
        this.getIndicesColunas().put("CPF", 8);
        this.getIndicesColunas().put("UF", 9);
        this.getIndicesColunas().put("IE", 10);
        this.getIndicesColunas().put("codMun", 11);
        this.getIndicesColunas().put("IM", 12);
        this.getIndicesColunas().put("suframa", 13);
        this.getIndicesColunas().put("indPerfil", 14);
        this.getIndicesColunas().put("indAtiv", 15);
    }
    
    // <editor-fold desc="Getters e Setters" defaultstate="collapsed">
    public String getCodVer() {
        return codVer;
    }

    public void setCodVer(String codVer) {
        this.codVer = codVer;
    }

    public String getCodFin() {
        return codFin;
    }

    public void setCodFin(String codFin) {
        this.codFin = codFin;
    }

    public Date getDataIni() {
        return dataIni;
    }

    public void setDataIni(Date dataIni) {
        this.dataIni = dataIni;
    }

    public Date getDataFin() {
        return dataFin;
    }

    public void setDataFin(Date dataFin) {
        this.dataFin = dataFin;
    }

    public String getNomeEmpresarialEntidade() {
        return nomeEmpresarialEntidade;
    }

    public void setNomeEmpresarialEntidade(String nomeEmpresarialEntidade) {
        this.nomeEmpresarialEntidade = nomeEmpresarialEntidade;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public String getIE() {
        return IE;
    }

    public void setIE(String IE) {
        this.IE = IE;
    }

    public String getCodMun() {
        return codMun;
    }

    public void setCodMun(String codMun) {
        this.codMun = codMun;
    }

    public String getIM() {
        return IM;
    }

    public void setIM(String IM) {
        this.IM = IM;
    }

    public String getSuframa() {
        return suframa;
    }

    public void setSuframa(String suframa) {
        this.suframa = suframa;
    }

    public String getIndPerfil() {
        return indPerfil;
    }

    public void setIndPerfil(String indPerfil) {
        this.indPerfil = indPerfil;
    }

    public String getIndAtiv() {
        return indAtiv;
    }

    public void setIndAtiv(String indAtiv) {
        this.indAtiv = indAtiv;
    }
    // </editor-fold>
    
    // <editor-fold desc="equals e hashCode" defaultstate="collapsed">
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.codVer);
        hash = 37 * hash + Objects.hashCode(this.codFin);
        hash = 37 * hash + Objects.hashCode(this.dataIni);
        hash = 37 * hash + Objects.hashCode(this.dataFin);
        hash = 37 * hash + Objects.hashCode(this.nomeEmpresarialEntidade);
        hash = 37 * hash + Objects.hashCode(this.CNPJ);
        hash = 37 * hash + Objects.hashCode(this.CPF);
        hash = 37 * hash + Objects.hashCode(this.UF);
        hash = 37 * hash + Objects.hashCode(this.IE);
        hash = 37 * hash + Objects.hashCode(this.codMun);
        hash = 37 * hash + Objects.hashCode(this.IM);
        hash = 37 * hash + Objects.hashCode(this.suframa);
        hash = 37 * hash + Objects.hashCode(this.indPerfil);
        hash = 37 * hash + Objects.hashCode(this.indAtiv);
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
        final SPED0000 other = (SPED0000) obj;
        if (!Objects.equals(this.codVer, other.codVer)) {
            return false;
        }
        if (!Objects.equals(this.codFin, other.codFin)) {
            return false;
        }
        if (!Objects.equals(this.nomeEmpresarialEntidade, other.nomeEmpresarialEntidade)) {
            return false;
        }
        if (!Objects.equals(this.CNPJ, other.CNPJ)) {
            return false;
        }
        if (!Objects.equals(this.CPF, other.CPF)) {
            return false;
        }
        if (!Objects.equals(this.UF, other.UF)) {
            return false;
        }
        if (!Objects.equals(this.IE, other.IE)) {
            return false;
        }
        if (!Objects.equals(this.codMun, other.codMun)) {
            return false;
        }
        if (!Objects.equals(this.IM, other.IM)) {
            return false;
        }
        if (!Objects.equals(this.suframa, other.suframa)) {
            return false;
        }
        if (!Objects.equals(this.indPerfil, other.indPerfil)) {
            return false;
        }
        if (!Objects.equals(this.indAtiv, other.indAtiv)) {
            return false;
        }
        if (!Objects.equals(this.dataIni, other.dataIni)) {
            return false;
        }
        if (!Objects.equals(this.dataFin, other.dataFin)) {
            return false;
        }
        return true;
    }
    // </editor-fold>
}
