
package br.com.mixfiscal.prodspedxnfe.gui.ws;

import br.com.lulusoftwares.luluframework.jquery.JQueryGridParam;
import br.com.lulusoftwares.luluframework.jquery.JQueryGridData;
import br.com.lulusoftwares.luluframework.jquery.JQueryResponse;
import br.com.lulusoftwares.luluframework.jquery.JQueryResult;
import br.com.mixfiscal.prodspedxnfe.domain.own.Cliente;
import br.com.mixfiscal.prodspedxnfe.services.ClienteService;
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

@Path("/cadastroClienteWS")
public class CadastroClienteWS {
    @Context 
    private HttpServletRequest request;
    
    @Context 
    private ServletContext servletContext;  
    
    private Gson gson;
    ClienteService clienteBlo;
    
    public CadastroClienteWS(){
        gson = new  Gson(); 
        clienteBlo = new ClienteService();
    }
    
    @POST
    @Path("/selecionarUmCliente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse selecionarUm(ClienteItemParam param){
        JQueryResponse resp = new JQueryResponse();
        Cliente cliente = null;
        try{
            if(param != null){
                cliente = new Cliente();
                cliente.setId(param.getId());
                cliente = clienteBlo.selecionarUm(cliente);
                param.setCnpj(cliente.getCnpj().trim());
                param.setNome(cliente.getNome().trim());
                param.setToken(cliente.getToken().trim());
                param.setIdView(cliente.getIdView());
            }
            resp.setObject(param);
            resp.setResult(JQueryResult.Success);
        }catch(Exception ex){
            resp.setMessage(ex.getMessage());
            resp.setResult(JQueryResult.Error);
        }
        return resp;
    }
    
    @POST
    @Path("/listarCliente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryGridData<ClienteGrideItemParam>listarCliente(JQueryGridParam jqParam) {  
        List<Cliente> lista = new ArrayList<>();
        List<ClienteGrideItemParam> listaGride = new ArrayList<>();
        try {
            lista = clienteBlo.listar(null, jqParam.getPage(), jqParam.getRows(), jqParam.getSidx(), jqParam.getSord());
            ClienteGrideItemParam itemGride;
            for(Cliente item: lista){
                itemGride = new ClienteGrideItemParam();
                itemGride.setToken(item.getToken());
                itemGride.setId(item.getId());
                itemGride.setNome(item.getNome());
                itemGride.setCnpj(item.getCnpj());
                itemGride.setIdView(item.getIdView());
                listaGride.add(itemGride);                
            }
        } catch(Exception ex){}        
        
        JQueryGridData<ClienteGrideItemParam> data = new JQueryGridData<>(); 
        data.setRows(listaGride);
        data.setPage(jqParam.getPage());
        data.setRecords(clienteBlo.getQtdTotalRegistros().intValue());
        data.setRegPerPag(jqParam.getRows());   
        return data;
    }
    
    @POST
    @Path("/salvarCliente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public JQueryResponse salvar(ClienteItemParam param){
        JQueryResponse rs = new JQueryResponse();
        Cliente cliente = new Cliente();
        try {
            if(param != null){
                cliente.setNome(param.getNome());
                cliente.setCnpj(param.getCnpj());
                cliente.setToken(param.getToken());
                cliente.setId(param.getId());
                cliente.setIdView(param.getIdView());
                
                if(param.getId() > 0){
                    clienteBlo.atualizar(cliente);
                    rs.setMessage("Atualizado com sucesso");
                }else{
                    Cliente cli = new Cliente();
                    cli.setCnpj(cliente.getCnpj().trim());
                    cli = clienteBlo.selecionarUm(cli);
                    if(cli != null && cli.getId() > 0){
                        rs.setMessage("Cliente já esta cadastrado");
                        rs.setObject(cli);
                        rs.setResult(JQueryResult.Error);
                        return rs;
                    }else{
                        cliente.setId(0);
                        clienteBlo.salvar(cliente);
                        rs.setMessage("Salvo com sucesso");
                    }
                }
                rs.setObject(cliente);
                rs.setResult(JQueryResult.Success);
            }else{
                rs.setObject(null);
                rs.setResult(JQueryResult.Error);
            }
        }catch(Exception ex){
             rs.setMessage("erro ao salvar"+ex.getMessage());
             rs.setResult(JQueryResult.Error);
             rs.setObject(ex);
        }
        return rs;
    }
    
    @POST
    @Path("/excluirCliente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse excluirUsuario(ClienteItemParam param){
        JQueryResponse resp = new JQueryResponse();
        Cliente cliente = null;
        try {
                if(param != null){
                    cliente = new Cliente();
                    cliente.setId(param.getId());
                    clienteBlo.excluir(cliente);
                    resp.setMessage("Registro excluido com sucesso");                    
                }
            resp.setResult(JQueryResult.Success);           
        }catch(Exception ex){
            resp.setResult(JQueryResult.Error);
            resp.setMessage("Erro ao excluir o registro, " + ex.getMessage());
        }
        return resp;
    }
    /*
    id: $(Cliente_idtxtHdnId).val()== ""? 0:$(Cliente_idtxtHdnId).val(),
                token: $(Cliente_txtToken).val(),
                nome: $(Cliente_txtNome).val(),
                cnpj: $(Cliente_txtCnpj).val(),
                view:$(Cliente_txtIdView).val(),
    */
    public static class ClienteItemParam{
        private int id;
        private String nome;
        private String cnpj;
        private String token;
        private Integer idView;        

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCnpj() {
            return cnpj;
        }

        public void setCnpj(String cnpj) {
            this.cnpj = cnpj;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Integer getIdView() {
            return idView;
        }
        
        public void setIdView(Integer idView) {
            this.idView = idView;
        }
     }
     
    public static class ClienteGrideItemParam{
        private int id;
        private String nome;
        private String cnpj;
        private String token;
        private Integer idView;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCnpj() {
            return cnpj;
        }

        public void setCnpj(String cnpj) {
            this.cnpj = cnpj;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Integer getIdView() {
            return idView;
        }

        public void setIdView(Integer idView) {
            this.idView = idView;
        }            
    }    
}
