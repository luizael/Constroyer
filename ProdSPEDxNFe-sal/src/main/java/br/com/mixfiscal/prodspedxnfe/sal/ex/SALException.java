package br.com.mixfiscal.prodspedxnfe.sal.ex;

public class SALException extends Exception {
    public SALException() {
        super();
    }
    
    public SALException(String message) {
        super(message);
    }
    
    public SALException(Throwable cause) {
        super(cause);
    }
    
    public SALException(String message, Throwable cause) {
        super(message, cause);
    }
}
