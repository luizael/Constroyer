package br.com.mixfiscal.prodspedxnfe.services.util;

import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoCFOP;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoCodigoSituacaoTributaria;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoNotaFiscal;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC100;
import br.com.mixfiscal.prodspedxnfe.ex.PermissaoDiretorioException;
import br.com.mixfiscal.prodspedxnfe.services.relatorio.Relatorio;
import br.com.mixfiscal.prodspedxnfe.services.relatorio.RelatorioDebitoCredito;
import br.com.mixfiscal.prodspedxnfe.util.FileUtil;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.Logger;

public final class MetodosUtil {
	private Logger log;
	private DecimalFormat formatarValor = new DecimalFormat("0.00");
	private DecimalFormat formatarAliquota = new DecimalFormat("0.0");

	public BigDecimal calcularValorCargaTributaria(BigDecimal aliquota, BigDecimal redBc) {
		BigDecimal result = BigDecimal.ZERO;
		BigDecimal base = new BigDecimal(100);
		try {
			result = base.subtract(redBc);
			result = result.multiply(aliquota).divide(base).setScale(0, RoundingMode.CEILING);

		} catch (Exception ex) {
			result = BigDecimal.ZERO;
		}
		return result;
	}

	public BigDecimal calcularValorOperacao(BigDecimal qtd, BigDecimal valorUnitario) {
		BigDecimal result = BigDecimal.TEN;
		try {
			result = valorUnitario.multiply(qtd);
		} catch (Exception ex) {
		}
		return result;

	}

	public String ajustarCst(String cst) {
		char[] cstArr = cst.toCharArray();
		String newCst;
		if (cst.length() > 3)
			newCst = Character.toString(cstArr[1]) + Character.toString(cstArr[2]) + Character.toString(cstArr[3]);
		else
			newCst = Character.toString(cstArr[1]) + Character.toString(cstArr[2]);

		return newCst;
	}

	public boolean verificarDivergencia(BigDecimal ei, BigDecimal ed) {
		boolean result = false;
		if (ei.equals(ed))
			result = true;
		return result;
	}

	public boolean verificarSimplesNacional(String cstCsosn) {
		// se o CSt tiver 4 char é simples nacional
		if (cstCsosn.length() > 3)
			return true;

		return false;
	}

	public BigDecimal identificarCstTributavel(String cst, BigDecimal cargaTribMix) {
		if (!cst.equals("00") && !cst.equals("20") && !cst.equals("90"))
			return BigDecimal.ZERO;

		return cargaTribMix;
	}

	public BigDecimal calcularCreditoIndevido(BigDecimal cargaSPED, BigDecimal cargaMIX) {
		BigDecimal creditoIndevido = BigDecimal.ZERO;
		if (cargaSPED.compareTo(cargaMIX) > 0)
			creditoIndevido = cargaSPED.subtract(cargaMIX);

		return creditoIndevido;

	}

	public BigDecimal calcularEstornoDeCredito(BigDecimal cargaSPED, BigDecimal cargaMIX) {
		BigDecimal creditoNaoAproveitado = BigDecimal.ZERO;
		// if(cargaSPED.compareTo(cargaMIX) < 0)
		if (cargaMIX.compareTo(cargaSPED) > 0)
			creditoNaoAproveitado = cargaMIX.subtract(cargaSPED);

		return creditoNaoAproveitado;

	}

	public BigDecimal identificarIcmsMix(BigDecimal cargaTribMix, BigDecimal valorOpercao) {
		BigDecimal result = BigDecimal.ZERO;
		BigDecimal base = new BigDecimal(100);
		try {
			result = cargaTribMix.multiply(valorOpercao).divide(base);
		} catch (Exception ex) {
		}
		return result;
	}

	public BigDecimal identificarDebito1(BigDecimal valorOperacao, BigDecimal cargaTribMix, BigDecimal cargaTribNota) {
		BigDecimal base = new BigDecimal(100);
		BigDecimal result = new BigDecimal(formatarValor
				.format(valorOperacao.multiply(cargaTribMix.subtract(cargaTribNota)).divide(base)).replace(",", "."));
		return result;
	}

	public BigDecimal calcularReducaoCargaTributaria(RelatorioDebitoCredito.CargaTributaria carga) {
		BigDecimal base = new BigDecimal(100);
		BigDecimal result = new BigDecimal(BigInteger.ZERO);
		try {
			result = new BigDecimal(
					formatarValor.format(carga.getAliquota().multiply(base.subtract(carga.getRedBc())).divide(base))
							.replace(",", "."));
		} catch (Exception ex) {
		}
		return result;
	}

	public BigDecimal calcularCargaTributaria(BigDecimal valorOperacao, BigDecimal vBcIcms) {
		BigDecimal base = new BigDecimal(100);
		BigDecimal result = BigDecimal.ZERO;
		if (vBcIcms.compareTo(BigDecimal.ZERO) == 0)
			return valorOperacao;
		try {
			result = new BigDecimal(formatarValor
					.format(valorOperacao.subtract(vBcIcms).multiply(base).divide(valorOperacao)).replace(",", "."));

		} catch (Exception ex) {
		}
		return result;
	}

	public ETipoNotaFiscal verificarCodigoOperacaoFiscal(String cfop) {
		cfop = cfop.substring(0, 1);
		int cfopInt = Integer.parseInt(cfop);
		if (cfopInt == ETipoCFOP.CompraDentroDoEstado.getValor() 
				|| cfopInt == ETipoCFOP.CompraDoExterior.getValor()
				|| cfopInt == ETipoCFOP.CompraForaDoEstado.getValor())
			return ETipoNotaFiscal.Entrada;
		else
			return ETipoNotaFiscal.Saida;
	}

	public boolean comparaCstSpedMix(String cstSped, String cstMix) {
		boolean result = false;
		if (cstSped.equals(cstMix))
			result = true;
		return result;
	}

	public boolean compararAliquotaSpedMix(BigDecimal aliqSped, BigDecimal aliqMix) {
		if (aliqSped.compareTo(aliqMix) == 0)
			return true;

		return false;
	}

	public boolean verificarNotaCancelada(SPEDC100 spd100) {
		boolean result = false;
		// verifica se nao é um documento cancelado
		if (Character.toString(spd100.getCodigoSituacao().toCharArray()[1])
				.equals(ETipoCodigoSituacaoTributaria.DocumentoCancelado.getValor())
				|| Character.toString(spd100.getCodigoSituacao().toCharArray()[1])
						.equals(ETipoCodigoSituacaoTributaria.DocumentoCanceladoExtemporaneo)) {
			result = true;
		}

		return result;
	}

	public boolean verificarCstValido(String cst) {
            return cst != null && 
                   (cst.equals("0") ||
                    cst.equals("00") ||
                    cst.equals("20"));			
	}

	public boolean identificarTipoDocPelaChave(String chaveNfe, String valor) {
		String tipoDoc = "";
		try {
			tipoDoc = chaveNfe.substring(20, 22);
			if (tipoDoc.equals(valor))
				return true;
		} catch (Exception ex) {
		}
		return false;
	}

	public Date converterData(String data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd");
		Date dt = new Date(Long.MIN_VALUE);
		try {
			return sdf.parse(data);
		} catch (Exception ex) {
			try {
				return sdf2.parse(data);
			} catch (Exception ex2) {
				try {
					return sdf3.parse(data);
				} catch (Exception ex3) {
					log.error(String.format("Não foi possível realizar o Parse da data dhEmi/dEmi = '%s'", data), ex3);
				}
			}
		}
		return dt;
	}

	public Relatorio processarRegrasNFe(Relatorio rel) {
		BigDecimal base = new BigDecimal(100);
		try {
			if (rel.getCstIcmsMix().trim().equals("60") 
					|| rel.getCstIcmsMix().trim().equals("40")
					|| rel.getCstIcmsMix().trim().equals("41") 
					|| rel.getCstIcmsMix().trim().equals("51")
					|| rel.getCstIcmsMix().trim().equals("70")) {
				rel.setBaseDeCalculoMix(BigDecimal.ZERO);
			}

			if (rel.getCstIcmsMix().trim().equals("0") || rel.getCstIcmsMix().trim().equals("00")) {// calculando carga
																									// tributaria da MIX
																									// CST 0 e 00

				try {
					BigDecimal fator = base.subtract(rel.getRedBcMix()).divide(base);// encontrando o fator de
																						// multiplicacao
					BigDecimal baseCalculo = rel.getValorOperacao().multiply(fator);// encontrando a base de calculo
					rel.setBaseDeCalculoMix(new BigDecimal(formatarValor.format(baseCalculo).replace(",", ".")));
					BigDecimal cargaTrib = rel.getValorOperacao().multiply(rel.getAliqIcmsMix()).divide(base);// encontrando
																												// a
																												// carga
																												// tributaria
					rel.setCargaTributariaMix(new BigDecimal(formatarValor.format(cargaTrib).replace(",", ".")));
				} catch (Exception ex) {
					log.error("erro ao calcular a carga tributaria da mix 0 e 00 " + ex.getMessage(), ex);
				}
			}

			if (rel.getCstIcmsMix().trim().equals("20")) {// calculando carga tributaria da MIX CST 20

				try {
					BigDecimal fator = base.subtract(rel.getRedBcMix()).divide(base);// encontrando o fator de
																						// multiplicação
					BigDecimal baseCalculo = rel.getValorOperacao().multiply(fator);// encontrando a base de calculo
					rel.setBaseDeCalculoMix(baseCalculo);
					BigDecimal cargaTrib = baseCalculo.multiply(rel.getAliqIcmsMix()).divide(base);// encontrando a
																									// carga tributaria
					rel.setCargaTributariaMix(new BigDecimal(formatarValor.format(cargaTrib).replace(",", ".")));
				} catch (Exception ex) {
					log.error("erro ao calcular a carga tributaria da mix 20 " + ex.getMessage(), ex);
				}
			}

		} catch (Exception ex) {
			log.error("erro no metodo processarRegras " + ex.getMessage(), ex);
		}
		return rel;
	}

	public void criarArquivoCSV(String path, StringBuilder sbCsv) throws PermissaoDiretorioException {
		if (sbCsv.length() > 0)
			FileUtil.criarArquivo(path, sbCsv);
	}

	public float compararDescricaoAproximada(String nfeItemDesc, String spedItemDesc) {
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
		return resultato;
	}

	public BigDecimal arredondarValorDecimal(BigDecimal valor) {
		BigDecimal result = BigDecimal.ZERO;
		try {
			result = new BigDecimal(formatarValor.format(valor).replace(",", "."));
		} catch (Exception ex) {
			log.error("erro ao trocar virgula por ponto no metodo arredondarValorDecimal() " + ex.getMessage(), ex);
		}
		return result;
	}

	public BigDecimal arredondarAliquotaDecimal(BigDecimal valor) {
		BigDecimal result = BigDecimal.ZERO;
		try {
			result = new BigDecimal(formatarValor.format(valor).replace(",", "."));
		} catch (Exception ex) {
			log.error("erro ao trocar virgula por ponto no metodo arredondarAliquotaDecimal() " + ex.getMessage(), ex);
		}
		return result;
	}
}
