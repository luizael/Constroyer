
package br.com.mixfiscal.prodspedxnfe.domain.own;

import br.com.mixfiscal.prodspedxnfe.domain.enums.EFormatarCodigoInterno;
import br.com.mixfiscal.prodspedxnfe.domain.enums.EStatusAtualizacaoInfoFiscal;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="requisicao_atualizacao_info_fiscal")
public class RequisicaoAtualizacaoInfoFiscal implements Serializable {
    private static final long serialVersionUID = -6873110251005370541L;
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_requisicao_atualizacao_info_fiscal")
    private Integer idRequisicaoAtualizacaoInfoFiscal;
    
    @Column(name="nome_arquivo")
    private String nomeArquivo;
    
    @Column(name="data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    
    @Column(name="status_requisicao")
    private EStatusAtualizacaoInfoFiscal statusRequisicao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    @Column(name="formatar_codigo")
    private EFormatarCodigoInterno formatarCodigo;
    
    public Integer getIdRequisicaoAtualizacaoInfoFiscal() {
        return idRequisicaoAtualizacaoInfoFiscal;
    }

    public void setIdRequisicaoAtualizacaoInfoFiscal(int idRequisicaoAtualizacaoInfoFiscal) {
        this.idRequisicaoAtualizacaoInfoFiscal = idRequisicaoAtualizacaoInfoFiscal;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public EStatusAtualizacaoInfoFiscal getStatusRequisicao() {
        return statusRequisicao;
    }

    public void setStatusRequisicao(EStatusAtualizacaoInfoFiscal statusRequisicao) {
        this.statusRequisicao = statusRequisicao;
    }    

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public EFormatarCodigoInterno getFormatarCodigo() {
        return formatarCodigo;
    }

    public void setFormatarCodigo(EFormatarCodigoInterno formatarCodigo) {
        this.formatarCodigo = formatarCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.idRequisicaoAtualizacaoInfoFiscal);
        hash = 73 * hash + Objects.hashCode(this.nomeArquivo);
        hash = 73 * hash + Objects.hashCode(this.dataCadastro);
        hash = 73 * hash + Objects.hashCode(this.statusRequisicao);
        hash = 73 * hash + Objects.hashCode(this.cliente);
        hash = 73 * hash + Objects.hashCode(this.formatarCodigo);
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
        final RequisicaoAtualizacaoInfoFiscal other = (RequisicaoAtualizacaoInfoFiscal) obj;
        if (!Objects.equals(this.nomeArquivo, other.nomeArquivo)) {
            return false;
        }
        if (!Objects.equals(this.idRequisicaoAtualizacaoInfoFiscal, other.idRequisicaoAtualizacaoInfoFiscal)) {
            return false;
        }
        if (!Objects.equals(this.dataCadastro, other.dataCadastro)) {
            return false;
        }
        if (this.statusRequisicao != other.statusRequisicao) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.formatarCodigo, other.formatarCodigo)) {
            return false;
        }
        return true;
    }
   
}
