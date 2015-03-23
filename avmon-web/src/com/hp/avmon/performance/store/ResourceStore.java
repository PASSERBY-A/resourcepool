package com.hp.avmon.performance.store;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.common.SpringContextHolder;

public class ResourceStore {
    private static final Log logger = LogFactory.getLog(ResourceStore.class);
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    
    @Autowired
    private static JdbcTemplate jdbcTemplate=null;
    
    private static Map<String,String> propCache=new ConcurrentHashMap();
    
    
    public static synchronized String getMoPropValue(String moId,String ciId){
        if(jdbcTemplate==null){
            jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");

        }
        String key=moId+"."+ciId;
        String result=propCache.get(key);
        if(result==null){
            String sql="select * from TD_AVMON_MO_INFO_ATTRIBUTE where mo_id='"+moId+"'";
            List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
            for(Map<String,Object> m:list){
                propCache.put(m.get("MO_ID")+"."+m.get("NAME"), (String) m.get("VALUE"));
            }
            //
            sql="select * from TD_AVMON_MO_INFO where mo_id='"+moId+"'";
            list=jdbcTemplate.queryForList(sql);
            if(list!=null && list.size()>0){
                Map<String,Object> m=list.get(0);
                propCache.put(moId+".typeId", (String) m.get("TYPE_ID"));
                propCache.put(moId+".caption", (String) m.get("CAPTION"));
                propCache.put(moId+".parentId", (String) m.get("PARENT_ID"));
                propCache.put(moId+".agentId", (String) m.get("AGENT_ID"));
            }
        }
        result=propCache.get(key);
        if(result==null){
            return "未知";
        }
        return propCache.get(key);
    }
}
