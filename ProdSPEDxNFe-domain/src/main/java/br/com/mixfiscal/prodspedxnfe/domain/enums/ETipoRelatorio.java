
package br.com.mixfiscal.prodspedxnfe.domain.enums;

import br.com.mixfiscal.prodspedxnfe.domain.ex.EnumDesconhecidoException;


public enum ETipoRelatorio {
    Entrada((byte)0),
    Saida((byte)1);
    
    private final byte tipoRelatorio;
    
    private ETipoRelatorio(byte tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }
    
    public byte getTipoRelatorio() {
        return this.tipoRelatorio;
    }
    
    public static ETipoRelatorio deCodigo(String codigo) throws EnumDesconhecidoException, NumberFormatException {
        byte iCodigo = Byte.parseByte(codigo);
        return deCodigo(iCodigo);
    }
    
    public static ETipoRelatorio deCodigo(byte codigo) throws EnumDesconhecidoException {
        for (ETipoRelatorio tipoItem : ETipoRelatorio.values()) {
            if (tipoItem.getTipoRelatorio() == codigo)
                return tipoItem;
        }
        throw new EnumDesconhecidoException();
    }
    
    
}
