package br.com.mixfiscal.prodspedxnfe.dao.questor.seq;

import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.questor.Pessoa;
import java.io.Serializable;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class PessoaCodigoSeq implements IdentifierGenerator {

    @Override
    public Serializable generate(SessionImplementor si, Object o) throws HibernateException {
        Integer proxId = null;        
        
        Criteria filtro =  ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().createCriteria(Pessoa.class)
                .setProjection(Projections.max("codigo"));
        proxId = (Integer)filtro.uniqueResult();
        
        return ++proxId;
    }
    
}
