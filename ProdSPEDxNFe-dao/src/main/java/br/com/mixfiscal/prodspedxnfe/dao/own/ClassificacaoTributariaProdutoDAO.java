package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.ex.DAOException;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.enums.ETipoRelatorio;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributaria;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributariaProduto;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsEntrada;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsSaida;
import br.com.mixfiscal.prodspedxnfe.domain.own.custom.ClassificacaoTributariaProdutoCustom;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Query;
import org.hibernate.Session;

public class ClassificacaoTributariaProdutoDAO extends SuperDAO<ClassificacaoTributariaProduto> {
    private final Logger log = LogManager.getLogger(ClassificacaoTributariaProdutoDAO.class);
    
    public  ClassificacaoTributariaProdutoDAO(){
         super(ClassificacaoTributariaProduto.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
    public ClassificacaoTributariaProdutoDAO(Session sessao){
        super(ClassificacaoTributariaProduto.class, sessao);
        //log = LogManager.getLogger(ClassificacaoTributariaDAO.class);
    }
    @Override
    protected void addFiltros(ClassificacaoTributariaProduto obj, Criteria ctr) {
        if(obj == null) return;
        if(obj.getIdProduto() != null )ctr.add(Restrictions.eq("id", obj.getIdProduto()));
        //if(obj.getClassificacaoTributariaId() != null)ctr.add(Restrictions.eq("classificacaoTributariaId", obj.getClassificacaoTributariaId()));
        if(obj.getCodigoProduto() != null)ctr.add(Restrictions.eq("codigoProduto", obj.getCodigoProduto()));
        if(obj.getEan() != null)ctr.add(Restrictions.eq("ean", obj.getEan()));
        if(obj.getDescricao() != null)ctr.add(Restrictions.eq("decricao", obj.getDescricao()));        
    }
    
    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ClassificacaoTributariaProduto selecionarProdutoPorCodigoECNPJCliente(String codigoProduto, String cnpjCliente) throws DAOException {
        StringBuilder hql = new StringBuilder();
        
        hql.append("select prod ");
        hql.append("from ClassificacaoTributariaProduto prod ");
        hql.append("join prod.classificacaoTributaria classTrib ");
        hql.append("join classTrib.cliente cli ");
        hql.append("left join fetch prod.icmsEntrada ");
        hql.append("left join fetch prod.icmsSaida ");
        hql.append("left join fetch prod.pisCofins ");
        hql.append("where cli.cnpj = :cnpjCliente ");
        hql.append("and prod.codigoProduto = :codigoProduto ");
        
        try {
            //Query query = sessao.createQuery(hql.toString());
            Query query = this.getSessao().createQuery(hql.toString());            
            query.setParameter("cnpjCliente", cnpjCliente);
            query.setParameter("codigoProduto", codigoProduto);
            return (ClassificacaoTributariaProduto) query.uniqueResult();
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DAOException(ex);
        }
    }
    
    public ClassificacaoTributaria selecionarUmProdutoRelacionado(ClassificacaoTributaria obj){
        StringBuilder hql = new StringBuilder();
        
        hql.append("select cl ");
        hql.append("from ClassificacaoTributaria cl ");
        hql.append("join cl.cliente cli ");
        hql.append("join cl.produto p ");
        hql.append("where cli.id = :idCliente ");
        hql.append("and (p.ean = :eanTrib ");
        hql.append("     or p.descricao = :descProd ");
        hql.append("     or p.codigoProduto = :codProd) ");
        
        try {
            Query query = this.getSessao().createQuery(hql.toString());            
            query.setParameter("idCliente", obj.getCliente().getId());
            query.setParameter("eanTrib", obj.getProduto().getEan());
            query.setParameter("descProd", obj.getProduto().getDescricao());
            query.setParameter("codProd", obj.getProduto().getCodigoProduto());
            return (ClassificacaoTributaria)query.uniqueResult();
        } catch(Exception ex) {
            return null;
        }
    }
    
    public ClassificacaoTributaria selecionarUmProdutoCustom(ClassificacaoTributariaProdutoCustom obj, ETipoRelatorio tipo){
        
        StringBuilder hqlProduto = new StringBuilder();
        StringBuilder hqlIcmsEntrada = new StringBuilder();
        StringBuilder hqlIcmsSaida = new StringBuilder();
        hqlProduto.append(" select p from ClassificacaoTributariaProdutoCustom p ");
        hqlProduto.append(" where p.cnpj =:cnpjCliente ");
        hqlProduto.append(" and p.codigoProduto =:codProd ");
        
        ClassificacaoTributaria result = new ClassificacaoTributaria();
        ClassificacaoTributariaProdutoCustom prodCustom = new ClassificacaoTributariaProdutoCustom();
        ClassificacaoTributariaProduto produto = null;
        IcmsEntrada icmEntrada;
        IcmsSaida icmSaida;
        try{
            Query queryProduto = this.getSessao().createQuery(hqlProduto.toString());
            queryProduto.setParameter("cnpjCliente", obj.getCnpj());
            queryProduto.setParameter("codProd", obj.getCodigoProduto());
            prodCustom = (ClassificacaoTributariaProdutoCustom)queryProduto.uniqueResult();
            
            if(prodCustom != null)
                if(!StringUtil.isNullOrEmpty(prodCustom.getCodigoProduto()))
                    if(tipo == ETipoRelatorio.Entrada){
                        hqlIcmsEntrada.append(" select icms from IcmsEntrada icms ");
                        hqlIcmsEntrada.append(" where icms.produto.idProduto = :produtoId");
                        Query queryIcms = this.getSessao().createQuery(hqlIcmsEntrada.toString());
                        queryIcms.setParameter("produtoId", prodCustom.getIdProduto());
                        
                        icmEntrada = (IcmsEntrada)queryIcms.uniqueResult();
                        
                        produto = new ClassificacaoTributariaProduto();
                        produto.setCodigoProduto(prodCustom.getCodigoProduto());
                        produto.setDescricao(prodCustom.getDescricao());
                        produto.setEan(prodCustom.getEan());
                        produto.setIcmsEntrada(icmEntrada);
                        result.setProduto(produto);
                    }else if(tipo == ETipoRelatorio.Saida){
                        hqlIcmsSaida.append(" select icms from IcmsSaida icms ");
                        hqlIcmsSaida.append(" where icms.produto.idProduto = :produtoId");
                        Query queryIcms = this.getSessao().createQuery(hqlIcmsSaida.toString());
                        queryIcms.setParameter("produtoId", prodCustom.getIdProduto());
                        icmSaida = (IcmsSaida)queryIcms.uniqueResult();
                        produto = new ClassificacaoTributariaProduto();
                        produto.setCodigoProduto(prodCustom.getCodigoProduto());
                        produto.setDescricao(prodCustom.getDescricao());
                        produto.setEan(prodCustom.getEan());
                        produto.setIcmsSaida(icmSaida);
                        result.setProduto(produto);
                    }
        }catch(Exception ex){
            System.out.println(""+ex.getMessage());
        }
        return result;
    }
    
    public void excluirCustom(String cnpjCliente){
        StringBuilder hqlIcmsEntrada = new StringBuilder();
        hqlIcmsEntrada.append(" delete from ClassificacaoTributariaProduto where cnpjCliente = :cnpj ");
        try{
            Query queryIcmsentrada = this.getSessao().createQuery(hqlIcmsEntrada.toString());
            queryIcmsentrada.setParameter("cnpj", cnpjCliente);
            queryIcmsentrada.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }    
    }
}
