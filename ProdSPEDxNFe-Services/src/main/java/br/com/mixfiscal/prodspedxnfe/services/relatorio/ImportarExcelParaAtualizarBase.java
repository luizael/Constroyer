package br.com.mixfiscal.prodspedxnfe.services.relatorio;

//import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.dao.own.ClassificacaoTributariaDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.enums.EFormatarCodigoInterno;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoOperacaoCliente;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributaria;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributariaProduto;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPED0000;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPED0200;
import br.com.mixfiscal.prodspedxnfe.domain.own.Cliente;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsEntrada;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsSaida;
import br.com.mixfiscal.prodspedxnfe.domain.own.PisCofins;
import br.com.mixfiscal.prodspedxnfe.domain.own.RequisicaoAtualizacaoInfoFiscal;
import br.com.mixfiscal.prodspedxnfe.services.ClassificacaoTributariaService;
import br.com.mixfiscal.prodspedxnfe.services.LeitorSpedFiscal;
import br.com.mixfiscal.prodspedxnfe.services.ClienteService;
import br.com.mixfiscal.prodspedxnfe.services.ex.ServiceException;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

public class ImportarExcelParaAtualizarBase extends RelatorioDebitoCredito {

    private static final int TOTAL_COLUNA_PADRAO_CSV = 61;
    private static final String CAMPO_VAZIO = "campo vazio";
    public enum FormatarCodigoInterno {
        SIM,
        NAO
    }
    public int getTotalColunaPadraoCsv(){
        return TOTAL_COLUNA_PADRAO_CSV;
    }
    
    // <editor-fold desc="Membros Privados" defaultstate="collapsed">
    private String caminhoExel;
    private LeitorSpedFiscal leitorSped;
    private int contador;
    private String nomeSped;
    private final Logger log;
    //private final ClassificacaoTributariaService classTribService;
    //private final ClienteService clienteService;
    // </editor-fold>

    // <editor-fold desc="Getters e Setters" defatulstate="collapsed">
    @Override
    public LeitorSpedFiscal getLeitorSped() {
        return leitorSped;
    }

    @Override
    public void setLeitorSped(LeitorSpedFiscal leitorSped) {
        this.leitorSped = leitorSped;
    }

    @Override
    public String getCaminhoExel() {
        return caminhoExel;
    }

    @Override
    public void setCaminhoExel(String caminhoExel) {
        this.caminhoExel = caminhoExel;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public String getNomeSped() {
        return nomeSped;
    }

    public void setNomeSped(String nomeSped) {
        this.nomeSped = nomeSped;
    }
    // </editor-fold>
    
    public ImportarExcelParaAtualizarBase() {
        log = LogManager.getLogger(ImportarExcelParaAtualizarBase.class);
        //classTribService = new ClassificacaoTributariaService();
        //clienteService = new ClienteService();
    }
    
    public List<String> getComparacaoSpedComExel(List<ExelcomSped> listaComparada) {
        Gson gson = new Gson();
        List<String> listaAux = new ArrayList<>();
        log.info("convertendo lista de comparação");
        try {
            listaComparada.stream().forEach(e -> {
                String converte = gson.toJson(e);
                listaAux.add(converte);

            });
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return listaAux;
    }

    public List<String> compararSpedComExel(List<Exel> listCT, List<SPED0200> listaSPED200) {
        super.setDescricaoProgresso("o sistema está comparando sped com excel, aguarde... ");
        try {
            List<ExelcomSped> listaComparacoes = new ArrayList<>();
            log.info("comparando sped com excel:  CompararSpedComExel()");
            for (int i = 90; i <= 300; i++) {
                for (int j = 0; j < listCT.size(); j++) {
                    try {
                        if ((listaSPED200.get(i).getCodItem() != null) && (listCT.get(j).getCodigoExel() != null)) {
                            if (listaSPED200.get(i).getCodItem().equals(listCT.get(j).getCodigoExel())) {
                                ExelcomSped objComparacos = new ExelcomSped();
                                objComparacos.setCodigoExel(listCT.get(j).getCodigoExel());
                                objComparacos.setDescExel(listCT.get(j).getDescExel());
                                objComparacos.setEanExel(listCT.get(j).getEanExel());
                                objComparacos.setCodigoSped(listaSPED200.get(i).getCodItem());
                                objComparacos.setDescSped(listaSPED200.get(i).getDescrItem());
                                objComparacos.setEanSped(listaSPED200.get(i).getCodBarra());
                                objComparacos.setNomeSped(this.getNomeSped());
                                listaComparacoes.add(objComparacos);
                            }
                        }
                    } catch (Exception ex) {
                        log.error(ex.getMessage(), ex);
                    }
                    super.setDescricaoProgresso("preparando lista de comparação [" + i + "]");
                }
            }
            return getComparacaoSpedComExel(listaComparacoes);
        } catch (Exception ex) {
            this.log.error("erro no metodo de comparação de sped com excel  CompararSpedComExel() " + ex.getMessage(),
                    ex);
            return null;
        }
    }

    public void importarExel(String caminho_arquivo_xls) {
        // List<String> lista = new ArrayList<String>();
        Workbook workbook = null;
        //Sheet sheet = null;
        log.info("Importando valores do arquivo excel...importarExel()");
        try {
            // Carrega planilha
            WorkbookSettings config = new WorkbookSettings();
            config.setEncoding("Cp1252");// configura acentuação
            workbook = Workbook.getWorkbook(new File(caminho_arquivo_xls), config);// recupera arquivo desejado
            //sheet = workbook.getSheet(0);
            // FileInputStream arquivo = new FileInputStream(new File(caminho_arquivo_xls));
            // HSSFWorkbook workbook = new HSSFWorkbook(arquivo);
            // HSSFSheet sheetAlunos = workbook.getSheetAt(0);
            // Iterator<Row> rowIterator = sheetAlunos.iterator();
            // while (rowIterator.hasNext()) {
            // Row row = rowIterator.next();
            // Iterator<Cell> cellIterator = row.cellIterator();
            // while (cellIterator.hasNext()) {
            // Cell cell = cellIterator.next();
            // BufferedReader reader = new BufferedReader(new FileReader(new
            // File(caminho_arquivo_xls)));
            // String [] dados;
            // String linha = null;
            // int cont = 0;
            // while(reader.ready()){
            // linha = reader.readLine();
            // dados = linha.split(";");
            // lista.add(linha);
            // }
            // }
            // }
        } catch (FileNotFoundException e) {
            this.log.error(e.getMessage(), e);
        } catch (IOException e) {
            this.log.error(e.getMessage(), e);
            // System.out.println(e);
        } catch (NumberFormatException e) {
            this.log.error(e.getMessage(), e);
            // System.out.println(e);
        } catch (Exception e) {
            this.log.error(e.getMessage(), e);
            // System.out.println(e);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    public void carregarObjetoDaImportacaoDoExel(Exel objCT, String[] row, String formatar) {
        super.setDescricaoProgresso("o sistema esta importando os valores, aguarde...");
        try {
            if (formatar.equalsIgnoreCase("sim")) {
                String codigo = "";
                // verifica se coluna e linha row não é vazia e adiciona o valor do campo no
                // atributo do obj!
                if (!row[0].isEmpty()) {
                    codigo = row[0];
                    codigo = codigo.substring(1);
                }
                objCT.setCodigoExel(codigo.equals(null) ? "campo vazio" : codigo);
            } else {
                objCT.setCodigoExel(row[0].equals(null) ? "campo vazio" : row[0]);
            }
            objCT.setEanExel(row[1].isEmpty() ? "campo vazio" : row[1]);
            objCT.setDescExel(row[2].isEmpty() ? "campo vazio" : row[2]);
            super.setDescricaoProgresso("Analizando excel para confirmação do usuário<br><span style='font-size: 11px;'>reg:  " + objCT.getCodigoExel() + ", " + objCT.getDescExel() + ", " + objCT.getEanExel() + "<span>");
            log.info("carregarObjetoDaImportacaoDoExel() -> ean [" + objCT.getEanExel() + "], descricao [" + objCT.getDescExel() + "], codigo [" + objCT.getCodigoExel() + "]");
        } catch (Exception e) {
            this.log.error(e.getMessage(), e);
            // System.out.println(e);
        }
    }

    public void carregarObjetoDaImportacaoDoExelCompleto(ClassificacaoTributaria objCT, String[] row, EFormatarCodigoInterno formatarCodigoInterno) {
        try {
            String codigoProduto = retConteudoPoisicaoArray(row, 0);

            if (EFormatarCodigoInterno.SIM.equals(formatarCodigoInterno) && 
                !CAMPO_VAZIO.equals(codigoProduto)) {
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
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public void carregarObjetoDaImportacaoDoExelApenasEntrada(ClassificacaoTributaria objCT, String[] row, SPED0000 sped0000) {
        try {
            // verifica se coluna e linha row não é vazia e adiciona o valor do campo no
            // atributo do obj!
            //try {
            //    objCT.setCnpjTrib(sped0000.getCNPJ());
            //} catch (Exception ex) {
            //}
            try {
                objCT.getProduto().setCodigoProduto(retConteudoPoisicaoArray(row, 0));//(row[0].isEmpty() ? "campo vazio" : row[0]);
            } catch (Exception ex) {
            }
            try {
                objCT.getProduto().setEan(retConteudoPoisicaoArray(row, 1));//(StringUtil.isNullOrEmpty(row[1]) ? "campo vazio" : row[1]);
            } catch (Exception ex) {
            }
            try {
                objCT.getProduto().setDescricao(retConteudoPoisicaoArray(row, 2));//(row[2].isEmpty() ? "campo vazio" : row[2]);
            } catch (Exception ex) {
            }

            try {
                objCT.getProduto().getPisCofins().setNcm(retConteudoPoisicaoArray(row, 9));//(row[9].isEmpty() ? "campo vazio" : row[9]);
            } catch (Exception ex) {
            }
            try {
                objCT.getProduto().getPisCofins().setNcmEx(retConteudoPoisicaoArray(row, 10));//(row[10].isEmpty() ? "campo vazio" : row[10]);
            } catch (Exception ex) {
            }
            try {
                objCT.getProduto().getPisCofins().setCodNaturezaReceita(retConteudoPoisicaoArray(row, 11));//(row[11].isEmpty() ? "campo vazio" : row[11]);
            } catch (Exception ex) {
            }

            try {
                objCT.getProduto().getIcmsEntrada().setEiCst(retConteudoPoisicaoArray(row, 14));//(row[14].isEmpty() ? "campo vazio" : row[14].toString());
            } catch (Exception ex) {
                objCT.getProduto().getIcmsEntrada().setEiCst("0");
            }
            try {
                objCT.getProduto().getIcmsEntrada().setEiAlq(retConteudoPoisicaoArray(row, 15));//(row[15].isEmpty() ? "campo vazio" : row[15].toString());
            } catch (Exception ex) {
                objCT.getProduto().getIcmsEntrada().setEiAlq("0");
            }
            try {
                objCT.getProduto().getIcmsEntrada().setEiAlqst(retConteudoPoisicaoArray(row, 16));//(row[16].isEmpty() ? "campo vazio" : row[16].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
            try {
                objCT.getProduto().getIcmsEntrada().setEiRbc(retConteudoPoisicaoArray(row, 17));//(row[17].isEmpty() ? "campo vazio" : row[17].toString());
            } catch (Exception ex) {
            }
            try {
                objCT.getProduto().getIcmsEntrada().setEiRbcst(retConteudoPoisicaoArray(row, 18));//(row[18].isEmpty() ? "campo vazio" : row[18].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }

            try {
                objCT.getProduto().getIcmsEntrada().setEdCst(retConteudoPoisicaoArray(row, 19));//(row[19].isEmpty() ? "campo vazio" : row[19].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
            try {
                objCT.getProduto().getIcmsEntrada().setEdAlq(retConteudoPoisicaoArray(row, 20));//(row[20].isEmpty() ? "campo vazio" : row[20].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
            try {
                objCT.getProduto().getIcmsEntrada().setEdAlqst(retConteudoPoisicaoArray(row, 21));//(row[21].isEmpty() ? "campo vazio" : row[21].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
            try {
                objCT.getProduto().getIcmsEntrada().setEdRbc(retConteudoPoisicaoArray(row, 22));//(row[22].isEmpty() ? "campo vazio" : row[22].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
            try {
                objCT.getProduto().getIcmsEntrada().setEdRbcst(retConteudoPoisicaoArray(row, 23));//(row[23].isEmpty() ? "campo vazio" : row[23].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }

            try {
                objCT.getProduto().getIcmsEntrada().setEsCst(retConteudoPoisicaoArray(row, 24));//(row[24].isEmpty() ? "campo vazio" : row[24].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
            try {
                objCT.getProduto().getIcmsEntrada().setEsAlq(retConteudoPoisicaoArray(row, 25));//(row[25].isEmpty() ? "campo vazio" : row[25].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
            try {
                objCT.getProduto().getIcmsEntrada().setEsAlqst(retConteudoPoisicaoArray(row, 26));//(row[26].isEmpty() ? "campo vazio" : row[26].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
            try {
                objCT.getProduto().getIcmsEntrada().setEsRbc(retConteudoPoisicaoArray(row, 27));//(row[27].isEmpty() ? "campo vazio" : row[27].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
            try {
                objCT.getProduto().getIcmsEntrada().setEsRbcst(retConteudoPoisicaoArray(row, 28));//(row[28].isEmpty() ? "campo vazio" : row[28].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }

            try {
                objCT.getProduto().getIcmsEntrada().setNfiCst(retConteudoPoisicaoArray(row, 29));//(row[29].isEmpty() ? "campo vazio" : row[29].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
            try {
                objCT.getProduto().getIcmsEntrada().setNfsCsosn(retConteudoPoisicaoArray(row, 30));//(row[30].isEmpty() ? "campo vazio" : row[30].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
            try {
                objCT.getProduto().getIcmsEntrada().setNfdCst(retConteudoPoisicaoArray(row, 31));//(row[31].isEmpty() ? "campo vazio" : row[31].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
            try {
                objCT.getProduto().getIcmsEntrada().setTipoMva(retConteudoPoisicaoArray(row, 32));//(row[32].isEmpty() ? "campo vazio" : row[32].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
            try {
                objCT.getProduto().getIcmsEntrada().setMva(retConteudoPoisicaoArray(row, 33));//(row[33].isEmpty() ? "campo vazio" : row[33].toString());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }

        } catch (Exception ex) {
            this.log.error(ex.getMessage(), ex);
            // System.out.println(ex);
        }
    }
   
    public List<String> percorrerPlanoExelParaTesteDeConfirmacaoDoUsusario(String caminhoArquivoXls, List<SPED0200> listaSPED200, String formatar) {       
        String line = "";
        List<Exel> listCT = new ArrayList<>();
        int cont = 0;
        BufferedReader br = null;

        try {
            super.setDescricaoProgresso("analizando CSV e SPÈD, aguarde...");

            br = new BufferedReader(new FileReader(caminhoArquivoXls));

            while ((line = br.readLine()) != null) {
                cont++;
                super.setContadorProgresso(cont);
                if (cont > 1) {
                    // "," ou ";" de acordo com o arquivo
                    String[] row = line.split(";");
                    if (row.length == 0) {
                        continue;
                    }
                    Exel objCT = new Exel();
                    // cria meu objeto que recebe as linhas
                    carregarObjetoDaImportacaoDoExel(objCT, row, formatar);
                    listCT.add(objCT);
                }
            }
        } catch (FileNotFoundException ex) {
            this.log.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            this.log.error(ex.getMessage(), ex);
            // System.out.println(ex);
        } catch (NumberFormatException ex) {
            this.log.error(ex.getMessage(), ex);
            // System.out.println(ex);
        } catch (Exception ex) {
            this.log.error(ex.getMessage(), ex);
            // System.out.println(ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
        }

        return compararSpedComExel(listCT, listaSPED200);
    }

    public boolean excluirDadosDaBase(SPED0000 sped0000) {
        ClassificacaoTributariaService classTribService = new ClassificacaoTributariaService();
        
        boolean result = false;
        try {
            //ClassificacaoTributaria objCT = new ClassificacaoTributaria();
            //objCT.setCnpjTrib(sped0000.getCNPJ());
            super.setDescricaoProgresso("Aguarde enquanto o sistema limpa o cache! ");
            result = classTribService.excluirPorCNPJCliente(sped0000.getCNPJ()); //excluirCustomizado(objCT);
        } catch (Exception ex) {
            this.log.error(ex.getMessage(), ex);
            // System.out.println(ex.getMessage());
        }
        return result;
    }

    public void percorrerPlanoExcelParaSalvarNoBD(String caminhoArquivoXls, RequisicaoAtualizacaoInfoFiscal requisicao) throws ServiceException{
        int cont = 0;
        String line;        
        BufferedReader br = null;
        ClassificacaoTributariaService classTribService = new ClassificacaoTributariaService();
        ClassificacaoTributaria objCT = null;
        
        try {
            super.setDescricaoProgresso("Carregando csv na memoria ");
            br = new BufferedReader(new FileReader(caminhoArquivoXls));
           
            while ((line = br.readLine()) != null) {
                cont++;
                try {
                    if (cont >= 2) {
                        // "," ou ";" de acordo com o arquivo
                        String[] row = line.split(";");
                        if (row.length == 0) {
                            continue;
                        }                        

                        objCT = classTribService.selecionarPorCNPJClienteECodigoProduto(requisicao.getCliente().getCnpj(), row[0]);
                        if (objCT == null || objCT.getId() == null) {
                            objCT = new ClassificacaoTributaria();                         
                            objCT.setCliente(new Cliente()); 
                            objCT.getCliente().setId(requisicao.getCliente().getId());
                        }
                        
                        // cria meu objeto que recebe as linhas
                        carregarObjetoDaImportacaoDoExelCompleto(objCT, row, requisicao.getFormatarCodigo());
                        try {
                            //classTribService.salvar(objCT);
                            salvarClassificacaoTributaria(objCT);
                            super.setDescricaoProgresso("Aguarde enquanto são armazenados: " + cont + " registros");
                        } catch (Exception ex) {
                            this.log.error("erro ao salvar a leitura do csv no banco. Msg: " + ex.getMessage() + ". Conteudo da Linha: " + line, ex);
                        }
                    }
                } catch (Exception ex) {
                    this.log.error("erro ao salvar a leitura do csv no banco " + ex.getMessage(), ex);
                }
            }
            super.setDescricaoProgresso("base atualizada com sucesso Total: " + cont + " registros");
        } catch (FileNotFoundException fe) {
            this.log.error(fe.getStackTrace(), fe);
            throw new ServiceException(fe);           
        } catch (IOException Ie) {
            this.log.error(Ie.getMessage(), Ie);
            throw new ServiceException(Ie);
        } catch (NumberFormatException Ne) {
            this.log.error("erro na formatação de numero " + Ne.getMessage(), Ne);
            throw new ServiceException(Ne);
        } catch (Exception e) {
            this.log.error("Erro ao ler e gravar" + e.getMessage() + "" + objCT, e);
            throw new ServiceException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
        }
    }
    
    private void salvarClassificacaoTributaria(ClassificacaoTributaria classTrib) {
        Session session = null;        
        try {
            session = ConstroyerHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            ClassificacaoTributariaDAO classTribDao = new ClassificacaoTributariaDAO(session);
            imprimirDetalheDeOjetoParaAnalize(classTrib);
            if (classTrib != null) {
                if (classTrib.getId() == null)
                    classTribDao.salvar(classTrib);
                else
                    classTribDao.atualizar(classTrib);
            }
            session.getTransaction().commit();
        } catch(Exception ex) {
            log.error("Erro ao salvar ClassificacaoTributaria" + ex.getMessage(), ex);
            session.getTransaction().rollback();            
        } finally {
            if (session != null && session.isOpen()) {
                try {
                    session.close();
                } catch(Exception ex) {
                    log.error("Erro ao encerrar sessão de ClassificacaoTributariaDAO. " + ex.getMessage(), ex);
                }
            }
        }
    }
    
    public void percorrerPlanoExcelParaSalvarNoBDJXl(String caminho_arquivo_xls) {
        ClassificacaoTributariaService classTribService = new ClassificacaoTributariaService();
        
        Workbook workbook = null;
        Sheet sheet = null;
        
        try {
            WorkbookSettings config = new WorkbookSettings();
            config.setEncoding("Cp1252");// configura acentuação
            workbook = Workbook.getWorkbook(new File(caminho_arquivo_xls), config);// recupera arquivo desejado
            sheet = workbook.getSheet(0);
            int linhas = sheet.getRows();

            for (int row = 1; row <= linhas; row++) {
                ClassificacaoTributaria objCT = new ClassificacaoTributaria();
                // cria meu objeto que recebe as linhas
                // carregarObjetoDaImportacaoDoExelParaSalvarNoBD(objCT,row,sheet);
                classTribService.salvar(objCT);
            }

        } catch (NumberFormatException ex) {
            this.log.error(ex.getMessage());
            System.out.println(ex);
        } catch (Exception ex) {
            this.log.error(ex.getMessage());
            System.out.println(ex);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    private String retConteudoPoisicaoArray(String[] row, int indice) {
        return row[indice] == null || row[indice].isEmpty() ? CAMPO_VAZIO : row[indice];
    }

    private void gravarTipoOperacaoCliente(SPED0000 sped0000) {
        ClienteService clService = new ClienteService();
        Cliente cl = new Cliente();
        cl.setCnpj(sped0000.getCNPJ().trim());
        super.setDescricaoProgresso("o sistema esta gravando o cnpj do cliente para pesquisas posterior: ");
        try {
            Cliente result = clService.selecionarUm(cl);
            if (result != null) {
                if (result.getCnpj() != null) {
                    if (result.getOperacao() != ETipoOperacaoCliente.Excel.getValor()) {
                        result.setOperacao(ETipoOperacaoCliente.Excel.getValor());
                        clService.atualizar(result);
                    }
                    return;
                }
            }
            cl.setNome(sped0000.getNomeEmpresarialEntidade());
            cl.setOperacao(ETipoOperacaoCliente.Excel.getValor());
            cl.setToken("nao tem");
            clService.salvar(cl);
        } catch (Exception ex) {
            this.log.error(ex.getMessage(), ex);
        }
    }

    public int obterTotalColunasCsv(String path){
        String linha = "";
        String[] row;
        int count = 0;
        int totColuns = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            while((linha = br.readLine()) != null){
                if(count > 0)
                    break;
                
                row = linha.split(";");
                totColuns = row.length;
                count++;
            }
        }catch(FileNotFoundException fnex){
            log.error(fnex.getMessage(), fnex);
        }catch(IOException ioex){
            log.error(ioex.getMessage(), ioex);
        }
        return totColuns;
    }
    
    public class Exel {

        private String descExel;
        private String eanExel;
        private String codigoExel;

        public String getDescExel() {
            return descExel;
        }

        public void setDescExel(String descExel) {
            this.descExel = descExel;
        }

        public String getEanExel() {
            return eanExel;
        }

        public void setEanExel(String eanExel) {
            this.eanExel = eanExel;
        }

        public String getCodigoExel() {
            return codigoExel;
        }

        public void setCodigoExel(String codigoExel) {
            this.codigoExel = codigoExel;
        }
    }

    public class ExelcomSped {

        private String nomeSped;
        private String descSped;
        private String eanSped;
        private String codigoSped;
        private String descExel;
        private String eanExel;
        private String codigoExel;

        public String getDescSped() {
            return descSped;
        }

        public void setDescSped(String descSped) {
            this.descSped = descSped;
        }

        public String getEanSped() {
            return eanSped;
        }

        public void setEanSped(String eanSped) {
            this.eanSped = eanSped;
        }

        public String getCodigoSped() {
            return codigoSped;
        }

        public void setCodigoSped(String codigoSped) {
            this.codigoSped = codigoSped;
        }

        public String getDescExel() {
            return descExel;
        }

        public void setDescExel(String descExel) {
            this.descExel = descExel;
        }

        public String getEanExel() {
            return eanExel;
        }

        public void setEanExel(String eanExel) {
            this.eanExel = eanExel;
        }

        public String getCodigoExel() {
            return codigoExel;
        }

        public void setCodigoExel(String codigoExel) {
            this.codigoExel = codigoExel;
        }

        public String getNomeSped() {
            return nomeSped;
        }

        public void setNomeSped(String nomeSped) {
            this.nomeSped = nomeSped;
        }

    }
    
    private String validarDetalhes(String valor){
        if(!StringUtil.isNullOrEmpty(valor))
            return valor;
        else
            return " ";
    
    }
    private void imprimirDetalheDeOjetoParaAnalize(ClassificacaoTributaria obj){
        StringBuilder sb = new StringBuilder();
        if( obj != null && obj.getProduto() != null){
            sb.append(String.format("|codigo_produto:%s ", validarDetalhes(obj.getProduto().getCodigoProduto())));
            sb.append(String.format("|ean:%s ", validarDetalhes(obj.getProduto().getEan())));
            sb.append(String.format("|descritivo_produto:%s ", validarDetalhes(obj.getProduto().getDescricao())));
            if(obj.getProduto().getPisCofins() != null){
                sb.append(String.format("|ncm:%s ", validarDetalhes(obj.getProduto().getPisCofins().getNcm())));
                sb.append(String.format("|ncm_ex:%s ", validarDetalhes(obj.getProduto().getPisCofins().getNcmEx())));
                sb.append(String.format("|cod_natureza_receita:%s ", validarDetalhes(obj.getProduto().getPisCofins().getCodNaturezaReceita())));
                sb.append(String.format("|mxf_piscofins_cst_e:%s ", validarDetalhes(obj.getProduto().getPisCofins().getPisCstE())));
                sb.append(String.format("|mxf_piscofins_cst_s:%s ", validarDetalhes(obj.getProduto().getPisCofins().getPisCstS())));
                sb.append(String.format("|mxf_pis_alq_e:%s ", validarDetalhes(obj.getProduto().getPisCofins().getPisAlqE())));
                sb.append(String.format("|mxf_pis_alq_s:%s ", validarDetalhes(obj.getProduto().getPisCofins().getPisAlqS())));
                sb.append(String.format("|mxf_cofins_alq_e:%s ", validarDetalhes(obj.getProduto().getPisCofins().getCofinsAlqE())));
                sb.append(String.format("|mxf_cofins_alq_s:%s ", validarDetalhes(obj.getProduto().getPisCofins().getCofinsAlqS())));
            }
            if(obj.getProduto().getIcmsSaida() != null){
                sb.append(String.format("|mxf_sac_cst:%s ", validarDetalhes(obj.getProduto().getIcmsSaida().getSacCst())));
                sb.append(String.format("|mxf_sac_alq:%s ", validarDetalhes(obj.getProduto().getIcmsSaida().getSacAlq())));
                sb.append(String.format("|mxf_sac_alqst:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSacAlqst())));
                sb.append(String.format("|mxf_sac_rbc:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSacRbc())));
                sb.append(String.format("|mxf_sac_rbcst:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSacRbcst())));
                sb.append(String.format("|mxf_sas_cst:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSasCst())));
                sb.append(String.format("|mxf_sas_alq:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSasAlq())));
                sb.append(String.format("|mxf_sas_alqst:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSasAlqst())));
                sb.append(String.format("|mxf_sas_rbc:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSasRbc())));
                sb.append(String.format("|mxf_sas_rbcst:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSasRbcst())));
                sb.append(String.format("|mxf_svc_cst:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSvcCst())));
                sb.append(String.format("|mxf_svc_alq:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSvcAlq())));
                sb.append(String.format("|mxf_svc_alqst:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSvcAlqst())));
                sb.append(String.format("|mxf_svc_rbc:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSvcRbc())));
                sb.append(String.format("|mxf_svc_rbcst:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSvcRbcst())));
                sb.append(String.format("|mxf_snc_cst:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSncCst())));
                sb.append(String.format("|mxf_snc_alq:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSncAlq())));
                sb.append(String.format("|mxf_snc_alqst:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSncAlqst())));
                sb.append(String.format("|mxf_snc_rbc:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSncRbc())));
                sb.append(String.format("|mxf_snc_rbcst:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getSncRbcst())));
                sb.append(String.format("|mxf_icms_s_fundamentolegal:%s ",validarDetalhes(obj.getProduto().getIcmsSaida().getFundamentoLegal())));
            }
            if(obj.getProduto().getIcmsEntrada() != null){
                sb.append(String.format("|mxf_icms_e_fundamentolegal:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getFundamentoLegal())));
                sb.append(String.format("|mxf_ei_cst:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getEiCst())));
                sb.append(String.format("|mxf_ei_alq:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getEiAlq())));
                sb.append(String.format("|mxf_ei_alqst:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getEiAlqst())));
                sb.append(String.format("|mxf_ei_rbc:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getEiRbc())));
                sb.append(String.format("|mxf_ei_rbcst:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getEiRbcst())));
                sb.append(String.format("|mxf_ed_cst:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getEdCst())));
                sb.append(String.format("|mxf_ed_alq:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getEdAlq())));
                sb.append(String.format("|mxf_ed_alqst:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getEdAlqst())));
                sb.append(String.format("|mxf_ed_rbc:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getEdRbc())));
                sb.append(String.format("|mxf_ed_rbcst:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getEdRbcst())));
                sb.append(String.format("|mxf_es_cst:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getEsCst())));
                sb.append(String.format("|mxf_es_alq:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getEsAlq())));
                sb.append(String.format("|mxf_es_alqst:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getEsAlqst())));
                sb.append(String.format("|mxf_es_rbc:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getEsRbc())));
                sb.append(String.format("|mxf_es_rbcst:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getEsRbcst())));
                sb.append(String.format("|mxf_nfi_cst:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getNfiCst())));
                sb.append(String.format("|mxf_nfd_cst:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getNfdCst())));
                sb.append(String.format("|mxf_nfs_csosn:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getNfsCsosn())));
                sb.append(String.format("|mxf_nf_alq:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getNfAlq())));
                sb.append(String.format("|mxf_icms_tipo_mva_rotulo:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getTipoMva())));
                sb.append(String.format("|mxf_icms_tipo_mva:%s ","0"));
                sb.append(String.format("|mxf_icms_mva_valor:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getMva())));
                sb.append(String.format("|mxf_mva_data_ini:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getMvaDataIni())));
                sb.append(String.format("|mxf_mva_data_fim:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getMvaDataFim())));
                sb.append(String.format("|mxf_credito_outorgado:%s ",validarDetalhes(obj.getProduto().getIcmsEntrada().getCreditoOutorgado())));
            }
            sb.append(String.format("|mxf_icms_mva_distribuidor_valor:%s ","0"));
            sb.append(String.format("|mxf_re_29560:%s ","0"));
        }
        log.info(sb.toString());        
    }

}
