package  com.hp.xo.uip.cmdb.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author andy
 * @version $Revision: 1.4 $
 */
public class ExtendDate extends Date {
	public static final String DATEFORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";
	public static final String DATEFORMAT_MILLISECOND = "yyyy-MM-dd HH:mm:ss.SSS";
	
	/** */
	private static final long serialVersionUID = 1L;

	/**
	 * get the current date no time
	 * @return
	 */
	public static Date getCurrentDateNoTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * @param date
	 * @return
	 */
	public static Date clearTimeFromDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	/**
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date resetTimeFromDate(Date date,int hour,int minute,int second){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * ��ݲ��������ڶ���
	 * @param year
	 * @param month
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date createDate(Integer year,Integer month,Integer day,Integer hour,Integer minute,Integer second){
		Calendar cal = Calendar.getInstance();
		Date date=new Date();
		cal.setTime(date);
		if(year!=null)
			cal.set(Calendar.YEAR, year);
		if(month!=null)
			cal.set(Calendar.MONTH, month);
		if(day!=null)
			cal.set(Calendar.DAY_OF_MONTH, day);
		if(hour!=null)
			cal.set(Calendar.HOUR_OF_DAY, hour);
		if(minute!=null)
			cal.set(Calendar.MINUTE, minute);
		if(second!=null)
			cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * �����������
	 * @param calendarField �����Ӧ��field
	 * @param value Ҫ���õ�ֵ
	 * @return
	 */
	public static Date setDateTime(Date date,Integer calendarField,Integer value){
		if(date!=null&&calendarField!=null&&value!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(calendarField, value);
			return cal.getTime();
		}
		return date;
	}
	/**
	 * @param date
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static Date setDateTime(Date date,Date timeDate){
		Calendar calTime=Calendar.getInstance();
		calTime.setTime(timeDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);		
		return cal.getTime();
	}

	public static String getNowDateShort() {
		java.text.SimpleDateFormat dateFormatShort = new java.text.SimpleDateFormat(
		"yyyyMMdd");
		String ret = dateFormatShort.format(new java.util.Date());
		if (ret == null) {
			ret = "";
		}
		return ret;

	}


	/**
	 * get the system's date
	 * @return
	 */
	public static String getSysDate() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ret = df.format(date);
		return ret;
	}

	/**
	 * @param scrollDate
	 * @return
	 */
	public static Date getTimingAssembledDate(String date, String time) {
		Date d = new Date();
		date = date + " " + time + ":00:01";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			d = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return d;
	}

	/**
	 * @param dateString
	 * @return
	 */
	public static Date formatDateFromString(String dateString){
		if(dateString==null||dateString.trim().length()==0)
			return null;
		Date returnDate=null;
		String formatString="yyyy-MM-dd";
		DateFormat format = new SimpleDateFormat(formatString);
		try {
			returnDate= format.parse(dateString);
		} catch (ParseException e) {
			returnDate=null;
		}
		return returnDate;
	}

	/**
	 * ��������ʽ�������ַ��ʽת��������
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static Date formatDateFromString(String dateString, String format) {
		SimpleDateFormat dataFormat = new SimpleDateFormat(format);
		try {
			Date date = dataFormat.parse(dateString);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}
	/**
	 * ��ʱ����ַ�ת����ʱ�������
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static Timestamp formatTimeStampFromString(String dateString, String format) {
		SimpleDateFormat dataFormat = new SimpleDateFormat(format);
		try {
			Date date = dataFormat.parse(dateString);
			Timestamp timeStamp=new Timestamp(date.getTime());
			return timeStamp;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * dateתString
	 * @param 
	 * @return
	 */
	public static String getStringDonversionDate(Date date) {

		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String str = formatDate.format(date);

		return str;
	}

	/**
	 * date format
	 * @param date
	 * @param formatString
	 * @return
	 */
	public static String dateFormatToString(Date date,String formatString){
		try {
			SimpleDateFormat formatDate = new SimpleDateFormat(formatString);
			String str = formatDate.format(date);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return date.toString();
		}
	}

	/**
	 * @param scrollDate
	 * @return
	 */
	public static Date getNowDateAheadTime(int scrollDate) {
		Date d = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = "";
		int date = 0;
		int month = 0;

		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(Calendar.DATE, scrollDate);
		month = currentDate.get(Calendar.MONTH) + 1;
		date = currentDate.get(Calendar.DAY_OF_MONTH);
		strDate = "" + currentDate.get(Calendar.YEAR) + "-"
		+ (month < 10 ? "0" + month : "" + month) + "-"
		+ (date < 10 ? "0" + date : "" + date);

		try {
			d = format.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return d;
	}

	/**
	 * @param scrollDate
	 * @return
	 */
	public static Date getControlDateAheadTime(String d, int scrollDate) {
		String strDate = "";
		int date = 0;
		int month = 0;
		Date dd = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String[] dc = d.split("-");
		int y, m, da;
		y = Integer.parseInt(dc[0]);
		m = Integer.parseInt(dc[1]);
		da = Integer.parseInt(dc[2]);

		GregorianCalendar currentDate = new GregorianCalendar();

		currentDate.set(y, m - 1, da);
		currentDate.add(Calendar.DATE, scrollDate);
		month = currentDate.get(Calendar.MONTH) + 1;
		date = currentDate.get(Calendar.DAY_OF_MONTH);
		strDate = "" + currentDate.get(Calendar.YEAR) + "-"
		+ (month < 10 ? "0" + month : "" + month) + "-"
		+ (date < 10 ? "0" + date : "" + date);

		try {
			dd = format.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dd;
	}

	/**
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDateNoTime(Date date1, Date date2) {
		if (date1 == null && date2 == null)
			return 0;
		else if (date1 == null && date2 != null)
			return -1;
		else if (date1 != null && date2 == null)
			return 1;
		else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String date1Str = (String) sdf.format(date1);
			String date2Str = (String) sdf.format(date2);
			return date1Str.compareTo(date2Str);
		}

	}

	/**
	 * @param date
	 * @return
	 */
	public static boolean dateWithInWeek(Date date) {
		if (date == null)
			return false;
		else {
			Date currencyDay = new Date(System.currentTimeMillis());
			long subTime = 7 * 24 * 60 * 60 * 1000;
			Date frontDay = new Date(System.currentTimeMillis() - subTime);
			if (date.compareTo(frontDay) >= 0
					&& date.compareTo(currencyDay) <= 0)
				return true;

		}
		return false;

	}

	/**
	 * @param date 
	 * @param extendDate 
	 * @return
	 */
	public static boolean compareDate(Date date, Date extendDate) {
		if (date == null || extendDate == null) {
			return false;
		} else {
			Date currentDay = new Date(System.currentTimeMillis());
			Date frontDate = new Date(currentDay.getTime()
					- extendDate.getTime());

			if (date.compareTo(frontDate) >= 0
					&& date.compareTo(currentDay) <= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param date
	 * @return
	 */
	public static boolean dateInFrontDay(Date date) {
		if (date != null) {
			Date currencyDay = new Date(System.currentTimeMillis());
			long subTime = 24 * 60 * 60 * 1000;
			Date frontDay = new Date(System.currentTimeMillis() - subTime);
			if (date.compareTo(frontDay) >= 0
					&& date.compareTo(currencyDay) <= 0)
				return true;
		}
		return false;
	}

	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String getLoanTerm(Date startDate, Date endDate) {
		Calendar xCalendar = Calendar.getInstance();
		xCalendar.setTime(startDate);
		Calendar yCalendar = Calendar.getInstance();
		yCalendar.setTime(endDate);

		int year = yCalendar.get(Calendar.YEAR) - xCalendar.get(Calendar.YEAR);
		int month = yCalendar.get(Calendar.MONTH)
		- xCalendar.get(Calendar.MONTH);
		int day = yCalendar.get(Calendar.DAY_OF_MONTH)
		- xCalendar.get(Calendar.DAY_OF_MONTH);

		int totalMonths = year * 12 + month;
		String loanterm = null;
		if (day >= 0)
			totalMonths = year * 12 + month + 1;
		if (totalMonths > 0 && totalMonths < 13) {
			loanterm = "1";
		} else if (totalMonths > 12 && totalMonths < 61) {
			loanterm = "2";
		} else if (totalMonths > 60) {
			loanterm = "3";
		}
		return loanterm;
	}



	/**
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareSameDay(Date date1, Date date2){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		cal1.setTime(date1);
		cal2.setTime(date2);
		if(cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR)){
			return false;
		}
		else if(cal1.get(Calendar.MONTH) != cal2.get(Calendar.MONTH)){
			return false;
		}
		else{
			if(cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)){
				return true;
			}else
				return false;
		}

	}
	/**
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareSameWeek(Date date1, Date date2){

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR)-cal2.get(Calendar.YEAR);
		if(subYear == 0)
		{
			if(cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}

		else if(subYear==1 && cal2.get(Calendar.MONTH)==11)
		{
			if(cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		else if(subYear==-1 && cal1.get(Calendar.MONTH)==11)
		{
			if(cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;

		}
		return false;
	}


	/**
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareSameMonth(Date date1, Date date2){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);

		if(cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR)){
			return false;
		}
		else {
			return (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))?true:false;
		}
	}

	/**
	 * @param date
	 * @param scollDays
	 * @return
	 */
	public static Date scollDate(Date date,int scollDays){
		Calendar calendar=Calendar.getInstance(); 
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+scollDays);
		return calendar.getTime();
	}

	/**
	 * @param date
	 * @param scollDays
	 * @return
	 */
	public static Date scollDateNoTime(Date date,int scollDays){
		Calendar calendar=Calendar.getInstance(); 
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+scollDays);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * ʱ������Ϸ���
	 * @param timestamp
	 * @param minute
	 * @return
	 */
	public static Timestamp addMinuteToTimestamp(Timestamp timestamp, int minute) {
		long time;
		if (timestamp != null) {
			time = timestamp.getTime();
		} else {
			time = System.currentTimeMillis();
		}
		time = time + 1000 * 60 * minute;
		return new Timestamp(time);
	}

	public static Timestamp subMinuteToTimestamp(Timestamp timestamp, int minute) {
		long time;
		if (timestamp != null) {
			time = timestamp.getTime();
		} else {
			time = System.currentTimeMillis();
		}
		time = time - 1000 * 60 * minute;
		return new Timestamp(time);
	}

	/**
	 * ȡ�õ�ǰ�����Ƕ�����
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * �õ�ĳһ���ܵ�����
	 * 
	 * @param year
	 * @return
	 */
	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

		return getWeekOfYear(c.getTime());
	}

	/**
	 * �õ�ĳ��ĳ�ܵĵ�һ��
	 *
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set (Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime ());
	}

	/**
	 * �õ�ĳ��ĳ�ܵ����һ��
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());
	}

	/**
	 * ȡ�õ�ǰ���������ܵĵ�һ��
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * ȡ�õ�ǰ���������ܵ����һ��
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	/**
	 * ȡ�õ�ǰ���������ܵĵڼ����Ӧ�ĵ�������
	 * @param date
	 * @param dayInWeek
	 * @return
	 */
	public static int getDayOfWeek(Date date,int dayInWeek){
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		int dayOfWeek=c.get(Calendar.DAY_OF_WEEK);
		c.add(Calendar.DAY_OF_MONTH, dayInWeek-dayOfWeek);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();   
        cal.setTime(date);   
        return cal.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();   
        cal.setTime(date);   
        return cal.get(Calendar.MONTH)+1;
	}

	public static int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();   
        cal.setTime(date);   
        return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static void main(String args[]) throws ParseException{ 
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd"); 
		Date date=scollDate(df.parse("2009-12-31"),1);
		System.out.println(df.format(date));
		System.out.println(ExtendDate.dateFormatToString(new Date(),
				ExtendDate.DATEFORMAT_MILLISECOND));
	}
}
