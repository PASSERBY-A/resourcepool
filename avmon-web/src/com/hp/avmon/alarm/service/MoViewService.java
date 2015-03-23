package com.hp.avmon.alarm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.utils.MyFunc;
import com.hp.avmonserver.entity.Alarm;

@Service
public class MoViewService {
    
    public static final String MO_ID="MO_ID";
    public static final String MO_CAPTION="CAPTION";
    public static final String MO_PARENT_ID="PARENT_ID";
    public static final String MO_TYPE_ID="TYPE_ID";
    
    
    private static volatile Map<String,List> moChild=null;
    private static volatile Map<String,Map> moList=null;
    private static volatile Map<String,String> attributeReference=null;
    private static final Log logger = LogFactory.getLog(MoViewService.class);

    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public MoViewService(){
        jdbcTemplate=SpringContextHolder.getBean("jdbcTemplate");
        reloadMoChild();
    }
    
    
    public void reloadMoChild(){
        moChild=new HashMap<String,List>();
        moList=new HashMap<String,Map>();
        String sql="select i.mo_id,(case when i.type_id like 'HOST_%' THEN a.value || '(' || i.caption || ')' ELSE i.caption  END) as caption,case when i.parent_id is null or i.parent_id='' then '0' else i.parent_id end as parent_id,i.type_id from TD_AVMON_MO_INFO i LEFT JOIN TD_AVMON_MO_INFO_ATTRIBUTE a ON i.mo_id = a.mo_id and a.name = 'hostName'";
        List<Map> list=jdbcTemplate.queryForList(sql);
        for(Map map:list){
            List l=new ArrayList();
            _reloadMoChild(l,(String)map.get("mo_id"),list);
            moChild.put((String)map.get("mo_id"), l);
            moList.put((String)map.get("mo_id"), map);
            //System.out.println("add mo list "+map.get("mo_id")+" "+map);
        }
        reloadMoAttr();
    }
    
    private void reloadMoAttr() {
        //加载mo扩展属性
        String sql=String.format("select mo_id,name,value from TD_AVMON_MO_INFO_ATTRIBUTE");
        List<Map> list=jdbcTemplate.queryForList(sql);
        for(Map map:list){
            String moId=(String) map.get("MO_ID");
            String attrName=(String) map.get("NAME");
            String attrValue=(String) map.get("VALUE");
            Map mo=moList.get(moId);
            if(mo!=null){
                Map attr=null;
                attr=(Map) mo.get("attr");
                if(attr==null){
                    attr=new HashMap();
                }
                attr.put(attrName, attrValue);
                mo.put("attr", attr);
            }
        }
        //logger.info("{} Monitor Object and {} Attributes loaded!",moList.size(),list.size());
        
        attributeReference=new HashMap<String,String>();
        sql=String.format("select type_id,name,caption from TD_AVMON_MO_TYPE_ATTRIBUTE");
        list=jdbcTemplate.queryForList(sql);
        for(Map map:list){
            attributeReference.put((String)map.get("TYPE_ID")+"-"+(String)map.get("CAPTION"),(String)map.get("NAME"));
        }
    }

    private void _reloadMoChild(List targetList,String moId,List<Map> list) {
        for(Map m:list){
            if(m.get("PARENT_ID").equals(moId) && !m.get("mo_id").equals(moId)){
                targetList.add(m.get("mo_id"));
                _reloadMoChild(targetList,(String)m.get("mo_id"),list);
            }
        }
    }

    public List getMoByFilter(String filter,String filterRule){
        @SuppressWarnings("rawtypes")
        List list=new LinkedList<Map>();
        
        Iterator it=moList.keySet().iterator();
        while(it.hasNext()){
            String key=(String)it.next();
            Map map=moList.get(key);
            if(matchFilter(map,filter,filterRule)){
                list.add(map);
            }
        }
        
        return list;
    }
    
    private boolean matchFilter(Map map, String filter, String filterRule) {
        //[{"FILTER_NO":"1","FILTER_FIELD":"主机类型","FILTER_OPERATOR":"=","FILTER_VALUE":"'HOST'"}]
        List<Map> list=JackJson.fromJsonToObject(filter, List.class);
        if(list.size()==0) return true;
        String rule=filterRule;
        for(int i=list.size()-1;i>=0;i--){
            Map m=list.get(i);
            String fieldNo=(String)m.get("FILTER_NO");
            String field=(String) m.get("FILTER_FIELD");
            String optr=(String) m.get("FILTER_OPERATOR");
            String filterValue=(String) m.get("FILTER_VALUE");
            if(field.equals("MO_TYPE")){
                field="TYPE_ID";
            }else if(field.equals("MO_NAME")){
                field="CAPTION";
            }
            String value=(String)map.get(field);
            if(value==null){
                if(map.get("attr")!=null){
                    value=(String) ((Map)(map.get("attr"))).get(field);
                    if(value==null){
                        field=attributeReference.get(field);
                        if(field!=null){
                            value=(String) ((Map)(map.get("attr"))).get(field);
                        }
                    }
                }
            }
            //System.out.format("MATCH %s %s %s\n",field,optr,value);
            if(value==null){
                return false;
            }
            if(matchCondition(value,optr,filterValue)){
                m.put("result", "'a'='a'");
                rule=rule.replaceAll(fieldNo, "'a'='a'");
            }
            else{
                m.put("result", "'a'='b'");
                rule=rule.replaceAll(fieldNo, "'a'='b'");
            }
        }
        String sql=String.format("select count(1) from dual where %s",rule);

        int n=jdbcTemplate.queryForInt(sql);
        return n==1;
    }


    private boolean matchCondition(String value, String optr, String filterValue) {
        //System.out.format("   %s %s %s\n",value,optr,filterValue);
        if(value==null) return false;
        if(optr.equals("=")){
            return value.equals(filterValue);
        }
        else if(optr.equals("like")){
            return value.indexOf(filterValue)>=0;
        }
        else if(optr.equals("not like")){
            return ! (value.indexOf(filterValue)>=0);
        }
        else if(optr.equals("!=")){
            return ! value.equals(filterValue);
        }
        else if(optr.equals("in")){
            return filterValue.indexOf(value)>=0;
        }
        else if(optr.equals("not in")){
            return ! (filterValue.indexOf(value)>=0);
        }
        // add by mark start 2014-1-15 添加is null功能
        else if(optr.equals("is")&&("null".equalsIgnoreCase(filterValue)||filterValue==null)){
            return  value==null||"null".equalsIgnoreCase(value);
        }
        // add by mark end 2014-1-15
        return false;
    }


    public List getAllMoChildrenByParent(String moId){
        List list=moChild.get(moId);
        if(list==null) list=new ArrayList();
        return list;
    }
    
    public Map getMoById(String moId){
        return moList.get(moId);
    }
    
    private List<Map<String, Object>> generateTree(String parentId,List<Map<String, Object>> list){
        
        if(parentId==null) return null;
        
        List<Map<String, Object>> ret=new ArrayList<Map<String, Object>>();
        
        for(Map<String, Object> map:list){
            String pid=(String)map.get("parent_id");
            if(pid==null)pid="";
            if(pid.equals(parentId)){
                Map<String, Object> m=new HashMap<String,Object>();
                m.put("id",map.get("mo_id"));
                m.put("text", map.get("caption"));
                m.put("type",map.get("type_id"));
                if(map.get("type_id")!=null && map.get("type_id").equals("DIR")){
                    m.put("isexpand", "true");
                   // m.put("icon", "resources/images/alarm/folder.gif");
                }
                else{
                    //m.put("icon", "resources/images/alarm/tree-leaf.gif");
                    String attrStr=(String)map.get("attribute");
                    if(attrStr!=null && attrStr.length()>0){
                        HashMap<String, Object> attr=(HashMap<String, Object>) JackJson.fromJsonToObject(attrStr,new TypeReference<Map<String, Object>>(){});
                        m.put("attribute",attr);
                    }
                }
                List<Map<String, Object>> children=generateTree((String) map.get("mo_id"),list);
                if(children.size()>0){
                    m.put("children", children);
                }
                
                ret.add(m);
            }
        }
        return ret;
    }
    
    public String getAlarmMonTreeJson(){
        String sql = "select mo_id as \"id\",type_id ,'resources/images/montype/'||type_id||'.png' as \"icon\",caption as \"text\",attribute,parent_id as \"pid\",is_dir from TD_AVMON_MO_INFO order by mo_id";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        String json=JackJson.fromObjectToJson(list);
        //System.out.println(json);
        return json;
    }
    public String getPerformanceMonTreeJson(){
        String sql = "select mo_id,type_id,caption,attribute,parent_id,is_dir from TD_AVMON_MO_INFO where show_in_performance_view=1 order by mo_id";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

        List<Map<String, Object>> tree=generateTree("",list);
        String json=JackJson.fromObjectToJson(tree);
        //System.out.println(json);
        return json;
    }
    
    public String getMonTreeJson(){
        String sql = "select mo_id as \"id\",type_id ,'resources/images/montype/'||type_id||'.png' as \"icon\",caption as \"text\",attribute,parent_id as \"pid\",is_dir from TD_AVMON_MO_INFO order by mo_id";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        String json=JackJson.fromObjectToJson(list);
        //System.out.println(json);
        return json;
    }
    
    public boolean addMo(String moId,String moType, String caption, String parentId) {

        String sql=String.format("INSERT INTO TD_AVMON_MO_INFO ( mo_id, TYPE_ID, CAPTION, ATTRIBUTE, PARENT_ID" +
                ", SHOW_IN_ALARM_VIEW, SHOW_IN_PERFORMANCE_VIEW) " +
                " VALUES ('%s' ,'%s' ,'%s' ,'' ,'%s' ,1 , 1)", moId,moType.toUpperCase(),caption,parentId);
        //System.out.println(sql);
        return jdbcTemplate.update(sql)>0;
    }

    public boolean removeMo(String moId) throws Exception {
//        String sql=String.format("select 1 from TD_AVMON_MO_INFO where parent_id='%s'", moId);
//        List list=jdbcTemplate.queryForList(sql);
//        if(list.size()>0){
//            throw new Exception("该监控对象下面有子对象，无法删除！");
//        }
        //System.out.println(sql);
        String sql=String.format("delete from TD_AVMON_MO_INFO_ATTRIBUTE where mo_id='%s'", moId);
        jdbcTemplate.update(sql);
        sql=String.format("delete from TD_AVMON_MO_INFO where mo_id='%s'", moId);
        return jdbcTemplate.update(sql)>0;
    }

    public Map getBasicInfo(String mo) {
        
        if(mo==null || mo.equals("")) return null;
        
        String sql=String.format("select a.*,b.CAPTION as type_name from TD_AVMON_MO_INFO a left join TD_AVMON_MO_TYPE b on a.type_id=b.type_id where a.mo_id='%s'",mo);
        List<Map> list=jdbcTemplate.queryForList(sql);
        if(list.size()>0) return list.get(0);
        else return null;
    }

    public boolean saveMoData(String moId, String moType, String caption, String description, Map extInfo) {
        description=description.replaceAll("'", "''");
        String sql=String.format("update TD_AVMON_MO_INFO set type_id='%s',caption='%s',description='%s' where mo_id='%s'",moType,caption,description,moId);
        jdbcTemplate.update(sql);
        
        Iterator it=extInfo.keySet().iterator();
        while(it.hasNext()){
            String key=(String) it.next();
            String []v=(String[])extInfo.get(key);
            if(key.startsWith("attr_") && v!=null && v.length>0){
                String name=key.substring(5);
                //System.out.format("save ext-attr %s.%s=%s\n",moId,name,v[0]);
                sql=String.format("update TD_AVMON_MO_INFO_ATTRIBUTE set value='%s' where mo_id='%s' and name='%s'",v[0],moId,name);
                //System.out.println(sql);
                if(jdbcTemplate.update(sql)==0){
                    sql=String.format("insert into TD_AVMON_MO_INFO_ATTRIBUTE(mo_id,name,value) values('%s','%s','%s')",moId,name,v[0]);
                    //System.out.println(sql);
                    jdbcTemplate.update(sql);
                }
            }
            
        }
        return true;
    }

    private void updateMoStatus(String moId,String lastUpdateTime){
        //System.out.format("update mo %s lastupdatetime %s ...\n",moId,lastUpdateTime);
        Map map=moList.get(moId);
        if(map!=null){
            //System.out.format("update mo %s lastupdatetime %s done. old=%s\n",moId,lastUpdateTime,map.get("lastUpdateTime"));
            map.put("lastUpdateTime", lastUpdateTime);
            moList.put(moId, map);
        }
    }
    
    public void onAlarm(Alarm alarm) {
        
        //update each mo status
        String moId=alarm.getMoId();
        String lastUpdateTime=MyFunc.generateTimeId();
        updateMoStatus(moId,lastUpdateTime);
        Iterator it=moChild.keySet().iterator();
        while(it.hasNext()){
            String key=(String) it.next();
            List list=moChild.get(key);
            if(list.indexOf(moId)>=0){
                updateMoStatus(key,lastUpdateTime);
            }
        }
    }



    
     public List<Map> getExtInfo(String mo) {
        
        List<Map> resultMap = new ArrayList<Map>();
        // 取得数据
        //List<TreeInfo> monitorObjDataList = getMonitorObjVo();
        HashMap<String, String> moIdMap = new LinkedHashMap<String, String>();
        // 构造监控对象树形结构
        //structureChildNode(monitorObjDataList, moIdMap, mo);


		@SuppressWarnings("rawtypes")
		Iterator iter = moIdMap.entrySet().iterator();
		while (iter.hasNext()) {
		    @SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) iter.next();
		    Object key = entry.getKey();
//		    Object val = entry.getValue();
		    
//	        String sql=String.format("SELECT a.name,a.caption,b.value from TD_AVMON_MO_TYPE_ATTRIBUTE a " +
//	        		"left join TD_AVMON_MO_INFO_ATTRIBUTE b on (a.name=b.name and b.mo_id='%s')" +
//	        		",TD_AVMON_MO_INFO c where a.metadata_id=c.type_id and c.mo_id='%s' order by a.order_index", key, key);
		    
	        String sql=String.format("SELECT " +
//	        "distinct " +
	        "T2.name, T2.caption, T3.value " + 
	        "from " + 
	        "TD_AVMON_MO_INFO T1 " + 
	        "left join TD_AVMON_MO_TYPE_ATTRIBUTE T2 on (T1.type_id = T2.type_id)  " + 
	        "left join TD_AVMON_MO_INFO_ATTRIBUTE T3 on (T2.name=T3.name and T1.mo_id = T3.mo_id) " + 
	        "where T3.mo_id='%s' order by T2.order_index", key);
		
	        //System.out.println(sql);
	        
	        List<Map> querylist = jdbcTemplate.queryForList(sql);
	        for (Map tmp : querylist) {
	        	resultMap.add(tmp);
	        }
		}
		
        return resultMap;
    }
    
   
    public List<Map> getMonitorList(String mo) {
        if(mo==null || mo.equals("")) return null;
        
        String sql=String.format("select * from TD_AVMON_MONITOR_INSTANCE where target_id='%s'",mo);
        List<Map> list=jdbcTemplate.queryForList(sql);
        
        return list;

    }


}
