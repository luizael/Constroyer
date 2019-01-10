package br.com.mixfiscal.prodspedxnfe.gui.backbean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
@ManagedBean
@SessionScoped
public class MeuBackBean {
    private String meuTeste;
    private String resultado;
    
    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    public String getMeuTeste() {
        return meuTeste;
    }
    public void setMeuTeste(String meuTeste) {
        this.meuTeste = meuTeste;
    }
    public  void btnProcesso()
    {
        if(!this.meuTeste.equals("Ola"))
        {
            this.resultado = "vc nao escreveu Ola!";
        }else
        {
                 this.resultado = "Aii sim em manu";
        }
        this.setMeuTeste(this.meuTeste);
    }
    public MeuBackBean() {       
    }    
}
