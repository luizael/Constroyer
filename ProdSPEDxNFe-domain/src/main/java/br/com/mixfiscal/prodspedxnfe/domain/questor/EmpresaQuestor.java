package br.com.mixfiscal.prodspedxnfe.domain.questor;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EMPRESA")
public class EmpresaQuestor implements Serializable {
    private static final long serialVersionUID = 1481440582603558340L;    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODIGOEMPRESA")
    private Integer codigo;
    @Column(name="NOMEEMPRESA")
    private String nome;
    @Column(name = "LOGOTIPOEMPRESA")
    private String logotipo;
    @OneToOne(mappedBy = "empresa")
    private Estabelecimento estabelecimento;

    public EmpresaQuestor() {
        this.estabelecimento = new Estabelecimento();
    }
    
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(String logotipo) {
        this.logotipo = logotipo;
    }   

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.codigo);
        hash = 41 * hash + Objects.hashCode(this.nome);
        hash = 41 * hash + Objects.hashCode(this.logotipo);
        hash = 41 * hash + Objects.hashCode(this.estabelecimento);
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
        final EmpresaQuestor other = (EmpresaQuestor) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.logotipo, other.logotipo)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.estabelecimento, other.estabelecimento)) {
            return false;
        }
        return true;
    }
    
    
}
