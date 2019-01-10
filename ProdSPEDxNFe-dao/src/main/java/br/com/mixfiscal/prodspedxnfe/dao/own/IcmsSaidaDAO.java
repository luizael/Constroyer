package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsSaida;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class IcmsSaidaDAO extends SuperDAO<IcmsSaida>{
    public IcmsSaidaDAO(){
        super(IcmsSaida.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
     public IcmsSaidaDAO(Session sessao){
        super(IcmsSaida.class, sessao);
    }
    @Override
    protected void addFiltros(IcmsSaida obj, Criteria ctr) {
        if(obj == null)return;
        if(obj.getId() != null)ctr.add(Restrictions.eq("id",obj.getId()));
        if(obj.getSacCst() != null)ctr.add(Restrictions.eq("sacCst",obj.getSacCst()));
        if(obj.getSacAlq() != null)ctr.add(Restrictions.eq("sacAlq", obj.getSacAlq()));
        if(obj.getSacAlqst() != null)ctr.add(Restrictions.eq("sacAlqst", obj.getSacAlqst()));
        if(obj.getSacRbc() != null)ctr.add(Restrictions.eq("sacRbc", obj.getSacRbc()));
        if(obj.getSacRbcst() != null)ctr.add(Restrictions.eq("sacRbcst", obj.getSacRbcst()));
        if(obj.getSasCst() != null)ctr.add(Restrictions.eq("sasCst", obj.getSasCst()));
        if(obj.getSasAlq() != null)ctr.add(Restrictions.eq("sasAlq", obj.getSasAlq()));
        if(obj.getSasAlqst()!= null)ctr.add(Restrictions.eq("sasAlqst", obj.getSasAlqst()));
        if(obj.getSasRbc() != null)ctr.add(Restrictions.eq("sasRbc", obj.getSasRbc()));
        if(obj.getSasRbcst() != null)ctr.add(Restrictions.eq("sasRbcst",obj.getSasRbcst()));
        if(obj.getSvcCst() != null)ctr.add(Restrictions.eq("svcCst", obj.getSvcCst()));
        if(obj.getSvcAlq() != null)ctr.add(Restrictions.eq("svcAlq", obj.getSvcAlq()));
        if(obj.getSvcAlqst()!= null)ctr.add(Restrictions.eq("svcAlqst", obj.getSvcAlqst()));
        if(obj.getSvcRbc() != null)ctr.add(Restrictions.eq("svcRbc", obj.getSvcRbc()));
        if(obj.getSvcRbcst() != null)ctr.add(Restrictions.eq("svcRbcst", obj.getSvcRbcst()));
        if(obj.getSncCst() != null)ctr.add(Restrictions.eq("sncCst", obj.getSncCst()));
        if(obj.getSncAlq() != null)ctr.add(Restrictions.eq("sncAlq", obj.getSncAlq()));
        if(obj.getSncAlqst() != null)ctr.add(Restrictions.eq("sncAlqst", obj.getSncAlqst()));
        if(obj.getSncRbc()!= null)ctr.add(Restrictions.eq("sncRbc", obj.getSncRbc()));
        if(obj.getSncRbcst() != null)ctr.add(Restrictions.eq("sncRbcst", obj.getSncRbcst()));
        if(obj.getFundamentoLegal() != null)ctr.add(Restrictions.eq("fundamentoLegal", obj.getFundamentoLegal() ));
        //if(obj.getCnpjCliente() != null)ctr.add(Restrictions.eq("cnpjCliente", obj.getCnpjCliente()));
        if(obj.getProduto() != null)ctr.add(Restrictions.eq("produto", obj.getProduto()));        
    }

    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
