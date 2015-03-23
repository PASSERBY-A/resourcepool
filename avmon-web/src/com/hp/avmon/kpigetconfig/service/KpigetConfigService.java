/**
 * 
 */
package com.hp.avmon.kpigetconfig.service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.hp.avmon.utils.DBUtils;
import com.hp.avmon.utils.ZipFile;

/**
 * @author muzh
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class KpigetConfigService {

	private static final Log logger = LogFactory.getLog(KpigetConfigService.class);
	
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
				//上传模板
				String templatePath = Config.getInstance().getReportTemplatePath() + entity.getReport_id();
				File dirPath = new File(templatePath);
			    if (!dirPath.exists()) {
			    	dirPath.mkdirs();
			    }else{
			    	ZipFile.zip(templatePath,entity.getReport_id());
			    	ZipFile.deleteDirectory(templatePath);
			    	dirPath.mkdirs();
			    }
				
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
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
			    	jdbcTemplate.update(updateReportMenuSql);
			    }else{//添加菜单
			    	String insertReportMenuSql = "insert into portal_modules(display_flag,display_order,module_id,module_name,module_url,parent_id) " +
			    			"values('show',0,'report_" + entity.getReport_id() + "','" + entity.getReport_name() + "','" + Config.getInstance().getReportActionUrl() + entity.getReport_id() + 
			    			"','" + entity.getMenu() + "')";
			    	jdbcTemplate.update(insertReportMenuSql);
			    }
			    
			    //保存HTML
			    String htmlType = request.getParameter("html_type");
			    String htmlStartTime = request.getParameter("html_start_time");
			    String htmlPath = Config.getInstance().getReportHtmlPath() + entity.getReport_id() + System.getProperty("file.separator");
			    if(htmlType!=null&&htmlStartTime!=null&&htmlType.length()>0&&htmlStartTime.length()>0){
			    	String htmlCountSql = "select count(1) from IREPORT_HTML where report_id='" + entity.getReport_id() + "'";
			    	int htmlCount = jdbcTemplate.queryForInt(htmlCountSql);
			    	if(htmlCount>0){//update
			    		String updateHtmlSql = "update IREPORT_HTML set \"TYPE\"='" + htmlType 
			    				+ "',start_time=to_date('" + htmlStartTime + "','yyyy-mm-dd hh24:mi:ss'),updated_dt=" + DBUtils.getDbSysdateKeyword() +
			    				" where report_id='" + entity.getReport_id() + "'";
			    		jdbcTemplate.update(updateHtmlSql);
			    	}else{//insert
			    		String insertHtmlSql = "insert into IREPORT_HTML(report_id,\"TYPE\",start_time,path,updated_dt) " +
			    				"values('" + entity.getReport_id() + "','" + htmlType + 
			    				"',to_date('" + htmlStartTime + "','yyyy-mm-dd hh24:mi:ss'),'" + htmlPath + "'," + DBUtils.getDbSysdateKeyword() + ")";
			    		jdbcTemplate.update(insertHtmlSql);
			    	}
			    	
			    }
			    
			    //保存EMAIL
			    String emailStartTime = request.getParameter("email_start_time");
			    String emailPeriod = request.getParameter("email_period");
			    String emailHost = request.getParameter("email_host");
			    String emailEmail = request.getParameter("email_email");
			    if(emailStartTime!=null&&emailPeriod!=null&&
			    		emailHost!=null&&emailEmail!=null&&
			    		emailStartTime.length()>0&&emailPeriod.length()>0&&
			    		emailHost.length()>0&&emailEmail.length()>0){
			    	String emailCountSql = "select count(1) from IREPORT_EMAIL where report_id='" + entity.getReport_id() + "'";
			    	int emailCount = jdbcTemplate.queryForInt(emailCountSql);
			    	if(emailCount>0){//update
			    		String updateEmailSql = "update IREPORT_EMAIL set start_time=to_date('" + emailStartTime + "','yyyy-mm-dd hh24:mi:ss')," +
			    				"period='" + emailStartTime + "',email='" + emailEmail + "',host='" + emailHost + "',updated_dt=" + DBUtils.getDbSysdateKeyword() +
			    				" where report_id='" + entity.getReport_id() + "'";
			    		jdbcTemplate.update(updateEmailSql);
			    	}else{//insert
			    		String insertEmailSql = "insert into IREPORT_EMAIL(report_id,start_time,period,email,host,updated_dt) " +
			    				"values('" + entity.getReport_id() + "',to_date('" + emailStartTime + "','yyyy-mm-dd hh24:mi:ss'),'" + 
			    				emailPeriod + "','" + emailEmail + "','" + emailHost + "'," + DBUtils.getDbSysdateKeyword() + ")";
			    		jdbcTemplate.update(insertEmailSql);
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
			String kpiCode, String moId, String startTime, String endTime) {
		List<Map<String, Object>> kpiInstanceList = null;
		try {
			
			String baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value " + 
				"where amp_inst_id='%s' and kpi_code = '%s' " + 
				"and mo_id='%s' " + 
				"and to_char(kpi_time,'YYYYMMDDHH24MISS') between '%s' and '%s' ";
			startTime = startTime.replace("-", "").replace(" ", "").replace(":", "");
			endTime = endTime.replace("-", "").replace(" ", "").replace(":", "");
			String getKpiInstanceSql = String.format(baseSql,ampInstId,kpiCode,moId,startTime,endTime);
			kpiInstanceList = jdbcTemplate.queryForList(getKpiInstanceSql);
			return kpiInstanceList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" getKpiInstance",e);
		}
		return kpiInstanceList;
	}

	public List<Map<String, Object>> getKpiChartData(String ampInstId,
			String kpiCode, String moId, String startTime, String endTime) {
		List<Map<String, Object>> kpiValueList = null;
		try {
			
			List<Map<String, Object>> kpiInstanceList = getKpiInstance(ampInstId,kpiCode,moId,startTime,endTime);
			
			String ins1 = "";
			String ins2 = "";
			
			for(int i=0;i<kpiInstanceList.size();i++){
				ins1 = ins1 + "sum(instance" + i + ") as \"instance" + i + "\",";
				ins2 = ins2 + "case when instance='" + kpiInstanceList.get(i).get("instance").toString() + "' then num_value else 0 end as instance" + i + ",";
			}
			String kpiValueSql = 
				"select " +  
				ins1 +
				"to_char(time,'mm-dd HH24:mi') as \"time\" "+
				"from( " +
				"select " + 
				ins2 +
				"kpi_time as time " +
				// modify by mark start 2013-9-28
				"from tf_avmon_kpi_value " +
				// modify by mark end 2013-9-28
				"where " +
				"amp_inst_id='%s' and kpi_code = '%s' " +
				"and mo_id='%s' "+
				"and to_char(kpi_time,'YYYYMMDDHH24MISS') between '%s' and '%s' )" +
				"group by time " +
				"order by time";
			startTime = startTime.replace("-", "").replace(" ", "").replace(":", "");
			endTime = endTime.replace("-", "").replace(" ", "").replace(":", "");
			kpiValueSql = String.format(kpiValueSql,ampInstId,kpiCode,moId,startTime,endTime);
			kpiValueList = jdbcTemplate.queryForList(kpiValueSql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" getKpiInstance",e);
		}
		return kpiValueList;	
	}	

}
