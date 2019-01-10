package br.com.mixfiscal.prodspedxnfe.gui.job;

import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.gui.config.JobManagerConfig;
import br.com.mixfiscal.prodspedxnfe.services.RequisicaoAtualizacaoInfoFiscalService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AtualizadorInfosFiscaisCSVJob implements Job {
    public static final String NOME_JOB = "ATUALIZADOR_INFOS_FISCAIS_CSV_JOB";
    public static final String CAMINHO_ATUALIZADOR_CSV = "caminhoCsv";       
    private final RequisicaoAtualizacaoInfoFiscalService requisicaoService;
    private final Logger log;
    private final JobManagerConfig jobManagerConfig;
    
    public AtualizadorInfosFiscaisCSVJob(){        
        this.requisicaoService = new RequisicaoAtualizacaoInfoFiscalService();
        this.log = LogManager.getLogger(AtualizadorInfosFiscaisCSVJob.class);
        this.jobManagerConfig = JobManagerConfig.getInstance();
    }
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        this.log.info(String.format("Iniciando o Job %s de atualização de CSV", NOME_JOB));
        try{
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().setTimeout(0);
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();            
            this.requisicaoService.processarProximoItemFilaRequisicao(jobManagerConfig.getCaminhoCsv());
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch(Exception ex) {
            this.log.error(String.format("Houve um erro ao executar o Job %s. Msg: %s", NOME_JOB, ex.getMessage()), ex);
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
        } finally {
            this.log.info(String.format("Finalizando o Job %s de atualização de CSV", NOME_JOB));
        }
    }
}
