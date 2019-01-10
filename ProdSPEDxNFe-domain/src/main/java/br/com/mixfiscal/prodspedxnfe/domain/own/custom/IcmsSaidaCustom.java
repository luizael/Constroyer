
package br.com.mixfiscal.prodspedxnfe.domain.own.custom;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="classificacao_tributaria_produto_icms_saida")
public class IcmsSaidaCustom implements Serializable{

    private static final long serialVersionUID = -5920594472761624717L;
     @Id
     private Integer idIcms;
     
     @Column(name = "cnpj_cliente")
     private String cnpjCliente;

    public Integer getIdIcms() {
        return idIcms;
    }

    public void setIdIcms(Integer idIcms) {
        this.idIcms = idIcms;
    }

    public String getCnpjCliente() {
        return cnpjCliente;
    }

    public void setCnpjCliente(String cnpjCliente) {
        this.cnpjCliente = cnpjCliente;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.idIcms);
        hash = 23 * hash + Objects.hashCode(this.cnpjCliente);
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
        final IcmsSaidaCustom other = (IcmsSaidaCustom) obj;
        if (!Objects.equals(this.idIcms, other.idIcms)) {
            return false;
        }
        if (!Objects.equals(this.cnpjCliente, other.cnpjCliente)) {
            return false;
        }
        return true;
    }
     
}
