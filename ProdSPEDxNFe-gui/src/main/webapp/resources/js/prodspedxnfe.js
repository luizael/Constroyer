function abrirNavegador(iddivnav, idTf, tp, idwnd, cmhoIni) {
    $("#" + iddivnav).html("<iframe src=\"" + contextPath + "/restrito/navegador.jsf?idtf=" + idTf + "&tp=" + tp + "&idwnd=" + idwnd + "&ci=" + cmhoIni + "\" style=\"border: 0px solid #000000\" width=\"100%\" height=\"100%\"></iframe>");
    $("#" + iddivnav).dialog("open");        
}
