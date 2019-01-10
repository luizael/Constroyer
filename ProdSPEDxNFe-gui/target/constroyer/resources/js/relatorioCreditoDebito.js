    var html ={
        itens:"",
        totalItens:0,
        totalValorOperacao:0,
        totalBaseCalculoSped:0,
        totalBaseCalculoMix:0,
        totalReducaoSped:0,
        totalReducaoMix:0,
        totalIcmsSped:0,
        totalIcmsMix:0,
        totalCreditoNaoAproveitado:0,
        totalEstornoDeCredito:0,
        registrosSalvo : 0,
        liberarDialog:false
    }
    html.header = '';
    
   function montarItensEntrada(ob){
        var row ="<tr>";           
            row += '<td  align="center" class="txt">'+ob.chaveNfe+'</td>';
            row += '<td  align="center" class="txt">'+ob.clienteFornecedor+'</td>';
            row += '<td  align="center" class="txt">'+ob.numeroNota+'</td>';
            row += '<td  align="center" class="txt">'+ob.descricaoProduto+'</td>';
            row += '<td  align="center" class="txt">'+ob.ean+'</td>';
            row += '<td  align="center" class="txt">'+ob.codigoInternoProduto+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.valorOperacao)+'</td>';
            row += '<td  align="center" class="txt">&nbsp'+ob.cstIcmsSped+'</td>';
            row += '<td  align="center" class="txt">&nbsp'+ob.cstIcmsMix+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.aliqIcmsSped)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.aliqIcmsMix)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.baseDeCalculoSped)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.baseDeCalculoMix)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.redBcSped)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.redBcMix)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.cargaTributariaSped)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.cargaTributariaMix)+'</td>';
            row += '<td  align="center" class="txt">'+ob.cfopSped+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.creditoIndevido)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.estornoDeCredito)+'</td>';
            row +='</tr>'; 
        return row;
    }   
    
    function RPF_montarHTMLEntrada(itens){ 
        RPF_TituloRelatorio = "Divergencia de Entrada";
       
        html.page = ''
           
            .concat('<table border="1" cellpadding="0" cellspacing="0" width="1964" style="border-collapse: collapse;width:1473pt;font-size: 11px; background: #fff" id="datatable">')
            .concat('<tr style="mso-height-source:userset;height:16.5pt">')
            .concat('<td width="308" style="border-top:none;border-left:none;width:231pt" align="center">chave da nota</td>')
            .concat('<td height="22" width="63" style="height:16.5pt;border-top:none;width:47pt" align="center">fornecedor</td>')
            .concat('<td width="64"  style="border-top:none;border-left:none;width:48pt" align="center">numero da nota</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">descricao do produto</td>') 
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">ean do produto</td>') 
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">codigo interno do produto</td>') 
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">valor da operacao</td>') 
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">cst sped</td>') 
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">cst mix</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">aliquota sped</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">aliquota mix</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">base de calculo sped</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">base de calculo mix</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">reducao bc sped</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">reducao bc  mix</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">valor icms sped</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">valor icms mix</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">cfop</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">credito  nao aproveitado</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">estorno de crédito</td>')                      
            .concat('</tr>')
            .concat(itens)
            .concat('<tr>')
            .concat('<td>T. de Itens</td>')
            .concat('<td></td>')
            .concat('<td></td>')
            .concat('<td></td>')
            .concat('<td></td>')
            .concat('<td></td>')
            .concat('<td>'+html.totalValorOperacao+'</td>')
            .concat('<td></td>')
            .concat('<td></td>')
            .concat('<td></td>')
            .concat('<td></td>')           
            .concat('<td>'+html.totalBaseCalculoSped+'</td>')
            .concat('<td>'+html.totalBaseCalculoMix+'</td>')
            .concat('<td>'+html.totalReducaoSped+'</td>')
            .concat('<td>'+html.totalReducaoMix+'</td>')
            .concat('<td>'+html.totalIcmsSped+'</td>')
            .concat('<td>'+html.totalIcmsMix+'</td>')
            .concat('<td></td>')
            .concat('<td>'+html.totalCreditoNaoAproveitado+'</td>')
            .concat('<td>'+html.totalEstornoDeCredito+'</td>')
            .concat('</tr>')                
            .concat('</table>');    
            
        $("#container").html(html.page); 
        $(indexVars.divBarraProgresso).dialog("close");
    }
    
    function montarItensSaida(ob){
         var row ="<tr>"; 
            row += '<td  align="center" class="txt">'+ob.tipoDoc+'</td>';
            row += '<td  align="center" class="txt">'+ob.chaveNfe+'</td>';
            row += '<td  align="center" class="txt">'+ob.clienteFornecedor+'</td>';
            row += '<td  align="center" class="txt">'+ob.numeroNota+'</td>';
            row += '<td  align="center" class="txt">'+ob.descricaoProduto+'</td>';
            row += '<td  align="center" class="txt">'+ob.ean+'</td>';
            row += '<td  align="center" class="txt">'+ob.codigoInternoProduto+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.valorOperacao)+'</td>';
            row += '<td  align="center" class="txt">'+ob.cfopSped+'</td>';
            row += '<td  align="center" class="txt">'+ob.cfopXml+'</td>';
            row += '<td  align="center" class="txt">&nbsp'+ob.cstIcmsSped+'</td>';
            row += '<td  align="center" class="txt">&nbsp'+ob.cstIcmsXml+'</td>';
            row += '<td  align="center" class="txt">&nbsp'+ob.cstIcmsMix+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.aliqIcmsSped)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.aliqIcmsXml)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.aliqIcmsMix)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.baseDeCalculoSped)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.baseDeCalculoXml)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.baseDeCalculoMix)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.redBcSped)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.redBcXml)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.redBcMix)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.cargaTributariaSped)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.cargaTributariaXml)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.cargaTributariaMix)+'</td>';            
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.debitoIndevido)+'</td>';
            row += '<td  align="center" class="num">'+RPF_replacePontuacao(ob.debitoNaoDeclarado)+'</td>';
            row +='</tr>'; 
        return row;
    }
    function RPF_montarHTMLSaida(itens){ 
        RPF_TituloRelatorio = "Divergencia de Entrada";
        html.page = ''
           
            .concat('<table border="1" cellpadding="0" cellspacing="0" width="1964" style="border-collapse: collapse;width:1473pt;font-size: 11px; background: #fff" id="datatable">')
            .concat('<tr style="mso-height-source:userset;height:16.5pt">')
            .concat('<td width="308" style="border-top:none;border-left:none;width:231pt" align="center">tipo</td>')
            .concat('<td width="308" style="border-top:none;border-left:none;width:231pt" align="center">chave da nota</td>')
            .concat('<td height="22" width="63" style="height:16.5pt;border-top:none;width:47pt" align="center">cliente</td>')
            .concat('<td width="64"  style="border-top:none;border-left:none;width:48pt" align="center">numero da nota</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">descricao do produto</td>') 
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">ean do produto</td>') 
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">codigo interno do produto</td>') 
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">valor da operacao</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">cfop sped</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">cfop xml</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">cst sped</td>') 
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">cst xml</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">cst mix</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">aliquota sped</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">aliquota xml</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">aliquota mix</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">base de calculo sped</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">base de calculo xml</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">base de calculo mix</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">reducao bc sped</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">reducao bc xml</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">reducao bc  mix</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">valor icms sped</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">valor icms xml</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">valor icms mix</td>')
            
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">debito indevido</td>')
            .concat('<td width="244" style="border-top:none;border-left:none;width:183pt" align="center">debito nao declarado</td>')                      
            .concat('</tr>')
            .concat(itens)
            .concat('<tr>')
            .concat('<td>T. de Itens</td>')
            .concat('<td></td>')
            .concat('<td></td>')
            .concat('<td></td>')
            .concat('<td></td>')
            .concat('<td></td>')
            .concat('<td>'+html.totalValorOperacao+'</td>')
            .concat('<td></td>')
            .concat('<td></td>')
            .concat('<td></td>')
            .concat('<td></td>')           
            .concat('<td>'+html.totalBaseCalculoSped+'</td>')
            .concat('<td>'+html.totalBaseCalculoMix+'</td>')
            .concat('<td>'+html.totalReducaoSped+'</td>')
            .concat('<td>'+html.totalReducaoMix+'</td>')
            .concat('<td>'+html.totalIcmsSped+'</td>')
            .concat('<td>'+html.totalIcmsMix+'</td>')
            .concat('<td></td>')
            .concat('<td>'+html.totalCreditoNaoAproveitado+'</td>')
            .concat('<td>'+html.totalEstornoDeCredito+'</td>')
            .concat('</tr>')                
            .concat('</table>');    
            
        $("#container").html(html.page);     
        $(indexVars.divBarraProgresso).dialog("close");
    }     

    function RPF_replacePontuacao(valor){
        try{
            return valor.toString().replace(".",",");
        }catch(err){
            return "nao se aplica";
            //console.log("err: -"+ err);
        }
    }
    function RPF_fecahrRelatorio(){
        $("#container").dialog("close");
    }
    
    function RPF_identificarDownloadXls(){
        var nomeArquivo = "#{relCredDebBackBean.tipoRelatorio}" + ".xls";
        var HTML = '<a download="'+nomeArquivo+'" href="#" onclick="return ExcellentExport.excel(this, \'datatable\', \'Relatório\');" style="margin: 15px;" >Download em XLS</a>';
        $("#linkDown").html(HTML);
    }
    
    function RPF_dialogo(){
            showMessage(
                        "Erro", 
                        "Houve um erro ao tentar processar os dados. Msg: ",
                        { "Ok": function () { 
                                return false;
                                $(this).dialog("close"); 
                            } 
                        });
            return true;
    }
    
    function RPF_toogleFormulario(){
       var desabilitar = false;
       RPF_tipo = $("select option:selected").val();
       if(RPF_tipo == 'DivergenciaEntrada')
           desabilitar = true;
          $(".CaminhoArquivo").val("");     
           
       $("#btnNavCaminhoDirXMLsNFes").prop("disabled", desabilitar);
       $("#btnNavCaminhoDirXMLsNFCe").prop("disabled",desabilitar);
       $("#btnNavCaminhoDirXMLsCFe").prop("disabled",desabilitar);
       $(".CaminhoArquivo").prop("disabled",desabilitar);
    }
    
    function RPF_processarRelatorio(){   
        html.itens = "";
        $("#container").html("");
         var data = { 
                    "tipo":RPF_tipo,
                    "atualizar":$("#atualizarWs").prop("checked")?"sim":"nao",
                    "caminhoSped": $("#"+RPF_caminhoSped).val(),
                    "caminhoNfe": $("#"+RPF_caminhoNFe).val(),
                    "caminhoNFCe":$("#"+RPF_caminhoNFCe).val(),
                    "caminhoCFe":$("#"+RPF_caminhoCFe).val()                    
                }; 
                
        var loadTitulo = "";
        $(indexVars.barraProgresso).progressbar("value", 0);
        $(indexVars.labelProgresso).html("");
        $(indexVars.descProgesso).html("");
        $(indexVars.barraProgressoBtnOk).hide();
        executeWebMethod(
        RPF_url, 
        "processarRelatorio",
        JSON.stringify(data),
        function (ret) {                        
            if (ret.result === "Success") { 
               RPF_obterRelatorio();
            } else {
                showMessage(
                    "Erro", 
                    "Houve um erro ao tentar gerar o relatorio: " + ret.message,
                    { "Ok": function () { $(this).dialog("close"); } });
            }                        
        },
        function () {
            $("#btProcessar").removeAttr("disabled");
            $("#btProcessar").attr("value", "Processar");
            checarProgresso();
            indexVars.verificarProgresso = false;
            
           // $(indexVars.barraProgressoBtnOk).show();
        });
        indexVars.verificarProgresso = true;
        checarProgresso();
        $(indexVars.divBarraProgresso).dialog("open");                
    }
    //--------------------------------------------------------------------------
    function RPF_obterRelatorio(){
        if(html.itens != ""){
//            $("#container").dialog({
//                title:RPF_TituloRelatorio
//            }).dialog("open");
            return;
        }        
        var data = {};
        executeWebMethod(
        RPF_url, 
        "obterRelatorio",
        JSON.stringify(data),
        function (ret) {                        
            if (ret.result === "Success") { 
                html.totalItens = ret.object.ttItem;
                html.totalCreditoNaoAproveitado = ret.object.ttNAproveitado;
                html.totalEstornoDeCredito = ret.object.ttEstorno;
                $(indexVars.descProgesso).html("Aguarde a conclusão...");
                if(ret.object.tipo == "DivergenciaEntrada"){
                    //console.log('Gerando relatorio de entrada.........')
                    $(ret.object.lista).each(function(i){                    
                        html.itens+=montarItensEntrada(this);
                    });
                    RPF_montarHTMLEntrada(html.itens);
                }else{
                    //console.log('Gerando relatório de saida.........')
                    $(ret.object.lista).each(function(i){                    
                        html.itens+=montarItensSaida(this);
                    });
                    RPF_montarHTMLSaida(html.itens);
                }
            } else {
                showMessage(
                    "Erro", 
                    "Houve um erro ao obter os itens do relatorio gerado: " + ret.message,
                    { "Ok": function () { $(this).dialog("close"); } });
            }                        
        });
    }     
    
    function mostrarTelaDeConfirmacaoParaSalvarDadosImportadosDoExel(lista){
        $("#dialog-confirm-exel").remove();
        var nomeEmpresa ="";
      
        if(lista.length != 0){
            $.each(lista,function(i,obj){ 
                var objeto = JSON.parse(obj);
                $("#informacoesParaSalvar").dialog({
                    title:"Verifique as informações para prosseguir",
                    autoOpen: false,
                    width: 811, 
                    height: 600,
                    position: { my: "center center", at: "center center", of: window },
                    buttons:[
                            {
                                id:"btnConfirma",
                                text:"Confirmar",
                                click:function(){
                                  RPF_obterlistaDeComparacaoParaConfirmacaoDoUsuario(RPF_url,"ConfirmacaoExelParaBanco");
                                }
                            },
                            {
                                id:"btnCancelar",
                                text:"Cancelar",
                                click:function(){
                                    html.liberarDialog = true;
                                    $(this).dialog("close");
                                }
                            }
                        ]
                });
                if(i == 1 )
                {                  
                    $('#informacoesParaSalvar').append('<label id="nomeEmpresaParaCabecalhoDaConfirmacao"> SPED : '+objeto.nomeSped+'</label>');
                }                
                $("#informacoesParaSalvar").append('\
                    <div class="divExel">\n\
                        <table class="tabelaExel">\n\
                            <tr><td class="caracTable">CARACTERISTICAS</td><td class="leTable">SPED</td><td class="leTable">EXEL</td></tr>\n\
                            <tr><td class="leTable">CODIGO</td><td class="ldTable">'+objeto.codigoSped+'</td><td class="ldTable">'+objeto.codigoExel+'</td></tr>\n\
                            <tr><td class="leTable">DESCRIÇÃO</td><td class="ldTable">'+objeto.descSped+'</td><td class="ldTable">'+objeto.descExel+'</td></tr>\n\
                            <tr><td class="leTable">EAN</td><td class="ldTable">'+objeto.eanSped+'</td><td class="ldTable">'+objeto.eanExel+'</td></tr>\n\
                        </table>\n\
                    </div>');
            });  
        }else{          
            $("#informacoesParaSalvar")
                    .html('<p><span>Nenhum dos dados lidos no Exel foram encontrados no SPED. \n\
                            Verifique se o SPED é compativel com o Exel!</span></p>'); 
        }
       
    }
    
    function checarContadorCsv(isSetTimeOut = true){
         var data = { };
         executeWebMethod(
            RPF_url, 
            "ChecarContador",
            JSON.stringify(data),
            function (ret) {                        
                if (ret.result === "Success") {
                    $("#contadorCsv").html(ret.object);
                    html.registrosSalvo = ret.object;
                }                        
            });
        if(isSetTimeOut){
            setTimeout(checarContadorCsv, 500);
        }
    }
    function abrirDialogDeInformacoes(parametro, isSave = false){ 
        checarContadorCsv();
        if(parametro == "ConfirmacaoExelParaBanco"){
            $('#informacoesParaSalvar').dialog({
                title:"Salvando aguarde...",
                height: 200,
                buttons:null,
                position: { my: "center center", at: "center center", of: window },
            });
        }
        if(isSave){
            $('#informacoesParaSalvar').dialog({
                title:"Operação concluida",
                height: 200,
                width:400,
                beforeClose:null,
                position: { my: "center center", at: "center center", of: window },
                buttons:[
                            {
                                id:"btnFinalizar",
                                text:"finalizar",
                                click:function(){
                                  $("#atualizarWs").attr("checked", false);
                                  $('#informacoesParaSalvar').dialog("close");
                                  //RPF_processarRelatorio();
                                }
                            }
                        ]
            });
           
        }
        $('#informacoesParaSalvar')
                .html('<span id="contadorCsv"></span>')
                .dialog("open").dialog({position: { my: "center center", at: "center center", of: window },});
    }
    function RPF_obterlistaDeComparacaoParaConfirmacaoDoUsuario(url,complemento){
        var data = { 
           "tipo":RPF_tipo,
           "atualizar":$("#atualizarWs").prop("checked")?"sim":"nao",
           "formatarCsv":$("#retirarUmDoCsv").prop("checked")?"sim":"nao",
           "caminhoSped": $("#"+RPF_caminhoSped).val(),
           "caminhoNfe": $("#"+RPF_caminhoNFe).val(),
           "caminhoNFCe":$("#"+RPF_caminhoNFCe).val(),
           "caminhoCFe":$("#"+RPF_caminhoCFe).val(),
           "caminhoExel":$("#"+RCF_caminhoExel).val()
        }; 
        abrirDialogDeInformacoes(complemento);
        $.ajax({        
          type: "POST",
          url: url +complemento,
          data: JSON.stringify(data),
          contentType: "application/json",
          dataType: "json",
          success: function (response) {     
            if(complemento == "ConfirmacaoExelParaBanco"){
                abrirDialogDeInformacoes(complemento, true);
            }else {
                mostrarTelaDeConfirmacaoParaSalvarDadosImportadosDoExel(response.object);
            }
          },
          error: function (response, a, b) {
              $('#informacoesParaSalvar')
                .html('<span id="contadorCsv">'+ response + a + b +'</span>');
          },
          beforeSend: function (request) {
              var token = $("meta[name='_csrf']").attr("content");
              var header = $("meta[name='_csrf_header']").attr("content");
              if (token != null && token != undefined && token != "")
                  request.setRequestHeader(header, token);
          }
      });    
    }
    
   
