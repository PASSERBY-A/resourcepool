package com.hp.xo.uip.cmdb.dao.impl.xml;

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
import com.hp.xo.uip.cmdb.dao.impl.CmdbDaoImpl;
import com.hp.xo.uip.cmdb.dao.template.HBaseDaoTemplate;
import com.hp.xo.uip.cmdb.dao.template.XmlDaoTemplate;
import com.hp.xo.uip.cmdb.domain.CiAttribute;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.probe.db.avmon.AvmonKpiToCi;
import com.hp.xo.uip.cmdb.util.IdGenerator;
import com.hp.xo.uip.cmdb.util.StringUtil;

public class CmdbDaoXmlImpl extends CmdbDaoImpl implements CmdbDao {
	private static Logger log = Logger.getLogger(CmdbDaoImpl.class);

	private XmlDaoTemplate xmlDaoTemplate;
	private CmdbCacheService cmdbCacheService;
	private IdGenerator idg = IdGenerator.createGenerator();


	public void persistenceNode(String ciType){
		List<Node> li= cmdbCacheService.getCitNodes(ciType);
		xmlDaoTemplate.writeXmlData(li, ciType);		
	}
	public int deleteNode(String ciType, List<Long> ciIds) throws CmdbException {
		persistenceNode(ciType);
		return ciIds.size();
	}

	public int deleteNodeCi(String ciType, Long ciId) throws CmdbException {
		persistenceNode(ciType);
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
		persistenceNode(node.getDerivedFrom());
		return node;
	}

	public void insertNodeCis(List<Node> nodes) throws CmdbException {
	  List<String> li=new ArrayList<String>();	
	  for(Node node:nodes){
		if (node.getId() == null || node.getId() == 0) {
			node.setId(idg.generate());
		}
		Node typeNode= cmdbCacheService.getCiTypes().get(node.getDerivedFrom());
		if(typeNode==null){
			String er="node type["+node.getDerivedFrom()+"] is null!";
			log.error(er);
			throw new CmdbException(er);
		}else{
			if(!li.contains(node.getDerivedFrom())){
				li.add(node.getDerivedFrom());
			}
		}		
	  }
	  for(String s:li){
	    persistenceNode(s);
	  }
	}
	public Node updateNodeCi(Node node) throws CmdbException {
		persistenceNode(node.getDerivedFrom());
		return node;
	}
	public void updateNodeCis(List<Node> nodes) throws CmdbException {
		List<String> li=new ArrayList<String>();
		for(Node n:nodes){
		  if(!li.contains(n.getDerivedFrom())){li.add(n.getDerivedFrom());};		  
		}
		for(String s:li){
			persistenceNode(s);
		}
	}
//---------------------------------------------
//	public Node getCiByCiId(String typeName,Long ciId)  throws CmdbException{
//		List<Cell> li= hbaseDaoTemplate.getRowCellByKey(typeName+"_"+ciId);
//		return transferNode(typeName, li);
//	}
//	public List getCiByTypeName(String typeName) throws CmdbException{
//		return getCiByTypeName(typeName, false);
//	}	
//	
//	public List getCiByTypeName(String typeName,boolean isRelation) throws CmdbException{
//		List li=new ArrayList<Node>();
//		Map<String,List<Cell>> ma=hbaseDaoTemplate.getRowCellByKeyFilter(hbaseDaoTemplate
//				.getDefTableName(), typeName + "_000000", typeName + "_999999", -1);
//		Iterator<String> ite2= ma.keySet().iterator();
//		while(ite2.hasNext()){
//		  String k2=ite2.next();
//		  if(isRelation){
//			li.add(transferRelationNode(typeName, ma.get(k2)));  
//		  }else{
//		    li.add(transferNode(typeName, ma.get(k2)));
//		  }
//		}
//		return li;
//	}
////	Map<String,Object> maf=new HashMap<String,Object>();
////	   maf.put("family", "cf");
////	   maf.put("qualifier","host");
////	   maf.put("compareOp", CompareOp.EQUAL);
////	   maf.put("value", "bossa-a");
////	
////	   Map<String,Object> maf2=new HashMap<String,Object>();
////	   maf2.put("family", "cf");
////	   maf2.put("qualifier","Ip");
////	   maf2.put("compareOp", CompareOp.EQUAL);
////	   maf2.put("value", "10.10.10.100");
////	   
////	   List<Map> li=new ArrayList<Map>();
////	   li.add(maf);
////	   li.add(maf2);
////	   Map mar= ht.QueryByCondition("test2", li);
//	public List getCiByCondition(String typeName,List<Map> conditions,boolean isRelation) throws CmdbException{
//		List li=new ArrayList<Node>();
//		Map<String,List<Cell>> ma=hbaseDaoTemplate.queryByCondition(hbaseDaoTemplate
//				.getDefTableName(), typeName + "_000000", typeName + "_999999",conditions);
//		Iterator<String> ite2= ma.keySet().iterator();
//		while(ite2.hasNext()){
//		  String k2=ite2.next();
//		  if(isRelation){
//			li.add(transferRelationNode(typeName, ma.get(k2)));  
//		  }else{
//		    li.add(transferNode(typeName, ma.get(k2)));
//		  }
//		}
//		return li;
//	}
//	
//	/**
//	 * 新增 节点操作  （关系类&关系实例）
//	 * @param node
//	 * @return
//	 */
//	public RelationNode insertRelation(RelationNode node) throws CmdbException {
//		if(node.getIsType()){
//			insertRelationCit(node);
//		}else{
//			insertRelationCi(node);
//		}
//		return node;
//	}
//	public RelationNode insertRelationCi(RelationNode node) throws CmdbException {
//		if (node.getId() == null || node.getId() == 0) {
//			node.setId(idg.generate());
//		}
//		RelationNode typeNode= cmdbCacheService.getRelationTypes().get(node.getDerivedFrom());
//		if(typeNode==null){
//			String er="relation node type["+node.getDerivedFrom()+"] is null!";
//			log.error(er);
//			throw new CmdbException(er);
//		}
//		String rowKey = node.getDerivedFrom() + "_" + node.getId();
//		hbaseDaoTemplate.save(hbaseDaoTemplate.getDefTableName(),
//				hbaseDaoTemplate.getDefColFamily(), rowKey, node.getMapData());
//		return node;
//	}
//	public void insertRelationCis(List<RelationNode> nodes) throws CmdbException {
//		  List<Put> li=new ArrayList<Put>();	
//		  for(RelationNode node:nodes){
//			if (node.getId() == null || node.getId() == 0) {
//				node.setId(idg.generate());
//			}
//			RelationNode typeNode= cmdbCacheService.getRelationTypes().get(node.getDerivedFrom());
//			if(typeNode==null){
//				String er="relation node type["+node.getDerivedFrom()+"] is null!";
//				log.error(er);
//				throw new CmdbException(er);
//			}
//			String rowKey = node.getDerivedFrom() + "_" + node.getId();
//			li.add(hbaseDaoTemplate.getPut(hbaseDaoTemplate.getDefColFamily(), rowKey, node.getMapData()));
//		  }
//			hbaseDaoTemplate.save(hbaseDaoTemplate.defTableName, li);
//		}
//	
//	public RelationNode updateRelation(RelationNode node) throws CmdbException {
//		if(node.getIsType()){
//			updateRelationCit(node);
//		}else{
//			updateRelationCi(node);
//		}
//		return node;
//	}
//	public RelationNode updateRelationCi(RelationNode node) throws CmdbException {
//		String rowKey = node.getDerivedFrom() + "_" + node.getId();
//		hbaseDaoTemplate.save(hbaseDaoTemplate.getDefTableName(),
//				hbaseDaoTemplate.getDefColFamily(), rowKey, node.getMapData());
//		return node;
//	}
//	public void updateRelationCis(List<RelationNode> nodes) throws CmdbException {
//		updateRelationCis(nodes);
//	}
//	public int deleteRelation(String ciType, Long ciId) throws CmdbException{
//		if (ciId == null || ciId == 0) {
//			deleteRelationCit(ciType);
//		} else {
//			deleteRelationCi(ciType, ciId);
//		}
//		return 1;
//	}
//	public int deleteRelationCi(String ciType, Long ciId) throws CmdbException{
//		return deleteNodeCi(ciType, ciId);
//	}
//
//	public List<RelationNode> getRelationCi() throws CmdbException{
//		List<RelationNode> li = new ArrayList<RelationNode>();
//		Iterator<String> ite = cmdbCacheService.getRelationTypes().keySet()
//				.iterator();
//		while (ite.hasNext()) {
//			String key = ite.next();
//			li.addAll(getCiByTypeName(key,true));
//		}
//		return li;
//	}
//
//	public RelationNode getRelationCiByCiId(String typeName,long ciId) throws CmdbException{
//		List<Cell> li= hbaseDaoTemplate.getRowCellByKey(typeName+"_"+ciId);
//		return transferRelationNode(typeName, li);
//	}
//	public List<RelationNode> getRelationCiByTypeName(String typeName) throws CmdbException{
//		return getCiByTypeName(typeName,true);
//	}

}
