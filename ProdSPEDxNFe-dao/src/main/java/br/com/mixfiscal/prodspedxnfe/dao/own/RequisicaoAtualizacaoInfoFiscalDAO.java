
package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.enums.EStatusAtualizacaoInfoFiscal;
import br.com.mixfiscal.prodspedxnfe.domain.own.RequisicaoAtualizacaoInfoFiscal;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class RequisicaoAtualizacaoInfoFiscalDAO extends SuperDAO<RequisicaoAtualizacaoInfoFiscal>{
    private final Logger log = LogManager.getLogger(RequisicaoAtualizacaoInfoFiscalDAO.class);
    
    public RequisicaoAtualizacaoInfoFiscalDAO(){
        super(RequisicaoAtualizacaoInfoFiscal.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
    
    public RequisicaoAtualizacaoInfoFiscalDAO(Session sessao){
        super(RequisicaoAtualizacaoInfoFiscal.class, sessao);
    }
    
    @Override
    protected void addFiltros(RequisicaoAtualizacaoInfoFiscal obj, Criteria ctr) {
        if(obj == null) return;
        if(obj.getIdRequisicaoAtualizacaoInfoFiscal() != null) 
            ctr.add(Restrictions.eq("idRequisicaoAtualizacaoInfoFiscal", obj.getIdRequisicaoAtualizacaoInfoFiscal()));
        
        if(!StringUtil.isNullOrEmpty(obj.getNomeArquivo()))
            ctr.add(Restrictions.eq("caminhoArquivo", obj.getNomeArquivo()));
        
        if(obj.getCliente() != null)
            ctr.add(Restrictions.eq("cliente", obj.getCliente()));
        
        if(obj.getStatusRequisicao() != null)
            ctr.add(Restrictions.eq("statusRequisicao",obj.getStatusRequisicao()));
        
    }

    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        
    }
    
    public List<RequisicaoAtualizacaoInfoFiscal> retornarProximosDaFila() {
        StringBuilder hql = new StringBuilder();
        hql.append("select raif ");
        hql.append("from RequisicaoAtualizacaoInfoFiscal raif ");
        hql.append("where raif.dataCadastro = ( ");
        hql.append("    select min(raif2.dataCadastro) ");
        hql.append("    from RequisicaoAtualizacaoInfoFiscal raif2 ");
        hql.append("    where raif2.statusRequisicao = :statusRequisicao ");
        hql.append(") ");
        hql.append("and raif.statusRequisicao = :statusRequisicao ");
        
        try {
            Query query = getSessao().createQuery(hql.toString());
            query.setParameter("statusRequisicao", EStatusAtualizacaoInfoFiscal.Pendente);
            return (List<RequisicaoAtualizacaoInfoFiscal>)query.list();
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }
    
}
