var contador = 0;
var cont =0;
var contaTabelas = 0;
var contDivs = 0;
var qtdDeVinculados =0;
var QuantidadeParaVincularManual = 0;

var url;

function verificaQtdDoMap(obj)
{
    var cont = 0;
    $.each(obj,function(i,j){
        cont++;
    });
    return cont;
}
function visualizarTelaDeVinculos(obj,caminho)
{
    url =caminho; 
    //BuscarInformacoesNoWebServiceMixPeloEan(url,"7896326100219","6f985ed7fab48bf485ec299baa473b89");
    criaCabecalho(obj.sped0000,obj.listDeItensVinculadosAuto,obj.itensPreAssociados);
    criaTabelaComTodosOsDados(obj.mapDeCabecalhos,obj.itensPreAssociados,obj.listDeItensVinculadosAuto,url);
};
function criaCabecalho(obj,obj2,obj3){
  //var obj = JSON.parse(ObjCabecalho); 
  qtdDeVinculados = obj2.length;
  QuantidadeParaVincularManual = verificaQtdDoMap(obj3);
  $('#tela').append('<header id="cabecalho"> <ul id="listCabecalho"><li class="listaCabecalho"><i>'+obj.nomeEmpresarialEntidade+'</i></li>\n\
<li class="listaCabecalho"><b> CNPJ:</b>&nbsp&nbsp'+obj.cnpj+'</li>\n\
<li class="listaCabecalho" id="gerarTabela"><b> Produtos vinculados Automaticamente:</b>&nbsp&nbsp'+qtdDeVinculados+'  </li>\n\
<li class="listaCabecalho"><b> Produtos Para Vincular Manualmente: </b>&nbsp&nbsp'+QuantidadeParaVincularManual+'</li></ul></header>');
//  $('#abrirTabela').click(function(){
//      criandoDivComTabelaDosVinculadosAuto(obj2);
//  });
pesquisarNaTabelaDeVinculadosAuto (obj2);
$('#gerarTabela').click(function(){
    tabelaDeVinculadosAuto (obj2);
});
};
function criaTabelaComTodosOsDados(ObjCabecalhoDeInfo,itensPreAssociados,objJaRelacionado,url)
{
    $('.divContainer').remove();
    var qtdJaVinculada = 0;
    var qtdVincularManual=0;
    var dif =0; 
    var totalimpostosComC170 = 0;
 $.each(ObjCabecalhoDeInfo,function(i,obj){ 
     contaTabelas++;
     var informacoes = JSON.parse(obj);
     qtdJaVinculada = verificaQtdDeVinculadosPorChave(i,objJaRelacionado);
     qtdVincularManual=informacoes.totalDeItensNoSped-qtdJaVinculada;
     dif = informacoes.vtNoC100 - informacoes.vtNaXML;
     totalimpostosComC170 = informacoes.totalC170 + informacoes.totalIcmsStPorNota + informacoes.totalIpiPorNota;
      $('#tela').append('<div class="divContainer" id="divContainer'+informacoes.chave+'">  \n\
        <table class="tabelaCabecalhoRelacionamento" id="tabelaCabecalhoRelacionamento'+informacoes.chave+'">   \n\
        <tr><td colspan="8" class="cd" id="cd'+i+'">Chave : '+informacoes.chave+'</td>\n\
	</tr>\n\
     <tr class="linhasParaOcultarDaTabela'+i+'"><td class="cl" id="inforC170"> Valor Total no|C100|</td>\n\
        <td class="cd" id="c100'+i+'">'+informacoes.vtNoC100+'</td>\n\
        <td class="cl">Valor Total na XML  </td>\n\
        <td class="cd">'+informacoes.vtNaXML+'</td>\n\
        <td class="cl"> Valor Total ICMSST da XML </td>\n\
        <td class="cd">'+informacoes.vtDaST+'</td>\n\
        <td class="cl">Total Descontos </td>\n\
        <td class="cd">'+informacoes.totalDescontos+'</td></tr>\n\
         <tr class="linhaOculta" id="linhaOculta'+i+'">\n\
        <td class="cl" id="inforC170"> Valor Total no|C170|</td>\n\
        <td class="cd">'+informacoes.totalC170+'</td>\n\
        <td class="cl">Valor Total na ICMSST (24)  </td>\n\
        <td class="cd">'+informacoes.totalIcmsStPorNota+'</td>\n\
        <td class="cl"> Valor Total IPI (18) </td>\n\
        <td class="cd">'+informacoes.totalIpiPorNota+'</td>\n\
        <td class="cl">Valor C170 com impostos </td>\n\
        <td class="cd">'+totalimpostosComC170+'</td></tr>\n\</tr>\n\
     <tr class="linhasParaOcultarDaTabela'+i+'"><td class="cl">Qtd Itens Ja vinculados Automaticamente</td>\n\
       <td id="gerarTabela'+contaTabelas+'" class="cd">'+qtdJaVinculada+'</td>\n\
       <td class="cl">Qtd Itens Para Vincular Manual</td>\n\
       <td class="cd">'+qtdVincularManual+'</td>\n\
       <td class="cl">Total de Itens C170</td>\n\
       <td class="cd">'+informacoes.totalDeItensNoSped+'</td>\n\
       <td class="cl">Total De Itens Na XML</td>\n\
       <td class="cd">'+informacoes.totalDeItensNaNota+'</td></tr></table>  </div> '); 
     
     $('#c100'+i).css("cursor","pointer").hover(function(){
         $(this).css("background-color","white");
     },function(){$(this).css("background-color","#cecece");}).click(function()
     {
         $('#linhaOculta'+i).fadeToggle(0);
     });
     
     $('#cd'+i).css("cursor","pointer").hover(function(){
         $(this).css("background-color","white");
     },function(){$(this).css("background-color","#cecece");}); 
     
     var totalNota = informacoes.totalDeItensNaNota;
     var totalSped = informacoes.totalDeItensNoSped;

        percorrerListaSped(i,itensPreAssociados,totalSped,totalNota,url);
        verificaQtdDeVinculadosAutoPorChave(i,objJaRelacionado,contaTabelas);
//       $('.dif').click(function(){
//           verificaQtdDeVinculadosPorChave (informacoes.chave,objJaRelacionado);
//           criarLinhasDeRelacionamento(map,chave,qtdAuto,qtdManual)
//           criandoDivComTabelaDosVinculadosAuto (listaAux,i);
//       });
 });   
}; 
function percorrerListaSped (chave, listaSped,totalSped,totalNota,url) 
{
   $.each(listaSped,function(i,j)
   {
       var condicao = 0;
    var prod = JSON.parse(i);
    if(chave == prod.chaveNFe){
         cont++ 
         $('#divContainer'+chave).append('<div id="divRelacao'+chave+'"></div>');
    $('#divRelacao'+chave).append('<table class="relacionamento" id="relacionamentoTable'+chave+cont+'"><tr>\n\
            <td class="linhaSped"><label><i class="nomeSped">'+prod.nome+'</i> &nbsp &nbsp &nbsp \n\
            <b>|VALOR: &nbsp</b>'+prod.valor+'  &nbsp \n\
            <b>|QTD: &nbsp</b> '+prod.qtd+'  &nbsp\n\
            <b>|CST: &nbsp</b> '+prod.cst+'  &nbsp &nbsp\n\
            <b>|VALOR ICMSST:</b> '+prod.valorIcmsSt+'  &nbsp &nbsp\n\
            <b>|VALOR IPI: &nbsp</b>  '+prod.valorIPI+'  &nbsp &nbsp\n\
            <b>|SEQUENCA SPED: &nbsp</b> '+prod.sequenciaNoSped+'  &nbsp &nbsp\n\
            <b>|EAN: &nbsp</b> '+prod.ean+'</label></td>\n\
            <td id="button'+chave+cont+'" class ="button"><b>OK</b></td></tr></table> '); 
        percorrerListaNfe(j,cont,chave,totalSped,totalNota,condicao,prod,url,listaSped) ;
        // prod.qtdDeNfeRelativa    
       }
   });
          $('#cd'+chave).click(function()
       {
           $('#divRelacao'+chave).fadeToggle(0);
       });
}
function percorrerListaNfe (listanfe,cont,chave,totalSped,totalNota,condicao,prod,url,map) 
{ 
    $.each(listanfe,function(i,obj){
         contador++;
         if(obj.valorIcmsSt == null){obj.valorIcmsSt = 0};
         if(obj.valorIpi == null){obj.valorIpi = 0};
         if(totalNota>totalSped)
         {
             condicao = 0;
        $('#relacionamentoTable'+chave+cont).append(' <tr><td class="nfelinha">\n\
        <input type="checkbox" name="listaNfe'+chave+cont+'" id="listaNfe'+contador+'">\n\
        <label for="listaNfe'+contador+'"><span class="idNfe">'+obj.id+'-</span><i class="nomeXML"> '+obj.nome+' |</i>&nbsp-->&nbsp'+obj.infAdProd+' &nbsp|\n\
        <b> &nbsp &nbspVALOR:&nbsp</b>'+obj.valor+'  \n\
        <b>&nbsp&nbsp|Valor Des Acessoria:&nbsp</b> '+obj.despesaAcessoria+'  \n\
        <b>&nbsp&nbsp|QTD:&nbsp</b> '+obj.qtd+'  \n\
        <b>&nbsp&nbsp|CST:&nbsp</b> '+obj.cst+' \n\
        <b>&nbsp&nbsp|VALOR ICMSST:&nbsp </b> '+obj.valorIcmsSt+' \n\
        <b>&nbsp&nbsp|VALOR IPI:&nbsp </b> '+obj.valorIpi+' \n\
        <b>&nbsp&nbsp|SEQ XML:&nbsp</b> '+obj.sequencia+' \n\
        <b>&nbsp&nbsp&nbsp&nbsp&nbsp <span  class="eanMIX" id="ean'+chave+cont+'">|EAN:&nbsp</b> '+obj.ean+'\n\
        </span> <b>&nbsp&nbsp<span class="eanMIX" id="eanTrib'+chave+cont+'">|EAN TRIB:&nbsp </b> '+obj.eanTrib+' \n\
        </span></label></input></td></tr>');       
         } 
         else {
             condicao =1;
          $('#relacionamentoTable'+chave+cont).append(' <tr><td class="nfelinha">\n\
        <input type="radio" name="listaNfe'+chave+cont+'" id="listaNfe'+contador+'">\n\
        <label for="listaNfe'+contador+'"><span class="idNfe">'+obj.id+'-</span><i class="nomeXML"> '+obj.nome+' |</i>&nbsp-->&nbsp'+obj.infAdProd+' &nbsp|\n\
        <b> &nbsp &nbspVALOR:&nbsp</b>'+obj.valor+'  \n\
         <b>&nbsp&nbsp|Valor Des Acessoria:&nbsp</b> '+obj.despesaAcessoria+'  \n\
        <b>&nbsp&nbsp|QTD:&nbsp</b> '+obj.qtd+'  \n\
        <b>&nbsp&nbsp|CST:&nbsp</b> '+obj.cst+' \n\
        <b>&nbsp&nbsp|VALOR ICMSST:&nbsp </b> '+obj.valorIcmsSt+' \n\
        <b>&nbsp&nbsp|VALOR IPI:&nbsp </b> '+obj.valorIpi+' \n\
        <b>&nbsp&nbsp|SEQ XML:&nbsp</b> '+obj.sequencia+' \n\
        <b>&nbsp&nbsp&nbsp&nbsp&nbsp <span class="eanMIX" id="ean'+chave+cont+'">|EAN:&nbsp</b> '+obj.ean+' \n\
         </span><b>&nbsp&nbsp<span class="eanMIX" id="eanTrib'+chave+cont+'">|EAN TRIB:&nbsp </b> '+obj.eanTrib+'\n\
         </span></label></input></td></tr>');      
        }
      // criaTelaDeEanConsumoMix (url,obj.ean,obj.eanTrib,chave,cont,obj.nome);
    }); 
    
     cssDoBotao(chave,prod,cont,listanfe,condicao,map);
     
}
function criaTelaDeEanConsumoMix (url,ean,eanTrib,chave,cont,descricao)
{
    $('#ean'+chave+cont).click(function()
    {
        BuscarInformacoesNoWebServiceMixPeloEan(url,ean,descricao);
    }); 
    
    $('#eanTrib'+chave+cont).click(function()
    {
        BuscarInformacoesNoWebServiceMixPeloEan(url,eanTrib,descricao);
    }); 
    
}
function criaListaDeRelacionamento (objrelacionar,i) 
{
   var mapaItensPreAssociadosTemp = new Map();
   $.each(objrelacionar,function(k,v){
       var prod = JSON.parse(k);
   if(prod.chaveNFe == i)
   {
     mapaItensPreAssociadosTemp.set(k,v);  
   }
   }); 
   return mapaItensPreAssociadosTemp;
}
function cssDivPrincipal(chave)
{
   $('divContainer'+chave).css("background-color","#e3ffba").
           css("padding","10px").
           css("border","1px solid #606060");
}
function criarLinhasDeRelacionamento(map,chave,qtdAuto,qtdManual)
{
    console.log(map);
 var cont =0;
 $.each(map,function(k,v){
 var prod = JSON.parse(k);
 if(prod.chaveNFe == chave){
     cont++
     $('#divContainer'+chave).append('<table class="relacionamento" id="relacionamentoTable'+chave+cont+'"><tr>\n\
            <td class="linhaSped"><label>'+prod.nome+'  <b>Valor:<b/>'+prod.valor+'  &nbsp <b>Qtd:</b> '+prod.qtd+'  &nbsp<b>CST:</b> '+prod.cst+'  &nbsp &nbsp<b>Valor IcmsST:</b> '+prod.valorIcmsSt+'  &nbsp &nbsp<b>Valor IPI:</b>  '+prod.valorIPI+'  &nbsp &nbsp<b>Sequencia Sped:</b> '+prod.sequenciaNoSped+'  &nbsp &nbsp<b>EAN:</b> '+prod.ean+'</label></td>\n\
            <td id="button'+chave+cont+'" class ="button"><b>OK</b></td></tr></table> ');
         $.each(v,function(i,obj){
             contador++;
             if(qtdManual>qtdAuto)
             {
               $('#relacionamentoTable'+chave+cont).append(' <tr><td class="nfelinha"><input type="checkbox" name="listaNfe'+chave+cont+'" id="listaNfe'+contador+'"><label for="listaNfe'+contador+'">  '+obj.nome+' <b> &nbspValor:</b>'+obj.valor+'   <b> &nbspQtd:</b> '+obj.qtd+'  <b> &nbspCST:</b> '+obj.cst+' <b> &nbspValor ICMSST: </b> '+obj.valorIcmsSt+' <b> &nbspValor IPI: </b> '+obj.valorIpi+' <b> &nbspSeq XML:</b> '+obj.sequencia+' <b>&nbsp EAN: </b> '+obj.ean+' <b> &nbspEan Trib: </b> '+obj.eanTrib+' <b> &nbspcomplemento Nome:</b> '+obj.infAdProd+'</label></input></td></tr>');    
             } else
             {
                $('#relacionamentoTable'+chave+cont).append(' <tr><td class="nfelinha"><input type="radio" name="listaNfe'+chave+cont+'" id="listaNfe'+contador+'"><label for="listaNfe'+contador+'">  '+obj.nome+' <b> &nbspValor:</b>'+obj.valor+'   <b> &nbspQtd:</b> '+obj.qtd+'  <b> &nbspCST:</b> '+obj.cst+' <b> &nbspValor ICMSST: </b> '+obj.valorIcmsSt+' <b> &nbspValor IPI: </b> '+obj.valorIpi+' <b> &nbspSeq XML:</b> '+obj.sequencia+' <b>&nbsp EAN: </b> '+obj.ean+' <b> &nbspEan Trib: </b> '+obj.eanTrib+' <b> &nbspcomplemento Nome:</b> '+obj.infAdProd+'</label></input></td></tr>');    
             }
         });
         cssDoBotao(chave,prod,cont,mapa);
     }
 });
} 
function ltrim(str) {
return str.replace(/^\s+/,"");
}
function cssDoBotao(chave,prod,cont,lista,condicao,map)
{
   var contInterno=0;
   var contExterno=0;
   var SpedRefencia;
   var nfeSelecionada;
   var descNfe; 
   var aux;
   var idnfe;
  $('#button'+chave+cont).hover(function(){$(this).css("background-color","white")},
                           function(){$(this).css("background-color","#d3d3d3")}).css("cursor","pointer").click( function(){
                                
                              
                               if(condicao ==1){
                          $("input[type='radio'][name=listaNfe"+chave+cont+"]").each(function(){
                          if ($(this).is(':checked')){
                             nfeSelecionada = $(this).parent().text();
                             var index = nfeSelecionada.indexOf("-");
                             descNfe = nfeSelecionada.slice(1,index);
                             aux=ltrim(descNfe);
                             SpedRefencia =prod.id; 
                             idnfe = encontraNfeReferencia (lista,aux);
                             confirmarItens(SpedRefencia, idnfe,chave,cont,nfeSelecionada,prod,map);  
                          } 
                      
                           });
                       }if(condicao == 0)
                       {
                          $("input[type='checkbox'][name=listaNfe"+chave+cont+"]").each(function(){
                          if ($(this).is(':checked')){
                             nfeSelecionada = $(this).parent().text();
                             var index = nfeSelecionada.indexOf("-");
                             descNfe = nfeSelecionada.slice(1,index);
                             aux=ltrim(descNfe);
                             SpedRefencia =prod.id; 
                             idnfe = encontraNfeReferencia (lista,aux);
                             confirmarItens(SpedRefencia, idnfe,chave,cont,nfeSelecionada,prod,map);  
                          } 
                           }); 
                       }
                       

                     });
            // alert("Sped Selecionado ----> " +SpedRefencia+" Nfe Selecionada -----> "+descNfe);;          
}
function verificaQtdDeVinculadosPorChave (chave,obj)
{ 
   var listaAux = new Array();
    var contador = 0;
    $.each(obj,function(i,j){
      var  prod =JSON.parse(j);
        if(prod.chave == chave)
        {
          listaAux.push(j);
          contador++;  
        }
    });
    return contador;
} 
function verificaQtdDeVinculadosAutoPorChave (chave,obj,cont)
{ 
    $('#gerarTabela'+cont).css("cursor","pointer").hover(function(){
        $(this).css("background-color","white");
    },function(){ $(this).css("background-color","#cecece;");});
   var listaAux = new Array();
    $.each(obj,function(i,j){
      var  prod =JSON.parse(j);
        if(prod.chave == chave)
        {
          listaAux.push(j);
        }
    });
    $('#gerarTabela'+cont).click(function(){
           tabelaDeVinculadosAuto(listaAux);
      });
}
function verificaChaveNaPesquisa(lista)
{  
    var objeto;
    var chave = $('#campoBuscaTable').val();
    $.each(lista,function(i,j)
    {
        var prod = JSON.parse(j);
        if(j.chave == chave) 
        {
            objeto = prod;
        }
    });
    return objeto;
}
function pesquisaNaTableDeVinculadosAuto(chave) 
{  
    $('#pesquisarNaTable').click(function()
    {
      tabelaDeVinculadosAuto();  
    });
}
function tabelaDeVinculadosAuto (listDeItensVinculadosAuto) 
{  
    var cont = 0
  $('.linhasTable').remove();
  $.each(listDeItensVinculadosAuto,function(i,j){
      cont++;
     var prod =JSON.parse(j); 
      $('#tableDeVinculadosAuto').append('<tr class="linhasTable" id="linhasTable'+cont+'">\n\
       <td class="ci">'+prod.chave+'</td>\n\
       <td class="ci">'+prod.descricaoSped+'</td>\n\
       <td class="ci">'+prod.descricaoNfe+'</td>\n\
       <td class="ci"> '+prod.sequenciaSped+'</td>\n\
       <td class="ci"> '+prod.sequenciaNfe+'</td>\n\
       <td class="ci">'+prod.dataVinculacao+'</td>\n\
       <td class="ci">'+prod.filto+'</td></tr>');  
        $('#linhasTable'+cont).dblclick(function(){
             detalhesDosITensDaTableDeVinculadosAuto(prod) 
            });
  }); 
  $('.modal-fechar').click(function(){
    $('.modal').hide("fast");  
  });
  $('#linkExelid').click(function(){
      $('#tableDeVinculadosAuto').table2exel({
          exclude:"ls",
          name:"My bio",
          filename:"biodata"
      });
  });
  $('.modal').show("fast");
} 
function detalhesDosITensDaTableDeVinculadosAuto (obj) 
{ 
$('.controle').remove();    
$('.modal2').append('<div class="controle"><label><b><i>Chave:&nbsp</i><u>'+obj.chave+'</u></b></label> <div class="comparacao">\n\
 <div id="sped">\n\
<label> NO SPED : </label><ul>\n\
  <li><b>Descrição SPED: </b>'+obj.descricaoSped+'</li> \n\
<li><b>VALOR: </b>'+obj.valorS+'</li>\n\
<li><b>QTD: </b>'+obj.qtdS+'</li>\n\
<li><b>EAN: </b>'+obj.eanS+'</li>\n\
<li><b>SEQUENCIA: </b>'+obj.sequenciaSped+'</li>\n\
  </ul></div>\n\
 <div id="nfe"><label> NO XML :</label><ul>\n\
 <li><b>Descrição XML: </b>'+obj.descricaoNfe+'</li> \n\
<li><b>VALOR: </b>'+obj.valor+'</li>\n\
<li><b>QTD: </b>'+obj.qtd+'</li>\n\
<li><b>EAN: </b>'+obj.ean+'</li>\n\
<li><b>EanTRIB: </b>'+obj.eanTrib+'</li>\n\
<li><b>SEQUENCIA: </b>'+obj.sequenciaNfe+'</li>\n\
 </ul></div>\n\
 </div> \n\
<div id="spes"> \n\
  <label><b>No SPED Original:</b><br></label> \n\
 <label>'+obj.linhaOriginal+'<br></label> \n\
     <label><b>No SPED Modificado:<br></b></label>\n\
  <label>'+obj.linhaAlterada+'</label>\n\
</div></div></div>');   
    $('.modal2').click(function()
    {
      $('.modal2').hide("fast");     
    });  
    $('.modal2').show("fast");  
}
function encontraNfeReferencia (listaNfe,id) 
{ 
    var idReal;
    $.each(listaNfe,function(i,j){
        if(id == j.id)
        {
            idReal = j.id;
        }
    });
    return idReal;
}
function removerItemJaSelecionado(container,nfe)
{
$('input',container).each(function()
{
     if(nfe == $(this).parent().text()) 
     {
    var linhaDaTabela = $(this).closest('.nfelinha'); 
    linhaDaTabela.remove(); 
     }
});
}
function confirmarItens(idLinhaNfeSped, idNfe,chave,cont,nfeSelecionada,prod,map){
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
      
    $.ajax({        
        type: "POST",
        url: url + "processarItensManuais",        
        data: JSON.stringify(itemAssociado),
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
           $('#relacionamentoTable'+chave+cont).remove();
           //map.delete(prod);
           QuantidadeParaVincularManual--;
           var container = $('#divRelacao'+chave).closest('.divContainer'); 
                        if($('input',container).length==0){
                          alterarCssQuandoConcluido(chave);  
                        }
           removerItemJaSelecionado(container,nfeSelecionada);
        },
        error: function (response, a, b) {alert("erro");},
        beforeSend: function (request) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            if (token != null && token != undefined && token != "")
                request.setRequestHeader(header, token);
        }
    });         
}  
function alterarCssQuandoConcluido (chave) 
{  
    $('.linhasParaOcultarDaTabela'+chave).fadeToggle(0);
    $('#cd'+chave).css("background-color","#BCDB6E").css("cursor","pointer").
            hover(function(){$(this).css("background-color","white")},function(){$(this).css("background-color","#BCDB6E")}).
            click(function()
            {
               $('.linhasParaOcultarDaTabela'+chave).fadeToggle(0);
            });
    
}
function pesquisarNaTabelaDeVinculadosAuto (lista)
{ 
    
    $('#pesquisarNaTable').click(function(){
        var chave = $('#campoBuscaTable').val();
        var listaAux = new Array();
       $.each(lista,function(i,j){
        var  prod =JSON.parse(j);
        if(prod.chave == chave)
        {
          listaAux.push(j);
        }
    });
        if(listaAux.length == 0)
        {
           tabelaDeVinculadosAuto(lista); 
        }else 
        {
         tabelaDeVinculadosAuto(listaAux);
        }
      });
}

function criaModelComTodasInformacoes(obj,descricao)
{  
   $('#telaConsumoMix').show();
   $("#fecharTela").click(function()
    {
      $('#telaConsumoMix').hide();
    });
    $('#conteudomix').remove();
    $('#telaConsumoMix').append('<div id"conteudoMix"> <label><i><b>'+descricao+'</b></i></label>\n\
             <table class="tabelainfoConsumoMix1">\n\
                     <tr><td class="cd" colspan="2" >NA MIX: Entrada Industria</td></tr>\n\
                    <tr><td class="cd">CST</td><td class="cl">'+obj.icmsEntrada.eiCst+'</td></tr>\n\
                    <tr><td class="cd">ALIQUOTA</td><td class="cl">'+obj.icmsEntrada.eiAlq+'</td></tr>\n\
                    <tr><td class="cd">REDUÇÃO</td><td class="cl">'+obj.icmsEntrada.edRbc+'</td></tr>\n\
                    <tr><td class="cd">MVA</td><td class="cl">'+obj.icmsEntrada.mva+'</td></tr>\n\
                    <tr><td class="cd">FUNDAMENTO LEGAL</td><td class="cl">'+obj.icmsEntrada.fundamentoLegal+'</td></tr>\n\
             </table>\n\
                     <table class="tabelainfoConsumoMix1">\n\
                    <tr><td class="cd" colspan="2">NA MIX: Distribuidor</td></tr>\n\
                    <tr><td class="cd">CST</td><td class="cl">'+obj.icmsEntrada.eiCst+'</td></tr>\n\
                    <tr><td class="cd">ALIQUOTA</td><td class="cl">'+obj.icmsEntrada.edAlq+'</td></tr>\n\
                    <tr><td class="cd">REDUÇÃO</td><td class="cl">'+obj.icmsEntrada.edRbc+'</td></tr>\n\
                    <tr><td class="cd">MVA</td><td class="cl">'+obj.icmsEntrada.mva+'</td></tr>\n\
                    <tr><td class="cd">FUNDAMENTO LEGAL</td><td class="cl">'+obj.icmsEntrada.fundamentoLegal+'</td></tr>\n\
              </table>\n\
              <table class="tabelainfoConsumoMix2">\n\
                    <tr><td class="cd" colspan="2">NA MIX: Saida Contriuinte</td></tr>\n\
                    <tr><td class="cd">CST</td><td class="cl">'+obj.icmsSaida.svcCst+'</td></tr>\n\
                    <tr><td class="cd">ALIQUOTA</td><td class="cl">'+obj.icmsSaida.svcAlq+'</td></tr>\n\
                    <tr><td class="cd">REDUÇÃO</td><td class="cl">'+obj.icmsSaida.svcRbc+'</td></tr>\n\
                    <tr><td class="cd">MVA</td><td class="cl">'+obj.icmsSaida.sncRbcst+'</td></tr>\n\
                    <tr><td class="cd">FUNDAMENTO LEGAL</td><td class="cl">'+obj.icmsSaida.fundamentoLegal+'</td></tr>\n\
             </table>\n\
             <table class="tabelainfoConsumoMix2">\n\
                    <tr><td class="cd" colspan="2">NA MIX: Saida Consumidor</td></tr>\n\
                    <tr><td class="cd">CST</td><td class="cl">'+obj.icmsSaida.sncCst+'</td></tr>\n\
                    <tr><td class="cd">ALIQUOTA</td><td class="cl">'+obj.icmsSaida.sncAlqst+'</td></tr>\n\
                    <tr><td class="cd">REDUÇÃO</td><td class="cl">'+obj.icmsSaida.sasRbc+'</td></tr>\n\
                    <tr><td class="cd">MVA</td><td class="cl">'+obj.icmsSaida.sasRbc+'</td></tr>\n\
               <tr><td class="cd">FUNDAMENTO LEGAL</td><td class="cl">'+obj.icmsSaida.fundamentoLegal+'</td></tr>\n\
               </table></div>');  
}
function BuscarInformacoesNoWebServiceMixPeloEan(url,ean,descricao) 
{ 
   var param = ean;

   $.ajax({        
        type: "POST",
        url: url + "consultarWsMix",        
        data:param,
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            criaModelComTodasInformacoes(response,descricao);
        },
        error: function (response, a, b) {
            console.log(response);
            console.log(a);
        },
        beforeSend: function (request) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            if (token != null && token != undefined && token != "")
                request.setRequestHeader(header, token);
        }
    });                
}
//function criandoDivComTabelaDosVinculadosAuto (list,chave) 
//{    
//$('#relacionamentoManual').append('<div class="tabelaVinculadosAuto">


////    $.each(list,function(i,j){
////    var  prod =JSON.parse(j);
////$('#tableDeVinculadosAuto'+chave).append('<tr class="linhasTable"><td class="ci">'+prod.chave+'</td><td class="ci">'+prod.descricaoSped+'</td><td class="ci">'+prod.descricaoNfe+'</td><td class="ci"> '+prod.sequenciaSped+'</td><td class="ci"> '+prod.sequenciaNfe+'</td><td class="ci">'+prod.dataVinculacao+'</td><td class="ci">'+prod.filto+'</td></tr>');
//   // });
//    //$('#tabelaVinculadosAuto').show();
//}