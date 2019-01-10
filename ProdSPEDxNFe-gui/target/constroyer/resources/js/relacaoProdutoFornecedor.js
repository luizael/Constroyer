    
    //Author: Adriano Dib 06/01/2017
    
    var tituloPagina = "Relação";
    var relacao_url = contextPath + "/ws/cadastroRelacaoWS/"; 
    var relacao_divIdFormulario = "#idFormularioRelacao";
    var relacao_divGrid = "#jqGrideRelacao";
    var relacao_divPageId = "#jqGrideRelacaoPage";
    var relacao_idtxtHdnId = "#txtHdnIdRelacao";
    var relacao_txtEmpresa ="#txtEmpresa";
    var relacao_txtProduto = "#txtProduto";
    var relacao_txtFornecedor ="#txtFornecedor";
    var relacao_txtCodFornecedor ="#txtCodFornecedor";
    var relacao_hdnIdRelacaoEquivalente = "#hdnIdRelacaoEquivalente";
    
    var relacao_buttons =
    [
        { id: tituloPagina+"_btnSalvar", text: "Salvar", click: function () {
           Relacao_salvar();
        }},
        { id: tituloPagina+"_btnExcluir", text: "Excluir", click: function () {
            Relacao_confirmarExclusao($(relacao_idtxtHdnId).val());
            $(relacao_divIdFormulario).dialog("close");
        }},
        { id: tituloPagina+"_btnCancelar", text: "Cancelar", click: function () {
            $(this).dialog("close");
        }}
    ];    
    
    $(document).ready(function () {
       carregarForm(relacao_divIdFormulario, "Cadastro de " + tituloPagina, 850, relacao_buttons, 400);
       relacao_configurarGridCadastro();
       setZindexJqGrideZero(relacao_divGrid);
    });    
    
    function relacao_novo(){
        limparForm(relacao_divIdFormulario)
        AbrirFormulario(relacao_divIdFormulario)
    }
    
    function relacao_salvar(){
        if(!validarForm(relacao_divIdFormulario)){
            showMessage("Validação", "Preencha todos os campos", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }
        if(!validarCampo(relacao_txtCodigo, 4)){
            showMessage("Atenção", "Código tamanho máximo 4 caracteres", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }
        if(!validarCampo(relacao_txtNome, 255)){
            showMessage("Atenção", "Nome excede o tamanho máximo de 250 caracteres", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }        
        if(!validarCampo(relacao_txtDescricao, 8000)) {
            showMessage("Atenção", "Descrição excede o tamanho máximo de 8000 caracteres", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }
        var data={
                id: $(relacao_idtxtHdnId).val()== ""? 0:$(relacao_idtxtHdnId).val(),
                empresa: $(relacao_txtEmpresa).val(),
                produto: $(relacao_txtProduto).val(),
                fornecedor: $(relacao_txtFornecedor).val(),
                referenciaProduto: $(relacao_txtCodFornecedor).val()
        }
        executeWebMethod(relacao_url, "salvarRelacao",
        JSON.stringify(data),
        function (ret) {
            var retObj = ret;
            if(retObj != null){
                if (retObj.result == "Success"){
                    showMessage("Salvando " + tituloPagina, retObj.message, { "Ok": function () { $(relacao_divIdFormulario).dialog("close"); $(this).dialog("close"); } });
                    $(relacao_divGrid).trigger("reloadGrid");
                }else{
                     showMessage("Atenção", retObj.message, { "Ok": function () { $(this).dialog("close"); } });
                }
            }else{
                showMessage("Atenção", "Houve um erro ao tentar salvar o registro."+retObj.message, { "Ok": function () { $(relacao_divIdFormulario).dialog("close"); $(this).dialog("close"); } });
            }                            
        });
    }
    function relacao_editar(id){
        var data={id:id}
        executeWebMethod(relacao_url, "editarRelacao",
            JSON.stringify(data),
            function (ret) {
                var retObj = ret;
                if (retObj != null) {
                    if (retObj.result == "Success") {
                        $(relacao_idtxtHdnId).val(retObj.object.id)
                        $(relacao_txtCodigo).val(retObj.object.codigo);
                        $(relacao_txtNome).val(retObj.object.nome);
                        $(relacao_txtDescricao).val(retObj.object.descricao);
                        $(relacao_hdnIdRelacaoEquivalente).val(retObj.object.idRelacaoEquivalente);
                        AbrirFormulario(relacao_divIdFormulario);
                    } else {
                        showMessage("Cadastro de " + tituloPagina, retObj.Message);
                    }
                } else {
                    showMessage("Cadastro de " + tituloPagina, "Houve um erro ao abrir o registro para edição.");
                }
            });
    }
    function relacao_confirmarExclusao(id) {
        showMessage("Confirmação", "Tem certeza que deseja excluir?",
        {
            "Sim": function () { relacao_excluir(id); $(this).dialog("close"); },
            "Não": function () { $(this).dialog("close"); }
        });
    }
    function relacao_excluir(id){
        var data={id:id}
        executeWebMethod(relacao_url, "excluirRelacao",
            JSON.stringify(data),
            function (ret) {
                var retObj = ret;
                if (retObj != null) {
                    if (retObj.result == "Success") {
                         $(relacao_divGrid).trigger("reloadGrid");  
                         $(this).dialog("close");
                    } else {
                        showMessage("Atenção", retObj.message);
                    }
                } else
                    showMessage("Cadastro de " + tituloPagina, "Houve um erro ao excluir.");
            });
    }    
    function relacao_gridButtons(cellvalue, options, rowObject) { 
        var iconExcluir = '', iconEditar = ''; 
            iconEditar = '<span class="icon-container"><span class="ui-icon ui-icon-pencil" style="display: inline-block" onclick="relacao_editar(' + rowObject.id + ')"></span></span>';
            iconExcluir ='<span class="icon-container"><span class="ui-icon ui-icon-trash" style="display: inline-block" onclick=" relacao_confirmarExclusao(' + rowObject.id +')"></span></span>';
        return iconExcluir + iconEditar ;
    }  
    function relacao_configurarGridCadastro() {               
        var colunas = [  
            { name: '', label: 'A&ccedil;&otilde;es', index: '', width: 100, align: 'center', formatter: relacao_gridButtons, search: false},
            { name: 'empresa', label: 'Empresa', index: 'empresa', width: 300},
            { name: 'produto', label: 'Produto', index: 'produto', width: 455 },
            { name: 'fornecedor', label: 'Fornecedor', index: 'fornecedor', width: 300 },
            { name: 'referenciaProduto', label: 'Código do Fornecedor', index: 'referenciaProduto', width: 200 }
        ];                   
       
        configurarJqGrid({
            gridId: relacao_divGrid,
            pagerId: relacao_divPageId,
            titulo: null,
            urlToSend: relacao_url + "listarRelacoes",
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
