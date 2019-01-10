package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoEmissao;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoFrete;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoItem;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoNotaFiscal;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoPagamento;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPED0000;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPED0200;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPED0220;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC100;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC170;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC150;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC190;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC460;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC465;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC470;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC800;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC850;
import br.com.mixfiscal.prodspedxnfe.services.ex.DadosSpedNaoCarregadosException;
import br.com.mixfiscal.prodspedxnfe.services.ex.ArquivoSpedVazioException;
import br.com.mixfiscal.prodspedxnfe.services.ex.ErroAoCarregarDadosSpedException;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import br.com.mixfiscal.prodspedxnfe.services.util.MetodosUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LeitorSpedFiscal { 
    // <editor-fold defaultstate="collapsed" desc="Membros Privados">
    private String caminhoArquivoSped;    
    private SPED0000 sped0000; 
    private Map<String, SPEDC100> mapSPEDC100;
    private List<SPEDC100> listaSPEDC100;
    private List<SPED0200> listaSPED0200;
    private List<SPEDC150> listaSPEDC150;
    
    private boolean arquivoSpedLido;
    private boolean operacaoDeRelatorio;
    private String[] conteudoArquivoSPED;
    private MetodosUtil mtUtil;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Membros Privados Cupom Fiscal e SAT">   
    private List<SPEDC190> listaSPEDC190;
    private List<SPEDC460>listaSPEDC460;    
    // </editor-fold>
    private List<SPEDC800>listaSPEDC800;
    // </editor-fold>
    private String infReg;

    public String getInfReg() {
        return infReg;
    }

    public void setInfReg(String infReg) {
        this.infReg = infReg;
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Construtores">
    public LeitorSpedFiscal() {
        this.sped0000 = new SPED0000();
        this.mapSPEDC100 = new HashMap<>();
        this.listaSPEDC100 = new ArrayList<>();        
        this.listaSPED0200 = new ArrayList<>();
        this.listaSPEDC150 = new ArrayList<>();        
        this.arquivoSpedLido = false;
        this.operacaoDeRelatorio = false;
        mtUtil = new MetodosUtil();
    
    // <editor-fold defaultstate="collapsed" desc="Inicializando Cupom Fiscal e SAT"> 
        this.listaSPEDC190 = new ArrayList<>();
        this.listaSPEDC460 = new ArrayList<>();
        this.listaSPEDC800 = new ArrayList<>();
    // </editor-fold>    
    
    }
    
    public LeitorSpedFiscal(String caminhoArquivoSped) {
        this();
        this.setCaminhoArquivoSped(caminhoArquivoSped);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters e Setters">
    public final String getCaminhoArquivoSped() { 
        return this.caminhoArquivoSped; 
    }
    
    public final void setCaminhoArquivoSped(String caminhoArquivoSped) { 
        this.caminhoArquivoSped = caminhoArquivoSped; 
    }
    
    public SPED0000 getSped0000() {
        return sped0000;
    }

    public void setSped0000(SPED0000 sped0000) {
        this.sped0000 = sped0000;
    }
    
    public Map<String, SPEDC100> getMapSPEDC100() throws DadosSpedNaoCarregadosException {
        if (!this.arquivoSpedLido)
            throw new DadosSpedNaoCarregadosException("O método lerArquivoSped() deve ser chamado antes de tentar usar getMapSPEDC100()");
        return mapSPEDC100;
    }
    
    public List<SPEDC100> getListaSPEDC100() throws DadosSpedNaoCarregadosException { 
        if (!this.arquivoSpedLido)
            throw new DadosSpedNaoCarregadosException("O método lerArquivoSped() deve ser chamado antes de tentar usar getListaSPEDC100()");
        return this.listaSPEDC100;
    }
    
    public List<SPED0200> getListaSPED0200() throws DadosSpedNaoCarregadosException {
        if (!this.arquivoSpedLido)
            throw new DadosSpedNaoCarregadosException("O método lerArquivoSped() deve ser chamado antes de tentar usar getListaSPED0200()");
        return this.listaSPED0200;
    }
    
    public List<SPEDC150> getListaSPEDC150() throws DadosSpedNaoCarregadosException{
        if(!this.arquivoSpedLido)
            throw new  DadosSpedNaoCarregadosException("O método lerArquivoSped() deve ser chamado antes de tentar usar getListaSPEDC150()");
        
        return this.listaSPEDC150;
    }
    public List<SPEDC460> getListaSPEDC460()throws DadosSpedNaoCarregadosException{
        if(!this.arquivoSpedLido)
            throw new  DadosSpedNaoCarregadosException("O método lerArquivoSped() deve ser chamado antes de tentar usar getListaSPEDC465()");
    
        return this.listaSPEDC460;
    }
    public List<SPEDC800> getListaSPEDC800()throws DadosSpedNaoCarregadosException{
        return this.listaSPEDC800;
    }
    public String[] getConteudoArquivoSPED() {
        return conteudoArquivoSPED;
    }

    public boolean isOperacaoDeRelatorio() {
        return operacaoDeRelatorio;
    }

    public void setOperacaoDeRelatorio(boolean operacaoDeRelatorio) {
        this.operacaoDeRelatorio = operacaoDeRelatorio;
    }

    public List<SPEDC190> getListaSPEDC190() {
        return listaSPEDC190;
    }

    public void setListaSPEDC190(List<SPEDC190> listaSPEDC190) {
        this.listaSPEDC190 = listaSPEDC190;
    }
    
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Públicos">  
    public void lerArquivoSped(String caminhoArquivoSped) throws ArquivoSpedVazioException, ErroAoCarregarDadosSpedException {
        this.setCaminhoArquivoSped(caminhoArquivoSped);
        this.lerArquivoSped();
    }
    
    public void lerArquivoSped() throws ArquivoSpedVazioException, ErroAoCarregarDadosSpedException {
        Path filePath = Paths.get(this.getCaminhoArquivoSped());
        String conteudo = null;
        
        try {            
            conteudo = new String(Files.readAllBytes(filePath));
        } catch(IOException ex) {
            throw new ErroAoCarregarDadosSpedException(String.format("Houve um erro de I/O ao tentar carregar os dados do arquivo '%s'", this.getCaminhoArquivoSped()), ex);
        }
        
        if (conteudo.isEmpty())
            throw new ArquivoSpedVazioException(String.format("O arquivo '%s' está vazio", this.caminhoArquivoSped));
        
        this.listaSPED0200.clear();
        this.listaSPEDC100.clear();
        this.mapSPEDC100.clear();
        this.listaSPEDC150.clear();
        // <editor-fold defaultstate="collapsed" desc="Limpar Cupom Fiscal e SAT">
        this.listaSPEDC190.clear();
        this.listaSPEDC460.clear();
        this.listaSPEDC800.clear();
        // </editor-fold>
        
        this.conteudoArquivoSPED = conteudo.split("\n");
        SPEDC100 c100Atual = null;        
        SPED0200 sped0200Atual = null;
        SPEDC460 c460Atual = null;
        SPEDC800 spedC800Atual = null;        
        Map<String, SPEDC150> mapSPEDC150 = new HashMap<>();
        Map<String, SPED0200> mapSPED0200 = new HashMap<>();        
        int seq = 0;
        String linha;
        String debug ="";
        for (int numLinha = 0; numLinha < this.conteudoArquivoSPED.length; numLinha++) {
            try{
            linha = this.conteudoArquivoSPED[numLinha];
            if (linha.indexOf("|0000|") == 0) {
                this.sped0000 = converterParaSPED0000(linha);
                debug = "|0000|";
            }
            
            if (linha.indexOf("|C100|") == 0) { 
                seq = 0;
                c100Atual = converterParaSPEDC100(linha);
               
                if(!this.isOperacaoDeRelatorio()){//verifica se a operação e solicitada pelo relatorio de divergencia
                    if(mtUtil.identificarTipoDocPelaChave(c100Atual.getChaveNFe(),"55")){ //contabiliza somente notas 55
                        c100Atual.setNumLinhaSPED(numLinha);
                        mapSPEDC100.put(c100Atual.getChaveNFe(), c100Atual);
                        listaSPEDC100.add(c100Atual);
                        debug = "|C100|";
                    }else{continue;}
                }else{//contabiliza somente notas 55 e 65
                    c100Atual.setNumLinhaSPED(numLinha);
                    mapSPEDC100.put(c100Atual.getChaveNFe(), c100Atual);
                    listaSPEDC100.add(c100Atual);
                    debug = "|C100|";
                }                           
            }
            
            if(linha.indexOf("|0150|") == 0) {
                SPEDC150 sc150 = this.converterParaSPEDC150(linha);
                sc150.setNumLinhaSPED(numLinha);
                listaSPEDC150.add(sc150);
                mapSPEDC150.put(sc150.getCodFornecedor(), sc150);
                debug = "|0150|";
            }
            
            if (linha.indexOf("|C170|") == 0) {
                SPEDC170 sc170 = converterParaSPEDC170(linha);
                sc170.setNumLinhaSPED(numLinha);
                sc170.setSequencia(seq++);
                sc170.setSPEDC100(c100Atual);
                c100Atual.addSPEDC170(sc170);
                debug = "|C170|";
            }
           
            if (linha.indexOf("|0200|") == 0) {
                sped0200Atual = converterParaSPED0200(linha); 
                sped0200Atual.setNumLinhaSPED(numLinha);
                listaSPED0200.add(sped0200Atual);
                try {
                    mapSPED0200.put(sped0200Atual.getCodItem(), sped0200Atual);
                } catch(Exception ex) {}
                debug = "|0200|";
                }
            if (linha.indexOf("|0220|") == 0) {
                SPED0220 sped0220 = converterParaSPED0220(linha);
                sped0220.setNumLinhaSPED(numLinha);
                sped0200Atual.setSPED0220(sped0220);
                debug = "|0220|";
            }
            // <editor-fold defaultstate="collapsed" desc="Cupom Fiscal e SAT">
               
             
            if(this.isOperacaoDeRelatorio()){
                if(linha.indexOf("|C190|") == 0){
                    SPEDC190 sc190 = this.converterParaSPEDC190(linha);
                    sc190.setNumLinhaSPED(numLinha);
                    sc190.setSequencia(seq++);
                    sc190.setSPEDC100(c100Atual);
                    c100Atual.addSPEDC190(sc190);
                    debug = "|C190|";
                    this.setInfReg(debug);
                }   
                if(linha.indexOf("|C460|") == 0){
                    seq = 0;
                    c460Atual = converterParaSPEDC460(linha);
                    c460Atual.setNumLinhaSPED(numLinha); 
                    this.listaSPEDC460.add(c460Atual);
                    debug = "|C460|";
                    this.setInfReg(debug);
                }            
                if(linha.indexOf("|C470|") == 0){
                    SPEDC470 spedCupom = converterParaSPEDC470(linha);
                    spedCupom.setNumLinhaSPED(numLinha);
                    spedCupom.setSequencia(seq++);
                    //spedCupom.setSPED0200(sped0200Atual);
                    spedCupom.setSPED460(c460Atual);
                    c460Atual.addSPEDC470(spedCupom);   
                    debug = "|C470|";
                    this.setInfReg(debug);
                }
                if(linha.indexOf("|C800|") == 0){
                    seq = 0;
                    spedC800Atual = this.converterParaSPEDC800(linha);
                    spedC800Atual.setNumLinhaSPED(numLinha);
                    this.listaSPEDC800.add(spedC800Atual);
                    debug = "|C800|";
                    this.setInfReg(debug);
                }
                if(linha.indexOf("|C850|") == 0){
                    SPEDC850 spdSat = this.converterParaSPEDC850(linha);
                    spdSat.setNumLinhaSPED(numLinha);
                    spdSat.setSequencia(seq++);
                    spdSat.setSpdc800(spedC800Atual);
                    spedC800Atual.addSPEDC850(spdSat);
                    debug = "|C850|";
                    this.setInfReg(debug);
                }                
            }
            }catch(Exception ex){ 
                String e = ex.getMessage();
                this.setInfReg(e);
                debug = "erro";
            }
            //System.out.println("REGISTRO: "+ debug);
            // </editor-fold>
        }
        
        // Faz a associação dos itens 0200 com os itens C170
        for (SPEDC100 spedC100 : listaSPEDC100) {
            spedC100.setFornecedor(mapSPEDC150.get(spedC100.getCodFornecedor()));
            for (SPEDC170 spedC170 : spedC100.getListaSPEDC170())
                spedC170.setSPED0200(mapSPED0200.get(spedC170.getCodItem()));            
        }        
        this.arquivoSpedLido = true;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Privados">
    private SPED0000 converterParaSPED0000(String linha) {        
        String[] campos = linha.split("\\|");
        SPED0000 sped = new SPED0000();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        
        sped.setCodVer(campos[2]);
        sped.setCodFin(campos[3]);
        try { sped.setDataIni(sdf.parse(campos[4])); } catch(ParseException ex) { }
        try { sped.setDataFin(sdf.parse(campos[5])); } catch(ParseException ex) { }        
        sped.setNomeEmpresarialEntidade(campos[6]);
        sped.setCNPJ(campos[7]);
        sped.setCPF(campos[8]);
        sped.setUF(campos[9]);
        sped.setIE(campos[10]);
        sped.setCodMun(campos[11]);
        sped.setIM(campos[12]);
        sped.setSuframa(campos[13]);
        sped.setIndPerfil(campos[14]);
        sped.setIndAtiv(campos[15]);
        
        return sped;
    }
    
    private SPEDC100 converterParaSPEDC100(String linha) {
        String[] campos = linha.split("\\|");
        SPEDC100 sped = new SPEDC100();                
        SimpleDateFormat sdf = new SimpleDateFormat("ddmmyyyy");
        
        sped.setTipoNotaFiscal(campos[2].equals("0") ? ETipoNotaFiscal.Entrada : ETipoNotaFiscal.Saida);
        sped.setTipoEmissao(campos[3].equals("0") ? ETipoEmissao.EmissaoPropria : ETipoEmissao.Terceiros);        
        sped.setCodFornecedor(campos[4]);
        sped.setModeloDocumento(campos[5]);
        sped.setCodigoSituacao(campos[6]);        
        sped.setSerie(campos[7]);
        sped.setNumeroNF(campos[8]);        
        sped.setChaveNFe(campos[9]);        
        try { sped.setDataEmissao(sdf.parse(campos[10])); } catch(Exception ex) { }
        try { sped.setDataEntradaSaida(sdf.parse(campos[11])); } catch(Exception ex) { }        
        try { sped.setValorTotalDocumento(new BigDecimal(campos[12].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try { sped.setTipoPagamento(ETipoPagamento.deCodigo(campos[13])); } catch(Exception ex) {  }
        try { sped.setValorTotalDesconto(new BigDecimal(campos[14].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try { sped.setValorAbatNaoTribut(new BigDecimal(campos[15].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try { sped.setValorTotalMercadorias(new BigDecimal(campos[16].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try { sped.setTipoFrete(ETipoFrete.deCodigo(campos[17])); } catch(Exception ex) {}
        try { sped.setValorFrete(new BigDecimal(campos[18].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try { sped.setValorSeguro(new BigDecimal(campos[19].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try { sped.setValorOutrasDespesas(new BigDecimal(campos[20].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try { sped.setValorBCICMS(new BigDecimal(campos[21].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try { sped.setValorICMS(new BigDecimal(campos[22].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try { sped.setValorBCICMSST(new BigDecimal(campos[23].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try { sped.setValorICMSST(new BigDecimal(campos[24].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try { sped.setValorIPI(new BigDecimal(campos[25].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try { sped.setValorPIS(new BigDecimal(campos[26].replace(".", "").replace(",", "."))); } catch(Exception ex) { }        
        try { sped.setValorCOFINS(new BigDecimal(campos[27].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try { sped.setValorPISST(new BigDecimal(campos[28].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try { sped.setValorCOFINSST(new BigDecimal(campos[29].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        
        return sped;
    }   
    
    private SPEDC150 converterParaSPEDC150(String linha){    
        String[] campos = linha.split("\\|");
        SPEDC150 sped = new  SPEDC150();
        sped.setCodFornecedor(campos[2]);
        sped.setNome(campos[3]);
        sped.setCnpj(campos[5]);
        sped.setCpf(campos[6]);        
        return sped;
    }
    
    private SPEDC170 converterParaSPEDC170(String linha) {
        String[] campos = linha.split("\\|");
        SPEDC170 sped = new SPEDC170();
        
        try {sped.setNumItem(campos[2]);} catch(Exception ex) {}
        try {sped.setCodItem(campos[3]);} catch(Exception ex) {}
        try {sped.setDescricaoComplementar(campos[4]); } catch(Exception ex) {}       
        try { sped.setQtd(new BigDecimal(campos[5].replace(".", "").replace(",", "."))); } catch(Exception ex) {}
        try {sped.setUnid(campos[6]);} catch(Exception ex) {}        
        try {sped.setValor(new BigDecimal(campos[7].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setValorDesconto(new BigDecimal(campos[8].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setIndMovimentacao((!StringUtil.isNullOrEmpty(campos[9]) && campos[9].equals("0")));} catch(Exception ex) {}
        try {sped.setCSTICMS(campos[10]);} catch(Exception ex) {}
        try {sped.setCFOP(campos[11]);} catch(Exception ex) {}
        try {sped.setCodigoNatureza(campos[12]);} catch(Exception ex) {}
        try {sped.setValorBCICMS(new BigDecimal(campos[13].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setAliqICMS(new BigDecimal(campos[14].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setValorICMS(new BigDecimal(campos[15].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setValorBCICMSST(new BigDecimal(campos[16].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setAliqICMSST(new BigDecimal(campos[17].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setValorICMSST(new BigDecimal(campos[18].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setIndicadorApuracao(campos[19]);} catch(Exception ex) {}
        try {sped.setCSTIPI(campos[20]);} catch(Exception ex) {}
        try {sped.setCodEnquadramentoIPI(campos[21]);} catch(Exception ex) {}
        try {sped.setValorBCIPI(new BigDecimal(campos[22].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setAliqIPI(new BigDecimal(campos[23].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setValorIPI(new BigDecimal(campos[24].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setCSTPIS(campos[25]);} catch(Exception ex) {}
        try {sped.setValorBCPIS(new BigDecimal(campos[26].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setAliqPISPercentual(new BigDecimal(campos[27].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setQuantidadeBCPIS(new BigDecimal(campos[28].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setAliqPISReais(new BigDecimal(campos[29].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setValorPIS(new BigDecimal(campos[30].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setCSTCOFINS(campos[31]);} catch(Exception ex) {}
        try {sped.setValorBCCOFINS(new BigDecimal(campos[32].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setAliqCOFINSPercentual(new BigDecimal(campos[33].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setQuantidadeBCCOFINS(new BigDecimal(campos[34].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setAliqCOFINSReais(new BigDecimal(campos[35].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setValorCOFINS(new BigDecimal(campos[36].replace(".", "").replace(",", "."))); } catch(Exception ex) { }
        try {sped.setCodigoCTA(campos[37]);} catch(Exception ex) {}
        
        return sped;
    }
    // <editor-fold defaultstate="collapsed" desc="Cupom Fiscal e SAT">
    private SPEDC460 converterParaSPEDC460(String linha){
        SPEDC460 sped = new SPEDC460();
        try{
            String[] campos = linha.split("\\|");
            try{sped.setCodMod(campos[2]);} catch(Exception ex) {}
            try{sped.setCodSit(campos[3]);} catch(Exception ex) {}
            try{sped.setNumDoc(campos[4]);} catch(Exception ex) {}
            try{sped.setDtDoc(campos[5]);} catch(Exception ex) {}
            try{sped.setVlDoc(campos[6]);} catch(Exception ex) {}
            try{sped.setVlPis(campos[7]);} catch(Exception ex) {}
            try{sped.setVlCofins(campos[8]);} catch(Exception ex) {}
            try{sped.setCpfCnpj(campos[9]);} catch(Exception ex) {}
            try{sped.setNomAdq(campos[10]);} catch(Exception ex) {}
        }catch(Exception ex){}
        return sped;
    }
    
    private SPEDC465 converterParaSPEDC465(String linha){
        SPEDC465 sped = new SPEDC465();
        String[] campos = linha.split("\\|");
        try{sped.setChvCfe(campos[1]);} catch(Exception ex) {}
        try{sped.setNumCcf(campos[2]);} catch(Exception ex) {}
        return sped;
    }
    
    private SPEDC470 converterParaSPEDC470(String linha){
        SPEDC470 sped = new SPEDC470();
        SPED0200 spd0200 = new SPED0200();
        String[] campo = linha.split("\\|");
        
        try{sped.setCodItem(campo[2]);} catch(Exception ex) { };
        //localiza o nome do produto pelo codigo e grava no membro 0200 do cupom para referencia
        spd0200 = this.localizarRegistro0200(sped.getCodItem());
        if(spd0200 != null){sped.setSPED0200(spd0200);}
        try{
            sped.setQtd(new BigDecimal(campo[3].replace(".", "").replace(",", ".")));
         } catch(Exception ex) { };
        try{
            sped.setQtdCanc(new BigDecimal(campo[4].replace(".", "").replace(",", ".")));
        } catch(Exception ex) { };
        
        try{sped.setUnid(campo[5]);}catch(Exception ex) {};
        try{
            sped.setValor(new BigDecimal(campo[6].replace(",", ".")));
        }catch(Exception ex) {};
        
        try{sped.setCSTICMS(campo[7]);}catch(Exception ex) {};
        try{sped.setCFOP(campo[8]);}catch(Exception ex) {};
        
        try{
            sped.setAliqICMS(new BigDecimal(campo[9].replace(".", "").replace(",", ".")));
        }catch(Exception ex) { };
        
        try{
            sped.setValorPIS(new BigDecimal(campo[10].replace(".", "").replace(",", ".")));
        }catch(Exception ex){};
    
        try{
        sped.setValorCOFINS(new BigDecimal(campo[11].replace(".", "").replace(",", ".")));
        }catch(Exception ex){}
        return sped;
    }    
    
    private SPEDC800 converterParaSPEDC800(String linha){
        SPEDC800 sped = new SPEDC800();
        SimpleDateFormat sdf = new SimpleDateFormat("ddmmyyyy");
        try{
             String[] campos = linha.split("\\|");
            try{ sped.setCodMod(campos[2]);}catch(Exception ex){};
            try{ sped.setCodSit(campos[3]);}catch(Exception ex){};
            try{ sped.setNumCfe(campos[4]);}catch(Exception ex){};
            try{ sped.setDtDoc(sdf.parse(campos[5]));}catch(Exception ex){};
            try{ sped.setVlCfe(this.convertToBigDecimal(campos[6]));}catch(Exception ex){};
            try{ sped.setVlPis(this.convertToBigDecimal(campos[7]));}catch(Exception ex){};
            try{ sped.setVlCofins(this.convertToBigDecimal(campos[8]));}catch(Exception ex){};
            try{ sped.setCnpjCpf(campos[9]);}catch(Exception ex){};
            try{ sped.setNrSat(campos[10]);}catch(Exception ex){};
            try{ sped.setChvCfe(campos[11]);}catch(Exception ex){};
            try{ sped.setVlDesc(this.convertToBigDecimal(campos[12]));}catch(Exception ex){};
            try{ sped.setVlMerc(this.convertToBigDecimal(campos[13]));}catch(Exception ex){};
            try{ sped.setVlOutDa(this.convertToBigDecimal(campos[14]));}catch(Exception ex){};
            try{ sped.setVlIcms(this.convertToBigDecimal(campos[15]));}catch(Exception ex){};
            try{ sped.setVlPisSt(this.convertToBigDecimal(campos[16]));}catch(Exception ex){};
            try{ sped.setVlCofinsSt(this.convertToBigDecimal(campos[17]));}catch(Exception ex){};
             
        }catch(Exception ex){}
        
        return sped;
    }
    
    private SPEDC850 converterParaSPEDC850(String linha){
        SPEDC850 spedC850 = new SPEDC850();
        try{
            String[] campo = linha.split("\\|");
            try{spedC850.setCstIcms(this.convertToBigDecimal(campo[2]));}catch(Exception ex){};
            try{spedC850.setCfop(campo[3]);}catch(Exception ex){};
            try{spedC850.setAliqIcms(this.convertToBigDecimal(campo[4]));}catch(Exception ex){};
            try{spedC850.setVlOpr(this.convertToBigDecimal(campo[5]));}catch(Exception ex){};
            try{spedC850.setVlBcIcms(this.convertToBigDecimal(campo[6]));}catch(Exception ex){};
            try{spedC850.setVlIcms(this.convertToBigDecimal(campo[7]));}catch(Exception ex){};
            try{spedC850.setCodObs(campo[8]);}catch(Exception ex){};     
        }catch(Exception ex){}        
        return spedC850;        
    }
    private SPEDC190 converterParaSPEDC190(String linha){
        SPEDC190 spedC190 = new SPEDC190();
        try{
            String[] campo = linha.split("\\|");
            try{spedC190.setCstIcms(this.convertToBigDecimal(campo[2]));}catch(Exception ex){};
            try{spedC190.setCfop(campo[3]);}catch(Exception ex){};
            try{spedC190.setAliqIcms(this.convertToBigDecimal(campo[4]));}catch(Exception ex){};
            try{spedC190.setVlOpr(this.convertToBigDecimal(campo[5]));}catch(Exception ex){};
            try{spedC190.setVlBcIcms(this.convertToBigDecimal(campo[6]));}catch(Exception ex){};
            try{spedC190.setVlIcms(this.convertToBigDecimal(campo[7]));}catch(Exception ex){};
            try{spedC190.setCodObs(campo[8]);}catch(Exception ex){};     
        }catch(Exception ex){}        
        return spedC190;        
    }
    
    private SPED0200 localizarRegistro0200(String codItem){
        Optional<SPED0200> res = this.listaSPED0200.stream().filter(spd -> spd.getCodItem().equals(codItem)).findFirst();
        SPED0200 spd0200 = res.isPresent() ? res.get() : null;
        return spd0200;
    }
    // </editor-fold>
    private SPED0200 converterParaSPED0200(String linha) {
        String[] campos = linha.split("\\|");
        SPED0200 sped = new SPED0200();
        
        ETipoItem tipoItem = ETipoItem.Desconhecido;
        
        try { tipoItem = ETipoItem.deCodigo(campos[7]); }
        catch(Exception ex) { tipoItem = ETipoItem.Desconhecido; }
        
        try{sped.setCodItem(campos[2]);}catch(Exception ex){};
        try{sped.setDescrItem(campos[3]);}catch(Exception ex){};
        try{sped.setCodBarra(campos[4]);}catch(Exception ex){};
        try{sped.setTipoItem(tipoItem);}catch(Exception ex){};
        try{sped.setCodNCM(campos[8]);}catch(Exception ex){};
        
        return sped;
    }    
    
    private SPED0220 converterParaSPED0220(String linha) {
        String[] campos = linha.split("\\|");
        SPED0220 sped = new SPED0220();
        BigDecimal valorFator = BigDecimal.ZERO;
        
        try { valorFator = new BigDecimal(campos[3].replace(".", "").replace(",", ".")); }
        catch(Exception ex) { valorFator = BigDecimal.ZERO; }
        
        try {sped.setNomeUnidadeMedida(campos[2]);}catch(Exception ex){};
        try {sped.setFator(valorFator);}catch(Exception ex){};
        
        return sped;
    }    
    
    private BigDecimal convertToBigDecimal(String campo){
        BigDecimal valor = BigDecimal.ZERO;
        try{
            valor = new BigDecimal(campo.replace(".", "").replace(",", "."));
        }catch(Exception ex){}
        return valor;
    }
    
    
   
    // </editor-fold>
}
