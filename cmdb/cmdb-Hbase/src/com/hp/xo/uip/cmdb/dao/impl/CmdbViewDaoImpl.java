package com.hp.xo.uip.cmdb.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hp.xo.uip.cmdb.cache.CmdbCacheService;
import com.hp.xo.uip.cmdb.cache.CmdbCacheServiceImpl;
import com.hp.xo.uip.cmdb.dao.CmdbViewDao;
import com.hp.xo.uip.cmdb.dao.template.HBaseDaoTemplate;
import com.hp.xo.uip.cmdb.dao.template.XmlDaoTemplate;
import com.hp.xo.uip.cmdb.domain.ConfigItem;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.domain.ViewCondition;
import com.hp.xo.uip.cmdb.domain.ViewDefine;
import com.hp.xo.uip.cmdb.util.IConstants;

public class CmdbViewDaoImpl implements CmdbViewDao {
	private static Logger log=Logger.getLogger(CmdbViewDaoImpl.class);
	private HBaseDaoTemplate hbaseDaoTemplate;
	private XmlDaoTemplate xmlDaoTemplate;
	private CmdbCacheService cmdbCacheService;

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

	// 视图增删改
	public ViewDefine insertViewDefine(ViewDefine vd) {
		xmlDaoTemplate.writeViewDefXml(vd);
		return vd;
	};

	public boolean deleteView(String viewName) {
		return xmlDaoTemplate.removeView(viewName);
	};

	public ViewDefine updateView(ViewDefine vd) {
		return insertViewDefine(vd);
	};
	
	@Override
	public ViewDefine getViewDefineByName(String name) {
		return xmlDaoTemplate.readViewDefXml(name);
	}
	
	// 视图查询
	public List<ViewDefine> getViewDefineAll() {
		return xmlDaoTemplate.readViewDefXmlAll();
	};
    
	public ViewDefine getViewDataByName(String name){
		return getViewDataByName(name,null,null);
	}
	public ViewDefine getViewDataByName(String name,Long startNodeId,String startNodeType,String relationName,Boolean reverse,int deepth,List<String> typeFilter){
		ViewCondition vc=new ViewCondition();
		vc.setStartNodeId(startNodeId);
		vc.setStartNodeType(startNodeType);
		vc.setRelationName(relationName);
		vc.setReverse(reverse);
		vc.setDeepth(deepth);
		List<ViewCondition> li=new ArrayList<ViewCondition>();
		li.add(vc);
		return getViewDataByName(name,li,typeFilter);
	}
	public ViewDefine getViewDataByName(String viewName,List<ViewCondition> conditions,List<String> typeFilter) {
		ViewDefine vd = xmlDaoTemplate.readViewDefXml(viewName);
		List<ViewCondition> li = vd.getConditions();
		Collections.sort(li);
		List<ViewCondition> lic=new ArrayList<ViewCondition>();
		if(conditions!=null)lic.addAll(conditions);
		lic.addAll(li);
		Map<String, List<Node>> ns = new HashMap<String, List<Node>>();
		Map<String, List<RelationNode>> rs = new HashMap<String, List<RelationNode>>();
		for (ViewCondition vc : lic) {
			List<Node> nodes = new ArrayList<Node>();
			List<RelationNode> relations = new ArrayList<RelationNode>();
			List<String> typeFilter_n=null;	
			if(vc.getTypeFilter()!=null&&!"".equals(vc.getTypeFilter())){
				typeFilter_n=new ArrayList<String>();
				String ss[]= vc.getTypeFilter().split(IConstants.splitStr);
				for(String s:ss){
					if(typeFilter!=null){typeFilter_n.addAll(typeFilter);}
					typeFilter_n.add(s);
				}
			}
			int deepthRecord = 0;
			if (vc.getStartNodeId() != null&&vc.getStartNodeId()!=0) {
				if(vc.getStartNodeType()==null){
					log.error("Error:startNodeType is null ,view:"+vc.getName()+",startNodeId:"+vc.getStartNodeId()+",startNodeTpe:"+vc.getStartNodeType());
					break;
					};
					
				gather(vc.getStartNodeId(), vc.getStartNodeType(), vc
						.getRelationName(), vc.getReverse(), vc.getDeepth(),
						deepthRecord, nodes, relations,typeFilter_n);
				transferTypeNodeMap(nodes,ns);
		        transferTypeRelationMap(relations,rs);
			} else if (vc.getStartNodeType() != null) {
                List<Node> lin=ns.get(vc.getStartNodeType());
                if(lin!=null){
                	for(Node n:lin){
                	 deepthRecord=0;
                	 gather(n.getId(), n.getDerivedFrom(), vc
    						.getRelationName(), vc.getReverse(), vc.getDeepth(),
    						deepthRecord, nodes, relations,typeFilter_n);
                	}
               	 transferTypeNodeMap(nodes,ns);
                 transferTypeRelationMap(relations,rs);
                }
			} else {
				log.error("Error:startNodeType is null and startNodeId is null ,view:"+vc.getName()+",startNodeId:"+vc.getStartNodeId()+",startNodeTpe:"+vc.getStartNodeType());
			}

		}
		if(vd.getValueFilter()!=null&&!"".equals(vd.getValueFilter())){
			ns=filteValue(ns, vd.getValueFilter());
			rs=filteReValue(rs,vd.getValueFilter());
		}		
        vd.setNodesMap(ns);
        vd.setRelationNodesMap(rs);
		return vd;
	}
	
	public Map<String,List<Node>> filteValue(Map<String,List<Node>> ma,String filter){
		String ss[]= filter.split(IConstants.splitStr);
		List<String> li=new ArrayList<String>();
		for(String s:ss){
			li.add(s);
		}
		Iterator<String> ite= ma.keySet().iterator();
		List<String> lis=new ArrayList<String>();
		while(ite.hasNext()){
			String key=ite.next();			
				if(!li.contains(key)){
					lis.add(key);
				}
		}
		for(String s:lis){ma.remove(s);}
		return ma;
	}
	public Map<String,List<RelationNode>> filteReValue(Map<String,List<RelationNode>> ma,String filter){
		String ss[]= filter.split(IConstants.splitStr);
		List<String> li=new ArrayList<String>();
		for(String s:ss){
			li.add(s);
		}
		Iterator<String> ite= ma.keySet().iterator();
		while(ite.hasNext()){
			String key=ite.next();			
			List<RelationNode> lir=ma.get(key);
			List<RelationNode> lid=new ArrayList<RelationNode>();
			for(RelationNode r:lir){
				if(!li.contains(r.getSourceType()) || !li.contains(r.getTargetType())){
					lid.add(r);
				}
			}
			for(RelationNode r:lid){
				lir.remove(r);
			}
			ma.put(key, lir);
		}
		return ma;
	}
    private Map<String,List<Node>> transferTypeNodeMap(List<Node> nodes,Map<String,List<Node>> ma){
    	for(Node n:nodes){
    		List<Node> li=ma.get(n.getDerivedFrom());
    		if(li==null){li=new ArrayList<Node>();}
    		   if(!li.contains(n)) li.add(n);
			   ma.put(n.getDerivedFrom(),li);
    	}
    	return ma;
    }
    private Map<String,List<RelationNode>> transferTypeRelationMap(List<RelationNode> nodes,Map<String,List<RelationNode>> ma){
    	for(RelationNode n:nodes){
    		List<RelationNode> li=ma.get(n.getDerivedFrom());
    		if(li==null){li=new ArrayList<RelationNode>();}
    		   if(!li.contains(n)) li.add(n);
			   ma.put(n.getDerivedFrom(),li);
    	}
    	return ma;
    }
	public void gather(Long nodeId, String nodeType, String relationName,
			Boolean reverse, int deepth, int deepthRecord, List<Node> nodes,
			List<RelationNode> relations,List<String> typeFilter) {
		Node nd=cmdbCacheService.getNodes().get(nodeType+"_"+nodeId);
		if(!addList(nodes,nd,typeFilter))return;
		if (deepth > deepthRecord||deepth<=0) {
			 List<String> li = cmdbCacheService.getRelationIds().get(
					relationName + "_" + nodeType + "_" + nodeId);
			 List<String> lir = cmdbCacheService.getRelationIds_reverse().get(
						relationName + "_" + nodeType + "_" + nodeId);
			if(null==reverse){
				if(li==null){
					li=new ArrayList<String>();
				}
				if(lir!=null){
					li.addAll(lir);
				}
			}else if (reverse) {
				li=lir;
			}
			if (li != null) {
				deepthRecord = deepthRecord + 1;
				for (String s : li) {
					// 见relationIds定义
					String[] ss = s.split(IConstants.splitStr);
					
					RelationNode r = cmdbCacheService.getRelations().get(ss[0]);
					Node n = cmdbCacheService.getNodes().get(ss[1]);
					
					if (r != null)
					    addList(relations,r);
					
					if (n != null) {
						gather(n.getId(), n.getDerivedFrom(), relationName,
								reverse, deepth, deepthRecord, nodes, relations,typeFilter);
						
					}
				}
			}
		}
	}
	public boolean addList(List nodes,Object obj){
	    return addList(nodes, obj, null);  	
	}
    public boolean addList(List nodes,Object obj,List<String> typeFilter){
    	boolean re=false;
    	if(nodes!=null&&obj!=null){
    			if(typeFilter!=null){
    			  ConfigItem c=(ConfigItem)obj;	
    			  if(typeFilter.contains(c.getDerivedFrom())){
    				  if(!nodes.contains(obj)){
    					  nodes.add(obj);
        			      re=true;
        			  }
    			  }	
    			}else{
    				if(!nodes.contains(obj)){
    					nodes.add(obj);
    			        re=true;
    			    }
    			}
    	}
    	return re;
    }
}
