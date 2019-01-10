package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOP;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOPDePara;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CFOPDeParaDAO extends SuperDAO<CFOPDePara> {
    
    public CFOPDeParaDAO(){
        super(CFOPDePara.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
    @Override
    protected void addFiltros(CFOPDePara obj, Criteria crt) {
        if(obj == null) return; 
        if(obj.getCfopDe() != null) crt.add(Restrictions.eq("cfopDe", obj.getCfopDe()));
        if(obj.getCfopPara() != null) crt.add(Restrictions.eq("cfopPara", obj.getCfopPara()));
    }
    
    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {      
        Order order = (orderDir == EDirecaoOrdenacao.Asc ? Order.asc(orderProp) : Order.desc(orderProp));
        if (orderProp.equals("de.codigo")) {
            ctr.createCriteria("cfopDe", "de", CriteriaSpecification.LEFT_JOIN).addOrder(order);
        }        
        if (orderProp.equals("para.codigo")) {
            ctr.createCriteria("cfopPara", "para", CriteriaSpecification.LEFT_JOIN).addOrder(order);
        }
    }
    
    private void addFiltrosCFOP(CFOP cfop, Criteria c) {
        if (cfop.getId() != null ) c.add(Restrictions.eq("id", cfop.getId()));
        if (!StringUtil.isNullOrEmpty(cfop.getCodigo())) c.add(Restrictions.like("codigo", "%" + cfop.getCodigo() + "%"));
        if (!StringUtil.isNullOrEmpty(cfop.getNome())) c.add(Restrictions.like("nome", "%" + cfop.getNome() + "%"));
        if (!StringUtil.isNullOrEmpty(cfop.getDescricao())) c.add(Restrictions.eq("descricao", "%" + cfop.getDescricao() + "%"));            
    }
}
