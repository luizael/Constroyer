package br.com.mixfiscal.prodspedxnfe.dao.util;

import br.com.mixfiscal.prodspedxnfe.domain.own.NFeProcessada;
import br.com.mixfiscal.prodspedxnfe.domain.own.NFeProcessadaItem;
import javax.resource.NotSupportedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ProdSPEDXNFeHibernateUtil implements HibernateUtil {
    private static final SessionFactory sessionFactory;
    private static Session currentSession;
    private static Transaction currentTransaction;

    static {
        try {
            Configuration cfg = new AnnotationConfiguration().configure("prodspedxnfe_hibernate.cfg.xml");
            
            cfg.addAnnotatedClass(NFeProcessada.class);
            cfg.addAnnotatedClass(NFeProcessadaItem.class);
            
            sessionFactory = cfg.buildSessionFactory();
        } catch(Throwable e) {
            System.err.println("Initial ProdSPEDXNFe SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static Session getCurrentSession() {
        if (currentSession == null || !currentSession.isOpen())
            currentSession = sessionFactory.getCurrentSession();
        return currentSession;
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
        return ProdSPEDXNFeHibernateUtil.getSessionFactory();
    }

    @Override
    public Session getHibernateCurrentSession() {
        return ProdSPEDXNFeHibernateUtil.getCurrentSession();
    }

    @Override
    public Transaction beginHibernateTransaction() {
        return ProdSPEDXNFeHibernateUtil.beginTransaction();
    }

    @Override
    public void commitHibernateCurrentTransaction() {
        ProdSPEDXNFeHibernateUtil.commitCurrentTransaction();
    }

    @Override
    public void rollbackHibernateCurrentTransaction() {
        ProdSPEDXNFeHibernateUtil.rollbackCurrentTransaction();
    }

    @Override
    public Transaction getHibernateCurrentTransaction() {
        return ProdSPEDXNFeHibernateUtil.getCurrentTransaction();
    }
}
