package br.com.mixfiscal.prodspedxnfe.services.relatorio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;


public class Relatorio implements Serializable{
    private static final long serialVersionUID = 1238151117415252326L; 
    
    private Date data;
    private String cnpj;
    private String clienteFornecedor;
    private String numeroNota;
    private String chaveNfe;
    private String ean;
    private String descricaoProduto;
    private String codigoInternoProduto;
    private String cstCsosn;
    
    private BigDecimal valorOperacao;
    private BigDecimal cargaTributaria;
    private String compraVenda;
    private String cstCompra;
    private BigDecimal aliqCompra;
    private BigDecimal redbcCompra;
    private String cstVenda;
    private BigDecimal aliqVenda;
    private BigDecimal redbcVenda;
    private BigDecimal creditoIndevidoCompra;
    private BigDecimal creditoNaoAproveitadoCompra;
    private BigDecimal debitoEstornarVenda;
    private BigDecimal debitoDeclararVenda;
    private boolean divergencia;
    private BigDecimal estornoDeCredito;
    private BigDecimal creditoIndevido;
    
    private String cstIcmsSped;
    private BigDecimal baseDeCalculoSped;
    private BigDecimal valorIcmsSped;
    private BigDecimal aliqIcmsSped;
    private BigDecimal redBcSped;
    private BigDecimal cargaTributariaSped;
    private String cfopSped;
    
    private String cstIcmsXml;
    private BigDecimal baseDeCalculoXml;
    private BigDecimal valorIcmsXml;
    private BigDecimal aliqIcmsXml;
    private BigDecimal redBcXml;
    private BigDecimal cargaTributariaXml;
    private String cfopXml;
    
    private String cstIcmsMix;
    private BigDecimal baseDeCalculoMix;
    private BigDecimal aliqIcmsMix;
    private BigDecimal redBcMix;
    private BigDecimal cargaTributariaMix;
     private String cfopMix;
     
    private BigDecimal debitoIndevido;
    private BigDecimal debitoNaoDeclarado;
   
    private String tipoDoc;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

   
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getClienteFornecedor() {
        return clienteFornecedor;
    }

    public void setClienteFornecedor(String clienteFornecedor) {
        this.clienteFornecedor = clienteFornecedor;
    }

    public String getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(String numeroNota) {
        this.numeroNota = numeroNota;
    }

    public String getChaveNfe() {
        return chaveNfe;
    }

    public void setChaveNfe(String chaveNfe) {
        this.chaveNfe = chaveNfe;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public String getCodigoInternoProduto() {
        return codigoInternoProduto;
    }

    public void setCodigoInternoProduto(String codigoInternoProduto) {
        this.codigoInternoProduto = codigoInternoProduto;
    }

    public String getCstCsosn() {
        return cstCsosn;
    }

    public void setCstCsosn(String cstCsosn) {
        this.cstCsosn = cstCsosn;
    }

    public BigDecimal getValorOperacao() {
        return valorOperacao;
    }

    public void setValorOperacao(BigDecimal valorOperacao) {
        this.valorOperacao = valorOperacao;
    }

    public BigDecimal getCargaTributaria() {
        return cargaTributaria;
    }

    public void setCargaTributaria(BigDecimal cargaTributaria) {
        this.cargaTributaria = cargaTributaria;
    }

    public String getCompraVenda() {
        return compraVenda;
    }

    public void setCompraVenda(String compraVenda) {
        this.compraVenda = compraVenda;
    }

    public String getCstCompra() {
        return cstCompra;
    }

    public void setCstCompra(String cstCompra) {
        this.cstCompra = cstCompra;
    }

    public BigDecimal getAliqCompra() {
        return aliqCompra;
    }

    public void setAliqCompra(BigDecimal aliqCompra) {
        this.aliqCompra = aliqCompra;
    }

    public BigDecimal getRedbcCompra() {
        return redbcCompra;
    }

    public void setRedbcCompra(BigDecimal redbcCompra) {
        this.redbcCompra = redbcCompra;
    }

    public String getCstVenda() {
        return cstVenda;
    }

    public void setCstVenda(String cstVenda) {
        this.cstVenda = cstVenda;
    }

    public BigDecimal getAliqVenda() {
        return aliqVenda;
    }

    public void setAliqVenda(BigDecimal aliqVenda) {
        this.aliqVenda = aliqVenda;
    }

    public BigDecimal getRedbcVenda() {
        return redbcVenda;
    }

    public void setRedbcVenda(BigDecimal redbcVenda) {
        this.redbcVenda = redbcVenda;
    }

    public BigDecimal getCreditoIndevidoCompra() {
        return creditoIndevidoCompra;
    }

    public void setCreditoIndevidoCompra(BigDecimal creditoIndevidoCompra) {
        this.creditoIndevidoCompra = creditoIndevidoCompra;
    }

    public BigDecimal getCreditoNaoAproveitadoCompra() {
        return creditoNaoAproveitadoCompra;
    }

    public void setCreditoNaoAproveitadoCompra(BigDecimal creditoNaoAproveitadoCompra) {
        this.creditoNaoAproveitadoCompra = creditoNaoAproveitadoCompra;
    }

    public BigDecimal getDebitoEstornarVenda() {
        return debitoEstornarVenda;
    }

    public void setDebitoEstornarVenda(BigDecimal debitoEstornarVenda) {
        this.debitoEstornarVenda = debitoEstornarVenda;
    }

    public BigDecimal getDebitoDeclararVenda() {
        return debitoDeclararVenda;
    }

    public void setDebitoDeclararVenda(BigDecimal debitoDeclararVenda) {
        this.debitoDeclararVenda = debitoDeclararVenda;
    }

    public boolean isDivergencia() {
        return divergencia;
    }

    public void setDivergencia(boolean divergencia) {
        this.divergencia = divergencia;
    }

    public BigDecimal getEstornoDeCredito() {
        return estornoDeCredito;
    }

    public void setEstornoDeCredito(BigDecimal estornoDeCredito) {
        this.estornoDeCredito = estornoDeCredito;
    }

    public BigDecimal getCreditoIndevido() {
        return creditoIndevido;
    }

    public void setCreditoIndevido(BigDecimal creditoIndevido) {
        this.creditoIndevido = creditoIndevido;
    }

    public String getCstIcmsSped() {
        return cstIcmsSped;
    }

    public void setCstIcmsSped(String cstIcmsSped) {
        this.cstIcmsSped = cstIcmsSped;
    }

    public BigDecimal getBaseDeCalculoSped() {
        return baseDeCalculoSped;
    }

    public void setBaseDeCalculoSped(BigDecimal baseDeCalculoSped) {
        this.baseDeCalculoSped = baseDeCalculoSped;
    }

    public BigDecimal getValorIcmsSped() {
        return valorIcmsSped;
    }

    public void setValorIcmsSped(BigDecimal valorIcmsSped) {
        this.valorIcmsSped = valorIcmsSped;
    }

    public BigDecimal getAliqIcmsSped() {
        return aliqIcmsSped;
    }

    public void setAliqIcmsSped(BigDecimal aliqIcmsSped) {
        this.aliqIcmsSped = aliqIcmsSped;
    }

    public BigDecimal getRedBcSped() {
        return redBcSped;
    }

    public void setRedBcSped(BigDecimal redBcSped) {
        this.redBcSped = redBcSped;
    }

    public BigDecimal getCargaTributariaSped() {
        return cargaTributariaSped;
    }

    public void setCargaTributariaSped(BigDecimal cargaTributariaSped) {
        this.cargaTributariaSped = cargaTributariaSped;
    }

    public String getCfopSped() {
        return cfopSped;
    }

    public void setCfopSped(String cfopSped) {
        this.cfopSped = cfopSped;
    }

    public String getCstIcmsXml() {
        return cstIcmsXml;
    }

    public void setCstIcmsXml(String cstIcmsXml) {
        this.cstIcmsXml = cstIcmsXml;
    }

    public BigDecimal getBaseDeCalculoXml() {
        return baseDeCalculoXml;
    }

    public void setBaseDeCalculoXml(BigDecimal baseDeCalculoXml) {
        this.baseDeCalculoXml = baseDeCalculoXml;
    }

    public BigDecimal getValorIcmsXml() {
        return valorIcmsXml;
    }

    public void setValorIcmsXml(BigDecimal valorIcmsXml) {
        this.valorIcmsXml = valorIcmsXml;
    }

    public BigDecimal getAliqIcmsXml() {
        return aliqIcmsXml;
    }

    public void setAliqIcmsXml(BigDecimal aliqIcmsXml) {
        this.aliqIcmsXml = aliqIcmsXml;
    }

    public BigDecimal getRedBcXml() {
        return redBcXml;
    }

    public void setRedBcXml(BigDecimal redBcXml) {
        this.redBcXml = redBcXml;
    }

    public BigDecimal getCargaTributariaXml() {
        return cargaTributariaXml;
    }

    public void setCargaTributariaXml(BigDecimal cargaTributariaXml) {
        this.cargaTributariaXml = cargaTributariaXml;
    }

    public String getCfopXml() {
        return cfopXml;
    }

    public void setCfopXml(String cfopXml) {
        this.cfopXml = cfopXml;
    }

    public String getCstIcmsMix() {
        return cstIcmsMix;
    }

    public void setCstIcmsMix(String cstIcmsMix) {
        this.cstIcmsMix = cstIcmsMix;
    }

    public BigDecimal getBaseDeCalculoMix() {
        return baseDeCalculoMix;
    }

    public void setBaseDeCalculoMix(BigDecimal baseDeCalculoMix) {
        this.baseDeCalculoMix = baseDeCalculoMix;
    }

    public BigDecimal getAliqIcmsMix() {
        return aliqIcmsMix;
    }

    public void setAliqIcmsMix(BigDecimal aliqIcmsMix) {
        this.aliqIcmsMix = aliqIcmsMix;
    }

    public BigDecimal getRedBcMix() {
        return redBcMix;
    }

    public void setRedBcMix(BigDecimal redBcMix) {
        this.redBcMix = redBcMix;
    }

    public BigDecimal getCargaTributariaMix() {
        return cargaTributariaMix;
    }

    public void setCargaTributariaMix(BigDecimal cargaTributariaMix) {
        this.cargaTributariaMix = cargaTributariaMix;
    }

    public String getCfopMix() {
        return cfopMix;
    }

    public void setCfopMix(String cfopMix) {
        this.cfopMix = cfopMix;
    }

    public BigDecimal getDebitoIndevido() {
        return debitoIndevido;
    }

    public void setDebitoIndevido(BigDecimal debitoIndevido) {
        this.debitoIndevido = debitoIndevido;
    }

    public BigDecimal getDebitoNaoDeclarado() {
        return debitoNaoDeclarado;
    }

    public void setDebitoNaoDeclarado(BigDecimal debitoNaoDeclarado) {
        this.debitoNaoDeclarado = debitoNaoDeclarado;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.data);
        hash = 97 * hash + Objects.hashCode(this.cnpj);
        hash = 97 * hash + Objects.hashCode(this.clienteFornecedor);
        hash = 97 * hash + Objects.hashCode(this.numeroNota);
        hash = 97 * hash + Objects.hashCode(this.chaveNfe);
        hash = 97 * hash + Objects.hashCode(this.ean);
        hash = 97 * hash + Objects.hashCode(this.descricaoProduto);
        hash = 97 * hash + Objects.hashCode(this.codigoInternoProduto);
        hash = 97 * hash + Objects.hashCode(this.cstCsosn);
        hash = 97 * hash + Objects.hashCode(this.valorOperacao);
        hash = 97 * hash + Objects.hashCode(this.cargaTributaria);
        hash = 97 * hash + Objects.hashCode(this.compraVenda);
        hash = 97 * hash + Objects.hashCode(this.cstCompra);
        hash = 97 * hash + Objects.hashCode(this.aliqCompra);
        hash = 97 * hash + Objects.hashCode(this.redbcCompra);
        hash = 97 * hash + Objects.hashCode(this.cstVenda);
        hash = 97 * hash + Objects.hashCode(this.aliqVenda);
        hash = 97 * hash + Objects.hashCode(this.redbcVenda);
        hash = 97 * hash + Objects.hashCode(this.creditoIndevidoCompra);
        hash = 97 * hash + Objects.hashCode(this.creditoNaoAproveitadoCompra);
        hash = 97 * hash + Objects.hashCode(this.debitoEstornarVenda);
        hash = 97 * hash + Objects.hashCode(this.debitoDeclararVenda);
        hash = 97 * hash + (this.divergencia ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.estornoDeCredito);
        hash = 97 * hash + Objects.hashCode(this.creditoIndevido);
        hash = 97 * hash + Objects.hashCode(this.cstIcmsSped);
        hash = 97 * hash + Objects.hashCode(this.baseDeCalculoSped);
        hash = 97 * hash + Objects.hashCode(this.valorIcmsSped);
        hash = 97 * hash + Objects.hashCode(this.aliqIcmsSped);
        hash = 97 * hash + Objects.hashCode(this.redBcSped);
        hash = 97 * hash + Objects.hashCode(this.cargaTributariaSped);
        hash = 97 * hash + Objects.hashCode(this.cfopSped);
        hash = 97 * hash + Objects.hashCode(this.cstIcmsXml);
        hash = 97 * hash + Objects.hashCode(this.baseDeCalculoXml);
        hash = 97 * hash + Objects.hashCode(this.valorIcmsXml);
        hash = 97 * hash + Objects.hashCode(this.aliqIcmsXml);
        hash = 97 * hash + Objects.hashCode(this.redBcXml);
        hash = 97 * hash + Objects.hashCode(this.cargaTributariaXml);
        hash = 97 * hash + Objects.hashCode(this.cfopXml);
        hash = 97 * hash + Objects.hashCode(this.cstIcmsMix);
        hash = 97 * hash + Objects.hashCode(this.baseDeCalculoMix);
        hash = 97 * hash + Objects.hashCode(this.aliqIcmsMix);
        hash = 97 * hash + Objects.hashCode(this.redBcMix);
        hash = 97 * hash + Objects.hashCode(this.cargaTributariaMix);
        hash = 97 * hash + Objects.hashCode(this.cfopMix);
        hash = 97 * hash + Objects.hashCode(this.debitoIndevido);
        hash = 97 * hash + Objects.hashCode(this.debitoNaoDeclarado);
        hash = 97 * hash + Objects.hashCode(this.tipoDoc);
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
        final Relatorio other = (Relatorio) obj;
        if (this.divergencia != other.divergencia) {
            return false;
        }
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        if (!Objects.equals(this.clienteFornecedor, other.clienteFornecedor)) {
            return false;
        }
        if (!Objects.equals(this.numeroNota, other.numeroNota)) {
            return false;
        }
        if (!Objects.equals(this.chaveNfe, other.chaveNfe)) {
            return false;
        }
        if (!Objects.equals(this.ean, other.ean)) {
            return false;
        }
        if (!Objects.equals(this.descricaoProduto, other.descricaoProduto)) {
            return false;
        }
        if (!Objects.equals(this.codigoInternoProduto, other.codigoInternoProduto)) {
            return false;
        }
        if (!Objects.equals(this.cstCsosn, other.cstCsosn)) {
            return false;
        }
        if (!Objects.equals(this.compraVenda, other.compraVenda)) {
            return false;
        }
        if (!Objects.equals(this.cstCompra, other.cstCompra)) {
            return false;
        }
        if (!Objects.equals(this.cstVenda, other.cstVenda)) {
            return false;
        }
        if (!Objects.equals(this.cstIcmsSped, other.cstIcmsSped)) {
            return false;
        }
        if (!Objects.equals(this.cfopSped, other.cfopSped)) {
            return false;
        }
        if (!Objects.equals(this.cstIcmsXml, other.cstIcmsXml)) {
            return false;
        }
        if (!Objects.equals(this.cfopXml, other.cfopXml)) {
            return false;
        }
        if (!Objects.equals(this.cstIcmsMix, other.cstIcmsMix)) {
            return false;
        }
        if (!Objects.equals(this.cfopMix, other.cfopMix)) {
            return false;
        }
        if (!Objects.equals(this.tipoDoc, other.tipoDoc)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.valorOperacao, other.valorOperacao)) {
            return false;
        }
        if (!Objects.equals(this.cargaTributaria, other.cargaTributaria)) {
            return false;
        }
        if (!Objects.equals(this.aliqCompra, other.aliqCompra)) {
            return false;
        }
        if (!Objects.equals(this.redbcCompra, other.redbcCompra)) {
            return false;
        }
        if (!Objects.equals(this.aliqVenda, other.aliqVenda)) {
            return false;
        }
        if (!Objects.equals(this.redbcVenda, other.redbcVenda)) {
            return false;
        }
        if (!Objects.equals(this.creditoIndevidoCompra, other.creditoIndevidoCompra)) {
            return false;
        }
        if (!Objects.equals(this.creditoNaoAproveitadoCompra, other.creditoNaoAproveitadoCompra)) {
            return false;
        }
        if (!Objects.equals(this.debitoEstornarVenda, other.debitoEstornarVenda)) {
            return false;
        }
        if (!Objects.equals(this.debitoDeclararVenda, other.debitoDeclararVenda)) {
            return false;
        }
        if (!Objects.equals(this.estornoDeCredito, other.estornoDeCredito)) {
            return false;
        }
        if (!Objects.equals(this.creditoIndevido, other.creditoIndevido)) {
            return false;
        }
        if (!Objects.equals(this.baseDeCalculoSped, other.baseDeCalculoSped)) {
            return false;
        }
        if (!Objects.equals(this.valorIcmsSped, other.valorIcmsSped)) {
            return false;
        }
        if (!Objects.equals(this.aliqIcmsSped, other.aliqIcmsSped)) {
            return false;
        }
        if (!Objects.equals(this.redBcSped, other.redBcSped)) {
            return false;
        }
        if (!Objects.equals(this.cargaTributariaSped, other.cargaTributariaSped)) {
            return false;
        }
        if (!Objects.equals(this.baseDeCalculoXml, other.baseDeCalculoXml)) {
            return false;
        }
        if (!Objects.equals(this.valorIcmsXml, other.valorIcmsXml)) {
            return false;
        }
        if (!Objects.equals(this.aliqIcmsXml, other.aliqIcmsXml)) {
            return false;
        }
        if (!Objects.equals(this.redBcXml, other.redBcXml)) {
            return false;
        }
        if (!Objects.equals(this.cargaTributariaXml, other.cargaTributariaXml)) {
            return false;
        }
        if (!Objects.equals(this.baseDeCalculoMix, other.baseDeCalculoMix)) {
            return false;
        }
        if (!Objects.equals(this.aliqIcmsMix, other.aliqIcmsMix)) {
            return false;
        }
        if (!Objects.equals(this.redBcMix, other.redBcMix)) {
            return false;
        }
        if (!Objects.equals(this.cargaTributariaMix, other.cargaTributariaMix)) {
            return false;
        }
        if (!Objects.equals(this.debitoIndevido, other.debitoIndevido)) {
            return false;
        }
        if (!Objects.equals(this.debitoNaoDeclarado, other.debitoNaoDeclarado)) {
            return false;
        }
        return true;
    }
    
}
