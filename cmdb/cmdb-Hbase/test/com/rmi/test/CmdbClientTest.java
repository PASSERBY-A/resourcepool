package com.rmi.test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.domain.ViewDefine;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.service.CmdbService;
import com.hp.xo.uip.cmdb.service.CmdbViewService;
import com.hp.xo.uip.cmdb.service.impl.test.CmdbServiceImpl;
import com.hp.xo.uip.cmdb.service.impl.test.CmdbViewServiceImpl;

public class CmdbClientTest {
    private Logger log=Logger.getLogger(CmdbClientTest.class);	
	public CmdbService cmdbService;
	public CmdbViewService cmdbViewService;
	
	public CmdbService getCmdbService() {
		return cmdbService;
	}
	public void setCmdbService(CmdbService cmdbService) {
		this.cmdbService = cmdbService;
	}
	public CmdbViewService getCmdbViewService() {
		return cmdbViewService;
	}
	public void setCmdbViewService(CmdbViewService cmdbViewService) {
		this.cmdbViewService = cmdbViewService;
	}
	public void initService(ApplicationContext context){
		cmdbService = (CmdbService) context.getBean("cmdbServiceProxy");
		cmdbViewService = (CmdbViewService) context.getBean("cmdbViewServiceProxy");
	      String result = cmdbService.testRmi();
	      System.out.println(result);
	}
	
	public void testView(){
		CmdbViewServiceImpl cvt=new CmdbViewServiceImpl();
		String name="view_test_relation";
		ViewDefine vdn=cvt.getViewDefineByName("");
		vdn.setName(name);
		cmdbViewService.insertViewDefine(vdn);
		
		cmdbViewService.updateView(vdn);
		
		List<ViewDefine> li= cmdbViewService.getViewDefineAll();
		for(ViewDefine vd:li){
			log.debug(vd.getName()+"-"+vd.getNodesMap()+"-"+vd.getRelationNodesMap());
		}
		
		ViewDefine vd=cmdbViewService.getViewDefineByName(name);
		log.debug("-1--"+vd);
		ViewDefine vd2=cmdbViewService.getViewDataByName(name);
		log.debug("-2--"+vd2);
		cmdbViewService.deleteView(name);
		
	}
	public void testViewQuery(){
		//Host:10.238.160.65:7610199872737999
		long startNodeId=7610199872737999l;
		String relationName="linked";
		String typeFilter[]={"Host","HostDisk"};
		List<Node> li= cmdbViewService.getNodeByRelation("Host", startNodeId, relationName, 
				1, typeFilter);
		log.debug("----"+li.size());
		
		long startNodeId2=7610199869528999l;
		String typeFilter2[]={"San","SanZoneSet"};
		List<Node> li2= cmdbViewService.getNodeByRelation("San", startNodeId2, relationName, 
				1, typeFilter2);
		log.debug("----"+li2.size());
	}
	public void testViewData(){
		String[] names={"view_fabric_54","view_fabric_55"};
//		String[] names={"view_fabric_54_host","view_fabric_55_host"};		
		for(String name:names){
			log.debug("begin-viewData--"+name);
//			ViewDefine vd2=cmdbViewService.getViewDataByNameFilter_host_sto(name);
			ViewDefine vd2=cmdbViewService.getViewDataByNameFilter(name);		
		Iterator<String> ite= vd2.getNodesMap().keySet().iterator();
		while(ite.hasNext()){
			String t=ite.next();
			List<Node> li=vd2.getNodesMap().get(t);
			log.debug("type:"+t);
			for(Node n:li){
				log.debug("-"+n.getName()+":"+n.getId());
			}
		}
		Iterator<String> ite2= vd2.getRelationNodesMap().keySet().iterator();
		while(ite2.hasNext()){
			String t=ite2.next();
			List<RelationNode> li=vd2.getRelationNodesMap().get(t);
			log.debug("type:"+t);
			for(RelationNode n:li){
				log.debug(n.getSourceType()+","+n.getTargetType()+"----"+n.getName()+":"+n.getId());
			}
		}
		log.debug("End-viewData--"+vd2.getName());
		}
	}
	
	public void testCiList() throws CmdbException{
		List<Node> li= cmdbService.getCiType();
		for(Node n : li){
		  log.debug(n.getName()+":"+n.getLabel());	
		  log.debug(cmdbService.getCiByTypeName(n.getName()).size());
		}
	}
	public void testCi() throws CmdbException{
		CmdbViewServiceImpl cvt=new CmdbViewServiceImpl();
	    Map<String,List<Node>> ma= cvt.getViewDefineByName("v1").getNodesMap();
	    Map<String,List<RelationNode>> mar= cvt.getViewDefineByName("v1").getRelationNodesMap();
	    testNode(ma);
	    testRelationNode(mar);
	}
	private void testNode(Map<String,List<Node>> ma) throws CmdbException{
		Iterator<String> ite= ma.keySet().iterator();	    
	    while(ite.hasNext()){
	    	String key=ite.next();
	    	List<Node> li=ma.get(key);
	    	for(Node n:li){
	    	  Node type=cmdbService.getCiTypeByName(n.getDerivedFrom());
	    	  n.setParentId(type.getId());
	    	  cmdbService.insertNode(n);
	    	}
	    	List<Node> lint=cmdbService.getCiByTypeName(key);
	    	for(Node n:lint){
	    		log.debug("--1--"+n.getName()+"--"+n.getLabel());
	    		log.debug("--2--"+cmdbService.getCiByCiId(n.getDerivedFrom(), n.getId()));
	    		log.debug("--3--"+cmdbService.deleteNode(n.getDerivedFrom(),n.getId()));
	    	}
	    }
	    List<Node> lin= cmdbService.getCi();
	    log.debug(lin+"--"+lin.size());
	}
	private void testRelationNode(Map<String,List<RelationNode>> ma) throws CmdbException{
		Iterator<String> ite= ma.keySet().iterator();	    
	    while(ite.hasNext()){
	    	String key=ite.next();
	    	List<RelationNode> li=ma.get(key);
	    	for(RelationNode n:li){
	    	  cmdbService.insertRelation(n);
	    	}
	    	List<RelationNode> lint=cmdbService.getRelationCiByTypeName(key);
	    	for(RelationNode n:lint){
	    		log.debug("--1--"+n.getName()+"--"+n.getLabel());
	    		log.debug("--2--"+cmdbService.getRelationCiByCiId(n.getDerivedFrom(), n.getId()));
//	    		log.debug("--3--"+cmdbService.deleteRelation(n.getDerivedFrom(),n.getId()));
	    	}
	    }
	    List<RelationNode> lin= cmdbService.getRelationCi();
	    log.debug(lin+"--"+lin.size());
	}
	
	public void testCiType() throws CmdbException, IllegalAccessException, InvocationTargetException{
//		CmdbServiceImpl testCS=new CmdbServiceImpl();
		List<Node> li= cmdbService.getCiType();
		List<RelationNode> lir= cmdbService.getRelationType();
		Node nt=new Node();
		for(Node n : li){
		  if("Host".equals(n.getName())){
			  BeanUtils.copyProperties(nt, n);
		   } 	
		  log.debug(n.getName()+":"+n.getLabel());
		}
		nt.setName("HostDiskTest");
		cmdbService.insertNode(nt);
		cmdbService.deleteNode("HostDiskTest",null);
		
		
		RelationNode ntr=new RelationNode();
		for(RelationNode n : lir){
			if("linked".equals(n.getName())){
			   BeanUtils.copyProperties(ntr, n);
			}	
		  log.debug(n.getName()+":"+n.getLabel());
		}
		ntr.setName("linkedTest");
		cmdbService.insertRelation(ntr);
		cmdbService.deleteRelation("linkedTest",null);
		
	}
	public void initDefAtt(){
		cmdbService.initAttDef();
	}
	public static void main(String arg[]){
	  ApplicationContext context =  new ClassPathXmlApplicationContext("spring/cmdbClient.xml");
      CmdbClientTest ct=new CmdbClientTest();
      ct.initService(context);
      try {
       // ct.testCiType();    	  
		//ct.testCi();
		ct.testView();
    	//ct.initDefAtt();
//  	ct.testViewData();
//    	ct.testViewQuery();  

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
    }
}
