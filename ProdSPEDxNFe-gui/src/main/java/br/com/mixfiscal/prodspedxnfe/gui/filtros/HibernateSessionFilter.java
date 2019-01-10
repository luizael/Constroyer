package br.com.mixfiscal.prodspedxnfe.gui.filtros;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;

public class HibernateSessionFilter implements Filter {

    private Logger log;
    private SessionFactory sessionFactory;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log = LogManager.getLogger(HibernateSessionFilter.class);
        sessionFactory = ConstroyerHibernateUtil.getSessionFactory();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            sessionFactory.getCurrentSession().beginTransaction();
            chain.doFilter(request, response);
            sessionFactory.getCurrentSession().getTransaction().commit();
        } catch (Throwable ex) {
            log.error("Ocorreu um erro ao tentar iniciar ou commitar a transação da request corrente. Msg: " + ex.getMessage(), ex);
            try {
                if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            } catch (Throwable ex2) {
                log.error("Ocorreu um erro ao ao tentar fazer o rollback da transação. Msg: " + ex2.getMessage(), ex2);
            }
            throw new ServletException(ex);
        } finally {
            try {
                if (sessionFactory.getCurrentSession() != null &&
                    sessionFactory.getCurrentSession().isOpen())
                    sessionFactory.close();
            } catch(Exception ex) {
                log.error("Erro ao tentar fechar a sessao. " + ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
