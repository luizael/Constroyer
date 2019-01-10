package br.com.mixfiscal.prodspedxnfe.domain.enums;


public enum ETipoModeloDocumento {
    NotaFiscal("01"),
    NotaFiscalAvulsa("1B"),
    NotaFiscalEletronica("55"),
    NotaFiscalConsumidorEletronico("65"); 
    private final String valor;
    ETipoModeloDocumento(String valor){
        this.valor = valor;
    }
    public boolean equalsName(String valor){
        return this.valor.equals(valor);
    }
    public String toString(){
     return this.valor;
    }
    
}
