package br.com.mixfiscal.prodspedxnfe.domain.questor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MUNICIPIO")
public class Municipio implements Serializable {
    private static final long serialVersionUID = -9086801268802971572L;    
    
    @Id
    @Column(name = "SIGLAESTADO")
    private String siglaEstado;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MunicipioSeq")
    @GenericGenerator(name = "MunicipioSeq", strategy = "br.com.mixfiscal.prodspedxnfe.dao.questor.seq.MunicipioCodigoMunicipioSeq")
    @Column(name = "CODIGOMUNIC")
    private Integer codigoMunicipio;
    @Column(name = "NOMEMUNIC")
    private String nome;
    @Column(name = "CEPMUNIC")
    private String cep;
    @Column(name = "VALORMINISSRETIDO")
    private BigDecimal valorMinISSRetido;
    @Column(name = "TAXARECISSQN")
    private BigDecimal taxaRecISSQN;
    @Column(name = "CODIGORAIS")
    private Integer codigoRais;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CodMunicipioSeq")
    @GenericGenerator(name = "CodMunicipioSeq", strategy = "br.com.mixfiscal.prodspedxnfe.dao.questor.seq.MunicipioCodigoFederalMunicipioSeq")
    @Column(name = "CODIGOMUNICIPIO")    
    private Integer codigoFederalMunicipio;

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public Integer getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(Integer codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public BigDecimal getValorMinISSRetido() {
        return valorMinISSRetido;
    }

    public void setValorMinISSRetido(BigDecimal valorMinISSRetido) {
        this.valorMinISSRetido = valorMinISSRetido;
    }

    public BigDecimal getTaxaRecISSQN() {
        return taxaRecISSQN;
    }

    public void setTaxaRecISSQN(BigDecimal taxaRecISSQN) {
        this.taxaRecISSQN = taxaRecISSQN;
    }

    public Integer getCodigoRais() {
        return codigoRais;
    }

    public void setCodigoRais(Integer codigoRais) {
        this.codigoRais = codigoRais;
    }

    public Integer getCodigoFederalMunicipio() {
        return codigoFederalMunicipio;
    }

    public void setCodigoFederalMunicipio(Integer codigoFederalMunicipio) {
        this.codigoFederalMunicipio = codigoFederalMunicipio;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.siglaEstado);
        hash = 43 * hash + Objects.hashCode(this.codigoMunicipio);
        hash = 43 * hash + Objects.hashCode(this.nome);
        hash = 43 * hash + Objects.hashCode(this.cep);
        hash = 43 * hash + Objects.hashCode(this.valorMinISSRetido);
        hash = 43 * hash + Objects.hashCode(this.taxaRecISSQN);
        hash = 43 * hash + Objects.hashCode(this.codigoRais);
        hash = 43 * hash + Objects.hashCode(this.codigoFederalMunicipio);
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
        final Municipio other = (Municipio) obj;
        if (!Objects.equals(this.siglaEstado, other.siglaEstado)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.cep, other.cep)) {
            return false;
        }
        if (!Objects.equals(this.codigoMunicipio, other.codigoMunicipio)) {
            return false;
        }
        if (!Objects.equals(this.valorMinISSRetido, other.valorMinISSRetido)) {
            return false;
        }
        if (!Objects.equals(this.taxaRecISSQN, other.taxaRecISSQN)) {
            return false;
        }
        if (!Objects.equals(this.codigoRais, other.codigoRais)) {
            return false;
        }
        if (!Objects.equals(this.codigoFederalMunicipio, other.codigoFederalMunicipio)) {
            return false;
        }
        return true;
    }
}
