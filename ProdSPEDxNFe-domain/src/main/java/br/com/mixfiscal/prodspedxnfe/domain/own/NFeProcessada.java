package br.com.mixfiscal.prodspedxnfe.domain.own;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "nfe_processada")
public class NFeProcessada implements Serializable {
    private static final long serialVersionUID = -3371367862398638998L;    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nfe_processada")
    private Integer idNFeProcessada;
    @Column(name="chave")
    private String chave;
    @Column(name = "numero_nota_fiscal")
    private String numeroNotaFiscal;
    @Column(name = "nome_emitente")
    private String nomeEmitente;
    @Column(name = "data_hora_emissao")
    private Timestamp dataHoraEmissao;
    @Column(name = "valor_total_nota_fiscal")
    private BigDecimal valorTotalNotaFiscal;
    @Column(name = "data_hora_inclusao")
    private Timestamp dataHoraInclusao;
    @Transient
    private Timestamp dataHoraEmissaoInicio;
    @Transient
    private Timestamp dataHoraEmissaoFim;
    @OneToMany(mappedBy = "nfeProcessada", cascade = CascadeType.ALL)
    private List<NFeProcessadaItem> itens;

    public NFeProcessada() {
        itens = new ArrayList<NFeProcessadaItem>();
    }
    
    public Integer getIdNFeProcessada() {
        return idNFeProcessada;
    }

    public void setIdNFeProcessada(Integer idNFeProcessada) {
        this.idNFeProcessada = idNFeProcessada;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public void setNumeroNotaFiscal(String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public String getNomeEmitente() {
        return nomeEmitente;
    }

    public void setNomeEmitente(String nomeEmitente) {
        this.nomeEmitente = nomeEmitente;
    }

    public Timestamp getDataHoraEmissao() {
        return dataHoraEmissao;
    }

    public void setDataHoraEmissao(Timestamp dataHoraEmissao) {
        this.dataHoraEmissao = dataHoraEmissao;
    }

    public BigDecimal getValorTotalNotaFiscal() {
        return valorTotalNotaFiscal;
    }

    public void setValorTotalNotaFiscal(BigDecimal valorTotalNotaFiscal) {
        this.valorTotalNotaFiscal = valorTotalNotaFiscal;
    }

    public Timestamp getDataHoraInclusao() {
        return dataHoraInclusao;
    }

    public void setDataHoraInclusao(Timestamp dataHoraInclusao) {
        this.dataHoraInclusao = dataHoraInclusao;
    }

    public Timestamp getDataHoraEmissaoInicio() {
        return dataHoraEmissaoInicio;
    }

    public void setDataHoraEmissaoInicio(Timestamp dataHoraEmissaoInicio) {
        this.dataHoraEmissaoInicio = dataHoraEmissaoInicio;
    }

    public Timestamp getDataHoraEmissaoFim() {
        return dataHoraEmissaoFim;
    }

    public void setDataHoraEmissaoFim(Timestamp dataHoraEmissaoFim) {
        this.dataHoraEmissaoFim = dataHoraEmissaoFim;
    }

    public List<NFeProcessadaItem> getItens() {
        return itens;
    }

    public void setItens(List<NFeProcessadaItem> itens) {
        this.itens = itens;
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idNFeProcessada);
        hash = 29 * hash + Objects.hashCode(this.chave);
        hash = 29 * hash + Objects.hashCode(this.numeroNotaFiscal);
        hash = 29 * hash + Objects.hashCode(this.nomeEmitente);
        hash = 29 * hash + Objects.hashCode(this.dataHoraEmissao);
        hash = 29 * hash + Objects.hashCode(this.valorTotalNotaFiscal);
        hash = 29 * hash + Objects.hashCode(this.dataHoraInclusao);
        hash = 29 * hash + Objects.hashCode(this.dataHoraEmissaoInicio);
        hash = 29 * hash + Objects.hashCode(this.dataHoraEmissaoFim);
        hash = 29 * hash + Objects.hashCode(this.itens);
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
        final NFeProcessada other = (NFeProcessada) obj;
        if (!Objects.equals(this.chave, other.chave)) {
            return false;
        }
        if (!Objects.equals(this.numeroNotaFiscal, other.numeroNotaFiscal)) {
            return false;
        }
        if (!Objects.equals(this.nomeEmitente, other.nomeEmitente)) {
            return false;
        }
        if (!Objects.equals(this.idNFeProcessada, other.idNFeProcessada)) {
            return false;
        }
        if (!Objects.equals(this.dataHoraEmissao, other.dataHoraEmissao)) {
            return false;
        }
        if (!Objects.equals(this.valorTotalNotaFiscal, other.valorTotalNotaFiscal)) {
            return false;
        }
        if (!Objects.equals(this.dataHoraInclusao, other.dataHoraInclusao)) {
            return false;
        }
        if (!Objects.equals(this.dataHoraEmissaoInicio, other.dataHoraEmissaoInicio)) {
            return false;
        }
        if (!Objects.equals(this.dataHoraEmissaoFim, other.dataHoraEmissaoFim)) {
            return false;
        }
        if (!Objects.equals(this.itens, other.itens)) {
            return false;
        }
        return true;
    }
}
