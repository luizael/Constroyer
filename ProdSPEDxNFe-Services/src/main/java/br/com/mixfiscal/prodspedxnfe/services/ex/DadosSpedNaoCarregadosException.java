package br.com.mixfiscal.prodspedxnfe.services.ex;

public class DadosSpedNaoCarregadosException extends Exception {
    public DadosSpedNaoCarregadosException() {
        super();
    }
    
    public DadosSpedNaoCarregadosException(String message) {
        super(message);
    }
    
    public DadosSpedNaoCarregadosException(Throwable cause) {
        super(cause);
    }
    
    public DadosSpedNaoCarregadosException(String message, Throwable cause) {
        super(message, cause);
    }
}
