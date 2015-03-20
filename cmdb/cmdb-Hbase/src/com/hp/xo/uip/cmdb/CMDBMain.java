package com.hp.xo.uip.cmdb;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hp.xo.uip.cmdb.cache.CmdbCacheService;
import com.hp.xo.uip.cmdb.dao.template.HBaseDaoTemplate;
import com.hp.xo.uip.cmdb.probe.db.avmon.task.SyncAvmonService;

public class CMDBMain {
   public static void main(String arg[]){
	   //System.setProperty("hadoop.home.dir","E:\\opensouce\\hbase\\hadoop-common-2.2.0-bin-master\\");
	   System.out.println("cmdb servcie start... --------");
	   ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring/cmdb.xml");
	   HBaseDaoTemplate hd=(HBaseDaoTemplate)context.getBean("hbaseDaoTemplate");
	   hd.initHbase();
	   CmdbCacheService cache=(CmdbCacheService)context.getBean("cmdbCacheService");
	   cache.initCache();
	   
	   SyncAvmonService syncAvmonService=(SyncAvmonService)context.getBean("syncAvmonService");
//	   syncAvmonService.startTask();
	  // -Djava.library.path=
	   System.out.println("cmdb servcie started --------");
   }
}
