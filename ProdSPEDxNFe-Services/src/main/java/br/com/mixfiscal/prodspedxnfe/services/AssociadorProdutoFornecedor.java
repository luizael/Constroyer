package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.dao.questor.EmpresaQuestorDAO;
import br.com.mixfiscal.prodspedxnfe.dao.questor.FatorConversaoUnidadeDAO;
import br.com.mixfiscal.prodspedxnfe.dao.questor.MunicipioDAO;
import br.com.mixfiscal.prodspedxnfe.dao.questor.PessoaDAO;
import br.com.mixfiscal.prodspedxnfe.dao.questor.ProdutoDAO;
import br.com.mixfiscal.prodspedxnfe.dao.questor.RelacProdutoFornecedorDAO;
import br.com.mixfiscal.prodspedxnfe.dao.questor.UnidadeMedidaDAO;
import br.com.mixfiscal.prodspedxnfe.dao.ex.DAOException;
//import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.EmpresaQuestor;
import br.com.mixfiscal.prodspedxnfe.domain.questor.FatorConversaoUnidade;
import br.com.mixfiscal.prodspedxnfe.domain.questor.Municipio;
import br.com.mixfiscal.prodspedxnfe.domain.questor.Pessoa;
import br.com.mixfiscal.prodspedxnfe.domain.questor.ProdutoQuestor;
import br.com.mixfiscal.prodspedxnfe.domain.questor.RelacProdutoFornecedor;
import br.com.mixfiscal.prodspedxnfe.domain.questor.UnidadeMedida;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFe;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFeItem;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.PessoaJuridica;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoNotaFiscal;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC100;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC170;
import br.com.mixfiscal.prodspedxnfe.ex.PermissaoDiretorioException;
import br.com.mixfiscal.prodspedxnfe.services.ex.ArquivoSpedVazioException;
import br.com.mixfiscal.prodspedxnfe.services.ex.DadosNFeNaoCarregadosException;
import br.com.mixfiscal.prodspedxnfe.services.ex.DadosSpedNaoCarregadosException;
import br.com.mixfiscal.prodspedxnfe.services.ex.EmpresaNaoEncontradaException;
import br.com.mixfiscal.prodspedxnfe.services.ex.ErroAoCarregarDadosNFeException;
import br.com.mixfiscal.prodspedxnfe.services.ex.ErroAoCarregarDadosSpedException;
import br.com.mixfiscal.prodspedxnfe.util.FileUtil;
import br.com.mixfiscal.prodspedxnfe.util.Masc;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AssociadorProdutoFornecedor {    
    // <editor-fold defaultstate="collapsed" desc="Membros Privados - Uso Interno">
    private LeitorSpedFiscal leitorSpedFiscal;
    private LeitorXmlNFe leitorXmlNFe;
    private PessoaDAO fornecDao;
    private EmpresaQuestorDAO empDao;
    private ProdutoDAO prodDao;
    private RelacProdutoFornecedorDAO relProdForDao;
    private UnidadeMedidaDAO uniMedDao;
    private FatorConversaoUnidadeDAO fatorConvUniDao;
    private Map<SPEDC170, NFeItem> mapItensSpdNFe; // Liga os itens do sped com os da nota fiscal
    private List<SPEDC170> itensSC170; // Lista de SPEDC170 utilizada para realização da pré-alálise
    private List<NFeItem> itensNFe; // Lista de NFeItem utilizada para realização da pré-alálise
    private BigDecimal valorTotalNFe;
    
    private Logger log;    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Membros Privados - Getters e Setters">    
    private String caminhoArquivoSpedFiscal;
    private String caminhoDiretorioXmlsNFes;
    private List<String> caminhosXmlsNFes;
    private List<String> chavesNFesNaoEncontradas;
    private Map<NFe, List<SPEDC170>> listaSPEDC170NaoEncontrados;
    private Map<NFe, List<NFeItem>> listaNFeItemNaoEncontrados;
    private int qtdFornecCad; // Contador de Fornecedores Cadastrados
    private int qtdProdCad; // Contador de Produtos Cadastrados
    private int qtdUnidMedCad; // Contador de Unidades de Medidas cadastradas
    private int qtdFatorCad; // Contador de Fatores de Conversão cadastrados
    private int qtdRelProdFornecCad; // Contador de Relacionamento ProdXFornecedores cadastrados
    private String resumoProcessamento; // Texto com o resumo do processamento realizado
    private boolean assumirItensEmOrdem; // Se for true, assume que os itens do SPED e dos XMLs estão na mesma ordem e ignora todas as outras verificações
    private boolean analisarOrdemItensAuto; // Se for true, o programa ignora a flag acima e faz uma análise automatica para verificar se os itens estão em ordem
    private boolean executarTransacoes; // Indica se o processo deve tentar inserir os dados no banco conforme necessidade. O padrão é true
    private boolean moverXMLsNaoEncSped; // Indica se o processo deve mover os XMLs que estão no diretório mas não estao no sped, para outro diretório. O padrão é true
    private List<String> caminhosArquivosCSV;
    private Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociados; // Map de itens para a realização de associação manual por parte do usuário. Esse Map é gerado pela logica de aproximação de nomes
    private Map<SPEDC170, NFeItem> itensAssociadosManualmente; // Itens associados pelo usuário
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Construtores">
    public AssociadorProdutoFornecedor(boolean executarTransacoes) {
        this.setExecutarTransacoes(executarTransacoes);
        
        if (this.executarTransacoes)
            inicializarDAOs();
        
        this.leitorSpedFiscal = new LeitorSpedFiscal();
        this.leitorXmlNFe = new LeitorXmlNFe();        
        this.mapItensSpdNFe = new HashMap<>();
        this.itensSC170 = new ArrayList<>();
        this.itensNFe = new ArrayList<>();
        this.chavesNFesNaoEncontradas = new ArrayList<>();
        this.listaSPEDC170NaoEncontrados = new HashMap<>();
        this.listaNFeItemNaoEncontrados = new HashMap<>();
        this.log = LogManager.getLogger(AssociadorProdutoFornecedor.class);
        this.assumirItensEmOrdem = false;
        this.executarTransacoes = true;
        this.moverXMLsNaoEncSped = true;
        this.caminhosArquivosCSV = new ArrayList<>();
        this.itensPreAssociados = new HashMap<>();
        this.itensAssociadosManualmente = new HashMap<>();
    }   
    
    public AssociadorProdutoFornecedor() {
        this(true);
    }
    
    private void inicializarDAOs() {
        this.fornecDao = new PessoaDAO();
        this.empDao = new EmpresaQuestorDAO();
        this.prodDao = new ProdutoDAO();
        this.relProdForDao = new RelacProdutoFornecedorDAO();
        this.uniMedDao = new UnidadeMedidaDAO();
        this.fatorConvUniDao = new FatorConversaoUnidadeDAO();
    }
    
    public AssociadorProdutoFornecedor(String caminhoArquivoSpedFiscal) {
        this();
        this.setCaminhoArquivoSpedFiscal(caminhoArquivoSpedFiscal);
    }
    
    public AssociadorProdutoFornecedor(String caminhoArquivoSpeedFiscal, String caminhoDiretorioXmlsNFes) {
        this(caminhoArquivoSpeedFiscal);
        this.setCaminhoDiretorioXmlsNFes(caminhoDiretorioXmlsNFes);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters e Setters">
    public final String getCaminhoArquivoSpedFiscal() {
        return this.caminhoArquivoSpedFiscal;
    }
    
    public final void setCaminhoArquivoSpedFiscal(String caminhoArquivoSpedFiscal) {
        this.caminhoArquivoSpedFiscal = caminhoArquivoSpedFiscal;
    }
    
    public final String getCaminhoDiretorioXmlsNFes() {
        return this.caminhoDiretorioXmlsNFes;
    }
    
    public final void setCaminhoDiretorioXmlsNFes(String caminhoDiretorioXmlsNFes) {
        this.caminhoDiretorioXmlsNFes = caminhoDiretorioXmlsNFes;
    }

    public List<String> getCaminhosXmlsNFes() {
        return caminhosXmlsNFes;
    }

    public void setCaminhosXmlsNFes(List<String> caminhosXmlsNFes) {
        this.caminhosXmlsNFes = caminhosXmlsNFes;
    }
    
    public List<String> getChavesNFesNaoEncontradas() {
        return chavesNFesNaoEncontradas;
    }

    public Map<NFe, List<SPEDC170>> getListaSPEDC170NaoEncontrados() {
        return listaSPEDC170NaoEncontrados;
    }

    public Map<NFe, List<NFeItem>> getListaNFeItemNaoEncontrados() {
        return listaNFeItemNaoEncontrados;
    }

    public int getQtdFonecCad() {
        return qtdFornecCad;
    }

    public int getQtdProdCad() {
        return qtdProdCad;
    }

    public int getQtdUnidMedCad() {
        return qtdUnidMedCad;
    }

    public int getQtdFatorCad() {
        return qtdFatorCad;
    }

    public int getQtdRelProdFornecCad() {
        return qtdRelProdFornecCad;
    }

    public String getResumoProcessamento() {
        return resumoProcessamento;
    }

    public void setAssumirItensEmOrdem(boolean assumirItensEmOrdem) {
        this.assumirItensEmOrdem = assumirItensEmOrdem;
    }

    public void setAnalisarOrdemItensAuto(boolean analisarOrdemItensAuto) {
        this.analisarOrdemItensAuto = analisarOrdemItensAuto;
    }   
    
    public LeitorSpedFiscal getLeitorSpedFiscal() {
        return this.leitorSpedFiscal;
    }   

    public boolean isExecutarTransacoes() {
        return executarTransacoes;
    }

    public void setExecutarTransacoes(boolean executarTransacoes) {
        this.executarTransacoes = executarTransacoes;
    }

    public boolean isMoverXMLsNaoEncSped() {
        return moverXMLsNaoEncSped;
    }

    public void setMoverXMLsNaoEncSped(boolean moverXMLsNaoEncSped) {
        this.moverXMLsNaoEncSped = moverXMLsNaoEncSped;
    }  

    public List<String> getCaminhosArquivosCSV() {
        return caminhosArquivosCSV;
    }

    public Map<SPEDC170, Map<NFeItem, Float>> getItensPreAssociados() {
        return itensPreAssociados;
    }

    public void setItensPreAssociados(Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociados) {
        this.itensPreAssociados = itensPreAssociados;
    }

    public Map<SPEDC170, NFeItem> getItensAssociadosManualmente() {
        return itensAssociadosManualmente;
    }

    public void setItensAssociadosManualmente(Map<SPEDC170, NFeItem> itensAssociadosManualmente) {
        this.itensAssociadosManualmente = itensAssociadosManualmente;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Públicos">
    public void processar() 
            throws ArquivoSpedVazioException, DadosSpedNaoCarregadosException, 
                   DadosNFeNaoCarregadosException, ErroAoCarregarDadosSpedException, 
                   ErroAoCarregarDadosNFeException, DAOException, PermissaoDiretorioException, 
                   EmpresaNaoEncontradaException {
        if (!StringUtil.isNullOrEmpty(this.caminhoDiretorioXmlsNFes) && this.caminhosXmlsNFes == null)
            FileUtil.testarPermissaoDiretorio(this.caminhoDiretorioXmlsNFes); 
        
        if (StringUtil.isNullOrEmpty(this.caminhoDiretorioXmlsNFes) && this.caminhosXmlsNFes == null)
            throw new DadosNFeNaoCarregadosException("Não foi informado nenhuma origem de arquivos XML. O processamento foi interrompido.");

        this.resumoProcessamento = "";
        this.qtdFornecCad = 0;
        this.qtdProdCad = 0;
        this.qtdUnidMedCad = 0;
        this.qtdFatorCad = 0;
        this.qtdRelProdFornecCad = 0;
        this.chavesNFesNaoEncontradas.clear();
        this.leitorSpedFiscal.setCaminhoArquivoSped(this.getCaminhoArquivoSpedFiscal());
        this.leitorSpedFiscal.lerArquivoSped();
        
        List<SPEDC100> listaSpedC100 = leitorSpedFiscal.getListaSPEDC100();
        Map<String, NFe> listaNFes = carregarNFes();
        StringBuilder sbCSVXmlEnc = new StringBuilder("Chave NFe\r\n");
        StringBuilder sbCSVXmlNEnc = new StringBuilder("Chave NFe\r\n");
        
        // Busca a empresa destinatária pelo CNPJ. A empresa destinatária (que emitiu o SPED) deve estar 
        // cadastrada no QUESTOR para que o processo siga em frente
        EmpresaQuestor emp = retornarEmpresa(this.leitorSpedFiscal.getSped0000().getCNPJ());
        
        // Caso a empresa não tenha sido encontrada, lançamos uma exceção explicando o motivo da interrupção do processamento
        if (emp == null) {            
            String msg = String.format("Empresa de CNPJ '%s' e nome '%s' não cadastrada no QUESTOR.", 
                                        this.leitorSpedFiscal.getSped0000().getCNPJ(), this.leitorSpedFiscal.getSped0000().getNomeEmpresarialEntidade());
            log.info(msg);
            throw new EmpresaNaoEncontradaException(msg);
        }
        
        try {       
            verificarSeItensEstaoEmOrdem(listaSpedC100, listaNFes);
            /* 
            o processamento é realizado apenas com notas de saida que representa as vendas do cliente
            a idéia do laço é percorrer o registro C100 do sped fiscal recuperando a XML referente ao campo CHV_NFE 
            para auditar os itens
            */
            for (SPEDC100 sc100 : listaSpedC100) {
                if (sc100.getTipoNotaFiscal() != ETipoNotaFiscal.Entrada) {
                    log.info(String.format("A nota fiscal '%s' é de SAÍDA. Indo para próxima Nota.", sc100.getChaveNFe()));
                    continue;
                }
                
                NFe nfe = listaNFes.get(sc100.getChaveNFe());// obtem a XML da nota fiscal referente a chave identificada no sped

                // Se nfe é null, quer dizer que o arquivo xml da nota fiscal não está no diretorio de XMLs escpecificado 'Não foi encontrada'
                if (nfe == null) {
                    String chaveNFe = sc100.getChaveNFe();
                    if (sc100.getChaveNFe().isEmpty() && sc100.getListaSPEDC170().size() > 0)
                        chaveNFe = sc100.getListaSPEDC170().get(0).getCodItem() + " - Sem Chave de NFe";
                    // Caso a nota NÃO tenha sido encontrada. Coloca a informação no log para montar o CSV posteriormente
                    sbCSVXmlNEnc.append(String.format("'%s\r\n", chaveNFe));
                    this.chavesNFesNaoEncontradas.add(sc100.getChaveNFe());
                    continue;
                }
                
                // Verifica se a serie da nota e do sped estão diferentes. Se sim, modifica a serie da Nota no SPED conforme a serie na NFe
                if (!StringUtil.isNullOrEmpty(sc100.getSerie()) && 
                    !StringUtil.isNullOrEmpty(nfe.getSerie()) && 
                    !sc100.getSerie().equals(nfe.getSerie())) {
                    String linha = leitorSpedFiscal.getConteudoArquivoSPED()[sc100.getNumLinhaSPED()];
                    String[] campos = linha.split("\\|");
                    campos[7] = nfe.getSerie();
                    linha = String.join("|", campos);                    
                    leitorSpedFiscal.getConteudoArquivoSPED()[sc100.getNumLinhaSPED()] = linha;
                }
                
                // Caso a nota tenha sido encontrada, coloca ela no log para montar o CSV posteriormente
                sbCSVXmlEnc.append(String.format("'%s\r\n", sc100.getChaveNFe()));    
                
                // Se a flag executar transacoes estiver ligada entao, tenta inserir dados no banco
               if (this.executarTransacoes)                 
                   executarTransacoes(emp, sc100, nfe);

                // Adiciona os itens não encontrados
                this.listaSPEDC170NaoEncontrados.put(nfe, this.itensSC170);
                this.listaNFeItemNaoEncontrados.put(nfe, this.itensNFe);               
            }           
        } catch(Exception ex) {                    
            log.error(String.format("Houve um erro no processamento. %s", ex.getMessage()), ex);
        }

        // Cria um pequeno relatório do que foi processado no Log
        StringBuilder sbLogResumo = new StringBuilder("\r\n");
        sbLogResumo.append(String.format("Qtd de Fornecedores Cadastrados: %d\r\n", this.qtdFornecCad));
        sbLogResumo.append(String.format("Qtd de Produtos Cadastrados: %d de %d\r\n", this.qtdProdCad, this.leitorSpedFiscal.getListaSPED0200().size()));
        sbLogResumo.append(String.format("Qtd de Unidades de Medidas Cadastradas: %d\r\n", this.qtdUnidMedCad));
        sbLogResumo.append(String.format("Qtd de Fatores de Conversões Cadastrados: %d\r\n", this.qtdFatorCad));
        sbLogResumo.append(String.format("Qtd de Relacionamentos ProdutoXFornecedor Cadastrados: %d\r\n", this.qtdRelProdFornecCad));
        sbLogResumo.append("\r\n");
        sbLogResumo.append(this.leitorXmlNFe.getReporte()).append("\r\n");
        this.resumoProcessamento = sbLogResumo.toString();
        log.info(this.resumoProcessamento);
        
        // Move os arquivos XML não encontrados no SPED
        if (this.moverXMLsNaoEncSped)
            moverArquivosXMLNaoEncontrados(leitorSpedFiscal.getMapSPEDC100());
        
        // Cria aqruivo CSV com as chaves das notas processadas
        String dtArquivo = new SimpleDateFormat("ddMMyyyyHHmmss").format(Calendar.getInstance().getTime());
        criarArquivoCSV(this.getCaminhoDiretorioXmlsNFes() + "\\XMLsNoSPEDEncontradosNoDir_" + dtArquivo + ".csv", sbCSVXmlEnc);
        criarArquivoCSV(this.getCaminhoDiretorioXmlsNFes() + "\\XMLsNoSPEDNaoEncontradosNoDir_" + dtArquivo + ".csv", sbCSVXmlNEnc);     
        
        criarArquivoSPEDModificado(this.getCaminhoArquivoSpedFiscal());
    }
    
    public void processarItensIdentificadosPeloUsuario() throws DAOException {
        this.mapItensSpdNFe.clear();
        this.mapItensSpdNFe.putAll(this.itensAssociadosManualmente);
        
        NFeItem nfei = this.itensAssociadosManualmente.get(this.itensAssociadosManualmente.keySet().toArray()[0]);
        NFe nfe = nfei.getNFe();
        EmpresaQuestor emp = retornarEmpresa(nfe.getDestinatario().getCNPJ());        
        
        this.cadastrarInformacoesSPEDNFe(emp, nfe);
    }    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Privados">
    private void executarTransacoes(EmpresaQuestor emp, SPEDC100 sc100, NFe nfe) {
        // Identifica os itens do SPED na NFe
        identificarItensSpdNFe(sc100, nfe);
        // Com os itens identificados, executa os cadastros necessários
        cadastrarInformacoesSPEDNFe(emp, nfe);
    }
    
    private void cadastrarInformacoesSPEDNFe(EmpresaQuestor emp, NFe nfe) {
        Pessoa fornec = addFornecedor(nfe);
        
        this.mapItensSpdNFe.forEach((sc170, nfeItem) -> {
            try {                            
                //QuestorHibernateUtil.beginTransaction();
                
                // Retorna unidade de medida. Caso não exista, insere a mesma na base
                UnidadeMedida unidMed = addUnidadeMedidaCasoNaoExistir(emp, nfeItem);
                // Retorna o produto. Caso não exista, insere o mesmo na base
                ProdutoQuestor prod = null;
                try {
                    prod = addProdutoSeNaoExistir(emp, sc170);
                } catch(Exception ex) {
                    String msg = String.format("Houve um erro ao tentar inserir o produto de ref '%s' e nome '%s' da empresa de codigo '%s'. Mensagem: %s", 
                                               sc170.getCodItem(), sc170.getSPED0200().getDescrItem(), emp.getCodigo(), ex.getMessage());
                    log.error(msg, ex);
                    throw new Exception(msg, ex);
                }
                if (prod == null) {
                    log.info(String.format("O produto %d - %s da nota fiscal '%s' não foi criado.", nfeItem.getNumeroItem(), nfeItem.getDescricaoProduto(), nfe.getChaveNFe()));                                
                    //QuestorHibernateUtil.commitCurrentTransaction();
                    return;
                }                            
                // Verifica se as quantidades do produto no SPED e na nota são iguais, caso negativo, adiciona um novo fator de conversão para o produto
                BigDecimal qtdSped = sc170.getQtd();
                if (qtdSped.compareTo(BigDecimal.ZERO) == 1 && 
                    qtdSped.compareTo(nfeItem.getQuantidade()) != 0 &&
                    nfeItem.getQuantidade().compareTo(BigDecimal.ZERO) == 1) {
                    BigDecimal fator = qtdSped.divide(nfeItem.getQuantidade(), 6, RoundingMode.CEILING);
                    addFatorConversaoUnidadeCasoNaoExistir(emp, prod, unidMed, fator);
                }
                // Verifica se a relação Produto X Fornecedor existe e caso não exista, cria a relação
                addRelacProdutoXFornecedorCasoNaoExistir(emp, fornec, prod, nfeItem.getCodigoProduto());
                
                //QuestorHibernateUtil.commitCurrentTransaction();
            } catch(Exception ex) {
                log.error("Houve um erro no processamento do Item '" + sc170.getNumItem() + "' da Nota '" + sc170.getSPEDC100().getChaveNFe() + "'. " + ex.getMessage(), ex);                
//                if (QuestorHibernateUtil.getCurrentTransaction().isActive()) {
//                    try {
//                        QuestorHibernateUtil.rollbackCurrentTransaction();
//                        log.info("REALIZANDO ROLLBACK DA TRANSAÇÃO DO PROCESSAMENTO DO ITEM '" + sc170.getNumItem() + "' DA NOTA '" + sc170.getSPEDC100().getChaveNFe() + "'.");
//                    } catch(Throwable ex2) {
//                        log.error("Houve erro ao tentar realizar o rollback da transação do processamento do Item '" + sc170.getNumItem() + "' da Nota '" + sc170.getSPEDC100().getChaveNFe() + "'. " + ex2.getMessage(), ex2);                    
//                    }
//                }                
            } finally {
                try {
//                    if (QuestorHibernateUtil.getCurrentSession().isOpen())
//                        QuestorHibernateUtil.getCurrentSession().close();
                } catch(Throwable ex) {
                    log.error("Houve um erro ao tentar fechar a sessão do Hibernate após o processamento do Item '" + sc170.getNumItem() + "' da Nota '" + sc170.getSPEDC100().getChaveNFe() + "'. " + ex.getMessage());
                }
            }
        });
    }
    
    // <editor-fold desc="Métodos de Identificação dos Itens no SPED e no Xml" defaultstate="collapsed">
    private void verificarSeItensEstaoEmOrdem(List<SPEDC100> listaSpedC100, Map<String, NFe> listaNFes) {
        if (this.analisarOrdemItensAuto) {
            // Verifica se os itens do SPED e da nota estão na mesma ordem através dos campos qtd e valor
            log.info("Verificando se os itens do SPED e das NFes estão na mesma ordem");
            this.assumirItensEmOrdem = true;
            mainLoop:
            for (SPEDC100 sc100 : listaSpedC100) {
                if (sc100.getTipoNotaFiscal() != ETipoNotaFiscal.Entrada)
                    continue;

                NFe nfe = listaNFes.get(sc100.getChaveNFe());
                if (nfe == null) continue;
                for (SPEDC170 sc170 : sc100.getListaSPEDC170()) {
                    Optional<NFeItem> optNfeItem = nfe.getItens().stream().filter(nfItem -> nfItem.getSequencia() == sc170.getSequencia()).findFirst();
                    if (optNfeItem.isPresent()) {
                        NFeItem nfeItem = optNfeItem.get();
                        if (sc170.getQtd().compareTo(nfeItem.getQuantidade()) != 0 || 
                            sc170.getValor().compareTo(nfeItem.getValorProduto()) != 0) {
                            this.assumirItensEmOrdem =  false;
                            break mainLoop;
                        }
                    }
                }
            }
            log.info(String.format("Assumindo que os itens do SPED e das NFes %s ESTÃO na mesma ordem", (!this.assumirItensEmOrdem ? "NÃO" : "" )));
        }
    }
    
    private void identificarItensSpdNFe(SPEDC100 sc100, NFe nfe) {        
        this.mapItensSpdNFe.clear();
        
        if (sc100 == null || nfe == null)
            return;
        
        // Se existe apenas um item no sped e na nota, assumimos que é o mesmo
        if (sc100.getListaSPEDC170().size() == 1 && nfe.getItens().size() == 1) {
            this.mapItensSpdNFe.put(sc100.getListaSPEDC170().get(0), nfe.getItens().get(0));
            return;
        }
        
        // Inicia as listas de trabalho
        this.itensSC170.clear();
        this.itensSC170.addAll(sc100.getListaSPEDC170());
        this.itensNFe.clear();
        this.itensNFe.addAll(nfe.getItens());
        
        // Se a flag assumirItensEmOrdem for true, ignora os passos de validação e considera que os itens do SPED
        // e do XML estão na mesma ordem
        if (this.assumirItensEmOrdem) {            
            this.itensSC170.forEach(spd -> {
                filtrarItensNFe(spd, n -> n.getSequencia() == spd.getSequencia());
            });            
        }
        if (todosItensMapeados()) return;
        
        // Busca itens com o mesmo EAN
        this.itensSC170.stream().filter((sc170) -> !(sc170.getSPED0200().getCodBarra().isEmpty())).forEach((sc170) -> {
            filtrarItensNFe(sc170, (n -> n.getCodigoEAN().equals(sc170.getSPED0200().getCodBarra())));
        });
        if (todosItensMapeados()) return;
        
        // Busca itens com a mesma descrição
        this.itensSC170.stream().filter((sc170) -> !(sc170.getSPED0200().getDescrItem().isEmpty())).forEach((sc170) -> {            
            filtrarItensNFe(sc170, (n -> n.getDescricaoProduto().equals(sc170.getSPED0200().getDescrItem())));
        });
        if (todosItensMapeados()) return;
        
        // Verifica se o valor dos produtos são únicos
        this.itensSC170.stream().forEach((sc170) -> {
            if (this.itensNFe.stream().filter(n -> n.getValorProduto().compareTo(sc170.getValor()) == 0).count() == 1)
                filtrarItensNFe(sc170, (nf -> nf.getValorProduto().compareTo(sc170.getValor()) == 0));
        });
        if (todosItensMapeados()) return;
        
        // Verifica se a ordem dos itens no SPEED e no XML são iguais, para isso precisamos ter encontrado
        // no mínimo 3 itens nos testes anteriores
        if (this.mapItensSpdNFe.size() >= 3) {            
            long qtdItensOrdem = this.mapItensSpdNFe.entrySet().stream()
                                                    .filter(e -> e.getKey().getSequencia() == e.getValue().getSequencia())
                                                    .count();
            // Faz uma validação para ver se os valores e quantidade batem em todas as posições
            boolean ordemValidada = validarOrdem(sc100, nfe);
            
            // Se os itens ja mapeados estão em ordem, assumimos que os itens estão em ordem no SPED e na NFe
            if (qtdItensOrdem == this.mapItensSpdNFe.size() && ordemValidada) {
                this.itensSC170.forEach(spd -> {
                    filtrarItensNFe(spd, n -> n.getSequencia() == spd.getSequencia());
                });
            }
        }
        if (todosItensMapeados()) return;
        
        // Se ainda sobrou itens, testamos a descrição aproximada
        this.itensSC170.stream().forEach(spd -> {     
            try {
                final Map<NFeItem, Float> provaveis = new HashMap<>();

                // Filtra os itens por aproximação de nomes e seleciona os que tiveram uma relevancia maior que 40%
                this.itensNFe.parallelStream().forEach(n -> {
                    float perc = compararDescricaoAproximada(n.getDescricaoProduto(), spd.getSPED0200().getDescrItem());
                    if (perc > 0.0F)
                        provaveis.put(n, perc);
                });

                NFeItem nfeiEscolhida = null;            

                // Caso so tenha retornado um item no filtro anterior, assumimos que é o item que estavamos procurando
                if (provaveis.size() == 1)
                    nfeiEscolhida = (NFeItem)provaveis.keySet().toArray()[0];

                // Caso tenha encontrado mais de uma possibilidade, novas analises serão feitas
                if (nfeiEscolhida == null) {
                    // No universo de possiveis possibilidades, pegamos todos com o mesmo valor do item Sped em questão
                    Map<NFeItem, Float> qtdMesmoValor = provaveis
                                                        .entrySet()
                                                        .stream()
                                                        .filter(e -> e.getKey().getValorProduto().compareTo(spd.getValor()) == 0)
                                                        .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
                    Map<NFeItem, Float> qtdMesmaQtd = null;

                    // Caso tenha encontrado so 1, assumimos que encontramos
                    if (qtdMesmoValor.size() == 1) {
                       nfeiEscolhida = (NFeItem)qtdMesmoValor.keySet().toArray()[0];
                    } else { // Caso não tenha encontrado nenhum item ou mais de 1, filtramos os que tem a mesma Quantidade do item no Sped
                        qtdMesmaQtd = qtdMesmoValor
                                        .entrySet()
                                        .stream()
                                        .filter(e -> ((spd.getQtd().compareTo(BigDecimal.ZERO) == 1 && 
                                                       spd.getQtd().compareTo(e.getKey().getQuantidade()) == 1 &&
                                                       spd.getQtd().divide( spd.getQtd().divide(e.getKey().getQuantidade()), 6, RoundingMode.CEILING ).compareTo(e.getKey().getQuantidade()) == 0) ||
                                                      e.getKey().getQuantidade().compareTo(spd.getQtd()) == 0))
                                        .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
                        // A mesma ideia de antes
                        if (qtdMesmaQtd.size() == 1) {
                            nfeiEscolhida = (NFeItem)qtdMesmaQtd.keySet().toArray()[0];
                        }
                    } 

                    // Caso não tenha encontrado nada ainda, faremos uma escolha por maior % no menos universo
                    if (nfeiEscolhida == null) {
                        Map<NFeItem, Float> subprovaveis = null;
                        // Pegamos o menor subgrupo ja filtrado ou o grupo original
                        if (qtdMesmoValor != null && qtdMesmoValor.size() > 0) subprovaveis = qtdMesmoValor;
                        else if (qtdMesmaQtd != null && qtdMesmaQtd.size() > 0) subprovaveis = qtdMesmaQtd;
                        else subprovaveis = provaveis;

                        if (subprovaveis != null) {
                            // Pega o maior percentual
                            final Comparator<Entry<NFeItem, Float>> comp = (e1, e2) -> Float.compare(e1.getValue(), e2.getValue());
                            Optional<Entry<NFeItem, Float>> optMaxPerc = subprovaveis.entrySet().stream().max(comp);                        
                            if (optMaxPerc.isPresent()) {
                                Entry<NFeItem, Float> maxPerc = optMaxPerc.get();
                                // Verifica se existem itens com o mesmo percentual maximo.
                                long qtdMaxPerc = subprovaveis.entrySet().stream().filter(e -> e.getValue().equals(maxPerc.getValue())).count();
                                // Se tem apenas um item com o percentual maximo, ele é o selecionado. Caso contrário, não sabemos ao certo qual é o item correto
                                if (qtdMaxPerc == 1)
                                    nfeiEscolhida = maxPerc.getKey();
                            }
                        }
                    }
                }
                
                // Adiciona itens para que sejam escolhidos manualmente
                this.itensPreAssociados.put(spd, provaveis);
            } catch(Exception ex) {
                log.error(String.format("Houve um erro na comparação por Aproximação de Nomes. O Item que estava sendo analisado era '%s'. Mensagem: %s", 
                                        spd.getSPED0200().getDescrItem()), ex.getMessage(), ex);
            }
            System.gc();
        });  
        todosItensMapeados();        
    }
    
    private void filtrarItensNFe(SPEDC170 sc170, Predicate<NFeItem> predicate) {
        Optional<NFeItem> optNfeItem = this.itensNFe.parallelStream().filter(predicate).findFirst();
        if (optNfeItem.isPresent()) {
            NFeItem nfeItem = optNfeItem.orElse(null);                
            this.mapItensSpdNFe.put(sc170, nfeItem);
            this.itensNFe.remove(nfeItem);
        }
    }
    
    private boolean todosItensMapeados() {
        this.itensSC170.removeAll(this.mapItensSpdNFe.keySet());
        return this.itensNFe.isEmpty();
    }
    
    private boolean validarOrdem(SPEDC100 sc100, NFe nfe) {
        for (int i = 0; i < sc100.getListaSPEDC170().size(); i++) {
            SPEDC170 spd = sc100.getListaSPEDC170().get(i);
            NFeItem nfi = nfe.getItens().get(i);
            if (spd.getQtd().compareTo(nfi.getQuantidade()) != 0 ||
                spd.getValor().compareTo(nfi.getValorProduto()) != 0)
                return false;           
        }
        return true;
    }
        
    private float compararDescricaoAproximada(String nfeItemDesc, String spedItemDesc) {
        String[] nfeItemPalavras = nfeItemDesc.replace(".", " ").replace("-", " ").split(" ");
        String[] spdItemPalavras = spedItemDesc.replace(".", " ").replace("-", " ").split(" ");
        float palavrasEncontradas = 0;
        
        if (nfeItemPalavras.length == 0 || spdItemPalavras.length == 0)
            return 0.0F;
        
        for (String pSpd : spdItemPalavras) {
            if (StringUtil.isNullOrEmpty(pSpd) || pSpd.length() <= 2)
                continue;
                
            for (String pNfe : nfeItemPalavras) {
                if (StringUtil.isNullOrEmpty(pNfe) || pNfe.length() <= 2)
                    continue;
                
                if (pSpd.toLowerCase().contains(pNfe.toLowerCase()) || pNfe.toLowerCase().contains(pSpd.toLowerCase()))
                    palavrasEncontradas++;
            }
        }
        
        return (palavrasEncontradas / (float)(spdItemPalavras.length + nfeItemPalavras.length));
    }
    
    private boolean compararDescricaoAproximada(NFeItem nfeItem, SPEDC170 itensSped) {
        return compararDescricaoAproximada(nfeItem.getDescricaoProduto(), itensSped.getSPED0200().getDescrItem()) >= 0.4F;
    }
    // </editor-fold>
    
    // <editor-fold desc="Métodos de Manipulação de arquivos" defaultstate="collapsedstate">
    private void moverArquivosXMLNaoEncontrados(Map<String, SPEDC100> mapSPEDC100) throws PermissaoDiretorioException {
        File dirXmls = new File(this.caminhoDiretorioXmlsNFes);
        File dirXmlsNaoEnc = new File(String.format("%s%s", dirXmls.getPath(), File.separator + "XMLsNaoEncontradosNoSPED"));        
        StringBuilder sbCsv = new StringBuilder();   
        LeitorXmlNFe leitorXmlNfe = new LeitorXmlNFe();
        NFe nfe = null;        
        
        dirXmlsNaoEnc.mkdir();        
        for (File fXml : dirXmls.listFiles()) {
            if (fXml.isDirectory() || !fXml.getName().contains(".xml"))
                continue;
            
            try {
                nfe = leitorXmlNfe.lerXmlNFe(fXml.getAbsolutePath());
            } catch(Exception ex) {}            
            
            if (nfe == null)
                continue;
            
            if (!mapSPEDC100.containsKey(nfe.getChaveNFe())) {
                fXml.renameTo(new File(String.format("%s%s%s", dirXmlsNaoEnc, File.separator, fXml.getName())));
                sbCsv.append(String.format("'%s;%s;'%s;%s;%s;%s\r\n", 
                            nfe.getChaveNFe(), fXml.getName(), nfe.getNumeroNotaFiscal(), 
                            nfe.getEmitente().getNomeFantasia(), nfe.getDataHoraEmissao(), nfe.getValorNotaFiscal()));
            }
        }
        
        // Cria um arquivo CSV com as chaves e nomes dos arquivos
        if (sbCsv.length() > 0)
            sbCsv.insert(0, "Chave NFe;Nome do Arquivo;Numero da NF;Fornecedor;Data Emissão;Valor da NF\r\n");
        String dtArquivo = new SimpleDateFormat("ddMMyyyyHHmmss").format(Calendar.getInstance().getTime());
        criarArquivoCSV(String.format("%s%s", dirXmlsNaoEnc.getPath(), File.separator + "XMLsNaoEncontradosNoSPED_" + dtArquivo + ".csv"), sbCsv);        
    }
    
    private void criarArquivoCSV(String path, StringBuilder sbCsv) throws PermissaoDiretorioException {        
        if (sbCsv.length() > 0) {
            this.caminhosArquivosCSV.add(path);
            FileUtil.criarArquivo(path, sbCsv);
        }
    }
    
    private void criarArquivoSPEDModificado(String path) throws PermissaoDiretorioException  {        
        StringBuilder sbConteudo = new StringBuilder();
        
        for (String linha : this.leitorSpedFiscal.getConteudoArquivoSPED())
            sbConteudo.append(String.format("%s\n", linha));
        
        String pathNovo = path.substring(0, path.lastIndexOf(File.separator));
        FileUtil.criarArquivo(pathNovo + "\\SPED_Modificado.txt", sbConteudo);
    }      
    // </editor-fold>
    
    // <editor-fold desc="Funções de adição de dados no banco" defaultstate="collapsed">
    private Pessoa addFornecedor(NFe nfe) {
        Pessoa fornec = null;
        try {
            //QuestorHibernateUtil.beginTransaction();            
            fornec = addFornecSeNaoExistir(nfe); // Retorna o fornecedor e caso não exista, insere o mesmo na base
            //QuestorHibernateUtil.commitCurrentTransaction();
        } catch(Throwable ex) {
            log.error("Houve um erro ao tentar cadastrar/atualizar o Fornecedor '" + nfe.getEmitente().getRazaoSocial() + "'. Mensagem: " + ex.getMessage(), ex);            
//            if (QuestorHibernateUtil.getCurrentTransaction().isActive()) {
//                try {
//                    QuestorHibernateUtil.rollbackCurrentTransaction();
//                    log.info(String.format("REALIZANDO ROLLBACK DA TRANSAÇÃO DE CADASTRO/ATUALIZAÇÃO DO FORNECEDOR '%s'.", nfe.getEmitente().getRazaoSocial()));
//                } catch(Throwable ex2) {
//                    log.error("Houve erro ao tentar realizar o rollback da transação de cadastro/atualização do Fornecedor '" + nfe.getEmitente().getRazaoSocial() + "'. " + ex2.getMessage(), ex2);
//                }                    
//            }            
        } finally {
            try {
//                if (QuestorHibernateUtil.getCurrentSession().isOpen()) {
//                    QuestorHibernateUtil.getCurrentSession().close();                    
//                }
            } catch(Throwable ex) {
                log.error(String.format("Houve um erro ao tentar fechar a sessão do Hibernate após a tentativa de cadatrar/atualizar o Fornecedor '" + nfe.getEmitente().getRazaoSocial() + "'. Mensagem: %s", ex.getMessage()));
            }
        }
        return fornec;
    }
    
    private Pessoa addFornecSeNaoExistir(NFe nfe) throws DAOException {
        Pessoa filtro = new Pessoa();
        filtro.setInscrFederal(nfe.getEmitente().getCNPJ());        
        Pessoa fornec = this.fornecDao.selecionarUm(filtro);
        String inscrFederal = "";
        PessoaJuridica pj = nfe.getEmitente();
        Municipio muni = addMunicipioCasoNaoExistir(pj);

        if (Masc.isCNPJ(pj.getCNPJ())) inscrFederal = Masc.mascararCNPJ(pj.getCNPJ());
        else if (Masc.isCPF(pj.getCNPJ())) inscrFederal = Masc.mascararCPF(pj.getCNPJ());

        if (fornec == null || fornec.getCodigo() == null || fornec.getCodigo().equals(0))
            fornec = new Pessoa();
        
        fornec.setNome(pj.getRazaoSocial().length() > 50 ? pj.getRazaoSocial().substring(0, 50) : pj.getRazaoSocial());                    
        fornec.setTipoInscr(2);
        fornec.setInscrFederal(inscrFederal);
        fornec.setEndereco(pj.getLogrdouro().length() > 40 ? pj.getLogrdouro().substring(0, 40) : pj.getLogrdouro());
        fornec.setNumEndereco(pj.getNumero());
        fornec.setBairro(pj.getBairro().length() > 30 ? pj.getBairro().substring(0, 30) : pj.getBairro());
        fornec.setCodigoMunicipio(muni.getCodigoMunicipio().shortValue());
        fornec.setSiglaEstado(pj.getSiglaUF());
        fornec.setCep(pj.getCep());
        fornec.setInscrEstad(pj.getIE());
        fornec.setInscrMunic(pj.getIM());
        fornec.setProdutoRural('0');
        fornec.setSequencia((short)0);
        
        if (fornec == null || fornec.getCodigo() == null || fornec.getCodigo().equals(0)) { 
            this.fornecDao.salvar(fornec);
            this.qtdFornecCad++;
            log.info(String.format("Incluiu o Fornecedor de codigo '%s' e nome '%s'", fornec.getCodigo().toString(), fornec.getNome()));
        } else {
            this.fornecDao.atualizar(fornec);
            log.info(String.format("Atualizou o Fornecedor de codigo '%s' e nome '%s'", fornec.getCodigo().toString(), fornec.getNome()));
        }
        
        return fornec;
    }
    
    private Municipio addMunicipioCasoNaoExistir(PessoaJuridica pj) throws DAOException {
        MunicipioDAO municipioDao = new MunicipioDAO();
        Municipio filtro = new Municipio();
        filtro.setCodigoRais(pj.getCodigoMunicipio());
        
        Municipio m = municipioDao.selecionarUm(filtro);
        if (m == null) {
            m = new Municipio();
            m.setSiglaEstado(pj.getSiglaUF());            
            m.setNome(pj.getNomeMunicipio());
            m.setCep(pj.getCep());
            m.setValorMinISSRetido(BigDecimal.ZERO);
            m.setTaxaRecISSQN(BigDecimal.ZERO);
            m.setCodigoRais(pj.getCodigoMunicipio());                        
            municipioDao.salvar(m);
            log.info(String.format("Adicionou o Municipio de codigo '%s' e nome '%s'", m.getCodigoMunicipio().toString(), m.getNome()));
        }
        
        return m;
    }
    
    private EmpresaQuestor retornarEmpresa(String cnpj) {
        EmpresaQuestor emp = null;
        try {
            //QuestorHibernateUtil.beginTransaction();            
            emp = retornarEmpresaPorCNPJ(cnpj); 
            //QuestorHibernateUtil.commitCurrentTransaction();
        } catch(DAOException ex) {
            log.error(String.format("Houve um erro de Banco de Dados ao tentar buscar a Empresa. Mensagem: %s", ex.getMessage()));
            try {
                //QuestorHibernateUtil.rollbackCurrentTransaction();
            } catch(Exception ex1) {}
        } finally {              
            try {
//                if (QuestorHibernateUtil.getCurrentSession().isOpen()) {
//                    QuestorHibernateUtil.getCurrentSession().close();
//                    log.info("A sessão do hibernate foi fechada apos buscar a Empresa emitente do SPED no banco.");
//                }
            } catch(Exception ex) {
                log.error(String.format("Houve um erro ao tentar fechar a sessão do Hibernate após buscar a Empresa emitente do SPED no banco. Mensagem: %s", ex.getMessage()));
            }
        }
        return emp;
    }
    
    private EmpresaQuestor retornarEmpresaPorCNPJ(String cnpjDest) throws DAOException {
        // Verifica se a empresa existe e pega o codigo da mesma        
        EmpresaQuestor filtroEmpresa = new EmpresaQuestor();
        filtroEmpresa.getEstabelecimento().setInscricaoFederal(cnpjDest);        
        return this.empDao.selecionarUm(filtroEmpresa);
    }
    
    private ProdutoQuestor addProdutoSeNaoExistir(EmpresaQuestor emp, SPEDC170 sc170) throws DAOException {        
        // Verifica se o produto existe para a empresa
        ProdutoQuestor filtroProduto = new ProdutoQuestor();
        filtroProduto.setCodigoEmpresa(emp.getCodigo()); 
        filtroProduto.setReferenProduto(sc170.getCodItem());
        ProdutoQuestor prod = this.prodDao.selecionarUm(filtroProduto);        
        
        if (prod == null) {            
            prod = new ProdutoQuestor();
            prod.setCodigoEmpresa(emp.getCodigo());
            prod.setReferenProduto(sc170.getCodItem());
            prod.setCodigoGrupoProduto((short)2);
            prod.setDescricao(sc170.getSPED0200().getDescrItem());
            prod.setUnidadeMedida(sc170.getUnid());
            prod.setIndicadorTipo((short)0);
            prod.setDataAtualizacao(Calendar.getInstance().getTime());
            prod.setDnf('0');
            prod.setDacon('1');
            prod.setCstEntrada(50);
            prod.setCstSaida(1);
            prod.setTipoCredito("4.3.07.01");
            prod.setTipoDebito("4.3.05.01");
            prod.setCodigoNcm(sc170.getSPED0200().getCodNCM());
            prod.setAliqIcms(sc170.getAliqICMS());
            this.prodDao.salvar(prod);
            this.qtdProdCad++;
            log.info(String.format("Adicionou o Produto de codigo '%s' e nome '%s'", prod.getCodigoProduto().toString(), prod.getDescricao()));
        }
        
        return prod;
    }
    
    private RelacProdutoFornecedor addRelacProdutoXFornecedorCasoNaoExistir(EmpresaQuestor emp, Pessoa fornec, ProdutoQuestor prod, String cProd) throws DAOException {        
        RelacProdutoFornecedor filtro = new RelacProdutoFornecedor();
        filtro.setCodigoEmpresa(emp.getCodigo());
        filtro.setCodigoEstab(emp.getEstabelecimento().getCodigoEstabelecimento());
        filtro.setCodigoPessoa(fornec.getCodigo());
        filtro.setReferItemFornecedor(cProd);
        
        RelacProdutoFornecedor relProdFor = this.relProdForDao.selecionarUm(filtro);        
        if (relProdFor == null) {
            relProdFor = new RelacProdutoFornecedor();
            relProdFor.setCodigoEmpresa(emp.getCodigo());
            relProdFor.setCodigoEstab(emp.getEstabelecimento().getCodigoEstabelecimento());
            relProdFor.setCodigoPessoa(fornec.getCodigo());
            relProdFor.setCodigoProduto(prod.getCodigoProduto());
            relProdFor.setReferItemFornecedor(cProd);
            this.relProdForDao.salvar(relProdFor);
            this.qtdRelProdFornecCad++;
            log.info(String.format("Adicionou a relação de Produto X Fornecedor com os codigos: empresa = %s(%s), estab = %s, fornecedor = %s(%s), produto = %s(%s), referitemfornec = %s", 
                                    emp.getNome(), emp.getCodigo().toString(), emp.getEstabelecimento().getCodigoEstabelecimento().toString(), 
                                    fornec.getNome(), fornec.getCodigo().toString(), 
                                    prod.getDescricao(), prod.getCodigoProduto().toString(), cProd));
        }
        
        return relProdFor;
    }
    
    private UnidadeMedida addUnidadeMedidaCasoNaoExistir(EmpresaQuestor emp, NFeItem nfeItem) throws DAOException {
        UnidadeMedida filtro = new UnidadeMedida();
        filtro.setCodigoEmpresa(emp.getCodigo());
        filtro.setCodigoUnidadeMedida(nfeItem.getUnidade());
        
        UnidadeMedida um = this.uniMedDao.selecionarUm(filtro);
        if (um == null) {
            um = new UnidadeMedida();
            um.setCodigoEmpresa(emp.getCodigo());
            um.setCodigoUnidadeMedida(nfeItem.getUnidade());
            um.setNome(nfeItem.getUnidade());
            this.uniMedDao.salvar(um);
            this.qtdUnidMedCad++;
            log.info(String.format("Adicionou Unidade de Medida com os dados: codigoempresa = %s, unidademed = '%s', nome = '%s'",
                     emp.getCodigo().toString(), nfeItem.getUnidade(), nfeItem.getUnidade()));
        }
        
        return um;
    }
    
    private FatorConversaoUnidade addFatorConversaoUnidadeCasoNaoExistir(EmpresaQuestor emp, ProdutoQuestor prod, UnidadeMedida unidMed, BigDecimal fator) throws DAOException {
        FatorConversaoUnidade filtro = new FatorConversaoUnidade();
        filtro.setCodigoEmpresa(emp.getCodigo());
        filtro.setCodigoProduto(prod.getCodigoProduto());
        filtro.setCodigoUnidadeMedida(unidMed.getCodigoUnidadeMedida());
        
        FatorConversaoUnidade fcu = this.fatorConvUniDao.selecionarUm(filtro);
        if (fcu == null && fator != null) {
            fcu = new FatorConversaoUnidade();
            fcu.setCodigoEmpresa(emp.getCodigo());
            fcu.setCodigoProduto(prod.getCodigoProduto());
            fcu.setCodigoUnidadeMedida(unidMed.getCodigoUnidadeMedida());
            fcu.setFator(fator);
            this.fatorConvUniDao.salvar(fcu);
            this.qtdFatorCad++;
            log.info(String.format("Adicionou Fator de Conversão com os dados: codigoempresa = %s, codigoproduto = %s, unidademedida = '%s', fator = %s",
                     emp.getCodigo().toString(), prod.getCodigoProduto(), unidMed.getCodigoUnidadeMedida(), fcu.getFator().toString()));
        }
        
        return fcu;
    }
    // </editor-fold>
    
    private Map<String, NFe> carregarNFes() throws ErroAoCarregarDadosNFeException, DadosNFeNaoCarregadosException {
        if (!StringUtil.isNullOrEmpty(this.caminhoDiretorioXmlsNFes))
            return this.leitorXmlNFe.lerXmlsNFes(caminhoDiretorioXmlsNFes);
        
        if (this.caminhosXmlsNFes != null && this.caminhosXmlsNFes.size() > 0) {
            Map<String, NFe> mapNFe = new HashMap<>();
            this.caminhosXmlsNFes.stream().forEach(s -> {
                try {
                    NFe nfe = this.leitorXmlNFe.lerXmlNFe(s);
                    mapNFe.put(nfe.getChaveNFe(), nfe);
                } catch(Exception ex) {
                    log.error(String.format("Não foi possivel carregar o xml no caminho '%s'. Mensagem: %s", s, ex.getMessage()));
                }
            });
            String caminho = this.caminhosXmlsNFes.get(0);
            this.caminhoDiretorioXmlsNFes = caminho.substring(0, caminho.lastIndexOf(File.separator));
            return mapNFe;
        }            
           
        throw new DadosNFeNaoCarregadosException("Nenhum arquivo de NFe foi carregado");
    }
    // </editor-fold>
}
