package br.com.mixfiscal.prodspedxnfe.services.ex;

public class ArquivoSpedVazioException extends Exception {
    public ArquivoSpedVazioException() {
        super();
    }
    
    public ArquivoSpedVazioException(String message) {
        super(message);
    }
    
    public ArquivoSpedVazioException(Throwable cause) {
        super(cause);
    }
    
    public ArquivoSpedVazioException(String message, Throwable cause) {
        super(message, cause);
    }
}
