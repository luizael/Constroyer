package br.com.mixfiscal.prodspedxnfe.dao.questor;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.Municipio;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class MunicipioDAO extends SuperDAO<Municipio> {
    public MunicipioDAO() {
        super(Municipio.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }

    @Override
    protected void addFiltros(Municipio obj, Criteria ctr) {
        if (obj == null) return;
        if (obj.getSiglaEstado() != null) ctr.add(Restrictions.eq("siglaEstado", obj.getSiglaEstado()));
        if (obj.getCodigoMunicipio() != null) ctr.add(Restrictions.eq("codigoMunicipio", obj.getCodigoMunicipio()));
        if (obj.getCodigoRais() != null) ctr.add(Restrictions.eq("codigoRais", obj.getCodigoRais()));
    }    

    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
