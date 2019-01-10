
package br.com.mixfiscal.prodspedxnfe.domain.enums;


public enum ETipoOperacaoCliente {
    WebService(1),
    Excel(2);
    
    public int tipoOperacaoCliente;
    
    ETipoOperacaoCliente(int valor){
        tipoOperacaoCliente = valor;
    }
    
    public int getValor(){
        return this.tipoOperacaoCliente;
    }
}
