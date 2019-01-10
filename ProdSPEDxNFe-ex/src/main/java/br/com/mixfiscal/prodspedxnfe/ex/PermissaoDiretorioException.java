package br.com.mixfiscal.prodspedxnfe.ex;

public class PermissaoDiretorioException extends Exception {
    public PermissaoDiretorioException() {
        super();
    }
    
    public PermissaoDiretorioException(String message) {
        super(message);
    }
    
    public PermissaoDiretorioException(Throwable cause) {
        super(cause);
    }
    
    public PermissaoDiretorioException(String message, Throwable cause) {
        super(message, cause);
    }
}
