package br.com.mixfiscal.prodspedxnfe.gui.backbean;

import br.com.mixfiscal.prodspedxnfe.gui.util.Constantes;
import br.com.mixfiscal.prodspedxnfe.gui.util.Utils;
import br.com.mixfiscal.prodspedxnfe.services.relatorio.ImportarExcelParaAtualizarBase;
import br.com.mixfiscal.prodspedxnfe.services.relatorio.RelatorioDebitoCredito;
import br.com.mixfiscal.prodspedxnfe.services.relatorio.RelatorioDebitoCredito.Fornecedor;
import br.com.mixfiscal.prodspedxnfe.services.relatorio.Relatorio;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "relCredDebBackBean")
@SessionScoped
public class RelatorioCreditoDebitoBackBean {

    // <editor-fold desc="Membros Privados" defaultstate="collapsed">
    @ManagedProperty(value = "#{indexBackBean}")
    private IndexBackBean indexBackBean;
    
    private HtmlInputText tfCaminhoArquivoSPED;
    private HtmlInputText tfCaminhoDirXMLsNFes; 
    private HtmlInputText tfCaminhoDirXMLsNFCes;
    private HtmlInputText tfCaminhoDirXMLsCFes;  
    private HtmlInputText tfCaminhoExel;
    private TipoRelatorio tipoRelatorio;
    private String mensagem;
    private List<Fornecedor> relatorio;
    private List<Relatorio> listaRelatorio;
    private int qtdItem;
    private BigDecimal valorTotalCreditoIndevido;
    private BigDecimal valorTotalCreditoAproveitar;
    private BigDecimal totalDebitoindevido;
    private BigDecimal totalDebitoNaoDeclarado;
    private boolean ckeckUpdate;
    private RelatorioDebitoCredito relatorioDebitoCredito;   
    private ImportarExcelParaAtualizarBase importarExelParaAtualizarBase;
    // </editor-fold>
    
    @PostConstruct
    public void init() {       
        this.relatorioDebitoCredito = new RelatorioDebitoCredito();
        this.setImportarExelParaAtualizarBase(new ImportarExcelParaAtualizarBase());
    }  
    
    public IndexBackBean getIndexBackBean() {
        return indexBackBean;
    }

    public void setIndexBackBean(IndexBackBean indexBackBean) {
        this.indexBackBean = indexBackBean;
    }
    
    public HtmlInputText getTfCaminhoArquivoSPED(){
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
    
    public String getCaminhoExel(){
    return this.indexBackBean.getCaminhoExel();
    } 
    public void setCaminhoExel (String caminhoExel){
     this.indexBackBean.setCaminhoExel(caminhoExel);
    }
    public String getCaminhoArquivoSPED() {
        return this.indexBackBean.getCaminhoArquivoSPED();
    }

    public void setCaminhoArquivoSPED(String caminhoArquivoSPED) {
        this.indexBackBean.setCaminhoArquivoSPED(caminhoArquivoSPED);
    }

    public String getCaminhoDirXMLsNFes() {
        return this.indexBackBean.getCaminhoDirXMLsNFes();
    }    
    
    public void setCaminhoDirXMLsNFes(String caminhoDirXMLsNFes) {
        this.indexBackBean.setCaminhoDirXMLsNFes(caminhoDirXMLsNFes);
    }    
    
    public void setCaminhoDirXMLsNFCe(String caminhoDirXMLsNFCes){
        this.indexBackBean.setCaminhoDirXMLsNFCes(caminhoDirXMLsNFCes);
    } 
    
    public String getCaminhoDirXMLsNFCe(){
        return this.indexBackBean.getCaminhoDirXMLsNFCes();
    }    
    
    public void setCaminhoDirXmlsCFes(String caminhoDirXMLsCFes){
        this.indexBackBean.setCaminhoDirXMLsCFes(caminhoDirXMLsCFes);
    }
    
    public String getCaminhoDirXmlsCFes(){
        return this.indexBackBean.getCaminhoDirXMLsCFes();
    }    
    
    public TipoRelatorio getTipoRelatorio() {
        return tipoRelatorio;
    }
    
    public void setTipoRelatorio(TipoRelatorio tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }
    
    public String getMensagem() {
        return mensagem;
    }
    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public List<Fornecedor> getRelatorio() {
        return relatorio;
    }   
    
    public void setRelatorio(List<Fornecedor> relatorio) {
        this.relatorio = relatorio;
    }
    
    public List<Relatorio> getRelatorioGeral() {
        return listaRelatorio;
    }
    
    public void setRelatorioGeral(List<Relatorio> relatorio) {
        this.listaRelatorio = relatorio;
    }
    
    public int getQtdItem() {
        return qtdItem;
    }
    
    public void setQtdItem(int qtdItem) {
        this.qtdItem = qtdItem;
    }
    
    public BigDecimal getValorTotalCreditoIndevido() {
        return valorTotalCreditoIndevido;
    }
    
    public void setValorTotalCreditoIndevido(BigDecimal valorTotalCreditoIndevido) {
        this.valorTotalCreditoIndevido = valorTotalCreditoIndevido;
    }
    
    public BigDecimal getValorTotalCreditoAproveitar() {
        return valorTotalCreditoAproveitar;
    }
    
    public void setValorTotalCreditoAproveitar(BigDecimal valorTotalCreditoAproveitar) {
        this.valorTotalCreditoAproveitar = valorTotalCreditoAproveitar;
    }

    public BigDecimal getTotalDebitoindevido() {
        return totalDebitoindevido;
    }

    public void setTotalDebitoindevido(BigDecimal totalDebitoindevido) {
        this.totalDebitoindevido = totalDebitoindevido;
    }

    public BigDecimal getTotalDebitoNaoDeclarado() {
        return totalDebitoNaoDeclarado;
    }

    public void setTotalDebitoNaoDeclarado(BigDecimal totalDebitoNaoDeclarado) {
        this.totalDebitoNaoDeclarado = totalDebitoNaoDeclarado;
    }

    public boolean isCkeckUpdate() {
        return ckeckUpdate;
    }

    public void setCkeckUpdate(boolean ckeckUpdate) {
        this.ckeckUpdate = ckeckUpdate;
    }

    public RelatorioDebitoCredito getRelatorioDebitoCredito() {
        return relatorioDebitoCredito;
    }

    public void setRelatorioDebitoCredito(RelatorioDebitoCredito relatorioDebitoCredito) {
        this.relatorioDebitoCredito = relatorioDebitoCredito;
           
    }
     /**
     * @return the tfCaminhoExel
     */
    public HtmlInputText getTfCaminhoExel() {
        return tfCaminhoExel;
    }

    /**
     * @param tfCaminhoExel the tfCaminhoExel to set
     */
    public void setTfCaminhoExel(HtmlInputText tfCaminhoExel) {
        this.tfCaminhoExel = tfCaminhoExel;
    }
        /**
     * @return the importarExelParaAtualizarBase
     */
    public ImportarExcelParaAtualizarBase getImportarExelParaAtualizarBase() {
        return importarExelParaAtualizarBase;
    }

    /**
     * @param importarExelParaAtualizarBase the importarExelParaAtualizarBase to set
     */
    public void setImportarExelParaAtualizarBase(ImportarExcelParaAtualizarBase importarExelParaAtualizarBase) {
        this.importarExelParaAtualizarBase = importarExelParaAtualizarBase;
    }
    
    // <editor-fold desc="Métodos Públicos" defaultstate="collapsed">
//    public void btGerarRelatorio() {
//        
//        if (StringUtil.isNullOrEmpty(this.indexBackBean.getCaminhoArquivoSPED())) {
//            this.mensagem = "Por favor, informe o caminho do arquivo SPED Modificado";
//            return;
//        }
//        try {
//            RelatorioDebitoCredito rel = new RelatorioDebitoCredito();
//            rel.setCaminhoArquivoSped(Utils.retornarCaminhoServidor(this.getCaminhoArquivoSPED()));
//            if(this.tipoRelatorio == TipoRelatorio.DivergenciaEntrada){
//                this.mensagem ="Gerando relatório de Divengencia de Entrada, aguarde...";
//                this.setValorTotalCreditoIndevido(rel.getValorTotalCreditoIndevido());
//                this.setValorTotalCreditoAproveitar(rel.getValorTotalCreditonaoAproveitado());
//                this.mensagem ="relatório finalizado";
//            }
//            if(this.tipoRelatorio == TipoRelatorio.DivergenciaSaida){
//                
//                rel.setCaminhoDirXMLsNFes(Utils.retornarCaminhoServidor(this.getCaminhoDirXMLsNFes()));
//                rel.setCaminhoDirXMLsNFCes(Utils.retornarCaminhoServidor(this.getCaminhoDirXMLsNFCe()));
//                rel.setCaminhoDirXMLsCFes(Utils.retornarCaminhoServidor(this.getCaminhoDirXmlsCFes()));
//                if(this.isCkeckUpdate()){
//                    this.btnGerarListaWs();
//                }
//                this.setTotalDebitoNaoDeclarado(rel.getTotalDebitoNaoDeclarado());
//                this.setTotalDebitoindevido(rel.getTotalDebitoIndevido());
//                this.mensagem ="relatório finalizado";
//            }
//        } catch(Exception ex) {
//            this.mensagem = "Houve um erro na geração do Relatório. Mensagem: " + ex.getMessage();
//            System.err.println(ex);
//        }        
//    }
    
    public void btnGerarListaWs(){
        RelatorioDebitoCredito rel = new RelatorioDebitoCredito();        
        try{
            rel.setCaminhoArquivoSped(Utils.retornarCaminhoServidor(this.getCaminhoArquivoSPED()));
            rel.atualizarTributacaoMix();
        }catch(Exception ex){
            this.mensagem = "Houve um erro na geração do Relatório. Mensagem: " + ex.getMessage();
            //System.err.println(ex);
        }     
    }
    private void somarValorRelatorio(){
//        for(RelatorioGeral rl: this.relatorioGeral){
//            this.valorTotalCreditoAproveitar.add(rl.getValorCreditoNaoAproveitado());
//            this.valorTotalCreditoIndevido.add(rl.getValorCreditoIndevido());
//        }
        
    }       
    public TipoRelatorio[] retornarTiposRelatorio() {        
        return TipoRelatorio.values();
    }
    
    public void exportarParaCsv(){
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
            StringBuilder sbCsv = new StringBuilder();
            List<RelatorioDebitoCredito> lista = (List<RelatorioDebitoCredito>)request.getSession().getAttribute(Constantes.SESSION_LISTA_REL_CFOP_FILTRADA);            
            //RelatorioCFOPWS.CarregarSPEDRetorno header = (RelatorioCFOPWS.CarregarSPEDRetorno)request.getSession().getAttribute(Constantes.SESSION_LEITOR_SPD);
            
    }
    // </editor-fold>
    
    // <editor-fold desc="Enum TipoRelatorio" defaultstate="collapsed">
    public enum TipoRelatorio {       
        DivergenciaEntrada("Divergencia Entrada"),
        DivergenciaSaida("Divergencia Saida");
        private String nome;
        
        private TipoRelatorio(String nome) {
            this.nome = nome;            
        }
        
        public String getNome() {
            return this.nome;
        }
    }
    // </editor-fold>

}
