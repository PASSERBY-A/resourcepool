package com.hp.xo.uip.cmdb.probe.avmon;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.hp.xo.uip.cmdb.cache.CmdbCacheService;
import com.hp.xo.uip.cmdb.dao.template.HBaseDaoTemplate;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.probe.db.avmon.AvmonKpiToCi;
import com.hp.xo.uip.cmdb.service.CmdbService;
import com.hp.xo.uip.cmdb.service.RepairRecordService;
import com.hp.xo.uip.cmdb.util.IdGenerator;
import com.rmi.test.CmdbClientTest;

@ContextConfiguration(locations={"/spring/cmdbClient.xml"}) 
public class AvmonKpiToCiTest extends AbstractJUnit4SpringContextTests {
	private Logger log=Logger.getLogger(CmdbClientTest.class);
	private IdGenerator idg=IdGenerator.createGenerator();
	@Autowired
	public CmdbService cmdbService;
	
	@Before
	public  void init(){
		URL u=this.getClass().getClassLoader().getResource("log4j_cmdb.properties");
		System.out.println(u.toString());
    	PropertyConfigurator.configure(u);
	}
    @Test
    public void testToCi() throws CmdbException{
//    	String re=cmdbService.syncAvmonCi(true);
//    	log.debug(re);
    	String re2=cmdbService.syncAvmonCi(false);
    	log.debug(re2);
    }
}

