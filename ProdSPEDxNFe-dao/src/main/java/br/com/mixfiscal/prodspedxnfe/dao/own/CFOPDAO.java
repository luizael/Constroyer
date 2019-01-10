package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOP;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class CFOPDAO extends SuperDAO<CFOP> {
    public CFOPDAO() {
        super(CFOP.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
    
    @Override
    protected void addFiltros(CFOP obj, Criteria ctr) {
        if (obj == null) return;
        if (obj.getId() != null) ctr.add(Restrictions.eq("id", obj.getId()));
        if (!StringUtil.isNullOrEmpty(obj.getCodigo())) ctr.add(Restrictions.like("codigo", "%" + obj.getCodigo() + "%"));
        if (!StringUtil.isNullOrEmpty(obj.getNome())) ctr.add(Restrictions.like("nome", "%" + obj.getNome() + "%"));
        if (!StringUtil.isNullOrEmpty(obj.getDescricao())) ctr.add(Restrictions.like("descricao", "%" + obj.getDescricao() + "%"));
       // if (obj.getCFOPEquivalente() != null) ctr.add(Restrictions.eq("CFOPEquivalente", obj.getCFOPEquivalente()));
    }

    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
