package com.hp.xo.uip.cmdb.service.impl.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hp.xo.uip.cmdb.domain.CiAttribute;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.domain.ViewCondition;
import com.hp.xo.uip.cmdb.domain.ViewDefine;
import com.hp.xo.uip.cmdb.service.CmdbViewService;
import com.hp.xo.uip.cmdb.util.IdGenerator;

public class CmdbViewServiceImpl implements CmdbViewService{
	private static IdGenerator idg=IdGenerator.createGenerator();
	private ViewDefine vd1=getVD();
	
	public CmdbViewServiceImpl(){
		vd1.setConditions(getVC(vd1.getOid()));
		vd1=initNode(vd1);
		vd1.setContent(getContent());
		
	}


	@Override
	public List<ViewDefine> getViewDefineAll() {
		List<ViewDefine> li=new ArrayList<ViewDefine>();
		li.add(vd1);
		ViewDefine vd2=getVD();
		vd2.setConditions(getVC(vd2.getOid()));
		vd2=initNode(vd2);
		vd2.setContent(getContent());
		li.add(vd2);
		return li;
	}

	
	@Override
	public ViewDefine getViewDefineByName(String name) {
		return vd1;
	}

//	@Override
//	public ViewDefine getViewDefineByOid(Long oid) {
//		ViewDefine vd2=getVD();
//		vd2.setConditions(getVC(vd2.getOid()));
//		vd2=initNode(vd2);
//		vd2.setContent(getContent());
//		return vd2;
//	}

	@Override
	public ViewDefine insertViewDefine(ViewDefine vd) {
		// TODO Auto-generated method stub
		return vd;
	}

	@Override
	public ViewDefine updateView(ViewDefine vd) {
		// TODO Auto-generated method stub
		vd1=vd;
		return vd;
	}

	private String getContent(){
		String re="";
		String path="test/topoDebugData.xml";
		File f=new File(path);
		System.out.println(f.getAbsolutePath());
		try {
			FileInputStream fis=new FileInputStream(f);
			byte[] b=new byte[fis.available()];
			fis.read(b);
			re=new String(b);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        return re;
	}
	private ViewDefine getVD(){
		ViewDefine vd1=new ViewDefine();
		vd1.setOid(10000l);
		vd1.setName("fabric_name"+(int)(Math.random()*100));
		vd1.setTitle("fabric_名称1"+(int)(Math.random()*100));
		vd1.setContent("<view></view>");
		vd1.setComments("comments");
		vd1.setCreator("admin");
		vd1.setCreateTime(new Timestamp(new Date().getTime()));
		vd1.setUpdator("admin");
		vd1.setUpdateTime(new Timestamp(new Date().getTime()));
		return vd1;
	}
	private List<ViewCondition> getVC(Long viewOid){
		List<ViewCondition> li=new ArrayList<ViewCondition>();
		ViewCondition vc=new ViewCondition();
		vc.setOid(idg.generate());
		vc.setName("query"+(int)(Math.random()*100));

		vc.setViewOid(viewOid);
		vc.setRelationName("linked");
		//deepth breadth reverse 均用默认值		
		vc.setStartNodeId(208925714120002l);
		vc.setStartNodeType("Host");
		li.add(vc);
		
//		ViewCondition vc2=new ViewCondition();
//		vc2.setOid(idg.generate());
//		vc2.setName("query"+(int)(Math.random()*100));
//		vc2.setViewOid(viewOid);
//		vc2.setRelationName("linked");
//		//deepth breadth reverse 均用默认值		
//		vc2.setStartNodeId(null);
//		li.add(vc2);
		
		return li;
	}
	
	private ViewDefine initNode(ViewDefine vd){
		List<Node> nodes=new ArrayList<Node>();
		List<RelationNode> relationNodes=new ArrayList<RelationNode>();
		//host
		Node n1=new Node();
		n1.setId(208925714120002l);
		n1.setName("host_bossa");
		n1.setLabel("boss主机");
		n1.setIcon("host");
		n1.setDerivedFrom("Host");
		n1.setParentId(10003l);
		n1.setPath("Ci/CommonCi/Hardware/Host");
		n1.setCreateTime(getTime());
		n1.setUpdateTime(getTime());
		n1.setIsType(false);
		n1.setExchangedId("");
		n1.setVersion(1);
		n1.setDomain("rootDomain");
		CiAttribute ct1= new CiAttribute();
		ct1.setCiId(n1.getId());
		ct1.setDataType("string");
		ct1.setLabel("IP地址");
		ct1.setName("ip");
		ct1.setValue("10.10.10.10");
		n1.getAttributes().put(ct1.getName(),ct1);
		CiAttribute ct2= new CiAttribute();
		ct2.setCiId(n1.getId());
		ct2.setDataType("string");
		ct2.setLabel("操作系统");
		ct2.setName("os");
		ct2.setValue("redHatES5.1 ");
		n1.getAttributes().put(ct2.getName(),ct2);
		
		nodes.add(n1);
		//host Hba
		Node n2=new Node();
		n2.setId(208925714120003l);
		n2.setName("host_bossa_hba1");
		n2.setLabel("boss主机hba卡端口1");
		n2.setIcon("hostHba");
		n2.setDerivedFrom("HostHba");
		n2.setParentId(100031l);
		n2.setPath("Ci/CommonCi/Hardware/HostHba");
		n2.setCreateTime(getTime());
		n2.setUpdateTime(getTime());
		n2.setIsType(false);
		n2.setExchangedId("");
		n2.setVersion(1);
		n2.setDomain("rootDomain");
		CiAttribute ct2_1= new CiAttribute();
		ct2_1.setCiId(n2.getId());
		ct2_1.setDataType("string");
		ct2_1.setLabel("IP地址");
		ct2_1.setName("ip");
		ct2_1.setValue("10.10.10.10");
		n2.getAttributes().put(ct2_1.getName(),ct2_1);
		CiAttribute ct2_2= new CiAttribute();
		ct2_2.setCiId(n2.getId());
		ct2_2.setDataType("string");
		ct2_2.setLabel("HBA卡端口号");
		ct2_2.setName("WWN");
		ct2_2.setValue("0x205e000dec0e2e00");
		n2.getAttributes().put(ct2_2.getName(),ct2_2);
		nodes.add(n2);
//------随机主机 模仿新增---------------------
		//host
		int ra=(int)(Math.random()*10000);
		Node n1_ra=new Node();
		n1_ra.setId(idg.generate());
		n1_ra.setName("host_bossa_"+ra);
		n1_ra.setLabel("boss主机_"+ra);
		n1_ra.setIcon("host");
		n1_ra.setDerivedFrom("Host");
		n1_ra.setParentId(10003l);
		n1_ra.setPath("Ci/CommonCi/Hardware/Host");
		n1_ra.setCreateTime(getTime());
		n1_ra.setUpdateTime(getTime());
		n1_ra.setIsType(false);
		n1_ra.setExchangedId("");
		n1_ra.setVersion(1);
		n1_ra.setDomain("rootDomain");
		CiAttribute ct1_ra= new CiAttribute();
		ct1_ra.setCiId(n1_ra.getId());
		ct1_ra.setDataType("string");
		ct1_ra.setLabel("IP地址");
		ct1_ra.setName("ip");
		ct1.setValue("10.10.10.10");
		n1_ra.getAttributes().put(ct1_ra.getName(),ct1_ra);
		CiAttribute ct2_ra= new CiAttribute();
		ct2_ra.setCiId(n1_ra.getId());
		ct2_ra.setDataType("string");
		ct2_ra.setLabel("操作系统");
		ct2_ra.setName("os");
		ct2_ra.setValue("redHatES5.1 ");
		n1_ra.getAttributes().put(ct2_ra.getName(),ct2_ra);
		
		nodes.add(n1_ra);
		//host Hba
		Node n2_ra=new Node();
		n2_ra.setId(idg.generate());
		n2_ra.setName("host_bossa_hba_"+ra);
		n2_ra.setLabel("boss主机hba卡端口_"+ra);
		n2_ra.setIcon("hostHba");
		n2_ra.setDerivedFrom("HostHba");
		n2_ra.setParentId(100031l);
		n2_ra.setPath("Ci/CommonCi/Hardware/HostHba");
		n2_ra.setCreateTime(getTime());
		n2_ra.setUpdateTime(getTime());
		n2_ra.setIsType(false);
		n2_ra.setExchangedId("");
		n2_ra.setVersion(1);
		n2_ra.setDomain("rootDomain");
		CiAttribute ct2_1_ra= new CiAttribute();
		ct2_1_ra.setCiId(n2_ra.getId());
		ct2_1_ra.setDataType("string");
		ct2_1_ra.setLabel("IP地址");
		ct2_1_ra.setName("ip");
		ct2_1_ra.setValue("10.10.10.10");
		n2.getAttributes().put(ct2_1_ra.getName(),ct2_1_ra);
		CiAttribute ct2_2_ra= new CiAttribute();
		ct2_2_ra.setCiId(n2_ra.getId());
		ct2_2_ra.setDataType("string");
		ct2_2_ra.setLabel("HBA卡端口号");
		ct2_2_ra.setName("WWN");
		ct2_2_ra.setValue("0x205e000dec0e2e00");
		n2_ra.getAttributes().put(ct2_2_ra.getName(),ct2_2_ra);
		nodes.add(n2_ra);
		
		//san port
		Node n3=new Node();
		n3.setId(208925714120004l);
		n3.setName("san1_port1");
		n3.setLabel("交换机端口1");
		n3.setIcon("sanPort");
		n3.setDerivedFrom("SanPort");
		n3.setParentId(100041l);
		n3.setPath("Ci/CommonCi/Hardware/SanPort");
		n3.setCreateTime(getTime());
		n3.setUpdateTime(getTime());
		n3.setIsType(false);
		n3.setExchangedId("");
		n3.setVersion(1);
		n3.setDomain("rootDomain");
		CiAttribute ct3_1= new CiAttribute();
		ct3_1.setCiId(n3.getId());
		ct3_1.setDataType("string");
		ct3_1.setLabel("IP地址");
		ct3_1.setName("ip");
		ct3_1.setValue("10.10.10.10");
		n3.getAttributes().put(ct3_1.getName(),ct3_1);
		CiAttribute ct3_2= new CiAttribute();
		ct3_2.setCiId(n3.getId());
		ct3_2.setDataType("string");
		ct3_2.setLabel("San端口号");
		ct3_2.setName("WWN");
		ct3_2.setValue("0x205e000dec0e2e00");
		n3.getAttributes().put(ct3_2.getName(),ct3_2);
		nodes.add(n3);		
		
		//san 
		Node n4=new Node();
		n4.setId(208925714120005l);
		n4.setName("san1");
		n4.setLabel("san交换机1");
		n4.setIcon("san");
		n4.setDerivedFrom("San");
		n4.setParentId(10004l);
		n4.setPath("Ci/CommonCi/Hardware/San");
		n4.setCreateTime(getTime());
		n4.setUpdateTime(getTime());
		n4.setIsType(false);
		n4.setExchangedId("");
		n4.setVersion(1);
		n4.setDomain("rootDomain");
		CiAttribute ct4_1= new CiAttribute();
		ct4_1.setCiId(n4.getId());
		ct4_1.setDataType("string");
		ct4_1.setLabel("管理IP地址");
		ct4_1.setName("ip");
		ct4_1.setValue("10.10.10.10");
		n4.getAttributes().put(ct4_1.getName(),ct4_1);
		CiAttribute ct4_2= new CiAttribute();
		ct4_2.setCiId(n4.getId());
		ct4_2.setDataType("string");
		ct4_2.setLabel("San型号");
		ct4_2.setName("type");
		ct4_2.setValue("IBM-HS21");
		n4.getAttributes().put(ct4_2.getName(),ct4_2);
		nodes.add(n4);
		
		//zone set
		Node n5=new Node();
		n5.setId(208925714120006l);
		n5.setName("zoneSet1");
		n5.setLabel("zone集合1");
		n5.setIcon("sanZoneSet");
		n5.setDerivedFrom("SanZoneSet");
		n5.setParentId(100043l);
		n5.setPath("Ci/CommonCi/Hardware/SanZoneSet");
		n5.setCreateTime(getTime());
		n5.setUpdateTime(getTime());
		n5.setIsType(false);
		n5.setExchangedId("");
		n5.setVersion(1);
		n5.setDomain("rootDomain");
		nodes.add(n5);

		//san port 2
		Node n6=new Node();
		n6.setId(208925714120007l);
		n6.setName("san1_port2");
		n6.setLabel("交换机端口2");
		n6.setIcon("sanPort");
		n6.setDerivedFrom("SanPort");
		n6.setParentId(100041l);
		n6.setPath("Ci/CommonCi/Hardware/SanPort");
		n6.setCreateTime(getTime());
		n6.setUpdateTime(getTime());
		n6.setIsType(false);
		n6.setExchangedId("");
		n6.setVersion(1);
		n6.setDomain("rootDomain");
		CiAttribute ct6_1= new CiAttribute();
		ct6_1.setCiId(n3.getId());
		ct6_1.setDataType("string");
		ct6_1.setLabel("IP地址");
		ct6_1.setName("ip");
		ct6_1.setValue("10.10.10.10");
		n6.getAttributes().put(ct6_1.getName(),ct6_1);
		CiAttribute ct6_2= new CiAttribute();
		ct6_2.setCiId(n3.getId());
		ct6_2.setDataType("string");
		ct6_2.setLabel("San端口号");
		ct6_2.setName("WWN");
		ct6_2.setValue("0x205e000dec0e2e00");
		n6.getAttributes().put(ct6_2.getName(),ct6_2);
		nodes.add(n6);
		
		//storage netport
		Node n7=new Node();
		n7.setId(208925714120008l);
		n7.setName("storage_a_netport1");
		n7.setLabel("存储A网口1");
		n7.setIcon("storageNetPort1");
		n7.setDerivedFrom("StorageNetPort");
		n7.setParentId(100051l);
		n7.setPath("Ci/CommonCi/Hardware/StorageNetPort");
		n7.setCreateTime(getTime());
		n7.setUpdateTime(getTime());
		n7.setIsType(false);
		n7.setExchangedId("");
		n7.setVersion(1);
		n7.setDomain("rootDomain");
		CiAttribute ct7_1= new CiAttribute();
		ct7_1.setCiId(n7.getId());
		ct7_1.setDataType("string");
		ct7_1.setLabel("IP地址");
		ct7_1.setName("ip");
		ct7_1.setValue("10.10.10.10");
		n7.getAttributes().put(ct7_1.getName(),ct7_1);
		CiAttribute ct7_2= new CiAttribute();
		ct7_2.setCiId(n7.getId());
		ct7_2.setDataType("string");
		ct7_2.setLabel("网卡端口号");
		ct7_2.setName("WWN");
		ct7_2.setValue("0x205e000dec0e2e00");
		n7.getAttributes().put(ct7_2.getName(),ct7_2);
		nodes.add(n7);
		
		//storage
		Node n8=new Node();
		n8.setId(208925714120009l);
		n8.setName("staorageA");
		n8.setLabel("存储A");
		n8.setIcon("storage");
		n8.setDerivedFrom("Storage");
		n8.setParentId(10005l);
		n8.setPath("Ci/CommonCi/Hardware/Storage");
		n8.setCreateTime(getTime());
		n8.setUpdateTime(getTime());
		n8.setIsType(false);
		n8.setExchangedId("");
		n8.setVersion(1);
		n8.setDomain("rootDomain");
		CiAttribute ct8_1= new CiAttribute();
		ct8_1.setCiId(n8.getId());
		ct8_1.setDataType("string");
		ct8_1.setLabel("IP地址");
		ct8_1.setName("ip");
		ct8_1.setValue("10.10.10.10");
		n8.getAttributes().put(ct1.getName(),ct1);
		CiAttribute ct8_2= new CiAttribute();
		ct8_2.setCiId(n1.getId());
		ct8_2.setDataType("string");
		ct8_2.setLabel("型号");
		ct8_2.setName("type");
		ct8_2.setValue("EMC-XXX");
		n8.getAttributes().put(ct8_2.getName(),ct8_2);
		
		
		nodes.add(n8);
		// n1(host)-n2(hbaport)-n3(sanport)-n5(zoneSet) 
		// n5(zoneSet)-n6(sanport)-n7(storageNetPort)-n8(storage)
		// n5(zoneSet)-n4(san)
		
		//--------
		// zoneSet--hbaPort, zoneSet-storageNetPort,
		// hbaPort--Host , storageNetPort--storage
		// host-disk , disk-storageNetPort
		
		RelationNode r=getR("host-hba","主机-网卡");
		r.setSourceId(n1.getId());
		r.setSourceName(n1.getName());
		r.setSourceLable(n1.getLabel());
		r.setSourceType(n1.getDerivedFrom());
		r.setTargetId(n2.getId());
		r.setTargetName(n2.getName());
		r.setTargetLable(n2.getLabel());
		r.setTargetType(n2.getDerivedFrom());
		relationNodes.add(r);
		//-----随机点 关系
		RelationNode r_ra=getR("host-hba","主机-网卡");
		r_ra.setSourceId(n1_ra.getId());
		r_ra.setSourceName(n1_ra.getName());
		r_ra.setSourceLable(n1_ra.getLabel());
		r_ra.setSourceType(n1.getDerivedFrom());
		r_ra.setTargetId(n2_ra.getId());
		r_ra.setTargetName(n2_ra.getName());
		r_ra.setTargetLable(n2_ra.getLabel());
		r_ra.setTargetName(n2_ra.getDerivedFrom());
		relationNodes.add(r_ra);
		//----- 随机点 和 zoneSet关系
		RelationNode r3_ra=getR("hosthba-zoneSet_"+ra,"主机网口-zone设置_"+ra);
		r3_ra.setSourceId(n2_ra.getId());
		r3_ra.setSourceName(n2_ra.getName());
		r3_ra.setSourceLable(n2_ra.getLabel());
		r3_ra.setSourceType(n2_ra.getDerivedFrom());
		r3_ra.setTargetId(n5.getId());
		r3_ra.setTargetName(n5.getName());
		r3_ra.setTargetLable(n5.getLabel());
		r3_ra.setTargetType(n5.getDerivedFrom());
		relationNodes.add(r3_ra);
		
		
		RelationNode r2=getR("hostHba-sanPort","主机网卡-San端口");
		r2.setSourceId(n2.getId());
		r2.setSourceName(n2.getName());
		r2.setSourceLable(n2.getLabel());
		r2.setSourceType(n2.getDerivedFrom());
		r2.setTargetId(n3.getId());
		r2.setTargetName(n3.getName());
		r2.setTargetLable(n3.getLabel());
		r2.setTargetType(n3.getDerivedFrom());
		relationNodes.add(r2);
		
		RelationNode r3=getR("sanPort1-zoneSet","San端口1-zone设置");
		r3.setSourceId(n3.getId());
		r3.setSourceName(n3.getName());
		r3.setSourceLable(n3.getLabel());
		r3.setSourceType(n3.getDerivedFrom());
		r3.setTargetId(n5.getId());
		r3.setTargetName(n5.getName());
		r3.setTargetLable(n5.getLabel());
		r3.setTargetType(n5.getDerivedFrom());
		relationNodes.add(r3);
		
		RelationNode r4=getR("sanPort2-zoneSet","San端口2-zone设置");
		r4.setSourceId(n6.getId());
		r4.setSourceName(n6.getName());
		r4.setSourceLable(n6.getLabel());
		r4.setSourceType(n6.getDerivedFrom());
		r4.setTargetId(n5.getId());
		r4.setTargetName(n5.getName());
		r4.setTargetLable(n5.getLabel());
		r4.setTargetType(n5.getDerivedFrom());
		relationNodes.add(r4);
		
		RelationNode r5=getR("sanPort2-storageNetPort1","San端口2-存储网口1");
		r5.setSourceId(n6.getId());
		r5.setSourceName(n6.getName());
		r5.setSourceLable(n6.getLabel());
		r5.setSourceType(n6.getDerivedFrom());
		r5.setTargetId(n7.getId());
		r5.setTargetName(n7.getName());
		r5.setTargetLable(n7.getLabel());
		r5.setTargetType(n7.getDerivedFrom());
		relationNodes.add(r5);

		RelationNode r6=getR("storageNetPort1-storage","存储网口1-存储服务器");
		r6.setSourceId(n7.getId());
		r6.setSourceName(n7.getName());
		r6.setSourceLable(n7.getLabel());
		r6.setSourceType(n7.getDerivedFrom());
		r6.setTargetId(n8.getId());
		r6.setTargetName(n8.getName());
		r6.setTargetLable(n8.getLabel());
		r6.setTargetType(n8.getDerivedFrom());
		relationNodes.add(r6);
		
		RelationNode r7=getR("zoneSet-san","zoneset-san交换机");
		r7.setSourceId(n5.getId());
		r7.setSourceName(n5.getName());
		r7.setSourceLable(n5.getLabel());
		r7.setSourceType(n5.getDerivedFrom());
		r7.setTargetId(n4.getId());
		r7.setTargetName(n4.getName());
		r7.setTargetLable(n4.getLabel());
		r7.setTargetType(n4.getDerivedFrom());
		relationNodes.add(r7);
		
		for(Node n:nodes){
			vd.putNode(n);
		}
		for(RelationNode n:relationNodes){
			vd.putRelation(n);
		}
		return vd;
	}
	private RelationNode getR(String name,String lable){
		RelationNode r2=new RelationNode();
		r2.setId(idg.generate());
		r2.setName(name);
		r2.setLabel(lable);
		r2.setIcon("line");
		r2.setDerivedFrom("linked");
		r2.setParentId(20000l);
		r2.setPath("CiReference/linked");
		r2.setCreateTime(getTime());
		r2.setUpdateTime(getTime());
		r2.setIsType(false);
		r2.setExchangedId("");
		r2.setVersion(1);
		r2.setDomain("rootDomain");
		return r2;
	}
	private Timestamp getTime(){
		return new Timestamp(new Date().getTime());
	}


	@Override
	public boolean deleteView(String viewName) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main(String arg[]){
		
	}



	@Override
	public ViewDefine getViewDataByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ViewDefine getViewDataByName(String name, Long startNodeId,
			String startNodeType, String relationName, boolean reverse,
			int deepth, List<String> typeFilter) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ViewDefine getViewDataByName(String viewName,
			List<ViewCondition> conditions, List<String> typeFilter) {
		return vd1;
	}


	@Override
	public ViewDefine getViewDataByNameFilter(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ViewDefine getViewDataByNameFilter_host_sto(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void gather(Long nodeId, String nodeType, String relationName,
			Boolean reverse, int deepth, int deepthRecord, List<Node> nodes,
			List<RelationNode> relations, List<String> typeFilter) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Node> getNodeByRelation(String startNodeType, Long startNodeId,
			String relationName, int deepth, String... typeFilter) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Node> getNodeByRelation(String startNodeType, Long startNodeId,
			String relationName, Boolean reverse, int deepth,
			String... typeFilter) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map<String, List<Node>> getNodeRelationMap(String startNodeType,
			Long startNodeId, String relationName, Boolean reverse, int deepth,
			String... typeFilter) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map<String, List<Node>> getNodeRelationMapLv1(String startNodeType,
			Long startNodeId, String relationName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Node> getNodeByRelation(List<Node> startNodes,
			String relationName, Boolean reverse, int deepth,
			String... typeFilter) {
		// TODO Auto-generated method stub
		return null;
	}

}
