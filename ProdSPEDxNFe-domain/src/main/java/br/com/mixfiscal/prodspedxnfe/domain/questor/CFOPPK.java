package br.com.mixfiscal.prodspedxnfe.domain.questor;

import java.io.Serializable;

public class CFOPPK implements Serializable {
    private Integer codigoEmpresa;
    private Integer codigoEstab;
    private Integer codigoCFOP;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.codigoEmpresa;
        hash = 41 * hash + this.codigoEstab;
        hash = 41 * hash + this.codigoCFOP;
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
        final CFOPPK other = (CFOPPK) obj;
        if (this.codigoEmpresa != other.codigoEmpresa) {
            return false;
        }
        if (this.codigoEstab != other.codigoEstab) {
            return false;
        }
        if (this.codigoCFOP != other.codigoCFOP) {
            return false;
        }
        return true;
    }
}
