package br.com.mixfiscal.prodspedxnfe.dao.questor;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.RelacProdutoFornecedor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class RelacProdutoFornecedorDAO extends SuperDAO<RelacProdutoFornecedor>{
    public RelacProdutoFornecedorDAO() {
        super(RelacProdutoFornecedor.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
    
    @Override
    protected void addFiltros(RelacProdutoFornecedor obj, Criteria ctr) {
        if (obj == null) return;
        if (obj.getCodigoEmpresa() != null) ctr.add(Restrictions.eq("codigoEmpresa", obj.getCodigoEmpresa()));
        if (obj.getCodigoEstab() != null) ctr.add(Restrictions.eq("codigoEstab", obj.getCodigoEstab()));
        if (obj.getCodigoPessoa() != null) ctr.add(Restrictions.eq("codigoPessoa", obj.getCodigoPessoa()));
        if (obj.getCodigoProduto() != null) ctr.add(Restrictions.eq("codigoProduto", obj.getCodigoProduto()));
        if (obj.getReferItemFornecedor() != null) ctr.add(Restrictions.eq("referItemFornecedor", obj.getReferItemFornecedor()));        
    }
    
    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
