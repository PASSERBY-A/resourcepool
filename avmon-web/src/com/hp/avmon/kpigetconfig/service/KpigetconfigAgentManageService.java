package com.hp.avmon.kpigetconfig.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.config.bean.AmpPolicyBean;
import com.hp.avmon.deploy.service.DeployService;
import com.hp.avmon.equipmentCenter.service.EquipmentCenterService;
import com.hp.avmon.home.service.LicenseService;
import com.hp.avmon.utils.DBUtils;
import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmon.utils.TreeObject;
import com.hp.avmonserver.api.AvmonServer;
import com.hp.avmonserver.entity.MO;

/**
 * Created with IntelliJ IDEA.
 * User: shiw
 * Date: 3/13/13
 * Time: 5:24 PM
 * To change this template use File | Settings | File Templates.
 */
@Service 
public class KpigetconfigAgentManageService {

	private static final Log logger = LogFactory.getLog(KpigetconfigAgentManageService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LicenseService licenseService;
    @Autowired
    private AvmonServer avmonServer;
    @Autowired
    private DeployService deployService;
    @Autowired
    private EquipmentCenterService equipmentCenterService;
    
    /**
     * 获取Agent grid 信息
     * @param request
     * @return
     * @throws Exception
     */
    public Map findAgentGridInfo(HttpServletRequest request) throws Exception{

        Map<String, Object> map = new HashMap<String, Object>();

        //获取查询参数
        String agentId = request.getParameter("agentId")==null?"":request.getParameter("agentId");
        String ip = request.getParameter("ip")==null?"":request.getParameter("ip");
        // add by mark start
        String osArry = request.getParameter("os")==null?"":request.getParameter("os");
        if("604674602".equals(osArry)){
        	osArry="null";
        }
        //"666656"
        // add by mark start
        
        String limit = request.getParameter("limit");
        String start = request.getParameter("start");
//        String page = request.getParameter("page");
//        System.out.println("page is >>>>>>>>>>>>>>>>>>>>:"+page);
        String where = "where 1=1 ";
        if(agentId.length()>0){
        	where = where + " and ag.AGENT_ID LIKE '%"+agentId+"%' ";
        }
        if(ip.length()>0){
        	where = where + " and ag.ip LIKE '%"+ip+"%' ";
        }
        
       
        
        // add by mark start
        String businessType = null;
        String os = null;
        
        	
        if(!"null".equals(osArry) && osArry.length() > 0 ){
        	if(osArry.indexOf("*")>0){
        		os = osArry.split("\\*")[0];
            	os = deployService.getMoCaptionById(os);
            	if("Solaris".equals(os)){
            		os = "SunOS";
            	}
            	where = where + " and ag.os LIKE '%"+os+"%' ";
            	
            	if(null != osArry.split("\\*")[1] && !"666656".equals(osArry.split("\\*")[1])){
            		businessType = osArry.split("\\*")[1];
            		//businessType = AgentTypes.BIZMAP.get(businessType);
            		businessType = equipmentCenterService.getBusinessNameById(businessType);
            		where = where +" and systemtb.value LIKE '%"+businessType+"%' ";	
            	}else if("666656".equals(osArry.split("\\*")[1])){
            		where += " and not exists (select mo_id from td_avmon_mo_info_attribute where name = 'businessSystem' and  mo_id = ag.mo_id) ";
            	}
            	
        	}else if(!"666656".equals(osArry)){
        		//businessType = AgentTypes.BIZMAP.get(osArry);
        		businessType = equipmentCenterService.getBusinessNameById(osArry);
        		where = where +" and systemtb.value LIKE '%"+businessType+"%' ";
        	}else if("666656".equals(osArry)){
        		where += " and not exists (select mo_id from td_avmon_mo_info_attribute where name = 'businessSystem' and  mo_id = ag.mo_id) ";
        	}
        }
        
        // add by mark end
        String orderBy = "";
        String sortBy = request.getParameter("sort");
        if(sortBy!=null){
			JSONArray jsonArr = JSONArray.fromObject(sortBy);
			String sort = jsonArr.getJSONObject(0).get("property").toString();
			if("ampCount".equalsIgnoreCase(sort)||"ampCount1".equalsIgnoreCase(sort)){
				sort = "amp.\"ampCount\"";
			}else if("hostName".endsWith(sort)){
				sort = "host.value";
			}else if("agentId".endsWith(sort)){
				sort = "ag.AGENT_ID";
			}else if("agentVersion".endsWith(sort)){
				sort = "ag.agent_version";
			}else if("agentStatus".endsWith(sort)){
				sort = "round((sysdate - ag.last_heartbeat_time) * 24 * 60)";
				if(DBUtils.isPostgreSql())
					sort = "round((EXTRACT(EPOCH from CURRENT_TIMESTAMP) - EXTRACT(EPOCH from ag.last_heartbeat_time)) / 60)";
			}else if("lastHeartbeatTime".endsWith(sort)){
			    sort = "ag.last_heartbeat_time";
			}else if("osVersion".endsWith(sort)){
                sort = "ag.os_version";
            }else if("moId".endsWith(sort)){
                sort = "ag.mo_id";
            }else if("agentCollectFlag".endsWith(sort)){
                sort = "ag.AGENT_COLLECT_FLAG";
            }else if("status".endsWith(sort)){
                sort = "ag.agentStatus";
            }
			
			orderBy = " order by " + sort + " " + jsonArr.getJSONObject(0).get("direction");
		}
        
        //String where = " WHERE ag.AGENT_ID LIKE '%"+agentId+"%' and ag.ip LIKE '%"+ip+"%'";
        String listSql = null;
        String countSql = null;
        if(!"666656".equals(businessType)){
        	listSql = SqlManager.getSql(KpigetconfigAgentManageService.class, "getBizAgentGridInfo");
        	countSql = SqlManager.getSql(KpigetconfigAgentManageService.class, "getAgentGridInfoCount");
        	if(null != businessType){
            	//businessType = AgentTypes.BIZMAP.get(businessType);
        		businessType = equipmentCenterService.getBusinessNameById(businessType);
            	listSql = String.format(listSql, businessType);	
            }
        }else{
        	listSql = SqlManager.getSql(KpigetconfigAgentManageService.class, "getBizAgentGridInfo_other");
        	countSql = SqlManager.getSql(KpigetconfigAgentManageService.class, "getAgentGridInfoCount_other");
        }
        
        String finSql =  generatPageSql(listSql+where+orderBy,limit,start);

        
        //String countSql = SqlManager.getSql(AgentManageService.class, "getAgentGridInfoCount")+where;
		
        //查询总条数
        //logger.debug(countSql);
        int rowCount = jdbcTemplate.queryForInt(countSql+where);

        //查询总数据
        //logger.debug(finSql);
        List<Map<String,Object>> list =  jdbcTemplate.queryForList(finSql);

        map.put("activeTotal", rowCount);
        //map.put("page", start);
        map.put("root", list);
        return map;
    }

    /**
     *  查询Agent相关amp实例信息
     * @param request
     * @return
     */
    public Map findAgentAmp(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();

        //获取查询参数
        String agentId = request.getParameter("agentId");
        String limit = request.getParameter("limit");
        String start = request.getParameter("start");

        String where = String.format(" WHERE ag.AGENT_ID = '%s'",agentId);

        String listSql = SqlManager.getSql(KpigetconfigAgentManageService.class, "findAgentAmpList");
        String finSql =  generatPageSql(listSql+where,limit,start);

        String countSql = SqlManager.getSql(KpigetconfigAgentManageService.class, "findAgentAmpListCount")+where;

        //查询总条数
        //logger.debug(countSql);
        int rowCount = jdbcTemplate.queryForInt(countSql);

        //查询总数据
        //logger.debug(finSql);
        List<Map<String,Object>> list =  jdbcTemplate.queryForList(finSql);

        map.put("activeTotal", rowCount);
        map.put("root", list);
        return map;
    }

    /**
     * 获取所有可用AMP 信息
     * @return
     */
    public Map getAMPTypeInfo(){
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "SELECT AMP_ID AS \"value\",CAPTION AS \"name\",TYPE AS \"type\" FROM TD_AVMON_AMP ";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        map.put("root",list);
        return map;
    }
    
    // add by mark start
    /**
     * 获取可用AMP 信息
     * @return
     */
    public Map<String,List<Map<String,String>>> getAMPTypeInfo(String targetOs){
        Map<String, List<Map<String,String>>> map = new HashMap<String, List<Map<String,String>>>();
        String sql = null;
        
        if("SunOS".equals(targetOs)){
        	targetOs = "SOLARIS";
        }
        
        if("null".equals(targetOs)){
        	sql = "SELECT AMP_ID AS \"value\",CAPTION AS \"name\",TYPE AS \"type\" FROM TD_AVMON_AMP WHERE TARGET_OS IS NULL";
        		
        }else{
        	sql = "SELECT AMP_ID AS \"value\",CAPTION AS \"name\",TYPE AS \"type\" FROM TD_AVMON_AMP WHERE TARGET_OS LIKE '%"+targetOs.toUpperCase()+"%'";
        }
        
        List<Map<String,String>> list = jdbcTemplate.queryForList(sql);
        map.put("root",list);
        return map;
    }
    // add by mark end

    /**
     * 启动Agent采集
     * @param request00:success
		01:Agent未响应
		02:未找到目标Agent
		03:未找到下发文件
		99:未知错误
     * @return
     * @throws Exception
     */
    public String startAgent(HttpServletRequest request)throws Exception{
    	//Map<String, Object> map = new HashMap<String, Object>();
        String status = request.getParameter("status");
        String agentId = request.getParameter("agentId");
        int agentCollectFlag = 1;
        String json = "";
        String result = avmonServer.startAgent(agentId);
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        
        if(result.startsWith("00")){
        	agentCollectFlag = 0;
        	this.updateAgentStatusByAgentId(status,agentId,agentCollectFlag);
        	json="{success:true,msg:'"+bundle.getString("agentStartSuccess")+"'}";
        }else {
        	this.updateAgentStatusByAgentId(status,agentId,agentCollectFlag);
        	json="{success:false,msg:'" + result + "'}";
        }
        return json;
    }

    /**
     * 停止Agent采集
     * @param request
     * @return
     * @throws Exception
     */
    public String stopAgent(HttpServletRequest request)throws Exception{
        String status = request.getParameter("status");
        String agentId = request.getParameter("agentId");
        
        String result = avmonServer.stopAgent(agentId);
        int agentCollectFlag  = 0;
        String updateStatus = "";
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        
        if(result.startsWith("00")){
        	agentCollectFlag = 1;
        	this.updateAgentStatusByAgentId(status,agentId,agentCollectFlag);
        	updateStatus ="{success:true,msg:'" +bundle.getString("agentStopSuccess")+ "'}";
        }else {
        	this.updateAgentStatusByAgentId(status,agentId,agentCollectFlag);
        	updateStatus="{success:false,msg:'" + result + "'}";
        }
        
        return updateStatus;
    }

    /**
     * 保存AMP信息
     * @param request
     * @return
     * @throws Exception
     */
    public Map saveAgentAmp(HttpServletRequest request) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success",true);

        String ampId = request.getParameter("ampId");
        String ampInstId = request.getParameter("ampInstId");
        String ampName = request.getParameter("ampName");
        String status = request.getParameter("status");
        String agentId = request.getParameter("agentId");

        String ampType = request.getParameter("ampType");
        
        List<String> sqlList = new ArrayList<String>();
        
        //插入AMP数据到实例表
        String insertSql = String.format("insert into TD_AVMON_AMP_INST(AMP_ID,AMP_INST_ID,AMP_VERSION,ENABLE_FLAG,SCHEDULE,AGENT_ID,CAPTION,STATUS,LAST_ACTIVE_TIME)\n" +
                "    values ('%s','%s',(select version from TD_AVMON_AMP where amp_id='%s' ),\n" +
                "    '%d',(select default_schedule from TD_AVMON_AMP where amp_id='%s' ),'%s','%s','0',null)",
                ampId,ampInstId,ampId,Integer.parseInt(status),ampId,agentId,ampName);
        //jdbcTemplate.execute(insertSql);

        sqlList.add(insertSql);
        
        if(!"vm".equalsIgnoreCase(ampType)&&!"ilo".equalsIgnoreCase(ampType)){
        	//复制AMP关联KPI数据到POLICY表中 只有添加普通amp实例时需要copy对应信息
            //查找关联API数据
            String apiSql = String.format("select AMP_ID as \"ampId\" ,KPI_CODE as \"kpiCode\",SCHEDULE as \"schedule\",KPI_GROUP as \"kpiGroup\"  from TD_AVMON_AMP_KPI where AMP_ID = '%s'",ampId);
            List<Map<String,Object>> apiList = jdbcTemplate.queryForList(apiSql);

            for(Map<String,Object> kpi : apiList){
            	String insertApiPolicySql = "INSERT INTO TD_AVMON_AMP_POLICY(KPI_CODE,KPI_GROUP,SCHEDULE,AGENT_ID,AMP_INST_ID,NODE_KEY)" +
                        "VALUES('%s','%s','%s','%s','%s','NA')";
            	insertApiPolicySql = String.format(insertApiPolicySql, kpi.get("kpiCode").toString(),kpi.get("kpiGroup") != null?kpi.get("kpiGroup").toString():"",
            			kpi.get("schedule") != null?kpi.get("schedule").toString():"",agentId,ampInstId);
            	sqlList.add(insertApiPolicySql);
            }
        }
        
        //复制AMP关联ATTR数据到INST_ATTR表中
        //查找关联ATTR数据
        String attrSql = String.format("select name as \"name\" ,default_value as \"value\" from td_avmon_amp_attr where AMP_ID = '%s'",ampId);
        List<Map<String,Object>> attrList = jdbcTemplate.queryForList(attrSql);

        for(Map<String,Object> attr : attrList){
        	String insertInstAttrSql = "INSERT INTO td_avmon_amp_inst_attr(NAME,VALUE,AGENT_ID,AMP_INST_ID)" +
                    "VALUES('%s','%s','%s','%s')";
        	insertInstAttrSql = String.format(insertInstAttrSql, attr.get("name").toString(),attr.get("value") != null?attr.get("value").toString():"",
        			agentId,ampInstId);
        	sqlList.add(insertInstAttrSql);
        }
        
        String batchSql[] = sqlList.toArray(new String[sqlList.size()]);
//        jdbcTemplate.batchUpdate(batchSql);
        try {
			String returnString = transfer1(batchSql);
			if("1".equals(returnString)){
				throw new Exception(this.getClass().getName() + "saveAgentAmp transfer error!");
			}else if("ORA-00001".equals(returnString)){
				throw new Exception(this.getClass().getName() + "saveAgentAmp ORA-00001 error!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(this.getClass().getName() + " saveAgentAmp()", e);
			throw e;
//			map.put("success",false);
//			return map;
		}
        
        return map;
    }

    /**
     * 获取普通AMP基本属性数据
     * @param request
     * @return
     */
    public Map getNormalAmpAttrList(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String ampId = request.getParameter("ampId");
        String sql = String.format(SqlManager.getSql(KpigetconfigAgentManageService.class, "getNormalAmpAttrList")+" where a1.AMP_ID = '%s' order by a1.ORDER_INDEX asc",ampId);
        logger.debug("sql: "+sql);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);

        List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();
        //用于属性显示
        Map<String, Object> propertyViewMap = new HashMap<String, Object>();
        //用于属性对应空的判断值
        Map<String, Object> propertyNullMap = new HashMap<String, Object>();
        //转化为 name-value属性值对
        for(Map<String,Object> mapDetail:list){
            propertyViewMap.put(mapDetail.get("name").toString(),mapDetail.get("ampInstValue") == null?"":mapDetail.get("ampInstValue"));
            propertyNullMap.put(mapDetail.get("name").toString(),mapDetail.get("nullAble") == null?"":mapDetail.get("nullAble"));
        }
        mapList.add(propertyViewMap);
        mapList.add(propertyNullMap);
        map.put("root",mapList);
        return map;
    }

    /**
     * 保存amp数据
     * @param request
     * @return
     */
    public Map saveNormalAmpAttr(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        final String agentId = request.getParameter("agentId");
        final String ampInstId = request.getParameter("ampInstId");
        String ampAttrJson = request.getParameter("ampAttr");
        Map<String,String>  jsonMap= JackJson.fromJsonToObject(ampAttrJson, Map.class);
        List<String> nameList = new LinkedList<String>();
        List<String> valueLis = new LinkedList<String>();
        for(Map.Entry<String,String> tempMap:jsonMap.entrySet()){
            nameList.add(tempMap.getKey());
            valueLis.add(tempMap.getValue().equals("")?"":tempMap.getValue());
        }
        //删除原有数据
        String delSql = "DELETE FROM TD_AVMON_AMP_INST_ATTR WHERE AMP_INST_ID = ? AND AGENT_ID = ?";
        Object[] delParams = new Object[]{ampInstId,agentId};
        int delCout = jdbcTemplate.update(delSql,delParams);

        //新增批量修改数据
        String newAttrSql = "INSERT INTO TD_AVMON_AMP_INST_ATTR(AMP_INST_ID,AGENT_ID,NAME,VALUE)VALUES(?,?,?,?)";
        final List<String> nameListF = nameList;
        final List<String> valueLisF = valueLis;
        BatchPreparedStatementSetter batchPreparedStatementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,ampInstId);
                preparedStatement.setString(2,agentId);
                preparedStatement.setString(3,nameListF.get(i));
                preparedStatement.setString(4,valueLisF.get(i));
            }

            @Override
            public int getBatchSize() {
                return nameListF.size();  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        int[] newCount = jdbcTemplate.batchUpdate(newAttrSql,batchPreparedStatementSetter);

        logger.debug("del count: "+delCout);
        logger.debug("insert count:"+newCount.length);
        map.put("success",true);
        return map;
    }

    /**
     * 下发脚本（调度+配置一起下发）成功后为AMP状态为停止运行
     * // modify by mark start
        //下发成功后修改AMP状态为  --正在运行运行
        // modify by mark end
     * @param request 方法需要改进，整体事务控制--muzh
     * @return
     */
    public Map pushAgentAmpScript(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        //获取需要操作的AMPList
        final  List<Map<String,String>> ampListMapF = getListMapByJsonArrayString(request.getParameter("agentAmpInfo"));
        StringBuffer buff = new StringBuffer();
        String msg = "";
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
      
        for(Map<String,String> ampMap : ampListMapF){
        	String ampInstId = ampMap.get("ampInstId");
        	String agentId = ampMap.get("agentId");
        	String ampId = ampMap.get("ampId");
        	
        	String result = avmonServer.deployAmpPackage(agentId,ampInstId);
        	buff.append(ampInstId);
        	if(result.startsWith("00")){
        		// modify by mark start
                //下发成功后修改AMP状态为  --正在运行运行
        		String updateAmpStatusSql = String.format("UPDATE TD_AVMON_AMP_INST SET STATUS = 1,LAST_AMP_UPDATE_TIME = sysdate WHERE AMP_INST_ID ='%s' AND AMP_ID ='%s' AND AGENT_ID ='%s'",ampInstId,ampId,agentId);
        		// modify by mark start
        		jdbcTemplate.update(updateAmpStatusSql);
        		buff.append(bundle.getString("scriptIssuedSuccess"));	
        	}else{
        		buff.append(bundle.getString("scriptIssuedFail"));	
        	}
        	buff.append("<br/>");
            map.put("msg", buff.toString());
        }
//        if(pushFlag){
//            flag  = updateNormalAMPStatus(ampListMapF,ConfigConstant.AMP_STATUS_DISABLED);
//        }

        map.put("success",true);
        return map;
    }

    /**
     * 下发配置
     * @param request
     * @return
     */
    public Map pushAgentAmpConfig(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String agentId = request.getParameter("agentId");
        List<Map<String,String>> ampListMapF = getListMapByJsonArrayString(request.getParameter("agentAmpInfo"));
        boolean flag = false;
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        //TODO 调用接口下发配置
        for(Map<String,String> ampMap : ampListMapF){
        	String ampInstId = ampMap.get("ampInstId");
        	//String moId = ampMap.get("moId");
        	//String kpiCode = ampMap.get("kpiCode");
        	//String schedule = ampMap.get("schedule");
        	String result = avmonServer.deployAmpConfig(agentId, ampInstId); 
        	//String result = avmonServer.deployAmpSchedule(agentId, ampInstId,moId,kpiCode,null,schedule);
        	if(result.startsWith("00")){
        		map.put("success",true);
        		map.put("msg", bundle.getString("configIssuedSuccess"));
        	String sql = String.format("UPDATE TD_AVMON_AMP_INST SET LAST_CONFIG_UPDATE_TIME = sysdate WHERE AMP_INST_ID ='%s' AND AGENT_ID ='%s'",ampInstId,agentId);
        	jdbcTemplate.execute(sql);
        	}else {
            	map.put("success",false);
            	map.put("msg", result);
            }
        }
        
        return map;
    }

    /**
     * 停止普通AMP
     * @param request
     * @return
     */
    public Map pauseNormalAmp(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        //TODO 停止AMP  成功后更新AMP实例状态
       
        final  List<Map<String,String>> ampListMapF = getListMapByJsonArrayString(request.getParameter("agentAmpInfo"));
        boolean flag = false;
        for(Map<String,String> ampMap : ampListMapF){
        	String ampInstId = ampMap.get("ampInstId");
        	String agentId = ampMap.get("agentId");
        	String ampId = ampMap.get("ampId");
        	Locale locale = request.getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        	
        	String result = avmonServer.stopAmp(agentId,ampInstId);
        	if(result.startsWith("00")){
        		String updateAmpStatusSql = String.format("UPDATE TD_AVMON_AMP_INST SET STATUS = 2 WHERE AMP_INST_ID ='%s' AND AMP_ID ='%s' AND AGENT_ID ='%s'",ampInstId,ampId,agentId);
        		jdbcTemplate.update(updateAmpStatusSql);
        		flag = true;
        		map.put("errorMsg", bundle.getString("stopNormalAMPSuccess"));
        	}else {
            	map.put("errorMsg", result);
            }
        }
        
        //updateNormalAMPStatus(ampListMapF,ConfigConstant.AMP_STATUS_DISABLED);
        if(flag) {
            map.put("success",true);
        }else{
            map.put("success",false);
        }
        return map;
    }

    /**
     * 启动普通AMP
     * @param request
     * @return
     */
    public Map startNormalAmp(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        //TODO 启动AMP  成功后更新AMP实例状态
        final  List<Map<String,String>> ampListMapF = getListMapByJsonArrayString(request.getParameter("agentAmpInfo"));
        
        boolean flag = false;
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        for(Map<String,String> ampMap : ampListMapF){
        	String ampInstId = ampMap.get("ampInstId");
        	String agentId = ampMap.get("agentId");
        	String ampId = ampMap.get("ampId");
        	
        	String result = avmonServer.startAmp(agentId,ampInstId);
        	if(result.startsWith("00")){
        		String updateAmpStatusSql = String.format("UPDATE TD_AVMON_AMP_INST SET STATUS = 1 WHERE AMP_INST_ID ='%s' AND AMP_ID ='%s' AND AGENT_ID ='%s'",ampInstId,ampId,agentId);
        		jdbcTemplate.update(updateAmpStatusSql);
        		flag = true;
        		map.put("errorMsg", bundle.getString("startNormalAMPSuccess"));
        	}else {
            	map.put("errorMsg", result);
            }
        }
        
        //boolean flag = updateNormalAMPStatus(ampListMapF, ConfigConstant.AMP_STATUS_ACTIVITY);
        if(flag) {
            map.put("success",true);
        }else{
            map.put("success",false);
        }
        return map;
    }

    /**
     * 获取AMP调度策略串
     * @param request
     * @return
     */
    public Map getNormalAmpSchedule(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String agentId=request.getParameter("agentId");
        String ampInstId=request.getParameter("ampInstId");
        //检查并插入缺失的KPI
        String sql=SqlManager.getSql(this, "updateNormalAmpSchedule");
        sql=sql.replaceAll("\\{AGENT_ID\\}", agentId);
        sql=sql.replaceAll("\\{AMP_INST_ID\\}", ampInstId);
        System.out.println(">>>"+sql);
        jdbcTemplate.execute(sql);
        //返回查询结果
        sql = SqlManager.getSql(KpigetconfigAgentManageService.class, "getNormalAmpSchedule");
        List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql,new Object[]{agentId,ampInstId});
        map.put("root",mapList);
        return map;
    }


    /**
     * 下发选中调度策略
     * @param request
     * @return
     */
    public Map pushAmpSchedule(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String scheduleJsonAttr = request.getParameter("agentAmpInfo");
        String agentId = request.getParameter("agentId");
        List<Map<String,String>> mapList = getListMapByJsonArrayString(scheduleJsonAttr);
        //TODO 调用后台策略下达接口，并返回信息给客户端
        /**
        agentId – Agent ID
        ampInstId – Amp Instance Id
        nodeKey - moId
        kpiCode – KPI Code
        instance – 预留，目前为空
        schedule – 调度串
        */
        boolean flag = false;
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        for(Map<String,String> ampMap : mapList){
        	String ampInstId = ampMap.get("ampInstId");
        	String nodeKey = ampMap.get("nodeKey");
        	String kpiCode = ampMap.get("kpiCode");
        	String schedule = ampMap.get("schedule");
        	
        	String result = avmonServer.deployAmpSchedule(agentId,ampInstId,nodeKey,kpiCode,null,schedule);
        	if(result.startsWith("00")){
        		flag = true;
        		map.put("errorMsg", bundle.getString("dispatchIssuedSuccess"));
        	}else {
            	map.put("errorMsg", bundle.getString("dispatchIssuedFail"));
            }
        }
        map.put("success",flag);
        return map;
    }

    /**
     * 下发AMP调度 多AMP实例方式
     * @param request
     * @return
     */
    public Map findIssuedAmpSchedule(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String scheduleJsonAttr = request.getParameter("agentAmpInfo");
        String agentId = request.getParameter("agentId");
        List<Map<String,String>> mapList = getListMapByJsonArrayString(scheduleJsonAttr);
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        //TODO 调用后台策略下达接口，并返回信息给客户端
        map.put("success",false);
        map.put("errorMsg",bundle.getString("cannotBeIssuedPolicyInformation"));
        return map;
    }

    /**
     * 保存策略信息
     * @param request
     * @return
     */
    public Map saveAgentSchedue(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String scheduleJsonAttr = request.getParameter("scheduleAttr");
        String agentId = request.getParameter("agentId");
        Map<String,String> jsonMap = JackJson.fromJsonToObject(scheduleJsonAttr,Map.class);
        //KPI Code所属群组是否为空，为空则单独进行更新，反之更新所有群组相同的KPI code 策略
        if(jsonMap.get("kpiGroup") == null || jsonMap.get("kpiGroup").equals("")){
           String sql = "update TD_AVMON_AMP_POLICY set SCHEDULE = ? where KPI_CODE = ? and AGENT_ID = ? and AMP_INST_ID = ? and KPI_GROUP is null and node_key='NA'";
           jdbcTemplate.update(sql,new Object[]{jsonMap.get("schedule"),jsonMap.get("kpiCode"),jsonMap.get("agentId"),jsonMap.get("ampInstId")});
        }else{
           String sql = "update TD_AVMON_AMP_POLICY set SCHEDULE = ? where KPI_GROUP = ? and AGENT_ID = ? and AMP_INST_ID = ? and node_key='NA'";
           jdbcTemplate.update(sql,new Object[]{jsonMap.get("schedule"),jsonMap.get("kpiGroup"),jsonMap.get("agentId"),jsonMap.get("ampInstId")});
        }
        map.put("success",true);
        return map;
    }

    /**
     * 卸载AMP信息
     * @param request
     * @return
     */
    public Map uinstallNormalAmp(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        String ampListJson = request.getParameter("agentAmpInfoList");
        String agentId = request.getParameter("agentId");
        List<Map<String,String>> mapList = getListMapByJsonArrayString(ampListJson);
        //TODO 调用后台方法进行卸载AMP信息
        map.put("success",true);
        return map;
    }


    /**
     * 转化JSON数组为对应ListMap信息
     * @param jsonString
     * @return
     */
    private List<Map<String,String>> getListMapByJsonArrayString(String jsonString) {
        List<Map<String,String>> ampListMap = new ArrayList<Map<String, String>>();
        JSONArray jsonArray = null;
        try {
            jsonArray = JSONArray.fromObject(jsonString);//new JSONArray(jsonString);
            for(int s=0;s<jsonArray.size();s++){//.length();s++){
                Map<String,String>  entityMap= JackJson.fromJsonToObject(jsonArray.get(s).toString(), Map.class);
                ampListMap.add(entityMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return ampListMap;
    }

    /**
     * 批量更新AMP实例状态
     * @param ampListMapF
     * @param status
     * @return
     */
    private boolean updateNormalAMPStatus(final  List<Map<String,String>> ampListMapF,final int status){
        String updateSql = "UPDATE TD_AVMON_AMP_INST SET STATUS = ? WHERE AMP_INST_ID = ? AND AMP_ID = ? AND AGENT_ID = ?";
        int[] updateCount = jdbcTemplate.batchUpdate(updateSql,new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Map<String,String> stringMap =  ampListMapF.get(i);
                preparedStatement.setInt(1,status);
                preparedStatement.setString(2,stringMap.get("ampInstId"));
                preparedStatement.setString(3,stringMap.get("ampId"));
                preparedStatement.setString(4,stringMap.get("agentId"));
            }

            @Override
            public int getBatchSize() {
                return ampListMapF.size();  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        if(updateCount.length == ampListMapF.size())
            return true;
        return false;
    }

    /**
     * 根据AgentId更新agent状态
     * @param status
     * @param agentId
     * @return
     */
    private String updateAgentStatusByAgentId(String status,String agentId,int agentCollectFlag){
    	// modify by mark start
    	// 增加agent下发状态
        String updateSql = String.format("update td_avmon_agent set agent_status = '%s',AGENT_COLLECT_FLAG = %s where AGENT_ID = '%s'",status,agentCollectFlag,agentId);
        // modify by mark end
        jdbcTemplate.execute(updateSql);
        return "success";
    }


    private String generatPageSql(String sql,String limit,String start){
        Integer limitL = Integer.valueOf(limit);
        Integer startL = Integer.valueOf(start);//+1;

        //构造oracle数据库的分页语句
        /*StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
        paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
        paginationSQL.append(sql);
        paginationSQL.append(" ) temp where ROWNUM <= " + (startL-1+limitL));//limitL*((startL-1)/10+1));//limitL*startL);
        paginationSQL.append(" ) WHERE num > " + (startL-1));
        return paginationSQL.toString();*/
        return DBUtils.pagination(sql, startL, limitL);
    }

    /**
     * 获取基本配置列表信息
     * @param request
     * @return
     */
    public List<Map<String,Object>> findAmpInstAttrGridInfo(HttpServletRequest request) {

        String agentId = request.getParameter("agentId");
        String ampInstId = request.getParameter("ampInstId");

        String listSql = SqlManager.getSql(KpigetconfigAgentManageService.class, "findAmpInstAttrList");
        listSql = String.format(listSql,ampInstId,agentId);

        List<Map<String,Object>> list =  jdbcTemplate.queryForList(listSql);

        return list;
	}

    /**
     * 保存基本配置
     * @param agentId
     * @param ampInstId
     * @param attrs
     * @return
     * @throws Exception
     */
	public Map saveAmpInstAttr(String agentId, String ampInstId,String attrs) throws Exception{
		
		List<String> sqlList = new ArrayList<String>();
		
        String []attrss=attrs.split(",");
        for(String attr:attrss){
            String []tmp=attr.split("=");
            if(tmp.length==2){
                String attrName=tmp[0];
                String attrValue=tmp[1];
                String updateAmpInstAttrSql = "update td_avmon_amp_inst_attr set value='%s'" +
                		" where agent_id='%s' and amp_inst_id='%s' and name='%s'";
                updateAmpInstAttrSql = String.format(updateAmpInstAttrSql,attrValue,agentId,ampInstId,attrName);
                
                sqlList.add(updateAmpInstAttrSql);
                
                //jdbcTemplate.execute(updateAmpInstAttrSql);
            }
        }
        String[] sqlArray = sqlList.toArray(new String[sqlList.size()]);
        try {
			transfer(sqlArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(this.getClass().getName() + " saveAmpInstAttr()",e);
		}
        
        return null;
    }

	/**
	 * 保存虚机的调度
	 * @param agentId
	 * @param ampInstId
	 * @param schedule
	 */
	public Map saveVmSchedule(String agentId, String ampInstId, String schedule) {
		String updateAmpInstScheduleSql = "update td_avmon_amp_inst set schedule='%s'" +
        		" where agent_id='%s' and amp_inst_id='%s'";
		updateAmpInstScheduleSql = String.format(updateAmpInstScheduleSql,schedule,agentId,ampInstId);
        jdbcTemplate.execute(updateAmpInstScheduleSql);
        //调用下发调度接口
        Map<String, Object> map = new HashMap<String, Object>();
        boolean flag = false;
      
        String result = avmonServer.deployAmpSchedule(agentId, ampInstId);
        if(result.startsWith("00")){
    		flag = true;
    		map.put("msg", "调度下发失败!");
    	}else {
        	map.put("msg", result);
        }
        
        map.put("success",flag);
        return map;
	}

	/**
	 * 保存虚机
	 * @param newObj
	 * @param agentId
	 * @param ampInstId
	 */
	public void saveVmHost(TreeObject newObj,String agentId,String ampInstId) {
		//modify by mark start 2013-9-12
		String insertVmHostSql = "insert into td_avmon_amp_vm_host(ip,hostname,host_status,enable_flag,obj_id,obj_name,obj_parent,agent_id,amp_inst_id,obj_type) " +
				"values('%s','%s','%s',0,'%s','%s','%s','%s','%s','%s')";
		//modify by mark end 2013-9-12
		insertVmHostSql = String.format(insertVmHostSql,newObj.getHostIp(),newObj.getHostName(),newObj.getHostStatus(),
				newObj.getId(),newObj.getText(),newObj.getPid(),agentId,ampInstId,newObj.getObjType());
		
		jdbcTemplate.execute(insertVmHostSql);
	}

	/**
	 * 更新虚机的监控状态
	 * @param agentId
	 * @param ampInstId
	 * @param flag
	 * @param objIds
	 * @throws Exception 
	 */
	public void updateVmMonitorStatus(String agentId, String ampInstId,
			String flag, String objIds) throws Exception {
		String objIdArray[] = objIds.split(",");
		
		String addMoId = "";
		
		List<String> addSql = new ArrayList();
		
		if("start".equalsIgnoreCase(flag)){//若是启动新的监控虚机，需要调用添加MO对象接口。
			for(String objId:objIdArray){
				String moId = UUID.randomUUID().toString();
				
				String selectVmInfoSql = String.format("select ip as \"ip\",hostname as \"hostName\" " +
						"from td_avmon_amp_vm_host " +
						"where agent_id='%s' and amp_inst_id='%s' and obj_id='%s'",agentId,ampInstId,objId);
				Map<String,String> vmMap = jdbcTemplate.queryForMap(selectVmInfoSql);
				Map<String,String> attrMap = new HashMap();
				attrMap.put("ip", vmMap!=null?vmMap.get("ip"):null);
				attrMap.put("hostname", vmMap!=null?vmMap.get("hostName"):null);
				String addReslult = this.addMo(moId, "VMHOST", objId, null, objId, agentId, attrMap);
				if(addReslult.startsWith("00")){
					addMoId = addMoId + "'" + moId + "',";
					// modify by mark start 2013-9-26
					String updateVmEnableFlagSql = "update td_avmon_amp_vm_host set enable_flag=%d ,mo_id='%s'" +
							"where agent_id='%s' and amp_inst_id='%s' and obj_id = '%s'";
					// modify by mark end 2013-9-26
					updateVmEnableFlagSql = String.format(updateVmEnableFlagSql, 1,moId, agentId, ampInstId, objId);
					addSql.add(updateVmEnableFlagSql);
				}else if("1".equalsIgnoreCase(addReslult)){
					throw new Exception("license count error!");
				}else{
					throw new Exception(addReslult);
				}
			}
			if(addMoId.length()>0){
				addMoId = addMoId.substring(1,addMoId.length()-2);
			}
		}
		
		if("start".equalsIgnoreCase(flag)){
			try{
				String[] sqlArray = addSql.toArray(new String[addSql.size()]);
				transfer(sqlArray);
			}catch(Exception e){
				//若是启动时异常，则需要删除添加了的MO对象；
				this.deleteMo(addMoId);
				e.printStackTrace();
				logger.error(this.getClass().getName() + " updateVmMonitorStatus",e);
			}
		}else{
			int enableFlag = 0;
			String updateObjIds = objIds.replace(",", "','");
			String updateVmEnableFlagSql = "update td_avmon_amp_vm_host set enable_flag=%d " +
					"where agent_id='%s' and amp_inst_id='%s' and obj_id in ('%s')";
			updateVmEnableFlagSql = String.format(updateVmEnableFlagSql, enableFlag, agentId, ampInstId, updateObjIds);
			try{
				jdbcTemplate.execute(updateVmEnableFlagSql);
			}catch(Exception e){
				e.printStackTrace();
				logger.error(this.getClass().getName() + " updateVmMonitorStatus",e);
			}
		}
	}

	/**
	 * 删除虚机
	 * @param agentId
	 * @param ampInstId
	 * @param objIds
	 * @throws Exception 
	 */
	public void deleteVmHost(String agentId, String ampInstId, String objIds) throws Exception {
		
		//删除mo对象
		String deleteMoAttrSql = String.format("delete from td_avmon_mo_info_attribute " +
				"where mo_id in (" +
				"select mo_id from td_avmon_amp_vm_host " +
				"where agent_id='%s' and amp_inst_id='%s' and obj_id in ('%s')" +
				")",agentId, ampInstId, objIds.replace(",", "','"));
		String deleteMoSql = String.format("delete from td_avmon_mo_info where mo_id in (" +
				"select mo_id from td_avmon_amp_vm_host " +
				"where agent_id='%s' and amp_inst_id='%s' and obj_id in ('%s')" +
				")",agentId, ampInstId, objIds.replace(",", "','"));
		
		String deleteVmHostSql = "delete from td_avmon_amp_vm_host " +
				"where agent_id='%s' and amp_inst_id='%s' and obj_id in ('%s')";
		deleteVmHostSql = String.format(deleteVmHostSql, agentId, ampInstId, objIds.replace(",", "','"));
		String[] deleteSql = {deleteMoAttrSql,deleteMoSql,deleteVmHostSql};
		try {
			String result = transfer1(deleteSql);
			if("1".equals(result)){
				throw new Exception(this.getClass().getName() + " deleteVmHost transfer error!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(this.getClass().getName() + " deleteVmHost",e);
			throw e;
		}
	}

	/**
	 * 获取ilo调度列表信息
	 * @param request
	 * @return
	 */
	public Map findAmpInstPolicyGridInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		//获取查询参数
        String agentId = request.getParameter("agentId");
        String ampInstId = request.getParameter("ampInstId");
        String flag = request.getParameter("flag");
        String nodeKey = request.getParameter("nodeKey");
        
        String listSql = "";
        if("new".equalsIgnoreCase(flag)){
        	listSql = SqlManager.getSql(KpigetconfigAgentManageService.class, "findAmpKpiList");
        	listSql = String.format(listSql, agentId, ampInstId);
        }else{
        	listSql = SqlManager.getSql(KpigetconfigAgentManageService.class, "findAmpInstPolicyList");
        	listSql = String.format(listSql, agentId, ampInstId, nodeKey);
        }
        List<Map<String,Object>> list =  jdbcTemplate.queryForList(listSql);

        map.put("root", list);
        return map;	
	}

	/**
	 * 获取ilo主机列表信息
	 * @param request
	 * @return
	 */
	public Map findIloHostGridInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		//获取查询参数
        String agentId = request.getParameter("agentId");
        String ampInstId = request.getParameter("ampInstId");
        
        String listSql = SqlManager.getSql(KpigetconfigAgentManageService.class, "findAmpIloHostList");
        listSql = String.format(listSql, agentId, ampInstId);
        
        List<Map<String,Object>> list =  jdbcTemplate.queryForList(listSql);

        map.put("root", list);
        return map;	
	}

	/**
	 * 保存ilo主机
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String saveIloHost(HttpServletRequest request) throws Exception{
		String agentId = request.getParameter("agentId");
		String ampInstId = request.getParameter("ampInstId");
		String ip = request.getParameter("ip");
		String oldIp = request.getParameter("oldIp");
		String hostName = request.getParameter("hostName");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String extParam1 = request.getParameter("extParam1");
		String extParam2 = request.getParameter("extParam2");
		String extParam3 = request.getParameter("extParam3");
		String policys = request.getParameter("policys");
		String flag = request.getParameter("flag");
		String moId = request.getParameter("moId");
		
		JSONArray json = JSONArray.fromObject(policys);
		List<AmpPolicyBean> policyList = (List<AmpPolicyBean>)JSONArray.toCollection(json, AmpPolicyBean.class);
		
		List<String> sqlList = new ArrayList<String>();
		String mo_id = UUID.randomUUID().toString();
		String iloCountSql = "select count(1) from td_avmon_amp_ilo_host where agent_id='%s' and amp_inst_id='%s' and ip='%s'";
		iloCountSql = String.format(iloCountSql, agentId, ampInstId, ip);
		int iloHostCount = jdbcTemplate.queryForInt(iloCountSql);
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		if("new".equalsIgnoreCase(flag)){
			if(iloHostCount>0){
				return "{success:false,msg:'"+bundle.getString("ipExisted")+"'}";
			}else{
				//添加ilo主机后需要调用添加mo info接口方法添加监控对象
				Map<String,String> attrMap = new HashMap();
				attrMap.put("ip", ip);
				attrMap.put("hostname", hostName);
				String addResult = this.addMo(mo_id, "iloRoot", hostName, null, ip, agentId, attrMap);
				if("1".equalsIgnoreCase(addResult)){
					return "{success:false,msg:'"+bundle.getString("monitoringObjectHasReachedTheMaximumLimit")+"'}";
				}else if(!addResult.startsWith("00")){
					return "{success:false,msg:'" + addResult + "'}";
				}
				String insertIloHostSql = "insert into td_avmon_amp_ilo_host(agent_id,amp_inst_id,ip,hostname,username,password,ext_param1,ext_param2,ext_param3,mo_id) " +
						"values('%s','%s','%s','%s','%s','%s','%s','%s','%s','" + mo_id + "')";
				insertIloHostSql = String.format(insertIloHostSql, agentId,ampInstId,ip,hostName,userName,password,extParam1,extParam2,extParam3);
				sqlList.add(insertIloHostSql);
				
				for(AmpPolicyBean policy : policyList){//新建ilo主机时copy对应的kpi信息
					String insertPolicySql = "insert into td_avmon_amp_policy(agent_id,amp_inst_id,kpi_code,node_key,kpi_group,schedule) " +
							"values('%s','%s','%s','%s','%s','%s')";
					
					insertPolicySql = String.format(insertPolicySql, agentId,ampInstId,policy.getKpiCode(),ip,policy.getKpiGroup()==null?"":policy.getKpiGroup(),MyFunc.returnNull(policy.getSchedule()));
					sqlList.add(insertPolicySql);
				}
			}
		}else if(!ip.equalsIgnoreCase(oldIp)){//修改时修改了IP需要判断IP可用性以及修改对应的policy信息,MO attr的信息
			if(iloHostCount>0){
				return "{success:false,msg:'"+bundle.getString("ipExisted")+"'}";
			}else{
				//1。update td_avmon_amp_policy
				String updatePolicyNodeKey = "update td_avmon_amp_policy set node_key='%s' where node_key='%s'";
				updatePolicyNodeKey = String.format(updatePolicyNodeKey, ip, oldIp);
				sqlList.add(updatePolicyNodeKey);
				//2。update mo_attr
				String updateMoAttrIp = "update td_avmon_mo_info_attribute set value='%s' where upper(name)=upper('%s') and mo_id='%s'";
				updateMoAttrIp = String.format(updateMoAttrIp, ip,"ip",moId);
				sqlList.add(updateMoAttrIp);
				//3。update avmon_ilo_host
				String updateIloHostIp = "update td_avmon_amp_ilo_host set ip='%s' where ip='%'";
				updateIloHostIp = String.format(updateIloHostIp, ip, oldIp);
				sqlList.add(updateIloHostIp);
			}
		}else{
			String updateIloHostSql = "update td_avmon_amp_ilo_host set hostname='%s',username='%s',password='%s'," +
					"ext_param1='%s',ext_param2='%s',ext_param3='%s' " +
					"where ip='%s'";
			updateIloHostSql = String.format(updateIloHostSql, hostName,userName,password,extParam1,extParam2,extParam3,ip);
			sqlList.add(updateIloHostSql);
		}
		
		String[] sqlArray = sqlList.toArray(new String[sqlList.size()]);
		//jdbcTemplate.batchUpdate(sqlArray);
		try {
			transfer(sqlArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//若是新增ilo主机再添加了MO对象之后出异常需要删除已添加的MO对象
			if("new".equalsIgnoreCase(flag)){
				this.deleteMo(mo_id);
			}
			e.printStackTrace();
			logger.error(this.getClass().getName() + " saveIloHost()",e);
			return "{success:false,msg:'"+bundle.getString("saveFail")+"'}";
		}
		return "{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
	}
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	//用于控制事务
	public void transfer(final String[] sqls) throws Exception{
		final String trasactionFlag = "0";
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					// JdbcTemplate batchUpdate操作
					jdbcTemplate.batchUpdate(sqls);
				} catch (Exception e) {
					System.out.println("----------RuntimeException-----" + e);
					status.setRollbackOnly(); // 回滚
				}
			}
		});
	}
	
	//用于控制事务
	public String transfer1(final String[] sqls) throws Exception{
		String flag = transactionTemplate.execute(new TransactionCallback<String>() {
			public String doInTransaction(TransactionStatus status) {
				try {
					// JdbcTemplate batchUpdate操作
					jdbcTemplate.batchUpdate(sqls);
				} catch (Exception e) {
					System.out.println("----------RuntimeException-----" + e);
					status.setRollbackOnly(); // 回滚
					
					if(e.toString().indexOf("ORA-00001: unique constraint")>-1){
						return "ORA-00001";
		            }else{
		            	return "1";
		            }
				}
				return "0";
			}
		});
		return flag;
	}

	/**
	 * 保存ilo主机的调度配置
	 * @param request
	 * @return
	 */
	public Map saveIloSchedule(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
        String ampInstId = request.getParameter("ampInstId");
        String agentId = request.getParameter("agentId");
        String nodeKey = request.getParameter("nodeKey");
        String kpiCode = request.getParameter("kpiCode");
        String schedule = request.getParameter("schedule");
        String kpiGroup = request.getParameter("kpiGroup");

        try{
        	//KPI Code所属群组是否为空，为空则单独进行更新，反之更新所有群组相同的KPI code 策略
            if(kpiGroup == null || "".equals(kpiGroup)){
               String sql = "update TD_AVMON_AMP_POLICY set SCHEDULE = ? " +
               		"where KPI_CODE = ? and AGENT_ID = ? and AMP_INST_ID = ? and node_key=? and KPI_GROUP is null ";
               jdbcTemplate.update(sql,new Object[]{schedule,kpiCode,agentId,ampInstId,nodeKey});
            }else{
               String sql = "update TD_AVMON_AMP_POLICY set SCHEDULE = ? where KPI_GROUP = ? and AGENT_ID = ? and AMP_INST_ID = ? and node_key=?";
               jdbcTemplate.update(sql,new Object[]{schedule,kpiGroup,agentId,ampInstId,nodeKey});
            }
            map.put("success",true);
        }catch(Exception e){
        	e.printStackTrace();
        	logger.error(this.getClass().getName() + " saveIloSchedule",e);
        	map.put("success",false);
        	return map;
        }
        
        return map;
	}

	/**
	 * 添加监控对象接口
	 * @param moId
	 * @param typeId
	 * @param caption
	 * @param parentId
	 * @param desc
	 * @param agentId
	 * @param attrMap
	 * 
	 * private String moId;
    private String agentId;
    private String caption;
    private String type;
    private String description;
    private Map attr;
	 * 
	 * @return "0"添加成功，"1"MO数量超出，"2"调用接口失败
	 */
	public String addMo(String moId,String typeId,String caption,String parentId,String desc,String agentId,Map<String,String> attrMap){
		logger.debug(String.format("addMo %s type=%s",moId,typeId ));
	    int maxMoCount = licenseService.getMaxMoCount();
		String countMoSql = "select count(1) from td_avmon_mo_info";
		int moCount = jdbcTemplate.queryForInt(countMoSql);
		String result = "99";
		if(moCount>=maxMoCount){
			return "1";
		}else{
			//调用yongqiang接口添加MO_INFO
			MO mo = new MO();
			mo.setAgentId(agentId);
			mo.setCaption(caption);
			mo.setDescription(desc);
			mo.setMoId(moId);
			mo.setType(typeId);
			if(attrMap!=null){
				Set<String> key = attrMap.keySet();        
				for (Iterator it = key.iterator(); it.hasNext();) {            
					String attrName = (String) it.next();            
					mo.setAttr(attrName, attrMap.get(attrName));     
				}
			}
			result = avmonServer.createMo(mo);
			logger.debug("avmonServer.createMo(mo) return "+result);
		}
		
		return result;
		
	}
	
	/**
	 * 删除监控对象
	 * @param moId
	 * @return
	 */
	public String deleteMo(String moId){
		String deleteMoAttrSql = String.format("delete from td_avmon_mo_info_attribute where mo_id in ('%s')",moId);
		String deleteMoSql = String.format("delete from td_avmon_mo_info where mo_id in ('%s')",moId);
		String[] deleteSql = {deleteMoAttrSql,deleteMoSql};
		try {
			transfer(deleteSql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "1";
		}
		return "0";
	}

	public void deleteIloHostByIps(String agentId, String ampInstId, String ips) throws Exception {
		//删除mo对象
		String deleteMoAttrSql = String.format("delete from td_avmon_mo_info_attribute " +
				"where mo_id in (" +
				"select mo_id from td_avmon_amp_ilo_host " +
				"where agent_id='%s' and amp_inst_id='%s' and ip in (%s)" +
				")",agentId, ampInstId, ips);
		String deleteMoSql = String.format("delete from td_avmon_mo_info where mo_id in (" +
				"select mo_id from td_avmon_amp_ilo_host " +
				"where agent_id='%s' and amp_inst_id='%s' and ip in (%s)" +
				")",agentId, ampInstId, ips);
		
		String deleteIloHostSql = "delete from td_avmon_amp_ilo_host " +
				"where agent_id='%s' and amp_inst_id='%s' and ip in (%s)";
		deleteIloHostSql = String.format(deleteIloHostSql, agentId, ampInstId, ips);
		String[] deleteSql = {deleteMoAttrSql,deleteMoSql,deleteIloHostSql};
		try {
			String result = transfer1(deleteSql);
			if("1".equals(result)){
				throw new Exception(this.getClass().getName() + " deleteIloHost transfer error!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(this.getClass().getName() + " deleteIloHost",e);
			throw e;
		}
	}

	public Map pushAgentAmpSchedule(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
        //获取需要操作的AMPList
        final  List<Map<String,String>> ampListMapF = getListMapByJsonArrayString(request.getParameter("agentAmpInfo"));
        StringBuffer buff = new StringBuffer();
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        
        for(Map<String,String> ampMap : ampListMapF){
        	String ampInstId = ampMap.get("ampInstId");
        	String agentId = ampMap.get("agentId");
        	String ampId = ampMap.get("ampId");
        	
        	String result = avmonServer.deployAmpSchedule(agentId,ampInstId);
        	buff.append(ampInstId);
        	if(result.startsWith("00")){
        		String updateAmpStatusSql = String.format("UPDATE TD_AVMON_AMP_INST SET STATUS = 1,LAST_SCHEDULE_UPDATE_TIME = SYSDATE WHERE AMP_INST_ID ='%s' AND AMP_ID ='%s' AND AGENT_ID ='%s'",ampInstId,ampId,agentId);
        		jdbcTemplate.update(updateAmpStatusSql);
        		buff.append(bundle.getString("dispatchIssuedSuccess"));
        	}else{
        		buff.append(bundle.getString("dispatchIssuedFail"));
        	}
        	
        	buff.append("<br/>");
            map.put("msg", buff.toString());
        }
        map.put("success",true);
        return map;
	}

	public Map batchPushAgentAmpSchedule(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
        //获取需要操作的AMPList
        final  List<Map<String,String>> ampListMapF = getListMapByJsonArrayString(request.getParameter("agentAmpInfo"));
        boolean flag = false;
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        for(Map<String,String> ampMap : ampListMapF){
        	String ampInstId = ampMap.get("ampInstId");
        	String agentId = ampMap.get("agentId");
        	
        	String result = avmonServer.deployAmpSchedule(agentId,ampInstId);
        	if(result.startsWith("00")){
        		//String updateAmpStatusSql = String.format("UPDATE TD_AVMON_AMP_INST SET STATUS = 2 WHERE AMP_INST_ID ='%s' AND AMP_ID ='%s' AND AGENT_ID ='%s'",ampInstId,ampId,agentId);
        		//jdbcTemplate.update(updateAmpStatusSql);
        		flag = true;
        		map.put("errorMsg", bundle.getString("dispatchIssuedSuccess"));
        	}else {
            	map.put("errorMsg", result);
            }
        }
        map.put("success",flag);
        return map;
	}
	
	
	public Map pushIloHostSchedule(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
        String agentId = request.getParameter("agentId");
        List<Map<String,String>> ampListMapF = getListMapByJsonArrayString(request.getParameter("agentAmpInfo"));
        boolean flag = false;
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        //TODO 调用接口下发配置
        for(Map<String,String> ampMap : ampListMapF){
        	String ampInstId = ampMap.get("ampInstId");
        	String ip = ampMap.get("ip");
        	
        	String sql = String.format("select * from td_avmon_amp_policy where agent_id='%s' and amp_inst_id='%s' and node_key='%s'",agentId,ampInstId,ip);
        	
        	List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        	
        	for(Map<String,Object> m : list){
        		String result = avmonServer.deployAmpSchedule(agentId, ampInstId, ampMap.get("moId"), m.get("KPI_CODE").toString(), null, m.get("SCHEDULE").toString()); 
            	//String result = avmonServer.deployAmpSchedule(agentId, ampInstId,moId,kpiCode,null,schedule);
            	if(result.startsWith("00")){
            		map.put("success",true);
            		map.put("errorMsg", bundle.getString("dispatchIssuedSuccess"));
            	}else {
                	map.put("success",false);
                	map.put("errorMsg", result);
                	return map;
                }
        	}
        }
        
        return map;
	}

	public Map pushSelectedIloHostSchedule(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
        String moId = request.getParameter("moId");
//        String nodeKey = null;
        // modify by mark start
//        String sql = SqlManager.getSql(AgentManageService.class, "getIloNodeKey");
//        sql = String.format(sql, moId);
//        List<Map<String,String>>list = jdbcTemplate.queryForList(sql);
//        if (list.size() > 0) {
//			nodeKey = list.get(0).get("ip");
//		}
        
        
        // modify by mark end
        List<Map<String,String>> ampListMapF = getListMapByJsonArrayString(request.getParameter("agentAmpInfo"));
        //TODO 调用接口下发配置
        for(Map<String,String> ampMap : ampListMapF){
        	String agentId = ampMap.get("agentId");
        	String ampInstId = ampMap.get("ampInstId");
        	String kpiCode = ampMap.get("kpiCode");
        	//String nodeKey = moId;
        	String schedule = ampMap.get("schedule");
        	// modify by mark start 2013-9-12
        	String result = avmonServer.deployAmpSchedule(agentId, ampInstId, moId, kpiCode, null, schedule);
        	// modify by mark end 2013-9-12
        	//String result = avmonServer.deployAmpSchedule(agentId, ampInstId,moId,kpiCode,null,schedule);
        	Locale locale = request.getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        	if(result.startsWith("00")){
        		map.put("success",true);
        		map.put("errorMsg", bundle.getString("scriptIssuedSuccess"));
        	}else {
            	map.put("success",false);
            	map.put("errorMsg", result);
            	return map;
            }
        }
        
        return map;
	}

	public void updateAgentMoId(String agentId, String moId) {
		String updateSql = String.format("update td_avmon_agent set mo_id='%s' where agent_id='%s'",moId,agentId);
		jdbcTemplate.execute(updateSql);
	}

    public String addAgentMo(String moId, String agentId) {
        String sql = String.format("select OS,OS_VERSION,IP from TD_AVMON_AGENT where AGENT_ID = '%s'",agentId);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        if(list!=null && list.size()>0){
            String os=(String) list.get(0).get("OS");
            String ip=(String) list.get(0).get("IP");
            String osVersion=(String) list.get(0).get("OS_VERSION");
            if(os!=null){
                os=os.toUpperCase();
                String type="HOST_"+os;
                Map attr=new HashMap();
                attr.put("ip", ip);
                attr.put("osVersion", osVersion);
                attr.put("os",os);
                return this.addMo(moId, type, ip, null, "Auto created for agent.", agentId, attr);
            }
        }
        return "99:未知错误";
    }

    public String removeAgent(HttpServletRequest request) {
        String status = request.getParameter("status");
        String agentId = request.getParameter("agentId");
        String json = "";
        String sql=String.format("delete from TD_AVMON_AMP_INST_ATTR where agent_id='%s'",agentId);
        jdbcTemplate.execute(sql);
        //TD_AVMON_AMP_POLICY
        sql=String.format("delete from TD_AVMON_AMP_POLICY where agent_id='%s'",agentId);
        jdbcTemplate.execute(sql);
        sql=String.format("delete from TD_AVMON_AMP_INST where agent_id='%s'",agentId);
        jdbcTemplate.execute(sql);
        sql=String.format("delete from TD_AVMON_AGENT where agent_id='%s'",agentId);
        jdbcTemplate.execute(sql);
        //删除以agentid为moid的对象
        String moId=agentId;
        sql=String.format("delete from TD_AVMON_MO_INFO_ATTRIBUTE where mo_id='%s' ",moId);
        jdbcTemplate.execute(sql);
        sql=String.format("delete from TD_AVMON_MO_INFO where mo_id='%s' ",moId);
        jdbcTemplate.execute(sql);
        
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        
        json="{success:true,msg:'"+bundle.getString("agentDeleteSuccess")+"'}";

        return json;
    }

    public String upgradeAgent(HttpServletRequest request) {
        String status = request.getParameter("status");
        String agentId = request.getParameter("agentId");
        String json = "";
        String result = avmonServer.upgradeAgent(agentId);
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        
        if(result.startsWith("00")){
            json="{success:true,msg:'"+bundle.getString("agentNewProcedureMsg")+"'}";
        }else {
            json="{success:false,msg:'" + result + "'}";
        }
        return json;
    }

    public Map removeAmp(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        

        //TODO 停止AMP  成功后更新AMP实例状态
       
        final  List<Map<String,String>> ampListMapF = getListMapByJsonArrayString(request.getParameter("agentAmpInfo"));
        boolean flag = false;
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        for(Map<String,String> ampMap : ampListMapF){
            String ampInstId = ampMap.get("ampInstId");
            String agentId = ampMap.get("agentId");
            String ampId = ampMap.get("ampId");
            
            jdbcTemplate.execute(String.format("delete from TD_AVMON_AMP_POLICY where agent_id='%s' and amp_inst_id='%s'",agentId,ampInstId));
            jdbcTemplate.execute(String.format("delete from TD_AVMON_AMP_VM_HOST where agent_id='%s' and amp_inst_id='%s'",agentId,ampInstId));
            jdbcTemplate.execute(String.format("delete from TD_AVMON_AMP_ILO_HOST where agent_id='%s' and amp_inst_id='%s'",agentId,ampInstId));
            jdbcTemplate.execute(String.format("delete from TD_AVMON_AMP_INST_ATTR where agent_id='%s' and amp_inst_id='%s'",agentId,ampInstId));
            jdbcTemplate.execute(String.format("delete from TD_AVMON_AMP_INST where agent_id='%s' and amp_inst_id='%s'",agentId,ampInstId));
            
            map.put("errorMsg", bundle.getString("uninstallSuccess"));
            
        }
        
        //updateNormalAMPStatus(ampListMapF,ConfigConstant.AMP_STATUS_DISABLED);
        if(flag) {
            map.put("success",true);
        }else{
            map.put("success",false);
        }
        return map;
    }

    // add by mark start
    /**
     * 获取当前agent采集状态 0 启动;1:停止
     * @param agentId
     * @return
     */
	public int getAgentCollectFlag(String agentId) {
		int agentCollectFlag = 1;
		String sql = "select AGENT_COLLECT_FLAG from TD_AVMON_AGENT where AGENT_ID = '"+agentId+"'";
		agentCollectFlag = jdbcTemplate.queryForInt(sql);
		return agentCollectFlag;
	}
	// add by mark end
}