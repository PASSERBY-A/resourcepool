package com.hp.avmon.home.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.common.Config;

@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class LicenseService {

	private static final Log log = LogFactory
			.getLog(LicenseService.class);
	
	private static final String menu = "'ALARM_CENTER_MODULE','PERFORMANCE_MODULE','REPORT_MODULE',"
			+ "'CONFIG_MODULE','RESOURCE_MODULE','SYSTEM_MODULE',"
			+ "'PERFORMANCE_ANALYZE_MODULE','DISCOVERY_MODULE','NETWORK_MODULE','AGENT_MODULE','AGENTLESS_MODULE'";
	private static final String[] mainMenu = new String[]{"ALARM_CENTER_MODULE","PERFORMANCE_MODULE","REPORT_MODULE",
			"CONFIG_MODULE","RESOURCE_MODULE","SYSTEM_MODULE",
			"PERFORMANCE_ANALYZE_MODULE","DISCOVERY_MODULE","NETWORK_MODULE","AGENT_MODULE","AGENTLESS_MODULE"} ;
	private static final int menuSize = 11;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String getLicensePath() {
		return Config.getInstance().getLicensePath();
	}
	
	/**
	 * 获取用户的主页菜单权限，0为有权限，1为没有license，2为没有授权
	 * 数组值以此代表菜单【告警中心、性能管理、报表管理、配置管理、资源管理、系统管理、性能分析、自动发现、简单网络管理、agent管理、agentless管理】
	 * @param userId
	 * @return
	 */
	public int[] getUserMainMenu(String userId){
		String licenseMenu = findLicenseModuleInfo();
		int[] menu = new int[menuSize];
		if(licenseMenu.length()<1){
			for(int i=0;i<menu.length;i++){
	    		menu[i] = 1;
	    	}
		}else{
			String[] lm = licenseMenu.split(",");
			String[] userPriv = getUserPriv(userId);
			
			for(int i=0;i<lm.length;i++){
				if(lm[i].equals("0")&&userPriv[i].equals("0")){
					menu[i] = 0;
				}else if(lm[i].equals("1")){
					menu[i] = 1;
				}else if(userPriv[i].equals("2")){
					menu[i] = 2;
				}
			}
		}
		System.out.println("=========="+"getUserMainMenu"+menu);
		
		return menu;
	}
	
	public Map<String,Integer> getUserMainMenuMap(String userId){
		String licenseMenu = findLicenseModuleInfo();
		
		Map<String,Integer> menuMap = new HashMap<String,Integer>();
		
		
		for(int i=0;i<this.menu.split(",").length;i++)
		{
			menuMap.put(mainMenu[i], 0);
		}
		
	/*	Map<String,Integer> menuMap = new HashMap<String,Integer>();
		if(licenseMenu.length()<1){//若解析license获取的菜单部分长度为0则表明license为旧license，没办法使用即所有菜单均无权限
			for(int i=0;i<mainMenu.length;i++){
	    		menuMap.put(mainMenu[i],1);
	    	}
		}else{
			String[] lm = licenseMenu.split(",");
			String[] userPriv = null;
			if(!"admin".equalsIgnoreCase(userId)){
				userPriv = getUserPriv(userId);
			}else{
				userPriv = new String[menuSize];
				for(int i=0;i<menuSize;i++){
					userPriv[i] = "0";
		    	}
			}
			for(int i=0;i<lm.length;i++){
				if(lm[i].equals("0")&&userPriv[i].equals("0")){
					menuMap.put(mainMenu[i],0);
				}else if(lm[i].equals("1")){
					menuMap.put(mainMenu[i],1);
				}else if(userPriv[i].equals("2")){
					menuMap.put(mainMenu[i],2);
				}
			}
		}
		*/
		
	   System.out.println("=========="+menuMap);
		
		
		
		return menuMap;
	}
	
	private String[] getUserPriv(String userId){
		// 获取当前用户的权限
		String sql = String
				.format("select upper(d.module_id) as \"module_id\" "
						+ "from PORTAL_USERS a,PORTAL_USERS_PORTAL_ROLES b,"
						+ "PORTAL_ROLES_PORTAL_MODULES c,PORTAL_MODULES d "
						+ "where a.id=b.PORTAL_USER_ROLES_ID "
						+ "and b.PORTAL_ROLE_ID=c.PORTAL_ROLE_MODULES_ID "
						+ "and c.PORTAL_MODULE_ID=d.id and "
						+ "a.USER_ID='%s' and "
						+ "upper(d.module_id) in (" + menu + ")", userId);
		List list = jdbcTemplate.queryForList(sql);
		String modules = ",";
		if (list.size() > 0) {
			for (Map m : (List<Map>) list) {
				modules += m.get("module_id") + ",";
			}
		}
//		String[] mainMenu = new String[]{"ALARM_CENTER_MODULE","PERFORMANCE_MODULE","REPORT_MODULE",
//				"CONFIG_MODULE","RESOURCE_MODULE","SYSTEM_MODULE",
//				"PERFORMANCE_ANALYZE_MODULE","DISCOVERY_MODULE","NETWORK_MODULE","AGENT_MODULE"} ;
		String[] sysPriv = new String[menuSize];
		for(int i=0;i<mainMenu.length;i++){
			if(modules.indexOf(mainMenu[i])>-1){
				sysPriv[i] = "0";
			}else{
				sysPriv[i] = "2";
			}
		}
		
		 System.out.println("=========="+sysPriv);
		
		return sysPriv;
	}
	
	/**
	 * 获取数据库License
	 * @return
	 */
	public String findLicenseInfo() {
		String sql = "select * from portal_params where param_code='licenseInfo' order by id";
		List<HashMap> licenseInfo = jdbcTemplate.queryForList(sql);
		if(licenseInfo.size()>0){
			return licenseInfo.get(0).get("param_value").toString();
		}else{
			return "";
		}
	}
	
	/**
	 * 获取License除模块外的信息
	 * @return
	 */
	public String findLicenseInfoS() {
		String license = findLicenseInfo();
		if(license.length()>45){
			return license.substring(0,45);
		}else{
			return license;
		}
	}
	
	private String getModule(String module){
		String ok = "([abcdefghijkNOPQRSTUVWXYZ])";
        String na = "([lmnopqrstuvwxyzABCDEFGHIJKLM])";
        Pattern p = Pattern.compile(ok);
    	Matcher m = p.matcher(module);  
    	while(m.find()) {   
    		module = module.replace(m.group(1), "0,");  
    	} 
    	Pattern p1 = Pattern.compile(na);
    	Matcher m1 = p1.matcher(module);  
    	while(m1.find()) {   
    		module = module.replace(m1.group(1), "1,");  
    	}
    	if(module.length()>1){
    		module = module.substring(0,module.length()-1);
    	}
    	return module;
	}
	
	/**
	 * 获取License模块
	 * @return
	 */
	public String findLicenseModuleInfo() {
		String license = findLicenseInfo();
		if(license.length()>45){
			license = license.substring(46,license.length());
			return getModule(license);
		}else{
			return "";
		}
	}

	/**
	 * 获取初始化注册码
	 * 
	 * @param encryptCpuid
	 * @return
	 */
	public String getInitRegisCode(String encryptCpuid) {

		char[] sAr = encryptCpuid.toCharArray();
		char[] sb = new char[24];
		StringBuffer sbfer = new StringBuffer();

		for (int i = sAr.length; i > 0; i--) {
			long serL = charToLong(sAr[i - 1]);
			long serDcd = serL;
			serDcd ^= 3 % 9;
			if (serDcd < 48L || (57L < serDcd & serDcd < 65L) || serDcd > 90) {
				serDcd = serL;
			}
			sb[i - 1] = longTochar(serDcd);
		}

		for (int j = 0; j < sb.length; j++) {
			sbfer.append(sb[j]);
		}
		return sbfer.toString();
	}

	/**
	 * 
	 * @param serChar
	 * @return
	 */
	public long charToLong(char serChar) {
		int i = serChar;
		return new Integer(i).longValue();
	}

	/**
	 * 
	 * @param serLong
	 * @return
	 */
	public char longTochar(long serLong) {
		int i = (int) serLong;
		char car = (char) i;
		return car;

	}

	/**
	 * 从文件中读取server的cpuID
	 * 
	 * @return
	 */
	public String getServerCpuIdFromFile() throws Exception{
		//TODO 注册模块
	/*	
		StringBuffer cpuidStrBf = new StringBuffer();
		// 产生文件
		callVbExeFile();
		// 产生文件

		String cpuidFullPath = getLicensePath() + "mycpuid.txt";
		
		File file = new File(cpuidFullPath);
		if (!file.exists()) {
			log.info("mycpuid is not exist!");
		}
		Reader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file));
			int buffer = 0;
			while ((buffer = reader.read()) != -1) {
				if ((char) buffer != '\n' && (char) buffer != '\r'
						&& (char) buffer != ' ') {
					cpuidStrBf.append((char) buffer);
				}
			}
			reader.close();

		} catch (Exception e) {
			log.error("mycpuid path error!" + cpuidFullPath);
			throw e;
		} finally{
			if(reader!=null){
				reader.close();
			}
		}
		return cpuidStrBf.toString();
		*/
		
		return "liqiang";
	}

	/**
	 * 获取所有的RoleIds
	 * @param userId
	 * @param userMap
	 */
	public Map queryRoleAndDepByUserId(String userId)
    {
          Map userMap =new HashMap();
          String queryRoleSql=String.format("select role_id from portal_roles  left join portal_users_portal_roles on portal_roles.id =portal_users_portal_roles.portal_role_id where portal_users_portal_roles.portal_user_roles_id=(select id from portal_users where user_id='%s')",userId);
        
          List<String> roles = jdbcTemplate.queryForList(queryRoleSql, String.class);
          
          StringBuffer role=new StringBuffer("");
          
          for(String r:roles)
          {
              role.append(r+",");
          }
          System.out.println(role);
          if(role.length()>1)
          {
        	  userMap.put("role_id", role.deleteCharAt(role.length()-1).toString());
          }
         
         // userMap.put("role_id", role.deleteCharAt(role.length()-1).toString());
          return userMap;
    }

	/**
	 * 解密
	 * 
	 * @param encryptCpuid
	 * @return
	 */
	public String getCpuidFromEncrypt(String encryptCpuid) {

		char[] sAr = encryptCpuid.toCharArray();
		char[] sb = new char[36];
		StringBuffer sbfer = new StringBuffer();

		for (int i = sAr.length; i > 0; i--) {
			long serL = charToLong(sAr[i - 1]);
			long serDcd = serL;
			serDcd ^= 5 % 9;
			if (serDcd < 48L || (57L < serDcd & serDcd < 65L) || serDcd > 90) {
				serDcd = serL;
			}
			sb[i - 1] = longTochar(serDcd);
		}

		for (int j = 0; j < sb.length; j++) {
			sbfer.append(sb[j]);
		}
		//log.info("解密后的字符串是" + sbfer.toString());
		return sbfer.toString();
	}

	public void callVbExeFile() {
		String OS = System.getenv().get("OS");
		if (OS == null || "".equals(OS)) {
			callLinuxCpuId();
		} else {
			if (OS.contains("Windows")) {
				callWindowsCpuId();
			} else if (OS.contains("Linux")) {
				callLinuxCpuId();
			}
		}

	}

	public void callLinuxCpuId() {
		Runtime rt = Runtime.getRuntime();
		Process p = null;
		try {
			String vbexepath = getLicensePath() + "/getcpusn";
			p = rt.exec(vbexepath);

			InputStreamReader isr_normal = new InputStreamReader(p.getInputStream());
			int ch = 0;
			StringBuffer strbuf = new StringBuffer();
			while ((ch = isr_normal.read()) != -1) {
				strbuf.append((char) ch);
			}
			p.waitFor();

		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public void callWindowsCpuId() {
		Runtime rt = Runtime.getRuntime();
		Process p = null;
		try {
			String exportPath = getLicensePath();
			String vbexepath = "cmd.exe /c" + exportPath + "/" + "vbcpuid_server.exe";
			p = rt.exec(vbexepath);

			InputStreamReader isr_normal = new InputStreamReader(
					p.getInputStream());
			int ch = 0;
			StringBuffer strbuf = new StringBuffer();
			while ((ch = isr_normal.read()) != -1) {
				strbuf.append((char) ch);
			}
			p.waitFor();

		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public String changeLocation(String str) {
		String result = null;
		String rlt = null;
		try {
			//log.info("changeLocation>>>>>>>>>>>>:" + str);
			String aa = str.replaceAll("-", "").substring(0, 36);
			int v = new Integer(str.substring(44)).intValue();
			char[] cary = aa.toCharArray();
			List<Character> list = new ArrayList<Character>();
			List<Character> listTo = new ArrayList<Character>();
			char[] caryTo = new char[36];

			for (int i = 0; i < cary.length; i++) {
				list.add(cary[i]);
			}

			for (int i = list.size() - 1; i >= 0; i--) {
				int to = 0;
				to = i ^ v;

				caryTo[to] = list.get(i);

			}

			for (int i = 0; i < caryTo.length; i++) {
				listTo.add(caryTo[i]);
			}

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < listTo.size(); i++) {
				sb.append(listTo.get(i));
			}

			String after = sb.toString();
			String cut1 = after.substring(0, 14);
			String cut2 = after.substring(14, 24);
			String cut3 = after.substring(24);

			result = cut2 + cut1 + cut3;

			rlt = result.substring(0, 4) + "-" + result.substring(4, 8) + "-"
					+ result.substring(8, 12) + "-" + result.substring(12, 16)
					+ "-" + result.substring(16, 20) + "-"
					+ result.substring(20, 24) + "-" + result.substring(24, 28)
					+ "-" + result.substring(28, 32) + "-"
					+ result.substring(32, 36);

		} catch (Throwable e) {
			// e.printStackTrace();
		}

		return rlt;
	}

	/**
	 * 从文件中得到初始注册码
	 * 
	 * @return
	 */
	public String getInitRgsCodeFromFile() throws Exception{
		try {
			return getInitRegisCode(getServerCpuIdFromFile());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	public String getRgsCode(){
		String license = findLicenseInfoS();
		if(license.length()<1){
			return "";
		}
		String regigtSerlNumWith = changeLocation(license);
		
		if(regigtSerlNumWith==null){
			return "";
		}
		
		String regigtSerlNum = regigtSerlNumWith.replaceAll("-", "");
		
		String regisCode = getCpuidFromEncrypt(regigtSerlNum);// 解密后的字符串
		return regisCode;
	}
	
	private String replaceStr(String result){
		result = result.replace("H","0");
        result = result.replace("Y","1");
        result = result.replace("M","2");
        result = result.replace("I","3");
        result = result.replace("Q","4");
        result = result.replace("#","5");
        result = result.replace("P","6");
        result = result.replace("!","7");
        result = result.replace("&","8");
        result = result.replace("B","9");
		return result;
	}
	
	/**
	 * 获取最大监控数量
	 */
	public int getMaxMoCount() {
		String regisCode = getRgsCode();// 解密后的字符串
		if(regisCode.length()<1){
			return 0;
		}
		String temp_ = regisCode.substring(24);
		temp_ = replaceStr(temp_);
		return Integer.valueOf(temp_.substring(0, temp_.indexOf("F")));
	}

	/**
	 * 判断注册码的CPU是否正确
	 * 
	 * @param code
	 * @return
	 */
	public boolean isRgsCodeRigthForRegis() throws Exception{
		try {
			String regisCode = getRgsCode();// 解密后的字符串
			
			if(regisCode.length()<1){
				return false;
			}
			
			String cpuid = regisCode.substring(0, 16);
			String initRegisCode = getInitRegisCode(cpuid);
			
			String cpuIdFromFile = getServerCpuIdFromFile();
			
			//TODO 注册码模块 
			return true;
/*			if (initRegisCode.startsWith(cpuIdFromFile)) {
				return true;
			} else {
				return false;
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("isRgsCodeRigthForRegis function error!");
			e.printStackTrace();
			throw e;
		}
	}

	public void delFile() {
		String cpuidFullPath = getLicensePath() + "/mycpuid.txt";
		File file = new File(cpuidFullPath);
		file.delete();
	}

	
	/**
	 * 判断是否过期
	 * 
	 * @return
	 */
	public boolean isDateValidLicense() {
		
		System.out.println("true==========");
		
		return true;
		/*String regisCode = getRgsCode();// 解密后的字符串
		if(regisCode.length()<1){
			return false;
		}
		// 到期时间：
		String overTimeStr = regisCode.substring(16);
		overTimeStr = replaceStr(overTimeStr);
		boolean isValid = false;
		try {
			String tmstpStr = this.getOverDateLicense(overTimeStr);
			if(!checkDate(tmstpStr)){
				return false;
			}
			
			if (getCurrentTime().after(Timestamp.valueOf(tmstpStr+" 24:00:00"))) {
				isValid = false;
			} else {
				isValid = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isValid;*/
	}
	
	public String getOverDate(){
		String regisCode = getRgsCode();// 解密后的字符串
		
		// 到期时间：
		String overTimeStr = regisCode.substring(16);
		overTimeStr = replaceStr(overTimeStr);
		String tmstpStr = this.getOverDateLicense(overTimeStr);
		
		return tmstpStr;
	}
	
	public String getOverDateLicense(String delDateStr) {
		String overData = delDateStr.substring(0, 4) + "-"
				+ delDateStr.substring(4, 6) + "-" + delDateStr.substring(6, 8);
		return overData;
	}

	public static Timestamp getCurrentTime() {
		try {
			Calendar cal = Calendar.getInstance(TimeZone.getDefault());
			String DATE_FORMAT_HMS_d = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat formatwithHMSd = new SimpleDateFormat(DATE_FORMAT_HMS_d);
			formatwithHMSd.setTimeZone(TimeZone.getDefault());
			Date currentdate_d = formatwithHMSd.parse(formatwithHMSd.format(cal.getTime()));
			long time = currentdate_d.getTime();
			Timestamp currentTime = new Timestamp(time);
			return currentTime;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}

	}

	public void test() {
		String regigtSerlNumWith = changeLocation("0646-MH1G-LMLY-EDML-DEDC-66EE-MMCL-MMMM-MMMM3");//-XScXTZqII");
		//String regigtSerlNumWith = changeLocation("0646-MH1G-LMLY-EDML-DEDC-66EE-MMCL-MMMM-MMMM3-XScXTZqII");
		String regigtSerlNum = regigtSerlNumWith.replaceAll("-", "");
		
		String regisCode = getCpuidFromEncrypt(regigtSerlNum);// 解密后的字符串
		// 初始注册码：
		
		String cpuid = regisCode.substring(0, 16);
		String initRegisCode = getInitRegisCode(cpuid);
		System.out.println("initRegisCode>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ initRegisCode);
		// 到期时间：
		String delDateStr = regisCode.substring(16);

		String overDateTime = getOverDateLicense(delDateStr);

		System.out.println("overDateTime>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ replaceStr(overDateTime));

		// 最大用户数：
		String temp_ = regisCode.substring(24);
		String userMAXCount = temp_.substring(0, temp_.indexOf("F"));
		System.out.println("userMAXCount>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
				+ replaceStr(userMAXCount));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		LicenseService licensse = new LicenseService();
//		licensse.test();
		
//		System.out.println(checkDate("2013-03-30"));
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
//
//		System.out.print(Timestamp.valueOf("9013-92-26 00:00:00"));
//		Date d = null;
//		try {
//			d = dateFormat.parse("9013-92-26 00:00:00");
//			System.out.println(d);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}   
//		System.out.println("9013-92-26 00:00:00".substring(5, 6));
		//0656-MH9G-HYTY-EDHM-DEDC-66EE-CMMY-MMMM-MMMM3-VQZVQXcUUO
		String module = "VQZVQXcUUO";
		String ok = "([abcdefghijkNOPQRSTUVWXYZ])";
        String na = "([lmnopqrstuvwxyzABCDEFGHIJKLM])";
        Pattern p = Pattern.compile(ok);
    	Matcher m = p.matcher(module);  
    	while(m.find()) {   
    		module = module.replace(m.group(1), "0,");  
    	} 
    	
    	Pattern p1 = Pattern.compile(na);
    	Matcher m1 = p1.matcher(module);  
    	while(m1.find()) {   
    		module = module.replace(m1.group(1), "1,");  
    	}
    	System.out.println(module);
	}

	public void saveOrUpdateLicense(String license,ResourceBundle bundle) throws Exception {
		String licenseName = bundle.getString("licenseName");
		try {
			String deleteSql = "delete from portal_params where param_code='licenseInfo'";
			
			String insertSql = "insert into portal_params(param_code,param_value,param_name) " +
					"values('licenseInfo','" + license + "','"+licenseName+"')";
			jdbcTemplate.execute(deleteSql);
			jdbcTemplate.execute(insertSql);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + "saveOrUpdateLicense ", e);
			throw e;
		}
	}
	
	public static boolean checkDate(String date) {
		String eL= "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-9]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(date);
		boolean b = m.matches();
		return b;
	}

	public int moCount(){
		String countMoSql = "select count(1) from td_avmon_mo_info";
		return jdbcTemplate.queryForInt(countMoSql);
	}
	
	/**
	 * 验证是否mo数量符合条件
	 * @param addCount
	 * @return
	 */
	public boolean checkMoCount(int addCount){
		int maxMoCount = getMaxMoCount();
		int moCount = moCount();
		return (addCount+moCount)<=maxMoCount;
	}
	
}
