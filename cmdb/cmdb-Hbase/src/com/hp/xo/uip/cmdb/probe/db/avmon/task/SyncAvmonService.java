package com.hp.xo.uip.cmdb.probe.db.avmon.task;

import java.util.Date;
import java.util.Calendar;
import java.util.Timer;

import org.apache.log4j.Logger;

import com.hp.xo.uip.cmdb.service.CmdbService;

public class SyncAvmonService {
    public CmdbService cmdbService;
    public static Logger log=Logger.getLogger(SyncAvmonService.class);
    private Timer timer = null;
	public CmdbService getCmdbService() {
		return cmdbService;
	}

	public void setCmdbService(CmdbService cmdbService) {
		this.cmdbService = cmdbService;
	}
    public void startTask(){
    	Calendar c = null;  
        c = Calendar.getInstance();  
        c.set(Calendar.HOUR_OF_DAY, 10);  
        c.set(Calendar.MINUTE, 00);  
        c.set(Calendar.SECOND, 00);  
        Date date = c.getTime();  
    	timer = new Timer(true);//true 指定为后台线程
    	SyncAvmonTask st= new SyncAvmonTask();
    	st.setCmdbService(cmdbService);
        // 设置任务计划，启动和间隔时间 24 * 60 * 60 * 1000
        timer.scheduleAtFixedRate(st, date,  24 * 60 * 60 * 1000);
        log.debug("avmon data synchronization start at 23:55 every day! beginning "+date);
    }
    public void cacelTask() {
        timer.cancel();
    }
	
}
