package br.com.mixfiscal.prodspedxnfe.gui.ws;

import br.com.lulusoftwares.luluframework.jquery.JQueryAutoCompleteItem;
import br.com.lulusoftwares.luluframework.jquery.JQueryAutoCompleteParam;
import br.com.lulusoftwares.luluframework.jquery.JQueryGridData;
import br.com.lulusoftwares.luluframework.jquery.JQueryGridFilter;
import br.com.lulusoftwares.luluframework.jquery.JQueryGridParam;
import br.com.lulusoftwares.luluframework.jquery.JQueryResponse;
import br.com.lulusoftwares.luluframework.jquery.JQueryResult;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOP;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOPDePara;
import br.com.mixfiscal.prodspedxnfe.services.CFOPDeParaService;
import br.com.mixfiscal.prodspedxnfe.services.CFOPService;
import br.com.mixfiscal.prodspedxnfe.services.ex.ServiceException;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/cfopDeParaWS")
public class CFOPDeParaWS {
    @Context
    private HttpServletRequest request;
    @Context
    private ServletContext servletContext;    
    private Gson gson;
    private CFOPDeParaService cfopDeParaServ;
        
    public CFOPDeParaWS() {
        gson = new Gson();
        cfopDeParaServ = new CFOPDeParaService();
    }
    
    @POST
    @Path("/listarCFOPDePara")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryGridData<GridCFOPDeParaItem> listarCFOPDePara(JQueryGridParam jqParam) {        
        List<GridCFOPDeParaItem> lista = new ArrayList<>();
        JQueryGridFilter jqFilter = gson.fromJson(jqParam.getFilters(), JQueryGridFilter.class);
        
        try {            
            CFOPDePara filtro = new CFOPDePara();
            
            if (jqFilter != null && jqFilter.getRules().length > 0) {
                for (byte i = 0; i < jqFilter.getRules().length; i++) {
                    if (jqFilter.getRules()[i].getField().equals("de.codigo") || jqFilter.getRules()[i].getField().equals("de.nome")) {
                        CFOP filtroCfopDe = new CFOP();
                        filtro.setCfopDe(filtroCfopDe);
                        if (jqFilter.getRules()[i].getField().equals("de.codigo")) filtro.getCfopDe().setCodigo(jqFilter.getRules()[i].getData());
                        if (jqFilter.getRules()[i].getField().equals("de.nome")) filtro.getCfopDe().setNome(jqFilter.getRules()[i].getData());
                    }
                    if (jqFilter.getRules()[i].getField().equals("para.codigo") || jqFilter.getRules()[i].getField().equals("para.nome")) {
                        CFOP filtroCfopPara = new CFOP();            
                        filtro.setCfopDe(filtroCfopPara);
                        if (jqFilter.getRules()[i].getField().equals("para.codigo")) filtro.getCfopPara().setCodigo(jqFilter.getRules()[i].getData());
                        if (jqFilter.getRules()[i].getField().equals("para.nome")) filtro.getCfopPara().setNome(jqFilter.getRules()[i].getData());
                    }
                }
            }
            
            List<CFOPDePara> listaCFOPDePara = cfopDeParaServ.listar(filtro, jqParam.getPage(), jqParam.getRows(), jqParam.getSidx(), jqParam.getSord());
            listaCFOPDePara.stream().forEach((c) -> {
                GridCFOPDeParaItem item = new GridCFOPDeParaItem();
                item.setIdDe(c.getCfopDe().getId());
                item.setCodigoDe(c.getCfopDe().getCodigo());
                item.setNomeDe(c.getCfopDe().getNome());
                item.setIdPara(c.getCfopPara().getId());
                item.setCodigoPara(c.getCfopPara().getCodigo());
                item.setNomePara(c.getCfopPara().getNome());
                lista.add(item);
            });
        } catch(Exception ex) {        
        }
                
        JQueryGridData<GridCFOPDeParaItem> data = new JQueryGridData<>();
        data.setPage(jqParam.getPage());
        data.setRegPerPag(jqParam.getRows());
        data.setRows(lista);
        data.setRecords(cfopDeParaServ.getQtdTotalRegistros().intValue());
        
        return data;
    }
    
    @POST
    @Path("/listarCFOPAutoComplete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<JQueryAutoCompleteItem> listarCFOPAutoComplete(JQueryAutoCompleteParam param) {        
        List<JQueryAutoCompleteItem> lista = new ArrayList<>();
        try {
            List<CFOP> listaCFOP = cfopDeParaServ.listarCFOPAutoComplete(param.getSearchTerm());
            listaCFOP.stream().forEach((cfop) -> {
                JQueryAutoCompleteItem item = new JQueryAutoCompleteItem();
                item.setId(cfop.getId().toString());
                item.setLabel(cfop.getCodigo() + " - " + cfop.getNome());
                item.setValue(cfop.getCodigo() + " - " + cfop.getNome());
                lista.add(item);
            });
        } catch(Exception ex) {
        
        }
        return lista;
    }
    
    @POST
    @Path("/salvarCfopDePara")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse salvarCfopDePara(CFOPDeParaParam cfopDeParaParam) {
        JQueryResponse resp = new JQueryResponse();
        
        try {
            CFOP cfopDeTeste = new CFOP();
            if((cfopDeParaParam.getIdCfopDe() == 0) ||  (cfopDeParaParam.getIdCfopPara() == 0)){
                resp.setMessage("CFOP inexistente ");
                resp.setResult(JQueryResult.Success);
                return resp;
            }
            cfopDeTeste.setId(cfopDeParaParam.getIdCfopDe());
            if (verificarExistencia(cfopDeTeste) != null) {
                resp.setMessage("Já existe um registro partindo do código informado");
                resp.setResult(JQueryResult.Success);
                return resp;
            }
            
            CFOP cfopDe = verificarCFOPExiste(cfopDeParaParam.getCodigoDe());
            CFOP cfopPara = verificarCFOPExiste(cfopDeParaParam.getCodigoPara());
            
            if (cfopDe != null) cfopDeParaParam.setIdCfopDe(cfopDe.getId());
            if (cfopPara != null) cfopDeParaParam.setIdCfopPara(cfopPara.getId());
            
            if (cfopDeParaParam.getIdCfopDe() == 0)
                cfopDeParaParam.setIdCfopDe(salvarNovoCFOP(cfopDeParaParam.getCodigoDe()));
            
            if (cfopDeParaParam.getIdCfopPara() == 0)                
                cfopDeParaParam.setIdCfopPara(salvarNovoCFOP(cfopDeParaParam.getCodigoPara()));
            
            executarAcao(cfopDeParaParam, 1);
            
            resp.setMessage("De-Para salvo com sucesso.");
            resp.setResult(JQueryResult.Success);
        } catch(Exception ex) {
            resp.setMessage("Houve um erro ao salvar o De-Para. Mensagem: " + ex.getMessage());
            resp.setResult(JQueryResult.Error);            
        }
        
        return resp;
    }
    
    @POST
    @Path("/excluirCfopDePara")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse excluirCfopDePara(CFOPDeParaParam cfopDeParaParam) {
        JQueryResponse resp = new JQueryResponse();
        
        try {
            executarAcao(cfopDeParaParam, 2);            
            resp.setMessage("De-Para excluído com sucesso.");
            resp.setResult(JQueryResult.Success);
        } catch(Exception ex) {
            resp.setMessage("Houve um erro ao excluir o De-Para. Mensagem: " + ex.getMessage());
            resp.setResult(JQueryResult.Error);            
        }
        
        return resp;
    }
        
    private void executarAcao(CFOPDeParaParam cfopDeParaParam, int acao) throws ServiceException {        
        CFOP cfopDe = new CFOP();
        CFOP cfopPara = new CFOP();

        cfopDe.setId(cfopDeParaParam.getIdCfopDe());
        cfopPara.setId(cfopDeParaParam.getIdCfopPara());

        CFOPDePara cfopDePara = new CFOPDePara();
        cfopDePara.setCfopDe(cfopDe);
        cfopDePara.setCfopPara(cfopPara);
        
        switch(acao) {
            case 1: cfopDeParaServ.salvar(cfopDePara); break;
            case 2: cfopDeParaServ.excluir(cfopDePara); break;
        }
    }
    
    private int salvarNovoCFOP(String novoCodigo) throws ServiceException {
        CFOPService cfopServ = new CFOPService();
        CFOP novoCFOP = new CFOP();
        novoCFOP.setCodigo(novoCodigo);
        cfopServ.salvar(novoCFOP);
        return novoCFOP.getId();
    }
    
    private CFOPDePara verificarExistencia(CFOP cfopDe) throws ServiceException {        
        CFOPDePara cfopDePara = new CFOPDePara();                
        cfopDePara.setCfopDe(cfopDe);
        return cfopDeParaServ.selecionarUm(cfopDePara);
    }
    
    private CFOP verificarCFOPExiste(String codigo) throws ServiceException {
        CFOPService cfopServ = new CFOPService();
        CFOP filtro = new CFOP();
        filtro.setCodigo(codigo.trim());
        return cfopServ.selecionarUm(filtro);
    }
    
    // <editor-fold desc="Sub Classes" defaultstate="collapsed">
    public static class GridCFOPDeParaItem {
        private int idDe;
        private String codigoDe;
        private String nomeDe;
        private int idPara;
        private String codigoPara;
        private String nomePara;        

        public GridCFOPDeParaItem() {}
        
        public int getIdDe() {
            return idDe;
        }

        public void setIdDe(int idDe) {
            this.idDe = idDe;
        }

        public String getCodigoDe() {
            return codigoDe;
        }

        public void setCodigoDe(String codigoDe) {
            this.codigoDe = codigoDe;
        }

        public String getNomeDe() {
            return nomeDe;
        }

        public void setNomeDe(String nomeDe) {
            this.nomeDe = nomeDe;
        }

        public int getIdPara() {
            return idPara;
        }

        public void setIdPara(int idPara) {
            this.idPara = idPara;
        }

        public String getCodigoPara() {
            return codigoPara;
        }

        public void setCodigoPara(String codigoPara) {
            this.codigoPara = codigoPara;
        }

        public String getNomePara() {
            return nomePara;
        }

        public void setNomePara(String nomePara) {
            this.nomePara = nomePara;
        }
    }
    
    public static class CFOPDeParaParam {
        private int idCfopDe;
        private String codigoDe;
        private int idCfopPara;
        private String codigoPara;

        public CFOPDeParaParam() {}

        public int getIdCfopDe() {
            return idCfopDe;
        }

        public void setIdCfopDe(int idCfopDe) {
            this.idCfopDe = idCfopDe;
        }

        public String getCodigoDe() {
            return codigoDe;
        }

        public void setCodigoDe(String codigoDe) {
            this.codigoDe = codigoDe;
        }

        public int getIdCfopPara() {
            return idCfopPara;
        }

        public void setIdCfopPara(int idCfopPara) {
            this.idCfopPara = idCfopPara;
        }

        public String getCodigoPara() {
            return codigoPara;
        }

        public void setCodigoPara(String codigoPara) {
            this.codigoPara = codigoPara;
        }
    }
    // </editor-fold>
}
