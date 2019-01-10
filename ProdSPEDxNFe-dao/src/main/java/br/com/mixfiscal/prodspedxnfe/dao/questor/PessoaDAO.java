package br.com.mixfiscal.prodspedxnfe.dao.questor;

import br.com.mixfiscal.prodspedxnfe.dao.EDirecaoOrdenacao;
import br.com.mixfiscal.prodspedxnfe.dao.SuperDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.Pessoa;
import br.com.mixfiscal.prodspedxnfe.util.Masc;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class PessoaDAO extends SuperDAO<Pessoa> {
    public PessoaDAO() {
        super(Pessoa.class, ConstroyerHibernateUtil.getSessionFactory().getCurrentSession());
    }
    
    @Override
    protected void addFiltros(Pessoa obj, Criteria ctr) {
        if (obj == null) return;
        if (obj.getInscrFederal() != null && !obj.getInscrFederal().isEmpty()) {
            String inscrFederal = "";
            if (Masc.isCNPJ(obj.getInscrFederal())) inscrFederal = Masc.mascararCNPJ(obj.getInscrFederal());
            else if (Masc.isCPF(obj.getInscrFederal())) inscrFederal = Masc.mascararCPF(obj.getInscrFederal());
            else inscrFederal = obj.getInscrFederal();
            ctr.add(Restrictions.eq("inscrFederal", inscrFederal));
        }
    }
    
    @Override
    protected void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
