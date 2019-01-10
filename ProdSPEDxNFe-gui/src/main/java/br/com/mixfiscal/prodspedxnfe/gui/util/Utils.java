package br.com.mixfiscal.prodspedxnfe.gui.util;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class Utils {
    public static String removerPrefixo(String caminho) {
        int idx = caminho.indexOf(":\\");
        if (idx >= 0) {
            if (idx + 3 < caminho.length() - 1)
                return caminho.substring(idx + 3);
            return caminho.substring(idx);
        }
        return caminho;
    }
    
    public static void forcarDownload(String contentType, byte[] data, String nomeArquivo) throws Exception {
        FacesContext cx = FacesContext.getCurrentInstance();
        ExternalContext ecx = cx.getExternalContext();
        
        ecx.responseReset();
        ecx.setResponseContentType(contentType);
        ecx.setResponseContentLength(data.length);
        ecx.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nomeArquivo + "\"");
        
        OutputStream ostr = ecx.getResponseOutputStream();
        ostr.write(data);
    }
    
    public static <T> List<T> paginarLista(List<T> lista, int pag, int qtdRegPorPag) {
        List<T> listaPaginada = new ArrayList<>();
        for (int p = ((pag - 1) * qtdRegPorPag), i = 0; i < qtdRegPorPag && p < lista.size(); p++, i++)
            listaPaginada.add(lista.get(p));
        return listaPaginada;
    }
    
    public static String retornarCaminhoServidor(String serverPath, String caminhoRelativo) {
        return String.format("%s%s", serverPath, Utils.removerPrefixo(caminhoRelativo));
    }
    
    public static String retornarCaminhoServidor(String caminhoRelativo) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String serverPath = ctx.getExternalContext().getInitParameter(Constantes.PARAM_SERVER_PATH);    
        return retornarCaminhoServidor(serverPath, caminhoRelativo);
    }
    
}
