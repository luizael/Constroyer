
package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributaria;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClassificacaoTributariaDAO extends SuperDAO<ClassificacaoTributaria> {
    private final Logger log = LogManager.getLogger(ClassificacaoTributariaDAO.class);
    
    public ClassificacaoTributariaDAO(){
        super(ClassificacaoTributaria.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
    
    public ClassificacaoTributariaDAO(Session sessao){
        super(ClassificacaoTributaria.class, sessao);
        //log = LogManager.getLogger(ClassificacaoTributariaDAO.class);
    }
    
    @Override
    protected void addFiltros(ClassificacaoTributaria obj, Criteria ctr) {
              
        if(obj == null) return;
        if(obj.getId() != null) ctr.add(Restrictions.eq("id", obj.getId()));
        //if(!StringUtil.isNullOrEmpty(obj.getStatus()))ctr.add(Restrictions.like("status", "%" + obj.getStatus() + "%"));
        //if(!StringUtil.isNullOrEmpty(obj.getDataResposta()))ctr.add(Restrictions.like("data_resposta", "%" + obj.getDataResposta() + "%"));
        //if(!StringUtil.isNullOrEmpty(obj.getCnpjTrib()))ctr.add(Restrictions.like("cnpjTrib", "%" + obj.getCnpjTrib() + "%"));   
    }
    
    public List<ClassificacaoTributaria> listarTudo(String cnpjCliente) {   
        List lista = new ArrayList();
        StringBuilder hql = new StringBuilder();
        hql.append("select classTrib ");
        hql.append("from ClassificacaoTributaria classTrib ");
        hql.append("join classTrib.cliente cli ");
        hql.append("where cli.cnpj = :cnpjTp");       
        
        try {            
            Query query = this.getSessao().createQuery(hql.toString());
            query.setParameter("cnpjTp", cnpjCliente);            
            lista = query.list();
        } catch (Exception e) {                                    
            log.error(e.getMessage(), e);
            throw e;
        }
        
        return lista;
    }    
    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void excluirCustom(String cnpj){
        StringBuilder hqlIcmsEntrada = new StringBuilder();
        hqlIcmsEntrada.append(" delete from ClassificacaoTributaria where cnpjTrib = :cnpj ");
        log.info("Excluindo ClassificacaoTributaria do CNPJ: " + cnpj);
        try{
            Query queryIcmsentrada = this.getSessao().createQuery(hqlIcmsEntrada.toString());
            queryIcmsentrada.setParameter("cnpj", cnpj);
            queryIcmsentrada.executeUpdate();
        }catch(Exception e){
            log.error(e.getMessage(), e);
        }    
    }
    
    public ClassificacaoTributaria selecionarPorCNPJCliente(String cnpjCliente) {
        StringBuilder hql = new StringBuilder();
        
        hql.append("select ct ");
        hql.append("from ClassificacaoTributaria ct ");
        hql.append("join ct.cliente cli ");        
        hql.append("where cli.cnpj = :cnpjCliente ");
        
        try {
            Query query = this.getSessao().createQuery(hql.toString());
            query.setParameter("cnpjCliente", cnpjCliente);
            return (ClassificacaoTributaria)query.uniqueResult();
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }        
    }
    
    public ClassificacaoTributaria selecionarPorCNPJClienteECodigoProduto(String cnpjCliente, String codigoProduto) {
        StringBuilder hql = new StringBuilder();
        
        hql.append("select ct ");
        hql.append("from ClassificacaoTributaria ct ");
        hql.append("join ct.cliente cli ");   
        hql.append("join ct.produto p ");
        hql.append("where cli.cnpj = :cnpjCliente ");
        hql.append("and p.codigoProduto = :codigoProduto ");
        
        try {
            Query query = this.getSessao().createQuery(hql.toString());
            query.setParameter("cnpjCliente", cnpjCliente);
            query.setParameter("codigoProduto", codigoProduto);
            return (ClassificacaoTributaria)query.uniqueResult();
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }        
    }
}
