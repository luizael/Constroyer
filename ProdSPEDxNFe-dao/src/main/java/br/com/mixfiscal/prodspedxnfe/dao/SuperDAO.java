package br.com.mixfiscal.prodspedxnfe.dao;

import br.com.mixfiscal.prodspedxnfe.dao.ex.DAOException;
import br.com.mixfiscal.prodspedxnfe.dao.util.HibernateUtil;
import br.com.mixfiscal.prodspedxnfe.dao.util.QuestorHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.io.Serializable;
import static java.lang.Math.log;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

public abstract class SuperDAO<T> {
    // <editor-fold defaultstate="collapsed" desc="Membros Privados">
    private Class<T> classType;
    private Session sessao;
    private Long qtdTotalRegistros;
    private Serializable id;
    private Logger log;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Construtores">
    protected SuperDAO(Class<T> type, Session sessao) {
        this.classType = type;
        this.sessao = sessao;
        this.id = 0;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters e Setters">
    public Long getQtdTotalRegistros() {
        return qtdTotalRegistros;
    }
    
    public Session getSessao() {
    	return sessao;    	
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Públicos">
    public int salvar(T obj) throws DAOException {
       return (int)this.executarOperacao(obj, EOperacao.SALVAR);
    }    
   
    public void atualizar(T obj) throws DAOException {
        this.executarOperacao(obj, EOperacao.ATUALIZAR);
    }

    public void excluir(T obj) throws DAOException {
        this.executarOperacao(obj, EOperacao.EXCLUIR);
    }
    
    public T selecionarUm(T obj) throws DAOException {
        List<T> lista = listar(obj);
        
        if (lista.size() > 0)
            return lista.get(0);
        
        return null;
    }

    public List<T> listar() throws DAOException {
        return listar(null);
    }
    
    public List<T> listar(T obj) throws DAOException {
        return listar(obj, -1, 0, null, null);
    }
    
    public List<T> listar(T obj, int page, int qtdRegistros) throws DAOException {
        return listar(obj, page, qtdRegistros, null, null);
    }
    
    public List<T> listar(T obj, String orderProp, String orderDir) throws DAOException {
        return listar(obj, -1, 0, orderProp, orderDir);
    }
    
    public List<T> listar(T obj, int page, int qtdRegistros, String orderProp, String orderDir) throws DAOException {
        List<T> lista = null;
        try {
            // Conta registros do banco
            Criteria count = sessao.createCriteria(classType, "this");
            addFiltros(obj, count);
            try{
                count.setProjection(Projections.rowCount());
            }catch(Exception ex){}
            
            this.qtdTotalRegistros = (Long)count.uniqueResult();
            int indice = qtdRegistros * (page - 1);            
            
            Criteria filtro = sessao.createCriteria(classType, "this");            
            addFiltros(obj, filtro); 
            if (page > -1)
                filtro.setFirstResult(indice);
            if (qtdRegistros > 0)
                filtro.setMaxResults(qtdRegistros);
            
            // TODO Implementar ordenação
            if (!StringUtil.isNullOrEmpty(orderProp)) {
                EDirecaoOrdenacao dirOrd = EDirecaoOrdenacao.porNome(orderDir);
                //addOrd(filtro, orderProp, dirOrd);
            }
            
            lista = filtro.list(); //Listar(sessao, obj, indicePrimeiroRegistro,  qtdRegistros, orderProp, orderDir);
        } catch (RuntimeException e) {            
            throw new DAOException(String.format("Houve um erro ao tentar listar %s. %s", this.classType.getName(), e.getMessage()), e);
        }

        return lista;
    }    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Membros Abstratos">
    protected abstract void addFiltros(T obj, Criteria ctr);
    protected abstract void addOrd(Criteria ctr, String orderProp, EDirecaoOrdenacao orderDir);
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Privados">
    private enum EOperacao {
        SALVAR,
        ATUALIZAR,
        EXCLUIR
    }

    private Serializable executarOperacao(T obj, EOperacao operacao) throws DAOException {
        if (obj == null)        
            throw new DAOException("O parâmetro obj não pode ser null");
        
        try {
            switch(operacao) {
                case SALVAR: 
                     id = sessao.save(obj); 
                    break;
                case ATUALIZAR: 
                    sessao.update(obj); 
                    break;
                case EXCLUIR: 
                    sessao.delete(obj); break;
            }
            return id;
            //this.transacao.commit();
        } catch (RuntimeException e) {
            //if (this.transacao.isActive()) {
            //    this.transacao.rollback();
            //}
            this.log.error(e.getMessage());     
            throw new DAOException(String.format("Não foi possível %s %s. Erro: %s", operacao, obj.getClass().getName(), e.getMessage()), e);
        } finally {
//            try {
//                if (this.sessao.isOpen()) {
//                    this.sessao.close();
//                }
//            } catch (Throwable e) {
//                System.out.println(String.format("Erro ao fechar a operação de %s. Mensagem: %s", operacao, e.getMessage()));
//            }
        }        
        
    }
    // </editor-fold>
}
