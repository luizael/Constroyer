package br.com.mixfiscal.prodspedxnfe.dao.mixfiscalamazon;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.MixFiscalAmazonHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.mixfiscalamazon.CmfConstroyerProduto;
import org.hibernate.Criteria;
import org.hibernate.Session;

public class CmfConstroyerProdutoDAO extends SuperDAO<CmfConstroyerProduto> {
    public CmfConstroyerProdutoDAO() {
        super(CmfConstroyerProduto.class, MixFiscalAmazonHibernateUtil.getSessionFactory().getCurrentSession());
    }
    
    public CmfConstroyerProdutoDAO(Session session) {
        super(CmfConstroyerProduto.class, session);
    }
    
    @Override
    protected void addFiltros(CmfConstroyerProduto obj, Criteria ctr) {
        
    }

    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        
    }    
}
