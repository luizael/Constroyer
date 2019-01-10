var ID_DIV_DIALOG = "#divDialog";
var ID_DIV_LOADING = "#loading";
var ID_DIV_LOADING_NCLOSE = "#loadingNoClose";
var ID_DIV_MESSAGE = "#divMessage";
var IS_LOADING_DIV_VISIBLE = false;
var AJAX_LOADING_MESSAGE_DELAY = 2000;
var AJAX_IS_AUTOCOMPLETE = false;
var indexVars = {
                divBarraProgresso: "#divBarraProgresso",
                barraProgresso: "#barraProgresso",
                labelProgresso: "#labelProgesso",
                descProgesso: "#descProgesso",
                barraProgressoBtnOk: "#btnBarraProgressoOk",
                verificarProgresso:false,
                url:""
            };
$(document).ready(function () {
    /*
    $(ID_DIV_LOADING).dialog({
        autoOpen: false,
        title: "Aguarde...",
        width: 300,
        height: 170,
        resizable: false,
        modal: true,
        beforeClose: function (event, ui) {
            if (IS_LOADING_DIV_VISIBLE) {
                showMessage("Aguarde", "Desculpe, espere o processamento.", { "Ok": function () { $(this).dialog("close"); } }, ID_DIV_LOADING_NCLOSE);
                return false;
            } else {
                return true;
            }
        }
    }); */ 
    $(ID_DIV_LOADING).hide();
    $(ID_DIV_LOADING_NCLOSE).dialog({ autoOpen: false });    
    $("input[type=submit], input[type=button], input[type=reset]").button();    
});

$(document).ajaxStart(function () { 
    if (!AJAX_IS_AUTOCOMPLETE)
        setTimeout(showLoading, AJAX_LOADING_MESSAGE_DELAY);
});

$(document).ajaxStop(function () {
    IS_LOADING_DIV_VISIBLE = false;
    //$(ID_DIV_LOADING).dialog("close");
    $(ID_DIV_LOADING).hide();
    $(ID_DIV_LOADING_NCLOSE).dialog("close");
});

function showLoading() {
    if ($.active > 0) {
        //$(ID_DIV_LOADING).dialog("open");
        $(ID_DIV_LOADING).show();
        IS_LOADING_DIV_VISIBLE = true;
    }
}

function configurarJqGrid(opcoes) {
    var gridId = opcoes.gridId,
        pagerId = opcoes.pagerId,
        titulo = opcoes.titulo,
        urlToSend = opcoes.urlToSend,
        colunas = opcoes.colunas,
        permitirOrdenacao = opcoes.permitirOrdenacao,
        dados = opcoes.dados,
        filterOptions = opcoes.filterOptions,
        autoWidth = opcoes.autoWidth,
        height = opcoes.height,
        width = opcoes.width,
        events = opcoes.events,
        loadonce = opcoes.loadonce,
        shrinkToFit = opcoes.shrinkToFit,
        gridView = opcoes.gridView,
        cellEdit = opcoes.cellEdit,
        cellsubmit = opcoes.cellsubmit,
        cellurl = opcoes.cellurl;
        
    $(gridId).jqGrid({
        url: urlToSend,
        datatype: "json",
        mtype: "POST",
        ajaxGridOptions: { 
            contentType: 'application/json; charset=utf-8' 
            
        },
        jsonReader: { repeatitems: false, root: "rows", page: "page", total: "total", records: "records" },
        serializeGridData: function (data) {
       
            var propertyName, propertyValue, dataToSend = {};

            for (propertyName in data) {
                if (data.hasOwnProperty(propertyName)) {
                    propertyValue = data[propertyName];
                    if ($.isFunction(propertyValue)) {
                        dataToSend[propertyName] = propertyValue();
                    } else {
                        dataToSend[propertyName] = propertyValue;
                    }
                }
            }

            return JSON.stringify(dataToSend);
        },
        postData: dados,
        colModel: colunas,
        rowList: [5, 10, 20, 50, 100],
        sortable: permitirOrdenacao,
        loadonce: loadonce,
        pager: pagerId,        
        caption: titulo,
        height: height,
        width: width,
        autowidth: autoWidth,
        shrinkToFit: shrinkToFit,
        gridview: gridView,
        cellEdit: cellEdit,
        cellsubmit: cellsubmit,
        cellurl: cellurl,
        altRows: true,
        viewrecords: true,        
        footerrow: false,
        userDataOnFooter: true,        
        alternateRowBackground: true,
        ignoreCase: true,        
        loadBeforeSend: function (request, settings) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            if (token != null && token != undefined && token != "")
                request.setRequestHeader(header, token);
        },
        loadComplete: (events != null && events["loadComplete"] != null ? events["loadComplete"] : null),
        onSelectRow: (events != null && events["onSelectRow"] != null ? events["onSelectRow"] : null),
        beforeSelectRow: (events != null && events["beforeSelectRow"] != null ? events["beforeSelectRow"] : null),
        gridComplete: (events != null && events["gridComplete"] != null ? events["gridComplete"] : null),
        ondblClickRow: (events != null && events["ondblClickRow"] != null ? events["ondblClickRow"] : null),
        onCellSelect: (events != null && events["onCellSelect"] != null ? events["onCellSelect"] : null)
    });
   
    jQuery(gridId).jqGrid('navGrid', pagerId, { del: false, add: false, edit: false, search: false, refresh: false });
    /*
    jQuery(gridId).jqGrid('navButtonAdd', pagerId, {
        caption: "Exportar Excel",
        buttonicon: "ui-icon-disk",
        onClickButton: function () {
            try {
                var postData = $(gridId).jqGrid("getGridParam", "postData");
                postData.colModel = GridColumns(gridId);
                $(gridId).jqGrid('excelExport', { tag: 'excel', url: $(gridId).jqGrid("getGridParam", "url") });
                //GridExportExcel(gridSelector, opcoes.exportFileName);
            } catch (e) {
                alert("Erro ao exportar." + e);
            }
        }
    });*/
  
    if (filterOptions != null)
        jQuery(gridId).jqGrid('filterToolbar', filterOptions);
}

function executeWebMethod(url, metodoNome, data, funcSucess, funcComplete, funcError, vAsinc, showMsg) {
   
    if (showMsg == undefined) showMsg = false;
    if (vAsinc == undefined) vAsinc = true;
    
    if (url[url.length - 1] != "/")
        url += "/";

    $.ajax({        
        type: "POST",
        url: url + metodoNome,        
        data: data,
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            if (funcSucess != null)
                funcSucess(response);
        },
        error: function (response, status, erro) {
            if (funcError != null)
                funcError(response, status, erro);
            else {
                var msg = null;
                try {                  
                    // Evita que a mensagem "There was an error..." aparece para o usuário quando ocorrer um erro. 
                    msg = JSON.parse(response.responseText); 
                    try{
                        msg = 'Houve um erro inesperado na aplicação. Por favor, contactar o administrador do sistema.\n Mensagem do Sistema: msg[' + msg.Message + '] status ['+ status +'] err [' + erro + ']'; 
                    }
                    catch(e){
                       msg = 'exception ['+ e +'] status ['+ status +'] err [ '+ erro + ']';
                    }
                }
                catch (e) {
                    msg = 'exception ['+ e + '] status['+ status +'] erro ['+ erro +']';
                }
                if(showMsg){
                    showMessage("Erro", msg,{
                        "Ok": function () { $(this).dialog("close"); }
                    });
                }
            }
        },
        complete: function () {
            if (funcComplete != null)
                funcComplete();
        },
        async: vAsinc,
        beforeSend: function (request) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            if (token != null && token != undefined && token != "")
                request.setRequestHeader(header, token);
        }
    });
}

function showMessage(titulo, msg, botoes, idDialog, params, vEvents) {
    var dialog = null;

    // se não passr um div, pega o div da master page
    if (idDialog == undefined) {
        idDialog = "#dialog";

        if ($(idDialog).length == 0) {
            if (parent.dialog !== undefined)
                idDialog = $(parent.dialog);
            else {
                $("body").append('<div style="display: none; width: 300px; height: 300px;" id="dialog"></div>');
                idDialog = "#dialog";
            }
        }
    }

    if (params == undefined)
        params = { width: "auto", height: "auto", modal: true };
    
        //params = { width: "300", height: "170   ", modal: true };

    if ((typeof idDialog) == "string")
        dialog = $(idDialog);
    else
        dialog = idDialog;

    dialog.html(msg);
    dialog.dialog({
        title: titulo,
        width: params["width"],
        height: params["height"],
        modal: params["modal"],
        buttons: botoes,
        close: (vEvents != null && vEvents["close"] != null ? vEvents["close"] : null)
    });
    dialog.dialog("open");
}

function gridButtons(cellvalue, options, rowObject) {
    var iconExcluir = '<span class="icon-container"><span class="ui-icon ui-icon-trash" style="display: inline-block" onclick="confirmarExclusao(' + rowObject.IdEmpresa + ')"></span></span>';
    var iconEditar = '<span class="icon-container"><span class="ui-icon ui-icon-pencil" style="display: inline-block" onclick="editar(' + rowObject.IdEmpresa + ')"></span></span>';
    return iconExcluir + iconEditar;
}

function checkBoxGrid(cellvalue, option, rowsObject){
    var ret = '<checkbox ';
    
    if (cellvalue == '1') 
    {
        ret = "checkbox";
    } 
    else 
    {
        ret = "checkbox";
    }
    
    return ret;
}

function limparForm(idDivForm) {
    $(idDivForm + " input[type!='submit'], textarea, select").each(function () {
        $(this).val("");
    });
}

function replaceAll(string, token, newToken) {
    while (string.indexOf(token) != -1) {
        string = string.replace(token, newToken);
    };
    return string;
}

function habilitarCampos(habilitar,array){
    if(habilitar == true){
        for(var i = 0; i < array.length; i++){
            $(array[i]).removeAttr("disabled");
        }
    }
    else
    {
            for(var i = 0; i < array.length; i++){
            $(array[i]).attr("disabled","disabled");
        }
    }
}

function autoComplete(idValueField, idTextField, url, method, minChars, successFunc, responseFunc) {
    $(idTextField).autocomplete({
        source: function (request, response) {
            AJAX_IS_AUTOCOMPLETE = true;

            var data = { searchTerm: request.term };

            executeWebMethod(url, method,
                JSON.stringify(data),
                function (retObj) {
                    response($.map(retObj, function (item) {
                        var i = item; //$.parseJSON(item);
                        return {
                            label: i.label,
                            value: i.value,
                            id: i.id
                        };
                    }));
                });

            AJAX_IS_AUTOCOMPLETE = false;
        },
        minLength: (minChars == 0 ? 3 : minChars),
        select: (successFunc != "undefined" && successFunc != null ? 
                    successFunc :
                    function (event, ui) {
                        $(idValueField).val(ui.item.id);
                        $(idTextField).val(ui.item.label);
                    }
        ),
        open: function (event, ui) {
            $(".ui-autocomplete").css("z-index", 1000);
        },
        error: function () {
            alert("Erro no Autocomplete de " + idTextField);
        },
        response: (responseFunc != "undefined" && responseFunc != null ? responseFunc : null)
    });
}

function AbrirFormulario(div)
{
    $(div).dialog("open");    
}
function carregarForm(idForm, tituloForm, width, botoes, height,functionClose) {
    $(idForm).dialog({            
        appendTo: "Form",
        autoOpen: false,
        title: tituloForm,
        modal: true,
        width: width,
        height:height,
        buttons: botoes,
        zIndex: 999999,
        close: functionClose
    });
};    
function converterParaDataISO(txtDate) {
    if (txtDate != "") {
        var datePart = txtDate.split("/");
        return datePart[2] + "-" + datePart[1] + "-" + datePart[0];
    }
    return "";
}

function formatarDataJSON(stringDate, format) {
    if (stringDate == null || stringDate == "" || stringDate == undefined)
        return;

    var date = new Date(parseInt(stringDate.replace("/Date(", "").replace(")/", ""), 10));
    if (format == undefined)
        return formatarData(date);
    else return date;
}

function formatarData(dateObject) {
    if (dateObject == null) return null;
    dateObject = NN(dateObject);
    var dia = "0" + dateObject.getDate();
    var mes = "0" + (dateObject.getMonth() + 1);
    var ano = dateObject.getFullYear();

    dia = dia.substr(dia.length - 2, 2);
    mes = mes.substr(mes.length - 2, 2);

    return dia + "/" + mes + "/" + ano;
}

// Not Null - checa se o valor do objeto é null ou undefined. Se for, seta igual a _default (''), senão retorna o valor
function NN(value, _default) {
    if (_default == null || _default == undefined)
        _default = '';

    // checa se é um controle
    if (value != null && value.length > 0 && value[0] == '#')
        value = $(value).val();

    if (value == undefined)
        return _default;
    else
        return value;
}

function validarCampos(idDivForm) {
    var valido = true;
    var msg = "Os seguintes campos são obrigatórios:<br/><ul>";

    $(idDivForm + " .requerido").each(function () {
        if ($(this).val() == "") {
            dv = $(this).data("dadosValidacao");
            msg += "<li>" + dv.label + "</li>";
            valido = false;
        }
    });
    msg += "</ul>";

    if (!valido) {
        showMessage("Erro de Validação", msg, { "Ok": function () { $(this).dialog("close"); } }, null, { width: 400, height: 300 });
    }

    return valido;
}

var validarForm = function (div) {
    var retorno = true;
    $(div + " input[type='text'],textarea").each(function () {
        if($(this).val()==""){
          $(this).css("border","1px solid #ffb54c");
          retorno = false;
        }else{$(this).css("border","1px solid #a9a9a9");}
    });
    return retorno;
}
var validarCampo = function(campo,maximo){
    if($(campo).val().length > maximo){
        $(campo).css("border","1px solid #ffb54c");
        return false;
    }else{
        return true;
    }
}
function getQueryStringParam(sParam) {
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam)
            return sParameterName[1];
    }
    return null;
}
function setZindexJqGrideZero(idGride){
    var id = idGride.split("#");
    $("#gbox_"+id[1]).css("z-index","0");
}

var removerDiretorioRaiz = function(path){
                var paths =  path.split("\\");
                var result = "";
                for(i = 0; i < paths.length; i ++)
                {
                    if(i > 1){
                        result += "\\" + paths[i]
                    }
                }
                return result;
            }

/**
 * Alias para functions reutiliadas na APP.
 */
var Utils = {};
Utils.execWebMethod = function(url, metodoNome, data, funcSucess, funcComplete, funcError, vAsinc){
	executeWebMethod(url, metodoNome, data, funcSucess, funcComplete, funcError, vAsinc);
}
function setTitulo(progresso,total){
    if(loadTitulo === "")
        loadTitulo = document.title;

    document.title = Math.round(progresso) +"/"+total ;
}
//executeWebMethod(url, metodoNome, data, funcSucess, funcComplete, funcError, vAsinc, showMsg)
function checarProgresso() {    
    if (indexVars.verificarProgresso) {
        var data = {};
        executeWebMethod(
            indexVars.url, 
            "checarProgresso",
            JSON.stringify(data),
            function (ret) {                        
                if (ret.result === "Success") {
                    retObj = ret.object;                                
                    var per = (retObj.atual / retObj.total) * 100;
                    $(indexVars.barraProgresso).progressbar("value", per);
                    $(indexVars.labelProgresso).html(retObj.atual + " / " + retObj.total);
                    $(indexVars.descProgesso).html(retObj.descricao);
                    setTitulo(retObj.atual,retObj.total);                              
                }                        
            },
            function (e){
                //checarProgresso();
            },
            undefined
            ,
            false
        );
        setTimeout(checarProgresso, 500);
    }                
}
function checarProgressoOLD(url,verificarProgresso,ob) {
   
    if (verificarProgresso) {
        var data = {};
        executeWebMethod(
            url, 
            "checarProgresso",
            JSON.stringify(data),
            function (ret) {                        
                if (ret.result === "Success") {
                    retObj = ret.object;                                
                    var per = (retObj.atual / retObj.total) * 100;
                    $(ob.barraProgresso).progressbar("value", per);
                    $(ob.labelProgresso).html(retObj.atual + " / " + retObj.total);
                    $(ob.descProgesso).html(retObj.descricao);
                   
                    console.log("---->>>>>"+per + "%");
                    setTitulo(retObj.atual,retObj.total);                              
                }                        
            });
        setTimeout(checarProgresso(url,verificarProgresso,ob), 500);
    }                
}


