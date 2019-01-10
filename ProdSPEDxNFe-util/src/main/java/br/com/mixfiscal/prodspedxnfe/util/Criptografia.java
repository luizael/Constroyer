package br.com.mixfiscal.prodspedxnfe.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {
    
    public static String converterParaMD5(String valor)throws NoSuchAlgorithmException{
       MessageDigest md = MessageDigest.getInstance("MD5");
       BigInteger hash = new BigInteger(1, md.digest(valor.getBytes()));
       return String.format("%32x", hash);
    }
    
    public static boolean compararMD5(String valor1, String valor2)throws NoSuchAlgorithmException{
        boolean result = false;
        if(converterParaMD5(valor1).equals(valor2))result =  true;
        return result;
    }
}
