package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS10;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS30;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS70;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.ICMS90;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.IPI;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.NFeItem;
import br.com.mixfiscal.prodspedxnfe.domain.nfe.TotalImposto;
import br.com.mixfiscal.prodspedxnfe.domain.own.RelacaoProdutoFornecedor;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPEDC170;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Filtro {
    private MetodosUtil util; 
    private int cont = 0;
    public int getCont() {
        return cont;
    }    
    public void relacionamentoExistente(List<NFeItem>itensNFe , List<SPEDC170>itensSC170,Map<SPEDC170, NFeItem[]> mapItensSpdNFe, LeitorSpedFiscal leitorSped, StringBuilder sbRelModifSPED, int oldLinha, StringBuffer docFornecedor){
        //Antes de tudo, verifica se ja existe uma relação para cada Produto e Fornecedor da Empresa corrente
        util = new MetodosUtil();
        System.out.println("###FILTRO RELACIONAMENTO EXISTENTE");
        this.cont = 0;
        //final List<NFeItem> nfeItens = new ArrayList<>();
        itensSC170.stream().forEach((sc170) -> {
            itensNFe.stream().forEach((nfeItem) -> {                
                List<RelacaoProdutoFornecedor> result = null; 
                try{
                    if(nfeItem != null){
                        result = util.buscarRelacaoProdutoFornecedor(
                                docFornecedor.toString(), 
                                sc170.getCodItem(), 
                                nfeItem.getCodigoProduto(),
                                sc170.getSPEDC100().getChaveNFe(),
                                sc170.getSPED0200().getDescrItem(),
                                nfeItem.getDescricaoProduto()
                        );
                    }
                }catch(Exception ex){  
                    System.out.println("" + ex.getMessage());
                     //log.error(String.format(" relacionamento nao existente no banco de dados c170: ("+sc170.getDescricaoComplementar() + ") NFE-Item: (" +nfeItem.getDescricaoProduto() +") " +  ex.getMessage()));
                }
                String achou = "RELACIONAMENTO EXISTENTE ACHOU: NAO"; 
                if (result != null && result.size() > 0){
                        achou = "RELACIONAMENTO EXISTENTE ACHOU: SIM";   
                        sc170.setFiltroDeRelacionamento("RELACIONAMENTO EXISTENTE");
                        mapItensSpdNFe.put(sc170, new NFeItem[]{nfeItem});
                        
                        util.aplicarModificacoesIdentificadasSPEDComNFe(sc170,nfeItem,false,achou,
                            oldLinha,
                            sbRelModifSPED,
                            itensSC170,
                            leitorSped
                            );
                        itensNFe.remove(nfeItem);
                        //NFeItem nfeIt = new NFeItem(); 
                        //nfeIt = nfeItem;
                        //nfeIt.setRelAutomatico(1);
                        //nfeItens.add(nfeItem);
                   }
             });
        });        
//        if (nfeItens.size() > 0 ) {                
//           for (NFeItem nfei : nfeItens){
//               itensNFe.remove(nfei);
//                this.cont++;
//           }
//        }   
    }    
    public void mesmaOrdem(boolean assumirItensEmOrdem, List<SPEDC170>itensSC170, List<NFeItem>itensNFe, Map<SPEDC170, NFeItem[]> mapItensSpdNFe ,LeitorSpedFiscal leitorSped, StringBuilder sbRelModifSPED, int oldLinha,Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociadosTemp){
         System.out.println("###FILTRO MESMA ORDEM");
            if (assumirItensEmOrdem) {           
                this.cont = 0;
                itensSC170.forEach(spd -> {
                    this.filtrarItensNFe(spd, 
                            n -> n.getSequencia() == spd.getSequencia(),
                            "POR MESMA ORDEM",
                            itensSC170,
                            itensNFe,
                            mapItensSpdNFe,
                            leitorSped,
                            sbRelModifSPED,
                            oldLinha,
                            itensPreAssociadosTemp,
                            false);
                });
            }
    }    
    public void porEan(List<SPEDC170>itensSC170, List<NFeItem>itensNFe, Map<SPEDC170, NFeItem[]> mapItensSpdNFe ,LeitorSpedFiscal leitorSped, StringBuilder sbRelModifSPED, int oldLinha,Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociadosTemp, boolean comparaSize){
        System.out.println("###FILTRO MESMA EAN");
        try{
               this.cont = 0;
               itensSC170.stream()
                        .forEach((sc170) -> {
                           
                            filtrarItensNFe(sc170, 
                                    (n ->n.getCodigoEAN().equals(sc170.getSPED0200().getCodBarra())),
                                    "EAN",
                                    itensSC170,
                                    itensNFe,
                                    mapItensSpdNFe,
                                    leitorSped,
                                    sbRelModifSPED,
                                    oldLinha,
                                    itensPreAssociadosTemp,
                                    comparaSize                                    
                                    );
                });
        }catch(Exception ex){
            System.out.println(""+ ex.getMessage());
        }
    }    
    public void porEanTrib(List<SPEDC170>itensSC170, List<NFeItem>itensNFe, Map<SPEDC170, NFeItem[]> mapItensSpdNFe ,LeitorSpedFiscal leitorSped, StringBuilder sbRelModifSPED, int oldLinha,Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociadosTemp, boolean comparaSize,int contador){
         try{
                System.out.println("###FILTRO MESMA EANTRIB");
                 this.cont = 0;
                itensSC170.stream().forEach((sc170) -> {
                    filtrarItensNFe(sc170, 
                            (n ->n.getCodigEANTrib().equals(sc170.getSPED0200().getCodBarra())),
                            "EANTRIB",
                            itensSC170,
                            itensNFe,
                            mapItensSpdNFe,
                            leitorSped,
                            sbRelModifSPED,
                            oldLinha,
                            itensPreAssociadosTemp,
                            comparaSize
                            
                    );
                });
            }catch(Exception ex){System.out.println(""+ ex.getMessage());}
    }    
    public void umParaUm(List<NFeItem>itensNFe , List<SPEDC170>itensSC170,Map<SPEDC170, NFeItem[]> mapItensSpdNFe, LeitorSpedFiscal leitorSped, StringBuilder sbRelModifSPED, int oldLinha){
        // Se existe apenas um item no sped e na nota, assumimos que é o mesmo
        util = new MetodosUtil();
        System.out.println("###FILTRO UM PRA UM");
        if (itensSC170.size() == 1 && itensNFe.size() == 1) {
            SPEDC170 sc170 = (SPEDC170)itensSC170.get(0);
            NFeItem nfeItem = (NFeItem)itensNFe.get(0);
            nfeItem.setRelAutomatico(1);
            sc170.setFiltroDeRelacionamento("FILTRO UM PRA UM");
            mapItensSpdNFe.put(sc170, new NFeItem[] { nfeItem });
            
            itensNFe.remove(nfeItem);//retira o item ligado da lista 
            if(sc170 == null)return;
            if(nfeItem == null)return;
            util.imprimirInformacoesParaDepuracao(sc170, nfeItem,"UM POR UM  ACHOU: SIM");
            //log.info(String.format("o Item foi encontrado pelo filtro um por um no XML "+ sc170.getSPEDC100().getChaveNFe() +" c170:(" +sc170.getDescricaoComplementar()+") nfeItem: (" + nfeItem.getDescricaoProduto())+")");
            util.aplicarModificacoesIdentificadasSPEDComNFe(sc170,nfeItem,false,"UM POR UM  ACHOU",
                            oldLinha,
                            sbRelModifSPED,
                            itensSC170,
                            leitorSped
                            );
            this.cont++;
            //return;
        }
    }
    public void verificaCombinacoesUmPraMuitos(Map<MetodosUtil.idxSPEDC170,List<NFeItem>> map) 
    {
      map.entrySet().stream().forEach(e->
      {
       if(e.getValue().size() > 1) 
       {
        e.getKey().getSped().setIdReferenciaComNFe(BigDecimal.ONE);
       } else 
       {
        e.getKey().getSped().setIdReferenciaComNFe(BigDecimal.ZERO);
       } 
      });
    }
    public  void umParaMuitos(List<NFeItem>itensNFe , List<SPEDC170>itensSC170,Map<SPEDC170, NFeItem[]> mapItensSpdNFe, LeitorSpedFiscal leitorSped, StringBuilder sbRelModifSPED, int oldLinha,Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociadosTemp){
    util = new MetodosUtil();
    System.out.println("###FILTRO UM PRA MUITOS");
    this.cont = 0;
    if (itensNFe.size() > itensSC170.size()){   
            MetodosUtil metodosUtil = new MetodosUtil();           
            for(SPEDC170 itemsc170 : itensSC170){
                metodosUtil.getListaDeCombinacoesEncontrados().clear();
                metodosUtil.identificarCombinacoesPossiveisDoSpedComNfe(itemsc170,itensNFe );
                verificaCombinacoesUmPraMuitos(metodosUtil.getListaDeCombinacoesEncontrados());
                if(metodosUtil.getListaDeCombinacoesEncontrados().keySet().size() == 1){//deu uma combinação
                    Optional<Map.Entry<MetodosUtil.idxSPEDC170, List<NFeItem>>> gruposEncontrados = metodosUtil.getListaDeCombinacoesEncontrados().entrySet().stream().findFirst();
                    if(gruposEncontrados.isPresent())
                    {
                        Map.Entry<MetodosUtil.idxSPEDC170, List<NFeItem>> grupo = gruposEncontrados.get();
                        SPEDC170 sc170 = grupo.getKey().getSped();
                        NFeItem[] nfItens =  new NFeItem[grupo.getValue().size()];
                        BigDecimal valorProd = BigDecimal.ZERO;
                        BigDecimal valorIcmsSt = BigDecimal.ZERO;
                        BigDecimal valorIpi = BigDecimal.ZERO;

                        for(int i=0; i < grupo.getValue().size(); i++){
                            nfItens[i] = grupo.getValue().get(i);
                            valorProd = sc170.getValor();//valorProd.add(nfItens[i].getValorProduto());
                            TotalImposto totalImpSt =  metodosUtil.identificarImpostoNFe(nfItens[i]);
                            
                            if(totalImpSt.getValorICMSST() != null)
                                valorIcmsSt = valorIcmsSt.add(totalImpSt.getValorICMSST());
                            
                            IPI ipi = (IPI)nfItens[i].getIPI();
                            if(ipi != null)
                                if(ipi.getvIPI() != null)
                                    valorIpi =  valorIpi.add(ipi.getvIPI());

                            itensNFe.remove(nfItens[i]);
                            util.imprimirInformacoesParaDepuracao(sc170,nfItens[i], " *UM PRA MUITOS ACHOU: SIM" );
                        }

                        NFeItem nfeItemAgregado = nfItens[0];
                        nfeItemAgregado.setValorProduto(valorProd);
                        if(nfeItemAgregado.getICMS() instanceof ICMS10 ){
                             ICMS10 icms = (ICMS10)nfeItemAgregado.getICMS();
                             icms.setValorICMSST(valorIcmsSt);
                        }else if(nfeItemAgregado.getICMS() instanceof ICMS30){
                            ICMS30 icms = (ICMS30)nfeItemAgregado.getICMS();
                            icms.setValorICMSST(valorIcmsSt);
                        }else if(nfeItemAgregado.getICMS() instanceof ICMS70){
                            ICMS70 icms = (ICMS70)nfeItemAgregado.getICMS();
                            icms.setValorICMSST(valorIcmsSt);
                        }else if(nfeItemAgregado.getICMS() instanceof ICMS90){
                            ICMS90 icms = (ICMS90)nfeItemAgregado.getICMS();
                            icms.setValorICMSST(valorIcmsSt);
                        }
                        IPI ipi = (IPI)nfeItemAgregado.getIPI();
                            if(ipi != null)
                                if(ipi.getvIPI() != null)
                                    ipi.setvIPI(valorIpi);
                        sc170.setFiltroDeRelacionamento("FILTRO UM PRA MUITOS");
                        mapItensSpdNFe.put(sc170,nfItens);
                       
                        try{
                            util.aplicarModificacoesIdentificadasSPEDComNFe(sc170,nfeItemAgregado,false,"UM PRA MUITOS",oldLinha, sbRelModifSPED, itensSC170, leitorSped); 
                            this.cont++;
                        }catch(Exception ex){System.out.println(""+ex.getMessage()); }
                    }
                }
        }
    }
    this.descricaoAproximadaValorIdentico(itensNFe, itensSC170, mapItensSpdNFe, leitorSped, sbRelModifSPED, oldLinha, itensPreAssociadosTemp);
    }    
    public void umParaMuitosEan(List<SPEDC170>itensSC170, List<NFeItem>itensNFe, Map<SPEDC170, NFeItem[]> mapItensSpdNFe, LeitorSpedFiscal leitorSped, StringBuilder sbRelModifSPED, int oldLinha,Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociadosTemp){
        System.out.println("### UM PARA MUITOS EAN");
        this.cont = 0;
        if(itensSC170.size() < itensNFe.size())
            for(SPEDC170 sc170 : itensSC170){
                if(sc170.getSPED0200().getCodBarra().isEmpty())continue;
                List<NFeItem>grupo = itensNFe
                                    .stream()
                                    .filter(
                                            n -> n.getCodigoEAN().equals(sc170.getSPED0200().getCodBarra())
                                    ).collect(Collectors.toList());
                
                if(grupo.size() > 1){
                    agruparItensNfe(grupo,itensNFe,
                            sc170,
                            mapItensSpdNFe,
                            "UM PARA MUITOS EAN ACHOU : SIM", 
                            oldLinha,
                            sbRelModifSPED,
                            itensSC170,
                            leitorSped
                    );
                }   
            }
    }    
    public void descricaoExata(List<SPEDC170>itensSC170, List<NFeItem>itensNFe, Map<SPEDC170, NFeItem[]> mapItensSpdNFe ,LeitorSpedFiscal leitorSped, StringBuilder sbRelModifSPED, int oldLinha,Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociadosTemp,int contador){
        System.out.println("###FILTRO POR DESCRICAO EXATA");
        this.cont = 0;
        itensSC170.stream().filter((sc170) -> !(sc170.getSPED0200().getDescrItem().isEmpty())).forEach((sc170) -> { 
            try{
                filtrarItensNFe(sc170, 
                        (n -> n.getDescricaoProdutoCustom().equals(sc170.getSPED0200().getDescricaoCustom())),"DESCRICAO EXATA",
                        itensSC170,
                        itensNFe,
                        mapItensSpdNFe,
                        leitorSped,
                        sbRelModifSPED,
                        oldLinha,
                        itensPreAssociadosTemp,
                        false
                );
            }catch(Exception ex){
                System.out.println(""+ ex.getMessage());
            }
        });        
    }    
    public void descricaoAproximadaValorIdentico(List<NFeItem>itensNFe , List<SPEDC170>itensSC170,Map<SPEDC170, NFeItem[]> mapItensSpdNFe, LeitorSpedFiscal leitorSped, StringBuilder sbRelModifSPED, int oldLinha,Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociadosTemp){
        util = new MetodosUtil();
        System.out.println("###FILTRO POR DESCRICAO APROXIMADA E VALOR IDENTICO");
        this.cont = 0;
        try{
            for(SPEDC170 itenSped : itensSC170){
                SPEDC170 itenscs170 = (SPEDC170)itenSped;
                NFeItem itemNfe = null;
                Map<NFeItem, Float> provaveis = new HashMap<>();
                int cont = 0;
                for(NFeItem itensNfe : itensNFe){       
                    if(itensNfe.getValorProduto().compareTo(itenscs170.getValor()) == 0)
                    {
                        if(cont > 0){
                            itemNfe = null;
                        }else{
                            itemNfe =(NFeItem) itensNfe;
                        }
                        provaveis.put(itensNfe,Float.NaN);
                        cont++;
                    }
                }
                if(itemNfe != null)
                {
                    itemNfe.setValorProduto(itemNfe.getValorTotal());
                    itenscs170.setFiltroDeRelacionamento("FILTRO POR DESCRICAO APROXIMADA E VALOR IDENTICO");
                    mapItensSpdNFe.put(itenscs170, new NFeItem[] { itemNfe });                    
                    util.imprimirInformacoesParaDepuracao(itenscs170, itemNfe,"DESCRIÇÃO APROXIMADA VALOR ACHOU: SIM" );
                    util.aplicarModificacoesIdentificadasSPEDComNFe(itenscs170,itemNfe,false,"DESCRIÇÃO APROXIMADA VALOR ACHOU: SIM",
                            oldLinha,
                            sbRelModifSPED,
                            itensSC170,
                            leitorSped
                            );
                    itensNFe.remove(itemNfe);
                    provaveis.remove(itemNfe);
                    this.cont++;
                }else{
                    itensPreAssociadosTemp.put(itenscs170, provaveis);
                }
            }
        }catch(Exception ex){System.out.println(ex.getMessage());}        
    }    
    public void descricaoAproximadaValorImposto(List<NFeItem>itensNFe , List<SPEDC170>itensSC170,Map<SPEDC170, NFeItem[]> mapItensSpdNFe, LeitorSpedFiscal leitorSped, StringBuilder sbRelModifSPED, int oldLinha,Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociadosTemp){
        System.out.println("###FILTRO POR DESCRICAO APROXIMADA E VALOR, ICMS ST, IPI ");      
        Map<NFeItem, Float> provaveis = new HashMap<>();
        util = new MetodosUtil();
        this.cont = 0;
        try{
        for(SPEDC170 itenscs170 : itensSC170){           
            NFeItem itemNfe = null;
            int cont = 0;
            for(NFeItem itensNfe : itensNFe){
                if(itensNfe.getValorTotal().compareTo(itenscs170.getValor()) == 0)
                {
                    if(cont > 0){
                        itemNfe = null;                        
                    }else{
                        itemNfe =(NFeItem) itensNfe;
                    }
                    provaveis.put(itensNfe,Float.NaN);
                    cont++;
                }
            }
            if(itemNfe != null)
            { 
                itenscs170.setFiltroDeRelacionamento("FILTRO POR DESCRICAO APROXIMADA E VALOR, ICMS ST, IPI");
                mapItensSpdNFe.put(itenscs170, new NFeItem[] { itemNfe });
                
                util.imprimirInformacoesParaDepuracao(itenscs170, itemNfe,"DESCRIÇÃO APROXIMADA VALOR ACHOU: SIM" );           
                util.aplicarModificacoesIdentificadasSPEDComNFe(itenscs170,itemNfe,false,"DESCRIÇÃO APROXIMADA VALOR ACHOU: SIM",
                            oldLinha,
                            sbRelModifSPED,
                            itensSC170,
                            leitorSped
                            );
                itensNFe.remove(itemNfe); 
                provaveis.remove(itemNfe);
                this.cont++;
            }else{itensPreAssociadosTemp.put(itenscs170, provaveis);}             
        }
        }catch(Exception ex){System.out.println(ex.getMessage());}
    }    
    public void descricaoAproximadaValorImpostoOLD(List<NFeItem>itensNFe , List<SPEDC170>itensSC170,Map<SPEDC170, NFeItem[]> mapItensSpdNFe, LeitorSpedFiscal leitorSped, StringBuilder sbRelModifSPED, int oldLinha,Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociadosTemp,Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociados){
        System.out.println("###FILTRO POR DESCRICAO APROXIMADA E VALOR, ICMS ST, IPI ");      
        itensPreAssociadosTemp.clear();
        util = new MetodosUtil();
        this.cont = 0;
        try{
        for(Map.Entry<SPEDC170,Map<NFeItem,Float>> itens : itensPreAssociados.entrySet()){           
            Map<NFeItem, Float> provaveis = new HashMap<>();
            SPEDC170 itenscs170 = (SPEDC170)itens.getKey();
            NFeItem itemNfe = null;
            int cont = 0;
            for(Map.Entry<NFeItem,Float> itensNfe : itens.getValue().entrySet()){
                if(itensNfe.getKey().getValorTotal().compareTo(itenscs170.getValor()) == 0)
                {
                    if(cont > 0){
                        itemNfe = null;                        
                    }else{
                        itemNfe =(NFeItem) itensNfe.getKey();
                    }
                    provaveis.put(itensNfe.getKey(),itensNfe.getValue());
                    cont++;
                }
            }
            if(itemNfe != null)
            {
                mapItensSpdNFe.put(itenscs170, new NFeItem[] { itemNfe });
                
                util.imprimirInformacoesParaDepuracao(itenscs170, itemNfe,"DESCRIÇÃO APROXIMADA VALOR ACHOU: SIM" );           
                util.aplicarModificacoesIdentificadasSPEDComNFe(itenscs170,itemNfe,false,"DESCRIÇÃO APROXIMADA VALOR ACHOU: SIM",
                            oldLinha,
                            sbRelModifSPED,
                            itensSC170,
                            leitorSped
                            );
                itensNFe.remove(itemNfe); 
                provaveis.remove(itemNfe);
                this.cont++;
            }else{itensPreAssociadosTemp.put(itenscs170, provaveis);}             
        }
        }catch(Exception ex){System.out.println(ex.getMessage());}
    }    
    private void filtrarItensNFe(SPEDC170 sc170, Predicate<NFeItem> predicate,String filtro , List<SPEDC170>itensSC170, List<NFeItem>itensNFe, Map<SPEDC170, NFeItem[]> mapItensSpdNFe ,LeitorSpedFiscal leitorSped, StringBuilder sbRelModifSPED, int oldLinha,Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociadosTemp, boolean comparaSize) {
        /*
            a ideia do filtro é inflar os itens associados e reduzir a lista de itens da NFE até que esteja vazia
        */
        util = new MetodosUtil();
        boolean restringir = false;
        if(filtro.equals("EAN") || filtro.equals("EANTRIB")){
            if(sc170.getSPED0200().getCodBarra().isEmpty()){
                restringir = true;
            }
            if(comparaSize){            
                if(sc170.getSPEDC100().getListaSPEDC170().size() <  itensNFe.size())
                    restringir = true; 
            }
        }
        try{            
            Optional<NFeItem> optNfeItem = itensNFe.parallelStream().filter(predicate).findFirst();            
            if (optNfeItem.isPresent() && restringir == false){
               
                NFeItem nfeItem = optNfeItem.orElse(null); 
                nfeItem.setRelAutomatico(1);
                sc170.setFiltroDeRelacionamento(filtro);
                mapItensSpdNFe.put(sc170, new NFeItem[] { nfeItem });//incrementa
                
                //SPEDC170 sc170, NFeItem nfeItem, boolean muitos, String filtro, int oldLinha, StringBuilder sbRelModifSPED, List<SPEDC170> itensSC170, LeitorSpedFiscal  leitorSped
                util.aplicarModificacoesIdentificadasSPEDComNFe(sc170,
                    nfeItem,
                    false,
                    filtro,
                    oldLinha,
                    sbRelModifSPED,
                    itensSC170,
                    leitorSped
                );
                itensNFe.remove(nfeItem);//decrementa
                util.imprimirInformacoesParaDepuracao(sc170, nfeItem, " ACHOU: SIM" );  
                this.cont++;
            }else{                
                util.imprimirInformacoesParaDepuracao(sc170, null, " ACHOU: NAO" );     
            }
        }catch(Exception ex){         
                //ALTERAÇOES SOLICITADA PELO GABRIEL E MARCELO PARA ACOMPANHAR O DEBUG
                System.out.println(
                    String.format(
                    "CHAVE:"+ sc170.getSPEDC100().getChaveNFe() 
                    + "| DESCRICAO SPED:" + sc170.getSPED0200().getDescrItem() 
                    + "| DESCRICAO XML: N/D"
                    + "| VALOR SPED: N/D" +  sc170.getValor()
                    + "| VALOR XML: N/D"
                    + "| QTD SPED:"+ sc170.getQtd()
                    + "| QTD XML:N/D "
                    + "| VALOR ICMS ST SPED:"+  sc170.getValorICMSST()
                    + "| VALOR ICMS ST XML: N/D"
                    + "| VALOR IPI SPED: "+ sc170.getValorIPI()
                    + "| VALOR IPI XML: N/D"
                    + "| FILTRO: ACHOU: NAO - EX ")
                           
            );                  
          //log.error(String.format("Err.: '%s'. Mensagem: %s" , sc170.getSPED0200().getDescrItem() + " > " + trecho, ex.getMessage()), ex);
        }        
    }    
    private void agruparItensNfe(List<NFeItem> grupo,List<NFeItem>itensNFe,SPEDC170 sc170,Map<SPEDC170, NFeItem[]> mapItensSpdNFe, String filtro, int oldLinha,StringBuilder sbRelModifSPED,List<SPEDC170>itensSC170,LeitorSpedFiscal leitorSped){
        BigDecimal valorProd = BigDecimal.ZERO;
        BigDecimal valorIcmsSt = BigDecimal.ZERO;
        BigDecimal valorIpi = BigDecimal.ZERO;

        NFeItem[] nfItens =  new NFeItem[grupo.size()];
        for(int i=0; i < grupo.size(); i++){
            nfItens[i] = grupo.get(i);
            valorProd = valorProd.add(nfItens[i].getValorTotal());
            IPI ipi = (IPI)nfItens[i].getIPI();
            if(ipi != null)
                if(ipi.getvIPI() != null)
                    valorIpi =  valorIpi.add(ipi.getvIPI());

            itensNFe.remove(nfItens[i]);
            util.imprimirInformacoesParaDepuracao(sc170,nfItens[i], filtro );
        }

        NFeItem nfeItemAgregado = nfItens[0];
        nfeItemAgregado.setValorProduto(valorProd);

        if(nfeItemAgregado.getICMS() instanceof ICMS10 ){
             ICMS10 icms = (ICMS10)nfeItemAgregado.getICMS();
        }else if(nfeItemAgregado.getICMS() instanceof ICMS30){
            ICMS30 icms = (ICMS30)nfeItemAgregado.getICMS();
        }else if(nfeItemAgregado.getICMS() instanceof ICMS70){
            ICMS70 icms = (ICMS70)nfeItemAgregado.getICMS();
        }else if(nfeItemAgregado.getICMS() instanceof ICMS90){
            ICMS90 icms = (ICMS90)nfeItemAgregado.getICMS();
        }
        IPI ipi = (IPI)nfeItemAgregado.getIPI();
            if(ipi != null)
                if(ipi.getvIPI() != null)
                    ipi.setvIPI(valorIpi);

        mapItensSpdNFe.put(sc170,nfItens);
        try{
            util.aplicarModificacoesIdentificadasSPEDComNFe(sc170,nfeItemAgregado,false,"UM PRA MUITOS EAN",oldLinha, sbRelModifSPED, itensSC170, leitorSped); 
        }catch(Exception ex){System.out.println(""+ex.getMessage()); }
    }    
    public void definirDescricaoAproximada(List<SPEDC170>itensSC170, List<NFeItem>itensNFe, Map<SPEDC170, Map<NFeItem, Float>> itensPreAssociadosTemp){
         //itensSC170.stream().forEach(item170 -> {
         util = new MetodosUtil();
         for (SPEDC170 item170 : itensSC170){
            try {   
                final Map<NFeItem, Float> provaveis = new HashMap<>();
                itensPreAssociadosTemp.clear();
                for(NFeItem nfeItem : itensNFe){
                    nfeItem.setRelAutomatico(0);
                    float perc = util.compararDescricaoAproximada(nfeItem.getDescricaoProduto(), item170.getSPED0200().getDescricaoCustom(),item170,nfeItem);
                    if (perc > 0.2F){
                        //descricaoAproximada.put(item170, nfeItem);
                        provaveis.put(nfeItem, perc);
                    } 
                }
                if(provaveis.size() > 0){
                    itensPreAssociadosTemp.put(item170, provaveis); 
                }
            } catch(Exception ex) { }
            System.gc();
         }
    }
}
