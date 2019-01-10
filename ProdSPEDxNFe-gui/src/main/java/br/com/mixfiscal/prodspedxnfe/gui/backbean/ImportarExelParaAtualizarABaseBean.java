package br.com.mixfiscal.prodspedxnfe.gui.backbean;

import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributaria;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPED0200;
import br.com.mixfiscal.prodspedxnfe.services.LeitorSpedFiscal;
import br.com.mixfiscal.prodspedxnfe.services.ex.ArquivoSpedVazioException;
import br.com.mixfiscal.prodspedxnfe.services.ex.ErroAoCarregarDadosSpedException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ManagedBean(name = "ExelBean")
@SessionScoped
public class ImportarExelParaAtualizarABaseBean {
    private final Logger log = LogManager.getLogger(ImportarExelParaAtualizarABaseBean.class);
    
    // <editor-fold defaultstate="collapsed" desc="Membros Privados">
    private String caminhoExel;
    private Sheet sheet;
    private LeitorSpedFiscal leitorSped;
    private String nomeSped;
    // </editor-fold>

    // <editor-fold desc="Getters e Setters" defaulstate="collapsed">
    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public LeitorSpedFiscal getLeitorSped() {
        return leitorSped;
    }

    public void setLeitorSped(LeitorSpedFiscal leitorSped) {
        this.leitorSped = leitorSped;
    }

    public String getCaminhoExel() {
        return caminhoExel;
    }

    public void setCaminhoExel(String caminhoExel) {
        this.caminhoExel = caminhoExel;
    }

    public String getNomeSped() {
        return nomeSped;
    }

    public void setNomeSped(String nomeSped) {
        this.nomeSped = nomeSped;
    }
    // </editor-fold>
    
    /**
     * Creates a new instance of ImportarExelParaAtualizarABaseBean
     */
    public ImportarExelParaAtualizarABaseBean() {
        this.leitorSped = new LeitorSpedFiscal();
    }

    public void carregarArquivoSPED(String caminhoSped)
            throws ArquivoSpedVazioException, ErroAoCarregarDadosSpedException {
        if (this.getLeitorSped() != null) {
            this.getLeitorSped().setOperacaoDeRelatorio(true);
            this.getLeitorSped().lerArquivoSped(caminhoSped);
        }
    }

    public List<ExelcomSped> compararSpedComExel(List<ClassificacaoTributaria> listCT, List<SPED0200> listaSPED200) {
        try {
            List<ExelcomSped> listaComparacoes = new ArrayList();
            ExelcomSped objComparacos = new ExelcomSped();
            for (int i = 0; i <= 5; i++) {
                final ClassificacaoTributaria ct = listCT.get(i);
                Optional<SPED0200> ret = listaSPED200.stream()
                        .filter(e -> e.getCodItem().equals(ct.getProduto().getCodigoProduto())).findFirst();

                if (ret.isPresent()) {
                    SPED0200 spd = (SPED0200) ret.get();
                    objComparacos.setCodigoExel(ct.getProduto().getCodigoProduto());
                    objComparacos.setDescExel(ct.getProduto().getDescricao());
                    objComparacos.setEanExel(ct.getProduto().getEan());
                    objComparacos.setCodigoSped(spd.getCodItem());
                    objComparacos.setDescSped(spd.getDescrItem());
                    objComparacos.setEanSped(spd.getCodBarra());
                    listaComparacoes.add(objComparacos);
                }
            }
            return listaComparacoes;
        } catch (Exception e) {
            return null;
        }
    }

    public void importarExel(String caminho_arquivo_xls) {
        Workbook workbook = null;
        try {
            // Carrega planilha
            WorkbookSettings config = new WorkbookSettings();
            config.setEncoding("Cp1252");// configura acentuação
            workbook = Workbook.getWorkbook(new File(caminho_arquivo_xls), config);// recupera arquivo desejado
            // recupera pagina/planilha/aba do arquivo
            setSheet(workbook.getSheet(0));        
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            // fechar
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    public ClassificacaoTributaria carregarObjetoDaImportacaoDoExel(ClassificacaoTributaria objCT, Sheet sheet, int row) {
        try {
            // verifica se coluna e linha row não é vazia e adiciona o valor do campo no
            // atributo do obj!
            objCT.getProduto().setCodigoProduto(retornarConteudoDaCelula(sheet, 0, row)); //(sheet.getCell(0, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(0, row).getContents().toString());
            objCT.getProduto().setEan(retornarConteudoDaCelula(sheet, 1, row)); //(sheet.getCell(1, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(1, row).getContents().toString());
            objCT.getProduto().setDescricao(retornarConteudoDaCelula(sheet, 2, row)); //(sheet.getCell(2, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(2, row).getContents().toString());
            
            objCT.getProduto().getPisCofins().setNcm(retornarConteudoDaCelula(sheet, 9, row)); //(sheet.getCell(9, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(9, row).getContents().toString());
            objCT.getProduto().getPisCofins().setNcmEx(retornarConteudoDaCelula(sheet, 10, row)); //(sheet.getCell(10, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(10, row).getContents().toString());
            objCT.getProduto().getPisCofins().setCodNaturezaReceita(retornarConteudoDaCelula(sheet, 11, row)); //(sheet.getCell(11, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(11, row).getContents().toString());
            objCT.getProduto().getPisCofins().setPisCstE(retornarConteudoDaCelula(sheet, 12, row));//(sheet.getCell(12, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(12, row).getContents().toString());
            objCT.getProduto().getPisCofins().setPisCstS(retornarConteudoDaCelula(sheet, 13, row)); //(sheet.getCell(13, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(13, row).getContents().toString());
            objCT.getProduto().getPisCofins().setPisAlqE(retornarConteudoDaCelula(sheet, 14, row)); //(sheet.getCell(14, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(14, row).getContents().toString());
            objCT.getProduto().getPisCofins().setPisAlqS(retornarConteudoDaCelula(sheet, 15, row)); //(sheet.getCell(15, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(15, row).getContents().toString());
            objCT.getProduto().getPisCofins().setCofinsCstE(retornarConteudoDaCelula(sheet, 4, row));//(sheet.getCell(80, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(4, row).getContents().toString());
            objCT.getProduto().getPisCofins().setCofinsCstS(retornarConteudoDaCelula(sheet, 4, row)); //(sheet.getCell(80, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(4, row).getContents().toString());
            objCT.getProduto().getPisCofins().setCofinsAlqE(retornarConteudoDaCelula(sheet, 16, row)); //(sheet.getCell(16, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(16, row).getContents().toString());
            objCT.getProduto().getPisCofins().setCofinsAlqS(retornarConteudoDaCelula(sheet, 17, row)); //(sheet.getCell(17, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(17, row).getContents().toString());

            objCT.getProduto().getIcmsSaida().setSacCst(retornarConteudoDaCelula(sheet, 19, row)); //(sheet.getCell(19, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(19, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSacAlq(retornarConteudoDaCelula(sheet, 20, row)); //(sheet.getCell(20, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(20, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSacAlqst(retornarConteudoDaCelula(sheet, 21, row)); //(sheet.getCell(21, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(21, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSacRbc(retornarConteudoDaCelula(sheet, 22, row));//(sheet.getCell(22, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(22, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSacRbcst(retornarConteudoDaCelula(sheet, 23, row));//(sheet.getCell(23, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(23, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSasCst(retornarConteudoDaCelula(sheet, 24, row));//(sheet.getCell(24, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(24, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSasAlq(retornarConteudoDaCelula(sheet, 25, row));//(sheet.getCell(25, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(25, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSasAlqst(retornarConteudoDaCelula(sheet, 26, row));//(sheet.getCell(26, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(26, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSasRbc(retornarConteudoDaCelula(sheet, 27, row));//(sheet.getCell(27, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(27, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSasRbcst(retornarConteudoDaCelula(sheet, 28, row));//(sheet.getCell(28, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(28, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSvcCst(retornarConteudoDaCelula(sheet, 29, row));//(sheet.getCell(29, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(29, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSvcAlq(retornarConteudoDaCelula(sheet, 30, row));//(sheet.getCell(30, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(30, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSvcAlqst(retornarConteudoDaCelula(sheet, 31, row));//(sheet.getCell(31, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(31, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSvcRbc(retornarConteudoDaCelula(sheet, 32, row));//(sheet.getCell(32, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(32, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSvcRbcst(retornarConteudoDaCelula(sheet, 33, row));//(sheet.getCell(33, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(33, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSncCst(retornarConteudoDaCelula(sheet, 34, row));//(sheet.getCell(34, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(34, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSncAlq(retornarConteudoDaCelula(sheet, 35, row));//(sheet.getCell(35, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(35, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSncAlqst(retornarConteudoDaCelula(sheet, 36, row));//(sheet.getCell(37, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(36, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSncRbc(retornarConteudoDaCelula(sheet, 37, row));//(sheet.getCell(37, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(37, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setSncRbcst(retornarConteudoDaCelula(sheet, 38, row));//(sheet.getCell(38, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(38, row).getContents().toString());
            objCT.getProduto().getIcmsSaida().setFundamentoLegal(retornarConteudoDaCelula(sheet, 40, row));//(sheet.getCell(40, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(40, row).getContents().toString());
            
            objCT.getProduto().getIcmsEntrada().setEiCst(retornarConteudoDaCelula(sheet, 41, row));//(sheet.getCell(41, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(41, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setEiAlq(retornarConteudoDaCelula(sheet, 42, row));//(sheet.getCell(42, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(42, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setEiAlqst(retornarConteudoDaCelula(sheet, 43, row));//(sheet.getCell(43, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(43, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setEiRbc(retornarConteudoDaCelula(sheet, 44, row));//(sheet.getCell(44, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(44, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setEiRbcst(retornarConteudoDaCelula(sheet, 45, row));//(sheet.getCell(45, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(45, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setEdCst(retornarConteudoDaCelula(sheet, 46, row));//(sheet.getCell(46, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(46, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setEdAlq(retornarConteudoDaCelula(sheet, 47, row));//(sheet.getCell(47, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(47, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setEdAlqst(retornarConteudoDaCelula(sheet, 48, row));//(sheet.getCell(48, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(48, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setEdRbc(retornarConteudoDaCelula(sheet, 49, row));//(sheet.getCell(49, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(49, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setEdRbcst(retornarConteudoDaCelula(sheet, 50, row));//(sheet.getCell(50, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(50, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setEsCst(retornarConteudoDaCelula(sheet, 51, row));//(sheet.getCell(51, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(51, row).getContents());
            objCT.getProduto().getIcmsEntrada().setEsAlq(retornarConteudoDaCelula(sheet, 52, row));//(sheet.getCell(52, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(52, row).getContents());
            objCT.getProduto().getIcmsEntrada().setEsAlqst(retornarConteudoDaCelula(sheet, 53, row));//(sheet.getCell(53, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(53, row).getContents());
            objCT.getProduto().getIcmsEntrada().setEsRbc(retornarConteudoDaCelula(sheet, 54, row));//(sheet.getCell(54, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(54, row).getContents());
            objCT.getProduto().getIcmsEntrada().setEsRbcst(retornarConteudoDaCelula(sheet, 55, row));//(sheet.getCell(55, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(55, row).getContents());
            objCT.getProduto().getIcmsEntrada().setNfiCst(retornarConteudoDaCelula(sheet, 56, row));//(sheet.getCell(56, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(56, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setNfdCst(retornarConteudoDaCelula(sheet, 57, row));//(sheet.getCell(57, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(57, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setNfsCsosn(retornarConteudoDaCelula(sheet, 58, row));//(sheet.getCell(58, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(58, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setNfAlq(retornarConteudoDaCelula(sheet, 59, row));//(sheet.getCell(59, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(59, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setFundamentoLegal(retornarConteudoDaCelula(sheet, 39, row));//(sheet.getCell(39, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(39, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setTipoMva(retornarConteudoDaCelula(sheet, 60, row));//(sheet.getCell(60, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(60, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setMva(retornarConteudoDaCelula(sheet, 61, row));//(sheet.getCell(61, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(61, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setMvaDataIni(retornarConteudoDaCelula(sheet, 63, row));//(sheet.getCell(63, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(63, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setMvaDataFim(retornarConteudoDaCelula(sheet, 64, row));//(sheet.getCell(64, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(64, row).getContents().toString());
            objCT.getProduto().getIcmsEntrada().setCreditoOutorgado(retornarConteudoDaCelula(sheet, 65, row));//(sheet.getCell(65, row).getContents().isEmpty() ? "campo vazio" : sheet.getCell(65, row).getContents().toString());
        } catch (Exception e) {
            System.out.println(e);
        }
        return objCT;
    }

    public List<ClassificacaoTributaria> percorrerPlanoExelParaTesteDeConfirmacaoDoUsusario(Sheet sheet) {
        List<ClassificacaoTributaria> listCT = new ArrayList();
        try {
            // recupera numero de linhas
            int linhas = sheet.getRows();
            // percorre todas as linhas da planilha
            for (int row = 1; row <= 5; row++) {
                // cria meu objeto que recebe as linhas
                ClassificacaoTributaria objCT = new ClassificacaoTributaria();
                carregarObjetoDaImportacaoDoExel(objCT, sheet, row);
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
        return listCT;
    }

    private String retornarConteudoDaCelula(Sheet sheet, int indiceColuna, int linha) {
        return sheet.getCell(indiceColuna, linha).getContents().isEmpty()
                ? "campo vazio"
                : sheet.getCell(indiceColuna, linha).getContents();
    }
    
    public class ExelcomSped {
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

    }

}
