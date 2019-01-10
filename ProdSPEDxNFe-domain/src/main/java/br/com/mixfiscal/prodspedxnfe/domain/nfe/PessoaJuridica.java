package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.util.Objects;

public class PessoaJuridica implements Serializable {
    private static final long serialVersionUID = -9024438299850952182L;    
    
    private String CNPJ;
    private String razaoSocial;
    private String nomeFantasia;
    private String logrdouro;
    private int numero;
    private String bairro;
    private int codigoMunicipio;
    private String nomeMunicipio;
    private String siglaUF;
    private String cep;
    private int codigoPais;
    private String nomePais;
    private String IE;
    private String IM;
    private int CNAE;
    private int CRT;
    private int indiIEDest;        
    private String email;

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getLogrdouro() {
        return logrdouro;
    }

    public void setLogrdouro(String logrdouro) {
        this.logrdouro = logrdouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(int codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getNomeMunicipio() {
        return nomeMunicipio;
    }

    public void setNomeMunicipio(String nomeMunicipio) {
        this.nomeMunicipio = nomeMunicipio;
    }

    public String getSiglaUF() {
        return siglaUF;
    }

    public void setSiglaUF(String siglaUF) {
        this.siglaUF = siglaUF;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(int codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getNomePais() {
        return nomePais;
    }

    public void setNomePais(String nomePais) {
        this.nomePais = nomePais;
    }

    public String getIE() {
        return IE;
    }

    public void setIE(String IE) {
        this.IE = IE;
    }

    public String getIM() {
        return IM;
    }

    public void setIM(String IM) {
        this.IM = IM;
    }

    public int getCNAE() {
        return CNAE;
    }

    public void setCNAE(int CNAE) {
        this.CNAE = CNAE;
    }

    public int getCRT() {
        return CRT;
    }

    public void setCRT(int CRT) {
        this.CRT = CRT;
    }

    public int getIndiIEDest() {
        return indiIEDest;
    }

    public void setIndiIEDest(int indiIEDest) {
        this.indiIEDest = indiIEDest;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.CNPJ);
        hash = 83 * hash + Objects.hashCode(this.razaoSocial);
        hash = 83 * hash + Objects.hashCode(this.nomeFantasia);
        hash = 83 * hash + Objects.hashCode(this.logrdouro);
        hash = 83 * hash + this.numero;
        hash = 83 * hash + Objects.hashCode(this.bairro);
        hash = 83 * hash + this.codigoMunicipio;
        hash = 83 * hash + Objects.hashCode(this.nomeMunicipio);
        hash = 83 * hash + Objects.hashCode(this.siglaUF);
        hash = 83 * hash + Objects.hashCode(this.cep);
        hash = 83 * hash + this.codigoPais;
        hash = 83 * hash + Objects.hashCode(this.nomePais);
        hash = 83 * hash + Objects.hashCode(this.IE);
        hash = 83 * hash + Objects.hashCode(this.IM);
        hash = 83 * hash + this.CNAE;
        hash = 83 * hash + this.CRT;
        hash = 83 * hash + this.indiIEDest;
        hash = 83 * hash + Objects.hashCode(this.email);
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
        final PessoaJuridica other = (PessoaJuridica) obj;
        if (this.numero != other.numero) {
            return false;
        }
        if (this.codigoMunicipio != other.codigoMunicipio) {
            return false;
        }
        if (this.codigoPais != other.codigoPais) {
            return false;
        }
        if (this.CNAE != other.CNAE) {
            return false;
        }
        if (this.CRT != other.CRT) {
            return false;
        }
        if (this.indiIEDest != other.indiIEDest) {
            return false;
        }
        if (!Objects.equals(this.CNPJ, other.CNPJ)) {
            return false;
        }
        if (!Objects.equals(this.razaoSocial, other.razaoSocial)) {
            return false;
        }
        if (!Objects.equals(this.nomeFantasia, other.nomeFantasia)) {
            return false;
        }
        if (!Objects.equals(this.logrdouro, other.logrdouro)) {
            return false;
        }
        if (!Objects.equals(this.bairro, other.bairro)) {
            return false;
        }
        if (!Objects.equals(this.nomeMunicipio, other.nomeMunicipio)) {
            return false;
        }
        if (!Objects.equals(this.siglaUF, other.siglaUF)) {
            return false;
        }
        if (!Objects.equals(this.cep, other.cep)) {
            return false;
        }
        if (!Objects.equals(this.nomePais, other.nomePais)) {
            return false;
        }
        if (!Objects.equals(this.IE, other.IE)) {
            return false;
        }
        if (!Objects.equals(this.IM, other.IM)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }
}
