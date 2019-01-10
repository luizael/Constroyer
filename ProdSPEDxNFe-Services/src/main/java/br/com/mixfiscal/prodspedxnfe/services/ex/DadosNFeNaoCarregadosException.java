package br.com.mixfiscal.prodspedxnfe.services.ex;

public class DadosNFeNaoCarregadosException extends Exception {
    public DadosNFeNaoCarregadosException() {
        super();
    }
    
    public DadosNFeNaoCarregadosException(String message) {
        super(message);
    }
    
    public DadosNFeNaoCarregadosException(Throwable cause) {
        super(cause);
    }
    
    public DadosNFeNaoCarregadosException(String message, Throwable cause) {
        super(message, cause);
    }
}
