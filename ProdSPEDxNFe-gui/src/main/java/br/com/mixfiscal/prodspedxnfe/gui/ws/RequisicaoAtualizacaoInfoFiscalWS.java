
package br.com.mixfiscal.prodspedxnfe.gui.ws;

import br.com.lulusoftwares.luluframework.jquery.JQueryGridData;
import br.com.lulusoftwares.luluframework.jquery.JQueryGridParam;
import br.com.lulusoftwares.luluframework.jquery.JQueryResponse;
import br.com.lulusoftwares.luluframework.jquery.JQueryResult;
import br.com.mixfiscal.prodspedxnfe.domain.enums.EFormatarCodigoInterno;
import br.com.mixfiscal.prodspedxnfe.domain.enums.EStatusAtualizacaoInfoFiscal;
import br.com.mixfiscal.prodspedxnfe.domain.own.Cliente;
import br.com.mixfiscal.prodspedxnfe.domain.own.RequisicaoAtualizacaoInfoFiscal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import br.com.mixfiscal.prodspedxnfe.util.FileUtil;
import br.com.mixfiscal.prodspedxnfe.util.FileInfo;
import br.com.mixfiscal.prodspedxnfe.services.ClienteService;
import br.com.mixfiscal.prodspedxnfe.gui.config.JobManagerConfig;
import br.com.mixfiscal.prodspedxnfe.services.RequisicaoAtualizacaoInfoFiscalService;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import javafx.scene.chart.PieChart.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/reqInfoFiscalWs")
public class RequisicaoAtualizacaoInfoFiscalWS {
    private final RequisicaoAtualizacaoInfoFiscalService requisicaoAtualizacaoInfoFiscalBlo;
    private final Logger log;
    private static String menssageReq;
    public String getMenssageReq(){
        return menssageReq;
    }
    public void setMenssageReq(String valor){
        menssageReq = valor;
    }
    public RequisicaoAtualizacaoInfoFiscalWS(){
        requisicaoAtualizacaoInfoFiscalBlo = new RequisicaoAtualizacaoInfoFiscalService();
        this.log = LogManager.getLogger(RequisicaoAtualizacaoInfoFiscalWS.class);
    }
    
    @Path("/reqInfoFiscalSalvar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public JQueryResponse salvarRequisicaoAtualizacaoInfoFiscal(
            @FormDataParam("upFile") InputStream input,
            @FormDataParam("upFile") FormDataContentDisposition info,
            @FormDataParam("idCliente") String idCliente,
            @FormDataParam("cbFormatarCodigoInterno") String formatarCodigoInterno){ 
            JQueryResponse resp = new JQueryResponse();           
            FileInfo fileInfo = new FileInfo();
            JobManagerConfig jobManagerConfig = JobManagerConfig.getInstance();            
            String caminho = jobManagerConfig.getCaminhoCsv();
            try{
                if(caminho != null){
                    if(!StringUtil.isNullOrEmpty(idCliente)){
                        Cliente cliente = new Cliente();                        
                        RequisicaoAtualizacaoInfoFiscal reqFiscal = new RequisicaoAtualizacaoInfoFiscal();
                        cliente.setId(Integer.parseInt(idCliente));
                        reqFiscal.setCliente(cliente);
                        reqFiscal.setFormatarCodigo(EFormatarCodigoInterno.NAO);//seta como default a formatação como nao
                        fileInfo.setNome(info.getFileName());
                        fileInfo.setLocalArmazenamento(caminho);
                        fileInfo.setBytes(new byte[1024 * 5]);//definindo o buffer
                        if(!StringUtil.isNullOrEmpty(formatarCodigoInterno))
                            reqFiscal.setFormatarCodigo(formatarCodigoInterno.equals(EFormatarCodigoInterno.SIM.toString())?EFormatarCodigoInterno.SIM :EFormatarCodigoInterno.NAO);  
                        try{
                            requisicaoAtualizacaoInfoFiscalBlo.salvarComArquivo(input, fileInfo, reqFiscal);
                            this.setMenssageReq(requisicaoAtualizacaoInfoFiscalBlo.getMenssageReq());
                            resp.setMessage(this.getMenssageReq());
                            resp.setResult(JQueryResult.Success);
                        }catch(Exception ex){
                            resp.setMessage(ex.getMessage());
                            resp.setResult(JQueryResult.Error);
                        }                       
                    }else{
                        this.setMenssageReq("Selecione um cliente");
                        resp.setMessage(this.getMenssageReq());
                        resp.setResult(JQueryResult.Error);
                    }
                }else{
                    this.setMenssageReq("Não existe local definido para armazenar o arquivo");
                    resp.setMessage(this.getMenssageReq());
                    resp.setResult(JQueryResult.Error);
                }
                resp.setObject(fileInfo);
            }catch(Exception ex){
                this.setMenssageReq("Não foi possivel enviar o arquivo");
                resp.setMessage(this.getMenssageReq());
                resp.setObject(ex.getMessage());
                resp.setResult(JQueryResult.Error);
            }
         return resp;
    }
   
    @POST
    @Path("/reqInfoFiscalExcluir")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse excluirRequisicaoAtualizacaoInfoFiscal(RequisicaoAtualizacaoInfoFiscalItemParam param){
        JQueryResponse resp = new   JQueryResponse();
        RequisicaoAtualizacaoInfoFiscal req = new RequisicaoAtualizacaoInfoFiscal();
        JobManagerConfig jobManagerConfig = JobManagerConfig.getInstance();  
        boolean retorno = false;
        try{
            req.setIdRequisicaoAtualizacaoInfoFiscal(param.getIdRequisicaoAtualizacaoInfoFiscal()); 
            retorno = requisicaoAtualizacaoInfoFiscalBlo.excluir(req,jobManagerConfig.getCaminhoCsv());
            if(retorno){
                resp.setMessage("Excluido com sucesso");
                resp.setResult(JQueryResult.Success);
            }else{
                resp.setMessage("Nao foi possivel excluir o registro devido ao seu status atual");
                resp.setResult(JQueryResult.Error);
            }
        }catch(Exception ex){
            resp.setMessage("Erro ao excluir " + ex.getMessage());
            resp.setResult(JQueryResult.Error);
            resp.setObject(ex);        
        }
        return resp;
    }
    
    @POST
    @Path("/listarReqInfoFiscal")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryGridData<RequisicaoAtualizacaoInfoFiscalWS.GrideRequisicaoAtualizacaoInfoFiscalItemParam>listarRequisicaoAtualizacaoInfoFiscal(JQueryGridParam jqParam) {  
        List<RequisicaoAtualizacaoInfoFiscal> lista = new ArrayList<>();
        List<RequisicaoAtualizacaoInfoFiscalWS.GrideRequisicaoAtualizacaoInfoFiscalItemParam> listaGride = new ArrayList<>();
        try {
            lista = requisicaoAtualizacaoInfoFiscalBlo.listar(null, jqParam.getPage(), jqParam.getRows(), jqParam.getSidx(), jqParam.getSord());
            RequisicaoAtualizacaoInfoFiscalWS.GrideRequisicaoAtualizacaoInfoFiscalItemParam itemGride;            
            
            for(RequisicaoAtualizacaoInfoFiscal item: lista){
                try{
                    itemGride = new RequisicaoAtualizacaoInfoFiscalWS.GrideRequisicaoAtualizacaoInfoFiscalItemParam();
                    itemGride.setIdRequisicaoAtualizacaoInfoFiscal(item.getIdRequisicaoAtualizacaoInfoFiscal());
                    itemGride.setNomeArquivo(item.getNomeArquivo());
                    itemGride.setDataCadastro(item.getDataCadastro());
                    if(item.getFormatarCodigo() != null){
                        itemGride.setFormatarCodigo(item.getFormatarCodigo().toString());
                    }else{
                        itemGride.setFormatarCodigo(EFormatarCodigoInterno.NAO.toString());
                    }
                    if(item.getCliente() != null && !StringUtil.isNullOrEmpty(item.getCliente().getNome()))
                        itemGride.setNomeCliente(item.getCliente().getNome());
                     
                    itemGride.setStatusRequisicao(item.getStatusRequisicao().toString());
                    listaGride.add(itemGride); 
                }catch(Exception ex){
                    this.log.error(ex.getMessage(), ex);
                }
            }            
        } catch(Exception ex){
            this.log.error(ex.getMessage(), ex);
        }
        JQueryGridData<RequisicaoAtualizacaoInfoFiscalWS.GrideRequisicaoAtualizacaoInfoFiscalItemParam> data = new JQueryGridData<>(); 
        data.setRows(listaGride);
        data.setPage(jqParam.getPage());
        data.setRecords(requisicaoAtualizacaoInfoFiscalBlo.getQtdTotalRegistros().intValue());
        data.setRegPerPag(jqParam.getRows());   
        return data;
    }
    
    @POST
    @Path("/obterStatus")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse obterStatus(){
        JQueryResponse resp = new JQueryResponse();
        resp.setMessage(this.getMenssageReq());
        resp.setObject(null);
        resp.setResult(JQueryResult.Success);
        return resp;
    }
    
    public static class GrideRequisicaoAtualizacaoInfoFiscalItemParam{
        private Integer idRequisicaoAtualizacaoInfoFiscal;
        private String nomeCliente;
        private String nomeArquivo;
        private Date dataCadastro;
        private String statusRequisicao;
        private String formatarCodigo;        
        public Integer getIdRequisicaoAtualizacaoInfoFiscal() {
            return idRequisicaoAtualizacaoInfoFiscal;
        }
        public void setIdRequisicaoAtualizacaoInfoFiscal(Integer idRequisicaoAtualizacaoInfoFiscal) {
            this.idRequisicaoAtualizacaoInfoFiscal = idRequisicaoAtualizacaoInfoFiscal;
        }
        public String getNomeCliente() {
            return nomeCliente;
        }
        public void setNomeCliente(String nomeCliente) {
            this.nomeCliente = nomeCliente;
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
        public String getStatusRequisicao() {
            return statusRequisicao;
        }
        public void setStatusRequisicao(String statusRequisicao) {
            this.statusRequisicao = statusRequisicao;
        }
        public String getFormatarCodigo() {
            return formatarCodigo;
        }
        public void setFormatarCodigo(String formataCodigo) {
            this.formatarCodigo = formataCodigo;
        }        
    }    
    public static class RequisicaoAtualizacaoInfoFiscalItemParam{
        private Integer idRequisicaoAtualizacaoInfoFiscal;
        private String nomeCliente;
        private String nomeArquivo;
        private Date dataCadastro;
        private String statusRequisicao;
        private Integer formataCodigo;

        public Integer getIdRequisicaoAtualizacaoInfoFiscal() {
            return idRequisicaoAtualizacaoInfoFiscal;
        }
        public void setIdRequisicaoAtualizacaoInfoFiscal(Integer idRequisicaoAtualizacaoInfoFiscal) {
            this.idRequisicaoAtualizacaoInfoFiscal = idRequisicaoAtualizacaoInfoFiscal;
        }
        public String getNomeCliente() {
            return nomeCliente;
        }
        public void setNomeCliente(String nomeCliente) {
            this.nomeCliente = nomeCliente;
        }
        public String getNomeArquivo() {
            return nomeArquivo;
        }
        public void setNomeArquivo(String nomeArquivo) {
            this.nomeArquivo = nomeArquivo;
        }
        public Date getDataRequisicao() {
            return dataCadastro;
        }
        public void setDataRequisicao(Date dataRequisicao) {
            this.dataCadastro = dataRequisicao;
        }
        public String getStatusRequisicao() {
            return statusRequisicao;
        }
        public void setStatusRequisicao(String statusRequisicao) {
            this.statusRequisicao = statusRequisicao;
        }
        public Date getDataCadastro() {
            return dataCadastro;
        }
        public void setDataCadastro(Date dataCadastro) {
            this.dataCadastro = dataCadastro;
        }
        public Integer getFormataCodigo() {
            return formataCodigo;
        }
        public void setFormataCodigo(Integer formataCodigo) {
            this.formataCodigo = formataCodigo;
        }        
    }
}
