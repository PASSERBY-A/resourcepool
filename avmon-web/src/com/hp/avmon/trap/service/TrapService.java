package com.hp.avmon.trap.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.home.service.LicenseService;
import com.hp.avmon.utils.DBUtils;
import com.hp.avmon.utils.SqlManager;

/**
 * @author muzh
 *
 */
@Service
@SuppressWarnings({ "unchecked"})
public class TrapService {

	private static final Log logger = LogFactory.getLog(TrapService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LicenseService licenseService;

	private String generatPageSqlForJQW(String sql,String limit,String start){
		Integer limitL = Integer.valueOf(limit);
		Integer startL = Integer.valueOf(start);
		
		return DBUtils.pagination(sql, startL, limitL);
	}
	
	public List<Map<String, Object>> getTrapList(String flagType,
			String sortName, String sortOrder,
			String limit, String start, String oidValue) throws Exception{
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		try{
			String whereCondition = "where 1=1";
			if("2".equals(flagType)){
				whereCondition = "where flag=0";
			}else if("1".equals(flagType)){
				whereCondition = "where flag=1";
			}
			if(oidValue!=null&&oidValue.length()>0){
				whereCondition = whereCondition + " and (trap_content like'%" + oidValue + "%' or oid_list like '%" + oidValue + "%')";
			}
			if(sortName==null) sortName="alarm_count";
			if(sortOrder==null) sortOrder="desc";
			String sql = String.format(
					SqlManager.getSql(TrapService.class, "trapList"),
					whereCondition,sortName,sortOrder);

			String querySql = generatPageSqlForJQW(sql, limit, start);
			listMap = jdbcTemplate.queryForList(querySql);
		}catch(Exception e){
			logger.error(this.getClass().getName()+" getTrapList()",e);
			throw e;
		}
		
		return listMap;
	}
	
	public int getTrapListCount(String flagType, String oidValue) throws Exception{
		try{
			String whereCondition = "where 1=1";
			if("2".equals(flagType)){
				whereCondition = "where flag=0";
			}else if("1".equals(flagType)){
				whereCondition = "where flag=1";
			}
			if(oidValue!=null&&oidValue.length()>0){
				whereCondition = whereCondition + " and (trap_content like'%" + oidValue + "%' or oid_list like '%" + oidValue + "%')";
			}
			String sql = String.format(SqlManager.getSql(TrapService.class,
					"trapListCount"), whereCondition);
			int n = jdbcTemplate.queryForInt(sql);

			return n;
			
		}catch(Exception e){
			logger.error(this.getClass().getName()+" getTrapListCount()",e);
			throw e;
		}
	}

	public List<Map<String, Object>> getOidListByKey(String trapKey) throws Exception{
		try{
			List<Map<String, Object>> oidList = new ArrayList<Map<String, Object>>();
			String sql = String.format(SqlManager.getSql(TrapService.class,
					"getTrapByKey"), trapKey);
			List<Map<String, Object>> listMap = jdbcTemplate.queryForList(sql);
			if(listMap.size()>0){
				Map<String, Object> m = listMap.get(0);
				if(m.get("trap_content")!=null){
					String trapContent = m.get("trap_content").toString();
					String trapOidValues[] = trapContent.split("&&");
					for(String oidValue : trapOidValues){
						String s[] = oidValue.split("=");
						Map<String, Object> oid = new HashMap<String, Object>();
						oid.put("oid", s[0]);
						oid.put("oidValue", s[1]);
						oidList.add(oid);
					}
				}
			}
			return oidList;
		}catch(Exception e){
			logger.error(this.getClass().getName()+" getOidListByKey()",e);
			throw e;
		}
		
	}

	public void saveTrap(String trapKey, String oidList,
			String alarmTitle, String alarmGrade, String alarmTime,
			String alarmType,String alarmContent) throws Exception{
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (StringUtils.isEmpty(alarmTime)) {
				alarmTime=format.format(new Date());
			}
			if(trapKey!=null&&trapKey.length()>0){
				String sql = String.format(
						SqlManager.getSql(TrapService.class, "updateTrap"),
						oidList,alarmTitle,alarmGrade,alarmTime,alarmType,alarmContent,trapKey);
				jdbcTemplate.execute(sql);
			}else{//手动添加
				String insertSql = "insert into TD_AVMON_SNMP_TRAP(trap_key,trap_content,oid_list,alarm_title,alarm_content,alarm_time,alarm_grade,alarm_type,flag,trap_status) "
						+ "values('%s','%s','%s','%s','%s','%s','%s','%s',1,0)";
				oidList = getOid(alarmTitle,oidList);
				oidList = getOid(alarmContent,oidList);
				if (StringUtils.isEmpty(alarmTime)) {
					oidList+=format.format(new Date());
				}else{
					oidList = getOid(alarmTime,oidList);
				}
				
				if (!StringUtils.isEmpty(alarmGrade)) {
					oidList = getOid(alarmGrade,oidList);
				}
				
				if (!StringUtils.isEmpty(alarmType)) {
					oidList = getOid(alarmType,oidList);
				}
				
				oidList = oidList.replaceAll(";;", ";");
				String trapContent = "";
				for(String oid : oidList.split(";")){
					if(oid.length()>0){
						trapContent = trapContent + oid + "=" + oid + "&&";
					}
				}
				if(trapContent.length()>0) trapContent = trapContent.substring(0,trapContent.length()-2);
				String tarpKey = UUID.randomUUID().toString().replace("-", "");
				insertSql = String.format(insertSql,tarpKey,
						trapContent,oidList,alarmTitle,alarmContent,alarmTime,alarmGrade,alarmType);
				jdbcTemplate.execute(insertSql);
			}
			String oids = oidList.replaceAll(";", "','");
			oids = oids.substring(2,oids.length()-2);
			String updateOidSql = "update td_avmon_snmp_miboid set status='1' where oid_id in ("+oids+")";
			jdbcTemplate.execute(updateOidSql);
			
		}catch(Exception e){
			logger.error(this.getClass().getName()+" saveTrap()",e);
			throw e;
		}
	}
	
	private String getOid(String oid,String oidList){
		String oidListTemp = ";";
		Pattern p = Pattern.compile(".*?(\\{.+?\\})");
		Matcher m = p.matcher(oid);  
		while(m.find()) {  
			String x = m.group(1);
			x = x.substring(1,x.length()-1);
			if(oidListTemp.indexOf(";"+x+";")<0&&
					oidList.indexOf(";"+x+";")<0){
				oidListTemp = oidListTemp + x + ";";
			}
		}
		return oidList + (oidListTemp.length()>1?oidListTemp:"");
	}

	public Map<String, String> getOidRule(String trapKey) throws Exception{
		try{
			Map<String, String> oidRuleMap = new HashMap<String, String>();
			String sql = String.format(SqlManager.getSql(TrapService.class,
					"getTrapByKey"), trapKey);
			List<Map<String, Object>> listMap = jdbcTemplate.queryForList(sql);
			if(listMap.size()>0){
				Map<String, Object> m = listMap.get(0);
				String alarmTitle = m.get("alarm_title").toString();
				String alarmContent = m.get("alarm_content").toString();
				String alarmTime = m.get("alarm_time").toString();
				String alarmGrade = m.get("alarm_grade").toString();
				String alarmType = m.get("alarm_type").toString();
				oidRuleMap.put("alarmTitle", alarmTitle);
				oidRuleMap.put("alarmContent", alarmContent);
				oidRuleMap.put("alarmTime", alarmTime);
				oidRuleMap.put("alarmGrade", alarmGrade);
				oidRuleMap.put("alarmType", alarmType);
			}
			return oidRuleMap;
		}catch(Exception e){
			logger.error(this.getClass().getName()+" getOidRule()",e);
			throw e;
		}
	}

	public List<Map<String, Object>> getRuleOidList() throws Exception{
		List<Map<String, Object>> oidList = new ArrayList<Map<String, Object>>();
		try{
			
			String sql = "select oid_id as \"oid\",oid_id as \"oidValue\" from td_avmon_snmp_miboid where oid_type='trap' and (status!='1' or status is null)";
			
			oidList = jdbcTemplate.queryForList(sql);
			
		}catch(Exception e){
			logger.error(this.getClass().getName()+" getRuleOidList()",e);
			throw e;
		}
		return oidList;
	}
	
	public static void main(String[] args) {  String text = "{3}123{3}{10}";  

	Pattern p = Pattern.compile(".*?(\\{.+?\\})");
	
	Matcher m = p.matcher(text);  
	while(m.find()) {   System.out.println(m.group(1));  } 
	}

	public void deleteTrap(String trapKeys) throws Exception{
		try{
			List<Map<String, Object>> listMap = jdbcTemplate.queryForList("select oid_list as \"oidList\" from TD_AVMON_SNMP_TRAP where trap_key in ("+trapKeys+")");
			String oids = "";
			for(Map<String, Object> m : listMap){
				oids = oids + m.get("oidList");
			}
			oids = oids.replaceAll(";;", "','");
			oids = oids.replaceAll(";", "','");
			oids = oids.substring(2,oids.length()-2);
			String updateOidSql = "update td_avmon_snmp_miboid set status='0' where oid_id in ("+oids+")";
			jdbcTemplate.execute(updateOidSql);
			String deleteTrapSql = "delete from TD_AVMON_SNMP_TRAP where trap_key in ("+trapKeys+")";
			jdbcTemplate.execute(deleteTrapSql);
		}catch(Exception e){
			logger.error(this.getClass().getName()+" deleteTrap()",e);
			throw e;
		}
	}
	
	public void startTrap(String trapKeys) throws Exception{
		try{
			
			String deleteTrapSql = "update TD_AVMON_SNMP_TRAP set trap_status=0 where trap_key in ("+trapKeys+")";
			jdbcTemplate.execute(deleteTrapSql);
		}catch(Exception e){
			logger.error(this.getClass().getName()+" startTrap()",e);
			throw e;
		}
	}
	
	public void stopTrap(String trapKeys) throws Exception{
		try{
			
			String deleteTrapSql = "update TD_AVMON_SNMP_TRAP set trap_status=1 where trap_key in ("+trapKeys+")";
			jdbcTemplate.execute(deleteTrapSql);
		}catch(Exception e){
			logger.error(this.getClass().getName()+" stopTrap()",e);
			throw e;
		}
	}

}
