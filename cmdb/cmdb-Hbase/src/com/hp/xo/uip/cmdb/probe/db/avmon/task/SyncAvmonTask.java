package com.hp.xo.uip.cmdb.probe.db.avmon.task;

import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.service.CmdbService;

public class SyncAvmonTask extends TimerTask{
    private CmdbService cmdbService;
    private Logger log=Logger.getLogger(SyncAvmonTask.class);
	public CmdbService getCmdbService() {
		return cmdbService;
	}

	public void setCmdbService(CmdbService cmdbService) {
		this.cmdbService = cmdbService;
	}

	@Override
	public void run() {
		try {
			log.info("avmon data synchronization start at "+new Date());
			cmdbService.syncAvmonCi(false);
			log.info("avmon data synchronization end at "+new Date());
		} catch (CmdbException e) {
			log.error("", e);
			e.printStackTrace();
		}
	}
  
}
