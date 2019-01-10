
package br.com.mixfiscal.prodspedxnfe.services.jobs;


//import br.com.mixfiscal.prodspedxnfe.services.job.SchedulerFactoryQuartzJob;
//import br.com.mixfiscal.prodspedxnfe.services.job.VacuumJob;
import org.junit.Test;
import org.quartz.Job;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleScheduleBuilder;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;

public class MyJobTestador {
    
    /*@Test
    public void testatJob(){
        //ConstroyerQuartz qtz = new ConstroyerQuartz();
        JobDetail job = newJob(VacuumJob.class)
                .withIdentity("POSTGRES","gp1")
                .build();
        Trigger trigger = newTrigger()
                .withIdentity("trigger1","gp1")
                .startNow().withSchedule(simpleSchedule()
                                        .withIntervalInSeconds(5)
                                        .repeatForever())
                .build();
        try{
           SchedulerFactoryQuartzJob.getStdSchedulerFactory().scheduleJob(job, trigger);
        }catch(Exception ex){
            System.out.println(""+ex.getMessage());
        }
        //scheduler.scheduleJob(job, trigger);
    }
    
    @Test
    public void testarExec(){
        VacuumJob jb = new VacuumJob();
        jb.acessarBatchVacuum();
    }*/
}
