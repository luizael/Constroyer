
package br.com.mixfiscal.prodspedxnfe.sal;

import br.com.mixfiscal.prodspedxnfe.domain.enums.EStatusJson;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributaria;
import br.com.mixfiscal.prodspedxnfe.domain.own.PisCofins;
import br.com.mixfiscal.prodspedxnfe.domain.wsmix.ParamJson;
import br.com.mixfiscal.prodspedxnfe.domain.wsmix.ResponseJson;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

public class ConsultaMixFiscal {
    private static final String URL_WS = "http://ws.mixfiscal.com.br/ws/rest/consulta-tributacao";
    private ClientResponse iniciarConexaoWS(String uri){       
        Client client = Client.create();
        ClientResponse response = null;
        try{
            WebResource resource = client.resource(uri);
            response = resource.accept("application/json").get(ClientResponse.class);
        }catch(Exception ex){}
        return response;
    }
    
    private  ResponseJson gerarCaptcha(ParamJson param)throws IOException{
        ResponseJson resp = new ResponseJson();
        String path = URL_WS + "gera-captcha/";
        if(param != null){
            try{
                ClientResponse clResp = iniciarConexaoWS(this.montarURI2(param));
                if(clResp.getStatus() != 200 )
                     throw new RuntimeException("Falha ao conectar no webservice " + clResp.getStatus());

                String json = clResp.getEntity(String.class);
                Gson gson = new Gson();
                resp = gson.fromJson(json,ResponseJson.class);
                return resp;
            }catch(Exception ex){}
        }
        return resp;
    }
    
    private String montarParametroURI(ParamJson param){
        Class<ParamJson> classe = ParamJson.class;
        String path = "";
        Object obj = null;
            for(java.lang.reflect.Field campo: param.getClass().getDeclaredFields()){
              try{
                campo.setAccessible(true);
                obj = campo.get(param);
                if(obj != null && obj != "")
                    path += "/" + obj.toString().trim();
              
              }catch(Exception ex){}
            }
        return path;
    }
    
    private URI montarURI(ParamJson param){
        URI uri = null;
        URI u = null;
        try{
           uri = new URI(URL_WS + this.montarParametroURI(param));
           
        }catch(URISyntaxException ex){}
        return uri;
    }
    private String montarURI2(ParamJson param){
       String uri = null;
        try{
           uri = URL_WS + this.montarParametroURI(param);
        }catch(Exception ex){}
        return uri;
    }
    public ClassificacaoTributaria consultarProduto(ParamJson param){
        String path = URL_WS + "consulta-tributacao";
        String status[] = new String[2];
        String json = "";
        ClassificacaoTributaria resp = null;
        try{
                ClientResponse clResp = iniciarConexaoWS(this.montarURI2(param));
                if(clResp.getStatus() != 200 )
                     throw new RuntimeException("Falha ao conectar no webservice " + clResp.getStatus());
                
                json = clResp.getEntity(String.class);
                Gson gson = new  GsonBuilder().create();
               resp = gson.fromJson(json,ClassificacaoTributaria.class);
                
        }catch(Exception ex){}
        return resp;
    }
}
