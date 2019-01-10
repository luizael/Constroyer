package br.com.mixfiscal.prodspedxnfe.services.relatorio;

import br.com.mixfiscal.prodspedxnfe.dao.own.ClassificacaoTributariaProdutoDAO;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import br.com.mixfiscal.prodspedxnfe.dao.own.EmpresaDAO;
import br.com.mixfiscal.prodspedxnfe.dao.own.FornecedorDAO;
import br.com.mixfiscal.prodspedxnfe.dao.own.RelacaoProdutoFornecedorDAO;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoModeloDocumento;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoNotaFiscal;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoRelatorio;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.CFe;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS00;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS10;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS20;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS30;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS40;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS41;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS50;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS51;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS60;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS70;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS90;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFe;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFeItem;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributaria;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributariaProduto;
import br.com.mixfiscal.prodspedxnfe.domain.own.Cliente;
import br.com.mixfiscal.prodspedxnfe.domain.own.Empresa;
import br.com.mixfiscal.prodspedxnfe.domain.own.RelacaoProdutoFornecedor;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPED0000;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPED0200;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC100;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC150;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC170;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC190;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC460;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC470;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC800;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC850;
import br.com.mixfiscal.prodspedxnfe.domain.wsmix.ParamJson;
import br.com.mixfiscal.prodspedxnfe.sal.ConsultaMixFiscal;
import br.com.mixfiscal.prodspedxnfe.services.ClassificacaoTributariaService;
import br.com.mixfiscal.prodspedxnfe.services.ClienteService;
import br.com.mixfiscal.prodspedxnfe.services.LeitorSpedFiscal;
import br.com.mixfiscal.prodspedxnfe.services.LeitorXmlCFe;
import br.com.mixfiscal.prodspedxnfe.services.LeitorXmlNFe;
import br.com.mixfiscal.prodspedxnfe.services.ex.ArquivoSpedVazioException;
import br.com.mixfiscal.prodspedxnfe.services.ex.DadosNFeNaoCarregadosException;
import br.com.mixfiscal.prodspedxnfe.services.ex.DadosSpedNaoCarregadosException;
import br.com.mixfiscal.prodspedxnfe.services.ex.ErroAoCarregarDadosNFeException;
import br.com.mixfiscal.prodspedxnfe.services.ex.ErroAoCarregarDadosSpedException;
import br.com.mixfiscal.prodspedxnfe.services.util.MetodosUtil;
import br.com.mixfiscal.prodspedxnfe.util.Masc;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;

public class RelatorioDebitoCredito {
    private LeitorSpedFiscal leitorSped;
    private LeitorXmlNFe leitorXmlNFe;
    private LeitorXmlCFe leitorXmlCFe;
    private String caminhoArquivoSped;
    private String caminhoDirXMLsNFes;
    private String caminhoDirXMLsNFCes;
    private String caminhoDirXMLsCFes;
    private String caminhoExel;
    private StringBuilder sbRelInconsistente; // Relatório de inconsistencia entre MIX,SPED,Xml
    private StringBuilder sbRelRegistroNaoEncontrado;
    private ClassificacaoTributariaProduto tributacaoMix;
    private ConsultaMixFiscal consultaMix;
    private List<SPEDC170> itensSC170;
    private List<NFeItem> itensNFe;
    private Map<String, NFe> listaXmlNfe;
    private Map<String, NFe> listaXmlNFCe;
    private List<ClassificacaoTributaria> itensMix;
    private String token;
    private RelacaoProdutoFornecedorDAO relProdFornecDao;
    private Empresa empresaAtual;
    private EmpresaDAO empDao;
    private Fornecedor fornec;
    private FornecedorDAO fornecDao;
    private Relatorio relatorio;
    private List<Relatorio> listaRelatorio;
    private BigDecimal valorTotalEstornoCredito;
    private BigDecimal valorTotalCreditonaoAproveitado;
    private String ean;
    private NFe nfe;
    private CFe cfe;
    private BigDecimal totalDebitoIndevido;
    private BigDecimal totalDebitoNaoDeclarado;
    private SimpleDateFormat dt = new SimpleDateFormat("ddmmyyyy");
    private String naoSeAplica = "não se aplica";
    private int totalItem;
    private int contadorProgresso;
    private String descricaoProgresso;
    private String tipoRelatorio;
    private SPED0000 sped000;
    private SPEDC850 spdc850;
    private Logger log;
    private String codigoCompara = "";
    private String descricaoCompara = "";
    private MetodosUtil mtUtil = new MetodosUtil();

    // <editor-fold desc="Construtores" defaultstate="collapsed">
    public RelatorioDebitoCredito() {
       
        this.leitorSped = new LeitorSpedFiscal();
        this.leitorXmlNFe = new LeitorXmlNFe();
        this.leitorXmlCFe = new LeitorXmlCFe();
        this.itensMix = new ArrayList<>();
        this.tributacaoMix = new ClassificacaoTributariaProduto();
        this.consultaMix = new ConsultaMixFiscal();
        this.itensSC170 = new ArrayList<>();
        this.itensNFe = new ArrayList<>();
        this.empDao = new EmpresaDAO();
        this.fornecDao = new FornecedorDAO();
        this.relProdFornecDao = new RelacaoProdutoFornecedorDAO();
        this.valorTotalEstornoCredito = BigDecimal.ZERO;
        this.valorTotalCreditonaoAproveitado = BigDecimal.ZERO;
        this.ean = "";
        this.totalDebitoIndevido = BigDecimal.ZERO;
        this.totalDebitoNaoDeclarado = BigDecimal.ZERO;
        this.contadorProgresso = 0;
        this.log = LogManager.getLogger(RelatorioDebitoCredito.class);
        // this.consultaTributacao.lerDirJsons("C:\\Users\\Lucas\\Documents\\Projetos\\LuluSoftwares\\MixFiscal\\wsmix\\tmp\\ws");
        this.sbRelInconsistente = new StringBuilder(
                "Chave; CodigoInternoXML; CodigoInternoCSV; DescricaoXML; DescricaoCSV; porc \r\n");
        this.sbRelRegistroNaoEncontrado = new StringBuilder("Codigo; Descricao; Chave \r\n");
    }

    public RelatorioDebitoCredito(LeitorSpedFiscal leitorSped) {
        this();
        this.leitorSped = leitorSped;
    }

    public RelatorioDebitoCredito(LeitorSpedFiscal leitorSped, LeitorXmlNFe leitorXmlNFe) {
        this(leitorSped);
        this.leitorXmlNFe = leitorXmlNFe;
    }
    // </editor-fold>

    // <editor-fold desc="Get e Set" defaultstate="collapsed">
    public String getCaminhoArquivoSped() {
        return caminhoArquivoSped;
    }

    public void setCaminhoArquivoSped(String caminhoArquivoSped)
            throws ArquivoSpedVazioException, ErroAoCarregarDadosSpedException {
        this.caminhoArquivoSped = caminhoArquivoSped;
        this.carregarArquivoSPED(caminhoArquivoSped);
    }

    public String getCaminhoDirXMLsNFes() {
        return caminhoDirXMLsNFes;
    }

    public void setCaminhoDirXMLsNFes(String caminhoDirXMLsNFes) {
        this.caminhoDirXMLsNFes = caminhoDirXMLsNFes;
    }

    public String getCaminhoDirXMLsNFCes() {
        return caminhoDirXMLsNFCes;
    }

    public void setCaminhoDirXMLsNFCes(String caminhoDirXMLsNFCes) {
        this.caminhoDirXMLsNFCes = caminhoDirXMLsNFCes;
    }

    public String getCaminhoDirXMLsCFes() {
        return caminhoDirXMLsCFes;
    }

    public void setCaminhoDirXMLsCFes(String caminhoDirXMLsCFes) {
        this.caminhoDirXMLsCFes = caminhoDirXMLsCFes;
    }

    public String getCaminhoExel() {
        return caminhoExel;
    }

    public void setCaminhoExel(String caminhoExel) {
        this.caminhoExel = caminhoExel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BigDecimal getValorTotalEstornoCredito() {
        return valorTotalEstornoCredito;
    }

    public void setValorTotalEstornoCredito(BigDecimal valorTotalEstornoCredito) {
        this.valorTotalEstornoCredito = valorTotalEstornoCredito;
    }

    public BigDecimal getValorTotalCreditonaoAproveitado() {
        return valorTotalCreditonaoAproveitado;
    }

    public void setValorTotalCreditonaoAproveitado(BigDecimal valorTotalCreditonaoAproveitado) {
        this.valorTotalCreditonaoAproveitado = valorTotalCreditonaoAproveitado;
    }

    public BigDecimal getTotalDebitoIndevido() {
        return totalDebitoIndevido;
    }

    public void setTotalDebitoIndevido(BigDecimal totalDebitoIndevido) {
        this.totalDebitoIndevido = totalDebitoIndevido;
    }

    public BigDecimal getTotalDebitoNaoDeclarado() {
        return totalDebitoNaoDeclarado;
    }

    public void setTotalDebitoNaoDeclarado(BigDecimal totalDebitoNaoDeclarado) {
        this.totalDebitoNaoDeclarado = totalDebitoNaoDeclarado;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getContadorProgresso() {
        return contadorProgresso;
    }

    public void setContadorProgresso(int contadorProgresso) {
        this.contadorProgresso = contadorProgresso;
    }

    public String getDescricaoProgresso() {
        return descricaoProgresso;
    }

    public void setDescricaoProgresso(String descricaoProgresso) {
        this.descricaoProgresso = descricaoProgresso;
    }

    public LeitorSpedFiscal getLeitorSped() {
        return leitorSped;
    }

    public void setLeitorSped(LeitorSpedFiscal leitorSped) {
        this.leitorSped = leitorSped;
    }

    public Relatorio getRelGeral() {
        return relatorio;
    }

    public void setRelGeral(Relatorio relGeral) {
        this.relatorio = relGeral;
    }

    public List<Relatorio> getListaRelatorio() {
        return listaRelatorio;
    }

    public void setListaRelatorio(List<Relatorio> listaRelatorio) {
        this.listaRelatorio = listaRelatorio;
    }

    public String getTipoRelatorio() {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(String tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }
    // </editor-fold>

    // <editor-fold desc="Métodos Publicos" defaultstate="collapsed">
    public void carregarArquivoSPED(String caminhoSped)
            throws ArquivoSpedVazioException, ErroAoCarregarDadosSpedException {
        if (this.leitorSped != null) {
            this.leitorSped.setOperacaoDeRelatorio(true);
            this.descricaoProgresso = "lendo SPED aguarde..";
            this.leitorSped.lerArquivoSped(caminhoSped);
        }
    }

    public boolean atualizarTributacaoMix() throws DadosSpedNaoCarregadosException {
        log.info("Iniciando atualização dos dados da tributação da Mix Fiscal");
        
        ClassificacaoTributariaService classTribServce = new ClassificacaoTributariaService();

        log.info("Iniciando a reorganização dos Dados Do Sped");
        HashMap<SPEDC150, HashMap<SPED0200, List<SPEDC100>>> dadosSped = reorganizarDadosSped();
        log.info("Finalizou a reorganização dos Dados Do Sped");
        // cria um HashMap onde
        // o chave é o
        // fornecedor, o valor é
        // um HashMap com a
        // chave sendo produto e
        // o valor sendo uma
        // lista de Nota fiscal
        // obtendo a chave do cliente
        this.log.info("Atualizando banco de dados pelo webservice");
        sped000 = this.leitorSped.getSped0000();
        this.setDescricaoProgresso("Verificando Token...");
        
        try {
            this.setToken(obterTokenDoCliente(sped000).getToken());
        } catch (Exception ex) {
            this.log.error(ex.getMessage());
            this.setDescricaoProgresso(ex.getMessage());
            return false;
        }

        if (StringUtil.isNullOrEmpty(this.getToken())) {
            this.setDescricaoProgresso("Tokens não cadastrado");
            return false;
        }
        
        // limpa registro temporario
        //ClassificacaoTributaria cl = new ClassificacaoTributaria();
        //cl.setCnpjTrib(sped000.getCNPJ());
        
        try {
            this.setDescricaoProgresso("Limpando registros temporarios...");
            classTribServce.excluirPorCNPJCliente(sped000.getCNPJ()); //excluirCustomizado(cl);
        } catch (Exception ex) {
            this.log.error(ex.getMessage(), ex);
        }
        
        // obtendo a lista de produtos

        List<SPED0200> produtos = null;
        
        try {
            this.setDescricaoProgresso("Obtendo Lista de produtos...");
            produtos = this.leitorSped.getListaSPED0200();
            this.totalItem = produtos.size();
        } catch (Exception ex) {
            this.log.error(ex.getMessage(), ex);
            return false;
        }
        
        this.contadorProgresso = 0;
        this.setDescricaoProgresso("Atualizando Cadastro: " + sped000.getNomeEmpresarialEntidade() + "...");
        
        produtos.forEach(prod -> {
            try {
                this.contadorProgresso++;
                ClassificacaoTributariaService clTrib = new ClassificacaoTributariaService();
                ClassificacaoTributaria classTribMix = this.consultarWebService(this.getToken(), prod);                
                // verifica se ja nao existe o produto no banco
                ClassificacaoTributaria classTribConsulta = clTrib.selecionarPorCNPJCliente(sped000.getCNPJ());
                if (classTribConsulta.getProduto().getIdProduto() == null) {
                    classTribMix.getProduto().setDescricao(prod.getDescrItem());
                    classTribMix.getProduto().setCodigoProduto(prod.getCodItem());
                    clTrib.salvar(classTribMix);
                }
                 this.setDescricaoProgresso("Cliente: " + sped000.getNomeEmpresarialEntidade());
                // this.itensMix.add(classTribMix);
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
        });
        return true;
    }

    public void gerarRelatorioDivergenciaEntrada() throws DadosSpedNaoCarregadosException {
        // HashMap onde o chave principal é o fornecedor e o valor é um MAP com a chave
        // sendo o produto
        // e o valor uma lista de notafiscal

        HashMap<SPEDC150, HashMap<SPED0200, List<SPEDC100>>> dadosSped = reorganizarDadosSped();
        this.listaRelatorio = new ArrayList<>();

        sped000 = this.leitorSped.getSped0000();
        this.setDescricaoProgresso("Verificando Token...");
        Cliente cli = null;
        try {
            cli = obterTokenDoCliente(sped000);
            this.setToken(cli.getToken());
        } catch (Exception ex) {
            this.descricaoProgresso = "Token inválido" + ex.getMessage();
            this.log.error(ex.getMessage(), ex);
            return;
        }
        this.setDescricaoProgresso("Calculando Total Registro C150...");
        this.contadorProgresso = 0;
        this.setTotalItem(dadosSped.size());
        this.setDescricaoProgresso("Gerando relatório divergencia entrada...");
        log.info("relatorio de entrada: percorrer a lista dadosSped");
        dadosSped.forEach((fornecSpdC150, prods) -> {// percorre o bloco referente ao fornecedor ou participantes no
            // sped
            this.contadorProgresso++;
            if(fornecSpdC150 == null || prods == null)return;           
            //try {
                log.info("relatorio de entrada: percorrer a lista fornecSpdC150, *prods");
                prods.forEach((prod, nfeSpedc100) -> {// para cada fornecedor, percorre seus produtos
                    if(prod == null || nfeSpedc100 == null)return;
                    //try {
                        log.info("relatorio de entrada: percorrer a lista prod, *nfeSpedc100");
                        nfeSpedc100.stream().forEach((SPEDC100 spd100) -> {// percorre as notas do produto
                            
                            if(spd100 == null)return;                           
                                String chave = "";
                                try{
                                    chave = spd100.getChaveNFe();
                                }catch(Exception ex){ log.error(ex.getMessage(), ex);}
                                log.info("relatorio de entrada:  percorrendo nota a nota na lista nfeSpedc100->" + chave);
                                
                                try {
                                    // filtro: se for nota de saida, nao gera o relatorio
                                    if (spd100.getTipoNotaFiscal() != ETipoNotaFiscal.Entrada) {
                                        return;// verifica se é uma nota de entrada
                                    }
                                    // filtro: se o produto nao pertencer a nota nao, gera relatorio
                                    SPEDC170 item = this.localizarProdutoEmSPEDC170(prod, spd100.getListaSPEDC170());
                                    if (item == null) {
                                        return;
                                    }

                                    // filtro: se o produto for tributado como simples nacional, nao gera relatorio,
                                    if (mtUtil.verificarSimplesNacional(item.getCSTICMS())) {
                                        return;
                                    }

                                    // filtro: se o produto nao existir na base da mix, nao gera relatorio
                                    try {
                                        this.tributacaoMix = this.obterTributacaoMix(item.getSPED0200(), ETipoRelatorio.Entrada);
                                    } catch (Exception ex) {
                                        this.log.error(" acessando o metodo obterTributacaoMix()" + ex.getMessage(), ex);
                                        return;
                                    }
                                    // filtro: se o CST que veio da mix for nulo ou vazio, nao gera relatorio
                                    try{
                                        if(tributacaoMix == null ||
                                            tributacaoMix.getIcmsEntrada()  == null ||
                                            tributacaoMix.getIcmsEntrada().getEdCst() == null ||
                                            StringUtil.isNullOrEmpty(tributacaoMix.getIcmsEntrada().getEdCst().trim()) ||
                                            tributacaoMix.getIcmsEntrada().getEdCst().trim().equals("campo vazio")){
                                            this.sbRelRegistroNaoEncontrado.append(String.format("'%s;%s;'%s;\r\n", item.getCodItem(), item.getSPED0200().getDescrItem(),chave));
                                                return;
                                            }
                                    }catch(Exception ex){
                                        log.error("erro aoverificar cst da mix "+ ex.getMessage(), ex);
                                        return;
                                    }

                                    // filtro: se o CST e a aliquota de Icms do sped for igual a mix, nao gera
                                    // relatorio
                                    String cstIcmsSped = item.getCSTICMS();
                                    if (cstIcmsSped.length() > 1) {cstIcmsSped = cstIcmsSped.substring(1);}
                                    try{
                                        if (mtUtil.comparaCstSpedMix(cstIcmsSped,
                                                tributacaoMix.getIcmsEntrada().getEdCst().trim())
                                                && mtUtil.compararAliquotaSpedMix(item.getAliqICMS(), new BigDecimal(
                                                        tributacaoMix.getIcmsEntrada().getEdAlq().trim()))) {
                                            return;
                                        }
                                    }catch(Exception ex){
                                        log.error("erro comparar CstSpedMix AliquotaSpedMix da mix "+ ex.getMessage(), ex);
                                        return;
                                    }

                                    // filtro: se CFOP do produto nao for de entrada, nao gera relatorio
                                    try{
                                        if (!this.identificarCFOPEntrada(item.getCFOP())) {
                                            return;
                                        }
                                    }catch(Exception ex){
                                        log.error("erro ao identificar CFOP entrada "+ ex.getMessage(), ex);
                                        return;
                                    }

                                    // inicializando o relatorio
                                    relatorio = new Relatorio();
                                    BigDecimal base = new BigDecimal(100);

                                    relatorio.setChaveNfe("'" + spd100.getChaveNFe() + "'");
                                    relatorio.setClienteFornecedor(spd100.getFornecedor().getNome());
                                    relatorio.setNumeroNota(spd100.getNumeroNF());
                                    relatorio.setDescricaoProduto(prod.getDescrItem());
                                    relatorio.setEan("'" + prod.getCodBarra() + "'");
                                    relatorio.setCodigoInternoProduto(prod.getCodItem());
                                    relatorio.setCstIcmsSped(cstIcmsSped);
                                    String edCst = "0";
                                    try {
                                        relatorio.setCstIcmsMix(
                                                tributacaoMix.getIcmsEntrada().getEdCst().trim());
                                    } catch (Exception ex) {
                                        this.log.error("setando CStIcms " + ex.getMessage(), ex);
                                        relatorio.setCstIcmsMix(edCst);
                                    }
                                    relatorio.setCfopSped(!StringUtil.isNullOrEmpty(item.getCFOP()) ? item.getCFOP() : "nao tem");
                                    relatorio.setAliqIcmsSped(BigDecimal.ZERO);
                                    relatorio.setBaseDeCalculoSped(BigDecimal.ZERO);
                                    relatorio.setRedBcSped(BigDecimal.ZERO);
                                    relatorio.setCargaTributariaSped(BigDecimal.ZERO);
                                    relatorio.setAliqIcmsMix(BigDecimal.ZERO);
                                    relatorio.setRedBcMix(BigDecimal.ZERO);
                                    relatorio.setBaseDeCalculoMix(BigDecimal.ZERO);
                                    relatorio.setCargaTributariaMix(BigDecimal.ZERO);
                                    relatorio.setEstornoDeCredito(BigDecimal.ZERO);
                                    relatorio.setCreditoIndevido(BigDecimal.ZERO);

                                    try {
                                        relatorio.setValorOperacao(item.getValor());
                                    } catch (Exception ex) {
                                        this.log.error("setando valorOperacao " + ex.getMessage(), ex);
                                        relatorio.setValorOperacao(BigDecimal.ZERO);
                                    }
                                    // inicializar valores referente a sped
                                    try {
                                        relatorio.setAliqIcmsSped(item.getAliqICMS());
                                    } catch (Exception ex) {
                                        this.log.error("setando AliqIcmsSped " + ex.getMessage(), ex);
                                    }
                                    try {
                                        relatorio.setBaseDeCalculoSped(item.getValorBCICMS());
                                    } catch (Exception ex) {
                                        this.log.error("setando  BaseCalculoSed " + ex.getMessage(), ex);
                                    }
                                    try {
                                        relatorio.setCargaTributariaSped(item.getValorICMS());
                                    } catch (Exception ex) {
                                        this.log.error("setando CargaTributariaSped " + ex.getMessage(), ex);
                                    }
                                    // inicializando valores referente a mix
                                    try {
                                        relatorio.setAliqIcmsMix(new BigDecimal(
                                                tributacaoMix.getIcmsEntrada().getEdAlq().trim()));
                                    } catch (Exception ex) {
                                        this.log.error("setando AliqIcmsMix " + ex.getMessage(), ex);
                                    }
                                    try {
                                        relatorio.setRedBcMix(new BigDecimal(
                                                tributacaoMix.getIcmsEntrada().getEdRbc().trim()));
                                    } catch (Exception ex) {
                                        this.log.error("setando RedBcMix " + ex.getMessage(), ex);
                                    }
                                    // ignora os CST's da MIX abaixo
                                    if (relatorio.getCstIcmsMix().trim().equals("10")
                                            || relatorio.getCstIcmsMix().trim().equals("30")
                                            || relatorio.getCstIcmsMix().trim().equals("40")
                                            || relatorio.getCstIcmsMix().trim().equals("41")
                                            || relatorio.getCstIcmsMix().trim().equals("51")
                                            || relatorio.getCstIcmsMix().trim().equals("60")
                                            || relatorio.getCstIcmsMix().trim().equals("70")) {
                                    }

                                    if (relatorio.getCstIcmsMix().trim().equals("0")// calculando o CST 00
                                            || relatorio.getCstIcmsMix().trim().equals("00")) {
                                        try {
                                            BigDecimal baseCalculo = item.getValor()
                                                    .multiply(new BigDecimal(
                                                            tributacaoMix.getIcmsEntrada().getEdAlq().trim()))
                                                    .divide(base);
                                            relatorio.setBaseDeCalculoMix(mtUtil.arredondarValorDecimal(baseCalculo));
                                            relatorio.setCargaTributariaMix(mtUtil.arredondarValorDecimal(baseCalculo));
                                        } catch (Exception ex) {
                                            this.log.error(" calculando o  CST 00: " + ex.getMessage(), ex);
                                        }
                                    }

                                    if (relatorio.getCstIcmsMix().trim().equals("20")) {// calculando o CST 20
                                        // Encontrado a redução da base de calculo do item do SPED
                                        try {
                                            BigDecimal fator = item.getValor().subtract(item.getValorBCICMS());
                                            fator = fator.multiply(base);
                                            BigDecimal bcSped = fator.divide(item.getValor());
                                            relatorio.setRedBcSped(mtUtil.arredondarValorDecimal(bcSped));
                                        } catch (Exception ex) {
                                            this.log.error("calculando o  CST 20: " + ex.getMessage());
                                        }
                                        try {
                                            // encontrando o fator de multiplicação
                                            BigDecimal fator = base
                                                    .subtract(new BigDecimal(
                                                            tributacaoMix.getIcmsEntrada().getEdRbc().trim()))
                                                    .divide(base);
                                            // encontrando a base de calculo
                                            BigDecimal baseCalculo = item.getValor().multiply(fator);
                                            relatorio.setBaseDeCalculoMix(mtUtil.arredondarValorDecimal(baseCalculo));

                                            // encontrando a carga tributaria da Mix
                                            BigDecimal cargaTrib = baseCalculo
                                                    .multiply(new BigDecimal(
                                                            tributacaoMix.getIcmsEntrada().getEdAlq().trim()))
                                                    .divide(base);
                                            relatorio.setCargaTributariaMix(mtUtil.arredondarValorDecimal(cargaTrib));

                                        } catch (Exception ex) {
                                            this.log.error("encontrando a base de calculo da mix: " + ex.getMessage());
                                        }
                                    }
                                    if(this.calcularEstornoCreditoNaoAproveitado(item))
                                        listaRelatorio.add(relatorio);// criando a lista de itens com divergencia
                                }catch(Exception ex){
                                    this.log.error("erro relatorio de entrada:  percorre as notas do produto : " + ex.getMessage(), ex);
                                }
                                
                        });
                    //} catch (Exception ex) {
                    //    this.log.error("percorre as notas do produto: " + ex.getMessage(), ex);
                   // }
                });
            //} catch (Exception ex) {
            //    this.log.error("percorre os produtos do fornecedor: " + ex.getMessage(), ex);
            //}
        });
        dadosSped.clear();
        String caminho = this.caminhoArquivoSped;
        String arquivo = this.caminhoArquivoSped.substring(caminho.lastIndexOf("\\")+1);
        caminho = caminho.replace(arquivo,"");
        caminho = caminho + File.separator + "registrosNaoEncontradosMix.csv";
        try{
            mtUtil.criarArquivoCSV(caminho, this.sbRelRegistroNaoEncontrado);
        }catch(Exception ex){
            this.log.error("erro ao gerar  csv de registros nao encontrados 65 "+ ex.getMessage(), ex);
        }
    }

    public List<Relatorio> gerarRelatorioDivergenciaSaida()
            throws DadosSpedNaoCarregadosException, ErroAoCarregarDadosNFeException, DadosNFeNaoCarregadosException {
        this.listaRelatorio = new ArrayList<>();
        List<Object> todoOsItens = new ArrayList<>();
        List<ClassificacaoTributaria> produtosClasificados = new ArrayList<>();
        this.itensMix.clear();
        this.log.info("gerando relatorio de saida");
        sped000 = this.leitorSped.getSped0000();

        this.setDescricaoProgresso("Verificando Token...");
        try {
            this.setToken(obterTokenDoCliente(sped000).getToken());
        } catch (Exception ex) {
            this.setDescricaoProgresso("Token inválido: " + ex.getMessage());
            this.log.error("erro ao obter o token do cliente " + sped000.getCNPJ() + ", " + ex.getMessage(), ex);
        }
        this.setDescricaoProgresso("Lendo produtos classificados...");
        try {
            ClassificacaoTributariaService listaClass = new ClassificacaoTributariaService();
            ClassificacaoTributaria prodCl = new ClassificacaoTributaria();
            //prodCl.setCnpjTrib(sped000.getCNPJ());
            // this.itensMix = listaClass.listarTudo(prodCl);
        } catch (Exception ex) {
            this.log.error(ex.getMessage(), ex);
        }

        this.setDescricaoProgresso("Lendo C100...");
        List<SPEDC100> listC100 = this.leitorSped.getListaSPEDC100();// NF-e,NFC-e

        this.setDescricaoProgresso("Lendo C460...");
        List<SPEDC460> listC460 = this.leitorSped.getListaSPEDC460(); // Cupom

        this.setDescricaoProgresso("Lendo C800...");
        List<SPEDC800> listC800 = this.leitorSped.getListaSPEDC800();// SAT

        this.contadorProgresso = 0;
        this.setTotalItem(listC100.size());
        this.setDescricaoProgresso("Gerando relatório saída C100 (55/65)...");

        // <editor-fold desc="Listando C100 do SPED NF-e" defaultstate="collapsed">
        listC100.forEach(spd100 -> {
            this.contadorProgresso++;
            if(spd100 == null)return;
            if (spd100.getModeloDocumento().equals("55")) {
                log.info("relatorio de saida 55: pércorrendo o registro C100 ["+ spd100.getChaveNFe()+ "]");
                if (!contemCFOPSaida(spd100.getSPEDC190())) {
                    return;// verifica o CFOP de saida do sped antes de prosseguir
                }
                try {
                    this.nfe = this.buscarXmlNFe(spd100.getChaveNFe());
                } catch (Exception ex) {
                    this.log.error(
                            "nao encontro o arquivo referente a chave no metodo buscarXmlNFe() " + spd100.getChaveNFe() + ", " + ex.getMessage(),
                            ex);
                }
                if (this.nfe == null) {
                    return;
                }
                if (this.nfe.getTipoNotaFiscal() == ETipoNotaFiscal.Entrada) {
                    return;
                }

                try {
                    compararRegistros(spd100.getSPEDC190(), nfe.getItens(), String.format("%s%s",
                            this.caminhoDirXMLsNFes, File.separator + "registrosInconsistentesNFe_.csv"),
                            this.sbRelInconsistente);
                } catch (Exception ex) {
                    this.log.error("erro no metodo compararRegistr() " + ex.getMessage(), ex);
                }

                try {
                    this.nfe.getItens().forEach(NFeItem -> {
                        if(NFeItem == null)return;
                        log.info("relatorio de saida 55: percorrendo o item da XML [" + NFeItem.getDescricaoProduto() + "], CHAVE :" + nfe.getChaveNFe());
                        SPED0200 spd0200 = new SPED0200();
                        // ClassificacaoTributaria tributacaoMix = null;

                        String cst = "00";
                        String ean = !StringUtil.isNullOrEmpty(NFeItem.getCodigoEAN()) ? NFeItem.getCodigoEAN()
                                : NFeItem.getCodigEANTrib();
                        spd0200.setCodBarra(ean);
                        spd0200.setCodItem(NFeItem.getCodigoProduto());
                        spd0200.setDescrItem(NFeItem.getDescricaoProduto());
                        BigDecimal base = new BigDecimal(100);
                        if (!this.identificarCFOPSaida(NFeItem.getCfop())) {
                            return;// verifica o CFOP de saida da XML antes de prosseguir
                        }
                        Relatorio rel = new Relatorio();
                        rel.setTipoDoc("NFe");
                        rel.setChaveNfe("'" + nfe.getChaveNFe());
                        rel.setNumeroNota(nfe.getNumeroNotaFiscal());
                        rel.setData(nfe.getDataHoraEmissao());
                        rel.setClienteFornecedor(nfe.getDestinatario().getRazaoSocial());
                        rel.setCnpj("'" + nfe.getDestinatario().getCNPJ());
                        rel.setEan("'" + spd0200.getCodBarra() + "'");
                        rel.setCodigoInternoProduto(NFeItem.getCodigoProduto());
                        rel.setDescricaoProduto(NFeItem.getDescricaoProduto());
                        try {
                            rel.setValorOperacao(NFeItem.getValorProduto());
                        } catch (Exception ex) {
                            rel.setValorOperacao(BigDecimal.ZERO);
                        }
                        // sped
                        rel.setCfopSped(naoSeAplica);
                        rel.setCstIcmsSped(naoSeAplica);
                        rel.setAliqIcmsSped(null);
                        rel.setBaseDeCalculoSped(null);
                        rel.setRedBcSped(null);
                        rel.setCargaTributariaSped(null);

                        rel.setCfopXml(NFeItem.getCfop());
                        rel.setAliqIcmsXml(BigDecimal.ZERO);
                        rel = identificarTipoDeIcmsXML(rel, NFeItem.getICMS());
                        rel.setCargaTributariaXml(rel.getValorIcmsXml());

                        rel.setAliqIcmsMix(BigDecimal.ZERO);
                        rel.setRedBcMix(BigDecimal.ZERO);
                        rel.setBaseDeCalculoMix(BigDecimal.ZERO);
                        rel.setCargaTributariaMix(BigDecimal.ZERO);
                        rel.setDebitoIndevido(BigDecimal.ZERO);
                        rel.setDebitoNaoDeclarado(BigDecimal.ZERO);

                        // SPED0200 nSped0200 = new SPED0200();
                        // nSped0200.setCodItem(NFeItem.getCodigoProduto());
                        // verificar se vai utilizar SNC ou SVC
                         log.info("relatorio de saida 55: acessando o banco de dados e buscando o produto  [" + NFeItem.getCodigoProduto() + "], CHAVE :" + nfe.getChaveNFe());
                        try {
                            rel = this.identificarTributosMix(rel, NFeItem, ETipoRelatorio.Saida,"55");
                        } catch (Exception ex) {
                            this.log.error("erro ao buscar tributos da Mix no metodo identificarTributosMix() "
                                    + ex.getMessage(), ex);
                        }

                        if (StringUtil.isNullOrEmpty(rel.getCstIcmsMix())
                                || rel.getCstIcmsMix().equals("campo vazio")) {
                             this.sbRelRegistroNaoEncontrado.append(String.format("'%s;%s;'%s;\r\n",NFeItem.getCodigoProduto(), NFeItem.getDescricaoProduto(),nfe.getChaveNFe()));
                            return;// se o CST da mix for null nao gera relatorio
                        }
                        try {
                            String st = Integer.toString(NFeItem.getICMS().getCST());
                            rel.setCstIcmsXml(st);
                        } catch (Exception ex) {
                            rel.setCstIcmsXml(null);
                            this.log.error(ex.getMessage(), ex);
                        }
                        rel = mtUtil.processarRegrasNFe(rel);

                        // calculando debitos indevidos e nao aproveitado
                        BigDecimal result = BigDecimal.ZERO;

                        if (rel.getCargaTributariaXml().compareTo(rel.getCargaTributariaMix()) > 0) {
                            result = rel.getCargaTributariaXml().subtract(rel.getCargaTributariaMix());
                            rel.setDebitoIndevido(mtUtil.arredondarValorDecimal(result));
                            this.somarValorDebitoIndevido(result);
                            rel.setDebitoNaoDeclarado(BigDecimal.ZERO);
                        } else if (rel.getCargaTributariaXml().compareTo(rel.getCargaTributariaMix()) < 0) {
                            result = rel.getCargaTributariaMix().subtract(rel.getCargaTributariaXml());
                            rel.setDebitoNaoDeclarado(mtUtil.arredondarValorDecimal(result));
                            this.somarValorDebitoNaoDeclarado(result);
                            rel.setDebitoIndevido(BigDecimal.ZERO);
                        } else {
                            return;// se a carga tributaria do XML for igual a carga Tributaria da MIX, nao gera relatorio
                        }
                        rel.setCstIcmsMix(corrigirCSTZero(rel.getCstIcmsMix()));
                        rel.setCstIcmsSped(corrigirCSTZero(rel.getCstIcmsSped()));
                        rel.setCstIcmsXml(corrigirCSTZero(rel.getCstIcmsXml()));
                        listaRelatorio.add(rel);//adicionando item na lista 

                    });
                } catch (Exception ex) {
                    this.log.error("erro ao gerar relatorio quando percorreu a XML (55) saida " + nfe.getChaveNFe() + ", " + ex.getMessage(), ex);
                }
                String caminho =  this.caminhoDirXMLsNFes + File.separator + "registrosNaoEncontradosNFe.csv";
                try{
                    mtUtil.criarArquivoCSV(caminho, this.sbRelRegistroNaoEncontrado);
                }catch(Exception ex){
                    this.log.error("erro ao gerar  csv de registros nao encontrados 65 "+ ex.getMessage(), ex);
                }
                // </editor-fold>

                // <editor-fold desc="Listando C100 do SPED NFCe" defaultstate="collapsed">
            } else if (spd100.getModeloDocumento().equals("65")) {
                
                 log.info("relatorio de saida 65: pércorrendo o registro C100 ["+ spd100.getChaveNFe()+ "]");
                if (!contemCFOPSaida(spd100.getSPEDC190())) {
                    return;
                }
                try {
                    this.nfe = this.buscarXmlNFCe(spd100.getChaveNFe());
                } catch (Exception ex) {
                    this.log.error(
                            "nao encontro o arquivo referente a chave (65) saida " + spd100.getChaveNFe() + ", " + ex.getMessage(),
                            ex);
                }
                if (this.nfe == null) {
                    return;
                }
                if (this.nfe.getTipoNotaFiscal() == ETipoNotaFiscal.Entrada) {
                    return;
                }

                try {
                    nfe.getItens().forEach(NFCeItem -> {
                        if(NFCeItem == null)return;
                        log.info("relatorio de saida 65: percorrendo o item da XML [" + NFCeItem.getDescricaoProduto() + "], CHAVE :" + nfe.getChaveNFe());
                        SPED0200 spd0200 = new SPED0200();
                        String cst = "00";
                        String ean = !StringUtil.isNullOrEmpty(NFCeItem.getCodigoEAN()) ? NFCeItem.getCodigoEAN()
                                : NFCeItem.getCodigEANTrib();
                        spd0200.setCodBarra(ean);
                        spd0200.setCodItem(NFCeItem.getCodigoProduto());
                        spd0200.setDescrItem(NFCeItem.getDescricaoProduto());
                        BigDecimal base = new BigDecimal(100);
                        if (!this.identificarCFOPSaida(NFCeItem.getCfop())) {
                            return;// verifica o CFOP de saida da XML antes de prosseguir
                        }
                        Relatorio rel = new Relatorio();
                        rel.setTipoDoc("NFCe");
                        rel.setChaveNfe("'" + nfe.getChaveNFe());
                        rel.setNumeroNota(nfe.getNumeroNotaFiscal());
                        rel.setData(nfe.getDataHoraEmissao());
                        rel.setClienteFornecedor(nfe.getDestinatario().getRazaoSocial());
                        rel.setCnpj("'" + nfe.getDestinatario().getCNPJ());
                        rel.setEan("'" + spd0200.getCodBarra() + "'");
                        rel.setCodigoInternoProduto(NFCeItem.getCodigoProduto());
                        rel.setDescricaoProduto(NFCeItem.getDescricaoProduto());
                        try {
                            rel.setValorOperacao(NFCeItem.getValorProduto());
                        } catch (Exception ex) {
                            rel.setValorOperacao(BigDecimal.ZERO);
                        }
                        // sped
                        rel.setCfopSped(naoSeAplica);
                        rel.setCstIcmsSped(naoSeAplica);
                        rel.setAliqIcmsSped(null);
                        rel.setBaseDeCalculoSped(null);
                        rel.setRedBcSped(null);
                        rel.setCargaTributariaSped(null);

                        rel.setCfopXml(NFCeItem.getCfop());
                        rel.setAliqIcmsXml(BigDecimal.ZERO);
                        rel = identificarTipoDeIcmsXML(rel, NFCeItem.getICMS());
                        rel.setCargaTributariaXml(rel.getValorIcmsXml());

                        rel.setAliqIcmsMix(BigDecimal.ZERO);
                        rel.setRedBcMix(BigDecimal.ZERO);
                        rel.setBaseDeCalculoMix(BigDecimal.ZERO);
                        rel.setCargaTributariaMix(BigDecimal.ZERO);
                        rel.setDebitoIndevido(BigDecimal.ZERO);
                        rel.setDebitoNaoDeclarado(BigDecimal.ZERO);
                        // obtendo registro da MIX                        
                        SPED0200 nSped0200 = new SPED0200();
                        nSped0200.setCodItem(NFCeItem.getCodigoProduto());
                        log.info("relatorio de saida 65: acessando o banco de dados e buscando o produto  [" + NFCeItem.getCodigoProduto() + "], CHAVE :" + nfe.getChaveNFe());
                        try {
                            tributacaoMix = this.obterTributacaoMix(nSped0200, ETipoRelatorio.Saida);
                        } catch (Exception ex) {
                            this.log.error("erro ao buscar tributos da Mix no metodo obterTributacaoMix() "
                                    + ex.getMessage(), ex);
                            return;
                        }
                        
                        if(tributacaoMix == null ||
                            tributacaoMix == null ||
                            tributacaoMix.getIcmsSaida() ==  null ||
                            tributacaoMix.getIcmsSaida().getSncCst() == null){
                            this.sbRelRegistroNaoEncontrado.append(String.format("'%s;%s;'%s;\r\n", NFCeItem.getCodigoProduto(), NFCeItem.getDescricaoProduto(),nfe.getChaveNFe()));
                            log.info("relatorio de saida 65: item do XML que nao tem na mix  codigo:[" + NFCeItem.getCodigoProduto() + "], descrição:["+ NFCeItem.getDescricaoProduto() +"],  CHAVE :" + nfe.getChaveNFe());
                            return;
                        }
                        try {
                            rel.setCstIcmsMix(tributacaoMix.getIcmsSaida().getSncCst().trim());
                        } catch (Exception ex) {
                            this.log.error("erro ao setar CstIcmsMix " + ex.getMessage(), ex);
                            return;
                        }

                        if (StringUtil.isNullOrEmpty(rel.getCstIcmsMix())
                                || rel.getCstIcmsMix().equals("campo vazio")) {
                            return;
                        }
                        if(tributacaoMix.getIcmsSaida() != null &&
                            tributacaoMix.getIcmsSaida().getSncAlq() != null){
                                rel.setAliqIcmsMix(mtUtil.arredondarAliquotaDecimal(
                                    new BigDecimal(tributacaoMix.getIcmsSaida().getSncAlq().trim())));
                        }    
//                      try {
//                      } catch (Exception ex) {
//                        this.log.error("erro ao setar AliqIcmsMix " + ex.getMessage(), ex);
//                      }
                        if(tributacaoMix.getIcmsSaida() != null &&
                               tributacaoMix.getIcmsSaida().getSncRbc() != null ){
                                rel.setRedBcMix(mtUtil.arredondarValorDecimal(
                                    new BigDecimal(tributacaoMix.getIcmsSaida().getSncRbc().trim())));

                        }
//                        try {
//                            rel.setRedBcMix(mtUtil.arredondarValorDecimal(
//                                    new BigDecimal(tributacaoMix.getProduto().getIcmsSaida().getSncRbc().trim())));
//
//                        } catch (Exception ex) {
//                            this.log.error("erro ao setar RedBcMix " + ex.getMessage(), ex);
//                        }
                        try {
                            String st = Integer.toString(NFCeItem.getICMS().getCST());
                            rel.setCstIcmsXml(st);
                        } catch (Exception ex) {
                            this.log.error("erro ao converte ICMS do item NFE para string " + ex.getMessage(), ex);
                        }

                        rel = mtUtil.processarRegrasNFe(rel);
                        // calculando debitos indevidos e nao aproveitado
                        BigDecimal result = BigDecimal.ZERO;
                        if (rel.getCargaTributariaXml().compareTo(rel.getCargaTributariaMix()) > 0) {
                            result = rel.getCargaTributariaXml().subtract(rel.getCargaTributariaMix());
                            rel.setDebitoIndevido(mtUtil.arredondarValorDecimal(result));
                            this.somarValorDebitoIndevido(result);
                            rel.setDebitoNaoDeclarado(BigDecimal.ZERO);
                        } else if (rel.getCargaTributariaXml().compareTo(rel.getCargaTributariaMix()) < 0) {
                            result = rel.getCargaTributariaMix().subtract(rel.getCargaTributariaXml());
                            rel.setDebitoNaoDeclarado(mtUtil.arredondarValorDecimal(result));
                            this.somarValorDebitoNaoDeclarado(result);
                            rel.setDebitoIndevido(BigDecimal.ZERO);
                        } else {
                            return;// se a carga tributaria do XML for igual a carga Tributaria da MIX, nao gera relatorio
                        } // nao gera relatorio
                        rel.setCstIcmsMix(corrigirCSTZero(rel.getCstIcmsMix()));
                        rel.setCstIcmsSped(corrigirCSTZero(rel.getCstIcmsSped()));
                        rel.setCstIcmsXml(corrigirCSTZero(rel.getCstIcmsXml()));
                        listaRelatorio.add(rel);
                    });
                } catch (Exception ex) {
                    this.log.error("erro ao gerar relatorio quando percorreu a XML (65) saida " + nfe.getChaveNFe() + ", " + ex.getMessage(), ex);
                }
                String caminho =  this.caminhoDirXMLsNFCes + File.separator + "registrosNaoEncontradosNFCe.csv";
                try{
                    mtUtil.criarArquivoCSV(caminho, this.sbRelRegistroNaoEncontrado);
                }catch(Exception ex){
                    this.log.error("erro ao gerar  csv de registros nao encontrados 65 "+ ex.getMessage(), ex);
                }
            }
            if (nfe != null) {
                spd100.setXmlReferente(nfe);
            }
        });
        // <editor-fold desc="Listando C460 do SPED ECF" defaultstate="collapsed">
        // assume que todos os item 460 Cupom ECF é de saida nao tem nota
        listC100.clear();
        this.contadorProgresso = 0;
        this.setTotalItem(listC460.size());
        this.setDescricaoProgresso("Gerando relatório saída C460 (ECF)...");
        listC460.forEach(spd460 -> {
            if(spd460 == null) return;
            log.info("relatorio de saida C460 (ECF): percorrendo o SPED [" + spd460.getNumDoc()+ "]");
            this.contadorProgresso++;
            try {
                spd460.getListaSPEDC470().forEach(spd470 -> {
                    if(spd470 == null)return;
                    if (!this.identificarCFOPSaida(spd470.getCFOP())) {
                        return;
                    }
                    log.info("relatorio de saida (ECF): percorrendo o SPED460 [" + spd460.getNumDoc()+ "], no regsitro 470 Codigo do Item ["+ spd470.getCodItem() + "]");
                    BigDecimal base = new BigDecimal(100);
                    Relatorio rel = new Relatorio();
                    rel.setTipoDoc("ECF");
                    rel.setChaveNfe(naoSeAplica);
                    rel.setNumeroNota(spd460.getNumDoc());
                    try {
                        rel.setData(dt.parse(spd460.getDtDoc()));
                    } catch (Exception ex) {
                        this.log.error("erro ao setar data " + ex.getMessage(), ex);
                    }
                    if (spd460.getNomAdq() != null && !spd460.getNomAdq().equals("")) {
                        rel.setClienteFornecedor(spd460.getNomAdq());
                    } else {
                        rel.setClienteFornecedor(naoSeAplica);
                    }
                    if (!spd460.getCpfCnpj().equals("")) {
                        rel.setCnpj("'" + spd460.getCpfCnpj());
                    } else {
                        rel.setCnpj(naoSeAplica);
                    }
                    rel.setEan("'" + spd470.getSPED0200().getCodBarra() + "'");
                    rel.setCodigoInternoProduto(spd470.getSPED0200().getCodItem());
                    rel.setDescricaoProduto(spd470.getSPED0200().getDescrItem());
                    try {
                        rel.setValorOperacao(spd470.getValor());
                    } catch (Exception ex) {
                        rel.setValorOperacao(BigDecimal.ZERO);
                        this.log.error("erro ao setar ValorOperacao " + ex.getMessage(), ex);
                    }

                    // valores SPED
                    rel.setCfopSped(!StringUtil.isNullOrEmpty(spd470.getCFOP()) ? spd470.getCFOP() : "000");
                    try {
                        rel.setCstIcmsSped(spd470.getCSTICMS());
                    } catch (Exception ex) {
                        rel.setCstIcmsSped("00");
                        this.log.error("erro ao setar CstIcmsSped " + ex.getMessage(), ex);
                    }

                    try {
                        rel.setAliqIcmsSped(spd470.getAliqICMS());
                    } catch (Exception ex) {
                        rel.setAliqIcmsSped(BigDecimal.ZERO);
                        this.log.error("erro ao setar AliqIcmsSped " + ex.getMessage(), ex);
                    }
                    try {
                        rel.setBaseDeCalculoSped(rel.getValorOperacao());
                    } catch (Exception ex) {
                        rel.setBaseDeCalculoSped(BigDecimal.ZERO);
                        this.log.error("erro ao setar BaseDeCalculoSped " + ex.getMessage(), ex);
                    }

                    try {
                        BigDecimal valorIcms = spd470.getAliqICMS().multiply(rel.getValorOperacao()).divide(base);// encontrando a carga tributaria do sped
                        rel.setValorIcmsSped(mtUtil.arredondarValorDecimal(valorIcms));
                        rel.setCargaTributariaSped(mtUtil.arredondarValorDecimal(valorIcms));

                    } catch (Exception ex) {
                        rel.setCargaTributariaSped(BigDecimal.ZERO);
                        this.log.error("erro ao setar CargaTributariaSped " + ex.getMessage(), ex);
                    }
                    try {
                        rel.setRedBcSped(BigDecimal.ZERO);
                    } catch (Exception ex) {
                        this.log.error("erro ao setar RedBcSped " + ex.getMessage(), ex);
                    }
                    try {
                        rel.setValorIcmsSped(spd470.getValorICMS());
                    } catch (Exception ex) {
                        rel.setValorIcmsSped(BigDecimal.ZERO);
                        this.log.error("erro ao setar ValorIcmsSped " + ex.getMessage(), ex);
                    }
                    // rel.setCargaTributariaSped(MetodosUtil.calcularValorCargaTributaria(rel.getAliqIcmsSped(),rel.getRedBcSped()));

                    // valores XML
                    rel.setCfopXml(naoSeAplica);
                    rel.setCstIcmsXml(naoSeAplica);
                    rel.setValorIcmsXml(null);
                    rel.setAliqIcmsXml(null);
                    rel.setRedBcXml(null);
                    rel.setBaseDeCalculoXml(null);
                    rel.setCargaTributariaXml(null);
                    // valores MIX                    
                    log.info("relatorio de saida (ECF): acessando o banco de dados e buscando o produto  [" + spd470.getSPED0200().getCodItem()+ "]");
                    try {
                        tributacaoMix = this.obterTributacaoMix(spd470.getSPED0200(), ETipoRelatorio.Saida);
                    } catch (Exception ex) {
                        this.log.error("erro ao obter Tributação da Mix no metodo obterTributacaoMix() " + ex.getMessage(), ex);
                    }
                    
                    if(tributacaoMix == null ||
                        tributacaoMix == null || 
                        tributacaoMix.getIcmsSaida() == null ||
                        tributacaoMix.getIcmsSaida().getSncCst() == null){
                        this.sbRelRegistroNaoEncontrado.append(String.format("'%s;%s;'%s;\r\n",spd470.getSPED0200().getCodItem(),spd470.getSPED0200().getDescrItem(),spd460.getNumDoc()));
                        return; 
                    }
                   
                    try {
                        rel.setCstIcmsMix(tributacaoMix.getIcmsSaida().getSncCst().trim());
                    } catch (Exception ex) {
                        this.log.error("erro so setar CstIcmsMix " + ex.getMessage(), ex);
                    }
                    try {
                        rel.setAliqIcmsMix(
                                new BigDecimal(tributacaoMix.getIcmsSaida().getSncAlq().trim()));
                    } catch (Exception ex) {
                        rel.setAliqIcmsMix(BigDecimal.ZERO);
                        this.log.error("erro so setar AliqIcmsMix " + ex.getMessage(), ex);
                    }
                    try {
                        rel.setRedBcMix(new BigDecimal(tributacaoMix.getIcmsSaida().getSncRbc().trim()));
                    } catch (Exception ex) {
                        rel.setRedBcMix(BigDecimal.ZERO);
                        this.log.error("erro so setar RedBcMix " + ex.getMessage(), ex);
                    }

                    rel.setBaseDeCalculoMix(BigDecimal.ZERO);
                    if (!StringUtil.isNullOrEmpty(rel.getCstIcmsMix())) {
                        if (rel.getCstIcmsMix().trim().equals("60")
                                || rel.getCstIcmsMix().trim().equals("40")
                                || rel.getCstIcmsMix().trim().equals("41")
                                || rel.getCstIcmsMix().trim().equals("51")
                                || rel.getCstIcmsMix().trim().equals("70")) {
                            rel.setCargaTributariaMix(BigDecimal.ZERO);
                            rel.setBaseDeCalculoMix(BigDecimal.ZERO);
                            rel.setValorIcmsSped(BigDecimal.ZERO);
                            rel.setCargaTributariaSped(BigDecimal.ZERO);
                        }

                        if (rel.getCstIcmsMix().trim().equals("0") || rel.getCstIcmsMix().trim().equals("00")) {
                            BigDecimal fator = base.subtract(rel.getRedBcMix()).divide(base);
                            BigDecimal baseCalculo = rel.getValorOperacao().multiply(fator);
                            rel.setBaseDeCalculoMix(mtUtil.arredondarValorDecimal(baseCalculo));
                            BigDecimal cargaTrib = rel.getValorOperacao().multiply(rel.getAliqIcmsMix()).divide(base);
                            rel.setCargaTributariaMix(mtUtil.arredondarValorDecimal(cargaTrib));
                        }

                        if (rel.getCstIcmsMix().trim().equals("20")) {
                            try {
                                BigDecimal fator = base.subtract(rel.getRedBcMix()).divide(base);
                                BigDecimal baseCalculo = rel.getValorOperacao().multiply(fator);
                                rel.setBaseDeCalculoMix(mtUtil.arredondarValorDecimal(baseCalculo));
                                BigDecimal cargaTrib = baseCalculo.multiply(rel.getAliqIcmsMix()).divide(base);
                                rel.setCargaTributariaMix(mtUtil.arredondarValorDecimal(cargaTrib));
                            } catch (Exception ex) {
                                this.log.error("erro ao calcular o CST 20 " + ex.getMessage(), ex);
                            }
                        }
                        // calculando debitos indevidos e nao aproveitado
                        BigDecimal result = BigDecimal.ZERO;

                        if (rel.getCargaTributariaSped().compareTo(rel.getCargaTributariaMix()) > 0) {
                            result = rel.getCargaTributariaSped().subtract(rel.getCargaTributariaMix());
                            rel.setDebitoIndevido(mtUtil.arredondarValorDecimal(result));
                            rel.setDebitoNaoDeclarado(BigDecimal.ZERO);
                        } else if (rel.getCargaTributariaMix().compareTo(rel.getCargaTributariaSped()) > 0) {
                            result = rel.getCargaTributariaMix().subtract(rel.getCargaTributariaSped());
                            rel.setDebitoNaoDeclarado(mtUtil.arredondarValorDecimal(result));
                            rel.setDebitoIndevido(BigDecimal.ZERO);
                        } else {
                            return;// se a carga tributaria do APED for igual a carga Tributaria da MIX, nao gera relatorio
                        }
                    } else {
                        rel.setCstIcmsMix("0");
                        rel.setDebitoNaoDeclarado(BigDecimal.ZERO);
                        rel.setDebitoIndevido(BigDecimal.ZERO);
                    }
                    rel.setCstIcmsMix(corrigirCSTZero(rel.getCstIcmsMix()));
                    rel.setCstIcmsSped(corrigirCSTZero(rel.getCstIcmsSped()));
                    rel.setCstIcmsXml(corrigirCSTZero(rel.getCstIcmsXml()));
                    listaRelatorio.add(rel);
                });
            } catch (Exception ex) {
                this.log.error("erro ao gerar relatorio quando percorreu a SPED (ECF)460 saida " + spd460.getNumDoc() + ", " + ex.getMessage(), ex);
            }
        });
        listC460.clear();
        String caminho = this.caminhoArquivoSped;
        String arquivo = this.caminhoArquivoSped.substring(caminho.lastIndexOf("\\")+1);
        caminho = caminho.replace(arquivo,"");
        caminho = caminho + File.separator + "registrosNaoEncontradosMix.csv";
        try{
            mtUtil.criarArquivoCSV(caminho, this.sbRelRegistroNaoEncontrado);
        }catch(Exception ex){
            this.log.error("erro ao gerar  csv de registros nao encontrados 65 "+ ex.getMessage(), ex);
        }
        // </editor-fold>

        // <editor-fold desc="Listando C800 do SPED CF-e" defaultstate="collapsed">
        // assume que todos os item C800 SAT é uma saida
        this.contadorProgresso = 0;

        this.setTotalItem(listC800.size());
        this.setDescricaoProgresso("Gerando relatorio saída C800 (SAT 59)...");
       
        listC800.forEach(spd800 -> {
            if(spd800 == null)return;
            this.contadorProgresso++;
             log.info("relatorio de saida SAT (59):  pércorrendo o registro C800 ["+ spd800.getChvCfe()+ "]");
            try {
                if (!mtUtil.identificarTipoDocPelaChave(spd800.getChvCfe(), "59")) {
                    return;
                }
                try {
                    cfe = this.buscarXmlCFe(spd800.getChvCfe());
                } catch (Exception ex) {
                    this.log.error(
                            "nao encontro o arquivo referente a chave (59) saida " + cfe.getChaveNFe() + ", " + ex.getMessage(),
                            ex);
                }
                if (cfe == null) {
                    return;
                }
                if (cfe.getItensCfe().size() == 0) {
                    return;
                }

                cfe.getItensCfe().forEach(CFeItem -> {
                    if(CFeItem == null)return;
                    log.info("relatorio de saida SAT 59: percorrendo o item da XML " + CFeItem.getDescricaoProduto() + ", CHAVE :" + cfe.getChaveNFe());
                    final SPEDC850 spdc850Ok = new SPEDC850();
                    SPED0200 spd0200 = new SPED0200();

                    String ean = !StringUtil.isNullOrEmpty(CFeItem.getCodigoEAN()) ? CFeItem.getCodigoEAN()
                            : CFeItem.getCodigEANTrib();
                    spd0200.setCodBarra(ean);
                    spd0200.setDescrItem(CFeItem.getDescricaoProduto());
                    BigDecimal base = new BigDecimal(100);
                    if (!this.identificarCFOPSaida(CFeItem.getCfop())) {
                        return;// verifica o CFOP de saida da XML antes de prosseguir
                    }
                    Relatorio rel = new Relatorio();
                    rel.setTipoDoc("CFe");
                    rel.setChaveNfe("'" + cfe.getChaveNFe());
                    rel.setNumeroNota(cfe.getNumeroNotaFiscal());
                    rel.setData(cfe.getDataHoraEmissao());
                    rel.setClienteFornecedor(naoSeAplica);
                    rel.setCnpj("'" + spd800.getCnpjCpf());
                    rel.setEan("'" + spd0200.getCodBarra() + "'");
                    rel.setCodigoInternoProduto(CFeItem.getCodigoProduto());
                    rel.setDescricaoProduto(CFeItem.getDescricaoProduto());
                    try {
                        rel.setValorOperacao(CFeItem.getValorItem());
                    } catch (Exception ex) {
                        rel.setValorOperacao(BigDecimal.ZERO);
                        this.log.error("erro so setar ValorOperacao " + ex.getMessage(), ex);
                    }

                    rel.setCfopSped("000");
                    rel.setCstIcmsSped("0");
                    rel.setAliqIcmsSped(BigDecimal.ZERO);
                    rel.setBaseDeCalculoSped(BigDecimal.ZERO);
                    rel.setRedBcSped(BigDecimal.ZERO);
                    rel.setCargaTributariaSped(BigDecimal.ZERO);

                    rel.setCfopXml(CFeItem.getCfop());
                    rel.setAliqIcmsXml(BigDecimal.ZERO);
                    rel = identificarTipoDeIcmsXML(rel, CFeItem.getICMS());
                    rel.setCargaTributariaXml(rel.getValorIcmsXml());

                    rel.setAliqIcmsMix(BigDecimal.ZERO);
                    rel.setRedBcMix(BigDecimal.ZERO);
                    rel.setBaseDeCalculoMix(BigDecimal.ZERO);
                    rel.setCargaTributariaMix(BigDecimal.ZERO);

                    rel.setDebitoIndevido(BigDecimal.ZERO);
                    rel.setDebitoNaoDeclarado(BigDecimal.ZERO);

                    // obtendo registro da MIX
                    
                    log.info("relatorio de saida (SAT): acessando o banco de dados e buscando o produto  [" + CFeItem.getCodigoProduto()+ "]");
                    try {
                        spd0200.setCodItem(CFeItem.getCodigoProduto());
                        tributacaoMix = this.obterTributacaoMix(spd0200, ETipoRelatorio.Saida);
                    } catch (Exception ex) {
                        this.log.error("erro ao obter a tributacao mix no metodo  obterTributacaoMix() " + ex.getMessage(), ex);
                    }
                     if(tributacaoMix == null ||
                        tributacaoMix == null || 
                        tributacaoMix.getIcmsSaida() == null ||
                        tributacaoMix.getIcmsSaida().getSncCst() == null){
                         
                         this.sbRelRegistroNaoEncontrado.append(String.format("'%s;%s;'%s;\r\n", CFeItem.getCodigoProduto(), CFeItem.getDescricaoProduto(),cfe.getChaveNFe()));
                            return;
                    }
                    
                    if (StringUtil.isNullOrEmpty(tributacaoMix.getIcmsSaida().getSncCst().trim())
                            || tributacaoMix.getIcmsSaida().getSncCst().trim().equals("campo vazio")) {
                        return;
                    }
                    try {
                        rel.setCstIcmsMix(tributacaoMix.getIcmsSaida().getSncCst().trim());
                    } catch (Exception ex) {
                        this.log.error("erro ao setar CstIcmsMix " + ex.getMessage(), ex);
                    }

                    try {
                        rel.setAliqIcmsMix(
                                new BigDecimal(tributacaoMix.getIcmsSaida().getSncAlq().trim()));
                    } catch (Exception ex) {
                        this.log.error("erro ao setar AliqIcmsMix " + ex.getMessage(), ex);
                    }

                    try {
                        rel.setRedBcMix(new BigDecimal(tributacaoMix.getIcmsSaida().getSncRbc().trim()));
                    } catch (Exception ex) {
                        this.log.error("erro ao setar RedBcMix" + ex.getMessage(), ex);
                    }
                    // obtendo registro do XML
                    try {
                        String st = Integer.toString(CFeItem.getICMS().getCST());
                        rel.setCstIcmsXml(st);
                    } catch (Exception ex) {
                        this.log.error("erro ao converter o ICMS do item da nota em string " + ex.getMessage(), ex);
                    }

                    ICMS icms = CFeItem.getICMS();
                    rel = this.identificarTipoDeIcmsXML(rel, icms);
                    rel.setCargaTributariaXml(rel.getValorIcmsXml());

                    if (rel.getCstIcmsMix().trim().equals("60")
                            || rel.getCstIcmsMix().trim().equals("40")
                            || rel.getCstIcmsMix().trim().equals("41")
                            || rel.getCstIcmsMix().trim().equals("51")
                            || rel.getCstIcmsMix().trim().equals("70")) {
                        rel.setBaseDeCalculoMix(BigDecimal.ZERO);
                    }

                    if (rel.getCstIcmsMix().trim().equals("0") || rel.getCstIcmsMix().trim().equals("00")) {

                        BigDecimal fator = base.subtract(rel.getRedBcMix()).divide(base);
                        BigDecimal baseCalculo = rel.getValorOperacao().multiply(fator);
                        rel.setBaseDeCalculoMix(mtUtil.arredondarValorDecimal(baseCalculo));
                        BigDecimal cargaTrib = rel.getValorOperacao().multiply(rel.getAliqIcmsMix()).divide(base);
                        rel.setCargaTributariaMix(mtUtil.arredondarValorDecimal(cargaTrib));
                    }

                    if (rel.getCstIcmsMix().trim().equals("20")) {// calculando carga tributaria da MIX CST 20

                        try {
                            BigDecimal fator = base.subtract(rel.getRedBcMix()).divide(base);
                            BigDecimal baseCalculo = rel.getValorOperacao().multiply(fator);
                            rel.setBaseDeCalculoMix(mtUtil.arredondarValorDecimal(baseCalculo));
                            BigDecimal cargaTrib = baseCalculo.multiply(rel.getAliqIcmsMix()).divide(base);
                            rel.setCargaTributariaMix(mtUtil.arredondarValorDecimal(cargaTrib));
                        } catch (Exception ex) {
                            this.log.error("erro ao calcular o CST 20 " + ex.getMessage(), ex);
                        }
                    }

                    // calculando debitos indevidos e nao aproveitado
                    BigDecimal result = BigDecimal.ZERO;
                    if (rel.getCargaTributariaXml().compareTo(rel.getCargaTributariaMix()) > 0) {
                        result = rel.getCargaTributariaXml().subtract(rel.getCargaTributariaMix());
                        rel.setDebitoIndevido(mtUtil.arredondarValorDecimal(result));
                        this.somarValorDebitoIndevido(result);
                        rel.setDebitoNaoDeclarado(BigDecimal.ZERO);
                    } else if (rel.getCargaTributariaXml().compareTo(rel.getCargaTributariaMix()) < 0) {
                        result = rel.getCargaTributariaMix().subtract(rel.getCargaTributariaXml());
                        rel.setDebitoNaoDeclarado(mtUtil.arredondarValorDecimal(result));
                        this.somarValorDebitoNaoDeclarado(result);
                        rel.setDebitoIndevido(BigDecimal.ZERO);
                    } else {
                        return;// se a carga tributaria do APED for igual a carga Tributaria da MIX, nao gera relatorio
                    }
                    rel.setCstIcmsMix(corrigirCSTZero(rel.getCstIcmsMix()));
                    rel.setCstIcmsSped(corrigirCSTZero(rel.getCstIcmsSped()));
                    rel.setCstIcmsXml(corrigirCSTZero(rel.getCstIcmsXml()));
                    listaRelatorio.add(rel);
                });
                spd800.setXmlReferente(cfe);
            } catch (Exception ex) {
                this.log.error("erro ao gerar relatorio quando percorreu a CFe SAT (59)c800 saida " + cfe.getChaveNFe() + ", " + ex.getMessage(), ex);
            }
            String path =  this.caminhoDirXMLsCFes + File.separator + "registrosNaoEncontradosCFe.csv";
            try{
                mtUtil.criarArquivoCSV(path, this.sbRelRegistroNaoEncontrado);
            }catch(Exception ex){
                this.log.error("erro ao gerar  csv de registros nao encontrados 65 "+ ex.getMessage(), ex);
            }
        });
        listC800.clear();
        // </editor-fold>
        return listaRelatorio;
    }

    public SPEDC170 localizarProduto(List<SPED0200> sped0200, String ean) {
        SPEDC170 sped = null;
        try {
            Optional<SPED0200> result = sped0200.stream().filter(spd0200 -> spd0200.getCodBarra().equals(ean))
                    .findFirst();
            SPED0200 prod = result.isPresent() ? result.get() : null;
            if (prod != null) {
                sped = new SPEDC170();
                sped.setDescricaoComplementar(prod.getDescrItem());
            }
        } catch (Exception ex) {
            this.log.error(ex.getMessage());
        }
        return sped;
    }

    public List<Fornecedor> retornarRelatorioCreditoIndevido() throws DadosSpedNaoCarregadosException {
        HashMap<SPEDC150, HashMap<SPED0200, List<SPEDC100>>> dadosSped = reorganizarDadosSped();
        // cria um HashMap onde
        // o chave é o
        // fornecedor, o valor é
        // um HashMap com a
        // chave sendo produto e
        // o valor sendo uma
        // lista de Nota fiscal
        final List<Fornecedor> rel = new ArrayList<>();
        // obtendo a chave do cliente
        sped000 = this.leitorSped.getSped0000();
        ClienteService clienteSvc = new ClienteService();
        Cliente cliente = new Cliente();
        cliente.setCnpj(sped000.getCNPJ());
        try {
            cliente = clienteSvc.selecionarUm(cliente);
        } catch (Exception ex) {
            this.log.error(ex.getMessage());
        }

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente nao tem token cadastrado");
        }

        this.setToken(cliente.getToken());
        dadosSped.forEach((fornec, prods) -> {// percorre o fornecedor
            if (fornec == null) {
                return;
            }

            try {
                final List<Produto> produtos = new ArrayList<>();
                prods.forEach((prod, nf) -> {// percorre os produtos do fornecedor
                    try {
                        final List<Tributacao> tributos = new ArrayList<>();
                        nf.stream().forEach(spd100 -> {// percorre as notas do produto
                            try {
                                // if(spd100.getTipoNotaFiscal() != ETipoNotaFiscal.Entrada)
                                // return;
                                // verifica se a nota é de entrada
                                Optional<SPEDC170> res = spd100.getListaSPEDC170().stream()
                                        .filter(s -> s.getCodItem().equals(prod.getCodItem())).findFirst();// compara o
                                // item da
                                // XML com
                                // SPED pelo
                                // codigo se
                                // for igual
                                // retora o
                                // mesmo
                                SPEDC170 item = res.isPresent() ? res.get() : null;
                                /*
								 * o trecho abaixo verifica se o ICMS do item do SPED em questão faz parte de
								 * uma Substituição Tributária.
                                 */

                                if (item == null || (!item.getCSTICMS().endsWith("10")
                                        && !item.getCSTICMS().endsWith("30") && !item.getCSTICMS().endsWith("60")
                                        && !item.getCSTICMS().endsWith("70"))) {
                                    return;
                                }

                                ClassificacaoTributaria tributacaoMix = null;
                                // try {
                                String codProd = StringUtil.isNullOrEmpty(item.getSPED0200().getCodBarra())
                                        ? item.getSPED0200().getCodBarra()
                                        : item.getSPED0200().getDescrItem().replace(" ", "+");
                                /*
								 * o trecho abaixo verifica junto ao webservice qual o tributo máximo permitido
								 * para esse produto
                                 */
                                ParamJson param = new ParamJson();
                                param.setToken(this.getToken());
                                param.setCaptcha(this.getToken());
                                if (!StringUtil.isNullOrEmpty(item.getSPED0200().getCodBarra())) {
                                    param.setEan(item.getSPED0200().getCodBarra());
                                }
                                /*
								 * if(!StringUtil.isNullOrEmpty(item.getSPED0200().getDescrItem())){
								 * param.setNome(item.getSPED0200().getDescrItem()); }
                                 */
                                tributacaoMix = consultaMix.consultarProduto(param);
                                // classTrib = consultaTributacao.consultarProduto(codProd, true);
                                // } catch(SALException ex) {
                                // classTrib = null;
                                // }
                                /*
								 * confronta SPED com XML e Mix analizando
                                 */
                                Tributacao t = new Tributacao();
                                t.setNumeroNota(spd100.getNumeroNF());
                                t.setValorProduto(item.getValor());
                                t.setValorBaseCalculo(item.getValorBCICMSST());
                                t.setImpostoSPED(item.getAliqICMSST());
                                t.setCargaSPED(item.getValorICMSST());
                                t.setTipoNota(spd100.getTipoNotaFiscal());

                                if (tributacaoMix != null && tributacaoMix.getProduto() != null
                                        && tributacaoMix.getProduto() != null) {
                                    BigDecimal alq = BigDecimal.ZERO;

                                    if (item.getCSTICMS().endsWith("10") || item.getCSTICMS().endsWith("30")) {
                                        alq = new BigDecimal(tributacaoMix.getProduto().getIcmsEntrada().getEiAlq());
                                    } else {
                                        alq = new BigDecimal(tributacaoMix.getProduto().getIcmsEntrada().getEdAlq());
                                    }

                                    t.setImpostoMix(alq);
                                    BigDecimal cargaMix = item.getValor()
                                            .multiply(t.getImpostoMix().divide(new BigDecimal(100)));
                                    t.setCargaMix(cargaMix);
                                }

                                tributos.add(t);
                            } catch (Exception ex) {
                                this.log.error(ex.getMessage());
                                System.out.println(ex);
                            }
                        });

                        if (tributos.size() > 0) {
                            Produto p = new Produto();
                            p.setCodigoCliente(prod.getCodItem());
                            p.setNome(prod.getDescrItem());
                            p.setTributacoes(tributos);
                            produtos.add(p);
                        }
                    } catch (Exception ex) {
                        this.log.error(ex.getMessage());
                        System.out.println(ex);
                    }
                });

                if (produtos.size() > 0) {
                    Fornecedor f = new Fornecedor();
                    f.setCodigo(fornec.getCodFornecedor());
                    f.setNome(fornec.getNome());
                    f.setCnpj(fornec.getCnpj());
                    f.setProdutos(produtos);
                    rel.add(f);
                }
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
                System.out.println(ex);
            }
        });

        return rel;
    }

    public List<Fornecedor> retornarRelatorioCstIncorreto() throws DadosSpedNaoCarregadosException {
        HashMap<SPEDC150, HashMap<SPED0200, List<SPEDC100>>> dadosSped = reorganizarDadosSped();
        final List<Fornecedor> fornecedores = new ArrayList<>();

        try {
            // carregar XML's
            Map<String, NFe> listaNfes = this.carregarNfes();
            // carregar o arquivo de sped
            dadosSped.forEach((fornec, prods) -> {// sped completo
                prods.forEach((prod, nfs) -> {// produtos
                    nfs.stream().forEach(spd100 -> {// notas
                        /*
						 * 1º ocorrencia percorrer o xml comparando se o CST de entrada do XML for
						 * 10,30,60,70 o cst de saida do SPED tem estar 60 se nao estiver mostra no
						 * relatorio
						 * 
						 * 2º ocorrencia se no XML o CST for 00,20,40,41,50,51 no SPEd nao podera estar
						 * CST 60,30,70,10 nem na entrada nem na saida se tiver mostra o CST do SPED o
						 * CST do XML se é entrada ou saida dos dois
                         */

                        // obtem o item do SPED completo
                        Optional<SPEDC170> retorno = spd100.getListaSPEDC170().stream()
                                .filter(i -> i.getCodItem().equals(prod.getCodItem())).findFirst();

                        SPEDC170 itemSped = retorno.isPresent() ? retorno.get() : null;
                        // se nao encontrar o item pula
                        if (itemSped == null) {
                            return;
                        }

                        // obtem o XML completo atravez da chave da nota fiscal encontrado no SPED
                        NFe nfe = listaNfes.get(spd100.getChaveNFe());
                        // se nao encontrar pula a nota
                        if (nfe == null) {
                            return;
                        }

                        // verifica se a nota XML é uma entrada
                        if (nfe.getTipoNotaFiscal() != ETipoNotaFiscal.Saida) {
                            return;
                        }

                        this.identificarItenSpedNfe(spd100, nfe);

                        // percorre a XML para localizar o item que esta no SPED atravez do codigo de
                        // barra
                        // nfe.getItens().forEach(itemNfe ->{
                        // aqui o filtro vai precisar encontrar o item

                        /*
						 * if(itemNfe.getCodigoEAN().equals(prod.getCodBarra())){ // obtem o ICMS do
						 * item da XML para verificar os tipos de ST //int teste =
						 * itemNfe.getICMS().getCST();
						 * 
						 * if(icmsNotaFisca.getCST().endsWith("10")) ||
						 * (icmsNotaFisca.getCST().endsWith("30")) ||
						 * (icmsNotaFisca.getCST().endsWith("60")) ||
						 * (icmsNotaFisca.getCST().endsWith("70"))){ if(itemSped.getCSTICMS() != "60"){
						 * Fornecedor f = new Fornecedor(); f.setCnpj(spd100.getFornecedor().getCnpj());
						 * f.setNome(spd100.getChaveNFe()); fornecedores.add(f); } } }
                         */
                        // });
                    });
                });
            });
            // carregar nfe
        } catch (Exception ex) {
            this.log.error(ex.getMessage());
        }
        return fornecedores;
    }

    public void identificarItenSpedNfe(SPEDC100 sc100, NFe nfe) {
        final StringBuffer docFornecedor = new StringBuffer();
        br.com.mixfiscal.prodspedxnfe.domain.own.Fornecedor fornec = this.obterEmpresa(sc100);
        if (fornec == null) {
            return;
        }

        if (Masc.isCNPJ(sc100.getFornecedor().getCnpj())) {
            docFornecedor.append(Masc.mascararCNPJ(sc100.getFornecedor().getCnpj()));
        } else if (Masc.isCPF(sc100.getFornecedor().getCpf())) {
            docFornecedor.append(Masc.mascararCPF(sc100.getFornecedor().getCpf()));
        }

        this.itensSC170.addAll(sc100.getListaSPEDC170());
        this.itensNFe.addAll(nfe.getItens());
        final List<NFeItem> nfeItens = new ArrayList<>();
        this.itensSC170.stream().forEach((sc170) -> {// percorre os itens do SPED
            nfeItens.clear();
            this.itensNFe.stream().forEach((nfeItem) -> {// percorre os itens do XML
                // verifica no banco quais itens foram relacionados
                List<RelacaoProdutoFornecedor> itensRelacionadoNoBanco = buscarRelacaoProdutoFornecedor(fornec.getId(),
                        docFornecedor.toString(), sc170.getCodItem(), nfeItem.getCodigoProduto());

                if (itensRelacionadoNoBanco != null && itensRelacionadoNoBanco.size() > 0) {
                    nfeItens.add(nfeItem);
                }
            });
        });
    }

    public br.com.mixfiscal.prodspedxnfe.domain.own.Fornecedor obterEmpresa(SPEDC100 sc100) {
        Empresa emp = new Empresa();
        br.com.mixfiscal.prodspedxnfe.domain.own.Fornecedor fornec = new br.com.mixfiscal.prodspedxnfe.domain.own.Fornecedor();
        fornec.setCnpj(Masc.mascararCNPJ(sc100.getFornecedor().getCnpj()));
        try {
            fornec = this.fornecDao.selecionarUm(fornec);
        } catch (Exception ex) {
            log.error("Erro ao selecionar o fornecedor " + fornec.getCnpj(), ex);
        }
        return fornec;
    }

    public Map<String, NFe> carregarNfes() throws ErroAoCarregarDadosNFeException, DadosNFeNaoCarregadosException {
        if (!StringUtil.isNullOrEmpty(this.caminhoDirXMLsNFes)) {
            return this.leitorXmlNFe.lerXmlsNFes(this.caminhoDirXMLsNFes);
        }
        throw new DadosNFeNaoCarregadosException("Nao carregou os aqurivos XML");
    }

    public Map<String, NFe> carregarNFCes() throws ErroAoCarregarDadosNFeException, DadosNFeNaoCarregadosException {
        if (!StringUtil.isNullOrEmpty(this.caminhoDirXMLsNFCes)) {
            return this.leitorXmlNFe.lerXmlsNFes(this.caminhoDirXMLsNFCes);
        }
        throw new DadosNFeNaoCarregadosException("Nao Carregou os arquivos XML");
    }

    public NFe buscarXmlNFe(String chaveNfe) throws ErroAoCarregarDadosNFeException, DadosNFeNaoCarregadosException {
        if (!StringUtil.isNullOrEmpty(this.caminhoDirXMLsNFes)) {
            return this.leitorXmlNFe.buscarXmlNFe(this.caminhoDirXMLsNFes, chaveNfe);
        }
        throw new DadosNFeNaoCarregadosException("Nao carregou o Aruivo XML");
    }

    public NFe buscarXmlNFCe(String chaveNfe) throws ErroAoCarregarDadosNFeException, DadosNFeNaoCarregadosException {
        if (!StringUtil.isNullOrEmpty(this.caminhoDirXMLsNFCes)) {
            return this.leitorXmlNFe.buscarXmlNFe(this.caminhoDirXMLsNFCes, chaveNfe);
        }
        throw new DadosNFeNaoCarregadosException("Nao carregou o Aruivo XML");
    }

    public CFe buscarXmlCFe(String chaveCFe) throws ErroAoCarregarDadosNFeException, DadosNFeNaoCarregadosException {
        if (!StringUtil.isNullOrEmpty(this.caminhoDirXMLsCFes)) {
            return this.leitorXmlCFe.buscarXmlCFe(this.caminhoDirXMLsCFes, chaveCFe);
        }
        throw new DadosNFeNaoCarregadosException("Nao carregou o arquivo XML");
    }

    public void inicializarVariaveis() {
        this.leitorSped = new LeitorSpedFiscal();
        this.leitorXmlNFe = new LeitorXmlNFe();
        this.leitorXmlCFe = new LeitorXmlCFe();
        this.itensMix = new ArrayList<>();
        this.tributacaoMix = new ClassificacaoTributariaProduto();
        this.consultaMix = new ConsultaMixFiscal();
        this.itensSC170 = new ArrayList<>();
        this.itensNFe = new ArrayList<>();
        this.empDao = new EmpresaDAO();
        this.fornecDao = new FornecedorDAO();
        this.relProdFornecDao = new RelacaoProdutoFornecedorDAO();
        this.valorTotalEstornoCredito = BigDecimal.ZERO;
        this.valorTotalCreditonaoAproveitado = BigDecimal.ZERO;
        this.ean = "";
        this.totalDebitoIndevido = BigDecimal.ZERO;
        this.totalDebitoNaoDeclarado = BigDecimal.ZERO;
        this.contadorProgresso = 0;
    }
    // </editor-fold>

    // <editor-fold desc="Métodos Privados" defaultstate="collapsed">
    private HashMap<SPEDC150, HashMap<SPED0200, List<SPEDC100>>> reorganizarDadosSped()
            throws DadosSpedNaoCarregadosException {
        final HashMap<SPEDC150, HashMap<SPED0200, List<SPEDC100>>> rel = new HashMap<>();// padrao de retorno de lista
        // que espera
        // fornecedor,item e XML's

        this.leitorSped.getListaSPEDC100().stream().forEach(spd100 -> {// percorre a lista de XML
            final HashMap<SPED0200, List<SPEDC100>> prods;

            if (rel.containsKey(spd100.getFornecedor())) {
                prods = rel.get(spd100.getFornecedor());
            } else {
                prods = new HashMap<>();
                rel.put(spd100.getFornecedor(), prods);
            }

            spd100.getListaSPEDC170().stream().forEach(spd170 -> {
                final List<SPEDC100> nfes;

                if (prods.containsKey(spd170.getSPED0200())) {
                    nfes = prods.get(spd170.getSPED0200());
                } else {
                    nfes = new ArrayList<>();
                    prods.put(spd170.getSPED0200(), nfes);
                }

                nfes.add(spd100);
            });
        });
        return rel;
    }

    private ClassificacaoTributariaProduto obterTributacaoMix(SPED0200 item, ETipoRelatorio tipo)
        throws DadosNFeNaoCarregadosException {
        ClassificacaoTributariaProdutoDAO tribProdMixDao = new ClassificacaoTributariaProdutoDAO();
        ClassificacaoTributariaProduto tribProdMix = new ClassificacaoTributariaProduto();
               
        try {
            tribProdMix = tribProdMixDao.selecionarProdutoPorCodigoECNPJCliente(item.getCodItem(),sped000.getCNPJ());
        } catch (Exception ex) {
            this.log.error("Erro no metodo obterTributacaoMix() chamado pelo relatorio [" + tipo.toString() + "] " + 
                           "codigo :" + item.getCodItem() + ", cnpj: " + sped000.getCNPJ() +  ", " + ex.getMessage(), 
                           ex);
        }
        
        return tribProdMix;
    }

    private List<RelacaoProdutoFornecedor> buscarRelacaoProdutoFornecedor(int idEmpresa, String cnpjFornec,
            String refProd, String refFornec) {
        List<RelacaoProdutoFornecedor> result = null;
        try {
            return relProdFornecDao.buscarPorFornecedor(idEmpresa, cnpjFornec, refProd, refFornec);
        } catch (Exception ex) {
            String msg = String.format(
                    "Houve um erro ao tentar verificar a existencia do Produto de Codigo '%s' no Fornecedor com Codigo %s da Empresa '%'",
                    refProd, refFornec, this.empresaAtual.getNome());
            // log.error(msg, ex);
            this.log.error(msg + " -->  " + ex.getMessage(), ex);
            return null;
        }
    }

    private Cliente obterTokenDoCliente(SPED0000 sped000) {
        ClienteService clienteSvc = new ClienteService();
        Cliente cliente = new Cliente();
        cliente.setCnpj(sped000.getCNPJ());

        try {
            cliente = clienteSvc.selecionarUm(cliente);
        } catch (Exception ex) {
            this.log.error(ex.getMessage());
        }
        // verifica se o cliente tem o token
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente nao tem token cadastrado");
        }

        return cliente;
    }

    private BigDecimal identificarDebitos(BigDecimal icmsNota, BigDecimal valorOperacao, BigDecimal cargaTribMix) {
        // se a carga da MIX for == a carga da XML pula
        // achando o ICMS da MIX = (cargaMix * valorOperacao)
        // achando o debito = ICMS da nota - ICMS da Mix
        // Debito indevido se for POSITIVO
        // Debito nao declarado se fo negativo
        BigDecimal result = BigDecimal.ZERO;
        try {
            result = icmsNota.subtract(mtUtil.identificarIcmsMix(cargaTribMix, valorOperacao));
        } catch (Exception ex) {
            this.log.error(ex.getMessage());
        }
        return result;
    }

    private void somarValorCreditoNaoAproveitado(BigDecimal valor) {
        this.valorTotalCreditonaoAproveitado = this.valorTotalCreditonaoAproveitado.add(valor);
    }

    private void somarValorEstornoCredito(BigDecimal valor) {
        this.valorTotalEstornoCredito = this.valorTotalEstornoCredito.add(valor);
    }

    private void somarValorDebitoNaoDeclarado(BigDecimal valor) {
        this.totalDebitoNaoDeclarado = this.totalDebitoNaoDeclarado.add(valor);
    }

    private void somarValorDebitoIndevido(BigDecimal valor) {
        this.totalDebitoIndevido = this.totalDebitoIndevido.add(valor);
    }

    private BigDecimal calcularCredito(CargaTributaria carga) {
        BigDecimal result = new BigDecimal(BigInteger.ZERO);
        try {
            result = carga.cargaTributaria.subtract(mtUtil.calcularReducaoCargaTributaria(carga));
        } catch (Exception ex) {
            this.log.error(ex.getMessage());
        }
        return result;
    }

    public ClassificacaoTributaria consultarWebService(String token, SPED0200 produto) {
        ParamJson param = new ParamJson();
        param.setToken(token);
        param.setCaptcha(token);
        produto.getDescricaoCustom();
        if (!StringUtil.isNullOrEmpty(produto.getCodBarra())) {
            param.setEan(produto.getCodBarra());
        } else {
            if (!StringUtil.isNullOrEmpty(produto.getDescrItem())) {
                param.setNome(produto.getDescrItem());
            } else {
                param.setNome(produto.getDescricaoCustom());
            }
        }
        return consultaMix.consultarProduto(param);
    }

    private SPEDC470 encontrarProdutoNaListaCupom(SPED0200 produto, List<SPEDC470> itens) {
        SPEDC470 itemCupom = null;
        // Optional<SPEDC470> retorno = itens.stream().filter(item ->
        // item.getCodigoItem().equals(produto.getCodItem())).findFirst();
        // if(retorno.isPresent())
        // itemCupom = retorno.get();

        return itemCupom;
    }

    private SPEDC170 localizarProdutoEmSPEDC170(SPED0200 produto, List<SPEDC170> itens) {
        // verifica se o produto cadastrado no bloco sped0200 esta contido na lista de
        // itens c170 referente a nota c100

        Optional<SPEDC170> res = itens.stream().filter(s -> s.getCodItem().equals(produto.getCodItem())).findFirst();
        SPEDC170 item = res.isPresent() ? res.get() : null;
        return item;
    }

    private Relatorio identificarTipoDeInstancia(Relatorio rel, Object doc) throws ParseException {
        String cstIcms = "0";
        String cpfCnpj = "N/A";
        String nNOta = "0";
        String produto = "";
        String CFOP = "";
        BigDecimal valorOperacao = new BigDecimal(BigInteger.ZERO);
        BigDecimal aliqIcms = new BigDecimal(BigInteger.ZERO);
        rel.setTipoDoc("N/A");
        if (doc instanceof SPEDC470) {// identifica
            SPEDC470 spdCupom = (SPEDC470) doc;
            SPEDC460 spdc460 = spdCupom.getSPED460();
            rel.setTipoDoc("ECF");
            rel.setChaveNfe(spdc460.getNumDoc());
            SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy");
            if (spdc460.getDtDoc() != null) {
                Date data = formato.parse(spdCupom.getSPED460().getDtDoc());
                rel.setData(data);
                rel.setCargaTributaria(spdCupom.getAliqICMS());
            }

            nNOta = spdc460.getNumDoc();
            this.ean = spdCupom.getSPED0200().getCodBarra();
            produto = spdCupom.getSPED0200().getDescrItem();
            CFOP = spdCupom.getCFOP();
            valorOperacao = spdCupom.getValor();
            cstIcms = spdCupom.getCSTICMS();
            aliqIcms = spdCupom.getAliqICMS();
        } else if (doc instanceof SPEDC170) {
            SPEDC170 spdNfe = (SPEDC170) doc;
            SPEDC100 spdc100 = spdNfe.getSPEDC100();
            if (spdc100.getTipoNotaFiscal().equals(ETipoNotaFiscal.Entrada)) {
                return rel;
            }
            rel.setData(spdc100.getDataEmissao());
            rel.setTipoDoc("NF-e");
            rel.setChaveNfe(spdNfe.getSPEDC100().getChaveNFe());
            nNOta = spdc100.getNumeroNF().toString();
            ean = spdNfe.getSPED0200().getCodBarra();
            produto = spdNfe.getSPED0200().getDescrItem();
            CFOP = spdNfe.getCFOP();
            valorOperacao = spdNfe.getValor();
            cstIcms = spdNfe.getCSTICMS();

        }
        rel.setClienteFornecedor("Consumidor");
        rel.setDescricaoProduto(produto);
        rel.setCfopSped(CFOP);
        rel.setValorOperacao(valorOperacao);
        rel.setNumeroNota(nNOta);
        rel.setCstIcmsSped(cstIcms);
        rel.setAliqIcmsSped(aliqIcms);
        rel.setCargaTributariaSped(mtUtil.calcularValorCargaTributaria(valorOperacao, aliqIcms));
        return rel;
    }

    private Relatorio identificarTibutosXml(Relatorio rel, NFe nfe) {
        NFeItem nfeItem = null;
        if (nfe != null) {
            Optional<NFeItem> res = nfe.getItens().stream().filter(item -> item.getCodigoEAN().equals(this.ean))
                    .findFirst();
            nfeItem = res.isPresent() ? res.get() : null;
            if (nfeItem != null) {
                rel.setClienteFornecedor(nfe.getDestinatario().getCNPJ());
                rel.setCstIcmsXml(Integer.toString(nfeItem.getICMS().getCST()));
                this.identificarTipoDeIcmsXML(rel, nfeItem.getICMS());
                rel.setCargaTributariaXml(
                        mtUtil.calcularValorCargaTributaria(nfeItem.getValorProduto(), rel.getAliqIcmsXml()));
            }
        }
        return rel;
    }

    private Relatorio identificarTributosMix(Relatorio rel, Object item, NFeItem nfeItem, ETipoRelatorio tipo) {
        SPED0200 spd0200 = null;
        SPEDC100 spdc100 = null;
        BigDecimal aliq = BigDecimal.ZERO;
        BigDecimal redBc = BigDecimal.ZERO;
        String cst = null;
        Class classe = item.getClass();
        if (classe.getSimpleName().equals("SPEDC170")) {
            SPEDC170 spdc170 = (SPEDC170) item;
            spdc100 = (SPEDC100) spdc170.getSPEDC100();
            spd0200 = spdc170.getSPED0200();
            try {
                tributacaoMix = this.obterTributacaoMix(spd0200, tipo);
                if ((!StringUtil.isNullOrEmpty(nfeItem.getNFe().getDestinatario().getCNPJ()))
                        && (!StringUtil.isNullOrEmpty(nfeItem.getNFe().getDestinatario().getIE()))) {
                    // SVC
                    cst = tributacaoMix.getIcmsSaida().getSvcCst().trim();
                    if (mtUtil.verificarCstValido(cst)) {// se cst for valido obtem aliquotas
                        aliq = new BigDecimal(tributacaoMix.getIcmsSaida().getSvcAlq().trim());
                        redBc = new BigDecimal(tributacaoMix.getIcmsSaida().getSvcRbc().trim());
                    }
                } else {
                    // SNC
                    cst = tributacaoMix.getIcmsSaida().getSncCst().trim();
                    aliq = new BigDecimal(tributacaoMix.getIcmsSaida().getSncAlq().trim());
                    redBc = new BigDecimal(tributacaoMix.getIcmsSaida().getSncRbc().trim());
                }
                rel.setCstIcmsMix(cst);
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
                rel.setCstIcmsMix(cst);
            }
        }
        rel.setAliqIcmsMix(mtUtil.arredondarAliquotaDecimal(aliq));
        rel.setRedBcMix(mtUtil.arredondarValorDecimal(redBc));
        return rel;
    }

    private Relatorio identificarTributosMix(Relatorio rel, NFeItem nfeItem, ETipoRelatorio tipo, String modelo) {
        SPED0200 spd0200 = null;
        SPEDC100 spdc100 = null;
        BigDecimal aliq = BigDecimal.ZERO;
        BigDecimal redBc = BigDecimal.ZERO;
        String cst = null;
        spd0200 = new SPED0200();
        spd0200.setCodItem(rel.getCodigoInternoProduto());
        try {
            tributacaoMix = this.obterTributacaoMix(spd0200, tipo);
            if (tributacaoMix != null) {
                if ((!StringUtil.isNullOrEmpty(nfeItem.getNFe().getDestinatario().getCNPJ()))
                        && (!StringUtil.isNullOrEmpty(nfeItem.getNFe().getDestinatario().getIE()))) {
                    // SVC
                    if(tributacaoMix != null &&
                       tributacaoMix.getIcmsSaida() != null &&
                       tributacaoMix.getIcmsSaida().getSvcCst() != null){
                        cst = tributacaoMix.getIcmsSaida().getSvcCst().trim();
                    }
                    if (mtUtil.verificarCstValido(cst)) {// se cst for valido obtem aliquotas
                        if(tributacaoMix != null &&
                           tributacaoMix.getIcmsSaida() != null &&
                           tributacaoMix.getIcmsSaida().getSvcAlq() != null){
                            aliq = new BigDecimal(tributacaoMix.getIcmsSaida().getSvcAlq().trim());
                        }
                        if(tributacaoMix != null &&
                           tributacaoMix.getIcmsSaida() != null &&
                           tributacaoMix.getIcmsSaida().getSvcRbc() != null) {
                            redBc = new BigDecimal(tributacaoMix.getIcmsSaida().getSvcRbc().trim());
                        }
                    }
                } else {
                    // SNC
                    if(tributacaoMix != null &&
                       tributacaoMix.getIcmsSaida() != null &&
                       tributacaoMix.getIcmsSaida().getSncCst() != null){
                        cst = tributacaoMix.getIcmsSaida().getSncCst().trim();
                    }
                    if(mtUtil.verificarCstValido(cst)){
                        if(tributacaoMix != null &&
                            tributacaoMix.getIcmsSaida() != null &&
                            tributacaoMix.getIcmsSaida().getSncAlq() != null){
                            aliq = new BigDecimal(tributacaoMix.getIcmsSaida().getSncAlq().trim());
                        }
                        if (tributacaoMix != null &&
                            tributacaoMix.getIcmsSaida() != null &&
                            tributacaoMix.getIcmsSaida().getSncRbc() != null){
                            redBc = new BigDecimal(tributacaoMix.getIcmsSaida().getSncRbc().trim());
                        }
                    }
                }
                rel.setCstIcmsMix(cst);
            } else {
                rel.setCstIcmsMix(cst);
            }
        } catch (Exception ex) {
            this.log.error("erro ao identificar o tipo de aliquota SvC e SNC " + ex.getMessage(), ex);
            rel.setCstIcmsMix(cst);
        }
        rel.setAliqIcmsMix(mtUtil.arredondarAliquotaDecimal(aliq));
        rel.setRedBcMix(mtUtil.arredondarValorDecimal(redBc));
        return rel;
    }

    private Relatorio identificarTipoDeIcmsXML(Relatorio rel, ICMS icms) {
        BigDecimal aliq = new BigDecimal(BigInteger.ZERO);
        BigDecimal redBc = new BigDecimal(BigInteger.ZERO);
        BigDecimal valor = BigDecimal.ZERO;
        BigDecimal baseCalculo = BigDecimal.ZERO;
        try {
            if (icms instanceof ICMS00) {
                ICMS00 icms00 = (ICMS00) icms;
                aliq = icms00.getAliquotaICMS();
                valor = icms00.getValorICMS();
                baseCalculo = icms00.getValorBC();

            } else if (icms instanceof ICMS10) {
                ICMS10 icms10 = (ICMS10) icms;
                aliq = icms10.getAliquotaICMS();
                redBc = icms10.getPercentualReducaoBCST();
                valor = icms10.getValorICMS();
                baseCalculo = icms10.getValorBC();
            } else if (icms instanceof ICMS20) {
                ICMS20 icms20 = (ICMS20) icms;
                aliq = icms20.getAliquotaICMS();
                redBc = icms20.getPercentualReducaoBC();
                valor = icms20.getValorICMS();
                baseCalculo = icms20.getValorBC();
            } else if (icms instanceof ICMS30) {
                ICMS30 icms30 = (ICMS30) icms;
                aliq = icms30.getAliquotaICMSST();
                redBc = icms30.getPercentualReducaoBCST();
                baseCalculo = icms30.getValorBCST();

            } else if (icms instanceof ICMS40) {
                ICMS40 icms40 = (ICMS40) icms;
                aliq = icms40.getValorICMSDesoneracao();

            } else if (icms instanceof ICMS41) {
                ICMS41 icms41 = (ICMS41) icms;
                aliq = icms41.getValorICMSDesoneracao();

            } else if (icms instanceof ICMS50) {
                ICMS50 icms50 = (ICMS50) icms;
                aliq = icms50.getValorICMSDesoneracao();
            } else if (icms instanceof ICMS51) {
                ICMS51 icms51 = (ICMS51) icms;
                aliq = icms51.getAliquotaICMS();
                redBc = icms51.getPercentualReducaoBC();
                valor = icms51.getValorICMSOperacao();
                baseCalculo = icms51.getValorBC();
            } else if (icms instanceof ICMS60) {
                ICMS60 icms60 = (ICMS60) icms;
                aliq = icms60.getValorICMSSTRet();
                baseCalculo = icms60.getValorBCSTRet();

            } else if (icms instanceof ICMS70) {
                ICMS70 icms70 = (ICMS70) icms;
                aliq = icms70.getAliquotaICMS();
                redBc = icms70.getPercentualReducaoBC();
                valor = icms70.getValorICMS();
                baseCalculo = icms70.getValorBC();
            } else if (icms instanceof ICMS90) {
                ICMS90 icms90 = (ICMS90) icms;
                aliq = icms90.getAliquotaICMS();
                redBc = icms90.getPercentualReducaoBC();
                valor = icms90.getValorICMS();
                baseCalculo = icms90.getValorBC();
            }
        } catch (Exception ex) {
            this.log.error(ex.getMessage());
            rel.setValorIcmsXml(mtUtil.arredondarValorDecimal(valor));
            rel.setAliqIcmsXml(mtUtil.arredondarAliquotaDecimal(aliq));
            rel.setRedBcXml(mtUtil.arredondarValorDecimal(redBc));
            rel.setBaseDeCalculoXml(mtUtil.arredondarValorDecimal(baseCalculo));
        }
        rel.setValorIcmsXml(mtUtil.arredondarValorDecimal(valor));
        rel.setAliqIcmsXml(mtUtil.arredondarAliquotaDecimal(aliq));
        rel.setRedBcXml(mtUtil.arredondarValorDecimal(redBc));
        rel.setBaseDeCalculoXml(mtUtil.arredondarValorDecimal(baseCalculo));
        return rel;

    }

    private Relatorio localizarItemNaListaXml(Relatorio rel, Object obj) {
        SPEDC170 spd = null;
        if (obj instanceof SPEDC170) {
            spd = (SPEDC170) obj;
        }
        if (spd == null) {
            return rel;
        }
        NFe nfe = null;

        if (ETipoModeloDocumento.NotaFiscalEletronica.equalsName(spd.getSPEDC100().getModeloDocumento()) == true) {
            // nfe = listaXmlNfe.get(spd.getSPEDC100().getChaveNFe());
            try {
                nfe = this.buscarXmlNFe(spd.getSPEDC100().getChaveNFe());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
        } else if (ETipoModeloDocumento.NotaFiscalConsumidorEletronico
                .equalsName(spd.getSPEDC100().getModeloDocumento()) == true) {
            // nfe = listaXmlNFCe.get(spd.getSPEDC100().getChaveNFe());
            try {
                nfe = this.buscarXmlNFCe(spd.getSPEDC100().getChaveNFe());
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
        }
        rel = this.identificarTibutosXml(rel, nfe);
        return rel;
    }

    private Relatorio zerarValoresRelatorio(Relatorio rel) {
        rel.setDescricaoProduto("");
        rel.setClienteFornecedor("");
        rel.setCnpj("");
        rel.setCfopSped("000");
        rel.setValorOperacao(BigDecimal.ZERO);
        // aliqotas sped
        rel.setCstIcmsSped("");
        rel.setValorIcmsSped(BigDecimal.ZERO);
        rel.setAliqIcmsSped(BigDecimal.ZERO);
        rel.setRedBcSped(BigDecimal.ZERO);
        rel.setCargaTributariaSped(BigDecimal.ZERO);
        // aliquotas XML
        rel.setCstIcmsXml("");
        rel.setAliqIcmsXml(BigDecimal.ZERO);
        rel.setRedBcXml(BigDecimal.ZERO);
        rel.setCargaTributariaXml(BigDecimal.ZERO);
        rel.setValorIcmsXml(BigDecimal.ZERO);
        // aliquota MIX
        rel.setCstIcmsMix("");
        rel.setAliqIcmsMix(BigDecimal.ZERO);
        rel.setCargaTributariaMix(BigDecimal.ZERO);
        rel.setRedBcMix(BigDecimal.ZERO);
        // debitos
        rel.setDebitoNaoDeclarado(BigDecimal.ZERO);
        rel.setDebitoIndevido(BigDecimal.ZERO);
        return rel;
    }

    private Relatorio totalizarDebitos(BigDecimal valor, Relatorio rel) {
        if (valor.compareTo(BigDecimal.ZERO) > 0) {
            rel.setDebitoIndevido(BigDecimal.ZERO);
            rel.setDebitoNaoDeclarado(valor);
            this.somarValorDebitoNaoDeclarado(valor);
        } else {
            rel.setDebitoNaoDeclarado(BigDecimal.ZERO);
            rel.setDebitoIndevido(valor);
            this.somarValorDebitoIndevido(valor);
        }
        return rel;
    }

    private boolean calcularEstornoCreditoNaoAproveitado(SPEDC170 item) {
        
        try {
            if (item.getValorICMS().compareTo(relatorio.getCargaTributariaMix()) > 0) {
                BigDecimal diferenca = item.getValorICMS().subtract(relatorio.getCargaTributariaMix());
                relatorio.setEstornoDeCredito(mtUtil.arredondarValorDecimal(diferenca));
            } else if (item.getValorICMS().compareTo(relatorio.getCargaTributariaMix()) < 0) {
                BigDecimal diferenca = relatorio.getCargaTributariaMix().subtract(item.getValorICMS());
                relatorio.setCreditoIndevido(mtUtil.arredondarValorDecimal(diferenca));
            }else{
                //System.out.println("valor do sped-> " + item.getValorICMS() + ", valor da MIX-> " + relatorio.getCargaTributariaMix());
                return false;
            }
           
        } catch (Exception ex) {           
            log.error("erro no metodo calcularEstornoCreditoNaoAproveitado() " + ex.getMessage(), ex);
            return false;
        }
         return true;
    }

    private boolean identificarCFOPSaida(String cfop) {
        if (cfop.equals("5102")
                || cfop.equals("5101")
                || cfop.equals("5103")
                || cfop.equals("5104")
                || cfop.equals("5105")
                || cfop.equals("5106")
                || cfop.equals("5405")
                || cfop.equals("5401")
                || cfop.equals("5403")) {
            return true;
        }

        return false;
    }

    private boolean identificarCFOPEntrada(String cfop) {

        if (cfop.equals("1101")
                || cfop.equals("1102")
                || cfop.equals("1113")
                || cfop.equals("1117")
                || cfop.equals("1121")
                || cfop.equals("1401")
                || cfop.equals("1403")) {
            return true;
        }

        return false;
    }

    private boolean contemCFOPSaida(List<? super SPEDC190> itens) {
        boolean result = false;
        for (Object item : itens) {
            if (item instanceof SPEDC190) {
                SPEDC190 it = (SPEDC190) item;
                result = this.identificarCFOPSaida(it.getCfop());
            } else if (item instanceof SPEDC170) {
                SPEDC170 it = (SPEDC170) item;
                result = this.identificarCFOPSaida(it.getCFOP());
                System.out.println("CFOP encontrado de NF-E--->>" + it.getCFOP() + " ACHOU " + result + " chave DA NOTA"
                        + it.getSPEDC100().getChaveNFe());
            }
            if (result) {
                break;
            }
        }
        return result;
    }

    private void compararRegistros(List<? super SPEDC190> spedItens, List<NFeItem> nfeItens, String path,
            StringBuilder stb) {
        for (Object item : spedItens) {
            if (item instanceof SPEDC190) {
                SPEDC190 it = (SPEDC190) item;
                codigoCompara = it.getCodItem();
                // descricaoCompara = it.getSPED0200();
            } else if (item instanceof SPEDC170) {
                SPEDC170 it = (SPEDC170) item;
                codigoCompara = it.getCodItem();
                descricaoCompara = it.getDescricaoComplementar();
            }
            try {
                nfeItens.forEach(itemNfe -> {
                    if (!itemNfe.getCodigoProduto().equals(codigoCompara)
                            || !itemNfe.getCodigoProduto().equals(descricaoCompara)) {
                        stb.append(String.format("'%s;%s;'%s;%s;%s\r\n", itemNfe.getNFe().getChaveNFe(),
                                itemNfe.getCodigoProduto(), codigoCompara, itemNfe.getDescricaoProduto(),
                                descricaoCompara));
                    }
                });

            } catch (Exception ex) {
                this.log.error(ex.getMessage(), ex);
            }
        }
        try {
            mtUtil.criarArquivoCSV(path, stb);
        } catch (Exception ex) {
            this.log.error("nao consegiu criar o csv para analize no metodo compararRegistros() " + ex.getMessage(),
                    ex);
        }

    }

    private String corrigirCSTZero(String cst) {
        if (cst.equals("0") || StringUtil.isNullOrEmpty(cst)) {
            return "00";
        }

        return cst;
    }

    private boolean identificarInconsistencia(float porc, String chave, String codigoXML, String codigoCSV,
            String descricaoXml, String descricaoCsv) {
        if (porc < 0.28) {
            this.sbRelInconsistente.append(String.format("'%s;%s;'%s;%s;%s;%s\r\n", chave, codigoXML, codigoCSV,
                    descricaoXml, descricaoCsv, porc));
            try {
                mtUtil.criarArquivoCSV(
                        String.format("%s%s", this.caminhoDirXMLsNFCes, File.separator + "InconsistentesNFCe_.csv"),
                        this.sbRelInconsistente);
            } catch (Exception ex) {
                this.log.error(ex.getMessage());
            }
            return true;
        }
        return false;
    }
    // </editor-fold>

    // <editor-fold desc="Classes Internas" defaultstate="collapsed">
    public static class Fornecedor {

        private String codigo;
        private String nome;
        private String cnpj;
        private List<Produto> produtos;

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCnpj() {
            return cnpj;
        }

        public void setCnpj(String cnpj) {
            this.cnpj = cnpj;
        }

        public List<Produto> getProdutos() {
            return produtos;
        }

        public void setProdutos(List<Produto> produtos) {
            this.produtos = produtos;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 23 * hash + Objects.hashCode(this.codigo);
            hash = 23 * hash + Objects.hashCode(this.nome);
            hash = 23 * hash + Objects.hashCode(this.cnpj);
            hash = 23 * hash + Objects.hashCode(this.produtos);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Fornecedor other = (Fornecedor) obj;
            if (!Objects.equals(this.codigo, other.codigo)) {
                return false;
            }
            if (!Objects.equals(this.nome, other.nome)) {
                return false;
            }
            if (!Objects.equals(this.cnpj, other.cnpj)) {
                return false;
            }
            if (!Objects.equals(this.produtos, other.produtos)) {
                return false;
            }
            return true;
        }
    }

    public static class Produto {

        private String nome;
        private String codigoCliente;
        private List<Tributacao> tributacoes;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCodigoCliente() {
            return codigoCliente;
        }

        public void setCodigoCliente(String codigoCliente) {
            this.codigoCliente = codigoCliente;
        }

        public List<Tributacao> getTributacoes() {
            return tributacoes;
        }

        public void setTributacoes(List<Tributacao> tributacoes) {
            this.tributacoes = tributacoes;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 67 * hash + Objects.hashCode(this.nome);
            hash = 67 * hash + Objects.hashCode(this.codigoCliente);
            hash = 67 * hash + Objects.hashCode(this.tributacoes);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Produto other = (Produto) obj;
            if (!Objects.equals(this.nome, other.nome)) {
                return false;
            }
            if (!Objects.equals(this.codigoCliente, other.codigoCliente)) {
                return false;
            }
            if (!Objects.equals(this.tributacoes, other.tributacoes)) {
                return false;
            }
            return true;
        }
    }

    public static class Tributacao {

        private Date data;
        private String numeroNota;
        private ETipoNotaFiscal tipoNota;
        private BigDecimal valorProduto;
        private BigDecimal valorBaseCalculo;
        private BigDecimal impostoXml;
        private BigDecimal cargaXml;
        private BigDecimal impostoSPED;
        private BigDecimal cargaSPED;
        private BigDecimal impostoMix;
        private BigDecimal cargaMix;

        public Date getData() {
            return data;
        }

        public void setData(Date data) {
            this.data = data;
        }

        public String getNumeroNota() {
            return numeroNota;
        }

        public void setNumeroNota(String numeroNota) {
            this.numeroNota = numeroNota;
        }

        public BigDecimal getValorProduto() {
            return valorProduto;
        }

        public void setValorProduto(BigDecimal valorProduto) {
            this.valorProduto = valorProduto;
        }

        public BigDecimal getValorBaseCalculo() {
            return valorBaseCalculo;
        }

        public void setValorBaseCalculo(BigDecimal valorBaseCalculo) {
            this.valorBaseCalculo = valorBaseCalculo;
        }

        public BigDecimal getImpostoXml() {
            return impostoXml;
        }

        public void setImpostoXml(BigDecimal impostoXml) {
            this.impostoXml = impostoXml;
        }

        public BigDecimal getCargaXml() {
            return cargaXml;
        }

        public void setCargaXml(BigDecimal cargaXml) {
            this.cargaXml = cargaXml;
        }

        public BigDecimal getImpostoSPED() {
            return impostoSPED;
        }

        public void setImpostoSPED(BigDecimal impostoSPED) {
            this.impostoSPED = impostoSPED;
        }

        public BigDecimal getCargaSPED() {
            return cargaSPED;
        }

        public void setCargaSPED(BigDecimal cargaSPED) {
            this.cargaSPED = cargaSPED;
        }

        public BigDecimal getImpostoMix() {
            return impostoMix;
        }

        public void setImpostoMix(BigDecimal impostoMix) {
            this.impostoMix = impostoMix;
        }

        public BigDecimal getCargaMix() {
            return cargaMix;
        }

        public void setCargaMix(BigDecimal cargaMix) {
            this.cargaMix = cargaMix;
        }

        public ETipoNotaFiscal getTipoNota() {
            return tipoNota;
        }

        public void setTipoNota(ETipoNotaFiscal tipoNota) {
            this.tipoNota = tipoNota;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 61 * hash + Objects.hashCode(this.data);
            hash = 61 * hash + Objects.hashCode(this.numeroNota);
            hash = 61 * hash + Objects.hashCode(this.tipoNota);
            hash = 61 * hash + Objects.hashCode(this.valorProduto);
            hash = 61 * hash + Objects.hashCode(this.valorBaseCalculo);
            hash = 61 * hash + Objects.hashCode(this.impostoXml);
            hash = 61 * hash + Objects.hashCode(this.cargaXml);
            hash = 61 * hash + Objects.hashCode(this.impostoSPED);
            hash = 61 * hash + Objects.hashCode(this.cargaSPED);
            hash = 61 * hash + Objects.hashCode(this.impostoMix);
            hash = 61 * hash + Objects.hashCode(this.cargaMix);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Tributacao other = (Tributacao) obj;
            if (!Objects.equals(this.numeroNota, other.numeroNota)) {
                return false;
            }
            if (!Objects.equals(this.data, other.data)) {
                return false;
            }
            if (this.tipoNota != other.tipoNota) {
                return false;
            }
            if (!Objects.equals(this.valorProduto, other.valorProduto)) {
                return false;
            }
            if (!Objects.equals(this.valorBaseCalculo, other.valorBaseCalculo)) {
                return false;
            }
            if (!Objects.equals(this.impostoXml, other.impostoXml)) {
                return false;
            }
            if (!Objects.equals(this.cargaXml, other.cargaXml)) {
                return false;
            }
            if (!Objects.equals(this.impostoSPED, other.impostoSPED)) {
                return false;
            }
            if (!Objects.equals(this.cargaSPED, other.cargaSPED)) {
                return false;
            }
            if (!Objects.equals(this.impostoMix, other.impostoMix)) {
                return false;
            }
            if (!Objects.equals(this.cargaMix, other.cargaMix)) {
                return false;
            }
            return true;
        }

    }

    public static class CargaTributaria {

        private BigDecimal cargaTributaria;
        private BigDecimal aliquota;
        private BigDecimal redBc;
        private BigDecimal cargaTribMix;

        public BigDecimal getCargaTributaria() {
            return cargaTributaria;
        }

        public void setCargaTributaria(BigDecimal cargaTributaria) {
            this.cargaTributaria = cargaTributaria;
        }

        public BigDecimal getAliquota() {
            return aliquota;
        }

        public void setAliquota(BigDecimal aliquota) {
            this.aliquota = aliquota;
        }

        public BigDecimal getRedBc() {
            return redBc;
        }

        public BigDecimal getCargaTribMix() {
            return cargaTribMix;
        }

        public void setCargaTribMix(BigDecimal cargaTribMix) {
            this.cargaTribMix = cargaTribMix;
        }

        public void setRedBc(BigDecimal redBc) {
            this.redBc = redBc;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 79 * hash + Objects.hashCode(this.cargaTributaria);
            hash = 79 * hash + Objects.hashCode(this.aliquota);
            hash = 79 * hash + Objects.hashCode(this.redBc);
            hash = 79 * hash + Objects.hashCode(this.cargaTribMix);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final CargaTributaria other = (CargaTributaria) obj;
            if (!Objects.equals(this.cargaTributaria, other.cargaTributaria)) {
                return false;
            }
            if (!Objects.equals(this.aliquota, other.aliquota)) {
                return false;
            }
            if (!Objects.equals(this.redBc, other.redBc)) {
                return false;
            }
            if (!Objects.equals(this.cargaTribMix, other.cargaTribMix)) {
                return false;
            }
            return true;
        }

    }
    // </editor-fold>

}
