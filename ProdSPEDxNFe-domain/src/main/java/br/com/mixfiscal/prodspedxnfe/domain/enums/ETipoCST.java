package br.com.mixfiscal.prodspedxnfe.domain.enums;

public enum ETipoCST {
    ICMS(0),
    IPI(1),
    COFINS(2);
    private final int valor;
    ETipoCST(int valorCst){
        valor = valorCst;
    }
    public int getValor(){
        return valor;
    }
}
