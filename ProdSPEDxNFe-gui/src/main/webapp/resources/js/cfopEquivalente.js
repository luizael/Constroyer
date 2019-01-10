var url = contextPath + "/ws/cfopDeParaWS/";
var idTblGrid = "#tblGrid";
var idDivGridPager = "#divPagerGrid";
var idDivForm = "#divForm";
var idBtnNovo = "#btnNovo";

$(document).ready(function () {
    configurarGrid();
    $(idDivForm).dialog({
        autoOpen: false,
        title: "De-Para",
        width: 630,
        modal: true,
        buttons: {
            "Salvar": function () { salvar(); },
            "Excluir": function () { confirmarExclusao(0, 0) },
            "Cancelar": function () {
                $(this).dialog("close");
            }
        }
    });
    $(idBtnNovo).click(function () {
        limparForm(idDivForm);
        abrirForm();
    });
    autoComplete("#hdnIdCfopDe", "#txtCodigoDe", url, "listarCFOPAutoComplete", 1);
    autoComplete("#hdnIdCfopPara", "#txtCodigoPara", url, "listarCFOPAutoComplete", 1);
    setZindexJqGrideZero(idTblGrid);
});

function abrirForm() {        
    $(idDivForm).dialog("open");
}

function gridEditDelFormatter(cellvalue, options, rowObject) {
    var iconExcluir = '<span class="icon-container"><span class="ui-icon ui-icon-trash" style="display: inline-block" onclick="confirmarExclusao(' + rowObject.idDe + ', ' + rowObject.idPara + ')"></span></span>';
    var iconEditar = ""; //'<span class="icon-container"><span class="ui-icon ui-icon-pencil" style="display: inline-block" onclick="editar(' + rowObject.idDe + ', ' + rowObject.idPara + ')"></span></span>';
    return iconExcluir + " " + iconEditar;                
}

function configurarGrid() {
    var colunas = [  
        { name: '', label: '', index: '', formatter: gridEditDelFormatter, width: 40, align: "center", sortable: false, search: false },                    
        { name: 'idDe', label: '', index: '', hidden: true },
        { name: 'codigoDe', label: 'De', index: 'de.codigo', width: 60, align: 'center', sortable: false },
        { name: 'nomeDe', label: 'Nome De', index: 'de.nome', width: 200, sortable: false },
        { name: 'idPara', label: '', index: '', hidden: true, sortable: false },
        { name: 'codigoPara', label: 'Para', index: 'para.codigo', width: 60, align: 'center', sortable: false },
        { name: 'nomePara', label: 'Nome Para', index: 'para.nome', width: 200, sortable: false }
    ];

    var filterOptions = {
        stringResult: true,
        searchOnEnter: false
    };

    configurarJqGrid({
        gridId: idTblGrid,
        pagerId: idDivGridPager,
        titulo: null,
        urlToSend: url + "listarCFOPDePara",
        colunas: colunas,
        permitirOrdenacao: true,
        dados: {                    
        },
        autoWidth: false,                
        height: 300,
        width: null,
        shrinkToFit: false,
        loadonce: false,
        filterOptions: null
    });
}

function salvar() {
    var data = {                   
        idCfopDe: $("#hdnIdCfopDe").val(),
        codigoDe: $("#txtCodigoDe").val(),
        idCfopPara: $("#hdnIdCfopPara").val(),
        codigoPara: $("#txtCodigoPara").val()
    };

    executeWebMethod(url, "salvarCfopDePara",
        JSON.stringify(data),
        function (ret) {
            var retObj = ret;
            if (retObj != null && retObj.result == "Success")
                showMessage("De-Para", retObj.message, { "Ok": function () { $(idDivForm).dialog("close"); $(this).dialog("close"); } });
            else
                showMessage("De-Para", "Houve um erro ao tentar salvar o registro.", { "Ok": function () { $(idDivForm).dialog("close"); $(this).dialog("close"); } });

            $(idTblGrid).trigger("reloadGrid");                
        });
}

function excluir(idDe, idPara) {
    var data = { 
        idCfopDe: idDe,
        idCfopPara: idPara
    };

    executeWebMethod(url, "excluirCfopDePara",
        JSON.stringify(data),
        function (ret) {
            var retObj = ret;
            if (retObj != null && retObj.result == "Success") 
                showMessage("De-Para", retObj.message, { "Ok": function () { $(this).dialog("close"); } });
            else
                showMessage("Prospecto", "Houve um erro ao tentar excluir o registro.", { "Ok": function () { $(this).dialog("close"); } });

            $(idDivForm).dialog("close");
            $(idTblGrid).trigger("reloadGrid");
        });   
}

function editar(id) {            
    var data = { IdProspecto: id };

    limparForm(idDivForm);

    executeWebMethod(url, "retornarDePara",
        JSON.stringify(data),
        function (ret) {
            var retObj = ret.d;
            if (retObj != null) {
                if (retObj.Result == 0) {
                    var obj = retObj.Object;

                    $("#hdnIdProspecto").val(obj.IdProspecto);
                    $("#txtNome").val(obj.Nome);
                    $("#txtTelefone").val(obj.Telefone);
                    $("#txtEmail").val(obj.Email);
                    $("#txtNomeContato").val(obj.NomeContato);
                    $("#txtLogradouro").val(obj.Logradouro);
                    $("#txtBairro").val(obj.Bairro);
                    $("#txtCidade").val(obj.Cidade);
                    $("#ddlSistema").val(obj.IdSistema);
                    $("#txtValorPago").val(obj.ValorPago);

                    abrirForm();
                } else {
                    showMessage(Prospecto, "Houve um erro ao abrir o registro para edição. Mensagem do Servidor: " + retObj.Message);
                }
            } else
                showMessage("Funcionário", "Houve um erro ao abrir o registro para edição.");

        });
}

function confirmarExclusao(idDe, idPara) {
    showMessage("Confirmação", "Tem certeza que deseja excluir o registro?",
    {
        "Sim": function () { excluir(idDe, idPara); $(this).dialog("close"); },
        "Não": function () { $(this).dialog("close"); }
    });
}

function limparForm() {
    $(idDivForm + " input").each(function () {
        $(this).val("");
    });
}         