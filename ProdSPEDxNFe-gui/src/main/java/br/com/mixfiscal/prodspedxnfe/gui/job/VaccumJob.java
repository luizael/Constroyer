package br.com.mixfiscal.prodspedxnfe.gui.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class VaccumJob implements Job {
    public static final String NOME_JOB = "VACCUM_JOB";
    public static final String CAMINHO_VACCUM_EXEC_PROP = "caminhoExecVacuum";
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Executou o job " + NOME_JOB + ". O caminho do executavel é " + context.getJobDetail().getJobDataMap().getString(CAMINHO_VACCUM_EXEC_PROP));        
    }   
}
