package com.hp.xo.uip.cmdb.service.impl.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hp.xo.uip.cmdb.domain.CiAttribute;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.service.CmdbService;
import com.hp.xo.uip.cmdb.util.IdGenerator;

public class CmdbServiceImpl implements CmdbService{
	private static IdGenerator idg=IdGenerator.createGenerator();
	List<Node> nodeTypes=new ArrayList<Node>();
	List<RelationNode> relationNodeTypes=new ArrayList<RelationNode>();
	
	Map<String,List<Node>> nodeMap=new HashMap<String,List<Node>>();
	public CmdbServiceImpl(){

		Node ci=getBaseTypeNode("Ci","配置项",null);
		nodeTypes.add(ci);
		Node commonCi=getBaseTypeNode("CommonCi","通用配置项",ci);
		nodeTypes.add(commonCi);
		
		Node hardware=getBaseTypeNode("Hardware","硬件",commonCi);
		nodeTypes.add(hardware);
		
		Node host=getBaseTypeNode("Host","主机",hardware);
		nodeTypes.add(host);
		List<Node> nodes=new ArrayList<Node>();
		for(int i=0;i<5;i++){
		  nodes.add(getBaseTypeNode("Host_"+i,"主机_"+i,host,false));
		}
		nodeMap.put(host.getName(), nodes);
		
		Node hostHba=getBaseTypeNode("HostHba","HBA卡",hardware);
		nodeTypes.add(hostHba);
		List<Node> nodes1=new ArrayList<Node>();
		for(int i=0;i<5;i++){
			  nodes1.add(getBaseTypeNode("HostHba_"+i,"HBA卡_"+i,hostHba,false));
		}
		nodeMap.put(hostHba.getName(), nodes1);
		
		Node hostDisk=getBaseTypeNode("HostDisk","主机磁盘",hardware);
		nodeTypes.add(hostDisk);
		List<Node> nodes2=new ArrayList<Node>();
		for(int i=0;i<5;i++){			  
			  nodes2.add(getBaseTypeNode("HostDisk_"+i,"主机磁盘_"+i,hostDisk,false));			  
		}
		nodeMap.put(hostDisk.getName(), nodes2);
			  
		
		Node san=getBaseTypeNode("San","san交换机",hardware);
		nodeTypes.add(san);
		  List<Node> nodes3=new ArrayList<Node>();		
		for(int i=0;i<5;i++){
			  nodes3.add(getBaseTypeNode("San_"+i,"san交换机_"+i,san,false));
		}
		  nodeMap.put(san.getName(), nodes3);
		
		Node sanPort=getBaseTypeNode("SanPort","san交换机端口",hardware);
		nodeTypes.add(sanPort);
		  List<Node> nodes4=new ArrayList<Node>();
		for(int i=0;i<5;i++){
			  nodes4.add(getBaseTypeNode("SanPort_"+i,"san交换机端口_"+i,sanPort,false));
		}
		  nodeMap.put(sanPort.getName(), nodes4);
			  
		Node sanZoneSet=getBaseTypeNode("SanZoneSet","san交换机Zone",hardware);
		nodeTypes.add(sanZoneSet);
		  List<Node> nodes5=new ArrayList<Node>();
		for(int i=0;i<5;i++){
			  nodes5.add(getBaseTypeNode("SanZoneSet_"+i,"san交换机Zone_"+i,sanZoneSet,false));
		}
		  nodeMap.put(sanZoneSet.getName(), nodes5);
			  

		Node storage=getBaseTypeNode("Storage","存储服务器",hardware);
		nodeTypes.add(storage);
		  List<Node> nodes6=new ArrayList<Node>();
		for(int i=0;i<5;i++){
			  nodes6.add(getBaseTypeNode("Storage_"+i,"存储服务器_"+i,storage,false));
		}
		  nodeMap.put(storage.getName(), nodes6);
		
		Node storageNetPort=getBaseTypeNode("StorageNetPort","存储服务器网口",hardware);
		nodeTypes.add(storageNetPort);
		  List<Node> nodes7=new ArrayList<Node>();
		for(int i=0;i<5;i++){		
			  nodes7.add(getBaseTypeNode("StorageNetPort_"+i,"存储服务器端口_"+i,storageNetPort,false));
		}
		nodeMap.put(storageNetPort.getName(), nodes7);
		
		Node storageLun=getBaseTypeNode("StorageLun","存储服务器Lun",hardware);
		nodeTypes.add(storageLun);
		List<Node> nodes8=new ArrayList<Node>();
		for(int i=0;i<5;i++){
			nodes8.add(getBaseTypeNode("StorageLun_"+i,"存储服务器Lun_"+i,storageLun,false));
		}
		nodeMap.put(storageLun.getName(), nodes8);
		
		RelationNode rci=getBaseTypeRelation("RelationCi","配置项关系",null,true);
		relationNodeTypes.add(rci);
		RelationNode linked=getBaseTypeRelation("linked","链接关系",rci,true);
		relationNodeTypes.add(linked);
				
		
	}
	@Override
	public int deleteNode(String typeName,Long ciId) {
        
		return 0;
	}


	@Override
	public List<Node> getCi() {
		// TODO Auto-generated method stub		
		return null;
	}




	@Override
	public List<Node> getCiByTypeName(String typeName) {
		List<Node> li=nodeMap.get(typeName);
		if(li==null){li=new ArrayList<Node>();}
		return li;
	}

	@Override
	public List<Node> getCiType() {		
		return nodeTypes;
	}


	@Override
	public Node getCiTypeByName(String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RelationNode> getRelationCi() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<RelationNode> getRelationCiByTypeName(String typeName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public RelationNode getRelationTypeByName(String typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node insertNode(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelationNode insertRelation(RelationNode relationNode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String testRmi() {
		
		return "CmdbService invoke successful!";
	}

	@Override
	public Node updateNode(Node node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelationNode updateRelation(RelationNode relationNode) {
		// TODO Auto-generated method stub
		return null;
	}

	private Node getBaseTypeNode(String name,String lable,Node pnode){
		return getBaseTypeNode(name,lable,pnode,true);
	}
	private Node getBaseTypeNode(String name,String lable,Node pnode,boolean istype){
		Node n1=new Node();
		n1.setId(idg.generate());
		n1.setName(name);
		n1.setLabel(lable);
		n1.setIcon(name);
		if(pnode==null){
		 n1.setDerivedFrom("");
		 n1.setParentId(0l);
		 n1.setPath(name);
		}else{
			 n1.setDerivedFrom(pnode.getName());
			 n1.setParentId(pnode.getId());
			 n1.setPath(pnode.getPath()+"/"+name);
		}
		n1.setCreateTime(getTime());
		n1.setUpdateTime(getTime());
		n1.setIsType(istype);
		n1.setExchangedId("");
		n1.setVersion(1);
		n1.setDomain("rootDomain");
		CiAttribute ct1= new CiAttribute();
		ct1.setCiId(n1.getId());
		ct1.setDataType("string");
		ct1.setLabel("扩展属性1");
		ct1.setName("att_1");
		ct1.setValue("");
		n1.getAttributes().put(ct1.getName(),ct1);
		CiAttribute ct2= new CiAttribute();
		ct2.setCiId(n1.getId());
		ct2.setDataType("string");
		ct2.setLabel("扩展属性2");
		ct2.setName("att_2");
		ct2.setValue("");
		n1.getAttributes().put(ct2.getName(),ct2);
        return n1;
	}
	
	private RelationNode getBaseTypeRelation(String name,String lable,RelationNode pnode,boolean istype){
		RelationNode n1=new RelationNode();
		n1.setId(idg.generate());
		n1.setName(name);
		n1.setLabel(lable);
		n1.setIcon(name);
		if(pnode==null){
		 n1.setDerivedFrom("");
		 n1.setParentId(0l);
		 n1.setPath(name);
		}else{
			 n1.setDerivedFrom(pnode.getName());
			 n1.setParentId(pnode.getId());
			 n1.setPath(pnode.getPath()+"/"+name);
		}
		n1.setCreateTime(getTime());
		n1.setUpdateTime(getTime());
		n1.setIsType(istype);
		n1.setExchangedId("");
		n1.setVersion(1);
		n1.setDomain("rootDomain");
		
		n1.setSourceCiTypeNames("Host;HostHba;");
		n1.setTargetCiTypeNames("HostHba");
        return n1;
	}
	
	private Timestamp getTime(){
		return new Timestamp(new Date().getTime());
	}
	
	public static void main(String arg[]){
		CmdbServiceImpl cs=new CmdbServiceImpl();
		Node storageLun=cs.getBaseTypeNode("StorageLun","存储服务器Lun",null);
		System.out.println(storageLun.getMapData());
	}
	@Override
	public int deleteRelation(String typeName, Long ciId) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Node getCiByCiId(String typeName, Long ciId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public RelationNode getRelationCiByCiId(String typeName, long ciId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<RelationNode> getRelationType() {
		return relationNodeTypes;
	}
	@Override
	public Node getCiByName(String typeName, String ciName) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public RelationNode getRelationCiByName(String typeName, String ciName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initAttDef() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String syncAvmonCi(Boolean preView) throws CmdbException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Node>  insertNodeCis(List<Node> nodes) throws CmdbException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<RelationNode>  insertRelationCis(List<RelationNode> nodes)
			throws CmdbException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List getCiByCondition(String typeName, List<Map> conditions,
			boolean isRelation) throws CmdbException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int deleteNodes(String typeName, List<Long> ciId)
			throws CmdbException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getKpiByClass(String className) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getClassByKpi(String kpiCode) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String> getClassViewKpi(String className) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getViewFuncKpi(String func) {
		// TODO Auto-generated method stub
		return null;
	}
}
