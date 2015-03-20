package com.hp.xo.uip.cmdb.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hp.xo.uip.cmdb.cache.CmdbCacheService;
import com.hp.xo.uip.cmdb.dao.CmdbDao;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.probe.db.avmon.AvmonKpiToCi;
import com.hp.xo.uip.cmdb.service.CmdbService;
import com.hp.xo.uip.cmdb.util.IdGenerator;

public class CmdbServiceImpl implements CmdbService{

    private static Logger log=Logger.getLogger(CmdbServiceImpl.class);
    private IdGenerator idg = IdGenerator.createGenerator();
    private CmdbDao cmdbDao;
    private CmdbCacheService cmdbCacheService;
    private AvmonKpiToCi avmonKpiToCi;
    
	public AvmonKpiToCi getAvmonKpiToCi() {
		return avmonKpiToCi;
	}

	public void setAvmonKpiToCi(AvmonKpiToCi avmonKpiToCi) {
		this.avmonKpiToCi = avmonKpiToCi;
	}

	public CmdbDao getCmdbDao() {
		return cmdbDao;
	}

	public void setCmdbDao(CmdbDao cmdbDao) {
		this.cmdbDao = cmdbDao;
	}

	public CmdbCacheService getCmdbCacheService() {
		return cmdbCacheService;
	}

	public void setCmdbCacheService(CmdbCacheService cmdbCacheService) {
		this.cmdbCacheService = cmdbCacheService;
	}


	@Override
	public Node updateNode(Node node) throws CmdbException {
		cmdbCacheService.updateNode(node);
		return cmdbDao.updateNode(node);
	}
	@Override
	public RelationNode updateRelation(RelationNode relationNode) throws CmdbException {
		cmdbCacheService.updateRelation(relationNode);
		return cmdbDao.updateRelation(relationNode);
	}
	@Override
	public int deleteNode(String typeName,Long ciId) throws CmdbException {
		cmdbCacheService.deleteNode(typeName, ciId);
		//TODO 删除节点对应的关系 
		return cmdbDao.deleteNode(typeName, ciId);
	}
	public int deleteNodes(String typeName,List<Long> ciId) throws CmdbException {
	   for(Long id:ciId){	 
		cmdbCacheService.deleteNode(typeName, id);
		//TODO 删除节点对应的关系
		cmdbDao.deleteNode(typeName, id);
	   }
		return ciId.size(); 
	}
	@Override
	public int deleteRelation(String typeName, Long ciId) throws CmdbException {
		cmdbCacheService.deleteRelation(typeName, ciId);
		return cmdbDao.deleteRelation(typeName, ciId);
	}
	@Override
	public Node insertNode(Node node) throws CmdbException {
		if (node.getId() == null || node.getId() == 0) {
			node.setId(idg.generate());
		}		
		//配合 avmon 展示数据用，ExchangedId=kpiname  name=kpivalue 
		//需要在avmonSync.properties中配置类对应的kpiname:kpicode
		if(node.getExchangedId()==null||"".equals(node.getExchangedId())){
			node.setExchangedId(getKpiByClass(node.getDerivedFrom()));
		}
		cmdbCacheService.insertNode(node);
		return cmdbDao.insertNode(node);
	}

	@Override
	public RelationNode insertRelation(RelationNode relationNode) throws CmdbException {
		if (relationNode.getId() == null || relationNode.getId() == 0) {
			relationNode.setId(idg.generate());
		}
		
		if(!relationNode.getIsType()&&cmdbCacheService.hasRelation(relationNode)){
			log.error("relationNode["+relationNode.getName()+"] is existing! can not insert Hbase");
			return relationNode;
		}
		cmdbCacheService.insertRelation(relationNode);
		return cmdbDao.insertRelation(relationNode);
	}
	
//-------------------------------------------------------------

	@Override
	public List<Node> getCi() throws CmdbException {
		List<Node> li = new ArrayList<Node>();
		Iterator<String> ite = cmdbCacheService.getCiTypes().keySet()
				.iterator();
		while (ite.hasNext()) {
			String key = ite.next();
			li.addAll(getCiByTypeName(key));
		}
		return li;
	}

	@Override
	public Node getCiByCiId(String typeName,Long ciId) throws CmdbException {
		return cmdbDao.getCiByCiId(typeName, ciId);
	}

//	@Override
//	public Node getCiByName(String typeName, String ciName) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public List<Node> getCiByTypeId(long typeId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<Node> getCiByTypeName(String typeName) throws CmdbException {
		return cmdbDao.getCiByTypeName(typeName);
	}

	@Override
	public List<Node> getCiType() {
		Map<String, Node> ma = cmdbCacheService.getCiTypes();
		List<Node> li = new ArrayList<Node>(ma.values());
		return li;
	}

//	@Override
//	public Node getCiTypeById(long typeId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Node getCiTypeByName(String typeName) throws CmdbException {
		return cmdbCacheService.getCiTypes().get(typeName);
	}

	@Override
	public List<RelationNode> getRelationCi() throws CmdbException {
		return cmdbDao.getRelationCi();
	}


	@Override
	public List<RelationNode> getRelationCiByTypeName(String typeName) throws CmdbException {
		return cmdbDao.getRelationCiByTypeName(typeName);
	}


	@Override
	public RelationNode getRelationTypeByName(String typeName){
		return cmdbCacheService.getRelationTypes().get(typeName);
	}

	@Override
	public String testRmi() {
		return "rmi connect successful!";
	}


	@Override
	public RelationNode getRelationCiByCiId(String typeName, long ciId) throws CmdbException {
		return cmdbDao.getRelationCiByCiId(typeName, ciId);
	}

    
	@Override
	public List<RelationNode> getRelationType(){
		Map<String, RelationNode> ma = cmdbCacheService.getRelationTypes();
		List<RelationNode> li = new ArrayList<RelationNode>(ma.values());
		return li;
	}

	@Override
	public Node getCiByName(String typeName, String ciName) {
        String idkey=cmdbCacheService.getNodes_name().get(typeName+"_"+ciName);
		return cmdbCacheService.getNodes().get(idkey);
	}

	@Override
	public RelationNode getRelationCiByName(String typeName, String ciName) {
		String idkey=cmdbCacheService.getRelations_name().get(typeName+"_"+ciName);
		return cmdbCacheService.getRelations().get(idkey);
	}
    
	public String syncAvmonCi(Boolean preView) throws CmdbException{
		return avmonKpiToCi.syncAvmonCi(preView);
	}
	
	public void initAttDef(){
		cmdbDao.initAttDef();
	}
	public List<Node> insertNodeCis(List<Node> nodes) throws CmdbException{
		List<Node> li=new ArrayList<Node>();
		for(Node node:nodes){
		 if (node.getId() == null || node.getId() == 0) {
			 node.setId(idg.generate());
		 }
		//配合 avmon 展示数据用，ExchangedId=kpiname  name=kpivalue 
			//需要在avmonSync.properties中配置类对应的kpiname:kpicode
			if(node.getExchangedId()==null||"".equals(node.getExchangedId())){
				node.setExchangedId(getKpiByClass(node.getDerivedFrom()));
			}
		 li.add(node);
		}
		for(Node node:li){
		   cmdbCacheService.insertNode(node);
		}
	    cmdbDao.insertNodeCis(li);
	    return li;
	}
	public List<RelationNode> insertRelationCis(List<RelationNode> nodes) throws CmdbException{
		List<RelationNode> li=new ArrayList<RelationNode>();
		for(RelationNode node:nodes){
		 if (node.getId() == null || node.getId() == 0) {
			 node.setId(idg.generate());
		 }
		 li.add(node);
		}
		for(RelationNode node:li){
			   cmdbCacheService.insertRelation(node);
		}
	    cmdbDao.insertRelationCis(li);
	    return li;
	}
	
	/**
	 * 条件查询
	 * @param typeName 填写类型 可大幅缩小查询范围
	 * @param conditions 条件，组成见实现类描述
	 * @param isRelation 是否关系类
	 * @return
	 * @throws CmdbException
	 */
	public List getCiByCondition(String typeName,List<Map> conditions,boolean isRelation) throws CmdbException{
		return cmdbDao.getCiByCondition(typeName, conditions, isRelation);
	}
	
	public String getKpiByClass(String className){
		return avmonKpiToCi.getKpiByClass(className);
	}
	public String getClassByKpi(String kpiCode){
		return avmonKpiToCi.getClassByKpi(kpiCode);
	}
	public List<String> getClassViewKpi(String className){
		 return avmonKpiToCi.getClassViewKpi(className);
	}

	@Override
	public String getViewFuncKpi(String func) {
		return avmonKpiToCi.getViewFuncKpi(func);
	}
}
