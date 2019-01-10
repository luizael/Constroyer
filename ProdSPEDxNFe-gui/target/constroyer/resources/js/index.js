/* global listaItensAssociados, url, mapaItensPreAssociadosTemp, indexVars, verificarProgresso, Utils */

var indexLinhaRelacionamento = 0;
var cont = 0;
function confirmarItensAcossiados(idLinhaNfeSped, idNfe){
//    if(idLinhaNfeSped === "") 
//        return;
//    
//    var spl = idLinhaNfeSped.split('*');
//    var idSpedNfe = spl[1].split('#');
    var listaDeItensEquivalentesSelecionadosLocal=[]; 
    
    listaDeItensEquivalentesSelecionadosLocal.push(idNfe);
//    listaDeItensEquivalentesSelecionadosLocal = retornarArrayDeAcossiados(idSpedNfe[0]);
//    if(listaDeItensEquivalentesSelecionadosLocal == ""){
//       showMessage("Atenção", 
//                    "Selecione um item da NF-e ",
//                    { "Ok": function () { $(this).dialog("close"); } });
//        return;
//    }
//                                
    var itemAssociado = {
        "idLinhaNfeSped": idLinhaNfeSped,
        "idSpedC170": idLinhaNfeSped, 
        "idNFeItem": listaDeItensEquivalentesSelecionadosLocal,
        "substituir": false
    }; 
    
    executeWebMethod(
        url, 
        "confirmarItensAssociados",
        JSON.stringify(itemAssociado),
        function (ret) {                        
            if (ret.result === "Success") {
                if (ret.message !== null) {
                    showMessage("Atenção", ret.message, 
                                { 
                                    "Sim": function () { 
                                        itemAssociado.substituir = true;
                                       // confirmarSubstituicaoItem(idLinhaNfeSped, itemAssociado); 
                                       // $(this).dialog("close"); 
                                    },
                                    "Não": function () { $(this).dialog("close"); } 
                                },
                                undefined,
                                { width: "567", height: "254", modal: true});
                } else {
                    removerLinha(idLinhaNfeSped);                    
                    listaItensAssociados.push(itemAssociado); 
                    contarNumeroDeLinhas();    
                    habilitarBotaoConfirmarSelecao();   
                }
            } else {
                showMessage(
                    "Erro", 
                    "Houve um erro ao tentar associar os produtos. Msg: " + ret.message,
                    { "Ok": function () { $(this).dialog("close"); } });
            }                        
        });             
}  

function confirmarSubstituicaoItem(idLinhaNfeSped, itemAssociado) {
    executeWebMethod(
        url, 
        "confirmarSubstituicaoItem",
        JSON.stringify(itemAssociado),
        function (ret) {                        
            if (ret.result === "Success") {                
                removerLinha(idLinhaNfeSped);
                if (ret.object !== null && ret.object !== undefined) {
                    var itemARecolocar = ret.object;
                    excluirAssociacao(itemARecolocar);
                    removerItemListaEquivalentePorIdSpedC170(itemARecolocar.idSpedC170);
                    removerItemListaAssociadosPorIdSpedC170(itemARecolocar.idSpedC170);
                    mapaItensPreAssociadosTemp.forEach(function(key, value) {
                        if (key.match("\"id\":\"" + itemARecolocar.idSpedC170 + "\"")) {
                            criarLinhaRelacionamento(++indexLinhaRelacionamento, key, value, 2);                            
                        }
                    });
                    listaItensAssociados.push(itemAssociado);
                    contarNumeroDeLinhas();    
                    habilitarBotaoConfirmarSelecao();   
                }
            } else {
                showMessage(
                    "Erro", 
                    "Houve um erro ao tentar associar os produtos. Msg: " + ret.message,
                    { "Ok": function () { $(this).dialog("close"); } });
            }                        
        });
}

function excluirAssociacao(itemAssociado) {
    executeWebMethod(
        url, 
        "excluirAssociacao",
        JSON.stringify(itemAssociado),
        function (ret) {
        });
}

function removerLinha(idLinhaNfeSped){
    var spl = idLinhaNfeSped.split('*');
    var linha = spl[0];//obtendo i id da linha               
    var idSpedNfe = spl[1].split('#');
    
    $('#linha-'+linha).slideUp("fast");
    
    $.each(listaItensPreAssociadosTemp,function(chave,valor){
        var prod = JSON.parse(chave);
        if(prod.id === idSpedNfe[0]){
            delete listaItensPreAssociadosTemp[chave];    
            return;
        }
    });                
} 

function IDX_converterParaMapa(mapa){
    mp = new Map();
    jQuery.map(mapa,function(key,value){
       mp.set(key,value);
    });
    return mp;
}

function IDX_filtrarItens(criterio){   
   //limparMemoria();
    listaItensEquivalente = [];    
    mapaItensPreAssociadosTemp = new Map();
    var maps = new Map();   
    $.map(listaItensPreAssociadosTemp,function(chave,valor){
        var prod = JSON.parse(valor);
        if(prod.nome.toUpperCase().match(criterio.toUpperCase()) ||
           prod.chaveNFe.match(criterio)){
            maps.set(chave,valor);
        }
    });   
    mapaItensPreAssociadosTemp = maps;
    IDX_montarHtmlDeItens();
}

function IDX_ordernar(orderby){}

 
function checarTotalAssociadoAutomaticamente(){
     var data = {};
        executeWebMethod(
            url, 
            "checarAssociadosAutomaticamente",
            JSON.stringify(data),
            function (ret) {                        
                if (ret.result === "Success") {
                    retObj = ret.object;                                
                    var per = (retObj.atual / retObj.total) * 100;
                    $("#qtdItensAssociadosAutomaticamente").html(retObj.total);
                }                        
            });
    
}
function gerarIdCheckBox(idLinhaNfeSped){
    var spl = idLinhaNfeSped.split('*');
    var idSpedNfe = spl[1].split('#');
    var idCheckBox  = spl[0]+'-'+idSpedNfe[0]+'-'+idSpedNfe[1].replace(/[^a-zA-Z0-9_]/gi, "");
    return idCheckBox;
}

function checarGride(idLinhaNfeSped){
    var idCheckBox ="#ckb"+gerarIdCheckBox(idLinhaNfeSped); 
    if($(idCheckBox).is(':checked')){
        adicionarItensPreSelecionados(idLinhaNfeSped);
    }else{
        removerItensEquivalente(idLinhaNfeSped);
    }
    contarItensSelecionados();
}

function checarSubGrade(idLinhaNfeSped){ 
    var idCheckBox ="#ckb_sub"+gerarIdCheckBox(idLinhaNfeSped);
    if($(idCheckBox).is(':checked')){
        adicionarSubItensEquivalente(idLinhaNfeSped);
    }else{
        removerItensEquivalente(idLinhaNfeSped); 
    }
    contarItensSelecionados();
} 

function adicionarItensPreSelecionados(idLinhaNfeSped){               
    var spl = idLinhaNfeSped.split('*');
    var idSpedNfe = spl[1].split('#');
    var itemEquivalente = {
        "idSpedC170": idSpedNfe[0],
        "idNFeEquivalente": idSpedNfe[1]              
    };
    listaItensEquivalente.push(itemEquivalente);
}

function adicionarSubItensEquivalente(idLinhaNfeSped){
    var encontrou = false;
    var spl = idLinhaNfeSped.split('*');
    var idSpedNfe = spl[1].split('#');
    var itemEquivalente = {
              "idSpedC170": idSpedNfe[0],
              "idNFeEquivalente": idSpedNfe[1]              
    };
     $.map(listaItensEquivalente,function(val,i){
        var itemEquivalenteLocal = {
             "idSpedC170": val.idSpedC170,
             "idNFeEquivalente": val.idNFeEquivalente 
        };
        if((itemEquivalente.idSpedC170 === itemEquivalenteLocal.idSpedC170) && 
           (itemEquivalente.idNFeEquivalente  === itemEquivalenteLocal.idNFeEquivalente)) {
            encontrou = true;
            return;
        }
    }); 
    if(!encontrou){
        listaItensEquivalente.push(itemEquivalente);
    }
    //percorrerArray(); 
    habilitarBotaoConfirmarSelecao();
}

function removerItensEquivalente(idLinhaNfeSped){
    var spl = idLinhaNfeSped.split('*');
    var idSpedNfe = spl[1].split('#');
    var itemEquivalente = {
        "idSpedC170": idSpedNfe[0],
        "idNFeEquivalente": idSpedNfe[1]                 
    };
        
    var excluir = true;
    while (excluir) {
        var index = -1;
        for (var i = 0; i < listaItensEquivalente.length; i++) {
            var obj = listaItensEquivalente[i];
            if((itemEquivalente.idSpedC170 === obj.idSpedC170) && 
               (itemEquivalente.idNFeEquivalente  === obj.idNFeEquivalente)) {
               index = i;
               break;
            }        
        }
        if (index > -1)
            listaItensEquivalente.splice(index, 1);
        else
            excluir = false;
    }
}

function percorrerArray(){
    $.map(listaItensEquivalente,function(val,i){
       // console.log("---------------------------------------------------------------");
       // console.log("Id idSpedC170 :"+val.idSpedC170+", id Item NFE Equivalente:"+val.idNFeEquivalente) ;
    });
} 

function removerItemListaEquivalentePorIdSpedC170(idSpedC170) {
    removerItemPorIdSpedC170DaLista(idSpedC170, listaItensEquivalente);
}

function removerItemListaAssociadosPorIdSpedC170(idSpedC170) {
    removerItemPorIdSpedC170DaLista(idSpedC170, listaItensAssociados);
}

function removerItemPorIdSpedC170DaLista(idSpedC170, lista) {
    var excluir = true;
    while (excluir) {
        var index = -1;        
        for (var i = 0; i < lista.length; i++) {
            var item = lista[i];
            if (item.idSpedC170 === String(idSpedC170)) {
                index = i;
                break;
            }
        }        
        if (index > -1)
            lista.splice(index, 1);
        else
            excluir = false;
    }    
}

function retornarArrayDeAcossiados(idSpedC170){
    var listaNfeEquivalente = [];
    $.map(listaItensEquivalente,function(val,i){
        if(val.idSpedC170 === idSpedC170){           
            listaNfeEquivalente.push(val.idNFeEquivalente);
        }                     
    });
    return listaNfeEquivalente;
} 

function contarNumeroDeLinhas(){
    $("#qtdLinhaConfirmada").html(listaItensAssociados.length);
}
            
function contarItensSelecionados(){
  $("#qtdItensNfeSelecionados").html(listaItensEquivalente.length);
}

function limparMemoria(){
    listaItensPreAssociadosTemp = [];
    listaItensEquivalente = [];
    listaItensAssociados = [];
    mapaItensPreAssociadosTemp = new Map();
    objDeRelacionamento = {};
}  

function habilitarBotaoConfirmarSelecao(){
    if(listaItensAssociados.length > 0){
        $("#btnSalvarItensAcossiados").removeAttr('disabled').css("color","#ffffff");
    }else{
        $("#btnSalvarItensAcossiados").attr('disabled','disabled').css("color","#ccc");
    }
}

function abrirTela(obj,url) { 
    //habilitarBotaoConfirmarSelecao();
    //IDX_montarHtmlDeItens();
    visualizarTelaDeVinculos(obj,url);
    $("#relacionamentoManual").dialog("open");               
} 

function expandirLista(id){
	IndexPage.loadItemsListAjax(id);
} 

/**
 * Objeto criado para encapsular as funcionalidades referente ao Ajax efetuado na página index.
 */
var IndexPage = {};
IndexPage.currArray = null;
IndexPage.loadItemsListAjax = function(id){
	var dropDownObj = $('#dropdown'+id);
	if(dropDownObj.attr('loaded')==="true"){
		dropDownObj.slideToggle('fast');
	
	}else{
		var htmlDropDown = '';
		var data = { 
				"pseudoIdParent": id,
				"chave":dropDownObj.attr('chave'),
				"idProduto":dropDownObj.attr('idproduto')
		};    
		Utils.execWebMethod(
				url, 
				"showSubItems",
				JSON.stringify(data),
				function (ret) { 
					if (ret.result === "Success") {
						retObj = ret.object;
						IndexPage.currArray =retObj;
                                                
						$(retObj).each(function(i){
                                                   	if(!this.selecionado){
                                                            var idXmlLz = "infDetXmlLz"+id+this.id;
                                                            var link ='                     <span class="det detXmlLz"  onclick="exibirDetalhes(\''+idXmlLz+'\')">'; 
                                                                link +='                        ';
                                                                link +=                         templateDetalhe("infDetXmlLz",idXmlLz, this);
                                                                link +='                     </span>';
                                                            htmlDropDown += IndexPage.getReplacedItemList(this.nome, this.perc,link, id,dropDownObj.attr('idproduto'),this.id, this.relMult, this.relAutomatico);
                                        		}
						});
					} 
				},
				function () {
					dropDownObj.html(htmlDropDown).slideToggle('fast', function(){$(this).attr('loaded',true);});
				},
				function(e){
					console.log(e);
					alert('Erro ao efetuar a operação!'+e);
				});
		
		
	}
    
	
};
IndexPage.iterateTemplate =  '    <div id="#idLinhaNfeSped#" class="produtoAssociado rel_multiplo_#relMultiplos# rel_automatico_#relAutomatico#" ><span class="seta">&#8227;&nbsp;</span>#nomeObjAssoc# -> [ <span class="porcentagem">#porcentagem#%</span>]#link#'
	.concat('        <div class="checkBoxContainer sub">')
	.concat('            <input type="checkbox" id="ckb_sub#idInputCheckBox#"')
	.concat('                    onclick=checarSubGrade("#idLinhaNfeSped#")')
	.concat('                     class="css-checkbox" />')
        
	.concat('            <label for="ckb_sub#idInputCheckBox#" class="css-label"></label>')
	.concat('        </div>')
	.concat('    </div>')
	.concat('    <div class="clear"></div>');

IndexPage.getReplacedItemList = function(nomeObjAssoc, porcentagem, link, indexId, prodId, assocId, relMultiplos,relAutomatico){
	
	var idLinhaNfeSped = indexId + '*' + prodId + '#' + assocId; 
        var idInputCheckBox = indexId +'-'+ prodId +'-'+ assocId;
	
	
	var retorno = this.iterateTemplate;
	retorno = replaceAll(retorno,'#idLinhaNfeSped#', idLinhaNfeSped);
	retorno = replaceAll(retorno,'#nomeObjAssoc#', nomeObjAssoc);
	retorno = replaceAll(retorno,'#porcentagem#', (porcentagem * 100).toFixed(2));  
        retorno = replaceAll(retorno,'#link#',link);
        
        retorno = replaceAll(retorno,'#relMultiplos#', relMultiplos);
        retorno = replaceAll(retorno,'#relAutomatico#', relAutomatico);
        
	retorno = replaceAll(retorno,'#idInputCheckBox#', idInputCheckBox);
	return retorno;	
};

function mostrarChaveNFe(nome, chaveNFe) {
    showMessage(
                "Chave NFe da Nota do Item " + nome, 
                chaveNFe,
                { "Ok": function () { $(this).dialog("close"); } });    
}

function IDX_montarHtmlDeItens() {
    var contZb = 0;
    
    $("#listaDeProdutosPreAssociado").html("");
    
    indexLinhaRelacionamento = 0;
    contarNumeroDeLinhas();               
    
    mapaItensPreAssociadosTemp.forEach(function(key, value) {        
        criarLinhaRelacionamento(indexLinhaRelacionamento, key, value, contZb);
        indexLinhaRelacionamento++;
        if (contZb < 1) { contZb = contZb + 1; } else { contZb = 0; }       
    }); 
    
    $("#qtdItens").html(indexLinhaRelacionamento);
}

function criarLinhaRelacionamento(indexId, key, value, contZb) {
    var html = "";        
    var preSelecionado = "";
    var ativarClique = "";
    var ativarChecked = "";
    var desabilitarChecked = "disabled";
    var ativarBotao = "botOkDisable";
    var idLinhaNfeSpedSelecionado = "";
    var idInputCheckBox = "";
    var htmlDropDown = "";
    var prod = JSON.parse(key);                        
    var rM = 0; 
    var idInfsped = "infDetSped"+ indexId;
    
    html +='<div id="linha-'+indexId+'" class="linha zb-'+ contZb +'">';
    html +='   <div class="celProdutoRegistrado">';
    html +='        <a href="javascript: mostrarChaveNFe(\'' + prod.nome + '\', \'' + prod.chaveNFe + '\')">'+prod.nome+'</a>';
    html +='        <div class="det detSped"  onclick="exibirDetalhes(\''+idInfsped+'\')">'; 
    html +='            ';
    html +=             templateDetalhe("infDetSped",idInfsped, prod);
    html +='        </div>';
    html +='    </div>';//coluna do SPED
    html +='    <div class="celProdutosEncontrado">';//Coluna da NF-e
    html +='           <div class="container">';                        
    
    jQuery.each(value,function(assoc){//itens da nota fiscal
        var assocObj = {};                            
        var percentagem = 0;
        assocObj = this;
        percentagem = (assocObj.perc * 100).toFixed(2);
        rM = assocObj.relMult;
        var idLinhaNfeSped = indexId + '*' + prod.id + '#' + assocObj.id; 
        var idInputCheckBoxSel = indexId +'-'+ prod.id +'-'+ assocObj.id;
        var idInputDet = "infDetXml"+ cont + '-'+ indexId +'-'+ prod.id +'-'+ assocObj.id;
        //var idInfXml= "infDetXml"+ indexId;
        /*
         * se o produto ja for pré selecionado habilita o botao ok 
         * adiciona o evento onclick com a função adicionarNaListaDeItensAcossiados
        */
        if (assocObj.selecionado) {
            ativarBotao = "botOk";
            ativarClique = "onclick=confirmarItensAcossiados";
            idLinhaNfeSpedSelecionado = idLinhaNfeSped;
            idInputCheckBox = idInputCheckBoxSel;
            preSelecionado =  assocObj.nome + ' -> [ <span class="porcentagem">' + percentagem + '%</span> ]';
            preSelecionado +='                     <span class="det detXml"  onclick="exibirDetalhes(\''+ idInputDet+'\')">'; 
            preSelecionado +='                        ';
            preSelecionado +=                         templateDetalhe("infDetXml", idInputDet, assocObj);
            preSelecionado +='                    </span>';
            ativarChecked = "checked";
            desabilitarChecked="";
            adicionarItensPreSelecionados(idLinhaNfeSpedSelecionado);
            contarItensSelecionados();
        }       
       
    });

    html += '              <div id="preselecionado'+indexId+'"  class="produtoPreSelecionado rel_multiplo_'+ rM +'">';
    html +=                   preSelecionado;     
    html +='                  <div class="containerControles">';
    html +='                    <div class="'+ativarBotao+'" '+ativarClique+'("'+idLinhaNfeSpedSelecionado+'")>OK</div>';
    html +='                    <div class="checkBoxOk"><input type="checkbox" name="ckb'+idLinhaNfeSpedSelecionado+'" id="ckb'+idInputCheckBox+'" onclick=checarGride("' + idLinhaNfeSpedSelecionado + '")  class="css-checkbox" '+desabilitarChecked+' '+ativarChecked+'  /><label for="ckb'+idInputCheckBox+'" class="css-label"></label></div>';
    html +='                    <div class="botVer" onclick=expandirLista('+indexId+')></div>';
    html +='                  </div>';
    html +='               </div>';
    html +='               <div id="dropdown'+indexId+'" class="containerDropdow" chave="'+prod.chaveNFe+'" idproduto="'+prod.id+'">';
    html +=                    htmlDropDown;
    html +='               </div>';
    html +='            </div>';
    html +='      </div>';   
    html+='   <div class="clear"></div>';
    html +='</div>'; 
    html+='<div class="clear"></div>';       
    cont++;
    $("#listaDeProdutosPreAssociado").append(html);
}           
var templateDetalhe = function (classeInf, indexId, prod){
    var chave = prod.chaveNFe == null? prod.chaveNfe : prod.chaveNFe;
    var valorIcms = prod.valorIcmsSt == null? 0: prod.valorIcmsSt;
    var valorIpi = prod.valorIpi == null? 0: prod.valorIpi;
    var valorOutrasDespesas = prod.valorOutros == null? 0:prod.valorOutros;
    var infAdProd = prod.infAdProd == null? "":prod.infAdProd;
    var html = "";
    html +='            <div class="detInf '+classeInf+'" id="'+indexId+'">';
    html +='                <div class="closeDet" onclick="closeDetalhes(\''+indexId+'\')">x</div>';
    html +='                <div>Nota: '+ chave +' </div>';
    html +='                <div>Inf Adicional: '+ infAdProd +' </div>';
    html +='                <div>Valor: <span class="detValor">'+ prod.valor +' </span></div>';
    html +='                <div>Valor Icms ST: <span class="detValor">'+ valorIcms +'</span> </div>';
    html +='                <div>Valor IPI: <span class="detValor">'+ valorIpi+'</span> </div>';
    html +='                <div>Outras Despesas: <span class="detValor">'+ valorOutrasDespesas +'</span> </div>';
    html +='            </div>';
   
    return html;
}
function exibirDetalhes(v){
    $("#"+v).slideToggle("fast");
}
function closeDetalhes(v){
    $("#"+v).css("dsplay","none");
}