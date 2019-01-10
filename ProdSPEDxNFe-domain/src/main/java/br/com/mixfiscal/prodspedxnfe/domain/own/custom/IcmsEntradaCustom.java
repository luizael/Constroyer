
package br.com.mixfiscal.prodspedxnfe.domain.own.custom;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="classificacao_tributaria_produto_icms_entrada")
public class IcmsEntradaCustom implements Serializable{
    private static final long serialVersionUID = -5183858497953250498L;
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
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idIcms);
        hash = 79 * hash + Objects.hashCode(this.cnpjCliente);
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
        final IcmsEntradaCustom other = (IcmsEntradaCustom) obj;
        if (!Objects.equals(this.idIcms, other.idIcms)) {
            return false;
        }
        if (!Objects.equals(this.cnpjCliente, other.cnpjCliente)) {
            return false;
        }
        return true;
    }
     
     
}
