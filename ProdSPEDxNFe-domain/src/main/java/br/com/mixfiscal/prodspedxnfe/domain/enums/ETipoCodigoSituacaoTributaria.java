
package br.com.mixfiscal.prodspedxnfe.domain.enums;

public enum ETipoCodigoSituacaoTributaria {
    DocumentoRegular(0),
    DocumentoRegularExtemporaneo(1),
    DocumentoCancelado(2),
    DocumentoCanceladoExtemporaneo(3),
    NfeOuCteDenegado(4),
    DocumentoFiscalComplementar(6),
    DocumentoFiscalComplementarExtemporaneo(7),
    DocumentoFiscalEmitidoComBaseEmRegimeEspecialOuNormaEspecifica(8);
    private int valor;
    ETipoCodigoSituacaoTributaria(int valorCodigoSit){
        valor = valorCodigoSit;
    }
    public int getValor(){
        return valor;
    } 
}
