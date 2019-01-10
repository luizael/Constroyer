package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.dao.util.ProdSPEDXNFeHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.NFeProcessada;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class NFeProcessadaDAO extends SuperDAO<NFeProcessada> {
    
    public NFeProcessadaDAO() {
        super(NFeProcessada.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
    
    @Override
    protected void addFiltros(NFeProcessada obj, Criteria ctr) {
        if (obj == null) return;
        if (obj.getIdNFeProcessada() != null) ctr.add(Restrictions.eq("idNFeProcessada", obj.getIdNFeProcessada()));        
        if (obj.getChave() != null) ctr.add(Restrictions.eq("chave", obj.getChave()));
        if (obj.getNumeroNotaFiscal() != null) ctr.add(Restrictions.eq("numeroNotaFiscal", obj.getNumeroNotaFiscal()));
        if (obj.getNomeEmitente() != null) ctr.add(Restrictions.like("nomeEmitente", "%" + obj.getNomeEmitente()+ "%"));
        if (obj.getDataHoraEmissaoInicio() != null && obj.getDataHoraEmissaoFim() != null) ctr.add(Restrictions.between("dataHoraEmissao", obj.getDataHoraEmissaoInicio(), obj.getDataHoraEmissaoFim()));
        if (obj.getValorTotalNotaFiscal() != null) ctr.add(Restrictions.eq("valorTotalNotaFiscal", obj.getValorTotalNotaFiscal()));
    }    

    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
