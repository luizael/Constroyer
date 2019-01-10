package br.com.mixfiscal.prodspedxnfe.domain.ex;

public class EnumDesconhecidoException extends Exception {
    public EnumDesconhecidoException() {
        super();
    }
    
    public EnumDesconhecidoException(String message) {
        super(message);
    }
    
    public EnumDesconhecidoException(Throwable cause) {
        super(cause);
    }
    
    public EnumDesconhecidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
