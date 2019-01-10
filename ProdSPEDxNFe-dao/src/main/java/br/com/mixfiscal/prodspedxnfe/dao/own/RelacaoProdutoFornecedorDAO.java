package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.ex.DAOException;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.RelacaoProdutoFornecedor;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class RelacaoProdutoFornecedorDAO extends SuperDAO<RelacaoProdutoFornecedor> {

    private Long qtdTotalRegistros;
    
    public RelacaoProdutoFornecedorDAO() {
        super(RelacaoProdutoFornecedor.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
    
    public List<RelacaoProdutoFornecedor> listarTudo() {   
        
        List lista = new ArrayList();
        StringBuilder hql = new StringBuilder();
        hql.append("select rel ");
        hql.append("from RelacaoProdutoFornecedor rel ");
        hql.append("join fetch rel.produto prod ");
        hql.append("join fetch rel.fornecedor forn ");
        hql.append("join fetch prod.empresa emp" );
        
        try {            
            Query query = this.getSessao().createQuery(hql.toString());
            lista = query.list();
            Criteria count = this.getSessao().createCriteria(RelacaoProdutoFornecedor.class, "this");
            count.setProjection(Projections.rowCount());
            this.qtdTotalRegistros = (Long)count.uniqueResult();
            
        } catch (Exception e) {                        
            e.printStackTrace();
            //throw new DAOException(String.format("Houve um erro ao executar a consulta", e.getMessage()), e);
        }
        return lista;
    }    
    
    public List<RelacaoProdutoFornecedor> buscarPorEmpresaProdutoFornecedor(String cnpjFornec, String refProd, String refFornec, String chaveNf, String descSped, String descXml) throws DAOException {        
        StringBuilder hql = new StringBuilder();
        hql.append("select rpf ");
        hql.append("from RelacaoProdutoFornecedor rpf ");
        hql.append("join rpf.produto p ");
        hql.append("join rpf.fornecedor f ");
        //hql.append("where p.empresa.id = :idEmpresa ");
        hql.append("where p.referenciaProduto = :refProd ");
        hql.append("and f.cnpj = :cnpjFornec ");
        hql.append("and rpf.referenciaFornecedor = :refFornec ");
        hql.append("and rpf.chaveNf = :chaveNf ");
        hql.append("and rpf.prodSped = :descSped ");
       // hql.append("and rpf.prodXml = :prodXml");
        
        try {            
            Query query = this.getSessao().createQuery(hql.toString());
            //query.setParameter("idEmpresa", idEmpresa);
            query.setParameter("cnpjFornec", cnpjFornec);
            query.setParameter("refProd", refProd);
            query.setParameter("refFornec", refFornec);       
            query.setParameter("chaveNf", chaveNf);
            query.setParameter("descSped", descSped);
            //query.setParameter("prodXml", descXml);
            return query.list();
        } catch (RuntimeException e) {                        
            throw new DAOException(String.format("Houve um erro ao tentar verificar a existencia de %s e %s. %s", refProd, refFornec, e.getMessage()), e);
        }
    }
    
    public List<RelacaoProdutoFornecedor> verificarOutrasAssociacoes(Integer idEmpresa, String cnpjFornec, String refProd, String refFornec) throws DAOException {
        StringBuilder hql = new StringBuilder();
        hql.append("select rpf ");
        hql.append("from RelacaoProdutoFornecedor rpf ");
        hql.append("join fetch rpf.produto p ");
        hql.append("join fetch p.empresa e ");
        hql.append("join fetch rpf.fornecedor f ");        
        hql.append("where p.empresa.id = :idEmpresa ");
        hql.append("and p.referenciaProduto <> :refProd ");
        hql.append("and f.cnpj = :cnpjFornec ");
        hql.append("and rpf.referenciaFornecedor = :refFornec ");
        
        try {            
            Query query = this.getSessao().createQuery(hql.toString());
            query.setParameter("idEmpresa", idEmpresa);
            query.setParameter("cnpjFornec", cnpjFornec);
            query.setParameter("refProd", refProd);
            query.setParameter("refFornec", refFornec);            
            return query.list();
        } catch (RuntimeException e) {                        
            throw new DAOException(String.format("Houve um erro ao tentar verificar a existencia de %s e %s. %s", refProd, refFornec, e.getMessage()), e);
        }
    }
    
    public List<RelacaoProdutoFornecedor> buscarPorFornecedor(Integer idEmpresa, String cnpjFornec, String refProd, String refFornec) throws DAOException {        
        StringBuilder hql = new StringBuilder();
           /*hql.append("select rpf ");
           hql.append("from RelacaoProdutoFornecedor rpf ");
           hql.append("join rpf.produto p ");
           hql.append("join rpf.fornecedor f ");
           hql.append("where p.empresa.id = :idEmpresa ");
           hql.append("and p.referenciaProduto = :refProd ");
           hql.append("and f.cnpj = :cnpjFornec ");
           hql.append("and rpf.referenciaFornecedor = :refFornec ");
           */
           /*
           elect * from relacao_produto_fornecedor rpf
            left join produto prod on prod.id_produto = rpf.id_produto
            left join fornecedor fornec on fornec.id_fornecedor = rpf.id_fornecedor
            where fornec.cnpj = '05.402.904/0021-00' 
                    and rpf.referencia_fornecedor = '000017005022'
           */
            hql.append("select rpf ");
            hql.append("from RelacaoProdutoFornecedor rpf ");
            hql.append("left join  rpf.produto as  prod  with prod.idProduto = rpf.produto.idProduto ");
            hql.append("left join  rpf.fornecedor as fornec with  fornec = rpf.fornecedor ");
            hql.append("where fornec.cnpj = :cnpjFornec ");           
            hql.append("and rpf.referenciaFornecedor = :refFornec ");
           
        try {            
            Query query = this.getSessao().createQuery(hql.toString());
            //query.setParameter("idEmpresa", idEmpresa);
            query.setParameter("cnpjFornec", cnpjFornec);
            //query.setParameter("refProd", refProd);
            query.setParameter("refFornec", refFornec);            
            return query.list();
        } catch (RuntimeException e) {                        
            throw new DAOException(String.format("Houve um erro ao tentar verificar a existencia de %s e %s. %s", refProd, refFornec, e.getMessage()), e);
        }
    }
    
    @Override
    protected void addFiltros(RelacaoProdutoFornecedor obj, Criteria ctr) {
        if (obj == null) return;        
        if (obj.getFornecedor() != null) ctr.add(Restrictions.eq("fornecedor", obj.getFornecedor()));
        if (obj.getProduto() != null) ctr.add(Restrictions.eq("produto", obj.getProduto()));
        if (!StringUtil.isNullOrEmpty(obj.getReferenciaFornecedor())) ctr.add(Restrictions.eq("referenciaFornecedor", obj.getReferenciaFornecedor())); 
        if(!StringUtil.isNullOrEmpty(obj.getChaveNf())) ctr.add(Restrictions.eq("chave", obj.getChaveNf()));
        if(!StringUtil.isNullOrEmpty(obj.getProdSped())) ctr.add(Restrictions.eq("prodSped", obj.getProdSped()));
        if(!StringUtil.isNullOrEmpty(obj.getProdXml())) ctr.add(Restrictions.eq("prodXml", obj.getProdXml()));
    }    

    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Long getQtdTotalRegistros(){
        return qtdTotalRegistros;
    }
}
