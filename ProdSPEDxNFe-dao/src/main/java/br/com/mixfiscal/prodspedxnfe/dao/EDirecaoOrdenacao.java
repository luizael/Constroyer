package br.com.mixfiscal.prodspedxnfe.dao;

public enum EDirecaoOrdenacao {
    Asc,
    Desc;    
    public static EDirecaoOrdenacao porNome(String nome) {
        for (EDirecaoOrdenacao v : EDirecaoOrdenacao.values()) {
            if (v.name().toLowerCase().equals(nome.toLowerCase()))
                return v;
        }
        throw new EnumConstantNotPresentException(EDirecaoOrdenacao.class, nome);
    }
}
