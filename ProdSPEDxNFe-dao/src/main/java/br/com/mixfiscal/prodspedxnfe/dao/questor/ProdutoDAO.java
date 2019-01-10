package br.com.mixfiscal.prodspedxnfe.dao.questor;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.ProdutoQuestor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class ProdutoDAO extends SuperDAO<ProdutoQuestor> {
    public ProdutoDAO() {
        super(ProdutoQuestor.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
    
    @Override
    protected void addFiltros(ProdutoQuestor obj, Criteria ctr) {        
        if (obj == null) return;
        if (obj.getCodigoProduto() != null) ctr.add(Restrictions.eq("codigoProduto", obj.getCodigoProduto()));
        if (obj.getCodigoEmpresa() != null) ctr.add(Restrictions.eq("codigoEmpresa", obj.getCodigoEmpresa()));
        if (obj.getReferenProduto() != null) ctr.add(Restrictions.eq("referenProduto", obj.getReferenProduto()));        
    }
    
    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
