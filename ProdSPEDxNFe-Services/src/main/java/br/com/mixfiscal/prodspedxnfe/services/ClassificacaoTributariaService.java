package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.services.ex.ServiceException;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributaria;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributariaProduto;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsSaida;
import br.com.mixfiscal.prodspedxnfe.dao.own.ClassificacaoTributariaDAO;
import br.com.mixfiscal.prodspedxnfe.dao.own.ClassificacaoTributariaProdutoDAO;
import br.com.mixfiscal.prodspedxnfe.dao.own.IcmsEntradaDAO;
import br.com.mixfiscal.prodspedxnfe.dao.own.IcmsSaidaDAO;
import br.com.mixfiscal.prodspedxnfe.dao.own.PisCofinsDAO;
import com.mysql.jdbc.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

public class ClassificacaoTributariaService {

    private ClassificacaoTributariaDAO classificacaoTribDAO;
    //private ClassificacaoTributariaProdutoCustomDAO clProdCustomTribDAO;
    private ClassificacaoTributariaProdutoDAO classificacaoTribProdDAO;
    private IcmsEntradaDAO icmsEntradaDao;
    private IcmsSaidaDAO icmsSaidaDao;
    private PisCofinsDAO pisCofinsDao;
    private Serializable idGerado;
    private Logger log;
    
    public ClassificacaoTributariaService() {
        classificacaoTribDAO = new ClassificacaoTributariaDAO();
        classificacaoTribProdDAO = new ClassificacaoTributariaProdutoDAO();
        //clProdCustomTribDAO = new ClassificacaoTributariaProdutoCustomDAO();
        icmsEntradaDao = new IcmsEntradaDAO();
        icmsSaidaDao = new IcmsSaidaDAO();
        pisCofinsDao = new PisCofinsDAO();
        log = LogManager.getLogger(ClassificacaoTributariaService.class);
    }
    
    public void salvar(ClassificacaoTributaria obj) throws ServiceException {
        try {
            if (obj != null) {
                if (obj.getId() == null)
                    classificacaoTribDAO.salvar(obj);
                else
                    classificacaoTribDAO.atualizar(obj);
            }
        } catch (Exception ex) {
            log.error("Erro ao salvar ou atualizar uma classificacaoTributaria", ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public void salvarCustom(ClassificacaoTributaria obj) throws ServiceException {
        try {
            ClassificacaoTributaria cl = new ClassificacaoTributaria();
            ClassificacaoTributariaProduto prod = new ClassificacaoTributariaProduto();
            ClassificacaoTributariaProdutoDAO prodDao = new ClassificacaoTributariaProdutoDAO();
            IcmsSaida imcsSaida = new IcmsSaida();
            
            //cl.setCnpjTrib(obj.getCnpjTrib());
            cl.setDataResposta(obj.getDataResposta());
            cl.setProximoCaptcha(cl.getProximoCaptcha());
            cl.setStatus(obj.getStatus());
            // classificação tributaria
            if (cl != null) {
                idGerado = classificacaoTribDAO.salvar(cl);
            }
            // produto
           // prod.setClassificacaoTributariaId((int) idGerado);
            //prod.setCnpjCliente(obj.getCnpjTrib());
            prod.setCodigoProduto(obj.getProduto().getCodigoProduto());
            prod.setDescricao(obj.getProduto().getDescricao());
            prod.setEan(obj.getProduto().getEan());

            if (cl.getProduto() != null) {
                idGerado = prodDao.salvar(prod);
            }

        } catch (Exception ex) {
            log.error("Erro ao salvar custom CFOP", ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public List<ClassificacaoTributaria> listar(ClassificacaoTributaria obj) throws ServiceException {
        List<ClassificacaoTributaria> lista = new ArrayList<>();
        try {
            if (obj != null) {
                lista = classificacaoTribDAO.listar(obj);
            }
        } catch (Exception ex) {
            log.error("Erro ao listar CFOP", ex);
            throw new ServiceException(ex.getMessage());
        }
        return lista;
    }

    public List<ClassificacaoTributaria> listarTudo(String cnpjCliente) throws ServiceException {
        List<ClassificacaoTributaria> lista = new ArrayList<>();
        try {
            if (!StringUtils.isNullOrEmpty(cnpjCliente)) {
                lista = classificacaoTribDAO.listarTudo(cnpjCliente);
            }
        } catch (Exception ex) {
            log.error("Erro ao listar todos os CFOPs", ex);
            throw new ServiceException(ex.getMessage());
        }
        return lista;
    }

    public ClassificacaoTributaria selecionarUm(ClassificacaoTributaria obj) throws ServiceException {
        ClassificacaoTributaria clsTrib = null;
        try {
            if (obj != null) {
                clsTrib = classificacaoTribDAO.selecionarUm(obj);
            }
        } catch (Exception ex) {
            log.error("Erro ao selecionar um CFOP", ex);
            throw new ServiceException(ex.getMessage());
        }
        return clsTrib;
    }

    public ClassificacaoTributaria selecionarPorCNPJCliente(String cnpjCliente) {
        return classificacaoTribDAO.selecionarPorCNPJCliente(cnpjCliente);
    }
    
    public ClassificacaoTributaria selecionarPorCNPJClienteECodigoProduto(String cnpjCliente, String codigoProduto) {
        return classificacaoTribDAO.selecionarPorCNPJClienteECodigoProduto(cnpjCliente, codigoProduto);
    }

    private ClassificacaoTributariaProduto selecionarUmPisCofins(ClassificacaoTributariaProduto obj)
            throws ServiceException {
        PisCofinsDAO pisCofinsDao = new PisCofinsDAO();
        try {
            if (obj.getPisCofins() != null) {
                obj.setPisCofins(pisCofinsDao.selecionarUm(obj.getPisCofins()));
            }
        } catch (Exception ex) {
            log.error("Erro ao selecionar um PisCofins", ex);
            throw new ServiceException(ex.getMessage());
        }
        return obj;
    }

    private ClassificacaoTributariaProduto selecionarUmIcmsEntrada(ClassificacaoTributariaProduto obj)
            throws ServiceException {
        IcmsEntradaDAO icmsEntrada = new IcmsEntradaDAO();

        try {
            if (obj.getIcmsEntrada() != null) {
                obj.setIcmsEntrada(icmsEntrada.selecionarUm(obj.getIcmsEntrada()));
            }
        } catch (Exception ex) {
            log.error("Erro ao selecionar um IcmsEntrada", ex);
            throw new ServiceException(ex.getMessage());
        }
        return obj;
    }

    private ClassificacaoTributariaProduto selecionarUmIcmsSaida(ClassificacaoTributariaProduto obj)
            throws ServiceException {
        IcmsSaidaDAO icmsSaidaDao = new IcmsSaidaDAO();
        try {
            if (obj.getIcmsSaida() != null) {
                obj.setIcmsSaida(icmsSaidaDao.selecionarUm(obj.getIcmsSaida()));
            }
        } catch (Exception ex) {
            log.error("Erro ao selecionar um IcmsSaida", ex);
            throw new ServiceException(ex.getMessage());
        }
        return obj;
    }

    public boolean excluirPorCNPJCliente(String cnpjCliente) throws ServiceException {
        try {
            ClassificacaoTributaria classTrib = classificacaoTribDAO.selecionarPorCNPJCliente(cnpjCliente); 
            if (classTrib != null)
                classificacaoTribDAO.excluir(classTrib);
            return true;
        } catch(Exception ex) {
            throw new ServiceException(ex);            
        }
    }
    
    /*
    public boolean excluirCustomizado(ClassificacaoTributaria obj) throws ServiceException {
        boolean result = false;
        ClassificacaoTributaria cl = new ClassificacaoTributaria();
        ClassificacaoTributariaDAO clDao = new ClassificacaoTributariaDAO();
        ClassificacaoTributariaProdutoCustomDAO clProdCustomDao = new ClassificacaoTributariaProdutoCustomDAO();
        ClassificacaoTributariaProduto prod = new ClassificacaoTributariaProduto();
        ClassificacaoTributariaProdutoDAO prodDao = new ClassificacaoTributariaProdutoDAO();
        List<ClassificacaoTributariaProdutoCustom> lista = new ArrayList<>();

        IcmsEntradaCustomDAO icmsEntradaDao = new IcmsEntradaCustomDAO();
        IcmsSaidaCustomDAO icmsSaidaDao = new IcmsSaidaCustomDAO();
        String cnpj = obj.getCnpjTrib().trim();
        try {
            icmsEntradaDao.excluirCustom(cnpj);
            icmsSaidaDao.excluirCustom(cnpj);
            prodDao.excluirCustom(cnpj);
            clDao.excluirCustom(cnpj);
        } catch (Exception ex) {
            this.log.error("Erro ao excluirCustomizado", ex);
            System.out.println(ex);
        }
        return result;
    }
*/
}
