// <![CDATA[
    var tituloPagina = "CFOP";
    var CFOP_url = contextPath + "/ws/cadastroCFOPWS/"; 
    var CFOP_divIdFormulario = "#idFormularioCFOP";
    var CFOP_divGrid = "#jqGrideCFOP";
    var CFOP_divPageId = "#jqGrideCFOPPage";
    var CFOP_idtxtHdnId = "#txtHdnIdCFOP";
    var CFOP_txtCodigo ="#txtCodigo";
    var CFOP_txtNome = "#txtNome";
    var CFOP_txtDescricao ="#txtDescricao";
    var CFOP_hdnIdCFOPEquivalente = "#hdnIdCFOPEquivalente";
    var CFOP_buttons =
    [
        { id: tituloPagina+"_btnSalvar", text: "Salvar", click: function () {
           CFOP_salvar();
        }},
        { id: tituloPagina+"_btnExcluir", text: "Excluir", click: function () {
            CFOP_confirmarExclusao($(CFOP_idtxtHdnId).val());
            $(CFOP_divIdFormulario).dialog("close");
        }},
        { id: tituloPagina+"_btnCancelar", text: "Cancelar", click: function () {
            $(this).dialog("close");
        }}
    ];    
    $(document).ready(function () {
       carregarForm(CFOP_divIdFormulario,"Cadastro de "+tituloPagina,850,CFOP_buttons,300);
       CFOP_configurarGridCadastro();
       setZindexJqGrideZero(CFOP_divGrid);
    });    
    function CFOP_novo(){
        limparForm(CFOP_divIdFormulario)
        AbrirFormulario(CFOP_divIdFormulario)
    }    
    function CFOP_salvar(){
        if(!validarForm(CFOP_divIdFormulario)){
            showMessage("Validação", "Preencha todos os campos", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }
        if(!validarCampo(CFOP_txtCodigo,4)){
             showMessage("Atenção", "Código tamanho máximo 4 caracteres", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }
        if(!validarCampo(CFOP_txtNome,255)){
             showMessage("Atenção", "Nome excede o tamanho máximo de 250 caracteres", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }        
        if(!validarCampo(CFOP_txtDescricao,8000)) {
             showMessage("Atenção", "Descrição excede o tamanho máximo de 8000 caracteres", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }
        var data={
                id: $(CFOP_idtxtHdnId).val()== ""? 0:$(CFOP_idtxtHdnId).val(),
                codigo: $(CFOP_txtCodigo).val(),
                nome: $(CFOP_txtNome).val(),
                descricao: $(CFOP_txtDescricao).val(),
                idCFOPEquivalente: $(CFOP_hdnIdCFOPEquivalente).val()
        }
        executeWebMethod(CFOP_url, "salvarCFOP",
        JSON.stringify(data),
        function (ret) {
            var retObj = ret;
            if(retObj != null){
                if (retObj.result == "Success"){
                    showMessage("Salvando "+tituloPagina, retObj.message, { "Ok": function () { $(CFOP_divIdFormulario).dialog("close"); $(this).dialog("close"); } });
                    $(CFOP_divGrid).trigger("reloadGrid");
                }else{
                     showMessage("Atenção", retObj.message, { "Ok": function () { $(this).dialog("close"); } });
                }
            }else{
                showMessage("Atenção", "Houve um erro ao tentar salvar o registro."+retObj.message, { "Ok": function () { $(CFOP_divIdFormulario).dialog("close"); $(this).dialog("close"); } });
            }                            
        });
    }
    function CFOP_editar(id){
        var data={id:id}
        executeWebMethod(CFOP_url, "editarCFOP",
            JSON.stringify(data),
            function (ret) {
                var retObj = ret;
                if (retObj != null) {
                    if (retObj.result == "Success") {
                        $(CFOP_idtxtHdnId).val(retObj.object.id)
                        $(CFOP_txtCodigo).val(retObj.object.codigo);
                        $(CFOP_txtNome).val(retObj.object.nome);
                        $(CFOP_txtDescricao).val(retObj.object.descricao);
                        $(CFOP_hdnIdCFOPEquivalente).val(retObj.object.idCFOPEquivalente);
                        AbrirFormulario(CFOP_divIdFormulario);
                    } else {
                        showMessage("Cadastro de "+tituloPagina, retObj.Message);
                    }
                } else
                    showMessage("Cadastro de "+tituloPagina, "Houve um erro ao abrir o registro para edição.");
            });
    }
    function CFOP_confirmarExclusao(id) {
        showMessage("Confirmação", "Tem certeza que deseja excluir?",
        {
            "Sim": function () { CFOP_excluir(id); $(this).dialog("close"); },
            "Não": function () { $(this).dialog("close"); }
        });
    }
    function CFOP_excluir(id){
        var data={id:id}
        executeWebMethod(CFOP_url, "excluirCFOP",
            JSON.stringify(data),
            function (ret) {
                var retObj = ret;
                if (retObj != null) {
                    if (retObj.result == "Success") {
                         $(CFOP_divGrid).trigger("reloadGrid");  
                         $(this).dialog("close");
                    } else {
                        showMessage("Ateção", retObj.message);
                    }
                } else
                    showMessage("Cadastro de "+tituloPagina, "Houve um erro ao excluir.");
            });
    }    
    function CFOP_gridButtons(cellvalue, options, rowObject) { 
        var iconExcluir = '', iconEditar = ''; 
            iconEditar = '<span class="icon-container"><span class="ui-icon ui-icon-pencil" style="display: inline-block" onclick="CFOP_editar(' + rowObject.id + ')"></span></span>';
            iconExcluir ='<span class="icon-container"><span class="ui-icon ui-icon-trash" style="display: inline-block" onclick=" CFOP_confirmarExclusao(' + rowObject.id +')"></span></span>';
        return iconExcluir + iconEditar ;
    }  
    function CFOP_configurarGridCadastro() {               
        var colunas = [  
            { name: '', label: 'A&ccedil;&otilde;es', index: '', width: 105, align: 'center', formatter: CFOP_gridButtons, search: false , search: false },
            { name: 'id', label: 'Id', index: 'id', hidden: true},
            { name: 'codigo', label: 'C&oacute;digo', index: 'codigo', width: 200 },
            { name: 'nome', label: 'Nome do CFOP', index: 'codigo', width: 100 },
            { name: 'descricao', label: 'Descri&ccedil;&atilde;o', index: 'descricao', width: 500 }
        ];                   
       
        configurarJqGrid({
            gridId: CFOP_divGrid,
            pagerId: CFOP_divPageId,
            titulo: null,
            urlToSend: CFOP_url + "listarCFOP",
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

