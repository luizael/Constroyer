// <![CDATA[
    var tituloPagina =" Requisicao de Aualização de Informaçoes Fiscais"; 
    var Req_url = contextPath +  "/ws/reqInfoFiscalWs/"
    var Req_divGrid = "#jqGrideRequisicao";
    var Req_divPageId = "#jqGrideRequisicaoPage";
    var Req_divIdFormulario = "#idDivFormulario";
    var Req_nameInputUpLoadFile = "upFile";
    var Req_idCbCliente = "#cbCliente";
    var Req_txtUpCnpjCliente = "";
    var Req_ativarClose = true;
    var Req_btnsalvar = "_btnSalvar";
    var Req_buttons =
    [
        { id: Req_btnsalvar, text: "Salvar", click: function () {
           Req_salvar();           
        }},
        /*{ id: tituloPagina+"_btnExcluir", text: "Excluir", click: function () {
            //Req_confirmarExclusao($(Req_idtxtHdnId).val());
            $(Req_divIdFormulario).dialog("close");
        }},
        { id: tituloPagina+"_btnCancelar", text: "Cancelar", click: function () {
            $(this).dialog("close");
        }}*/
    ];
    
    var ativarClose = function(){
        if(Req_ativarClose){
            $(this).dialog("close");
        }else{
            $(this).dialog("open");
        }
    }
    
    $(document).ready(function(){  
        carregarForm(Req_divIdFormulario,"Cadastro de "+tituloPagina,850,Req_buttons,300,ativarClose);
        Req_configurarGridCadastro();
        setZindexJqGrideZero(Req_divGrid);
    });
    
    function Req_novo(){
        $("#"+Req_btnsalvar).button("enable");
        limparForm(Req_divIdFormulario);
        AbrirFormulario(Req_divIdFormulario);
    }
    
    function Req_confirmarExclusao(id) {
        showMessage("Confirmação", "Tem certeza que deseja excluir?",
        {
            "Sim": function () { Req_excluir(id);$(this).dialog("close");},
            "Não": function () { $(this).dialog("close"); }
        });
    }
    
    function Req_excluir(id){
        var data={idRequisicaoAtualizacaoInfoFiscal:id}
        executeWebMethod(Req_url, "reqInfoFiscalExcluir",
            JSON.stringify(data),
            function (ret) {
                var retObj = ret;
                if (retObj != null) {
                    if (retObj.result == "Success") {
                         $(Req_divGrid).trigger("reloadGrid");  
                         //$(this).dialog("close");
                    } else {
                        showMessage("Ateção", retObj.message);
                    }
                } else
                    showMessage("Cadastro de "+tituloPagina, "Houve um erro ao excluir.");
            });
    }    
    
    function Req_salvar(){
        var file = $('input[name="'+ Req_nameInputUpLoadFile +'"').get(0).files[0];
        var data = new FormData();
        data.append(Req_nameInputUpLoadFile,file);
        data.append("idCliente",$(".cbCliente").val());
        data.append("formatarCodigoInterno",$(".cbFormatarCodigoInterno").val());
        $("#"+Req_btnsalvar).button("disable");
        $("#statusUpLoad").html("eviando arquivo, aguarde...");
        Req_ativarClose = false;
        $.ajax({
            url:Req_url + "reqInfoFiscalSalvar",
            type:'POST',
            xhr:function(){
               return $.ajaxSettings.xhr();
            },
            success:function(response){
               var obj = response;
               if(obj != null){
                   if(obj.result == "Success"){
                      alert("Sucesso!")
                   }                   
               }
            },
            data:data,
            cache:false,
            contentType:false,
            processData:false,
            dataType: "json",
            async: true,
            error: function (a,b,c) {
                 Req_ativarClose = true;
                /*var msg = JSON.parse(a.responseText); 
                alert("a["+a+"], b["+b+"], c["+c+"]" + msg.Message );*/
            },
            complete: function (response) {
                var titulo = "Arquivo enviado com sucesso!";
                
                $("#statusUpLoad").html("");
                var data={}
                executeWebMethod(Req_url, "obterStatus",
                    JSON.stringify(data),
                    function (ret) {
                        var retObj = ret;
                        titulo = retObj.message;
                        showMessage("Confirmação", titulo,
                        {
                            "Ok": function () { 
                                $(this).dialog("close");
                                $(Req_divIdFormulario).dialog("close");
                                 $(Req_divGrid).trigger("reloadGrid");
                            }
                        });    
                    });
                              
            },
            beforeSend: function (request) {
              var token = $("meta[name='_csrf']").attr("content");
              var header = $("meta[name='_csrf_header']").attr("content");
              if (token != null && token != undefined && token != "")
                  request.setRequestHeader(header, token);
           }
        });     
    }
    
    function Req_gridButtons(cellvalue, options, rowObject) { 
        var iconExcluir = ''/*, iconEditar*/; 
            /*iconEditar = '<span class="icon-container"><span class="ui-icon ui-icon-pencil" style="display: inline-block" onclick="Cliente_editar(' + rowObject.idRequisicaoAtualizacaoInfoFiscal + ')"></span></span>';*/
            iconExcluir ='<span class="icon-container"><span class="ui-icon ui-icon-trash" style="display: inline-block" onclick=" Req_confirmarExclusao(' + rowObject.idRequisicaoAtualizacaoInfoFiscal +')"></span></span>';
        return iconExcluir /*+ iconEditar*/;
    }    
    function Req_configurarGridCadastro() {               
        var colunas = [  
            { name: '', label: 'A&ccedil;&otilde;es', index: '', width: 105, align: 'center', formatter: Req_gridButtons, search: false , search: false },
            { name: 'idRequisicaoAtualizacaoInfoFiscal', label: 'Id', index: 'idRequisicaoAtualizacaoInfoFiscal', hidden: true},
            { name: 'nomeArquivo', label: 'Arquivo', index: 'nomeArquivo', width: 800 },
            { name: 'dataCadastro', label: 'Data de Cadastro', index: 'dataCadastro', width: 90, formatter: "date",  formatoptions: { srcformat: "ISO8601Long", newformat: "d/m/Y" }},
            { name: 'statusRequisicao', label: 'Status', index: 'statusRequisicao', width: 90 },
            { name: 'nomeCliente', label: 'Cliente', index: 'nomeCliente', width: 90 },
            { name: 'formatarCodigo', label: 'Formatar campo', index: 'formatarCodigo', width: 90 }
        ];                   
       
        configurarJqGrid({
            gridId: Req_divGrid,
            pagerId: Req_divPageId,
            titulo: null,
            urlToSend: Req_url + "listarReqInfoFiscal",
            colunas: colunas,
            permitirOrdenacao: true,
            dados:{},
            autoWidth: false,                
            height: 300,
            width: null,
            shrinkToFit: false,
            loadonce: false,
            filterOptions: null
        });                
    }
// ]]>


