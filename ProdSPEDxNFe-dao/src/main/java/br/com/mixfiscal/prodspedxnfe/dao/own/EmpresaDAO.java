package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOP;
import br.com.mixfiscal.prodspedxnfe.domain.own.Empresa;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class EmpresaDAO extends SuperDAO<Empresa> {
    public EmpresaDAO() {
        super(Empresa.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
    
    @Override
    protected void addFiltros(Empresa obj, Criteria ctr) {
        if (obj == null) return;
        if (obj.getId() != null) ctr.add(Restrictions.eq("id", obj.getId()));
        if (!StringUtil.isNullOrEmpty(obj.getNome())) ctr.add(Restrictions.eq("nome", obj.getNome()));
        if (!StringUtil.isNullOrEmpty(obj.getCnpj())) ctr.add(Restrictions.eq("cnpj", obj.getCnpj()));
        if (!StringUtil.isNullOrEmpty(obj.getCpf())) ctr.add(Restrictions.eq("cpf", obj.getCpf()));        
        if (!StringUtil.isNullOrEmpty(obj.getInscricaoMunicipal())) ctr.add(Restrictions.eq("inscricaoMunicipal", obj.getInscricaoMunicipal()));
        if (!StringUtil.isNullOrEmpty(obj.getInscricaoEstadual())) ctr.add(Restrictions.eq("inscricaoEstadual", obj.getInscricaoEstadual()));
        if (!StringUtil.isNullOrEmpty(obj.getUf())) ctr.add(Restrictions.eq("uf", obj.getUf()));
        if (obj.getCodigoMunicipio() != null) ctr.add(Restrictions.eq("codigoMunicipio", obj.getCodigoMunicipio()));
    }
    
    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
