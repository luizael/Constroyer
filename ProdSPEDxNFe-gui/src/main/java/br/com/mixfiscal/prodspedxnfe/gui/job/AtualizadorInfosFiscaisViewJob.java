package br.com.mixfiscal.prodspedxnfe.gui.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class AtualizadorInfosFiscaisViewJob implements Job {
    public static final String NOME_JOB = "ATUALIZADOR_INFOS_FISCAIS_VIEW_JOB";
    private final Logger log = LogManager.getLogger(AtualizadorInfosFiscaisViewJob.class);
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {     
        this.log.info(String.format("Iniciando o Job %s de atualização de Informações de Fiscais através da View", NOME_JOB));
        try {
            
        } catch(Exception ex) {
            this.log.info(String.format("Houve um erro ao executar o Job %s de atualização de Informações de Fiscais através da View", NOME_JOB));
        } finally {
            this.log.info(String.format("Finalizando o Job %s de atualização de Informações de Fiscais através da View", NOME_JOB));
        }
    }

}
