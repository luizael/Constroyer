package br.com.mixfiscal.prodspedxnfe.util;

import br.com.mixfiscal.prodspedxnfe.ex.PermissaoDiretorioException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUtil {
    private static Logger log;
    public FileUtil(){
        this.log = LogManager.getLogger(FileUtil.class);
    }
    
    public static void testarPermissaoDiretorio(String path) throws PermissaoDiretorioException {        
        try {
            File dir = new File(String.format("%s%s", path, File.separator + "teste"));
            File file = new File(String.format("%s%s", dir.getPath(), File.separator + "teste.txt"));
            dir.mkdir();
            file.createNewFile();
            file.delete();
            dir.delete();            
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new PermissaoDiretorioException(
                    String.format("Houve um de permissão no diretório '%s'. Mensagem: %s", 
                                  path,
                                  ex.getMessage()), 
                    ex);
        }
    }
    
    public static void criarArquivo(String path, StringBuilder sbConteudo) throws PermissaoDiretorioException {
        try {            
            if (sbConteudo.length() > 0) {                
                File csv = new File(path);
                FileWriter fw = new FileWriter(csv);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(sbConteudo.toString());
                bw.close();
                fw.close();                
            }
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new PermissaoDiretorioException(ex.getMessage(), ex);
        }
    }
    
    public static boolean criarArquivo(InputStream input, FileInfo info) throws PermissaoDiretorioException{
        int read = 0;
        try{
            OutputStream output = new FileOutputStream(new File(info.getLocalArmazenamento() + info.getNome()));
            while( (read = input.read(info.getBytes())) != -1){
                output.write(info.getBytes(),0, read);
            }
            output.flush();
            output.close();
            input.close();
        }catch(IOException ex){
            log.error(ex.getMessage(), ex);
            return false;
        } 
        return true;
    }    
    
    public static boolean excluirArquivoNoServidor(String caminho, String nome){
        String path = caminho + nome;
        try{
            excluirArquivo(path);
        }catch(IOException ex){
            log.error(ex.getMessage(), ex);
            return false;
        }
        return true;
    }
    
    private static void excluirArquivo(String path) throws IOException{
        File file = new File(path);
        if(file.exists() && file.isFile())
            file.delete();        
    }    
}
