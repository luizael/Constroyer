package br.com.mixfiscal.prodspedxnfe.gui.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import br.com.lulusoftwares.luluframework.jquery.JQueryResponse;
import br.com.lulusoftwares.luluframework.jquery.JQueryResult;
import br.com.mixfiscal.prodspedxnfe.dao.ex.DAOException;
import br.com.mixfiscal.prodspedxnfe.dao.own.ClassificacaoTributariaProdutoDAO;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFItem;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFeItem;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.TotalImposto;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributariaProduto;
import br.com.mixfiscal.prodspedxnfe.domain.own.Cliente;
import br.com.mixfiscal.prodspedxnfe.domain.own.RelacaoProdutoFornecedor;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPED0000;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC100;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC170;
import br.com.mixfiscal.prodspedxnfe.gui.backbean.IndexBackBean;
import br.com.mixfiscal.prodspedxnfe.gui.util.Constantes;
import br.com.mixfiscal.prodspedxnfe.gui.util.Utils;
import br.com.mixfiscal.prodspedxnfe.services.ClienteService;
import br.com.mixfiscal.prodspedxnfe.services.ProcessadorSPED;
import br.com.mixfiscal.prodspedxnfe.services.MetodosUtil;
import br.com.mixfiscal.prodspedxnfe.util.Masc;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/asProdForWS")
public class AssociadorProdutoFornecedorWS {

    private final Logger log;
    private final String SESSAO_ITENS_MANUAIS = "sessao_itensManuais";

    @Context
    private HttpServletRequest request;
    @Context
    private ServletContext servletContext;
    private final Gson gson;
    private String tokenCliente = "";

    public AssociadorProdutoFornecedorWS() {
        this.gson = new Gson();
        this.log = LogManager.getLogger(ProcessadorSPED.class);
    }

    @POST
    @Path("/showSubItems")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse showSubItems(ProcessarParam param) {
        JQueryResponse resp = new JQueryResponse();
        try {
            IndexBackBean ibb = (IndexBackBean) request.getSession().getAttribute("indexBackBean");
            if (ibb.getProcessadorSPED() != null && ibb.getProcessadorSPED().getItensPreAssociados() != null
                    && ibb.getProcessadorSPED().getItensToLazyLoadIndex().size() > 0) {
                for (String key : ibb.getProcessadorSPED().getItensToLazyLoadIndex().keySet()) {
                    boolean isIdProduto = key.indexOf(param.getIdProduto().trim()) != -1;
                    boolean isChaveNota = key.indexOf(param.getChave().trim()) != -1;
                    if (isIdProduto && isChaveNota) {

                        resp.setObject(ibb.getProcessadorSPED().getItensToLazyLoadIndex().get(key));
                    }
                }
            }
            resp.setResult(JQueryResult.Success);

        } catch (Exception ex) {
            resp.setResult(JQueryResult.Error);
            resp.setObject(ex);
            resp.setMessage("Erro inesperado ao tentar carregar sub-lista de itens a serem relacionados.");
            log.error("Erro inesperado ao tentar carregar sub-lista de itens a serem relacionados. ex:" + ex.getMessage(), ex);
        }
        return resp;
    }

    @POST
    @Path("/processar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse processar(ProcessarParam param) {
        JQueryResponse resp = new JQueryResponse();
        try {
            IndexBackBean ibb = (IndexBackBean) request.getSession().getAttribute("indexBackBean");
            ProcessadorSPED asProdFor = ibb.getProcessadorSPED();
            //boolean executarTransacoes = Boolean.parseBoolean(servletContext.getInitParameter(Constantes.PARAM_EXECUTAR_TRANSACOES)); 
            String serverPath = servletContext.getInitParameter(Constantes.PARAM_SERVER_PATH);
//            if(serverPath.charAt(serverPath.length() -1) == File.separator.charAt(0))
//                serverPath = serverPath.substring(0, serverPath.lastIndexOf(File.separator)); 

            String caminhoSPED = Utils.retornarCaminhoServidor(serverPath, param.getCaminhoSPED());
            String caminhoDir = Utils.retornarCaminhoServidor(serverPath, param.getCaminhoDirXMLs());

            ibb.setCaminhoArquivoSPED(param.getCaminhoSPED());
            ibb.setCaminhoDirXMLsNFes(param.getCaminhoDirXMLs());

            asProdFor.setCaminhoArquivoSPED(caminhoSPED);
            asProdFor.setCaminhoDiretorioXmlsNFes(caminhoDir);
            // Deixando o sistema resolver 
            //asProdFor.setAnalisarOrdemItensAuto(true);
            //asProdFor.setAssumirItensEmOrdem(false);
            //asProdFor.setExecutarTransacoes(executarTransacoes);

            asProdFor.processar();
            String resumo = asProdFor.getResumoProcessamento().replace("\r\n", "<br/>");

            ProcessarRetorno ret = new ProcessarRetorno();
            ret.setResumo("Processamento finalizado com sucesso<br/><br/>" + resumo);
            ret.setSped0000(asProdFor.getSped0000());
            /*
             * @autor eugenio e andrei
             * Externalizando método que já deixa os itens associados por chave da nota String para facilitar a recuperação posterior.
             */
            // Map<SPEDC170, Map<NFeItem, Float>> mapDeVinculadosManuais = identificarSpedComNfe(asProdFor.getItensPreAssociados());
            Map<String, String> mapDeCabecalhos = criarOsCabecalhos(asProdFor.getItensPreAssociados(), encontrarChavesDistintas(asProdFor.getItensPreAssociados()), asProdFor.getMapDeItensVinculadosAuto(), asProdFor, asProdFor.getSped0000());
            List<String> listaDeVinculadosAuto = tranformarOsItensVinculadosAutoEmJSON(asProdFor.getMapDeItensVinculadosAuto());
            //  List<String> listaSpedManuais =  criaListaDeVinculosManuaisSped(mapDeVinculadosManuais);
            //  List<NFItem> listaNfeManuais =  criaListaDeVinculosManuaisNfe (mapDeVinculadosManuais);
            Map<String, List<NFItem>> mapJson = transformarMapDeVinculosManuaisJSON(asProdFor.getItensPreAssociados());
            // mapJson.keySet().forEach((key) -> {
            //asProdFor.getItensToLazyLoadIndex().put(key, mapJson.get(key));
            // });
            ret.setItensPreAssociados(mapJson);
            //  ret.setListaSped(listaSpedManuais);
            // ret.setListaNfe(listaNfeManuais);
            ret.setMapDeCabecalhos(mapDeCabecalhos);
            ret.setListDeItensVinculadosAuto(listaDeVinculadosAuto);
            resp.setObject(ret);
            resp.setResult(JQueryResult.Success);
        } catch (Exception ex) {
            resp.setObject(ex);
            resp.setResult(JQueryResult.Error);
            resp.setMessage(ex.getMessage());
            log.error("Houve um erro ao tentar Processar. Mensagem: " + ex.getMessage(), ex);
        }
        return resp;
    }

    @POST
    @Path("/processarItensManuais")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse processarItensManuais(ItensManuais id) {
        JQueryResponse resp = new JQueryResponse();

        try {
            IndexBackBean ibb = (IndexBackBean) request.getSession().getAttribute("indexBackBean");
            ProcessadorSPED processadorSPED = ibb.getProcessadorSPED();
            // List<ItensManuais> listaItensManuais = obterSessaoListaItensManuais();//obtem a sessão de itens relacionado manualmente

            //  listaItensManuais.stream().forEach(im -> {//percorre a sessao de itens manuais e localiza o mesmo na lista ja 
            Optional<Entry<SPEDC170, Map<NFeItem, Float>>> oIpa
                    = processadorSPED.getItensPreAssociados().entrySet().stream()
                            .filter(e -> testarIdSped(e.getKey(), id.getIdSpedC170()))
                            .findAny();
            if (oIpa.isPresent()) {
                Entry<SPEDC170, Map<NFeItem, Float>> spdc170 = oIpa.get();
                Map<NFeItem, Float> itens = spdc170.getValue();
                List<NFeItem> nfeItens = new ArrayList<>();

                for (String idNFeItem : id.idNFeItem) {

//                        itens.entrySet().stream().forEach(n->{ 
//                         // idNFeItem.trim();
//                          //idNFeItem.replace("/n","");
//                          if(testarIdNFeItem(n.getKey(),idNFeItem))
//                          {
//                             nfeItens.add(n.getKey());
//                          }
//                        });
                    Optional<Entry<NFeItem, Float>> oNfei
                            = itens.entrySet().stream().filter(e -> testarIdNFeItem(e.getKey(), idNFeItem)).findAny();
                    if (oNfei.isPresent()) {
                        Entry<NFeItem, Float> nfei = oNfei.get();
                        nfeItens.add(nfei.getKey());//obtem o item da nota fiscal
                    }
                }

                if (id.isSubstituir()) {
                    String cnpjEmpresa = Masc.mascararCNPJ(processadorSPED.getLeitorSped().getSped0000().getCNPJ());
                    processadorSPED.excluirAssociacaoProdutoFornecedor(cnpjEmpresa, spdc170.getKey(), nfeItens);
                }
                processadorSPED.getItensAssociadosManualmente().put(spdc170.getKey(), nfeItens.toArray(new NFeItem[]{}));
            }
            // });

            processadorSPED.processarItensIdentificadosPeloUsuario();
            limparSessaoListaItensManuais();
            resp.setResult(JQueryResult.Success);
        } catch (Exception ex) {
            resp.setObject(ex);
            resp.setResult(JQueryResult.Error);
            resp.setMessage(ex.getMessage());
            log.error("Houve um erro ao tentar Processar Itens Manuais. Mensagem: " + ex.getMessage(), ex);
        }
        return resp;
    }

    @POST
    @Path("/confirmarItensAssociados")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse confirmarItensAssociados(ItensManuais itemManual) {
        JQueryResponse resp = new JQueryResponse();
        IndexBackBean ibb = (IndexBackBean) request.getSession().getAttribute("indexBackBean");

        try {
            ProcessadorSPED procSped = ibb.getProcessadorSPED();
            List<ItensManuais> listaItensManuais = obterSessaoListaItensManuais();
            ItensManuais itemJaAssociado = retornarAssociacaoExistenteSessao(listaItensManuais, itemManual);
            Optional<SPEDC170> optSc170Sel
                    = procSped.getColecaoItensSC170().stream()
                            .filter(x -> testarIdSped(x, itemManual.getIdSpedC170()))
                            .findFirst();
            SPEDC170 sc170Sel = optSc170Sel.get();

            if (itemJaAssociado != null) {
                Optional<SPEDC170> optSc170
                        = procSped.getColecaoItensSC170().stream()
                                .filter(x -> testarIdSped(x, itemJaAssociado.getIdSpedC170()))
                                .findFirst();
                Optional<NFeItem> optNFeItem
                        = procSped.getColecaoItensNFe().stream()
                                .filter(x -> itemJaAssociado.getIdNFeItem().contains(montarIdNFeItem(x)))
                                .findFirst();
                SPEDC170 sc170 = optSc170.get();
                NFeItem nfeItem = optNFeItem.get();
                resp.setMessage(String.format("<b>Confirmar a Troca de Associação abaixo?</b><br/>"
                        + "<b>Produto da Nota Fiscal:</b> '%s' - <b>Referencia:</b> '%s' <br/>"
                        + "<b>Associação Atual:</b> '%s'<br/>"
                        + "<b>Nova Associação:</b> '%s'",
                        nfeItem.getDescricaoProduto(), nfeItem.getCodigoProduto(),
                        sc170.getSPED0200().getDescrItem(), sc170Sel.getSPED0200().getDescrItem()));
                resp.setResult(JQueryResult.Success);
                return resp;
            }

            Optional<NFeItem> optNFeItem
                    = procSped.getColecaoItensNFe().stream()
                            .filter(x -> itemManual.getIdNFeItem().contains(montarIdNFeItem(x)))
                            .findFirst();
            try {
                if (optNFeItem != null && optNFeItem.isPresent()) {
                    NFeItem nfeItem = optNFeItem.get();
                    List<RelacaoProdutoFornecedor> relacaoJaExistente
                            = ibb.getProcessadorSPED().verificarOutrasAssociacoes(Masc.mascararCNPJ(nfeItem.getNFe().getEmitente().getCNPJ()),
                                    sc170Sel.getCodItem(), nfeItem.getCodigoProduto());

                    if (relacaoJaExistente != null && relacaoJaExistente.size() > 0) {
                        RelacaoProdutoFornecedor rpf = relacaoJaExistente.get(0);
                        resp.setMessage(String.format("<b>Confirmar a Troca de Associação abaixo?</b><br/>"
                                + "<b>Produto da Nota Fiscal:</b> '%s' - <b>Referencia:</b> '%s' <br/>"
                                + "<b>Associação Atual:</b> '%s'<br/>"
                                + "<b>Nova Associação:</b> '%s'<br/>"
                                + "(Caso positivo, o produto associado anteriormente só poderá "
                                + "ser associado com outro produto num outro processamento em que o mesmo esteja presente pois essa "
                                + "associação não faz parte do processamento corrente.)",
                                nfeItem.getDescricaoProduto(), nfeItem.getCodigoProduto(),
                                rpf.getProduto().getDescricao(), sc170Sel.getSPED0200().getDescrItem()));
                        resp.setResult(JQueryResult.Success);
                        return resp;
                    }
                }
            } catch (Exception ex) {
            }
            listaItensManuais.add(itemManual);
            atualizarSessaoListaItensManuais(listaItensManuais);
            resp.setResult(JQueryResult.Success);
        } catch (Exception ex) {
            resp.setMessage(ex.getMessage());
            resp.setResult(JQueryResult.Error);
        }
        return resp;
    }

    @POST
    @Path("/confirmarSubstituicaoItem")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse confirmarSubstituicaoItem(ItensManuais itemManual) {
        JQueryResponse resp = new JQueryResponse();
        IndexBackBean ibb = (IndexBackBean) request.getSession().getAttribute("indexBackBean");
        ProcessadorSPED procSPED = ibb.getProcessadorSPED();
        try {
            List<ItensManuais> listaItensManuais = obterSessaoListaItensManuais();

            if (itemManual.isSubstituir()) {
                ItensManuais itemJaAssociado = retornarAssociacaoExistenteSessao(listaItensManuais, itemManual);
                if (itemJaAssociado != null) {
                    listaItensManuais.remove(itemJaAssociado);
                    resp.setObject(itemJaAssociado);
                } else {
                    Optional<SPEDC170> optSped170 = procSPED.getColecaoItensSC170().stream()
                            .filter(s -> testarIdSped(s, itemManual.getIdSpedC170()))
                            .findFirst();
                    Optional<NFeItem> optNFeItem
                            = procSPED.getColecaoItensNFe().stream()
                                    .filter(n -> itemManual.getIdNFeItem().contains(montarIdNFeItem(n)))
                                    .findFirst();

                    if (optSped170.isPresent() && optNFeItem.isPresent()) {
                        SPEDC170 spd170 = optSped170.get();
                        NFeItem nfeItem = optNFeItem.get();
                        List<RelacaoProdutoFornecedor> listaRpf
                                = procSPED.verificarOutrasAssociacoes(Masc.mascararCNPJ(nfeItem.getNFe().getEmitente().getCNPJ()),
                                        spd170.getCodItem(),
                                        nfeItem.getCodigoProduto());
                        if (listaRpf != null && listaRpf.size() > 0) {
                            RelacaoProdutoFornecedor rpf = listaRpf.get(0);
                            Optional<SPEDC170> optSpdRel = procSPED.getColecaoItensSC170().stream()
                                    .filter(s -> s.getCodItem().equals(rpf.getProduto().getReferenciaProduto())
                                    && Masc.mascararCNPJ(s.getSPEDC100().getFornecedor().getCnpj())
                                            .equals(rpf.getFornecedor().getCnpj()))
                                    .findFirst();
                            if (optSpdRel.isPresent()) {
                                List<String> idsNFeItem = new ArrayList<>();
                                idsNFeItem.add(montarIdNFeItem(nfeItem));

                                ItensManuais im = new ItensManuais();
                                im.setIdSpedC170(montarIdSpedC170(optSpdRel.get()));
                                im.setIdNFeItem(idsNFeItem);

                                resp.setObject(im);
                            }
                        }
                    }
                }
            }

            listaItensManuais.add(itemManual);
            atualizarSessaoListaItensManuais(listaItensManuais);

            resp.setResult(JQueryResult.Success);
        } catch (Exception ex) {
            resp.setMessage(ex.getMessage());
            resp.setResult(JQueryResult.Error);
        }

        return resp;
    }

    @POST
    @Path("/excluirAssociacao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse excluirAssociacaoProdutoFornecedor(ItensManuais itemManual) {
        JQueryResponse resp = new JQueryResponse();
        try {
            IndexBackBean ibb = (IndexBackBean) request.getSession().getAttribute("indexBackBean");
            ProcessadorSPED processadorSPED = ibb.getProcessadorSPED();
            String cnpjEmpresa = processadorSPED.getLeitorSped().getSped0000().getCNPJ();

            Optional<SPEDC170> optSpd170 = processadorSPED.getColecaoItensSC170().stream()
                    .filter(s -> testarIdSped(s, itemManual.getIdSpedC170()))
                    .findFirst();
            List<NFeItem> listNFeItem = processadorSPED.getColecaoItensNFe().stream()
                    .filter(n -> itemManual.getIdNFeItem().contains(montarIdNFeItem(n)))
                    .collect(Collectors.toList());
            if (optSpd170.isPresent() && listNFeItem != null && listNFeItem.size() > 0) {
                SPEDC170 spd170 = optSpd170.get();
                processadorSPED.excluirAssociacaoProdutoFornecedor(Masc.mascararCNPJ(cnpjEmpresa), spd170, listNFeItem);
            }
        } catch (Exception ex) {
            resp.setMessage(ex.getMessage());
            resp.setResult(JQueryResult.Error);
        }
        return resp;
    }

    @POST
    @Path("/checarProgresso")
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse checarProgresso() {
        JQueryResponse resp = new JQueryResponse();

        try {
            IndexBackBean ibb = (IndexBackBean) request.getSession().getAttribute("indexBackBean");
            ProcessadorSPED asProdFor = ibb.getProcessadorSPED();

            if (asProdFor != null) {
                ChecarProgressoRet cpRet = new ChecarProgressoRet();
                cpRet.setTotal(asProdFor.getLeitorSped().getListaSPEDC100().size());
                cpRet.setAtual(asProdFor.getContadorProgresso());
                cpRet.setDescricao(asProdFor.getDescricaoProgresso());
                resp.setObject(cpRet);
                resp.setResult(JQueryResult.Success);
            } else {
                resp.setResult(JQueryResult.Error);
                resp.setMessage("Não foi possível verificar o progresso do processamento");
            }
        } catch (Exception ex) {
            resp.setResult(JQueryResult.Error);
            resp.setMessage(ex.getMessage());
            resp.setObject(ex);
        }

        return resp;
    }

    @POST
    @Path("/checarAssociadosAutomaticamente")
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse checarAssocAutomatico() {
        JQueryResponse resp = new JQueryResponse();

        try {
            IndexBackBean ibb = (IndexBackBean) request.getSession().getAttribute("indexBackBean");
            ProcessadorSPED asProdFor = ibb.getProcessadorSPED();

            if (asProdFor != null) {
                ChecarProgressoRet cpRet = new ChecarProgressoRet();
                cpRet.setTotal(asProdFor.getContadorItemAssociados());
                resp.setObject(cpRet);
                resp.setResult(JQueryResult.Success);
            } else {
                resp.setResult(JQueryResult.Error);
                resp.setMessage("Não foi possível verificar o progresso do processamento");
            }
        } catch (Exception ex) {
            resp.setResult(JQueryResult.Error);
            resp.setMessage(ex.getMessage());
            resp.setObject(ex);
        }

        return resp;
    }

    private boolean testarIdSped(SPEDC170 spd170, String idSpedC170) {
        return montarIdSpedC170(spd170).equals(idSpedC170);
    }

    private boolean testarIdNFeItem(NFeItem nfeItem, String idNFeItem) {
        return montarIdNFeItem(nfeItem).contains(idNFeItem);
    }

    private String montarIdNFeItem(NFeItem nfeItem) {
        return String.format("%s_%s_%s_%s", nfeItem.getNFe().getEmitente().getCNPJ(), nfeItem.getNFe().getNumeroNotaFiscal(), nfeItem.getCodigoProduto().replaceAll("[^a-zA-Z0-9]", ""), nfeItem.getSequencia());
    }

    private String montarIdSpedC170(SPEDC170 spd170) {
        IndexBackBean ibb = (IndexBackBean) request.getSession().getAttribute("indexBackBean");
        ProcessadorSPED procSped = ibb.getProcessadorSPED();
        return String.format("%s_%s_%s_%s",
                procSped.getLeitorSped().getSped0000().getCNPJ(),
                spd170.getSPEDC100().getNumeroNF(),
                spd170.getSPED0200().getCodItem().replaceAll("[^a-zA-Z0-9]", ""),
                spd170.getNumItem());
    }

    private int identificarRelacionamentoMultiplo(int totalItensC170, int totalItensNfe) {
        if (totalItensC170 < totalItensNfe) {
            return 1;
        } else {
            return 0;
        }
    }

    private ItensManuais retornarAssociacaoExistenteSessao(List<ItensManuais> listaItensManuais, ItensManuais itemManual) {
        for (ItensManuais im : listaItensManuais) {
            for (String idNFeSessao : im.getIdNFeItem()) {
                for (String idNFeSelecionado : itemManual.getIdNFeItem()) {
                    if (idNFeSessao.equals(idNFeSelecionado)
                            && !im.getIdSpedC170().equals(itemManual.getIdSpedC170())) {
                        return im;
                    }
                }
            }
        }
        return null;
    }

    private List<ItensManuais> obterSessaoListaItensManuais() {
        List<ItensManuais> listaItensManuais = (List<ItensManuais>) request.getSession().getAttribute(SESSAO_ITENS_MANUAIS);
        if (listaItensManuais == null) {
            listaItensManuais = new ArrayList<>();
        }
        return listaItensManuais;
    }

    private void atualizarSessaoListaItensManuais(List<ItensManuais> listaItensManuais) {
        request.getSession().setAttribute(SESSAO_ITENS_MANUAIS, listaItensManuais);
    }

    private void limparSessaoListaItensManuais() {
        List<ItensManuais> listaItensManuais = obterSessaoListaItensManuais();
        listaItensManuais.clear();
        atualizarSessaoListaItensManuais(listaItensManuais);
    }

    private Map<String, List<NFItem>> transformarEmJson(Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociados) {
        Map<String, List<NFItem>> map = new HashMap<>();
        Map<String, String> mapSortAux = new HashMap<>();

        itensPreAssociados.entrySet().stream().forEach(e -> {
            try {
                SC170 key = new SC170();
                List<NFItem> value = new ArrayList<>();

                key.setId(montarIdSpedC170(e.getKey()));
                key.setNome(e.getKey().getSPED0200().getDescrItem());
                key.setChaveNFe(e.getKey().getSPEDC100().getChaveNFe());
                key.setValor(e.getKey().getValor().toString());
                key.setQtd(e.getKey().getQtd());
                key.setValorIcmsSt(e.getKey().getValorBCICMSST());
                key.setValorIPI(e.getKey().getValorIPI());
                key.setEan(e.getKey().getSPED0200().getCodBarra());
                key.setCst(e.getKey().getCSTICMS());
                for (NFeItem n : e.getValue().keySet()) {
                    Float p = e.getValue().get(n);
                    NFItem item = new NFItem();

                    if (p == null) {
                        p = 0F;
                    }
                    item.setQtd(n.getQuantidade());
                    item.setId(montarIdNFeItem(n));
                    item.setNome(n.getDescricaoProduto());
                    item.setPerc(p);
                    item.setValor(n.getValorProduto().toString());
                    item.setChaveNfe(n.getNFe().getChaveNFe());
                    item.setEan(n.getCodigoEAN());
                    item.setEanTrib(n.getCodigEANTrib());
                    item.setSequencia(n.getSequencia());
                    item.setCst(n.getICMS().getCST());
                    if (n.getInfAdProd() != null) {
                        item.setInfAdProd(n.getInfAdProd());
                    }
                    try {
                        MetodosUtil mtUtil = new MetodosUtil();
                        TotalImposto ttImp = mtUtil.identificarImpostoNFe(n);
                        item.setValorIcmsSt(ttImp.getValorICMSST().toString());
                    } catch (Exception ex) {
                    }
                    if (n.getIPI() != null) {
                        if (n.getIPI().getvIPI() != null) {
                            item.setValorIpi(n.getIPI().getvIPI().toString());
                        }
                    }

                    if (n.getValorOutros() != null) {
                        item.setValorOutros(n.getValorOutros().toString());
                    }

                    item.setRelAutomatico(n.getRelAutomatico());
                    item.setRelMult(identificarRelacionamentoMultiplo(e.getKey().getSPEDC100().getListaSPEDC170().size(), n.getNFe().getItens().size()));

                    value.add(item);
                }

                Optional<NFItem> oMaxNFEItem = value.stream().max((v1, v2) -> Float.compare(v1.getPerc(), v2.getPerc()));
                if (oMaxNFEItem.isPresent()) {
                    oMaxNFEItem.get().setSelecionado(true);
                }

                List<NFItem> valueOrdenado = new ArrayList<>();
                value.stream().sorted((v1, v2) -> v1.getNome().compareTo(v2.getNome())).forEach(v -> {
                    valueOrdenado.add(v);
                });

                String sKey = gson.toJson(key);
                mapSortAux.put(key.getNome(), sKey);
                map.put(sKey, valueOrdenado);
            } catch (Exception ex) {
                System.err.println(ex);
            }
        });

        Map<String, String> mapSortAuxOrdenado = new LinkedHashMap<>();
        mapSortAux.entrySet().stream()
                .sorted(Map.Entry.<String, String>comparingByKey())
                .forEachOrdered(m -> mapSortAuxOrdenado.put(m.getKey(), m.getValue()));

        Map<String, List<NFItem>> mapOrdenado = new LinkedHashMap<>();
        mapSortAuxOrdenado.keySet().stream().map((key) -> mapSortAuxOrdenado.get(key)).forEach((sKey) -> {
            mapOrdenado.put(sKey, map.get(sKey));
        });

        return mapOrdenado;
    }

    // <editor-fold desc="Classes Internas" defaultstate="collapsed">
    public static class ProcessarParam implements Serializable {

        private String caminhoSPED;
        private String caminhoDirXMLs;
        private String pseudoIdParent;
        private String chave;
        private String idProduto;
        private String valor;
        private boolean relMultiplo;

        public ProcessarParam() {
        }

        public String getCaminhoSPED() {
            return caminhoSPED;
        }

        public void setCaminhoSPED(String caminhoSPED) {
            this.caminhoSPED = caminhoSPED;
        }

        public String getCaminhoDirXMLs() {
            return caminhoDirXMLs;
        }

        public void setCaminhoDirXMLs(String caminhoDirXMLs) {
            this.caminhoDirXMLs = caminhoDirXMLs;
        }

        public String getPseudoIdParent() {
            return pseudoIdParent;
        }

        public void setPseudoIdParent(String pseudoIdParent) {
            this.pseudoIdParent = pseudoIdParent;
        }

        public String getChave() {
            return chave;
        }

        public void setChave(String chave) {
            this.chave = chave;
        }

        public String getIdProduto() {
            return idProduto;
        }

        public void setIdProduto(String idProduto) {
            this.idProduto = idProduto;
        }

        public boolean isRelMultiplo() {
            return relMultiplo;
        }

        public void setRelMultiplo(boolean relMultiplo) {
            this.relMultiplo = relMultiplo;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }
    }

    public static class ProcessarRetorno implements Serializable {

        private String resumo;
        private Map<String, List<NFItem>> itensPreAssociados;
        private List<String> listaSped;
        private List<NFItem> listaNfe;
        private SPED0000 Sped0000;
        private List<String> listDeItensVinculadosAuto;
        private Map<String, String> mapDeCabecalhos;

        /**
         * @return the itensPreAssociados //
         */
//        public Map<String, List<NFItem>> getItensPreAssociados() {
//            return itensPreAssociados;
//        }
//        /**
//         * @param itensPreAssociados the itensPreAssociados to set
//         */
//        public void setItensPreAssociados(Map<String, List<NFItem>> itensPreAssociados) {
//            this.itensPreAssociados = itensPreAssociados;
//        }
        public String getResumo() {
            return resumo;
        }

        public void setResumo(String resumo) {
            this.resumo = resumo;
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
         * @return the listDeItensVinculadosAuto
         */
        public List<String> getListDeItensVinculadosAuto() {
            return listDeItensVinculadosAuto;
        }

        /**
         * @param listDeItensVinculadosAuto the listDeItensVinculadosAuto to set
         */
        public void setListDeItensVinculadosAuto(List<String> listDeItensVinculadosAuto) {
            this.listDeItensVinculadosAuto = listDeItensVinculadosAuto;
        }

        /**
         * @return the mapDeCabecalhos
         */
        public Map<String, String> getMapDeCabecalhos() {
            return mapDeCabecalhos;
        }

        /**
         * @param mapDeCabecalhos the mapDeCabecalhos to set
         */
        public void setMapDeCabecalhos(Map<String, String> mapDeCabecalhos) {
            this.mapDeCabecalhos = mapDeCabecalhos;
        }

        /**
         * @return the listaSped
         */
        public List<String> getListaSped() {
            return listaSped;
        }

        /**
         * @param listaSped the listaSped to set
         */
        public void setListaSped(List<String> listaSped) {
            this.listaSped = listaSped;
        }

        /**
         * @return the listaNfe
         */
        public List<NFItem> getListaNfe() {
            return listaNfe;
        }

        /**
         * @param listaNfe the listaNfe to set
         */
        public void setListaNfe(List<NFItem> listaNfe) {
            this.listaNfe = listaNfe;
        }

        /**
         * @return the itensPreAssociados
         */
        public Map<String, List<NFItem>> getItensPreAssociados() {
            return itensPreAssociados;
        }

        /**
         * @param itensPreAssociados the itensPreAssociados to set
         */
        public void setItensPreAssociados(Map<String, List<NFItem>> itensPreAssociados) {
            this.itensPreAssociados = itensPreAssociados;
        }
    }

    public static class SubListaItemRetorno implements Serializable {

        public SubListaItemRetorno(Map<NFeItem, Float> items) {
            if (items != null && items.size() > 0) {
                this.itens = new ArrayList<SubListaItem>();
                for (NFeItem item : items.keySet()) {
                    this.itens.add(new SubListaItem(item, items.get(item)));
                }
            }

        }

        private List<SubListaItem> itens;

        public List<SubListaItem> getItens() {
            return itens;
        }

        public void setItens(List<SubListaItem> itens) {
            this.itens = itens;
        }

    }

    public static class SubListaItem implements Serializable {

        private String nome;
        private String porcentagem;

        public SubListaItem(NFeItem nfeItem, Float porcentagem) {
            this.nome = nfeItem.getDescricaoProduto();
            this.porcentagem = porcentagem.toString();

        }

        public String getPorcentagem() {
            return porcentagem;
        }

        public void setPorcentagem(String porcentagem) {
            this.porcentagem = porcentagem;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

    }

    public static class SC170 implements Serializable {

        private String id;
        private String nome;
        private String chaveNFe;
        private String valor;
        private BigDecimal qtd;
        private String cst;
        private BigDecimal valorIcmsSt;
        private BigDecimal valorIPI;
        private int sequenciaNoSped;
        private String ean;
        private int qtdDeNfeRelativa;
        private BigDecimal idRef;
        private BigDecimal aliquota;
        private BigDecimal valorMva;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getChaveNFe() {
            return this.chaveNFe;
        }

        public void setChaveNFe(String chaveNFe) {
            this.chaveNFe = chaveNFe;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }

        /**
         * @return the qtd
         */
        public BigDecimal getQtd() {
            return qtd;
        }

        /**
         * @param qtd the qtd to set
         */
        public void setQtd(BigDecimal qtd) {
            this.qtd = qtd;
        }

        /**
         * @return the cst
         */
        public String getCst() {
            return cst;
        }

        /**
         * @param cst the cst to set
         */
        public void setCst(String cst) {
            this.cst = cst;
        }

        /**
         * @return the valorIcmsSt
         */
        public BigDecimal getValorIcmsSt() {
            return valorIcmsSt;
        }

        /**
         * @param valorIcmsSt the valorIcmsSt to set
         */
        public void setValorIcmsSt(BigDecimal valorIcmsSt) {
            this.valorIcmsSt = valorIcmsSt;
        }

        /**
         * @return the valorIPI
         */
        public BigDecimal getValorIPI() {
            return valorIPI;
        }

        /**
         * @param valorIPI the valorIPI to set
         */
        public void setValorIPI(BigDecimal valorIPI) {
            this.valorIPI = valorIPI;
        }

        /**
         * @return the sequenciaNoSped
         */
        public int getSequenciaNoSped() {
            return sequenciaNoSped;
        }

        /**
         * @param sequenciaNoSped the sequenciaNoSped to set
         */
        public void setSequenciaNoSped(int sequenciaNoSped) {
            this.sequenciaNoSped = sequenciaNoSped;
        }

        /**
         * @return the ean
         */
        public String getEan() {
            return ean;
        }

        /**
         * @param ean the ean to set
         */
        public void setEan(String ean) {
            this.ean = ean;
        }

        /**
         * @return the qtdDeNfeRelativa
         */
        public int getQtdDeNfeRelativa() {
            return qtdDeNfeRelativa;
        }

        /**
         * @param qtdDeNfeRelativa the qtdDeNfeRelativa to set
         */
        public void setQtdDeNfeRelativa(int qtdDeNfeRelativa) {
            this.qtdDeNfeRelativa = qtdDeNfeRelativa;
        }

        /**
         * @return the idRef
         */
        public BigDecimal getIdRef() {
            return idRef;
        }

        /**
         * @param idRef the idRef to set
         */
        public void setIdRef(BigDecimal idRef) {
            this.idRef = idRef;
        }

        /**
         * @return the aliquota
         */
        public BigDecimal getAliquota() {
            return aliquota;
        }

        /**
         * @param aliquota the aliquota to set
         */
        public void setAliquota(BigDecimal aliquota) {
            this.aliquota = aliquota;
        }

        /**
         * @return the valorMva
         */
        public BigDecimal getValorMva() {
            return valorMva;
        }

        /**
         * @param valorMva the valorMva to set
         */
        public void setValorMva(BigDecimal valorMva) {
            this.valorMva = valorMva;
        }

    }

    public static class ProcessarItensManuaisParam implements Serializable {

        private List<ItensManuais> itensManuais;

        public List<ItensManuais> getItensManuais() {
            return itensManuais;
        }

        public void setItensManuais(List<ItensManuais> itensManuais) {
            this.itensManuais = itensManuais;
        }
    }

    public static class ItensManuais implements Serializable {

        private String idLinhaNfeSped;
        private String idSpedC170;
        private List<String> idNFeItem;
        private boolean substituir;

        public String getIdLinhaNfeSped() {
            return idLinhaNfeSped;
        }

        public void setIdLinhaNfeSped(String idLinhaNfeSped) {
            this.idLinhaNfeSped = idLinhaNfeSped;
        }

        public String getIdSpedC170() {
            return idSpedC170;
        }

        public void setIdSpedC170(String idSpedC170) {
            this.idSpedC170 = idSpedC170;
        }

        public List<String> getIdNFeItem() {
            return idNFeItem;
        }

        public void setIdNFeItem(List<String> idNFeItem) {
            this.idNFeItem = idNFeItem;
        }

        public boolean isSubstituir() {
            return substituir;
        }

        public void setSubstituir(boolean substituir) {
            this.substituir = substituir;
        }
    }

    public static class ChecarProgressoRet implements Serializable {

        private int total;
        private int atual;
        private String descricao;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getAtual() {
            return atual;
        }

        public void setAtual(int atual) {
            this.atual = atual;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }
    }

    public static class InformacoesDaTabelaDeItensVinculadosAUTO {

        private String chave;
        private String descricaoSped;
        private String descricaoNfe;
        private int sequenciaSped;
        private int sequenciaNfe;
        private Date dataVinculacao;
        private String filto;

        private BigDecimal valorS;
        private BigDecimal qtdS;
        private String eanS;
        private String valor;
        private String ean;
        private String eanTrib;
        private BigDecimal qtd;

        private String linhaOriginal;
        private String linhaAlterada;

        /**
         * @return the chave
         */
        public String getChave() {
            return chave;
        }

        /**
         * @param chave the chave to set
         */
        public void setChave(String chave) {
            this.chave = chave;
        }

        /**
         * @return the descricaoSped
         */
        public String getDescricaoSped() {
            return descricaoSped;
        }

        /**
         * @param descricaoSped the descricaoSped to set
         */
        public void setDescricaoSped(String descricaoSped) {
            this.descricaoSped = descricaoSped;
        }

        /**
         * @return the descricaoNfe
         */
        public String getDescricaoNfe() {
            return descricaoNfe;
        }

        /**
         * @param descricaoNfe the descricaoNfe to set
         */
        public void setDescricaoNfe(String descricaoNfe) {
            this.descricaoNfe = descricaoNfe;
        }

        /**
         * @return the sequenciaSped
         */
        public int getSequenciaSped() {
            return sequenciaSped;
        }

        /**
         * @param sequenciaSped the sequenciaSped to set
         */
        public void setSequenciaSped(int sequenciaSped) {
            this.sequenciaSped = sequenciaSped;
        }

        /**
         * @return the sequenciaNfe
         */
        public int getSequenciaNfe() {
            return sequenciaNfe;
        }

        /**
         * @param sequenciaNfe the sequenciaNfe to set
         */
        public void setSequenciaNfe(int sequenciaNfe) {
            this.sequenciaNfe = sequenciaNfe;
        }

        /**
         * @return the dataVinculacao
         */
        public Date getDataVinculacao() {
            return dataVinculacao;
        }

        /**
         * @param dataVinculacao the dataVinculacao to set
         */
        public void setDataVinculacao(Date dataVinculacao) {
            this.dataVinculacao = dataVinculacao;
        }

        /**
         * @return the filto
         */
        public String getFilto() {
            return filto;
        }

        /**
         * @return the eanS
         */
        public String getEanS() {
            return eanS;
        }

        /**
         * @param eanS the eanS to set
         */
        public void setEanS(String eanS) {
            this.eanS = eanS;
        }

        /**
         * @param filto the filto to set
         */
        public void setFilto(String filto) {
            this.filto = filto;
        }

        /**
         * @return the linhaOriginal
         */
        public String getLinhaOriginal() {
            return linhaOriginal;
        }

        /**
         * @param linhaOriginal the linhaOriginal to set
         */
        public void setLinhaOriginal(String linhaOriginal) {
            this.linhaOriginal = linhaOriginal;
        }

        /**
         * @return the linhaAlterada
         */
        public String getLinhaAlterada() {
            return linhaAlterada;
        }

        /**
         * @param linhaAlterada the linhaAlterada to set
         */
        public void setLinhaAlterada(String linhaAlterada) {
            this.linhaAlterada = linhaAlterada;
        }

        /**
         * @return the qtdS
         */
        public BigDecimal getQtdS() {
            return qtdS;
        }

        /**
         * @param qtdS the qtdS to set
         */
        public void setQtdS(BigDecimal qtdS) {
            this.qtdS = qtdS;
        }

        /**
         * @return the cstS
         */
        /**
         * @return the valorS
         */
        public BigDecimal getValorS() {
            return valorS;
        }

        /**
         * @param valorS the valorS to set
         */
        public void setValorS(BigDecimal valorS) {
            this.valorS = valorS;
        }

        /**
         * @return the valor
         */
        public String getValor() {
            return valor;
        }

        /**
         * @param valor the valor to set
         */
        public void setValor(String valor) {
            this.valor = valor;
        }

        public String getEan() {
            return ean;
        }

        /**
         * @param ean the ean to set
         */
        public void setEan(String ean) {
            this.ean = ean;
        }

        /**
         * @return the eanTrib
         */
        public String getEanTrib() {
            return eanTrib;
        }

        /**
         * @param eanTrib the eanTrib to set
         */
        public void setEanTrib(String eanTrib) {
            this.eanTrib = eanTrib;
        }

        /**
         * @return the qtd
         */
        public BigDecimal getQtd() {
            return qtd;
        }

        /**
         * @param qtd the qtd to set
         */
        public void setQtd(BigDecimal qtd) {
            this.qtd = qtd;
        }

    }

    public static class InformacoesDoCabecalho {

        private String chave;
        private BigDecimal vtNoC100;
        private BigDecimal vtNaXML;
        private BigDecimal vtDaST;
        private int itensParaVincular;
        private int totalDeItensNaNota;
        private int totalDeItensNoSped;
        private BigDecimal diferencaC100XML;
        private BigDecimal totalIcmsStPorNota;
        private BigDecimal totalIpiPorNota;
        private BigDecimal totalC170;
        private BigDecimal totalDescontos;
        private String tokenCliente;

        /**
         * @return the chave
         */
        public String getChave() {
            return chave;
        }

        /**
         * @param chave the chave to set
         */
        public void setChave(String chave) {
            this.chave = chave;
        }

        /**
         * @return the vtNoC100
         */
        public BigDecimal getVtNoC100() {
            return vtNoC100;
        }

        /**
         * @param vtNoC100 the vtNoC100 to set
         */
        public void setVtNoC100(BigDecimal vtNoC100) {
            this.vtNoC100 = vtNoC100;
        }

        /**
         * @return the vtNaXML
         */
        public BigDecimal getVtNaXML() {
            return vtNaXML;
        }

        /**
         * @param vtNaXML the vtNaXML to set
         */
        public void setVtNaXML(BigDecimal vtNaXML) {
            this.vtNaXML = vtNaXML;
        }

        /**
         * @return the vtDaST
         */
        public BigDecimal getVtDaST() {
            return vtDaST;
        }

        /**
         * @param vtDaST the vtDaST to set
         */
        public void setVtDaST(BigDecimal vtDaST) {
            this.vtDaST = vtDaST;
        }

        /**
         * @return the itensParaVincular
         */
        public int getItensParaVincular() {
            return itensParaVincular;
        }

        /**
         * @param itensParaVincular the itensParaVincular to set
         */
        public void setItensParaVincular(int itensParaVincular) {
            this.itensParaVincular = itensParaVincular;
        }

        /**
         * @return the totalDeItensNaNota
         */
        public int getTotalDeItensNaNota() {
            return totalDeItensNaNota;
        }

        /**
         * @param totalDeItensNaNota the totalDeItensNaNota to set
         */
        public void setTotalDeItensNaNota(int totalDeItensNaNota) {
            this.totalDeItensNaNota = totalDeItensNaNota;
        }

        /**
         * @return the totalDeItensNoSped
         */
        public int getTotalDeItensNoSped() {
            return totalDeItensNoSped;
        }

        /**
         * @param totalDeItensNoSped the totalDeItensNoSped to set
         */
        public void setTotalDeItensNoSped(int totalDeItensNoSped) {
            this.totalDeItensNoSped = totalDeItensNoSped;
        }

        /**
         * @return the diferencaC100XML
         */
        public BigDecimal getDiferencaC100XML() {
            return diferencaC100XML;
        }

        /**
         * @param diferencaC100XML the diferencaC100XML to set
         */
        public void setDiferencaC100XML(BigDecimal diferencaC100XML) {
            this.diferencaC100XML = diferencaC100XML;
        }

        /**
         * @return the totalIcmsStPorNota
         */
        public BigDecimal getTotalIcmsStPorNota() {
            return totalIcmsStPorNota;
        }

        /**
         * @param totalIcmsStPorNota the totalIcmsStPorNota to set
         */
        public void setTotalIcmsStPorNota(BigDecimal totalIcmsStPorNota) {
            this.totalIcmsStPorNota = totalIcmsStPorNota;
        }

        /**
         * @return the totalIpiPorNota
         */
        public BigDecimal getTotalIpiPorNota() {
            return totalIpiPorNota;
        }

        /**
         * @param totalIpiPorNota the totalIpiPorNota to set
         */
        public void setTotalIpiPorNota(BigDecimal totalIpiPorNota) {
            this.totalIpiPorNota = totalIpiPorNota;
        }

        /**
         * @return the totalC170
         */
        public BigDecimal getTotalC170() {
            return totalC170;
        }

        /**
         * @param totalC170 the totalC170 to set
         */
        public void setTotalC170(BigDecimal totalC170) {
            this.totalC170 = totalC170;
        }

        /**
         * @return the totalDescontos
         */
        public BigDecimal getTotalDescontos() {
            return totalDescontos;
        }

        /**
         * @param totalDescontos the totalDescontos to set
         */
        public void setTotalDescontos(BigDecimal totalDescontos) {
            this.totalDescontos = totalDescontos;
        }

        /**
         * @return the tokenCliente
         */
        public String getTokenCliente() {
            return tokenCliente;
        }

        /**
         * @param tokenCliente the tokenCliente to set
         */
        public void setTokenCliente(String tokenCliente) {
            this.tokenCliente = tokenCliente;
        }
    }

    public static class informacoesDoItemNoWsMix {

        private String eiCst;
        private String eiAliquota;
        private String eiReducao;
        private String eiMva;
        private String eiFundamento;

        private String edCst;
        private String edAliquota;
        private String edReducao;
        private String edMva;
        private String edFundamento;

        private String svcCst;
        private String svcAliquota;
        private String svcReducao;
        private String svcMva;
        private String svcFundamento;

        private String sncCst;
        private String sncAliquota;
        private String sncReducao;
        private String sncMva;
        private String sncFundamento;

        /**
         * @return the eiCst
         */
        public String getEiCst() {
            return eiCst;
        }

        /**
         * @param eiCst the eiCst to set
         */
        public void setEiCst(String eiCst) {
            this.eiCst = eiCst;
        }

        /**
         * @return the eiAliquota
         */
        public String getEiAliquota() {
            return eiAliquota;
        }

        /**
         * @param eiAliquota the eiAliquota to set
         */
        public void setEiAliquota(String eiAliquota) {
            this.eiAliquota = eiAliquota;
        }

        /**
         * @return the eiReducao
         */
        public String getEiReducao() {
            return eiReducao;
        }

        /**
         * @param eiReducao the eiReducao to set
         */
        public void setEiReducao(String eiReducao) {
            this.eiReducao = eiReducao;
        }

        /**
         * @return the eiMva
         */
        public String getEiMva() {
            return eiMva;
        }

        /**
         * @param eiMva the eiMva to set
         */
        public void setEiMva(String eiMva) {
            this.eiMva = eiMva;
        }

        /**
         * @return the eiFundamento
         */
        public String getEiFundamento() {
            return eiFundamento;
        }

        /**
         * @param eiFundamento the eiFundamento to set
         */
        public void setEiFundamento(String eiFundamento) {
            this.eiFundamento = eiFundamento;
        }

        /**
         * @return the edCst
         */
        public String getEdCst() {
            return edCst;
        }

        /**
         * @param edCst the edCst to set
         */
        public void setEdCst(String edCst) {
            this.edCst = edCst;
        }

        /**
         * @return the edAliquota
         */
        public String getEdAliquota() {
            return edAliquota;
        }

        /**
         * @param edAliquota the edAliquota to set
         */
        public void setEdAliquota(String edAliquota) {
            this.edAliquota = edAliquota;
        }

        /**
         * @return the edReducao
         */
        public String getEdReducao() {
            return edReducao;
        }

        /**
         * @param edReducao the edReducao to set
         */
        public void setEdReducao(String edReducao) {
            this.edReducao = edReducao;
        }

        /**
         * @return the edMva
         */
        public String getEdMva() {
            return edMva;
        }

        /**
         * @param edMva the edMva to set
         */
        public void setEdMva(String edMva) {
            this.edMva = edMva;
        }

        /**
         * @return the edFundamento
         */
        public String getEdFundamento() {
            return edFundamento;
        }

        /**
         * @param edFundamento the edFundamento to set
         */
        public void setEdFundamento(String edFundamento) {
            this.edFundamento = edFundamento;
        }

        /**
         * @return the svcCst
         */
        public String getSvcCst() {
            return svcCst;
        }

        /**
         * @param svcCst the svcCst to set
         */
        public void setSvcCst(String svcCst) {
            this.svcCst = svcCst;
        }

        /**
         * @return the svcAliquota
         */
        public String getSvcAliquota() {
            return svcAliquota;
        }

        /**
         * @param svcAliquota the svcAliquota to set
         */
        public void setSvcAliquota(String svcAliquota) {
            this.svcAliquota = svcAliquota;
        }

        /**
         * @return the svcReducao
         */
        public String getSvcReducao() {
            return svcReducao;
        }

        /**
         * @param svcReducao the svcReducao to set
         */
        public void setSvcReducao(String svcReducao) {
            this.svcReducao = svcReducao;
        }

        /**
         * @return the svcMva
         */
        public String getSvcMva() {
            return svcMva;
        }

        /**
         * @param svcMva the svcMva to set
         */
        public void setSvcMva(String svcMva) {
            this.svcMva = svcMva;
        }

        /**
         * @return the svcFundamento
         */
        public String getSvcFundamento() {
            return svcFundamento;
        }

        /**
         * @param svcFundamento the svcFundamento to set
         */
        public void setSvcFundamento(String svcFundamento) {
            this.svcFundamento = svcFundamento;
        }

        /**
         * @return the sncCst
         */
        public String getSncCst() {
            return sncCst;
        }

        /**
         * @param sncCst the sncCst to set
         */
        public void setSncCst(String sncCst) {
            this.sncCst = sncCst;
        }

        /**
         * @return the sncAliquota
         */
        public String getSncAliquota() {
            return sncAliquota;
        }

        /**
         * @param sncAliquota the sncAliquota to set
         */
        public void setSncAliquota(String sncAliquota) {
            this.sncAliquota = sncAliquota;
        }

        /**
         * @return the sncReducao
         */
        public String getSncReducao() {
            return sncReducao;
        }

        /**
         * @param sncReducao the sncReducao to set
         */
        public void setSncReducao(String sncReducao) {
            this.sncReducao = sncReducao;
        }

        /**
         * @return the sncMva
         */
        public String getSncMva() {
            return sncMva;
        }

        /**
         * @param sncMva the sncMva to set
         */
        public void setSncMva(String sncMva) {
            this.sncMva = sncMva;
        }

        /**
         * @return the sncFundamento
         */
        public String getSncFundamento() {
            return sncFundamento;
        }

        /**
         * @param sncFundamento the sncFundamento to set
         */
        public void setSncFundamento(String sncFundamento) {
            this.sncFundamento = sncFundamento;
        }

    }

    // </editor-fold> 
    private Map<String, List<NFItem>> transformarMapDeVinculosManuaisJSON(Map<SPEDC170, Map<NFeItem, Float>> mapDeVinculadosManuais) {
        Map<String, List<NFItem>> mapSpeds = new HashMap();
        mapDeVinculadosManuais.entrySet().stream().forEach(e -> {
            SC170 key = new SC170();
            key.setId(montarIdSpedC170(e.getKey()));
            key.setSequenciaNoSped(e.getKey().getSequencia());
            key.setNome(e.getKey().getSPED0200().getDescrItem());
            key.setChaveNFe(e.getKey().getSPEDC100().getChaveNFe());
            key.setValor(e.getKey().getValor().toString());
            key.setQtd(e.getKey().getQtd());
            key.setValorIcmsSt(e.getKey().getValorICMSST());
            key.setValorIPI(e.getKey().getValorIPI());
            key.setEan(e.getKey().getSPED0200().getCodBarra());
            key.setCst(e.getKey().getCSTICMS());
            key.setQtdDeNfeRelativa(e.getValue().keySet().size());
            key.setIdRef(e.getKey().getIdReferenciaComNFe());
            String sKey = gson.toJson(key);
            mapSpeds.put(sKey, criaListaDeVinculosManuaisNfe(e.getValue()));

        });
        return mapSpeds;
    }

    private List<NFItem> criaListaDeVinculosManuaisNfe(Map<NFeItem, Float> mapDeVinculadosManuais) {

        List<NFItem> listaNfe = new ArrayList();
        mapDeVinculadosManuais.entrySet().stream().forEach(e -> {
            if (e.getKey() != null) {
                {
                    //Float p = e.getKey();
                    NFItem item = new NFItem();
                    try {
                        //  if (p == null || p.isNaN()){
                        //   p = 0F;}
                        item.setQtd(e.getKey().getQuantidade());
                        item.setId(montarIdNFeItem(e.getKey()));
                        item.setNome(e.getKey().getDescricaoProduto());
                        //item.setPerc(p);
                        item.setValor(e.getKey().getValorProduto().toString());
                        item.setChaveNfe(e.getKey().getNFe().getChaveNFe());
                        item.setEan(e.getKey().getCodigoEAN());
                        item.setEanTrib(e.getKey().getCodigEANTrib());
                        item.setSequencia(e.getKey().getSequencia());
                        item.setCst(e.getKey().getICMS().getCST());
                        item.setDespesaAcessoria(e.getKey().getValorOutros());
                        if (e.getKey().getInfAdProd() != null) {
                            item.setInfAdProd(e.getKey().getInfAdProd());
                        }

                        MetodosUtil mtUtil = new MetodosUtil();
                        TotalImposto ttImp = mtUtil.identificarImpostoNFe(e.getKey());
                        item.setValorIcmsSt(ttImp.getValorICMSST().toString());
                    } catch (Exception ex) {
                    }
                    if (e.getKey().getIPI() != null) {
                        if (e.getKey().getIPI().getvIPI() != null) {
                            item.setValorIpi(e.getKey().getIPI().getvIPI().toString());
                        }
                    }

                    if (e.getKey().getValorOutros() != null) {
                        item.setValorOutros(e.getKey().getValorOutros().toString());
                    }

                    //item.setRelAutomatico(n.getRelAutomatico());
                    //  item.setRelMult(identificarRelacionamentoMultiplo(e.getKey().getSPEDC100().getListaSPEDC170().size(),n.getNFe().getItens().size()));
                    listaNfe.add(item);
                }
            }
        });
        return listaNfe;
    }

    private List<String> tranformarOsItensVinculadosAutoEmJSON(Map<SPEDC170, NFeItem[]> mapTest) {
        //List<NFItem> listaNfe = new ArrayList();
        List<String> objTabela = new ArrayList();
        mapTest.forEach((k, v) -> {
            InformacoesDaTabelaDeItensVinculadosAUTO key = new InformacoesDaTabelaDeItensVinculadosAUTO();
            key.setChave(k.getSPEDC100().getChaveNFe());
            key.setDescricaoSped(k.getSPED0200().getDescricaoCustom());
            key.setSequenciaSped(k.getSequencia());
            key.setFilto(k.getFiltroDeRelacionamento());
            key.setValorS(k.getValor());
            key.setQtdS(k.getQtd());
            key.setLinhaOriginal(k.getLinhaOriginal());
            key.setLinhaAlterada(k.getLinhaComModificacoes());
            key.setEanS(k.getSPED0200().getCodBarra());

            for (NFeItem nfe : v) {
                key.setDescricaoNfe(nfe.getDescricaoProdutoCustom());
                key.setSequenciaNfe(nfe.getSequencia());
                key.setValor(nfe.getValorProduto().toString());
                key.setQtd(nfe.getQuantidade());
                key.setEan(nfe.getCodigoEAN());
                key.setEanTrib(nfe.getCodigEANTrib());
            }
            String sKey = gson.toJson(key);
            objTabela.add(sKey);
        });
        return objTabela;
    }

    private Set<String> encontrarChavesDistintas(Map<SPEDC170, Map<NFeItem, Float>> mapDeVinculadosManuais) {
        Set<String> chavesDistintas = new HashSet();
        mapDeVinculadosManuais.entrySet().stream().forEach(e -> {
            chavesDistintas.add(e.getKey().getSPEDC100().getChaveNFe());
        });
        return chavesDistintas;
    }

    private Map<String, String> criarOsCabecalhos(Map<SPEDC170, Map<NFeItem, Float>> mapDeVinculadosManuais, Set<String> chavesDistintas, Map<SPEDC170, NFeItem[]> mapDeVinculadosAuto, ProcessadorSPED asProdFor, SPED0000 sped0000) {
        Cliente cliente = new Cliente();
        try {
            cliente = obterTokenDoCliente(sped0000);
            this.tokenCliente = cliente.getToken();
        } catch (Exception ex) {
        }

        List<String> listaAux = new ArrayList();
        Map<String, String> mapDosCabecalhosEncontrados = new HashMap();

        chavesDistintas.stream().forEach(chave -> {
            mapDeVinculadosManuais.entrySet().stream().forEach(e -> {
                if (!listaAux.contains(chave)) {
                    if (chave.equals(e.getKey().getSPEDC100().getChaveNFe())) {
                        listaAux.add(chave);
                        for (NFeItem nfe : e.getValue().keySet()) {
                            mapDosCabecalhosEncontrados.put(chave,
                                    instanciarObgCabelhoComSeusValores(e.getKey(),
                                            nfe,
                                            asProdFor,
                                            chave,
                                            this.tokenCliente));
                        }
                    }
                }
            });
        });
        return mapDosCabecalhosEncontrados;
    }

    private String instanciarObgCabelhoComSeusValores(SPEDC170 sped, NFeItem nfe, ProcessadorSPED asProdFor, String chave, String token) {

        InformacoesDoCabecalho cabecalho = new InformacoesDoCabecalho();
        // encontrarValorTotalC170(cabecalho,asProdFor,chave);
        cabecalho.setChave(sped.getSPEDC100().getChaveNFe());
        cabecalho.setVtNoC100(sped.getSPEDC100().getValorTotalDocumento());
        cabecalho.setTotalDeItensNoSped(sped.getSPEDC100().getListaSPEDC170().size());
        cabecalho.setVtNaXML(nfe.getNFe().getValorNotaFiscal());
        cabecalho.setVtDaST(nfe.getNFe().getValorTotalICMSST());
        cabecalho.setTotalDeItensNaNota(nfe.getNFe().getItens().size());
        cabecalho.setDiferencaC100XML(cabecalho.getVtNoC100().subtract(cabecalho.getVtDaST()));
        cabecalho.setTotalDescontos(nfe.getNFe().getValorTotalDesconto());
        cabecalho.setTokenCliente(token);
        encontrarValorTotalC170(cabecalho, asProdFor, chave);
        String objCabecalhoTransformado = gson.toJson(cabecalho);
        return objCabecalhoTransformado;
    }

    private void encontrarValorTotalC170(InformacoesDoCabecalho cabecalho, ProcessadorSPED asProdFor, String chavesDistintas) {
        BigDecimal totalC170 = BigDecimal.ZERO;
        BigDecimal totalIcmsSt = BigDecimal.ZERO;
        BigDecimal totalIPI = BigDecimal.ZERO;
        for (SPEDC100 spedc100 : asProdFor.getListaC100()) {
            if (spedc100.getChaveNFe().equals(chavesDistintas)) {
                for (SPEDC170 spedc170 : spedc100.getListaSPEDC170()) {
                    totalC170 = totalC170.add(spedc170.getValor());
                    totalIcmsSt = totalIcmsSt.add(spedc170.getValorICMSST());
                    totalIPI = totalIPI.add(spedc170.getValorIPI());
                }
            }
        }

        cabecalho.setTotalC170(totalC170);
        cabecalho.setTotalIcmsStPorNota(totalIcmsSt);
        cabecalho.setTotalIpiPorNota(totalIPI);
    }

    private Cliente obterTokenDoCliente(SPED0000 sped000) {
        ClienteService clienteSvc = new ClienteService();
        Cliente cliente = new Cliente();
        cliente.setCnpj(sped000.getCNPJ());

        try {
            cliente = clienteSvc.selecionarUm(cliente);
        } catch (Exception ex) {
        }
        //verifica se o cliente tem o token
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente nao tem token cadastrado");
        }

        return cliente;
    }

    @POST
    @Path("/consultarWsMix")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ClassificacaoTributariaProduto consultaWsMix(String ean) throws DAOException {

        try {
            //ConstroyerHibernateUtil.beginTransaction();                        
            ClassificacaoTributariaProdutoDAO classficacaoDAO = new ClassificacaoTributariaProdutoDAO();
            ClassificacaoTributariaProduto prod = new ClassificacaoTributariaProduto();
            prod.setEan(ean);
            prod = classficacaoDAO.selecionarUm(prod);
            return prod;
        } catch (Exception ex) {
            log.error(String.format("Houve um erro ao tentar incluir o Produto '%s'. Mensagem: %s"), ex);
            //ConstroyerHibernateUtil.rollbackCurrentTransaction();
        }
        return null;
    }
}
