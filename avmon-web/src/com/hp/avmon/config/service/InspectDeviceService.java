package com.hp.avmon.config.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.utils.DBUtils;
import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.utils.SqlManager;

@Service
public class InspectDeviceService {
    
	private static final Log logger = LogFactory.getLog(InspectDeviceService.class);
    
    private JdbcTemplate jdbc;
    
    public InspectDeviceService(){
    	if (jdbc == null) {
            jdbc = SpringContextHolder.getBean("jdbcTemplate");
    	}
    }
    
    /**
     * 取得巡检设备信息列表
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getRIDeviceList(
            HttpServletRequest request) throws Exception {
    	
        String limit = request.getParameter("limit");
        String start = request.getParameter("start");
    	String sql = "select * from TF_AVMON_ROUTE_INSPECT_DEVICE ";
    	
        String querySql = MyFunc.generatPageSql(sql, limit, start);		
    	logger.debug("getRIDeviceList sql:"+querySql);
    	
		List<Map> list = jdbc.queryForList(querySql);
        
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("RIDeviceTotal", list.size());
        map.put("RIDeviceData", list);
        
        return map;
    }
    
    /**
     * 取得巡检设备信息列表
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getRIDeviceCommList(
            HttpServletRequest request) throws Exception {
    	
        String limit = request.getParameter("limit");
        String start = request.getParameter("start");
        String deviceIp = request.getParameter("DEVICE_IP");
        StringBuilder sb = new StringBuilder();
        if(DBUtils.isOracle())
        		sb.append(" select ROWNUM AS \"TEMP_ID\", DEVICE_IP, COMM_CODE, COMM_VALUE, USR, PWD, QUIT_MODE1, QUIT_MODE2, IS_ACTIVE from TF_AVMON_RI_DEVICE_COMM_MAP where DEVICE_IP = ");
        if(DBUtils.isPostgreSql())
      		sb.append(" select row_number() over() AS \"TEMP_ID\", DEVICE_IP, COMM_CODE, COMM_VALUE, USR, PWD, QUIT_MODE1, QUIT_MODE2, IS_ACTIVE from TF_AVMON_RI_DEVICE_COMM_MAP where DEVICE_IP = ");
    	sb.append("'");
    	sb.append(deviceIp);
    	sb.append("'");
//    	String sql = "select * from TF_AVMON_RI_DEVICE_COMM_MAP ";
    	
        String querySql = MyFunc.generatPageSql(sb.toString(), limit, start);		
    	logger.debug("getRIDeviceList sql:"+querySql);
    	
		List<Map> list = jdbc.queryForList(querySql);
        
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("RIDeviceCTotal", list.size());
        map.put("RIDeviceCData", list);
        
        return map;
    }
    
    /**
     * 取得设备类型
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getDeviceTypeData(
            HttpServletRequest request) throws Exception {
    	
    	String sql = "select distinct DEVICE_TYPE_CODE, DEVICE_TYPE_VALUE from TF_AVMON_RI_CODE_CATEGORY ";
		List<Map> list = jdbc.queryForList(sql);
		sql = null;
		
        return list;
    }

    /**
     * 取得设备类型
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getInspectCommandData(
            HttpServletRequest request) throws Exception {
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append(" select distinct COMM_CODE, COMM_VALUE from TF_AVMON_RI_CODE_CATEGORY where DEVICE_TYPE_CODE = ");
    	sb.append("'");
    	String deviceTypeCode = request.getParameter("DEVICE_TYPE_CODE");
    	sb.append(deviceTypeCode);
    	sb.append("'");

		List<Map> list = jdbc.queryForList(sb.toString());
		sb = null;
		deviceTypeCode = null;

        return list;
    }
    
    /**
     * 插入巡检设备管理
     * 
     * @param userId
     * @param parentId
     * @return
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map insertRIDeviceAndComm(Map dataMap, List<Map<String, String>> commList) {
		String insSql = String.format("insert into TF_AVMON_ROUTE_INSPECT_DEVICE" +
				"(ID, INSPECT_TYPE, DEVICE_TYPE, DEVICE_VERSION, DEVICE_NM, DEVICE_IP, USR, PWD, DEPLOY_ENGINE, INSPECT_CMD, QUIT_MODE1, QUIT_MODE2, BACKUP1, BACKUP2, BACKUP3, BACKUP4, BACKUP5)" +
				" values('%s','%s','%s','%s', '%s', '%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')", 
				dataMap.get("ID"),
				dataMap.get("INSPECT_TYPE"),
				dataMap.get("DEVICE_TYPE"),
				dataMap.get("DEVICE_VERSION"),
				dataMap.get("DEVICE_NM"),
				dataMap.get("DEVICE_IP"),
				dataMap.get("USR"),
				dataMap.get("PWD"),
				dataMap.get("DEPLOY_ENGINE"),
				"",
				dataMap.get("QUIT_MODE1"),
				"",
				dataMap.get("BACKUP1"),
				"",
				"",
				"",
				""
				);
		logger.debug("insertRIDeviceAndComm sql:"+insSql);
		jdbc.execute(insSql);
		
		String querySql = String.format("select * from TF_AVMON_ROUTE_INSPECT_DEVICE where ID = '%s'", dataMap.get("ID"));
		List<Map> list = jdbc.queryForList(querySql);
		
		if (commList != null) {
			for(Map<String, String> temp : commList) {
				String commSql = String.format("insert into TF_AVMON_RI_DEVICE_COMM_MAP " +
						"(DEVICE_IP, COMM_CODE, COMM_VALUE, USR, PWD, QUIT_MODE1, QUIT_MODE2, IS_ACTIVE)" +
						" values('%s','%s','%s','%s', '%s', '%s','%s','%s')", 
						temp.get("COMM_DEVICE_IP"),
						temp.get("COMM_COMM_CODE"),
						"",
						temp.get("COMM_USR"),
						temp.get("COMM_PWD"),
						temp.get("COMM_QUIT_MODE1"),
						temp.get("COMM_QUIT_MODE2"),
						""
						);
				logger.debug("insertRIDeviceAndComm Detail sql:"+commSql);
				jdbc.execute(commSql);
			}
		}
		
		Map result = new HashMap();
		if (list != null && list.size() != 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}

		return result;
	}
    
    /**
     * 插入巡检设备管理
     * 
     * @param userId
     * @param parentId
     * @return
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map updateRIDeviceAndComm(Map dataMap, List<Map<String, String>> commList) {
		// 删除原数据
		String delSql = String.format("delete from TF_AVMON_ROUTE_INSPECT_DEVICE where  ID = '%s'", dataMap.get("ID"));
		jdbc.execute(delSql);
		delSql = String.format("delete from TF_AVMON_RI_DEVICE_COMM_MAP where  DEVICE_IP = '%s'", dataMap.get("DEVICE_IP"));
		jdbc.execute(delSql);
		delSql = null;
		
		String insSql = String.format("insert into TF_AVMON_ROUTE_INSPECT_DEVICE" +
				"(ID, INSPECT_TYPE, DEVICE_TYPE, DEVICE_VERSION, DEVICE_NM, DEVICE_IP, USR, PWD, DEPLOY_ENGINE, INSPECT_CMD, QUIT_MODE1, QUIT_MODE2, BACKUP1, BACKUP2, BACKUP3, BACKUP4, BACKUP5)" +
				" values('%s','%s','%s','%s', '%s', '%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')", 
				dataMap.get("ID"),
				dataMap.get("INSPECT_TYPE"),
				dataMap.get("DEVICE_TYPE"),
				dataMap.get("DEVICE_VERSION"),
				dataMap.get("DEVICE_NM"),
				dataMap.get("DEVICE_IP"),
				dataMap.get("USR"),
				dataMap.get("PWD"),
				dataMap.get("DEPLOY_ENGINE"),
				"",
				dataMap.get("QUIT_MODE1"),
				"",
				dataMap.get("BACKUP1"),
				"",
				"",
				"",
				""
				);
		logger.debug("updateRIDeviceAndComm sql:"+insSql);
		jdbc.execute(insSql);
		insSql = null;
		
		String querySql = String.format("select * from TF_AVMON_ROUTE_INSPECT_DEVICE where ID = '%s'", dataMap.get("ID"));
		List<Map> list = jdbc.queryForList(querySql);
		querySql = null;
		
		if (commList != null) {
			for(Map<String, String> temp : commList) {
				String commSql = String.format("insert into TF_AVMON_RI_DEVICE_COMM_MAP " +
						"(DEVICE_IP, COMM_CODE, COMM_VALUE, USR, PWD, QUIT_MODE1, QUIT_MODE2, IS_ACTIVE)" +
						" values('%s','%s','%s','%s', '%s', '%s','%s','%s')", 
						temp.get("COMM_DEVICE_IP"),
						temp.get("COMM_COMM_CODE"),
						"",
						temp.get("COMM_USR"),
						temp.get("COMM_PWD"),
						temp.get("COMM_QUIT_MODE1"),
						temp.get("COMM_QUIT_MODE2"),
						""
						);
				logger.debug("updateRIDeviceAndComm Detail sql:"+commSql);
				jdbc.execute(commSql);
				commSql = null;
			}
		}
		
		Map result = new HashMap();
		if (list != null && list.size() != 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}

		return result;
	}
	
    /**
     * 删除
     * 
     * @param userId
     * @param parentId
     * @return
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map deleteRIDeviceAndComm(String id) {
		String querySql = String.format("select * from TF_AVMON_ROUTE_INSPECT_DEVICE where  ID = '%s'", id);
		List<Map> list = jdbc.queryForList(querySql);
		if (list != null && list.size() != 0) {
			for (Map temp : list) {
				String delCommSql = String.format("delete from TF_AVMON_RI_DEVICE_COMM_MAP where  DEVICE_IP = '%s'", temp.get("DEVICE_IP"));
				jdbc.execute(delCommSql);
				delCommSql = null;
			}
		}
		
		String delSql = String.format("delete from TF_AVMON_ROUTE_INSPECT_DEVICE where  ID = '%s'", id);
		logger.debug("delete TF_AVMON_ROUTE_INSPECT_DEVICE:" + delSql);
		jdbc.execute(delSql);
		
		List<Map> resultlist = jdbc.queryForList(querySql);
		Map result = new HashMap();
		if (resultlist == null || resultlist.size() == 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}

		querySql = null;
		delSql = null;
		list = null;
		resultlist = null;
		return result;
	}
	
    /**
     * 取得巡检设备导出数据
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getRIDCommOutputData() {
    	
    	String tempSql = SqlManager.getSql(InspectDeviceService.class, "getRIDCommOutputData");
    	
    	logger.debug("getRIDCommOutputData sql:" + tempSql);
    	
		List<Map> list = jdbc.queryForList(tempSql);
		tempSql = null;
		
        return list;
    }
}
