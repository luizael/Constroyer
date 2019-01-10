
package br.com.mixfiscal.prodspedxnfe.services;

import br.com.mixfiscal.prodspedxnfe.dao.ex.DAOException;
import br.com.mixfiscal.prodspedxnfe.dao.mixfiscalamazon.CmfConstroyerProdutoDAO;
import br.com.mixfiscal.prodspedxnfe.dao.own.ClassificacaoTributariaDAO;
import br.com.mixfiscal.prodspedxnfe.domain.own.RequisicaoAtualizacaoInfoFiscal;
import br.com.mixfiscal.prodspedxnfe.dao.own.RequisicaoAtualizacaoInfoFiscalDAO;
import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.dao.util.MixFiscalAmazonHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.enums.EStatusAtualizacaoInfoFiscal;
import br.com.mixfiscal.prodspedxnfe.domain.sped.SPED0000;
import br.com.mixfiscal.prodspedxnfe.ex.PermissaoDiretorioException;
import br.com.mixfiscal.prodspedxnfe.services.ex.ServiceException;
import br.com.mixfiscal.prodspedxnfe.services.relatorio.ImportarExcelParaAtualizarBase;
import br.com.mixfiscal.prodspedxnfe.util.FileInfo;
import br.com.mixfiscal.prodspedxnfe.util.FileUtil;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

public class RequisicaoAtualizacaoInfoFiscalService {  
    private final Logger log;
    private StringBuilder sb;
    private final DateFormat dateFormat;
    private ImportarExcelParaAtualizarBase impExcel; 
    private List<RequisicaoAtualizacaoInfoFiscal> requisicoesParaAtualizacao;
    private RequisicaoAtualizacaoInfoFiscalDAO requisicaoDao;
    private String menssageReq;
    public void setMenssageReq(String value){
        menssageReq = value;
    }
    public String getMenssageReq(){
        return menssageReq;
    }
    public RequisicaoAtualizacaoInfoFiscalService(){  
        this.dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.requisicaoDao = new RequisicaoAtualizacaoInfoFiscalDAO();
        this.log = LogManager.getLogger(RequisicaoAtualizacaoInfoFiscalService.class);
    }    
    
    public void salvar(RequisicaoAtualizacaoInfoFiscal obj){
        try{
            requisicaoDao.salvar(obj);
        }catch(Exception ex){
            log.error(ex.getMessage(),ex);
        }
    }    
    
    public void salvarComArquivo(InputStream input, FileInfo info, RequisicaoAtualizacaoInfoFiscal obj)throws ServiceException{
        boolean criouArquivo = false;
        try{
            try{
                FileUtil.testarPermissaoDiretorio(info.getLocalArmazenamento());//testa o local de armazenamento
            }catch(Exception ex){
                throw new PermissaoDiretorioException(ex);
            }
            ClienteService clService = new ClienteService();
            if((obj.getCliente() != null) && (obj.getCliente().getId() > 0))
                obj.setCliente(clService.selecionarUm(obj.getCliente()));
            
            info.setNome(this.comporNomeDoArquivo(obj, info));//compoe o nome do arquivo
            try{
                criouArquivo = FileUtil.criarArquivo(input, info);
                impExcel = new ImportarExcelParaAtualizarBase();
                int totalColunas = impExcel.obterTotalColunasCsv(info.getLocalArmazenamento() + info.getNome());
                
                if(totalColunas < impExcel.getTotalColunaPadraoCsv()){
                    FileUtil.excluirArquivoNoServidor(info.getLocalArmazenamento(), info.getNome());
                    criouArquivo = false;
                    this.setMenssageReq(" o csv nao pode ser utilizado porque nao esta no padrao de 61 colunas!!");
                    this.log.error(this.getMenssageReq());
                }
                
            }catch(PermissaoDiretorioException ex){
                this.log.error(ex.getMessage(), ex);
            }
            if(criouArquivo){
                Date data = new Date();
                obj.setDataCadastro(data);
                obj.setNomeArquivo(info.getNome());
                obj.setStatusRequisicao(EStatusAtualizacaoInfoFiscal.Pendente);
                this.salvar(obj);
                this.setMenssageReq("Arquivo CSV salvo com sucesso");
            }
        }catch(Exception ex){
            log.error(ex.getMessage(),ex);
            throw new ServiceException(ex.getMessage());
        }
    }      
    
    public List<RequisicaoAtualizacaoInfoFiscal> listar(RequisicaoAtualizacaoInfoFiscal obj, int indicePrimeiroRegistro, int qtdRegistros, String orderProp, String orderDir) throws ServiceException{
        List<RequisicaoAtualizacaoInfoFiscal> listar = new ArrayList<RequisicaoAtualizacaoInfoFiscal>();
        try{
            listar = requisicaoDao.listar(obj, orderProp, orderDir);
        }catch(Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return listar;        
    }    
    
    public void selecionarUm(){}
    
    public void atualizar(RequisicaoAtualizacaoInfoFiscal req){
        try{
            this.requisicaoDao.atualizar(req);
        }catch(Exception ex){
         log.error(ex.getMessage(),ex);
        }
    }
    
    public boolean excluir(RequisicaoAtualizacaoInfoFiscal req, String caminho) throws ServiceException{
        boolean arquivoExcluido = false;
        try{
            //excluir arquivo antes de remover o registro no banco de dados
            req = this.requisicaoDao.selecionarUm(req);
            if(req.getStatusRequisicao().equals(EStatusAtualizacaoInfoFiscal.Pendente) || req.getStatusRequisicao().equals(EStatusAtualizacaoInfoFiscal.Erro)){
                if(req != null && !StringUtil.isNullOrEmpty(req.getNomeArquivo().trim())){
                    arquivoExcluido = FileUtil.excluirArquivoNoServidor(caminho, req.getNomeArquivo());
                    if(arquivoExcluido){
                        requisicaoDao.excluir(req);
                        return true;
                    }
                }else if(req != null && StringUtil.isNullOrEmpty(req.getNomeArquivo().trim())){
                    requisicaoDao.excluir(req);
                    return true;
                } 
            }
            return false;
        }catch(Exception ex){
             if(req != null && req.getNomeArquivo() == null){
                 try{
                    requisicaoDao.excluir(req);
                    return true;
                 }catch(DAOException e){
                      log.error(e.getMessage(),e);
                     throw new ServiceException(e);
                 }
             }
             log.error(ex.getMessage(),ex);
             throw new ServiceException(ex);
        }
    }     
    
    public Long getQtdTotalRegistros(){
        return requisicaoDao.getQtdTotalRegistros();
    }    
    
    private String comporNomeDoArquivo(RequisicaoAtualizacaoInfoFiscal obj, FileInfo info){
        Date data = new Date();
        String caminhoCompleto = "";
        sb = new StringBuilder();        
        sb.append(dateFormat.format(data));//compoe data do upload
        
        if(obj.getCliente() != null && !StringUtil.isNullOrEmpty(obj.getCliente().getNome())){
            sb.append("-" + obj.getCliente().getNome().trim());//compoe nome do cliente
            sb.append("-"+obj.getCliente().getCnpj().trim()); // compoe Cnpj
        }else{
            sb.append("-SemDadosDoCliente");
        }
        if(info != null && !StringUtil.isNullOrEmpty(info.getNome()))
            sb.append("-"+info.getNome());//nome do arquivo original
        
        return sb.toString();
    }
    
    public void processarProximoItemFilaRequisicao(String path)throws Exception {
        RequisicaoAtualizacaoInfoFiscal requisicaoDeException = null;        
        try {            
            requisicaoDao = new RequisicaoAtualizacaoInfoFiscalDAO();
            requisicoesParaAtualizacao = requisicaoDao.retornarProximosDaFila(); 
            
            if(requisicoesParaAtualizacao.size() < 1)
                return;
            
            if (requisicoesParaAtualizacao.get(0).getCliente() != null && requisicoesParaAtualizacao.get(0).getCliente().getId() > 0 && requisicoesParaAtualizacao.get(0).getCliente().getCnpj() != null ) {
                RequisicaoAtualizacaoInfoFiscal requisicao = requisicoesParaAtualizacao.get(0);//impede uma possivel sobreposição no objeto requisicao 
                requisicaoDeException = requisicao;//cria um copia da requisição pra passar ao exception em caso de falha 
                this.log.info(String.format("Importando informações do Cliente de CNPJ %s", requisicao.getCliente().getCnpj()));
                
                try {                    
                    requisicao.setStatusRequisicao(EStatusAtualizacaoInfoFiscal.Executando);//muda o status para executando para iniciar o service
                    salvarRequisicao(requisicao);
                    impExcel = new ImportarExcelParaAtualizarBase();
                    impExcel.percorrerPlanoExcelParaSalvarNoBD(path + requisicao.getNomeArquivo(), requisicao);
                    
                    requisicao.setStatusRequisicao(EStatusAtualizacaoInfoFiscal.Finalizado);//muda o status para finalizado concluindo o processo                    
                    salvarRequisicao(requisicao);
                } catch(Throwable ex) {
                    requisicao.setStatusRequisicao(EStatusAtualizacaoInfoFiscal.Erro);// se der errado marca com status como erro para informar ao usuario qu eo csv adicionado nao foi gravado no banco
                    salvarRequisicao(requisicao);
                    this.log.error(ex.getMessage(),ex);
                }
            }            
        } catch(Exception ex) {
            if (requisicaoDeException.getIdRequisicaoAtualizacaoInfoFiscal() != null) {
                requisicaoDeException.setStatusRequisicao(EStatusAtualizacaoInfoFiscal.Erro);
                salvarRequisicao(requisicaoDeException);
            }
            this.log.error(ex.getMessage(),ex);
            throw new ServiceException(ex.getMessage(), ex);
        }
    }
    
    public void atualizarInformacoesFiscais() {
        try {
                MixFiscalAmazonHibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
                CmfConstroyerProdutoDAO cmfConstroyerProdDAO = new CmfConstroyerProdutoDAO();
                MixFiscalAmazonHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
            }catch(Exception ex) {
                        log.error(ex.getMessage(), ex);
        }
    }
    
    private void atualizarInformacoes() {
        try {
            MixFiscalAmazonHibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            CmfConstroyerProdutoDAO cmfConstroyerProdDAO = new CmfConstroyerProdutoDAO();
            MixFiscalAmazonHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
    
    private void salvarRequisicao(RequisicaoAtualizacaoInfoFiscal req) {
        Session session = null;        
        try {
            session = ConstroyerHibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            RequisicaoAtualizacaoInfoFiscalDAO reqDao 
                   = new RequisicaoAtualizacaoInfoFiscalDAO(session);
            reqDao.atualizar(req);
            session.getTransaction().commit();
        } catch(Exception ex) {
            log.error("Erro ao salvar RequisicaoAtualizacaoInfoFiscal" + ex.getMessage(), ex);
            session.getTransaction().rollback();            
        } finally {
            if (session != null && session.isOpen()) {
                try {
                    session.close();
                } catch(Exception ex) {
                    log.error("Erro ao encerrar sessão. " + ex.getMessage(), ex);
                }
            }
        }
    }
    
    private String criarCaminhoCompletoDoArquivo(RequisicaoAtualizacaoInfoFiscal obj, FileInfo info){
        Date data = new Date();
        String caminhoCompleto = "";
        sb = new StringBuilder();
        sb.append(dateFormat.format(data));
        if(obj.getCliente() != null && !StringUtil.isNullOrEmpty(obj.getCliente().getNome())){
            sb.append("-" + obj.getCliente().getNome().trim());
            sb.append("-"+obj.getCliente().getCnpj().trim());
        }else{
            sb.append("-NaoAchouOCliente");
        }
        if(info != null && !StringUtil.isNullOrEmpty(info.getNome())){
            sb.append("-"+info.getNome());
        }
        return sb.toString();
    }
    
    
}
