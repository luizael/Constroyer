package br.com.mixfiscal.prodspedxnfe.gui.ws;

import br.com.lulusoftwares.luluframework.jquery.JQueryGridParam;
import br.com.lulusoftwares.luluframework.jquery.JQueryGridData;
import br.com.lulusoftwares.luluframework.jquery.JQueryResponse;
import br.com.lulusoftwares.luluframework.jquery.JQueryResult;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoCST;
import br.com.mixfiscal.prodspedxnfe.domain.own.CST;
import br.com.mixfiscal.prodspedxnfe.services.CSTService;
import com.google.gson.Gson;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/cadastroCSTWS")
public class CadastroCSTWS {
    @Context
    private HttpServletRequest request;
    @Context
    private ServletContext servletContext;
   
    private Gson gson;
    private CSTService cstService;
    
    public CadastroCSTWS(){
        gson = new Gson();
        cstService = new CSTService();        
    }
    
    @POST
    @Path("/salvarCST")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse salvar(CSTItemParam param){
        JQueryResponse rs = new JQueryResponse();
        CST cst = new CST();
        
        try {           
            CST cstEquiv = new CST();
            if (param.getIdCSTEquivalente() != null)
                cstEquiv.setId(param.getIdCSTEquivalente());
           
            cst.setId(param.getId());
            cst.setCodigo(param.getCodigo());
            cst.setDescricao(param.getDescricao());
            cst.setTipo(param.getTipo()); 
            
            if (cstEquiv.getId() != null)
                cst.setCSTEquivalente(cstEquiv);
           
            if(cst.getId()==0){
                cstService.salvar(cst);
                rs.setMessage("Registro salvo com sucesso");
            }else{
                cstService.atualizar(cst);
                rs.setMessage("Registro atualizado com sucesso");
            }
            rs.setResult(JQueryResult.Success);
            rs.setObject(cst);
        } catch(Exception ex) {
            rs.setResult(JQueryResult.Error);
            rs.setObject(ex);
            rs.setMessage("erro ao salvar ou alterar o registro");
        }
        return rs;
    }
    
    @POST
    @Path("/editarCST")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse editar(CSTItemParam param){
        JQueryResponse rs = new JQueryResponse();
        CST cst = new CST();
        
        try {
            cst.setId(param.getId());
            cst = cstService.selecionarUm(cst);
            
            param.setId(cst.getId());
            param.setTipo(cst.getTipo());
            param.setCodigo(cst.getCodigo());
            param.setDescricao(cst.getDescricao());
            if (cst.getCSTEquivalente() != null)
                param.setIdCSTEquivalente(cst.getCSTEquivalente().getId());
            
            rs.setResult(JQueryResult.Success);
            rs.setObject(param);
        } catch(Exception ex) {
            rs.setResult(JQueryResult.Error);
            rs.setObject(ex);
            rs.setMessage("erro ao inserir registro");
        }
        
        return rs;
    }
    
    @POST
    @Path("/excluirCST")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse excluir(CSTItemParam param){
        JQueryResponse rs = new JQueryResponse();
        CST obj = new CST();
        try{
            obj.setId(param.getId());
            if(cstService.excluir(obj)){
                rs.setMessage("Registro excluido com sucesso");
                rs.setResult(JQueryResult.Success);
            }else{
                rs.setMessage("Nao foi possivel excluir o registro");
                rs.setResult(JQueryResult.Error);
            }
            rs.setObject(obj);                
        }catch(Exception ex){
            rs.setMessage(ex.getMessage());
            rs.setResult(JQueryResult.Error);
            rs.setObject(ex);
        }
        return rs;
    }
    
    @POST
    @Path("/listarCST")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryGridData<CSTGrideItemParam> listarCST(JQueryGridParam param ){
        List<CST> lista = new ArrayList<>();
        List<CSTGrideItemParam> listaCst = new ArrayList<>();
        try{
            lista = cstService.listar();
            CSTGrideItemParam itemParam;
            for(CST cst:lista ){
                itemParam = new CSTGrideItemParam();
                itemParam.setId(cst.getId());
                itemParam.setCodigo(cst.getCodigo());
                itemParam.setDescricao(cst.getDescricao());
                itemParam.setTipo(cst.getTipo());
                listaCst.add(itemParam);
            }
        }catch(Exception ex){}
        JQueryGridData data = new JQueryGridData();
        data.setPage(param.getPage());
        data.setRecords(cstService.getQtdTotalRegistros().intValue());
        data.setRows(listaCst);
        return data;
    }
    
    public static class CSTItemParam{
        private Integer id;
        private String codigo;
        private String descricao;
        private Integer tipo;
        private Integer idCSTEquivalente;
        
        public CSTItemParam(){}

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public Integer getTipo() {
            return tipo;
        }

        public void setTipo(Integer tipo) {
            this.tipo = tipo;
        }

        public Integer getIdCSTEquivalente() {
            return idCSTEquivalente;
        }

        public void setIdCSTEquivalente(Integer idCSTEquivalente) {
            this.idCSTEquivalente = idCSTEquivalente;
        }
    }
    
    public  static class CSTGrideItemParam{
        private Integer id;
        private String codigo;
        private String descricao;
        private Integer tipo;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public Integer getTipo() {
            return tipo;
        }

        public void setTipo(Integer tipo) {
            this.tipo = tipo;
        }
        
        
    }
    
}
