package com.rmi.test;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.hp.xo.uip.cmdb.dao.template.HBaseDaoTemplate;
import com.hp.xo.uip.cmdb.domain.ChangeRecord;
import com.hp.xo.uip.cmdb.domain.RepairRecord;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.service.ChangeRecordService;
import com.hp.xo.uip.cmdb.service.RepairRecordService;
import com.hp.xo.uip.cmdb.util.IdGenerator;

@ContextConfiguration(locations={"/spring/cmdbClient.xml"}) 
public class RecordCilentTest extends AbstractJUnit4SpringContextTests {
	private Logger log=Logger.getLogger(CmdbClientTest.class);
	private IdGenerator idg=IdGenerator.createGenerator();
	@Autowired
	public RepairRecordService repairServiceProxy;
	@Autowired
	public ChangeRecordService changeServiceProxy;
    
	
	@Before
	public  void init(){
		URL u=this.getClass().getClassLoader().getResource("log4j_cmdb.properties");
		System.out.println(u.toString());
    	PropertyConfigurator.configure(u);
	}
    @Test
    public void testRepair() throws CmdbException{
    	String nodeType="Host";
    	Long nodeId=208925714120002l;
    	Long id=0l;
       for(int i=0;i<15;i++){
    	RepairRecord rr= new RepairRecord();
    	String nodeKey=nodeType+"_"+nodeId;
    	rr.setId(idg.generate());
    	id=rr.getId();
    	rr.setNodeKey(nodeKey);
    	rr.setDamageDeviceNum("damageDeviceNum_"+i);
    	rr.setDeviceType("deviceType");
    	rr.setMaDate(new Timestamp(new Date().getTime()));
    	rr.setMaNum("maNum");
    	rr.setRepairDate(new Timestamp(new Date().getTime()));
    	rr.setRepairType("repairType");
    	rr.setReplaceDeviceNum("replaceDeviceNum");
    	repairServiceProxy.insert(rr);
       }
    	
    	List<RepairRecord> li= repairServiceProxy.getRecordList(nodeType, nodeId,null,5);
    	Long lastId=0l;
    	for(RepairRecord r1:li){
    		log.debug(r1.getDamageDeviceNum());
    		lastId=r1.getId();
    	}
    	List<RepairRecord> li2= repairServiceProxy.getRecordList(nodeType, nodeId,lastId,5);
    	for(RepairRecord r1:li2){
    		log.debug(r1.getDamageDeviceNum());
    		lastId=r1.getId();
    	}
    	
    	RepairRecord r=this.repairServiceProxy.getRecord(nodeType+"_"+nodeId, lastId);
    	r.setDamageDeviceNum("damageDeviceNum-change");
    	repairServiceProxy.update(r);
    	
    	List<RepairRecord> li3=repairServiceProxy.getRecordList(nodeType, nodeId);
    	log.debug("----"+li3.size());
    	List<String> lis=new ArrayList<String>();
    	for(RepairRecord r1:li3){
    		log.debug(r1.getDamageDeviceNum());
    		lis.add(r1.getNodeKey()+"_"+r1.getId());
    	}
    	repairServiceProxy.delete(lis);
    	
    	List<RepairRecord> li4=repairServiceProxy.getRecordList(nodeType, nodeId);
    	log.debug("----tt----"+li4.size());
    }
    @Test
    public void testChange() throws CmdbException{
    	String nodeType="Host";
    	Long nodeId=208925714120002l;
    	Long id=0l;
       for(int i=0;i<15;i++){
    	ChangeRecord rr= new ChangeRecord();
    	String nodeKey=nodeType+"_"+nodeId;
    	rr.setId(idg.generate());
    	id=rr.getId();
    	rr.setNodeKey(nodeKey);
    	rr.setCdate(new Timestamp(new Date().getTime()));
    	rr.setContent("content"+i);
    	rr.setOperator("operator");
//    	rr.setSequence(1111l);
    	
    	changeServiceProxy.insert(rr);
       }
    	
    	List<ChangeRecord> li= changeServiceProxy.getRecordList(nodeType, nodeId,null,5);
    	Long lastId=0l;
    	for(ChangeRecord r1:li){
    		log.debug(r1.getContent());
    		lastId=r1.getId();
    	}
    	List<ChangeRecord> li2= changeServiceProxy.getRecordList(nodeType, nodeId,lastId,5);
    	for(ChangeRecord r1:li2){
    		log.debug(r1.getContent());
    		lastId=r1.getId();
    	}
    	
    	ChangeRecord r=this.changeServiceProxy.getRecord(nodeType+"_"+nodeId, lastId);
    	r.setContent("content-change");
    	changeServiceProxy.update(r);
    	
    	List<ChangeRecord> li3=changeServiceProxy.getRecordList(nodeType, nodeId);
    	log.debug("----"+li3.size());
    	List<String> lis=new ArrayList<String>();
    	for(ChangeRecord r1:li3){
    		log.debug(r1.getContent());
    		lis.add(r1.getNodeKey()+"_"+r1.getId());
    	}
    	changeServiceProxy.delete(lis);
    	
    	List<ChangeRecord> li4=changeServiceProxy.getRecordList(nodeType, nodeId);
    	log.debug("----tt----"+li4.size());
    }
}
