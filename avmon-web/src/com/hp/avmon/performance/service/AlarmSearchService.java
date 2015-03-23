package com.hp.avmon.performance.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.ireport.util.StringUtil;
import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmon.utils.Utils;

@Service
public class AlarmSearchService {
    
    private static final Log logger = LogFactory.getLog(AlarmSearchService.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    /**
     * 取得活动告警
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getAlarmList(HttpServletRequest request) throws Exception {

    	String treeId = request.getParameter("treeId");
    	// add by mark start 2013-9-14
    	//this is about the VCenter's moId (是由  obj_id ||'&'||amp_inst_id || '&'||agent_id)
    	if (treeId.contains("&")) {
    		String[] params = treeId.split("\\&");
    		String objId = params[0];
    		String ampInstId = params[1];
    		String agentId = params[2];
			String moId = this.getVmMoId(objId,ampInstId,agentId);
			if (null!= moId) {
				treeId = moId;
			}
			logger.info("moId is>>>>>>>>>>>>>>> "+moId);
		}
    	// add by mark end 2013-9-14

        String limit = request.getParameter("limit");
        String start = request.getParameter("start");
        String isMyalarm = request.getParameter("isMyalarm");
        String isNewalarm = request.getParameter("isNewalarm");
        String level = request.getParameter("level");
        
        String tempSql = SqlManager.getSql(AlarmSearchService.class, "getAlarmSearchList");
        StringBuilder sb = new StringBuilder();
        sb.append(" and t1.MO_ID='" + treeId + "'");
        // 我的告警
        if ("1".equals(isMyalarm)) {
            String userId = Utils.getCurrentUserId(request);
            sb.append(" and t8.USER_ID='" + userId + "'");
        }
        // 新告警
        if ("1".equals(isNewalarm)) {
        	sb.append(" and t1.STATUS  ='0'");
        }
        // 级别
        if (!StringUtil.isEmpty(level)) {
            List<Integer> levelList=new ArrayList<Integer>();
            if(level != null){
                for(String s: level.split(",")){
                    if(s.trim().length() > 0){
                    	levelList.add(Integer.valueOf(s));
                    }
                }
            }
            Integer []levels = new Integer[levelList.size()];
            for(int i=0;i < levelList.size();i++){
            	levels[i] = levelList.get(i);
            }
            
            String g="";
            if(levels != null){
                for(Integer n : levels){
                    if(n != null){
                        g += "," + n;
                    }
                }
            }
            if(g.length() > 0){
            	sb.append(" and t1.CURRENT_GRADE in (" + g.substring(1) + ") ");
            }
        }
        
        String sql = String.format(tempSql, sb.toString());
        String querySql = MyFunc.generatPageSql(sql, limit, start);
        
        //logger.debug("AlarmSearchService getAlarmList sql: " + querySql);
		List<Map<String,Object>> list = jdbcTemplate.queryForList(querySql);
        
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("alarmTotal", list.size());
        map.put("alarmData", list);
        return map;
    }

    /**
     * 根据参数查询 Vm主机的moId
     * @param objId
     * @param ampInstId
     * @param agentId
     * @return
     */
	private String getVmMoId(String objId, String ampInstId, String agentId) {
		String moId = null;
		
		String sql = SqlManager.getSql(AlarmSearchService.class, "getVmMoId");
		sql = String.format(sql, agentId,objId,agentId,ampInstId);
		List<Map<String,String>> list = jdbcTemplate.queryForList(sql);

		if(list.size() > 0){
			moId = list.get(0).get("moId");
		}
		return moId;
	}
}
