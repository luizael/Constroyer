package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.dao.util.ProdSPEDXNFeHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.NFeProcessada;
import br.com.mixfiscal.prodspedxnfe.domain.own.NFeProcessadaItem;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class NFeProcessadaItemDAO extends SuperDAO<NFeProcessadaItem> {
    public NFeProcessadaItemDAO() {
        super(NFeProcessadaItem.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }

    @Override
    protected void addFiltros(NFeProcessadaItem obj, Criteria ctr) {
        if (obj == null) return;
        if (obj.getIdNFeProcessadaItem() != null) ctr.add(Restrictions.eq("idNFeProcessadaItem", obj.getIdNFeProcessadaItem()));
        if (obj.getNFeProcessada() != null) {
            NFeProcessada nfe = obj.getNFeProcessada();
            Criteria ctrNFeProc = ctr.createCriteria("nfeProcessada", "nfeproc");
            if (nfe.getIdNFeProcessada() != null) ctrNFeProc.add(Restrictions.eq("idNFeProcessada", nfe.getIdNFeProcessada()));
            if (nfe.getChave()!= null) ctrNFeProc.add(Restrictions.eq("chave", nfe.getChave()));
            if (nfe.getNumeroNotaFiscal()!= null) ctrNFeProc.add(Restrictions.eq("numeroNotaFiscal", nfe.getNumeroNotaFiscal()));            
        }
        if (obj.getCodigoProduto() != null) ctr.add(Restrictions.eq("codigoProduto", obj.getCodigoProduto()));
        if (obj.getEAN()!= null) ctr.add(Restrictions.eq("EAN", obj.getEAN()));
        if (obj.getNomeProduto()!= null) ctr.add(Restrictions.like("nomeProduto", "%" + obj.getNomeProduto() + "%"));
        if (obj.getNCM()!= null) ctr.add(Restrictions.eq("NCM", obj.getNCM()));
        if (obj.getCFOP()!= null) ctr.add(Restrictions.eq("CFOP", obj.getCFOP()));
        if (obj.getUnidadeMedida()!= null) ctr.add(Restrictions.eq("unidadeMedida", obj.getUnidadeMedida()));        
    }

    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
