package br.com.mixfiscal.prodspedxnfe.gui.ws;

import br.com.lulusoftwares.luluframework.jquery.JQueryAutoCompleteItem;
import br.com.lulusoftwares.luluframework.jquery.JQueryAutoCompleteParam;
import br.com.lulusoftwares.luluframework.jquery.JQueryGridData;
import br.com.lulusoftwares.luluframework.jquery.JQueryGridFilter;
import br.com.lulusoftwares.luluframework.jquery.JQueryGridParam;
import br.com.lulusoftwares.luluframework.jquery.JQueryResponse;
import br.com.lulusoftwares.luluframework.jquery.JQueryResult;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOP;
import br.com.mixfiscal.prodspedxnfe.util.Criptografia;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.google.gson.Gson;
import br.com.mixfiscal.prodspedxnfe.services.UsuarioService;
import br.com.mixfiscal.prodspedxnfe.domain.own.Usuario;
import java.util.ArrayList;
import java.util.List;

@Path("/usuarioWS")
public class UsuarioWS {
    @Context
    private HttpServletRequest request;
    @Context
    private ServletContext servletContext;
    
    private Gson gson;
    private UsuarioService usuarioServ;
    
    public UsuarioWS(){
         gson = new  Gson();
         usuarioServ = new UsuarioService();
    }
    
    @POST
    @Path("/salvarUsuario")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse salvarUsuario(UsuarioParam usuarioParam){
        JQueryResponse resp = new JQueryResponse();
        Usuario usuario = null;
        try {
                if(usuarioParam != null){
                    usuario = new Usuario();
                    usuario.setId(usuarioParam.getId());
                    usuario.setNome(usuarioParam.getNome());
                    usuario.setEmail(usuarioParam.getEmail());
                    usuario.setUsuario(usuarioParam.getUsuario());
                    usuario.setSenha(Criptografia.converterParaMD5(usuarioParam.getSenha()));
                    if(usuario.getId() > 0){
                        usuarioServ.atualizar(usuario);
                        resp.setMessage("Registro atualizado com sucesso");
                    }else{
                         usuarioServ.salvar(usuario);
                         resp.setMessage("Registro salvo com sucesso");
                    }
                }
            resp.setResult(JQueryResult.Success);           
        }catch(Exception ex){
            resp.setResult(JQueryResult.Error);
            resp.setMessage("Erro ao salvar o registro" + ex.getMessage());
        }
        return resp;
    }
    
    @POST
    @Path("/excluirUsuario")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse excluirUsuario(UsuarioParam usuarioParam){
        JQueryResponse resp = new JQueryResponse();
        Usuario usuario = null;
        try {
                if(usuarioParam != null){
                    usuario = new Usuario();
                    usuario.setId(usuarioParam.getId());
                    usuarioServ.excluir(usuario);
                    resp.setMessage("Registro excluido com sucesso");                    
                }
            resp.setResult(JQueryResult.Success);           
        }catch(Exception ex){
            resp.setResult(JQueryResult.Error);
            resp.setMessage("Erro ao excluir o registro, " + ex.getMessage());
        }
        return resp;
    }
    
    @POST
    @Path("/selecionarUmUsuario")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse selecionarUm(UsuarioParam usuarioParam){
        JQueryResponse resp = new JQueryResponse();
        Usuario user = null;
        try{
            if(usuarioParam != null){
                user = new Usuario();
                user.setId(usuarioParam.getId());
                user = usuarioServ.selecionarUm(user);
            }
            resp.setObject(user);
            resp.setResult(JQueryResult.Success);
        }catch(Exception ex){
            resp.setMessage(ex.getMessage());
            resp.setResult(JQueryResult.Error);
        }
        return resp;
    }
    
    @POST
    @Path("/listarUsuario")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<GrideUsuarioParam> listarUsuario() {        
        List<GrideUsuarioParam> lista = new ArrayList<>();
        Usuario usuario = new Usuario();       
        try {
            List<Usuario> listaUsuario = usuarioServ.listar(usuario, -1, -1, "", "");
            listaUsuario.stream().forEach((user) -> {
                GrideUsuarioParam item = new GrideUsuarioParam();
                item.setId(user.getId());
                item.setNome(user.getNome());
                item.setEmail(user.getEmail());
                item.setUsuario(user.getUsuario());
                item.setSenha(user.getSenha());
                lista.add(item);
            });
        } catch(Exception ex) {}
        return lista;
    }
      
    public static class UsuarioParam{
        private int id;
        private String nome;
        private String email;
        private String usuario;
        private String senha;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }        
    }
    
    public static class GrideUsuarioParam{
        private int id;
        private String nome;
        private String email;
        private String usuario;
        private String senha;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }        
    }
}
