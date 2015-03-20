package com.hp.xo.uip.cmdb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hp.xo.uip.cmdb.dao.CmdbViewDao;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.domain.ViewCondition;
import com.hp.xo.uip.cmdb.domain.ViewDefine;
import com.hp.xo.uip.cmdb.service.CmdbViewService;

public class CmdbViewServiceImpl implements CmdbViewService{
	private static Logger log=Logger.getLogger(CmdbViewServiceImpl.class);
    private CmdbViewDao cmdbViewDao;
    
	public CmdbViewDao getCmdbViewDao() {
		return cmdbViewDao;
	}

	public void setCmdbViewDao(CmdbViewDao cmdbViewDao) {
		this.cmdbViewDao = cmdbViewDao;
	}

	@Override
	public boolean deleteView(String viewName) {
		return cmdbViewDao.deleteView(viewName);
	}

	@Override
	public void gather(Long nodeId, String nodeType, String relationName,
			Boolean reverse, int deepth, int deepthRecord, List<Node> nodes,
			List<RelationNode> relations, List<String> typeFilter) {
		cmdbViewDao.gather(nodeId, nodeType, relationName, reverse, deepth, deepthRecord, nodes, relations, typeFilter);
	}

	@Override
	public ViewDefine getViewDataByName(String name) {
		return cmdbViewDao.getViewDataByName(name);
	}

	public ViewDefine getViewDataByNameFilter_host_sto(String name){
		ViewDefine vd= cmdbViewDao.getViewDataByName(name);
		vd.getNodesMap().remove("HostDisk");
		List<RelationNode> lir= vd.getRelationNodesMap().get("linked");
		Map<String,List<String>> host_disk=new HashMap<String,List<String>>();
		Map<String,List<String>> disk_port=new HashMap<String,List<String>>();
		List<RelationNode> lid=new ArrayList<RelationNode>();
		Map<String,RelationNode> rema=new HashMap<String,RelationNode>(); 
	if(lir !=null){
		for(RelationNode r:lir){			
			if(r.getSourceType().equals("Host")&&r.getTargetType().equals("HostDisk")){
				String hk=r.getSourceType()+";"+r.getSourceId()+";"+r.getSourceName();
				String hd=r.getTargetType()+";"+r.getTargetId()+";"+r.getTargetName();
				List<String> lihd=host_disk.get(hk);
				if(lihd==null){
					lihd=new ArrayList<String>();
				}
				lihd.add(hd);
				host_disk.put(hk,lihd);
				lid.add(r);
			}
            if(r.getSourceType().equals("HostDisk")&&r.getTargetType().equals("StorageNetPort")){
            	String hd=r.getSourceType()+";"+r.getSourceId()+";"+r.getSourceName(); 
				String snp=r.getTargetType()+";"+r.getTargetId()+";"+r.getTargetName();
				List<String> lisnp=disk_port.get(hd);
				if(lisnp==null){
					lisnp=new ArrayList<String>();
				}
				lisnp.add(snp);
				disk_port.put(hd,lisnp);
            	lid.add(r);
			}
		}
		for(RelationNode r:lid){
			lir.remove(r);
		}
		
		Iterator<String> itr= host_disk.keySet().iterator();
		while(itr.hasNext()){
			String host=itr.next();
			List<String> disks=host_disk.get(host);
		  if(disks!=null){	
			for(String disk:disks){
				
			List<String> ports=disk_port.get(disk);
		  if(ports!=null){
			for(String port:ports){  
			RelationNode re=new RelationNode();
			 re.setId(System.currentTimeMillis());
			 String[] h=host.split(";");
			 String[] p=port.split(";");
			 re.setSourceType(h[0]);re.setSourceId(Long.parseLong(h[1]));re.setSourceName(h[2]);
			 re.setTargetType(p[0]);re.setTargetId(Long.parseLong(p[1]));re.setTargetName(p[2]);
			 re.setName(re.getSourceName()+"_"+re.getTargetName());
			 rema.put(re.getName(), re);
			}
			}
		  
			}  
		  }
		}
		}
	    if(rema.values()!=null)lir.addAll(rema.values());
		vd.getRelationNodesMap().put("linked", lir);
		return vd;
	}
	public ViewDefine getViewDataByNameFilter(String name) {
		ViewDefine vd= cmdbViewDao.getViewDataByName(name);
		String filter="SanZoneSet";
		String filterRelation="linked";
		String connType="HostHba";
		String connType2="StorageNetPort";		
	
			List<Node> li=vd.getNodesMap().get(filter);			
			List<RelationNode> lir=vd.getRelationNodesMap().get(filterRelation);
			List<Node> lin=new ArrayList<Node>();
			List<Node> linh=new ArrayList<Node>();
			List<Node> linp=new ArrayList<Node>();
			List<RelationNode> lirn=new ArrayList<RelationNode>();
			if(li!=null&&lir!=null){
			 for(Node n:li){
				for(RelationNode rn:lir){
					 if(rn.getSourceType().equals(filter)&&rn.getSourceId()==n.getId()
							 &&connType.equals(rn.getTargetType())){
					   if(!linh.contains(n))linh.add(n);	
					 }
					 if(rn.getSourceType().equals(filter)&&rn.getSourceId()==n.getId()
							 &&connType2.equals(rn.getTargetType())){
					   if(!linp.contains(n))linp.add(n);	
					 }
				}
			 }
			 for(Node n:li){
				 if(linh.contains(n)&&linp.contains(n)){
					 lin.add(n);
				 }
			 }
			
			//过滤关系
			
			for(RelationNode rn:lir){
			    for(Node n:lin){
			    	if(vd.getNodesMap().get(rn.getSourceType())==null
			    			||vd.getNodesMap().get(rn.getTargetType())==null){
			    		break;
			    	}
			    	if(!rn.getSourceType().equals(filter) && !rn.getTargetType().equals(filter)){
			    		if(!lirn.contains(rn)){
			    			lirn.add(rn);break;
			    		}
			    	}
			    	if(rn.getSourceType().equals(filter) && rn.getSourceId()==n.getId()){
			    		if(!lirn.contains(rn)){
			    			lirn.add(rn);break;
			    		}
			    	}
			    	if(rn.getTargetType().equals(filter) && rn.getTargetId()==n.getId()){
			    		if(!lirn.contains(rn)){
			    			lirn.add(rn);break;
			    		}
			    	}
			    }	
			}
			}
			vd.getNodesMap().put(filter, lin);
			vd.getRelationNodesMap().put(filterRelation, lirn);
			
	
//			List<String> li1=new ArrayList<String>();
//			List<RelationNode> li2=new ArrayList<RelationNode>();
//			for(RelationNode rn:lirn){
//				if("10.238.158.54_bildb1_hba1_DMX4_5229_7b0".equals(rn.getSourceName())
//						&& ("StorageNetPort".equals(rn.getTargetType())
//								||"HostHba".equals(rn.getTargetType()))){
//					li1.add(rn.getName());
//					li2.add(rn);
//				}
//			}
//			
//            List<Node> lisn2=vd.getNodesMap().get("SanZoneSet");
//			for(Node sn:lisn2){
//			if("10.238.158.54_bildb1_hba1_DMX4_5229_7b0".equals(sn.getName())){
//				log.debug(sn);
//			}
//			}
//			log.debug(li1.size()+li2.size());
		return vd;
	}
	
	@Override
	public ViewDefine getViewDataByName(String name, Long startNodeId,
			String startNodeType, String relationName, boolean reverse,
			int deepth, List<String> typeFilter) {
		return cmdbViewDao.getViewDataByName(name, startNodeId, startNodeType, relationName, reverse, deepth, typeFilter);
	}

	@Override
	public ViewDefine getViewDataByName(String viewName,
			List<ViewCondition> conditions, List<String> typeFilter) {
		return cmdbViewDao.getViewDataByName(viewName, conditions, typeFilter);
	}

	@Override
	public List<ViewDefine> getViewDefineAll() {
		return cmdbViewDao.getViewDefineAll();
	}

	@Override
	public ViewDefine getViewDefineByName(String name) {
		return cmdbViewDao.getViewDataByName(name);
	}

	@Override
	public ViewDefine insertViewDefine(ViewDefine vd) {
		return cmdbViewDao.insertViewDefine(vd);
	}

	@Override
	public ViewDefine updateView(ViewDefine vd) {
		if(vd!=null&&(vd.getConditions()==null||vd.getConditions().size()<=0)){
			ViewDefine vdt=cmdbViewDao.getViewDefineByName(vd.getName());
			vd.setConditions(vdt.getConditions());
		}
		return cmdbViewDao.updateView(vd);
	}


	//-------------
	public List<Node> getNodeByRelation(String startNodeType,Long startNodeId,
			String relationName,int deepth,String... typeFilter){
		return getNodeByRelation(startNodeType, startNodeId, relationName, null, deepth, typeFilter);
	}
	public List<Node> getNodeByRelation(String startNodeType,Long startNodeId,
			String relationName,Boolean reverse,int deepth,String... typeFilter){
		List<Node> nodes=new ArrayList<Node>();
		List<RelationNode> relations=new ArrayList<RelationNode>();
		List<String> li=new ArrayList<String>();
		if(typeFilter!=null&&!"".equals(typeFilter)){
		  for(String s:typeFilter){
		   li.add(s);
		  }
		}else{
			li=null;
		}
		int deepthRecord=0;
		gather(startNodeId, startNodeType, relationName, reverse, 1, deepthRecord, 
				nodes, relations, li);
		return nodes;
	}
	/**
	 * 批量起始点 关系查询
	 * @param startNodes
	 * @param relationName
	 * @param reverse
	 * @param deepth
	 * @param typeFilter
	 * @return
	 */
	public List<Node> getNodeByRelation(List<Node> startNodes,
			String relationName,Boolean reverse,int deepth,String... typeFilter){
		List<Node> nodes=new ArrayList<Node>();
		List<RelationNode> relations=new ArrayList<RelationNode>();
		List<String> li=new ArrayList<String>();
		if(typeFilter!=null&&!"".equals(typeFilter))
		for(String s:typeFilter){
		  li.add(s);
		}
        if(startNodes!=null){
        	int deepthRecord=0;
        	for(Node n:startNodes){
        	 deepthRecord=0;
        	 gather(n.getId(), n.getDerivedFrom(),relationName, reverse, deepth,
					deepthRecord, nodes, relations,li);
        	}       	 
        }
        return nodes;
	}
	
	/**
	 * 通用查询 返回 map<typeName,list<node>>
	 * @param startNodeType
	 * @param startNodeId
	 * @param relationName
	 * @param reverse  null：表示正反双向
	 * @param deepth  表示向下深度
	 * @param typeFilter 类型过滤
	 * @return
	 */
	public Map<String,List<Node>> getNodeRelationMap(String startNodeType,Long startNodeId,String relationName,Boolean reverse,int deepth,String... typeFilter){
		   List<Node> lin=getNodeByRelation(startNodeType, startNodeId, relationName, reverse, deepth, typeFilter);
		   Map<String,List<Node>> ma=new HashMap<String,List<Node>>();
		   for(Node node:lin){
				String type=node.getDerivedFrom();
				if(ma.get(type)==null){
				   ma.put(type,new ArrayList<Node>());
				}
				ma.get(type).add(node);
			}
			return ma;
	}
	/**
	 * 通用查询 获得第一层 ci
	 * @param startNodeType
	 * @param startNodeId
	 * @param relationName
	 * @return
	 */
	public Map<String,List<Node>> getNodeRelationMapLv1(String startNodeType,Long startNodeId,String relationName){
		return getNodeRelationMap(startNodeType, startNodeId, relationName, null, 1, null);
	}
}
