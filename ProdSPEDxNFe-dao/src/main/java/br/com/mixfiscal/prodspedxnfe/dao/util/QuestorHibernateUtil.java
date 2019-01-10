package br.com.mixfiscal.prodspedxnfe.dao.util;

import br.com.mixfiscal.prodspedxnfe.domain.questor.CFOP;
import br.com.mixfiscal.prodspedxnfe.domain.questor.EmpresaQuestor;
import br.com.mixfiscal.prodspedxnfe.domain.questor.Estabelecimento;
import br.com.mixfiscal.prodspedxnfe.domain.questor.FatorConversaoUnidade;
import br.com.mixfiscal.prodspedxnfe.domain.questor.Municipio;
import br.com.mixfiscal.prodspedxnfe.domain.questor.Pessoa;
import br.com.mixfiscal.prodspedxnfe.domain.questor.ProdutoQuestor;
import br.com.mixfiscal.prodspedxnfe.domain.questor.RelacProdutoFornecedor;
import br.com.mixfiscal.prodspedxnfe.domain.questor.UnidadeMedida;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class QuestorHibernateUtil implements HibernateUtil {
    private static final SessionFactory sessionFactory;
    //private static Session currentSession;
    private static Transaction currentTransaction;

    static {
        try {
            Configuration cfg = new AnnotationConfiguration().configure("questor_hibernate.cfg.xml");
            
            cfg.addAnnotatedClass(CFOP.class);
            cfg.addAnnotatedClass(EmpresaQuestor.class);
            cfg.addAnnotatedClass(Estabelecimento.class);
            cfg.addAnnotatedClass(Pessoa.class);
            cfg.addAnnotatedClass(ProdutoQuestor.class);
            cfg.addAnnotatedClass(RelacProdutoFornecedor.class);
            cfg.addAnnotatedClass(UnidadeMedida.class);
            cfg.addAnnotatedClass(FatorConversaoUnidade.class);
            cfg.addAnnotatedClass(Municipio.class);
            
            sessionFactory = cfg.buildSessionFactory();
        } catch(Throwable e) {
            System.err.println("Initial Questor SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static Session getCurrentSession() {
        //if (currentSession == null || !currentSession.isOpen())
        //    currentSession = sessionFactory.getCurrentSession();
        return sessionFactory.getCurrentSession();
    }
    
    public static Transaction beginTransaction() {
        currentTransaction = getCurrentSession().beginTransaction();
        return currentTransaction;
    }
    
    public static void commitCurrentTransaction() {
        if (currentTransaction != null)
            currentTransaction.commit();
    }
    
    public static void rollbackCurrentTransaction() {
        if (currentTransaction != null)
            currentTransaction.rollback();
    }
    
    public static Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    @Override
    public Session openHibernateSession() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void closeHibernateSession() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public SessionFactory getHibernateSessionFactory() {
        return QuestorHibernateUtil.getSessionFactory();
    }

    @Override
    public Session getHibernateCurrentSession() {
        return QuestorHibernateUtil.getCurrentSession();
    }

    @Override
    public Transaction beginHibernateTransaction() {
        return QuestorHibernateUtil.beginTransaction();
    }

    @Override
    public void commitHibernateCurrentTransaction() {
        QuestorHibernateUtil.commitCurrentTransaction();
    }

    @Override
    public void rollbackHibernateCurrentTransaction() {
        QuestorHibernateUtil.rollbackCurrentTransaction();
    }

    @Override
    public Transaction getHibernateCurrentTransaction() {
        return QuestorHibernateUtil.getCurrentTransaction();
    }
}