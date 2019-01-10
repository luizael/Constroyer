package br.com.mixfiscal.prodspedxnfe.dao.util;

import br.com.mixfiscal.prodspedxnfe.domain.mixfiscalamazon.CmfConstroyerProduto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class MixFiscalAmazonHibernateUtil  {
    private static final SessionFactory sessionFactory;
    private static final Logger log = LogManager.getLogger(MixFiscalAmazonHibernateUtil.class);
    
    static {
        try {
            log.info("Inicializando SessionFactory da " + MixFiscalAmazonHibernateUtil.class.getSimpleName());
            Configuration cfg = new AnnotationConfiguration().configure("mixfiscalamazon_hibernate.cfg.xml");
            cfg.addAnnotatedClass(CmfConstroyerProduto.class);
            sessionFactory = cfg.buildSessionFactory();
            log.info("SessionFactory da " + MixFiscalAmazonHibernateUtil.class.getSimpleName() + " inicializado com sucesso!");
        } catch(Throwable ex) {           
            log.error("Initial Constroyer SessionFactory creation failed. Msg: " + ex.getMessage(), ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
