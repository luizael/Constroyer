package br.com.mixfiscal.prodspedxnfe.gui.backbean;

import br.com.mixfiscal.prodspedxnfe.gui.util.Constantes;
import br.com.mixfiscal.prodspedxnfe.gui.util.Utils;
import br.com.mixfiscal.prodspedxnfe.services.ProcessadorSPED;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ManagedBean
@SessionScoped
public class IndexBackBean {
    private HtmlInputText tfCaminhoArquivoSPED;
    private HtmlInputText tfCaminhoDirXMLsNFes;
    private HtmlInputText tfCaminhoDirXMLsNFCes;
    private HtmlInputText tfCaminhoDirXMLsCFes;
    private HtmlOutputText otResumoProcessamento;
    private String caminhoArquivoSPED;
    private String caminhoDirXMLsNFes;
    private String caminhoDirXMLsNFCes;
    private String caminhoDirXMLsCFes;
    private String caminhoExel;
    private String mensagem;
    private String resumoProcessamento;
    private ModoVerificacaoOrdemItens modoDeteccaoOrdemItens;    
    private ProcessadorSPED processadorSPED;   
    private Logger log;
    private boolean exibirPreAssociados; 
    
    @PostConstruct
    public void init() {
        this.processadorSPED = new ProcessadorSPED();
        this.modoDeteccaoOrdemItens = ModoVerificacaoOrdemItens.Sistema;
        this.setLog(LogManager.getLogger(IndexBackBean.class));
        //this.jobMananger = new JobMananger();
        //this.executarJobs();
    }  
    
    public enum ModoVerificacaoOrdemItens {
        NaoUsar,
        Sistema,
        AssumirOrdenados
    }

    public HtmlInputText getTfCaminhoArquivoSPED() {
        return tfCaminhoArquivoSPED;
    }

    public void setTfCaminhoArquivoSPED(HtmlInputText tfCaminhoArquivoSPED) {
        this.tfCaminhoArquivoSPED = tfCaminhoArquivoSPED;
    }

    public HtmlInputText getTfCaminhoDirXMLsNFes() {
        return tfCaminhoDirXMLsNFes;
    }

    public void setTfCaminhoDirXMLsNFes(HtmlInputText tfCaminhoDirXMLsNFes) {
        this.tfCaminhoDirXMLsNFes = tfCaminhoDirXMLsNFes;
    }  
    
     public HtmlInputText getTfCaminhoDirXMLsNFCes() {
        return tfCaminhoDirXMLsNFCes;
    }

    public void setTfCaminhoDirXMLsNFCes(HtmlInputText tfCaminhoDirXMLsNFCes) {
        this.tfCaminhoDirXMLsNFCes = tfCaminhoDirXMLsNFCes;
    }
    
    public HtmlInputText getTfCaminhoDirXMLsCFes() {
        return tfCaminhoDirXMLsCFes;
    }

    public void setTfCaminhoDirXMLsCFes(HtmlInputText tfCaminhoDirXMLsCFes) {
        this.tfCaminhoDirXMLsCFes = tfCaminhoDirXMLsCFes;
    }    
    public HtmlOutputText getOtResumoProcessamento() {
        return otResumoProcessamento;
    }

    public void setOtResumoProcessamento(HtmlOutputText otResumoProcessamento) {
        this.otResumoProcessamento = otResumoProcessamento;
    }
        
    public String getCaminhoExel() {
        return caminhoExel;
    }

   
    public void setCaminhoExel(String caminhoExel) {
        this.caminhoExel = caminhoExel;
    }
    public String getCaminhoArquivoSPED() {
        return caminhoArquivoSPED;
    }

    public void setCaminhoArquivoSPED(String caminhoArquivoSPED) {
        this.caminhoArquivoSPED = caminhoArquivoSPED;
    }

    public String getCaminhoDirXMLsNFes() {
        return caminhoDirXMLsNFes;
    }

    public void setCaminhoDirXMLsNFes(String caminhoDirXMLsNFes) {
        this.caminhoDirXMLsNFes = caminhoDirXMLsNFes;
    }

    public String getCaminhoDirXMLsCFes() {
        return caminhoDirXMLsCFes;
    }
    public void setCaminhoDirXMLsCFes(String caminhoDirXMLsCFes) {
        this.caminhoDirXMLsCFes = caminhoDirXMLsCFes;
    }   
    
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }    

    public String getResumoProcessamento() {
        return resumoProcessamento;
    }

    public void setResumoProcessamento(String resumoProcessamento) {
        this.resumoProcessamento = resumoProcessamento;
    }
    
    public ModoVerificacaoOrdemItens getModoDeteccaoOrdemItens() {
        return modoDeteccaoOrdemItens;
    }

    public void setModoDeteccaoOrdemItens(ModoVerificacaoOrdemItens modoDeteccaoOrdemItens) {
        this.modoDeteccaoOrdemItens = modoDeteccaoOrdemItens;
    }  

    public ProcessadorSPED getProcessadorSPED() {
        return processadorSPED;
    }

    public void setProcessadorSPED(ProcessadorSPED processadorSPED) {
        this.processadorSPED = processadorSPED;
    }
    
    public boolean isExibirPreAssociados() {
        return exibirPreAssociados;
    }
    
    public void setExibirPreAssociados(boolean exibirPreAssociados) {
        this.exibirPreAssociados = exibirPreAssociados;
    }

    public String getCaminhoDirXMLsNFCes() {
        return caminhoDirXMLsNFCes;
    }

    public void setCaminhoDirXMLsNFCes(String caminhoDirXMLsNFCes) {
        this.caminhoDirXMLsNFCes = caminhoDirXMLsNFCes;
    }
        
    public void btProcessar() {
        FacesContext ctx = FacesContext.getCurrentInstance();        
        boolean executarTransacoes = Boolean.parseBoolean(ctx.getExternalContext().getInitParameter(Constantes.PARAM_EXECUTAR_TRANSACOES));
        String caminhoSPED = Utils.retornarCaminhoServidor(this.caminhoArquivoSPED);
        String caminhoDirXmlNFe = Utils.retornarCaminhoServidor(this.caminhoDirXMLsNFes);
        
        try {            
            processadorSPED.setCaminhoArquivoSPED(caminhoSPED);
            processadorSPED.setCaminhoDiretorioXmlsNFes(caminhoDirXmlNFe);
           
            // Deixando o sistema resolver 
            //processadorSPED.setAnalisarOrdemItensAuto(true);
            //processadorSPED.setAssumirItensEmOrdem(false);
            //processadorSPED.setExecutarTransacoes(executarTransacoes);            
           
            processadorSPED.processar();            
            if(processadorSPED.getItensPreAssociados().size() > 0)
            {
                //chama a tela aqui 
               this.setExibirPreAssociados(true);
            }
            String resumo = processadorSPED.getResumoProcessamento().replace("\r\n", "");
            resumoProcessamento = "<div class='resumoProcessamento'>Processamento finalizado com sucesso...</div>" + resumo;
        } catch(Exception ex) {
            getLog().error(ex.getMessage(), ex);
            mensagem = ex.getMessage();
        }
    }
   
    public Logger getLog() {
        return log;
    }
   
    public void setLog(Logger log) {
        this.log = log;
    }    
}
