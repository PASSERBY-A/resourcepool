package com.hp.avmon.deploy.store;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmonserver.entity.Agent;

public class AgentStore {
    
    @Autowired
    private static JdbcTemplate jdbcTemplate=null;
    
    public static synchronized Agent getAgent(String agentId){
        
        Agent agent=null;
        
        if(jdbcTemplate==null){
            jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");

        }

        String sql="select * from TD_AVMON_AGENT where agent_id='"+agentId+"'";
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
        if(list.size()>0){
            Map map=list.get(0);
            agent=new Agent();
            agent.setAgentId(agentId);
            agent.setIp((String) map.get("IP"));
            agent.setOs((String) map.get("OS"));
            //agent.setAgentStatus((String) map.get(""));
            
        }
        
        return agent;
    }
}
