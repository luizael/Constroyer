package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.dao.own.EmpresaDAO;
import br.com.mixfiscal.prodspedxnfe.dao.own.FornecedorDAO;
import br.com.mixfiscal.prodspedxnfe.dao.own.ProdutoDAO;
import br.com.mixfiscal.prodspedxnfe.dao.own.RelacaoProdutoFornecedorDAO;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoEmissao;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFe;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFeItem;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.TotalImposto;
import br.com.mixfiscal.prodspedxnfe.domain.own.Empresa;
import br.com.mixfiscal.prodspedxnfe.domain.own.Fornecedor;
import br.com.mixfiscal.prodspedxnfe.domain.own.Produto;
import br.com.mixfiscal.prodspedxnfe.domain.own.RelacaoProdutoFornecedor;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoNotaFiscal;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.IPI;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFItem;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPED0000;
import java.util.List;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC100;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC150;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC170;
import br.com.mixfiscal.prodspedxnfe.ex.PermissaoDiretorioException;
import br.com.mixfiscal.prodspedxnfe.services.ex.ArquivoSpedVazioException;
import br.com.mixfiscal.prodspedxnfe.services.ex.DadosNFeNaoCarregadosException;
import br.com.mixfiscal.prodspedxnfe.services.ex.DadosSpedNaoCarregadosException;
import br.com.mixfiscal.prodspedxnfe.services.ex.ErroAoCarregarDadosNFeException;
import br.com.mixfiscal.prodspedxnfe.services.ex.ErroAoCarregarDadosSpedException;
import br.com.mixfiscal.prodspedxnfe.util.FileUtil;
import br.com.mixfiscal.prodspedxnfe.util.Masc;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProcessadorSPED {
    
    private final Logger log;
    private final LeitorXmlNFe leitorXmlNFe;
    private final LeitorSpedFiscal leitorSped;
    private final Map<SPEDC170, NFeItem[]> mapItensSpdNFe; // Liga os itens do sped com os da nota fiscal
    private final List<SPEDC170> itensSC170; // Lista de SPEDC170 utilizada para realização da pré-alálise
    private final List<NFeItem> itensNFe; // Lista de NFeItem utilizada para realização da pré-alálise
    private final Set<SPEDC170> colecaoItensSC170; // Set de todos os produtos do SPED processados numa sessão de processamento
    private final Set<NFeItem> colecaoItensNFe; // Set de todos os produtos da Nota fiscal processados numa sessao de processamento
    private boolean assumirItensEmOrdem; // Se for true, assume que os itens do SPED e dos XMLs estão na mesma ordem e ignora todas as outras verificações
    private Empresa empresaAtual; // Representa a empresa atual do processamento. Ela é a emissora do SPED sendo processado. Ela é retornada no método processar()
    private final StringBuilder sbCSVXmlEnc;
    private final StringBuilder sbCSVXmlNEnc;
    private final StringBuilder sbRelModifSPED; // Relatório de modificações do SPED
    private final DecimalFormat df = new DecimalFormat("##0.##");
    private final DecimalFormatSymbols dfs = new DecimalFormatSymbols();
    private EmpresaDAO empDao;    
    private FornecedorDAO fornecDao;
    private ProdutoDAO prodDao;
    private RelacaoProdutoFornecedorDAO relProdFornecDao;
    private final BigDecimal valorTotalItens = BigDecimal.ZERO;
    private boolean relMultiplo;
    private boolean vincular;
    private BigDecimal totalNfeItem;
    private Map<Map<Integer, SPEDC170>,SPEDC170> relaGravacaoeSped;
    private Map<SPEDC170, NFeItem> descricaoAproximada;
    private Map<SPEDC170, NFeItem> descricaoAproximadaTemp;
    private MetodosUtil util;
    private int contadorItemAssociados;
    private int contadorXmlProcessadas;
    private int contadorRelacionamentoExistente;
    private int contadorMesmaOrdem;
    private int contadorMesmoEan;
    private int contadorMesmoEanTrib;
    private int contadorUmParaUm;
    private int contadorUmParaMuitos;
    private int contadorDescricaoExata;
    private int contadorDescricaoAproximadaValorIdentico;
    private int contadorDescricaoAproximadaValorIdenticoComImposto;
    private SPED0000 Sped0000; 
    private List<SPEDC100> listaC100;
    private List<SPED0000> listaTeste0000;
    private Map<SPEDC170, NFeItem[]> mapDeItensVinculadosAuto;
    //membros privados para testes de debug solicitados pelo gabriel
    private int oldLinha;  
    // </editor-fold>

    // <editor-fold desc="Membros Privados Getters e Setters" defaultstate="collapsed">
    private String caminhoArquivoSPED;
    private String caminhoDiretorioXmlsNFes;
    private Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociados; // Map de itens para a realização de associação manual por parte do usuário. Esse Map é gerado pela logica de aproximação de nomes
    private Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociadosTemp; 
    private Map<SPEDC170, NFeItem[]> itensAssociadosManualmente; // Itens associados pelo usuário
    private int qtdEmpresaCad; // Contador de Empresas Cadastradas
    private int qtdFornecCad; // Contador de Fornecedores Cadastrados
    private int qtdProdCad; // Contador de Produtos Cadastrados    
    private int qtdRelProdFornecCad; // Contador de Relacionamento ProdXFornecedores cadastrados    
    private String resumoProcessamento; // Texto com o resumo do processamento realizado
    private final List<String> chavesNFesNaoEncontradas;
    private int contadorProgresso;
    private String descricaoProgresso;
    private Map<String, List<NFItem>> itensToLazyLoadIndex = new HashMap<String, List<NFItem>>();
    // </editor-fold>
  
    public ProcessadorSPED() {
        this.log = LogManager.getLogger(ProcessadorSPED.class);
        this.leitorXmlNFe = new LeitorXmlNFe();
    	this.leitorSped = new LeitorSpedFiscal(); 
        this.mapItensSpdNFe = new HashMap<>();      
        this.itensSC170 = new ArrayList<>();
        this.itensNFe = new ArrayList<>();
        this.colecaoItensSC170 = new HashSet<>();
        this.colecaoItensNFe = new HashSet<>();
        this.itensPreAssociados = new HashMap<>();
        this.itensPreAssociadosTemp = new HashMap<>();
        this.itensAssociadosManualmente = new HashMap<>();
        this.chavesNFesNaoEncontradas = new ArrayList<>();
        this.sbCSVXmlEnc = new StringBuilder();
        this.sbCSVXmlNEnc = new StringBuilder();
        this.sbRelModifSPED = new StringBuilder("Linha;Coluna;Valor Antigo;Valor Novo;Chave SPED;Chave XML; Descricao SPED; Descricao XML; Valor SPED; Valor XML; Qtd SPED; Qtd XML; Filtro;\r\n");
        this.dfs.setDecimalSeparator(',');        
        this.df.setDecimalFormatSymbols(dfs);    
        this.itensToLazyLoadIndex = new HashMap<>();  
        this.totalNfeItem = BigDecimal.ZERO;
        this.relaGravacaoeSped = new HashMap<>();
        this.util = new MetodosUtil();
        //mebros privados para testes solicitados pelo gabriel
        this.oldLinha = 0;
        this.contadorItemAssociados = 0;
        this.contadorXmlProcessadas = 0;
        this.contadorRelacionamentoExistente = 0;
        this.contadorMesmaOrdem = 0;
        this.contadorMesmoEan = 0;
        this.contadorMesmoEanTrib = 0;
        this.contadorUmParaUm = 0;
        this.contadorUmParaMuitos = 0;
        this.contadorDescricaoExata = 0;
        this.contadorDescricaoAproximadaValorIdentico = 0;
        this.contadorDescricaoAproximadaValorIdenticoComImposto = 0;
        this.Sped0000 = new SPED0000();
        this.listaC100 = new ArrayList();
        listaTeste0000 = new ArrayList();
        this.mapDeItensVinculadosAuto = new HashMap();
    }

        /**
     * @return the Sped0000
     */
    public SPED0000 getSped0000() {
        return Sped0000;
    }

    /**
     * @param Sped0000 the Sped0000 to set
     */
    public void setSped0000(SPED0000 Sped0000) {
        this.Sped0000 = Sped0000;
    }
   /**
     * @return the listaC100
     */
    public List<SPEDC100> getListaC100() {
        return listaC100;
    }

    /**
     * @param listaC100 the listaC100 to set
     */
    public void setListaC100(List<SPEDC100> listaC100) {
        this.listaC100 = listaC100;
    }


   /**
     * @return the listaTeste0000
     */
    public List<SPED0000> getListaTeste0000() {
        return listaTeste0000;
    }

    /**
     * @param listaTeste0000 the listaTeste0000 to set
     */
    public void setListaTeste0000(List<SPED0000> listaTeste0000) {
        this.listaTeste0000 = listaTeste0000;
    }
      /**
     * @return the mapDeItensVinculadosAuto
     */
    public Map<SPEDC170, NFeItem[]> getMapDeItensVinculadosAuto() {
        return mapDeItensVinculadosAuto;
    }

    /**
     * @param mapDeItensVinculadosAuto the mapDeItensVinculadosAuto to set
     */
    public void setMapDeItensVinculadosAuto(Map<SPEDC170, NFeItem[]> mapDeItensVinculadosAuto) {
        this.mapDeItensVinculadosAuto = mapDeItensVinculadosAuto;
    }

        /**
     * @return the mapItensSpdNFe
     */
    public Map<SPEDC170, NFeItem[]> getMapItensSpdNFe() {
        return mapItensSpdNFe;
    }
    public String getCaminhoArquivoSPED() {
        return caminhoArquivoSPED;
    }

    public void setCaminhoArquivoSPED(String caminhoArquivoSPED) {
        this.caminhoArquivoSPED = caminhoArquivoSPED;
    }

    public String getCaminhoDiretorioXmlsNFes() {
        return caminhoDiretorioXmlsNFes;
    }

    public void setCaminhoDiretorioXmlsNFes(String caminhoDiretorioXmlsNFes) {
        this.caminhoDiretorioXmlsNFes = caminhoDiretorioXmlsNFes;
    }

    public Map<SPEDC170, Map<NFeItem, Float>> getItensPreAssociados() {
        return itensPreAssociados;
    }

    public void setItensPreAssociados(Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociados) {
        this.itensPreAssociados = itensPreAssociados;
    }

    public Map<SPEDC170, NFeItem[]> getItensAssociadosManualmente() {
        return itensAssociadosManualmente;
    }

    public void setItensAssociadosManualmente(Map<SPEDC170, NFeItem[]> itensAssociadosManualmente) {
        this.itensAssociadosManualmente = itensAssociadosManualmente;
    }

    public LeitorXmlNFe getLeitorXmlNFe() {
        return leitorXmlNFe;
    }
    
    public LeitorSpedFiscal getLeitorSped() {
        return leitorSped;
    }

    public int getQtdEmpresaCad() {
        return qtdEmpresaCad;
    }
    
    public int getQtdFornecCad() {
        return qtdFornecCad;
    }

    public int getQtdProdCad() {
        return qtdProdCad;
    }

    public int getQtdRelProdFornecCad() {
        return qtdRelProdFornecCad;
    }

    public String getResumoProcessamento() {
        return resumoProcessamento;
    }

    public int getContadorProgresso() {
        return contadorProgresso;
    }

    public String getDescricaoProgresso() {
        return descricaoProgresso;
    }

    public Set<SPEDC170> getColecaoItensSC170() {
        return colecaoItensSC170;
    }

    public Set<NFeItem> getColecaoItensNFe() {
        return colecaoItensNFe;
    }

    public boolean isRelMultiplo() {
        return relMultiplo;
    }

    public void setRelMultiplo(boolean relMultiplo) {
        this.relMultiplo = relMultiplo;
    }

    public int getContadorItemAssociados() {
        return contadorItemAssociados;
    }

    public void setContadorItemAssociados(int contadorItemAssociados) {
        this.contadorItemAssociados = contadorItemAssociados;
    }
    
    public void processar() 
    		throws PermissaoDiretorioException, DadosNFeNaoCarregadosException, ArquivoSpedVazioException, 
                       ErroAoCarregarDadosSpedException, DadosSpedNaoCarregadosException, ErroAoCarregarDadosNFeException {  
    	if (!StringUtil.isNullOrEmpty(this.caminhoDiretorioXmlsNFes))
            FileUtil.testarPermissaoDiretorio(this.caminhoDiretorioXmlsNFes); 
        
        if (StringUtil.isNullOrEmpty(this.caminhoDiretorioXmlsNFes))
            throw new DadosNFeNaoCarregadosException("Não foi informado nenhuma origem de arquivos XML. O processamento foi interrompido.");
        
        inicializarDAOs();
        reiniciaContadores();
        this.chavesNFesNaoEncontradas.clear();
        this.setItensPreAssociados(new HashMap<>());
        this.itensToLazyLoadIndex = new HashMap<>();
        this.itensAssociadosManualmente = new HashMap<>();
        this.sbCSVXmlEnc.setLength(0);
        this.sbCSVXmlNEnc.setLength(0);
        this.descricaoProgresso = "Lendo os arquivos SPED e NFes";
        this.colecaoItensSC170.clear();
        this.colecaoItensNFe.clear();
        this.setRelMultiplo(false);
        
        this.leitorSped.lerArquivoSped(this.caminhoArquivoSPED);
        this.setSped0000(this.leitorSped.getSped0000());
        listaTeste0000.add(this.getSped0000());
        List<SPEDC100> listaSpedC100 = this.leitorSped.getListaSPEDC100();
        this.listaC100 = listaSpedC100;
        Map<String, NFe> listaNFes = carregarNFes();
        
        verificarSeItensEstaoEmOrdem(listaSpedC100, listaNFes);
        
        this.empresaAtual = addEmpresaSeNaoExistir(); // Retorna a empresa atual do processamento    
        this.descricaoProgresso = "Processando os registros C100 do SPED";        
        
        //listaSpedC100.stream().forEach((SPEDC100 sc100) -> {
        try{
            for(SPEDC100 sc100 : listaSpedC100){           
                if (sc100.getTipoEmissao() == ETipoEmissao.EmissaoPropria) {
                    log.info(String.format("A nota fiscal '%s' é de EMISSÃO PRÓPRIA. Indo para próxima Nota.", sc100.getChaveNFe()));
                    this.contadorProgresso++;
                    //return;
                    continue;
                }            

                if (sc100.getTipoNotaFiscal() == ETipoNotaFiscal.Saida) {
                    log.info(String.format("A nota fiscal '%s' é de SAÍDA. Indo para próxima Nota.", sc100.getChaveNFe()));
                    this.contadorProgresso++;
                    //return;
                    continue;
                } 

                NFe nfe = listaNFes.get(sc100.getChaveNFe());
                 
                if (nfe == null) { 
                    String chaveNFe = sc100.getChaveNFe();
                    if (sc100.getChaveNFe().isEmpty() && sc100.getListaSPEDC170().size() > 0)
                        chaveNFe = sc100.getListaSPEDC170().get(0).getCodItem() + " - Sem Chave de NFe";
                    // Caso a nota NÃO tenha sido encontrada. Coloca a informação no log para montar o CSV posteriormente
                    sbCSVXmlNEnc.append(String.format("'%s\r\n", chaveNFe));
                    this.chavesNFesNaoEncontradas.add(sc100.getChaveNFe());
                    this.contadorProgresso++;
                    //return;
                    log.info(String.format("A nota fiscal '%s'nao existe.", sc100.getChaveNFe()));               
                    continue;
                }            
                // Caso a nota tenha sido encontrada, coloca ela no log para montar o CSV posteriormente
                sbCSVXmlEnc.append(String.format("'%s\r\n", sc100.getChaveNFe()));   
                // aplicação de filtros
                System.out.println("---------------------------------------INICIO NOTA ---"+ nfe.getChaveNFe());
               
                try{
                    identificarItensSpdNFe(sc100, nfe,0); 
                    this.contadorXmlProcessadas++;
                   // this.mapDeItensVinculadosAuto.put(sc100.getChaveNFe(), mapItensSpdNFe);
                    addMapDeVinculadosAuto(this.getMapItensSpdNFe());
                }catch(Exception ex){
                    System.out.println(" NOTA COM ERRO--->" + sc100.getChaveNFe());
                }
               
                // Verifica se a serie e outros registros da nota e do sped estão diferentes. Se sim, modifica no SPED conforme a NFe 
                compararSPEDcomNFe(sc100, nfe);            
                // Com os itens identificados, executa os cadastros necessários
                //cadastrarInformacoesSPEDNFe();
                this.contadorProgresso++;
            //});
            }
        }catch(Exception ex){
            System.out.println(" UMA EXCEPTION AQUI ..."+ex.getMessage());
        }
        
        this.descricaoProgresso = "Montando o resumo do processamento";
        montarResumo();
        
        this.descricaoProgresso = "Gerando os Relatórios";
        // Move os arquivos XML não encontrados no SPED        
        moverArquivosXMLNaoEncontrados(leitorSped.getMapSPEDC100());
        // Cria aqruivo CSV com as chaves das notas processadas
        criarArquivosCSV();
        
        this.descricaoProgresso = "Gerando o SPED Modificado";
        criarArquivoSPEDModificado();
        this.descricaoProgresso = "Processo finalizado";       
       
        System.out.println("ITENS PARA ASSOCIAR MANUALMENTE ["+ this.itensSC170.size()+"]");
        try{
            this.itensSC170.forEach((sc170) ->{
                try{
                    System.out.println("SPED = " + sc170.getSPED0200().getDescrItem() + " valor["+ sc170.getValor()+"]" + " CHAVE:" + sc170.getSPEDC100().getChaveNFe());
                 }catch(Exception ex){}
                this.itensNFe.forEach(nfItem ->{
                    try{
                        System.out.println(" NF  " +nfItem.getDescricaoProduto() + " valor comum ["+ nfItem.getValorProduto()+"] valor + imp["+nfItem.getValorTotal()+"]"+ "  CHAVE:" + sc170.getSPEDC100().getChaveNFe() );
                    }catch(Exception ex){}
                });
            }); 
        }catch(Exception ex){}
    }
    private void addMapDeVinculadosAuto (Map<SPEDC170, NFeItem[]> mapVinculadosAuto){
        mapVinculadosAuto.entrySet().stream().forEach(e->{
            this.mapDeItensVinculadosAuto.put(e.getKey(),e.getValue());
        });
    }
    public void processarItensIdentificadosPeloUsuario() {
        inicializarDAOs();
        this.getMapItensSpdNFe().clear();
        this.getMapItensSpdNFe().putAll(this.itensAssociadosManualmente);  
        cadastrarInformacoesSPEDNFe();
        TotalImposto totalImposto = null;
        MetodosUtil util = new MetodosUtil();
        try {            
            for(Map.Entry<SPEDC170,NFeItem[]> associacao :  this.getMapItensSpdNFe().entrySet()){
                boolean multiplos = false;
                try{
                    SPEDC170 sc170 = (SPEDC170)associacao.getKey();
                    NFeItem nfeItem = null;                    
                    BigDecimal valorTotal = BigDecimal.ZERO; 
                    BigDecimal valorIcmsST = BigDecimal.ZERO;
                    BigDecimal valorIpi = BigDecimal.ZERO;                   
                    MetodosUtil mtUtil = new MetodosUtil(); 
                    for(NFeItem nfItem : associacao.getValue()){
                        /*
                            verifica a lista de itens nfe relacionados pelo usuario quem tem  mais de uma ocorrencia
                            se existe, então soma os valores do produto, ICMSST, e IPI
                        */
                        if(associacao.getValue().length > 1){
                            
                            multiplos = true;
                            //valorTotal = valorTotal.add(nfItem.getValorProduto());
                            //mtUtil.somarValorDeProdutoComST(nfeItem);
                          
                            //ICMS - obtem os valores de ST para alterar o sped modificado
                            totalImposto = new TotalImposto();                           
                            totalImposto = mtUtil.identificarImpostoNFe(nfItem);
                            if(totalImposto.getValorICMSST() != null && totalImposto.getValorICMSST().compareTo(BigDecimal.ZERO) != 0){
                                valorIcmsST = valorIcmsST.add(totalImposto.getValorICMSST());
                           }                             
                            //IPI - obtem os valores de IPI para alterar o sped modificado
                            IPI ipi = (IPI)nfItem.getIPI();
                            if(ipi != null)
                                if(ipi.getvIPI() != null)
                                    valorIpi = valorIpi.add(ipi.getvIPI());
                        }
                    }
                    //altera os valores do objeto para gravar as alteraçoes corretas no SPED
                    nfeItem =(NFeItem)associacao.getValue()[0];
                    if(multiplos){
                        nfeItem.setValorProduto(valorTotal);
                        nfeItem = util.inicializarTotalizadorIcmsSTRelacionados(nfeItem,valorIcmsST); 
                        if(nfeItem.getIPI() != null){
                            IPI ipi = (IPI)nfeItem.getIPI();
                            ipi.setvIPI(valorIpi);
                            nfeItem.setIPI(ipi);
                        }
                    }
                    
                    util.aplicarModificacoesIdentificadasSPEDComNFe(
                            sc170,
                            nfeItem,
                            multiplos,
                            "IDENTIFICADOS PELO USUARIO",
                            this.oldLinha,
                            this.sbRelModifSPED,
                            this.itensSC170,
                            this.leitorSped
                            );
                }catch(Exception ex){
                    System.out.println(""+ex.getMessage());
                }
            }
            montarResumo();
            criarArquivoModificacoesSPED();
            criarArquivoSPEDModificado();
        } catch(Exception ex) {
            log.error(ex.getMessage());
        }
    }  
    
    public List<RelacaoProdutoFornecedor> verificarOutrasAssociacoes(String cnpjFornec, String refProd, String refFornec) {
        List<RelacaoProdutoFornecedor> result = null;
        try {
            result = relProdFornecDao.verificarOutrasAssociacoes(this.empresaAtual.getId(), cnpjFornec, refProd, refFornec);
            return result;
        } catch(Exception ex) {
            String msg = String.format("Houve um erro ao tentar verificar a existencia do Produto de Codigo '%s' no Fornecedor com Codigo %s da Empresa '%'",
                                       refProd,
                                       refFornec,
                                       this.empresaAtual.getNome());
            log.error(msg, ex);
            return null;            
        }
    }
    
    public void excluirAssociacaoProdutoFornecedor(String cnpjEmpresa, SPEDC170 spd170, List<NFeItem> nfeItens) { 
        inicializarDAOs();
        try {
            for (NFeItem nfeItem : nfeItens) {                
                Produto p = prodDao.retornarProdutoPorCNPJECodigoItem(cnpjEmpresa, spd170.getCodItem());

                Fornecedor fFiltro = new Fornecedor();
                fFiltro.setCnpj(Masc.mascararCNPJ(nfeItem.getNFe().getEmitente().getCNPJ()));
                Fornecedor f = fornecDao.selecionarUm(fFiltro);
                if (p != null && f != null) {
                    RelacaoProdutoFornecedor rpf = new RelacaoProdutoFornecedor();
                    rpf.setProduto(p);
                    rpf.setFornecedor(f);
                    rpf.setReferenciaFornecedor(nfeItem.getCodigoProduto());
                    relProdFornecDao.excluir(rpf);                
                }
            }
        } catch(Exception ex) {
            String msg = String.format("Houve um erro ao tentar excluir as associações da Empresa de CNPJ '%s' e produto Codigo do Produto '%s'",
                                       cnpjEmpresa,
                                       spd170);
            log.error(msg, ex);
        }
    }
    
//    private void inserirEntradaRelModifSPED(int linha, int coluna, String valorAntigo, String valorNovo,String filtro, SPEDC170 sc170, NFeItem nfItem) {
//        try{
//            //Linha;Coluna;Valor Antigo;Valor Novo,Chave SPED,Chave XML, Descricao SPED, Descricao XML, Valor SPED,Valor XML, Qtd SPED, Qtd XML
//            if(sc170 == null)
//                sc170 = new SPEDC170();
//            
//            if(nfItem == null) 
//                nfItem = new NFeItem();            
//            this.sbRelModifSPED.append(
//                    String.format("%d;%d;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\r\n", 
//                            linha, 
//                            coluna, 
//                            valorAntigo, 
//                            valorNovo,
//                            "'"+sc170.getSPEDC100().getChaveNFe()+"'",
//                            "'"+nfItem.getNFe().getChaveNFe()+"'",
//                            sc170.getSPED0200().getDescrItem(),
//                            nfItem.getDescricaoProdutoCustom(),
//                            sc170.getValor().toString(),
//                            nfItem.getValorTotal().toString(),
//                            sc170.getQtd().toString(),
//                            nfItem.getQuantidade().toString(),
//                            filtro
//                    )
//            );
//        }catch(Exception ex){
//            System.out.println(ex.getMessage());
//        }
//    }
    
    private void inicializarDAOs() {
        this.empDao = new EmpresaDAO();
        this.fornecDao = new FornecedorDAO();
        this.prodDao = new ProdutoDAO();
        this.relProdFornecDao = new RelacaoProdutoFornecedorDAO();
    }
    
    private void compararSPEDcomNFe(SPEDC100 sc100, NFe nfe) {
      
        BigDecimal valorNFeAuditado = BigDecimal.ZERO;
        try {            

            if (!StringUtil.isNullOrEmpty(sc100.getSerie()) && 
                !StringUtil.isNullOrEmpty(nfe.getSerie()) && 
                !sc100.getSerie().equals(nfe.getSerie())) {
//                util.modificarConteudoSPED(sc100.getNumLinhaSPED(), sc100.getIndiceColuna("serie"), sc100.getSerie(), nfe.getSerie(),"COMPARAÇÃO DA FACE DA NOTA SERIE",
//                        null,
//                        null,
//                        this.itensSC170,
//                        this.leitorSped
//                        );
                sc100.setSerie(nfe.getSerie());            
            }
            //compararItemSPEDComItemNFe();            
            //procedimento de auditoria que soma os itens da nota fiscal e compara com o total do sped
            //no caso do valor totald do sped ser diferente da somatoria dos itens da nota assume o valor da somatoria 
            valorNFeAuditado = this.auditarValorNFe(nfe);
            if(sc100.getValorTotalDocumento() == null || valorNFeAuditado.compareTo(sc100.getValorTotalDocumento()) != 0){
                this.setRelMultiplo(true);
//                util.modificarConteudoSPED(sc100.getNumLinhaSPED(), 
//                        sc100.getIndiceColuna("valorTotalDocumento"), 
//                        formatarNumero(this.df, sc100.getValorTotalDocumento()), 
//                        formatarNumero(this.df, valorNFeAuditado),
//                        "COMPARAÇÃO DA FACE DA NOTA VALOR TOTAL DO SPED COM A NOTA",
//                        null,
//                        null,
//                        this.itensSC170,
//                        this.leitorSped
//                );
                
                sc100.setValorTotalDocumento(valorNFeAuditado); 
            }
            
//            if (sc100.getValorTotalDocumento() == null || sc100.getValorTotalDocumento().compareTo(nfe.getValorNotaFiscal()) != 0) {
//                modificarConteudoSPED(sc100.getNumLinhaSPED(), sc100.getIndiceColuna("valorTotalDocumento"), formatarNumero(this.df, sc100.getValorTotalDocumento()), formatarNumero(this.df, nfe.getValorNotaFiscal()));
//                sc100.setValorTotalDocumento(nfe.getValorNotaFiscal());            
//            }

            if (sc100.getValorTotalDesconto() == null || sc100.getValorTotalDesconto().compareTo(nfe.getValorTotalDesconto()) != 0) {
                //util.modificarConteudoSPED(sc100.getNumLinhaSPED(), sc100.getIndiceColuna("valorTotalDesconto"), formatarNumero(this.df, sc100.getValorTotalDesconto()), formatarNumero(this.df, nfe.getValorTotalDesconto()),"COMPARAÇÃO DA FACE DA NOTA VALOR TOTAL DE DESCONTO",null,null);
                sc100.setValorTotalDesconto(nfe.getValorTotalDesconto());            
            }
            if (sc100.getValorTotalMercadorias() == null || sc100.getValorTotalMercadorias().compareTo(nfe.getValorTotalProdServ()) != 0) {
                //util.modificarConteudoSPED(sc100.getNumLinhaSPED(), sc100.getIndiceColuna("valorTotalMercadorias"), formatarNumero(this.df, sc100.getValorTotalMercadorias()), formatarNumero(this.df, nfe.getValorTotalProdServ()),"COMPARAÇÃO DA FACE DA NOTA VALOR TOTAL MERCADORIAS",null,null);
                sc100.setValorTotalMercadorias(nfe.getValorTotalProdServ());            
            }
            if (sc100.getValorFrete() == null || sc100.getValorFrete().compareTo(nfe.getValorTotalFrete()) != 0) {
                //util.modificarConteudoSPED(sc100.getNumLinhaSPED(), sc100.getIndiceColuna("valorFrete"), formatarNumero(this.df, sc100.getValorFrete()), formatarNumero(this.df, nfe.getValorTotalFrete()),"COMPARAÇÃO DA FACE DA NOTA VALOR FRETE",null,null);
                sc100.setValorFrete(nfe.getValorTotalFrete());            
            }
            if (sc100.getValorSeguro() == null || sc100.getValorSeguro().compareTo(nfe.getValorTotalSeguro()) != 0) {
               // util.modificarConteudoSPED(sc100.getNumLinhaSPED(), sc100.getIndiceColuna("valorSeguro"), formatarNumero(this.df, sc100.getValorSeguro()), formatarNumero(this.df, nfe.getValorTotalSeguro()),"COMPARAÇÃO DA FACE DA NOTA VALOR SEGURO",null,null);
                sc100.setValorSeguro(nfe.getValorTotalSeguro());            
            }
            if (sc100.getValorOutrasDespesas() == null || sc100.getValorOutrasDespesas().compareTo(nfe.getValorOutrasDespesas()) != 0) {
                //util.modificarConteudoSPED(sc100.getNumLinhaSPED(), sc100.getIndiceColuna("valorOutrasDespesas"), formatarNumero(this.df, sc100.getValorOutrasDespesas()), formatarNumero(this.df, nfe.getValorOutrasDespesas()),"COMPARAÇÃO DA FACE DA NOTA OUTRAS DESPESAS",null,null);
                sc100.setValorOutrasDespesas(nfe.getValorOutrasDespesas());            
            }
            if (sc100.getValorBCICMS() == null || sc100.getValorBCICMS().compareTo(nfe.getValorTotalBCICMS()) != 0) {
                //util.modificarConteudoSPED(sc100.getNumLinhaSPED(), sc100.getIndiceColuna("valorBCICMS"), formatarNumero(this.df, sc100.getValorBCICMS()), formatarNumero(this.df, nfe.getValorTotalBCICMS()),"COMPARAÇÃO DA FACE DA NOTA BASE DE CALCULO ICMS",null,null);
                sc100.setValorBCICMS(nfe.getValorTotalBCICMS());            
            }
            if (sc100.getValorICMS() == null || sc100.getValorICMS().compareTo(nfe.getValorTotalICMS()) != 0) {
                //util.modificarConteudoSPED(sc100.getNumLinhaSPED(), sc100.getIndiceColuna("valorICMS"), formatarNumero(this.df, sc100.getValorICMS()), formatarNumero(this.df, nfe.getValorTotalICMS()),"COMPARAÇÃO DA FACE DA NOTA VALOR ICMS",null,null);
                sc100.setValorICMS(nfe.getValorTotalICMS());            
            }
            if (sc100.getValorBCICMSST() == null || sc100.getValorBCICMSST().compareTo(nfe.getValorTotalBCICMSST()) != 0) {
                //util.modificarConteudoSPED(sc100.getNumLinhaSPED(), sc100.getIndiceColuna("valorBCICMSST"), formatarNumero(this.df, sc100.getValorBCICMSST()), formatarNumero(this.df, nfe.getValorTotalBCICMSST()),"COMPARAÇÃO DA FACE DA NOTA VALOR TOTAL DE BASE DE CALCULO ICMS",null,null);
                sc100.setValorBCICMSST(nfe.getValorTotalBCICMSST());            
            }
            if (sc100.getValorICMSST() == null || sc100.getValorICMSST().compareTo(nfe.getValorTotalICMSST()) != 0) {
               // util.modificarConteudoSPED(sc100.getNumLinhaSPED(), sc100.getIndiceColuna("valorICMSST"), formatarNumero(this.df, sc100.getValorICMSST()), formatarNumero(this.df, nfe.getValorTotalICMSST()),"COMPARAÇÃO DA FACE DA NOTA VALOR TOTAL DE ICMS ST",null,null);
                sc100.setValorICMSST(nfe.getValorTotalICMSST());            
            }
            if (sc100.getValorIPI() == null || sc100.getValorIPI().compareTo(nfe.getValorTotalIPI()) != 0) {
                //util.modificarConteudoSPED(sc100.getNumLinhaSPED(), sc100.getIndiceColuna("valorIPI"), formatarNumero(this.df, sc100.getValorIPI()), formatarNumero(this.df, nfe.getValorTotalIPI()),"COMPARAÇÃO DA FACE DA NOTA DE IPI",null,null);
                sc100.setValorIPI(nfe.getValorTotalIPI());            
            }
            if (sc100.getValorPIS() == null || sc100.getValorPIS().compareTo(nfe.getValorPIS()) != 0) {
                //util.modificarConteudoSPED(sc100.getNumLinhaSPED(), sc100.getIndiceColuna("valorPIS"), formatarNumero(this.df, sc100.getValorPIS()), formatarNumero(this.df, nfe.getValorPIS()),"COMPARAÇÃO DA FACE DA NOTA VALOR TOTAL DE PIS",null,null);
                sc100.setValorPIS(nfe.getValorPIS());            
            }
            if (sc100.getValorCOFINS() == null || sc100.getValorCOFINS().compareTo(nfe.getValorCOFINS()) != 0) {
                //util.modificarConteudoSPED(sc100.getNumLinhaSPED(), sc100.getIndiceColuna("valorCOFINS"), formatarNumero(this.df, sc100.getValorCOFINS()), formatarNumero(this.df, nfe.getValorCOFINS()),"COMPARAÇÃO DA FACE DA NOTA VALOR TOTAL DE COFINS",null,null);
                sc100.setValorCOFINS(nfe.getValorCOFINS());            
            }
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
        }
       
        
        //compararItemSPEDComItemNFe();        
    } 
    
    private void reiniciaContadores() {
        this.qtdFornecCad = 0;
        this.qtdProdCad = 0;
        this.qtdRelProdFornecCad = 0;
        this.contadorProgresso = 0;
        this.descricaoProgresso = "";
    }
    
    private void criarArquivosCSV() throws PermissaoDiretorioException {
        String dtArquivo = criarTimeStampString();
        criarArquivoCSV(this.getCaminhoDiretorioXmlsNFes() + File.separator + "XMLsNoSPEDEncontradosNoDir_" + dtArquivo + ".csv", sbCSVXmlEnc);
        criarArquivoCSV(this.getCaminhoDiretorioXmlsNFes() + File.separator + "XMLsNoSPEDNaoEncontradosNoDir_" + dtArquivo + ".csv", sbCSVXmlNEnc);
        criarArquivoModificacoesSPED();
    }
    
    private void criarArquivoModificacoesSPED() throws PermissaoDiretorioException {
        String caminhoDirSPED = this.getCaminhoArquivoSPED().substring(0, this.getCaminhoArquivoSPED().lastIndexOf(File.separator));        
        criarArquivoCSV(caminhoDirSPED + File.separator + "ModificacoesSPED_" + criarTimeStampString() + ".csv", sbRelModifSPED);
    }
    
    private String criarTimeStampString() {
        return new SimpleDateFormat("ddMMyyyyHHmmss").format(Calendar.getInstance().getTime());
    }
    
    private void montarResumo() throws DadosSpedNaoCarregadosException {
        // Cria um pequeno relatório do que foi processado no Log
        StringBuilder sbLogResumo = new StringBuilder("");       
        sbLogResumo.append(String.format("<div class='resumoProcessamento'>Qtd de Empresas Cadastradas:<span class='resumoProcessamentoValor'> %d </span></div>", this.qtdEmpresaCad));
        sbLogResumo.append(String.format("<div class='resumoProcessamento'>Qtd de Fornecedores Cadastrados:<span class='resumoProcessamentoValor'> %d </span> </div>", this.qtdFornecCad));
        sbLogResumo.append(String.format("<div class='resumoProcessamento'>Qtd de Produtos Cadastrados: <span class='resumoProcessamentoValor'> %d </span> de <span class='resumoProcessamentoValor'> %d </span></div>", this.qtdProdCad, this.leitorSped.getListaSPED0200().size()));        
        sbLogResumo.append(String.format("<div class='resumoProcessamento'>Qtd de Relacionamentos ProdutoXFornecedor Cadastrados: <span class='resumoProcessamentoValor'> %d </span> </div>", this.qtdRelProdFornecCad));
        sbLogResumo.append(String.format("<div class='resumoProcessamento'>Qtd de XML's Processadas: <span class='resumoProcessamentoValor'> %d </span> </div>", this.contadorXmlProcessadas));
        sbLogResumo.append(String.format("<div class='resumoProcessamento'>Qtd de XML's Nao Encontradas: <span class='resumoProcessamentoValor'> %d </span> </div>", this.chavesNFesNaoEncontradas.size()));
        sbLogResumo.append(String.format("<div class='resumoProcessamento'>Qtd de Itens associados automaticamente: <span class='resumoProcessamentoValor'> %d </span></div>", this.contadorItemAssociados));
        sbLogResumo.append(String.format("<div class='resumoProcessamento'>Qtd filtro relacionamento existente: <span class='resumoProcessamentoValor'> %d </span> </div>", this.contadorRelacionamentoExistente));
        sbLogResumo.append(String.format("<div class='resumoProcessamento'>Qtd filtro mesma ordem: <span class='resumoProcessamentoValor'> %d </span> </div>",  this.contadorMesmaOrdem));        
        sbLogResumo.append(String.format("<div class='resumoProcessamento'>Qtd filtro Mesmo EAN: <span class='resumoProcessamentoValor'> %d </span> </div>",  this.contadorMesmoEan));
        sbLogResumo.append(String.format("<div class='resumoProcessamento'>Qtd filtro Mesmo EANTRIB: <span class='resumoProcessamentoValor'> %d </span> </div>", this.contadorMesmoEanTrib));
        sbLogResumo.append(String.format("<div class='resumoProcessamento'>Qtd filtro Um Para Um: <span class='resumoProcessamentoValor'> %d </span> </div>",  this.contadorUmParaUm));
        sbLogResumo.append(String.format("<div class='resumoProcessamento'>Qtd filtro Um Para Muitos: <span class='resumoProcessamentoValor'> %d </span> </div>", this.contadorUmParaMuitos));
        sbLogResumo.append(String.format("<div class='resumoProcessamento'>Qtd filtro Descrição Exata: <span class='resumoProcessamentoValor'> %d </span> </div>",  this.contadorDescricaoExata));
        sbLogResumo.append(String.format("<div class='resumoProcessamento'>Qtd filtro Descrição Aproximada Valor Identico: <span class='resumoProcessamentoValor'> %d </span> </div>", this.contadorDescricaoAproximadaValorIdentico));
        sbLogResumo.append(String.format("<div class='resumoProcessamento'>Qtd filtro Descrição Aproximada Valor Com Imposto: <span class='resumoProcessamentoValor'> %d </span> </div>",this.contadorDescricaoAproximadaValorIdenticoComImposto));

        sbLogResumo.append(this.leitorXmlNFe.getReporte()).append("\r\n");
        this.resumoProcessamento = sbLogResumo.toString();
        log.info(this.resumoProcessamento);
    }
    
    private void criarArquivoSPEDModificado() throws PermissaoDiretorioException  {        
        StringBuilder sbConteudo = new StringBuilder();
        String path = this.getCaminhoArquivoSPED();
        
        for (String linha : this.leitorSped.getConteudoArquivoSPED())
            sbConteudo.append(String.format("%s\n", linha));
        
        String pathNovo = path.substring(0, path.lastIndexOf(File.separator));
        FileUtil.criarArquivo(pathNovo + File.separator + "SPED_Modificado.txt", sbConteudo);
    }   
    
    private void cadastrarInformacoesSPEDNFe() {
        this.getMapItensSpdNFe().forEach((sc170, nfeItems) -> {
            Fornecedor fornec = addFornecedorSeNaoExistir(sc170.getSPEDC100().getFornecedor());
            Produto prod = addProdutoSeNaoExistir(sc170);
            
            for (NFeItem nfeItem : nfeItems) {
                addRelacProdFornecSeNaoExistir(fornec, prod, nfeItem.getCodigoProduto(),sc170.getSPEDC100().getChaveNFe(),sc170.getSPED0200().getDescrItem(), nfeItem.getDescricaoProduto());
            }
        });
    }
    
    public Map<String, NFe> carregarNFes() throws ErroAoCarregarDadosNFeException, DadosNFeNaoCarregadosException {
        if (!StringUtil.isNullOrEmpty(this.caminhoDiretorioXmlsNFes))
            return this.leitorXmlNFe.lerXmlsNFes(caminhoDiretorioXmlsNFes);        
        throw new DadosNFeNaoCarregadosException("Nenhum arquivo de NFe foi carregado");
    }
    
    public void verificarSeItensEstaoEmOrdem(List<SPEDC100> listaSpedC100, Map<String, NFe> listaNFes) {        
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
    
    public void identificarItensSpdNFe(SPEDC100 sc100, NFe nfe,int filtro) { 
        
        final StringBuffer docFornecedor = new StringBuffer();
        
        if (Masc.isCNPJ(sc100.getFornecedor().getCnpj()))
            docFornecedor.append(Masc.mascararCNPJ(sc100.getFornecedor().getCnpj()));
        else if (Masc.isCPF(sc100.getFornecedor().getCpf()))
            docFornecedor.append(Masc.mascararCPF(sc100.getFornecedor().getCpf()));
        
        this.getMapItensSpdNFe().clear();
        
        if (sc100 == null || nfe == null)
            return;
        
        // Inicia as listas de trabalho
        this.itensSC170.clear();
        this.itensSC170.addAll(sc100.getListaSPEDC170());
        this.itensNFe.clear();
        this.itensNFe.addAll(nfe.getItens());
        this.colecaoItensSC170.addAll(this.itensSC170);
        this.colecaoItensNFe.addAll(this.itensNFe);
        Filtro filtros = new Filtro(); 
        
        filtros.relacionamentoExistente(this.itensNFe,this.itensSC170, this.getMapItensSpdNFe(), this.leitorSped, this.sbRelModifSPED, this.oldLinha,docFornecedor);
        this.contadorRelacionamentoExistente = this.contadorRelacionamentoExistente + filtros.getCont();
        if (todosItensMapeados()){
            imprimirItensRelacionados();
            return;
        }else{
            imprimirItensRelacionados();
        }
        
        // 1º combinação dos filtros
        filtros.umParaUm(this.itensNFe, this.itensSC170, this.getMapItensSpdNFe(), this.leitorSped, this.sbRelModifSPED, this.oldLinha);
        this.contadorUmParaUm = this.contadorUmParaUm + filtros.getCont();
        if (todosItensMapeados()){
            imprimirItensRelacionados();
            return;
        }else{
            imprimirItensRelacionados();
        }
        filtros.mesmaOrdem(assumirItensEmOrdem,this.itensSC170,this.itensNFe, this.getMapItensSpdNFe(),  this.leitorSped, this.sbRelModifSPED, this.oldLinha, this.itensPreAssociadosTemp);            
        this.contadorMesmaOrdem = this.contadorMesmaOrdem + filtros.getCont();
        if (todosItensMapeados()){
            imprimirItensRelacionados();
            return;
        }else{
            imprimirItensRelacionados();
        }
        
        filtros.porEan(this.itensSC170, this.itensNFe, this.getMapItensSpdNFe(), this.leitorSped, this.sbRelModifSPED, this.oldLinha, this.itensPreAssociadosTemp, true);            
        this.contadorMesmoEan = this.contadorMesmoEan + filtros.getCont();
        if (todosItensMapeados()){
            imprimirItensRelacionados();
            return;
        }else{
            imprimirItensRelacionados();
        }
      
        filtros.porEanTrib(this.itensSC170, this.itensNFe, this.getMapItensSpdNFe(),this.leitorSped, this.sbRelModifSPED, this.oldLinha,  this.itensPreAssociadosTemp,true, this.contadorMesmoEanTrib);
        this.contadorMesmoEanTrib = this.contadorMesmoEanTrib + filtros.getCont();
        if (todosItensMapeados()){
            imprimirItensRelacionados();
            return;
        }else{
            imprimirItensRelacionados();
        }
        
        //IDENTEIFICA OS ITENS COM DESCRIÇÃO APROXIMADA    
        filtros.definirDescricaoAproximada( this.itensSC170,  this.itensNFe,  this.itensPreAssociadosTemp);
        
        filtros.descricaoAproximadaValorIdentico(this.itensNFe, this.itensSC170, this.getMapItensSpdNFe(),  this.leitorSped,  this.sbRelModifSPED, this.oldLinha,this.itensPreAssociadosTemp);
        this.contadorDescricaoAproximadaValorIdentico = this.contadorDescricaoAproximadaValorIdentico + filtros.getCont();
        if (todosItensMapeados()){ 
            imprimirItensRelacionados();
            return;
        }else{imprimirItensRelacionados();}
        
        filtros.descricaoAproximadaValorImposto(this.itensNFe, this.itensSC170, this.getMapItensSpdNFe(), this.leitorSped, this.sbRelModifSPED,  this.oldLinha, this.itensPreAssociadosTemp);
        this.contadorDescricaoAproximadaValorIdenticoComImposto = this.contadorDescricaoAproximadaValorIdenticoComImposto + filtros.getCont();
        if (todosItensMapeados()){ 
            imprimirItensRelacionados();
            return;
        }else{imprimirItensRelacionados();}
 
        filtros.umParaMuitos(this.itensNFe, this.itensSC170, this.getMapItensSpdNFe(),  this.leitorSped, this.sbRelModifSPED,  this.oldLinha, this.itensPreAssociadosTemp);        
        this.contadorUmParaMuitos = this.contadorUmParaMuitos + filtros.getCont();
        if (todosItensMapeados()){ 
            imprimirItensRelacionados();
            return;
        }else{imprimirItensRelacionados();}
        
        // 2º combinação dos filtros
        filtros.porEan(this.itensSC170,  this.itensNFe, this.getMapItensSpdNFe(), this.leitorSped, this.sbRelModifSPED, this.oldLinha,  this.itensPreAssociadosTemp, false);            
        this.contadorMesmoEan = this.contadorMesmoEan + filtros.getCont();
        if (todosItensMapeados()){
            imprimirItensRelacionados();
            return;
        }else{
            imprimirItensRelacionados();
        }
        
        filtros.porEanTrib(this.itensSC170, this.itensNFe, this.getMapItensSpdNFe(),  this.leitorSped,  this.sbRelModifSPED, this.oldLinha,  this.itensPreAssociadosTemp, false, this.contadorMesmoEanTrib);
        this.contadorMesmoEanTrib = this.contadorMesmoEanTrib + filtros.getCont();
        if (todosItensMapeados()){
            imprimirItensRelacionados();
            return;
        }else{
            imprimirItensRelacionados();
        }
        
        //IDENTEIFICA OS ITENS COM DESCRIÇÃO APROXIMADA    
        filtros.definirDescricaoAproximada( this.itensSC170,  this.itensNFe,  this.itensPreAssociadosTemp);
        
        filtros.descricaoAproximadaValorIdentico(this.itensNFe, this.itensSC170, this.getMapItensSpdNFe(),  this.leitorSped,  this.sbRelModifSPED, this.oldLinha,this.itensPreAssociadosTemp);
        this.contadorDescricaoAproximadaValorIdentico = this.contadorDescricaoAproximadaValorIdentico + filtros.getCont();
        if (todosItensMapeados()){ 
            imprimirItensRelacionados();
            return;
        }else{imprimirItensRelacionados();}  
     
        filtros.descricaoAproximadaValorImposto(this.itensNFe, this.itensSC170, this.getMapItensSpdNFe(), this.leitorSped, this.sbRelModifSPED,  this.oldLinha, this.itensPreAssociadosTemp);
        this.contadorDescricaoAproximadaValorIdenticoComImposto = this.contadorDescricaoAproximadaValorIdenticoComImposto +  filtros.getCont();
        if (todosItensMapeados()){ 
            imprimirItensRelacionados();
            return;
        }else{imprimirItensRelacionados();}
        
        
        filtros.umParaMuitos(this.itensNFe,  this.itensSC170, this.getMapItensSpdNFe(),   this.leitorSped,  this.sbRelModifSPED,  this.oldLinha,  this.itensPreAssociadosTemp);        
        this.contadorUmParaMuitos = this.contadorUmParaMuitos + filtros.getCont();
        if (todosItensMapeados()){ 
            imprimirItensRelacionados();
            return;
        }else{imprimirItensRelacionados();}
       
        filtros.umParaUm(this.itensNFe, this.itensSC170, this.getMapItensSpdNFe(), this.leitorSped, this.sbRelModifSPED, this.oldLinha);
        this.contadorUmParaUm = this.contadorUmParaUm + filtros.getCont();
        if (todosItensMapeados()){
            imprimirItensRelacionados();
            return;
        }else{
            imprimirItensRelacionados();
        }
        
        // 3º combinação dos filtros
        //IDENTEIFICA OS ITENS COM DESCRIÇÃO APROXIMADA    
        filtros.definirDescricaoAproximada( this.itensSC170,  this.itensNFe,  this.itensPreAssociadosTemp);
        
        filtros.descricaoAproximadaValorIdentico(this.itensNFe, this.itensSC170, this.getMapItensSpdNFe(),  this.leitorSped,  this.sbRelModifSPED, this.oldLinha,this.itensPreAssociadosTemp);
        this.contadorDescricaoAproximadaValorIdentico = this.contadorDescricaoAproximadaValorIdentico + filtros.getCont();
        if (todosItensMapeados()){ 
            imprimirItensRelacionados();
            return;
        }else{imprimirItensRelacionados();}  
     
        filtros.descricaoAproximadaValorImposto(this.itensNFe, this.itensSC170, this.getMapItensSpdNFe(), this.leitorSped, this.sbRelModifSPED,  this.oldLinha, this.itensPreAssociadosTemp);
        this.contadorDescricaoAproximadaValorIdenticoComImposto = this.contadorDescricaoAproximadaValorIdenticoComImposto + filtros.getCont();
        if (todosItensMapeados()){ 
            imprimirItensRelacionados();
            return;
        }else{imprimirItensRelacionados();}
        
        
        filtros.umParaMuitos(this.itensNFe,  this.itensSC170, this.getMapItensSpdNFe(),   this.leitorSped,  this.sbRelModifSPED,  this.oldLinha,  this.itensPreAssociadosTemp);        
        this.contadorUmParaMuitos = this.contadorUmParaMuitos + filtros.getCont();
        if (todosItensMapeados()){ 
            imprimirItensRelacionados();
            return;
        }else{imprimirItensRelacionados();}
       
        filtros.umParaUm(this.itensNFe, this.itensSC170, this.getMapItensSpdNFe(), this.leitorSped, this.sbRelModifSPED, this.oldLinha);
        this.contadorUmParaUm = this.contadorUmParaUm + filtros.getCont();
        if (todosItensMapeados()){
            imprimirItensRelacionados();
            return;
        }else{
            imprimirItensRelacionados();
        }
        
        // se sobrou dexa o usuario decidir
        this.itensSC170.forEach(sc170 ->{
            Map<NFeItem, Float> provaveis = new HashMap<>();
            this.itensNFe.forEach(nf->{
                provaveis.put(nf, Float.NaN);
            });
            this.itensPreAssociados.put(sc170, provaveis);
        });
      } 
    
    private boolean todosItensMapeados() {
        /*
            a ideia do trecho pe reduzir a lista de itens do SPED conforme a relação entre o SPEd e o XML for alimentando
            até que os itens da XML esteja vazio
        */
        //this.itensSC170.removeAll(this.mapItensSpdNFe.keySet());
        for(Map.Entry<SPEDC170,NFeItem[]> map : this.getMapItensSpdNFe().entrySet()){
            SPEDC170 sc170 = map.getKey();
            if(this.itensSC170.contains(sc170)){
                    this.itensSC170.remove(sc170);
                     this.contadorItemAssociados++;
            }
        }
        return this.itensNFe.isEmpty();       
    }  
    
    private boolean validarOrdem(SPEDC100 sc100, NFe nfe) {
        for (int i = 0; i < sc100.getListaSPEDC170().size(); i++) {
            SPEDC170 spd = sc100.getListaSPEDC170().get(i);
            NFeItem nfi = nfe.getItens().get(i);
            if (spd.getQtd().compareTo(nfi.getQuantidade()) != 0 || spd.getValor().compareTo(nfi.getValorProduto()) != 0){
                return false;           
            }
            //log.info(String.format(" comparando qtd c170:(" +spd.getQtd()+") qtd nfeItem: (" + nfi.getQuantidade())+") - valor c170:" + spd.getValor() + " valor nfeItem:"+ nfi.getValorProduto());
        }
        return true;
    }
    
//    private float compararDescricaoAproximada(String nfeItemDesc, String spedItemDesc, SPEDC170 sc170, NFeItem nfeItem) {
//        nfeItemDesc = StringUtil.removerAcentos(nfeItemDesc);
//        spedItemDesc = StringUtil.removerAcentos(spedItemDesc);
//        String[] nfeItemPalavras = nfeItemDesc.replace(".", " ").replace("-", " ").replace("/", " ").replace("\\", " ").split(" ");
//        String[] spdItemPalavras = spedItemDesc.replace(".", " ").replace("-", " ").replace("/", " ").replace("\\", " ").split(" ");
//        
//        float palavrasEncontradas = 0;
//        float resultato = 0;
//        
//        if (nfeItemPalavras.length == 0 || spdItemPalavras.length == 0)
//            return 0.0F;
//        
//        for (String pSpd : spdItemPalavras) {
//            if (StringUtil.isNullOrEmpty(pSpd) || pSpd.length() <= 1)
//                continue;
//                
//            for (String pNfe : nfeItemPalavras) {
//                if (StringUtil.isNullOrEmpty(pNfe) || pNfe.length() <= 1)
//                    continue;
//                
//                if (pSpd.toLowerCase().contains(pNfe.toLowerCase()) || pNfe.toLowerCase().contains(pSpd.toLowerCase()))
//                    palavrasEncontradas++;
//            }
//        }         
//        resultato = (palavrasEncontradas / (float)(spdItemPalavras.length + nfeItemPalavras.length));
//         //log.info(String.format("descrição aproximada USUARIO VAI DECIDIR XML: "+ chave +"  C170:("+ spedItemDesc + ") NFEItem:("+nfeItemDesc +") porcentagem --> ("+ resultato+")"));
//        //imprimirInformacoesParaDepuracao(sc170,nfeItem,"DESCRICAO APROXIMADA  - USUARIO VAI DECIDIR");       
//        return resultato;
//    }
    
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
        if (sbCsv.length() > 0)
            FileUtil.criarArquivo(path, sbCsv);
    }
    
    private BigDecimal auditarValorNFe(NFe nfe){
        this.itensNFe.clear();
        this.itensNFe.addAll(nfe.getItens());
        BigDecimal soma = BigDecimal.ZERO;
        if(this.itensNFe.size() == 0)return soma;
        
        for(NFeItem item : this.itensNFe)
        {
            try{
                if(item.getIndicaTotal() == 1)//regra da nfe se o indicador for 1 entra no somatorio da nota fiscal
                    soma = soma.add(item.getValorProduto());
            }catch(Exception ex){continue;}
        }
        //subtraindo o total de desconto e desoneração
        try{soma = soma.subtract(nfe.getValorTotalDesconto());}catch(Exception ex){soma = soma.subtract(BigDecimal.ZERO);}
        try{soma = soma.subtract(nfe.getValorTotalICMSDesonerado());}catch(Exception ex){soma = soma.subtract(BigDecimal.ZERO);}
        //incluindo total de  frete
        try{soma = soma.add(nfe.getValorTotalFrete());}catch(Exception ex){soma = soma.add(BigDecimal.ZERO);}
        //incluindo total de seguro
        try{soma = soma.add(nfe.getValorTotalSeguro());}catch(Exception ex){soma = soma.add(BigDecimal.ZERO);}
        //incluindo o total de outros
        try{soma = soma.add(nfe.getValorOutrasDespesas());}catch(Exception ex){soma = soma.add(BigDecimal.ZERO);}
        //incluindo substituiçao tributaria
        try{soma = soma.add(nfe.getValorTotalICMSST());}catch(Exception ex){soma = soma.add(BigDecimal.ZERO);} 
        //incluindo o Inposto de Inportação
        try{soma = soma.add(nfe.getValorTotalII());}catch(Exception ex){soma = soma.add(BigDecimal.ZERO);} 
        //incluindo o valor de Ipi
        try{soma = soma.add(nfe.getValorTotalIPI());}catch(Exception ex){soma = soma.add(BigDecimal.ZERO);} 
        return  soma;
    }  
   
    private Empresa addEmpresaSeNaoExistir() {        
        SPED0000 spd0 = this.leitorSped.getSped0000();
        Empresa emp = new Empresa();        
        try {  
            emp.setCnpj(Masc.mascararCNPJ(this.leitorSped.getSped0000().getCNPJ()));
            emp = empDao.selecionarUm(emp);

            if (emp == null || emp.getId() == null) {
                emp = new Empresa();
                emp.setCnpj(Masc.mascararCNPJ(spd0.getCNPJ()));
                emp.setNome(spd0.getNomeEmpresarialEntidade());
                emp.setCodigoMunicipio(Integer.parseInt(spd0.getCodMun()));
                emp.setInscricaoEstadual(spd0.getIE());
                emp.setInscricaoMunicipal(spd0.getIM());
                emp.setUf(spd0.getUF());
                empDao.salvar(emp);
                this.qtdEmpresaCad++;
            }
            return emp;
        } catch(Exception ex) {
             //ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().beginTransaction().rollback();
            log.error(String.format("Houve um erro ao tentar adicionar a Empresa '%s'. Mensagem: %s", spd0.getNomeEmpresarialEntidade(), ex.getMessage()), ex);
        }        
        
        return null;        
    }
    
    private Fornecedor addFornecedorSeNaoExistir(SPEDC150 spdc150) {
        Fornecedor fornec = new Fornecedor();        
        try {            
            String documento = null;
            if (Masc.isCNPJ(spdc150.getCnpj()))
                documento = Masc.mascararCNPJ(spdc150.getCnpj());
            else if (Masc.isCPF(spdc150.getCpf()))
                documento = Masc.mascararCPF(spdc150.getCpf());
            else
                throw new Exception(String.format("O Fornecedor '%s' não tem um documento válido.", spdc150.getNome()));
            
            fornec.setCnpj(documento);
            fornec = fornecDao.selecionarUm(fornec);

            if (fornec == null || fornec.getId() == null) {
                fornec = new Fornecedor();
                fornec.setCnpj(documento);
                fornec.setNome(spdc150.getNome());
                fornecDao.salvar(fornec);
                this.qtdFornecCad++;
            }            
            return fornec;
        } catch(Exception ex) {
            log.error(String.format("Houve um erro ao tentar incluir o Fornecedor '%s'. Mensagem: %s", spdc150.getNome(), ex.getMessage()), ex);
        }
        
        return null;
    }
    private Produto addProdutoSeNaoExistir(SPEDC170 spdc170) {        
        Produto prod = new Produto();       
        try {
            prod.setEmpresa(this.empresaAtual);
            prod.setReferenciaProduto(spdc170.getCodItem());
            prod = prodDao.selecionarUm(prod);
            
            if (prod == null || prod.getId() == null) {
                prod = new Produto();
                prod.setEmpresa(this.empresaAtual);
                prod.setDescricao(spdc170.getSPED0200().getDescrItem());
                prod.setEan(spdc170.getSPED0200().getCodBarra());
                prod.setNcm(spdc170.getSPED0200().getCodNCM());
                prod.setReferenciaProduto(spdc170.getCodItem());
                prod.setUnidadeMedida(spdc170.getUnid());
                prod.setAliqICMS(spdc170.getAliqICMS());
                prodDao.salvar(prod);
                this.qtdProdCad++;
            }            
            return prod;
        } catch(Exception ex) {
            log.error(String.format("Houve um erro ao tentar incluir o Produto '%s'. Mensagem: %s", spdc170.getSPED0200().getDescrItem(), ex.getMessage()), ex);
        }        
        return null;
    }
    
    private RelacaoProdutoFornecedor addRelacProdFornecSeNaoExistir(Fornecedor fornec, Produto prod, String cProd, String chave, String descSped, String descXml) {        
        inicializarDAOs();
        RelacaoProdutoFornecedor relProdFornec = new RelacaoProdutoFornecedor();
        try {
            boolean atualizar = true;
            relProdFornec.setFornecedor(fornec);
            relProdFornec.setProduto(prod);
            relProdFornec.setReferenciaFornecedor(cProd);
            relProdFornec = relProdFornecDao.selecionarUm(relProdFornec);
            if (relProdFornec == null) {
                relProdFornec = new RelacaoProdutoFornecedor();
                this.qtdRelProdFornecCad++;
                atualizar = false;
            }
            
            relProdFornec.setFornecedor(fornec);
            relProdFornec.setProduto(prod);
            relProdFornec.setReferenciaFornecedor(cProd);
            
            relProdFornec.setChaveNf(chave);
            relProdFornec.setProdSped(descSped);
            relProdFornec.setProdXml(descXml);
            
            if (atualizar)
                relProdFornecDao.atualizar(relProdFornec);
            else                
                relProdFornecDao.salvar(relProdFornec);            
            
            return relProdFornec;
        } catch(Exception ex) {
            if (relProdFornec != null) {
                log.error(String.format("Houve um erro ao tentar incluir a Relação do Produto. relProdFornec = null. Mensagem: %s", ex.getMessage()), ex);
            } else {
                try {
            log.error(String.format("Houve um erro ao tentar incluir a Relação do Produto '%s' com o Fornecedor '%s' para a Empresa '%s'. Mensagem: %s", 
                                        relProdFornec.getProduto().getDescricao(), relProdFornec.getFornecedor().getNome(), relProdFornec.getProduto().getEmpresa().getNome(), ex.getMessage()), ex);
                } catch(Exception ex2) {
                    log.error(String.format("Houve um erro ao tentar incluir a Relação do Produto. Mensagem: %s", ex2.getMessage()), ex2); 
                }
            }            
        }
        
        return null;
    }
    
    public Map<String, List<NFItem>> getItensToLazyLoadIndex() {
		return itensToLazyLoadIndex;
    }     
    
    public boolean compararItensRelacionados(SPEDC170 sc170, NFeItem nfeItem){
        boolean econtrouItemSped = false;
        boolean econtrouItemNfe = false;
        loopSped:
        for(Map.Entry<SPEDC170,NFeItem[]> associacao :  this.getMapItensSpdNFe().entrySet())
         {
            if(associacao.getKey() != sc170)continue;
             econtrouItemSped = true;
             loopNfe:
             for(NFeItem n : associacao.getValue()){
                 if(n == nfeItem){
                    econtrouItemNfe = true; 
                    break loopNfe;
                 }
             };
             break loopSped;
         }
        if(econtrouItemSped == true && econtrouItemNfe == true)return true;
        
        return false;
    }
    
    public  boolean compararItensPreAssociados(SPEDC170 sc170, NFeItem nfeItem){
        boolean econtrouItemSped = false;
        boolean econtrouItemNfe = false;
        loopSped:
        for(Entry<SPEDC170, Map<NFeItem, Float>> associacao : this.itensPreAssociados.entrySet()){
          if(associacao.getKey() != sc170)continue;
          econtrouItemSped = true;
          loopNfe:
             for(NFeItem n : associacao.getValue().keySet()){
                 if(n == nfeItem){
                    econtrouItemNfe = true; 
                    break loopNfe;
                 }
             };        
            break loopSped;
        };
        if(econtrouItemSped == true && econtrouItemNfe == true)return true;
        return false;
    }  
   
    public void  imprimirItensRelacionados(){
        int sizeTest = 0;
        for(Map.Entry<SPEDC170, NFeItem[]> associados : this.getMapItensSpdNFe().entrySet())
        {
            for(NFeItem nfeItem : associados.getValue())
            {
                sizeTest = sizeTest + 1;
            }
        }        
        System.out.println(String.format("TOTAL DE ITENS ASSOCIADOS DA NOTA AUTOMATICAMENTE: " +  this.getMapItensSpdNFe().size()));
        System.out.println(String.format("SOBROU PARA PROXIMO FILTRO: " +  this.itensNFe.size()));  
        this.itensNFe.forEach(nf ->{            
            System.out.println("NAO FORAM ASSOCIADOS --> " + nf.getDescricaoProduto());
        });
    }   

}
