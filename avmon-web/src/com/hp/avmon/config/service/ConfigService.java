/**
 * 
 */
package com.hp.avmon.config.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.hp.avmon.common.Config;
import com.hp.avmon.config.bean.IreportMgtBean;
import com.hp.avmon.ireport.util.StringUtil;
import com.hp.avmon.utils.Constants;
import com.hp.avmon.utils.DBUtils;
import com.hp.avmon.utils.ZipFile;

/**
 * @author muzh
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ConfigService {

	private static final Log logger = LogFactory.getLog(ConfigService.class);
	
	private static List<Map<String, Object>> kpiInstanceList = new ArrayList<Map<String,Object>>();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void execute(String sql){
		try {
			jdbcTemplate.execute(sql);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(this.getClass().getName() + "execute sql:[" + sql + "]", e);
			throw e;
		}	
		
	}

	public void saveReportTemplateInfo(String updateSql, IreportMgtBean entity,
			HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		try {
			int r = jdbcTemplate.update(updateSql);
			//保存模板成功后上传模板文件，并根据页面传来的值判断是否保存html和email
			if(r>0){
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
				//上传模板
				String templatePath = Config.getInstance().getReportTemplatePath() + entity.getReport_id();
				File dirPath = new File(templatePath);
			    if (!dirPath.exists()) {
			    	dirPath.mkdirs();
			    }else{
			    	boolean flag = false;
			    	for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
				    	String fileName = (String)it.next(); 
				        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(fileName);
				        if(file!=null && file.getSize() != 0){
					        flag = true;
					        break;
				        }
				    }
			    	if(flag){
			    		ZipFile.zip(templatePath,entity.getReport_id());
				    	ZipFile.deleteDirectory(templatePath);
				    	dirPath.mkdirs();
			    	}
			    }
				
			    for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
			    	String fileName = (String)it.next(); 
			        
			        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(fileName);
			        if(file!=null && file.getSize() != 0){
				        String sep = System.getProperty("file.separator");
				        File uploadedFile = new File(dirPath + sep + file.getOriginalFilename());
				        FileCopyUtils.copy(file.getBytes(), uploadedFile);
			        }
			    }
		    
			    //保存菜单
			    String menuSql = "select parent_id as \"parent_id\",module_name as \"module_name\" from portal_modules where module_id='report_" + entity.getReport_id() + "'";
			    List<Map<String,String>> l = jdbcTemplate.queryForList(menuSql);
			    if(l.size()>0
			    		//&&(!l.get(0).get("parent_id").equals(entity.getMenu())||
			    		//!l.get(0).get("module_name").equals(entity.getReport_name()))
			    		){//更新菜单的
			    	String updateReportMenuSql = "update portal_modules set parent_id='" + entity.getMenu() + "'," +
			    			"module_name='" + entity.getReport_name() + "' where module_id='report_" + entity.getReport_id() + "'";
			    	jdbcTemplate.execute(updateReportMenuSql);
			    }else{//添加菜单
			    	String insertReportMenuSql = "insert into portal_modules(display_flag,display_order,module_id,module_name,module_url,parent_id) " +
			    			"values('show',0,'report_" + entity.getReport_id() + "','" + entity.getReport_name() + "','" + Config.getInstance().getReportActionUrl() + entity.getReport_id() + 
			    			"','" + entity.getMenu() + "')";
			    	jdbcTemplate.execute(insertReportMenuSql);
			    }
			    
			    //保存HTML
			    String htmlType = request.getParameter("html_type");
			    String htmlStartTime = request.getParameter("html_start_time");
			    String htmlPath = Config.getInstance().getReportHtmlPath() + entity.getReport_id() + System.getProperty("file.separator");
			    if(htmlType!=null&&htmlStartTime!=null&&htmlType.length()>0&&htmlStartTime.length()>0){
			    	String htmlCountSql = "select count(1) from IREPORT_HTML where report_id='" + entity.getReport_id() + "'";
			    	int htmlCount = jdbcTemplate.queryForInt(htmlCountSql);
			    	if(htmlCount>0){//update
			    		String updateHtmlSql = "update IREPORT_HTML set type='" + htmlType 
			    				+ "',start_time="+DBUtils.getDBToDateFunction()+"('" + htmlStartTime + "','yyyy-mm-dd hh24:mi:ss'),updated_dt=" + DBUtils.getDbSysdateKeyword() +
			    				" where report_id='" + entity.getReport_id() + "'";
			    		jdbcTemplate.execute(updateHtmlSql);
			    	}else{//insert
			    		String insertHtmlSql = "insert into IREPORT_HTML(report_id,type1,start_time,path,updated_dt) " +
			    				"values('" + entity.getReport_id() + "','" + htmlType + 
			    				"',"+DBUtils.getDBToDateFunction()+"('" + htmlStartTime + "','yyyy-mm-dd hh24:mi:ss'),'" + htmlPath + "'," + DBUtils.getDbSysdateKeyword() + ")";
			    		jdbcTemplate.execute(insertHtmlSql);
			    	}
			    	
			    }
			    
			    //保存EMAIL
			    String emailStartTime = request.getParameter("email_start_time");
			    String emailPeriod = request.getParameter("email_period");
			    String emailHost = request.getParameter("email_host");
			    String emailEmail = request.getParameter("email_email");
			    String isAttachment = request.getParameter("is_attachment");
			    String attachmentType = request.getParameter("attachment_type");
			    if(emailStartTime!=null&&
			    		emailHost!=null&&emailEmail!=null&&
			    		emailStartTime.length()>0&&
			    		emailHost.length()>0&&emailEmail.length()>0){
			    	String emailCountSql = "select count(1) from IREPORT_EMAIL where report_id='" + entity.getReport_id() + "'";
			    	int emailCount = jdbcTemplate.queryForInt(emailCountSql);
			    	if(emailCount>0){//update
			    		String updateEmailSql = "update IREPORT_EMAIL set start_time="+DBUtils.getDBToDateFunction()+"('" + emailStartTime + "','yyyy-mm-dd hh24:mi:ss')," +
			    				"period='" + emailStartTime + "',email='" + emailEmail + "',host='" + emailHost + "',updated_dt=" + DBUtils.getDbSysdateKeyword() +
			    				",is_attachment='" + isAttachment + "',attachment_type='" + attachmentType + "' "
			    						+ "where report_id='" + entity.getReport_id() + "'";
			    		jdbcTemplate.execute(updateEmailSql);
			    	}else{//insert
			    		String insertEmailSql = "insert into IREPORT_EMAIL(report_id,start_time,period,email,host,updated_dt,is_attachment,attachment_type) " +
			    				"values('" + entity.getReport_id() + "',"+DBUtils.getDBToDateFunction()+"('" + emailStartTime + "','yyyy-mm-dd hh24:mi:ss'),'" + 
			    				emailPeriod + "','" + emailEmail + "','" + emailHost + "'," + DBUtils.getDbSysdateKeyword() + ",'" + isAttachment + "','" + attachmentType + "')";
			    		jdbcTemplate.execute(insertEmailSql);
			    	}
			    }
			    
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteReportTemplateByIds(String[] sql) {
		// TODO Auto-generated method stub
		try {
			jdbcTemplate.batchUpdate(sql);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	public List<Map<String, Object>> getKpiInstance(String ampInstId,
			String kpiCode, String moId, String startTime, String endTime,String grainsize) {
		
		if(kpiInstanceList!=null||kpiInstanceList.size() > 0){
			kpiInstanceList.clear();
		}
		String baseSql = StringUtil.EMPTY;
		String getKpiInstanceSql = StringUtil.EMPTY;
		String type = StringUtil.EMPTY;
		try {
			// modify by mark start 2014-3-12
			String snmpSql = "select type_id as \"typeId\" from td_avmon_mo_info where mo_id = '"+moId+"'";
			Map<String,String> typeMap = jdbcTemplate.queryForMap(snmpSql);
			if(typeMap != null || !StringUtil.isBlank(typeMap.get("typeId"))){
				type = typeMap.get("typeId");
			}
					
			if("SNMP".equals(type)||"HARDWARE_HP".equals(type)||"HARDWARE_DELL".equals(type)){
				if("1".equals(grainsize))
				{
					baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value " + 
							"where kpi_code = '%s' " + 
							"and mo_id='%s' " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				}
				else if("2".equals(grainsize))
				{
					baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value_hourly " + 
							"where kpi_code = '%s' " + 
							"and mo_id='%s' " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				}
				else if("3".equals(grainsize))
				{
					baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value_daily " + 
							"where kpi_code = '%s' " + 
							"and mo_id='%s' " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				}else{
					baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value " + 
							"where kpi_code = '%s' " + 
							"and mo_id='%s' " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				}
				
				getKpiInstanceSql = String.format(baseSql,kpiCode,moId,startTime,endTime);
			}else{
				if("1".equals(grainsize))
				{
					baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value " + 
							//"where amp_inst_id='%s' and kpi_code = '%s' " + 
							"where kpi_code = '%s' " + 
							"and mo_id='%s' " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				}
				else if("2".equals(grainsize))
				{
					baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value_hourly " + 
							//"where amp_inst_id='%s' and kpi_code = '%s' " +
							"where  kpi_code = '%s' " + 
							"and mo_id='%s' " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				}
				else if("3".equals(grainsize))
				{
					baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value_daily " + 
							//"where amp_inst_id='%s' and kpi_code = '%s' " + 
							"where kpi_code = '%s' " + 
							"and mo_id='%s' " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				}else{
					baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value " + 
							//"where amp_inst_id='%s' and kpi_code = '%s' " + 
							"where kpi_code = '%s' " + 
							"and mo_id='%s' " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				}
				
				//getKpiInstanceSql = String.format(baseSql,ampInstId,kpiCode,moId,startTime,endTime);
				getKpiInstanceSql = String.format(baseSql,kpiCode,moId,startTime,endTime);
			}
//			startTime = startTime.replace("-", "").replace(" ", "").replace(":", "");
//			endTime = endTime.replace("-", "").replace(" ", "").replace(":", "");
			// modify by mark end 2014-3-12
			kpiInstanceList = jdbcTemplate.queryForList(getKpiInstanceSql);
			return kpiInstanceList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" getKpiInstance",e);
		}
		return kpiInstanceList;
	}

	//jay 2014/11/25 backup
	public List<Map<String, Object>> getKpiInstanceOld(String ampInstId,
			String kpiCode, String moId, String startTime, String endTime,String grainsize) {
		
		if(kpiInstanceList!=null||kpiInstanceList.size() > 0){
			kpiInstanceList.clear();
		}
		String baseSql = StringUtil.EMPTY;
		String getKpiInstanceSql = StringUtil.EMPTY;
		String type = StringUtil.EMPTY;
		try {
			// modify by mark start 2014-3-12
			String snmpSql = "select type_id as \"typeId\" from td_avmon_mo_info where mo_id = '"+moId+"'";
			Map<String,String> typeMap = jdbcTemplate.queryForMap(snmpSql);
			if(typeMap != null || !StringUtil.isBlank(typeMap.get("typeId"))){
				type = typeMap.get("typeId");
			}
					
			if("SNMP".equals(type)||"iloRoot".equals(type)||"IDRAC".equals(type)){
				if("1".equals(grainsize))
				{
					baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value " + 
							"where kpi_code = '%s' " + 
							"and mo_id='%s' " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				}
				else if("2".equals(grainsize))
				{
					baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value_hourly " + 
							"where kpi_code = '%s' " + 
							"and mo_id='%s' " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				}
				else if("3".equals(grainsize))
				{
					baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value_daily " + 
							"where kpi_code = '%s' " + 
							"and mo_id='%s' " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				}else{
					baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value " + 
							"where kpi_code = '%s' " + 
							"and mo_id='%s' " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				}
				
				getKpiInstanceSql = String.format(baseSql,kpiCode,moId,startTime,endTime);
			}else{
				if("1".equals(grainsize))
				{
					baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value " + 
							//"where amp_inst_id='%s' and kpi_code = '%s' " + 
							"where kpi_code = '%s' " + 
							"and mo_id='%s' " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				}
				else if("2".equals(grainsize))
				{
					baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value_hourly " + 
							//"where amp_inst_id='%s' and kpi_code = '%s' " +
							"where  kpi_code = '%s' " + 
							"and mo_id='%s' " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				}
				else if("3".equals(grainsize))
				{
					baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value_daily " + 
							//"where amp_inst_id='%s' and kpi_code = '%s' " + 
							"where kpi_code = '%s' " + 
							"and mo_id='%s' " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				}else{
					baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value " + 
							//"where amp_inst_id='%s' and kpi_code = '%s' " + 
							"where kpi_code = '%s' " + 
							"and mo_id='%s' " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				}
				
				//getKpiInstanceSql = String.format(baseSql,ampInstId,kpiCode,moId,startTime,endTime);
				getKpiInstanceSql = String.format(baseSql,kpiCode,moId,startTime,endTime);
			}
//			startTime = startTime.replace("-", "").replace(" ", "").replace(":", "");
//			endTime = endTime.replace("-", "").replace(" ", "").replace(":", "");
			// modify by mark end 2014-3-12
			kpiInstanceList = jdbcTemplate.queryForList(getKpiInstanceSql);
			return kpiInstanceList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" getKpiInstance",e);
		}
		return kpiInstanceList;
	}

	public List<Map<String, Object>> getKpiChartData(String ampInstId,
			String kpiCode, String moId, String startTime, String endTime,String grainsize) {
		List<Map<String, Object>> kpiValueList = null;
		try {
			
			List<Map<String, Object>> kpiInstanceList = getKpiInstance(ampInstId,kpiCode,moId,startTime,endTime,grainsize);
			String ins1 = "";
			String ins2 = "";
			String differ = "";
			String tableName = "";
			String valueColum = "num_value";
			if("1".equals(grainsize)){
				valueColum = "num_value";
				tableName = "tf_avmon_kpi_value";
			}else if("2".equals(grainsize)){
				valueColum = "avg_value";
				tableName = "tf_avmon_kpi_value_hourly";
			}else if("3".equals(grainsize)){
				valueColum = "avg_value";
				tableName = "tf_avmon_kpi_value_daily";
			}else{
				tableName = "tf_avmon_kpi_value";
			}
			
			// modify by mark start 2014-3-12
			String snmpDiffer = StringUtil.EMPTY;
			String type = StringUtil.EMPTY;
			boolean diferFlag = false;
			String snmpSql = "select type_id as \"typeId\" from td_avmon_mo_info where mo_id = '"+moId+"'";
			Map<String,String> typeMap = jdbcTemplate.queryForMap(snmpSql);
			if((typeMap != null) && (!StringUtil.isBlank(typeMap.get("typeId")))){
				type = typeMap.get("typeId");
			}
			if((!StringUtil.isEmpty(type))  && ("SNMP".equals(type)||"HARDWARE_HP".equals(type)||"HARDWARE_DELL".equals(type))){
				snmpDiffer = " kpi_code = '%s' ";
				diferFlag = true;
			}else{
				//snmpDiffer = "amp_inst_id='%s' and kpi_code = '%s' ";
				snmpDiffer = " kpi_code = '%s' ";
			}
			// modify by mark end 2014-3-12
			int dbType = DBUtils.getDbType();
		/*	if (Constants.DB_POSTGRESQL==dbType) {
				 differ = " as a ";
			}else{
				differ = " ";
			}*/
			if(kpiInstanceList.size()==1){
				ins1 = ins1 + "sum(instance) as \"instance\" ,";
				ins2 = ins2 + "case when instance='" + kpiInstanceList.get(0).get("instance").toString() + "' then "+valueColum+" else 0 end as instance, ";
				
			}
			String instanceNameString="";
			if(kpiInstanceList.size()>1){
				/*for(int i=0;i<kpiInstanceList.size();i++){
					ins1 = ins1 + "sum(instance" + i + ") as \"instance" + i + "\",";
					ins2 = ins2 + "case when instance='" + kpiInstanceList.get(i).get("instance").toString() + "' then "+valueColum+" else 0 end as instance" + i + ",";
				}*/
				
				for(int i=0;i<kpiInstanceList.size();i++){
				    instanceNameString="\""+kpiInstanceList.get(i).get("instance").toString()+"\"";
					ins1 = ins1 + "sum(instance" + i + ") as "+instanceNameString+" ,";
					ins2 = ins2 + "case when instance='" + kpiInstanceList.get(i).get("instance").toString() + "' then "+valueColum+" else 0 end as instance" + i + ",";
				}
			}
			
			
			String kpiValueSql = 
				"select " +  
				ins1 +
				"to_char(time,'yyyy-mm-dd HH24:mi') as \"time\" "+ " ,  \"insname\" "+
				" from( " +
				"select " + 
				ins2 + " instance as \"insname\" "+
				" ,kpi_time as time " +
				// modify by mark start 2013-9-28
				"from  " + tableName + 
				// modify by mark end 2013-9-28
				" where " +
				// modify by mark start 2014-3-12
				snmpDiffer +
				// modify by mark end 2014-3-12
				"and mo_id='%s' "+
				"and kpi_time between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and  "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS' )) a " +
				differ +
				"group by time , \"insname\"" +
				" order by time";
//			startTime = startTime.replace("-", "").replace(" ", "").replace(":", "");
//			endTime = endTime.replace("-", "").replace(" ", "").replace(":", "");
			// modify by mark start 2014-3-12
			if(diferFlag){
				kpiValueSql = String.format(kpiValueSql,kpiCode,moId,startTime,endTime);
			}else{
				//kpiValueSql = String.format(kpiValueSql,ampInstId,kpiCode,moId,startTime,endTime);
				kpiValueSql = String.format(kpiValueSql,kpiCode,moId,startTime,endTime);
			}
			// modify by mark end 2014-3-12
			System.out.println("----------kpiValueSql---------:"+kpiValueSql);
			kpiValueList = jdbcTemplate.queryForList(kpiValueSql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" getKpiInstance",e);
		}
		logger.debug("kpiValueList----------------------------------------------------"+kpiValueList);
		return kpiValueList;	
	}
	

	public List<Map<String, Object>> getKpiInstanceCompare(String ampInstId,
			String kpiCode, String moId, String startTime, String endTime,String grainsize) {
		
		if(kpiInstanceList.size()>0){kpiInstanceList.clear();}
		
		String baseSql = StringUtil.EMPTY;
		String getKpiInstanceSql = StringUtil.EMPTY;
		moId = moId.replace(",", "','");
		String type = "";
		try {
			// modify by mark start 2014-5-26
			String snmpSql = "select distinct type_id as \"typeId\" from td_avmon_mo_info where mo_id in ('"+moId+"')";
			List<Map<String,String>> typeMapList = jdbcTemplate.queryForList(snmpSql);
			if(typeMapList.size() ==1){
				type = typeMapList.get(0).get("typeId");
			}else{
				for (Map<String, String> typeMap : typeMapList) {
					if (!typeMap.get("typeId").startsWith("HOST")) {
						return null;
					}
				}
			}
			
			Map<String,String> captionCache = new HashMap<String, String>();
			String captionSql = "select caption as \"caption\",mo_id as \"moId\" from td_avmon_mo_info where mo_id in ('"+moId+"')";
			List<Map<String,String>> captionList = jdbcTemplate.queryForList(captionSql);
			for (Map<String, String> map : captionList) {
				captionCache.put(map.get("moId"),map.get("caption"));
			}
			
			//根据粒度动态切换table
			String tableName = StringUtils.EMPTY;
			if(!StringUtils.isEmpty(grainsize)){
				if("1".equals(grainsize)){
					tableName = "tf_avmon_kpi_value";
				}else if("2".equals(grainsize)){
					tableName = "tf_avmon_kpi_value_hourly";
				}else if("3".equals(grainsize)){
					tableName = "tf_avmon_kpi_value_daily";
				}
			}else{
				tableName = "tf_avmon_kpi_value";
			}
			
			if(!StringUtils.isBlank(type)||"SNMP".equals(type)||"iloRoot".equals(type)||"IDRAC".equals(type)){
				baseSql = "select distinct instance as \"instance\",mo_id as \"moId\" from "+tableName+" " + 
							"where kpi_code = '%s' " + 
							"and mo_id in ('%s') " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				
				getKpiInstanceSql = String.format(baseSql,kpiCode,moId,startTime,endTime);
			}
			else{
				baseSql = "select distinct instance as \"instance\",mo_id as \"moId\" from "+tableName+" " + 
							"where amp_inst_id='%s' and kpi_code = '%s' " + 
							"and mo_id in ('%s') " + 
							"and kpi_time  between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') ";
				
				getKpiInstanceSql = String.format(baseSql,ampInstId,kpiCode,moId,startTime,endTime);
			}
			// modify by mark end 2014-3-12
			kpiInstanceList = jdbcTemplate.queryForList(getKpiInstanceSql);
			if (kpiInstanceList.size() > 0) {
				for (Map<String, Object> map : kpiInstanceList) {
					map.put("instance", map.get("instance").toString()+"-"+captionCache.get(map.get("moId")).toString());
				}
			}
			
			logger.debug("------------------------------getKpiInstanceCompare-------------------------------------"+kpiInstanceList);
			return kpiInstanceList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" getKpiInstanceCompare",e);
		}
		return kpiInstanceList;
	}
	
	public List<Map<String, Object>> getKpiChartDataCompare(String ampInstId,
			String kpiCode, String moId, String startTime, String endTime,String grainsize) {
		List<Map<String, Object>> kpiValueList = null;
		try {
			
//			List<Map<String, Object>> kpiInstanceList = getKpiInstanceCompare(ampInstId,kpiCode,moId,startTime,endTime);
			
			String ins1 = "";
			String ins2 = "";
			String differ = "";
			// modify by mark start 2014-3-12
			String snmpDiffer = StringUtil.EMPTY;
			String type = StringUtil.EMPTY;
			moId = moId.replace(",", "','");
			boolean flag = false;
			String snmpSql = "select distinct type_id as \"typeId\" from td_avmon_mo_info where mo_id in ('"+moId+"')";
			Map<String,String> typeMap = jdbcTemplate.queryForMap(snmpSql);
			type = typeMap.get("typeId");
			if("SNMP".equals(type)||"iloRoot".equals(type)||"IDRAC".equals(type)){
				snmpDiffer = " kpi_code = '%s' ";
				flag = true;
			}else{
				snmpDiffer = "amp_inst_id='%s' and kpi_code = '%s' ";
			}
			// modify by jay end 2014-11-21
			int dbType = DBUtils.getDbType();
			if (Constants.DB_POSTGRESQL==dbType) {
				 differ = " as a ";
			}else{
				differ = " ";
			}
			
			//根据粒度动态切换table和column
			String column = StringUtils.EMPTY;
			String tableName = StringUtils.EMPTY;
			if(!StringUtils.isEmpty(grainsize)){
				if("1".equals(grainsize)){
					tableName = "tf_avmon_kpi_value";
					column = "num_value";
				}else if("2".equals(grainsize)){
					tableName = "tf_avmon_kpi_value_hourly";
					column = "avg_value";
				}else if("3".equals(grainsize)){
					tableName = "tf_avmon_kpi_value_daily";
					column = "avg_value";
				}
			}else{
				tableName = "tf_avmon_kpi_value";
				column = "num_value";
			}
			
			
			String instance = StringUtils.EMPTY;
			String moIdParam = StringUtils.EMPTY;
			if(kpiInstanceList.size() > 0){
				for(int i=0;i<kpiInstanceList.size();i++){
					instance = kpiInstanceList.get(i).get("instance").toString();
					moIdParam = kpiInstanceList.get(i).get("moId").toString();
					instance = instance.substring(0, instance.indexOf("-"));
					ins1 = ins1 + "sum(instance" + i + ") as \"instance" + i + "\",";
					ins2 = ins2 + "case when instance='" + instance + "' and mo_id = '"+moIdParam+"' then "+column+" else 0 end as instance" + i + ",";
				}
			}else{
				
			}

			String kpiValueSql = 
				"select " +  
				ins1 +
				"to_char(time,'mm-dd HH24:mi') as \"time\" "+
				" from( " +
				"select " + 
				ins2 +
				"kpi_time as time " +
				// modify by mark start 2013-9-28
				"from "+tableName+" " +
				// modify by mark end 2013-9-28
				"where " +
				// modify by mark start 2014-3-12
				snmpDiffer +
				// modify by mark end 2014-3-12
				"and mo_id in('%s') "+
				"and kpi_time between "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS') and  "+DBUtils.getDBToDateFunction()+"('%s','YYYY-MM-DD HH24:MI:SS' ))" +
				differ +
				"group by time " +
				"order by time";
//			startTime = startTime.replace("-", "").replace(" ", "").replace(":", "");
//			endTime = endTime.replace("-", "").replace(" ", "").replace(":", "");
			// modify by mark start 2014-3-12
			if(flag){
				kpiValueSql = String.format(kpiValueSql,kpiCode,moId,startTime,endTime);
			}else{
				kpiValueSql = String.format(kpiValueSql,ampInstId,kpiCode,moId,startTime,endTime);	
			}
			// modify by mark end 2014-3-12
			kpiValueList = jdbcTemplate.queryForList(kpiValueSql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" getKpiChartDataCompare",e);
		}
		return kpiValueList;	
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, List<Map<String, String>>> getPropertiesTypeComboxValues() {
		String sql ="select DISTINCT TYPE_ID as \"value\",CAPTION as \"name\" from TD_AVMON_MO_TYPE";
		List<Map<String,String>> list = jdbcTemplate.queryForList(sql);
		Map<String,List<Map<String, String>>> map = new HashMap<String,List<Map<String, String>>>();
		map.put("root", list);
		return map;
	}

}
