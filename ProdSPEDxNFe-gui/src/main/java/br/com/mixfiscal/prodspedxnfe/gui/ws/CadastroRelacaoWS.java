/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mixfiscal.prodspedxnfe.gui.ws;

import br.com.lulusoftwares.luluframework.jquery.JQueryGridData;
import br.com.lulusoftwares.luluframework.jquery.JQueryGridParam;
import br.com.mixfiscal.prodspedxnfe.dao.ex.DAOException;
import br.com.mixfiscal.prodspedxnfe.domain.own.RelacaoProdutoFornecedor;
import br.com.mixfiscal.prodspedxnfe.services.RelacaoService;
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

/**
 * @author adrianodib
 */
@Path("/cadastroRelacaoWS")
public class CadastroRelacaoWS {
    
    @Context 
    private HttpServletRequest request;
    
    @Context 
    private ServletContext servletContext;  
    
    private final Gson gson;
    private final RelacaoService relacaoService;    
    
    public CadastroRelacaoWS() {
        gson = new  Gson();  
        relacaoService = new  RelacaoService();        
    }
    
   @POST
    @Path("/listarRelacoes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JQueryGridData<CadastroRelacaoWS.RelacaoGridItemParam> listarRelacoes(JQueryGridParam jqParam) {  
        List<RelacaoProdutoFornecedor> lista = new ArrayList<>();
        List<CadastroRelacaoWS.RelacaoGridItemParam> listaGrid = new ArrayList<>();
        
        try {
            lista = relacaoService.listar(null, jqParam.getPage(), jqParam.getRows(), jqParam.getSidx(), jqParam.getSord());
            CadastroRelacaoWS.RelacaoGridItemParam itemGrid;
            for(RelacaoProdutoFornecedor item: lista){
                
                itemGrid = new CadastroRelacaoWS.RelacaoGridItemParam();
                itemGrid.setEmpresa(item.getProduto().getEmpresa().getNome());
                itemGrid.setProduto(item.getProduto().getDescricao());
                itemGrid.setFornecedor(item.getFornecedor().getNome());
                itemGrid.setReferenciaProduto(item.getProduto().getReferenciaProduto());
                listaGrid.add(itemGrid);
            }
        } catch(ServiceException ex){
            ex.printStackTrace();
        }        
        
        JQueryGridData<CadastroRelacaoWS.RelacaoGridItemParam> data = new JQueryGridData<>(); 
        data.setRows(listaGrid);
        data.setPage(jqParam.getPage());
        data.setRecords(relacaoService.getQtdTotalRegistros().intValue()); 
        data.setRegPerPag(jqParam.getRows());     
        
        return data;
    }    
    
    public static class RelacaoGridItemParam{
        private String empresa;
        private String produto;
        private String fornecedor;
        private String referenciaProduto;
        
        public RelacaoGridItemParam(){}

        public String getEmpresa() {
            return empresa;
        }

        public void setEmpresa(String empresa) {
            this.empresa = empresa;
        }

        public String getProduto() {
            return produto;
        }

        public void setProduto(String produto) {
            this.produto = produto;
        }

        public String getFornecedor() {
            return fornecedor;
        }

        public void setFornecedor(String fornecedor) {
            this.fornecedor = fornecedor;
        }       
        
        public String getReferenciaProduto() {
            return referenciaProduto;
        }

        public void setReferenciaProduto(String referenciaProduto) {
            this.referenciaProduto = referenciaProduto;
        }       
    }    
}