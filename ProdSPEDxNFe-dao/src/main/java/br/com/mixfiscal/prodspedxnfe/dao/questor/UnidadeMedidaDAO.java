package br.com.mixfiscal.prodspedxnfe.dao.questor;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.UnidadeMedida;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UnidadeMedidaDAO extends SuperDAO<UnidadeMedida> {
    public UnidadeMedidaDAO() {
        super(UnidadeMedida.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }

    @Override
    protected void addFiltros(UnidadeMedida obj, Criteria ctr) {
        if (obj == null) return;
        if (obj.getCodigoEmpresa() != null) ctr.add(Restrictions.eq("codigoEmpresa", obj.getCodigoEmpresa()));
        if (obj.getCodigoUnidadeMedida() != null) ctr.add(Restrictions.eq("codigoUnidadeMedida", obj.getCodigoUnidadeMedida()));
        if (obj.getNome() != null) ctr.add(Restrictions.like("nome", "%" + obj.getCodigoEmpresa() + "%"));        
    }
    
    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
