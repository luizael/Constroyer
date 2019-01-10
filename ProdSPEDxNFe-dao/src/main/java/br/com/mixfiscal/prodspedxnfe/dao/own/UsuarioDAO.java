
package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.domain.own.Usuario;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import org.hibernate.criterion.Restrictions;

public class UsuarioDAO extends SuperDAO<Usuario> {
    
    public UsuarioDAO(){
        super(Usuario.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
    
    @Override
    protected void addFiltros(Usuario obj, Criteria ctr){
    
        if(obj == null)return;
        if(obj.getId() != null) ctr.add(Restrictions.eq("id", obj.getId()));
        if(!StringUtil.isNullOrEmpty(obj.getNome())) ctr.add(Restrictions.like("nome","%" + obj.getNome() +"%"));
        if(!StringUtil.isNullOrEmpty(obj.getEmail())) ctr.add(Restrictions.eq("email", "%" +obj.getEmail() + "%"));
        if(!StringUtil.isNullOrEmpty(obj.getUsuario())) ctr.add(Restrictions.eq("usuario", "%" + obj.getUsuario() + "%" ));
        if(!StringUtil.isNullOrEmpty(obj.getSenha())) ctr.add(Restrictions.eq("senha", "%" + obj.getSenha() + "%"));
    }
    
    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir){
    
    }
}
