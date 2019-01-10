package br.com.mixfiscal.prodspedxnfe.domain.enums;

import br.com.mixfiscal.prodspedxnfe.domain.ex.EnumDesconhecidoException;

public enum ETipoItem {
    Desconhecido(-1),
    MercadoriaParaRevenda(0),
    MateriaPrima(1),
    Embalagem(2),
    ProdutoEmProcesso(3),
    ProdutoAcabado(4),
    Subproduto(5),
    ProdutoIntermediario(6),
    MaterialDeUsoEConsumo(7),
    AtivoImobilizado(8),
    Servicos(9),
    OutrosInsumos(10),
    Outras(99);

    private final int codTipoItem;
    
    private ETipoItem(int codTipoItem) {
        this.codTipoItem = codTipoItem;
    }
    
    public int getCodTipoItem() { return codTipoItem; } 
    
    public static ETipoItem deCodigo(String codigo) throws EnumDesconhecidoException, NumberFormatException {
        int iCodigo = Integer.parseInt(codigo);
        return deCodigo(iCodigo);
    }
    
    public static ETipoItem deCodigo(int codigo) throws EnumDesconhecidoException {
        for (ETipoItem tipoItem : ETipoItem.values()) {
            if (tipoItem.getCodTipoItem() == codigo)
                return tipoItem;
        }
        throw new EnumDesconhecidoException();
    }
}
