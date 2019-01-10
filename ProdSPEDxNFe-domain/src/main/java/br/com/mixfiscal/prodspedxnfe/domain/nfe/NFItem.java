package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import java.io.Serializable;
import java.math.BigDecimal;

public class NFItem implements Serializable{ 

    private static final long serialVersionUID = -841706984200286267L;
    
    private String id;
    private String nome;
    private float perc;
    private boolean selecionado;
    private int relMult;
    private int relAutomatico;
    private String valor;
    private String valorIcmsSt;
    private String valorIpi;
    private String valorOutros;
    private String chaveNfe;
    private String infAdProd;
    private int sequencia;
    private String ean;
    private String eanTrib;
    private int cst;
    private BigDecimal qtd;
    private BigDecimal idRef;
    private BigDecimal despesaAcessoria;
    
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

    public float getPerc() {
        return perc;
    }

    public void setPerc(float perc) {
        this.perc = perc;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public int getRelMult() {
        return relMult;
    }

    public void setRelMult(int relMult) {
        this.relMult = relMult;
    }

    public int getRelAutomatico() {
        return relAutomatico;
    }

    public void setRelAutomatico(int relAutomatico) {
        this.relAutomatico = relAutomatico;
    }      

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValorIcmsSt() {
        return valorIcmsSt;
    }

    public void setValorIcmsSt(String valorIcmsSt) {
        this.valorIcmsSt = valorIcmsSt;
    }

    public String getValorIpi() {
        return valorIpi;
    }

    public void setValorIpi(String valorIpi) {
        this.valorIpi = valorIpi;
    }

    public String getValorOutros() {
        return valorOutros;
    }

    public void setValorOutros(String valorOutros) {
        this.valorOutros = valorOutros;
    }
    
    public String getChaveNfe() {
        return chaveNfe;
    }

    public void setChaveNfe(String chaveNfe) {
        this.chaveNfe = chaveNfe;
    }

    public String getInfAdProd() {
        return infAdProd;
    }

    public void setInfAdProd(String infAdProd) {
        this.infAdProd = infAdProd;
    }
    /**
     * @return the sequencia
     */
    public int getSequencia() {
        return sequencia;
    }

    /**
     * @param sequencia the sequencia to set
     */
    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
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
     * @return the cst
     */
    public int getCst() {
        return cst;
    }

    /**
     * @param cst the cst to set
     */
    public void setCst(int cst) {
        this.cst = cst;
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
     * @return the despesaAcessoria
     */
    public BigDecimal getDespesaAcessoria() {
        return despesaAcessoria;
    }

    /**
     * @param despesaAcessoria the despesaAcessoria to set
     */
    public void setDespesaAcessoria(BigDecimal despesaAcessoria) {
        this.despesaAcessoria = despesaAcessoria;
    }

}
