package com.hp.avmon.performance.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.ireport.util.StringUtil;
import com.hp.avmon.utils.SqlManager;

@Service
public class DeviceStatusService {
    
    private static final Log logger = LogFactory.getLog(DeviceStatusService.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
	/**
	 * 取得主机状态列表信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    public List<Map> getMainEngineList(String moId, String condition, String type,String others){
    	// 判断页面选择过滤条件
    	if (!StringUtil.isEmpty(condition)) {
        	String sql = null;
        	if ("1".equals(condition)) {
        		// 半小时内严重告警
            	String tempSql = SqlManager.getSql(DeviceStatusService.class, "getMainEngine_Half_Warn");
            	
            	if (!StringUtil.isEmpty(type) && !"null".equals(type)) {
            		tempSql += " where a.id in  " +
            				"(select  a.mo_id from TD_AVMON_MO_INFO a left join TD_AVMON_MO_INFO_ATTRIBUTE b on (a.mo_id=b.mo_id and b.name='businessSystem' and b.value is not null) where (case when b.value is null then '"+others+"' else b.value end) = '%s' and a.type_id like 'HOST%%') " +
            				"order by a.id " ;
            		// 从总体监控视图跳转的场合
            		sql = String.format(tempSql, type);
            	} else {
            		// 从树节点跳转的场合
            		tempSql += " where a.pid='%s' order by a.id " ;
            		sql = String.format(tempSql, moId);
            	}
        	} else if ("2".equals(condition)) {
        		// 1小时内严重告警
        		String tempSql = SqlManager.getSql(DeviceStatusService.class, "getMainEngine_One_Warn");
        		
        		if (!StringUtil.isEmpty(type) && !"null".equals(type)) {
            		tempSql += " where a.id in  " +
            				"(select a.mo_id from TD_AVMON_MO_INFO a left join TD_AVMON_MO_INFO_ATTRIBUTE b on (a.mo_id=b.mo_id and b.name='businessSystem' and b.value is not null) where (case when b.value is null then '"+others+"' else b.value end) = '%s' and a.type_id like 'HOST%%') " +
            				"order by a.id " ;
            		// 从总体监控视图跳转的场合
            		sql = String.format(tempSql, type);
            	} else {
            		// 从树节点跳转的场合
            		tempSql += " where a.pid='%s' order by a.id " ;
            		sql = String.format(tempSql, moId);
            	}
        	} else if ("3".equals(condition)) {
        		// 所有严重告警
        		String tempSql = SqlManager.getSql(DeviceStatusService.class, "getMainEngine_All_Warn");
        		
        		if (!StringUtil.isEmpty(type) && !"null".equals(type)) {
            		tempSql += " where a.id in  " +
            				"(select a.mo_id from TD_AVMON_MO_INFO a left join TD_AVMON_MO_INFO_ATTRIBUTE b on (a.mo_id=b.mo_id and b.name='businessSystem' and b.value is not null) where (case when b.value is null then '"+others+"' else b.value end) = '%s' and a.type_id like 'HOST%%') " +
            				"order by a.id " ;
            		// 从总体监控视图跳转的场合
            		sql = String.format(tempSql, type);
            	} else {
            		// 从树节点跳转的场合
            		tempSql += " where a.pid='%s' order by a.id " ;
            		sql = String.format(tempSql, moId);
            	}
        	} else if ("4".equals(condition)) {
        		// 所有告警
        		String tempSql = SqlManager.getSql(DeviceStatusService.class, "getMainEngine_All_Normal");
        		
        		if (!StringUtil.isEmpty(type) && !"null".equals(type)) {
            		tempSql += " where a.id in  " +
            				"(select a.mo_id from TD_AVMON_MO_INFO a left join TD_AVMON_MO_INFO_ATTRIBUTE b on (a.mo_id=b.mo_id and b.name='businessSystem' and b.value is not null) where (case when b.value is null then '"+others+"' else b.value end) = '%s' and a.type_id like 'HOST%%') " +
            				"order by a.id " ;
            		// 从总体监控视图跳转的场合
            		sql = String.format(tempSql, type);
            	} else {
            		// 从树节点跳转的场合
            		tempSql += " where a.pid='%s' order by a.id " ;
            		sql = String.format(tempSql, moId);
            	}
        	}
        	
            logger.info("getMainEngineList SQL:" + sql);
            @SuppressWarnings({ "rawtypes", "unchecked" })
			List<Map> kpiList = jdbcTemplate.queryForList(sql);
            return kpiList;
    	} else {
    		return null;
    	}
    }
}
