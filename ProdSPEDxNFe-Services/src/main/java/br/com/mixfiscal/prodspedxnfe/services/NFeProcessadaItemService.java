package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.dao.own.NFeProcessadaItemDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ProdSPEDXNFeHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.NFeProcessadaItem;
import br.com.mixfiscal.prodspedxnfe.services.ex.ServiceException;
import java.util.List;

public class NFeProcessadaItemService {
    private NFeProcessadaItemDAO nfeProcItemDao;
    
    public NFeProcessadaItemService() {
        this.nfeProcItemDao = new NFeProcessadaItemDAO();
    }
    
    public List<NFeProcessadaItem> listar() throws ServiceException {
        return this.listar(null);
    }
    
    public List<NFeProcessadaItem> listar(NFeProcessadaItem filtro) throws ServiceException {
        List<NFeProcessadaItem> lista = null;
        try {
            ProdSPEDXNFeHibernateUtil.beginTransaction();
            lista = this.nfeProcItemDao.listar(filtro);
            ProdSPEDXNFeHibernateUtil.commitCurrentTransaction();
            return lista;
        } catch(Exception ex) {
            ProdSPEDXNFeHibernateUtil.rollbackCurrentTransaction();
            throw new ServiceException(ex.getMessage(), ex);
        }
    }
}
