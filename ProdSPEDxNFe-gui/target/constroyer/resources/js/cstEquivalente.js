var CSTDeParaVars = {
    "url": contextPath + "/ws/cstDeParaWS/",
    "tblGrid": "#tblGrid",
    "divGridPager": "#divPagerGrid",
    "divForm": "#divForm",
    "btnNovo": "#btnNovo",
    "hdnIdCstDe": "#hdnIdCstDe",
    "hdnIdCstPara": "#hdnIdCstPara",
    "txtCodigoDe": "#txtCodigoDe",
    "txtCodigoPara": "#txtCodigoPara"
};

$(document).ready(function () {
    configurarGrid();
    $(CSTDeParaVars.divForm).dialog({
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
    $(CSTDeParaVars.btnNovo).click(function () {
        limparForm(CSTDeParaVars.divForm);
        abrirForm();
    });
    autoComplete(CSTDeParaVars.hdnIdCstDe, CSTDeParaVars.txtCodigoDe, CSTDeParaVars.url, "listarCSTAutoComplete", 1);
    autoComplete(CSTDeParaVars.hdnIdCstPara, CSTDeParaVars.txtCodigoPara, CSTDeParaVars.url, "listarCSTAutoComplete", 1);
    setZindexJqGrideZero(CSTDeParaVars.tblGrid);
});

function abrirForm() {        
    $(CSTDeParaVars.divForm).dialog("open");
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
        { name: 'descricaoDe', label: 'Descri&ccedil;&atild;o De', index: 'de.descricao', width: 200, sortable: false },
        { name: 'idPara', label: '', index: '', hidden: true, sortable: false },
        { name: 'codigoPara', label: 'Para', index: 'para.codigo', width: 60, align: 'center', sortable: false },
        { name: 'descricaoPara', label: 'Descri&ccedil;&atild;o Para', index: 'para.descricao', width: 200, sortable: false }
    ];

    var filterOptions = {
        stringResult: true,
        searchOnEnter: false
    };

    configurarJqGrid({
        gridId: CSTDeParaVars.tblGrid,
        pagerId: CSTDeParaVars.divGridPager,
        titulo: null,
        urlToSend: CSTDeParaVars.url + "listarCSTDePara",
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
        idCstDe: $(CSTDeParaVars.hdnIdCstDe).val(),
        codigoDe: $(CSTDeParaVars.txtCodigoDe).val(),
        idCstPara: $(CSTDeParaVars.hdnIdCstPara).val(),
        codigoPara: $(CSTDeParaVars.txtCodigoPara).val()
    };

    executeWebMethod(CSTDeParaVars.url, "salvarCSTDePara",
        JSON.stringify(data),
        function (ret) {
            var retObj = ret;
            if (retObj != null && retObj.result == "Success")
                showMessage("De-Para", retObj.message, { "Ok": function () { $(CSTDeParaVars.divForm).dialog("close"); $(this).dialog("close"); } });
            else
                showMessage("De-Para", "Houve um erro ao tentar salvar o registro.", { "Ok": function () { $(CSTDeParaVars.divForm).dialog("close"); $(this).dialog("close"); } });

            $(CSTDeParaVars.tblGrid).trigger("reloadGrid");                
        });
}

function excluir(idDe, idPara) {
    var data = { 
        idCstDe: idDe,
        idCstPara: idPara
    };

    executeWebMethod(CSTDeParaVars.url, "excluirCSTDePara",
        JSON.stringify(data),
        function (ret) {
            var retObj = ret;
            if (retObj != null && retObj.result == "Success") 
                showMessage("De-Para", retObj.message, { "Ok": function () { $(this).dialog("close"); } });
            else
                showMessage("Prospecto", "Houve um erro ao tentar excluir o registro.", { "Ok": function () { $(this).dialog("close"); } });

            $(CSTDeParaVars.divForm).dialog("close");
            $(CSTDeParaVars.tblGrid).trigger("reloadGrid");
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
    $(CSTDeParaVars.divForm + " input").each(function () {
        $(this).val("");
    });
}    