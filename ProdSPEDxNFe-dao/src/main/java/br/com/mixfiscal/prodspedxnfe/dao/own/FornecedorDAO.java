package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOP;
import br.com.mixfiscal.prodspedxnfe.domain.own.Fornecedor;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class FornecedorDAO extends SuperDAO<Fornecedor> {
    public FornecedorDAO() {
        super(Fornecedor.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
    
    @Override
    protected void addFiltros(Fornecedor obj, Criteria ctr) {   
        if (obj == null) return;
        if (obj.getId() != null) ctr.add(Restrictions.eq("id", obj.getId()));        
        if (!StringUtil.isNullOrEmpty(obj.getNome())) ctr.add(Restrictions.like("nome", obj.getNome()));
        if (!StringUtil.isNullOrEmpty(obj.getCnpj())) ctr.add(Restrictions.eq("cnpj", obj.getCnpj()));        
    }
    
    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
}
