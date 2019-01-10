package br.com.mixfiscal.prodspedxnfe.util;

public class Masc {
    public static boolean isCPF(String numDoc) {
       return Masc.isCPFOrCNPJ(numDoc, ETipoDoc.CPF);
    }

    public static boolean isCNPJ(String numDoc) {
        return Masc.isCPFOrCNPJ(numDoc, ETipoDoc.CNPJ);
    }
    
    public static String mascararCNPJ(String numDoc) {
        if (!isCNPJ(numDoc))
            throw new IllegalArgumentException(String.format("numDoc: '%s' não é um CNPJ válido", numDoc));
        
        String[] partes = new String[5];
        partes[0] = numDoc.substring(0, 2);
        partes[1] = numDoc.substring(2, 5);
        partes[2] = numDoc.substring(5, 8);
        partes[3] = numDoc.substring(8, 12);
        partes[4] = numDoc.substring(12, 14);
        
        return String.format("%s.%s.%s/%s-%s", partes[0], partes[1], partes[2], partes[3], partes[4]);
    }
    
    public static String mascararCPF(String numDoc) {
        if (!isCPF(numDoc))
            throw new IllegalArgumentException(String.format("numDoc: '%s' não é um CPF válido", numDoc));
        
        String[] partes = new String[4];
        partes[0] = numDoc.substring(0, 3);
        partes[1] = numDoc.substring(3, 6);
        partes[2] = numDoc.substring(6, 9);
        partes[3] = numDoc.substring(9, 11);        
        
        return String.format("%s.%s.%s-%s", partes[0], partes[1], partes[2], partes[3]);
    }
    
    private static boolean isCPFOrCNPJ(String numDoc, ETipoDoc tpDoc) {
        if (numDoc == null || numDoc.isEmpty())
            return false;
        byte qtdChars = (byte)(tpDoc == ETipoDoc.CNPJ ? 14 : 11);
        return numDoc.replace(".", "").replace("/", "").replace("-", "").length() == qtdChars;
    }
    
    private enum ETipoDoc {
        CPF,
        CNPJ
    }
}
