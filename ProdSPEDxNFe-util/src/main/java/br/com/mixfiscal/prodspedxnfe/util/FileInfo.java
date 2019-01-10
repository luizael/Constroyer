
package br.com.mixfiscal.prodspedxnfe.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;


public class FileInfo implements Serializable {
    private static final long serialVersionUID = -5180151819044013067L;    
    private String nome;
    private String tamanho;
    private String localArmazenamento;
    private byte[] bytes;

    public String getLocalArmazenamento() {
        return localArmazenamento;
    }

    public void setLocalArmazenamento(String localArmazenamento) {
        this.localArmazenamento = localArmazenamento;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.nome);
        hash = 59 * hash + Objects.hashCode(this.tamanho);
        hash = 59 * hash + Objects.hashCode(this.localArmazenamento);
        hash = 59 * hash + Arrays.hashCode(this.bytes);
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
        final FileInfo other = (FileInfo) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.tamanho, other.tamanho)) {
            return false;
        }
        if (!Objects.equals(this.localArmazenamento, other.localArmazenamento)) {
            return false;
        }
        if (!Arrays.equals(this.bytes, other.bytes)) {
            return false;
        }
        return true;
    }

}
