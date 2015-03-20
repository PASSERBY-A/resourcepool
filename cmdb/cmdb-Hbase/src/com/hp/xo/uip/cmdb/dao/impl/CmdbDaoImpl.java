package com.hp.xo.uip.cmdb.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.log4j.Logger;

import com.hp.xo.uip.cmdb.cache.CmdbCacheService;
import com.hp.xo.uip.cmdb.dao.CmdbDao;
import com.hp.xo.uip.cmdb.dao.template.HBaseDaoTemplate;
import com.hp.xo.uip.cmdb.dao.template.XmlDaoTemplate;
import com.hp.xo.uip.cmdb.domain.CiAttribute;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.probe.db.avmon.AvmonKpiToCi;
import com.hp.xo.uip.cmdb.util.IdGenerator;
import com.hp.xo.uip.cmdb.util.StringUtil;

public class CmdbDaoImpl implements CmdbDao {
	private static Logger log = Logger.getLogger(CmdbDaoImpl.class);
	private HBaseDaoTemplate hbaseDaoTemplate;
	private XmlDaoTemplate xmlDaoTemplate;
	private CmdbCacheService cmdbCacheService;
	private AvmonKpiToCi avmonKpiToCi;
	private IdGenerator idg = IdGenerator.createGenerator();

	
	public AvmonKpiToCi getAvmonKpiToCi() {
		return avmonKpiToCi;
	}

	public void setAvmonKpiToCi(AvmonKpiToCi avmonKpiToCi) {
		this.avmonKpiToCi = avmonKpiToCi;
	}

	public HBaseDaoTemplate getHbaseDaoTemplate() {
		return hbaseDaoTemplate;
	}

	public void setHbaseDaoTemplate(HBaseDaoTemplate hbaseDaoTemplate) {
		this.hbaseDaoTemplate = hbaseDaoTemplate;
	}

	public XmlDaoTemplate getXmlDaoTemplate() {
		return xmlDaoTemplate;
	}

	public void setXmlDaoTemplate(XmlDaoTemplate xmlDaoTemplate) {
		this.xmlDaoTemplate = xmlDaoTemplate;
	}

	public CmdbCacheService getCmdbCacheService() {
		return cmdbCacheService;
	}

	public void setCmdbCacheService(CmdbCacheService cmdbCacheService) {
		this.cmdbCacheService = cmdbCacheService;
	}

	public int deleteNode(String ciType, Long ciId) throws CmdbException {
		if (ciId == null || ciId == 0) {
			deleteNodeCit(ciType);
		} else {
			deleteNodeCi(ciType, ciId);
		}
		return 1;
	}

	public Node insertNode(Node node) throws CmdbException {
		if (node.getIsType()) {
			return insertNodeCit(node);
		} else {
			return insertNodeCi(node);
		}
	}

	public Node updateNode(Node node) throws CmdbException {
		if (node.getIsType()) {
			return updateNodeCit(node);
		} else {
			return updateNodeCi(node);
		}
	}

	public int deleteNodeCit(String ciTypeName) throws CmdbException {
		xmlDaoTemplate.writeXml(cmdbCacheService.getCiTypes());
		return 1;
	}

	public Node insertNodeCit(Node node) throws CmdbException {
		xmlDaoTemplate.writeXml(cmdbCacheService.getCiTypes());
		return node;
	}

	public Node updateNodeCit(Node node) throws CmdbException {
		xmlDaoTemplate.writeXml(cmdbCacheService.getCiTypes());
		return node;
	}

	public int deleteNode(String ciType, List<Long> ciIds) throws CmdbException {
		List<String> li = new ArrayList<String>();
		for (Long ciId : ciIds) {
			li.add(ciType + "_" + ciId);
		}
		hbaseDaoTemplate.delete(hbaseDaoTemplate.getDefTableName(), li);
		return li.size();
	}

	public int deleteNodeCi(String ciType, Long ciId) throws CmdbException {
		List<String> li = new ArrayList<String>();
		li.add(ciType + "_" + ciId);
		hbaseDaoTemplate.delete(hbaseDaoTemplate.getDefTableName(), li);
		return 1;
	}

	public Node insertNodeCi(Node node) throws CmdbException {
		if (node.getId() == null || node.getId() == 0) {
			node.setId(idg.generate());
		}
		Node typeNode= cmdbCacheService.getCiTypes().get(node.getDerivedFrom());
		if(typeNode==null){
			String er="node type["+node.getDerivedFrom()+"] is null!";
			log.error(er);
			throw new CmdbException(er);
		}
		String rowKey = node.getDerivedFrom() + "_" + node.getId();
		hbaseDaoTemplate.save(hbaseDaoTemplate.getDefTableName(),
				hbaseDaoTemplate.getDefColFamily(), rowKey, node.getMapData());
		return node;
	}

	public void insertNodeCis(List<Node> nodes) throws CmdbException {
	  List<Put> li=new ArrayList<Put>();	
	  for(Node node:nodes){
		if (node.getId() == null || node.getId() == 0) {
			node.setId(idg.generate());
		}
		Node typeNode= cmdbCacheService.getCiTypes().get(node.getDerivedFrom());
		if(typeNode==null){
			String er="node type["+node.getDerivedFrom()+"] is null!";
			log.error(er);
			throw new CmdbException(er);
		}
		String rowKey = node.getDerivedFrom() + "_" + node.getId();
		li.add(hbaseDaoTemplate.getPut(hbaseDaoTemplate.getDefColFamily(), rowKey, node.getMapData()));
	  }
		hbaseDaoTemplate.save(hbaseDaoTemplate.defTableName, li);
	}
	public Node updateNodeCi(Node node) throws CmdbException {
		String rowKey = node.getDerivedFrom() + "_" + node.getId();
		hbaseDaoTemplate.save(hbaseDaoTemplate.getDefTableName(),
				hbaseDaoTemplate.getDefColFamily(), rowKey, node.getMapData());
		return node;
	}
	public void updateNodeCis(List<Node> nodes) throws CmdbException {
		insertNodeCis(nodes);
	}
//---------------------------------------------
	public List<Node> getCi() throws CmdbException{
		List<Node> li = new ArrayList<Node>();
		Iterator<String> ite = cmdbCacheService.getCiTypes().keySet()
				.iterator();
		while (ite.hasNext()) {
			String key = ite.next();
			li.addAll(getCiByTypeName(key));
		}
		return li;
	}
	public Node getCiByCiId(String typeName,Long ciId)  throws CmdbException{
		List<Cell> li= hbaseDaoTemplate.getRowCellByKey(typeName+"_"+ciId);
		return transferNode(typeName, li);
	}
	public List getCiByTypeName(String typeName) throws CmdbException{
		return getCiByTypeName(typeName, false);
	}	
	
	public List getCiByTypeName(String typeName,boolean isRelation) throws CmdbException{
		List li=new ArrayList<Node>();
		Map<String,List<Cell>> ma=hbaseDaoTemplate.getRowCellByKeyFilter(hbaseDaoTemplate
				.getDefTableName(), typeName + "_000000", typeName + "_999999", -1);
		Iterator<String> ite2= ma.keySet().iterator();
		while(ite2.hasNext()){
		  String k2=ite2.next();
		  if(isRelation){
			li.add(transferRelationNode(typeName, ma.get(k2)));  
		  }else{
		    li.add(transferNode(typeName, ma.get(k2)));
		  }
		}
		return li;
	}
//	Map<String,Object> maf=new HashMap<String,Object>();
//	   maf.put("family", "cf");
//	   maf.put("qualifier","host");
//	   maf.put("compareOp", CompareOp.EQUAL);
//	   maf.put("value", "bossa-a");
//	
//	   Map<String,Object> maf2=new HashMap<String,Object>();
//	   maf2.put("family", "cf");
//	   maf2.put("qualifier","Ip");
//	   maf2.put("compareOp", CompareOp.EQUAL);
//	   maf2.put("value", "10.10.10.100");
//	   
//	   List<Map> li=new ArrayList<Map>();
//	   li.add(maf);
//	   li.add(maf2);
//	   Map mar= ht.QueryByCondition("test2", li);
	public List getCiByCondition(String typeName,List<Map> conditions,boolean isRelation) throws CmdbException{
		List li=new ArrayList<Node>();
		Map<String,List<Cell>> ma=hbaseDaoTemplate.queryByCondition(hbaseDaoTemplate
				.getDefTableName(), typeName + "_000000", typeName + "_999999",conditions);
		Iterator<String> ite2= ma.keySet().iterator();
		while(ite2.hasNext()){
		  String k2=ite2.next();
		  if(isRelation){
			li.add(transferRelationNode(typeName, ma.get(k2)));  
		  }else{
		    li.add(transferNode(typeName, ma.get(k2)));
		  }
		}
		return li;
	}
	
	private Node transferNode(String type, List<Cell> cells) {
		Node tNode = cmdbCacheService.getCiTypes().get(type);
		Node n = new Node();
		if(tNode==null){return n;}
		
		try {
			n=tNode.clone();n.setId(null);			
			Map<String, String> sysProperty = n.getSysMapData();
			for (Cell c : cells) {
				String cName = StringUtil.newStr(CellUtil.cloneQualifier(c));
				String cValue = StringUtil.newStr(CellUtil.cloneValue(c));
				if (sysProperty.containsKey(cName)) {
					if("updateTime".equals(cName)||"createTime".equals(cName)){
					    BeanUtils.setProperty(n, cName, Long.parseLong(cValue));
					}else{
						BeanUtils.setProperty(n, cName, cValue);
					}
					
				}else if(n.getAttributes().get(cName)!=null){
					n.getAttributes().get(cName).setValue(cValue);
				}else{
					CiAttribute catt = new CiAttribute();
					catt.setName(cName);catt.setValue(cValue);
					catt.setLabel(cName+"[已删除]");
					catt.setViewMode(3);
					//暂时不显示 删除属性
					n.getAttributes().put(cName, catt);					
				}
			}
		} catch (Exception e) {
			log.error("", e);
			e.printStackTrace();
		}
		n.setIsType(false);
		//配合avmon , 类对应的kpicode 
		//配合 avmon 展示数据用，ExchangedId=kpiname  name=kpivalue 
		//需要在avmonSync.properties中配置类对应的kpiname:kpicode
		if(n.getExchangedId()==null||"".equals(n.getExchangedId())){
			n.setExchangedId(avmonKpiToCi.getKpiByClass(n.getDerivedFrom()));
		}
		return n;
	}
	private RelationNode transferRelationNode(String type, List<Cell> cells) {
		RelationNode tNode = cmdbCacheService.getRelationTypes().get(type);
		RelationNode n = new RelationNode();
		try {
			n=tNode.clone();
			Map<String, String> sysProperty = n.getMapData();
			for (Cell c : cells) {
				String cName = StringUtil.newStr(CellUtil.cloneQualifier(c));
				String cValue = StringUtil.newStr(CellUtil.cloneValue(c));
				if (sysProperty.containsKey(cName)) {
					if("updateTime".equals(cName)||"createTime".equals(cName)){
					    BeanUtils.setProperty(n, cName, Long.parseLong(cValue));
					}else{
					   BeanUtils.setProperty(n, cName, cValue);
					}
				}else{
					log.error("no property ["+cName+"]");
				}
			}
		} catch (Exception e) {
			log.error("", e);
			e.printStackTrace();
		}
		n.setIsType(false);
		return n;
	}
	/**
	 * 新增 节点操作  （关系类&关系实例）
	 * @param node
	 * @return
	 */
	public RelationNode insertRelation(RelationNode node) throws CmdbException {
		if(node.getIsType()){
			insertRelationCit(node);
		}else{
			insertRelationCi(node);
		}
		return node;
	}
	public RelationNode insertRelationCi(RelationNode node) throws CmdbException {
		if (node.getId() == null || node.getId() == 0) {
			node.setId(idg.generate());
		}
		RelationNode typeNode= cmdbCacheService.getRelationTypes().get(node.getDerivedFrom());
		if(typeNode==null){
			String er="relation node type["+node.getDerivedFrom()+"] is null!";
			log.error(er);
			throw new CmdbException(er);
		}
		String rowKey = node.getDerivedFrom() + "_" + node.getId();
		hbaseDaoTemplate.save(hbaseDaoTemplate.getDefTableName(),
				hbaseDaoTemplate.getDefColFamily(), rowKey, node.getMapData());
		return node;
	}
	public void insertRelationCis(List<RelationNode> nodes) throws CmdbException {
		  List<Put> li=new ArrayList<Put>();	
		  for(RelationNode node:nodes){
			if (node.getId() == null || node.getId() == 0) {
				node.setId(idg.generate());
			}
			RelationNode typeNode= cmdbCacheService.getRelationTypes().get(node.getDerivedFrom());
			if(typeNode==null){
				String er="relation node type["+node.getDerivedFrom()+"] is null!";
				log.error(er);
				throw new CmdbException(er);
			}
			String rowKey = node.getDerivedFrom() + "_" + node.getId();
			li.add(hbaseDaoTemplate.getPut(hbaseDaoTemplate.getDefColFamily(), rowKey, node.getMapData()));
		  }
			hbaseDaoTemplate.save(hbaseDaoTemplate.defTableName, li);
		}
	public RelationNode insertRelationCit(RelationNode node) throws CmdbException {
		if (node.getId() == null || node.getId() == 0) {
			node.setId(idg.generate());
		}
		xmlDaoTemplate.writeXmlRelation(cmdbCacheService.getRelationTypes());
		return node;
	}
	
	public RelationNode updateRelation(RelationNode node) throws CmdbException {
		if(node.getIsType()){
			updateRelationCit(node);
		}else{
			updateRelationCi(node);
		}
		return node;
	}
	public RelationNode updateRelationCi(RelationNode node) throws CmdbException {
		String rowKey = node.getDerivedFrom() + "_" + node.getId();
		hbaseDaoTemplate.save(hbaseDaoTemplate.getDefTableName(),
				hbaseDaoTemplate.getDefColFamily(), rowKey, node.getMapData());
		return node;
	}
	public void updateRelationCis(List<RelationNode> nodes) throws CmdbException {
		updateRelationCis(nodes);
	}
	public RelationNode updateRelationCit(RelationNode node) throws CmdbException {
		xmlDaoTemplate.writeXmlRelation(cmdbCacheService.getRelationTypes());
		return node;
	}
	public int deleteRelation(String ciType, Long ciId) throws CmdbException{
		if (ciId == null || ciId == 0) {
			deleteRelationCit(ciType);
		} else {
			deleteRelationCi(ciType, ciId);
		}
		return 1;
	}
	public int deleteRelationCi(String ciType, Long ciId) throws CmdbException{
		return deleteNodeCi(ciType, ciId);
	}
	public int deleteRelationCit(String ciType) throws CmdbException{
		xmlDaoTemplate.writeXmlRelation(cmdbCacheService.getRelationTypes());
		return 1;
	}

	public List<RelationNode> getRelationCi() throws CmdbException{
		List<RelationNode> li = new ArrayList<RelationNode>();
		Iterator<String> ite = cmdbCacheService.getRelationTypes().keySet()
				.iterator();
		while (ite.hasNext()) {
			String key = ite.next();
			li.addAll(getCiByTypeName(key,true));
		}
		return li;
	}

	public RelationNode getRelationCiByCiId(String typeName,long ciId) throws CmdbException{
		List<Cell> li= hbaseDaoTemplate.getRowCellByKey(typeName+"_"+ciId);
		return transferRelationNode(typeName, li);
	}
	public List<RelationNode> getRelationCiByTypeName(String typeName) throws CmdbException{
		return getCiByTypeName(typeName,true);
	}
	public Map<String,Node> initAttFromFile(Map<String,Node> nodes,File f) throws Exception{
		return xmlDaoTemplate.initAttFromFile(nodes, f);
	}
	public void initAttDef(){
		Map<String,Node> ma= cmdbCacheService.getCiTypes();		
		String p=getClass().getClassLoader().getResource("").getPath();
		log.debug("type def path:"+p);
		File f_host=new File(p+"/HostType.txt");
		File f_net=new File(p+"/NetworkType.txt");
		File f_sto=new File(p+"/StorageType.txt");
		
		try {			
			xmlDaoTemplate.initAttFromFile(ma, f_host);
//			xmlDaoTemplate.initAttFromFile(ma, f_net);
//			xmlDaoTemplate.initAttFromFile(ma, f_sto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		xmlDaoTemplate.writeXml(ma);
	}
	public static void main(String arg[]) {
		Node n = new Node();
		BeanUtils b = new BeanUtils();
		try {
			b.setProperty(n, "name", "-----");
			b.setProperty(n, "updateTime", 1387509561266l);//System.currentTimeMillis()
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("[[[" + n.getName()+n.getUpdateTime());
	}

}
