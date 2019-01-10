package br.com.mixfiscal.prodspedxnfe.domain.wsmix;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ClassificacaoTributaria implements Serializable  {
    private static final long serialVersionUID = 6603375034076775129L; 
    
    private String status;
    private Produto produto;
    private Date dataResposta;
    private String proximoCaptcha;
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public Date getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(Date dataResposta) {
        this.dataResposta = dataResposta;
    }

    public String getProximoCaptcha() {
        return proximoCaptcha;
    }

    public void setProximoCaptcha(String proximoCaptcha) {
        this.proximoCaptcha = proximoCaptcha;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.status);
        hash = 53 * hash + Objects.hashCode(this.dataResposta);
        hash = 53 * hash + Objects.hashCode(this.proximoCaptcha);
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
        final ClassificacaoTributaria other = (ClassificacaoTributaria) obj;
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.proximoCaptcha, other.proximoCaptcha)) {
            return false;
        }
        if (!Objects.equals(this.dataResposta, other.dataResposta)) {
            return false;
        }
        return true;
    }   
    
}
