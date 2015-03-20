package com.hp.avmon.searchMgr.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.modelView.service.query.xj.CmdbQueryService;
import com.hp.avmon.modelView.service.query.xj.CmdbQueryServiceImpl;
import com.hp.avmon.utils.CsvUtil;
import com.hp.avmon.utils.PropertyUtils;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmon.utils.StringUtil;
import com.hp.xo.uip.cmdb.domain.CiAttribute;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.service.CmdbService;
import com.hp.xo.uip.cmdb.service.CmdbViewService;
import com.hp.xo.uip.cmdb.util.IdGenerator;

/**
 * Handles requests for the application home page.
 */
@Service
public class SearchMgrService {
	@SuppressWarnings("unused")
	private static IdGenerator idg=IdGenerator.createGenerator();
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(SearchMgrService.class);
	
	@Autowired
    private CmdbService cmdbServiceProxy;
	
	@Autowired
	private CmdbViewService cmdbViewServiceProxy;
	
	@Autowired
	private CmdbQueryServiceImpl cmdbQueryService;

	@SuppressWarnings("unused")
	private JdbcTemplate jdbc;
	@SuppressWarnings("unused")
	private static int RELATION_TYPE_INCLUDE = 0;
    /**
     * 构造函数
     */
    public SearchMgrService() {
    	// 加载连接对象
    	jdbc = SpringContextHolder.getBean("jdbcTemplate");
    }
	
	
    /**
     * 获得San list数据
     * @param request
     * @return
     */
    public String getSanList(HttpServletRequest request){
    	List<Map<String, String>> mapList = new ArrayList<Map<String,String>>();
    	String typeName = request.getParameter("typeName");
    	List<Node> sanNodes = new ArrayList<Node>();
    	try {
			sanNodes = cmdbServiceProxy.getCiByTypeName(typeName);
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	for(Node node:sanNodes){
			if(node.getParentId() != null && node.getParentId() !=  Long.parseLong("0")){
				Map<String, String> map = new HashMap<String,String>();
				map.put("name", node.getId()+"");
				map.put("label", node.getLabel());
				mapList.add(map);
			}
		}
    	String json = JackJson.fromObjectToJson(mapList);
		return json;
    }
    
    /**
     * 查找主机信息
     * @param request
     * @return
     */
    public String searchHost(HttpServletRequest request){
    	
    	Map<String,Object> returnSource = new HashMap<String, Object>();
    	
    	ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
    	
    	String cscomputername = request.getParameter("cscomputername");
    	String machine_net_ip = request.getParameter("machine_net_ip");
    	String typeName = request.getParameter("typeName");
    	List<Node> hostNodes = new ArrayList<Node>();
    	try {
			hostNodes = cmdbQueryService.getHost(cscomputername, machine_net_ip);
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String isDownload = request.getParameter("isDownload");
		
		if(isDownload.equals("yes")){
			//返回filepath,供下载用
			CsvUtil cu=new CsvUtil();
			String nodecolumn=request.getParameter("node_column");
			String filePath=cu.writeXlsToFlie(cu.transNode(hostNodes,nodecolumn), "QueryHost");
			returnSource.put("filePath", filePath);
		}
		
		
    	mapList = getWebGridData(typeName,hostNodes);
    	
    	returnSource.put("rows", mapList);
    	
    	String json = JackJson.fromObjectToJson(returnSource);
		return json;
    }
    
   // public String getDownloadFileName
    
    /**
     * 查找主机HBA信息
     * @param request
     * @return
     */
    public String searchHostHba(HttpServletRequest request){
    	Map<String,Object> returnSource = new HashMap<String, Object>();
    	
    	ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
    	
    	String hba_hostname = request.getParameter("hba_hostname");
    	String hba_wwpn = request.getParameter("hba_wwpn");
    	String hba_name = request.getParameter("hba_name");
    	String typeName = request.getParameter("typeName");
    	List<Node> hostNodes = new ArrayList<Node>();
    	try {
			hostNodes = cmdbQueryService.getHostHba(hba_hostname, hba_wwpn, hba_name);
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        String isDownload = request.getParameter("isDownload");
		
		if(isDownload.equals("yes")){
			//返回filepath,供下载用
			CsvUtil cu=new CsvUtil();
			String nodecolumn=request.getParameter("node_column");
			String filePath=cu.writeXlsToFlie(cu.transNode(hostNodes,nodecolumn), "QueryHostHba");
			returnSource.put("filePath", filePath);
		}
		
    	mapList = getWebGridData(typeName,hostNodes);
    	returnSource.put("rows", mapList);
    	String json = JackJson.fromObjectToJson(returnSource);
		return json;
    }
    
    
    /**
     * 查找SanZone信息
     * @param request
     * @return
     */
    public String searchSanZone(HttpServletRequest request){
    	Map<String,Object> returnSource = new HashMap<String, Object>();
    	
    	ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
    	
    	String sanSwitchID = request.getParameter("sanSwitchID");
    	String zoneName = request.getParameter("zoneName");
    	String zoneConfig = request.getParameter("zoneConfig");
    	String typeName = request.getParameter("typeName");
    	List<Node> hostNodes = new ArrayList<Node>();
    	if(!NumberUtils.isNumber(sanSwitchID) && sanSwitchID != null && !sanSwitchID.equals("")){
    		sanSwitchID = "1";
    	}
    	Long sanSwitchIDLong = sanSwitchID.equals("")|| sanSwitchID == null?null:Long.parseLong(sanSwitchID);
    	try {
			hostNodes = cmdbQueryService.getSanZone(sanSwitchIDLong, zoneName, zoneConfig);
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
       String isDownload = request.getParameter("isDownload");
		
		if(isDownload.equals("yes")){
			//返回filepath,供下载用
			CsvUtil cu=new CsvUtil();
			String nodecolumn=request.getParameter("node_column");
			String filePath=cu.writeXlsToFlie(cu.transNode(hostNodes,nodecolumn), "QuerySanZone");
			returnSource.put("filePath", filePath);
		}
		
    	mapList = getWebGridData(typeName,hostNodes);
    	returnSource.put("rows", mapList);
    	String json = JackJson.fromObjectToJson(returnSource);
		return json;
    }
    
    
    /**
     * 查找Storage信息
     * @param request
     * @return
     */
    public String searchStorage(HttpServletRequest request){
    	Map<String,Object> returnSource = new HashMap<String, Object>();
    	
    	ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
    	
    	String storageName = request.getParameter("storageName");
    	String typeName = request.getParameter("typeName");
    	List<Node> hostNodes = new ArrayList<Node>();
    	try {
			hostNodes = cmdbQueryService.getStorage(storageName);
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        String isDownload = request.getParameter("isDownload");
		
		if(isDownload.equals("yes")){
			//返回filepath,供下载用
			CsvUtil cu=new CsvUtil();
			String nodecolumn=request.getParameter("node_column");
			String filePath=cu.writeXlsToFlie(cu.transNode(hostNodes,nodecolumn), "QueryStorage");
			returnSource.put("filePath", filePath);
		}
		
    	mapList = getWebGridData(typeName,hostNodes);
    	returnSource.put("rows", mapList);
    	String json = JackJson.fromObjectToJson(returnSource);
		return json;
    }
	
    
    private  ArrayList<HashMap<String,Object>> getWebGridData(String typeName,List<Node> nodeList){
		//定义返回数据
		ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
		//获取配置的列名称
		String line = this.getCiConfigPropertity(typeName);
		if(line!= null &&line.indexOf(",")>0&&!line.startsWith("#")){
			  String s[]=line.split(",");
			  
			  for(Node n : nodeList)
          	{
				  //获取当前节点属性值
					Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
			       	attributeList = n.getAttributes();
			       	
			       	if(!attributeList.isEmpty())
			       	{
						Map<String, Object> cellMap = new HashMap<String, Object>();   
			       		cellMap.put("id", n.getId());
			       		cellMap.put("ciLabel", n.getLabel());
			       		cellMap.put("name", n.getName());
			           	for(String a : s)
			    		  {
			           		if(attributeList.get(a.toLowerCase())!= null)
			           		{
			           			String value = attributeList.get(a.toLowerCase()).getValue();
			           			if(value == null)
			           				value = "";
			           			cellMap.put(attributeList.get(a.toLowerCase()).getName(), value.equals("null")?"":value);
			           		}  
			    		  }
			           	//特殊处理，如果是主机的话则添加两个额外属性,主机hba增加一个属性
			           	if(typeName.equals("Host")){
			           		String value1 = attributeList.get("hostdisk").getValue();
			           		String value2 = attributeList.get("hosthba").getValue();
			           		cellMap.put("hostdisk".toLowerCase(), value1);
			           		cellMap.put("hosthba".toLowerCase(), value2);
			           	}else if(typeName.equals("HostHba")){
			           		String value2 = attributeList.get("hosthba").getValue();
			           		cellMap.put("hosthba".toLowerCase(), value2);
			           	}else if(typeName.equals("SanZoneSet")){
			           		String value11 = attributeList.get("storagenetport").getValue();
			           		String value22 = attributeList.get("hosthba").getValue();
			           		cellMap.put("storagenetport", value11);
			           		cellMap.put("hosthba", value22);
			           	}else if(typeName.equals("Storage")){
			           		String value111 = attributeList.get("stohostre").getValue();
			           		String value222 = attributeList.get("stohost").getValue();
			           		cellMap.put("stohostre", value111);
			           		cellMap.put("stohost", value222);
			           	}
			            mapList.add((HashMap<String, Object>) cellMap);  
			       	}
          	} 
		}else{
			for(Node n : nodeList)
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
		
		return mapList;
	}
	

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
					  
					  
					  
				}
		            	
		            	


				String json = JackJson.fromObjectToJson(mapList);
				return json;
	}
	
	
	/**
	 * 获取已经存在的TypeName list
	 * @return
	 */
	public String getTypeNameList(){
		List<Map<String, String>> mapList = new ArrayList<Map<String,String>>();
    	
    	List<Node> nodes = cmdbServiceProxy.getCiType();
    	
    	for(Node node:nodes){
    		Map<String, String> map = new HashMap<String,String>();
			map.put("name", node.getName().substring(0,1).toLowerCase()+node.getName().substring(1,node.getName().length())+"Attribute");
			map.put("label", node.getLabel());
			mapList.add(map);
    	}
    	String json = JackJson.fromObjectToJson(mapList);
		return json;
	}
	
	
	/**
	 * 判断是否存在key
	 * @param lines
	 * @param key
	 * @return
	 */
	public Boolean judgeKeyValue(String[] lines,String key){
		Boolean flag = false;
		for(String a : lines)
		  {
			if(a.toLowerCase().equals(key)){
				flag = true;
				break;
			}
		  }
		
		return flag;
	}
	
	/**
	 * 设置属性到配置文件中
	 * @param request
	 * @return
	 */
	public String setPropertyInfo(HttpServletRequest request){
		String result = null;
		HashMap<String, Object> message = new HashMap<String, Object>();
		String typeAttrName = request.getParameter("typeAttrName");
		String selKeys = request.getParameter("selKeys");
		String configItem = "";
		if(selKeys != null){
			configItem = selKeys.substring(0, selKeys.length()-1);
		}
		try {
			PropertyUtils.writeData(typeAttrName, configItem);
			message.put("issuccess", true);
			message.put("result", "0");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message.put("issuccess", false);
			message.put("result", "1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message.put("issuccess", false);
			message.put("result", "1");
		}
		
		
		result = JackJson.fromObjectToJson(message);
		return result;
	}
	
	
	/**
	 * 获取当前类型的所有attri
	 * @return
	 */
	public String getPropertyInfoList(String oraTypeName){
		Map<String,List<Map<String, String>>> returnSource = new HashMap<String, List<Map<String,String>>>();
		Node nt=new Node();
		String typeName = getOraTypeName(oraTypeName);
		
		String line = this.getCiConfigPropertity(typeName);
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
		Map<String,String> metaDat = nt.getMeta();
		List<Map<String, String>> mapList = new ArrayList<Map<String,String>>();
		for(Map.Entry<String, String> metD:metaDat.entrySet()){
			Boolean judgeFlag = false;
			if(line != null){
				judgeFlag = judgeKeyValue(line.split(","),metD.getKey());
			}
			//过滤已经配置了的属性值
			if(!judgeFlag){
				Map<String, String> map = new HashMap<String,String>();
				map.put("name", metD.getKey());
				map.put("label", metD.getValue());
				mapList.add(map);
			}
			
		}
		//添加所有配置项
		returnSource.put("all", mapList);
		
		
		List<Map<String, String>> configItemList = new ArrayList<Map<String,String>>();
		
		Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
      	attributeList = nt.getAttributes();
		 if(line != null && line.indexOf(",")>0&&!line.startsWith("#")){
			  String s[]=line.split(",");
			  for(String a : s)
			  {
				  if(attributeList.get(a.toLowerCase())!= null)
				  {
	              		Map<String, String> cellMap = new HashMap<String, String>();
	              		cellMap.put("label", attributeList.get(a.toLowerCase()).getLabel());
	              		cellMap.put("name", a.toLowerCase());
	              		configItemList.add(cellMap);
				  }
         		
			  }
		 }
		//添加所有配置项
		returnSource.put("configed", configItemList);
		
		//获取已经配置了的选项
    	String json = JackJson.fromObjectToJson(returnSource);
		return json;
	}
	
	/**
	 * 根据配置的属性获取typeName
	 * @param typeAttrName
	 * @return
	 */
	public String getOraTypeName(String typeAttrName){
		String oraTypeName = "";
        List<Node> nodes = cmdbServiceProxy.getCiType();
        Map<String, String> map = new HashMap<String,String>();
    	for(Node node:nodes){
			map.put(node.getName().substring(0,1).toLowerCase()+node.getName().substring(1,node.getName().length())+"Attribute", node.getName());
    	}
    	oraTypeName = map.get(typeAttrName);
		return oraTypeName;
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
	

	public String getCitTypeAtrrByNameAll(String typeName) {
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
		String line = getCiConfigPropertity(typeName);
		if(nt == null)
			return "";
		Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
      	attributeList = nt.getAttributes();
      	if(!attributeList.isEmpty())
      	{
      		Map<String, Object> ciMap_1 = new HashMap<String, Object>();
            ciMap_1.put("label", "系统显示名称");
            ciMap_1.put("name", "ciLabel");
            ciMap_1.put("index", "ciLabel");
            ciMap_1.put("width", "60px"); 
            ciMap_1.put("align", "left");
            ciMap_1.put("editable", true);
//            ciMap_1.put("hidden", "false");
            
            Map<String, Object> ciMap2 = new HashMap<String, Object>();
            ciMap2.put("label", "系统名称");
            ciMap2.put("name", "name");
            ciMap2.put("index", "name");
            ciMap2.put("width", "60px"); 
            ciMap2.put("align", "left");
            mapList.add((HashMap<String, Object>) ciMap_1);
            mapList.add((HashMap<String, Object>) ciMap2);
            
            //特殊处理后台手动添加了相应显示字段，且此字段没有在文件中配置
            if(typeName.equals("Host")){
            	
            	Map<String, Object> hostDisk = new HashMap<String, Object>();
            	hostDisk.put("label", "主机磁盘");
            	hostDisk.put("name", "hostdisk");
            	hostDisk.put("index", "hostdisk");
            	hostDisk.put("width", "160px"); 
            	hostDisk.put("align", "left");
            	hostDisk.put("editable", true);
            	
            	Map<String, Object> hostHba = new HashMap<String, Object>();
            	hostHba.put("label", "主机HBA关联");
            	hostHba.put("name", "hosthba");
            	hostHba.put("index", "hosthba");
            	hostHba.put("width", "160px"); 
            	hostHba.put("align", "left");
            	hostHba.put("editable", true);
            	mapList.add((HashMap<String, Object>) hostDisk);
                mapList.add((HashMap<String, Object>) hostHba);
            }else if(typeName.equals("HostHba")){
            	Map<String, Object> hostHba = new HashMap<String, Object>();
            	hostHba.put("label", "主机HBA关联");
            	hostHba.put("name", "hosthba");
            	hostHba.put("index", "hosthba");
            	hostHba.put("width", "160px"); 
            	hostHba.put("align", "left");
            	hostHba.put("editable", true);
            	mapList.add((HashMap<String, Object>) hostHba);
            }else if(typeName.equals("SanZoneSet")){
            	Map<String, Object> storagenetport = new HashMap<String, Object>();
            	storagenetport.put("label", "存储关联");
            	storagenetport.put("name", "storagenetport");
            	storagenetport.put("index", "storagenetport");
            	storagenetport.put("width", "160px"); 
            	storagenetport.put("align", "left");
            	storagenetport.put("editable", true);
            	
            	Map<String, Object> hostHba = new HashMap<String, Object>();
            	hostHba.put("label", "HBA关联");
            	hostHba.put("name", "hosthba");
            	hostHba.put("index", "hosthba");
            	hostHba.put("width", "160px"); 
            	hostHba.put("align", "left");
            	hostHba.put("editable", true);
            	mapList.add((HashMap<String, Object>) storagenetport);
                mapList.add((HashMap<String, Object>) hostHba);
            } else if(typeName.equals("Storage")){
            	Map<String, Object> storagenetport1 = new HashMap<String, Object>();
            	storagenetport1.put("label", "存储关联信息");
            	storagenetport1.put("name", "stohostre");
            	storagenetport1.put("index", "stohostre");
            	storagenetport1.put("width", "160px"); 
            	storagenetport1.put("align", "left");
            	storagenetport1.put("editable", true);
            	
            	Map<String, Object> stohost = new HashMap<String, Object>();
            	stohost.put("label", "存储关联主机");
            	stohost.put("name", "stohost");
            	stohost.put("index", "stohost");
            	stohost.put("width", "160px"); 
            	stohost.put("align", "left");
            	stohost.put("editable", true);
            	mapList.add((HashMap<String, Object>) storagenetport1);
                mapList.add((HashMap<String, Object>) stohost);
            }
            
    		 if(line!= null &&line.indexOf(",")>0&&!line.startsWith("#")){
    			  String s[]=line.split(",");
    			  for(String a : s)
    			  {
    				  if(attributeList.get(a.toLowerCase())!= null)
    				  {
    					  Log.debug("a:"+a);
    	              		Map<String, Object> cellMap = new HashMap<String, Object>();
    	              		cellMap.put("label", attributeList.get(a.toLowerCase()).getLabel());
    	              		cellMap.put("name", a.toLowerCase());
    	              		cellMap.put("index", a.toLowerCase());
    	              		cellMap.put("width", "60px"); 
    	              		cellMap.put("align", "left");
    	              		cellMap.put("editable", true);
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
//	              		cellMap.put("hidden", "false");
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
}
