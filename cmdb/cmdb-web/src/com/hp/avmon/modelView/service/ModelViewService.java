package com.hp.avmon.modelView.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.utils.MyString;
import com.hp.avmon.utils.PropertyUtils;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmon.utils.StringUtil;
import com.hp.avmon.utils.Utils;
import com.hp.xo.uip.cmdb.domain.ChangeRecord;
import com.hp.xo.uip.cmdb.domain.CiAttribute;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RepairRecord;
import com.hp.xo.uip.cmdb.domain.ViewDefine;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.service.ChangeRecordService;
import com.hp.xo.uip.cmdb.service.CmdbService;
import com.hp.xo.uip.cmdb.service.CmdbViewService;
import com.hp.xo.uip.cmdb.service.RepairRecordService;
import com.hp.xo.uip.cmdb.util.IdGenerator;

@Service
public class ModelViewService {
	private static IdGenerator idg=IdGenerator.createGenerator();    
    private static final Log logger = LogFactory.getLog(ModelViewService.class);
    
    @Autowired
    private CmdbService cmdbServiceProxy;
    
    @Autowired
    public CmdbViewService cmdbViewServiceProxy;
    
    @Autowired
    public RepairRecordService repairServiceProxy;
    
    @Autowired
    public ChangeRecordService changeServiceProxy;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    private Logger log=Logger.getLogger(ModelViewService.class);	
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
	
	/**
	 * true 预览不进行同步数据的操作   false 执行同步操作并预览数据
	 * @param syncFlag
	 * @return
	 */
	public String getSyncDataInfo(Boolean syncFlag){
		String console="";
		try {
			console = cmdbServiceProxy.syncAvmonCi(syncFlag);
		} catch (CmdbException e) {
			e.printStackTrace();
		}
		return console;
	}
	
	public void testView(){
		List<ViewDefine> li= cmdbViewService.getViewDefineAll();
		for(ViewDefine vd:li){
			log.debug(vd.getName()+"-"+vd.getNodesMap()+"-"+vd.getRelationNodesMap());
		}
	}
	public void testCi(){
		List<Node> li= cmdbService.getCiType();
		for(Node n : li){
		  log.debug(n.getName()+":"+n.getLabel());	
		  try {
			log.debug(cmdbService.getCiByTypeName(n.getName()).size());
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
    
    public void test()
    {
    	String result = cmdbServiceProxy.testRmi(); 
    	System.out.println(result);
    }
	
    public Object getHostCI(String CiType) {
        // TODO Auto-generated method stub
    	//HostDisk
        Map<String, Object> map = new HashMap<String, Object>();
        
        List<Node> list = null;
		try {
			list = cmdbServiceProxy.getCiByTypeName(CiType);
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        map.put("HostCI", list);
        return map;
    }
    
    /**
     * 获取节点关系
     * @param startNodeType
     * @param startNodeId
     * @param relationName
     * @param deepth
     * @param typeFilter
     * @return
     */
     public Object getNodeByRelation(String startNodeType,long startNodeId,String relationName,int deepth,String typeFilter[]){
    	 Map<String, Object> map = new HashMap<String, Object>();
         List<Map<String,String>> newList = new ArrayList<Map<String,String>>();
         List<Node> list = cmdbViewServiceProxy.getNodeByRelation(startNodeType, startNodeId, relationName, deepth, typeFilter);

         for(int i=0;i<list.size();i++){
        	 Node node = list.get(i);
        	 Map<String,CiAttribute> ciattrMap = node.getAttributes();
        	 Map<String,String> attrMap = new HashMap<String,String>();
        	 for(Entry<String,CiAttribute> entry:ciattrMap.entrySet()){
        		 if(entry.getKey()!=null&&entry.getValue()!=null&&entry.getValue().getValue()!=null){
//        		   attrMap.put(entry.getKey(), entry.getValue().getValue().replace("\r\n", " -- "));
        		 }
        	 }
        	 if(i!=0)
        	   newList.add(attrMap);
         }
         map.put("NodeRelation", newList);
         return newList;
     }
	
    public Object getHostCIAttribute(String hostId) {
        // TODO Auto-generated method stub
    	//HostDisk
    	Long longHostId = Long.parseLong(hostId);
        Map<String, Object> map = new HashMap<String, Object>();
        
        List<Node> list = null;
		try {
			list = cmdbServiceProxy.getCiByTypeName("Host");
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        List mapList = new ArrayList();  
        for (int i = 0; i < list.size(); i++) {  
        	
        	if(list.get(i).getId() == longHostId)
        	{
        		Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
            	attributeList = list.get(i).getAttributes();
            	
                Map cellMap = new HashMap();  
                cellMap.put("cell", new Object[] {
                		( list.get(i).getLabel()), 
                        ( attributeList.get("att_1").getValue()),  
                        (attributeList.get("att_2")).getValue()  });
                mapList.add(cellMap);  

        	}
        	        }  
        map.put("page", "1");
        map.put("total", "5");
        map.put("rows", mapList);
        return map;
    }
    
    
    public Object getHostAttributeJqx(String hostId) {
        // TODO Auto-generated method stub
    	//HostDisk
    	Long longHostId = Long.parseLong(hostId);
        Map<String, Object> map = new HashMap<String, Object>();
        
        List<Node> list = null;
		try {
			list = cmdbServiceProxy.getCiByTypeName("Host");
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        List mapList = new ArrayList();  
        for (int i = 0; i < list.size(); i++) {  
        	
        	if(list.get(i).getId().toString().equals(hostId))
        	{
        		Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
            	attributeList = list.get(i).getAttributes();
            	
                Map cellMap_1 = new HashMap();  
                //cellMap_1.put("attGroup", attributeList.get("att_1").getAttGroup());
                cellMap_1.put("attLabel", attributeList.get("att_1").getName());
                cellMap_1.put("attValue", attributeList.get("att_1").getValue());
                mapList.add(cellMap_1);  
                Map cellMap_2 = new HashMap();  
                //cellMap_2.put("attGroup", attributeList.get("att_2").getAttGroup());
                cellMap_2.put("attLabel", attributeList.get("att_2").getName());
                cellMap_2.put("attValue", attributeList.get("att_2").getValue());
                mapList.add(cellMap_2);
        	}
        	        }  
        return mapList;
    }
    
	/**
	 * 取得主机列表
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 * @throws CmdbException 
	 */  
    public Object getHostList() throws CmdbException {
        // TODO Auto-generated method stub
    	//HostDisk
        Map<String, Object> map = new HashMap<String, Object>();
        
        List<Node> list = cmdbServiceProxy.getCiByTypeName("HostDisk");
        
        List<Map<String, Object[]>> mapList = new ArrayList<Map<String, Object[]>>();  
        for (int i = 0; i < list.size(); i++) {  
        	
        	Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
        	attributeList = list.get(i).getAttributes();
        	       	
            Map cellMap = new HashMap();  
            cellMap.put("cell", new Object[] {
            		( list.get(i).getLabel()), 
                    ( attributeList.get("att_1").getValue()),  
                    (attributeList.get("att_2")).getValue()  });  
            mapList.add(cellMap);  
        }  
        map.put("page", "1");
        map.put("total", "5");
        map.put("rows", mapList);
        return map;
    }
    
    /**
	 * 取得主机列表jqxGrid
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
     * @throws CmdbException 
	 */  
    public Object getHostListJqxgrid() throws CmdbException {
        // TODO Auto-generated method stub
    	//HostDisk
        Map<String, Object> map = new HashMap<String, Object>();
        
        List<Node> list = cmdbServiceProxy.getCiByTypeName("Host");
        
        List<Map<String, Object[]>> mapList = new ArrayList<Map<String, Object[]>>();  
        for (int i = 0; i < list.size(); i++) {  
        	
        	Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
        	attributeList = list.get(i).getAttributes();
        	       	
            Map cellMap = new HashMap();   
            cellMap.put("host", list.get(i).getLabel());
            cellMap.put("att_1", attributeList.get("att_1").getValue());
            cellMap.put("att_2", attributeList.get("att_2").getValue());
            //cellMap.put("attr_Group", attributeList.get("att_2").getValue());
            mapList.add(cellMap);  
        }  
//        map.put("rows", mapList);
        return mapList;
    }
    
    
	/**
	 * 取得常规信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getStoreBasicInfo(String moId) {
        
        Map map=new HashMap();
        //get ci
    	String tempSql = SqlManager.getSql(ModelViewService.class, "store_BasicInfo");
        String sql = String.format(tempSql, moId);
    	
        List list = jdbcTemplate.queryForList(sql);
        if(list != null && list.size() > 0){
            Map m = (Map) list.get(0);
            map.putAll(m);
        }

//        map.put("RUNRATE", getKpiValue(moId,"wk_cfg_1035","cpu_speed","/") );
//        map.put("RUNTIME", getKpiValue(moId,"wk_cfg_1035","live_time","/") );
        map.put("USERCOUNT", 0);
        map.put("ALARMCOUNT", 0);

        return map;
    }
    
    
    public Object getMainPageTreeStructure(){
    	List<Map<String,Object>> zNode = new ArrayList<Map<String,Object>>();
    	List<ViewDefine> secondListView = new ArrayList<ViewDefine>();
    	Map<Integer,String> pidMapViewName = new HashMap<Integer,String>();
    	//获取所有视图定义
    	List<ViewDefine> li= cmdbViewServiceProxy.getViewDefineAll();
        int fabricId = 1;
        int typeId = 100;
        
        //提前遍历所有视图查找子视图entry
        for(int i=0;i<li.size();i++){
        	ViewDefine temsVd = li.get(i);
        	if(temsVd.getName().indexOf("$") != -1){
        		secondListView.add(temsVd);
        	}
        }
        li.removeAll(secondListView);
        for(ViewDefine vd:li){
        	pidMapViewName.put(fabricId, vd.getName());
        	Map<String, Object> map = new HashMap<String, Object>();
        	ViewDefine vd1=cmdbViewServiceProxy.getViewDefineByName(vd.getName());
        	//获取当前视图所有关联节点list
        	Map<String, List<Node>> nodeMap = vd1.getNodesMap();
        	Iterator<String> ite = nodeMap.keySet().iterator();
        	Long rootId = null;
        	
        	//vd.getNodesMap()  // 耳机key  三级 list
        	//过滤并获取二层视图名称
        	Boolean isSecond =false;
        	while (ite.hasNext()) {
        		String typeKey = ite.next();
        		if(typeKey.equals("Host") )
				{
        			Node secondNodeHost = addSecondNode(zNode,typeId,fabricId,typeKey,secondListView,pidMapViewName,vd.getName());
        			rootId = secondNodeHost.getParentId();
        			List<Node> threeNodeHost = nodeMap.get(typeKey);
        			for(Node c : threeNodeHost)
					{
        				addThreeNode(zNode,typeId,typeKey,c);
					}
					typeId++;
				}else if(typeKey.equals("San")){
					Node secondNodeHost = addSecondNode(zNode,typeId,fabricId,typeKey,secondListView,pidMapViewName,vd.getName());
        			rootId = secondNodeHost.getParentId();
        			List<Node> threeNodeHost = nodeMap.get(typeKey);
        			for(Node c : threeNodeHost)
					{
        				addThreeNode(zNode,typeId,typeKey,c);
					}
					typeId++;
				}else if(typeKey.equals("Storage")){
					Node secondNodeHost = addSecondNode(zNode,typeId,fabricId,typeKey,secondListView,pidMapViewName,vd.getName());
        			rootId = secondNodeHost.getParentId();
        			List<Node> threeNodeHost = nodeMap.get(typeKey);
        			for(Node c : threeNodeHost)
					{
        				addThreeNode(zNode,typeId,typeKey,c);
					}
					typeId++;
				}else{
					Node secondNodeHost = addSecondNode(zNode,typeId,fabricId,typeKey,secondListView,pidMapViewName,vd.getName());
        			rootId = secondNodeHost.getParentId();
        			List<Node> threeNodeHost = nodeMap.get(typeKey);
        			for(Node c : threeNodeHost)
					{
        				addThreeNode(zNode,typeId,typeKey,c);
					}
					typeId++;
				}
        	}
        	if(!isSecond){
        		map.put("id", fabricId);
    	        map.put("pId", 0);
    	        map.put("name", vd1.getTitle());
    	        map.put("iconSkin", "icon_hardware");
    	        map.put("open", true);
    	        map.put("iframeURL", "mainFrame.jsp");
    	        map.put("typeName", vd1.getViewType());
    	        map.put("viewName", vd1.getName());
    	        map.put("rootViewName", vd1.getName());
    	        zNode.add(map);
        	}
        	
	        fabricId++;
	        typeId = 100*(fabricId+1);
        }
        
        return zNode;
    }
    
    /**
     * 添加第三层节点
     * @param zNode
     * @param typeId
     * @param typeKey
     * @return
     */
    private Map<String, Object> addThreeNode(List<Map<String,Object>> zNode,int typeId, String typeKey,Node c){
    	Map<String, Object> nodeMap = new HashMap<String, Object>();
    	nodeMap.put("id", c.getId());
    	nodeMap.put("pId", typeId);
    	nodeMap.put("name", c.getLabel());
    	nodeMap.put("iconSkin", "icon_hardware");
    	nodeMap.put("open", false);
    	nodeMap.put("iframeURL", "threeInstanceFrame.jsp");
    	nodeMap.put("typeName", typeKey);
    	nodeMap.put("viewName", typeKey);
    	nodeMap.put("rootViewName", typeKey);
		zNode.add(nodeMap);
    	return nodeMap;
    }
    /**
     * 添加并获取二级节点
     * @param zNode
     * @param typeId
     * @param fabricId
     * @param typeKey
     */
    private Node addSecondNode(List<Map<String,Object>> zNode,int typeId, int fabricId,String typeKey,List<ViewDefine> secondListView,Map<Integer,String> pidMapViewName,String name){
    	Node n=new Node();
		try {
			n = cmdbServiceProxy.getCiTypeByName(typeKey);
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> nodeMap = new HashMap<String, Object>();
		nodeMap.put("id", typeId);
		nodeMap.put("pId", fabricId);
		nodeMap.put("name", n.getLabel());
		nodeMap.put("iconSkin", "icon_hardware");
		nodeMap.put("open", false);
		nodeMap.put("iframeURL", "SecondFrame.jsp");
		nodeMap.put("typeName", typeKey);
		
		for(Entry<Integer,String> entry:pidMapViewName.entrySet()){
			if(entry.getKey().equals(fabricId)){
				//父节点view name
				nodeMap.put("rootViewName", entry.getValue());
			}
		}
		for(ViewDefine vd:secondListView){
			String[] varq = vd.getName().split("\\$");
			if(name.toLowerCase().equals(varq[0].toLowerCase()) && typeKey.toLowerCase().equals(varq[1].toLowerCase())){
				nodeMap.put("viewName", vd.getName());
				
			}
		}

		
		zNode.add(nodeMap);
		return n;
    }
	/**
	 * 取得树结构
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 * @throws CmdbException 
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getTreeStructure() throws CmdbException {
        List<Map<String,Object>> zNode = new ArrayList<Map<String,Object>>();
        
        Long hardwareId = null ;
        //List<Node> typeList= cmdbServiceProxy.getCiType();
        List<ViewDefine> li= cmdbViewServiceProxy.getViewDefineAll();
        int fabricId = 1;
        int typeId = 100;
		for(ViewDefine vd:li){
			Map<String, Object> map = new HashMap<String, Object>();
			log.debug(vd.getName()+"-"+vd.getNodesMap()+"-"+vd.getRelationNodesMap());
			ViewDefine vd1=cmdbViewServiceProxy.getViewDefineByName(vd.getName());
			Map<String, List<Node>> nodeMap = vd1.getNodesMap();
			Iterator<String> ite = nodeMap.keySet().iterator();
			Long rootId = null;
			while (ite.hasNext()) {
				String key = ite.next();
				List<Node> typeList = nodeMap.get(key);
				log.debug("-key--"+key);
				log.debug("-key--"+typeList.get(0).getLabel());
				
				//host
				if(key.equals("Host") )
				{
					Node n=new Node();
					n = cmdbServiceProxy.getCiTypeByName(key);
					Map<String, Object> hostMap = new HashMap<String, Object>();
					hostMap.put("id", typeId);
					hostMap.put("pId", fabricId);
					hostMap.put("name", n.getLabel());
					hostMap.put("iconSkin", "icon_hardware");
					hostMap.put("open", false);
					hostMap.put("iframeURL", "hostFrame.jsp");
					hostMap.put("typeName", key);
					zNode.add(hostMap);
					log.debug("Level 1 Node:" + n.getId() + ";" + n.getParentId() + ";" + n.getLabel());
					rootId = n.getParentId();
					List<Node> hostList= cmdbServiceProxy.getCiByTypeName(key);
					
					for(Node c : hostList)
					{
						Map<String, Object> hostCiMap = new HashMap<String, Object>();
						hostCiMap.put("id", c.getId());
						hostCiMap.put("pId", typeId);
						hostCiMap.put("name", c.getLabel());
						hostCiMap.put("iconSkin", "icon_hardware");
						hostCiMap.put("open", false);
						hostCiMap.put("iframeURL", "hostInstanceFrame.jsp");
						hostCiMap.put("typeName", key);
						zNode.add(hostCiMap);
					}
					typeId++;
				} else 	if( key.equals("San") )
				{
					Node n=new Node();
					n = cmdbServiceProxy.getCiTypeByName(key);
					Map<String, Object> hostMap = new HashMap<String, Object>();
					hostMap.put("id", typeId);
					hostMap.put("pId", fabricId);
					hostMap.put("name", n.getLabel());
					hostMap.put("iconSkin", "icon_hardware");
					hostMap.put("open", false);
					hostMap.put("iframeURL", "sanFrame.jsp");
					hostMap.put("typeName", key);
					zNode.add(hostMap);
					log.debug("Level 1 Node:" + n.getId() + ";" + n.getParentId() + ";" + n.getLabel());
					List<Node> sanList= cmdbServiceProxy.getCiByTypeName(key);
					
					for(Node c : sanList)
					{
						Map<String, Object> hostCiMap = new HashMap<String, Object>();
						hostCiMap.put("id", c.getId());
						hostCiMap.put("pId", typeId);
						hostCiMap.put("name", c.getLabel());
						hostCiMap.put("iconSkin", "icon_hardware");
						hostCiMap.put("open", false);
						hostCiMap.put("iframeURL", "sanSwitchInstanceFrame.jsp");
						hostCiMap.put("typeName", key);
						zNode.add(hostCiMap);
					}
					typeId++;
				} else 	if( key.equals("Storage"))
				{
					Node n=new Node();
					n = cmdbServiceProxy.getCiTypeByName(key);
					Map<String, Object> hostMap = new HashMap<String, Object>();
					hostMap.put("id", typeId);
					hostMap.put("pId", fabricId);
					hostMap.put("name", n.getLabel());
					hostMap.put("iconSkin", "icon_hardware");
					hostMap.put("open", false);
					hostMap.put("iframeURL", "storageFrame.jsp");
					hostMap.put("typeName",key);
					zNode.add(hostMap);
					log.debug("Level 1 Node:" + n.getId() + ";" + n.getParentId() + ";" + n.getLabel());
					List<Node> storageList= cmdbServiceProxy.getCiByTypeName(key);
					
					for(Node c : storageList)
					{
						Map<String, Object> hostCiMap = new HashMap<String, Object>();
						hostCiMap.put("id", c.getId());
						hostCiMap.put("pId", typeId);
						hostCiMap.put("name", c.getLabel());
						hostCiMap.put("iconSkin", "icon_hardware");
						hostCiMap.put("open", false);
						hostCiMap.put("iframeURL", "storageInstanceFrame.jsp");
						hostCiMap.put("typeName", key);
						zNode.add(hostCiMap);
					}
					typeId++;
				}
				
		        
		        
			}
			
			//view
	        //hardware 192976471930002 root node
	        map.put("id", fabricId);
	        map.put("pId", 0);
	        map.put("name", vd1.getTitle());
	        map.put("iconSkin", "icon_hardware");
	        map.put("open", true);
	        map.put("iframeURL", "mainFrame.jsp");
	        map.put("typeName", "");
	        map.put("viewName", vd1.getName());
	        zNode.add(map);
	        fabricId++;
	        typeId = 100*(fabricId+1);
		}
        
//        vd.getNodesMap();
//        for(Node n : typeList){
//			if(n.getName().equals("Hardware") )
//			{
//				 hardwareId = n.getId();
//			}
//        }
        

//        List<Node> li= cmdbServiceProxy.getCiType();
//        
//		for(Node n : li){
//			if(n.getName().equals("Host") )
//			{
//				Map<String, Object> hostMap = new HashMap<String, Object>();
//				hostMap.put("id", n.getId());
//				hostMap.put("pId", n.getParentId());
//				hostMap.put("name", n.getLabel());
//				hostMap.put("iconSkin", "icon_hardware");
//				hostMap.put("open", false);
//				hostMap.put("iframeURL", "hostFrame.jsp");
//				zNode.add(hostMap);
//				log.debug("Level 1 Node:" + n.getId() + ";" + n.getParentId() + ";" + n.getLabel());
//			} else 	if( n.getName().equals("sanSwitch") )
//			{
//				Map<String, Object> hostMap = new HashMap<String, Object>();
//				hostMap.put("id", n.getId());
//				hostMap.put("pId", n.getParentId());
//				hostMap.put("name", n.getLabel());
//				hostMap.put("iconSkin", "icon_hardware");
//				hostMap.put("open", false);
//				hostMap.put("iframeURL", "sanFrame.jsp");
//				zNode.add(hostMap);
//				log.debug("Level 1 Node:" + n.getId() + ";" + n.getParentId() + ";" + n.getLabel());
//			} else 	if( n.getName().equals("Storage"))
//			{
//				Map<String, Object> hostMap = new HashMap<String, Object>();
//				hostMap.put("id", n.getId());
//				hostMap.put("pId", n.getParentId());
//				hostMap.put("name", n.getLabel());
//				hostMap.put("iconSkin", "icon_hardware");
//				hostMap.put("open", false);
//				hostMap.put("iframeURL", "storageFrame.jsp");
//				zNode.add(hostMap);
//				log.debug("Level 1 Node:" + n.getId() + ";" + n.getParentId() + ";" + n.getLabel());
//			}
//		}
//
//		List<Node> hostList= cmdbServiceProxy.getCiByTypeName("Host");
//		
//		for(Node c : hostList)
//		{
//			Map<String, Object> hostCiMap = new HashMap<String, Object>();
//			hostCiMap.put("id", c.getId());
//			hostCiMap.put("pId", c.getParentId());
//			hostCiMap.put("name", c.getLabel());
//			hostCiMap.put("iconSkin", "icon_hardware");
//			hostCiMap.put("open", false);
//			hostCiMap.put("iframeURL", "hostInstanceFrame.jsp");
//			zNode.add(hostCiMap);
//		}
//		
//		List<Node> sanList= cmdbServiceProxy.getCiByTypeName("sanSwitch");
//		
//		for(Node c : sanList)
//		{
//			Map<String, Object> hostCiMap = new HashMap<String, Object>();
//			hostCiMap.put("id", c.getId());
//			hostCiMap.put("pId", c.getParentId());
//			hostCiMap.put("name", c.getLabel());
//			hostCiMap.put("iconSkin", "icon_hardware");
//			hostCiMap.put("open", false);
//			hostCiMap.put("iframeURL", "sanSwitchInstanceFrame.jsp");
//			zNode.add(hostCiMap);
//		}
//		
//		List<Node> storageList= cmdbServiceProxy.getCiByTypeName("Storage");
//		
//		for(Node c : storageList)
//		{
//			Map<String, Object> hostCiMap = new HashMap<String, Object>();
//			hostCiMap.put("id", c.getId());
//			hostCiMap.put("pId", c.getParentId());
//			hostCiMap.put("name", c.getLabel());
//			hostCiMap.put("iconSkin", "icon_hardware");
//			hostCiMap.put("open", false);
//			hostCiMap.put("iframeURL", "storageInstanceFrame.jsp");
//			zNode.add(hostCiMap);
//		}
		
        return zNode;
    }
    
    /**
	 * 获取ci属性配置名称
	 * @param typeName
	 * @return
	 */
	public String getCiConfigPropertity(String typeName){
		String line = null;
		String key = typeName.substring(0,1).toLowerCase()+typeName.substring(1,typeName.length())+"Attribute";
		line = PropertyUtils.getProperty(key);
		return line;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public String getCibyType2(String typeName) {
		//Node nt=new Node();
				List<Node> ciList = new ArrayList<Node>();
				try {
					if(typeName.equals("null"))
					{
						typeName = "Ci";
						}
					 ciList = cmdbServiceProxy.getCiByTypeName(typeName);
						  
					  
				} catch (CmdbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String line = this.getCiConfigPropertity(typeName);
				ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
				
				if(line!= null &&line.indexOf(",")>0&&!line.startsWith("#")){
					  String s[]=line.split(",");
					  
					  for(Node n : ciList)
		            	{
						  //获取当前节点属性值
							Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
					       	attributeList = n.getAttributes();
					       	
					       	if(!attributeList.isEmpty())
					       	{
					       		@SuppressWarnings("rawtypes")
								Map<String, Object> cellMap = new HashMap<String, Object>();   
					       		cellMap.put("id", n.getId());
					       		cellMap.put("ciLabel", n.getLabel());
					       		cellMap.put("name", n.getName());
					           	for(String a : s)
					    		  {
					           		//if the attribute do not exists
					        		//System.out.println("a:"+a);
					    			  //System.out.println("Test:" + attributeList.get(a.toLowerCase()).getName());
					           		if(attributeList.get(a.toLowerCase())!= null)
					           		{
					           			String value = attributeList.get(a.toLowerCase()).getValue();
					           			if(value == null)
					           				value = "";
					           			cellMap.put(attributeList.get(a.toLowerCase()).getName(), value.equals("null")?"":value);
					           		}  
					    		  }
					            
					            //cellMap.put("ciName", n.getName());
					            
					            mapList.add((HashMap<String, Object>) cellMap);  
					       	}
		            	} 
				}else{
					for(Node n : ciList)
	            	{
					  //获取当前节点属性值
						Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
				       	attributeList = n.getAttributes();
				       	
				       	if(!attributeList.isEmpty())
				       	{
				       		@SuppressWarnings("rawtypes")
							Map<String, Object> cellMap = new HashMap<String, Object>();   
				       		cellMap.put("id", n.getId());
				       		cellMap.put("ciLabel", n.getLabel());
				       		cellMap.put("name", n.getName());
				       		for(Map.Entry<String, CiAttribute> ciMap:attributeList.entrySet()){
				       			cellMap.put(ciMap.getValue().getName(), ciMap.getValue().getValue());
				       		}
				       		
				       	 mapList.add((HashMap<String, Object>) cellMap);  
				       	}
	            	}
				}
		            	
		            	


				String json = JackJson.fromObjectToJson(mapList);
				return json;
	}
	
	
	
	public String getAlCiListJqgrid(HttpServletRequest request)
			throws CmdbException {
		
		String typeName = request.getParameter("typeName");
		Node nt=new Node();
		if(null == typeName||"".equals(typeName))
		{
			typeName = "Ci";
			}
			try {
				nt = cmdbServiceProxy.getCiTypeByName(typeName);
			} catch (CmdbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
		String line = this.getCiConfigPropertity(typeName);
		if(nt == null)
			return "";
		Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
      	attributeList = nt.getAttributes();
      	if(!attributeList.isEmpty())
      	{
      		Map<String, Object> ciMap_1 = new HashMap<String, Object>();
            ciMap_1.put("label", "系统显示名称*");
            ciMap_1.put("name", "ciLabel");
            ciMap_1.put("index", "ciLabel");
            ciMap_1.put("width", "60px"); 
            ciMap_1.put("align", "left");
            ciMap_1.put("isRequired", true);
            ciMap_1.put("editable", true);
            Map<String,Object> editrules1 = new HashMap<String, Object>();
            editrules1.put("required", true);
            ciMap_1.put("editrules", editrules1);
//            ciMap_1.put("hidden", "false");
            
            Map<String, Object> ciMap2 = new HashMap<String, Object>();
            ciMap2.put("label", "系统名称*");
            ciMap2.put("name", "name");
            ciMap2.put("index", "name");
            ciMap2.put("width", "60px"); 
            ciMap2.put("align", "left");
            ciMap2.put("isRequired", true);
            ciMap2.put("editrules", editrules1);
            mapList.add((HashMap<String, Object>) ciMap_1);
            mapList.add((HashMap<String, Object>) ciMap2);
            
    		 if(line!= null && line.indexOf(",")>0&&!line.startsWith("#")){
    			  String s[]=line.split(",");
    			  for(String a : s)
    			  {
    				  if(attributeList.get(a.toLowerCase())!= null)
    				  {
    					  System.out.println("a:"+a);
    	              		Map<String, Object> cellMap = new HashMap<String, Object>();
    	              		cellMap.put("label", attributeList.get(a.toLowerCase()).getLabel());
    	              		cellMap.put("name", a.toLowerCase());
    	              		cellMap.put("index", a.toLowerCase());
    	              		cellMap.put("width", "60px");
    	              		cellMap.put("align", "left");
    	              		cellMap.put("editable", true);
    	              		//添加验证类型
    	              		Map<String,Object> editrules = new HashMap<String, Object>();
    	              		editrules.put("required", attributeList.get(a.toLowerCase()).isRequired());
    	              		cellMap.put("editrules", JackJson.fromObjectToJson(editrules));
    	              		//设置默认值
    	              		cellMap.put("value", attributeList.get(a.toLowerCase()).getDefValue());
    	              		//设置显示状态
    	              		cellMap.put("hidden", attributeList.get(a.toLowerCase()).getViewMode() == 2?true:false);
    	              		//是否必填信息
    	              		cellMap.put("isRequired", attributeList.get(a.toLowerCase()).isRequired());
    	              		//获得分组信息
    	              		cellMap.put("group", attributeList.get(a.toLowerCase()).getAttGroup());
    	              		mapList.add((HashMap<String, Object>) cellMap);
    				  }
              		
    			  }
    		 }else{
    			 for(Map.Entry<String, CiAttribute> mm:attributeList.entrySet()){
    				 Map<String, Object> cellMap = new HashMap<String, Object>();
	              		cellMap.put("label", mm.getValue().getLabel());
	              		cellMap.put("name", mm.getValue().getName());
	              		cellMap.put("index", mm.getValue().getName());
	              		cellMap.put("width", "60px"); 
	              		cellMap.put("align", "left");
	              		cellMap.put("editable", true);
	            		//添加验证类型
	              		Map<String,Object> editrules = new HashMap<String, Object>();
	              		editrules.put("required", mm.getValue().isRequired());
	              		cellMap.put("editrules", JackJson.fromObjectToJson(editrules));
	              	    //设置默认值
	              		cellMap.put("value", mm.getValue().getDefValue());
	              		//设置显示状态
	              		cellMap.put("hidden", mm.getValue().getViewMode() == 2?true:false);
//	              		cellMap.put("hidden", "false");
	              	    //是否必填信息
	              		cellMap.put("isRequired", mm.getValue().isRequired());
	              		//获得分组信息
	              		cellMap.put("group", mm.getValue().getAttGroup());
	              		mapList.add((HashMap<String, Object>) cellMap);
    			 }
    		 }
      	}
        
		
          Map<String, Object> ciMap = new HashMap<String, Object>();
          ciMap.put("label", "id");
          ciMap.put("name", "id");
          ciMap.put("index", "id");
          ciMap.put("width", "60px"); 
          ciMap.put("align", "left");
//          ciMap.put("hidden", "true");
//          ciMap.put("editable", true);
          mapList.add((HashMap<String, Object>) ciMap);
		String json = JackJson.fromObjectToJson(mapList);
		return json;
	}
    
    //
	  /**
     * 得到所有的类
     * @return
	 * @throws CmdbException 
     */
    @SuppressWarnings("unchecked")
	public String getHostListJqgrid(HttpServletRequest request) throws CmdbException{
       
       String sField = request.getParameter("searchField");
       String sValue = request.getParameter("searchString");
       String sOper = request.getParameter("searchOper");
       String viewName = request.getParameter("rootViewName");
       
       
       String page = request.getParameter("page");//页码
       String rows = request.getParameter("rows");//每页记录条数
       String sidx = request.getParameter("sidx");//排序字段
       String sord = request.getParameter("sord");//排序方向
       
       String pid = request.getParameter("id");
       String typeName = request.getParameter("typeName");
       
       Map<String, Object> map = new HashMap<String, Object>();
       
       List<Node> list = cmdbViewServiceProxy.getViewDataByName(viewName).getNodesMap().get(typeName);
//       List<Node> list = cmdbServiceProxy.getCiByTypeName(typeName);
       List<Map<String, Object[]>> mapList = new ArrayList<Map<String, Object[]>>();  
      
       String line = this.getCiConfigPropertity(typeName);
		 if(line.indexOf(",")>0&&!line.startsWith("#")){
			  String s[]=line.split(",");
       
      
       for (int i = 0; i < list.size(); i++) {  
       	
       	Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
       	attributeList = list.get(i).getAttributes();
       	if(!attributeList.isEmpty())
       	{
       		Map cellMap = new HashMap();   
           	for(String a : s)
    		  {
           		//if the attribute do not exists
        		//System.out.println("a:"+a);
    			  //System.out.println("Test:" + attributeList.get(a.toLowerCase()).getName());
           		if(attributeList.get(a.toLowerCase())!= null)
           		{
           			cellMap.put(attributeList.get(a.toLowerCase()).getName(), attributeList.get(a.toLowerCase()).getValue());
           		}  
    		  }
            cellMap.put("id", list.get(i).getId());
            //cellMap.put("ciName", n.getName());
            cellMap.put("ciLabel", list.get(i).getLabel());
               mapList.add(cellMap);
       	}
        
       
       }         
    }
		 String json = JackJson.fromObjectToJson(mapList);
			return json;
    }
	/**
	 * 新增CI类型
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 * @throws CmdbException 
	 */
    public Object addCIType(String hostId) {
        // TODO Auto-generated method stub
    	//HostDisk
    	Long longHostId = Long.parseLong(hostId);
        Map<String, Object> map = new HashMap<String, Object>();
        
        List<Node> list = null;
		try {
			list = cmdbServiceProxy.getCiByTypeName("Host");
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        List mapList = new ArrayList();  
        for (int i = 0; i < list.size(); i++) {  
        	
        	if(list.get(i).getId() == longHostId)
        	{
        		Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
            	attributeList = list.get(i).getAttributes();
            	
                Map cellMap = new HashMap();  
                cellMap.put("cell", new Object[] {
                		( list.get(i).getLabel()), 
                        ( attributeList.get("att_1").getValue()),  
                        (attributeList.get("att_2")).getValue()  });
                mapList.add(cellMap);  

        	}
        	        }  
        map.put("page", "1");
        map.put("total", "5");
        map.put("rows", mapList);
        return map;
    }
    
    /**
	 * 根据ID查询类属性
	 * @param id
	 * @return
	 */
	public String getCiAttr(String typeName,String ciId) {
		Node nt=new Node();
		try {
			if(typeName.equals("null"))
			{
				typeName = "Ci";
				}
			nt = cmdbServiceProxy.getCiByCiId(typeName,new Long(ciId));
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
		

        		Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
            	attributeList = nt.getAttributes();
            	for(Object o : attributeList.keySet())
            	{
                    Map cellMap = new HashMap();  
                    cellMap.put("name", attributeList.get(o).getName());
                    cellMap.put("label", attributeList.get(o).getLabel());
                    cellMap.put("value", attributeList.get(o).getValue());
                    mapList.add((HashMap<String, Object>) cellMap);  
            	}
		String json = JackJson.fromObjectToJson(mapList);
		return json;
	}
    
	/**
	 * 根据ID查询类属性
	 * @param id
	 * @return
	 */
	public String addCibyType(String typeName,String name,String label) {
		//Node nt=new Node();
		Map<String,String> message = new HashMap<String,String>();
		List<Node> ciList = new ArrayList<Node>();
		try {
			if(null == typeName||"".equals(typeName))
			{
				typeName = "Ci";
				}
			 ciList = cmdbServiceProxy.getCiByTypeName(typeName);
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Node nt=new Node();
		nt.setId(idg.generate());
		nt.setName(name);
		nt.setLabel(label);
		nt.setIsType(false);
		nt.setDerivedFrom(typeName);
		try {
			cmdbServiceProxy.insertNode(nt);
			message.put("success", "添加成功！");
			message.put("result", "0");
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			message.put("error", "添加失败！");
			message.put("result", "1");
			e.printStackTrace();
		}
		
		String json = JackJson.fromObjectToJson(message);
		return json;
	}
	
	/**
	 * 根据ID查询类属性
	 * @param id
	 * @return
	 */
	public String updateCiAttr(String typeName,String ciId,String name,String value) {
		Node nt=new Node();
		HashMap<String, String> message = new HashMap<String, String>();
		try {
			if(typeName.equals("null"))
			{
				typeName = "Ci";
				}
			nt = cmdbServiceProxy.getCiByCiId(typeName,new Long(ciId));
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
		
		CiAttribute newAttr = new CiAttribute();
		newAttr.setName(name);		
		newAttr.setValue(value);
		
        		Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
            	attributeList = nt.getAttributes();
            	attributeList.put(name, newAttr);
            	nt.setAttributes(attributeList);
            	try {
					cmdbServiceProxy.updateNode(nt);
					message.put("success", "修改成功！");
					message.put("result", "0"); 
				} catch (CmdbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		String json = JackJson.fromObjectToJson(message);
		return json;
	}
	
	 /**
	 * 根据ID查询类属性
	 * @param id
	 * @return
	 */
	public String getCiRecord(String typeName,String ciId) {
		//Node nt=new Node();
			if(typeName.equals("null"))
			{
				typeName = "Ci";
				}
			List<RepairRecord> li3 = null;
			try {
				li3 = repairServiceProxy.getRecordList(typeName, new Long(ciId));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CmdbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
		
            	for(RepairRecord record : li3)
            	{
                    Map cellMap = new HashMap();  
                    cellMap.put("id", record.getId());
                    cellMap.put("nodeKey", record.getNodeKey());
                    cellMap.put("repairNum", record.getRepairNum());
                    cellMap.put("damageDeviceNum", record.getDamageDeviceNum());
                    cellMap.put("deviceType", record.getDeviceType());
                    cellMap.put("replaceDeviceNum", record.getReplaceDeviceNum());
                    cellMap.put("repairDate", record.getRepairDate());
                    cellMap.put("repairType", record.getRepairType());
                    cellMap.put("maNum", record.getMaNum());
                    cellMap.put("maDate", record.getMaDate());
                    mapList.add((HashMap<String, Object>) cellMap);  
            	}
		String json = JackJson.fromObjectToJson(mapList);
		return json;
	}
	
	
	 /**
	 * 根据ID查询类属性
	 * @param id
	 * @return
	 */
	public String addCiRecord(String typeName,String ciId,String repairNum,String damageDeviceNum,String deviceType,String replaceDeviceNum,String repairDate,String repairType,String maNum,String maDate) {
		//Node nt=new Node();
		Map<String,String> message = new HashMap<String,String>();
			if(typeName.equals("null"))
			{
				typeName = "Ci";
				}
			try {
				//li3 = repairServiceProxy.getRecordList(typeName, new Long(ciId));
				RepairRecord rr= new RepairRecord();
		    	String nodeKey=typeName+"_"+ciId;
		    	rr.setId(idg.generate());
		    	//id=rr.getId();
		    	rr.setNodeKey(nodeKey);
		    	rr.setRepairNum(repairNum);
		    	rr.setDamageDeviceNum(damageDeviceNum);
		    	rr.setDeviceType(deviceType);
		    	rr.setMaDate(new Timestamp(new Date().getTime()));
		    	rr.setMaNum(maNum);
		    	rr.setRepairDate(new Timestamp(new Date().getTime()));
		    	rr.setRepairType(repairType);
		    	rr.setReplaceDeviceNum(replaceDeviceNum);
		    	repairServiceProxy.insert(rr);
		    	message.put("success", "添加成功！");
				message.put("result", "0");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message.put("error", "添加失败！");
				message.put("result", "1");
			} catch (CmdbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		String json = JackJson.fromObjectToJson(message);
		return json;
	}
	
	 /**
	 * 根据ID查询类属性
	 * @param id
	 * @return
	 */
	public String updateCiRecord(String typeName,String ciId,String id,String nodeKey,String repairNum,String damageDeviceNum,String deviceType,String replaceDeviceNum,String repairDate,String repairType,String maNum,String maDate) {
		//Node nt=new Node();
		Map<String,String> message = new HashMap<String,String>();
			if(typeName.equals("null"))
			{
				typeName = "Ci";
				}
			try {
				RepairRecord rr = repairServiceProxy.getRecord(nodeKey, new Long(id));		    	
		    	rr.setDamageDeviceNum(damageDeviceNum);
		    	rr.setDeviceType(deviceType);
		    	rr.setMaDate(new Timestamp(new Date().getTime()));
		    	rr.setMaNum(maNum);
		    	rr.setRepairDate(new Timestamp(new Date().getTime()));
		    	rr.setRepairType(repairType);
		    	rr.setReplaceDeviceNum(replaceDeviceNum);
		    	repairServiceProxy.update(rr);
		    	message.put("success", "修改成功！");
				message.put("result", "0");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message.put("error", "修改失败！");
				message.put("result", "1");
			} catch (CmdbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		String json = JackJson.fromObjectToJson(message);
		return json;
	}
	
	 /**
	 * 根据ID查询类属性
	 * @param id
	 * @return
	 */
	public String getCiChange(String typeName,String ciId) {
		//Node nt=new Node();
			if(typeName.equals("null"))
			{
				typeName = "Ci";
				}
			List<ChangeRecord> li3 = null;
			try {
				li3 = changeServiceProxy.getRecordList(typeName, new Long(ciId));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CmdbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
		
            	for(ChangeRecord record : li3)
            	{
                    Map cellMap = new HashMap();  
                    cellMap.put("id", record.getId());
                    cellMap.put("nodeKey", record.getNodeKey());
                    cellMap.put("sequence", record.getSequence());
                    cellMap.put("cdate", record.getCdate());
                    cellMap.put("content", record.getContent());
                    cellMap.put("operator", record.getOperator());
                    mapList.add((HashMap<String, Object>) cellMap);  
            	}
		String json = JackJson.fromObjectToJson(mapList);
		return json;
	}
	
	 /**
	 * 根据ID查询类属性
	 * @param id
	 * @return
	 */
	public String addCiChange(String typeName,String ciId,String sequence,String cdate,String content,String operator) {
		//Node nt=new Node();
		Map<String,String> message = new HashMap<String,String>();
			if(typeName.equals("null"))
			{
				typeName = "Ci";
				}
			try {
				//li3 = repairServiceProxy.getRecordList(typeName, new Long(ciId));
		    	ChangeRecord rr= new ChangeRecord();
		    	String nodeKey=typeName+"_"+ciId;
		    	rr.setId(idg.generate());
		    	//id=rr.getId();
		    	rr.setNodeKey(nodeKey);
		    	rr.setCdate(new Timestamp(new Date().getTime()));
		    	rr.setContent(content);
		    	rr.setOperator(operator);
		    	changeServiceProxy.insert(rr);
		    	message.put("success", "添加成功！");
				message.put("result", "0");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message.put("error", "添加失败！");
				message.put("result", "1");
			} catch (CmdbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		String json = JackJson.fromObjectToJson(message);
		return json;
	}
	
	/**
	 * 根据ID查询类属性
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCitAttrbyName(String typeName) {
		Node nt=new Node();
		if(null == typeName||"".equals(typeName))
		{
			typeName = "Ci";
			}
			try {
				nt = cmdbServiceProxy.getCiTypeByName(typeName);
			} catch (CmdbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
		String line = this.getCiConfigPropertity(typeName);
		
		Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
      	attributeList = nt.getAttributes();
      	if(!attributeList.isEmpty())
      	{
      		Map ciMap_1 = new HashMap();
            ciMap_1.put("label", "显示名");
            ciMap_1.put("name", "ciLabel");
            ciMap_1.put("index", "ciLabel");
            ciMap_1.put("width", "60px"); 
            ciMap_1.put("align", "left");
//            ciMap_1.put("hidden", "false");
            mapList.add((HashMap<String, Object>) ciMap_1);
            
    		 if(line.indexOf(",")>0&&!line.startsWith("#")){
    			  String s[]=line.split(",");
    			  for(String a : s)
    			  {
    				  if(attributeList.get(a.toLowerCase())!= null)
    				  {
    					  System.out.println("a:"+a);
    	              		Map cellMap = new HashMap();
    	              		cellMap.put("label", attributeList.get(a.toLowerCase()).getLabel());
    	              		cellMap.put("name", a.toLowerCase());
    	              		cellMap.put("index", a.toLowerCase());
    	              		cellMap.put("width", "60px"); 
    	              		cellMap.put("align", "left");
//    	              		cellMap.put("hidden", "false");
    	              		mapList.add((HashMap<String, Object>) cellMap);
    				  }
              		
    			  }
    		 }else{
    			 for(Map.Entry<String, CiAttribute> mm:attributeList.entrySet()){
    				 Map<String, Object> cellMap = new HashMap<String, Object>();
                  		cellMap.put("label", mm.getValue().getLabel());
                  		cellMap.put("name", mm.getValue().getName());
                  		cellMap.put("index", mm.getValue().getName());
                  		cellMap.put("width", "60px"); 
                  		cellMap.put("align", "left");
                  		cellMap.put("editable", true);
//                  		cellMap.put("hidden", "false");
                  		mapList.add((HashMap<String, Object>) cellMap);
    			 }
    		 }
      	}
        
		
          Map ciMap = new HashMap();
          ciMap.put("label", "id");
          ciMap.put("name", "id");
          ciMap.put("index", "id");
          ciMap.put("width", "60px"); 
          ciMap.put("align", "left");
//          ciMap.put("hidden", "true");
          mapList.add((HashMap<String, Object>) ciMap);
          

          


		String json = JackJson.fromObjectToJson(mapList);
		return json;
	}
	
}
