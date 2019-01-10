package br.com.mixfiscal.prodspedxnfe.domain.enums;

import br.com.mixfiscal.prodspedxnfe.domain.ex.EnumDesconhecidoException;

public enum ETipoFrete {
    Emitente((byte)0),
    DestinatarioRemetente((byte)1),
    Terceiros((byte)2),
    SemCobrancaFrete((byte)9);
    
    private final byte tipoFrete;
    
    private ETipoFrete(byte tipoFrete) {
        this.tipoFrete = tipoFrete;
    }

    public byte getTipoFrete() {
        return tipoFrete;
    }
    
    public static ETipoFrete deCodigo(String codigo) throws EnumDesconhecidoException {
        int iCodigo = Integer.parseInt(codigo);
        return deCodigo(iCodigo);
    }
    
    public static ETipoFrete deCodigo(int codigo) throws EnumDesconhecidoException {
        for (ETipoFrete item : ETipoFrete.values()) {
            if (item.getTipoFrete() == codigo)
                return item;
        }
        throw new EnumDesconhecidoException();
    }
}
