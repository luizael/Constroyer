package br.com.mixfiscal.prodspedxnfe.domain.enums;

import br.com.mixfiscal.prodspedxnfe.domain.ex.EnumDesconhecidoException;

public enum ETipoEmissao {
    EmissaoPropria((byte)0),
    Terceiros((byte)1);
    
    private final byte emitente;
    
    private ETipoEmissao(byte emitente) {
        this.emitente = emitente;
    }

    public byte getEmitente() {
        return emitente;
    }
    
    public static ETipoEmissao deCodigo(String codigo) throws EnumDesconhecidoException, NumberFormatException {
        byte iCodigo = Byte.parseByte(codigo);
        return deCodigo(iCodigo);
    }
    
    public static ETipoEmissao deCodigo(byte codigo) throws EnumDesconhecidoException {
        for (ETipoEmissao tipoItem : ETipoEmissao.values()) {
            if (tipoItem.getEmitente() == codigo)
                return tipoItem;
        }
        throw new EnumDesconhecidoException();
    }
}
