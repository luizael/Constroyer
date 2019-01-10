package br.com.mixfiscal.prodspedxnfe.domain.questor;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UNIDMEDIDANOME")
public class UnidadeMedida implements Serializable {
    private static final long serialVersionUID = -5942572244764306616L;    
    
    @Id
    @Column(name = "CODIGOEMPRESA")
    private Integer codigoEmpresa;
    @Id
    @Column(name = "CODIGOUN")
    private String codigoUnidadeMedida;
    @Column(name = "NOMEUN")
    private String nome;

    public Integer getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(Integer codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getCodigoUnidadeMedida() {
        return codigoUnidadeMedida;
    }

    public void setCodigoUnidadeMedida(String codigoUnidadeMedida) {
        this.codigoUnidadeMedida = codigoUnidadeMedida;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.codigoEmpresa);
        hash = 97 * hash + Objects.hashCode(this.codigoUnidadeMedida);
        hash = 97 * hash + Objects.hashCode(this.nome);
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
        final UnidadeMedida other = (UnidadeMedida) obj;
        if (!Objects.equals(this.codigoUnidadeMedida, other.codigoUnidadeMedida)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.codigoEmpresa, other.codigoEmpresa)) {
            return false;
        }
        return true;
    }
}
