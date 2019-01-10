package br.com.mixfiscal.prodspedxnfe.gui.ws;

import br.com.lulusoftwares.luluframework.jquery.JQueryGridParam;
import br.com.lulusoftwares.luluframework.jquery.JQueryGridData;
import br.com.lulusoftwares.luluframework.jquery.JQueryResponse;
import br.com.lulusoftwares.luluframework.jquery.JQueryResult;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOP;
import br.com.mixfiscal.prodspedxnfe.services.CFOPService;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/cadastroCFOPWS")
public class CadastroCFOPWS {
    @Context 
    private HttpServletRequest request;
    
    @Context 
    private ServletContext servletContext;  
    
    private Gson gson;
    private CFOPService cfopBlo;
    
    public CadastroCFOPWS(){
        gson = new  Gson();  
        cfopBlo = new  CFOPService();
    }    
    @POST
    @Path("/listarCFOP")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryGridData<CFOPGrideItemParam>listarCFOP(JQueryGridParam jqParam) {  
        List<CFOP> lista = new ArrayList<>();
        List<CFOPGrideItemParam> listaGride = new ArrayList<>();
        
        try {
            lista = cfopBlo.listar(null, jqParam.getPage(), jqParam.getRows(), jqParam.getSidx(), jqParam.getSord());
            CFOPGrideItemParam itemGride;
            for(CFOP item: lista){
                itemGride = new CFOPGrideItemParam();
                itemGride.setCodigo(item.getCodigo());
                itemGride.setId(item.getId());
                itemGride.setNome(item.getNome());
                itemGride.setDescricao(item.getDescricao());
                listaGride.add(itemGride);
            }
        } catch(Exception ex){}        
        
        JQueryGridData<CFOPGrideItemParam> data = new JQueryGridData<>(); 
        data.setRows(listaGride);
        data.setPage(jqParam.getPage());
        data.setRecords(cfopBlo.getQtdTotalRegistros().intValue());
        data.setRegPerPag(jqParam.getRows());     
        
        return data;
    }
    
    @POST
    @Path("/salvarCFOP")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse salvar(CFOPitemParam param){
        JQueryResponse rs = new JQueryResponse();
        CFOP cfop = new CFOP();
        CFOP cfopEquiv = new CFOP();
        
        try {
            if (param.getIdCFOPEquivalente() != null)
                cfopEquiv.setId(param.getIdCFOPEquivalente());
            
            cfop.setCodigo(param.getCodigo());
            cfop.setNome(param.getNome());
            cfop.setDescricao(param.getDescricao());
            cfop.setId(param.getId());
            if (cfopEquiv.getId() != null)
                cfop.setCFOPEquivalente(cfopEquiv);
            
            if(cfop.getId() != 0){
                cfopBlo.atualizar(cfop);
                rs.setMessage("Atualizado com sucesso");
            }else{
                cfop.setId(null);
                cfopBlo.salvar(cfop);
                rs.setMessage("Salvo com sucesso");
            }
            
            rs.setObject(cfop);
            rs.setResult(JQueryResult.Success);
        }catch(Exception ex){
             rs.setMessage("erro ao salvar"+ex.getMessage());
             rs.setResult(JQueryResult.Error);
             rs.setObject(ex);
        }
        return rs;
    }
    
    @POST
    @Path("/editarCFOP")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse editar(CFOPitemParam param){
        JQueryResponse rs = new JQueryResponse();
        CFOP cfop = new CFOP();
        try{
            cfop.setId(param.getId());
            cfop = cfopBlo.selecionarUm(cfop);
            
            param.setId(cfop.getId());
            param.setCodigo(cfop.getCodigo());
            param.setNome(cfop.getNome());
            param.setDescricao(cfop.getDescricao());
            if (cfop.getCFOPEquivalente() != null)
                param.setIdCFOPEquivalente(cfop.getCFOPEquivalente().getId());
            
            rs.setObject(param);
            rs.setResult(JQueryResult.Success);
        }catch(Exception ex){
            rs.setMessage("Erro ao editar registro");
            rs.setResult(JQueryResult.Error);
            rs.setObject(ex);
        }
        return rs;
    }
    
    @POST
    @Path("/excluirCFOP")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse excluir(CFOPitemParam param){
        JQueryResponse rs = new JQueryResponse();
        CFOP cfop = new CFOP();
        try{
            cfop.setId(param.getId());
            if(cfopBlo.excluir(cfop)){
                rs.setMessage("Excluido com sucesso");
                rs.setResult(JQueryResult.Success);
            }else{
                rs.setMessage("CFOP já tem um equivalente registrado");
                rs.setResult(JQueryResult.Error);
            }
        }catch(Exception ex){
            rs.setMessage("CFOP já tem um equivalente registrado");
            rs.setResult(JQueryResult.Error);
            rs.setObject(ex);
        }
        return rs;
    }
    
    public static class CFOPitemParam{
        private Integer id;
        private String codigo;
        private String nome;
        private String descricao;
        private Integer idCFOPEquivalente;
            
        public CFOPitemParam(){}
        
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

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public Integer getIdCFOPEquivalente() {
            return idCFOPEquivalente;
        }

        public void setIdCFOPEquivalente(Integer idCFOPEquivalente) {
            this.idCFOPEquivalente = idCFOPEquivalente;
        }
    }
    
    public static class CFOPGrideItemParam{
        private Integer id;
        private String codigo;
        private String nome;
        private String descricao;
        
        public CFOPGrideItemParam(){}

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

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }       
        
    }
}
