package br.com.mixfiscal.prodspedxnfe.dao.questor;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.EmpresaQuestor;
import br.com.mixfiscal.prodspedxnfe.domain.questor.Estabelecimento;
import br.com.mixfiscal.prodspedxnfe.util.Masc;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class EmpresaQuestorDAO extends SuperDAO<EmpresaQuestor> {
    public EmpresaQuestorDAO() {
        super(EmpresaQuestor.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }

    @Override
    protected void addFiltros(EmpresaQuestor obj, Criteria ctr) {
        if (obj == null) return;
        if (obj.getCodigo() != null) ctr.add(Restrictions.eq("codigo", obj.getCodigo()));
        if (obj.getEstabelecimento() != null) {
            Estabelecimento est = obj.getEstabelecimento();
            if (est.getInscricaoFederal() != null && !est.getInscricaoFederal().isEmpty()) {
                String inscrFederal = "";
                if (Masc.isCNPJ(est.getInscricaoFederal())) inscrFederal = Masc.mascararCNPJ(est.getInscricaoFederal());
                else if (Masc.isCPF(est.getInscricaoFederal())) inscrFederal = Masc.mascararCPF(est.getInscricaoFederal());
                else inscrFederal = est.getInscricaoFederal();
                ctr.createCriteria("estabelecimento", "est")                        
                   .add(Restrictions.eq("inscricaoFederal", inscrFederal));
            }
        }
    }     

    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
