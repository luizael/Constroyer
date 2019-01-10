
package br.com.mixfiscal.prodspedxnfe.domain.enums;


public enum EStatusJson {
    Sucesso(1), 
    TokenInformadoInválido(2), 
    CaptchaInformadoInválido(3),
    ProdutoNaoCadastrado(4),
    ProdutoEmClassificacao(5),
    ParametroObrigatorioNaoInformado(6),
    OProdutoIndicadoNaConsultaNaoExiteNoAmbienteDeHomologacao(7);
    
    public int statusJson;
    
    EStatusJson(int valor){
        statusJson = valor;
    }
    
    public int getValor(){
        return statusJson;
    }
}
