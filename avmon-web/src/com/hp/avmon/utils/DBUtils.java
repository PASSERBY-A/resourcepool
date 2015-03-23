package com.hp.avmon.utils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;




import com.hp.avmon.common.SpringContextHolder;


public class DBUtils {
    
    private static final Logger log = LoggerFactory.getLogger(DBUtils.class);

    private static JdbcTemplate jdbcTemplate=null;

    private static Integer dbType = -1;
    
    public static void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        DBUtils.jdbcTemplate = jdbcTemplate;
    }

    
    public static boolean isSnmp(String moId){
    	JdbcTemplate jdbcTemplate=SpringContextHolder.getBean("jdbcTemplate");
    	String sql="select protocal_method from td_avmon_mo_info where protocal_method = 'SNMP' and mo_id='"+moId+"'";
    	List res = jdbcTemplate.queryForList(sql);
    	if(res.size()>0)
    	{
    		return true;
    	}
    	else{
    		return false;
    	}
    	
    }
    public static boolean isIpmi(String moId){
    	JdbcTemplate jdbcTemplate=SpringContextHolder.getBean("jdbcTemplate");
    	String sql="select protocal_method from td_avmon_mo_info where protocal_method = 'IPMI' and mo_id='"+moId+"'";
    	List res = jdbcTemplate.queryForList(sql);
    	if(res.size()>0)
    	{
    		return true;
    	}
    	else{
    		return false;
    	}
    	
    }
//    public static JdbcTemplate getJdbcMemory() {
//        return jdbcMemory;
//    }
//
//    public static void setJdbcMemory(JdbcTemplate jdbcMemory) {
//        MemDB.jdbcMemory = jdbcMemory;
//    }



    
    public static String formatStr(Object value){
        String str=String.valueOf(value);
        if(value==null){
            return "''";
        }
        else{
            String s=(String) str;
            s=s.replaceAll("'", "''");
            str=s;
        }
        //System.out.println(value+"  --->  "+str);
        if(value instanceof String){
            if(str.equals("null")){
                return "''";
            }
            return new String("'"+str+"'");
        }
        else{
            return new String(str);
        }
    }
    
//    public static int formatInt(Object o){
//        if(o==null) return 0;
//        if(o instanceof BigDecimal){
//            return ((BigDecimal)o).intValue();
//        }
//        else if( o instanceof String){
//            if("".equals(o)){
//                return 0;
//            }
//            return Integer.valueOf((String) o);
//        }
//        else {
//            String s=String.valueOf(o);
//            return Integer.valueOf(s);
//        }
//        //return 0;
//    }

    public static Object formatDate(Object o) {
        if(o==null){
            return "null";
        }
        String s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)o);
        return "TIMESTAMP '"+s+"'";
    }
    
    public static String formatDateOracle(Object o)
    {
        if(o == null)
        {
            return "null";
        } else
        {
            String s = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format((Date)o);
            return String.format("to_date('%s','yyyy-MM-dd HH24:mi:ss')", new Object[] {
                s
            });
        }
    }

    public static double formatDouble(Object o) {
        if(o==null) return 0.0;
        if(o instanceof BigDecimal){
            return ((BigDecimal)o).doubleValue();
        }
        else if( o instanceof String){
            return Double.valueOf((String) o);
        }
        else {
            String s=String.valueOf(o);
            return Double.valueOf(s);
        }
    }

    public static float formatFloat(Object o) {
        if(o==null) return 0;
        if(o instanceof BigDecimal){
            return ((BigDecimal)o).floatValue();
        }
        else if( o instanceof String){
            return Float.valueOf((String) o);
        }
        else {
            String s=String.valueOf(o);
            return Float.valueOf(s);
        }
    }

    public static int toInt(Object o){
        if(o==null) return 0;
        if(o instanceof BigDecimal){
            return ((BigDecimal)o).intValue();
        }
        else if( o instanceof String){
            return Integer.valueOf((String) o);
        }
        else if( o instanceof Integer){
            return (Integer) o;
        }
        return 0;
    }
    
    
    //  >>>>>>>>>>>>>>>>>>>>>>>>>   多数据库支持   >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public static int getDbType(){
    	if(dbType == -1) {
    		initDBType();
    	}
    	return dbType;
    }
    
    public static boolean isOracle() {
    	return getDbType() == Constants.DB_ORACLE;
    }
    
    public static boolean isPostgreSql() {
    	return getDbType() == Constants.DB_POSTGRESQL;
    }
    
    private static void initDBType(){
  	 JdbcTemplate jdbc=SpringContextHolder.getBean("jdbcTemplate");
      String driverName = "";
      try {
				driverName = jdbc.getDataSource().getConnection().getMetaData().getDriverName();
				log.info("Database driver name : " + driverName);
			} catch (SQLException e) {
				e.printStackTrace();
			}
      if(driverName.toLowerCase().indexOf("oracle") >= 0)
      	dbType = Constants.DB_ORACLE;
      else if(driverName.toLowerCase().indexOf("postgresql") >= 0)
      	dbType = Constants.DB_POSTGRESQL;
      else
      	throw new RuntimeException("未知数据库类型！");
    }
   // startL 起始记录，从0开始
    public static String pagination(String sql, int startL, int limitL) {
    	String psql = sql;
    	getDbType();
    	if(dbType == Constants.DB_ORACLE) {
    		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
    		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
    		paginationSQL.append(sql);
    		paginationSQL.append(" ) temp where ROWNUM <= " + (startL + limitL));//limitL*startL);
    		paginationSQL.append(" ) WHERE num > " + (startL));
    		psql = paginationSQL.toString();
    	}
    	if(dbType == Constants.DB_POSTGRESQL) {
    		if(startL==1){
    			startL = 0;
    		}
    		psql = sql + " limit " + limitL + " offset " + startL;
    	}
    	
    	return psql;
    }
    // jqw分页
    public static String paginationforjqw(String sql, int startL, int limitL) {
    	String psql = sql;
    	getDbType();
    	
    	if(dbType == Constants.DB_ORACLE) {
    		if(startL==0){
        		startL=1;
        	}
    		else{
    			startL=startL+1;
    		}
    		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
    		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
    		paginationSQL.append(sql);
    		paginationSQL.append(" ) temp where ROWNUM <= " + (limitL*startL));//limitL*startL);
    		paginationSQL.append(" ) WHERE num > " + (startL-1)*limitL);
    		psql = paginationSQL.toString();
    	}
    	if(dbType == Constants.DB_POSTGRESQL) {
    		if(startL<1){
    			startL = 0;
    		}
    		psql = sql + " limit " + limitL + " offset " + (limitL*startL);
    		//psql = sql + " limit " + limitL + " offset " + startL;
    	}
    	return psql;
    }
    
    public static String getDbSysdateKeyword() {
    	if(isOracle())
    		return "sysdate";
    	if(isPostgreSql())
    		return "localtimestamp";
    	return "";
    }

    public static Object getDBToDateFunction() {
        if(isOracle()){
            return "to_date";
        }
        else if(isPostgreSql()){
            return "to_timestamp";
        }
        else{
            return "to_date";
        }
    }


  public static String getDBCurrentDateFunction(){
    	
        if(isOracle()){
            return "sysdate";
        }
        else if(isPostgreSql()){
            return "now()";
        }
        else{
            return "currentdate()";
        }
    }
}
