package br.com.mixfiscal.prodspedxnfe.domain.questor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ESTAB", uniqueConstraints = {
    @UniqueConstraint(columnNames = "CODIGOEMPRESA"),
    @UniqueConstraint(columnNames = "CODIGOESTAB")
})
public class Estabelecimento implements Serializable {
    private static final long serialVersionUID = 529070877716862425L;    
    
    //CODIGOEMPRESA smallint,  
    @Id
    @Column(name = "CODIGOEMPRESA")
    private Integer codigoEmpresa;
    //CODIGOESTAB smallint, 
    @Id
    @Column(name = "CODIGOESTAB")
    private Integer codigoEstabelecimento;
    //NOMEESTAB varchar(100), 
    @Column(name = "NOMEESTAB")
    private String nome;
    //NOMEESTABCOMPLETO varchar(300), 
    @Column(name = "NOMEESTABCOMPLETO")
    private String nomeCompleto;
    //NOMEFANTASIA varchar(55), 
    @Column(name = "NOMEFANTASIA")
    private String nomeFantasia;
    //APELIDOESTAB varchar(10), 
    @Column(name = "APELIDOESTAB")
    private String apelido;
    //LOGRADESTAB varchar(15), 
    @Column(name = "LOGRADESTAB")
    private String logradouro;
    //ENDERECOESTAB varchar(40), 
    @Column(name = "ENDERECOESTAB")
    private String endereco;
    //NUMENDERESTAB integer,
    @Column(name = "NUMENDERESTAB")
    private Integer numero;
    //COMPLENDERESTAB varchar(50), 
    @Column(name = "COMPLENDERESTAB")
    private String complemento;
    //BAIRROENDERESTAB varchar(30),
    @Column(name = "BAIRROENDERESTAB")
    private String bairro;
    //DATAALTERENDER date,
    @Column(name = "DATAALTERENDER")
    private Date dataAlteracaoEndereco;
    //SIGLAESTADO char(2), 
    @Column(name = "SIGLAESTADO")
    private String siglaEstado;
    //CODIGOMUNIC smallint, 
    @Column(name = "CODIGOMUNIC")
    private Short codigoMunicipio;
    //CEPENDERESTAB varchar(10), 
    @Column(name = "CEPENDERESTAB")
    private String cep;
    //DDDFONE smallint,
    @Column(name = "DDDFONE")
    private Short dddFone;
    //NUMEROFONE integer, 
    @Column(name = "NUMEROFONE")
    private Integer numeroFone;
    //DDDFAX smallint, 
    @Column(name = "DDDFAX")
    private Short dddFax;
    //NUMEROFAX integer, 
    @Column(name = "NUMEROFAX")
    private Integer numeroFax;
    //CAIXAPOSTAL integer, 
    @Column(name = "CAIXAPOSTAL")
    private Integer caixaPostal;
    //SIGLAESTADOCXP char(2), 
    @Column(name = "SIGLAESTADOCXP")
    private String siglaEstadoCaixaPostal;
    //CEPCAIXAPOSTAL varchar(10),
    @Column(name = "CEPCAIXAPOSTAL")
    private String cepCaixaPosatal;
    //EMAIL varchar(100), 
    @Column(name = "EMAIL")
    private String email;
    //PAGINAINTERNET varchar(100), 
    @Column(name = "PAGINAINTERNET")
    private String paginaInternet;
    //DATAINICIOATIV date, 
    @Column(name = "DATAINICIOATIV")
    private Date dataInicioAtividades;
    //DATAENCERATIV date, 
    @Column(name = "DATAENCERATIV")
    private Date dataEncerramentoAtividades;
    //SOCIEDADECONTAPARTICIPACAO char(1), 
    @Column(name = "SOCIEDADECONTAPARTICIPACAO")
    private Character sociedadeContaParticipacao;
    //INSCRICAOSCP varchar(14), 
    @Column(name = "INSCRICAOSCP")
    private String inscricaoSCP;
    //TIPOINSCR smallint, 
    @Column(name = "TIPOINSCR")
    private Short tipoInscricao;
    //INSCRFEDERAL varchar(18), 
    @Column(name = "INSCRFEDERAL")
    private String inscricaoFederal;    
    //SUFRAMA varchar(9), 
    @Column(name = "SUFRAMA")
    private String suframa;
    //CODIGONATURJURID smallint, 
    @Column(name = "CODIGONATURJURID")
    private Short codigoNaturezaJuridica;
    //CODIGOATIVFEDERAL varchar(9),
    @Column(name = "CODIGOATIVFEDERAL")
    private String codigoAtividadeFederal;
    //DESCRATIVFEDESTAB varchar(200),
    @Column(name = "DESCRATIVFEDESTAB")
    private String descricaoAtividadeFederal;
    //DATAALTERATIVFED date, 
    @Column(name = "DATAALTERATIVFED")
    private Date dataAlteracaoAtividadeFederal;
    //TIPOREGIST smallint, 
    @Column(name = "TIPOREGIST")
    private Short tipoRegistro;
    //NUMEROREGIST varchar(50),
    @Column(name = "NUMEROREGIST")
    private String numeroRegistro;
    //DATAREGIST date, 
    @Column(name = "DATAREGIST")
    private Date dataRegistro;
    //OBSERVREGIST varchar(150), 
    @Column(name = "OBSERVREGIST")
    private String observacoesRegistro;
    //INSCRESTAD varchar(25), 
    @Column(name = "INSCRESTAD")
    private String inscricaoEstadual;
    //CODIGOATIVESTAD varchar(25), 
    @Column(name = "CODIGOATIVESTAD")
    private String codigoAtividadeEstadual;
    //DESCRATIVESTESTAB varchar(100),
    @Column(name = "DESCRATIVESTESTAB")
    private String descricaoAtividadeEstadual;
    //INSCRMUNIC varchar(25), 
    @Column(name = "INSCRMUNIC")
    private String inscricaoMunicipal;
    //CODIGOATIVMUNIC varchar(25), 
    @Column(name = "CODIGOATIVMUNIC")
    private String codigoAtividadeMunicipal;
    //INSCRIMOBILIARIA varchar(19),
    @Column(name = "INSCRIMOBILIARIA")
    private String inscricaoImobiliaria;
    //ESPECIEESTAB varchar(30), 
    @Column(name = "ESPECIEESTAB")
    private String especieEstabelecimento;
    //INSCRBANCOCENTRAL varchar(20),
    @Column(name = "INSCRBANCOCENTRAL")
    private String inscricaoBancoCentral;
    //INSCRSUSEP varchar(20), 
    @Column(name = "INSCRSUSEP")
    private String inscricaoSUSEP;
    //DESCRATIVMUNESTAB varchar(100),
    @Column(name = "DESCRATIVMUNESTAB")
    private String descricaoAtividadeMunicipal;
    //PORTEEMPRESA smallint,
    @Column(name = "PORTEEMPRESA")
    private Short porteEmpresa;
    //INSCRCVM varchar(20), 
    @Column(name = "INSCRCVM")
    private String inscricaoCVM;
    //CODIGOTABFERIADO smallint,
    @Column(name = "CODIGOTABFERIADO")
    private Short codigoTabFeriado;
    //VALORNOMINALCOTAS numeric(14, 2),
    @Column(name = "VALORNOMINALCOTAS")
    private BigDecimal valorNominalCotas;
    //CAEPF integer, 
    @Column(name = "CAEPF")
    private Integer CAEPF;
    //NOMEAUDITOR varchar(100), 
    @Column(name = "NOMEAUDITOR")
    private String nomeAuditor;
    //CVMAUDITOR varchar(50), 
    @Column(name = "CVMAUDITOR")
    private String cvmAuditor;
    //CAPITALSOCIAL numeric(14, 2),
    @Column(name = "CAPITALSOCIAL")
    private BigDecimal capitalSocial;
    //CONSTRAINT PKESTAB PRIMARY KEY (CODIGOEMPRESA, CODIGOESTAB)
    @OneToOne
    @JoinColumn(name = "CODIGOEMPRESA", insertable = false, updatable = false)
    private EmpresaQuestor empresa;

    public Integer getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(Integer codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public Integer getCodigoEstabelecimento() {
        return codigoEstabelecimento;
    }

    public void setCodigoEstabelecimento(Integer codigoEstabelecimento) {
        this.codigoEstabelecimento = codigoEstabelecimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
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

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
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

    public Date getDataAlteracaoEndereco() {
        return dataAlteracaoEndereco;
    }

    public void setDataAlteracaoEndereco(Date dataAlteracaoEndereco) {
        this.dataAlteracaoEndereco = dataAlteracaoEndereco;
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

    public Short getDddFax() {
        return dddFax;
    }

    public void setDddFax(Short dddFax) {
        this.dddFax = dddFax;
    }

    public Integer getNumeroFax() {
        return numeroFax;
    }

    public void setNumeroFax(Integer numeroFax) {
        this.numeroFax = numeroFax;
    }

    public Integer getCaixaPostal() {
        return caixaPostal;
    }

    public void setCaixaPostal(Integer caixaPostal) {
        this.caixaPostal = caixaPostal;
    }

    public String getSiglaEstadoCaixaPostal() {
        return siglaEstadoCaixaPostal;
    }

    public void setSiglaEstadoCaixaPostal(String siglaEstadoCaixaPostal) {
        this.siglaEstadoCaixaPostal = siglaEstadoCaixaPostal;
    }

    public String getCepCaixaPosatal() {
        return cepCaixaPosatal;
    }

    public void setCepCaixaPosatal(String cepCaixaPosatal) {
        this.cepCaixaPosatal = cepCaixaPosatal;
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

    public Date getDataInicioAtividades() {
        return dataInicioAtividades;
    }

    public void setDataInicioAtividades(Date dataInicioAtividades) {
        this.dataInicioAtividades = dataInicioAtividades;
    }

    public Date getDataEncerramentoAtividades() {
        return dataEncerramentoAtividades;
    }

    public void setDataEncerramentoAtividades(Date dataEncerramentoAtividades) {
        this.dataEncerramentoAtividades = dataEncerramentoAtividades;
    }

    public Character getSociedadeContaParticipacao() {
        return sociedadeContaParticipacao;
    }

    public void setSociedadeContaParticipacao(Character sociedadeContaParticipacao) {
        this.sociedadeContaParticipacao = sociedadeContaParticipacao;
    }

    public String getInscricaoSCP() {
        return inscricaoSCP;
    }

    public void setInscricaoSCP(String inscricaoSCP) {
        this.inscricaoSCP = inscricaoSCP;
    }

    public Short getTipoInscricao() {
        return tipoInscricao;
    }

    public void setTipoInscricao(Short tipoInscricao) {
        this.tipoInscricao = tipoInscricao;
    }

    public String getInscricaoFederal() {
        return inscricaoFederal;
    }

    public void setInscricaoFederal(String inscricaoFederal) {
        this.inscricaoFederal = inscricaoFederal;
    }

    public String getSuframa() {
        return suframa;
    }

    public void setSuframa(String suframa) {
        this.suframa = suframa;
    }

    public Short getCodigoNaturezaJuridica() {
        return codigoNaturezaJuridica;
    }

    public void setCodigoNaturezaJuridica(Short codigoNaturezaJuridica) {
        this.codigoNaturezaJuridica = codigoNaturezaJuridica;
    }

    public String getCodigoAtividadeFederal() {
        return codigoAtividadeFederal;
    }

    public void setCodigoAtividadeFederal(String codigoAtividadeFederal) {
        this.codigoAtividadeFederal = codigoAtividadeFederal;
    }

    public String getDescricaoAtividadeFederal() {
        return descricaoAtividadeFederal;
    }

    public void setDescricaoAtividadeFederal(String descricaoAtividadeFederal) {
        this.descricaoAtividadeFederal = descricaoAtividadeFederal;
    }

    public Date getDataAlteracaoAtividadeFederal() {
        return dataAlteracaoAtividadeFederal;
    }

    public void setDataAlteracaoAtividadeFederal(Date dataAlteracaoAtividadeFederal) {
        this.dataAlteracaoAtividadeFederal = dataAlteracaoAtividadeFederal;
    }

    public Short getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(Short tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getObservacoesRegistro() {
        return observacoesRegistro;
    }

    public void setObservacoesRegistro(String observacoesRegistro) {
        this.observacoesRegistro = observacoesRegistro;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getCodigoAtividadeEstadual() {
        return codigoAtividadeEstadual;
    }

    public void setCodigoAtividadeEstadual(String codigoAtividadeEstadual) {
        this.codigoAtividadeEstadual = codigoAtividadeEstadual;
    }

    public String getDescricaoAtividadeEstadual() {
        return descricaoAtividadeEstadual;
    }

    public void setDescricaoAtividadeEstadual(String descricaoAtividadeEstadual) {
        this.descricaoAtividadeEstadual = descricaoAtividadeEstadual;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getCodigoAtividadeMunicipal() {
        return codigoAtividadeMunicipal;
    }

    public void setCodigoAtividadeMunicipal(String codigoAtividadeMunicipal) {
        this.codigoAtividadeMunicipal = codigoAtividadeMunicipal;
    }

    public String getInscricaoImobiliaria() {
        return inscricaoImobiliaria;
    }

    public void setInscricaoImobiliaria(String inscricaoImobiliaria) {
        this.inscricaoImobiliaria = inscricaoImobiliaria;
    }

    public String getEspecieEstabelecimento() {
        return especieEstabelecimento;
    }

    public void setEspecieEstabelecimento(String especieEstabelecimento) {
        this.especieEstabelecimento = especieEstabelecimento;
    }

    public String getInscricaoBancoCentral() {
        return inscricaoBancoCentral;
    }

    public void setInscricaoBancoCentral(String inscricaoBancoCentral) {
        this.inscricaoBancoCentral = inscricaoBancoCentral;
    }

    public String getInscricaoSUSEP() {
        return inscricaoSUSEP;
    }

    public void setInscricaoSUSEP(String inscricaoSUSEP) {
        this.inscricaoSUSEP = inscricaoSUSEP;
    }

    public String getDescricaoAtividadeMunicipal() {
        return descricaoAtividadeMunicipal;
    }

    public void setDescricaoAtividadeMunicipal(String descricaoAtividadeMunicipal) {
        this.descricaoAtividadeMunicipal = descricaoAtividadeMunicipal;
    }

    public Short getPorteEmpresa() {
        return porteEmpresa;
    }

    public void setPorteEmpresa(Short porteEmpresa) {
        this.porteEmpresa = porteEmpresa;
    }

    public String getInscricaoCVM() {
        return inscricaoCVM;
    }

    public void setInscricaoCVM(String inscricaoCVM) {
        this.inscricaoCVM = inscricaoCVM;
    }

    public Short getCodigoTabFeriado() {
        return codigoTabFeriado;
    }

    public void setCodigoTabFeriado(Short codigoTabFeriado) {
        this.codigoTabFeriado = codigoTabFeriado;
    }

    public BigDecimal getValorNominalCotas() {
        return valorNominalCotas;
    }

    public void setValorNominalCotas(BigDecimal valorNominalCotas) {
        this.valorNominalCotas = valorNominalCotas;
    }

    public Integer getCAEPF() {
        return CAEPF;
    }

    public void setCAEPF(Integer CAEPF) {
        this.CAEPF = CAEPF;
    }

    public String getNomeAuditor() {
        return nomeAuditor;
    }

    public void setNomeAuditor(String nomeAuditor) {
        this.nomeAuditor = nomeAuditor;
    }

    public String getCvmAuditor() {
        return cvmAuditor;
    }

    public void setCvmAuditor(String cvmAuditor) {
        this.cvmAuditor = cvmAuditor;
    }

    public BigDecimal getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(BigDecimal capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public EmpresaQuestor getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaQuestor empresa) {
        this.empresa = empresa;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.codigoEmpresa);
        hash = 17 * hash + Objects.hashCode(this.codigoEstabelecimento);
        hash = 17 * hash + Objects.hashCode(this.nome);
        hash = 17 * hash + Objects.hashCode(this.nomeCompleto);
        hash = 17 * hash + Objects.hashCode(this.nomeFantasia);
        hash = 17 * hash + Objects.hashCode(this.apelido);
        hash = 17 * hash + Objects.hashCode(this.logradouro);
        hash = 17 * hash + Objects.hashCode(this.endereco);
        hash = 17 * hash + Objects.hashCode(this.numero);
        hash = 17 * hash + Objects.hashCode(this.complemento);
        hash = 17 * hash + Objects.hashCode(this.bairro);
        hash = 17 * hash + Objects.hashCode(this.dataAlteracaoEndereco);
        hash = 17 * hash + Objects.hashCode(this.siglaEstado);
        hash = 17 * hash + Objects.hashCode(this.codigoMunicipio);
        hash = 17 * hash + Objects.hashCode(this.cep);
        hash = 17 * hash + Objects.hashCode(this.dddFone);
        hash = 17 * hash + Objects.hashCode(this.numeroFone);
        hash = 17 * hash + Objects.hashCode(this.dddFax);
        hash = 17 * hash + Objects.hashCode(this.numeroFax);
        hash = 17 * hash + Objects.hashCode(this.caixaPostal);
        hash = 17 * hash + Objects.hashCode(this.siglaEstadoCaixaPostal);
        hash = 17 * hash + Objects.hashCode(this.cepCaixaPosatal);
        hash = 17 * hash + Objects.hashCode(this.email);
        hash = 17 * hash + Objects.hashCode(this.paginaInternet);
        hash = 17 * hash + Objects.hashCode(this.dataInicioAtividades);
        hash = 17 * hash + Objects.hashCode(this.dataEncerramentoAtividades);
        hash = 17 * hash + Objects.hashCode(this.sociedadeContaParticipacao);
        hash = 17 * hash + Objects.hashCode(this.inscricaoSCP);
        hash = 17 * hash + Objects.hashCode(this.tipoInscricao);
        hash = 17 * hash + Objects.hashCode(this.inscricaoFederal);
        hash = 17 * hash + Objects.hashCode(this.suframa);
        hash = 17 * hash + Objects.hashCode(this.codigoNaturezaJuridica);
        hash = 17 * hash + Objects.hashCode(this.codigoAtividadeFederal);
        hash = 17 * hash + Objects.hashCode(this.descricaoAtividadeFederal);
        hash = 17 * hash + Objects.hashCode(this.dataAlteracaoAtividadeFederal);
        hash = 17 * hash + Objects.hashCode(this.tipoRegistro);
        hash = 17 * hash + Objects.hashCode(this.numeroRegistro);
        hash = 17 * hash + Objects.hashCode(this.dataRegistro);
        hash = 17 * hash + Objects.hashCode(this.observacoesRegistro);
        hash = 17 * hash + Objects.hashCode(this.inscricaoEstadual);
        hash = 17 * hash + Objects.hashCode(this.codigoAtividadeEstadual);
        hash = 17 * hash + Objects.hashCode(this.descricaoAtividadeEstadual);
        hash = 17 * hash + Objects.hashCode(this.inscricaoMunicipal);
        hash = 17 * hash + Objects.hashCode(this.codigoAtividadeMunicipal);
        hash = 17 * hash + Objects.hashCode(this.inscricaoImobiliaria);
        hash = 17 * hash + Objects.hashCode(this.especieEstabelecimento);
        hash = 17 * hash + Objects.hashCode(this.inscricaoBancoCentral);
        hash = 17 * hash + Objects.hashCode(this.inscricaoSUSEP);
        hash = 17 * hash + Objects.hashCode(this.descricaoAtividadeMunicipal);
        hash = 17 * hash + Objects.hashCode(this.porteEmpresa);
        hash = 17 * hash + Objects.hashCode(this.inscricaoCVM);
        hash = 17 * hash + Objects.hashCode(this.codigoTabFeriado);
        hash = 17 * hash + Objects.hashCode(this.valorNominalCotas);
        hash = 17 * hash + Objects.hashCode(this.CAEPF);
        hash = 17 * hash + Objects.hashCode(this.nomeAuditor);
        hash = 17 * hash + Objects.hashCode(this.cvmAuditor);
        hash = 17 * hash + Objects.hashCode(this.capitalSocial);
        hash = 17 * hash + Objects.hashCode(this.empresa);
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
        final Estabelecimento other = (Estabelecimento) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.nomeCompleto, other.nomeCompleto)) {
            return false;
        }
        if (!Objects.equals(this.nomeFantasia, other.nomeFantasia)) {
            return false;
        }
        if (!Objects.equals(this.apelido, other.apelido)) {
            return false;
        }
        if (!Objects.equals(this.logradouro, other.logradouro)) {
            return false;
        }
        if (!Objects.equals(this.endereco, other.endereco)) {
            return false;
        }
        if (!Objects.equals(this.complemento, other.complemento)) {
            return false;
        }
        if (!Objects.equals(this.bairro, other.bairro)) {
            return false;
        }
        if (!Objects.equals(this.siglaEstado, other.siglaEstado)) {
            return false;
        }
        if (!Objects.equals(this.cep, other.cep)) {
            return false;
        }
        if (!Objects.equals(this.siglaEstadoCaixaPostal, other.siglaEstadoCaixaPostal)) {
            return false;
        }
        if (!Objects.equals(this.cepCaixaPosatal, other.cepCaixaPosatal)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.paginaInternet, other.paginaInternet)) {
            return false;
        }
        if (!Objects.equals(this.inscricaoSCP, other.inscricaoSCP)) {
            return false;
        }
        if (!Objects.equals(this.inscricaoFederal, other.inscricaoFederal)) {
            return false;
        }
        if (!Objects.equals(this.suframa, other.suframa)) {
            return false;
        }
        if (!Objects.equals(this.codigoAtividadeFederal, other.codigoAtividadeFederal)) {
            return false;
        }
        if (!Objects.equals(this.descricaoAtividadeFederal, other.descricaoAtividadeFederal)) {
            return false;
        }
        if (!Objects.equals(this.numeroRegistro, other.numeroRegistro)) {
            return false;
        }
        if (!Objects.equals(this.observacoesRegistro, other.observacoesRegistro)) {
            return false;
        }
        if (!Objects.equals(this.inscricaoEstadual, other.inscricaoEstadual)) {
            return false;
        }
        if (!Objects.equals(this.codigoAtividadeEstadual, other.codigoAtividadeEstadual)) {
            return false;
        }
        if (!Objects.equals(this.descricaoAtividadeEstadual, other.descricaoAtividadeEstadual)) {
            return false;
        }
        if (!Objects.equals(this.inscricaoMunicipal, other.inscricaoMunicipal)) {
            return false;
        }
        if (!Objects.equals(this.codigoAtividadeMunicipal, other.codigoAtividadeMunicipal)) {
            return false;
        }
        if (!Objects.equals(this.inscricaoImobiliaria, other.inscricaoImobiliaria)) {
            return false;
        }
        if (!Objects.equals(this.especieEstabelecimento, other.especieEstabelecimento)) {
            return false;
        }
        if (!Objects.equals(this.inscricaoBancoCentral, other.inscricaoBancoCentral)) {
            return false;
        }
        if (!Objects.equals(this.inscricaoSUSEP, other.inscricaoSUSEP)) {
            return false;
        }
        if (!Objects.equals(this.descricaoAtividadeMunicipal, other.descricaoAtividadeMunicipal)) {
            return false;
        }
        if (!Objects.equals(this.inscricaoCVM, other.inscricaoCVM)) {
            return false;
        }
        if (!Objects.equals(this.nomeAuditor, other.nomeAuditor)) {
            return false;
        }
        if (!Objects.equals(this.cvmAuditor, other.cvmAuditor)) {
            return false;
        }
        if (!Objects.equals(this.codigoEmpresa, other.codigoEmpresa)) {
            return false;
        }
        if (!Objects.equals(this.codigoEstabelecimento, other.codigoEstabelecimento)) {
            return false;
        }
        if (!Objects.equals(this.numero, other.numero)) {
            return false;
        }
        if (!Objects.equals(this.dataAlteracaoEndereco, other.dataAlteracaoEndereco)) {
            return false;
        }
        if (!Objects.equals(this.codigoMunicipio, other.codigoMunicipio)) {
            return false;
        }
        if (!Objects.equals(this.dddFone, other.dddFone)) {
            return false;
        }
        if (!Objects.equals(this.numeroFone, other.numeroFone)) {
            return false;
        }
        if (!Objects.equals(this.dddFax, other.dddFax)) {
            return false;
        }
        if (!Objects.equals(this.numeroFax, other.numeroFax)) {
            return false;
        }
        if (!Objects.equals(this.caixaPostal, other.caixaPostal)) {
            return false;
        }
        if (!Objects.equals(this.dataInicioAtividades, other.dataInicioAtividades)) {
            return false;
        }
        if (!Objects.equals(this.dataEncerramentoAtividades, other.dataEncerramentoAtividades)) {
            return false;
        }
        if (!Objects.equals(this.sociedadeContaParticipacao, other.sociedadeContaParticipacao)) {
            return false;
        }
        if (!Objects.equals(this.tipoInscricao, other.tipoInscricao)) {
            return false;
        }
        if (!Objects.equals(this.codigoNaturezaJuridica, other.codigoNaturezaJuridica)) {
            return false;
        }
        if (!Objects.equals(this.dataAlteracaoAtividadeFederal, other.dataAlteracaoAtividadeFederal)) {
            return false;
        }
        if (!Objects.equals(this.tipoRegistro, other.tipoRegistro)) {
            return false;
        }
        if (!Objects.equals(this.dataRegistro, other.dataRegistro)) {
            return false;
        }
        if (!Objects.equals(this.porteEmpresa, other.porteEmpresa)) {
            return false;
        }
        if (!Objects.equals(this.codigoTabFeriado, other.codigoTabFeriado)) {
            return false;
        }
        if (!Objects.equals(this.valorNominalCotas, other.valorNominalCotas)) {
            return false;
        }
        if (!Objects.equals(this.CAEPF, other.CAEPF)) {
            return false;
        }
        if (!Objects.equals(this.capitalSocial, other.capitalSocial)) {
            return false;
        }
        if (!Objects.equals(this.empresa, other.empresa)) {
            return false;
        }
        return true;
    }
    
    
}
