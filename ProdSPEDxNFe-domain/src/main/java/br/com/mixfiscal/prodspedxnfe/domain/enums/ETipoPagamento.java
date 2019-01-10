package br.com.mixfiscal.prodspedxnfe.domain.enums;

import br.com.mixfiscal.prodspedxnfe.domain.ex.EnumDesconhecidoException;

public enum ETipoPagamento {
    AVista((byte)0),
    APrazo((byte)1),
    SemPagamento((byte)2);
    
    private final byte tipoPagamento;
    
    private ETipoPagamento(byte tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public byte getTipoPagamento() {
        return tipoPagamento;
    }
    
    public static ETipoPagamento deCodigo(String codigo) throws EnumDesconhecidoException {
        int iCodigo = Integer.parseInt(codigo);
        return deCodigo(iCodigo);
    }
    
    public static ETipoPagamento deCodigo(int codigo) throws EnumDesconhecidoException {
        for (ETipoPagamento item : ETipoPagamento.values()) {
            if (item.getTipoPagamento() == codigo)
                return item;
        }
        throw new EnumDesconhecidoException();
    }
}
