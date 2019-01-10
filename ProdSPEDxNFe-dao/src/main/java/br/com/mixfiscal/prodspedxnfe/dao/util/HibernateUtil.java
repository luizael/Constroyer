package br.com.mixfiscal.prodspedxnfe.dao.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public interface HibernateUtil {
    public SessionFactory getHibernateSessionFactory(); 
    public Session openHibernateSession();
    public void closeHibernateSession();
    public Session getHibernateCurrentSession();    
    public Transaction beginHibernateTransaction();    
    public void commitHibernateCurrentTransaction();    
    public void rollbackHibernateCurrentTransaction();    
    public Transaction getHibernateCurrentTransaction();
}
