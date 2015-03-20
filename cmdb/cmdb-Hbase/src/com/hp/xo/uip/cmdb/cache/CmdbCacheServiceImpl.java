package com.hp.xo.uip.cmdb.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hp.xo.uip.cmdb.dao.CmdbDao;
import com.hp.xo.uip.cmdb.dao.template.HBaseDaoTemplate;
import com.hp.xo.uip.cmdb.dao.template.XmlDaoTemplate;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.domain.ViewDefine;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.util.IConstants;

public class CmdbCacheServiceImpl implements CmdbCacheService {
	private static Logger log = Logger.getLogger(CmdbCacheServiceImpl.class);
	private HBaseDaoTemplate hbaseDaoTemplate;
	private CmdbDao cmdbDao;
	private XmlDaoTemplate xmlDaoTemplate;

	public  String splitStr = IConstants.splitStr;
	public  String connectStr = IConstants.connectStr;
	// Map<typeName,node> 类型map
	public static Map<String, Node> ciTypes = new HashMap<String, Node>();
	// public static Map<String, Long> ciTypes_id = new HashMap<String, Long>();
	// Map<relationTypeName,relationNode> 关系类型map
	public static Map<String, RelationNode> relationTypes = new HashMap<String, RelationNode>();
	// map<viewName,viewdefine> 暂不做视图缓存
	// public static Map<String, ViewDefine> views = new HashMap<String,
	// ViewDefine>();
	// map<relationName_sourceNodeType_sourceNodeId,List<relationName_relationNodeId;targetNodeType_nodeId>>
	// 关系正向 实例
	public static Map<String, List<String>> relationIds = new HashMap<String, List<String>>();
	// map<relationName_targetNodeType_targetNodeId,List<relationName_relationNodeId;sourceNodeType_nodeId>>
	//关系反向实例
	public static Map<String, List<String>> relationIds_reverse = new HashMap<String, List<String>>();
    //ci类型 - nodeId集合
	public static Map<String, List<Long>> citNodes = new HashMap<String, List<Long>>();
	//关系类型 - 关系id集合
	public static Map<String, List<Long>> relationtNodes = new HashMap<String, List<Long>>();

	// map<nodeType_nodeId,Node>
	// id - node集合
	public static Map<String, Node> nodes = new HashMap<String, Node>();
	// map<relationType_relationNodeId>
	// id - relation集合
	public static Map<String, RelationNode> relations = new HashMap<String, RelationNode>();

	//处理name和id的对应
	//map<nodeType_nodeName,nodeType_nodeId>
	public static Map<String,String> nodes_name=new HashMap<String,String>();
	public static Map<String,String> relations_name = new HashMap<String,String>();
	
	public Map<String, Node> getCiTypes() {
		return ciTypes;
	}

	public void setCiTypes(Map<String, Node> ciTypes) {
		CmdbCacheServiceImpl.ciTypes = ciTypes;
	}

	public Map<String, RelationNode> getRelationTypes() {
		return relationTypes;
	}

	public void setRelationTypes(Map<String, RelationNode> relationTypes) {
		CmdbCacheServiceImpl.relationTypes = relationTypes;
	}

	

	public Map<String, List<Long>> getCitNodes() {
		return citNodes;
	}

	public void setCitNodes(Map<String, List<Long>> citNodes) {
		this.citNodes = citNodes;
	}

	public List<Node> getCitNodes(String ciType){
		List<Long> li= citNodes.get(ciType);
		List<Node> lin=new ArrayList<Node>();
		for(Long id:li){
		   lin.add(nodes.get(id));
		}
		return lin;
	}
	
	public  Map<String, String> getNodes_name() {
		return nodes_name;
	}

	public  void setNodes_name(Map<String, String> nodesName) {
		nodes_name = nodesName;
	}

	public  Map<String, String> getRelations_name() {
		return relations_name;
	}

	public  void setRelations_name(Map<String, String> relationsName) {
		relations_name = relationsName;
	}

	public Map<String, List<String>> getRelationIds() {
		return relationIds;
	}

	public void setRelationIds(Map<String, List<String>> relationIds) {
		CmdbCacheServiceImpl.relationIds = relationIds;
	}

	public Map<String, List<String>> getRelationIds_reverse() {
		return relationIds_reverse;
	}

	public void setRelationIds_reverse(
			Map<String, List<String>> relationIdsReverse) {
		relationIds_reverse = relationIdsReverse;
	}

	// public Map<String, List<Long>> getCitNodes() {
	// return citNodes;
	// }
	//
	// public void setCitNodes(Map<String, List<Long>> citNodes) {
	// CmdbCacheServiceImpl.citNodes = citNodes;
	// }

	public Map<String, Node> getNodes() {
		return nodes;
	}

	public void setNodes(Map<String, Node> nodes) {
		CmdbCacheServiceImpl.nodes = nodes;
	}

	public Map<String, RelationNode> getRelations() {
		return relations;
	}

	public void setRelations(Map<String, RelationNode> relations) {
		CmdbCacheServiceImpl.relations = relations;
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

	public CmdbDao getCmdbDao() {
		return cmdbDao;
	}

	public void setCmdbDao(CmdbDao cmdbDao) {
		this.cmdbDao = cmdbDao;
	}

	public void refrenshCiTypes() {
		ciTypes = xmlDaoTemplate.readXml();
	}

	public void refrenshRelationTypes() {
		relationTypes = xmlDaoTemplate.readXmlRelation();
	}

	// public void refrenshViews(){
	// Map<String,ViewDefine> ma=new HashMap<String,ViewDefine>();
	// List<ViewDefine> li=xmlDaoTemplate.readViewDefXmlAll();
	// for(ViewDefine vd:li){
	// ma.put(vd.getName(), vd);
	// }
	// views=ma;
	// }

	// nodes
	public void refrenshNodes() throws CmdbException {
		List<Node> li = cmdbDao.getCi();
		nodes = new HashMap<String, Node>();
		for (Node n : li) {
			nodes.put(n.getDerivedFrom() + "_" + n.getId(), n);
			nodes_name.put(n.getDerivedFrom() + "_" + n.getName(), n.getDerivedFrom() + "_" + n.getId());
			List<Long> id_li=citNodes.get(n.getDerivedFrom());
			if(id_li==null)id_li=new ArrayList<Long>();
			id_li.add(n.getId());
			citNodes.put(n.getDerivedFrom(), id_li);
		}
		log.debug("nodes init:"+nodes.values().size());
	}

	public void refrenshNodesByType(String nodeType) throws CmdbException {
		List<Node> li = cmdbDao.getCiByTypeName(nodeType);
		List<Long> id_li= citNodes.remove(nodeType);
		for(Long id:id_li){
			Node n= nodes.get(nodeType+"_"+id);
			if(n!=null)nodes_name.remove(nodeType+"_"+n.getName());
			nodes.remove(nodeType+"_"+id);
		}
		for (Node n : li) {
			nodes.put(n.getDerivedFrom() + connectStr + n.getId(), n);
			nodes_name.put(n.getDerivedFrom() + connectStr + n.getName(),n.getDerivedFrom() + connectStr + n.getId());
			List<Long> id_li2=citNodes.get(n.getDerivedFrom());
			if(id_li2==null)id_li2=new ArrayList<Long>();
			id_li2.add(n.getId());
			citNodes.put(n.getDerivedFrom(), id_li2);
		}
	}

	// relationIds relationIds_reverse relations
	public void refrenshRelations() throws CmdbException {
		List<RelationNode> li = cmdbDao.getRelationCi();
		relations = new HashMap<String, RelationNode>();
		for (RelationNode n : li) {
			insertRelations(n);
		}
		log.debug("relations init:"+relations.values().size());
		log.debug("relationIds init:"+relationIds.values().size());
		log.debug("relationIds_reverse init:"+relationIds_reverse.values().size());
	}
	
	public void refrenshRelationByType(String nodeType) throws CmdbException {
		List<RelationNode> li = cmdbDao.getRelationCiByTypeName(nodeType);
		List<Long> id_li= relationtNodes.remove(nodeType);
		for(Long id:id_li){
			relations.remove(nodeType+"_"+id);
		}
		for (RelationNode n : li) {
			insertRelations(n);
		}
	}	
	public void removeRelations(RelationNode n) {
		relations.remove(n.getDerivedFrom() + connectStr + n.getId());
		relations_name.remove(n.getDerivedFrom() + connectStr + n.getName());
		String relationIdsKey = n.getDerivedFrom() + connectStr + n.getSourceType()
				+ connectStr + n.getSourceId();
		relationIds.remove(relationIdsKey);
		String relationIdsKey_r = n.getDerivedFrom() + connectStr + n.getTargetType()
				+ connectStr + n.getTargetId();
		relationIds_reverse.remove(relationIdsKey_r);
	}
	public List<RelationNode> getRelationTypeByNode(Node node){
		List<RelationNode> li=new ArrayList<RelationNode>();
		String type= node.getDerivedFrom();
		for(RelationNode r:relationTypes.values()){
			String sou=r.getSourceCiTypeNames();
			String tar=r.getTargetCiTypeNames();
			if(sou.contains(splitStr+type+splitStr)
					||sou.startsWith(type+splitStr)
					||sou.endsWith(splitStr+type)){
				li.add(r);
			}else if(tar.contains(splitStr+type+splitStr)
					||tar.startsWith(type+splitStr)
					||tar.endsWith(splitStr+type)){
				li.add(r);
			}
		}
		return li;
	}
	public boolean hasRelation(RelationNode rn){
		return hasRelation(rn.getDerivedFrom(), rn.getSourceType(), rn.getSourceId(), rn.getTargetType(), rn.getTargetId());
	}
	public boolean hasRelation(String relationType,String sourceType,long sourceId,String targetType,long targetId){
		return hasRelation(relationType, sourceType, sourceId, targetType, targetId, false); 
	}
    public boolean hasRelation(String relationType,String sourceType,long sourceId,String targetType,long targetId,boolean reverse){
    	String key=null;
    	if(!reverse){
    	   key=relationType+connectStr+sourceType+connectStr+sourceId;
    	}else{
    	   key=relationType+connectStr+targetType+connectStr+targetId;	
    	}
    	List<String> li= relationIds.get(key);
    	if(li!=null){
    	for(String s:li){
    		String ss[]=s.split(splitStr);
    		String target=targetType+connectStr+targetId;
    		if(target.equals(ss[1])){
    			return true;
    		}
    	}
    	}
    	return false;
    } 
	public void insertRelations(RelationNode n) {
		relations.put(n.getDerivedFrom() + connectStr + n.getId(), n);
		relations_name.put(n.getDerivedFrom() + connectStr + n.getName(),n.getDerivedFrom() + connectStr + n.getId());
		List<Long> id_li2=relationtNodes.get(n.getDerivedFrom());
		if(id_li2==null)id_li2=new ArrayList<Long>();
		id_li2.add(n.getId());
		relationtNodes.put(n.getDerivedFrom(), id_li2);
		
		String relationIdsKey = n.getDerivedFrom() + connectStr + n.getSourceType()
				+ connectStr + n.getSourceId();
		String relationIdsValue = n.getDerivedFrom() + connectStr + n.getId()
				+ splitStr + n.getTargetType() + connectStr + n.getTargetId();
		List<String> rli = relationIds.get(relationIdsKey);
		if (rli == null) {
			rli = new ArrayList<String>();
		}
		rli.add(relationIdsValue);
		relationIds.put(relationIdsKey, rli);
		// map<relationName_targetNodeType_targetNodeId,List<relationName_relationNodeId;sourceNodeType_nodeId>>
		String relationIdsKey_r = n.getDerivedFrom() + connectStr + n.getTargetType()
				+ connectStr + n.getTargetId();
		String relationIdsValue_r = n.getDerivedFrom() + connectStr + n.getId()
				+ splitStr + n.getSourceType() + connectStr + n.getSourceId();
		List<String> rli_r = relationIds_reverse.get(relationIdsKey_r);
		if (rli_r == null) {
			rli_r = new ArrayList<String>();
		}
		rli_r.add(relationIdsValue_r);
		relationIds_reverse.put(relationIdsKey_r, rli_r);
	}

	// 操作过程更新缓存------------------------
	@Override
	public int deleteNode(String ciType, Long ciId) {
		int re = 0;
		Node n = null;
		if (ciId == null || ciId == 0) {
			n = ciTypes.remove(ciType);
		} else {
			n = nodes.remove(ciType + "_" + ciId);
			nodes_name.remove(ciType + "_" + n.getName());
		}
		if (n != null)
			re = 1;
		return re;
	}

	@Override
	public int deleteRelation(String ciType, Long ciId) {
		int re = 0;
		RelationNode n = null;
		if (ciId == null || ciId == 0) {
			n = relationTypes.remove(ciType);
		} else {
			n = relations.get(ciType + "_" + ciId);
			// 更新关系id缓存
			removeRelations(n);
		}
		if (n != null)
			re = 1;
		return re;
	}

	@Override
	public Node insertNode(Node node) {
		if (!node.getIsType()) {
			nodes.put(node.getDerivedFrom() + "_" + node.getId(), node);
			nodes_name.put(node.getDerivedFrom() + "_" + node.getName(), node.getDerivedFrom() + "_" + node.getId());
		} else {
			ciTypes.put(node.getName(), node);
		}
		return node;
	}

	@Override
	public RelationNode insertRelation(RelationNode node) {
		if (!node.getIsType()) {
			relations.put(node.getDerivedFrom() + "_" + node.getId(), node);
			//更新id缓存
			insertRelations(node);
		} else {
			relationTypes.put(node.getName(), node);
		}
		return node;
	}

	@Override
	public Node updateNode(Node node) {
		return insertNode(node);
	}

	@Override
	public RelationNode updateRelation(RelationNode node) {
		return insertRelation(node);
	}

	public void initCache() {
		try {
			refrenshCiTypes();
			refrenshRelationTypes();
			refrenshNodes();
			refrenshRelations();
		} catch (Exception e) {
			log.error("", e);
			e.printStackTrace();
		}
	}

	@Override
	public Node getCiType(String ciTypeName) {
		return getCiTypes().get(ciTypeName);
	}

	@Override
	public Node getNode(String type_Id) {
		return getNodes().get(type_Id);
	}

	@Override
	public String getNodeId_name(String type_name) {
		return getNodeId_name(type_name);
	}

	@Override
	public String getRelationId_name(String type_name) {
		return getRelations_name().get(type_name);
	}
	public RelationNode getRelationNode_name(String type_name) {
		return getRelations(getRelations_name().get(type_name));
	}

	@Override
	public List<String> getRelationIds(String startRelationNode) {
		return getRelationIds().get(startRelationNode);
	}

	@Override
	public List<String> getRelationIds_reverse(String startRelationNode) {
		return getRelationIds_reverse().get(startRelationNode);
	}

	@Override
	public RelationNode getRelationType(String relationType) {
		return getRelationTypes().get(relationType);
	}

	@Override
	public RelationNode getRelations(String type_id) {
		return getRelations().get(type_id);
	}
}
