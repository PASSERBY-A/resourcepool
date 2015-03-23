package com.hp.avmon.performance.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.utils.DBUtils;
import com.hp.avmon.utils.SqlManager;

@Service
public class PingStatusService {
    private static final Log logger = LogFactory.getLog(VirtualMachineService.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 
     * @return
     */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getHostPing(HttpServletRequest request) {
        
		String limit = request.getParameter("limit");
        String start = request.getParameter("start");
        String where = " ";
        String orderBy = "";
        String sortBy = request.getParameter("sort");
        String ip = request.getParameter("ip")==null?"":request.getParameter("ip");
        // add by mark start 2014-6-3
        String os = request.getParameter("os")==null||"ALL".equals(request.getParameter("os"))?"":request.getParameter("os");
        String status = request.getParameter("status")==null||"ALL".equals(request.getParameter("status"))?"":request.getParameter("status");

        // add by mark start 2014-6-3
        String sql = SqlManager.getSql(this, "getHostPing");
        String countSql = SqlManager.getSql(this, "getHostPingCount");
        
        if(ip.length() > 0){
        	where += " and  b.ipvalue like '%"+ip+"%'";
        	countSql += " and  b.ipvalue like '%"+ip+"%'";
        }
        
        if(os.length() > 0){
        	where += " and  a.os = '"+os+"'";
        	countSql += " and  a.os = '"+os+"%'";
        }
        
        if(status.length() > 0){
        	if("UNKNOWN".equals(status)){
        		where += " and  d.status is null ";
	        	countSql += " and  d.status is null ";
        	}else{
        		where += " and  d.status =  '"+status+"' ";
	        	countSql += " and  d.status =  '"+status+"' ";
        	}
        }
        
        if(null != sortBy ){
        	JSONArray jsonArr = JSONArray.fromObject(sortBy);
			String sort = jsonArr.getJSONObject(0).get("property").toString();
			if("ip".equalsIgnoreCase(sort)){
				sort = "b.ipvalue";
			}else if("agentId".equalsIgnoreCase(sort)){
				sort = "info.mo_id";
			}else if("os".equalsIgnoreCase(sort)){
				sort = "a.os";
			}else if("pingTime".equalsIgnoreCase(sort)){
				sort = "d.kpitime";
			}else if("pingStatus".equalsIgnoreCase(sort)){
				sort = "d.status";
			}else if("realip".equalsIgnoreCase(sort)){
				sort = "c.realipvalue";
			}
			orderBy = " order by " + sort + " " + jsonArr.getJSONObject(0).get("direction");
        }else{
        	orderBy = " order by  b.ipvalue ";
        }
        
        String pingSql =  DBUtils.pagination(sql+where+orderBy, Integer.parseInt(start), Integer.parseInt(limit));
        
        List<Map<String,String>> list  = new ArrayList<Map<String,String>>();
		int pingCount = jdbcTemplate.queryForInt(countSql);
		try{
			list = jdbcTemplate.queryForList(pingSql);
		}catch(Exception e){
		    logger.error(pingSql);
		    e.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();			
		map.put("total", pingCount);
		map.put("root", list);
        return map;
	}
    
	
	@SuppressWarnings("finally")
	public Map<String, Boolean> savePinghost(String moId, String ip, String pingtime) {
		Map<String,Boolean> result = new HashMap<String, Boolean>();
		 
		String updateSql = String
				.format("update TD_AVMON_MO_INFO_ATTRIBUTE set value='%s' where name = 'realip' and  mo_id='%s' ",
						ip, moId);
		String insertSql = String.format("insert into TD_AVMON_MO_INFO_ATTRIBUTE(mo_id,name,value) values('%s','realip','%s')",moId,ip);
		
		String cntSql = String.format("select count(*) from TD_AVMON_MO_INFO_ATTRIBUTE where name = 'realip' and  mo_id='%s'", moId);
		int cnt = jdbcTemplate.queryForInt(cntSql);
		try {
			if(cnt > 0){
				jdbcTemplate.execute(updateSql);
				result.put("result", true);
			}else{
				jdbcTemplate.execute(insertSql);
				result.put("result", true);
			}
		
		} catch (Exception e) {
			result.put("result", true);
		}finally{
			return result;
		}
	}
}
