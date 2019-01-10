package br.com.mixfiscal.prodspedxnfe.domain.own;

import br.com.mixfiscal.prodspedxnfe.util.Masc;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {
    private static final long serialVersionUID = -2671942453417140525L;    
    
    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "cnpj")
    private String cnpj;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "inscricao_municipal")
    private String inscricaoMunicipal;
    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;
    @Column(name = "uf")
    private String uf;
    @Column(name = "codigo_municipio")
    private Integer codigoMunicipio;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")    
    private Set<Produto> produtos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpjComMascara() {
        if (!StringUtil.isNullOrEmpty(cnpj))
            return Masc.mascararCNPJ(cnpj);
        return null;
    }
    
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpfComMascara() {
        if (!StringUtil.isNullOrEmpty(cpf))
            return Masc.mascararCPF(cpf);
        return null;
    }
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Integer getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(Integer codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.nome);
        hash = 13 * hash + Objects.hashCode(this.cnpj);
        hash = 13 * hash + Objects.hashCode(this.cpf);
        hash = 13 * hash + Objects.hashCode(this.inscricaoMunicipal);
        hash = 13 * hash + Objects.hashCode(this.inscricaoEstadual);
        hash = 13 * hash + Objects.hashCode(this.uf);
        hash = 13 * hash + Objects.hashCode(this.codigoMunicipio);
        hash = 13 * hash + Objects.hashCode(this.produtos);
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
        final Empresa other = (Empresa) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        if (!Objects.equals(this.inscricaoMunicipal, other.inscricaoMunicipal)) {
            return false;
        }
        if (!Objects.equals(this.inscricaoEstadual, other.inscricaoEstadual)) {
            return false;
        }
        if (!Objects.equals(this.uf, other.uf)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.codigoMunicipio, other.codigoMunicipio)) {
            return false;
        }
        if (!Objects.equals(this.produtos, other.produtos)) {
            return false;
        }
        return true;
    }
}
