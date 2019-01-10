package br.com.mixfiscal.prodspedxnfe.domain.own;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;

@Entity
@Table(name="cliente")
public class Cliente implements Serializable  {
    private static final long serialVersionUID = -1054814991720976009L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cliente")
    private int id;
    
    @Column(name="nome")
    private String nome;
    
    @Column(name="token")
    private String token;
    
    @Column(name="cnpj")
    private String cnpj;
    
    @Column(name="tipo_operacao")
    private int operacao;
        
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente" )    
    private List<RequisicaoAtualizacaoInfoFiscal> requisicaoAtualizacaoInfoFiscal;
        
    //@OneToOne(fetch = FetchType.LAZY, mappedBy = "cliente")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<ClassificacaoTributaria> classificacaoTributaria;
    
    @Column(name="id_view")
    private Integer idView;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public int getOperacao() {
        return operacao;
    }

    public void setOperacao(int operacao) {
        this.operacao = operacao;
    }

    public List<RequisicaoAtualizacaoInfoFiscal> getRequisicaoAtualizacaoInfoFiscal() {
        return requisicaoAtualizacaoInfoFiscal;
    }

    public void setRequisicaoAtualizacaoInfoFiscal(List<RequisicaoAtualizacaoInfoFiscal> requisicaoAtualizacaoInfoFiscal) {
        this.requisicaoAtualizacaoInfoFiscal = requisicaoAtualizacaoInfoFiscal;
    }

    public List<ClassificacaoTributaria> getClassificacaoTributaria() {
        return classificacaoTributaria;
    }

    public void setClassificacaoTributaria(List<ClassificacaoTributaria> classificacaoTributaria) {
        this.classificacaoTributaria = classificacaoTributaria;
    }
 
    public Integer getIdView() {
        return idView;
    }

    public void setIdView(Integer idView) {
        this.idView = idView;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode(this.nome);
        hash = 53 * hash + Objects.hashCode(this.token);
        hash = 53 * hash + Objects.hashCode(this.cnpj);
        hash = 53 * hash + this.operacao;
        hash = 53 * hash + Objects.hashCode(this.requisicaoAtualizacaoInfoFiscal);
        hash = 53 * hash + Objects.hashCode(this.classificacaoTributaria);
        hash = 53 * hash + Objects.hashCode(this.idView);
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
        final Cliente other = (Cliente) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.operacao != other.operacao) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.token, other.token)) {
            return false;
        }
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        if (!Objects.equals(this.idView, other.idView)) {
            return false;
        }
        if (!Objects.equals(this.requisicaoAtualizacaoInfoFiscal, other.requisicaoAtualizacaoInfoFiscal)) {
            return false;
        }
        if (!Objects.equals(this.classificacaoTributaria, other.classificacaoTributaria)) {
            return false;
        }
        return true;
    }
    
}
