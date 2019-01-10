package br.com.mixfiscal.prodspedxnfe.gui.ws;

import br.com.lulusoftwares.luluframework.jquery.JQueryAutoCompleteItem;
import br.com.lulusoftwares.luluframework.jquery.JQueryAutoCompleteParam;
import br.com.lulusoftwares.luluframework.jquery.JQueryGridData;
import br.com.lulusoftwares.luluframework.jquery.JQueryGridFilter;
import br.com.lulusoftwares.luluframework.jquery.JQueryGridParam;
import br.com.lulusoftwares.luluframework.jquery.JQueryResponse;
import br.com.lulusoftwares.luluframework.jquery.JQueryResult;
import br.com.mixfiscal.prodspedxnfe.domain.own.CST;
import br.com.mixfiscal.prodspedxnfe.domain.own.CSTDePara;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.google.gson.Gson;
import br.com.mixfiscal.prodspedxnfe.services.CSTDeParaService;
import br.com.mixfiscal.prodspedxnfe.services.CSTService;
import br.com.mixfiscal.prodspedxnfe.services.ex.ServiceException;
import java.util.ArrayList;

@Path("/cstDeParaWS")
public class CSTDeParaWS {
    @Context
    private HttpServletRequest request;
    @Context
    private ServletContext servletContext;
    
    private Gson gson;
    private CSTDeParaService cstDeParaServ;
    
    public CSTDeParaWS(){    
        gson = new  Gson();
        cstDeParaServ = new CSTDeParaService();
    }
    
    @POST
    @Path("/listarCSTDePara")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryGridData<GridCFOPDeParaItem> listarCSTDePara(JQueryGridParam jqParam) {        
        List<GridCFOPDeParaItem> lista = new ArrayList<>();
        JQueryGridFilter jqFilter = gson.fromJson(jqParam.getFilters(), JQueryGridFilter.class);
        
        try {            
            CSTDePara filtro = new CSTDePara();
            
            if (jqFilter != null && jqFilter.getRules().length > 0) {
                for (byte i = 0; i < jqFilter.getRules().length; i++) {
                    
                }
            }
            
            List<CSTDePara> listaCFOPDePara = cstDeParaServ.listar(filtro, jqParam.getPage(), jqParam.getRows(), jqParam.getSidx(), jqParam.getSord());
            listaCFOPDePara.stream().forEach((c) -> {
                GridCFOPDeParaItem item = new GridCFOPDeParaItem();
                item.setIdDe(c.getCstDe().getId());
                item.setCodigoDe(c.getCstDe().getCodigo());
                item.setDescricaoDe(c.getCstDe().getDescricao());
                item.setIdPara(c.getCstPara().getId());
                item.setCodigoPara(c.getCstPara().getCodigo());
                item.setDescricaoPara(c.getCstPara().getDescricao());
                lista.add(item);
            });
        } catch(Exception ex) {        
        }
                
        JQueryGridData<GridCFOPDeParaItem> data = new JQueryGridData<>();
        data.setPage(jqParam.getPage());
        data.setRegPerPag(jqParam.getRows());
        data.setRows(lista);
        data.setRecords(cstDeParaServ.getQtdTotalRegistros().intValue());
        
        return data;
    }
    
    @POST
    @Path("/listarCSTAutoComplete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<JQueryAutoCompleteItem> listarCFOPAutoComplete(JQueryAutoCompleteParam param) {        
        List<JQueryAutoCompleteItem> lista = new ArrayList<>();
        try {
            List<CST> listaCFOP = cstDeParaServ.listarCSTAutoComplete(param.getSearchTerm());
            listaCFOP.stream().forEach((cfop) -> {
                JQueryAutoCompleteItem item = new JQueryAutoCompleteItem();
                item.setId(cfop.getId().toString());
                item.setLabel(cfop.getCodigo() + " - " + cfop.getDescricao());
                item.setValue(cfop.getCodigo() + " - " + cfop.getDescricao());
                lista.add(item);
            });
        } catch(Exception ex) {
        
        }
        return lista;
    }
    
    @POST
    @Path("/salvarCSTDePara")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse salvarCfopDePara(CSTDeParaParam cstDeParaParam) {
        JQueryResponse resp = new JQueryResponse();
        
        try {
            CST cstDeTeste = new CST();
            cstDeTeste.setId(cstDeParaParam.getIdCstDe());
            if (verificarExistencia(cstDeTeste) != null) {
                resp.setMessage("Já existe um registro partindo do código informado");
                resp.setResult(JQueryResult.Success);
                return resp;
            }
            
            CST cfopDe = verificarCSTExiste(cstDeParaParam.getCodigoDe());
            CST cfopPara = verificarCSTExiste(cstDeParaParam.getCodigoPara());
            
            if (cfopDe != null) cstDeParaParam.setIdCstDe(cfopDe.getId());
            if (cfopPara != null) cstDeParaParam.setIdCstPara(cfopPara.getId());
            
            if (cstDeParaParam.getIdCstDe() == 0)
                cstDeParaParam.setIdCstDe(salvarNovoCST(cstDeParaParam.getCodigoDe()));
            
            if (cstDeParaParam.getIdCstPara() == 0)                
                cstDeParaParam.setIdCstPara(salvarNovoCST(cstDeParaParam.getCodigoPara()));
            
            executarAcao(cstDeParaParam, 1);
            
            resp.setMessage("De-Para salvo com sucesso.");
            resp.setResult(JQueryResult.Success);
        } catch(Exception ex) {
            resp.setMessage("Houve um erro ao salvar o De-Para. Mensagem: " + ex.getMessage());
            resp.setResult(JQueryResult.Error);            
        }
        
        return resp;
    }
    
    @POST
    @Path("/excluirCSTDePara")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryResponse excluirCfopDePara(CSTDeParaParam cfopDeParaParam) {
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
        
    private void executarAcao(CSTDeParaParam cfopDeParaParam, int acao) throws ServiceException {        
        CST cfopDe = new CST();
        CST cfopPara = new CST();

        cfopDe.setId(cfopDeParaParam.getIdCstDe());
        cfopPara.setId(cfopDeParaParam.getIdCstPara());

        CSTDePara cstDePara = new CSTDePara();
        cstDePara.setCstDe(cfopDe);
        cstDePara.setCstPara(cfopPara);
        
        switch(acao) {
            case 1: cstDeParaServ.salvar(cstDePara); break;
            case 2: cstDeParaServ.excluir(cstDePara); break;
        }
    }
    
    private int salvarNovoCST(String novoCodigo) throws ServiceException {
        CSTService cstServ = new CSTService();
        CST novoCFOP = new CST();
        novoCFOP.setCodigo(novoCodigo);
        cstServ.salvar(novoCFOP);
        return novoCFOP.getId();
    }
    
    private CSTDePara verificarExistencia(CST cfopDe) throws ServiceException {        
        CSTDePara cfopDePara = new CSTDePara();                
        cfopDePara.setCstDe(cfopDe);
        return cstDeParaServ.selecionarUm(cfopDePara);
    }
    
    private CST verificarCSTExiste(String codigo) throws ServiceException {
        CSTService cstServ = new CSTService();
        CST filtro = new CST();
        filtro.setCodigo(codigo.trim());
        return cstServ.selecionarUm(filtro);
    }
    
    // <editor-fold desc="Sub Classes" defaultstate="collapsed">
    public static class GridCFOPDeParaItem {
        private int idDe;
        private String codigoDe;
        private String descricaoDe;
        private int idPara;
        private String codigoPara;
        private String descricaoPara;        

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

        public String getDescricaoDe() {
            return descricaoDe;
        }

        public void setDescricaoDe(String descricaoDe) {
            this.descricaoDe = descricaoDe;
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

        public String getDescricaoPara() {
            return descricaoPara;
        }

        public void setDescricaoPara(String descricaoPara) {
            this.descricaoPara = descricaoPara;
        }
    }   
    
    public static class CSTDeParaParam {
        private int idCstDe;
        private String codigoDe;        
        private int idCstPara;
        private String codigoPara;        

        public int getIdCstDe() {
            return idCstDe;
        }

        public void setIdCstDe(int idCstDe) {
            this.idCstDe = idCstDe;
        }

        public String getCodigoDe() {
            return codigoDe;
        }

        public void setCodigoDe(String codigoDe) {
            this.codigoDe = codigoDe;
        }

        public int getIdCstPara() {
            return idCstPara;
        }

        public void setIdCstPara(int idCstPara) {
            this.idCstPara = idCstPara;
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
