package com.hp.avmon.relMgr.service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmon.utils.StringUtil;
import com.hp.xo.uip.cmdb.domain.CiAttribute;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.service.CmdbService;
import com.hp.xo.uip.cmdb.util.IdGenerator;

/**
 * Handles requests for the application home page.
 */
@Service
public class RelMgrService {
	private static IdGenerator idg=IdGenerator.createGenerator();
	private static final Logger logger = LoggerFactory
			.getLogger(RelMgrService.class);
	
	@Autowired
    private CmdbService cmdbServiceProxy;

	private JdbcTemplate jdbc;
	private static int RELATION_TYPE_INCLUDE = 0;
    /**
     * 构造函数
     */
    public RelMgrService() {
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
        List<RelationNode> list= cmdbServiceProxy.getRelationType();
        ArrayList<HashMap<String,Object>> classList = new ArrayList<HashMap<String,Object>>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HashMap<String,Object> map = null;
        for (RelationNode node : list) {
               map = new HashMap<String, Object>();
               map.put("id", node.getId());
//             map.put("name", node.getName());
               map.put("name", node.getLabel());
//             map.put("icon", "../resources/images/motype/"+node.getIcon()+".png");
               map.put("icon", "../resources/images/motype/"+"HOST"+".png");
               map.put("derivedFrom", node.getDerivedFrom());
               map.put("typeName", node.getName());
//               map.put("parentId", node.getParentId());
//               map.put("pId", node.getParentId());
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
     * 得到一级节点
     * @return
     */
	public String getCiList(String id) {
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
	public Map<String,Object> addClass(String id, String name, String pid,String displayIcon,String label,String derivedFrom,String isEdit,String domain) {
		Map<String,Object> message = new HashMap<String,Object>();
		try {
			if((null == name||"".equals(name))  && (null == derivedFrom||"".equals(derivedFrom)) ){
				message.put("error", "添加失败！");
				message.put("result", "-1");
				message.put("node", "");
				return message;
			}
			if(isEdit.equals("true"))
			{
				Node nt=new Node();
				nt = cmdbServiceProxy.getCiTypeByName(derivedFrom);
				//nt.setId(idg.generate());
				//nt.setName(newName);
				nt.setLabel(label);
				nt.setIcon(displayIcon);
				nt.setDomain(domain);
				//nt.setParentId(Long.parseLong(pid));
				cmdbServiceProxy.updateNode(nt);
				Map nodeMap = new HashMap();
				nodeMap.put("id", nt.getId());
				nodeMap.put("name", nt.getLabel());
				nodeMap.put("pId", nt.getParentId());
				nodeMap.put("typeName", nt.getName());
				message.put("success", "修改成功！");
				message.put("result", "1");
				message.put("node", nodeMap);
			}else
			{
				Node nt=new Node();
				nt = cmdbServiceProxy.getCiTypeByName(derivedFrom);
				nt.setId(idg.generate());
				nt.setName(name);
				nt.setLabel(label);
				nt.setIcon(displayIcon);
				nt.setParentId(Long.parseLong(pid));
				nt.setIsType(true);
				nt.setDomain(domain);
				cmdbServiceProxy.insertNode(nt);
				Map nodeMap = new HashMap();
				nodeMap.put("id", nt.getId());
				nodeMap.put("name", nt.getLabel());
				nodeMap.put("pId", nt.getParentId());
				nodeMap.put("typeName", nt.getName());
				message.put("success", "添加成功！");
				message.put("result", "0");
				message.put("node", nodeMap);
			}


		} catch (Exception e) {
			logger.debug(e.getMessage());
			message.put("error", "添加失败！");
			message.put("result", "-1");
			message.put("node", "");
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
			
			String isRepeatSql = SqlManager.getSql(RelMgrService.class, "isRepeatName");
			int count = jdbc.queryForInt(isRepeatSql);
			if (count > 0) {
				message.put("success", "该名称已存在失败！");
				message.put("result", "1");
			}
			
			String classSql = SqlManager.getSql(RelMgrService.class, "editClass"); 
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
	
	/**
	 * 修改子节点
	 * @param id
	 * @return
	 */
	public String delClass(String typeName,String ciid) {
		String result = null;
		HashMap<String, String> message = new HashMap<String, String>();
		
		if(null==typeName||"".equals(typeName)){
			message.put("error", "修改失败！");
			return JackJson.fromObjectToJson(message);
		}

		try {
			cmdbServiceProxy.deleteRelation(typeName, Long.parseLong(ciid));
			message.put("success", "删除成功！");
			message.put("result", "0");
			result = JackJson.fromObjectToJson(message);
		} catch (CmdbException e) {
			e.printStackTrace();
			message.put("success", "删除失败！");
			message.put("result", "1");
			result = JackJson.fromObjectToJson(message);
		}
		
		return result;
	}

	/**
	 * 根据ID查询类属性
	 * @param id
	 * @return
	 */
	public String getCiTypeAttrbyName(String typeName) {
		Node nt=new Node();
		try {
			if(null == typeName||"".equals(typeName))
			{
				typeName = "RelationCi";
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
                    Map cellMap = new HashMap();  
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
	public String addCiTypeAttr(String typeName,String name,String label,String dataType,String attGroup,String order,String defValue,String viewMode,String isRequired,String recordChange,String updateMode) {
		String result = null;
		HashMap<String, String> message = new HashMap<String, String>();
		Node nt=new Node();
		try {
			if(null == typeName||"".equals(typeName))
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
        		newAttr.setName(name);
        		newAttr.setAttGroup(attGroup);
        		newAttr.setDataType(dataType);
        		newAttr.setDefValue(defValue);
        		newAttr.setOrder(new Integer(order));
        		newAttr.setRecordChange(new Boolean(recordChange));
        		newAttr.setRequired(new Boolean(isRequired));
        		newAttr.setUpdateMode(new Integer(updateMode));
        		newAttr.setViewMode(new Integer(viewMode));
        		newAttr.setLabel(label);
            	attributeList = nt.getAttributes();
            	attributeList.put(name, newAttr);
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
	public String delCiTypeAttr(String attrName,String typeName) {
		String result = null;
		HashMap<String, String> message = new HashMap<String, String>();
		Node nt=new Node();
		try {
			if(null == typeName||"".equals(typeName))
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
            	Iterator iterator = attributeList.keySet().iterator();    
            	while (iterator.hasNext()) {   
            	    String key = (String) iterator.next();   
            	    if (attrName.equals(key) ) {  
            	    	iterator.remove();        
            	    }   
            	 } 
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getCibyType(String typeName) {
		//Node nt=new Node();
		List<RelationNode> ciList = new ArrayList<RelationNode>();
		try {
			if(typeName.equals("null"))
			{
				typeName = "Ci";
				}
			 ciList = cmdbServiceProxy.getRelationCiByTypeName(typeName);
		} catch (CmdbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
		
            	for(RelationNode n : ciList)
            	{
            		
            		Map cellMap = new HashMap();
                    cellMap.put("id", n.getId());
                    cellMap.put("name", n.getName());
                    cellMap.put("label", n.getLabel());
                    
            		Field[] fields = n.getClass().getDeclaredFields();
            		for(Field ff:fields){
            			String methodName = ff.getName();
            			try {
            				if(!methodName.equals("isRelation") && !methodName.equals("serialVersionUID")){
            					PropertyDescriptor pd = new PropertyDescriptor(methodName,n.getClass());
    							Method getMethod = pd.getReadMethod();
    							Object obj = getMethod.invoke(n);
    							cellMap.put(methodName, obj);
    							logger.debug(obj+"");
            				}
							
						} catch (IntrospectionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            		 
            		}
            		
                    
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
	 * 添加关系
	 * @param id
	 * @return
	 */
	public String addRelationCi(String typeName,String name,String label,String sourceNodeId,String sourceNode,String sourceType,String targetNodeId,String targetNode,String targetType) {
		HashMap<String, String> message = new HashMap<String, String>();
		try {
			if(null == typeName||"".equals(typeName))
			{
				typeName = "Ci";
				}
			RelationNode nt = new RelationNode();
			nt = cmdbServiceProxy.getRelationTypeByName(typeName);
			nt.setId(idg.generate());
			nt.setRelation(true);
			nt.setIsType(false);
			nt.setSourceId(new Long(sourceNodeId));
			nt.setSourceName(sourceNode);
			nt.setSourceType(sourceType);
			nt.setTargetId(new Long(targetNodeId));
			nt.setTargetName(targetNode);
			nt.setTargetType(targetType);
			nt.setName(name);
			nt.setDerivedFrom(typeName);
			cmdbServiceProxy.insertRelation(nt);
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
	
}
