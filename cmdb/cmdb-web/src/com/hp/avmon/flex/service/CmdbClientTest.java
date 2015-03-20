package com.hp.avmon.flex.service;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

import com.hp.avmon.remote.Alert;
import com.hp.avmon.remote.IAvmonRemoteService;
import com.hp.avmon.remote.KpiValue;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.ViewCondition;
import com.hp.xo.uip.cmdb.domain.ViewDefine;
import com.hp.xo.uip.cmdb.service.CmdbService;
import com.hp.xo.uip.cmdb.service.CmdbViewService;
import com.hp.xo.uip.cmdb.exception.CmdbException;

@Service("CmdbService")
@RemotingDestination
public class CmdbClientTest {
    private Logger log=Logger.getLogger(CmdbClientTest.class);		
	@Autowired
	public CmdbService cmdbServiceProxy;
	@Autowired
	public CmdbViewService cmdbViewServiceProxy;	
	@Autowired
	public IAvmonRemoteService avmonRemoteService;	
	
	public List<ViewDefine> testView(){
		List<ViewDefine> li= cmdbViewServiceProxy.getViewDefineAll();
		for(ViewDefine vd:li){
			log.debug(vd.getName()+"-"+vd.getNodesMap()+"-"+vd.getRelationNodesMap());
		}
		return li;
	}
	
	public List<Node> testCi(){
		List<Node> li= cmdbServiceProxy.getCiType();
		for(Node n : li){
		  log.debug(n.getName()+":"+n.getLabel());	
		  try {
			log.debug(cmdbServiceProxy.getCiByTypeName(n.getName()).size());
		} catch (CmdbException e) {
			e.printStackTrace();
		}
		}
		return li;
	}
//	public static void main(String arg[]){
//	  ApplicationContext context =  new ClassPathXmlApplicationContext("spring/cmdbClient.xml");
//      CmdbClientTest ct=new CmdbClientTest();
//      ct.initService(context);
//      ct.testCi();
//      ct.testView();
//    }
	
	public List<Node> getNodesByFlex()
	{
	   return  testCi();
	}
	@RemotingInclude
	public List<ViewDefine> getViewByFlex()
	{
		   List<ViewDefine> li= cmdbViewServiceProxy.getViewDefineAll();
		   return  li;
	}
	@RemotingInclude
	public ViewDefine getViewDefineByName(String name){
		   ViewDefine vd = new ViewDefine();
		   //新疆的特殊处理，此处不需要
//		   if(name.indexOf("host") >-1){
//			   vd = ct.cmdbViewService.getViewDataByNameFilter_host_sto(name);
//		   }else{
//			   vd = ct.cmdbViewService.getViewDataByNameFilter(name);
//		   }
		   vd = cmdbViewServiceProxy.getViewDataByName(name);
//		   List<Node> li=vd.getNodesMap().get("Host");
//		   for(Node n:li){
//		     log.debug(n.getName()+":"+n.getId());
//		   }
//		   Node n1=li.get(0);
//		   n1.setId(88610453462062998l);
//		   n1.setName(n1.getName()+"tttttt");
//		   li.add(0, n1);
//		   vd.getNodesMap().put("Host1", li);
		   return vd;
		}
	
	@RemotingInclude
	public List<Node> getAllTypeNodes(){
		   List<Node> listNode = cmdbServiceProxy.getCiType();
		   return listNode;
	}
	
	@RemotingInclude
	public List<Node> getAllMyCIs() throws CmdbException{
		   List<Node> listNode = cmdbServiceProxy.getCi();
		   return listNode;
	}
	
	@RemotingInclude
	public List<Node> getCiByTypeName(String typeName){
		   List<Node> listNode = null;
		try {
			listNode = cmdbServiceProxy.getCiByTypeName(typeName);
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return listNode;
	}
	
	public List<Node> getNodeByRelation(String startNodeType,Long startNodeId,
			String relationName,int deepth,String... typeFilter){
		List<Node> listNode = null;
		listNode = cmdbViewServiceProxy.getNodeByRelation(startNodeType, startNodeId, relationName, deepth, typeFilter);
		 return listNode;
	}
	
	public List<Node> getNodeByRelation2(String startNodeType,Long startNodeId,
			String relationName,Boolean reverse,int deepth,String... typeFilter){
		List<Node> listNode = null;
		listNode = cmdbViewServiceProxy.getNodeByRelation(startNodeType, startNodeId, relationName, reverse, deepth, typeFilter);
		 return listNode;
	}
	
	/**
	 * 更新ViewDefine视图
	 * @param vd
	 * @return
	 */
	@RemotingInclude
	public ViewDefine updateView(ViewDefine vd){
		   ViewDefine newVd = new ViewDefine();
		   newVd.setName(vd.getName());
		   newVd.setOid(vd.getOid());
		   newVd.setComments(vd.getComments());
		   newVd.setTitle(vd.getTitle());
		   newVd.setCreateTime(vd.getCreateTime());
		   newVd.setCreator(vd.getCreator());
		   newVd.setUpdateTime(vd.getUpdateTime());
		   newVd.setUpdator(vd.getUpdator());
		   newVd.setContent(vd.getContent());
		   newVd.setViewType(vd.getViewType());
		   newVd.setTimer(vd.getTimer());
		  // newVd.setConditions(Arrays.asList(vd.getViewConditions()));
		   ViewDefine updatedVd = cmdbViewServiceProxy.updateView(newVd);
		   return updatedVd;
	}

	@RemotingInclude
	public boolean deleteView(String viewName) {
		return cmdbViewServiceProxy.deleteView(viewName);
	}
	
	
	@RemotingInclude
	public ViewDefine addViewDefine(ViewDefine vd,ViewCondition[] viewCondition){
		   ViewDefine newVd = new ViewDefine();
		   newVd.setName(vd.getName());
		   newVd.setOid(vd.getOid());
		   newVd.setComments(vd.getComments());
		   newVd.setTitle(vd.getTitle());
		   newVd.setCreateTime(vd.getCreateTime());
		   newVd.setCreator(vd.getCreator());
		   newVd.setUpdateTime(vd.getUpdateTime());
		   newVd.setUpdator(vd.getUpdator());
		   newVd.setContent(vd.getContent());
		   newVd.setConditions(Arrays.asList(viewCondition));

		   newVd.setViewType(vd.getViewType());

		   ViewDefine vdNew = cmdbViewServiceProxy.insertViewDefine(newVd);

		   return vdNew;
	}
	@RemotingInclude
	public List<Alert> getHighAlertByCiList(List<String> ciNames,String kpiCode) throws RemoteException{
		return avmonRemoteService.getHighAlertByCiList_single(ciNames,kpiCode);
	}
	@RemotingInclude
	public List<KpiValue> getCurKpi(List<String> ciNames,String kpiCode,String viewKpiCode) throws RemoteException{
		log.debug("avmonRemoteService:"+avmonRemoteService+".getCurKpi - "+ciNames+","+kpiCode+","+viewKpiCode);
		return avmonRemoteService.getCurKpi(ciNames,kpiCode,viewKpiCode);
	}
	@RemotingInclude
	public List<KpiValue> getViewFuncCurKpi(List<String> ciNames,String kpiCode,String viewFunc) throws RemoteException{
		log.debug("avmonRemoteService:"+avmonRemoteService+".getCurKpi - "+ciNames+","+kpiCode+","+viewFunc);
		return avmonRemoteService.getViewFuncCurKpi(ciNames,kpiCode,viewFunc);
	}
	@RemotingInclude
	public List<Alert> getHighAlertByView_single(String viewName) throws RemoteException{
		return avmonRemoteService.getHighAlertByView_single(viewName);
	}
	
	@RemotingInclude
	public List<KpiValue> getViewCurKpi(String ciName,String ciKpiCode,String ciType) throws RemoteException{
		return avmonRemoteService.getViewCurKpi(ciName,ciKpiCode,ciType);
	}
}