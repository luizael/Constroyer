package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsEntrada;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class IcmsEntradaDAO extends SuperDAO<IcmsEntrada> {

    public IcmsEntradaDAO(){
        super(IcmsEntrada.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
    public IcmsEntradaDAO(Session sessao){
        super(IcmsEntrada.class, sessao);
    }
    @Override
    protected void addFiltros(IcmsEntrada obj, Criteria ctr) {
        if(obj == null)return;
        if(obj.getId() != null)ctr.add(Restrictions.eq("id", obj.getId()));
        if(obj.getTipoMva() != null)ctr.add(Restrictions.eq("tipoMva", obj.getTipoMva()));
        if(obj.getMva() != null)ctr.add(Restrictions.eq("mva", obj.getMva()));
        if(obj.getMvaDataIni() != null)ctr.add(Restrictions.eq("mvaDataIni", obj.getMvaDataIni()));
        if(obj.getMvaDataFim()!= null)ctr.add(Restrictions.eq("mvaDataFim", obj.getMvaDataFim()));
        if(obj.getCreditoOutorgado() != null)ctr.add(Restrictions.eq("creditoOutorgado", obj.getCreditoOutorgado()));
        if(obj.getGeraDebito() != null)ctr.add(Restrictions.eq("geraDebito", obj.getGeraDebito()));
        if(obj.getSubRbcAlq() != null)ctr.add(Restrictions.eq("subRbcAlq", obj.getSubRbcAlq()));
        if(obj.getEiCst() != null)ctr.add(Restrictions.eq("eiCst", obj.getEiCst()));
        if(obj.getEiAlq() != null)ctr.add(Restrictions.eq("eiAlq", obj.getEiAlq()));
        if(obj.getEiAlqst() != null)ctr.add(Restrictions.eq("eiAlqst", obj.getEiAlqst()));
        if(obj.getEiRbc() != null)ctr.add(Restrictions.eq("eiRbc",obj.getEiRbc()));
        if(obj.getEiRbcst() != null)ctr.add(Restrictions.eq("eiRbcst", obj.getEiRbcst()));
        if(obj.getEdCst() != null)ctr.add(Restrictions.eq("edCst", obj.getEdCst()));
        if(obj.getEdAlq() != null)ctr.add(Restrictions.eq("edAlq", obj.getEdAlq()));
        if(obj.getEdAlqst() != null)ctr.add(Restrictions.eq("edAlqst", obj.getEdAlqst()));
        if(obj.getEdRbc() != null)ctr.add(Restrictions.eq("edRbc", obj.getEdRbc()));
        if(obj.getEdRbcst() != null)ctr.add(Restrictions.eq("edRbcst", obj.getEdRbcst()));
        if(obj.getEsCst() != null)ctr.add(Restrictions.eq("esCst",obj.getEsCst()));
        if(obj.getEsAlq() != null)ctr.add(Restrictions.eq("esAlq", obj.getEsAlq()));
        if(obj.getEsAlqst() != null)ctr.add(Restrictions.eq("esAlqst", obj.getEsAlqst()));
        if(obj.getEsRbc() != null)ctr.add(Restrictions.eq("esRbc", obj.getEsRbc()));
        if(obj.getEsRbcst() != null)ctr.add(Restrictions.eq("esRbcst", obj.getEsRbcst()));
        if(obj.getNfiCst() != null)ctr.add(Restrictions.eq("nfiCst", obj.getNfiCst()));
        if(obj.getNfdCst() != null)ctr.add(Restrictions.eq("nfdCst", obj.getNfdCst()));
        if(obj.getNfsCsosn() != null)ctr.add(Restrictions.eq("nfsCsosn", obj.getNfsCsosn()));
        if(obj.getNfAlq() != null)ctr.add(Restrictions.eq("nfAlq", obj.getNfAlq()));
        if(obj.getFundamentoLegal() != null)ctr.add(Restrictions.eq("fundamentoLegal", obj.getFundamentoLegal()));
        //if(obj.getCnpjCliente()!= null)ctr.add(Restrictions.eq("cnpjCliente", obj.getCnpjCliente()));
        if(obj.getProduto()!= null)ctr.add(Restrictions.eq("produto", obj.getProduto()));
    }

    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
