package br.com.mixfiscal.prodspedxnfe.domain.enums;

import br.com.mixfiscal.prodspedxnfe.domain.ex.EnumDesconhecidoException;

public enum ELocalDestino {
    OperacaoInterna((byte)1),
    OperacaoInterestadual((byte)2),
    OperacaoComExterior((byte)3);
    
    private final byte idLocalDestino;
    
    private ELocalDestino(byte idLocalDestino) {
        this.idLocalDestino = idLocalDestino;
    }

    public byte getIdLocalDestino() {
        return idLocalDestino;
    }
    
    public static ELocalDestino deCodigo(String codigo) throws EnumDesconhecidoException, NumberFormatException {
        byte iCodigo = Byte.parseByte(codigo);
        return deCodigo(iCodigo);
    }
    
    public static ELocalDestino deCodigo(byte codigo) throws EnumDesconhecidoException {
        for (ELocalDestino tipoItem : ELocalDestino.values()) {
            if (tipoItem.getIdLocalDestino() == codigo)
                return tipoItem;
        }
        throw new EnumDesconhecidoException();
    }
}
