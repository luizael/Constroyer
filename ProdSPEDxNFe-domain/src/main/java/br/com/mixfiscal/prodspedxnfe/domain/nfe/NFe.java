package br.com.mixfiscal.prodspedxnfe.domain.nfe;

import br.com.mixfiscal.prodspedxnfe.domain.enums.ELocalDestino;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoNotaFiscal;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoPagamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NFe implements Serializable {  
    private static final long serialVersionUID = 2572780307901981314L;    
    
    // <ide>
    private String chaveNFe; // prop Id da tag infNFe  
    private String natOp; // tag <natOp>
    private ETipoPagamento indicadorPagamento; // tag <indPag>
    private String modelo; // tag <mod>
    private String serie; // tag <serie>
    private String numeroNotaFiscal; // tag <nNF>
    private Date dataHoraEmissao; // tag <dhEmi>
    private Date dataHoraSaiEnt; // tag <dhSaiEnt>
    private ETipoNotaFiscal tipoNotaFiscal; // tag <tpNF>
    private ELocalDestino idDestino; // tag <idDest>
    // </ide>
            
    private PessoaJuridica emitente; // dados da tag <emit>
    private PessoaJuridica destinatario; // dados da tag <dest>
    
    // <total>
    //      <ICMSTot>
    private BigDecimal valorTotalBCICMS; // <vBC>
    private BigDecimal valorTotalICMS; // <vICMS>
    private BigDecimal valorTotalICMSDesonerado; // <vICMSDeson>
    private BigDecimal valorTotalBCICMSST; // <vBCST>
    private BigDecimal valorTotalICMSST; // <vST>    
    private BigDecimal valorTotalProdServ; // <vProd>
    private BigDecimal valorTotalFrete; // <vFrete>
    private BigDecimal valorTotalSeguro; // <vSeg>
    private BigDecimal valorTotalDesconto; // <vDesc>
    private BigDecimal valorTotalII; // <vII>
    private BigDecimal valorTotalIPI; // <vIPI>
    private BigDecimal valorPIS; // <vPIS>
    private BigDecimal valorCOFINS; // <vCOFINS>
    private BigDecimal valorOutrasDespesas; // <vOutro>
    private BigDecimal valorNotaFiscal; // <vNF>
    private BigDecimal valorTotalTributos; // <vTotTrib>
    //      </ICMSTot>
    // </total>
    
    private List<NFeItem> itens;
    private Map<String, NFeItem> mapCProdItens;
    private Map<Integer, NFeItem> mapNItemItens;
    
    public NFe() {
        emitente = new PessoaJuridica();
        destinatario = new PessoaJuridica();
        itens = new ArrayList<>();
        mapCProdItens = new HashMap<>();
        mapNItemItens = new HashMap<>();
    }
    
    // <editor-fold desc="Getters e Setters" defaultstate="collapsed">
    public String getChaveNFe() {
        return chaveNFe;
    }

    public void setChaveNFe(String chaveNFe) {
        this.chaveNFe = chaveNFe;
    }
   
    public PessoaJuridica getEmitente() {
        return this.emitente;
    }
    
    public void setEmitente(PessoaJuridica emitente) {
        this.emitente = emitente;
    }
   
    public PessoaJuridica getDestinatario() {
        return this.destinatario;
    }
    
    public void setDestinatario(PessoaJuridica destinatario) {
        this.destinatario = destinatario;
    }
    
    public List<NFeItem> getItens() {
        return itens;
    }

    public void setItens(List<NFeItem> itens) {
        this.itens = itens;
        for (NFeItem item : this.itens) {
            this.mapCProdItens.put(item.getCodigoProduto(), item);
            this.mapNItemItens.put(item.getNumeroItem(), item);
        }
    }
    
    public NFeItem retornarItemPorCProd(String cProd) {
        return this.mapCProdItens.get(cProd);
    }
    
    public NFeItem retornarItemPorNItem(int nItem) {
        return this.mapNItemItens.get(nItem);
    }

    public String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public void setNumeroNotaFiscal(String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public Date getDataHoraEmissao() {
        return dataHoraEmissao;
    }

    public void setDataHoraEmissao(Date dataHoraEmissao) {
        this.dataHoraEmissao = dataHoraEmissao;
    }

    public BigDecimal getValorNotaFiscal() {
        return valorNotaFiscal;
    }

    public void setValorNotaFiscal(BigDecimal valorNotaFiscal) {
        this.valorNotaFiscal = valorNotaFiscal;
    }

    public BigDecimal getValorTotalICMSST() {
        return valorTotalICMSST;
    }

    public void setValorTotalICMSST(BigDecimal valorTotalICMSST) {
        this.valorTotalICMSST = valorTotalICMSST;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNatOp() {
        return natOp;
    }

    public void setNatOp(String natOp) {
        this.natOp = natOp;
    }

    public ETipoPagamento getIndicadorPagamento() {
        return indicadorPagamento;
    }

    public void setIndicadorPagamento(ETipoPagamento indicadorPagamento) {
        this.indicadorPagamento = indicadorPagamento;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Date getDataHoraSaiEnt() {
        return dataHoraSaiEnt;
    }

    public void setDataHoraSaiEnt(Date dataHoraSaiEnt) {
        this.dataHoraSaiEnt = dataHoraSaiEnt;
    }

    public ETipoNotaFiscal getTipoNotaFiscal() {
        return tipoNotaFiscal;
    }

    public void setTipoNotaFiscal(ETipoNotaFiscal tipoNotaFiscal) {
        this.tipoNotaFiscal = tipoNotaFiscal;
    }

    public ELocalDestino getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(ELocalDestino idDestino) {
        this.idDestino = idDestino;
    }

    public BigDecimal getValorTotalBCICMS() {
        return valorTotalBCICMS;
    }

    public void setValorTotalBCICMS(BigDecimal valorTotalBCICMS) {
        this.valorTotalBCICMS = valorTotalBCICMS;
    }

    public BigDecimal getValorTotalICMS() {
        return valorTotalICMS;
    }

    public void setValorTotalICMS(BigDecimal valorTotalICMS) {
        this.valorTotalICMS = valorTotalICMS;
    }

    public BigDecimal getValorTotalICMSDesonerado() {
        return valorTotalICMSDesonerado;
    }

    public void setValorTotalICMSDesonerado(BigDecimal valorTotalICMSDesonerado) {
        this.valorTotalICMSDesonerado = valorTotalICMSDesonerado;
    }

    public BigDecimal getValorTotalBCICMSST() {
        return valorTotalBCICMSST;
    }

    public void setValorTotalBCICMSST(BigDecimal valorTotalBCICMSST) {
        this.valorTotalBCICMSST = valorTotalBCICMSST;
    }

    public BigDecimal getValorTotalProdServ() {
        return valorTotalProdServ;
    }

    public void setValorTotalProdServ(BigDecimal valorTotalProdServ) {
        this.valorTotalProdServ = valorTotalProdServ;
    }

    public BigDecimal getValorTotalFrete() {
        return valorTotalFrete;
    }

    public void setValorTotalFrete(BigDecimal valorTotalFrete) {
        this.valorTotalFrete = valorTotalFrete;
    }

    public BigDecimal getValorTotalSeguro() {
        return valorTotalSeguro;
    }

    public void setValorTotalSeguro(BigDecimal valorTotalSeguro) {
        this.valorTotalSeguro = valorTotalSeguro;
    }

    public BigDecimal getValorTotalDesconto() {
        return valorTotalDesconto;
    }

    public void setValorTotalDesconto(BigDecimal valorTotalDesconto) {
        this.valorTotalDesconto = valorTotalDesconto;
    }

    public BigDecimal getValorTotalII() {
        return valorTotalII;
    }

    public void setValorTotalII(BigDecimal valorTotalII) {
        this.valorTotalII = valorTotalII;
    }

    public BigDecimal getValorTotalIPI() {
        return valorTotalIPI;
    }

    public void setValorTotalIPI(BigDecimal valorTotalIPI) {
        this.valorTotalIPI = valorTotalIPI;
    }

    public BigDecimal getValorPIS() {
        return valorPIS;
    }

    public void setValorPIS(BigDecimal valorPIS) {
        this.valorPIS = valorPIS;
    }

    public BigDecimal getValorCOFINS() {
        return valorCOFINS;
    }

    public void setValorCOFINS(BigDecimal valorCOFINS) {
        this.valorCOFINS = valorCOFINS;
    }

    public BigDecimal getValorOutrasDespesas() {
        return valorOutrasDespesas;
    }

    public void setValorOutrasDespesas(BigDecimal valorOutrasDespesas) {
        this.valorOutrasDespesas = valorOutrasDespesas;
    }

    public BigDecimal getValorTotalTributos() {
        return valorTotalTributos;
    }

    public void setValorTotalTributos(BigDecimal valorTotalTributos) {
        this.valorTotalTributos = valorTotalTributos;
    }
    // </editor-fold>
    
    // <editor-fold desc="equals e hashCode" defaultstate="collapsed">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.chaveNFe);
        hash = 29 * hash + Objects.hashCode(this.natOp);
        hash = 29 * hash + Objects.hashCode(this.indicadorPagamento);
        hash = 29 * hash + Objects.hashCode(this.modelo);
        hash = 29 * hash + Objects.hashCode(this.serie);
        hash = 29 * hash + Objects.hashCode(this.numeroNotaFiscal);
        hash = 29 * hash + Objects.hashCode(this.dataHoraEmissao);
        hash = 29 * hash + Objects.hashCode(this.dataHoraSaiEnt);
        hash = 29 * hash + Objects.hashCode(this.tipoNotaFiscal);
        hash = 29 * hash + Objects.hashCode(this.idDestino);
        hash = 29 * hash + Objects.hashCode(this.emitente);
        hash = 29 * hash + Objects.hashCode(this.destinatario);
        hash = 29 * hash + Objects.hashCode(this.valorTotalBCICMS);
        hash = 29 * hash + Objects.hashCode(this.valorTotalICMS);
        hash = 29 * hash + Objects.hashCode(this.valorTotalICMSDesonerado);
        hash = 29 * hash + Objects.hashCode(this.valorTotalBCICMSST);
        hash = 29 * hash + Objects.hashCode(this.valorTotalICMSST);
        hash = 29 * hash + Objects.hashCode(this.valorTotalProdServ);
        hash = 29 * hash + Objects.hashCode(this.valorTotalFrete);
        hash = 29 * hash + Objects.hashCode(this.valorTotalSeguro);
        hash = 29 * hash + Objects.hashCode(this.valorTotalDesconto);
        hash = 29 * hash + Objects.hashCode(this.valorTotalII);
        hash = 29 * hash + Objects.hashCode(this.valorTotalIPI);
        hash = 29 * hash + Objects.hashCode(this.valorPIS);
        hash = 29 * hash + Objects.hashCode(this.valorCOFINS);
        hash = 29 * hash + Objects.hashCode(this.valorOutrasDespesas);
        hash = 29 * hash + Objects.hashCode(this.valorNotaFiscal);
        hash = 29 * hash + Objects.hashCode(this.valorTotalTributos);
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
        final NFe other = (NFe) obj;
        if (!Objects.equals(this.chaveNFe, other.chaveNFe)) {
            return false;
        }
        if (!Objects.equals(this.natOp, other.natOp)) {
            return false;
        }
        if (!Objects.equals(this.modelo, other.modelo)) {
            return false;
        }
        if (!Objects.equals(this.serie, other.serie)) {
            return false;
        }
        if (!Objects.equals(this.numeroNotaFiscal, other.numeroNotaFiscal)) {
            return false;
        }
        if (!Objects.equals(this.dataHoraEmissao, other.dataHoraEmissao)) {
            return false;
        }
        if (!Objects.equals(this.dataHoraSaiEnt, other.dataHoraSaiEnt)) {
            return false;
        }
        if (this.indicadorPagamento != other.indicadorPagamento) {
            return false;
        }
        if (this.tipoNotaFiscal != other.tipoNotaFiscal) {
            return false;
        }
        if (this.idDestino != other.idDestino) {
            return false;
        }
        if (!Objects.equals(this.emitente, other.emitente)) {
            return false;
        }
        if (!Objects.equals(this.destinatario, other.destinatario)) {
            return false;
        }
        if (!Objects.equals(this.valorTotalBCICMS, other.valorTotalBCICMS)) {
            return false;
        }
        if (!Objects.equals(this.valorTotalICMS, other.valorTotalICMS)) {
            return false;
        }
        if (!Objects.equals(this.valorTotalICMSDesonerado, other.valorTotalICMSDesonerado)) {
            return false;
        }
        if (!Objects.equals(this.valorTotalBCICMSST, other.valorTotalBCICMSST)) {
            return false;
        }
        if (!Objects.equals(this.valorTotalICMSST, other.valorTotalICMSST)) {
            return false;
        }
        if (!Objects.equals(this.valorTotalProdServ, other.valorTotalProdServ)) {
            return false;
        }
        if (!Objects.equals(this.valorTotalFrete, other.valorTotalFrete)) {
            return false;
        }
        if (!Objects.equals(this.valorTotalSeguro, other.valorTotalSeguro)) {
            return false;
        }
        if (!Objects.equals(this.valorTotalDesconto, other.valorTotalDesconto)) {
            return false;
        }
        if (!Objects.equals(this.valorTotalII, other.valorTotalII)) {
            return false;
        }
        if (!Objects.equals(this.valorTotalIPI, other.valorTotalIPI)) {
            return false;
        }
        if (!Objects.equals(this.valorPIS, other.valorPIS)) {
            return false;
        }
        if (!Objects.equals(this.valorCOFINS, other.valorCOFINS)) {
            return false;
        }
        if (!Objects.equals(this.valorOutrasDespesas, other.valorOutrasDespesas)) {
            return false;
        }
        if (!Objects.equals(this.valorNotaFiscal, other.valorNotaFiscal)) {
            return false;
        }
        if (!Objects.equals(this.valorTotalTributos, other.valorTotalTributos)) {
            return false;
        }
        if (!Objects.equals(this.itens, other.itens)) {
            return false;
        }
        if (!Objects.equals(this.mapCProdItens, other.mapCProdItens)) {
            return false;
        }
        if (!Objects.equals(this.mapNItemItens, other.mapNItemItens)) {
            return false;
        }
        return true;
    }
    // </editor-fold>    
}
