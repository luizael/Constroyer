package br.com.mixfiscal.prodspedxnfe.services.ex;

public class EmpresaNaoEncontradaException extends Exception {
    public EmpresaNaoEncontradaException() {
        super();
    }
    
    public EmpresaNaoEncontradaException(String message) {
        super(message);
    }
    
    public EmpresaNaoEncontradaException(Throwable cause) {
        super(cause);
    }
    
    public EmpresaNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }    
}
