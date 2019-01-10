package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.dao.own.CFOPDAO;
import br.com.mixfiscal.prodspedxnfe.dao.own.CSTDAO;
import br.com.mixfiscal.prodspedxnfe.dao.own.RelacaoProdutoFornecedorDAO;
//import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
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
import br.com.mixfiscal.prodspedxnfe.domain.nfe.IPI;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC170;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFeItem;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.TotalImposto;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOP;
import br.com.mixfiscal.prodspedxnfe.domain.own.CST;
import br.com.mixfiscal.prodspedxnfe.domain.own.RelacaoProdutoFornecedor;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;

import static java.lang.Math.log;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.Optional;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MetodosUtil {
	private BigDecimal valorTotalStIcmsIpi;
	private Map<idxSPEDC170, List<NFeItem>> listaDeCombinacoesEncontrados = new HashMap<>();
	private BigDecimal contadorDeConjuntos = new BigDecimal(1);
	private final DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	private final DecimalFormat df = new DecimalFormat("##0.##");
	private Logger log;

	public MetodosUtil() {
		log = LogManager.getLogger(MetodosUtil.class);
		this.valorTotalStIcmsIpi = BigDecimal.ZERO;
		this.df.setDecimalFormatSymbols(dfs);

	}

	public BigDecimal getValorTotalStIcmsIpi() {
		return valorTotalStIcmsIpi;
	}

	public BigDecimal getContadorDeConjuntos() {
		return contadorDeConjuntos;
	}

	public void setContadorDeConjuntos(BigDecimal contadorDeConjuntos) {
		this.contadorDeConjuntos = contadorDeConjuntos;
	}

	/*
	 * codogo comentado original do andrei private Map<BigDecimal,List<BigDecimal>>
	 * listaDeCombinacoesEncontrados = new HashMap<>(); private BigDecimal
	 * contadorDeConjuntos = new BigDecimal(1);
	 * 
	 * private void ordenarLista (List<BigDecimal> valores){ BigDecimal valorAux =
	 * new BigDecimal(0);
	 * 
	 * for (int i = 0; i < valores.size(); i++) { for (int j = i + 1; j <
	 * valores.size(); j++) { BigDecimal primeiraPosicao = valores.get(i);
	 * BigDecimal posicaoAux = valores.get(j); if
	 * (primeiraPosicao.compareTo(posicaoAux) > 1) { valorAux = valores.get(i);
	 * valores.set(i, valores.get(j)); valores.set(j, valorAux); } } } }
	 * 
	 * public void identificarCombinacoesPossiveisDoSpedComNfe(BigDecimal
	 * valor,List<BigDecimal> valores){ ordenarLista(valores); BigDecimal totalSped
	 * = valor; for (int i = 0; i < valores.size(); i++) {
	 * identificaPosicaoDaListaParaVerificacao(i,valores,totalSped); }
	 * contadorDeConjuntos = BigDecimal.ZERO; }
	 * 
	 * private void identificaPosicaoDaListaParaVerificacao (int
	 * index,List<BigDecimal> valores,BigDecimal totalSped){ for(int i =
	 * valores.size()-1; i >= index; i--) { percorrerLista(i,
	 * index,valores,totalSped); } }
	 * 
	 * private void percorrerLista (int posicao,int index,List<BigDecimal>
	 * valores,BigDecimal totalSped ){ List<BigDecimal> listaAux = new
	 * ArrayList<>(); for(int i = posicao; i >= index; i--) {
	 * listaAux.add(valores.get(i)); } BigDecimal totalValoresCombinados =
	 * testarListaEmDecremento(totalSped,valores,listaAux);
	 * testarPercorrendoLista(posicao,
	 * totalValoresCombinados,valores,totalSped,listaAux); }
	 * 
	 * private BigDecimal testarListaEmDecremento (BigDecimal
	 * totalSped,List<BigDecimal> valores,List<BigDecimal> listaAux){ BigDecimal
	 * totalValoresCombinados; if (listaAux.size() == 1) { totalValoresCombinados =
	 * listaAux.get(0); } else { totalValoresCombinados = BigDecimal.ZERO; for
	 * (BigDecimal valor : listaAux) { totalValoresCombinados =
	 * totalValoresCombinados.add(valor); } } if
	 * (totalValoresCombinados.compareTo(totalSped) == 0) {
	 * adicionaConjuntosDasCombinacoesEncontradas(listaAux); } return
	 * totalValoresCombinados; }
	 * 
	 * private void testarPercorrendoLista (int posicao, BigDecimal
	 * total,List<BigDecimal> valores,BigDecimal totalSped,List<BigDecimal>
	 * listaAux){ for (int i =posicao+2 ;i< valores.size(); i++) { BigDecimal soma =
	 * BigDecimal.ZERO; soma = soma.add(valores.get(i)); soma = soma.add(total);
	 * 
	 * if (soma.compareTo(totalSped) == 0) { listaAux.add(valores.get(i));
	 * listaDeCombinacoesEncontrados.put(contadorDeConjuntos,listaAux);
	 * contadorDeConjuntos = contadorDeConjuntos.add(new BigDecimal (1)); } }
	 * //listaAux.clear(); }
	 * 
	 * private void adicionaConjuntosDasCombinacoesEncontradas (List<BigDecimal>
	 * listaAux){ listaDeCombinacoesEncontrados.put(contadorDeConjuntos,listaAux);
	 * contadorDeConjuntos = contadorDeConjuntos.add(new BigDecimal (1)); }
	 * 
	 * public Map<BigDecimal,List<BigDecimal>> getListaDeCombinacoesEncontrados(){
	 * return listaDeCombinacoesEncontrados; }
	 */

	// public void identificarCombinacoesPossiveisDoSpedComNfe(SPEDC170
	// valor,List<NFeItem> valores,boolean incluirImpostos){
	// //valores.forEach(v->System.out.println("antes" + v.getValorProduto()));
	// Collections.sort(valores);// ordena por valor
	// //System.out.println("-------------------------------------------------");
	// // valores.forEach(v->System.out.println("depois" + v.getValorProduto()));
	// SPEDC170 totalSped = valor;
	public void identificarCombinacoesPossiveisDoSpedComNfe(SPEDC170 valor, List<NFeItem> valores) {
		Collections.sort(valores);// ordena por valor
		Collections.reverse(valores);
		SPEDC170 totalSped = valor;
		BigDecimal v = valor.getValor();
		for (int i = 0; i < valores.size(); i++) {
			identificaPosicaoDaListaParaVerificacao(i, valores, totalSped);
		}
		contadorDeConjuntos = BigDecimal.ZERO;
	}

	// for (int i = 0; i < valores.size(); i++)
	// {
	// BigDecimal valorItemNfe = valores.get(i).getValorProduto();
	// if(incluirImpostos)
	// valorItemNfe = valores.get(i).getValorTotal();
	//
	// if(valor.getValor().compareTo(valorItemNfe) == 0)continue;
	// identificaPosicaoDaListaParaVerificacao(i,valores,totalSped);
	// }
	// contadorDeConjuntos = BigDecimal.ZERO;
	// }
	//
	private void identificaPosicaoDaListaParaVerificacao(int index, List<NFeItem> valores, SPEDC170 totalSped) {
		for (int i = valores.size() - 1; i >= index; i--) {
			percorrerLista(i, index, valores, totalSped);
		}
	}

	private void percorrerLista(int posicao, int index, List<NFeItem> valores, SPEDC170 totalSped) {
		List<NFeItem> listaAux = new ArrayList<>();
		for (int i = posicao; i >= index; i--) {
			listaAux.add(valores.get(i));
		}
		BigDecimal totalValoresCombinados = testarListaEmDecremento(totalSped, valores, listaAux);
		testarPercorrendoLista(posicao, totalValoresCombinados, valores, totalSped, listaAux);
	}

	private BigDecimal testarListaEmDecremento(SPEDC170 totalSped, List<NFeItem> valores, List<NFeItem> listaAux) {
		BigDecimal totalValoresCombinados;
		if (listaAux.size() == 1) {
			BigDecimal valorItemNfe = listaAux.get(0).getValorProduto();
			totalValoresCombinados = valorItemNfe;
		} else {
			totalValoresCombinados = BigDecimal.ZERO;
			for (NFeItem valor : listaAux) {
				BigDecimal valorItemNfe = valor.getValorProduto();
				totalValoresCombinados = totalValoresCombinados.add(valorItemNfe);
			}
		}
		if (totalValoresCombinados.compareTo(totalSped.getValor()) == 0) {
			adicionaConjuntosDasCombinacoesEncontradas(totalSped, listaAux);
		}
		return totalValoresCombinados;
	}

	private void testarPercorrendoLista(int posicao, BigDecimal total, List<NFeItem> valores, SPEDC170 totalSped,
			List<NFeItem> listaAux) {
		for (int i = posicao + 2; i < valores.size(); i++) {
			BigDecimal soma = BigDecimal.ZERO;
			BigDecimal valorItemNfe = valores.get(i).getValorProduto();
			soma = soma.add(valorItemNfe);
			soma = soma.add(total);
			if (soma.compareTo(totalSped.getValor()) == 0) {
				listaAux.add(valores.get(i));
				idxSPEDC170 spedIdx = new idxSPEDC170();
				spedIdx.setIndex(contadorDeConjuntos);
				spedIdx.setSped(totalSped);
				listaDeCombinacoesEncontrados.put(spedIdx, listaAux);
				contadorDeConjuntos = contadorDeConjuntos.add(new BigDecimal(1));
			}
		}
		// listaAux.clear();
	}

	private void adicionaConjuntosDasCombinacoesEncontradas(SPEDC170 totalSped, List<NFeItem> listaAux) {
		idxSPEDC170 spedIdx = new idxSPEDC170();
		spedIdx.setIndex(contadorDeConjuntos);
		spedIdx.setSped(totalSped);
		listaDeCombinacoesEncontrados.put(spedIdx, listaAux);
		contadorDeConjuntos = contadorDeConjuntos.add(new BigDecimal(1));
	}

	public Map<idxSPEDC170, List<NFeItem>> getListaDeCombinacoesEncontrados() {
		return listaDeCombinacoesEncontrados;
	}

	public NFeItem inicializarTotalizadorIcmsSTRelacionados(NFeItem nfeItem, BigDecimal valorIcmsST) {
		if (nfeItem.getICMS() instanceof ICMS10) {
			ICMS10 icms = (ICMS10) nfeItem.getICMS();
			icms.setValorICMSST(valorIcmsST);
			icms.setValorBCST(BigDecimal.ZERO);
			icms.setAliquotaICMSST(BigDecimal.ZERO);
			nfeItem.setICMS(icms);
			// log.info(String.format("o ICMS é 10", nfeItem.getDescricaoProduto()));
		} else if (nfeItem.getICMS() instanceof ICMS30) {
			ICMS30 icms = (ICMS30) nfeItem.getICMS();
			icms.setValorICMSST(valorIcmsST);
			icms.setValorBCST(BigDecimal.ZERO);
			icms.setAliquotaICMSST(BigDecimal.ZERO);
			nfeItem.setICMS(icms);
			// log.info(String.format("o ICMS é 30", nfeItem.getDescricaoProduto()));
		} else if (nfeItem.getICMS() instanceof ICMS70) {
			ICMS70 icms = (ICMS70) nfeItem.getICMS();
			icms.setValorBCST(BigDecimal.ZERO);
			icms.setAliquotaICMSST(BigDecimal.ZERO);
			icms.setValorICMSST(valorIcmsST);
			nfeItem.setICMS(icms);
			// log.info(String.format("o ICMS é 70", nfeItem.getDescricaoProduto()));
		} else if (nfeItem.getICMS() instanceof ICMS90) {
			ICMS90 icms = (ICMS90) nfeItem.getICMS();
			icms.setValorICMSST(valorIcmsST);
			icms.setValorBCST(BigDecimal.ZERO);
			icms.setAliquotaICMSST(BigDecimal.ZERO);
			nfeItem.setICMS(icms);
			// log.info(String.format("o ICMS é 90", nfeItem.getDescricaoProduto()));
		}
		return nfeItem;
	}

	public TotalImposto identificarImpostoNFe(NFeItem nfeItem) {
		TotalImposto totalImposto = new TotalImposto();
		if (nfeItem.getICMS() instanceof ICMS00) {
			ICMS00 icms = (ICMS00) nfeItem.getICMS();
			totalImposto.setValorBCICMS(icms.getValorBC());
			totalImposto.setAliqICMS(icms.getAliquotaICMS());
			totalImposto.setValorICMS(icms.getValorICMS());
			return totalImposto;
		} else if (nfeItem.getICMS() instanceof ICMS10) {
			ICMS10 icms = (ICMS10) nfeItem.getICMS();
			totalImposto.setValorBCICMSST(icms.getValorBCST());
			totalImposto.setAliqICMSST(icms.getAliquotaICMSST());
			totalImposto.setValorICMSST(icms.getValorICMSST());
			return totalImposto;
		} else if (nfeItem.getICMS() instanceof ICMS20) {
			ICMS20 icms = (ICMS20) nfeItem.getICMS();
			totalImposto.setValorBCICMS(icms.getValorBC());
			totalImposto.setAliqICMS(icms.getAliquotaICMS());
			totalImposto.setValorICMS(icms.getValorICMS());
			return totalImposto;
		} else if (nfeItem.getICMS() instanceof ICMS30) {
			ICMS30 icms = (ICMS30) nfeItem.getICMS();
			totalImposto.setValorBCICMSST(icms.getValorBCST());
			totalImposto.setAliqICMSST(icms.getAliquotaICMSST());
			totalImposto.setValorICMSST(icms.getValorICMSST());
			return totalImposto;
		} else if (nfeItem.getICMS() instanceof ICMS40 || nfeItem.getICMS() instanceof ICMS41
				|| nfeItem.getICMS() instanceof ICMS50) {
			// NAO TEM NADA
			return totalImposto;
		} else if (nfeItem.getICMS() instanceof ICMS51) {
			ICMS51 icms = (ICMS51) nfeItem.getICMS();
			totalImposto.setValorBCICMS(icms.getValorBC());
			totalImposto.setAliqICMS(icms.getAliquotaICMS());
			totalImposto.setValorICMS(icms.getValorICMS());
			return totalImposto;
		} else if (nfeItem.getICMS() instanceof ICMS60) {
			ICMS60 icms = (ICMS60) nfeItem.getICMS();
			totalImposto.setValorBCICMSST(icms.getValorBCSTRet());
			totalImposto.setValorICMSST(icms.getValorICMSSTRet());
			return totalImposto;
		} else if (nfeItem.getICMS() instanceof ICMS70) {
			ICMS70 icms = (ICMS70) nfeItem.getICMS();
			totalImposto.setAliqICMS(icms.getAliquotaICMS());
			totalImposto.setValorICMS(icms.getValorICMS());
			totalImposto.setValorBCICMSST(icms.getValorBCST());
			totalImposto.setAliqICMSST(icms.getAliquotaICMSST());
			totalImposto.setValorICMSST(icms.getValorICMSST());
			return totalImposto;
		} else if (nfeItem.getICMS() instanceof ICMS90) {
			ICMS90 icms = (ICMS90) nfeItem.getICMS();
			totalImposto.setValorBCICMS(icms.getValorBC());
			totalImposto.setAliqICMS(icms.getAliquotaICMS());
			totalImposto.setValorICMS(icms.getValorICMS());
			totalImposto.setValorBCICMSST(icms.getValorBCST());
			totalImposto.setAliqICMSST(icms.getAliquotaICMSST());
			totalImposto.setValorICMSST(icms.getValorICMSST());
			return totalImposto;
		}
		return totalImposto;
	}

	public void imprimirLinhaSped(SPEDC170 spedc170, int linha, String filtro) {

		try {
			spedc170.setLinhaOriginal("|C170|" + spedc170.getNumItem() + "|" + spedc170.getCodItem() + "|"
					+ spedc170.getDescricaoComplementar() + "|" + spedc170.getQtd() + "|" + spedc170.getUnid() + "|"
					+ spedc170.getValor() + "|" + spedc170.getValorDesconto() + "|" + "|" + spedc170.getCSTICMS() + "|"
					+ spedc170.getCFOP() + "|" + spedc170.getCodigoNatureza() + "|" + spedc170.getValorBCICMS() + "|"
					+ spedc170.getAliqICMS() + "|" + spedc170.getValorICMS() + "|" + spedc170.getValorBCICMSST() + "|"
					+ spedc170.getAliqICMSST() + "|" + spedc170.getValorICMSST() + "|" + spedc170.getIndicadorApuracao()
					+ "|" + spedc170.getCSTIPI() + "|" + spedc170.getCodEnquadramentoIPI() + "|"
					+ spedc170.getValorBCIPI() + "|" + spedc170.getAliqIPI() + "|" + spedc170.getValorIPI() + "|"
					+ spedc170.getCSTPIS() + "|" + spedc170.getValorBCPIS() + "|" + spedc170.getAliqPISReais() + "|"
					+ " ->" + "ORIGINAL -- ");
			System.out.println(spedc170.getLinhaOriginal() + filtro);
		} catch (Exception ex) {
		}
	}

	private void atribuirValorParaALinhaModificada(SPEDC170 spedc170, String linhaModificada) {
		spedc170.setLinhaComModificacoes(linhaModificada);
	}

	public int imprimirLinhaAlteradaSped(int linha, int coluna, String valorAntigo, String valorNovo, String filtro,
			int oldLinha, List<SPEDC170> itensSC170, LeitorSpedFiscal leitorSped) {
		if (oldLinha == linha)
			return oldLinha;
		Optional<SPEDC170> linhaDoSped = itensSC170.stream().filter(sc170 -> sc170.getNumLinhaSPED() == linha)
				.findFirst();
		if (linhaDoSped.isPresent()) { // original
			imprimirLinhaSped(linhaDoSped.get(), linha, filtro);
			atribuirValorParaALinhaModificada(linhaDoSped.get(), leitorSped.getConteudoArquivoSPED()[linha]);
		}
		// alterado
		System.out.println(leitorSped.getConteudoArquivoSPED()[linha]);

		oldLinha = linha;
		return oldLinha;
	}

	public void modificarConteudoSPED(int numLinha, int coluna, String valorAntigo, String valorNovo, String filtro,
			SPEDC170 sc170, NFeItem nfItem, int oldLinha, StringBuilder sbRelModifSPED, List<SPEDC170> itensSC170,
			LeitorSpedFiscal leitorSped) {
		imprimirLinhaAlteradaSped(numLinha, coluna, valorAntigo, valorNovo, filtro, oldLinha, itensSC170, leitorSped);
		inserirEntradaRelModifSPED(numLinha + 1, coluna, valorAntigo, valorNovo, filtro, sc170, nfItem, sbRelModifSPED);
		String linha = leitorSped.getConteudoArquivoSPED()[numLinha];
		String[] campos = linha.split("\\|");
		campos[coluna] = valorNovo;
		linha = String.join("|", campos);
		leitorSped.getConteudoArquivoSPED()[numLinha] = linha;
	}

	public CFOP retornarCFOPEntrada(CFOP NFeCFOP) {
		CFOPDAO cfopEquivDAO = new CFOPDAO();
		CFOP cfop = null;
		try {
			cfop = cfopEquivDAO.selecionarUm(NFeCFOP);
			return cfop;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			// log.error(String.format("Houve um erro ao tentar retornar o CFOP equivalente
			// ao '%s' da NFe, Mensagem: %s", NFeCFOP.getCodigo(), ex.getMessage()), ex);
		}
		return null;
	}

	private boolean identificarCstNaoTributavel(int cst) {
		boolean result = false;
		if (cst == 10 || cst == 30 || cst == 60 || cst == 70)
			result = true;
		return result;
	}

	private CST retornarCSTEntrada(CST NFeCST) {
		CSTDAO cfopEquivDAO = new CSTDAO();
		CST cst = null;

		try {
			cst = cfopEquivDAO.selecionarUm(NFeCST);
			return cst;
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			// log.error(String.format("Houve um erro ao tentar retornar o CST equivalente
			// ao '%s' da NFe, Mensagem: %s", NFeCST.getCodigo(), ex.getMessage()), ex);
		}
		return null;
	}

	public void aplicarModificacoesIdentificadasSPEDComNFe(SPEDC170 sc170, NFeItem nfeItem, boolean muitos,
			String filtro, int oldLinha, StringBuilder sbRelModifSPED, List<SPEDC170> itensSC170,
			LeitorSpedFiscal leitorSped) {
		// metodo é responsavel por identificar os valores divergentes a aplicar a
		// midificação no conteudo do sped

		try {
			// modificando o CFOP do SPED
			if (((!StringUtil.isNullOrEmpty(sc170.getCFOP()) && !sc170.getCFOP().endsWith("556")
					&& !sc170.getCFOP().endsWith("551") && !sc170.getCFOP().endsWith("406")
					&& !sc170.getCFOP().endsWith("407")) || StringUtil.isNullOrEmpty(sc170.getCFOP()))
					&& !StringUtil.isNullOrEmpty(nfeItem.getCfop())) {

				CFOP cfopFiltro = new CFOP();
				cfopFiltro.setCodigo(nfeItem.getCfop());
				CFOP cfopEntrada = null;
				try {
					cfopEntrada = retornarCFOPEntrada(cfopFiltro);
				} catch (Exception ex) {
					System.out.println("nao foi possivel localizar o CFOP -> " + cfopFiltro);
				}

				if (cfopEntrada != null && cfopEntrada.getCFOPEquivalente() != null) {
					// no procedimento a seguir o sistema verifica se o cfop é valido para a
					// operação de venda realizada
					modificarConteudoSPED(sc170.getNumLinhaSPED(), sc170.getIndiceColuna("CFOP"), sc170.getCFOP(),
							cfopEntrada.getCFOPEquivalente().getCodigo(), filtro, sc170, nfeItem, oldLinha,
							sbRelModifSPED, itensSC170, leitorSped);
					// log.log(Level.DEBUG," Modificou o CFOP : SPD "+ sc170.getCFOP()+" para NFE "
					// + cfopEntrada.getCFOPEquivalente().getCodigo());
					sc170.setCFOP(cfopEntrada.getCFOPEquivalente().getCodigo());
				}
			}

			// modificando o CST do ICMS do SPED
			if (nfeItem.getICMS() != null) {
				// if (!StringUtil.isNullOrEmpty(sc170.getCSTICMS()) &&
				// Integer.parseInt(sc170.getCSTICMS()) != nfeItem.getICMS().getCST()) {
				if (nfeItem.getICMS().getCST() > 0) {

					CST cstFiltro = new CST();
					cstFiltro.setCodigo(String.valueOf(nfeItem.getICMS().getCST()));
					CST cstEntrada = null;
					try {
						cstEntrada = retornarCSTEntrada(cstFiltro);
					} catch (Exception ex) {
						System.out.println("nao foi possivel localizar o CST -> " + cstFiltro);
					}

					if (cstEntrada != null && cstEntrada.getCSTEquivalente() != null
							&& !StringUtil.isNullOrEmpty(cstEntrada.getCSTEquivalente().getCodigo())) {
						String codCSTICMS = "0" + cstEntrada.getCSTEquivalente().getCodigo();
						codCSTICMS = codCSTICMS.substring(codCSTICMS.length() - 3);
						modificarConteudoSPED(sc170.getNumLinhaSPED(), sc170.getIndiceColuna("CSTICMS"),
								sc170.getCSTICMS(), codCSTICMS, filtro, sc170, nfeItem, oldLinha, sbRelModifSPED,
								itensSC170, leitorSped);
						// log.log(Level.DEBUG," Modificou o CST ICMS: SPD "+ sc170.getCSTICMS()+" para
						// NFE " + codCSTICMS);
						sc170.setCSTICMS(codCSTICMS);
					}
				}
				// if (sc170.getQtd() == null ||
				// sc170.getQtd().compareTo(nfeItem.getQuantidade()) != 0){
				// if(muitos)nfeItem.setQuantidade(sc170.getQtd());
				//
				// modificarConteudoSPED(sc170.getNumLinhaSPED(), sc170.getIndiceColuna("Qtd"),
				// formatarNumero(this.df, sc170.getQtd()), formatarNumero(this.df,
				// nfeItem.getQuantidade()));
				// sc170.setValorDesconto(nfeItem.getQuantidade());
				// }
				//
				// if (sc170.getValor() == null ||
				// sc170.getValor().compareTo(nfeItem.getValorProduto()) != 0){
				// if(muitos)nfeItem.setValorItem(sc170.getValor());
				if (nfeItem.getValorProduto() != null) {
					modificarConteudoSPED(sc170.getNumLinhaSPED(), sc170.getIndiceColuna("Valor"),
							formatarNumero(this.df, sc170.getValor()),
							formatarNumero(this.df, nfeItem.getValorProduto()), filtro, sc170, nfeItem, oldLinha,
							sbRelModifSPED, itensSC170, leitorSped);
					// log.log(Level.DEBUG," Modificou o VALOR: SPD "+ sc170.getValor()+" para NFE "
					// + nfeItem.getValorProduto());
					sc170.setValor(nfeItem.getValorProduto());
				}

				// modificando o valor de desconto do item no SPED pelo da NFe
				// if (sc170.getValorDesconto() == null ||
				// sc170.getValorDesconto().compareTo(nfeItem.getValorDesconto()) != 0) {
				// modificarConteudoSPED(sc170.getNumLinhaSPED(),
				// sc170.getIndiceColuna("ValorDesconto"), formatarNumero(this.df,
				// sc170.getValorDesconto()), formatarNumero(this.df,
				// nfeItem.getValorDesconto()),filtro,sc170,nfeItem, oldLinha, sbRelModifSPED,
				// itensSC170, leitorSped);
				// //log.log(Level.DEBUG," Modificou o VALOR DESCONTO: SPD "+
				// sc170.getValorDesconto()+" para NFE " + nfeItem.getValorDesconto());
				// sc170.setValorDesconto(nfeItem.getValorDesconto());
				// }

				MetodosUtil mtUtil = new MetodosUtil();
				TotalImposto totalImposto = mtUtil.identificarImpostoNFe(nfeItem);

				// caso o ICMS pertença a lista de CST nao tributavel os impostos acima serão
				// zerados
				if (this.identificarCstNaoTributavel(nfeItem.getICMS().getCST())) {
					totalImposto.setValorBCICMS(BigDecimal.ZERO);
					totalImposto.setValorICMS(BigDecimal.ZERO);
					totalImposto.setAliqICMS(BigDecimal.ZERO);
					// log.log(Level.DEBUG," CST é Nao Tributavel da NFE "
					// +nfeItem.getICMS().getCST());
				} else {
					System.out.println("CST é Tributavel da NFE " + nfeItem.getICMS().getCST());
					// log.log(Level.DEBUG," CST é Tributavel da NFE " +nfeItem.getICMS().getCST());
				}

				// modificando o valor da base de calculo de icms do SPED
				if (totalImposto.getValorBCICMS() != null
						&& sc170.getValorBCICMS().compareTo(totalImposto.getValorBCICMS()) != 0) {
					modificarConteudoSPED(sc170.getNumLinhaSPED(), sc170.getIndiceColuna("ValorBCICMS"),
							formatarNumero(this.df, sc170.getValorBCICMS()),
							formatarNumero(this.df, totalImposto.getValorBCICMS()), filtro, sc170, nfeItem, oldLinha,
							sbRelModifSPED, itensSC170, leitorSped);
					// log.log(Level.DEBUG," Modificou o VALOR BICMS: SPD "+
					// sc170.getValorBCICMS()+" para NFE " + totalImposto.getValorBCICMS());
					sc170.setValorBCICMS(totalImposto.getValorBCICMS());
				}

				// modificando a aliquota de Icms do SPED
				if (totalImposto.getAliqICMS() != null
						&& sc170.getAliqICMS().compareTo(totalImposto.getAliqICMS()) != 0) {
					modificarConteudoSPED(sc170.getNumLinhaSPED(), sc170.getIndiceColuna("AliqICMS"),
							formatarNumero(this.df, sc170.getAliqICMS()),
							formatarNumero(this.df, totalImposto.getAliqICMS()), filtro, sc170, nfeItem, oldLinha,
							sbRelModifSPED, itensSC170, leitorSped);
					// log.log(Level.DEBUG," Modificou o VALOR ALIQ: SPD "+ sc170.getAliqICMS()+"
					// para NFE " + totalImposto.getAliqICMS());

					sc170.setAliqICMS(totalImposto.getAliqICMS());
				}

				// modificando o valor de ICMS do SPED
				if (totalImposto.getValorICMS() != null
						&& sc170.getValorICMS().compareTo(totalImposto.getValorICMS()) != 0) {
					modificarConteudoSPED(sc170.getNumLinhaSPED(), sc170.getIndiceColuna("ValorICMS"),
							formatarNumero(this.df, sc170.getValorICMS()),
							formatarNumero(this.df, totalImposto.getValorICMS()), filtro, sc170, nfeItem, oldLinha,
							sbRelModifSPED, itensSC170, leitorSped);
					// log.log(Level.DEBUG," Modificou o VALOR ICMS: SPD "+ sc170.getValorICMS()+"
					// para NFE " + totalImposto.getValorICMS());
					sc170.setValorICMS(totalImposto.getValorICMS());
				}

				// modificando o valor de base de calculo da substituição tributaria do Icms
				if (totalImposto.getValorBCICMSST() != null
						&& sc170.getValorBCICMSST().compareTo(totalImposto.getValorBCICMSST()) != 0) {
					// if(muitos)totalImposto.setValorBCICMSST(sc170.getValorBCICMSST());
					modificarConteudoSPED(sc170.getNumLinhaSPED(), sc170.getIndiceColuna("ValorBCICMSST"),
							formatarNumero(this.df, sc170.getValorBCICMSST()),
							formatarNumero(this.df, totalImposto.getValorBCICMSST()), filtro, sc170, nfeItem, oldLinha,
							sbRelModifSPED, itensSC170, leitorSped);
					// log.log(Level.DEBUG," Modificou o VALOR ICMSST: SPD "+
					// sc170.getValorBCICMSST()+" para NFE " + totalImposto.getValorBCICMSST());
					sc170.setValorBCICMSST(totalImposto.getValorBCICMSST());
				}

				// modificando a aliquota do ICMSST substituição tributaria
				if (totalImposto.getAliqICMSST() != null
						&& sc170.getAliqICMSST().compareTo(totalImposto.getAliqICMSST()) != 0) {
					// if(muitos)totalImposto.setAliqICMSST(sc170.getAliqICMSST());
					modificarConteudoSPED(sc170.getNumLinhaSPED(), sc170.getIndiceColuna("AliqICMSST"),
							formatarNumero(this.df, sc170.getAliqICMSST()),
							formatarNumero(this.df, totalImposto.getAliqICMSST()), filtro, sc170, nfeItem, oldLinha,
							sbRelModifSPED, itensSC170, leitorSped);
					// log.log(Level.DEBUG," Modificou o Aliq ICMSST: SPD "+ sc170.getAliqICMSST()+"
					// para NFE " + totalImposto.getAliqICMSST());
					sc170.setAliqICMSST(totalImposto.getAliqICMSST());
				}

				// modificando o valor do ICMS de substituição de base de calculo
				if (totalImposto.getValorICMSST() != null
						&& sc170.getValorICMSST().compareTo(totalImposto.getValorICMSST()) != 0) {
					// if(muitos)totalImposto.setValorICMSST(sc170.getValorICMSST());
					modificarConteudoSPED(sc170.getNumLinhaSPED(), sc170.getIndiceColuna("ValorICMSST"),
							formatarNumero(this.df, sc170.getValorICMSST()),
							formatarNumero(this.df, totalImposto.getValorICMSST()), filtro, sc170, nfeItem, oldLinha,
							sbRelModifSPED, itensSC170, leitorSped);
					// log.log(Level.DEBUG," Modificou o VALOR ICMSST: SPD "+
					// sc170.getValorICMSST()+" para NFE " + totalImposto.getValorICMSST());
					sc170.setValorICMSST(totalImposto.getValorICMSST());
				}
				// if(valorBCSTRet != null &&)
				// modificando o valor do IPI
				if (nfeItem.getIPI() != null) {
					IPI ipi = (IPI) nfeItem.getIPI();

					if (ipi.getCST() != null) {
						if (sc170.getCSTIPI() != null && sc170.getCSTIPI().compareTo(ipi.getCST()) != 0) {
							modificarConteudoSPED(sc170.getNumLinhaSPED(), sc170.getIndiceColuna("CSTIPI"),
									sc170.getCSTIPI(), ipi.getCST(), filtro, sc170, nfeItem, oldLinha, sbRelModifSPED,
									itensSC170, leitorSped);
							sc170.setCSTIPI(ipi.getCST());
						}
					}
					if (ipi.getcEnq() != null) {
						if (sc170.getCodEnquadramentoIPI() != null
								&& sc170.getCodEnquadramentoIPI().compareTo(ipi.getcEnq()) != 0) {
							modificarConteudoSPED(sc170.getNumLinhaSPED(), sc170.getIndiceColuna("CodEnquadramentoIPI"),
									sc170.getCodEnquadramentoIPI(), ipi.getcEnq(), filtro, sc170, nfeItem, oldLinha,
									sbRelModifSPED, itensSC170, leitorSped);
							sc170.setCSTIPI(ipi.getCST());
						}
					}
					if (ipi.getvBC() != null) {
						if (sc170.getValorBCIPI() != null && sc170.getValorBCIPI().compareTo(ipi.getvBC()) != 0) {
							if (muitos)
								ipi.setvBC(sc170.getValorIPI());
							modificarConteudoSPED(sc170.getNumLinhaSPED(), sc170.getIndiceColuna("ValorBCIPI"),
									formatarNumero(this.df, sc170.getValorBCIPI()),
									formatarNumero(this.df, ipi.getvBC()), filtro, sc170, nfeItem, oldLinha,
									sbRelModifSPED, itensSC170, leitorSped);
							sc170.setValorBCIPI(ipi.getvBC());
						}
					}
					if (ipi.getpIPI() != null) {
						if (sc170.getAliqIPI() != null && sc170.getAliqIPI().compareTo(ipi.getpIPI()) != 0) {
							modificarConteudoSPED(sc170.getNumLinhaSPED(), sc170.getIndiceColuna("AliqIPI"),
									formatarNumero(this.df, sc170.getAliqIPI()), formatarNumero(this.df, ipi.getpIPI()),
									filtro, sc170, nfeItem, oldLinha, sbRelModifSPED, itensSC170, leitorSped);
							// log.log(Level.DEBUG," Modificou o Aliq IPI: SPD "+ sc170.getAliqIPI()+" para
							// NFE " + ipi.getpIPI());
							sc170.setAliqIPI(ipi.getpIPI());
						}
					}
					if (ipi.getvIPI() != null) {
						if (sc170.getValorIPI() != null && sc170.getValorIPI().compareTo(ipi.getvIPI()) != 0) {
							modificarConteudoSPED(sc170.getNumLinhaSPED(), sc170.getIndiceColuna("ValorIPI"),
									formatarNumero(this.df, sc170.getValorIPI()),
									formatarNumero(this.df, ipi.getvIPI()), filtro, sc170, nfeItem, oldLinha,
									sbRelModifSPED, itensSC170, leitorSped);
							// log.log(Level.DEBUG," Modificou o Valor IPI: SPD "+ sc170.getValorIPI()+"
							// para NFE " + ipi.getvIPI());
							sc170.setValorIPI(ipi.getvIPI());
						}
					}
				}
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			// log.error(ex.getMessage(), ex);
		}
	}

	private String formatarNumero(DecimalFormat df, BigDecimal obj) {
		if (obj == null)
			return "";
		return df.format(obj);
	}

	public void imprimirInformacoesParaDepuracao(SPEDC170 sc170, NFeItem nfeItem, String descricao) {
		String ean = "";
		String eanTrib = "";
		String getDescricaoProduto = "";
		String valor = "";
		String qtd = "";
		String vICmsSt = "";
		String vBcIcmsSt = "";
		String vIPI = "";
		String aliqIcmsST = "";
		TotalImposto ttImp;
		IPI ipi;
		try {
			if (nfeItem != null) {
				getDescricaoProduto = nfeItem.getDescricaoProduto();
				ean = nfeItem.getCodigoEAN();
				eanTrib = nfeItem.getCodigEANTrib();
				valor = nfeItem.getValorProduto().toString();
				qtd = nfeItem.getQuantidade().toString();
				try {
					MetodosUtil mtUtil = new MetodosUtil();
					ttImp = mtUtil.identificarImpostoNFe(nfeItem);
					vICmsSt = ttImp.getValorICMSST().toString();
					vBcIcmsSt = ttImp.getValorBCICMSST().toString();
					aliqIcmsST = ttImp.getAliqICMSST().toString();
				} catch (Exception ex) {
				}
				try {
					ipi = (IPI) nfeItem.getIPI();
					vIPI = ipi.getvIPI().toString();
				} catch (Exception ex) {
				}
			}
			System.out.println(

					"CHAVE:" + sc170.getSPEDC100().getChaveNFe() + "| DESCRICAO SPED:"
							+ sc170.getSPED0200().getDescrItem() + "| DESCRICAO XML:" + getDescricaoProduto
							+ "| EAN SPED:" + sc170.getSPED0200().getCodBarra() + "| EAN XML:" + ean
							+ "| EAN TRIB SPED:N/D" + "| EAN TRIB XML:" + eanTrib + "| VALOR SPED:" + sc170.getValor()
							+ "| VALOR XML:" + valor + "| VALOR ICMS ST SPED:" + sc170.getValorICMSST()
							+ "| VALOR ICMS ST XML:" + vICmsSt + "| VALOR IPI SPED: " + sc170.getValorIPI()
							+ "| VALOR IPI XML: " + vIPI + "| FILTRO:" + descricao);
		} catch (Exception ex) {
			System.out.println(String.format("---> " + ex.getMessage()));
		}
	}

	public void inserirEntradaRelModifSPED(int linha, int coluna, String valorAntigo, String valorNovo, String filtro,
			SPEDC170 sc170, NFeItem nfItem, StringBuilder sbRelModifSPED) {
		try {
			// Linha;Coluna;Valor Antigo;Valor Novo,Chave SPED,Chave XML, Descricao SPED,
			// Descricao XML, Valor SPED,Valor XML, Qtd SPED, Qtd XML
			if (sc170 == null)
				sc170 = new SPEDC170();

			if (nfItem == null)
				nfItem = new NFeItem();

			sbRelModifSPED.append(String.format("%d;%d;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\r\n", linha, coluna, valorAntigo,
					valorNovo, "'" + sc170.getSPEDC100().getChaveNFe() + "'", "'" + nfItem.getNFe().getChaveNFe() + "'",
					sc170.getSPED0200().getDescrItem(), nfItem.getDescricaoProdutoCustom(), sc170.getValor().toString(),
					nfItem.getValorProduto().toString(), sc170.getQtd().toString(), nfItem.getQuantidade().toString(),
					filtro));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public List<RelacaoProdutoFornecedor> buscarRelacaoProdutoFornecedor(String cnpjFornec, String refProd,
			String refFornec, String chave, String descSped, String descXml) {

		try {
			RelacaoProdutoFornecedorDAO relProdFornecDao = new RelacaoProdutoFornecedorDAO();
			List<RelacaoProdutoFornecedor> result = new ArrayList<>();
			result = relProdFornecDao.buscarPorEmpresaProdutoFornecedor(cnpjFornec, refProd, refFornec, chave, descSped,
					descXml);
			return result;

		} catch (Exception ex) {
			// String msg = String.format("Houve um erro ao tentar verificar a existencia do
			// Produto de Codigo '%s' no Fornecedor com Codigo %s da Empresa '%'",
			// refProd,
			// refFornec,
			// empresaAtual.getNome());
			// log.error(msg, ex);
			log.error(ex.getMessage(), ex);
			return null;
		}
	}

	public float compararDescricaoAproximada(String nfeItemDesc, String spedItemDesc, SPEDC170 sc170, NFeItem nfeItem) {
		nfeItemDesc = StringUtil.removerAcentos(nfeItemDesc);
		spedItemDesc = StringUtil.removerAcentos(spedItemDesc);
		String[] nfeItemPalavras = nfeItemDesc.replace(".", " ").replace("-", " ").replace("/", " ").replace("\\", " ")
				.split(" ");
		String[] spdItemPalavras = spedItemDesc.replace(".", " ").replace("-", " ").replace("/", " ").replace("\\", " ")
				.split(" ");

		float palavrasEncontradas = 0;
		float resultato = 0;

		if (nfeItemPalavras.length == 0 || spdItemPalavras.length == 0)
			return 0.0F;

		for (String pSpd : spdItemPalavras) {
			if (StringUtil.isNullOrEmpty(pSpd) || pSpd.length() <= 1)
				continue;

			for (String pNfe : nfeItemPalavras) {
				if (StringUtil.isNullOrEmpty(pNfe) || pNfe.length() <= 1)
					continue;

				if (pSpd.toLowerCase().contains(pNfe.toLowerCase()) || pNfe.toLowerCase().contains(pSpd.toLowerCase()))
					palavrasEncontradas++;
			}
		}
		resultato = (palavrasEncontradas / (float) (spdItemPalavras.length + nfeItemPalavras.length));
		// log.info(String.format("descrição aproximada USUARIO VAI DECIDIR XML: "+
		// chave +" C170:("+ spedItemDesc + ") NFEItem:("+nfeItemDesc +") porcentagem
		// --> ("+ resultato+")"));
		// imprimirInformacoesParaDepuracao(sc170,nfeItem,"DESCRICAO APROXIMADA -
		// USUARIO VAI DECIDIR");
		return resultato;
	}

	public class idxSPEDC170 {
		private BigDecimal index;
		private SPEDC170 sped;

		public BigDecimal getIndex() {
			return index;
		}

		public void setIndex(BigDecimal index) {
			this.index = index;
		}

		public SPEDC170 getSped() {
			return sped;
		}

		public void setSped(SPEDC170 sped) {
			this.sped = sped;
		}

	}
}
