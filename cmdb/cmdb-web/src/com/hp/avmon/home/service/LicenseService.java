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

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String getLicensePath() {
		return Config.getInstance().getLicensePath();
	}
	
	/**
	 * 获取License
	 * @return
	 */
	public String findLicenseInfoS() {
		String sql = "select * from portal_params where param_code='licenseInfo' order by id";
		List<HashMap> licenseInfo = jdbcTemplate.queryForList(sql);
		if(licenseInfo.size()>0){
			return licenseInfo.get(0).get("param_value").toString();
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
	public String getServerCpuIdFromFile() {
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
			try {
				reader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return cpuidStrBf.toString();
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
	public String getInitRgsCodeFromFile() {
		return getInitRegisCode(getServerCpuIdFromFile());
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
	public boolean isRgsCodeRigthForRegis() {
		
		
		String regisCode = getRgsCode();// 解密后的字符串
		
		if(regisCode.length()<1){
			return false;
		}
		
		String cpuid = regisCode.substring(0, 16);
		String initRegisCode = getInitRegisCode(cpuid);
		
		String cpuIdFromFile =getServerCpuIdFromFile();
		
		if (initRegisCode.startsWith(cpuIdFromFile)) {
			return true;
		} else {
			return false;
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
		
		String regisCode = getRgsCode();// 解密后的字符串
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

		return isValid;
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
		String regigtSerlNumWith = changeLocation("0646-MH1G-LMLY-EDML-DEDC-66EE-MMCL-MMMM-MMMM3");
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
		LicenseService licensse = new LicenseService();
		licensse.test();
		
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
	}

	public void saveOrUpdateLicense(String license) throws Exception {
		try {
			String deleteSql = "delete portal_params where param_code='licenseInfo'";
			
			String insertSql = "insert into portal_params(param_code,param_value,param_name) " +
					"values('licenseInfo','" + license + "','License注册码')";
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

}
