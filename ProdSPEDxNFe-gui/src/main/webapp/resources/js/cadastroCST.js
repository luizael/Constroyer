// <![CDATA[
    var tituloPagina = "CST";
    var CST_url = contextPath + "/ws/cadastroCSTWS/"; 
    var CST_divIdFormulario = "#idFormularioCST";
    var CST_divGrid = "#jqGrideCST";
    var CST_divPageId = "#jqGrideCSTPage";
    var CST_idtxtHdnId = "#txtHdnIdCST";
    var CST_txtCodigo ="#txtCodigo";
    var CST_txtDescricao ="#txtDescricao";
    var CST_sltTipo ="#sltTipoCst";
    var CST_hdnIdCSTEquivalente = "#hdnIdCSTEquivalente";
    var CST_buttons =
    [
        { id: tituloPagina+"_btnSalvar", text: "Salvar", click: function () {
           CST_salvar();
        }},
        { id: tituloPagina+"_btnExcluir", text: "Excluir", click: function () {
            CST_confirmarExclusao($(CST_idtxtHdnId).val());
            $(CST_divIdFormulario).dialog("close");
        }},
        { id: tituloPagina+"_btnCancelar", text: "Cancelar", click: function () {
            $(this).dialog("close");
        }}
    ];   
    
    $(document).ready(function(){
        carregarForm(CST_divIdFormulario,"Cadastro de "+tituloPagina,850,CST_buttons,300);
        CST_configurarGridCadastro();
        setZindexJqGrideZero(CST_divGrid);
    })
    
    function CST_novo(){
        limparForm(CST_divIdFormulario)
        AbrirFormulario(CST_divIdFormulario)
    }
    function CST_salvar(){
         if(!validarForm(CST_divIdFormulario)){
            showMessage("Validação", "Preencha todos os campos", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }
        if(!validarCampo(CST_txtCodigo,4)){
             showMessage("Atenção", "Código tamanho máximo 4 caracteres", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }               
        if(!validarCampo(CST_txtDescricao,8000)) {
             showMessage("Atenção", "Descrição excede o tamanho máximo de 8000 caracteres", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }
        var data={
                id: $(CST_idtxtHdnId).val() == "" ? 0 : $(CST_idtxtHdnId).val(),
                codigo: $(CST_txtCodigo).val(),
                descricao: $(CST_txtDescricao).val(),
                tipo: $(CST_sltTipo).val(),
                idCSTEquivalente: $(CST_hdnIdCSTEquivalente).val()
        }       
        executeWebMethod(CST_url, "salvarCST",
        JSON.stringify(data),
        function (ret) {
            var retObj = ret;
            if(retObj != null){
                if (retObj.result == "Success"){
                    showMessage("Salvando "+tituloPagina, retObj.message, { "Ok": function () { $(CST_divIdFormulario).dialog("close"); $(this).dialog("close"); } });
                    $(CST_divGrid).trigger("reloadGrid");
                }else{
                     showMessage("Atenção", retObj.message, { "Ok": function () { $(this).dialog("close"); } });
                }
            }else{
                showMessage("Atenção", "Houve um erro ao tentar salvar o registro."+retObj.message, { "Ok": function () { $(CST_divIdFormulario).dialog("close"); $(this).dialog("close"); } });
            }                            
        });
    }
    function CST_editar(id){
        var data={id:id}
        executeWebMethod(CST_url, "editarCST",
            JSON.stringify(data),
            function (ret) {
                var retObj = ret;
                if (retObj != null) {
                    if (retObj.result == "Success") {
                        $(CST_idtxtHdnId).val(retObj.object.id);
                        $(CST_txtCodigo).val(retObj.object.codigo);
                        $(CST_txtDescricao).val(retObj.object.descricao);
                        $(CST_sltTipo).val(retObj.object.tipo);
                        $(CST_hdnIdCSTEquivalente).val(retObj.object.idCSTEquivalente);
                        AbrirFormulario(CST_divIdFormulario);
                    } else {
                        showMessage("Cadastro de "+tituloPagina, retObj.Message);
                    }
                } else
                    showMessage("Cadastro de "+tituloPagina, "Houve um erro ao abrir o registro para edição.");
            });
    }
    function CST_confirmarExclusao(id) {
        showMessage("Confirmação", "Tem certeza que deseja excluir?",
        {
            "Sim": function () { CST_excluir(id); $(this).dialog("close"); },
            "Não": function () { $(this).dialog("close"); }
        });
    }
    function CST_excluir(id){
        var data={id:id}
        executeWebMethod(CST_url, "excluirCST",
            JSON.stringify(data),
            function (ret) {
                var retObj = ret;
                if (retObj != null) {
                    if (retObj.result == "Success") {
                         $(CST_divGrid).trigger("reloadGrid");  
                         $(this).dialog("close");
                    } else {
                        showMessage("Ateção", retObj.message);
                    }
                } else
                    showMessage("Cadastro de "+tituloPagina, "Houve um erro ao excluir.");
            });
    }    
    function CST_gridButtons(cellvalue, options, rowObject) { 
        var iconExcluir = '', iconEditar = ''; 
            iconEditar = '<span class="icon-container"><span class="ui-icon ui-icon-pencil" style="display: inline-block" onclick="CST_editar(' + rowObject.id + ')"></span></span>';
            iconExcluir ='<span class="icon-container"><span class="ui-icon ui-icon-trash" style="display: inline-block" onclick="CST_confirmarExclusao(' + rowObject.id +')"></span></span>';
        return iconExcluir + iconEditar ;
    }  
    function CST_configurarGridCadastro() {               
            var colunas = [  
                { name: '', label: 'A&ccedil;&otilde;es', index: '', width: 105, align: 'center', formatter: CST_gridButtons, search: false , search: false },
                { name: 'id', label: 'Id', index: 'id', hidden: true},
                { name: 'tipo', label: 'Tipo', index: 'tipo', width: 50 },
                { name: 'codigo', label: 'C&oacute;digo', index: 'codigo', width: 200 },
                { name: 'descricao', label: 'Descri&ccedil;&atilde;o', index: 'descricao', width: 500 }
            ];                   
            configurarJqGrid({
                gridId: CST_divGrid,
                pagerId: CST_divPageId,
                titulo: null,
                urlToSend: CST_url + "listarCST",
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


