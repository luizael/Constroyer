package br.com.mixfiscal.prodspedxnfe.gui.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JobManagerConfig {
    private static final String PROPERTIES_FILE_NAME = "/jobmanager.properties";
    private static final JobManagerConfig instance = new JobManagerConfig();
    
    private final Logger log = LogManager.getLogger(JobManagerConfig.class); 
    private final Properties props;
    
    private JobManagerConfig() {
        props = new Properties();
        try {
            props.load(JobManagerConfig.class.getResourceAsStream(PROPERTIES_FILE_NAME));          
        } catch (IOException ex) {
            log.error(String.format("Ocorreu um erro ao tentar Ler o arquivo %s.", 
                                    PROPERTIES_FILE_NAME), ex);
        } catch(Exception ex) {
            log.error(String.format("Ocorreu um erro ao tentar carregar o arquivo %s.", 
                                    PROPERTIES_FILE_NAME), ex);
        }
    }
    
    public static JobManagerConfig getInstance() {        
        return instance;
    }
    
    public boolean isJobsAtivos() {
        return Boolean.parseBoolean(props.getProperty("jobsAtivos"));
    }
    
    public String getPeriodicidade(String nomeJob) {
        return props.getProperty(nomeJob);
    }
    
    public String getCaminhoExecutavelVacuum() {
        return props.getProperty("caminhoExecVacuum");
    }  
    
    public String getCaminhoBackup() {
        return props.getProperty("caminhoBackup");
    }
    
    public String getCaminhoCsv(){
        return props.getProperty("caminhoCsv");
    }
}
