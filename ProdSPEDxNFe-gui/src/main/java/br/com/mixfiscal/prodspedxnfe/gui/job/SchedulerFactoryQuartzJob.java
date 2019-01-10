
package br.com.mixfiscal.prodspedxnfe.gui.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerFactoryQuartzJob {
    private final Logger log = LogManager.getLogger(SchedulerFactoryQuartzJob.class);
    private static Scheduler scheduler;    
    static{
        try{
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            if(!scheduler.isStarted()){
                scheduler.start();
            }
        }catch(Exception ex){
            System.out.println("erro no quartz"+ ex.getMessage());
        }
    }    
    public static Scheduler getStdSchedulerFactory(){
        return scheduler;
    }
}
