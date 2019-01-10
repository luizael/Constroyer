package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.Produto;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;

import javax.persistence.NonUniqueResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

public class ProdutoDAO extends SuperDAO<Produto> {
    private Logger log;
    
    public ProdutoDAO() {
        super(Produto.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
        log = LogManager.getLogger(ProdutoDAO.class);
    }
    
    @Override
    protected void addFiltros(Produto obj, Criteria ctr) {
        if (obj == null) return;
        if (obj.getEmpresa() != null) ctr.add(Restrictions.eq("empresa", obj.getEmpresa()));
        if (!StringUtil.isNullOrEmpty(obj.getDescricao())) ctr.add(Restrictions.eq("descricao", obj.getDescricao()));
        if (!StringUtil.isNullOrEmpty(obj.getEan())) ctr.add(Restrictions.eq("ean", obj.getEan()));
        if (!StringUtil.isNullOrEmpty(obj.getNcm())) ctr.add(Restrictions.eq("ncm", obj.getNcm()));
        if (!StringUtil.isNullOrEmpty(obj.getReferenciaProduto())) ctr.add(Restrictions.eq("referenciaProduto", obj.getReferenciaProduto()));
        if (!StringUtil.isNullOrEmpty(obj.getUnidadeMedida())) ctr.add(Restrictions.eq("unidadeMedida", obj.getUnidadeMedida()));
        if (obj.getAliqICMS() != null) ctr.add(Restrictions.eq("aliqICMS", obj.getAliqICMS()));        
    }

    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Produto retornarProdutoPorCNPJECodigoItem(String cnpjEmpresa, String referenciaProduto) {
        StringBuilder hql = new StringBuilder();
        hql.append("select p from Produto p ");
        hql.append("inner join fetch p.empresa e ");
        hql.append("where p.referenciaProduto = :referenciaProduto ");
        hql.append("and e.cnpj = :cnpjEmpresa ");
        
        try {
            Query query = this.getSessao().createQuery(hql.toString());            
            query.setParameter("cnpjEmpresa", cnpjEmpresa);
            query.setParameter("referenciaProduto", referenciaProduto);
            return (Produto)query.uniqueResult();
        } catch(NonUniqueResultException ex) {
            log.error(String.format("O cnpj %s e a referencia de Produto %s retornou mais de um resultado",
                                    cnpjEmpresa, referenciaProduto), ex);
            return null;
        } catch(Exception ex) {
            log.error(String.format("Ocorreu uma exceção ao pesquisar o cnpj %s e a referencia de Produto %s", 
                                    cnpjEmpresa, referenciaProduto));
            return null;
        }
    }
}
