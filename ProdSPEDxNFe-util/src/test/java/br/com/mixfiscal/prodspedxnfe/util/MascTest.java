package br.com.mixfiscal.prodspedxnfe.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MascTest {
    
    public MascTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testIsCPFValido() {
        System.out.println("isCPF");
        String numDoc = "11111111111";
        boolean expResult = true;
        boolean result = Masc.isCPF(numDoc);
        assertEquals(expResult, result);        
    }
    
    @Test
    public void testIsCPFInvalido() {
        System.out.println("isCPF");
        String numDoc = "11111111111111111111111";
        boolean expResult = false;
        boolean result = Masc.isCPF(numDoc);
        assertEquals(expResult, result);        
    }

    @Test
    public void testIsCNPJValido() {
        System.out.println("isCNPJ");
        String numDoc = "49384308000177";
        boolean expResult = true;
        boolean result = Masc.isCNPJ(numDoc);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIsCNPJInvalido() {
        System.out.println("isCNPJ");
        String numDoc = "493843080001773232323233";
        boolean expResult = false;
        boolean result = Masc.isCNPJ(numDoc);
        assertEquals(expResult, result);
    }

    @Test
    public void testMascararCNPJ() {
        System.out.println("mascararCNPJ");
        String numDoc = "49384308000177";
        String expResult = "49.384.308/0001-77";
        String result = Masc.mascararCNPJ(numDoc);
        assertEquals(expResult, result);        
    }

    /**
     * Test of mascararCPF method, of class Masc.
     */
    @Test
    public void testMascararCPF() {
        System.out.println("mascararCPF");
        String numDoc = "12345678901";
        String expResult = "123.456.789-01";
        String result = Masc.mascararCPF(numDoc);
        assertEquals(expResult, result);        
    }
    
}
