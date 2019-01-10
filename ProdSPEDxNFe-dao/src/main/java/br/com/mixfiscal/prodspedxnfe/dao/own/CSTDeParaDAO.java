package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.domain.own.CSTDePara;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class CSTDeParaDAO extends SuperDAO<CSTDePara>{
    public CSTDeParaDAO(){
        super(CSTDePara.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    } 

    @Override
    protected void addFiltros(CSTDePara obj, Criteria ctr) {
        if(obj == null)return;
        if (obj.getCstDe() != null) ctr.add(Restrictions.eq("cstDe", obj.getCstDe()));
        if (obj.getCstPara() != null) ctr.add(Restrictions.eq("cstPara", obj.getCstPara()));
    }

    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
