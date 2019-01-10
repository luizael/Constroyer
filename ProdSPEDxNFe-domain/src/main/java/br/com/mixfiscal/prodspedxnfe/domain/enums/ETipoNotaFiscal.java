package br.com.mixfiscal.prodspedxnfe.domain.enums;

import br.com.mixfiscal.prodspedxnfe.domain.ex.EnumDesconhecidoException;

public enum ETipoNotaFiscal {
    Entrada((byte)0),
    Saida((byte)1);
    
    private final byte tipoNotaFiscal;
    
    private ETipoNotaFiscal(byte tipoNotaFiscal) {
        this.tipoNotaFiscal = tipoNotaFiscal;
    }
    
    public byte getTipoNotaFiscal() {
        return this.tipoNotaFiscal;
    }
    
    public static ETipoNotaFiscal deCodigo(String codigo) throws EnumDesconhecidoException, NumberFormatException {
        byte iCodigo = Byte.parseByte(codigo);
        return deCodigo(iCodigo);
    }
    
    public static ETipoNotaFiscal deCodigo(byte codigo) throws EnumDesconhecidoException {
        for (ETipoNotaFiscal tipoItem : ETipoNotaFiscal.values()) {
            if (tipoItem.getTipoNotaFiscal() == codigo)
                return tipoItem;
        }
        throw new EnumDesconhecidoException();
    }
}
