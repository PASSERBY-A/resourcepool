package com.hp.avmon.notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hp.avmon.notify.utils.SpringContainer;


public class Start {

    private static final Logger log = LoggerFactory.getLogger(Start.class);
    
    private static String version="2.0.0";
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        NotifyWorker sender=(NotifyWorker)SpringContainer.getBean("notifyWorker");
        
        sender.start();
    }

}
