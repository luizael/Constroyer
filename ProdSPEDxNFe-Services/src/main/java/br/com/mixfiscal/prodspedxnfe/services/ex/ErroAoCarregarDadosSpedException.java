package br.com.mixfiscal.prodspedxnfe.services.ex;

public class ErroAoCarregarDadosSpedException extends Exception {
    public ErroAoCarregarDadosSpedException() {
        super();
    }
    
    public ErroAoCarregarDadosSpedException(String message) {
        super(message);
    }
    
    public ErroAoCarregarDadosSpedException(Throwable cause) {
        super(cause);
    }
    
    public ErroAoCarregarDadosSpedException(String message, Throwable cause) {
        super(message, cause);
    }
}
