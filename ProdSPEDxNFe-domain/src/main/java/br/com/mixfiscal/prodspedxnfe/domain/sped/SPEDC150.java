package br.com.mixfiscal.prodspedxnfe.domain.sped;

import java.io.Serializable;
import java.util.Objects;

public class SPEDC150 extends SPEDBase implements Serializable {
    private static final long serialVersionUID = -7187603842259061607L;
        
    private String codFornecedor;
    private String nome;
    private String cnpj;
    private String cpf;
    
    public SPEDC150() {
        this.getIndicesColunas().put("codFornecedor", 2);
        this.getIndicesColunas().put("nome", 3);
        this.getIndicesColunas().put("cnpj", 5);
        this.getIndicesColunas().put("cpf", 6);  
    }

    // <editor-fold desc="Getters e Setters" defaultstate="collapsed">
    public String getCodFornecedor() {
        return codFornecedor;
    }
    
    public void setCodFornecedor(String codFornecedor) {
        this.codFornecedor = codFornecedor;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    // </editor-fold>
    
    // <editor-fold desc="equals e hashCode" defaultstate="collapsed">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.codFornecedor);
        hash = 97 * hash + Objects.hashCode(this.nome);
        hash = 97 * hash + Objects.hashCode(this.cnpj);
        hash = 97 * hash + Objects.hashCode(this.cpf);
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
        final SPEDC150 other = (SPEDC150) obj;
        if (!Objects.equals(this.codFornecedor, other.codFornecedor)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        return true;
    }
    // </editor-fold>
}
