
package br.com.mixfiscal.prodspedxnfe.sal;

import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributaria;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ELocalDestino;
import br.com.mixfiscal.prodspedxnfe.sal.ex.SALException;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
public class ConsultaTributacaoSal {
    private static final String URL_WEB_SERVICE = "http://ws.mixfiscal.com.br:80/ws/rest/consulta-tributacao/e0d33aa5027097b8f863dbb702eb32aa/0/%s";
    private Gson gson;
    private File[] arquivosJson;
    private Map<String, String> jsons;
    
    public ConsultaTributacaoSal(){
        gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        jsons = new HashMap<>();
    }
    public void lerDirJsons(String caminhoDirJsons) {
        File dirJsons = new File(caminhoDirJsons);
        arquivosJson = dirJsons.listFiles();
        for (File f : arquivosJson) {
            try {
                jsons.put(f.getName(), new String(Files.readAllBytes(Paths.get(f.getAbsolutePath()))));
            } catch(Exception ex) {
            }
        }
    }
    public ClassificacaoTributaria consultarProduto(String eanProduto) throws SALException {
        try {
            URL url = new URL(String.format(URL_WEB_SERVICE, eanProduto));
            HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();

            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Accept", "application/json");

            String result = converterInputStreamParaString(httpConn.getInputStream());
            return gson.fromJson(result, ClassificacaoTributaria.class);
        } catch(IOException | JsonSyntaxException ex) {
            throw new SALException(ex);
        }
    }
    public ClassificacaoTributaria consultarProduto(String eanProduto, boolean dir) throws SALException {
        try {
            Optional<String> optJson = jsons.keySet().stream().filter(j -> j.contains(eanProduto)).findFirst();
            String json = optJson.isPresent() ? optJson.get() : null;
            if (json == null)
                return null;            
            return gson.fromJson(jsons.get(json), ClassificacaoTributaria.class);            
        } catch(Exception ex) {
            throw new SALException(ex);
        }
    }
    private String converterInputStreamParaString(InputStream is) throws IOException {         
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String linha;
        
        while ((linha = br.readLine()) != null)
            sb.append(linha);
        
        return sb.toString();
    }
    
}
