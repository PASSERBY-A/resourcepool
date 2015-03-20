package com.rmi.test;


import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.service.CmdbService;
import com.hp.xo.uip.cmdb.service.CmdbViewService;
import com.hp.xo.uip.cmdb.util.IdGenerator;

@ContextConfiguration(locations={"/spring/cmdbClient.xml"})
public class CmdbQueryTest  extends AbstractJUnit4SpringContextTests {
	 
//	   public Alert getHighAlertByCiList(List<String> ciNameList,String ciKpiCode);
//	   public List<Alert> getAlertByCi(String ciName,String ciKpiCode,Timestamp beginTime,Timestamp endTime);
//	   
//	   public List<KpiDefine> getKpiDefine(); 
//	   public KpiValue getCurKpi(String ciName,String ciKpiCode,String viewKpiCode);
//	   public List<KpiValue> getHisKpi(String ciName,String ciKpiCode,String viewKpiCode,Timestamp beginTime,Timestamp endTime);
	
		private Logger log=Logger.getLogger(CmdbClientTest.class);
		private IdGenerator idg=IdGenerator.createGenerator();
		@Autowired
		public CmdbService cmdbServiceProxy;
		@Autowired
		public CmdbViewService cmdbViewServiceProxy;
		
		@Before
		public  void init(){
			URL u=this.getClass().getClassLoader().getResource("log4j_cmdb.properties");
			System.out.println(u.toString());
	    	PropertyConfigurator.configure(u);
		}
	    @Test
	    public void testHostQuery() throws CmdbException{
            //逻辑关系
	    	
	    }
	}

