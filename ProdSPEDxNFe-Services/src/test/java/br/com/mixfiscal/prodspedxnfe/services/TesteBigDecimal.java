
package br.com.mixfiscal.prodspedxnfe.services;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;


public class TesteBigDecimal extends BigDecimal  implements Comparable<BigDecimal> {
       
    public TesteBigDecimal(char[] in, int offset, int len) {
        super(in, offset, len);
    }
    public TesteBigDecimal(double val) {
        super(val);
    }
    public TesteBigDecimal(char[] in, int offset, int len, MathContext mc) {
        super(in, offset, len, mc);
    }
    public TesteBigDecimal(char[] in) {
        super(in);
    }
    public TesteBigDecimal(String val) {
        super(val);
    }
    public TesteBigDecimal(double val, MathContext mc) {
        super(val, mc);
    }
    public TesteBigDecimal(BigInteger val) {
        super(val);
    }
    public TesteBigDecimal(BigInteger unscaledVal, int scale, MathContext mc) {
        super(unscaledVal, scale, mc);
    }
    public TesteBigDecimal(int val) {
        super(val);
    }

    public TesteBigDecimal(long val) {
        super(val);
    }    
    public TesteBigDecimal(long val, MathContext mc) {
        super(val, mc);
    }   
  
    @Override
    public int compareTo(BigDecimal o) {
        
        if(super.compareTo(o) < 0)
            return -1;
        if(super.compareTo(o) > 0)
            return 1;
        
        return 0;
    }
    
}
