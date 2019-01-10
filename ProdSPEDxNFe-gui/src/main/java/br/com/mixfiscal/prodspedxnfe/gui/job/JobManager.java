package br.com.mixfiscal.prodspedxnfe.gui.job;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import br.com.mixfiscal.prodspedxnfe.gui.config.JobManagerConfig;

public class JobManager implements ServletContextListener {
    private final Logger log = LogManager.getLogger(JobManager.class);
    private final Map<String, Class<? extends Job>> mapJobs = new HashMap<String, Class<? extends Job>>();;
    private final JobManagerConfig jobManagerConfig = JobManagerConfig.getInstance();
    
    public JobManager() {
        mapJobs.put(VaccumJob.NOME_JOB, VaccumJob.class);  
        mapJobs.put(AtualizadorInfosFiscaisCSVJob.NOME_JOB, AtualizadorInfosFiscaisCSVJob.class);
        mapJobs.put(AtualizadorInfosFiscaisViewJob.NOME_JOB, AtualizadorInfosFiscaisViewJob.class);
    }
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        agendarJobs();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {       
        try{
            SchedulerFactoryQuartzJob
                .getStdSchedulerFactory()
                    .shutdown();
            log.info("Scheduler do Quartz finalizado.");
        }catch(Exception ex){
            log.error("Houve um erro ao tentar finalizar o Scheduler do Quartz.", ex);
        }
    }
    
    private void agendarJobs() {
        if(!jobManagerConfig.isJobsAtivos())
            return;
        
        log.info("INICIANDO AGENDAMENTO DOS JOBS DA APLICAÇÃO");
        for (String nomeJob : mapJobs.keySet()) {
            agendarJob(nomeJob);
        }
        log.info("FINALIZANDO O AGENDDAMENTO DOS JOBS DA APLICAÇÃO");
    }
    
    private void agendarJob(String nomeJob) {
        try{
           
            
            JobDetail job = newJob(mapJobs.get(nomeJob))
                                .withIdentity(nomeJob)
                                .build();
            
            if (nomeJob.equals(VaccumJob.NOME_JOB)) {
                job.getJobDataMap().put(VaccumJob.CAMINHO_VACCUM_EXEC_PROP, jobManagerConfig.getCaminhoExecutavelVacuum());
            }
            
            Trigger trigger = newTrigger()
                                .withIdentity(nomeJob + "_Trigger")
                                .startNow()
                                .withSchedule(CronScheduleBuilder.cronSchedule(jobManagerConfig.getPeriodicidade(nomeJob)))
                                .build();
        
            SchedulerFactoryQuartzJob
                    .getStdSchedulerFactory()
                    .scheduleJob(job, trigger);
            log.info(String.format("Job '%s' agendado com sucesso", nomeJob));
        }catch(Exception ex){
            log.error(String.format("Houve um erro ao tentar agendar o job '%s'", nomeJob), ex);
        }
    }
}
