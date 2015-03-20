package com.hp.avmon.ciMgr.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.common.jackjson.JackJson;
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
public class CiMgrService {
	private static IdGenerator idg=IdGenerator.createGenerator();
	private static final Logger logger = LoggerFactory
			.getLogger(CiMgrService.class);
	
	@Autowired
    private CmdbService cmdbServiceProxy;
	
	@Autowired
	private CmdbViewService cmdbViewServiceProxy;

	private JdbcTemplate jdbc;
	private static int RELATION_TYPE_INCLUDE = 0;
    /**
     * 构造函数
     */
    public CiMgrService() {
    	// 加载连接对象
    	jdbc = SpringContextHolder.getBean("jdbcTemplate");
    }

    /**
     * 得到一级节点
     * @return
     */
	public String getBaseClasses(String id) {
        String sql  = null;
        boolean flag = false;
        String json = null;
        Map<String,Object> rootNode = new HashMap<String, Object>();
        if (null == id ||"".equals(id)) {
               //sql  = SqlManager.getSql(ClassMgrService.class, "getBaseClasses");
               flag = true;
        }else{
               //sql = String.format(SqlManager.getSql(ClassMgrService.class, "getClassNodes"),id);
        }
//      List<Map<String,Object>> list =  jdbc.queryForList(sql);
        
        if(flag){
               //子节点集合
        List<Node> list = cmdbServiceProxy.getCiType();
        ArrayList<HashMap<String,Object>> classList = new ArrayList<HashMap<String,Object>>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HashMap<String,Object> map = null;
        for (Node node : list) {
               map = new HashMap<String, Object>();
               map.put("id", node.getId());
//             map.put("name", node.getName());
               map.put("name", node.getLabel());
//             map.put("icon", "../resources/images/motype/"+node.getIcon()+".png");
               map.put("icon", "../resources/images/motype/"+"HOST"+".png");
               map.put("derivedFrom", node.getDerivedFrom());
               map.put("typeName", node.getName());
               //map.put("parentId", node.getParentId());
               //map.put("pId", node.getParentId());
               map.put("getIsType", node.getIsType());
               map.put("path", node.getPath());
               map.put("updateTime", format.format(node.getUpdateTime().getTime()));
               map.put("createTime", format.format(node.getCreateTime().getTime()));
               map.put("version", node.getVersion());
               map.put("accessRoles", node.getAccessRoles());
               map.put("accessUsers", node.getAccessUsers());
               map.put("domain", node.getDomain());
               //map.put("updateMode", node.getUpdateMode());
               classList.add(map);
               }
               
               json = JackJson.fromObjectToJson(classList);
        }else{
               //TODO 如果是异步加载,就需要根据ID查询子节点
               //List<Node> list = cmdbServiceProxy.getCiTypeByParentId(long id);
//             json=JackJson.fromObjectToJson(list);
        }
        
        logger.info(json);
        return json;

	}
	
	
	  /**
     * 得到所有的类
     * @return
     */
    public String getAllClass(HttpServletRequest request){
       
       String sField = request.getParameter("searchField");
       String sValue = request.getParameter("searchString");
       String sOper = request.getParameter("searchOper");
       
       
       String page = request.getParameter("page");//页码
       String rows = request.getParameter("rows");//每页记录条数
       String sidx = request.getParameter("sidx");//排序字段
       String sord = request.getParameter("sord");//排序方向
       
       String pid = request.getParameter("id");
       
       
       String result = StringUtil.EMPTY;
       List<Node> list = cmdbServiceProxy.getCiType();
       ArrayList<HashMap<String,Object>> classList = new ArrayList<HashMap<String,Object>>();
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       HashMap<String,Object> map = null;
       for (Node node : list) {
              map = new HashMap<String, Object>();
              map.put("id", node.getId());
              map.put("name", node.getName());
//            map.put("icon", "../resources/images/motype/"+node.getIcon()+".png");
              map.put("icon", "../resources/images/motype/"+"HOST"+".png");
              map.put("derivedFrom", node.getDerivedFrom());
              map.put("label", node.getLabel());
              map.put("parentId", node.getParentId());
              map.put("pid", node.getParentId());
              map.put("pId", node.getParentId());
              map.put("getIsType", node.getIsType());
              map.put("path", node.getPath());
              map.put("updateTime", format.format(node.getUpdateTime().getTime()));
              map.put("createTime", format.format(node.getCreateTime().getTime()));
              map.put("version", node.getVersion());
              map.put("accessRoles", node.getAccessRoles());
              map.put("accessUsers", node.getAccessUsers());
              map.put("domain", node.getDomain());
              //map.put("updateMode", node.getUpdateMode());
              classList.add(map);
              }
       
//    if(StringUtil.isEmpty(pid)){
//            
//    }
       
       
       result=JackJson.fromObjectToJson(classList);
       
//    result = "[{id:1, pId:0, name:\"Hardware\", iconSkin:\"icon_hardware\", open:true},"+
//    "{id:2, pId:1, name:\"Network\", iconSkin:\"icon_network\"},"+
//     "{id:3, pId:1, name:\"Server\", iconSkin:\"icon_server\", open:true}]";
       logger.debug(result);
       return result;
    }


	/**
	 * 添加子节点
	 * @param id
	 * @return
	 */
	public Map<String,String> addClass(String id, String name, String pid,String displayIcon,String label,String derivedFrom) {
		Map<String,String> message = new HashMap<String,String>();
		try {
			if(null == name||"".equals(name)){
				message.put("error", "添加失败！");
				message.put("result", "1");
				return message;
			}
			Node nt=new Node();
			nt = cmdbServiceProxy.getCiTypeByName(derivedFrom);
			nt.setId(idg.generate());
			nt.setName(name);
			nt.setLabel(label);
			nt.setIcon(displayIcon);
			nt.setParentId(Long.parseLong(pid));
			cmdbServiceProxy.insertNode(nt);
			
//			String[] sqls = new String[2];
//			String classSql = SqlManager.getSql(ClassMgrService.class, "addClass"); 
//			classSql = String.format(classSql, id,name,displayIcon);
//			sqls[0] = classSql;
//			String classRelation = SqlManager.getSql(ClassMgrService.class, "addClassRelation");
//			classRelation = String.format(classRelation, pid,id,RELATION_TYPE_INCLUDE);
//			sqls[1] = classRelation;
//			jdbc.batchUpdate(sqls);
			message.put("success", "添加成功！");
			message.put("result", "0");
		} catch (Exception e) {
			logger.debug(e.getMessage());
			message.put("error", "添加失败！");
			message.put("result", "1");
			return message;
		}
		
		return message;
	}
	
	/**
	 * 修改子节点
	 * @param id
	 * @return
	 */
	@SuppressWarnings("finally")
	public String editClass(String id, String name) {
		String result = null;
		HashMap<String, String> message = new HashMap<String, String>();
		try {
			if(null==id ||null == name||"".equals(id)||"".equals(name)){
				message.put("success", "修改失败！");
				message.put("result", "1");
			}
			
			String isRepeatSql = SqlManager.getSql(CiMgrService.class, "isRepeatName");
			int count = jdbc.queryForInt(isRepeatSql);
			if (count > 0) {
				message.put("success", "该名称已存在失败！");
				message.put("result", "1");
			}
			
			String classSql = SqlManager.getSql(CiMgrService.class, "editClass"); 
			classSql = String.format(classSql,id,name,id);
			jdbc.update(classSql);
			message.put("success", "修改成功！");
			message.put("result", "0");
			 
		} catch (Exception e) {
			logger.debug(e.getMessage());
			message.put("success", "修改失败！");
			message.put("result", "1");
		}finally{
			result = JackJson.fromObjectToJson(message);
			return result;
		}
	}
	
	public String delCiInfo(HttpServletRequest request){
		String ciId = request.getParameter("id");
		String typeName = request.getParameter("typeName");
		HashMap<String, String> message = new HashMap<String, String>();
		try {
			if(ciId.indexOf(",") != -1){
				String[] ciids =ciId.split(",");
				List<Long> ciidsLong = new ArrayList<Long>();
				for(String s:ciids){
					ciidsLong.add(Long.parseLong(s));
				}
				cmdbServiceProxy.deleteNodes(typeName, ciidsLong);
			}else{
				int count = cmdbServiceProxy.deleteNode(typeName, Long.parseLong(ciId));
			}
			
			

			message.put("success", "删除成功！");
			message.put("result", "0");
		} catch (NumberFormatException e) {
			message.put("success", "删除失败！");
			message.put("result", "1");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CmdbException e) {
			message.put("success", "删除失败！");
			message.put("result", "1");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return JackJson.fromObjectToJson(message);
	}
	
	/**
	 * 修改子节点
	 * @param id
	 * @return
	 */
	public String delClass(String id) {
		String result = null;
		HashMap<String, String> message = new HashMap<String, String>();
		
		if(null==id||"".equals(id)){
			message.put("error", "修改失败！");
			return JackJson.fromObjectToJson(message);
		}

		String isParentClassSql = SqlManager.getSql(CiMgrService.class, "isParentClass");
		isParentClassSql = String.format(isParentClassSql, id);
		int childrenCount = jdbc.queryForInt(isParentClassSql);
		//判断是否有子节点
		if (childrenCount > 0) {
			message.put("error", "不能删除父节点,删除失败！");
			return JackJson.fromObjectToJson(message);
		}
		
		//删除节点
		String delClassSql = SqlManager.getSql(CiMgrService.class, "delClass"); 
		delClassSql = String.format(delClassSql, id);
		String[] sqls = new String[2];
		sqls[0] = delClassSql;
		String delClassRelation = SqlManager.getSql(CiMgrService.class, "delClassRelation");
		delClassRelation = String.format(delClassRelation,id);
		sqls[1] = delClassRelation;
		
		try {
			jdbc.batchUpdate(sqls);
			message.put("success", "删除成功！");
			message.put("result", "0");
			result = JackJson.fromObjectToJson(message);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			message.put("success", "删除失败！");
			message.put("result", "1");
		}
		
		return result;
	}

	/**
	 * 根据ID查询类属性
	 * @param id
	 * @return
	 */
	public String getCibyType(String typeName) {
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
		ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
		
            	for(Node n : ciList)
            	{
                    Map<String, Object> cellMap = new HashMap<String, Object>();
                    cellMap.put("id", n.getId().toString());
                    cellMap.put("name", n.getName());
                    cellMap.put("label", n.getLabel());
                    mapList.add((HashMap<String, Object>) cellMap);  
            	}
            	


		String json = JackJson.fromObjectToJson(mapList);
		return json;
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
					       		cellMap.put("ciname", n.getName());
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
			       		@SuppressWarnings("rawtypes")
						Map<String, Object> cellMap = new HashMap<String, Object>();   
			       		cellMap.put("id", n.getId());
			       		cellMap.put("ciLabel", n.getLabel());
			       		cellMap.put("ciname", n.getName());
			       		
					  //获取当前节点属性值
						Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
				       	attributeList = n.getAttributes();
				       	if(!attributeList.isEmpty())
				       	{

				       		for(Map.Entry<String, CiAttribute> ciMap:attributeList.entrySet()){
				       			cellMap.put(ciMap.getValue().getName(), ciMap.getValue().getValue());
				       		}
				       		
				       	 
				       	}
				       	mapList.add((HashMap<String, Object>) cellMap);  
	            	}
				}
		            	
		            	


				String json = JackJson.fromObjectToJson(mapList);
				return json;
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
	
	
	/**
	 * 创建ci信息
	 * @param request
	 * @return
	 */
	public String addCiInfoBytype(HttpServletRequest request){
		
		Map<String,Object> message = new HashMap<String,Object>();
		
		String name = request.getParameter("name");
		String label = request.getParameter("label");
		String domain = request.getParameter("domain");
		String attribute = request.getParameter("attribute");
		String typeName = request.getParameter("typeName");
		//获取原始ci配置信息
		Node node = null;
		List<Node> ciList = new ArrayList<Node>();
		try {
			node = cmdbServiceProxy.getCiTypeByName(typeName);
			//ciList = cmdbServiceProxy.getCiByTypeName(typeName);
		
		//Node subNode = ciList.get(0);
		
		
		Node newNode = new Node();
		Long id = idg.generate();
		newNode.setId(id);
		newNode.setName(name);
		newNode.setDomain(domain);
		newNode.setLabel(label);
		newNode.setIsType(false);
		newNode.setDerivedFrom(typeName);
		newNode.setParentId(node.getId());//实例的父节点应该为类型的id
		newNode.setIcon(node.getIcon());
		newNode.setPath(node.getPath());
		newNode.setExchangedId(node.getExchangedId());
		newNode.setAccessRoles(node.getAccessRoles());
		newNode.setAccessUsers(node.getAccessUsers());
		
		
		//设置attrobute
		if(attribute != null){
			Map<String, CiAttribute> attributes = node.getAttributes(); 
			
			//重置所有属性值
			String ciConfig = this.getCiConfigPropertity(typeName);
			if(ciConfig != null){
				String[] line = ciConfig.split(",");
				for(String key:line){
					if(attributes.containsKey(key)){
						CiAttribute ciattr = attributes.get(key);
						ciattr.setValue("");
						attributes.put(key, ciattr);
					}
				}
			}
			
			String[] attrArr = attribute.split("[|]");
			if(attrArr != null && attrArr.length == 2){
				String[] keyAttr = attrArr[0].split(",");
				String[] valueAttr = attrArr[1].split(",");
				
				//设置已经存在属性
				for(int i=0;i<keyAttr.length;i++){
					String key = keyAttr[i];
					String value = valueAttr[i];
					if(attributes.containsKey(key)){
						CiAttribute ciattr = attributes.get(key);
						ciattr.setValue(value);
						attributes.put(key, ciattr);
					}
				}
			}
			
			
			newNode.setAttributes(attributes);
			System.out.print(newNode);
		}
		
		Node insertNode = cmdbServiceProxy.insertNode(newNode);
		
		message.put("success", "添加成功！");
		message.put("result", "0");
		
		} catch (CmdbException e) {
			
			message.put("error", "添加失败！");
			message.put("result", "1");
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
	public String addCibyType(String typeName,String name,String label) {
		//Node nt=new Node();
		Map<String,Object> message = new HashMap<String,Object>();
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
		Map<String, Comparable> nodeMap = new HashMap<String, Comparable>();
		nodeMap.put("id", nt.getId());
		nodeMap.put("name", nt.getLabel());
		//nodeMap.put("pId", nt.getParentId());
		nodeMap.put("typeName", typeName);
		try {
			cmdbServiceProxy.insertNode(nt);
			message.put("success", "添加成功！");
			message.put("result", "0");
			message.put("node", nodeMap);
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			message.put("error", "添加失败！");
			message.put("result", "1");
			message.put("node", "");
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
	public String updateCibyType(String typeName,String id,String name,String label) {
		//Node nt=new Node();
		Map<String,String> message = new HashMap<String,String>();
		Node nt = new Node();
		try {
			if(null == typeName||"".equals(typeName))
			{
				typeName = "Ci";
				}
			nt = cmdbServiceProxy.getCiByCiId(typeName,new Long(id));
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//nt.setName(name);
		nt.setLabel(label);
		try {
			cmdbServiceProxy.updateNode(nt);
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
	 * 更新ci信息
	 * @param request
	 * @return
	 */
	public String updateCiInfo(HttpServletRequest request){
		Map<String,String> message = new HashMap<String,String>();
		
		String typeName = request.getParameter("typeName");
		String ciId = request.getParameter("id");
		String label = request.getParameter("ciLabel");
		Map<String,String[]> parmap = request.getParameterMap();
		//获取原来的ci实例信息
		Node sourceNode = new Node();
		try {
			sourceNode = cmdbServiceProxy.getCiByCiId(typeName, Long.parseLong(ciId));
			sourceNode.setLabel(label);
		Map<String,CiAttribute> ciAttribute = sourceNode.getAttributes();
		for(Map.Entry<String, String[]> paraMap:parmap.entrySet()){
			//如果当前ci实例包含传递过来的参数则覆盖其值
		    if(ciAttribute.containsKey(paraMap.getKey())){
		    	CiAttribute ci = ciAttribute.get(paraMap.getKey());
		    	String val = paraMap.getValue()[0];
		    	ci.setValue(val);
		    	ciAttribute.put(paraMap.getKey(), ci);
		    }
		}
		//更新ci node
		cmdbServiceProxy.updateNode(sourceNode);
		message.put("success", "修改成功！");
		message.put("result", "0");
		} catch (NumberFormatException e) {
			message.put("error", "修改失败！");
			message.put("result", "1");			
			e.printStackTrace();
		} catch (CmdbException e) {
			message.put("error", "修改失败！");
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
	public String addCiTypeAttr(String typeName) {
		String result = null;
		HashMap<String, String> message = new HashMap<String, String>();
		Node nt=new Node();
		try {
			if(typeName.equals("null"))
			{
				typeName = "Ci";
				}
			nt = cmdbServiceProxy.getCiTypeByName(typeName);
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
		

        		Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
        		CiAttribute newAttr = new CiAttribute();
        		newAttr.setLabel("类属性三");
            	attributeList = nt.getAttributes();
            	attributeList.put("attr_3", newAttr);
            	nt.setAttributes(attributeList);
//            	for(Object o : attributeList.keySet())
//            	{
//                    Map cellMap = new HashMap();  
//                    cellMap.put("name", attributeList.get(o).getName());
//                    cellMap.put("dataType", attributeList.get(o).getDataType());
//                    cellMap.put("value", attributeList.get(o).getValue());
//                    cellMap.put("attGroup", attributeList.get(o).getAttGroup());
//                    cellMap.put("order", attributeList.get(o).getOrder());
//                    cellMap.put("defValue", attributeList.get(o).getDefValue());
//                    cellMap.put("viewMode", attributeList.get(o).getViewMode());
//                    cellMap.put("isRequired", attributeList.get(o).isRequired());
//                    cellMap.put("recordChange", attributeList.get(o).isRecordChange());
//                    cellMap.put("updateMode", attributeList.get(o).getUpdateMode());
//                    cellMap.put("label", attributeList.get(o).getLabel());
//                    mapList.add((HashMap<String, Object>) cellMap);  
//            	}
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
	public String getCiTypeAttrbyName(String typeName) {
		Node nt=new Node();
		try {
			if(typeName.equals("null"))
			{
				typeName = "Ci";
				}
			nt = cmdbServiceProxy.getCiTypeByName(typeName);
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
		

        		Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
            	attributeList = nt.getAttributes();
            	for(Object o : attributeList.keySet())
            	{
                    Map<String, Object> cellMap = new HashMap<String, Object>();  
                    cellMap.put("name", attributeList.get(o).getName());
                    cellMap.put("dataType", attributeList.get(o).getDataType());
                    cellMap.put("value", attributeList.get(o).getValue());
                    cellMap.put("attGroup", attributeList.get(o).getAttGroup());
                    cellMap.put("order", attributeList.get(o).getOrder());
                    cellMap.put("defValue", attributeList.get(o).getDefValue());
                    cellMap.put("viewMode", attributeList.get(o).getViewMode());
                    cellMap.put("isRequired", attributeList.get(o).isRequired());
                    cellMap.put("recordChange", attributeList.get(o).isRecordChange());
                    cellMap.put("updateMode", attributeList.get(o).getUpdateMode());
                    cellMap.put("label", attributeList.get(o).getLabel());
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
//		String line = PropertyUtils.getProperty("hostAttribute");
		String line = this.getCiConfigPropertity(typeName);
		Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
      	attributeList = nt.getAttributes();
      
        Map<String, Object> ciMap_1 = new HashMap<String, Object>();
        ciMap_1.put("label", "显示名");
        ciMap_1.put("name", "ciLabel");
        ciMap_1.put("index", "ciLabel");
        ciMap_1.put("width", "60px"); 
        ciMap_1.put("align", "left");
//        ciMap_1.put("hidden", "false");
        mapList.add((HashMap<String, Object>) ciMap_1);
        
		 if(line.indexOf(",")>0&&!line.startsWith("#")){
			  String s[]=line.split(",");
			  for(String a : s)
			  {
          		System.out.println("a:"+a);
          		Map<String, Object> cellMap = new HashMap<String, Object>();
          		cellMap.put("label", attributeList.get(a.toLowerCase()).getLabel());
          		cellMap.put("name", a.toLowerCase());
          		cellMap.put("index", a.toLowerCase());
          		cellMap.put("width", "60px"); 
          		cellMap.put("align", "left");
//          		cellMap.put("hidden", "false");
          		mapList.add((HashMap<String, Object>) cellMap);
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
//              		cellMap.put("hidden", "false");
              		mapList.add((HashMap<String, Object>) cellMap);
			 }
		 }
		
          Map<String, Object> ciMap = new HashMap<String, Object>();
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
	
	public String getCitAttrbyName2(String typeName) {
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
    	              		Map<String, Object> cellMap = new HashMap<String, Object>();
    	              		cellMap.put("label", attributeList.get(a.toLowerCase()).getLabel());
    	              		cellMap.put("name", a.toLowerCase());
    	              		cellMap.put("index", a.toLowerCase());
    	              		cellMap.put("width", "60px"); 
    	              		cellMap.put("align", "left");
//    	              		cellMap.put("hidden", "false");
    	              		mapList.add((HashMap<String, Object>) cellMap);
    				  }
              		
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
          mapList.add((HashMap<String, Object>) ciMap);
          

          


		String json = JackJson.fromObjectToJson(mapList);
		return json;
	}
	
	@SuppressWarnings("unchecked")
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
		if(nt == null)
			return "";
		
		ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
		String line = this.getCiConfigPropertity(typeName);

		//设置规则是否必填
        Map<String,Object> editrules1 = new HashMap<String, Object>();
        editrules1.put("required", true);
        
  		Map<String, Object> ciMap_1 = new HashMap<String, Object>();
        ciMap_1.put("label", "系统显示名称*");
        ciMap_1.put("name", "ciLabel");
        ciMap_1.put("index", "ciLabel");
        ciMap_1.put("width", "60px"); 
        ciMap_1.put("align", "left");
        ciMap_1.put("isRequired", true);
        ciMap_1.put("editable", true);
        ciMap_1.put("editrules", editrules1);
//        ciMap_1.put("hidden", "false");
        mapList.add((HashMap<String, Object>) ciMap_1);
        

        Map<String, Object> ciMap2 = new HashMap<String, Object>();
        ciMap2.put("label", "系统名称*");
        ciMap2.put("name", "ciname");
        ciMap2.put("index", "ciname");
        ciMap2.put("width", "60px"); 
        ciMap2.put("align", "left");
        ciMap2.put("isRequired", true);
        ciMap2.put("editrules", editrules1);
        mapList.add((HashMap<String, Object>) ciMap2);
        
		Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
      	attributeList = nt.getAttributes();
      	if(!attributeList.isEmpty())
      	{
			 for(Map.Entry<String, CiAttribute> mm:attributeList.entrySet()){
			 	Map<String, Object> cellMap = new HashMap<String, Object>();
         		cellMap.put("label", mm.getValue().getLabel().toLowerCase());
         		cellMap.put("name", mm.getValue().getName().toLowerCase());
         		cellMap.put("index", mm.getValue().getName().toLowerCase());
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
         		cellMap.put("hidden", true);
         		if(line!= null && line.toLowerCase().indexOf(mm.getValue().getName().toLowerCase()) > -1&&!line.startsWith("#")){
         			cellMap.put("hidden", mm.getValue().getViewMode() == 2?true:false);
         		}
         	    //是否必填信息
         		cellMap.put("isRequired", mm.getValue().isRequired());
         		//获得分组信息
         		cellMap.put("group", mm.getValue().getAttGroup());
         		mapList.add((HashMap<String, Object>) cellMap);
		    }
			 
//    		if(line!= null && line.indexOf(",")>0&&!line.startsWith("#")){
//    			  String s[]=line.split(",");
//    			  for(String a : s)
//    			  {
//    				  if(attributeList.get(a.toLowerCase())!= null)
//    				  {
//    					  	System.out.println("a:"+a);
//    	              		Map<String, Object> cellMap = new HashMap<String, Object>();
//    	              		cellMap.put("label", attributeList.get(a.toLowerCase()).getLabel());
//    	              		cellMap.put("name", a.toLowerCase());
//    	              		cellMap.put("index", a.toLowerCase());
//    	              		cellMap.put("width", "60px");
//    	              		cellMap.put("align", "left");
//    	              		cellMap.put("editable", true);
//    	              		//添加验证类型
//    	              		Map<String,Object> editrules = new HashMap<String, Object>();
//    	              		editrules.put("required", attributeList.get(a.toLowerCase()).isRequired());
//    	              		cellMap.put("editrules", JackJson.fromObjectToJson(editrules));
//    	              		//设置默认值
//    	              		cellMap.put("value", attributeList.get(a.toLowerCase()).getDefValue());
//    	              		//设置显示状态
//    	              		cellMap.put("hidden", attributeList.get(a.toLowerCase()).getViewMode() == 2?true:false);
//    	              		//是否必填信息
//    	              		cellMap.put("isRequired", attributeList.get(a.toLowerCase()).isRequired());
//    	              		//获得分组信息
//    	              		cellMap.put("group", attributeList.get(a.toLowerCase()).getAttGroup());
//    	              		mapList.add((HashMap<String, Object>) cellMap);
//    				 }
//    			 }
//    		 }
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
      
      	Map<String, Object> opci = new HashMap<String, Object>();
      	opci.put("label", "操作");
      	opci.put("name", "op");
      	opci.put("index", "op");
      	opci.put("width", "60px"); 
      	opci.put("align", "center");
      	ciMap.put("editable", false);
      	mapList.add((HashMap<String, Object>) opci);
          
		String json = JackJson.fromObjectToJson(mapList);
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
					nt = cmdbServiceProxy.getCiByCiId(typeName,new Long(ciId));
					attributeList = nt.getAttributes();
					newAttr = attributeList.get(name);
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
	public String getCiAttr(String typeName,String ciId) {
		//如果ciid为“”则说明是请求的相应typeName的ci公共属性
		if(ciId.equals("")){
			
		}
		
		Node nt=new Node();
		try {
			if(typeName.equals("null"))
			{
				typeName = "Ci";
			}
			if(ciId.equals("") || ciId == null || ciId.equals("null")){
				nt = cmdbServiceProxy.getCiTypeByName(typeName);
			}else{
				nt = cmdbServiceProxy.getCiByCiId(typeName,new Long(ciId));
			}
			
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
		
        Map<String, Object> cellMap = new HashMap<String, Object>();  
        cellMap.put("name", "ciLabel");
        cellMap.put("label", "系统显示名称");
        cellMap.put("value", nt.getLabel());
		cellMap.put("order", 0);
        mapList.add((HashMap<String, Object>) cellMap);
        
		Map<String, CiAttribute> attributeList = new HashMap<String, CiAttribute> ();
		nt.sortAtt();
    	attributeList = nt.getAttributes();
    	for(Object o : attributeList.keySet())
    	{
            cellMap = new HashMap<String, Object>();  
            cellMap.put("name", attributeList.get(o).getName());
            cellMap.put("label", attributeList.get(o).getLabel());
            cellMap.put("value", attributeList.get(o).getValue());
			cellMap.put("order", attributeList.get(o).getOrder());
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
	public String showProperty(String typeName) {
		String result = null;
		HashMap<String, String> message = new HashMap<String, String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		HashMap<String, Object> map = new HashMap<String, Object>();
		Node node=new Node();
		try {
			if(null == typeName||"".equals(typeName))
			{
				typeName = "Ci";
				}
			node = cmdbServiceProxy.getCiTypeByName(typeName);
			
	        map.put("id", node.getId());
	        map.put("name", node.getLabel());
	        map.put("icon", "../resources/images/motype/"+"HOST"+".png");
	        map.put("derivedFrom", node.getDerivedFrom());
	        map.put("typeName", node.getName());
	        map.put("parentId", node.getParentId());
	        map.put("pId", node.getParentId());
	        map.put("getIsType", node.getIsType());
	        map.put("path", node.getPath());
	        map.put("updateTime", format.format(node.getUpdateTime().getTime()));
	        map.put("createTime", format.format(node.getCreateTime().getTime()));
	        map.put("version", node.getVersion());
	        map.put("accessRoles", node.getAccessRoles());
	        map.put("accessUsers", node.getAccessUsers());
	        map.put("domain", node.getDomain());
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 	
		String json = JackJson.fromObjectToJson(map);
		return json;
	}
	
	
	/**
	 * 获取所有关系类型
	 * @return
	 */
	public String getRelationList(){
		List<RelationNode> relationNodeList = new ArrayList<RelationNode>();
		relationNodeList = cmdbServiceProxy.getRelationType();
		List<Map<String, String>> mapList = new ArrayList<Map<String,String>>();
		
		for(RelationNode reNode:relationNodeList){
			if(!reNode.getName().equals("RelationCi")){
				Map<String, String> map = new HashMap<String,String>();
				map.put("name", reNode.getName());
				map.put("label", reNode.getLabel());
				mapList.add(map);
			}
		}
		
		String json = JackJson.fromObjectToJson(mapList);
		return json;
	}
	
	public String getNodeRelationMapL1(HttpServletRequest request){
		//定义返回的数据源已经 key为相应的typeName value是已经格式化好的前台可用数据
		Map<String,ArrayList<HashMap<String,Object>>> returnSource = new HashMap<String, ArrayList<HashMap<String,Object>>>();
		
		String startNodeType = request.getParameter("startNodeType");
		String startNodeId = request.getParameter("startNodeId");
		String relationName = request.getParameter("relationName");
		Map<String,List<Node>> relationNodeMapList = cmdbViewServiceProxy.getNodeRelationMapLv1(startNodeType, Long.parseLong(startNodeId), relationName);
		
		for(Map.Entry<String,List<Node>> nodeMap:relationNodeMapList.entrySet()){
			String curTypeName = nodeMap.getKey();
			ArrayList<HashMap<String,Object>> curTypeList = this.getWebGridData(curTypeName, nodeMap.getValue());
			returnSource.put(curTypeName, curTypeList);
		}

		String json = JackJson.fromObjectToJson(returnSource);
		return json;
	}
	
	/**
	 * 获取web端可用grid数据
	 * @param typeName
	 * @param nodeList
	 * @return
	 */
	@SuppressWarnings("unused")
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
			       		cellMap.put("ciname", n.getName());
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
			            mapList.add((HashMap<String, Object>) cellMap);  
			       	}
          	} 
		}
		
		return mapList;
	}
	
}
