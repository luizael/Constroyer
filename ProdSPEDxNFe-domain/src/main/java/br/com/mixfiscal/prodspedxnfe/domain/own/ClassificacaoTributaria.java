
package br.com.mixfiscal.prodspedxnfe.domain.own;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import com.google.gson.annotations.SerializedName;
import javax.persistence.CascadeType;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;

@Entity
@Table(name = "classificacao_tributaria")
public class ClassificacaoTributaria implements Serializable {
    private static final long serialVersionUID = -1596574385097491671L;
    
    @Id
    //@SequenceGenerator(name = "class_tributaria_seq", sequenceName = "class_tributaria_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clas_tributaria")
    private Integer id;
    
    @Column(name = "status")
    @SerializedName("status")
    private String status;
    
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "classificacaoTributaria", cascade = CascadeType.ALL)
    @SerializedName("produto")
    private ClassificacaoTributariaProduto produto;
    
    @Column(name = "data_resposta")
    @SerializedName("dataResposta")
    private String dataResposta;
    
    @Column(name = "proximo_captcha")
    @SerializedName("proximoCaptcha")
    private String proximoCaptcha;
    
    //@OneToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    
    public ClassificacaoTributaria(){
        this.produto = new ClassificacaoTributariaProduto();
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public ClassificacaoTributariaProduto getProduto() {
        return produto;
    }

     public void setProduto(ClassificacaoTributariaProduto produto) {
        this.produto = produto;
    }

    public String getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(String dataResposta) {
        this.dataResposta = dataResposta;
    }

    public String getProximoCaptcha() {
        return proximoCaptcha;
    }

    public void setProximoCaptcha(String proximoCaptcha) {
        this.proximoCaptcha = proximoCaptcha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.status);
        hash = 59 * hash + Objects.hashCode(this.produto);
        hash = 59 * hash + Objects.hashCode(this.dataResposta);
        hash = 59 * hash + Objects.hashCode(this.proximoCaptcha);
        hash = 59 * hash + Objects.hashCode(this.cliente);
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
        final ClassificacaoTributaria other = (ClassificacaoTributaria) obj;
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.dataResposta, other.dataResposta)) {
            return false;
        }
        if (!Objects.equals(this.proximoCaptcha, other.proximoCaptcha)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.produto, other.produto)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        return true;
    }
}
