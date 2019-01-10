// <![CDATA[
    var tituloPagina = "Usuario";
    var Usuario_url = contextPath + "/ws/usuarioWS/"; 
    var Usuario_divIdFormulario = "#idFormularioUsuario";
    var Usuario_divGrid = "#jqGrideUsuario";
    var Usuario_divPageId = "#jqGrideUsuarioPage";
    var Usuario_idtxtHdnId = "#txtHdnIdUsuario";   
    var Usuario_txtNome = "#txtNome";
    var Usuario_txtEmail ="#txtEmail";
    var Usuario_txtUsuario ="#txtUsuario";
    var Usuario_txtSenha ="#txtSenha";  
    var Usuario_txtRptSenha ="#txtRptSenha"; 
    var Usuario_buttons =
    [
        { id: tituloPagina+"_btnSalvar", text: "Salvar", click: function () {
           Usuario_salvar();
        }},
        { id: tituloPagina+"_btnExcluir", text: "Excluir", click: function () {
            Usuario_confirmarExclusao($(Usuario_idtxtHdnId).val());
            $(Usuario_divIdFormulario).dialog("close");
        }},
        { id: tituloPagina+"_btnCancelar", text: "Cancelar", click: function () {
            $(this).dialog("close");
        }}
    ];    
    $(document).ready(function () {
       carregarForm(Usuario_divIdFormulario,"Cadastro de "+tituloPagina,600,Usuario_buttons,400);
       Usuario_configurarGridCadastro();
       setZindexJqGrideZero(Usuario_divGrid);
    });    
    function Usuario_novo(){
        limparForm(Usuario_divIdFormulario)
        AbrirFormulario(Usuario_divIdFormulario)
    }    
    function Usuario_salvar(){
        if(!validarForm(Usuario_divIdFormulario)){
            showMessage("Validação", "Preencha todos os campos", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }
      
        if(!validarCampo(Usuario_txtNome,255)){
             showMessage("Atenção", "Nome excede o tamanho máximo de 250 caracteres", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }        
        if(!validarCampo(Usuario_txtEmail,255)){
             showMessage("Atenção", "Email excede o tamanho máximo de 250 caracteres", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        } 
        if(!validarCampo(Usuario_txtUsuario,255)){
             showMessage("Atenção", "Usuario excede o tamanho máximo de 250 caracteres", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        } 
        if(!validarCampo(Usuario_txtSenha,255)){
             showMessage("Atenção", "Senha excede o tamanho máximo de 250 caracteres", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        } 
        if($(Usuario_txtSenha).val() != $(Usuario_txtRptSenha).val()){
            showMessage("Atenção", "Senha nao confere", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }
        if($(Usuario_txtSenha).val() == "" && $(Usuario_txtRptSenha).val() == ""){
            showMessage("Atenção", "Senha esta em branco", { "Ok": function () { $(this).dialog("close"); } });
            return false;
        }
        var data={
                id: $(Usuario_idtxtHdnId).val()== ""? 0:$(Usuario_idtxtHdnId).val(),
                nome: $(Usuario_txtNome).val(),
                email: $(Usuario_txtEmail).val(),
                usuario: $(Usuario_txtUsuario).val(),
                senha: $(Usuario_txtSenha).val()
        }
        executeWebMethod(Usuario_url, "salvarUsuario",
        JSON.stringify(data),
        function (ret) {
            var retObj = ret;
            if(retObj != null){
                if (retObj.result == "Success"){
                    showMessage("Salvando "+tituloPagina, retObj.message, { "Ok": function () { $(Usuario_divIdFormulario).dialog("close"); $(this).dialog("close"); } });
                    $(Usuario_divGrid).trigger("reloadGrid");
                }else{
                     showMessage("Atenção", retObj.message, { "Ok": function () { $(this).dialog("close"); } });
                }
            }else{
                showMessage("Atenção", "Houve um erro ao tentar salvar o registro."+retObj.message, { "Ok": function () { $(CFOP_divIdFormulario).dialog("close"); $(this).dialog("close"); } });
            }                            
        });
    }
    function Usuario_selecionar(id){
        var data={id:id}
        executeWebMethod(Usuario_url, "selecionarUmUsuario",
            JSON.stringify(data),
            function (ret) {
                var retObj = ret;
                if (retObj != null) {
                    if (retObj.result == "Success") {
                        if(retObj.object != null){
                            $(Usuario_idtxtHdnId).val(retObj.object.id)                       
                            $(Usuario_txtNome).val(retObj.object.nome);
                            $(Usuario_txtEmail).val(retObj.object.email);
                            $(Usuario_txtUsuario).val(retObj.object.usuario);
                            $(Usuario_txtSenha).val(retObj.object.senha);
                            $(Usuario_txtRptSenha).val("");
                            AbrirFormulario(Usuario_divIdFormulario);
                        }
                       
                    } else {
                        showMessage("Cadastro de "+tituloPagina, retObj.Message);
                    }
                } else
                    showMessage("Cadastro de "+tituloPagina, "Houve um erro ao abrir o registro para edição.");
            });
    }
    function Usuario_confirmarExclusao(id) {
        showMessage("Confirmação", "Tem certeza que deseja excluir?",
        {
            "Sim": function () { Usuario_excluir(id); $(this).dialog("close"); },
            "Não": function () { $(this).dialog("close"); }
        });
    }
    function Usuario_excluir(id){
        var data={id:id}
        executeWebMethod(Usuario_url, "excluirUsuario",
            JSON.stringify(data),
            function (ret) {
                var retObj = ret;
                if (retObj != null) {
                    if (retObj.result == "Success") {
                         $(Usuario_divGrid).trigger("reloadGrid");  
                         $(this).dialog("close");
                    } else {
                        showMessage("Ateção", retObj.message);
                    }
                } else
                    showMessage("Cadastro de "+tituloPagina, "Houve um erro ao excluir.");
            });
    }    
    function Usuario_gridButtons(cellvalue, options, rowObject) { 
        var iconExcluir = '', iconEditar = ''; 
            iconEditar = '<span class="icon-container"><span class="ui-icon ui-icon-pencil" style="display: inline-block" onclick="Usuario_selecionar(' + rowObject.id + ')"></span></span>';
            iconExcluir ='<span class="icon-container"><span class="ui-icon ui-icon-trash" style="display: inline-block" onclick=" Usuario_confirmarExclusao(' + rowObject.id +')"></span></span>';
        return iconExcluir + iconEditar ;
    }  
    function Usuario_configurarGridCadastro() {               
        var colunas = [  
            { name: '', label: 'A&ccedil;&otilde;es', index: '', width: 105, align: 'center', formatter: Usuario_gridButtons, search: false , search: false },
            { name: 'id', label: 'Id', index: 'id', hidden: true},
            { name: 'nome', label: 'Nome do Usuario', index: 'nome', width: 100 },
            { name: 'email', label: 'Email', index: 'email', width: 200 },
            { name: 'usuario', label: 'Usuario', index: 'usuario', width: 100 },
            { name: 'senha', label: 'Senha', index: 'senha', width: 450 }
        ];                   
       
        configurarJqGrid({
            gridId: Usuario_divGrid,
            pagerId: Usuario_divPageId,
            titulo: null,
            urlToSend: Usuario_url + "listarUsuario",
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


