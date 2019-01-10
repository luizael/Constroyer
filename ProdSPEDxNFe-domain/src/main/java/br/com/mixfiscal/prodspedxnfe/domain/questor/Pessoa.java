package br.com.mixfiscal.prodspedxnfe.domain.questor;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PESSOA")
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 5935960478456016892L;    
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PessoaSeq")
    @GenericGenerator(name = "PessoaSeq", strategy = "br.com.mixfiscal.prodspedxnfe.dao.questor.seq.PessoaCodigoSeq")
    @Column(name = "CODIGOPESSOA")    
    private Integer codigo;
    @Column(name = "NOMEPESSOA")
    private String nome;
    @Column(name = "TIPOINSCR")
    private Integer tipoInscr;
    @Column(name = "INSCRFEDERAL")
    private String inscrFederal;
    @Column(name = "SUFRAMA")
    private String suframa;
    @Column(name = "SEQUENCIA")
    private Short sequencia;
    @Column(name = "LOGRADPESSOA") 
    private String logradouro;
    @Column(name = "ENDERECOPESSOA")
    private String endereco;
    @Column(name = "NUMENDERPESSOA")
    private Integer numEndereco;
    @Column(name = "COMPLENDERPESSOA")
    private String complemento;
    @Column(name = "BAIRROENDERPESSOA")
    private String bairro;
    @Column(name = "SIGLAESTADO")
    private String siglaEstado;
    @Column(name = "CODIGOMUNIC")
    private Short codigoMunicipio;
    @Column(name = "CEPENDERPESSOA")
    private String cep;
    @Column(name = "DDDFONE")
    private Short dddFone;
    @Column(name = "NUMEROFONE")
    private Integer numeroFone;
    @Column(name = "PRODUTRURAL")
    private Character produtoRural;
    @Column(name = "INSCRPROD")
    private String inscrProd;
    @Column(name = "INSCRESTAD")
    private String inscrEstad;
    @Column(name = "INSCRMUNIC")
    private String inscrMunic;
    @Column(name = "CODIGOATIVFEDERAL")
    private String codigoAtivFederal;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PAGINAINTERNET")
    private String paginaInternet;
    @Column(name = "FORMADUPLICATAPARCELA")
    private Short formaDuplicataParcela;
    @Column(name = "CODIGOUSUARIO")
    private Short codigoUsuario;
    @Column(name = "DATAHORALCTO")
    private Date dataHoraLcto;    
    @Column(name = "ORIGEMDADO")
    private Short origemDado;
    
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

    public Integer getTipoInscr() {
        return tipoInscr;
    }

    public void setTipoInscr(Integer tipoInscr) {
        this.tipoInscr = tipoInscr;
    }

    public String getInscrFederal() {
        return inscrFederal;
    }

    public void setInscrFederal(String inscrFederal) {
        this.inscrFederal = inscrFederal;
    }

    public String getSuframa() {
        return suframa;
    }

    public void setSuframa(String suframa) {
        this.suframa = suframa;
    }

    public Short getSequencia() {
        return sequencia;
    }

    public void setSequencia(Short sequencia) {
        this.sequencia = sequencia;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getNumEndereco() {
        return numEndereco;
    }

    public void setNumEndereco(Integer numEndereco) {
        this.numEndereco = numEndereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public Short getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(Short codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Short getDddFone() {
        return dddFone;
    }

    public void setDddFone(Short dddFone) {
        this.dddFone = dddFone;
    }

    public Integer getNumeroFone() {
        return numeroFone;
    }

    public void setNumeroFone(Integer numeroFone) {
        this.numeroFone = numeroFone;
    }

    public Character getProdutoRural() {
        return produtoRural;
    }

    public void setProdutoRural(Character produtoRural) {
        this.produtoRural = produtoRural;
    }

    public String getInscrProd() {
        return inscrProd;
    }

    public void setInscrProd(String inscrProd) {
        this.inscrProd = inscrProd;
    }

    public String getInscrEstad() {
        return inscrEstad;
    }

    public void setInscrEstad(String inscrEstad) {
        this.inscrEstad = inscrEstad;
    }

    public String getInscrMunic() {
        return inscrMunic;
    }

    public void setInscrMunic(String inscrMunic) {
        this.inscrMunic = inscrMunic;
    }

    public String getCodigoAtivFederal() {
        return codigoAtivFederal;
    }

    public void setCodigoAtivFederal(String codigoAtivFederal) {
        this.codigoAtivFederal = codigoAtivFederal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaginaInternet() {
        return paginaInternet;
    }

    public void setPaginaInternet(String paginaInternet) {
        this.paginaInternet = paginaInternet;
    }

    public Short getFormaDuplicataParcela() {
        return formaDuplicataParcela;
    }

    public void setFormaDuplicataParcela(Short formaDuplicataParcela) {
        this.formaDuplicataParcela = formaDuplicataParcela;
    }

    public Short getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Short codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public Date getDataHoraLcto() {
        return dataHoraLcto;
    }

    public void setDataHoraLcto(Date dataHoraLcto) {
        this.dataHoraLcto = dataHoraLcto;
    }

    public Short getOrigemDado() {
        return origemDado;
    }

    public void setOrigemDado(Short origemDado) {
        this.origemDado = origemDado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.codigo;
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
        final Pessoa other = (Pessoa) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
        return true;
    }
    
    
}
