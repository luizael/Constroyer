// <![CDATA[
    var tituloPagina = "Cliente";
    var Cliente_url = contextPath + "/ws/cadastroClienteWS/"; 
    var Cliente_url_upLoadFile = contextPath + "/ws/uploadWs/";
    var Cliente_divIdFormulario = "#idFormularioCliente";
    var Cliente_divGrid = "#jqGrideCliente";
    var Cliente_divPageId = "#jqGrideClientePage";
    var Cliente_idtxtHdnId = "#txtHdnIdCliente";
    var Cliente_txtToken ="#txtToken";
    var Cliente_txtNome = "#txtNome";
    var Cliente_txtCnpj ="#txtCnpj";
    var Cliente_txtIdView = "#txtIdView";
    var Cliente_containerInputFile = "#containerInputFile";   
    var Cliente_nameInputUpLoadFile = "upFile";
    var Cliente_txtUpCnpjCliente = "#upCnpjCliente";
    var Cliente_buttons =
    [
        { id: tituloPagina+"_btnSalvar", text: "Salvar", click: function () {
           Cliente_salvar();
        }},
        { id: tituloPagina+"_btnExcluir", text: "Excluir", click: function () {
            Cliente_confirmarExclusao($(Cliente_idtxtHdnId).val());
            $(Cliente_divIdFormulario).dialog("close");
        }},
        { id: tituloPagina+"_btnCancelar", text: "Cancelar", click: function () {
            $(this).dialog("close");
        }}
    ];
    var Cliente_upLoadButton = [{
            id:tituloPagina+ "_upload",text:"enviar arquivo",click:function(){
                upLoadFile();
            }
    }];

    $(document).ready(function () {
       carregarForm(Cliente_divIdFormulario,"Cadastro de "+tituloPagina,850,Cliente_buttons,300);
       carregarForm(Cliente_containerInputFile,"Upload de Arquivo CSV",550,Cliente_upLoadButton,250);
       Cliente_configurarGridCadastro();
       setZindexJqGrideZero(Cliente_divGrid);
    });    
    function Cliente_novo(){
        limparForm(Cliente_divIdFormulario)
        AbrirFormulario(Cliente_divIdFormulario)
    }    
    function Cliente_salvar(){
       
        if(!validarForm(Cliente_divIdFormulario)){
            showMessage("Validação", "Preencha todos os campos", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }
        
        if(!validarCampo(Cliente_txtToken,200)){
             showMessage("Atenção", "Token máximo 200 caracteres", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }
        
        if(!validarCampo(Cliente_txtNome,200)){
             showMessage("Atenção", "Nome excede o tamanho máximo de 200 caracteres", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }  
       
        if(!validarCampo(Cliente_txtCnpj,14)) {
             showMessage("Atenção", "cjpj excede o tamanho máximo de 14 caracteres", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }
        if(!validarCampo(Cliente_txtIdView,14)) {
             showMessage("Atenção", "informe o id da view", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }
        var data={
                id: $(Cliente_idtxtHdnId).val()== ""? 0:$(Cliente_idtxtHdnId).val(),
                token: $(Cliente_txtToken).val(),
                nome: $(Cliente_txtNome).val(),
                cnpj: $(Cliente_txtCnpj).val(),
                idView:$(Cliente_txtIdView).val(),
        }
        executeWebMethod(Cliente_url, "salvarCliente",
        JSON.stringify(data),
        function (ret) {
            var retObj = ret;
            if(retObj != null){
                if (retObj.result == "Success"){
                    showMessage("Salvando "+tituloPagina, retObj.message, { "Ok": function () { $(Cliente_divIdFormulario).dialog("close"); $(this).dialog("close"); } });
                    $(Cliente_divGrid).trigger("reloadGrid");
                }else{
                     showMessage("Atenção", retObj.message, { "Ok": function () { $(this).dialog("close"); } });
                }
            }else{
                showMessage("Atenção", "Houve um erro ao tentar salvar o registro."+retObj.message, { "Ok": function () { $(Cliente_divIdFormulario).dialog("close"); $(this).dialog("close"); } });
            }                            
        });
    }
    function Cliente_editar(id){
        var data={id:id}
        executeWebMethod(Cliente_url, "selecionarUmCliente",
            JSON.stringify(data),
            function (ret) {
                var retObj = ret;
                if (retObj != null) {
                    if (retObj.result == "Success") {
                        $(Cliente_idtxtHdnId).val(retObj.object.id)
                        $(Cliente_txtToken).val(retObj.object.token);
                        $(Cliente_txtNome).val(retObj.object.nome);
                        $(Cliente_txtCnpj).val(retObj.object.cnpj);
                        $(Cliente_txtIdView).val(retObj.object.idView);                        
                        AbrirFormulario(Cliente_divIdFormulario);
                    } else {
                        showMessage("Cadastro de "+tituloPagina, retObj.Message);
                    }
                } else
                    showMessage("Cadastro de "+tituloPagina, "Houve um erro ao abrir o registro para edição.");
            });
    }
    function Cliente_confirmarExclusao(id) {
        showMessage("Confirmação", "Tem certeza que deseja excluir?",
        {
            "Sim": function () { Cliente_excluir(id); $(this).dialog("close"); },
            "Não": function () { $(this).dialog("close"); }
        });
    }
    function Cliente_excluir(id){
        var data={id:id}
        executeWebMethod(Cliente_url, "excluirCliente",
            JSON.stringify(data),
            function (ret) {
                var retObj = ret;
                if (retObj != null) {
                    if (retObj.result == "Success") {
                         $(Cliente_divGrid).trigger("reloadGrid");  
                         $(this).dialog("close");
                    } else {
                        showMessage("Ateção", retObj.message);
                    }
                } else
                    showMessage("Cadastro de "+tituloPagina, "Houve um erro ao excluir.");
            });
    } 
    
      
    function Cliente_gridButtons(cellvalue, options, rowObject) { 
        var iconExcluir = '', iconEditar = '', iconUpLoad =''; 
            iconEditar = '<span class="icon-container"><span class="ui-icon ui-icon-pencil" style="display: inline-block" onclick="Cliente_editar(' + rowObject.id + ')"></span></span>';
            iconExcluir ='<span class="icon-container"><span class="ui-icon ui-icon-trash" style="display: inline-block" onclick=" Cliente_confirmarExclusao(' + rowObject.id +')"></span></span>';
            iconUpLoad ='<span class="icon-container"><span class="ui-icon ui-icon-document" style="display: inline-block" onclick=" Cliente_uploadCsv(' + rowObject.id +')"></span></span>';
        return iconExcluir + iconEditar + iconUpLoad ;
    }
    
    function Cliente_configurarGridCadastro() {               
        var colunas = [  
            { name: '', label: 'A&ccedil;&otilde;es', index: '', width: 105, align: 'center', formatter: Cliente_gridButtons, search: false , search: false },
            { name: 'id', label: 'Id', index: 'id', hidden: true},
            { name: 'nome', label: 'Nome', index: 'nome', width: 200 },
            { name: 'cnpj', label: 'Cnpj', index: 'cnpj', width: 100 },
            { name: 'token', label: 'Token', index: 'token', width: 500 },
            { name: 'idView', label: 'ID da View', index: 'idView', width: 500 }
        ];                   
       
        configurarJqGrid({
            gridId: Cliente_divGrid,
            pagerId: Cliente_divPageId,
            titulo: null,
            urlToSend: Cliente_url + "listarCliente",
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
    /*
    function Cliente_uploadCsv(id){
        var data={id:id}
        executeWebMethod(Cliente_url, "selecionarUmCliente",
            JSON.stringify(data),
            function (ret) {
                var retObj = ret;
                if (retObj != null) {
                    if (retObj.result == "Success") {
                        $(Cliente_idtxtHdnId).val(retObj.object.id)
                        $(Cliente_txtUpCnpjCliente).val(retObj.object.cnpj);
                    } 
                } else
                    showMessage("Cadastro de "+tituloPagina, "Houve um erro ao abrir o registro para edição.");
            });
        AbrirFormulario(Cliente_containerInputFile);
    }
    */
     
// ]]>

