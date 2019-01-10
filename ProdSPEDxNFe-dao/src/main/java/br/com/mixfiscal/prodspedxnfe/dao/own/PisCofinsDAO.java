package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.domain.own.PisCofins;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class PisCofinsDAO extends SuperDAO<PisCofins>{

    public PisCofinsDAO(){
        super(PisCofins.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
    
    @Override
    protected void addFiltros(PisCofins obj, Criteria ctr) {
        if(obj == null)return;
        if(obj.getId() != null)ctr.add(Restrictions.eq("id", obj.getId()));
        if(obj.getNcm() != null)ctr.add(Restrictions.eq("ncm", obj.getNcm()));
        if(obj.getNcmEx() != null)ctr.add(Restrictions.eq("ncmEx", obj.getNcmEx()));
        if(obj.getCodNaturezaReceita() != null)ctr.add(Restrictions.eq("codNaturezaReceita",obj.getCodNaturezaReceita()));
        if(obj.getCreditoPresumido() != null)ctr.add(Restrictions.eq("creditoPresumido", obj.getCreditoPresumido()));
        if(obj.getPisCstE() != null)ctr.add(Restrictions.eq("pisCstE", obj.getPisCstE()));
        if(obj.getPisCstS() != null)ctr.add(Restrictions.eq("pisCstS", obj.getPisCstS()));
        if(obj.getPisAlqE() != null)ctr.add(Restrictions.eq("pisAlqE", obj.getPisAlqE()));
        if(obj.getCofinsAlqS() != null)ctr.add(Restrictions.eq("pisAlqS", obj.getCofinsAlqS()));
        if(obj.getCofinsCstE() != null)ctr.add(Restrictions.eq("cofinsCstE", obj.getCofinsCstE()));
        if(obj.getCofinsCstS() != null)ctr.add(Restrictions.eq("cofinsCstS", obj.getCofinsCstS()));
        if(obj.getCofinsAlqE() != null)ctr.add(Restrictions.eq("cofinsAlqE", obj.getCofinsAlqE()));        
        if(obj.getCofinsAlqS() != null)ctr.add(Restrictions.eq("cofinsAlqS", obj.getCofinsAlqS()));
        //if(obj.getCnpjCliente() != null)ctr.add(Restrictions.eq("cnpjCliente", obj.getCnpjCliente()));
        if(obj.getProduto() != null)ctr.add(Restrictions.eq("produto", obj.getProduto()));
    }

    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
