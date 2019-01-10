package br.com.mixfiscal.prodspedxnfe.domain.enums;

public enum ETipoCFOP {
    CompraDentroDoEstado(1),
    CompraForaDoEstado(2),
    CompraDoExterior(3),
    VendaDentroDoEstado(5),
    VendaForaDoEstado(6),
    VendaParaExterior(7);
    private int valor;
    
    ETipoCFOP(int cfop){
       valor = cfop;
    }
    public int getValor(){
        return valor;
    }
}
