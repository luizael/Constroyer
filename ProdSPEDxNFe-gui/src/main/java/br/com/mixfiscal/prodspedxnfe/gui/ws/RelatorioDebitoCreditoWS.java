
package br.com.mixfiscal.prodspedxnfe.gui.ws;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import br.com.lulusoftwares.luluframework.jquery.JQueryResponse;
import br.com.lulusoftwares.luluframework.jquery.JQueryResult;
import br.com.mixfiscal.prodspedxnfe.domain.enums.EFormatarCodigoInterno;
import br.com.mixfiscal.prodspedxnfe.domain.own.RequisicaoAtualizacaoInfoFiscal;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPED0000;
import br.com.mixfiscal.prodspedxnfe.gui.backbean.RelatorioCreditoDebitoBackBean;
import br.com.mixfiscal.prodspedxnfe.gui.backbean.RelatorioCreditoDebitoBackBean.TipoRelatorio;
import br.com.mixfiscal.prodspedxnfe.gui.util.Constantes;
import br.com.mixfiscal.prodspedxnfe.gui.util.Utils;
import br.com.mixfiscal.prodspedxnfe.services.relatorio.Relatorio;
import br.com.mixfiscal.prodspedxnfe.services.relatorio.RelatorioDebitoCredito;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import br.com.mixfiscal.prodspedxnfe.services.relatorio.ImportarExcelParaAtualizarBase;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.logging.log4j.Logger;

@Path("/relDebCreditoWS")
public class RelatorioDebitoCreditoWS {
    @Context
    private HttpServletRequest req; 
    @Context 
    private ServletContext servletContext;
    private Logger log;
    //private RelatorioDivergenciaEntrada relatorio;
    private RelatorioParam relatorio;
    public RelatorioDebitoCreditoWS(){}
    
    @POST
    @Path("/processarRelatorio")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse processarRelatorio(Parameter param){
        JQueryResponse response = new JQueryResponse();
        try{
            
            RelatorioCreditoDebitoBackBean rbb = (RelatorioCreditoDebitoBackBean)req.getSession().getAttribute("relCredDebBackBean");
            String serverPath = servletContext.getInitParameter(Constantes.PARAM_SERVER_PATH);
            RelatorioDebitoCredito relDebCred = rbb.getRelatorioDebitoCredito();
            relDebCred.inicializarVariaveis();
            relDebCred.setCaminhoArquivoSped(Utils.retornarCaminhoServidor(serverPath,param.getCaminhoSped()));
            
            if(param.getAtualizar().equals("sim")){
                if(!relDebCred.atualizarTributacaoMix()){
                    relDebCred.inicializarVariaveis();
                    response.setMessage("verifique o token de acesso ao webservice da mix referente a esse cnpj");
                    response.setResult(JQueryResult.Error);
                    return response;
                }
            }
            relDebCred.setTipoRelatorio(param.getTipo());
            
            if(param.getTipo().equals(TipoRelatorio.DivergenciaEntrada.toString()))
                relDebCred.gerarRelatorioDivergenciaEntrada();
            
            if(param.getTipo().equals(TipoRelatorio.DivergenciaSaida.toString())){
                try{relDebCred.setCaminhoDirXMLsNFes(Utils.retornarCaminhoServidor(serverPath,param.getCaminhoNfe()));}catch(Exception ex){} 
                try{relDebCred.setCaminhoDirXMLsNFCes(Utils.retornarCaminhoServidor(serverPath,param.getCaminhoNFCe()));}catch(Exception ex){} 
                try{relDebCred.setCaminhoDirXMLsCFes(Utils.retornarCaminhoServidor(serverPath,param.getCaminhoCFe()));}catch(Exception ex){} 
                try{relDebCred.gerarRelatorioDivergenciaSaida();}catch(Exception ex){}
            }
            response.setResult(JQueryResult.Success);
        }catch(Exception ex){            
           
            response.setResult(JQueryResult.Error);
            response.setObject(ex);
            response.setMessage(ex.getMessage());
        }
        return response; 
    }
    
    @POST
    @Path("/checarProgresso")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse checarProgresso(){
        JQueryResponse response = new JQueryResponse();
        try{
            RelatorioCreditoDebitoBackBean rbb = (RelatorioCreditoDebitoBackBean)req.getSession().getAttribute("relCredDebBackBean");
            RelatorioDebitoCredito relDebCred = rbb.getRelatorioDebitoCredito();
            
            ChecarProgressoRet progresso = new ChecarProgressoRet();
            
            progresso.setAtual(relDebCred.getContadorProgresso());
            progresso.setDescricao(relDebCred.getDescricaoProgresso());
            progresso.setTotal(relDebCred.getTotalItem());
            
            response.setObject(progresso);
            response.setResult(JQueryResult.Success);
        }catch(Exception ex){
            this.log.error(ex.getMessage());
            response.setResult(JQueryResult.Error);
            response.setObject(ex);
        }
        return response; 
    }
    
    @POST
    @Path("/obterRelatorio")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse obterRelatorio(){
        JQueryResponse response = new JQueryResponse();
        try{
            RelatorioCreditoDebitoBackBean rbb = (RelatorioCreditoDebitoBackBean)req.getSession().getAttribute("relCredDebBackBean");
            RelatorioDebitoCredito relDebCred = rbb.getRelatorioDebitoCredito();
            relatorio  = new RelatorioParam();
            relatorio.setTipo(relDebCred.getTipoRelatorio());           
            relDebCred.getListaRelatorio().forEach(rel->{
               relatorio.lista.add(rel);
            });
            relatorio.setTtItem(relatorio.lista.size());
            relatorio.setTtEstorno(relDebCred.getValorTotalEstornoCredito().toString());
            relatorio.setTtNAproveitado(relDebCred.getValorTotalCreditonaoAproveitado().toString());
            response.setObject(relatorio);
            response.setResult(JQueryResult.Success);
        }catch(Exception ex){
           
            response.setObject(ex);
            response.setResult(JQueryResult.Error);
            response.setMessage(ex.getMessage());
        }
        return response;
    }
    
    @POST
    @Path("/realizarTesteConfirmacaoExel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse confirmarImportacaoExel(Parameter param){
        JQueryResponse response = new JQueryResponse();
        try{
            RelatorioCreditoDebitoBackBean rbb = (RelatorioCreditoDebitoBackBean)req.getSession().getAttribute("relCredDebBackBean");
            String serverPath = servletContext.getInitParameter(Constantes.PARAM_SERVER_PATH);
            ImportarExcelParaAtualizarBase importarExelParaAtualizarBase = rbb.getImportarExelParaAtualizarBase();
            RelatorioDebitoCredito relDebCred = rbb.getRelatorioDebitoCredito();
            relDebCred.inicializarVariaveis();
            relDebCred.setCaminhoArquivoSped(Utils.retornarCaminhoServidor(serverPath,param.getCaminhoSped()));
            relDebCred.setCaminhoExel(Utils.retornarCaminhoServidor(serverPath,param.getCaminhoExel()));
            importarExelParaAtualizarBase.setNomeSped(relDebCred.getLeitorSped().getSped0000().getNomeEmpresarialEntidade());
            // Sheet sheet = importarExelParaAtualizarBase.importarExel(relDebCred.getCaminhoExel());
            List<String>  listaCT = new ArrayList ();
            //List<String> listaRetorno = new ArrayList();

            listaCT = importarExelParaAtualizarBase.percorrerPlanoExelParaTesteDeConfirmacaoDoUsusario(
                    relDebCred.getCaminhoExel(),
                    relDebCred.getLeitorSped().getListaSPED0200(),
                    param.getFormatarCsv()
            );
            //listaRetorno = importarExelParaAtualizarBase.CompararSpedComExel(listaCT,relDebCred.getLeitorSped().getListaSPED0200());
            response.setObject(listaCT);
            response.setResult(JQueryResult.Success);
            return response;
        }catch(Exception ex){
            this.log.error(ex.getMessage());
            response.setObject(ex);
            response.setResult(JQueryResult.Error);
            response.setMessage(ex.getMessage());
            return response;
            }
    }
    
    @POST
    @Path("/ConfirmacaoExelParaBanco")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse ImportarExelnoBanco(Parameter param){
        JQueryResponse response = new JQueryResponse();
        try{
            RelatorioCreditoDebitoBackBean rbb = (RelatorioCreditoDebitoBackBean)req.getSession().getAttribute("relCredDebBackBean");
            String serverPath = servletContext.getInitParameter(Constantes.PARAM_SERVER_PATH);
            ImportarExcelParaAtualizarBase importarExcelParaAtualizarBase = rbb.getImportarExelParaAtualizarBase();
            RelatorioDebitoCredito relDebCred = rbb.getRelatorioDebitoCredito();
            relDebCred.inicializarVariaveis();
            relDebCred.setCaminhoArquivoSped(Utils.retornarCaminhoServidor(serverPath,param.getCaminhoSped()));
            relDebCred.setCaminhoExel(Utils.retornarCaminhoServidor(serverPath,param.getCaminhoExel()));            
            RequisicaoAtualizacaoInfoFiscal requisicao = new RequisicaoAtualizacaoInfoFiscal();
            requisicao.setFormatarCodigo(EFormatarCodigoInterno.valueOf(param.getFormatarCsv().toUpperCase()));
            importarExcelParaAtualizarBase.percorrerPlanoExcelParaSalvarNoBD(relDebCred.getCaminhoExel(),
                                                                             requisicao);
            response.setResult(JQueryResult.Success);
            return response;
        }catch(Exception ex){
           
            response.setObject(ex);
            response.setResult(JQueryResult.Error);
            response.setMessage(ex.getMessage());
            return response;
        }
    }
    @POST
    @Path("/ChecarContador")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse checarContador(Parameter param){
        JQueryResponse response = new JQueryResponse();
        try{
             RelatorioCreditoDebitoBackBean rbb = (RelatorioCreditoDebitoBackBean)req.getSession().getAttribute("relCredDebBackBean");
             response.setObject(rbb.getImportarExelParaAtualizarBase().getDescricaoProgresso());
             response.setResult(JQueryResult.Success);
        }catch(Exception ex){
            response.setObject(ex);
            response.setResult(JQueryResult.Error);
            response.setMessage(ex.getMessage());
        }
        return response;
    }
    
    public static class Parameter{
        private String tipo;
        private String atualizar;
        private String formatarCsv;
        private String caminhoSped;
        private String caminhoNfe;
        private String caminhoNFCe;
        private String caminhoCFe;
        private String caminhoExel;
        public String getFormatarCsv() {
            return formatarCsv;
        }

        public void setFormatarCsv(String formatarCsv) {
            this.formatarCsv = formatarCsv;
        }
        public String getCaminhoSped() {
            return caminhoSped;
        }

        public void setCaminhoSped(String caminhoSped) {
            this.caminhoSped = caminhoSped;
        }

        public String getCaminhoNfe() {
            return caminhoNfe;
        }

        public void setCaminhoNfe(String caminhoNfe) {
            this.caminhoNfe = caminhoNfe;
        }

        public String getCaminhoNFCe() {
            return caminhoNFCe;
        }

        public void setCaminhoNFCe(String caminhoNFCe) {
            this.caminhoNFCe = caminhoNFCe;
        }

        public String getCaminhoCFe() {
            return caminhoCFe;
        }

        public void setCaminhoCFe(String caminhoCFe) {
            this.caminhoCFe = caminhoCFe;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getAtualizar() {
            return atualizar;
        }

        public void setAtualizar(String atualizar) {
            this.atualizar = atualizar;
        }

        public String getCaminhoExel() {
            return caminhoExel;
        }
        public void setCaminhoExel(String caminhoExel) {
            this.caminhoExel = caminhoExel;
        }
    }
    
    public static class ChecarProgressoRet implements Serializable {
        private int total;
        private int atual;
        private String descricao;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getAtual() {
            return atual;
        }

        public void setAtual(int atual) {
            this.atual = atual;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }
    }
    
    public static class RelatorioParam extends Relatorio{
        private List<Relatorio> lista;
        private String tipo;
        private int ttItem;
        private String ttEstorno;
        private String ttNAproveitado;
        
        public List<Relatorio> getLista() {
            return lista;
        }
        public void setLista(List<Relatorio> lista) {
            this.lista = lista;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }
        
        public RelatorioParam(){
            lista = new ArrayList<>();
        }

        public int getTtItem() {
            return ttItem;
        }

        public void setTtItem(int ttItem) {
            this.ttItem = ttItem;
        }

        public String getTtEstorno() {
            return ttEstorno;
        }

        public void setTtEstorno(String ttEstorno) {
            this.ttEstorno = ttEstorno;
        }

        public String getTtNAproveitado() {
            return ttNAproveitado;
        }

        public void setTtNAproveitado(String ttNAproveitado) {
            this.ttNAproveitado = ttNAproveitado;
        }
    }
    
    public static class RetornoProcessoExcel {
        private List<String> listaComOsDadosComparadosEntreExcelC200;
        private SPED0000 sped0000; 

        public List<String> getListaComOsDadosComparadosEntreExcelC200() {
            return listaComOsDadosComparadosEntreExcelC200;
        }

        public void setListaComOsDadosComparadosEntreExcelC200(List<String> listaComOsDadosComparadosEntreExcelC200) {
            this.listaComOsDadosComparadosEntreExcelC200 = listaComOsDadosComparadosEntreExcelC200;
        }

        public SPED0000 getSped0000() {
            return sped0000;
        }

        public void setSped0000(SPED0000 sped0000) {
            this.sped0000 = sped0000;
        }     
    }
}
