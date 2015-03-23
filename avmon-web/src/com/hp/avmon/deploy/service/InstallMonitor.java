package com.hp.avmon.deploy.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmonserver.api.AvmonServer;

@Service
public class InstallMonitor {
    
    private static final Log logger = LogFactory.getLog(InstallMonitor.class);

    //private static volatile List<HashMap> alarmList=null;
    
    //private static volatile Map<String,Alarm> alarmCache=null;
    
    //private final static Object lock = new Object();
    
    
    @Autowired
    private AvmonServer avmonServer;
    
    @Autowired
    private JdbcTemplate jdbc;

    public InstallMonitor(){

    }
  
}
