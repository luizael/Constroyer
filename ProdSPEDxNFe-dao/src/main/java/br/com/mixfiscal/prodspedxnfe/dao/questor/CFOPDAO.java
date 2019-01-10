package br.com.mixfiscal.prodspedxnfe.dao.questor;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.CFOP;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class CFOPDAO extends SuperDAO<CFOP> {
    public CFOPDAO() {
        super(CFOP.class, QuestorHibernateUtil.getSessionFactory().getCurrentSession());
    }
    
    @Override
    protected void addFiltros(CFOP obj, Criteria ctr) {
        if (obj == null) return;
        if (obj.getCodigoCFOP() != 0) ctr.add(Restrictions.eq("codigoCFOP", obj.getCodigoCFOP()));
        if (obj.getDescricao() != null) ctr.add(Restrictions.like("descricao", "%" + obj.getDescricao() + "%"));
    }

    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
