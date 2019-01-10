package br.com.mixfiscal.prodspedxnfe.services.ex;

public class ErroAoCarregarDadosNFeException extends Exception {
    public ErroAoCarregarDadosNFeException() {
        super();
    }
    
    public ErroAoCarregarDadosNFeException(String message) {
        super(message);
    }
    
    public ErroAoCarregarDadosNFeException(Throwable cause) {
        super(cause);
    }
    
    public ErroAoCarregarDadosNFeException(String message, Throwable cause) {
        super(message, cause);
    }
}
