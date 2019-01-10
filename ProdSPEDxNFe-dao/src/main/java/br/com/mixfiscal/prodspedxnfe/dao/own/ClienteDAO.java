package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.ex.DAOException;
import br.com.mixfiscal.prodspedxnfe.domain.own.Cliente;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class ClienteDAO extends SuperDAO<Cliente>  {
    private final Logger log = LogManager.getLogger(ClienteDAO.class);
    
    public ClienteDAO(){
        super(Cliente.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
    
    public ClienteDAO(Session session){
        super(Cliente.class, session);
    }

    @Override
    protected void addFiltros(Cliente obj, Criteria ctr) {
        if (obj == null) return;
        try{
            if(obj.getId() > 0)ctr.add(Restrictions.eq("id", obj.getId()));
            if (!StringUtil.isNullOrEmpty(obj.getNome())) 
                ctr.add(Restrictions.like("nome", obj.getNome()));
            if (!StringUtil.isNullOrEmpty(obj.getCnpj())) 
                ctr.add(Restrictions.eq("cnpj", obj.getCnpj()));    
            if (!StringUtil.isNullOrEmpty(obj.getToken())) 
                ctr.add(Restrictions.eq("token", obj.getToken())); 
            if (obj.getOperacao() > 0) ctr.add(Restrictions.eq("tipo_operacao", obj.getOperacao())); 
        }catch(Exception ex){
             log.error(ex.getMessage(), ex);
        }
    }

    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Cliente selecionarPorCNPJ(String cnpj) throws DAOException {
        try {
            Query query = this.getSessao().createQuery("select c from Cliente c where c.cnpj = :cnpj");
            query.setParameter("cnpj", cnpj);
            return (Cliente)query.uniqueResult();
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DAOException(ex);
        }
    }
}
