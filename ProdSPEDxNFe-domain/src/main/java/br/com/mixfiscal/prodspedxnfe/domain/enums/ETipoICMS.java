package br.com.mixfiscal.prodspedxnfe.domain.enums;

import br.com.mixfiscal.prodspedxnfe.domain.ex.EnumDesconhecidoException;

public enum ETipoICMS {
    ICMS00((byte)0),
    ICMS10((byte)2),
    ICMS20((byte)3),
    ICMS30((byte)4),
    ICMS40((byte)5),
    ICMS41((byte)6),
    ICMS50((byte)7),
    ICMS51((byte)8),
    ICMS60((byte)9),
    ICMS70((byte)10),
    ICMS90((byte)11);
    
    private final byte codICMS;
    
    private ETipoICMS(byte codICMS) {
        this.codICMS = codICMS;
    }

    public byte getCodICMS() {
        return codICMS;
    }
    
    public static ETipoICMS deCodigo(String codigo) throws EnumDesconhecidoException, NumberFormatException {
        int iCodigo = Integer.parseInt(codigo);
        return deCodigo(iCodigo);
    }
    
    public static ETipoICMS deCodigo(int codigo) throws EnumDesconhecidoException {
        for (ETipoICMS tipoItem : ETipoICMS.values()) {
            if (tipoItem.getCodICMS() == codigo)
                return tipoItem;
        }
        throw new EnumDesconhecidoException();
    }
}
