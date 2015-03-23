package com.hp.avmon.kpigetconfig.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.common.Config;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.common.sqlgenerator.CreateSqlTools;
import com.hp.avmon.config.bean.IreportDatasourceBean;
import com.hp.avmon.config.bean.IreportMgtBean;
import com.hp.avmon.config.bean.TdAvmonAutoCloseRuleBean;
import com.hp.avmon.config.bean.TdAvmonFilterRuleBean;
import com.hp.avmon.config.bean.TdAvmonKpiThresholdBean;
import com.hp.avmon.config.bean.TdAvmonMergeRuleBean;
import com.hp.avmon.config.bean.TdAvmonNotifyRuleBean;
import com.hp.avmon.config.bean.TdAvmonTranslateRuleBean;
import com.hp.avmon.config.bean.TdAvmonUpgradeRuleBean;
import com.hp.avmon.config.bean.TfAvmonRouteInspectionDevBean;
import com.hp.avmon.config.bean.TfAvmonRouteInspectionKpiBean;
import com.hp.avmon.config.bean.TfAvmonRouteKpiCodeBean;
import com.hp.avmon.config.service.ConfigService;
import com.hp.avmon.home.service.HomeService;
import com.hp.avmon.ireport.util.StringUtil;
import com.hp.avmon.kpigetconfig.bean.TdAvmonKpiInfoBeanSnmp;
import com.hp.avmon.utils.DBUtils;
import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.utils.Utils;
 
@Controller
@SuppressWarnings("unchecked")
@RequestMapping("/kpigetconfig/*")
public class KpigetConfigController {
	
	private static final Log logger = LogFactory.getLog(KpigetConfigController.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private HomeService homeService;
	
	
	private static List<HashMap<String, Object>> RULELIST = new ArrayList<HashMap<String, Object>>();
	
	@RequestMapping(value = "main")
	public String index(Locale locale, Model model) {

		return "/kpigetconfig/main";

	}
	
    @RequestMapping(value = "menuTree")
    public void getMenuTree(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String userId=Utils.getCurrentUserId(request);
        String type=request.getParameter("type");
        String parentId=request.getParameter("id");
        if(parentId==null||parentId.equals("")){
            parentId="root";
        }
        Object obj=homeService.getMenuTree(userId,parentId,type);
        String json=JackJson.fromObjectToJson(obj);
        //System.out.println(json);
        writer.write(json);
        writer.flush();
        writer.close();
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
	
	private String getGridRoot(String baseSql,String sortBy,String limit,String start){
		String sql = baseSql + " order by r.id desc ";
		if(sortBy!=null){
			JSONArray jsonArr = JSONArray.fromObject(sortBy);
			String sort = jsonArr.getJSONObject(0).get("property").toString();
			sql = baseSql + " order by r." + sort + " " + jsonArr.getJSONObject(0).get("direction");
		}
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(generatPageSql(sql,limit,start));
		
		JSONArray jsonArray = JSONArray.fromObject(list);
		String root = jsonArray.toString();
		return root;
	}
	
    @RequestMapping("index")
    public String sysMain(HttpServletRequest request,Model model) throws IOException {
    	String type=request.getParameter("type");
    	model.addAttribute("moId", request.getParameter("moId"));
    	if("upgradeRule".equalsIgnoreCase(type)){
    		return "config/alarmUpgradeRule"; 
    	}else if("mergeRule".equalsIgnoreCase(type)){
    		return "config/alarmMergeRule"; 
    	}else if("filterRule".equalsIgnoreCase(type)){
    		return "config/alarmFilterRule"; 
    	}else if("translateRule".equalsIgnoreCase(type)){
    		return "config/alarmTranslateRule"; 
    	}else if("autoCloseRule".equalsIgnoreCase(type)){
    		return "config/alarmAutoCloseRule"; 
    	}else if("kpiThreshold".equalsIgnoreCase(type)){
    		return "config/kpiThreshold"; 
    	}else if("kpiHistory".equalsIgnoreCase(type)){
    		return "config/kpiHistory"; 
    	}else if("dbResource".equalsIgnoreCase(type)){
    		return "config/dbResource"; 
    	}else if("reportMgt".equalsIgnoreCase(type)){
    		return "config/reportMgt"; 
    	}else if("notifyRule".equalsIgnoreCase(type)){
    		return "/config/notifyRule"; 
    	}else if("routeDevice".equalsIgnoreCase(type)){
    		return "/config/routeDevice"; 
    	}else if("routeKpi".equalsIgnoreCase(type)){
    		return "/config/routeKpi"; 
    	}else if("routeKpiThreshold".equalsIgnoreCase(type)){
    		return "/config/routeKpiThreshold"; 
    	}else if("netArp".equalsIgnoreCase(type)){
    		return "/config/netArp/index"; 
    	}else if("netCheckDetail".equalsIgnoreCase(type)){
    		return "/config/netCheckDetail"; 
    	}else if("routeConfigDetail".equalsIgnoreCase(type)){
    		return "/config/routeDeviceConfig"; 
    	}else if("kpiInfo".equalsIgnoreCase(type)){
    		return "/config/snmp/kpiget_config/kpiInfo"; 
    	}else if("kpiChart".equalsIgnoreCase(type)){
    		return "/config/kpiChart/index"; 
    	}else if("regexConfirm".equalsIgnoreCase(type)){
    		return "/config/regexConfirm"; 
    	}else{
    		logger.error("ConfigController type error!");
    		return "commons/timeout";
    	}
        
    }
    
    //upgrade/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping("alarmUpgradeRuleIndex")
    public String findAlarmUpgradeRuleGridInfo(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String sortBy = request.getParameter("sort");
			
			String baseSql = "select id as \"id\",alarm_count as \"alarm_count\",alarm_count_duration as \"alarm_count_duration\"," +
					"content as \"content\",grade as \"grade\",kpi as \"kpi\",mo as \"mo\",new_grade as \"new_grade\" " +
					" from td_avmon_upgrade_rule r ";
			
			String root = getGridRoot(baseSql,sortBy,request.getParameter("limit"),request.getParameter("start"));
			
			String countSql = "select count(1) from td_avmon_upgrade_rule";
			int total = jdbcTemplate.queryForInt(countSql);

			json = "{total:" + total +",root:" + root + "}";
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findAlarmUpgradeRuleGridInfo",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
    
    @RequestMapping("saveUpdateAlarmUpgradeRuleInfo")
	public String saveUpdateAlarmUpgradeRuleInfo(
			HttpServletResponse response,
			HttpServletRequest request,
			@ModelAttribute TdAvmonUpgradeRuleBean entity){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String updateSql = "";
						
			if(entity.getId()!=null){//更新数据
				updateSql = CreateSqlTools.getUpdateSql(entity);
			}else{//保存新数据
				updateSql = CreateSqlTools.getInsertSql(entity);
			}
			
			configService.execute(updateSql);
			data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" saveUpdateAlarmUpgradeRuleInfo",e);
			data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	@RequestMapping("deleteAlarmUpgradeRuleByIds")
	public String deleteAlarmUpgradeRuleByIds(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String ids = request.getParameter("ids");
			
			String sql = "delete from td_avmon_upgrade_rule where id in(" + ids + ")";
			configService.execute(sql);
			data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" deleteAlarmUpgradeRuleByIds",e);
			data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	//merge///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping("alarmMergeRuleIndex")
    public String findAlarmMergeRuleGridInfo(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String sortBy = request.getParameter("sort");
			
			String baseSql = "select id as \"id\",content as \"content\",grade as \"grade\",kpi as \"kpi\"," +
					"merge_time_window as \"merge_time_window\",mo as \"mo\""+ 
					" from td_avmon_merge_rule r ";

			String root = getGridRoot(baseSql,sortBy,request.getParameter("limit"),request.getParameter("start"));
			
			String countSql = "select count(1) from td_avmon_merge_rule";
			int total = jdbcTemplate.queryForInt(countSql);

			json = "{total:" + total +",root:" + root + "}";
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findAlarmMergeRuleGridInfo",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
    
    @RequestMapping("saveUpdateAlarmMergeRuleInfo")
	public String saveUpdateAlarmMergeRuleInfo(
			HttpServletResponse response,
			HttpServletRequest request,
			@ModelAttribute TdAvmonMergeRuleBean entity){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String updateSql = "";
						
			if(entity.getId()!=null){//更新数据
				updateSql = CreateSqlTools.getUpdateSql(entity);
			}else{//保存新数据
				updateSql = CreateSqlTools.getInsertSql(entity);
			}
			
			configService.execute(updateSql);
			data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" saveUpdateAlarmMergeRuleInfo",e);
			data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	@RequestMapping("deleteAlarmMergeRuleByIds")
	public String deleteAlarmMergeRuleByIds(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String ids = request.getParameter("ids");
			
			String sql = "delete from td_avmon_merge_rule where id in(" + ids + ")";
			configService.execute(sql);
			data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" deleteAlarmMergeRuleByIds",e);
			data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	//filter///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		@RequestMapping("alarmFilterRuleIndex")
	    public String findAlarmFilterRuleGridInfo(
				HttpServletResponse response,
				HttpServletRequest request){
			String json = "";
			try {
				String sortBy = request.getParameter("sort");
				
				String baseSql = "select id as \"id\",content as \"content\",grade as \"grade\",kpi as \"kpi\",mo as \"mo\""+ 
						" from td_avmon_filter_rule r ";

				String root = getGridRoot(baseSql,sortBy,request.getParameter("limit"),request.getParameter("start"));
				
				String countSql = "select count(1) from td_avmon_filter_rule";
				int total = jdbcTemplate.queryForInt(countSql);

				json = "{total:" + total +",root:" + root + "}";
		        
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(this.getClass().getName()+" findAlarmFilterRuleGridInfo",e);
			}
			
			return Utils.responsePrintWrite(response,json.toString(),null);
		}
	
	
	
	// add by mark 2013-12-3 start  
	/**
	 * 验证正则表达式	
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("regexConfirmindex")
    public void findRegexConfirmInfo(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			getAllRules();
			String check = request.getParameter("check");
			if(Boolean.valueOf(check)){
				for (HashMap<String, Object> map : RULELIST) {
					
					if(checkReg(map))
					{
						map.put("status",bundle.getString("pass"));
					}else{
						map.put("status",bundle.getString("notPass"));
					}
				}
			}
		
			int limit = StringUtil.isEmpty(request.getParameter("limit"))?0:Integer.valueOf(request.getParameter("limit"));
			int start = StringUtil.isEmpty(request.getParameter("start"))?0:(Integer.valueOf(request.getParameter("start")));
			
			String sortByPage = request.getParameter("sort");
			if(StringUtil.isEmpty(sortByPage)){
				sortByPage = "module";
			}else{
				sortByPage =  JSONObject.fromObject(JSONArray.fromObject(sortByPage).get(0)).get("property").toString();
			}
			
			final String sortBy = sortByPage;
			List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			Collections.sort(RULELIST, new Comparator<Map<String, Object>>() {

				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					
					return o1.get(sortBy).toString().compareTo(o2.get(sortBy).toString());
				}
			});

			list = RULELIST.subList(start, start+limit >= RULELIST.size()?RULELIST.size():start+limit);
//			logger.debug(list.size());
			JSONArray jsonArray = JSONArray.fromObject(list);
			
			String root = jsonArray.toString();

			json = "{total:" + RULELIST.size() +",root:" + root + "}";
		
			response.getWriter().write(json);
//			logger.debug(json);
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findRegexConfirmInfo",e);
		}

	}
	
	/**
	 * 获得所有过滤规则
	 * @return
	 */
	private void getAllRules(){
		
		List<HashMap<String,Object>> tempList = new ArrayList<HashMap<String,Object>>();
		String mergeRuleSql = "select 'HB_'||id as \"id\", '告警合并规则' as \"module\",content as \"content\",grade as \"grade\",kpi as \"kpi\"," +
				"mo as \"mo\" ,'未验证' as \"status\"  from td_avmon_merge_rule r ";
//		String thresholdSql = "select 'FZ_'||id as \"id\", 'KPI告警阈值' as \"module\",alarm_level as \"grade\"," +
//				"content as \"content\",kpi as \"kpi\",mo as \"mo\",'未验证' as \"status\" " +
//				" from TD_AVMON_KPI_THRESHOLD ";
		String filterSql = "select 'GL_'||id as \"id\", '告警过滤规则' as \"module\",content as \"content\",grade as \"grade\",kpi as \"kpi\",mo as \"mo\",'未验证' as \"status\" "+ 
						" from td_avmon_filter_rule r ";
		
		String upgradeSql = "select 'SJ_'||id as \"id\", '告警升级规则' as \"module\"," +
					"content as \"content\",grade as \"grade\",kpi as \"kpi\",mo as \"mo\" ,'未验证' as \"status\"" +
					" from td_avmon_upgrade_rule r ";
		
		String translateSql = "select 'FY_'||id as \"id\", '告警翻译规则' as \"module\",content as \"content\",mo as \"mo\",'NA' as \"kpi\",'NA' as \"grade\" ,'未验证' as \"status\" "+ 
					" from td_avmon_translate_rule r ";
		
		String autoCloseSql = "select 'XC_'||id as \"id\", '告警自动清除设置' as \"module\",content as \"content\",grade as \"grade\",kpi as \"kpi\",mo as \"mo\" ,'未验证' as \"status\" "+ 
					" from td_avmon_auto_close_rule r ";
		
		RULELIST = jdbcTemplate.queryForList(mergeRuleSql);
//		tempList = jdbcTemplate.queryForList(thresholdSql);
//		RULELIST.addAll(tempList);
		tempList = jdbcTemplate.queryForList(filterSql);
		RULELIST.addAll(tempList);
		tempList = jdbcTemplate.queryForList(upgradeSql);
		RULELIST.addAll(tempList);
		tempList = jdbcTemplate.queryForList(translateSql);
		RULELIST.addAll(tempList);
		tempList = jdbcTemplate.queryForList(autoCloseSql);
		RULELIST.addAll(tempList);
	}
	
	/**
	 * 正则表达式验证
	 * @return
	 */
	private boolean checkReg(HashMap<String, Object> map) {

		boolean result = true;
		String regStr = StringUtil.EMPTY;
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			try {
				// if("id".equals(entry.getKey())||"kpi".equals(entry.getKey())){
				// continue;
				// }
				if(null != regStr){
					regStr = entry.getValue().toString();
					if ("*".equals(regStr)) {
						regStr = regStr.replace("*", ".*");
					}
					Pattern.compile(regStr);
				}else{
					return false;
				}
			} catch (Exception e) {
				result = false;
			}
		}
		return result;
	}
	// add by mark 2013-12-3 end
    
    @RequestMapping("saveUpdateAlarmFilterRuleInfo")
	public String saveUpdateAlarmFilterRuleInfo(
			HttpServletResponse response,
			HttpServletRequest request,
			@ModelAttribute TdAvmonFilterRuleBean entity){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String updateSql = "";
						
			if(entity.getId()!=null){//更新数据
				updateSql = CreateSqlTools.getUpdateSql(entity);
			}else{//保存新数据
				updateSql = CreateSqlTools.getInsertSql(entity);
			}
			
			configService.execute(updateSql);
			data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" saveUpdateAlarmFilterRuleInfo",e);
			data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	@RequestMapping("deleteAlarmFilterRuleByIds")
	public String deleteAlarmFilterRuleByIds(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String ids = request.getParameter("ids");
			
			String sql = "delete from td_avmon_filter_rule where id in(" + ids + ")";
			configService.execute(sql);
			data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" deleteAlarmFilterRuleByIds",e);
			data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	//Translate///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping("alarmTranslateRuleIndex")
    public String findAlarmTranslateRuleGridInfo(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String sortBy = request.getParameter("sort");
			
			String baseSql = "select id as \"id\",content as \"content\",mo as \"mo\",translated_content as \"translated_content\""+ 
					" from td_avmon_translate_rule r ";

			String root = getGridRoot(baseSql,sortBy,request.getParameter("limit"),request.getParameter("start"));
			
			String countSql = "select count(1) from td_avmon_translate_rule";
			int total = jdbcTemplate.queryForInt(countSql);

			json = "{total:" + total +",root:" + root + "}";
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findAlarmTranslateRuleGridInfo",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
    
    @RequestMapping("saveUpdateAlarmTranslateRuleInfo")
	public String saveUpdateAlarmTranslateRuleInfo(
			HttpServletResponse response,
			HttpServletRequest request,
			@ModelAttribute TdAvmonTranslateRuleBean entity){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String updateSql = "";
						
			if(entity.getId()!=null){//更新数据
				updateSql = CreateSqlTools.getUpdateSql(entity);
			}else{//保存新数据
				updateSql = CreateSqlTools.getInsertSql(entity);
			}
			
			configService.execute(updateSql);
			data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" saveUpdateAlarmTranslateRuleInfo",e);
			data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	@RequestMapping("deleteAlarmTranslateRuleByIds")
	public String deleteAlarmTranslateRuleByIds(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String ids = request.getParameter("ids");
			
			String sql = "delete from td_avmon_translate_rule where id in(" + ids + ")";
			configService.execute(sql);
			data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" deleteAlarmTranslateRuleByIds",e);
			data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	//AutoClose///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping("alarmAutoCloseRuleIndex")
    public String findAlarmAutoCloseRuleGridInfo(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String sortBy = request.getParameter("sort");
			
			String baseSql = "select id as \"id\",content as \"content\",grade as \"grade\",kpi as \"kpi\",mo as \"mo\",timeout as \"timeout\""+ 
					" from td_avmon_auto_close_rule r ";

			String root = getGridRoot(baseSql,sortBy,request.getParameter("limit"),request.getParameter("start"));
			
			String countSql = "select count(1) from td_avmon_auto_close_rule";
			int total = jdbcTemplate.queryForInt(countSql);

			json = "{total:" + total +",root:" + root + "}";
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findAlarmAutoCloseRuleGridInfo",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
    
    @RequestMapping("saveUpdateAlarmAutoCloseRuleInfo")
	public String saveUpdateAlarmAutoCloseRuleInfo(
			HttpServletResponse response,
			HttpServletRequest request,
			@ModelAttribute TdAvmonAutoCloseRuleBean entity){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String updateSql = "";
						
			if(entity.getId()!=null){//更新数据
				updateSql = CreateSqlTools.getUpdateSql(entity);
			}else{//保存新数据
				updateSql = CreateSqlTools.getInsertSql(entity);
			}
			
			configService.execute(updateSql);
			data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" saveUpdateAlarmAutoCloseRuleInfo",e);
			data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	@RequestMapping("deleteAlarmAutoCloseRuleByIds")
	public String deleteAlarmAutoCloseRuleByIds(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String ids = request.getParameter("ids");
			
			String sql = "delete from td_avmon_auto_close_rule where id in(" + ids + ")";
			configService.execute(sql);
			data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" deleteAlarmAutoCloseRuleByIds",e);
			data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	//kpiThreshold/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping("kpiThresholdIndex")
    public String findKpiThresholdGridInfo(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String sortBy = request.getParameter("sort");
			
			String baseSql = "select r.id as \"id\",accumulate_count as \"accumulate_count\",alarm_level as \"alarm_level\"," +
					"check_optr as \"check_optr\",content as \"content\",kpi as \"kpi\",mo as \"mo\"," +
					"amp_instance as \"monitor_instance\",threshold as\"threshold\",i.caption as \"kpiName\" " +
					" from TD_AVMON_KPI_THRESHOLD r " +
					"left join td_avmon_kpi_info i " +
					"on r.kpi=i.kpi_code";
			
			String root = getGridRoot(baseSql,sortBy,request.getParameter("limit"),request.getParameter("start"));
			
			String countSql = "select count(1) from TD_AVMON_KPI_THRESHOLD";
			int total = jdbcTemplate.queryForInt(countSql);

			json = "{total:" + total +",root:" + root + "}";
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findKpiThresholdGridInfo",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
    
    @RequestMapping("saveUpdateKpiThresholdInfo")
	public String saveUpdateKpiThresholdInfo(
			HttpServletResponse response,
			HttpServletRequest request,
			@ModelAttribute TdAvmonKpiThresholdBean entity){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String updateSql = "";
						
			if(entity.getId()!=null){//更新数据
				updateSql = CreateSqlTools.getUpdateSql(entity);
			}else{//保存新数据
				updateSql = CreateSqlTools.getInsertSql(entity);
			}
			
			configService.execute(updateSql);
			data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" saveUpdateKpiThresholdInfo",e);
			data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	@RequestMapping("deleteKpiThresholdByIds")
	public String deleteKpiThresholdByIds(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String ids = request.getParameter("ids");
			
			String sql = "delete from TD_AVMON_KPI_THRESHOLD where id in(" + ids + ")";
			configService.execute(sql);
			data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" deleteKpiThresholdByIds",e);
			data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	//kpi chart//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//kpi history left kpi list
	@RequestMapping("kpiList")
    public String kpiList(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String moId = request.getParameter("moId");
			moId = moId.replace(",", "','");
			
			boolean flag = true;
			String iloSql = "select agent_id as agentId from td_avmon_amp_ilo_host where mo_id in ('"+moId+"')";
			String vmSql =  "select agent_id as agentId from TD_AVMON_AMP_VM_HOST where mo_id in ('"+moId+"')";
			String baseSql = "select distinct b.amp_inst_id||'/'||c.kpi_code as \"kpi\",b.caption||'/'||c.caption as \"displayKpi\" " +
					"from td_avmon_amp_kpi a " +
					"left join td_avmon_amp_inst b on a.amp_id=b.amp_id " +
					"left join td_avmon_kpi_info c on a.kpi_code=c.kpi_code " +
					"where b.agent_id in ('" + moId + "') and c.datatype=0 order by \"kpi\" desc ";
			
			List<Map<String,String>> list = jdbcTemplate.queryForList(iloSql);
			
			if(list.size() > 0){
				moId = list.get(0).get("agentId");
				flag = false;
				baseSql = "select distinct b.amp_inst_id||'/'||c.kpi_code as \"kpi\",b.caption||'/'||c.caption as \"displayKpi\" " +
						"from td_avmon_amp_kpi a " +
						"left join td_avmon_amp_inst b on a.amp_id=b.amp_id " +
						"left join td_avmon_kpi_info c on a.kpi_code=c.kpi_code " +
						"where b.agent_id in ('" + moId + "') and  c.datatype=0 and b.amp_inst_id = 'amp_ilo' order by \"kpi\" desc "; 
			}
			
			//查询是否是VMHOST
			if(flag){
				list = jdbcTemplate.queryForList(vmSql);
				if(list.size() > 0){
					moId = list.get(0).get("agentId");
					flag = false;
					baseSql = "select distinct b.amp_inst_id||'/'||c.kpi_code as \"kpi\",b.caption||'/'||c.caption as \"displayKpi\" " +
							"from td_avmon_amp_kpi a " +
							"left join td_avmon_amp_inst b on a.amp_id=b.amp_id " +
							"left join td_avmon_kpi_info c on a.kpi_code=c.kpi_code " +
							"where b.agent_id in ('" + moId + "') and  c.datatype=0 and b.amp_inst_id = 'amp_vcenter' order by \"kpi\" desc ";
				}
			}
			
			List<Map<String, Object>> finalList = jdbcTemplate.queryForList(baseSql);
			
			JSONArray jsonArray = JSONArray.fromObject(finalList);
			json = jsonArray.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" kpiList",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
	
	/**
	@RequestMapping("kpiList")
    public String kpiList(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String moId = request.getParameter("moId");
			//moId = "bogon.192.168.220.131";
			
			String baseSql = "select distinct " + 
				"case when path='/' or path is null then " + 
				"AMP_INST_ID||'/'||kpi_id " + 
				"else  " + 
				"AMP_INST_ID||'/'||kpi_id||'/'||path " + 
				"end " + 
				"as \"kpi\" " + 
				"from tf_avmon_kpi_value where mo_id='" + moId + "' order by \"kpi\"";
			
			List<Map<String, Object>> list = jdbcTemplate.queryForList(baseSql);
			
			JSONArray jsonArray = JSONArray.fromObject(list);
			json = jsonArray.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" kpiList",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
	*/
	@RequestMapping("kpiChartData")
    public String kpiChartData(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String kpis = request.getParameter("kpis");
			String moId = request.getParameter("moId");
			
			String startTime = request.getParameter("startTime") + ":00";//":00:00";
			String endTime = request.getParameter("endTime") + ":00";//":00:00";
			
			//moId = "bogon.192.168.220.131";
			
			String kpiArr[] = kpis.split(",");
			String baseSql = "select to_char(kpi_time+1/24,'YYYY-MM-DD hh24') as \"time\", trunc(avg(num_value),2) as \"" + kpiArr[0] + "_avg\"," +
					" trunc(max(num_value),2) as \"" + kpiArr[0] + "_max\", trunc(min(num_value),2) as \"" + kpiArr[0] + "_min\" " +
					" from TF_AVMON_KPI_VALUE " +
					" where mo_id='" + moId + "' and " +
					" kpi_time>to_date('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and kpi_time<=to_date('" + endTime + "','YYYY-MM-DD hh24:mi:ss') ";
			
			// PostgreSQL syntax
			if(DBUtils.isPostgreSql()) {
				baseSql = "select to_char(kpi_time + interval '1 hour','YYYY-MM-DD hh24') as \"time\", trunc(avg(num_value),2) as \"" + kpiArr[0] + "_avg\"," +
						" trunc(max(num_value),2) as \"" + kpiArr[0] + "_max\", trunc(min(num_value),2) as \"" + kpiArr[0] + "_min\" " +
						" from TF_AVMON_KPI_VALUE " +
						" where mo_id='" + moId + "' and " +
						" kpi_time>to_date('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and kpi_time<=to_date('" + endTime + "','YYYY-MM-DD hh24:mi:ss') ";
			}
			
			if(kpiArr.length==1){
				String kpi[] = kpiArr[0].split("/");
				if(kpi.length==2){
					baseSql = baseSql + " and AMP_INST_ID='" + kpi[0] + "' and KPI_CODE='" + kpi[1] + "' and (INSTANCE='/' or INSTANCE is null or UPPER(INSTANCE)='ALL')" +
							" group by to_char(kpi_time+1/24,'YYYY-MM-DD hh24') " +
							" order by to_char(kpi_time+1/24,'YYYY-MM-DD hh24')";
				}else if(kpi.length==3){
					baseSql = baseSql + " and AMP_INST_ID='" + kpi[0] + "' and KPI_CODE='" + kpi[1] + "' and INSTANCE='" + kpi[2] + "' " +
							" group by to_char(kpi_time+1/24,'YYYY-MM-DD hh24') " +
							" order by to_char(kpi_time+1/24,'YYYY-MM-DD hh24')";
				}
				
				// PostgreSQL syntax
				if(DBUtils.isPostgreSql()) {
					if(kpi.length==2){
						baseSql = baseSql + " and AMP_INST_ID='" + kpi[0] + "' and KPI_CODE='" + kpi[1] + "' and (INSTANCE='/' or INSTANCE is null or UPPER(INSTANCE)='ALL')" +
								" group by to_char(kpi_time + interval '1 hour','YYYY-MM-DD hh24') " +
								" order by to_char(kpi_time + interval '1 hour','YYYY-MM-DD hh24')";
					}else if(kpi.length==3){
						baseSql = baseSql + " and AMP_INST_ID='" + kpi[0] + "' and KPI_CODE='" + kpi[1] + "' and INSTANCE='" + kpi[2] + "' " +
								" group by to_char(kpi_time + interval '1 hour','YYYY-MM-DD hh24') " +
								" order by to_char(kpi_time + interval '1 hour','YYYY-MM-DD hh24')";
					}
				}
				
				List<Map<String, Object>> list = jdbcTemplate.queryForList(baseSql);
				
				//查询告警阀值
				String alarmThresholdSql = "select threshold as \"threshold\" from td_avmon_kpi_threshold where kpi='" + kpi[1] + "' " +
						"and mo='" + moId + "' and AMP_instance='" + kpi[0] + "' and alarm_Level=4";
				List<Map<String, Object>> thresholdList = jdbcTemplate.queryForList(alarmThresholdSql);
				if(thresholdList.size()>0){
					for(Map<String,Object> m : list){
						if(thresholdList.get(0).get("threshold")!=null){
							m.put("threshold", Long.valueOf(thresholdList.get(0).get("threshold").toString()));
						}
//						else{
//							m.put("threshold", 0);
//						}
					}
				}else{
					alarmThresholdSql = "select threshold as \"threshold\" from td_avmon_kpi_threshold where kpi='" + kpi[1] + "' " +
							"and mo='*' and AMP_instance='*' and alarm_Level=4";
					thresholdList = jdbcTemplate.queryForList(alarmThresholdSql);
					if(thresholdList.size()>0){
						for(Map<String,Object> m : list){
							//m.put("threshold", thresholdList.get(0).get("threshold"));
							if(thresholdList.get(0).get("threshold")!=null){
								m.put("threshold", Long.valueOf(thresholdList.get(0).get("threshold").toString()));
							}
//							else{
//								m.put("threshold", 0);
//							}
						}
					}
				}
				
				JSONArray jsonArray = JSONArray.fromObject(list);
				json = jsonArray.toString();
			}else if(kpiArr.length>1){
				
//				String dataSql = "select to_char(d.\"time\",'YYYY-MM-DD hh24') as \"time\",\"kpi\",num_value as \"kpiValue\" from( " + 
//						"select kpi_time as \"time\", " + 
//						"case when INSTANCE='/' or INSTANCE is null then " + 
//						"AMP_INST_ID||'/'||KPI_CODE  " + 
//						"else " + 
//						"AMP_INST_ID||'/'||KPI_CODE||'/'||INSTANCE " + 
//						"end " + 
//						"as \"kpi\", " + 
//						"num_value " + 
//						"from TF_AVMON_KPI_HOURLY " + 
//						"where mo_id='" + moId + "' and " +
//						"kpi_time>=to_date('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and kpi_time<=to_date('" + endTime + "','YYYY-MM-DD hh24:mi:ss') " +  
//						")d " +
//						"where d.\"kpi\" in ('" + kpis.replace(",", "','") + "') " + 
//						"order by to_char(d.\"time\",'YYYY-MM-DD hh24')";
//						
//				List<Map<String, Object>> list = jdbcTemplate.queryForList(dataSql);
//				
//				json = "[";
//				for(Map<String,Object> kpiMap : list){
//					String kpiTime = kpiMap.get("time").toString();
//					json = json + "{time:'" + kpiTime + "','" + kpiMap.get("kpi") + "':" + kpiMap.get("kpiValue") + ",";
//				}
//				json = json.substring(0,json.length()-1) + "},";
//				json = json.substring(0,json.length()-1) + "]";
				
				String timeSql = "select to_char(d.\"time\",'YYYY-MM-DD hh24') as \"time\" from( " + 
						"select kpi_time as \"time\", " + 
						"case when INSTANCE='/' or INSTANCE is null then " + 
						"AMP_INST_ID||'/'||KPI_CODE  " + 
						"else " + 
						"AMP_INST_ID||'/'||KPI_CODE||'/'||INSTANCE " + 
						"end " + 
						"as \"kpi\", " + 
						"num_value " + 
						"from TF_AVMON_KPI_VALUE_HOURLY " + 
						"where mo_id='" + moId + "' and " +
						"kpi_time>to_date('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and kpi_time<=to_date('" + endTime + "','YYYY-MM-DD hh24:mi:ss') " +  
						")d " +
						"where d.\"kpi\" in ('" + kpis.replace(",", "','") + "') " + 
						"group by to_char(d.\"time\",'YYYY-MM-DD hh24') " + 
						"order by to_char(d.\"time\",'YYYY-MM-DD hh24')";
						
				List<Map<String, Object>> timeList = jdbcTemplate.queryForList(timeSql);
				
//				String kpiSql = "select to_char(d.\"time\"+1/24,'YYYY-MM-DD hh24') as \"time\",d.\"kpi\" as \"kpi\",trunc(avg(d.num_value),2) as \"kpiValue\" from( " + 
//						"select time as \"time\", " + 
//						"case when INSTANCE='/' or INSTANCE is null then " + 
//						"AMP_INST_ID||'/'||KPI_CODE  " + 
//						"else " + 
//						"AMP_INST_ID||'/'||KPI_CODE||'/'||INSTANCE " + 
//						"end " + 
//						"as \"kpi\", " + 
//						"num_value " + 
//						"from TF_AVMON_KPI_VALUE " + 
//						"where mo_id='" + moId + "' and " +
//						"time>to_date('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and time<=to_date('" + endTime + "','YYYY-MM-DD hh24:mi:ss') " + 
//						")d " +
//						"where d.\"kpi\" in ('" + kpis.replace(",", "','") + "') " + 
//						"group by d.\"kpi\",to_char(d.\"time\"+1/24,'YYYY-MM-DD hh24') " + 
//						"order by to_char(d.\"time\"+1/24,'YYYY-MM-DD hh24')";
				
				String kpiSql = "select to_char(d.\"time\",'YYYY-MM-DD hh24') as \"time\",\"kpi\",num_value as \"kpiValue\" from( " + 
						"select kpi_time as \"time\", " + 
						"case when INSTANCE='/' or INSTANCE is null then " + 
						"AMP_INST_ID||'/'||KPI_CODE  " + 
						"else " + 
						"AMP_INST_ID||'/'||KPI_CODE||'/'||INSTANCE " + 
						"end " + 
						"as \"kpi\", " + 
						"num_value " + 
						"from TF_AVMON_KPI_VALUE_HOURLY " + 
						"where mo_id='" + moId + "' and " +
						"kpi_time>=to_date('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and kpi_time<=to_date('" + endTime + "','YYYY-MM-DD hh24:mi:ss') " +  
						")d " +
						"where d.\"kpi\" in ('" + kpis.replace(",", "','") + "') " + 
						"order by to_char(d.\"time\",'YYYY-MM-DD hh24')";
				
				List<Map<String, Object>> kpiList = jdbcTemplate.queryForList(kpiSql);	
				json = "[";
				for(Map<String,Object> map : timeList){
					String time = map.get("time").toString();
					json = json + "{time:'" + time + "',";
					for(Map<String,Object> kpiMap : kpiList){
						String kpiTime = kpiMap.get("time").toString();
						if(kpiTime.equals(time)){
							json = json + "'" + kpiMap.get("kpi") + "':" + kpiMap.get("kpiValue") + ",";
						}
					}
					json = json.substring(0,json.length()-1) + "},";
				}
				json = json.substring(0,json.length()-1) + "]";
			}
			if("[".equals(json)||"]".equals(json)){
				json="[]";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" kpiChartData",e);
		}
		
		return Utils.responsePrintWrite(response,json,null);
	}
	
	@RequestMapping("kpiCompareChartData")
    public String kpiCompareChartData(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String kpi = request.getParameter("kpi");
			String moIds = request.getParameter("moIds");
			
			String startTime = request.getParameter("startTime") + ":00";//":00:00";
			String endTime = request.getParameter("endTime") + ":00";//":00:00";
			
			String timeSql = "select to_char(d.\"time\",'YYYY-MM-DD hh24') as \"time\" from( " + 
					"select kpi_time as \"time\", " + 
					"case when INSTANCE='/' or INSTANCE is null then " + 
					"AMP_INST_ID||'/'||KPI_CODE  " + 
					"else " + 
					"AMP_INST_ID||'/'||KPI_CODE||'/'||INSTANCE " + 
					"end " + 
					"as \"kpi\", " + 
					"num_value " + 
					"from TF_AVMON_KPI_HOURLY " + 
					"where mo_id in ('" + moIds.replace(",", "','") + "') and " +
					"kpi_time>=to_date('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and kpi_time<=to_date('" + endTime + "','YYYY-MM-DD hh24:mi:ss') " +  
					")d " +
					"where d.\"kpi\"='" + kpi + "'" + 
					"group by to_char(d.\"time\",'YYYY-MM-DD hh24') " + 
					"order by to_char(d.\"time\",'YYYY-MM-DD hh24')";
					
			List<Map<String, Object>> timeList = jdbcTemplate.queryForList(timeSql);
			
//			String kpiSql = "select to_char(d.\"time\"+1/24,'YYYY-MM-DD hh24') as \"time\",d.\"kpi\" as \"kpi\",d.mo_id as \"mo_id\",trunc(avg(d.num_value),2) as \"kpiValue\" from( " + 
//					"select time as \"time\", mo_id," + 
//					"case when INSTANCE='/' or INSTANCE is null then " + 
//					"AMP_INST_ID||'/'||KPI_CODE  " + 
//					"else " + 
//					"AMP_INST_ID||'/'||KPI_CODE||'/'||INSTANCE " + 
//					"end " + 
//					"as \"kpi\", " + 
//					"num_value " + 
//					"from TF_AVMON_KPI_VALUE " + 
//					"where mo_id in ('" + moIds.replace(",", "','") + "') and " +
//					"time>to_date('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and time<=to_date('" + endTime + "','YYYY-MM-DD hh24:mi:ss') " + 
//					")d " +
//					"where d.\"kpi\"='" + kpi + "'" + 
//					"group by d.\"kpi\",d.mo_id,to_char(d.\"time\"+1/24,'YYYY-MM-DD hh24') " + 
//					"order by to_char(d.\"time\"+1/24,'YYYY-MM-DD hh24')";
			String kpiSql = "select to_char(d.\"time\",'YYYY-MM-DD hh24') as \"time\",\"kpi\",d.mo_id as \"mo_id\",num_value as \"kpiValue\" from( " + 
					"select kpi_time as \"time\", " + 
					"case when INSTANCE='/' or INSTANCE is null then " + 
					"AMP_INST_ID||'/'||KPI_CODE  " + 
					"else " + 
					"AMP_INST_ID||'/'||KPI_CODE||'/'||INSTANCE " + 
					"end " + 
					"as \"kpi\", " + 
					"num_value,mo_id " + 
					"from TF_AVMON_KPI_HOURLY " + 
					"where mo_id in ('" + moIds.replace(",", "','") + "') and " +
					"kpi_time>=to_date('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and kpi_time<=to_date('" + endTime + "','YYYY-MM-DD hh24:mi:ss') " +  
					")d " +
					"where d.\"kpi\"='" + kpi + "'" + 
					"order by to_char(d.\"time\",'YYYY-MM-DD hh24')";
					
			List<Map<String, Object>> kpiList = jdbcTemplate.queryForList(kpiSql);
			String m[] = moIds.split(",");
			int lines = m.length;
			
			json = "[";
			for(Map<String,Object> map : timeList){
				String time = map.get("time").toString();
				json = json + "{x:'" + time + "',";
				for(Map<String,Object> kpiMap : kpiList){
					String kpiTime = kpiMap.get("time").toString();
					String moId = kpiMap.get("mo_id").toString();
					if(lines==1){
						if(kpiTime.equals(time)&&moId.equalsIgnoreCase(m[0])){
							json = json + "'Host1':" + kpiMap.get("kpiValue") + ",";
						}
					}
					if(lines==2){
						if(kpiTime.equals(time)&&moId.equalsIgnoreCase(m[0])){
							json = json + "'Host1':" + kpiMap.get("kpiValue") + ",";
						}else if(kpiTime.equals(time)&&moId.equalsIgnoreCase(m[1])){
							json = json + "'Host2':" + kpiMap.get("kpiValue") + ",";
						}
					}
					if(lines==3){
						if(kpiTime.equals(time)&&moId.equalsIgnoreCase(m[0])){
							json = json + "'Host1':" + kpiMap.get("kpiValue") + ",";
						}else if(kpiTime.equals(time)&&moId.equalsIgnoreCase(m[1])){
							json = json + "'Host2':" + kpiMap.get("kpiValue") + ",";
						}else if(kpiTime.equals(time)&&moId.equalsIgnoreCase(m[2])){
							json = json + "'Host3':" + kpiMap.get("kpiValue") + ",";
						}
					}
					if(lines==4){
						if(kpiTime.equals(time)&&moId.equalsIgnoreCase(m[0])){
							json = json + "'Host1':" + kpiMap.get("kpiValue") + ",";
						}else if(kpiTime.equals(time)&&moId.equalsIgnoreCase(m[1])){
							json = json + "'Host2':" + kpiMap.get("kpiValue") + ",";
						}else if(kpiTime.equals(time)&&moId.equalsIgnoreCase(m[2])){
							json = json + "'Host3':" + kpiMap.get("kpiValue") + ",";
						}else if(kpiTime.equals(time)&&moId.equalsIgnoreCase(m[3])){
							json = json + "'Host4':" + kpiMap.get("kpiValue") + ",";
						}
					}
					if(lines==5){
						if(kpiTime.equals(time)&&moId.equalsIgnoreCase(m[0])){
							json = json + "'Host1':" + kpiMap.get("kpiValue") + ",";
						}else if(kpiTime.equals(time)&&moId.equalsIgnoreCase(m[1])){
							json = json + "'Host2':" + kpiMap.get("kpiValue") + ",";
						}else if(kpiTime.equals(time)&&moId.equalsIgnoreCase(m[2])){
							json = json + "'Host3':" + kpiMap.get("kpiValue") + ",";
						}else if(kpiTime.equals(time)&&moId.equalsIgnoreCase(m[3])){
							json = json + "'Host4':" + kpiMap.get("kpiValue") + ",";
						}else if(kpiTime.equals(time)&&moId.equalsIgnoreCase(m[4])){
							json = json + "'Host5':" + kpiMap.get("kpiValue") + ",";
						}
					}
				}
				json = json.substring(0,json.length()-1) + "},";
			}
			json = json.substring(0,json.length()-1) + "]";
			
			
			
			
			//json = "[{x:'a',y1:1,y2:3,y3:4,y4:2,y4:1},{x:'b',y1:3,y2:1,y3:2,y4:4,y4:5},{x:'c',y1:7,y2:4,y3:6,y4:2,y4:5}]";
			if("[".equals(json)||"]".equals(json)){
				json="[]";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" kpiCompareChartData",e);
		}
		
		return Utils.responsePrintWrite(response,json,null);
	}
	
	//report data source/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping("reportDatasourceIndex")
    public String findReportDatasourceGridInfo(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String sortBy = request.getParameter("sort");
			if(sortBy!=null){
				sortBy = sortBy.replace("user", "user_name");
			}
//			String baseSql = "select id as \"id\",title as \"title\",driver as \"driver\"," +
//					"url as \"url\",\"USER\" as \"user\",password as \"password\",to_char(updated_dt,'yyyy-mm-dd hh24:mi:ss') as \"updated_dt\" " +
//					" from IREPORT_DATASOURCE ";
			
			String baseSql = "select id as \"id\",title as \"title\",driver as \"driver\"," +
					"url as \"url\",user_name as \"user\",password as \"password\",to_char(updated_dt,'yyyy-mm-dd hh24:mi:ss') as \"updated_dt\" " +
					" from IREPORT_DATASOURCE r ";
			
			if("combox".equalsIgnoreCase(request.getParameter("dType"))){
				List<Map<String, Object>> list = jdbcTemplate.queryForList(baseSql+" order by id");
				
				JSONArray jsonArray = JSONArray.fromObject(list);
				json = jsonArray.toString();
				
				return Utils.responsePrintWrite(response,json.toString(),null);
			}
			
			String root = getGridRoot(baseSql,sortBy,request.getParameter("limit"),request.getParameter("start"));
			
			String countSql = "select count(1) from IREPORT_DATASOURCE";
			int total = jdbcTemplate.queryForInt(countSql);

			json = "{total:" + total +",root:" + root + "}";
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findReportDatasourceGridInfo",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
    
    @RequestMapping("saveReportDatasourceInfo")
	public String saveReportDatasourceInfo(
			HttpServletResponse response,
			HttpServletRequest request,
			@ModelAttribute IreportDatasourceBean entity){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String updateSql = "";
						
			if(entity.getId()!=null){//更新数据
				updateSql = CreateSqlTools.getUpdateSql(entity);
			}else{//保存新数据
				updateSql = CreateSqlTools.getInsertSql(entity);
			}
			
			configService.execute(updateSql);
			
			data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" saveReportDatasourceInfo",e);
			data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	@RequestMapping("deleteReportDatasourceByIds")
	public String deleteReportDatasourceByIds(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String ids = request.getParameter("ids");
			
			//被report使用的datasource不允许被删除
			String countReport = "select count(1) from IREPORT_MGT where DATASOURCE_ID in (" + ids + ")";
			
			int reportCount = jdbcTemplate.queryForInt(countReport);
			if(reportCount>0){
				data="{success:false,msg:'"+bundle.getString("deleteFailDataSourceAlreadyUsed")+"'}";
				return Utils.responsePrintWrite(response,data,null);
			}
			
			String sql = "delete from IREPORT_DATASOURCE where id in(" + ids + ")";
			configService.execute(sql);
			data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" deleteReportDatasourceByIds",e);
			data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	//report management/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping("reportTemplateIndex")
    public String findReportTemplateGridInfo(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String sortBy = request.getParameter("sort");
			
			String baseSql = "select r.id as \"id\",r.report_id as \"report_id\",r.report_name as \"report_name\"," +
					"r.datasource_id as \"datasource_id\",r.template as \"template\",r.menu as \"menu\"," +
					"to_char(r.updated_dt,'yyyy-mm-dd hh24:mi:ss') as \"updated_dt\",d.title as \"db_title\",m.module_name as \"module_name\"," +
					"h.type as \"html_type\",to_char(h.start_time,'yyyy-mm-dd hh24:mi:ss') as \"html_start_time\"," +
					"to_char(e.start_time,'yyyy-mm-dd hh24:mi:ss') as \"email_start_time\",e.period as \"email_period\",e.email as \"email_email\"," +
					"e.host as \"email_host\" " +
					" from IREPORT_MGT r" +
					" left join ireport_datasource d on cast(r.datasource_id as int)=d.id" +
					" left join PORTAL_MODULES m on r.menu=m.module_id" +
					" left join ireport_html h on r.report_id=h.report_id" +
					" left join ireport_email e on r.report_id=e.report_id";
			
//			String root = getGridRoot(baseSql,sortBy,request.getParameter("limit"),request.getParameter("start"));
			
			if(sortBy!=null){
				JSONArray jsonArr = JSONArray.fromObject(sortBy);
				String sort = jsonArr.getJSONObject(0).get("property").toString();
				if("module_name".equalsIgnoreCase(sort)){
					baseSql = baseSql + " order by m." + sort + " " + jsonArr.getJSONObject(0).get("direction");
				}else if("db_title".equalsIgnoreCase(sort)){
					baseSql = baseSql + " order by d.title " + jsonArr.getJSONObject(0).get("direction");
				}else{
					baseSql = baseSql + " order by r." + sort + " " + jsonArr.getJSONObject(0).get("direction");
				}
				
			}else{
				baseSql = baseSql + " order by r.id desc ";
			}
			
			List<Map<String, Object>> list = jdbcTemplate.queryForList(generatPageSql(baseSql,request.getParameter("limit"),request.getParameter("start")));
			
			JSONArray jsonArray = JSONArray.fromObject(list);
			String root = jsonArray.toString();
			
			String countSql = "select count(1) from IREPORT_MGT";
			int total = jdbcTemplate.queryForInt(countSql);

			json = "{total:" + total +",root:" + root + "}";
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findReportTemplateGridInfo",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
    
    @RequestMapping("saveReportTemplateInfo")
	public String saveReportTemplateInfo(
			HttpServletResponse response,
			HttpServletRequest request,
			@ModelAttribute IreportMgtBean entity){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String reportId = entity.getReport_id();
			String updateSql = "";
			entity.setTemplate(Config.getInstance().getReportTemplatePath() + reportId + File.separator);
			entity.setUpdated_dt("sysdate");
			if(entity.getId()!=null){//更新数据
				updateSql = CreateSqlTools.getUpdateSql(entity);
			}else{//保存新数据
				//先判断reportId唯一性
				String uniqueReportIdSql = "select count(1) from ireport_mgt where report_id='" + reportId + "'";
				int reportCount = jdbcTemplate.queryForInt(uniqueReportIdSql);
				if(reportCount>0){
					data="{success:false,msg:'"+bundle.getString("reportNumberExisted")+"'}";
					return Utils.responsePrintWrite(response,data,null);
				}else{
					updateSql = CreateSqlTools.getInsertSql(entity);					
				}
			}
			
			configService.saveReportTemplateInfo(updateSql,entity,request);
			
			data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" saveReportTemplateInfo",e);
			data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	@RequestMapping("deleteReportTemplateByIds")
	public String deleteReportTemplateByIds(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String ids = "'" +request.getParameter("ids").replace(",", "','") + "'";
						
			String sqlMgt = "delete from IREPORT_MGT where report_id in(" + ids + ")";
			String sqlHtml = "delete from ireport_html where report_id in(" + ids + ")";
			String sqlEmail = "delete from ireport_email where report_id in(" + ids + ")";
			
			
			String moduleIds = "'report_" +request.getParameter("ids").replace(",", "','report_") + "'";
			
			String sqlRoleModule = "delete from portal_roles_portal_modules where portal_module_id in (select id from portal_modules where module_id in (" + moduleIds + "))";
			String sqlModule = "delete from portal_modules where module_id in (" + moduleIds + ")";
			
			String[] sql = {sqlMgt,sqlHtml,sqlEmail,sqlRoleModule,sqlModule};
			configService.deleteReportTemplateByIds(sql);
			data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" deleteReportTemplateByIds",e);
			data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	//notifyRule
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping("notifyRuleIndex")
    public String findNotifyRuleGridInfo(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String sortBy = request.getParameter("sort");
			String userId = Utils.getCurrentUserId(request);
			String baseSql = "select id as \"id\",user_id as \"user_id\",r.view_id as \"view_id\"," +
					"alarm_level1 as \"alarm_level1\",alarm_level2 as \"alarm_level2\",alarm_level3 as \"alarm_level3\"," +
					"alarm_level4 as \"alarm_level4\",alarm_level5 as \"alarm_level5\"," +
					"email_flag as \"email_flag\",sms_flag as \"sms_flag\",max_sms_per_day as \"max_sms_per_day\",enable_flag as \"enable_flag\"," +
					"sms_recv_time as \"sms_recv_time\",to_char(last_update_time,'yyyy-MM-dd HH24:mi:ss') as \"last_update_time\"," +
					"case when v.view_name is null then r.view_id else v.view_name end as \"view_name\" " +
					"from td_avmon_notify_rule r " +
					"left join td_avmon_view v on r.view_id=v.view_id";
			if(!"admin".equalsIgnoreCase(userId)){//除admin外，用户只能查看自己的订阅信息
				baseSql = baseSql + " where r.user_id='" + userId + "'";
			}
			String root = getGridRoot(baseSql,sortBy,request.getParameter("limit"),request.getParameter("start"));
			
			String countSql = "select count(1) from td_avmon_notify_rule";
			int total = jdbcTemplate.queryForInt(countSql);

			json = "{total:" + total +",root:" + root + "}";
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findNotifyRuleGridInfo",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
    
    @RequestMapping("saveUpdateNotifyRuleInfo")
	public String saveUpdateNotifyRuleInfo(
			HttpServletResponse response,
			HttpServletRequest request//,
			//@ModelAttribute TdAvmonNotifyRuleBean entity
			){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			TdAvmonNotifyRuleBean entity = new TdAvmonNotifyRuleBean();
			String updateSql = "";
			String viewId = request.getParameter("view_id");
			if(viewId.split("\\*").length==2){
				viewId = viewId.split("\\*")[1];
			}
			entity.setView_id(viewId);
			
			//告警级别
			if("on".equalsIgnoreCase(request.getParameter("alarm_level1"))){
				entity.setAlarm_level1(1L);
			}else{
				entity.setAlarm_level1(0L);
			}
			if("on".equalsIgnoreCase(request.getParameter("alarm_level2"))){
				entity.setAlarm_level2(1L);
			}else{
				entity.setAlarm_level2(0L);
			}
			if("on".equalsIgnoreCase(request.getParameter("alarm_level3"))){
				entity.setAlarm_level3(1L);
			}else{
				entity.setAlarm_level3(0L);
			}
			if("on".equalsIgnoreCase(request.getParameter("alarm_level4"))){
				entity.setAlarm_level4(1L);
			}else{
				entity.setAlarm_level4(0L);
			}
			if("on".equalsIgnoreCase(request.getParameter("alarm_level5"))){
				entity.setAlarm_level5(1L);
			}else{
				entity.setAlarm_level5(0L);
			}
			//接受方式
			if("on".equalsIgnoreCase(request.getParameter("email_flag"))){
				entity.setEmail_flag(1L);
			}else{
				entity.setEmail_flag(0L);
			}
			if("on".equalsIgnoreCase(request.getParameter("sms_flag"))){
				entity.setSms_flag(1L);
			}else{
				entity.setSms_flag(0L);
			}
			
			entity.setMax_sms_per_day(Long.valueOf(request.getParameter("max_sms_per_day")));
			
			if("on".equalsIgnoreCase(request.getParameter("enable_flag"))){
				entity.setEnable_flag(1L);
			}else{
				entity.setEnable_flag(0L);
			}
			
			String smsRecvTime = request.getParameter("sms_recv_time");
			if("全部时间".equals(smsRecvTime)){
				smsRecvTime = "0";
			}else if("工作时间".equals(smsRecvTime)){
				smsRecvTime = "0";
			}
			
			entity.setSms_recv_time(Long.valueOf(request.getParameter("sms_recv_time")));
			
			entity.setLast_update_time(MyFunc.getCurrentTimeString("yyyy-MM-dd HH:mm:ss"));
			if(request.getParameter("id")!=null&&request.getParameter("id").length()>0){//更新数据
				entity.setId(Long.valueOf(request.getParameter("id")));
				entity.setUser_id(request.getParameter("user_id"));
				updateSql = CreateSqlTools.getUpdateSql(entity);
			}else{//保存新数据
				String userId = Utils.getCurrentUserId(request);
				entity.setUser_id(userId);
				updateSql = CreateSqlTools.getInsertSql(entity);
			}
			
			configService.execute(updateSql);
			data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" saveUpdateNotifyRuleInfo",e);
			data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	@RequestMapping("deleteNotifyRuleByIds")
	public String deleteNotifyRuleByIds(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String ids = request.getParameter("ids");
			
			String sql = "delete from td_avmon_notify_rule where id in(" + ids + ")";
			configService.execute(sql);
			data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" deleteNotifyRuleByIds",e);
			data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	@RequestMapping("findViewById")
    public String findViewById(
			HttpServletResponse response,
			HttpServletRequest request){
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		String viewId = request.getParameter("viewId");
		String json = "{res:'failed',msg:'"+bundle.getString("loadViewID")+viewId+bundle.getString("informationError")+"'}";
		if("0".equals(viewId)){
			json = "{res:'success',entityName:'',msg:'"+bundle.getString("loadSuccess")+"'}";
			return Utils.responsePrintWrite(response,json,null);
		}
		
		try {
			//viewId = viewId.split("\\*")[1];
			if(viewId.startsWith("V")){
				String sql = "select view_name as \"viewName\" from td_avmon_view " +
						" where view_id=?";
				String[] args = {viewId};
				List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args);
				
				if(list.size()>0){
					json = "{res:'success',viewName:'" + list.get(0).get("viewName").toString() + "',msg:'"+bundle.getString("loadSuccess")+"'}";
				}else{
					logger.error(this.getClass().getName()+" findViewById() " + "加载视图ID为："+viewId+"信息出错!");
				}
			}else{
				json = "{res:'success',viewName:'" + viewId + "',msg:'"+bundle.getString("loadSuccess")+"'}";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findViewById",e);
		}
		
		return Utils.responsePrintWrite(response,json,null);
	}
	
	@RequestMapping("loadViewById")
    public String loadViewById(
			HttpServletResponse response,
			HttpServletRequest request){
		String viewId = request.getParameter("viewId");
		String json = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			if(viewId.indexOf("*")>-1){
				viewId = viewId.split("\\*")[1];
			}
			
			String sql = "select id as \"id\",user_id as \"user_id\"," + //r.view_id as \"view_id\"," +
					"alarm_level1 as \"alarm_level1\",alarm_level2 as \"alarm_level2\",alarm_level3 as \"alarm_level3\"," +
					"alarm_level4 as \"alarm_level4\",alarm_level5 as \"alarm_level5\"," +
					"email_flag as \"email_flag\",sms_flag as \"sms_flag\",max_sms_per_day as \"max_sms_per_day\",enable_flag as \"enable_flag\"," +
					"sms_recv_time as \"sms_recv_time\",to_char(last_update_time,'yyyy-MM-dd HH24:mi:ss') as \"last_update_time\" " +
					"from td_avmon_notify_rule r where view_id='" + viewId + "' and user_id='" + Utils.getCurrentUserId(request) + "'";
			
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			
			JSONObject jsonObject = JSONObject.fromObject(list.get(0));
			json="{success:true,msg:'"+bundle.getString("loadSuccess")+"',data:" + jsonObject.toString() + "}";
		} catch (Exception e) {
			e.printStackTrace();
			json="{success:false,msg:'"+bundle.getString("loadFail")+"'}";  
			logger.error(this.getClass().getName()+" loadViewById",e);
		}
		
		return Utils.responsePrintWrite(response,json,null);
	}
	
	//routeDevice/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping("routeDeviceIndex")
    public String findRouteDeviceGridInfo(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String sortBy = request.getParameter("sort");
			
			String baseSql = "select id as \"id\",device_name as \"device_name\",device_type as \"device_type\"," +
					"device_ip as \"device_ip\",device_desc as \"device_desc\",device_status as \"device_status\"," +
					"device_othrer1 as \"device_othrer1\",device_othrer2 as \"device_othrer2\",report_date as \"report_date\" " +
					" from TF_AVMON_ROUTE_INSPECTION_DEV r ";
			
			String root = getGridRoot(baseSql,sortBy,request.getParameter("limit"),request.getParameter("start"));
			
			String countSql = "select count(1) from TF_AVMON_ROUTE_INSPECTION_DEV";
			int total = jdbcTemplate.queryForInt(countSql);

			json = "{total:" + total +",root:" + root + "}";
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findRouteDeviceGridInfo",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
    
    @RequestMapping("saveUpdateRouteDeviceInfo")
	public String saveUpdateRouteDeviceInfo(
			HttpServletResponse response,
			HttpServletRequest request,
			@ModelAttribute TfAvmonRouteInspectionDevBean entity){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String updateSql = "";
						
			if(entity.getId()!=null){//更新数据
				updateSql = CreateSqlTools.getUpdateSql(entity);
			}else{//保存新数据
				updateSql = CreateSqlTools.getInsertSql(entity);
			}
			
			configService.execute(updateSql);
			data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" saveUpdateRouteDeviceInfo",e);
			data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	@RequestMapping("deleteRouteDeviceByIds")
	public String deleteRouteDeviceByIds(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String ids = request.getParameter("ids");
			
			String sql = "delete from TF_AVMON_ROUTE_INSPECTION_DEV where id in(" + ids + ")";
			configService.execute(sql);
			data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" deleteRouteDeviceByIds",e);
			data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	//routeKpi/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping("routeKpiIndex")
    public String findRouteKpiGridInfo(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String sortBy = request.getParameter("sort");
			
			String baseSql = "select id as \"id\",kpi_id as \"kpi_id\",kpi_name as \"kpi_name\"," +
					"kpi_type as \"kpi_type\",other1 as \"other1\",other2 as \"other2\" " +
					" from tf_avmon_route_kpi_code r ";
			
			String root = getGridRoot(baseSql,sortBy,request.getParameter("limit"),request.getParameter("start"));
			
			String countSql = "select count(1) from tf_avmon_route_kpi_code";
			int total = jdbcTemplate.queryForInt(countSql);

			json = "{total:" + total +",root:" + root + "}";
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findRouteKpiGridInfo",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
    
    @RequestMapping("saveUpdateRouteKpiInfo")
	public String saveUpdateRouteKpiInfo(
			HttpServletResponse response,
			HttpServletRequest request,
			@ModelAttribute TfAvmonRouteKpiCodeBean entity){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String updateSql = "";
						
			if(entity.getId()!=null){//更新数据
				updateSql = CreateSqlTools.getUpdateSql(entity);
			}else{//保存新数据
				updateSql = CreateSqlTools.getInsertSql(entity);
			}
			
			configService.execute(updateSql);
			data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" saveUpdateRouteKpiInfo",e);
			data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	@RequestMapping("deleteRouteKpiByIds")
	public String deleteRouteKpiByIds(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String ids = request.getParameter("ids");
			
			String sql = "delete from tf_avmon_route_kpi_code where id in(" + ids + ")";
			configService.execute(sql);
			data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" deleteRouteKpiByIds",e);
			data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	//routeKpi/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping("routeKpiThresholdIndex")
    public String findRouteKpiThresholdGridInfo(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String sortBy = request.getParameter("sort");
			
			String deviceIp = request.getParameter("deviceIp");
			String kpi = request.getParameter("kpi");
			
			String baseSql = "select r.id as \"id\",r.device_ip as \"device_ip\",r.kpi_id as \"kpi_id\"," +
					"r.kpi_threshold as \"kpi_threshold\",r.kpi_type as \"kpi_type\",r.ignore_value as \"ignore_value\",r.error_value as \"error_value\", " +
					"c.kpi_name as \"kpi_name\" " +
					" from tf_avmon_route_inspection_kpi r " +
					"left join TF_AVMON_ROUTE_KPI_CODE c " +
					"on r.kpi_id=c.kpi_id where 1=1 ";
			
			if(deviceIp!=null&&deviceIp.length()>0){
				baseSql = baseSql + " and r.device_ip like '%"+deviceIp+"%' ";
			}
			if(kpi!=null&&kpi.length()>0){
				baseSql = baseSql + " and upper(c.kpi_name) like upper('%"+kpi+"%') ";
			}
			
			String root = getGridRoot(baseSql,sortBy,request.getParameter("limit"),request.getParameter("start"));
			
			String countSql = "select count(1) from tf_avmon_route_inspection_kpi r " +
					"left join TF_AVMON_ROUTE_KPI_CODE c " +
					"on r.kpi_id=c.kpi_id where 1=1 ";
			
			if(deviceIp!=null&&deviceIp.length()>0){
				countSql = countSql + " and r.device_ip like '%"+deviceIp+"%' ";
			}
			if(kpi!=null&&kpi.length()>0){
				countSql = countSql + " and upper(c.kpi_name) like upper('%"+kpi+"%') ";			
			}
			
			int total = jdbcTemplate.queryForInt(countSql);

			json = "{total:" + total +",root:" + root + "}";
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findRouteKpiThresholdGridInfo",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
    
    @RequestMapping("saveUpdateRouteKpiThresholdInfo")
	public String saveUpdateRouteKpiThresholdInfo(
			HttpServletResponse response,
			HttpServletRequest request,
			@ModelAttribute TfAvmonRouteInspectionKpiBean entity){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String updateSql = "";
			String deviceIps = entity.getDevice_ip();
			String deviceIpArr[] = deviceIps.split(",");
			for(String deviceIp : deviceIpArr){
				entity.setDevice_ip(deviceIp);
				if(entity.getId()!=null){//更新数据
					updateSql = CreateSqlTools.getUpdateSql(entity);
				}else{//保存新数据
					updateSql = CreateSqlTools.getInsertSql(entity);
				}
				
				configService.execute(updateSql);
			}
			
			data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" saveUpdateRouteKpiThresholdInfo",e);
			data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	@RequestMapping("deleteRouteKpiThresholdByIds")
	public String deleteRouteKpiThresholdByIds(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String ids = request.getParameter("ids");
			
			String sql = "delete from tf_avmon_route_inspection_kpi where id in(" + ids + ")";
			configService.execute(sql);
			data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" deleteRouteKpiThresholdByIds",e);
			data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	@RequestMapping("routeKpiList")
    public String routeKpiList(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			
			String baseSql = "select kpi_id as \"kpi_id\", " + 
				       "kpi_name as \"kpi_name\" " +
				  "from tf_avmon_route_kpi_code order by kpi_name";
			
			List<Map<String, Object>> list = jdbcTemplate.queryForList(baseSql);
			
			JSONArray jsonArray = JSONArray.fromObject(list);
			json = jsonArray.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" routeKpiList",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
	
	@RequestMapping("findAllDevice")
    public String findAllDevice(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			
			String baseSql = "select id as \"id\",device_name as \"device_name\",device_type as \"device_type\"," +
					"device_ip as \"device_ip\",device_desc as \"device_desc\",device_status as \"device_status\"," +
					"device_othrer1 as \"device_othrer1\",device_othrer2 as \"device_othrer2\",report_date as \"report_date\" " +
					" from TF_AVMON_ROUTE_INSPECTION_DEV r order by device_ip";
			
			List<Map<String, Object>> list = jdbcTemplate.queryForList(baseSql);
			
			JSONArray jsonArray = JSONArray.fromObject(list);
			String root = jsonArray.toString();

			json = "{root:" + root + "}";
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findAllDevice",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
	
	@RequestMapping("updateDeviceReportTime")
	public String updateDeviceReportTime(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String updateSql = "update tf_avmon_route_inspection_dev set report_date='" + request.getParameter("reportDate") + "' " +
					"where device_ip in ('" + request.getParameter("deviceIp") + "')";
			configService.execute(updateSql);
			data="{success:true,msg:'"+bundle.getString("updateReportTimeSuccess")+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" updateDeviceReportTime",e);
			data="{success:false,msg:'"+bundle.getString("updateReportTimeFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	
	@RequestMapping("findAllArp")
    public String findAllArp(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			
			String deviceIp = request.getParameter("deviceIp");
			String date = request.getParameter("date");
			
			if(deviceIp==null||deviceIp.length()==0||date==null||date.length()==0){
				json = "{root:[]}";
			}else{
				String baseSql = "select device_ip as \"device_ip\",substr(str_value,1,instr(str_value,':')-1) as \"ip\"," +
						"substr(str_value,instr(str_value,':')+1) as \"mac\",kpi_threshold as \"is_threshold\"" + 
						" from tf_avmon_route_inspection_info where kpi_id='address' " +
						"and to_char(last_update_time,'yyyy-MM-dd')='" + date + "' " +
						"and device_ip='" + deviceIp + "'";
				List<Map<String, Object>> list = jdbcTemplate.queryForList(baseSql);
				
				JSONArray jsonArray = JSONArray.fromObject(list);
				String root = jsonArray.toString();
				json = "{root:" + root + "}";
			}
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findAllArp",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
	
	@RequestMapping("findAllArpDevice")
    public String findAllArpDevice(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			
			String baseSql = "select id as \"id\",device_name||'('||device_ip||')' as \"device_name\",device_type as \"device_type\"," +
					"device_ip as \"device_ip\",device_desc as \"device_desc\",device_status as \"device_status\"," +
					"device_othrer1 as \"device_othrer1\",device_othrer2 as \"device_othrer2\",report_date as \"report_date\" " +
					" from TF_AVMON_ROUTE_INSPECTION_DEV r order by device_ip";
			
			List<Map<String, Object>> list = jdbcTemplate.queryForList(baseSql);
			
			JSONArray jsonArray = JSONArray.fromObject(list);
			String root = jsonArray.toString();

			//json = "{root:" + root + "}";
			json = root;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findAllArpDevice",e);
		}
		
		return Utils.responsePrintWrite(response,json,null);
	}
	
	@RequestMapping("setArpThredhold")
	public String setArpThredhold(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String deviceIp = request.getParameter("deviceIp");
			String date = request.getParameter("date");
						
			if(deviceIp==null||deviceIp.length()==0||date==null||date.length()==0){
				data="{success:false,msg:'"+bundle.getString("setFail")+"'}";
			}else{
				
				String updateSql = "update tf_avmon_route_inspection_info set kpi_threshold='true' " +
						"where kpi_id='address' and to_char(last_update_time,'yyyy-MM-dd')='" + date + "' and device_ip='" + deviceIp + "'";
				
				String updateSql1 = "update tf_avmon_route_inspection_info set kpi_threshold='false' " +
						"where kpi_id='address' and to_char(last_update_time,'yyyy-MM-dd')!='" + date + "' and device_ip='" + deviceIp + "'";
				String sql[] = {updateSql,updateSql1};
				jdbcTemplate.batchUpdate(sql);
				//configService.execute(updateSql);
				
				data="{success:true,msg:'"+bundle.getString("setSuccess")+"'}";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" setArpThredhold",e);
			data="{success:false,msg:'"+bundle.getString("setFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	private String getNetCheckGridRoot(String baseSql,String sortBy,String limit,String start){
		String sql = baseSql + " order by r.last_update_time desc ";
		if(sortBy!=null){
			JSONArray jsonArr = JSONArray.fromObject(sortBy);
			String sort = jsonArr.getJSONObject(0).get("property").toString();
			if(sort.equals("kpi_id")){
				sql = baseSql + " order by r.last_update_time desc,k.kpi_name " + jsonArr.getJSONObject(0).get("direction");
			}else if(sort.equals("last_update_time")){
				sql = baseSql + " order by r.last_update_time " + jsonArr.getJSONObject(0).get("direction");
			}else{
				sql = baseSql + " order by r.last_update_time desc,r." + sort + " " + jsonArr.getJSONObject(0).get("direction");
			}
			
		}
		
		String querySql = generatPageSql(sql,limit,start);
		//System.out.println(querySql);
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(querySql);
		
		JSONArray jsonArray = JSONArray.fromObject(list);
		String root = jsonArray.toString();
		return root;
	}
	
	@RequestMapping("netCheckIndex")
    public String findNetCheckGridInfo(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String sortBy = request.getParameter("sort");

			String deviceName = request.getParameter("deviceName");
			String deviceIp = request.getParameter("deviceIp");
			String kpi = request.getParameter("kpi");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String tableName = "tf_avmon_route_inspection_info";
			
			if(request.getParameter("tableName")!=null){
				tableName = request.getParameter("tableName");
			}
			
			String baseSql = "select r.AMP_INST_ID as \"AMP_INST_ID\",r.device_ip as \"device_ip\"," +
					"r.device_type as \"device_type\",k.kpi_name as \"kpi_id\",r.str_value as \"str_value\"," +
					"r.kpi_threshold as \"kpi_threshold\",to_char(last_update_time,'yyyy-mm-dd HH24:mi:ss') as \"last_update_time\"," +
					"r.kpi_status as \"kpi_status\",d.device_name as\"device_name\" " +
					" from " + tableName + " r " +
					"left join tf_avmon_route_kpi_code k " +
					"on r.kpi_id=k.kpi_id " +
					"left join tf_avmon_route_inspection_dev d " +
					"on r.device_ip=d.device_ip " +
					"where 1=1 ";
			
			if(deviceIp!=null&&deviceIp.length()>0){
				baseSql = baseSql + " and r.device_ip like '%"+deviceIp+"%' ";
			}
			if(kpi!=null&&kpi.length()>0){
//				baseSql = baseSql + " and upper(r.kpi_id) like upper('%"+kpi+"%') ";
				baseSql = baseSql + " and upper(k.kpi_name) like upper('%"+kpi+"%') ";
			}
			if(startDate!=null&&startDate.length()>0){
				baseSql = baseSql + " and r.last_update_time >= to_date('" + startDate + "','yyyy-mm-dd HH24:mi:ss') ";
			}
			if(endDate!=null&&endDate.length()>0){
				baseSql = baseSql + " and r.last_update_time <= to_date('" + endDate + "','yyyy-mm-dd HH24:mi:ss') ";
			}
			if(deviceName!=null&&deviceName.length()>0){
				baseSql = baseSql + " and upper(d.device_name) like upper('%"+deviceName+"%') ";
			}
			String root = getNetCheckGridRoot(baseSql,sortBy,request.getParameter("limit"),request.getParameter("start"));
			
			String countSql = "select count(1) from " + tableName + " r " +
					"left join tf_avmon_route_kpi_code k " +
					"on r.kpi_id=k.kpi_id " +
					"left join tf_avmon_route_inspection_dev d " +
					"on r.device_ip=d.device_ip " +
					"where 1=1 ";
			
			if(deviceIp!=null&&deviceIp.length()>0){
				countSql = countSql + " and r.device_ip like '%"+deviceIp+"%' ";
			}
			if(kpi!=null&&kpi.length()>0){
//				baseSql = baseSql + " and upper(r.kpi_id) like upper('%"+kpi+"%') ";
				countSql = countSql + " and upper(k.kpi_name) like upper('%"+kpi+"%') ";			
			}
			if(startDate!=null&&startDate.length()>0){
				countSql = countSql + " and r.last_update_time >= to_date('" + startDate + "','yyyy-mm-dd HH24:mi:ss') ";
			}
			if(endDate!=null&&endDate.length()>0){
				countSql = countSql + " and r.last_update_time <= to_date('" + endDate + "','yyyy-mm-dd HH24:mi:ss') ";
			}
			if(deviceName!=null&&deviceName.length()>0){
				countSql = countSql + " and upper(d.device_name) like upper('%"+deviceName+"%') ";
			}
			
			int total = jdbcTemplate.queryForInt(countSql);

			json = "{total:" + total +",root:" + root + "}";
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findNetCheckGridInfo",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
	
	@RequestMapping("downLoadConfigFile")
	public String downLoadConfigFile(
			HttpServletRequest request,
			HttpServletResponse response) {

		String filePath = request.getParameter("filePath");
		if(filePath!=null){
			String fileName = filePath;
			if(filePath.indexOf("/")>-1){
				fileName = filePath.substring(filePath.lastIndexOf("/")+1);
			}
			
			String contentType = "application/octet-stream";
		    response.setContentType(contentType);
		    response.setCharacterEncoding("utf-8");
		
		    InputStream is = null;
		    OutputStream os = null;
		    try {
		        response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
		
		        is = new BufferedInputStream(new FileInputStream(filePath));
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        os = new BufferedOutputStream(response.getOutputStream());
		
		        byte[] buffer = new byte[4 * 1024];
		        int read = 0;
		        while ((read = is.read(buffer)) != -1) {
		            baos.write(buffer, 0, read);
		        }
		        os.write(baos.toByteArray());
		       
		    } catch(Exception e) {
		        e.printStackTrace();
		        logger.error("downLoadConfigFile error", e);
		    }  finally {
		        try {
		            if (os != null) {
		                os.close();
		            }
		            if (is != null) {
		                is.close();
		            }
		        } catch(IOException e) {
		            e.printStackTrace();
		        }
		
		    }
		}
	    return null;
	}
	
	@RequestMapping("findAllDeviceAttribute")
    public String findAllDeviceAttribute(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			
			String baseSql = "select name as \"attr_name\",caption as \"attr_caption\" " +
					" from td_avmon_mo_type_attribute r order by name";
			
			List<Map<String, Object>> list = jdbcTemplate.queryForList(baseSql);
			
			JSONArray jsonArray = JSONArray.fromObject(list);
			String root = jsonArray.toString();

			json = "{root:" + root + "}";
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findAllDeviceAttribute",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
	
	//kpi config//////////////////////////////////////////////////////////////////////////////
	@RequestMapping("findKpiGridInfo")
    public String findKpiGridInfo(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String sortBy = request.getParameter("sort");
			
			String kpiCode = request.getParameter("kpiCode");
			String kpiName = request.getParameter("kpiName");
			
			String baseSql = "select id as \"id\",KPI_CODE as \"kpi_code\",CAPTION as \"caption\",kpi_name as \"kpi_name\"," +
					"AGGMETHOD as \"aggmethod\",PRECISION as \"precision\",UNIT as \"unit\",ISCALC as \"iscalc\",CALCMETHOD as \"calcmethod\"," +
					"ISSTORE as \"isstore\",STOREPERIOD as \"storeperiod\",DATATYPE as \"datatype\",recent_trend as \"recent_trend\" " +
					" from td_avmon_snmpkpi_info r " + 
					"where 1=1 ";
			
			if(kpiCode!=null&&kpiCode.length()>0){
				baseSql = baseSql + " and upper(r.kpi_code) like upper('%"+kpiCode+"%') ";
			}
			if(kpiName!=null&&kpiName.length()>0){
				baseSql = baseSql + " and upper(r.kpi_name) like upper('%"+kpiName+"%') ";
			}
			String root = getGridRoot(baseSql,sortBy,request.getParameter("limit"),request.getParameter("start"));
			
			String countSql = "select count(1) from td_avmon_snmpkpi_info r where 1=1 ";
			if(kpiCode!=null&&kpiCode.length()>0){
				countSql = countSql + " and upper(r.kpi_code) like upper('%"+kpiCode+"%') ";
			}
			if(kpiName!=null&&kpiName.length()>0){
				countSql = countSql + " and upper(r.kpi_code) like upper('%"+kpiName+"%') ";
			}
			int total = jdbcTemplate.queryForInt(countSql);

			json = "{total:" + total +",root:" + root + "}";
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findKpiGridInfo",e);
		}
		
		return Utils.responsePrintWrite(response,json.toString(),null);
	}
    
    @RequestMapping("saveUpdateKpiInfo")
	public String saveUpdateKpiInfo(
			HttpServletResponse response,
			HttpServletRequest request,
			@ModelAttribute TdAvmonKpiInfoBeanSnmp entity){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String kpiCode = entity.getKpi_code();
			
			String updateSql = "";
						
			if(entity.getId()!=null && entity.getId() != 0){//更新数据	
				updateSql = CreateSqlTools.getUpdateSql(entity);
				updateSql += " kpi_code = '"+kpiCode+"'";
			}else{//保存新数据
				String countSql = "select count(1) from td_avmon_snmpkpi_info where upper(kpi_code)=upper('" + kpiCode + "')";
				int total = jdbcTemplate.queryForInt(countSql);
				if(total>0){
					data="{success:false,msg:'"+bundle.getString("kpiCannotRepet")+"'}";
					return Utils.responsePrintWrite(response,data,null);
				}else{
					entity.setId(Long.valueOf(entity.getKpi_code()));
					updateSql = CreateSqlTools.getInsertSql(entity);	
				}
			}
			
			configService.execute(updateSql);
			data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" saveUpdateKpiInfo",e);
			data="{success:false,msg:'"+bundle.getString("saveFail")+""+e.getMessage()+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
	}
	
	@RequestMapping("deleteKpiInfoByIds")
	public String deleteKpiInfoByIds(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String ids = request.getParameter("ids");
			
			String sql = "delete from td_avmon_snmpkpi_info where id in(" + ids + ")";
			configService.execute(sql);
			data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" deleteKpiInfoByIds",e);
			data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	
	@RequestMapping("dynamicKpiChartData")
    public String dynamicKpiChartData(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String ampInstId = request.getParameter("ampInstId");
			String kpiCode = request.getParameter("kpiCode");
			String moId = request.getParameter("moId");
			
			String startTime = request.getParameter("startTime") + ":00";//":00:00";
			String endTime = request.getParameter("endTime") + ":00";//":00:00";
			
			String grainsize = request.getParameter("grainsize");

			List<Map<String, Object>> kpiValueList = configService.getKpiChartData(ampInstId,kpiCode,moId,startTime,endTime,grainsize);
			if(kpiValueList.size()<1){
				json = "{root:[{time:'0'}],moId:'" + moId + "'}";
			}else{
				JSONArray jsonArray = JSONArray.fromObject(kpiValueList);
				json = "{root:" + jsonArray.toString() + ",moId:'" + moId + "'}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" dynamicKpiChartData",e);
		}
		
		return Utils.responsePrintWrite(response,json,null);
	}
	
	@RequestMapping("getKpiInstance")
    public String getKpiInstance(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String ampInstId = request.getParameter("ampInstId");
			String kpiCode = request.getParameter("kpiCode");
			String moId = request.getParameter("moId");
			
			String startTime = request.getParameter("startTime") + ":00";//":00:00";
			String endTime = request.getParameter("endTime") + ":00";//":00:00";

			String grainsize = request.getParameter("grainsize");
			List<Map<String, Object>> kpiInstanceList = configService.getKpiInstance(ampInstId,kpiCode,moId,startTime,endTime,grainsize);
			JSONArray jsonArray = JSONArray.fromObject(kpiInstanceList);
			
			String moCaptionSql = "select caption as \"caption\" from td_avmon_mo_info where mo_id='" + moId + "'";
			List<Map<String, Object>> captionList = jdbcTemplate.queryForList(moCaptionSql);
			String caption = moId;
			if(captionList.size()>0){
				caption = captionList.get(0).get("caption").toString();
			}
			
//			json = jsonArray.toString();
			json = "{root:" + jsonArray.toString() + ",moId:'" + moId + "',caption:'" + caption + "'}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" getKpiInstance",e);
		}
		
		return Utils.responsePrintWrite(response,json,null);
	}
}
