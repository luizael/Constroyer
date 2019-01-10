package br.com.mixfiscal.prodspedxnfe.dao.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import br.com.mixfiscal.prodspedxnfe.domain.own.CFOP;
import br.com.mixfiscal.prodspedxnfe.domain.own.CFOPDePara;
import br.com.mixfiscal.prodspedxnfe.domain.own.CST;
import br.com.mixfiscal.prodspedxnfe.domain.own.CSTDePara;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributaria;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributariaProduto;
import br.com.mixfiscal.prodspedxnfe.domain.own.Cliente;
import br.com.mixfiscal.prodspedxnfe.domain.own.Empresa;
import br.com.mixfiscal.prodspedxnfe.domain.own.Fornecedor;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsEntrada;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsSaida;
import br.com.mixfiscal.prodspedxnfe.domain.own.PisCofins;
import br.com.mixfiscal.prodspedxnfe.domain.own.Produto;
import br.com.mixfiscal.prodspedxnfe.domain.own.RelacaoProdutoFornecedor;
import br.com.mixfiscal.prodspedxnfe.domain.own.RequisicaoAtualizacaoInfoFiscal;
import br.com.mixfiscal.prodspedxnfe.domain.own.Usuario;
import br.com.mixfiscal.prodspedxnfe.domain.own.custom.ClassificacaoTributariaProdutoCustom;
import br.com.mixfiscal.prodspedxnfe.domain.own.custom.IcmsEntradaCustom;
import br.com.mixfiscal.prodspedxnfe.domain.own.custom.IcmsSaidaCustom;

@SuppressWarnings("deprecation")
public class ConstroyerHibernateUtil {
    private static final SessionFactory sessionFactory;    
    static {
        try {
            Configuration cfg = new AnnotationConfiguration().configure("constroyer_hibernate.cfg.xml");
            cfg.addAnnotatedClass(Empresa.class);
            cfg.addAnnotatedClass(Fornecedor.class);
            cfg.addAnnotatedClass(Produto.class);
            cfg.addAnnotatedClass(RelacaoProdutoFornecedor.class);
            cfg.addAnnotatedClass(CFOP.class);
            cfg.addAnnotatedClass(CFOPDePara.class);
            cfg.addAnnotatedClass(CST.class);
            cfg.addAnnotatedClass(CSTDePara.class);
            cfg.addAnnotatedClass(Usuario.class);
            cfg.addAnnotatedClass(Cliente.class);
            cfg.addAnnotatedClass(ClassificacaoTributaria.class);
            cfg.addAnnotatedClass(ClassificacaoTributariaProduto.class);
            cfg.addAnnotatedClass(ClassificacaoTributariaProdutoCustom.class);
            cfg.addAnnotatedClass(PisCofins.class);
            cfg.addAnnotatedClass(IcmsEntrada.class);
            cfg.addAnnotatedClass(IcmsEntradaCustom.class);
            cfg.addAnnotatedClass(IcmsSaida.class);
            cfg.addAnnotatedClass(IcmsSaidaCustom.class);
            cfg.addAnnotatedClass(RequisicaoAtualizacaoInfoFiscal.class);
            sessionFactory = cfg.buildSessionFactory();
        } catch(Throwable e) {
            System.err.println("Initial Constroyer SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }    
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
