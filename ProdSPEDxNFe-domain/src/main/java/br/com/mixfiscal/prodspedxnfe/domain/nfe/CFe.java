package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class CFe extends NFe implements Serializable{
    private static final long serialVersionUID = -1039210149716865691L;
    
    private String infCFe;
    private String nserieSAT;
    private String nCFe;    
    private String numeroCaixa;
    private String cnpj;
    private List<CFeItem> itens;

    public CFe() {
        this.itens = new ArrayList<>();
    }
    
    
    public String getInfCFe() {
        return infCFe;
    }

    public void setInfCFe(String infCFe) {
        this.infCFe = infCFe;
    }

    public String getNserieSAT() {
        return nserieSAT;
    }

    public void setNserieSAT(String nserieSAT) {
        this.nserieSAT = nserieSAT;
    }

    public String getnCFe() {
        return nCFe;
    }

    public void setnCFe(String nCFe) {
        this.nCFe = nCFe;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNumeroCaixa() {
        return numeroCaixa;
    }

    public void setNumeroCaixa(String numeroCaixa) {
        this.numeroCaixa = numeroCaixa;
    }

    public List<CFeItem> getItensCfe() {
        return itens;
    }

    public void setItensCfe(List<CFeItem> itens) {
        this.itens = itens;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.infCFe);
        hash = 97 * hash + Objects.hashCode(this.nserieSAT);
        hash = 97 * hash + Objects.hashCode(this.nCFe);
        hash = 97 * hash + Objects.hashCode(this.numeroCaixa);
        hash = 97 * hash + Objects.hashCode(this.cnpj);
        hash = 97 * hash + Objects.hashCode(this.itens);
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
        final CFe other = (CFe) obj;
        if (!Objects.equals(this.infCFe, other.infCFe)) {
            return false;
        }
        if (!Objects.equals(this.nserieSAT, other.nserieSAT)) {
            return false;
        }
        if (!Objects.equals(this.nCFe, other.nCFe)) {
            return false;
        }
        if (!Objects.equals(this.numeroCaixa, other.numeroCaixa)) {
            return false;
        }
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        if (!Objects.equals(this.itens, other.itens)) {
            return false;
        }
        return true;
    }
    
        
}
