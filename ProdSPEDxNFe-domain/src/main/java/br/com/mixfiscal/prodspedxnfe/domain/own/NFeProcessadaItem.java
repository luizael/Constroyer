package br.com.mixfiscal.prodspedxnfe.domain.own;

import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFeItem;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "nfe_processada_item")
public class NFeProcessadaItem implements Serializable {
    private static final long serialVersionUID = -7874684446781603468L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nfe_processada_item")
    private Integer idNFeProcessadaItem;    
    @Column(name = "codigo_produto")
    private String codigoProduto;
    @Column(name = "ean")
    private String EAN;
    @Column(name = "nome_produto")
    private String nomeProduto;
    @Column(name = "ncm")
    private String NCM;
    @Column(name = "cfop")
    private String CFOP;
    @Column(name = "unidade_medida")
    private String unidadeMedida;
    @Column(name = "qtd")
    private BigDecimal qtd;
    @Column(name = "valor_unidade_com")
    private BigDecimal valorUnidadeComercial;
    @Column(name = "valor_produto")
    private BigDecimal valorProduto;
    @Column(name = "data_hora_inclusao")
    private Timestamp dataHoraInclusao;
    @ManyToOne
    @JoinColumn(name = "id_nfe_processada")
    private NFeProcessada nfeProcessada;

    public NFeProcessadaItem() {
        this.nfeProcessada = new NFeProcessada();
    }
    
    public NFeProcessadaItem(NFeItem nfeItem) {
        super();        
        this.codigoProduto = nfeItem.getCodigoProduto();
        this.EAN = nfeItem.getCodigoEAN();
        this.nomeProduto = nfeItem.getDescricaoProduto();
        this.NCM = nfeItem.getCodigoNCM();
        this.CFOP = nfeItem.getCfop();
        this.unidadeMedida = nfeItem.getUnidade();
        this.qtd = nfeItem.getQuantidade();
        this.valorUnidadeComercial = nfeItem.getValorUnitario();
        this.valorProduto = nfeItem.getValorProduto();
        this.dataHoraInclusao = new Timestamp(Calendar.getInstance().getTime().getTime());
    }
    
    public Integer getIdNFeProcessadaItem() {
        return idNFeProcessadaItem;
    }

    public void setIdNFeProcessadaItem(Integer idNFeProcessadaItem) {
        this.idNFeProcessadaItem = idNFeProcessadaItem;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getEAN() {
        return EAN;
    }

    public void setEAN(String EAN) {
        this.EAN = EAN;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getNCM() {
        return NCM;
    }

    public void setNCM(String NCM) {
        this.NCM = NCM;
    }

    public String getCFOP() {
        return CFOP;
    }

    public void setCFOP(String CFOP) {
        this.CFOP = CFOP;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public BigDecimal getQtd() {
        return qtd;
    }

    public void setQtd(BigDecimal qtd) {
        this.qtd = qtd;
    }

    public BigDecimal getValorUnidadeComercial() {
        return valorUnidadeComercial;
    }

    public void setValorUnidadeComercial(BigDecimal valorUnidadeComercial) {
        this.valorUnidadeComercial = valorUnidadeComercial;
    }

    public BigDecimal getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(BigDecimal valorProduto) {
        this.valorProduto = valorProduto;
    }

    public Timestamp getDataHoraInclusao() {
        return dataHoraInclusao;
    }

    public void setDataHoraInclusao(Timestamp dataHoraInclusao) {
        this.dataHoraInclusao = dataHoraInclusao;
    }
    
    public NFeProcessada getNFeProcessada() {
        return nfeProcessada;
    }

    public void setNFeProcessada(NFeProcessada nfeProcessada) {
        this.nfeProcessada = nfeProcessada;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.idNFeProcessadaItem);
        hash = 47 * hash + Objects.hashCode(this.codigoProduto);
        hash = 47 * hash + Objects.hashCode(this.EAN);
        hash = 47 * hash + Objects.hashCode(this.nomeProduto);
        hash = 47 * hash + Objects.hashCode(this.NCM);
        hash = 47 * hash + Objects.hashCode(this.CFOP);
        hash = 47 * hash + Objects.hashCode(this.unidadeMedida);
        hash = 47 * hash + Objects.hashCode(this.qtd);
        hash = 47 * hash + Objects.hashCode(this.valorUnidadeComercial);
        hash = 47 * hash + Objects.hashCode(this.valorProduto);
        hash = 47 * hash + Objects.hashCode(this.dataHoraInclusao);
        hash = 47 * hash + Objects.hashCode(this.nfeProcessada);
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
        final NFeProcessadaItem other = (NFeProcessadaItem) obj;
        if (!Objects.equals(this.codigoProduto, other.codigoProduto)) {
            return false;
        }
        if (!Objects.equals(this.EAN, other.EAN)) {
            return false;
        }
        if (!Objects.equals(this.nomeProduto, other.nomeProduto)) {
            return false;
        }
        if (!Objects.equals(this.NCM, other.NCM)) {
            return false;
        }
        if (!Objects.equals(this.CFOP, other.CFOP)) {
            return false;
        }
        if (!Objects.equals(this.unidadeMedida, other.unidadeMedida)) {
            return false;
        }
        if (!Objects.equals(this.idNFeProcessadaItem, other.idNFeProcessadaItem)) {
            return false;
        }
        if (!Objects.equals(this.qtd, other.qtd)) {
            return false;
        }
        if (!Objects.equals(this.valorUnidadeComercial, other.valorUnidadeComercial)) {
            return false;
        }
        if (!Objects.equals(this.valorProduto, other.valorProduto)) {
            return false;
        }
        if (!Objects.equals(this.dataHoraInclusao, other.dataHoraInclusao)) {
            return false;
        }
        if (!Objects.equals(this.nfeProcessada, other.nfeProcessada)) {
            return false;
        }
        return true;
    }
}
