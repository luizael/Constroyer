package br.com.mixfiscal.prodspedxnfe.domain.wsmix;

import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributaria;
import com.google.gson.annotations.SerializedName;
public class ResponseJson {
    //@SerializedName("status")
    private String status;
    
    //@SerializedName("produto")
   // private Produto produto;
    private ClassificacaoTributaria produto;
    
    //@SerializedName("dataResposta")
    private String dataResposta;
    
    //@SerializedName("proximoCaptcha")
    private String proximoCaptcha;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ClassificacaoTributaria getProduto() {
        return produto;
    }

    public void setProduto(ClassificacaoTributaria produto) {
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

   
    
}
