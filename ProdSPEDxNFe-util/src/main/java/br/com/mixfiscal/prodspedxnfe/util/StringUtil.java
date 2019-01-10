package br.com.mixfiscal.prodspedxnfe.util;

import java.text.Normalizer;

public class StringUtil {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
    public static String removerAcentos(String palavra){
        String result = palavra;
        try{
           result = Normalizer.normalize(palavra, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replace("%", " ");
        }catch(Exception ex){System.out.println("" + ex.getMessage());}
        return result;
    }
}
