package br.com.mixfiscal.prodspedxnfe.domain.enums;


public enum EStatusAtualizacaoInfoFiscal {
    Pendente(0),
    Executando(1),
    Finalizado(2),
    Erro(3);
    
    
    private final int statusAtualizacaoInforFiscal;
    
    public int getStatusAtualizacaoInforFiscal(){
        return this.statusAtualizacaoInforFiscal;
    }
    
    EStatusAtualizacaoInfoFiscal(int valor){
        this.statusAtualizacaoInforFiscal = valor;
    }    
}
