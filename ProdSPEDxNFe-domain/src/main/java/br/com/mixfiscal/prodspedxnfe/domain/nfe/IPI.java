package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class IPI implements Serializable{
    private static final long serialVersionUID = -4228641680117497355L;
    
    private String CST;
    private String cIEnq;
    private String CNPJProd;
    private String cSelo;
    private String qSelo;
    private String cEnq;
    private String IPITrib;
    private BigDecimal vBC;
    private BigDecimal pIPI;
    private BigDecimal qUnid;
    private BigDecimal vUnid;
    private BigDecimal vIPI;
    private String IPINT;

    public String getCST() {
        return CST;
    }

    public void setCST(String CST) {
        this.CST = CST;
    }

    public String getcIEnq() {
        return cIEnq;
    }

    public void setcIEnq(String cIEnq) {
        this.cIEnq = cIEnq;
    }

    public String getCNPJProd() {
        return CNPJProd;
    }

    public void setCNPJProd(String CNPJProd) {
        this.CNPJProd = CNPJProd;
    }

    public String getcSelo() {
        return cSelo;
    }

    public void setcSelo(String cSelo) {
        this.cSelo = cSelo;
    }

    public String getqSelo() {
        return qSelo;
    }

    public void setqSelo(String qSelo) {
        this.qSelo = qSelo;
    }

    public String getcEnq() {
        return cEnq;
    }

    public void setcEnq(String cEnq) {
        this.cEnq = cEnq;
    }

    public String getIPITrib() {
        return IPITrib;
    }

    public void setIPITrib(String IPITrib) {
        this.IPITrib = IPITrib;
    }

    public BigDecimal getvBC() {
        return vBC;
    }

    public void setvBC(BigDecimal vBC) {
        this.vBC = vBC;
    }

    public BigDecimal getpIPI() {
        return pIPI;
    }

    public void setpIPI(BigDecimal pIPI) {
        this.pIPI = pIPI;
    }

    public BigDecimal getqUnid() {
        return qUnid;
    }

    public void setqUnid(BigDecimal qUnid) {
        this.qUnid = qUnid;
    }

    public BigDecimal getvUnid() {
        return vUnid;
    }

    public void setvUnid(BigDecimal vUnid) {
        this.vUnid = vUnid;
    }

    public BigDecimal getvIPI() {
        return vIPI;
    }

    public void setvIPI(BigDecimal vIPI) {
        this.vIPI = vIPI;
    }

    public String getIPINT() {
        return IPINT;
    }

    public void setIPINT(String IPINT) {
        this.IPINT = IPINT;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.CST);
        hash = 23 * hash + Objects.hashCode(this.cIEnq);
        hash = 23 * hash + Objects.hashCode(this.CNPJProd);
        hash = 23 * hash + Objects.hashCode(this.cSelo);
        hash = 23 * hash + Objects.hashCode(this.qSelo);
        hash = 23 * hash + Objects.hashCode(this.cEnq);
        hash = 23 * hash + Objects.hashCode(this.IPITrib);
        hash = 23 * hash + Objects.hashCode(this.vBC);
        hash = 23 * hash + Objects.hashCode(this.pIPI);
        hash = 23 * hash + Objects.hashCode(this.qUnid);
        hash = 23 * hash + Objects.hashCode(this.vUnid);
        hash = 23 * hash + Objects.hashCode(this.vIPI);
        hash = 23 * hash + Objects.hashCode(this.IPINT);
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
        final IPI other = (IPI) obj;
        if (this.CST != other.CST) {
            return false;
        }
        if (!Objects.equals(this.cIEnq, other.cIEnq)) {
            return false;
        }
        if (!Objects.equals(this.CNPJProd, other.CNPJProd)) {
            return false;
        }
        if (!Objects.equals(this.cSelo, other.cSelo)) {
            return false;
        }
        if (!Objects.equals(this.qSelo, other.qSelo)) {
            return false;
        }
        if (!Objects.equals(this.cEnq, other.cEnq)) {
            return false;
        }
        if (!Objects.equals(this.IPITrib, other.IPITrib)) {
            return false;
        }
        if (!Objects.equals(this.IPINT, other.IPINT)) {
            return false;
        }
        if (!Objects.equals(this.vBC, other.vBC)) {
            return false;
        }
        if (!Objects.equals(this.pIPI, other.pIPI)) {
            return false;
        }
        if (!Objects.equals(this.qUnid, other.qUnid)) {
            return false;
        }
        if (!Objects.equals(this.vUnid, other.vUnid)) {
            return false;
        }
        if (!Objects.equals(this.vIPI, other.vIPI)) {
            return false;
        }
        return true;
    }
    
}
