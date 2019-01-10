package br.com.mixfiscal.prodspedxnfe.domain.own;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

        
@Entity
@Table(name = "cfop")
public class CFOP implements Serializable {
   private static final long serialVersionUID = 8398812175162029845L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "id_cfop")
    private Integer id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;
    
    @OneToOne(optional = true, cascade = CascadeType.DETACH, orphanRemoval = false)
    @JoinTable(name = "cfop_de_para", 
               joinColumns = { @JoinColumn( name = "id_cfop_de" ) },
               inverseJoinColumns = { @JoinColumn( name = "id_cfop_para" ) })
    private CFOP CFOPEquivalente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public CFOP getCFOPEquivalente() {
        return CFOPEquivalente;
    }

    public void setCFOPEquivalente(CFOP CFOPEquivalente) {
        this.CFOPEquivalente = CFOPEquivalente;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.codigo);
        hash = 53 * hash + Objects.hashCode(this.nome);
        hash = 53 * hash + Objects.hashCode(this.descricao);
        hash = 53 * hash + Objects.hashCode(this.CFOPEquivalente);
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
        final CFOP other = (CFOP) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.CFOPEquivalente, other.CFOPEquivalente)) {
            return false;
        }
        return true;
    }
    
    
}
