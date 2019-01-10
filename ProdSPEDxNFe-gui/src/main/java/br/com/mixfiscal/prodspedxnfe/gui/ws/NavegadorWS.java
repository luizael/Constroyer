package br.com.mixfiscal.prodspedxnfe.gui.ws;

import br.com.lulusoftwares.luluframework.jquery.JQueryResponse;
import br.com.lulusoftwares.luluframework.jquery.JQueryResult;
import br.com.mixfiscal.prodspedxnfe.gui.util.Constantes;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/navegadorWS")
public class NavegadorWS {
    @Context
    private ServletContext selectContext;
    
    @POST
    @Path("/selecionarPasta")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse selecionarPasta(SelecionarPastaParam param) {
        JQueryResponse resp = new JQueryResponse();
        String pathInicial = selectContext.getInitParameter(Constantes.PARAM_SERVER_PATH);
        if(pathInicial.charAt(pathInicial.length() -1) == File.separator.charAt(0))
            pathInicial = pathInicial.substring(0, pathInicial.lastIndexOf(File.separator));
        
        File files = null;
        FileInfo fi = null;
        List<FileInfo> listaArquivos = new ArrayList<>();
        try {            
            String path = StringUtil.isNullOrEmpty(param.getCaminho()) ? pathInicial : param.getCaminho();
            
            if (!path.contains(pathInicial))
                path = pathInicial + path; 
                        
            files  = new File(path); 
            if(!files.isDirectory())
                files = new  File(pathInicial);                
            
            listaArquivos = this.percorrerDiretorio(files, pathInicial);
            listaArquivos.sort(new Comparator<FileInfo>() { 
                @Override
                public int compare(FileInfo o1, FileInfo o2) {
                    if (o1.isDiretorio() && !o2.isDiretorio()) return -1;
                    else if (!o1.isDiretorio() && o2.isDiretorio()) return 1;
                    else return 0;
                }
            });            
                        
            if (param.getCaminho() != null && !path.equals(pathInicial.substring(0, pathInicial.lastIndexOf(File.separator)))) {                
                String backPath = path;
                
                if (path.lastIndexOf(File.separator) > -1)
                    backPath = path.substring(0, path.lastIndexOf(File.separator));
                
                fi = new FileInfo();
                fi.setNome("..");
                fi.setDiretorio(true);
                fi.setCaminhoAbsoluto(backPath);
                listaArquivos.add(0, fi);
            }              
            FileInfo localAtual = new FileInfo();
            localAtual.setNome(files.getName());
            localAtual.setCaminhoRelativo(files.getAbsolutePath().replace(pathInicial.substring(0, pathInicial.lastIndexOf(File.separator)), File.separator));
            localAtual.setCaminhoAbsoluto(files.getAbsolutePath());
            localAtual.setDiretorio(files.isDirectory());
            localAtual.setArquivos(listaArquivos);            
            resp.setObject(localAtual);
            resp.setResult(JQueryResult.Success);            
        }catch(NullPointerException ex){ 
            resp.setResult(JQueryResult.Error);
            files = new File(pathInicial); 
            listaArquivos = this.percorrerDiretorio(files, pathInicial);
            FileInfo arqResult = new FileInfo();
            FileInfo diretorioPadrao = new FileInfo(); 
            diretorioPadrao.setNome("..");
            diretorioPadrao.setDiretorio(true);
            listaArquivos.add(0,diretorioPadrao);
            arqResult.setCaminhoAbsoluto(pathInicial);
            arqResult.setCaminhoRelativo(arqResult.getCaminhoAbsoluto()
                    .replace(pathInicial.substring(0, pathInicial.lastIndexOf(File.separator)), File.separator));
            arqResult.setDiretorio(true);
            arqResult.setArquivos(listaArquivos);            
            resp.setObject(arqResult);
        }catch(Exception ex){
            resp.setResult(JQueryResult.Error);
            resp.setMessage(""+ex.getMessage());
            resp.returnException(ex);    
        }        
        return resp;
    }
    
    private List<FileInfo> percorrerDiretorio(File files, String pathInicial){
        List<FileInfo> listaArquivos = new ArrayList<>();
        FileInfo fi = null;
        for (File f : files.listFiles()) {
            fi = new FileInfo();
            fi.setNome(f.getName());
            fi.setCaminhoRelativo(f.getAbsolutePath()
                    .replace(pathInicial.substring(0, pathInicial.lastIndexOf(File.separator)), ""));
            fi.setCaminhoAbsoluto(f.getAbsolutePath());
            fi.setDiretorio(f.isDirectory());
            listaArquivos.add(fi);
        }
        return listaArquivos;
    }
    
    public static class SelecionarPastaParam {
        private String caminho;

        public String getCaminho() {
            return caminho;
        }

        public void setCaminho(String caminho) {
            this.caminho = caminho;
        }
    }
    
    public static class FileInfo {
        private String nome;
        private boolean diretorio;
        private String caminhoRelativo;
        private String caminhoRelativoSemArquivo;
        private String caminhoAbsoluto;
        private List<FileInfo> arquivos;
        
        public FileInfo() {
            arquivos = new ArrayList<>();
        }
        
        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public boolean isDiretorio() {
            return diretorio;
        }

        public void setDiretorio(boolean diretorio) {
            this.diretorio = diretorio;
        }

        public String getCaminhoRelativo() {
            return caminhoRelativo;
        }

        public void setCaminhoRelativo(String caminhoRelativo) {
            this.caminhoRelativo = caminhoRelativo;
        }

        public String getCaminhoRelativoSemArquivo() {
            return caminhoRelativoSemArquivo;
        }

        public void setCaminhoRelativoSemArquivo(String caminhoRelativoSemArquivo) {
            this.caminhoRelativoSemArquivo = caminhoRelativoSemArquivo;
        }
        
        public String getCaminhoAbsoluto() {
            return caminhoAbsoluto;
        }

        public void setCaminhoAbsoluto(String caminhoAbsoluto) {
            this.caminhoAbsoluto = caminhoAbsoluto;
        }

        public List<FileInfo> getArquivos() {
            return arquivos;
        }

        public void setArquivos(List<FileInfo> arquivos) {
            this.arquivos = arquivos;
        }
    }
    
}
