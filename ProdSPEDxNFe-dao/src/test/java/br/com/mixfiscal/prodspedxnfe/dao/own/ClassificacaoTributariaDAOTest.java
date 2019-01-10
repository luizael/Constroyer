package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.enums.EFormatarCodigoInterno;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributaria;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributariaProduto;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsEntrada;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsSaida;
import br.com.mixfiscal.prodspedxnfe.domain.own.PisCofins;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucas
 */
public class ClassificacaoTributariaDAOTest {
    
    public ClassificacaoTributariaDAOTest() {
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

    /**
     * Test of addFiltros method, of class ClassificacaoTributariaDAO.
     */
    @Test
    public void testAddFiltros() {
        System.out.println("addFiltros");
        ClassificacaoTributaria obj = null;
        Criteria ctr = null;
        ClassificacaoTributariaDAO instance = new ClassificacaoTributariaDAO();
        instance.addFiltros(obj, ctr);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listarTudo method, of class ClassificacaoTributariaDAO.
     */
    @Test
    public void testListarTudo() {
        System.out.println("listarTudo");
        String cnpjCliente = "";
        ClassificacaoTributariaDAO instance = new ClassificacaoTributariaDAO();
        List<ClassificacaoTributaria> expResult = null;
        List<ClassificacaoTributaria> result = instance.listarTudo(cnpjCliente);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addOrd method, of class ClassificacaoTributariaDAO.
     */
    @Test
    public void testAddOrd() {
        System.out.println("addOrd");
        Criteria ctr = null;
        String orderProp = "";
        EDirecaoOrdenacao orderDir = null;
        ClassificacaoTributariaDAO instance = new ClassificacaoTributariaDAO();
        instance.addOrd(ctr, orderProp, orderDir);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of excluirCustom method, of class ClassificacaoTributariaDAO.
     */
    @Test
    public void testExcluirCustom() {
        System.out.println("excluirCustom");
        String cnpj = "";
        ClassificacaoTributariaDAO instance = new ClassificacaoTributariaDAO();
        instance.excluirCustom(cnpj);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selecionarPorCNPJCliente method, of class ClassificacaoTributariaDAO.
     */
    @Test
    public void testSelecionarPorCNPJCliente() {
        System.out.println("selecionarPorCNPJCliente");
        String cnpjCliente = "";
        ClassificacaoTributariaDAO instance = new ClassificacaoTributariaDAO();
        ClassificacaoTributaria expResult = null;
        ClassificacaoTributaria result = instance.selecionarPorCNPJCliente(cnpjCliente);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selecionarPorCNPJClienteECodigoProduto method, of class ClassificacaoTributariaDAO.
     */
    @Test
    public void testSelecionarPorCNPJClienteECodigoProduto() {
        System.out.println("selecionarPorCNPJClienteECodigoProduto");
        String cnpjCliente = "";
        String codigoProduto = "";
        ClassificacaoTributariaDAO instance = new ClassificacaoTributariaDAO();
        ClassificacaoTributaria expResult = null;
        ClassificacaoTributaria result = instance.selecionarPorCNPJClienteECodigoProduto(cnpjCliente, codigoProduto);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testSalvarClassificacaoTributaria() {        
        Session session = ConstroyerHibernateUtil.getSessionFactory().getCurrentSession();
        
        String[] row = "26119;78402089;\"CIGARRO MILL\";\"BAZAR GERAL\";.;TABACARIA;CIGARROS;ATIVO;04.001.00;24022000;;101;75;05;0.000;0.000;0.000;0.000;\"Lei n 9.532/97, Art. 53;Lei n 9.715/98, Art. 5\";60;0.000;0.000;0.000;0.000;60;0.000;0.000;0.000;0.000;60;0.000;0.000;0.000;0.000;60;0.000;0.000;0.000;0.000;\"RICMS/12, Anexo X, Art. 22, 23 e Art. 14 12 e An.XII Art.1\";\"RICMS/12, Anexo X, Art. 22, 23 e Art. 14 12 e An.XII Art.1\";60;29.000;29.000;0.000;0.000;60;29.000;29.000;0.000;0.000;60;29.000;29.000;0.000;0.000;10;60;20;29.000;IVA;I;50.000;2017-03-01;;0;;0".split(";");
        ClassificacaoTributaria objCT = new ClassificacaoTributaria();
        this.carregarObjetoDaImportacaoDoExelCompleto(objCT, row, EFormatarCodigoInterno.NAO);
        
        try {            
            session.beginTransaction();     
            ClienteDAO clienteDao = new ClienteDAO(session);
            ClassificacaoTributariaDAO classTribDao = new ClassificacaoTributariaDAO(session);
            
            objCT.setCliente(clienteDao.selecionarPorCNPJ("07970113000169"));
            
            classTribDao.salvar(objCT);
            session.getTransaction().commit();
            assert true;
        } catch(Throwable e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            assert false;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }
    
    private String retConteudoPoisicaoArray(String[] row, int indice) {
        return row[indice] == null || row[indice].isEmpty() ? "campo vazio" : row[indice];
    }
    
    private void carregarObjetoDaImportacaoDoExelCompleto(ClassificacaoTributaria objCT, String[] row, EFormatarCodigoInterno formatarCodigoInterno) {        
        String codigoProduto = retConteudoPoisicaoArray(row, 0);

        if (EFormatarCodigoInterno.SIM.equals(formatarCodigoInterno) && 
            !"campo vazio".equals(codigoProduto)) {
            codigoProduto = codigoProduto.substring(1);
        }

        ClassificacaoTributariaProduto produto = objCT.getProduto();            
        if (produto == null)
            produto = new ClassificacaoTributariaProduto();            
        objCT.setProduto(produto);
        produto.setClassificacaoTributaria(objCT);
        produto.setCodigoProduto(retConteudoPoisicaoArray(row, 0));
        produto.setEan(retConteudoPoisicaoArray(row, 1));
        produto.setDescricao(retConteudoPoisicaoArray(row, 2));            

        if (row.length >= 18) {
            PisCofins pisCofins = produto.getPisCofins();                
            if (pisCofins == null)
                pisCofins = new PisCofins();                
            pisCofins.setProduto(produto);
            produto.setPisCofins(pisCofins);               
            pisCofins.setNcm(retConteudoPoisicaoArray(row, 9));//(row[9].isEmpty() ? "campo vazio" : row[9]);
            pisCofins.setNcmEx(retConteudoPoisicaoArray(row, 10));//(row[10].isEmpty() ? "campo vazio" : row[10]);
            pisCofins.setCodNaturezaReceita(retConteudoPoisicaoArray(row, 11));//(row[11].isEmpty() ? "campo vazio" : row[11]);
            pisCofins.setPisCstE(retConteudoPoisicaoArray(row, 12));//(row[12].isEmpty() ? "campo vazio" : row[12]);
            pisCofins.setPisCstS(retConteudoPoisicaoArray(row, 13));//(row[13].isEmpty() ? "campo vazio" : row[13]);
            pisCofins.setPisAlqE(retConteudoPoisicaoArray(row, 14));//(row[14].isEmpty() ? "campo vazio" : row[14]);
            pisCofins.setPisAlqS(retConteudoPoisicaoArray(row, 15));//(row[15].isEmpty() ? "campo vazio" : row[15]);
            pisCofins.setCofinsAlqE(retConteudoPoisicaoArray(row, 16));//(row[16].isEmpty() ? "campo vazio" : row[16]);
            pisCofins.setCofinsAlqS(retConteudoPoisicaoArray(row, 17));//(row[17].isEmpty() ? "campo vazio" : row[17]);
        }

        if (row.length >= 41) {
            IcmsSaida icmsSaida = produto.getIcmsSaida();                
            if (icmsSaida == null)
                icmsSaida = new IcmsSaida();                
            icmsSaida.setProduto(produto);
            produto.setIcmsSaida(icmsSaida);
            icmsSaida.setSacCst(retConteudoPoisicaoArray(row, 19));//(row[19].isEmpty() ? "campo vazio" : row[19]);
            icmsSaida.setSacAlq(retConteudoPoisicaoArray(row, 20));//(row[20].isEmpty() ? "campo vazio" : row[20]);
            icmsSaida.setSacAlqst(retConteudoPoisicaoArray(row, 21));//(row[21].isEmpty() ? "campo vazio" : row[21]);
            icmsSaida.setSacRbc(retConteudoPoisicaoArray(row, 22));//(row[22].isEmpty() ? "campo vazio" : row[22]);
            icmsSaida.setSacRbcst(retConteudoPoisicaoArray(row, 23));//(row[23].isEmpty() ? "campo vazio" : row[23]);
            icmsSaida.setSasCst(retConteudoPoisicaoArray(row, 24));//row[24].isEmpty() ? "campo vazio" : row[24]);
            icmsSaida.setSasAlq(retConteudoPoisicaoArray(row, 25));//(row[25].isEmpty() ? "campo vazio" : row[25]);
            icmsSaida.setSasAlqst(retConteudoPoisicaoArray(row, 26));//(row[26].isEmpty() ? "campo vazio" : row[26]);
            icmsSaida.setSasRbc(retConteudoPoisicaoArray(row, 27));//(row[27].isEmpty() ? "campo vazio" : row[27]);
            icmsSaida.setSasRbcst(retConteudoPoisicaoArray(row, 28));//(row[28].isEmpty() ? "campo vazio" : row[28]);
            icmsSaida.setSvcCst(retConteudoPoisicaoArray(row, 29));//(row[29].isEmpty() ? "campo vazio" : row[29]);
            icmsSaida.setSvcAlq(retConteudoPoisicaoArray(row, 30));//(row[30].isEmpty() ? "campo vazio" : row[30]);
            icmsSaida.setSvcAlqst(retConteudoPoisicaoArray(row, 31));//(row[31].isEmpty() ? "campo vazio" : row[31]);
            icmsSaida.setSvcRbc(retConteudoPoisicaoArray(row, 32));//(row[32].isEmpty() ? "campo vazio" : row[32]);
            icmsSaida.setSvcRbcst(retConteudoPoisicaoArray(row, 33));//(row[33].isEmpty() ? "campo vazio" : row[33]);
            icmsSaida.setSncCst(retConteudoPoisicaoArray(row, 34));//(row[34].isEmpty() ? "campo vazio" : row[34].toString());
            icmsSaida.setSncAlq(retConteudoPoisicaoArray(row, 35));//(row[35].isEmpty() ? "campo vazio" : row[35].toString());
            icmsSaida.setSncAlqst(retConteudoPoisicaoArray(row, 36));//(row[36].isEmpty() ? "campo vazio" : row[36].toString());
            icmsSaida.setSncRbc(retConteudoPoisicaoArray(row, 37));//(row[37].isEmpty() ? "campo vazio" : row[37].toString());
            icmsSaida.setSncRbcst(retConteudoPoisicaoArray(row, 38));//(row[38].isEmpty() ? "campo vazio" : row[38].toString());
            icmsSaida.setFundamentoLegal(retConteudoPoisicaoArray(row, 40));//(row[40].isEmpty() ? "campo vazio" : row[40].toString());
        }

        if (row.length >= 66) {
            IcmsEntrada icmsEntrada = produto.getIcmsEntrada();
            if (icmsEntrada == null)
                icmsEntrada = new IcmsEntrada();
            icmsEntrada.setProduto(produto);
            produto.setIcmsEntrada(icmsEntrada);
            icmsEntrada.setEiCst(retConteudoPoisicaoArray(row, 41));//(row[41].isEmpty() ? "campo vazio" : row[41].toString());
            icmsEntrada.setEiAlq(retConteudoPoisicaoArray(row, 42));//(row[42].isEmpty() ? "campo vazio" : row[42].toString());
            icmsEntrada.setEiAlqst(retConteudoPoisicaoArray(row, 43));//(row[43].isEmpty() ? "campo vazio" : row[43].toString());
            icmsEntrada.setEiRbc(retConteudoPoisicaoArray(row, 44));//(row[44].isEmpty() ? "campo vazio" : row[44].toString());
            icmsEntrada.setEiRbcst(retConteudoPoisicaoArray(row, 45));//(row[45].isEmpty() ? "campo vazio" : row[45].toString());
            icmsEntrada.setEdCst(retConteudoPoisicaoArray(row, 46));//(row[46].isEmpty() ? "campo vazio" : row[46].toString());
            icmsEntrada.setEdAlq(retConteudoPoisicaoArray(row, 47));//(row[47].isEmpty() ? "campo vazio" : row[47].toString());
            icmsEntrada.setEdAlqst(retConteudoPoisicaoArray(row, 48));//(row[48].isEmpty() ? "campo vazio" : row[48].toString());
            icmsEntrada.setEdRbc(retConteudoPoisicaoArray(row, 49));//(row[49].isEmpty() ? "campo vazio" : row[49].toString());
            icmsEntrada.setEdRbcst(retConteudoPoisicaoArray(row, 50));//(row[50].isEmpty() ? "campo vazio" : row[50].toString());
            icmsEntrada.setEsCst(retConteudoPoisicaoArray(row, 51));//(row[51].isEmpty() ? "campo vazio" : row[51].toString());
            icmsEntrada.setEsAlq(retConteudoPoisicaoArray(row, 52));//(row[52].isEmpty() ? "campo vazio" : row[52].toString());
            icmsEntrada.setEsAlqst(retConteudoPoisicaoArray(row, 53));//(row[53].isEmpty() ? "campo vazio" : row[53].toString());
            icmsEntrada.setEsRbc(retConteudoPoisicaoArray(row, 54));//(row[54].isEmpty() ? "campo vazio" : row[54].toString());
            icmsEntrada.setEsRbcst(retConteudoPoisicaoArray(row, 55));//(row[55].isEmpty() ? "campo vazio" : row[55].toString());
            icmsEntrada.setNfiCst(retConteudoPoisicaoArray(row, 56));//(row[56].isEmpty() ? "campo vazio" : row[56].toString());
            icmsEntrada.setNfdCst(retConteudoPoisicaoArray(row, 57));//(row[57].isEmpty() ? "campo vazio" : row[57].toString());
            icmsEntrada.setNfsCsosn(retConteudoPoisicaoArray(row, 58));//(row[58].isEmpty() ? "campo vazio" : row[58].toString());
            icmsEntrada.setNfAlq(retConteudoPoisicaoArray(row, 59));//(row[59].isEmpty() ? "campo vazio" : row[59].toString());
            icmsEntrada.setFundamentoLegal(retConteudoPoisicaoArray(row, 39));//(row[39].isEmpty() ? "campo vazio" : row[39].toString());
            icmsEntrada.setTipoMva(retConteudoPoisicaoArray(row, 60));//(row[60].isEmpty() ? "campo vazio" : row[60].toString());
            icmsEntrada.setMva(retConteudoPoisicaoArray(row, 61));//(row[61].isEmpty() ? "campo vazio" : row[61].toString());
            icmsEntrada.setMvaDataIni(retConteudoPoisicaoArray(row, 63));//(row[63].isEmpty() ? "campo vazio" : row[63].toString());
            icmsEntrada.setMvaDataFim(retConteudoPoisicaoArray(row, 64));//(row[64].isEmpty() ? "campo vazio" : row[64].toString());
            icmsEntrada.setCreditoOutorgado(retConteudoPoisicaoArray(row, 65));//(row[65].isEmpty() ? "campo vazio" : row[65].toString());           
        }  

    }
}
