package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class NFeItem implements Comparable<NFeItem>,Serializable {


    private static final long serialVersionUID = -8248913426917334123L;    
    
    private int sequencia;
    private int relAutomatico;
    private int numeroItem; // <det nItem="x">
    private String codigoProduto; // <cProd>
    private String descricaoProduto; // <xProd>
    private String descricaoProdutoCustom;
    private String codigoEAN; // <cEAN>
    private String codigoEANTrib; //<cEANTrib>
    private String codigoNCM; // <NCM>
    private String unidade; // <uCom>
    private BigDecimal quantidade; // <qCom>
    private BigDecimal valorUnitario; // <vUnCom>
    private BigDecimal valorProduto; // <vProd>
    private BigDecimal valorFrete;//<vFrete> 
    private BigDecimal valorSeguro;//<vSeg>
    private BigDecimal valorDesconto;//<vDesc>
    private BigDecimal valorOutros;//<vOutro>
    private BigDecimal valorItem;//<vItem>
    private int indicaTotal;//<indTot>
    private String cfop; // <CFOP>    
    private ICMS ICMS; // <ICMS00>, <ICMS10>, <ICMS20> e etc
    private PIS PIS; // <PISAliq>, <PISNT>, <PISOutr> e <PISQtde>
    private COFINS COFINS; // <COFINSAliq>, <COFINSNT>, <COFINSOutr>, <COFINSQtde>
    private IPI IPI;
    private NFe NFe;
    private BigDecimal valorTotal;
    private String infAdProd; //<infAdProd>
    private BigDecimal idRefComSped;
    // <editor-fold desc="Getters e Setters" defaultstate="collapsed">
    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public int getNumeroItem() {
        return numeroItem;
    }

    public void setNumeroItem(int numeroItem) {
        this.numeroItem = numeroItem;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }
    public String getDescricaoProdutoCustom() {
        descricaoProdutoCustom = StringUtil.removerAcentos(descricaoProduto);
        return descricaoProdutoCustom;
    }

    private void setDescricaoProdutoCustom(String descricaoProduto) {
        this.descricaoProdutoCustom = this.getDescricaoProduto();
    }
    
    public String getCodigoEAN() {
        return codigoEAN;
    }

    public void setCodigoEAN(String codigoEAN) {
        this.codigoEAN = codigoEAN;
    }

    public String getCodigoNCM() {
        return codigoNCM;
    }

    public void setCodigoNCM(String codigoNCM) {
        this.codigoNCM = codigoNCM;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(BigDecimal valorProduto) {
        this.valorProduto = valorProduto;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public BigDecimal getValorSeguro() {
        return valorSeguro;
    }

    public void setValorSeguro(BigDecimal valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorOutros() {
        return valorOutros;
    }

    public void setValorOutros(BigDecimal valorOutros) {
        this.valorOutros = valorOutros;
    }

    public int getIndicaTotal() {
        return indicaTotal;
    }

    public void setIndicaTotal(int indicaTotal) {
        this.indicaTotal = indicaTotal;
    }  
    
    public String getCfop() {
        return cfop;
    }

    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

    public ICMS getICMS() {
        return ICMS;
    }

    public void setICMS(ICMS ICMS) {
        this.ICMS = ICMS;
    }

    public PIS getPIS() {
        return PIS;
    }

    public void setPIS(PIS PIS) {
        this.PIS = PIS;
    }

    public COFINS getCOFINS() {
        return COFINS;
    }

    public void setCOFINS(COFINS COFINS) {
        this.COFINS = COFINS;
    }
    
    public NFe getNFe() {
        return NFe;
    }

    public void setNFe(NFe nfe) {
        this.NFe = nfe;
    }

    public BigDecimal getValorItem() {
        return valorItem;
    }

    public void setValorItem(BigDecimal valorItem) {
        this.valorItem = valorItem;
    }
    
    public IPI getIPI() {
        return IPI;
    }

    public void setIPI(IPI IPI) {
        this.IPI = IPI;
    }

    public int getRelAutomatico() {
        return relAutomatico;
    }

    public void setRelAutomatico(int relAutomatico) {
        this.relAutomatico = relAutomatico;
    }

    public String getCodigEANTrib() {
        return codigoEANTrib;
    }

    public void setCodigoEANTrib(String codigoEANTrib) {
        this.codigoEANTrib = codigoEANTrib;
    }   

    public BigDecimal getValorTotal() {
        
        this.valorTotal = BigDecimal.ZERO;
        try{
        if (this.ICMS instanceof ICMS00) {
            ICMS00 icms = (ICMS00)ICMS;
            //this.valorTotal =  this.valorTotal.add(icms.getValorICMS());           
        } else if (this.ICMS instanceof ICMS10) {
            ICMS10 icms = (ICMS10)ICMS;
            this.valorTotal =  this.valorTotal.add(icms.getValorICMSST()); 
        } else if (this.ICMS instanceof ICMS20) {
            ICMS20 icms = (ICMS20)ICMS;
            //this.valorTotal =  this.valorTotal.add(icms.getValorICMS());
        } else if (this.ICMS instanceof ICMS30) {
            ICMS30 icms = (ICMS30)ICMS;
            this.valorTotal =  this.valorTotal.add(icms.getValorICMSST()); 
        } else if (ICMS instanceof ICMS40 || 
                   ICMS instanceof ICMS41 ||
                   ICMS instanceof ICMS50) {
            // NAO TEM NADA                    
        } else if (ICMS instanceof ICMS51) {
            ICMS51 icms = (ICMS51)ICMS;          
            //this.valorTotal =  this.valorTotal.add(icms.getValorICMS());
        }else if(ICMS instanceof ICMS60){
           ICMS60 icms = (ICMS60)ICMS;            
           //this.valorTotal =  this.valorTotal.add(icms.getValorICMSSTRet());
        }else if (ICMS instanceof ICMS70 ) {
            ICMS70 icms = (ICMS70)ICMS;                        
            this.valorTotal =  this.valorTotal.add(icms.getValorICMSST()); 
        } else if (ICMS instanceof ICMS90) {
            ICMS90 icms = (ICMS90)ICMS;            
            this.valorTotal =  this.valorTotal.add(icms.getValorICMSST()); 
        }
        IPI ipi = (IPI)this.IPI;
        if(ipi != null)
            if(ipi.getvIPI() != null)
                this.valorTotal =  this.valorTotal.add(ipi.getvIPI());
        }catch(Exception ex){}
        this.valorTotal =  this.valorTotal.add(this.valorProduto);
        
        return valorTotal;
    }    

    public String getInfAdProd() {
        return infAdProd;
    }

    public void setInfAdProd(String infAdProd) {
        this.infAdProd = infAdProd;
    }
    
    // </editor-fold>

    // <editor-fold desc="equals e hashCode" defaultstate="collapsed">
    
     // </editor-fold>

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.sequencia;
        hash = 37 * hash + this.numeroItem;
        hash = 37 * hash + Objects.hashCode(this.codigoProduto);
        hash = 37 * hash + Objects.hashCode(this.descricaoProduto);
        hash = 37 * hash + Objects.hashCode(this.codigoEAN);
        hash = 37 * hash + Objects.hashCode(this.codigoNCM);
        hash = 37 * hash + Objects.hashCode(this.unidade);
        hash = 37 * hash + Objects.hashCode(this.quantidade);
        hash = 37 * hash + Objects.hashCode(this.valorUnitario);
        hash = 37 * hash + Objects.hashCode(this.valorProduto);
        hash = 37 * hash + Objects.hashCode(this.valorFrete);
        hash = 37 * hash + Objects.hashCode(this.valorSeguro);
        hash = 37 * hash + Objects.hashCode(this.valorDesconto);
        hash = 37 * hash + Objects.hashCode(this.valorOutros);
        hash = 37 * hash + Objects.hashCode(this.valorItem);
        hash = 37 * hash + this.indicaTotal;
        hash = 37 * hash + Objects.hashCode(this.cfop);
        hash = 37 * hash + Objects.hashCode(this.ICMS);
        hash = 37 * hash + Objects.hashCode(this.PIS);
        hash = 37 * hash + Objects.hashCode(this.COFINS);
        hash = 37 * hash + Objects.hashCode(this.NFe);
        hash = 37 * hash + Objects.hashCode(this.IPI);
        hash = 37 * hash + Objects.hashCode(this.relAutomatico);
        hash = 37 * hash + Objects.hashCode(this.codigoEANTrib);
        hash = 37 * hash + Objects.hashCode(this.valorTotal);
        hash = 37 * hash + Objects.hashCode(this.infAdProd);
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
        final NFeItem other = (NFeItem) obj;
        if (this.sequencia != other.sequencia) {
            return false;
        }
        if (this.numeroItem != other.numeroItem) {
            return false;
        }
        if (this.indicaTotal != other.indicaTotal) {
            return false;
        }
        if (!Objects.equals(this.codigoProduto, other.codigoProduto)) {
            return false;
        }
        if (!Objects.equals(this.descricaoProduto, other.descricaoProduto)) {
            return false;
        }
        if (!Objects.equals(this.codigoEAN, other.codigoEAN)) {
            return false;
        }
        if (!Objects.equals(this.codigoNCM, other.codigoNCM)) {
            return false;
        }
        if (!Objects.equals(this.unidade, other.unidade)) {
            return false;
        }
        if (!Objects.equals(this.cfop, other.cfop)) {
            return false;
        }
        if (!Objects.equals(this.quantidade, other.quantidade)) {
            return false;
        }
        if (!Objects.equals(this.valorUnitario, other.valorUnitario)) {
            return false;
        }
        if (!Objects.equals(this.valorProduto, other.valorProduto)) {
            return false;
        }
        if (!Objects.equals(this.valorFrete, other.valorFrete)) {
            return false;
        }
        if (!Objects.equals(this.valorSeguro, other.valorSeguro)) {
            return false;
        }
        if (!Objects.equals(this.valorDesconto, other.valorDesconto)) {
            return false;
        }
        if (!Objects.equals(this.valorOutros, other.valorOutros)) {
            return false;
        }
        if (!Objects.equals(this.valorItem, other.valorItem)) {
            return false;
        }
        if (!Objects.equals(this.ICMS, other.ICMS)) {
            return false;
        }
        if (!Objects.equals(this.PIS, other.PIS)) {
            return false;
        }
        if (!Objects.equals(this.COFINS, other.COFINS)) {
            return false;
        }
        if (!Objects.equals(this.NFe, other.NFe)) {
            return false;
        }
        if(!Objects.equals(this.IPI, other.IPI)){
            return false;
        }
        if(!Objects.equals(this.relAutomatico , other.relAutomatico)){
            return false;
        }
        if(!Objects.equals(this.codigoEANTrib , other.codigoEANTrib)){
            return false;
        }
        if(!Objects.equals(this.valorTotal , other.valorTotal)){
            return false;
        }
        if (!Objects.equals(this.infAdProd, other.infAdProd)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int compareTo(NFeItem o) {
        if(this.getValorProduto().compareTo(o.getValorProduto()) < 0) return -1;
        if(this.getValorProduto().compareTo(o.getValorProduto()) > 0) return 1;       
        return 0;
    }
}