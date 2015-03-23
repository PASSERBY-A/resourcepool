package com.hp.avmon.alarm.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import uk.co.westhawk.snmp.stack.varbind;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.ireport.util.StringUtil;
import com.hp.avmon.utils.DBUtils;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmon.utils.Utils;
import com.hp.avmonserver.api.AvmonServer;
import com.hp.avmonserver.entity.Alarm;
import com.hp.avmonserver.entity.AlarmComment;
import com.hp.avmonserver.entity.MO;
import com.ireport.util.sql.BizDataUtil;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Service
public class AlarmService {

	private static final Log logger = LogFactory.getLog(AlarmService.class);

	@Autowired
	private AvmonServer avmonServer;

	@Autowired
	private AlarmViewService alarmViewService;

	private JdbcTemplate jdbc;

	/**
	 * 构造函数
	 */
	public AlarmService() {
		// 加载连接对象
		jdbc = SpringContextHolder.getBean("jdbcTemplate");
	}

	/**
	 * 将Alarm对象转换为页面对应字段
	 * 
	 * @param alarm
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	public Map convertAlarm(Alarm alarm) {
		HashMap data = new HashMap();
		if (alarm != null) {
			data.put("id", alarm.getId());
			data.put("mo", alarm.getMoId());
			data.put("monitor", "SIM");

			data.put("level", String.valueOf(alarm.getGrade()));
			data.put("status", String.valueOf(alarm.getStatus()));
			MO mo = alarmViewService.getMonitorObjectMap().get(alarm.getMoId());
			if (mo != null) {
				data.put("moCaption", mo.getCaption());
				if (mo.getAttr() != null) {
					Iterator it = mo.getAttr().keySet().iterator();
					while (it.hasNext()) {
						String key = (String) it.next();
						String value = (String) mo.getAttr().get(key);
						data.put("attr_" + key, value);
					}
				}
			}
			data.put("title", alarm.getContent());
			data.put("content_t", alarm.getContent());
			data.put("firstOccurTimeShort", new SimpleDateFormat("MM-dd HH:mm")
					.format(alarm.getFirstOccurTime()));
			data.put("occurTimes", alarm.getOccurTimes());

			data.put("firstOccurTime", new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(alarm.getFirstOccurTime()));
			data.put("lastUpdateTime", new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(alarm.getLastOccurTime()));
			data.put("attr_actorUser", alarm.getAknowledgeBy());
			data.put("attr_result", alarm.getSolution());
			if (alarm.getCloseTime() != null) {
				data.put("clearing_time", new SimpleDateFormat(
						"yyyy-MM-dd HH:mm").format(alarm.getCloseTime()));
			}

			data.put("lastUpdateTimeShort", new SimpleDateFormat("MM-dd HH:mm")
					.format(alarm.getLastOccurTime()));
			// data.put("firstOccurTime", alarm.getFirstOccurTime());
			// data.put("lastUpdateTime", alarm.getLastOccurTime());
			data.put("content", alarm.getOriginalContent());
			// data.put("status_imageurl",getStatusImageUrl(String.valueOf(alarm.getStatus())));
			// data.put("level_imageurl",getLevelImageUrl(String.valueOf(alarm.getGrade())));
			data.put("aknowledge_by", alarm.getAknowledgeBy());
			data.put(
					"aknowledge_time",
					alarm.getAknowledgeTime() == null ? ""
							: new SimpleDateFormat("yyyy-MM-dd").format(alarm
									.getAknowledgeTime()));
			data.put("close_by", alarm.getCloseBy());
			data.put("solution", alarm.getSolution());
			if (alarm.getCloseTime() != null) {
				data.put("close_time", new SimpleDateFormat("yyyy-MM-dd HH:mm")
						.format(alarm.getCloseTime()));
			}
		}

		return data;
	}

	// private String getLevelImageUrl(String num) {
	//
	// return "/resources/images/alarm/level" + num + ".png";
	// }
	//
	// private String getStatusImageUrl(String num) {
	// String str = "unknown";
	// if (num!=null){
	// if(num.equals("9")) str="close";
	// if(num.equals("3")) str="process";
	// if(num.equals("2")) str="process";
	// if(num.equals("1")) str="confirmed";
	// if(num.equals("0")) str="newalarm";
	// }
	//
	// return "/resources/images/alarm/"+str+".gif";
	// }

	/**
	 * 取得活动告警
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getActiveAlarmList(HttpServletRequest request) throws Exception {
		String path = request.getParameter("treeId");
		String sortname = null, sortorder = null;
		if (sortname == null || sortname.length() == 0) {
			sortname = "t1.last_occur_time";
			sortorder = "desc";
		}
		String sort = request.getParameter("sort");
		if (!StringUtil.isEmpty(sort)) {
			Gson gson = new Gson();
			List<Map<String, String>> attrList = gson.fromJson(sort,
					new TypeToken<List<Map<String, String>>>() {
					}.getType());

			for (Map<String, String> temp : attrList) {
				sortname = temp.get("property");
				sortorder = temp.get("direction");
			}

			// 将对应的字段与数据库对应
			if (!StringUtil.isEmpty(sortname)) {
				if ("level".equals(sortname)) {
					sortname = "t1.CURRENT_GRADE";
				} else if ("status".equals(sortname)) {
					sortname = "t1.STATUS";
				} else if ("moCaption".equals(sortname)) {
					sortname = "t2.CAPTION";
				} else if ("attr_ipaddr".equals(sortname)) {
					sortname = "t3.VALUE";
				} else if ("title".equals(sortname)) {
					sortname = "t1.TITLE";
				} else if ("content_t".equals(sortname)) {
					sortname = "t1.CONTENT";
				} else if ("firstOccurTime".equals(sortname)) {
					sortname = "t1.FIRST_OCCUR_TIME";
				} else if ("lastOccurTime".equals(sortname)) {
					sortname = "t1.LAST_OCCUR_TIME";
					sortorder = "desc";
				} else if ("occurTimes".equals(sortname)) {
					sortname = "t1.COUNT";
				} else if ("attr_result".equals(sortname)) {
					sortname = "t1.SOLUTION";
				} else if ("attr_actorUser".equals(sortname)) {
					sortname = "t1.CONFIRM_USER";
				} else if ("attr_businessSystem".equals(sortname)) {
					sortname = "t4.VALUE";
				} else if ("attr_position".equals(sortname)) {
					sortname = "t5.VALUE";
				} else if ("attr_usage".equals(sortname)) {
					sortname = "t6.VALUE";
				} else if ("attr_owner".equals(sortname)) {
					sortname = "t1.last_occur_time";
					sortorder = "desc";
				} else if ("hostName".equals(sortname)) {
					sortname = "t7.VALUE";
				}
			}
		}

		// String pagesize = request.getParameter("limit");
		String alarmId = request.getParameter("alarmId");
		String monitor = request.getParameter("monitor");
		String occurTimes = request.getParameter("occurTimes");
		String alarmTitle = request.getParameter("alarmTitle");
		String level = request.getParameter("level");
		String showMyAlarm = request.getParameter("isMyalarm");
		String showNewAlarm = request.getParameter("isNewalarm");
		String lastUpdateTime = request.getParameter("lastUpdateTime");

		HashMap filter = new HashMap();
		// 我的告警
		if ("1".equals(showMyAlarm)) {
			String userId = Utils.getCurrentUserId(request);
			filter.put("showMyAlarm", userId);
		} else {
			filter.put("showMyAlarm", null);
		}
		// 新告警
		if ("1".equals(showNewAlarm)) {
			filter.put("showNewAlarm", showNewAlarm);
		} else {
			filter.put("showNewAlarm", null);
		}
		filter.put("alarmId", alarmId);
		filter.put("monitor", monitor);
		filter.put("occurTimes", occurTimes);
		filter.put("content", alarmTitle);
		// 最后请求时间
		if (!StringUtils.isEmpty(lastUpdateTime)) {
			SimpleDateFormat myFormatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			filter.put("lastUpdateTime", myFormatter.format(new Date(new Long(
					(String) lastUpdateTime).longValue())));
		} else {
			filter.put("lastUpdateTime", null);
		}

		// 高级检索条件对应
		// 设备名称

		if (null != request.getParameter("s_moCaption")) {
			filter.put(
					"s_moCaption",
					new String(request.getParameter("s_moCaption").getBytes(
							"ISO-8859-1"), "UTF-8"));
		}

		// 告警内容
		if (null != request.getParameter("s_content_t")) {

			filter.put(
					"s_content_t",
					new String(request.getParameter("s_content_t").getBytes(
							"ISO-8859-1"), "UTF-8"));
		}

		// IP地址
		filter.put("s_attr_ipaddr", request.getParameter("s_attr_ipaddr"));
		// 处理人
		if (!StringUtil.isEmpty(request.getParameter("s_aknowledge_by"))) {
			JdbcTemplate jdbcTemplate = SpringContextHolder
					.getBean("jdbcTemplate");

			String sql = "select USER_ID as \"USER_ID\" from portal_users where real_name like '%"
					+ new String(request.getParameter("s_aknowledge_by")
							.getBytes("ISO-8859-1"), "UTF-8") + "%' ";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			String userId = "";
			for (Map<String, Object> map : list) {
				userId += "'" + map.get("USER_ID") + "'" + ",";
			}
			if (!StringUtil.isEmpty(userId) && userId.length() > 0) {
				userId = userId.substring(0, userId.length() - 1);
			}
			// add by mark start 2014-1-20
			else {
				userId += "'" + request.getParameter("s_aknowledge_by") + "'"
						+ ",";
				userId = userId.substring(0, userId.length() - 1);
			}
			// add by mark end 2014-1-20

			filter.put("s_aknowledge_by", userId);
		}

		// 业务系统
		if (null != request.getParameter("s_attr_businessSystem")) {
			filter.put("s_attr_businessSystem",
					new String(request.getParameter("s_attr_businessSystem")
							.getBytes("ISO-8859-1"), "UTF-8"));
		}

		// 位置
		if (null != request.getParameter("s_attr_position")) {
			filter.put("s_attr_position",
					new String(request.getParameter("s_attr_position")
							.getBytes("ISO-8859-1"), "UTF-8"));
		}

		// 告警开始时间
		String s_start_date = request.getParameter("s_start_date");
		String s_start_hours = request.getParameter("s_start_hours");
		String s_start_minutes = request.getParameter("s_start_minutes");
		if (!StringUtil.isEmpty(s_start_date)
				&& !StringUtil.isEmpty(s_start_hours)
				&& !StringUtil.isEmpty(s_start_minutes)) {
			String s_start_time = s_start_date + " " + s_start_hours + ":"
					+ s_start_minutes;
			filter.put("s_start_time", s_start_time);
		} else {
			filter.put("s_start_time", null);
		}

		// 告警结束时间 2012/12/05 00:00
		String s_end_date = request.getParameter("s_end_date");
		String s_end_hours = request.getParameter("s_end_hours");
		String s_end_minutes = request.getParameter("s_end_minutes");
		if (!StringUtil.isEmpty(s_end_date) && !StringUtil.isEmpty(s_end_hours)
				&& !StringUtil.isEmpty(s_end_minutes)) {
			String s_end_time = s_end_date + " " + s_end_hours + ":"
					+ s_end_minutes;
			filter.put("s_end_time", s_end_time);
		} else {
			filter.put("s_end_time", null);
		}

		List<Integer> levelList = new ArrayList<Integer>();
		if (level != null) {
			for (String s : level.split(",")) {
				if (s.trim().length() > 0) {
					// modify by mark start
					levelList.add(Integer.valueOf(s));
					// modify by mark end
				}
			}
		}
		Integer[] levels = new Integer[levelList.size()];
		for (int i = 0; i < levelList.size(); i++) {
			levels[i] = levelList.get(i);
		}

		int rowCount = getActiveAlarmCount(path, levels, filter);
		String limit = request.getParameter("limit_active");
		String start = request.getParameter("start_active");
		List<Map<String, Object>> list = getActiveAlarm(path, levels, filter,
				sortname, sortorder, limit, start);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activeTotal", rowCount);
		map.put("activeData", list);
		return map;
	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	public String getMonTreeJson(String userId) throws IOException {

		List<Map> list = alarmViewService.getViewItemList(userId);
		String json = JackJson.fromObjectToJson(list);
		return json;
	}

	/**
	 * 认领告警
	 * 
	 * @param alarmIdList
	 * @param userId
	 * @param content
	 * @param contentType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap aknowledge(String alarmIdList, String userId,
			String content, String contentType) throws Exception {
		HashMap m = new HashMap();

		alarmIdList = alarmIdList.replaceAll("\\(", "");
		alarmIdList = alarmIdList.replaceAll("\\)", "");
		String alarmIds[] = alarmIdList.split(",");

		for (String alarmId : alarmIds) {
			// logger.debug("aknowledge alarm with id={}",alarmId);
			Alarm alarm = getAlarmObjById(alarmId);
			if (alarm != null) {
				alarm.setStatus(Alarm.STATUS_AKNOWLEDGED);
				alarm.setAknowledgeBy(userId);
				alarm.setAknowledgeTime(new Date());
				alarm.setSolution(content);
				avmonServer.updateAlarm(alarm);
				avmonServer.addAlarmComments(alarmId, userId, content,
						contentType);
			}
		}

		m.put("userId", userId);
		m.put("result", "success");
		return m;
	}

	/**
	 * 关闭告警
	 * 
	 * @param alarmIdList
	 * @param userId
	 * @param content
	 * @param contentType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map close(String alarmIdList, String userId, String content,
			String contentType) throws Exception {
		Map m = new HashMap();

		alarmIdList = alarmIdList.replaceAll("\\(", "");
		alarmIdList = alarmIdList.replaceAll("\\)", "");
		String alarmIds[] = alarmIdList.split(",");
		// 关闭告警
		for (String alarmId : alarmIds) {
			// logger.debug("close alarm with id={}", alarmId);
			Alarm a = getAlarmObjById(alarmId);
			if (a != null) {
				a.setCloseBy(userId);
				a.setCloseTime(new Date());
				a.setSolution(content);
				a.setSolutionType(contentType);

				a.setAknowledgeBy(userId);
				a.setAknowledgeTime(new Date());
				a.setSolution(content);
				avmonServer.addAlarmComments(alarmId, userId, content,
						contentType);
				avmonServer.closeAlarm(a);
			}
		}

		// // 更新节点的告警数量
		// String alarmCntSql =
		// "SELECT mo_id,  count(*) as alarmCount  FROM TF_AVMON_ALARM_DATA  group by mo_id";
		// List<Map<String, Object>> alarmCntlist =
		// jdbc.queryForList(alarmCntSql);
		//
		// String alarmTreeSql = "SELECT * FROM TF_AVMON_ALARM_DATA ";
		// List<Map<String, Object>> alarmTreelist =
		// jdbc.queryForList(alarmTreeSql);

		// 返回结果
		m.put("result", "ok");
		return m;
	}

	public Alarm getAlarm(String alarmId) throws Exception {
		if (alarmId == null || alarmId.equals("")) {
			return null;
		}
		return getAlarmObjById(alarmId);
	}

	@SuppressWarnings("rawtypes")
	public Map getAlarmMap(String alarmId) throws Exception {
		if (alarmId == null || alarmId.equals("")) {
			return null;
		}
		return convertAlarm(getAlarmObjById(alarmId));
	}

	// @SuppressWarnings({ "rawtypes", "unchecked" })
	// public Map getMoLastUpdateTime(String moId) throws Exception {
	// Map m=new HashMap();
	// List<String> moList=alarmViewService.getAllMoChildrenByParent(moId);
	// String moIds[]=new String[moList.size()];
	// for(int i=0;i<moList.size();i++){
	// moIds[i]=moList.get(i);
	// }
	// String lastUpdateTime = avmonServer.checkMoLastUpdateTime(moIds);
	// m.put("lastUpdateTime", lastUpdateTime);
	// m.put("maxAlarmGrade", "");
	// return m;
	// }

	// public List<AlarmComment> getAlarmComments(String alarmId) {
	// logger.debug("fetch alarmcomments {} {}",alarmId,avmonServer.getAlarmComments(alarmId));
	// return avmonServer.getAlarmComments(alarmId);
	// }

	/**
	 * 取得历史告警列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getHistoryAlarmList(HttpServletRequest request) throws Exception {
		String mo = request.getParameter("treeId");
		// String page = null;
		// if (MyFunc.nullToShort(request.getParameter("start")) == 0) {
		// page = "1";
		// } else {
		// page =
		// MyFunc.nullToString(MyFunc.nullToShort(request.getParameter("start"))
		// / MyFunc.nullToShort(request.getParameter("limit")) + 1);
		// }
		String sortname = null, sortorder = null;
		if (sortname == null || sortname.length() == 0) {
			// modify by mark start
			sortname = "t1.last_occur_time";
			// modify by mark end
			sortorder = "desc";
		}
		String sort = request.getParameter("sort");
		if (!StringUtil.isEmpty(sort)) {
			Gson gson = new Gson();
			List<Map<String, String>> attrList = gson.fromJson(sort,
					new TypeToken<List<Map<String, String>>>() {
					}.getType());

			for (Map<String, String> temp : attrList) {
				sortname = temp.get("property");
				sortorder = temp.get("direction");
			}

			// 将对应的字段与数据库对应
			if (!StringUtil.isEmpty(sortname)) {
				if ("level".equals(sortname)) {
					sortname = "T1.CURRENT_GRADE";
				} else if ("status".equals(sortname)) {
					sortname = "T1.STATUS";
				} else if ("moCaption".equals(sortname)) {
					sortname = "t2.CAPTION";
				} else if ("attr_ipaddr".equals(sortname)) {
					sortname = "t3.VALUE";
				} else if ("title".equals(sortname)) {
					sortname = "t1.TITLE";
				} else if ("content_t".equals(sortname)) {
					sortname = "t1.CONTENT";
				} else if ("firstOccurTime".equals(sortname)) {
					sortname = "t1.FIRST_OCCUR_TIME";
				} else if ("lastOccurTime".equals(sortname)) {
					sortname = "t1.LAST_OCCUR_TIME";
				} else if ("occurTimes".equals(sortname)) {
					sortname = "t1.COUNT";
				} else if ("attr_result".equals(sortname)) {
					sortname = "t1.SOLUTION";
					sortorder = "desc";
				} else if ("attr_actorUser".equals(sortname)) {
					sortname = "t1.CONFIRM_USER";
				} else if ("attr_businessSystem".equals(sortname)) {
					sortname = "t4.VALUE";
				} else if ("attr_position".equals(sortname)) {
					sortname = "t5.VALUE";
				} else if ("attr_usage".equals(sortname)) {
					sortname = "t6.VALUE";
				} else if ("attr_owner".equals(sortname)) {
					// modify by mark start
					sortname = "t1.last_occur_time";
					// modify by mark end
					sortorder = "desc";
				} else if ("clearing_time".equals(sortname)) {
					sortname = "t1.close_time";
				}
			}
		}

		List<String> moList = alarmViewService.getAllMoChildrenByParent(mo);
		String moIds[] = new String[moList.size()];
		for (int i = 0; i < moList.size(); i++) {
			moIds[i] = moList.get(i);
		}
		String level = request.getParameter("level");
		// 级别列表
		List<Integer> gradeList = new ArrayList<Integer>();
		if (level != null) {
			for (String s : level.split(",")) {
				if (s.trim().length() > 0) {
					gradeList.add(Integer.valueOf(s));
				}
			}
		}
		Integer[] grades = new Integer[gradeList.size()];
		for (int i = 0; i < gradeList.size(); i++) {
			grades[i] = gradeList.get(i);
		}

		String alarmId = request.getParameter("alarmId");
		String monitor = request.getParameter("monitor");
		String occurTimes = request.getParameter("occurTimes");
		String alarmTitle = request.getParameter("alarmTitle");
		HashMap filter = new HashMap();
		filter.put("alarmId", alarmId);
		filter.put("monitor", monitor);
		filter.put("occurTimes", occurTimes);
		filter.put("content", alarmTitle);
		// String pagesize = request.getParameter("limit");

		// 告警数量
		int rowCount = getHistoryAlarmCount(moIds, grades, filter);
		// 告警数据
		String limit = request.getParameter("limit_history");
		String start = request.getParameter("start_history");
		List<Map<String, Object>> list = getHistoryAlarm(moIds, grades, filter,
				sortname, sortorder, limit, start);

		Map map = new HashMap();
		map.put("historyTotal", rowCount);
		map.put("historyData", list);
		return map;
	}

	/**
	 * 取得历史告警列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getHistoryAlarmListFor48H(HttpServletRequest request) throws Exception {
				String level = request.getParameter("level");
				String moId = request.getParameter("moId");
				HashMap filter = new HashMap();
				/*// 告警开始时间
				String s_start_date = request.getParameter("s_start_time");
				String s_start_hours = request.getParameter("s_start_hours");
				String s_start_minutes = request.getParameter("s_start_minutes");
				if(s_start_hours==null&&s_start_minutes==null){
					s_start_hours="00";
					s_start_minutes="00";
				}
				
				if (!StringUtil.isEmpty(s_start_date)
						//&& !StringUtil.isEmpty(s_start_hours)
						//&& !StringUtil.isEmpty(s_start_minutes)
						) {
					String s_start_time = s_start_date + " " + s_start_hours + ":"
							+ s_start_minutes;
					filter.put("s_start_time", s_start_time);
				} else {
					filter.put("s_start_time", null);
				}*/

				// 告警结束时间 2012/12/05 00:00
			/*	String s_end_date = request.getParameter("s_end_time");
				String s_end_hours = request.getParameter("s_end_hours");
				String s_end_minutes = request.getParameter("s_end_minutes");
				if(s_end_hours==null&&s_end_minutes==null){
					s_end_hours="00";
					s_end_minutes="00";
				}
				if (!StringUtil.isEmpty(s_end_date) 
						//&& !StringUtil.isEmpty(s_end_hours)
						//&& !StringUtil.isEmpty(s_end_minutes)
						) {
					String s_end_time = s_end_date + " " + s_end_hours + ":"
							+ s_end_minutes;
					filter.put("s_end_time", s_end_time);
				} else {
					filter.put("s_end_time", null);
				}*/

				// List<Integer> levelList = new ArrayList<Integer>();
				// int rowCount = getActiveAlarmCount(path, levels, filter);
				String limit = request.getParameter("limit_active");
				String start = request.getParameter("start_active");
				filter.put("moId", moId);
				List<Map<String, Object>> list = null;
				if (level == null) {
					list = getHistoryActiveAlarmByCriteria(request,new Integer[] { 1, 2, 3, 4, 5 },
							filter, limit, start);
				} else {
					list = getHistoryActiveAlarmByCriteria(request,
							new Integer[] { Integer.valueOf(level) }, filter, limit,
							start);
				}
				return list;
	}
	/**
	 * 取得tab页面的历史告警
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getPastRecordsList(HttpServletRequest request) throws Exception {

		// String mo = request.getParameter("treeId");
		String page = MyFunc.nullToString(MyFunc.nullToShort(request
				.getParameter("pagenum")) + 1);
		String pagesize = request.getParameter("pagesize");
		String moId = request.getParameter("moId");
		// moId = "8Mr5Uyd1GAf3Im1Pu8XBg4Jn2Qv9YCh";
		String kpiInfo = request.getParameter("kpiInfo");
		// kpiInfo = "1001011005|";
		//String alarmId = request.getParameter("alarmId");
		HashMap filter = new HashMap();
		filter.put("moId", moId);
		if(kpiInfo==null) 
			kpiInfo="";
		if (kpiInfo.indexOf("|") > 0) {
			filter.put("kpiCode", kpiInfo.split("\\|")[0]);
		}

		int rowCount = getPastRecordsAlarmCount(filter);
		List<Map<String, Object>> list = getPastRecordsAlarmList(filter,
				"t1.close_time", "desc",
				Integer.valueOf(page == null ? "0" : page),
				Integer.valueOf(pagesize == null ? "0" : pagesize));
		if(list.size()==0){
			 return list;
		 }
		 Map<String, Object> map=new HashMap<String, Object>();
		 map.put("totalcount", rowCount);
		 list.add(map);
		/*Map map = new HashMap();
		map.put("pastRecordsTotal", rowCount);
		map.put("pastRecordsData", list);*/
		return list;
	}

	/**
	 * 取得历史告警数量
	 * 
	 * @param moIds
	 * @param grades
	 * @param filter
	 * 
	 * @return 数量
	 */
	@SuppressWarnings("rawtypes")
	public int getPastRecordsAlarmCount(Map filter) {
		//String alarmId = MyFunc.nullToString(filter.get("alarmId"));
		String moId = MyFunc.nullToString(filter.get("moId"));
		String kpiCode = MyFunc.nullToString(filter.get("kpiCode"));
        String where="";
        where  =" where 1=1 and t1.mo_id = '"+moId+"'";
        where +=" and t1.kpi_code = '"+kpiCode+"'";
       // where += String.format(" and t1.kpi_code = '%s' ", kpiCode);
      /*  where += String.format(" where 1=1 and t1.mo_id = '%s' ", moId);
		where += String.format(" and t1.kpi_code = '%s' ", kpiCode);*/
		/*String sql = String.format(SqlManager.getSql(AlarmService.class,
				"getPastRecordsAlarmCount"), alarmId);*/
        String sql = String.format(SqlManager.getSql(AlarmService.class,
				"getPastRecordsAlarmCount"), where);
        int count  = jdbc.queryForInt(sql);
       
       /* List<Map<String, Object>> listMap  = jdbc.queryForList(sql);
        int totalCount=0;
        if(listMap!=null){
        	totalCount=listMap.size();
        }*/
         
		return count;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	List<Map<String, Object>> getPastRecordsAlarmList(Map filter,
			String sortName, String sortOrder, int page, int pageSize) {
		// String where = this.makeHistoryAlarmSqlWhere(moIds, grades, filter,
		// sortName, sortOrder, 0, 0);
		String moId = MyFunc.nullToString(filter.get("moId"));
		String kpiCode = MyFunc.nullToString(filter.get("kpiCode"));
		String where = "";
		where += String.format(" where 1=1 and t1.mo_id = '%s' ", moId);
		where += String.format(" and t1.kpi_code = '%s' ", kpiCode);
		String sql="";
		if (pageSize > 0 && DBUtils.isOracle()) {
			sql=" SELECT * FROM (SELECT temp.* , ROWNUM num FROM ( ";
			/*where += String.format(" and rownum between '%d' and '%d' ",
					Integer.valueOf((page - 1) * pageSize + 1),
					Integer.valueOf(page * pageSize));*/
			/*where += String.format(" and rownum between '%d' and '%d' ",
					Integer.valueOf((page - 1) * pageSize + 1),
					Integer.valueOf(page * pageSize));*/
		if (sortName != null && sortOrder != null && sortName.length() > 0
				&& sortOrder.length() > 0) {
			where += String.format(" order by %s %s ", sortName, sortOrder);//and rownum between '%d' and '%d'
			where +="  )  temp " +String.format("  where ROWNUM <= '%d' ) WHERE num > '%d'  ",Integer.valueOf(page * pageSize),
					Integer.valueOf((page - 1) * pageSize )
					);
			 sql += String.format(SqlManager.getSql(AlarmService.class,
						"getPastRecordsAlarmList"), where);
		}
		}
		
		if (pageSize > 0 && DBUtils.isPostgreSql()) {
			where += String.format(" limit '%d' offset '%d' ",
					Integer.valueOf(pageSize),
					Integer.valueOf((page - 1) * pageSize));
			sql = String.format(SqlManager.getSql(AlarmService.class,
						"getPastRecordsAlarmList"), where);
		}

		
		//sql=" SELECT * FROM (SELECT temp.* , ROWNUM num FROM ( "+sql+
		List<Map<String, Object>> listMap = jdbc.queryForList(sql);

		return listMap;
	}

	/**
	 * 取得节点告警数量
	 * 
	 * @param request
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMoStatus(HttpServletRequest request, String userId)
			throws Exception {

		String mo = request.getParameter("treeId");
		// 取得子节点信息
		List<String> moList = alarmViewService.getAllMoChildrenByParent(mo);
		String moIds[] = new String[moList.size()];
		for (int i = 0; i < moList.size(); i++) {
			moIds[i] = moList.get(i);
		}
		// 取得各个类型的告警数量
		String where = "1=1";
		if (moIds != null && moIds.length > 0) {
			where = "";
			for (String moId : moIds) {
				where += ",'" + moId + "'";
			}
			where = new String("mo_id in (" + new String(where.substring(1))
					+ ")");
		}

		// log.debug(s1);

		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getMoStatusCount"),
				userId, where);
		List<Map<String, Object>> list = jdbc.queryForList(sql);

		int level0AlarmCount = 0;
		int level1AlarmCount = 0;
		int level2AlarmCount = 0;
		int level3AlarmCount = 0;
		int level4AlarmCount = 0;
		int newAlarmCount = 0;
		int userAlarmCount = 0;
		// 累加各种级别的告警数量
		for (Map<String, Object> temp : list) {
			level0AlarmCount += MyFunc.nullToInteger(temp.get("level0"));
			level1AlarmCount += MyFunc.nullToInteger(temp.get("level1"));
			level2AlarmCount += MyFunc.nullToInteger(temp.get("level2"));
			level3AlarmCount += MyFunc.nullToInteger(temp.get("level3"));
			level4AlarmCount += MyFunc.nullToInteger(temp.get("level4"));
			newAlarmCount += MyFunc.nullToInteger(temp.get("newalarm"));
			userAlarmCount += MyFunc.nullToInteger(temp.get("userAlarm"));
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("level0Cnt", level0AlarmCount);
		map.put("level1Cnt", level1AlarmCount);
		map.put("level2Cnt", level2AlarmCount);
		map.put("level3Cnt", level3AlarmCount);
		map.put("level4Cnt", level4AlarmCount);
		map.put("newAlarmCnt", newAlarmCount);
		map.put("myAlarmCnt", userAlarmCount);

		return map;
	}

	/**
	 * 取得变更节点
	 * 
	 * @param request
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getChangedNode(HttpServletRequest request) throws Exception {

		// 根据节点的绝对路径得到上层结构关系
		String nodePath = request.getParameter("nodePath");
		nodePath = nodePath.substring(6);
		String moIds[] = nodePath.split("\\/");

		String where = "";
		String path[] = new String[moIds.length];

		where = "/root/";
		int i = 0;
		for (String moId : moIds) {
			String a[] = moId.split("\\*");
			if (a.length > 1) {
				where += a[1] + "/";
				path[i] = where.substring(0, where.length() - 1);
				i++;
			}
		}

		// 在postgresql中，如果pid或id为null，结果也会是null，需要改为concat函数处理
		String sql = "select " + "a.pid||'*'||a.id as \"id\" "
				+ "from STG_AVMON_ALARM_TREE a " + "where ";
		where = "a.path in (";
		for (String temp : path) {
			where += "'" + temp + "'" + ",";
		}
		where = where.substring(0, where.length() - 1);
		where += ")";

		sql = sql + where;
		List<Map<String, Object>> list = jdbc.queryForList(sql);

		String nodeIds = "";
		for (Map<String, Object> temp : list) {
			nodeIds = nodeIds + temp.get("id") + ",";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nodeIds", nodeIds.substring(0, nodeIds.length() - 1));

		return map;
	}

	/**
	 * 取得主机类型的图片信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMoTypeImageInfo(String alarmId) {
		if (!StringUtil.isEmpty(alarmId)) {
			// 图片信息
			String tempSql = SqlManager.getSql(AlarmService.class,
					"getMoTypeImageInfo");
			String sql = String.format(tempSql, alarmId);

			// logger.info("getMoTypeImageInfo SQL:" + sql);
			List<Map> kpiList = jdbc.queryForList(sql);
			HashMap data = new HashMap();
			for (Map temp : kpiList) {
				data.put("resource_picture", temp.get("RESOURCE_PICTURE"));
				data.put("picture_direction", temp.get("PICTURE_DIRECTION"));
			}
			return data;
		} else {
			return null;
		}
	}

	/**
	 * 取得活动告警列表
	 * 
	 * @param moIds
	 * @param grades
	 * @param filter
	 * @param sortName
	 * @param sortOrder
	 * @param page
	 * @param pageSize
	 * 
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map<String, Object>> getActiveAlarm(String path,
			Integer[] grades, Map filter, String sortName, String sortOrder,
			String limit, String start) {
		String whereCondition = this.getActiveAlarmSqlWhere(path, grades,
				filter, sortName, sortOrder);
		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getCurrentAlarmList"),
				whereCondition);

		if (StringUtil.isEmpty(limit) || StringUtil.isEmpty(start)) {
			// 定时刷新任务的场合
			List<Map<String, Object>> listMap = jdbc.queryForList(sql);
			return listMap;
		} else {
			// 检索的场合
			String querySql = generatPageSqlForJQW(sql, limit, start);
			List<Map<String, Object>> listMap = jdbc.queryForList(querySql);
			return listMap;
		}
	}

	private String generatPageSql(String sql, String limit, String start) {
		Integer limitL = Integer.valueOf(limit);
		// Integer startL = Integer.valueOf(start)+1;
		Integer startL = Integer.valueOf(start);

		// 构造oracle数据库的分页语句
		return DBUtils.pagination(sql, startL, limitL);
		/*
		 * StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		 * paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		 * paginationSQL.append(sql); //
		 * MyFunc.nullToString(MyFunc.nullToShort(request.getParameter("start"))
		 * / MyFunc.nullToShort(request.getParameter("limit")) + 1); //
		 * paginationSQL.append(" ) temp where ROWNUM <= " +
		 * limitL*((startL-1)/10+1));//limitL*startL); //
		 * paginationSQL.append(" ) WHERE num > " + (startL-1));
		 * paginationSQL.append(" ) temp where ROWNUM <= " + (startL +
		 * limitL));//limitL*startL); paginationSQL.append(" ) WHERE num > " +
		 * (startL)); return paginationSQL.toString();
		 */
	}
	

	@SuppressWarnings("rawtypes")
	private String getActiveAlarmSqlWhere(String path, Integer[] grades,
			Map filter, String sortName, String sortOrder) {
		String where = " where 1=1 ";
		// mo list
		// String mo="";
		// if(moIds != null) {
		// for(String s : moIds){
		// if(s != null&&s.trim().length() > 0){
		// mo += ",'" + s + "'";
		// }
		// }
		// }
		// if(mo.length()>0){
		// where+=" and t1.MO_ID in ("+mo.substring(1)+") ";
		// }

		where += " and t1.MO_ID in (select tree.id from STG_AVMON_ALARM_TREE tree where tree.path like '%"
				+ path + "%'" + ") ";

		// grade list
		String g = "";
		if (grades != null) {
			for (Integer n : grades) {
				if (n != null) {
					g += "," + n;
				}
			}
		}
		if (g.length() > 0) {
			where += " and t1.CURRENT_GRADE in (" + g.substring(1) + ") ";
		}

		// other filters
		if (filter != null) {
			// 我的告警
			String showMyAlarm = (String) filter.get("showMyAlarm");
			if (showMyAlarm != null && showMyAlarm.length() > 0) {
				where += " and t1.CONFIRM_USER ='" + showMyAlarm + "' ";
			}
			// 新告警
			String showNewAlarm = (String) filter.get("showNewAlarm");
			if (showNewAlarm != null && showNewAlarm.length() > 0) {
				where += " and t1.STATUS  ='0' ";
			}
			// 设备名称
			String s_moCaption = (String) filter.get("s_moCaption");
			if (s_moCaption != null && s_moCaption.length() > 0) {
				where += " and t2.CAPTION like '%" + s_moCaption + "%' ";
			}
			// 告警内容
			String s_content_t = (String) filter.get("s_content_t");
			if (s_content_t != null && s_content_t.length() > 0) {
				where += " and t1.content like '%" + s_content_t + "%' ";
			}
			// IP地址
			String s_attr_ipaddr = (String) filter.get("s_attr_ipaddr");
			if (s_attr_ipaddr != null && s_attr_ipaddr.length() > 0) {
				where += " and t3.VALUE like '%" + s_attr_ipaddr + "%' ";
			}
			// 处理人
			String s_aknowledge_by = (String) filter.get("s_aknowledge_by");
			if (s_aknowledge_by != null && s_aknowledge_by.length() > 0) {
				where += " and t1.CONFIRM_USER in (" + s_aknowledge_by + ") ";
			}
			// 业务系统
			String s_attr_businessSystem = (String) filter
					.get("s_attr_businessSystem");
			if (s_attr_businessSystem != null
					&& s_attr_businessSystem.length() > 0) {
				where += " and t4.VALUE like '%" + s_attr_businessSystem
						+ "%' ";
			}
			// 位置
			String s_attr_position = (String) filter.get("s_attr_position");
			if (s_attr_position != null && s_attr_position.length() > 0) {
				where += " and t5.VALUE like '%" + s_attr_position + "%' ";
			}
			// 告警开始时间
			String s_start_time = (String) filter.get("s_start_time");
			if (s_start_time != null && s_start_time.length() > 0) {
				// where +=
				// " and "+DBUtils.getDBToDateFunction()+"(t1.FIRST_OCCUR_TIME,'yyyy/MM/dd HH:mm') >= "+DBUtils.getDBToDateFunction()+"('"
				// + s_start_time + "','yyyy/MM/dd HH:mm') ";
				try {
					// Date data1 = new
					// SimpleDateFormat("yyyy/MM/dd HH:mm").parse(s_start_time);
					// String start = new
					// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data1);
					where += " and t1.first_occur_time >= "+DBUtils.getDBToDateFunction()+"('"
							+ s_start_time + "', 'YYYY-MM-DD HH24:MI:SS')";
				} catch (ParseException e) {
					logger.error(e.getMessage());
				}
			}
			// 告警结束时间
			String s_end_time = (String) filter.get("s_end_time");
			if (s_end_time != null && s_end_time.length() > 0) {
				// where +=
				// " and "+DBUtils.getDBToDateFunction()+"(t1.FIRST_OCCUR_TIME,'yyyy/MM/dd HH:mm') <= "+DBUtils.getDBToDateFunction()+"('"
				// + s_end_time + "','yyyy/MM/dd HH:mm') ";
				try {
					// Date data2 = new
					// SimpleDateFormat("yyyy/MM/dd HH:mm").parse(s_end_time);
					// String end = new
					// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data2);
					where += " and t1.first_occur_time <= "+DBUtils.getDBToDateFunction()+"('"
							+ s_end_time + "', 'YYYY-MM-DD HH24:MI:SS')";
				} catch (ParseException e) {
					logger.error(e.getMessage());
				}
			}

			String content = (String) filter.get("content");
			if (content != null && content.length() > 0 && !content.equals("*")) {
				where += " and t1.content like '%" + content + "%' ";
			}

			if (filter.get("occurTimes") != null
					&& ((String) filter.get("occurTimes")).length() > 0) {
				Integer occurTimes = Integer.valueOf((String) filter
						.get("occurTimes"));
				where += " and t1.count>=" + occurTimes + " ";
			}
			// 页面最后提交请求时间
			String lastUpdateTimeStr = (String) filter.get("lastUpdateTime");
			if (lastUpdateTimeStr != null && lastUpdateTimeStr.length() > 0) {
				// // log.debug("Get Alarm List after time {}",
				// lastUpdateTimeStr);
				// SimpleDateFormat df = new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// try {
				// // Date lastUpdateTime = df.parse(lastUpdateTimeStr);
				// //where += " and t1.LAST_OCCUR_TIME >= " +
				// MemDB.formatDate(lastUpdateTime);
				// where += " and t1.LAST_OCCUR_TIME >= "+DBUtils.getDBToDateFunction()+"('" +
				// lastUpdateTimeStr + "', 'YYYY-MM-DD HH24:MI:SS')";
				// } catch(Exception e) {
				// logger.error(e.getMessage());
				// }
				where += " and t1.LAST_OCCUR_TIME >= "+DBUtils.getDBToDateFunction()+"('"
						+ lastUpdateTimeStr + "', 'YYYY-MM-DD HH24:MI:SS')";
			}
		}

		// if(pageSize > 0){
		// where += String.format(" and rownum between '%d' and '%d' ", (page-1)
		// * pageSize - 1, pageSize);
		// }

		if (sortName != null && sortOrder != null && sortName.length() > 0
				&& sortOrder.length() > 0) {
			where += String.format(" order by %s %s ", sortName, sortOrder);
		}

		return where;
	}

	/**
	 * 取得活动告警数量
	 * 
	 * @param moIds
	 * @param grades
	 * @param filter
	 * 
	 * @return 数量
	 */
	@SuppressWarnings("rawtypes")
	int getActiveAlarmCount(String path, Integer[] grades, Map filter) {
		String where = this.getActiveAlarmSqlWhere(path, grades, filter, null,
				null);

		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getCurrentAlarmCount"),
				where);
		int n = jdbc.queryForInt(sql);

		return n;
	}

	/**
	 * 取得历史告警数量
	 * 
	 * @param moIds
	 * @param grades
	 * @param filter
	 * 
	 * @return 数量
	 */
	@SuppressWarnings("rawtypes")
	int getHistoryAlarmCount(String[] moIds, Integer[] grades, Map filter) {
		String where = this.makeHistoryAlarmSqlWhere(moIds, grades, filter,
				null, null);

		String sql = String.format(
				"select count(*) from TF_AVMON_ALARM_HISTORY T1 %s ",
				new Object[] { where });
		int n = jdbc.queryForInt(sql);
		return n;
	}

	@SuppressWarnings("rawtypes")
	private String makeHistoryAlarmSqlWhere(String[] moIds, Integer[] grades,
			Map filter, String sortName, String sortOrder) {
		String where = "where 1=1 ";
		// mo list
		String mo = "";
		if (moIds != null) {
			for (String s : moIds) {
				if (s != null && s.trim().length() > 0) {
					mo += ",'" + s + "'";
				}
			}
		}

		if (mo.length() > 0) {
			where += " and t1.MO_ID in (" + mo.substring(1) + ") ";
		}

		// if(pageSize > 0){
		// where += String.format(" and rownum between '%d' and '%d' ", (page-1)
		// * pageSize - 1, pageSize);
		// }

		if (sortName != null && sortOrder != null && sortName.length() > 0
				&& sortOrder.length() > 0) {
			where += String.format(" order by %s %s ", sortName, sortOrder);
		}

		return where;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	List<Map<String, Object>> getHistoryAlarm(String[] moIds, Integer[] grades,
			Map filter, String sortName, String sortOrder, String limit,
			String start) {
		String where = this.makeHistoryAlarmSqlWhere(moIds, grades, filter,
				sortName, sortOrder);
		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getHistoryAlarmList"),
				where);

		if (StringUtil.isEmpty(limit) || StringUtil.isEmpty(start)) {
			// 定时刷新任务的场合
			List<Map<String, Object>> listMap = jdbc.queryForList(sql);

			return listMap;
		} else {
			// 检索的场合
			String querySql = generatPageSqlForJQW(sql, limit, start);
			List<Map<String, Object>> listMap = jdbc.queryForList(querySql);

			return listMap;
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getAlarmById(String alarmId) {
		String where = " where t1.alarm_id = '" + alarmId + "'";
		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getCurrentAlarmList"),
				where);
		Map<String, Object> list = jdbc.queryForMap(sql);

		return list;
	}

	public Alarm getAlarmObjById(String alarmId) {

		String sql = String.format(
				"select * from TF_AVMON_ALARM_DATA where alarm_id='%s'",
				alarmId);
		List<Map<String, Object>> list = jdbc.queryForList(sql);
		if (list != null && list.size() > 0) {
			Map map = list.get(0);
			Alarm alarm = new Alarm();
			alarm.setId((String) map.get("ALARM_ID"));
			alarm.setMoId((String) map.get("MO_ID"));
			alarm.setSource((String) map.get("SOURCE"));
			alarm.setSourceType(String.valueOf(map.get("SOURCE_TYPE")));
			alarm.setAdditionalInfo((String) map.get("ADDITIONAL_INFO"));
			alarm.setCode((String) map.get("CODE"));
			alarm.setTitle((String) map.get("TITLE"));
			alarm.setContent((String) map.get("CONTENT"));
			alarm.setOriginalContent((String) map.get("CONTENT"));
			alarm.setGrade(DBUtils.toInt(map.get("CURRENT_GRADE")));
			alarm.setFirstOccurTime((Date) map.get("FIRST_OCCUR_TIME"));
			alarm.setLastOccurTime((Date) map.get("LAST_OCCUR_TIME"));
			alarm.setOccurTimes(DBUtils.toInt(map.get("COUNT")));
			alarm.setAknowledgeTime((Date) map.get("CONFIRM_TIME"));
			alarm.setAknowledgeBy((String) map.get("CONFIRM_USER"));
			alarm.setStatus(DBUtils.toInt(map.get("STATUS")));
			alarm.setKpiCode((String) map.get("KPI_CODE"));
			alarm.setInstance((String) map.get("INSTANCE"));
			return alarm;
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getAlarmComments(String alarmId) {
		String sql = String
				.format("select * from  TF_AVMON_ALARM_COMMENT where alarm_id='%s' order by create_time desc ",
						alarmId);
		List list = jdbc.queryForList(sql);
		List comments = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			AlarmComment c = new AlarmComment((Map) list.get(i));
			comments.add(c);
		}

		return comments;
	}

	/**
	 * 取得设备类型
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getRecipientData(HttpServletRequest request)
			throws Exception {

		String sql = "select distinct USER_ID, REAL_NAME from PORTAL_USERS ";
		List<Map> list = jdbc.queryForList(sql);
		sql = null;

		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map<String, Object>> getAllActiveAlarm(String path,
			Integer[] grades, Map filter, String sortName, String sortOrder) {
		String whereCondition = this.getActiveAlarmSqlWhere(path, grades,
				filter, sortName, sortOrder);
		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getCurrentAlarmList"),
				whereCondition);

		List<Map<String, Object>> listMap = jdbc.queryForList(sql);
		return listMap;

		// if (StringUtil.isEmpty(limit) || StringUtil.isEmpty(start)) {
		// // 定时刷新任务的场合
		// List<Map<String,Object>> listMap = jdbc.queryForList(sql);
		// return listMap;
		// } else {
		// // 检索的场合
		// String querySql = generatPageSql(sql, limit, start);
		// List<Map<String,Object>> listMap = jdbc.queryForList(querySql);
		// return listMap;
		// }
	}

	// Jay add alarm data list by moid

	public List getAllActiveAlarmDataByMoId(HttpServletRequest request)
			throws Exception {
		String moId = request.getParameter("moId");
		String where = "WHERE t1.MO_id ='" + moId
				+ "' order by t1.last_occur_time desc";
		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getCurrentAlarmList"),
				where);
		List<Map<String, Object>> list = jdbc.queryForList(sql);
		return list;
	}

	public List getOtherMoData(HttpServletRequest request) throws Exception {
		String sql = "select  distinct t1.MO_ID as \"mo\",  tt.caption as \"attr_ipaddr\" ,tt.type_id as \"typeId\" from TF_AVMON_ALARM_DATA t1 "
				+ "inner join TD_AVMON_MO_INFO tt on (t1.MO_ID = tt.MO_ID) "
				+ "left join portal_users tu on (t1.CONFIRM_USER = tu.USER_ID or t1.CONFIRM_USER = tu.REAL_NAME) "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t3 on (t1.MO_ID = t3.MO_ID and t3.NAME='ip')  "
				+ "left join TD_AVMON_KPI_INFO t10 on (t1.KPI_CODE=t10.KPI_CODE) "
				+ "WHERE t1.MO_id  in ( SELECT distinct mo_id from td_avmon_mo_info where type_id not in (\'HOST_LINUX\',\'HOST_WINDOWS\',\'HOST_SUNOS\',\'HOST_SUNOS\',\'HOST_AIX\')) "
				+ "order by tt.type_id desc";
		List<Map<String, Object>> list = jdbc.queryForList(sql);
		for (int i = 0; i < list.size(); i++) {
			int count = getAlarmCountByMoId(request, list.get(i).get("mo")
					.toString());
			list.get(i).put("attr_ipaddr",
					list.get(i).get("attr_ipaddr") + "(" + count + ")");
		}
		return list;
	}

	public List getAllMoData(HttpServletRequest request) throws Exception {
		String typeId = request.getParameter("typeId");
		String isIp = request.getParameter("isIp");
		String where = "";
		String where1="";
		String where2="";
		String where3="";
		String pagesize = request.getParameter("pagesize");
		String pagenum = request.getParameter("pagenum");
		List<Map<String, Object>> list =null;
		if(!"1".equals(isIp)&&isIp!=null){
			where1 = "hostName";
			where2="";
		}			
		else {
			where1 = "ip";
			where2="";
		}
		if(typeId!=null){
			where = "where \"typeId\"  like '" + typeId +"_%' ";
			where3=" and a.type_id like '" + typeId +"_%'";
		}

	    if(pagesize==null&&pagenum==null){
		pagesize="20";
		pagenum="0";
		Integer pagenum1 = (Integer.parseInt(pagenum));
		String ss=SqlManager.getSql(AlarmService.class,
				"getAllMoDataByTypeId");
		String sql = String.format(ss,where1,where2,where,where3);
		String querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
		//System.out.println("qinjie;"+querySql);
		list = jdbc.queryForList(querySql);
		
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put(
					"attr_ipaddr",
					list.get(i).get("attr_ipaddr") + "("
							+ list.get(i).get("count") + ")"
							+ list.get(i).get("typeId"));
		}
		if(typeId==null){
			Map map=new HashMap<String , Object>();
			map.put("attr_ipaddr", "点击加载更多");
			list.add(map);
		}
	    }
	    else{
	    	//pagesize="20";
			//pagenum="0";
			Integer pagenum1 = (Integer.parseInt(pagenum));
			String ss=SqlManager.getSql(AlarmService.class,
					"getAllMoDataByTypeId");
			String sql = String.format(ss,where1,where2,where,where3);
			String querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
			//System.out.println("letian;"+querySql);
			list = jdbc.queryForList(querySql);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).put(
						"attr_ipaddr",
						list.get(i).get("attr_ipaddr") + "("
								+ list.get(i).get("count") + ")"
								+ list.get(i).get("typeId"));
			}
	    }
		
		return list;
	}
	  //jqw分页
  	private String generatPageSqlForJQW(String sql, String limit, String start) {
  		Integer limitL = Integer.valueOf(limit);
  		// Integer startL = Integer.valueOf(start)+1;
  		Integer startL = Integer.valueOf(start);

  		// 构造oracle数据库的分页语句
  		return DBUtils.paginationforjqw(sql, startL, limitL);
  		/*
  		 * StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
  		 * paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
  		 * paginationSQL.append(sql); //
  		 * MyFunc.nullToString(MyFunc.nullToShort(request.getParameter("start"))
  		 * / MyFunc.nullToShort(request.getParameter("limit")) + 1); //
  		 * paginationSQL.append(" ) temp where ROWNUM <= " +
  		 * limitL*((startL-1)/10+1));//limitL*startL); //
  		 * paginationSQL.append(" ) WHERE num > " + (startL-1));
  		 * paginationSQL.append(" ) temp where ROWNUM <= " + (startL +
  		 * limitL));//limitL*startL); paginationSQL.append(" ) WHERE num > " +
  		 * (startL)); return paginationSQL.toString();
  		 */
  	}
	public List queryMoData(HttpServletRequest request) throws Exception {
		String ipaddress=request.getParameter("ipaddress");
		String where = "where \"attr_ipaddr\" like '%"+ipaddress+"%'";
		String where1 = "ip";
		String where2 = "hostName";
		String where3 = "";
		String sql = String.format(SqlManager.getSql(AlarmService.class,
				"getQueryAllMoDataByTypeId"), where1,where2,where,where3,where);
		List<Map<String, Object>> list = jdbc.queryForList(sql);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put(
					"attr_ipaddr",
					list.get(i).get("attr_ipaddr") + "("
							+ list.get(i).get("count") + ")"
							+ list.get(i).get("typeId"));
		}
		return list;
	}
	

	public int getActiveAlarmAllCount(HttpServletRequest request) {
		/*
		 * String where = this.getActiveAlarmSqlWhere(path, grades, filter,
		 * null, null);
		 */
		String typeId=request.getParameter("typeId");
		String wheresqlString = "";
		if(typeId!=null){
			wheresqlString=" where t2.type_id like '"+typeId+"_%'";
		}
		String sql = String.format(SqlManager.getSql(AlarmService.class,
				"getCurrentAlarmCount"), wheresqlString);
		int n = jdbc.queryForInt(sql);

		return n;
	}
	
	public int getAlarmDataForKpiCount(HttpServletRequest request) {
		 String bizName=request.getParameter("bizName");
		    String kpi=request.getParameter("kpi");
		    String wheresqlString=" where t10.kpi_name="+"'"+kpi+"'";
		    String period=request.getParameter("period");
	        String s_start_date=request.getParameter("s_start_date");
	        String s_end_date=request.getParameter("s_end_date");
			String defaultString="";
			String typeId=request.getParameter("typeId");
	        if(typeId!=null&&typeId.length()>0){
	        	wheresqlString=wheresqlString+" and tt.type_Id like '"+typeId+"_%'";
	        	
	        }
			if (period == null||period.equals("")) {
				period = "4";
			}
			if(DBUtils.isOracle()){
				if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
					defaultString=wheresqlString;
				}
				else{
					defaultString=wheresqlString+" and trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
							"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
				}
				switch (Integer.parseInt(period)) {
				case 1:// today
					wheresqlString =wheresqlString+ "and trunc(sysdate)=trunc(t1.first_occur_time)";
					break;
				case 2:// one week
					wheresqlString = wheresqlString+" and trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
					break;
				case 3:// two week
					wheresqlString = wheresqlString+"and trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
					break;
				case 4:// one month
					wheresqlString =wheresqlString+"and t1.first_occur_time>add_months(sysdate,-1)";
					break;
				default:
					//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
					wheresqlString =defaultString;
					break;
				}
			}
				else{
					if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
						defaultString=wheresqlString;
					}
					else{
						defaultString=wheresqlString +" and  "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
								"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
				
					}
					switch (Integer.parseInt(period)) {
					case 1:// today
						wheresqlString =wheresqlString+  " and "+DBUtils.getDBToDateFunction()+"(to_char(current_date,'yyyy/mm/dd'),'yyyy/mm/dd')="+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')";;
						break;
					case 2:// one week
						wheresqlString = wheresqlString+" and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-7 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
						break;
					case 3:// two week
						wheresqlString = wheresqlString+"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-14 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
						break;
					case 4:// one month
						wheresqlString =wheresqlString+ "and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>(current_date - interval '1 month')";
						break;
					default:
						//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
						wheresqlString =defaultString;
						break;
					}
				}
			String sql="";
			if(bizName!=null){
				String whereBizString="";
			
				if("ALL".equals(bizName)){
					whereBizString="";
				}
			   else{
				   try {
						bizName = URLDecoder.decode(bizName, "utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage());
					}// 字符编码和文档编码一致
				   if(bizName.equals("others")){
					   whereBizString="   inner join ( select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem'  and  attr.value not in " +
						   		"(select businessname as \"name\" from tf_avmon_biz_dictionary) union all  ( select distinct mo_id, 'Other' from td_avmon_mo_info_attribute attr where attr.mo_id not in (select mo_id from td_avmon_mo_info_attribute attr where name='businessSystem'))) t on t1.mo_id = t.mo_id  ";
					
				   }
				   else{
					   whereBizString="inner join (select value,mo_id from td_avmon_mo_info_attribute " +
						   		"where name='businessSystem' and value='"+bizName+"') d on t1.mo_id = d.mo_id";   
				   }
				  
				}
				 sql = String.format(
						SqlManager.getSql(AlarmService.class, "getCurrentAlarmAllCount"),
						whereBizString,wheresqlString);
			}else{
				 sql = String.format(
						SqlManager.getSql(AlarmService.class, "getCurrentAlarmAllCount"),
						wheresqlString);
			}
			


		/*String sql = String.format(SqlManager.getSql(AlarmService.class,
				"getCurrentAlarmAllCount"), wheresqlString);*/
		int n = jdbc.queryForInt(sql);

		return n;
	}
	
	public int getActiveAlarmByCaptionCount(HttpServletRequest request) {
	    String capation=request.getParameter("caption");
	    String bizName=request.getParameter("bizName");
	    String wheresqlString=" where tt.caption="+"'"+capation+"'";
	    String period=request.getParameter("period");
        String s_start_date=request.getParameter("s_start_date");
        String s_end_date=request.getParameter("s_end_date");
		String defaultString="";
		String typeId=request.getParameter("typeId");
        if(typeId!=null&&typeId.length()>0){
        	wheresqlString=wheresqlString+" and tt.type_Id like '"+typeId+"_%'";
        	
        }
		if (period == null||period.equals("")) {
			period = "4";
		}
		 if(DBUtils.isOracle()){
				if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
					defaultString=wheresqlString;
				}
				else{
					defaultString=wheresqlString+" and trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
							"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
				}
				switch (Integer.parseInt(period)) {
				case 1:// today
					wheresqlString =wheresqlString+ "and trunc(sysdate)=trunc(t1.first_occur_time)";
					break;
				case 2:// one week
					wheresqlString = wheresqlString+" and trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
					break;
				case 3:// two week
					wheresqlString = wheresqlString+"and trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
					break;
				case 4:// one month
					wheresqlString =wheresqlString+"and t1.first_occur_time>add_months(sysdate,-1)";
					break;
				default:
					//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
					wheresqlString =defaultString;
					break;
				}
			}
				else{
					if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
						defaultString=wheresqlString;
					}
					else{
						defaultString=wheresqlString +" and  "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
								"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
				
					}
					switch (Integer.parseInt(period)) {
					case 1:// today
						wheresqlString =wheresqlString+  " and "+DBUtils.getDBToDateFunction()+"(to_char(current_date,'yyyy/mm/dd'),'yyyy/mm/dd')="+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')";;
						break;
					case 2:// one week
						wheresqlString = wheresqlString+" and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-7 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
						break;
					case 3:// two week
						wheresqlString = wheresqlString+"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-14 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
						break;
					case 4:// one month
						wheresqlString =wheresqlString+ "and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>(current_date - interval '1 month')";
						break;
					default:
						//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
						wheresqlString =defaultString;
						break;
					}
				}
		 String sql="";
			if(bizName!=null){
				String whereBizString="";
			
				if("ALL".equals(bizName)){
					whereBizString="";
				}
			   else{
				   try {
						bizName = URLDecoder.decode(bizName, "utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage());
					}// 字符编码和文档编码一致
				   if(bizName.equals("others")){
					   whereBizString="   inner join ( select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem'  and  attr.value not in " +
						   		"(select businessname as \"name\" from tf_avmon_biz_dictionary) union all  ( select distinct mo_id, 'Other' from td_avmon_mo_info_attribute attr where attr.mo_id not in (select mo_id from td_avmon_mo_info_attribute attr where name='businessSystem'))) t on t1.mo_id = t.mo_id  ";
					
				   }
				   else{
					   whereBizString="inner join (select value,mo_id from td_avmon_mo_info_attribute " +
						   		"where name='businessSystem' and value='"+bizName+"') d on t1.mo_id = d.mo_id";   
				   }
				  
				}
				 sql = String.format(
						SqlManager.getSql(AlarmService.class, "getCurrentAlarmAllCount"),
						whereBizString,wheresqlString);
			}else{
				 sql = String.format(
						SqlManager.getSql(AlarmService.class, "getCurrentAlarmAllCount"),
						wheresqlString);
			}

	int n = jdbc.queryForInt(sql);

	return n;
}
	
	public int getActiveAlarmByHandlerCount(HttpServletRequest request) {
		String level=request.getParameter("level");
		String wheresqlString=" where t1.CURRENT_GRADE="+"'"+level+"'";
		  String period=request.getParameter("period");
	        String s_start_date=request.getParameter("s_start_date");
	        String s_end_date=request.getParameter("s_end_date");
			String defaultString="";
			String typeId=request.getParameter("typeId");
	        if(typeId!=null&&typeId.length()>0){
	        	wheresqlString=wheresqlString+" and tt.type_Id like '"+typeId+"_%'";
	        	
	        }
			if (period == null||period.equals("")) {
				period = "4";
			}
			if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
				defaultString=wheresqlString;
			}
			else{
				defaultString=wheresqlString+" and trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
						"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			}
			switch (Integer.parseInt(period)) {
			case 1:// today
				wheresqlString =wheresqlString+ "and trunc(sysdate)=trunc(t1.first_occur_time)";
				break;
			case 2:// one week
				wheresqlString = wheresqlString+" and trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 3:// two week
				wheresqlString = wheresqlString+"and trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 4:// one month
				wheresqlString =wheresqlString+"and t1.first_occur_time>add_months(sysdate,-1)";
				break;
			default:
				//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
				wheresqlString =defaultString;
				break;
			}
		//getCurrentAlarmAllCount
		String sql = String.format(SqlManager.getSql(AlarmService.class,
				"getCurrentAlarmHandlerAllCount"), wheresqlString);
		int n = jdbc.queryForInt(sql);

		return n;
	}
	
	public int getActiveAlarmByNoHandlerCount(HttpServletRequest request) {
		 String bizName=request.getParameter("bizName");
		String level=request.getParameter("level");
		String wheresqlString=" where t1.CURRENT_GRADE="+"'"+level+"'";
		  String period=request.getParameter("period");
	        String s_start_date=request.getParameter("s_start_date");
	        String s_end_date=request.getParameter("s_end_date");
			String defaultString="";
			String typeId=request.getParameter("typeId");
	        if(typeId!=null&&typeId.length()>0){
	        	wheresqlString=wheresqlString+" and tt.type_Id like '"+typeId+"_%'";
	        	
	        }
			if (period == null||period.equals("")) {
				period = "4";
			}
			if(DBUtils.isOracle()){
				if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
					defaultString=wheresqlString;
				}
				else{
					defaultString=wheresqlString+" and trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
							"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
				}
				switch (Integer.parseInt(period)) {
				case 1:// today
					wheresqlString =wheresqlString+ "and trunc(sysdate)=trunc(t1.first_occur_time)";
					break;
				case 2:// one week
					wheresqlString = wheresqlString+" and trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
					break;
				case 3:// two week
					wheresqlString = wheresqlString+"and trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
					break;
				case 4:// one month
					wheresqlString =wheresqlString+"and t1.first_occur_time>add_months(sysdate,-1)";
					break;
				default:
					//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
					wheresqlString =defaultString;
					break;
				}
			}
				else{
					if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
						defaultString=wheresqlString;
					}
					else{
						defaultString=wheresqlString +" and  "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
								"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
				
					}
					switch (Integer.parseInt(period)) {
					case 1:// today
						wheresqlString =wheresqlString+  " and "+DBUtils.getDBToDateFunction()+"(to_char(current_date,'yyyy/mm/dd'),'yyyy/mm/dd')="+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')";;
						break;
					case 2:// one week
						wheresqlString = wheresqlString+" and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-7 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
						break;
					case 3:// two week
						wheresqlString = wheresqlString+"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-14 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
						break;
					case 4:// one month
						wheresqlString =wheresqlString+ "and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>(current_date - interval '1 month')";
						break;
					default:
						//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
						wheresqlString =defaultString;
						break;
					}
				}
			String sql="";
			if(bizName!=null){
				String whereBizString="";
			
				if("ALL".equals(bizName)){
					whereBizString="";
				}
			   else{
				   try {
						bizName = URLDecoder.decode(bizName, "utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage());
					}// 字符编码和文档编码一致
				   if(bizName.equals("others")){
					   whereBizString="   inner join ( select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem'  and  attr.value not in " +
						   		"(select businessname as \"name\" from tf_avmon_biz_dictionary) union all  ( select distinct mo_id, 'Other' from td_avmon_mo_info_attribute attr where attr.mo_id not in (select mo_id from td_avmon_mo_info_attribute attr where name='businessSystem'))) t on t1.mo_id = t.mo_id  ";
					
				   }
				   else{
					   whereBizString="inner join (select value,mo_id from td_avmon_mo_info_attribute " +
						   		"where name='businessSystem' and value='"+bizName+"') d on t1.mo_id = d.mo_id";   
				   }
				  
				}
				 sql = String.format(
						SqlManager.getSql(AlarmService.class, "getCurrentAlarmAllCount"),
						whereBizString,wheresqlString);
			}else{
				 sql = String.format(
						SqlManager.getSql(AlarmService.class, "getCurrentAlarmAllCount"),
						wheresqlString);
			}


		int n = jdbc.queryForInt(sql);
 
		return n;
	}
	
	public int getAlarmDataTypeForCaptionCount(HttpServletRequest request) {
		String typeId=request.getParameter("typeId");
		 String bizName=request.getParameter("bizName");
		String wheresqlString="";
		if(typeId.contains("_")){
			wheresqlString="where tt.type_Id = "+"'"+typeId+"'";
		}
		else{
			wheresqlString="where tt.type_Id like "+"'"+typeId+"_%'";
		}
		  String period=request.getParameter("period");
	        String s_start_date=request.getParameter("s_start_date");
	        String s_end_date=request.getParameter("s_end_date");
			String defaultString="";
			if (period == null||period.equals("")) {
				period = "4";
			}
			 if(DBUtils.isOracle()){
					if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
						defaultString=wheresqlString;
					}
					else{
						defaultString=wheresqlString+" and trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
								"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
					}
					switch (Integer.parseInt(period)) {
					case 1:// today
						wheresqlString =wheresqlString+ "and trunc(sysdate)=trunc(t1.first_occur_time)";
						break;
					case 2:// one week
						wheresqlString = wheresqlString+" and trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
						break;
					case 3:// two week
						wheresqlString = wheresqlString+"and trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
						break;
					case 4:// one month
						wheresqlString =wheresqlString+"and t1.first_occur_time>add_months(sysdate,-1)";
						break;
					default:
						//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
						wheresqlString =defaultString;
						break;
					}
				}
					else{
						if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
							defaultString=wheresqlString;
						}
						else{
							defaultString=wheresqlString +" and  "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
									"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
					
						}
						switch (Integer.parseInt(period)) {
						case 1:// today
							wheresqlString =wheresqlString+  " and "+DBUtils.getDBToDateFunction()+"(to_char(current_date,'yyyy/mm/dd'),'yyyy/mm/dd')="+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')";;
							break;
						case 2:// one week
							wheresqlString = wheresqlString+" and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-7 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
							break;
						case 3:// two week
							wheresqlString = wheresqlString+"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-14 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
							break;
						case 4:// one month
							wheresqlString =wheresqlString+ "and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>(current_date - interval '1 month')";
							break;
						default:
							//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
							wheresqlString =defaultString;
							break;
						}
					}
			 String sql="";
				if(bizName!=null){
					String whereBizString="";
				
					if("ALL".equals(bizName)){
						whereBizString="";
					}
				   else{
					   try {
							bizName = URLDecoder.decode(bizName, "utf-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							logger.error(e.getMessage());
						}// 字符编码和文档编码一致
					   if(bizName.equals("others")){
						   whereBizString="   inner join ( select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem'  and  attr.value not in " +
							   		"(select businessname as \"name\" from tf_avmon_biz_dictionary) union all  ( select distinct mo_id, 'Other' from td_avmon_mo_info_attribute attr where attr.mo_id not in (select mo_id from td_avmon_mo_info_attribute attr where name='businessSystem'))) t on t1.mo_id = t.mo_id  ";
						
					   }
					   else{
						   whereBizString="inner join (select value,mo_id from td_avmon_mo_info_attribute " +
							   		"where name='businessSystem' and value='"+bizName+"') d on t1.mo_id = d.mo_id";   
					   }
					  
					}
					 sql = String.format(
							SqlManager.getSql(AlarmService.class, "getCurrentAlarmAllCount"),
							whereBizString,wheresqlString);
				}else{
					 sql = String.format(
							SqlManager.getSql(AlarmService.class, "getCurrentAlarmAllCount"),
							wheresqlString);
				}

		int n = jdbc.queryForInt(sql);
 
		return n;
	}

	public List getAllActiveAlarmDataList(HttpServletRequest request)
			throws Exception {
		String typeId=request.getParameter("typeId");
		String wheresqlString="";
		if(typeId!=null){
			wheresqlString=" where tt.type_id like '"+typeId+"_%'";
		}
		String sortdatafield = request.getParameter("sortdatafield");
		String sortorder = request.getParameter("sortorder");
		if (sortdatafield == null)
			sortdatafield = "hostName";
		if (sortorder == null)
			sortorder = "desc";
		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getCurrentAlarmList"),
				wheresqlString);
		String pagesize = request.getParameter("pagesize");
		String pagenum = request.getParameter("pagenum");
		Integer pagenum1 = (Integer.parseInt(pagenum));
		String querySql ="";
		if(DBUtils.isOracle()){
	    querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
		
		querySql = querySql + " order by " + "\"" + sortdatafield + "\"" + " "
					+ sortorder;
		}
		else{
			 querySql = generatPageSqlForJQW(sql+" order by " + "\"" + sortdatafield + "\"" + " "
						+ sortorder, pagesize, pagenum1.toString());
			 /*querySql = querySql + " order by " + "\"" + sortdatafield + "\"" + " "
						+ sortorder;*/
		}
		
		List<Map<String, Object>> list = jdbc.queryForList(querySql);
		return list;
	}
	
	public List getActiveAlarmDataListForHandler(HttpServletRequest request)
			throws Exception {
        String level=request.getParameter("level");
		String wheresqlString="where t1.CURRENT_GRADE="+"'"+level+"'";
		String period = request.getParameter("period");
		String s_start_date = request.getParameter("s_start_date");
		String s_end_date = request.getParameter("s_end_date");
		String typeId=request.getParameter("typeId");
        if(typeId!=null&&typeId.length()>0){
        	wheresqlString=wheresqlString+" and tt.type_Id like '"+typeId+"_%'";
        	
        }
		String defaultString = "";
		if (period == null || period.equals("")) {
			period = "4";
		}
		if ((s_start_date == null || "".equals(s_start_date))
				&& (s_end_date == null || "".equals(s_end_date))) {
			defaultString = wheresqlString;
		} else {
			defaultString = wheresqlString
					+ " and trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"(" + "'"
					+ s_start_date + "'" + ",'yyyy/mm/dd') "
					+ "and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"(" + "'"
					+ s_end_date + "'" + ",'yyyy/mm/dd')";
		}
		
		switch (Integer.parseInt(period)) {
		case 1:// today
			wheresqlString = wheresqlString
					+ "and trunc(sysdate)=trunc(t1.first_occur_time)";
			break;
		case 2:// one week
			wheresqlString = wheresqlString
					+ " and trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
			break;
		case 3:// two week
			wheresqlString = wheresqlString
					+ "and trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
			break;
		case 4:// one month
			wheresqlString = wheresqlString
					+ "and t1.first_occur_time>add_months(sysdate,-1)";
			break;
		default:
			// "+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
			wheresqlString = defaultString;
			break;
		}
		String sortdatafield = request.getParameter("sortdatafield");
		String sortorder = request.getParameter("sortorder");
		if (sortdatafield == null)
			sortdatafield = "hostName";
		if (sortorder == null)
			sortorder = "desc";
		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getCurrentNoHandlerAlarmList"),
				wheresqlString);
		String pagesize = request.getParameter("pagesize");
		String pagenum = request.getParameter("pagenum");
		Integer pagenum1 = (Integer.parseInt(pagenum) );
		String querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
		querySql = querySql + " order by " + "\"" + sortdatafield + "\"" + " "
				+ sortorder;
		List<Map<String, Object>> list = jdbc.queryForList(querySql);
		return list;
	}
	
	public List getAlarmDataTypeForCaption(HttpServletRequest request)
			throws Exception {
		String bizName=request.getParameter("bizName");
	    String typeId=request.getParameter("typeId");
		//where tt.type_Id = 'HOST_LINUX'
		String wheresqlString="";
		if(typeId.contains("_")){
			wheresqlString="where tt.type_Id = "+"'"+typeId+"'";
		}
		else{
			wheresqlString="where tt.type_Id like "+"'"+typeId+"_%'";
		}
		  String period=request.getParameter("period");
	        String s_start_date=request.getParameter("s_start_date");
	        String s_end_date=request.getParameter("s_end_date");
			String defaultString="";
			if (period == null||period.equals("")) {
				period = "4";
			}
	   if(DBUtils.isOracle()){
			if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
				defaultString=wheresqlString;
			}
			else{
				defaultString=wheresqlString+" and trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
						"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			}
			switch (Integer.parseInt(period)) {
			case 1:// today
				wheresqlString =wheresqlString+ "and trunc(sysdate)=trunc(t1.first_occur_time)";
				break;
			case 2:// one week
				wheresqlString = wheresqlString+" and trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 3:// two week
				wheresqlString = wheresqlString+"and trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 4:// one month
				wheresqlString =wheresqlString+"and t1.first_occur_time>add_months(sysdate,-1)";
				break;
			default:
				//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
				wheresqlString =defaultString;
				break;
			}
		}
			else{
				if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
					defaultString=wheresqlString;
				}
				else{
					defaultString=wheresqlString +" and  "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
							"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			
				}
				switch (Integer.parseInt(period)) {
				case 1:// today
					wheresqlString =wheresqlString+  " and "+DBUtils.getDBToDateFunction()+"(to_char(current_date,'yyyy/mm/dd'),'yyyy/mm/dd')="+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')";;
					break;
				case 2:// one week
					wheresqlString = wheresqlString+" and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-7 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
					break;
				case 3:// two week
					wheresqlString = wheresqlString+"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-14 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
					break;
				case 4:// one month
					wheresqlString =wheresqlString+ "and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>(current_date - interval '1 month')";
					break;
				default:
					//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
					wheresqlString =defaultString;
					break;
				}
			}
		//if(!"ALL".equals(typeId)&&typeId!=null){
		
		//}
		String sortdatafield = request.getParameter("sortdatafield");
		String sortorder = request.getParameter("sortorder");
		if (sortdatafield == null)
			sortdatafield = "hostName";
		if (sortorder == null)
			sortorder = "desc";
		String sql="";
		if(bizName!=null){
			String whereBizString="";
		
			if("ALL".equals(bizName)){
				whereBizString="";
			}
		   else{
			   try {
					bizName = URLDecoder.decode(bizName, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
				}// 字符编码和文档编码一致
			   if(bizName.equals("others")){
				   whereBizString="   inner join ( select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem'  and  attr.value not in " +
					   		"(select businessname as \"name\" from tf_avmon_biz_dictionary) union all  ( select distinct mo_id, 'Other' from td_avmon_mo_info_attribute attr where attr.mo_id not in (select mo_id from td_avmon_mo_info_attribute attr where name='businessSystem'))) t on t1.mo_id = t.mo_id  ";
				
			   }
			   else{
				   whereBizString="inner join (select value,mo_id from td_avmon_mo_info_attribute " +
					   		"where name='businessSystem' and value='"+bizName+"') d on t1.mo_id = d.mo_id";   
			   }
			  
			}
			 sql = String.format(
					SqlManager.getSql(AlarmService.class, "getCurrentAlarmListByBiz"),
					whereBizString,wheresqlString);
		}else{
			 sql = String.format(
					SqlManager.getSql(AlarmService.class, "getCurrentAlarmList"),
					wheresqlString);
		}
		
		
		String pagesize = request.getParameter("pagesize");
		String pagenum = request.getParameter("pagenum");
		Integer pagenum1 = (Integer.parseInt(pagenum) );
		String querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
		 if(DBUtils.isOracle()){
		querySql = querySql + " order by " + "\"" + sortdatafield + "\"" + " "
				+ sortorder;
		 }
		List<Map<String, Object>> list = jdbc.queryForList(querySql);
		return list;
		
	}
	
	public List getActiveAlarmDataListForNoHandler(HttpServletRequest request)
			throws Exception {
		 String bizName=request.getParameter("bizName");
		String level=request.getParameter("level");
    	String wheresqlString="where t1.CURRENT_GRADE="+"'"+level+"'";
    	  String period=request.getParameter("period");
          String s_start_date=request.getParameter("s_start_date");
          String s_end_date=request.getParameter("s_end_date");
  		String defaultString="";
  		String typeId=request.getParameter("typeId");
        if(typeId!=null&&typeId.length()>0){
        	wheresqlString=wheresqlString+" and tt.type_Id like '"+typeId+"_%'";
        	
        }
  		if (period == null||period.equals("")) {
  			period = "4";
  		}
  		if(DBUtils.isOracle()){
			if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
				defaultString=wheresqlString;
			}
			else{
				defaultString=wheresqlString+" and trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
						"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			}
			switch (Integer.parseInt(period)) {
			case 1:// today
				wheresqlString =wheresqlString+ "and trunc(sysdate)=trunc(t1.first_occur_time)";
				break;
			case 2:// one week
				wheresqlString = wheresqlString+" and trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 3:// two week
				wheresqlString = wheresqlString+"and trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 4:// one month
				wheresqlString =wheresqlString+"and t1.first_occur_time>add_months(sysdate,-1)";
				break;
			default:
				//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
				wheresqlString =defaultString;
				break;
			}
		}
			else{
				if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
					defaultString=wheresqlString;
				}
				else{
					defaultString=wheresqlString +" and  "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
							"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			
				}
				switch (Integer.parseInt(period)) {
				case 1:// today
					wheresqlString =wheresqlString+  " and "+DBUtils.getDBToDateFunction()+"(to_char(current_date,'yyyy/mm/dd'),'yyyy/mm/dd')="+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')";;
					break;
				case 2:// one week
					wheresqlString = wheresqlString+" and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-7 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
					break;
				case 3:// two week
					wheresqlString = wheresqlString+"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-14 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
					break;
				case 4:// one month
					wheresqlString =wheresqlString+ "and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>(current_date - interval '1 month')";
					break;
				default:
					//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
					wheresqlString =defaultString;
					break;
				}
			}
		String sortdatafield = request.getParameter("sortdatafield");
		String sortorder = request.getParameter("sortorder");
		String sql="";
		if (sortdatafield == null)
			sortdatafield = "hostName";
		if (sortorder == null)
			sortorder = "desc";
		if(bizName!=null){
			String whereBizString="";
		
			if("ALL".equals(bizName)){
				whereBizString="";
			}
		   else{
			   try {
					bizName = URLDecoder.decode(bizName, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
				}// 字符编码和文档编码一致
			   if(bizName.equals("others")){
				   whereBizString="   inner join ( select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem'  and  attr.value not in " +
					   		"(select businessname as \"name\" from tf_avmon_biz_dictionary) union all  ( select distinct mo_id, 'Other' from td_avmon_mo_info_attribute attr where attr.mo_id not in (select mo_id from td_avmon_mo_info_attribute attr where name='businessSystem'))) t on t1.mo_id = t.mo_id  ";
				
			   }
			   else{
				   whereBizString="inner join (select value,mo_id from td_avmon_mo_info_attribute " +
					   		"where name='businessSystem' and value='"+bizName+"') d on t1.mo_id = d.mo_id";   
			   }
			  
			}
			 sql = String.format(
					SqlManager.getSql(AlarmService.class, "getCurrentAlarmListByBiz"),
					whereBizString,wheresqlString);
		}else{
			 sql = String.format(
					SqlManager.getSql(AlarmService.class, "getCurrentAlarmList"),
					wheresqlString);
		}
		
		
		String pagesize = request.getParameter("pagesize");
		String pagenum = request.getParameter("pagenum");
		Integer pagenum1 = (Integer.parseInt(pagenum) );
		String querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
		if(DBUtils.isOracle()){
		querySql = querySql + " order by " + "\"" + sortdatafield + "\"" + " "
				+ sortorder;
		 }
		List<Map<String, Object>> list = jdbc.queryForList(querySql);
		return list;
		
	}
	
	public List getAlarmDataForKpi(HttpServletRequest request)
			throws Exception {
		String bizName=request.getParameter("bizName");
        String kpi=request.getParameter("kpi");
        String period=request.getParameter("period");
        String s_start_date=request.getParameter("s_start_date");
        String s_end_date=request.getParameter("s_end_date");
		String wheresqlString="where t10.kpi_name="+"'"+kpi+"'";
		String defaultString="";
		String typeId=request.getParameter("typeId");
        if(typeId!=null&&typeId.length()>0){
        	wheresqlString=wheresqlString+" and tt.type_Id like '"+typeId+"_%'";
        	
        }
		if (period == null||period.equals("")) {
			period = "4";
		}
		if(DBUtils.isOracle()){
			if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
				defaultString=wheresqlString;
			}
			else{
				defaultString=wheresqlString+" and trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
						"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			}
			switch (Integer.parseInt(period)) {
			case 1:// today
				wheresqlString =wheresqlString+ "and trunc(sysdate)=trunc(t1.first_occur_time)";
				break;
			case 2:// one week
				wheresqlString = wheresqlString+" and trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 3:// two week
				wheresqlString = wheresqlString+"and trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 4:// one month
				wheresqlString =wheresqlString+"and t1.first_occur_time>add_months(sysdate,-1)";
				break;
			default:
				//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
				wheresqlString =defaultString;
				break;
			}
		}
			else{
				if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
					defaultString=wheresqlString;
				}
				else{
					defaultString=wheresqlString +" and  "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
							"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			
				}
				switch (Integer.parseInt(period)) {
				case 1:// today
					wheresqlString =wheresqlString+  " and "+DBUtils.getDBToDateFunction()+"(to_char(current_date,'yyyy/mm/dd'),'yyyy/mm/dd')="+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')";;
					break;
				case 2:// one week
					wheresqlString = wheresqlString+" and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-7 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
					break;
				case 3:// two week
					wheresqlString = wheresqlString+"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-14 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
					break;
				case 4:// one month
					wheresqlString =wheresqlString+ "and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>(current_date - interval '1 month')";
					break;
				default:
					//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
					wheresqlString =defaultString;
					break;
				}
			}
		String sortdatafield = request.getParameter("sortdatafield");
		String sortorder = request.getParameter("sortorder");
		if (sortdatafield == null)
			sortdatafield = "hostName";
		if (sortorder == null)
			sortorder = "desc";
		String sql="";
		if(bizName!=null){
			String whereBizString="";
		
			if("ALL".equals(bizName)){
				whereBizString="";
			}
		   else{
			   try {
					bizName = URLDecoder.decode(bizName, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
				}// 字符编码和文档编码一致
			   if(bizName.equals("others")){
				   whereBizString="   inner join ( select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem'  and  attr.value not in " +
					   		"(select businessname as \"name\" from tf_avmon_biz_dictionary) union all  ( select distinct mo_id, 'Other' from td_avmon_mo_info_attribute attr where attr.mo_id not in (select mo_id from td_avmon_mo_info_attribute attr where name='businessSystem'))) t on t1.mo_id = t.mo_id  ";
				
			   }
			   else{
				   whereBizString="inner join (select value,mo_id from td_avmon_mo_info_attribute " +
					   		"where name='businessSystem' and value='"+bizName+"') d on t1.mo_id = d.mo_id";   
			   }
			  
			}
			 sql = String.format(
					SqlManager.getSql(AlarmService.class, "getCurrentAlarmListByBiz"),
					whereBizString,wheresqlString);
		}else{
			 sql = String.format(
					SqlManager.getSql(AlarmService.class, "getCurrentAlarmList"),
					wheresqlString);
		}
		
		
		String pagesize = request.getParameter("pagesize");
		String pagenum = request.getParameter("pagenum");
		Integer pagenum1 = (Integer.parseInt(pagenum) );
		String querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
		 if(DBUtils.isOracle()){
		querySql = querySql + " order by " + "\"" + sortdatafield + "\"" + " "
				+ sortorder;
		 }
		List<Map<String, Object>> list = jdbc.queryForList(querySql);
		return list;
	}
	
	public List getActiveAlarmDataListByCaption(HttpServletRequest request)
			throws Exception {
        String capation=request.getParameter("caption");
        String bizName=request.getParameter("bizName");
        String period=request.getParameter("period");
        String s_start_date=request.getParameter("s_start_date");
        String s_end_date=request.getParameter("s_end_date");
		String wheresqlString="where tt.caption="+"'"+capation+"'";
		String defaultString="";
		String typeId=request.getParameter("typeId");
        if(typeId!=null&&typeId.length()>0){
        	wheresqlString=wheresqlString+" and tt.type_Id like '"+typeId+"_%'";
        	
        }
		if (period == null||period.equals("")) {
			period = "4";
		}
		 if(DBUtils.isOracle()){
				if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
					defaultString=wheresqlString;
				}
				else{
					defaultString=wheresqlString+" and trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
							"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
				}
				switch (Integer.parseInt(period)) {
				case 1:// today
					wheresqlString =wheresqlString+ "and trunc(sysdate)=trunc(t1.first_occur_time)";
					break;
				case 2:// one week
					wheresqlString = wheresqlString+" and trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
					break;
				case 3:// two week
					wheresqlString = wheresqlString+"and trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
					break;
				case 4:// one month
					wheresqlString =wheresqlString+"and t1.first_occur_time>add_months(sysdate,-1)";
					break;
				default:
					//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
					wheresqlString =defaultString;
					break;
				}
			}
		 else{
				if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
					defaultString=wheresqlString;
				}
				else{
					defaultString=wheresqlString +" and  "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
							"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			
				}
				switch (Integer.parseInt(period)) {
				case 1:// today
					wheresqlString =wheresqlString+  " and "+DBUtils.getDBToDateFunction()+"(to_char(current_date,'yyyy/mm/dd'),'yyyy/mm/dd')="+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')";;
					break;
				case 2:// one week
					wheresqlString = wheresqlString+" and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-7 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
					break;
				case 3:// two week
					wheresqlString = wheresqlString+"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-14 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
					break;
				case 4:// one month
					wheresqlString =wheresqlString+ "and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>(current_date - interval '1 month')";
					break;
				default:
					//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
					wheresqlString =defaultString;
					break;
				}
			}
		String sortdatafield = request.getParameter("sortdatafield");
		String sortorder = request.getParameter("sortorder");
		String sql="";
		if (sortdatafield == null)
			sortdatafield = "hostName";
		if (sortorder == null)
			sortorder = "desc";
		if(bizName!=null){
			String whereBizString="";
		
			if("ALL".equals(bizName)){
				whereBizString="";
			}
		   else{
			   try {
					bizName = URLDecoder.decode(bizName, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
				}// 字符编码和文档编码一致
			   if(bizName.equals("others")){
				   whereBizString="   inner join ( select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem'  and  attr.value not in " +
					   		"(select businessname as \"name\" from tf_avmon_biz_dictionary) union all  ( select distinct mo_id, 'Other' from td_avmon_mo_info_attribute attr where attr.mo_id not in (select mo_id from td_avmon_mo_info_attribute attr where name='businessSystem'))) t on t1.mo_id = t.mo_id  ";
				
			   }
			   else{
				   whereBizString="inner join (select value,mo_id from td_avmon_mo_info_attribute " +
					   		"where name='businessSystem' and value='"+bizName+"') d on t1.mo_id = d.mo_id";   
			   }
			  
			}
			 sql = String.format(
					SqlManager.getSql(AlarmService.class, "getCurrentAlarmListByBiz"),
					whereBizString,wheresqlString);
		}else{
			 sql = String.format(
					SqlManager.getSql(AlarmService.class, "getCurrentAlarmList"),
					wheresqlString);
		}
		
		String pagesize = request.getParameter("pagesize");
		String pagenum = request.getParameter("pagenum");
		Integer pagenum1 = (Integer.parseInt(pagenum) );
		String querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
		 if(DBUtils.isOracle()){
			 querySql = querySql + " order by " + "\"" + sortdatafield + "\"" + " "
						+ sortorder;
		 }
		List<Map<String, Object>> list = jdbc.queryForList(querySql);
		return list;
	}

	private int getAlarmCountByMoId(HttpServletRequest request, String moId) {
		String where = "where t1.mo_id=" + "'" + moId + "'";
		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getCurrentAlarmCount"),
				where);
		int n = jdbc.queryForInt(sql);
		return n;
	}

	// jay add search all alarm data by

	@SuppressWarnings("rawtypes")
	private String getActiveAlarmSqlWhereByCriteria(Integer[] grades, Map filter) {
		String where = " where 1=1 ";
		// grade list
		/*
		 * String g = ""; if (grades != null) { for (Integer n : grades) { if (n
		 * != null) { g += "," + n; } } }
		 */
		if (grades.length > 0) {
		String levelsString = Arrays.toString(grades).replace("[", "")
				.replace("]", "");
		where += " and t1.CURRENT_GRADE in (" + levelsString + ") ";
		 }
  
		// other filters
		if (filter != null) {
			// 我的告警
			String showMyAlarm = (String) filter.get("showMyAlarm");
			if (showMyAlarm != null && showMyAlarm.length() > 0) {
				where += " and t1.CONFIRM_USER ='" + showMyAlarm + "' ";
			}
			// 新告警
			String showNewAlarm = (String) filter.get("showNewAlarm");
			if (showNewAlarm != null && showNewAlarm.length() > 0) {
				where += " and t1.STATUS  ='0' ";
			}
			// 设备名称
			String s_moCaption = (String) filter.get("s_moCaption");
			if (s_moCaption != null && s_moCaption.length() > 0) {
				where += " and t2.CAPTION like '%" + s_moCaption + "%' ";
			}
			// 告警内容
			String s_content_t = (String) filter.get("s_content_t");
			if (s_content_t != null && s_content_t.length() > 0) {
				where += " and t1.content like '%" + s_content_t + "%' ";
			}
			// IP地址
			String s_attr_ipaddr = (String) filter.get("s_attr_ipaddr");
			if (s_attr_ipaddr != null && s_attr_ipaddr.length() > 0) {
				where += " and t3.VALUE like '%" + s_attr_ipaddr + "%' ";
			}
			// 处理人
			String s_aknowledge_by = (String) filter.get("s_aknowledge_by");
			if (s_aknowledge_by != null && s_aknowledge_by.length() > 0) {
				where += " and t1.CONFIRM_USER in (" + s_aknowledge_by + ") ";
			}
			// 业务系统
			String s_attr_businessSystem = (String) filter
					.get("s_attr_businessSystem");
			if (s_attr_businessSystem != null
					&& s_attr_businessSystem.length() > 0) {
				where += " and t4.VALUE like '%" + s_attr_businessSystem
						+ "%' ";
			}
			// 位置
			String s_attr_position = (String) filter.get("s_attr_position");
			if (s_attr_position != null && s_attr_position.length() > 0) {
				where += " and t5.VALUE like '%" + s_attr_position + "%' ";
			}
			
			//typeId
			String typeId = (String) filter.get("typeId");
			if (typeId != null && typeId.length() > 0) {
				where += " and tt.type_id like '" + typeId + "_%' ";
			}
			// 告警开始时间
			String s_start_time = (String) filter.get("s_start_time");
			if (s_start_time != null && s_start_time.length() > 0) {
				try {
					where += " and t1.first_occur_time >= "+DBUtils.getDBToDateFunction()+"('"
							+ s_start_time + "', 'YYYY-MM-DD HH24:MI:SS')";
				} catch (ParseException e) {
					logger.error(e.getMessage());
				}
			}
			// 告警结束时间
			String s_end_time = (String) filter.get("s_end_time");
			if (s_end_time != null && s_end_time.length() > 0) {
				try {
					where += " and t1.first_occur_time < "+DBUtils.getDBToDateFunction()+"('"
							+ s_end_time + "', 'YYYY-MM-DD HH24:MI:SS')";
				} catch (ParseException e) {
					logger.error(e.getMessage());
				}
			}

			String content = (String) filter.get("content");
			if (content != null && content.length() > 0 && !content.equals("*")) {
				where += " and t1.content like '%" + content + "%' ";
			}

			if (filter.get("occurTimes") != null
					&& ((String) filter.get("occurTimes")).length() > 0) {
				Integer occurTimes = Integer.valueOf((String) filter
						.get("occurTimes"));
				where += " and t1.count>=" + occurTimes + " ";
			}
			// 页面最后提交请求时间
			String lastUpdateTimeStr = (String) filter.get("lastUpdateTime");
			if (lastUpdateTimeStr != null && lastUpdateTimeStr.length() > 0) {
				where += " and t1.LAST_OCCUR_TIME >= "+DBUtils.getDBToDateFunction()+"('"
						+ lastUpdateTimeStr + "', 'YYYY-MM-DD HH24:MI:SS')";
			}
		}
		return where;
	}
	
	@SuppressWarnings("rawtypes")
	private String getHistoryAlarmSqlWhereByCriteria(Integer[] grades, Map filter) {
		String where = " where 1=1 ";
		String levelsString = Arrays.toString(grades).replace("[", "")
				.replace("]", "");
		where += " and t1.CURRENT_GRADE in (" + levelsString + ") ";
		if(!"".equals(filter.get("moId"))){
			where += " and t1.MO_ID="+"'"+filter.get("moId")+"'";
		}
		if(DBUtils.isOracle()){
			where +=" and trunc(t1.CLOSE_TIME)>=sysdate-2 and trunc(t1.CLOSE_TIME)<=sysdate";
		}
		else{
			where +=" and "+DBUtils.getDBToDateFunction()+"(to_char(t1.CLOSE_TIME,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-2 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.CLOSE_TIME,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
		}
		return where;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map<String, Object>> getAllActiveAlarmByCriteria(Integer[] grades, Map filter) {
		String whereCondition = this.getActiveAlarmSqlWhereByCriteria(grades,
				filter);
		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getCurrentAlarmList"),
				whereCondition);

		List<Map<String, Object>> listMap = jdbc.queryForList(sql);
		return listMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map<String, Object>> getActiveAlarmByCriteria(HttpServletRequest request,
			Integer[] grades, Map filter, String limit, String start) {
		String whereCondition = this.getActiveAlarmSqlWhereByCriteria(grades,
				filter);
		//////////////pager
		
		String sortdatafield = request.getParameter("sortdatafield");
		String sortorder = request.getParameter("sortorder");
		String  isBatchUpdate= request.getParameter("isBatchUpdate");
		if (sortdatafield == null)
			sortdatafield = "hostName";
		if (sortorder == null)
			sortorder = "desc";
		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getCurrentAlarmList"),
				whereCondition);
		String pagesize = request.getParameter("pagesize");
		if(pagesize==null)
			pagesize="20";
		String pagenum = request.getParameter("pagenum");
		if(pagenum==null)
			pagenum="0";
		Integer pagenum1 = (Integer.parseInt(pagenum) );
		String querySql="";
		if(!Boolean.parseBoolean(isBatchUpdate)){
		if(DBUtils.isOracle()){
			querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
			querySql = querySql + " order by " + "\"" + sortdatafield + "\"" + " "
					+ sortorder;
		}
		else{
			querySql = generatPageSqlForJQW(sql+ " order by " + "\"" + sortdatafield + "\"" + " " + sortorder, pagesize, pagenum1.toString());
		}
		
		}
		else{
			querySql=sql;
		}
		//////////////
		
		List<Map<String, Object>> listMap = jdbc.queryForList(querySql);
		return listMap;

		/*
		 * if (StringUtil.isEmpty(limit) || StringUtil.isEmpty(start)) { //
		 * 定时刷新任务的场合 List<Map<String, Object>> listMap = jdbc.queryForList(sql);
		 * return listMap; } else { // 检索的场合 String querySql =
		 * generatPageSql(sql, limit, start); List<Map<String, Object>> listMap
		 * = jdbc.queryForList(querySql); return listMap; }
		 */
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map<String, Object>> getHistoryActiveAlarmByCriteria(HttpServletRequest request,
			Integer[] grades, Map filter, String limit, String start) {
		String whereCondition = this.getHistoryAlarmSqlWhereByCriteria(grades,
				filter);
		//////////////pager
		
		String sortdatafield = request.getParameter("sortdatafield");
		String sortorder = request.getParameter("sortorder");
		if (sortdatafield == null)
			sortdatafield = "firstOccurTime";
		if (sortorder == null)
			sortorder = "desc";
		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getHistoryAlarmList"),
				whereCondition);
		String pagesize = request.getParameter("pagesize");
		if(pagesize==null)
			pagesize="20";
		String pagenum = request.getParameter("pagenum");
		if(pagenum==null)
			pagenum="0";
		Integer pagenum1 = (Integer.parseInt(pagenum) );
		String querySql = "";
		if(DBUtils.isOracle()){
			querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
			querySql = querySql + " order by " + "\"" + sortdatafield + "\"" + " "
					+ sortorder;
		}
		else{
			querySql = generatPageSqlForJQW(sql+ " order by " + "\"" + sortdatafield + "\"" + " " + sortorder, pagesize, pagenum1.toString());
		}
		/*querySql = querySql + " order by " + "\"" + sortdatafield + "\"" + " "
				+ sortorder;*/
		//////////////
		List<Map<String, Object>> listMap = jdbc.queryForList(querySql);
		return listMap;

		/*
		 * if (StringUtil.isEmpty(limit) || StringUtil.isEmpty(start)) { //
		 * 定时刷新任务的场合 List<Map<String, Object>> listMap = jdbc.queryForList(sql);
		 * return listMap; } else { // 检索的场合 String querySql =
		 * generatPageSql(sql, limit, start); List<Map<String, Object>> listMap
		 * = jdbc.queryForList(querySql); return listMap; }
		 */
	}

	/**
	 * add by jay for CriteriaCount
	 * @param request
	 * @param grades
	 * @param filter
	 * @param limit
	 * @param start
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int getActiveAlarmByCriteriaCount(HttpServletRequest request,
			Integer[] grades, Map filter, String limit, String start) {
		String whereCondition = this.getActiveAlarmSqlWhereByCriteria(grades,
				filter);
		//////////////pager
		
		/*String sortdatafield = request.getParameter("sortdatafield");
		String sortorder = request.getParameter("sortorder");
		if (sortdatafield == null)
			sortdatafield = "hostName";
		if (sortorder == null)
			sortorder = "desc";*/
		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getCurrentAlarmCount"),
				whereCondition);
		/*	String sql = String.format(
		SqlManager.getSql(AlarmService.class, "getCurrentAlarmList"),
		whereCondition);*/
	/*	String pagesize = request.getParameter("pagesize");
		if(pagesize==null)
			pagesize="20";
		String pagenum = request.getParameter("pagenum");
		if(pagenum==null)
			pagenum="0";
		Integer pagenum1 = (Integer.parseInt(pagenum) );*/
		//String querySql = generatPageSql(sql, pagesize, pagenum1.toString());
		/*querySql = querySql + " order by " + "\"" + sortdatafield + "\"" + " "
				+ sortorder;*/
		//////////////
		
	/*	List<Map<String, Object>> listMap = jdbc.queryForList(sql);*/
		int count = jdbc.queryForInt(sql);
		return count;

		/*
		 * if (StringUtil.isEmpty(limit) || StringUtil.isEmpty(start)) { //
		 * 定时刷新任务的场合 List<Map<String, Object>> listMap = jdbc.queryForList(sql);
		 * return listMap; } else { // 检索的场合 String querySql =
		 * generatPageSql(sql, limit, start); List<Map<String, Object>> listMap
		 * = jdbc.queryForList(querySql); return listMap; }
		 */
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int getHistoryAlarmByCriteriaCount(HttpServletRequest request,
			Integer[] grades, Map filter, String limit, String start) {
		String whereCondition = this.getHistoryAlarmSqlWhereByCriteria(grades,
				filter);
		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getHistoryAlarmListCount"),
				whereCondition);
		int count = jdbc.queryForInt(sql);
		return count;

		/*
		 * if (StringUtil.isEmpty(limit) || StringUtil.isEmpty(start)) { //
		 * 定时刷新任务的场合 List<Map<String, Object>> listMap = jdbc.queryForList(sql);
		 * return listMap; } else { // 检索的场合 String querySql =
		 * generatPageSql(sql, limit, start); List<Map<String, Object>> listMap
		 * = jdbc.queryForList(querySql); return listMap; }
		 */
	}
	/**
	 * 取得活动告警
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, Object>> getActiveAlarmDataByCriteria(
			HttpServletRequest request) throws Exception {
		// String pagesize = request.getParameter("limit");
		/*
		 * String alarmId = request.getParameter("alarmId"); String monitor =
		 * request.getParameter("monitor"); String occurTimes =
		 * request.getParameter("occurTimes"); String alarmTitle =
		 * request.getParameter("alarmTitle");
		 */
		String level = request.getParameter("level");
		String typeId = request.getParameter("typeId");
		String showMyAlarm = request.getParameter("isMyalarm");
		String showNewAlarm = request.getParameter("isNewalarm");
		String lastUpdateTime = request.getParameter("lastUpdateTime");
		
		HashMap filter = new HashMap();
		if(typeId!=null){
			if(!typeId.equals("ALL")&&(!typeId.equals(""))){
				filter.put("typeId", typeId);
			}
		}
		// 我的告警
		if ("1".equals(showMyAlarm)) {
			String userId = Utils.getCurrentUserId(request);
			filter.put("showMyAlarm", userId);
		} else {
			filter.put("showMyAlarm", null);
		}
		// 新告警
		if ("1".equals(showNewAlarm)) {
			filter.put("showNewAlarm", showNewAlarm);
		} else {
			filter.put("showNewAlarm", null);
		}
		/*
		 * filter.put("alarmId", alarmId); filter.put("monitor", monitor);
		 * filter.put("occurTimes", occurTimes); filter.put("content",
		 * alarmTitle);
		 */
		// 最后请求时间
		/*
		 * if (!StringUtils.isEmpty(lastUpdateTime)) { SimpleDateFormat
		 * myFormatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
		 * filter.put("lastUpdateTime", myFormatter.format(new Date(new Long(
		 * (String) lastUpdateTime).longValue()))); } else {
		 * filter.put("lastUpdateTime", null); }
		 */

		// 高级检索条件对应
		// 设备名称

		if (null != request.getParameter("s_moCaption")) {
			filter.put(
					"s_moCaption",
					new String(request.getParameter("s_moCaption").getBytes(
							"ISO-8859-1"), "UTF-8"));
		}

		// 告警内容
		if (null != request.getParameter("s_content_t")) {
			String s_content_t="";
			try {
				s_content_t=URLDecoder.decode(request.getParameter("s_content_t"), "utf-8");

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}//字符编码和文档编码一致
			filter.put("s_content_t",s_content_t);
		}

		// IP地址
		filter.put("s_attr_ipaddr", request.getParameter("s_attr_ipaddr"));
		// 处理人
		if (!StringUtil.isEmpty(request.getParameter("s_aknowledge_by"))) {
			String s_aknowledge_by="";
			try {
				s_aknowledge_by=URLDecoder.decode(request.getParameter("s_aknowledge_by"), "utf-8");

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}//字符编码和文档编码一致
			JdbcTemplate jdbcTemplate = SpringContextHolder
					.getBean("jdbcTemplate");

			String sql = "select USER_ID as \"USER_ID\" from portal_users where real_name like '%"
					+ s_aknowledge_by + "%' ";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			String userId = "";
			for (Map<String, Object> map : list) {
				userId += "'" + map.get("USER_ID") + "'" + ",";
			}
			if (!StringUtil.isEmpty(userId) && userId.length() > 0) {
				userId = userId.substring(0, userId.length() - 1);
			}
			// add by mark start 2014-1-20
			else {
				userId += "'" + request.getParameter("s_aknowledge_by") + "'"
						+ ",";
				userId = userId.substring(0, userId.length() - 1);
			}
			// add by mark end 2014-1-20

			filter.put("s_aknowledge_by", userId);
		}
        
		// 业务系统
		if (null != request.getParameter("s_attr_businessSystem")) {
			String s_attr_businessSystem="";
			try {
				s_attr_businessSystem=URLDecoder.decode(request.getParameter("s_attr_businessSystem"), "utf-8");

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}//字符编码和文档编码一致
			
			filter.put("s_attr_businessSystem",
					s_attr_businessSystem);
		}

		// 位置
		if (null != request.getParameter("s_attr_position")) {
			filter.put("s_attr_position",
					new String(request.getParameter("s_attr_position")
							.getBytes("ISO-8859-1"), "UTF-8"));
		}

		// 告警开始时间
		String s_start_date = request.getParameter("s_start_time");
		String s_start_hours = request.getParameter("s_start_hours");
		String s_start_minutes = request.getParameter("s_start_minutes");
		if(s_start_hours==null&&s_start_minutes==null){
			s_start_hours="00";
			s_start_minutes="00";
		}
		
		if (!StringUtil.isEmpty(s_start_date)
				//&& !StringUtil.isEmpty(s_start_hours)
				//&& !StringUtil.isEmpty(s_start_minutes)
				) {
			String s_start_time = s_start_date + " " + s_start_hours + ":"
					+ s_start_minutes;
			filter.put("s_start_time", s_start_time);
		} else {
			filter.put("s_start_time", null);
		}

		// 告警结束时间 2012/12/05 00:00
		String s_end_date = request.getParameter("s_end_time");
		String s_end_hours = request.getParameter("s_end_hours");
		String s_end_minutes = request.getParameter("s_end_minutes");
		if(s_end_hours==null&&s_end_minutes==null){
			s_end_hours="00";
			s_end_minutes="00";
		}
		if (!StringUtil.isEmpty(s_end_date) ) {
			String s_end_time =null;
			//if(s_end_date.equals(s_start_date)){
				  SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				  Calendar cal = Calendar.getInstance();
					 cal.setTime(format.parse(s_end_date));
					 cal.add(Calendar.DATE, 1);
					 s_end_date=format.format(cal.getTime());
					 s_end_time = s_end_date + " " + s_end_hours + ":"+ s_end_minutes;
			//}
			//else{
				 //s_end_time = s_end_date + " " + s_end_hours + ":"+ s_end_minutes;
			//}
			filter.put("s_end_time", s_end_time);
		} else {
			filter.put("s_end_time", null);
		}

		// List<Integer> levelList = new ArrayList<Integer>();
		// int rowCount = getActiveAlarmCount(path, levels, filter);
		String limit = request.getParameter("limit_active");
		String start = request.getParameter("start_active");
		List<Map<String, Object>> list = null;
		if (level == null) {
			list = getActiveAlarmByCriteria(request,new Integer[] { 1, 2, 3, 4, 5 },
					filter, limit, start);
		} else {
			list = getActiveAlarmByCriteria(request,
					new Integer[] { Integer.valueOf(level) }, filter, limit,
					start);
		}
		return list;
	}
	
	
	/**
	 * 取得活动告警总数
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getActiveAlarmDataByCriteriaCount(
			HttpServletRequest request) throws Exception {
		String level = request.getParameter("level");
		String showMyAlarm = request.getParameter("isMyalarm");
		String showNewAlarm = request.getParameter("isNewalarm");
		HashMap filter = new HashMap();
		// 我的告警
		if ("1".equals(showMyAlarm)) {
			String userId = Utils.getCurrentUserId(request);
			filter.put("showMyAlarm", userId);
		} else {
			filter.put("showMyAlarm", null);
		}
		// 新告警
		if ("1".equals(showNewAlarm)) {
			filter.put("showNewAlarm", showNewAlarm);
		} else {
			filter.put("showNewAlarm", null);
		}
		/*
		 * filter.put("alarmId", alarmId); filter.put("monitor", monitor);
		 * filter.put("occurTimes", occurTimes); filter.put("content",
		 * alarmTitle);
		 */
		// 最后请求时间
		/*
		 * if (!StringUtils.isEmpty(lastUpdateTime)) { SimpleDateFormat
		 * myFormatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
		 * filter.put("lastUpdateTime", myFormatter.format(new Date(new Long(
		 * (String) lastUpdateTime).longValue()))); } else {
		 * filter.put("lastUpdateTime", null); }
		 */

		// 高级检索条件对应
		// 设备名称

		if (null != request.getParameter("s_moCaption")) {
			filter.put(
					"s_moCaption",
					new String(request.getParameter("s_moCaption").getBytes(
							"ISO-8859-1"), "UTF-8"));
		}

		// 告警内容

				if (null != request.getParameter("s_content_t")) {
					String s_content_t="";
					try {
						s_content_t=URLDecoder.decode(request.getParameter("s_content_t"), "utf-8");

					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage());
					}//字符编码和文档编码一致
					filter.put("s_content_t",s_content_t);
				}
/*		if (null != request.getParameter("s_content_t")) {

			filter.put(
					"s_content_t",
					new String(request.getParameter("s_content_t").getBytes(
							"ISO-8859-1"), "UTF-8"));
		}*/

		// IP地址
		filter.put("s_attr_ipaddr", request.getParameter("s_attr_ipaddr"));
		// 处理人
		if (!StringUtil.isEmpty(request.getParameter("s_aknowledge_by"))) {
			JdbcTemplate jdbcTemplate = SpringContextHolder
					.getBean("jdbcTemplate");

			String sql = "select USER_ID as \"USER_ID\" from portal_users where real_name like '%"
					+ new String(request.getParameter("s_aknowledge_by")
							.getBytes("ISO-8859-1"), "UTF-8") + "%' ";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			String userId = "";
			for (Map<String, Object> map : list) {
				userId += "'" + map.get("USER_ID") + "'" + ",";
			}
			if (!StringUtil.isEmpty(userId) && userId.length() > 0) {
				userId = userId.substring(0, userId.length() - 1);
			}
			// add by mark start 2014-1-20
			else {
				userId += "'" + request.getParameter("s_aknowledge_by") + "'"
						+ ",";
				userId = userId.substring(0, userId.length() - 1);
			}
			// add by mark end 2014-1-20

			filter.put("s_aknowledge_by", userId);
		}

		// 业务系统
		if (null != request.getParameter("s_attr_businessSystem")) {
			filter.put("s_attr_businessSystem",
					new String(request.getParameter("s_attr_businessSystem")
							.getBytes("ISO-8859-1"), "UTF-8"));
		}

		// 位置
		if (null != request.getParameter("s_attr_position")) {
			filter.put("s_attr_position",
					new String(request.getParameter("s_attr_position")
							.getBytes("ISO-8859-1"), "UTF-8"));
		}

		// 告警开始时间
		String s_start_date = request.getParameter("s_start_time");
		String s_start_hours = request.getParameter("s_start_hours");
		String s_start_minutes = request.getParameter("s_start_minutes");
		if(s_start_hours==null&&s_start_minutes==null){
			s_start_hours="00";
			s_start_minutes="00";
		} 
		if (!StringUtil.isEmpty(s_start_date)
				&& !StringUtil.isEmpty(s_start_hours)
				&& !StringUtil.isEmpty(s_start_minutes)) {
			String s_start_time = s_start_date + " " + s_start_hours + ":"
					+ s_start_minutes;
			filter.put("s_start_time", s_start_time);
		} else {
			filter.put("s_start_time", null);
		}

		// 告警结束时间 2012/12/05 00:00
		String s_end_date = request.getParameter("s_end_time");
		String s_end_hours = request.getParameter("s_end_hours");
		String s_end_minutes = request.getParameter("s_end_minutes");
		if(s_end_hours==null&&s_end_minutes==null){
			s_end_hours="00";
			s_end_minutes="00";
		} 
		if (!StringUtil.isEmpty(s_end_date) && !StringUtil.isEmpty(s_end_hours)
				&& !StringUtil.isEmpty(s_end_minutes)) {
			String s_end_time =null;
			//if(s_end_date.equals(s_start_date)){
				  SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				  Calendar cal = Calendar.getInstance();
					 cal.setTime(format.parse(s_end_date));
					 cal.add(Calendar.DATE, 1);
					 s_end_date=format.format(cal.getTime());
					 s_end_time = s_end_date + " " + s_end_hours + ":"+ s_end_minutes;
			//}
			//else{
			//	 s_end_time = s_end_date + " " + s_end_hours + ":"+ s_end_minutes;
			//}
			filter.put("s_end_time", s_end_time);
		} else {
			filter.put("s_end_time", null);
		}

		// List<Integer> levelList = new ArrayList<Integer>();
		// int rowCount = getActiveAlarmCount(path, levels, filter);
		String limit = request.getParameter("limit_active");
		String start = request.getParameter("start_active");
		int count=0;
		//List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (level == null) {
			/*list = getActiveAlarmByCriteriaCount(request,new Integer[] { 1, 2, 3, 4, 5 },
					filter, limit, start);*/
			count = getActiveAlarmByCriteriaCount(request,new Integer[] { 1, 2, 3, 4, 5 },
					filter, limit, start);
		} else {
			/*list = getActiveAlarmByCriteriaCount(request,
					new Integer[] { Integer.valueOf(level) }, filter, limit,
					start);*/
			count = getActiveAlarmByCriteriaCount(request,
					new Integer[] { Integer.valueOf(level) }, filter, limit,
					start);
		}
		return count;
	}
	
	
	/**
	 * 取得历史告警总数
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getHistoryAlarmDataByCriteriaCount(
			HttpServletRequest request) throws Exception {
		String level = request.getParameter("level");
		String moId = request.getParameter("moId");
		HashMap filter = new HashMap();
		String limit = request.getParameter("limit_active");
		String start = request.getParameter("start_active");
		filter.put("moId", moId); 
		if (level == null) {
			return getHistoryAlarmByCriteriaCount(request,new Integer[] { 1, 2, 3, 4, 5 },
					filter, limit, start);
		} else {
			return getHistoryAlarmByCriteriaCount(request,
					new Integer[] { Integer.valueOf(level) }, filter, limit,
					start);
		}
		
	}

	// /////////////jay add end

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getActiveAllAlarmList(HttpServletRequest request)
			throws Exception {

		String path = request.getParameter("treeId");
		// String mo = request.getParameter("treeId");
		// String page = null;
		// if (MyFunc.nullToShort(request.getParameter("start")) == 0) {
		// page = "1";
		// } else {
		// page =
		// MyFunc.nullToString(MyFunc.nullToShort(request.getParameter("start"))
		// / MyFunc.nullToShort(request.getParameter("limit")) + 1);
		// }

		String sortname = null, sortorder = null;
		if (sortname == null || sortname.length() == 0) {
			sortname = "t1.last_occur_time";
			sortorder = "desc";
		}
		String sort = request.getParameter("sort");
		if (!StringUtil.isEmpty(sort)) {
			Gson gson = new Gson();
			List<Map<String, String>> attrList = gson.fromJson(sort,
					new TypeToken<List<Map<String, String>>>() {
					}.getType());

			for (Map<String, String> temp : attrList) {
				sortname = temp.get("property");
				sortorder = temp.get("direction");
			}

			// 将对应的字段与数据库对应
			if (!StringUtil.isEmpty(sortname)) {
				if ("level".equals(sortname)) {
					sortname = "t1.CURRENT_GRADE";
				} else if ("status".equals(sortname)) {
					sortname = "t1.STATUS";
				} else if ("moCaption".equals(sortname)) {
					sortname = "t2.CAPTION";
				} else if ("attr_ipaddr".equals(sortname)) {
					sortname = "t3.VALUE";
				} else if ("title".equals(sortname)) {
					sortname = "t1.TITLE";
				} else if ("content_t".equals(sortname)) {
					sortname = "t1.CONTENT";
				} else if ("firstOccurTime".equals(sortname)) {
					sortname = "t1.FIRST_OCCUR_TIME";
				} else if ("lastOccurTime".equals(sortname)) {
					sortname = "t1.LAST_OCCUR_TIME";
					sortorder = "desc";
				} else if ("occurTimes".equals(sortname)) {
					sortname = "t1.COUNT";
				} else if ("attr_result".equals(sortname)) {
					sortname = "t1.SOLUTION";
				} else if ("attr_actorUser".equals(sortname)) {
					sortname = "t1.CONFIRM_USER";
				} else if ("attr_businessSystem".equals(sortname)) {
					sortname = "t4.VALUE";
				} else if ("attr_position".equals(sortname)) {
					sortname = "t5.VALUE";
				} else if ("attr_usage".equals(sortname)) {
					sortname = "t6.VALUE";
				} else if ("attr_owner".equals(sortname)) {
					sortname = "t1.last_occur_time";
					sortorder = "desc";
				} else if ("hostName".equals(sortname)) {
					sortname = "t7.VALUE";
				}
			}
		}

		// String pagesize = request.getParameter("limit");
		String alarmId = request.getParameter("alarmId");
		String monitor = request.getParameter("monitor");
		String occurTimes = request.getParameter("occurTimes");
		String alarmTitle = request.getParameter("alarmTitle");
		String level = request.getParameter("level");
		String showMyAlarm = request.getParameter("isMyalarm");
		String showNewAlarm = request.getParameter("isNewalarm");
		String lastUpdateTime = request.getParameter("lastUpdateTime");

		HashMap filter = new HashMap();
		// 我的告警
		if ("1".equals(showMyAlarm)) {
			String userId = Utils.getCurrentUserId(request);
			filter.put("showMyAlarm", userId);
		} else {
			filter.put("showMyAlarm", null);
		}
		// 新告警
		if ("1".equals(showNewAlarm)) {
			filter.put("showNewAlarm", showNewAlarm);
		} else {
			filter.put("showNewAlarm", null);
		}
		filter.put("alarmId", alarmId);
		filter.put("monitor", monitor);
		filter.put("occurTimes", occurTimes);
		filter.put("content", alarmTitle);
		// 最后请求时间
		if (!StringUtils.isEmpty(lastUpdateTime)) {
			SimpleDateFormat myFormatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			filter.put("lastUpdateTime", myFormatter.format(new Date(new Long(
					(String) lastUpdateTime).longValue())));
		} else {
			filter.put("lastUpdateTime", null);
		}

		// 高级检索条件对应
		// 设备名称

		if (null != request.getParameter("s_moCaption")) {
			filter.put(
					"s_moCaption",
					new String(request.getParameter("s_moCaption").getBytes(
							"ISO-8859-1"), "UTF-8"));
		}

		// 告警内容
		if (null != request.getParameter("s_content_t")) {

			filter.put(
					"s_content_t",
					new String(request.getParameter("s_content_t").getBytes(
							"ISO-8859-1"), "UTF-8"));
		}

		// IP地址
		filter.put("s_attr_ipaddr", request.getParameter("s_attr_ipaddr"));
		// 处理人
		if (!StringUtil.isEmpty(request.getParameter("s_aknowledge_by"))) {
			JdbcTemplate jdbcTemplate = SpringContextHolder
					.getBean("jdbcTemplate");

			String sql = "select USER_ID as \"USER_ID\" from portal_users where real_name like '%"
					+ new String(request.getParameter("s_aknowledge_by")
							.getBytes("ISO-8859-1"), "UTF-8") + "%' ";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			String userId = "";
			for (Map<String, Object> map : list) {
				userId += "'" + map.get("USER_ID") + "'" + ",";
			}
			if (!StringUtil.isEmpty(userId) && userId.length() > 0) {
				userId = userId.substring(0, userId.length() - 1);
			}
			// add by mark start 2014-1-20
			else {
				userId += "'" + request.getParameter("s_aknowledge_by") + "'"
						+ ",";
				userId = userId.substring(0, userId.length() - 1);
			}
			// add by mark end 2014-1-20

			filter.put("s_aknowledge_by", userId);
		}

		// 业务系统
		if (null != request.getParameter("s_attr_businessSystem")) {
			filter.put("s_attr_businessSystem",
					new String(request.getParameter("s_attr_businessSystem")
							.getBytes("ISO-8859-1"), "UTF-8"));
		}

		// 位置
		if (null != request.getParameter("s_attr_position")) {
			filter.put("s_attr_position",
					new String(request.getParameter("s_attr_position")
							.getBytes("ISO-8859-1"), "UTF-8"));
		}

		// 告警开始时间
		String s_start_date = request.getParameter("s_start_date");
		String s_start_hours = request.getParameter("s_start_hours");
		String s_start_minutes = request.getParameter("s_start_minutes");
		if (!StringUtil.isEmpty(s_start_date)
				&& !StringUtil.isEmpty(s_start_hours)
				&& !StringUtil.isEmpty(s_start_minutes)) {
			String s_start_time = s_start_date + " " + s_start_hours + ":"
					+ s_start_minutes;
			filter.put("s_start_time", s_start_time);
		} else {
			filter.put("s_start_time", null);
		}

		// 告警结束时间 2012/12/05 00:00
		String s_end_date = request.getParameter("s_end_date");
		String s_end_hours = request.getParameter("s_end_hours");
		String s_end_minutes = request.getParameter("s_end_minutes");
		if (!StringUtil.isEmpty(s_end_date) && !StringUtil.isEmpty(s_end_hours)
				&& !StringUtil.isEmpty(s_end_minutes)) {
			String s_end_time = s_end_date + " " + s_end_hours + ":"
					+ s_end_minutes;
			filter.put("s_end_time", s_end_time);
		} else {
			filter.put("s_end_time", null);
		}

		// List<String> moList = alarmViewService.getAllMoChildrenByParent(mo);
		// String moIds[] = new String[moList.size()];
		// for(int i=0; i < moList.size(); i++){
		// moIds[i] = moList.get(i);
		// }

		List<Integer> levelList = new ArrayList<Integer>();
		if (level != null) {
			for (String s : level.split(",")) {
				if (s.trim().length() > 0) {
					// modify by mark start
					levelList.add(Integer.valueOf(s));
					// modify by mark end
				}
			}
		}
		Integer[] levels = new Integer[levelList.size()];
		for (int i = 0; i < levelList.size(); i++) {
			levels[i] = levelList.get(i);
		}

		int rowCount = getActiveAlarmCount(path, levels, filter);
		String limit = request.getParameter("limit_active");
		String start = request.getParameter("start_active");
		// List<Map<String,Object>> list = getActiveAlarm(path, levels, filter,
		// sortname, sortorder, limit, start);

		List<Map<String, Object>> listAllDelete = getAllActiveAlarm(path,
				levels, filter, sortname, sortorder);

		// for(int i=0;i<listAllDelete.size();i++)
		// {
		// System.out.println("mo_id  :  " + listAllDelete.get(i).get("mo"));
		// System.out.println("alarm_id :  " + listAllDelete.get(i).get("id"));
		// }

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("activeTotal", rowCount);
		map.put("activeData", listAllDelete);
		return map;
	}

	// @SuppressWarnings({ "rawtypes", "unchecked" })
	public void deleteAllActiveAllAlarm(HttpServletRequest request)
			throws Exception {
		// Integer alarmid = Integer.parseInt(request.getParameter("alarmid"));
		String strAlarmId = request.getParameter("alarmid");
		String updateSql = String
				.format("update TF_AVMON_ALARM_DATA set status = '1' where alarm_id = '%s'",
						strAlarmId);

		jdbc.update(updateSql);
		// ("update TD_AVMON_MO_INFO_ATTRIBUTE set value='%s' where name = 'ip' and  mo_id='%s' ",
		// ip, moId);
		//
		// String updateSql =
		// "update TF_AVMON_ALARM_DATA set status = '1' where alarm_id = '" + +
		// "'";
	}

	// add by Jay
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getAllAlertGrade(HttpServletRequest request) throws Exception {
		String typeId=request.getParameter("typeId");
		String moId=request.getParameter("moId");
		String whereString="where 1=1 ";
		if(typeId!=null){
			if((!typeId.equals(""))&&(!typeId.equals("ALL"))){
				if(typeId.contains("_")){
					whereString+=" and tt.type_id='"+typeId+"' ";
				}else{
					whereString+=" and  tt.type_id like '"+typeId+"_%' ";
				}
				 
			}	
		}
		
		if(moId!=null){
			if((!moId.equals(""))){	
					whereString=" where t1.mo_id='"+moId+"' ";

			}	
		}
		
		String sql = " select count(*) as \"count\",current_grade as \"grade\" from tf_avmon_alarm_data t1 "
				+ "inner join TD_AVMON_MO_INFO tt on (t1.MO_ID = tt.MO_ID) "
				+ "left join portal_users tu on (t1.CONFIRM_USER = tu.USER_ID or t1.CONFIRM_USER = tu.REAL_NAME) "
				+ "left join TD_AVMON_MO_INFO t2 on (t1.MO_ID = t2.MO_ID)  "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t3 on (t1.MO_ID = t3.MO_ID and t3.NAME='ip')   "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t4 on (t1.MO_ID = t4.MO_ID and t4.NAME='businessSystem') "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t5 on (t1.MO_ID = t5.MO_ID and t5.NAME='position')   "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t6 on (t1.MO_ID = t6.MO_ID and t6.NAME='usage') "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t7 on (t1.MO_ID = t7.MO_ID and t7.NAME='hostName') "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t8 on (t1.MO_ID = t8.MO_ID and t8.NAME='deviceSN') "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t9 on (t1.MO_ID = t9.MO_ID and t9.NAME='assetNo') "
				+ "left join TD_AVMON_KPI_INFO t10 on (t1.KPI_CODE=t10.KPI_CODE) "
				+whereString
				+ " group by current_grade order by current_grade";
		List<Map<String, String>> list = jdbc.queryForList(sql);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gradeData", list);
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getHandlerAlarmLevelData(String typeId, String period,String s_start_date,String s_end_date)
			throws Exception {
		String wheresqlString = "";
		String defaultString = "";
		if (!"ALL".equals(typeId) && typeId != null) {
			wheresqlString = "where t2.type_id like '" + typeId + "_%'";
			if (period == null||period.equals("")) {
				period = "4";
			}
			if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
				defaultString=wheresqlString;
			}
			else{
				defaultString=wheresqlString+" and trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
						"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			}
			switch (Integer.parseInt(period)) {
			case 1:// today
				wheresqlString =wheresqlString+ "and trunc(sysdate)=trunc(t1.first_occur_time)";
				break;
			case 2:// one week
				wheresqlString = wheresqlString+" and trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 3:// two week
				wheresqlString = wheresqlString+"and trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 4:// one month
				wheresqlString =wheresqlString+"and t1.first_occur_time>add_months(sysdate,-1)";
				break;
			default:
				//"+DBUtils.getDBToDateFunction()+"('2004-05-07 13:23:44','yyyy-mm-dd hh24:mi:ss')
				wheresqlString =defaultString;
				break;
			}
		}
		else{
			if (period == null||period.equals("")) {
				period = "4";
			}
			if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
				defaultString=wheresqlString;
			}
			else{
				defaultString=wheresqlString+" where trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
						"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			}
			switch (Integer.parseInt(period)) {
			case 1:// today
				wheresqlString ="where trunc(sysdate)=trunc(t1.first_occur_time)";
				break;
			case 2:// one week
				wheresqlString = "where trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 3:// two week
				wheresqlString = "where trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 4:// one month
				wheresqlString ="where t1.first_occur_time>add_months(sysdate,-1)";
				break;
			default:
				//wheresqlString ="where t1.first_occur_time>add_months(sysdate,-1)";
				wheresqlString =defaultString;
	
				break;
			}
		}



		String sql = "select count(*) as  \"handlercount\", current_grade as \"grade\" from "
				+ "tf_avmon_alarm_history t1 inner join TD_AVMON_MO_INFO tt on (t1.MO_ID = tt.MO_ID) "
				+ "left join portal_users tu on (t1.CONFIRM_USER = tu.USER_ID or t1.CONFIRM_USER = tu.REAL_NAME) "
				+ "left join TD_AVMON_MO_INFO t2 on (t1.MO_ID = t2.MO_ID)  "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t3 on (t1.MO_ID = t3.MO_ID and t3.NAME='ip')   "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t4 on (t1.MO_ID = t4.MO_ID and t4.NAME='businessSystem')   "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t5 on (t1.MO_ID = t5.MO_ID and t5.NAME='position')   "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t6 on (t1.MO_ID = t6.MO_ID and t6.NAME='usage') "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t7 on (t1.MO_ID = t7.MO_ID and t7.NAME='hostName') "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t8 on (t1.MO_ID = t8.MO_ID and t8.NAME='deviceSN') "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t9 on (t1.MO_ID = t9.MO_ID and t9.NAME='assetNo') "
				+ "left join TD_AVMON_KPI_INFO t10 on (t1.KPI_CODE=t10.KPI_CODE) "
				+ wheresqlString
				+ " "
				+ "group by current_grade order by current_grade";
		List<Map<String, Object>> list = jdbc.queryForList(sql);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get("grade").equals(BigDecimal.valueOf(1))) {
				list.get(i).put("grade", "一级告警");
			} else if (list.get(i).get("grade").equals(BigDecimal.valueOf(2))) {
				list.get(i).put("grade", "二级告警");
			} else if (list.get(i).get("grade").equals(BigDecimal.valueOf(3))) {
				list.get(i).put("grade", "三级告警");
			} else if (list.get(i).get("grade").equals(BigDecimal.valueOf(4))) {
				list.get(i).put("grade", "四级告警");
			} else if (list.get(i).get("grade").equals(BigDecimal.valueOf(5))) {
				list.get(i).put("grade", "五级告警");
			}
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getTodayAndYesterdayAlarmData() throws Exception {
		String todaySql = "select count(*) as \"count\" from tf_avmon_alarm_data "
				+ "where trunc(sysdate)=trunc(first_occur_time)";
		String yesterdaySql = "select count(*) as \"count\" from tf_avmon_alarm_data "
				+ "where trunc(sysdate-1)=trunc(first_occur_time)";
		List<Map<String, Object>> todayList = jdbc.queryForList(todaySql);
		List<Map<String, Object>> yesterdayList = jdbc
				.queryForList(yesterdaySql);
		todayList.get(0).put("day", "今天");
		yesterdayList.get(0).put("day", "昨天");
		BigDecimal todayListCount = (BigDecimal) (todayList.get(0).get("count"));
		BigDecimal yesterdayListCount = (BigDecimal) (yesterdayList.get(0)
				.get("count"));
		BigDecimal total = todayListCount.add(yesterdayListCount);
		todayList.get(0).put(
				"percent",
				todayListCount.divide(total, 2, BigDecimal.ROUND_DOWN)
						.multiply(new BigDecimal(100)));
		yesterdayList.get(0).put(
				"percent",
				yesterdayListCount.divide(total, 2, BigDecimal.ROUND_UP)
						.multiply(new BigDecimal(100)));
		todayList.addAll(yesterdayList);
		return todayList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getAlarmDataByTypeId(String typeId,String period,String s_start_date,String s_end_date,String bizName) throws Exception {
		JdbcTemplate jdbc = SpringContextHolder.getBean("jdbcTemplate");
		String wheresqlString = "";
		String whereperiodString = "";
		String defaultString="";
		String whereBizString="";
		
		if("ALL".equals(bizName)){
			whereBizString="";
		}
	    else{
		   try {
				bizName = URLDecoder.decode(bizName, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}// 字符编码和文档编码一致
		   if(!bizName.equals("others")){
			   whereBizString=" inner join (select value,mo_id from td_avmon_mo_info_attribute " +
				   		"where name='businessSystem' and value='"+bizName+"') d on t1.mo_id = d.mo_id "; 
		   }
		   else{
			   whereBizString="   inner join ( select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem'  and  attr.value not in " +
			   		"(select businessname as \"name\" from tf_avmon_biz_dictionary) union all  ( select distinct mo_id, 'Other' from td_avmon_mo_info_attribute attr where attr.mo_id not in (select mo_id from td_avmon_mo_info_attribute attr where name='businessSystem'))) t on t1.mo_id = t.mo_id  ";
		   }
		   
		}
		if(DBUtils.isOracle()){
		if (!"ALL".equals(typeId) && typeId != null) {
			wheresqlString = "where \"typeId\" like '" + typeId + "_%'";
			if (period == null||period.equals("")) {
				period = "4";
			}
			if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
				defaultString="";
			}
			else{
				defaultString=" where  trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
						"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			}
			switch (Integer.parseInt(period)) {
			case 1:// today
				whereperiodString = " where trunc(sysdate)=trunc(t1.first_occur_time)";
				break;
			case 2:// one week
				whereperiodString = " where trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 3:// two week
				whereperiodString = "where trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 4:// one month
				whereperiodString = "where t1.first_occur_time>add_months(sysdate,-1)";
				break;
			default:
				whereperiodString = defaultString;
	
				break;
			}
		}
		}
		//is pg db
		else{
					if (!"ALL".equals(typeId) && typeId != null) {
						wheresqlString = "where \"typeId\" like '" + typeId + "_%'";
						if (period == null||period.equals("")) {
							period = "4";
						}
						if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
							defaultString="";
						}
						else{
							defaultString=" where  "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
									"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
						}
						switch (Integer.parseInt(period)) {
						case 1:// today
							whereperiodString = " where "+DBUtils.getDBToDateFunction()+"(to_char(current_date,'yyyy/mm/dd'),'yyyy/mm/dd')="+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')";
							break;
						case 2:// one week
							whereperiodString = " where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-7 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
							break;
						case 3:// two week
							whereperiodString = "where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-14 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
							break;
						case 4:// one month
							whereperiodString = "where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>(current_date - interval '1 month')";
							break;
						default:
							whereperiodString = defaultString;
				
							break;
						}
					}		
		}
		String sql = "";
		if(DBUtils.isOracle()){
			sql = "select \"typeId\",count(*)as \"count\" from( "
					+ "select  tt.type_id as \"typeId\" from TF_AVMON_ALARM_DATA t1 "
					+ "inner join TD_AVMON_MO_INFO tt on (t1.MO_ID = tt.MO_ID) "
					+ "left join portal_users tu on (t1.CONFIRM_USER = tu.USER_ID or t1.CONFIRM_USER = tu.REAL_NAME) "
					+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t3 on (t1.MO_ID = t3.MO_ID and t3.NAME='ip')  "
					+ whereBizString
					+ "left join TD_AVMON_KPI_INFO t10 on (t1.KPI_CODE=t10.KPI_CODE)  "+whereperiodString+") "
					+ wheresqlString + "  group by \"typeId\""; 
		}else{
			sql = "select \"typeId\",count(*)as \"count\" from( "
					+ "select  tt.type_id as \"typeId\" from TF_AVMON_ALARM_DATA t1 "
					+ "inner join TD_AVMON_MO_INFO tt on (t1.MO_ID = tt.MO_ID) "
					+ "left join portal_users tu on (t1.CONFIRM_USER = tu.USER_ID or t1.CONFIRM_USER = tu.REAL_NAME) "
					+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t3 on (t1.MO_ID = t3.MO_ID and t3.NAME='ip')  "
					+ whereBizString
					+ "left join TD_AVMON_KPI_INFO t10 on (t1.KPI_CODE=t10.KPI_CODE)  "+whereperiodString+") as aa  "
					+ wheresqlString + "  group by \"typeId\""; 
		}
		 
		List<Map<String, Object>> list = jdbc.queryForList(sql);
		BigDecimal totalCount = new BigDecimal(0);
		for (Map<String, Object> m : list) {
			if(m.get("count") instanceof Long){
				totalCount = totalCount.add(new BigDecimal ((Long) m.get("count")));
			}else{
				totalCount = totalCount.add((BigDecimal) m.get("count"));
			}
			
		}
		if(totalCount.compareTo(new BigDecimal(0))==0){
			return new ArrayList();
		}
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				if(list.get(i).get("count") instanceof Long){
					list.get(i).put("percent",(new BigDecimal((Long) list.get(i).get("count"))).divide(
									totalCount, 3, BigDecimal.ROUND_UP).multiply(
									new BigDecimal(100)));
				}
				else{
				list.get(i).put(
						"percent",
						((BigDecimal) list.get(i).get("count")).divide(
								totalCount, 3, BigDecimal.ROUND_UP).multiply(
								new BigDecimal(100)));
				}
			} else {
				if(list.get(i).get("count") instanceof Long){
				list.get(i).put(
						"percent",
						(new BigDecimal((Long) list.get(i).get("count"))).divide(
								totalCount, 3, BigDecimal.ROUND_DOWN).multiply(
								new BigDecimal(100)));
				}else{
					list.get(i).put(
							"percent",
							((BigDecimal) list.get(i).get("count")).divide(
									totalCount, 3, BigDecimal.ROUND_DOWN).multiply(
									new BigDecimal(100)));
				}
			}

		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getAlarmDataByAllType(String period,String s_start_date, String s_end_date, String bizName) throws Exception {
		JdbcTemplate jdbc = SpringContextHolder.getBean("jdbcTemplate");
		String wheresqlString = "";
		String whereperiodString = "";
		String defaultString="";
		String whereBizString="";
	
		if("ALL".equals(bizName)){
			whereBizString="";
		}
	    else{
		   try {
				bizName = URLDecoder.decode(bizName, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}// 字符编码和文档编码一致
		   if(!bizName.equals("others")){
			   whereBizString=" inner join (select value,mo_id from td_avmon_mo_info_attribute " +
				   		"where name='businessSystem' and value='"+bizName+"') d on t1.mo_id = d.mo_id ";  
		   }
		   else{
			   whereBizString="   inner join ( select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem'  and  attr.value not in " +
				   		"(select businessname as \"name\" from tf_avmon_biz_dictionary) union all  ( select distinct mo_id, 'Other' from td_avmon_mo_info_attribute attr where attr.mo_id not in (select mo_id from td_avmon_mo_info_attribute attr where name='businessSystem'))) t on t1.mo_id = t.mo_id  ";
			 }
		  
		}
		if(DBUtils.isOracle()){
		if (period == null||period.equals("")) {
			period = "4";
		}
		if(("".equals(s_start_date)||s_start_date==null)&&("".equals(s_end_date)||s_end_date==null)){
			defaultString=wheresqlString;
		}
		else{
			defaultString=" where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
					"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
		}
		switch (Integer.parseInt(period)) {
		case 1:// today
			whereperiodString ="where trunc(sysdate)=trunc(t1.first_occur_time)";
			break;
		case 2:// one week
			whereperiodString = "where trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
			break;
		case 3:// two week
			whereperiodString = "where trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
			break;
		case 4:// two week
			whereperiodString ="where t1.first_occur_time>add_months(sysdate,-1)";
			break;
		
		default:
			whereperiodString =defaultString;

			break;
		}}
		else{
			if (period == null||period.equals("")) {
				period = "4";
			}
			if(("".equals(s_start_date)||s_start_date==null)&&("".equals(s_end_date)||s_end_date==null)){
				defaultString=wheresqlString;
			}
			else{
				defaultString=" where trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
						"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			}
			switch (Integer.parseInt(period)) {
			case 1:// today
			whereperiodString ="where "+DBUtils.getDBToDateFunction()+"(to_char(current_date,'yyyy/mm/dd'),'yyyy/mm/dd')="+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')";
			break;
		   case 2:// one week
			whereperiodString = "where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-7 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
			break;
		   case 3:// two week
			whereperiodString = "where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-14 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
			break;
		   case 4:// two week
			whereperiodString ="where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>(current_date - interval '1 month')";
			break;
			default:
				whereperiodString =defaultString;

				break;
			}
		}
		String sql ="";
		if(DBUtils.isOracle()){
		 sql = "select \"typeId\",count(*)as \"count\" from( "
				+ "select  tt.type_id as \"typeId\" from TF_AVMON_ALARM_DATA t1 "
				+ "inner join TD_AVMON_MO_INFO tt on (t1.MO_ID = tt.MO_ID) "
				+ "left join portal_users tu on (t1.CONFIRM_USER = tu.USER_ID or t1.CONFIRM_USER = tu.REAL_NAME) "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t3 on (t1.MO_ID = t3.MO_ID and t3.NAME='ip')  "
				+ whereBizString
				+ "left join TD_AVMON_KPI_INFO t10 on (t1.KPI_CODE=t10.KPI_CODE)  "+whereperiodString+") "
				+ wheresqlString + " group by \"typeId\"";
		}else {
			 sql = "select \"typeId\",count(*)as \"count\" from( "
						+ "select  tt.type_id as \"typeId\" from TF_AVMON_ALARM_DATA t1 "
						+ "inner join TD_AVMON_MO_INFO tt on (t1.MO_ID = tt.MO_ID) "
						+ "left join portal_users tu on (t1.CONFIRM_USER = tu.USER_ID or t1.CONFIRM_USER = tu.REAL_NAME) "
						+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t3 on (t1.MO_ID = t3.MO_ID and t3.NAME='ip')  "
						+ whereBizString
						+ "left join TD_AVMON_KPI_INFO t10 on (t1.KPI_CODE=t10.KPI_CODE)  "+whereperiodString+") "
						+ wheresqlString + " as aa group by \"typeId\"";
		}
		List<Map<String, Object>> list = jdbc.queryForList(sql);
		List<Map<String, Object>> newlist = new ArrayList();
		BigDecimal hostTotalCount = new BigDecimal(0);
		BigDecimal vmTotalCount = new BigDecimal(0);
		BigDecimal dbTotalCount = new BigDecimal(0);
		BigDecimal nwTotalCount = new BigDecimal(0);
		BigDecimal sTotalCount = new BigDecimal(0);
		BigDecimal hwTotalCount = new BigDecimal(0);

		for (int j = 0; j < list.size(); j++) {
			if(list.get(j).get("typeId")!=null){
				
			
			if (list.get(j).get("typeId").toString().startsWith("VE")) {
				if(list.get(j).get("count") instanceof Long){
					vmTotalCount = vmTotalCount.add(new BigDecimal((Long)list.get(j).get("count")));
				}
				else{
					vmTotalCount = vmTotalCount.add((BigDecimal) list.get(j).get(
							"count"));
				}
			} else if (list.get(j).get("typeId").toString().startsWith("HOST")) {
				if(list.get(j).get("count") instanceof Long){
					hostTotalCount = hostTotalCount.add(new BigDecimal((Long)list.get(j).get("count")));
				}
				else{
					hostTotalCount = hostTotalCount.add((BigDecimal) list.get(j).get("count"));
				}
				
			} else if (list.get(j).get("typeId").toString()
					.startsWith("DATABASE")) {
				if(list.get(j).get("count") instanceof Long){
					dbTotalCount = dbTotalCount.add(new BigDecimal((Long)list.get(j).get("count")));
				}
				else{
					dbTotalCount = dbTotalCount.add((BigDecimal) list.get(j).get(
							"count"));
				}
			
			} else if (list.get(j).get("typeId").toString()
					.startsWith("NETWORK")) {
				if(list.get(j).get("count") instanceof Long){
					nwTotalCount = nwTotalCount.add(new BigDecimal((Long)list.get(j).get("count")));
				}
				else{
					nwTotalCount = nwTotalCount.add((BigDecimal) list.get(j).get(
							"count"));
				}
				
			} else if (list.get(j).get("typeId").toString()
					.startsWith("STORAGE")) {
				if(list.get(j).get("count") instanceof Long){
					sTotalCount = sTotalCount.add(new BigDecimal((Long)list.get(j).get("count")));
				}
				else{
					sTotalCount = sTotalCount.add((BigDecimal) list.get(j).get(
							"count"));
				}
			
			} else if (list.get(j).get("typeId").toString()
					.startsWith("HARDWARE")) {
				if(list.get(j).get("count") instanceof Long){
					hwTotalCount = hwTotalCount.add(new BigDecimal((Long)list.get(j).get("count")));
				}
				else{
					hwTotalCount = hwTotalCount.add((BigDecimal) list.get(j).get(
							"count"));	
				}
			}
			}
		}
		Map<String, Object> hostMap = new HashMap<String, Object>();
		hostMap.put("typeId", "HOST");
		hostMap.put("count", hostTotalCount);
		newlist.add(hostMap);
		Map<String, Object> vmMap = new HashMap<String, Object>();
		vmMap.put("typeId", "VE");
		vmMap.put("count", vmTotalCount);
		newlist.add(vmMap);
		Map<String, Object> dbMap = new HashMap<String, Object>();
		dbMap.put("typeId", "DATABASE");
		dbMap.put("count", dbTotalCount);
		newlist.add(dbMap);
		Map<String, Object> nwMap = new HashMap<String, Object>();
		nwMap.put("typeId", "NETWORK");
		nwMap.put("count", dbTotalCount);
		newlist.add(nwMap);
		Map<String, Object> sMap = new HashMap<String, Object>();
		sMap.put("typeId", "STORAGE");
		sMap.put("count", dbTotalCount);
		newlist.add(sMap);
		Map<String, Object> hwMap = new HashMap<String, Object>();
		hwMap.put("typeId", "HARDWARE");
		hwMap.put("count", hwTotalCount);
		newlist.add(hwMap);

		BigDecimal totalCount = new BigDecimal(0);
		for (Map<String, Object> m : newlist) {
			totalCount = totalCount.add((BigDecimal) m.get("count"));
		}
		if(totalCount.compareTo(new BigDecimal(0))==0){
			return newlist;
		}
		for (int i = 0; i < newlist.size(); i++) {
			if (i == newlist.size() - 1) {
				newlist.get(i).put(
						"percent",
						((BigDecimal) newlist.get(i).get("count")).divide(
								totalCount, 3, BigDecimal.ROUND_UP).multiply(
								new BigDecimal(100)));
			} else {
				newlist.get(i).put(
						"percent",
						((BigDecimal) newlist.get(i).get("count")).divide(
								totalCount, 3, BigDecimal.ROUND_DOWN).multiply(
								new BigDecimal(100)));
			}

		}
		return newlist;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getTopTenAlarmData1(HttpServletRequest request) throws Exception {
		String typeId = request.getParameter("typeId");
		String period = request.getParameter("period");
		String s_start_date=request.getParameter("s_start_date");
		String s_end_date=request.getParameter("s_end_date");
		String wheresqlString = "";
		String whereperiodString="";
		String defaultString="";
		if(typeId==null){ 
			typeId="ALL";
		}
		if(period==null){
			period="5";
		}
		if (!"ALL".equals(typeId) && typeId != null){
			wheresqlString = "where a.type_id like '" + typeId + "_%'";
			if (period == null||period.equals("")) {
				period = "4";
			}
			if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
				defaultString=whereperiodString;
			}
			else{
				defaultString=" where trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
						"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			}
			switch (Integer.parseInt(period)) {
			case 1:// today
				whereperiodString =" where trunc(sysdate)=trunc(t1.first_occur_time)";
				break;
			case 2:// one week
				whereperiodString = " where trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 3:// two week
				whereperiodString = " where trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 4:// one month
				whereperiodString =" where t1.first_occur_time>add_months(sysdate,-1)";
				break;
			default:
				whereperiodString =defaultString;
				break;
			}
		}
		else{
			if (period == null||period.equals("")) {
				period = "4";
			}
			if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
				defaultString=whereperiodString;
			}
			else{
				defaultString=" where trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
						"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			}
			switch (Integer.parseInt(period)) {
			case 1:// today
				whereperiodString =" where trunc(sysdate)=trunc(t1.first_occur_time)";
				break;
			case 2:// one week
				whereperiodString = "where trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 3:// two week
				whereperiodString = "where trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 4:// two week
				whereperiodString =" where t1.first_occur_time>add_months(sysdate,-1)";
				break;
			default:
				whereperiodString=defaultString;
				break;
			}
		}
		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getTopTenAlarmData"),
				whereperiodString,wheresqlString);
		sql = sql + " order by total desc ) where rownum <= 5";
		List<Map<String, Object>> list = jdbc.queryForList(sql);
		BigDecimal totalCount = new BigDecimal(0);
		for (Map m : list) {
			BigDecimal count = (BigDecimal) m.get("total");
			totalCount = totalCount.add(count);
		}
		for (Map map : list) {
			BigDecimal countData = (BigDecimal) map.get("total");
			map.put("percent",
					countData.divide(totalCount, 3, BigDecimal.ROUND_DOWN)
							.setScale(3, BigDecimal.ROUND_DOWN)
							.multiply(new BigDecimal(100)).setScale(1));
		}
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getTopTenAlarmData(HttpServletRequest request) throws Exception {
		String typeId = request.getParameter("typeId");
		String period = request.getParameter("period");
		String s_start_date=request.getParameter("s_start_date");
		String s_end_date=request.getParameter("s_end_date");
		String wheresqlString = "";
		String whereperiodString="";
		String defaultString="";
		String whereBizString="";
		String bizName = request.getParameter("bizName");
		if("ALL".equals(bizName)){
			whereBizString="";
		}
	    else{
		   try {
				bizName = URLDecoder.decode(bizName, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}// 字符编码和文档编码一致
		   if(!bizName.equals("others")){
			   whereBizString=" inner join (select value,mo_id from td_avmon_mo_info_attribute " +
				   		"where name='businessSystem' and value='"+bizName+"') d on a.mo_id = d.mo_id";  
		   }
		   else{
			   whereBizString=" and a.mo_id in(select attr.mo_id from td_avmon_mo_info_attribute attr " +
			   		"where name='businessSystem' and attr.value not in (select businessname as \"name\" from " +
			   				"tf_avmon_biz_dictionary) union all ( select distinct mo_id from td_avmon_mo_info_attribute attr " +
			   				" where attr.mo_id not in (select mo_id from td_avmon_mo_info_attribute attr  where name='businessSystem'))) ";
		   }
		 
		}
		
		/*try {
			bizName = URLDecoder.decode(bizName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}// 字符编码和文档编码一致
*/		//whereBizString=" attr.value ='"+bizName+"' ";
		if(typeId==null){ 
			typeId="ALL";
		}
		if(period==null){
			period="5";
		}
		if(DBUtils.isOracle()){
		if (!"ALL".equals(typeId) && typeId != null){
			wheresqlString = "where a.type_id like '" + typeId + "_%'";
			if (period == null||period.equals("")) {
				period = "4";
			}
			if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
				defaultString=whereperiodString;
			}
			else{
				defaultString=" where trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
						"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			}
			switch (Integer.parseInt(period)) {
			case 1:// today
				whereperiodString =" where trunc(sysdate)=trunc(t1.first_occur_time)";
				break;
			case 2:// one week
				whereperiodString = " where trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 3:// two week
				whereperiodString = " where trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 4:// one month
				whereperiodString =" where t1.first_occur_time>add_months(sysdate,-1)";
				break;
			default:
				whereperiodString =defaultString;
				break;
			}
		}
		else{
			if (period == null||period.equals("")) {
				period = "4";
			}
			if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
				defaultString=whereperiodString;
			}
			else{
				defaultString=" where trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
						"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			}
			switch (Integer.parseInt(period)) {
			case 1:// today
				whereperiodString =" where trunc(sysdate)=trunc(t1.first_occur_time)";
				break;
			case 2:// one week
				whereperiodString = "where trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 3:// two week
				whereperiodString = "where trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 4:// two week
				whereperiodString =" where t1.first_occur_time>add_months(sysdate,-1)";
				break;
			default:
				whereperiodString=defaultString;
				break;
			}
		}}
		//is pg db
				else{
					if (!"ALL".equals(typeId) && typeId != null){
						wheresqlString = "where a.type_id like '" + typeId + "_%'";
						if (period == null||period.equals("")) {
							period = "4";
						}
						if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
							defaultString=whereperiodString;
						}
						else{
							defaultString=" where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
									"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
						}
						switch (Integer.parseInt(period)) {
						case 1:// today
							whereperiodString =" where "+DBUtils.getDBToDateFunction()+"(to_char(current_date,'yyyy/mm/dd'),'yyyy/mm/dd')="+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')";
							break;
						case 2:// one week
							whereperiodString = " where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-7 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
							break;
						case 3:// two week
							whereperiodString = " where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-14 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
							break;
						case 4:// one month
							whereperiodString =" where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>(current_date - interval '1 month')";
							break;
						default:
							whereperiodString =defaultString;
							break;
						}
					}
					else{
						if (period == null||period.equals("")) {
							period = "4";
						}
						if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
							defaultString=whereperiodString;
						}
						else{
							defaultString=" where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
									"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
						}
						switch (Integer.parseInt(period)) {
						case 1:// today //"+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')
							whereperiodString =" where "+DBUtils.getDBToDateFunction()+"(to_char(current_date,'yyyy/mm/dd'),'yyyy/mm/dd')="+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')";
							break;
						case 2:// one week
							whereperiodString = "where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-7 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
							break;
						case 3:// two week
							whereperiodString = "where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-14 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
							break;
						case 4:// two week
							whereperiodString =" where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>(current_date - interval '1 month')";
							break;
						default:
							whereperiodString=defaultString;
							break;
						}
					}
		   }
		String sql = String.format(
				SqlManager.getSql(AlarmService.class, "getTopTenAlarmData"),
				whereperiodString,whereBizString,wheresqlString);
		
		if(DBUtils.isOracle()){
			sql = sql + " order by total desc ) where rownum <= 5";
		}
		else{
			sql = sql + " order by total desc ) as aa  limit 5";
		}
		
		List<Map<String, Object>> list = jdbc.queryForList(sql);
		for(int i=0;i<list.size();i++){
			if(list.get(i).get("VALUE")==null){
				list.get(i).put("VALUE",list.get(i).get("hostname"));
			}
		}
		return list;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getTopTenKpiAlarmData(String typeId,String period,String s_start_date,String s_end_date,String bizName) throws Exception {
		String wheresqlString = "";
		String defaultString="";
		String whereBizString="";
		String whereDTable="";

		if("ALL".equals(bizName)){
			whereBizString="";
		}
	    else{
		   try {
				bizName = URLDecoder.decode(bizName, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}// 字符编码和文档编码一致
		   if(!bizName.equals("others")){
			   whereBizString=" and d.MO_ID=a.mo_id and d.name='businessSystem' and d.value='"+bizName+"'";  
			   whereDTable=" ,td_avmon_mo_info_attribute d ";
		   }
		   else{
			   whereBizString=" and a.mo_id in(select attr.mo_id from td_avmon_mo_info_attribute attr " +
			   		"where name='businessSystem' and attr.value not in (select businessname as \"name\" from " +
			   				"tf_avmon_biz_dictionary) union all ( select distinct mo_id from td_avmon_mo_info_attribute attr " +
			   				" where attr.mo_id not in (select mo_id from td_avmon_mo_info_attribute attr  where name='businessSystem'))) ";
		   }
		  
		}
		if(DBUtils.isOracle()){
		if (!"ALL".equals(typeId) && typeId != null){
			wheresqlString = " and c.type_id  like '" + typeId + "_%'";
			if (period == null||period.equals("")) {
				period = "4";
			}
			if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
				defaultString=wheresqlString;
			}
			else{
				defaultString=wheresqlString+" and trunc(a.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
						"and trunc(a.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			}
			switch (Integer.parseInt(period)) {
			case 1:// today
				wheresqlString =wheresqlString+ " and trunc(sysdate)=trunc(a.first_occur_time)";
				break;
			case 2:// one week
				wheresqlString = wheresqlString+" and trunc(a.first_occur_time)>=sysdate-7 and trunc(a.first_occur_time)<=sysdate";
				break;
			case 3:// two week
				wheresqlString = wheresqlString+" and trunc(a.first_occur_time)>=sysdate-14 and trunc(a.first_occur_time)<=sysdate";
				break;
			case 4:// one month
				wheresqlString =wheresqlString+" and a.first_occur_time>add_months(sysdate,-1)";
				break;
			default:
				wheresqlString =defaultString;
				break;
			}
		}
		else{
			if (period == null||period.equals("")) {
				period = "4";
			}
			if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
					defaultString=wheresqlString;
			}
			else{
				defaultString=wheresqlString+" and trunc(a.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
						"and trunc(a.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			}
			switch (Integer.parseInt(period)) {
			case 1:// today
				wheresqlString =" and trunc(sysdate)=trunc(a.first_occur_time)";
				break;
			case 2:// one week
				wheresqlString = " and trunc(a.first_occur_time)>=sysdate-7 and trunc(a.first_occur_time)<=sysdate";
				break;
			case 3:// two week
				wheresqlString = " and trunc(a.first_occur_time)>=sysdate-14 and trunc(a.first_occur_time)<=sysdate";
				break;
			case 4:// two week
				wheresqlString =" and a.first_occur_time>add_months(sysdate,-1)";
				break;
			default:
				wheresqlString =defaultString;
				break;
			}
		}
		}
		//is pg db
				else{
					if (!"ALL".equals(typeId) && typeId != null) {
						wheresqlString = "and c.type_id like '" + typeId + "_%'";
						if (period == null||period.equals("")) {
							period = "4";
						}
						if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
							defaultString=wheresqlString;
						}
						else{
							/*defaultString=wheresqlString+" and trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
									"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";*/
							defaultString=wheresqlString+" and "+DBUtils.getDBToDateFunction()+"(to_char(a.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
									"and "+DBUtils.getDBToDateFunction()+"(to_char(a.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
							
						}
						
						switch (Integer.parseInt(period)) {
						case 1:// today
							//to_char(a.first_occur_time,'YYYYMMDD')=to_char(current_date,'YYYYMMDD')
							wheresqlString =wheresqlString+ "and "+DBUtils.getDBToDateFunction()+"(to_char(current_date,'yyyy/mm/dd'),'yyyy/mm/dd')="+DBUtils.getDBToDateFunction()+"(to_char(a.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')";
							break;
						case 2:// one week
							wheresqlString = wheresqlString+"and "+DBUtils.getDBToDateFunction()+"(to_char(a.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-7 and "+DBUtils.getDBToDateFunction()+"(to_char(a.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
							break;
						case 3:// two week
							wheresqlString = wheresqlString+"and "+DBUtils.getDBToDateFunction()+"(to_char(a.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-14 and "+DBUtils.getDBToDateFunction()+"(to_char(current_date,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
							break;
						case 4:
							// one month
							wheresqlString = wheresqlString+"and "+DBUtils.getDBToDateFunction()+"(to_char(a.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>(current_date - interval '1 month')";
							break;	
						default:
							wheresqlString =defaultString;
							break;
						}
					}
					else{
						if (period == null||period.equals("")) {
							period = "4";
						}
						if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
							defaultString=wheresqlString;
						}
						else{
							defaultString=wheresqlString+" and "+DBUtils.getDBToDateFunction()+"(to_char(a.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
									" and "+DBUtils.getDBToDateFunction()+"(to_char(a.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
						}
						switch (Integer.parseInt(period)) {
						case 1:// today
							wheresqlString ="where "+DBUtils.getDBToDateFunction()+"(to_char(a.current_date,'yyyy/mm/dd'),'yyyy/mm/dd')="+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')";
							break;
						case 2:// one week
							wheresqlString = "where "+DBUtils.getDBToDateFunction()+"(to_char(a.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=sysdate-7 and "+DBUtils.getDBToDateFunction()+"(to_char(a.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
							break;
						case 3:// two week
							wheresqlString = "where "+DBUtils.getDBToDateFunction()+"(to_char(a.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=sysdate-14 and "+DBUtils.getDBToDateFunction()+"(to_char(a.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
							break;
						case 4:
							// one month
							wheresqlString ="where "+DBUtils.getDBToDateFunction()+"(to_char(a.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>(current_date - interval '1 month')";
							break;	
						default:
							wheresqlString =defaultString;
							
							break;
						}
					}
		}
		/*if (typeId != null) {
			wheresqlString = "and c.type_id  like '" + typeId + "_%'";
		}*/
		//
		String sql ="";
		if(!bizName.equals("others")){
			 sql = " select * from (select count(*) as \"total\", "
					+ " b.kpi_name as \"kpiName\",c.type_id as \"typeId\" "
					+ " from tf_avmon_alarm_data a ,td_avmon_kpi_info b,TD_AVMON_MO_INFO c  " + whereDTable
					+ " where a.kpi_code is not null and a.kpi_code=b.kpi_code and a.MO_ID = c.MO_ID  " 
					+ whereBizString + wheresqlString+" ";
		}
		else{
			 sql = " select * from (select count(*) as \"total\", "
						+ " b.kpi_name as \"kpiName\",c.type_id as \"typeId\" "
						+ " from tf_avmon_alarm_data a ,td_avmon_kpi_info b,TD_AVMON_MO_INFO c  " 
						+ " where a.kpi_code is not null and a.kpi_code=b.kpi_code and a.MO_ID = c.MO_ID  " 
						+ whereBizString + wheresqlString+" ";
		}
		
		if(DBUtils.isOracle()){
			sql = sql + " group by c.type_id,b.kpi_name order by \"total\" desc) where rownum <=5";
		}else{
			sql = sql + " group by c.type_id,b.kpi_name order by \"total\" desc) as aa limit 5";
		}	
		List<Map<String, String>> list = jdbc.queryForList(sql);
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getNoHandlerAlarmLevelData(String typeId,String period,String s_start_date,String s_end_date,String bizName) throws Exception {
		String wheresqlString = "";
		String defaultString="";
		String whereBizString="";
		if("ALL".equals(bizName)){
			whereBizString="";
		}
	   else{
		   try {
				bizName = URLDecoder.decode(bizName, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}// 字符编码和文档编码一致
		   
		   if(!bizName.equals("others")){
			   whereBizString=" inner join (select value,mo_id from td_avmon_mo_info_attribute " +
				   		"where name='businessSystem' and value='"+bizName+"') d on t1.mo_id = d.mo_id "; 
		   }
		   else{
			   whereBizString="   inner join ( select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem'  and  attr.value not in " +
				   		"(select businessname as \"name\" from tf_avmon_biz_dictionary) union all  ( select distinct mo_id, 'Other' from td_avmon_mo_info_attribute attr where attr.mo_id not in (select mo_id from td_avmon_mo_info_attribute attr where name='businessSystem'))) t on t1.mo_id = t.mo_id  ";
		   }
		   
		}
		if(DBUtils.isOracle()){
		if (!"ALL".equals(typeId) && typeId != null) {
			wheresqlString = "where t2.type_id like '" + typeId + "_%'";
			if (period == null||period.equals("")) {
				period = "4";
			}
			if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
				defaultString=wheresqlString;
			}
			else{
				defaultString=wheresqlString+" and trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
						"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			}
			
			switch (Integer.parseInt(period)) {
			case 1:// today
				wheresqlString =wheresqlString+ "and trunc(sysdate)=trunc(t1.first_occur_time)";
				break;
			case 2:// one week
				wheresqlString = wheresqlString+"and trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 3:// two week
				wheresqlString = wheresqlString+"and trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 4:
				// one month
				wheresqlString = wheresqlString+"and t1.first_occur_time>add_months(sysdate,-1)";
				break;	
			default:
				wheresqlString =defaultString;
				break;
			}
		}
		else{
			if (period == null||period.equals("")) {
				period = "4";
			}
			if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
				defaultString=wheresqlString;
			}
			else{
				defaultString=wheresqlString+" where trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
						"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
			}
			switch (Integer.parseInt(period)) {
			case 1:// today
				wheresqlString ="where trunc(sysdate)=trunc(t1.first_occur_time)";
				break;
			case 2:// one week
				wheresqlString = "where trunc(t1.first_occur_time)>=sysdate-7 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 3:// two week
				wheresqlString = "where trunc(t1.first_occur_time)>=sysdate-14 and trunc(t1.first_occur_time)<=sysdate";
				break;
			case 4:
				// one month
				wheresqlString ="where t1.first_occur_time>add_months(sysdate,-1)";
				break;	
			default:
				wheresqlString =defaultString;
				
				break;
			}
		}
		}
		//is pg db
		else{
			if (!"ALL".equals(typeId) && typeId != null) {
				wheresqlString = "where t2.type_id like '" + typeId + "_%'";
				if (period == null||period.equals("")) {
					period = "4";
				}
				if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
					defaultString=wheresqlString;
				}
				else{
					/*defaultString=wheresqlString+" and trunc(t1.first_occur_time)>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
							"and trunc(t1.first_occur_time)<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";*/
					defaultString=wheresqlString+" and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
							"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
					
				}
				
				switch (Integer.parseInt(period)) {
				case 1:// today
					//to_char(a.first_occur_time,'YYYYMMDD')=to_char(current_date,'YYYYMMDD')
					wheresqlString =wheresqlString+ "and "+DBUtils.getDBToDateFunction()+"(to_char(current_date,'yyyy/mm/dd'),'yyyy/mm/dd')="+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')";
					break;
				case 2:// one week
					wheresqlString = wheresqlString+"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-7 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
					break;
				case 3:// two week
					wheresqlString = wheresqlString+"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=current_date-14 and "+DBUtils.getDBToDateFunction()+"(to_char(current_date,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
					break;
				case 4:
					// one month
					wheresqlString = wheresqlString+"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>(current_date - interval '1 month')";
					break;	
				default:
					wheresqlString =defaultString;
					break;
				}
			}
			else{
				if (period == null||period.equals("")) {
					period = "4";
				}
				if((s_start_date==null||"".equals(s_start_date))&&(s_end_date==null||"".equals(s_end_date))){
					defaultString=wheresqlString;
				}
				else{
					defaultString=wheresqlString+" where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>="+DBUtils.getDBToDateFunction()+"("+"'"+s_start_date+"'"+",'yyyy/mm/dd') " +
							"and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<="+DBUtils.getDBToDateFunction()+"("+"'"+s_end_date+"'"+",'yyyy/mm/dd')";
				}
				switch (Integer.parseInt(period)) {
				case 1:// today
					wheresqlString ="where "+DBUtils.getDBToDateFunction()+"(to_char(t1.current_date,'yyyy/mm/dd'),'yyyy/mm/dd')="+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')";
					break;
				case 2:// one week
					wheresqlString = "where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=sysdate-7 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
					break;
				case 3:// two week
					wheresqlString = "where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>=sysdate-14 and "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')<=current_date";
					break;
				case 4:
					// one month
					wheresqlString ="where "+DBUtils.getDBToDateFunction()+"(to_char(t1.first_occur_time,'yyyy/mm/dd'),'yyyy/mm/dd')>(current_date - interval '1 month')";
					break;	
				default:
					wheresqlString =defaultString;
					
					break;
				}
			}
		}
		String sql = "select count(*) as \"nohandlercount\" , current_grade as \"grade\" from tf_avmon_alarm_data t1 "
				+ "inner join TD_AVMON_MO_INFO tt on (t1.MO_ID = tt.MO_ID) "
				+ "left join portal_users tu on (t1.CONFIRM_USER = tu.USER_ID or t1.CONFIRM_USER = tu.REAL_NAME) "
				+ "left join TD_AVMON_MO_INFO t2 on (t1.MO_ID = t2.MO_ID)  "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t3 on (t1.MO_ID = t3.MO_ID and t3.NAME='ip')   "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t4 on (t1.MO_ID = t4.MO_ID and t4.NAME='businessSystem') "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t5 on (t1.MO_ID = t5.MO_ID and t5.NAME='position') "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t6 on (t1.MO_ID = t6.MO_ID and t6.NAME='usage') "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t7 on (t1.MO_ID = t7.MO_ID and t7.NAME='hostName') "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t8 on (t1.MO_ID = t8.MO_ID and t8.NAME='deviceSN') "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t9 on (t1.MO_ID = t9.MO_ID and t9.NAME='assetNo') "
				+whereBizString
				+ "left join TD_AVMON_KPI_INFO t10 on (t1.KPI_CODE=t10.KPI_CODE) "
				+ wheresqlString
				+ " "
				+ "group by current_grade order by current_grade";
		List<Map<String, Object>> list = jdbc.queryForList(sql);

		int level1count=0;
		int level2count=0;
		int level3count=0;
		int level4count=0;
		int level5count=0;
		for (int i = 0; i < list.size(); i++) {
			if(DBUtils.isOracle()){
				if (list.get(i).get("grade").equals(BigDecimal.valueOf(1))) {
					level1count++;
					list.get(i).put("grade", "一级告警");
				} else if (list.get(i).get("grade").equals(BigDecimal.valueOf(2))) {
					level2count++;
					list.get(i).put("grade", "二级告警"); 
				} else if (list.get(i).get("grade").equals(BigDecimal.valueOf(3))) {
					level3count++;
					list.get(i).put("grade", "三级告警");
				} else if (list.get(i).get("grade").equals(BigDecimal.valueOf(4))) {
					level4count++;
					list.get(i).put("grade", "四级告警");
				} else if (list.get(i).get("grade").equals(BigDecimal.valueOf(5))) {
					level5count++;
					list.get(i).put("grade", "五级告警");
				}
			}
			else{
				if (list.get(i).get("grade").equals(Integer.valueOf(1))) {
					level1count++;
					list.get(i).put("grade", "一级告警");
				} else if (list.get(i).get("grade").equals(Integer.valueOf(2))) {
					level2count++;
					list.get(i).put("grade", "二级告警"); 
				} else if (list.get(i).get("grade").equals(Integer.valueOf(3))) {
					level3count++;
					list.get(i).put("grade", "三级告警");
				} else if (list.get(i).get("grade").equals(Integer.valueOf(4))) {
					level4count++;
					list.get(i).put("grade", "四级告警");
				} else if (list.get(i).get("grade").equals(Integer.valueOf(5))) {
					level5count++;
					list.get(i).put("grade", "五级告警");
				}
			}
			
		}
	    if(level1count==0){
	    	Map map=new HashMap();
	    	map.put("grade", "一级告警");
	    	map.put("nohandlercount",new BigDecimal(0));
	    	list.add(0,map);
		}
	    if(level2count==0){
	    	Map map=new HashMap();
	    	map.put("grade", "二级告警");
	    	map.put("nohandlercount",new BigDecimal(0));
	    	list.add(1,map);
	    }
	     if(level3count==0){
	    	Map map=new HashMap();
	    	map.put("grade", "三级告警");
	    	map.put("nohandlercount",new BigDecimal(0));
	    	list.add(2,map);
	    }
	     if(level4count==0){
	    	Map map=new HashMap();
	    	map.put("grade", "四级告警");
	    	map.put("nohandlercount",new BigDecimal(0));
	    	list.add(3,map);
	    }
	     if(level5count==0){
	    	Map map=new HashMap();
	    	map.put("grade", "五级告警");
	    	map.put("nohandlercount",new BigDecimal(0));
	    	list.add(4,map);
	    }
		BigDecimal totalCount = new BigDecimal(0);
		BigDecimal count;
		for (Map m : list) {
			if(m.get("nohandlercount") instanceof Long){
				 count = new BigDecimal((Long)m.get("nohandlercount"));
			}
			else{
				 count = (BigDecimal) m.get("nohandlercount");
			}
			
			totalCount = totalCount.add(count);
		}
	   /*	for (Map map : list) {
			BigDecimal countData = (BigDecimal) map.get("nohandlercount");
			map.put("percent",
					countData.divide(totalCount, 3, BigDecimal.ROUND_DOWN)
							.setScale(3, BigDecimal.ROUND_DOWN)
							.multiply(new BigDecimal(100)).setScale(1));
		}*/
		BigDecimal countData;
		for (int i=0;i<list.size();i++) {
			Map map=list.get(i);
			if(map.get("nohandlercount").equals(0)){
				map.put("percent",0);
				continue;
			}
			if(map.get("nohandlercount") instanceof Long){
				countData = new BigDecimal( (Long)map.get("nohandlercount"));
			}else{
				 countData = (BigDecimal) map.get("nohandlercount");
					
			}
			map.put("percent",
						countData.divide(totalCount, 4, BigDecimal.ROUND_DOWN)
								.setScale(4, BigDecimal.ROUND_DOWN)
								.multiply(new BigDecimal(100)).setScale(2));
		}
		return list;
	}

	/**
	 * 批量更新告警数据
	 * 
	 * @param alarmId
	 * @param userId
	 * @param content
	 * @param contentType
	 * @param param
	 * @return
	 */
	public Map batchClose(HttpServletRequest request) throws Exception {

		// content为关闭时的处理意见
		String batchFilter=request.getParameter("batchFilter");
		String content = request.getParameter("content");
		// contentType为意见类型，目前可选项为：知识、意见
		String contentType = request.getParameter("contentType");
		String userId = Utils.getCurrentUserId(request);
       
		String alarmId = request.getParameter("alarmId");
		String monitor = request.getParameter("monitor");
		String occurTimes = request.getParameter("occurTimes");
		String alarmTitle = request.getParameter("alarmTitle");
		String level = request.getParameter("level");
		String showMyAlarm = request.getParameter("isMyalarm");
		String showNewAlarm = request.getParameter("isNewalarm");
		String lastUpdateTime = request.getParameter("lastUpdateTime");
		String alarmIdList = request.getParameter("alarmIdList");
		String from = " from TF_AVMON_ALARM_DATA t1 inner join TD_AVMON_MO_INFO tt on (t1.MO_ID = tt.MO_ID) "
				+ "left join portal_users tu on (t1.CONFIRM_USER = tu.USER_ID or t1.CONFIRM_USER = tu.REAL_NAME)"
				+ "left join TD_AVMON_MO_INFO t2 on (t1.MO_ID = t2.MO_ID) "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t3 on (t1.MO_ID = t3.MO_ID and t3.NAME='ip')  "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t4 on (t1.MO_ID = t4.MO_ID and t4.NAME='businessSystem')  "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t5 on (t1.MO_ID = t5.MO_ID and t5.NAME='position')  "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t6 on (t1.MO_ID = t6.MO_ID and t6.NAME='usage') "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t7 on (t1.MO_ID = t7.MO_ID and t7.NAME='hostName') "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t8 on (t1.MO_ID = t8.MO_ID and t8.NAME='deviceSN') "
				+ "left join TD_AVMON_MO_INFO_ATTRIBUTE t9 on (t1.MO_ID = t9.MO_ID and t9.NAME='assetNo') ";

		HashMap filter = new HashMap();

		// alarmIdList为关闭的id，以逗号分割，比如aaa,bbb,ccc
		if ("1".equals(showMyAlarm)) {
			// String userId = Utils.getCurrentUserId(request);
			filter.put("showMyAlarm", userId);
		} else {
			filter.put("showMyAlarm", null);
		}
		// 新告警
		if ("1".equals(showNewAlarm)) {
			filter.put("showNewAlarm", showNewAlarm);
		} else {
			filter.put("showNewAlarm", null);
		}
		filter.put("alarmId", alarmId);
		filter.put("monitor", monitor);
		filter.put("occurTimes", occurTimes);
		filter.put("content", alarmTitle);
		// 最后请求时间
		if (!StringUtils.isEmpty(lastUpdateTime)) {
			SimpleDateFormat myFormatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			filter.put("lastUpdateTime", myFormatter.format(new Date(new Long(
					(String) lastUpdateTime).longValue())));
		} else {
			filter.put("lastUpdateTime", null);
		}

		// 高级检索条件对应
		// 设备名称
		String s_moCaption = request.getParameter("s_moCaption");
		if (null != s_moCaption) {
			if (s_moCaption.equals(new String(s_moCaption
					.getBytes("ISO-8859-1"), "UTF-8"))) {
				s_moCaption = new String(s_moCaption.getBytes("ISO-8859-1"),
						"UTF-8");
			}
			filter.put("s_moCaption", s_moCaption);
		}

		String s_content_t = request.getParameter("s_content_t");
		// 告警内容
		if (null != s_content_t) {
			if (s_content_t.equals(new String(s_content_t
					.getBytes("ISO-8859-1"), "UTF-8"))) {
				s_content_t = new String(s_content_t.getBytes("iso8859-1"),
						"utf-8");
				;
			}

			filter.put("s_content_t", s_content_t);
		}

		// IP地址
		filter.put("s_attr_ipaddr", request.getParameter("s_attr_ipaddr"));
		// 处理人
		if (!StringUtil.isEmpty(request.getParameter("s_aknowledge_by"))) {
			JdbcTemplate jdbcTemplate = SpringContextHolder
					.getBean("jdbcTemplate");

			String sql = "select USER_ID as \"USER_ID\" from portal_users where real_name like '%"
					+ new String(request.getParameter("s_aknowledge_by")
							.getBytes("ISO-8859-1"), "UTF-8") + "%' ";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			// String userId = "";
			for (Map<String, Object> map : list) {
				userId += "'" + map.get("USER_ID") + "'" + ",";
			}
			if (!StringUtil.isEmpty(userId) && userId.length() > 0) {
				userId = userId.substring(0, userId.length() - 1);
			}
			// add by mark start 2014-1-20
			else {
				userId += "'" + request.getParameter("s_aknowledge_by") + "'"
						+ ",";
				userId = userId.substring(0, userId.length() - 1);
			}
			// add by mark end 2014-1-20

			filter.put("s_aknowledge_by", userId);
		}

		// 业务系统
		String s_attr_businessSystem = request
				.getParameter("s_attr_businessSystem");
		if (null != s_attr_businessSystem) {
			if (s_attr_businessSystem.equals(new String(s_attr_businessSystem
					.getBytes("ISO-8859-1"), "UTF-8"))) {
				s_attr_businessSystem = new String(
						s_attr_businessSystem.getBytes("ISO-8859-1"), "UTF-8");
			}
			filter.put("s_attr_businessSystem", s_attr_businessSystem);
		}

		// 位置
		String s_attr_position = request.getParameter("s_attr_position");
		if (null != s_attr_position) {

			if (s_attr_position.equals(new String(s_attr_position
					.getBytes("ISO-8859-1"), "UTF-8"))) {
				s_attr_position = new String(
						s_attr_position.getBytes("ISO-8859-1"), "UTF-8");
			}
			filter.put("s_attr_position", s_attr_position);
		}

		// 告警开始时间
		                               
		String s_start_date = request.getParameter("s_start_time");
		String s_start_hours = request.getParameter("s_start_hours");
		String s_start_minutes = request.getParameter("s_start_minutes");

        if(s_start_hours==null&&s_start_minutes==null){
			s_start_hours="00";
			s_start_minutes="00";
		}
		if (!StringUtil.isEmpty(s_start_date)
				&& !StringUtil.isEmpty(s_start_hours)
				&& !StringUtil.isEmpty(s_start_minutes)) {
			String s_start_time = s_start_date + " " + s_start_hours + ":"
					+ s_start_minutes;
			filter.put("s_start_time", s_start_time);
		} else {
			filter.put("s_start_time", null);
		}

		// 告警结束时间 2012/12/05 00:00
		String s_end_date = request.getParameter("s_end_time");
		String s_end_hours = request.getParameter("s_end_hours");
		String s_end_minutes = request.getParameter("s_end_minutes");
		if(s_end_hours==null&&s_end_minutes==null){
			s_end_hours="00";
			s_end_minutes="00";
		}
		if (!StringUtil.isEmpty(s_end_date) && !StringUtil.isEmpty(s_end_hours)
				&& !StringUtil.isEmpty(s_end_minutes)) {
			String s_end_time = s_end_date + " " + s_end_hours + ":"
					+ s_end_minutes;
			filter.put("s_end_time", s_end_time);
		} else {
			filter.put("s_end_time", null);
		}
		
		if (!StringUtil.isEmpty(s_end_date) ) {
			String s_end_time =null;
			//if(s_end_date.equals(s_start_date)){
				  SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				  Calendar cal = Calendar.getInstance();
					 cal.setTime(format.parse(s_end_date));
					 cal.add(Calendar.DATE, 1);
					 s_end_date=format.format(cal.getTime());
					 s_end_time = s_end_date + " " + s_end_hours + ":"+ s_end_minutes;
			//}
			//else{
				 //s_end_time = s_end_date + " " + s_end_hours + ":"+ s_end_minutes;
			//}
			filter.put("s_end_time", s_end_time);
		} else {
			filter.put("s_end_time", null);
		}

		List<Integer> levelList = new ArrayList<Integer>();
		if (level != null) {
			for (String s : level.split(",")) {
				if (s.trim().length() > 0) {
					// modify by mark start
					levelList.add(Integer.valueOf(s));
					// modify by mark end
				}
			}
		}
		/*
		 * Integer[] levels = new Integer[levelList.size()]; for (int i = 0; i <
		 * levelList.size(); i++) { levels[i] = levelList.get(i); }
		 */

		/*
		 * String where = this.getActiveAlarmSqlWhere(path, levels, filter,
		 * null, null);
		 */
		String where="";
		if(level==null){
			 where =this.getActiveAlarmSqlWhereByCriteria(new Integer[] { 1, 2, 3, 4, 5 },
					filter);
		}
		else{
			 where =this.getActiveAlarmSqlWhereByCriteria(new Integer[] { Integer.parseInt(level) },
					filter);	
		}
		

		Map<String, String> m = new HashMap<String, String>();
		try {
			//update by jay (dont know why there need to add Comments)
			//this.addAlarmComments(alarmIdList, userId, content, contentType,where, from);
			
			this.closeAlarm(alarmIdList, userId, where, from);
			// 返回结果
			m.put("result", "ok");
		} catch (Exception e) {
			logger.error(e.getMessage());
			m.put("result", "error");
		}
		return m;
	}

	/**
	 * 关闭告警
	 * 
	 * @param alarmIdList
	 * @param userId
	 */
	private void closeAlarm(String alarmIdList, String userId, String where,
			String from) {
		logger.debug("closeAlarm begin..");
		String sysDate = "sysdate";
		if (DBUtils.isPostgreSql()) {
			sysDate = "now()";
		}
		String originalWhere = "";
		String[] alarmIds=null;
		String temString="";
		if(alarmIdList!=null&&alarmIdList.length()>0){
			alarmIds=alarmIdList.split(",");
			
			//修改逻辑
			if (alarmIds.length > 0) {
				for(String s:alarmIds){
					temString+="'"+s+"'"+",";
				}
				temString=temString.substring(0, temString.length()-1);
				where = " WHERE ALARM_ID IN " +"(" +temString+")";
				/*originalWhere = " where merged_alarm_id in " + "(" +temString+")"
						+ " or alarm_id in "  +"(" +temString+")";*/
			} else {
				/*originalWhere = "where alarm_id in( select t1.alarm_id " + from
						+ where + ")";*/
			}
		}
		
		

		String sql = "INSERT INTO "
				+ "TF_AVMON_ALARM_HISTORY(ALARM_ID,CASE_ID,"
				+ "MO_ID,SOURCE,SOURCE_TYPE,CODE,TITLE,CONTENT,CURRENT_GRADE,COUNT,STATUS,TYPE,FIRST_OCCUR_TIME,LAST_OCCUR_TIME,"
				+ " SOLUTION,SOLUTION_TYPE,CLOSE_BY,CLOSE_TIME,RECEIVE_TIME,KPI_CODE,INSTANCE) "
				+ "SELECT ALARM_ID,CASE_ID,t1.MO_ID,SOURCE,SOURCE_TYPE,CODE,TITLE,CONTENT,CURRENT_GRADE,COUNT,STATUS,TYPE,FIRST_OCCUR_TIME,"
				+ "LAST_OCCUR_TIME,SOLUTION,SOLUTION_TYPE,'" + userId + "',"
				+ sysDate + ",RECEIVE_TIME,KPI_CODE,INSTANCE " + from + where;

		logger.debug("closeAlarm  TF_AVMON_ALARM_HISTORY insert sql :  " + sql);

		jdbc.execute(sql);

		/*String deleteOriginalDataSql = "delete from tf_avmon_alarm_original "
				+ originalWhere;
		logger.debug("closeAlarm  deleteOriginalDataSql :  "
				+ deleteOriginalDataSql);
		jdbc.execute(deleteOriginalDataSql);*/

		String deleteAlarmDataSql = "delete FROM TF_AVMON_ALARM_DATA  where alarm_id in ( select t1.alarm_id "
				+ from + where + ")";
		logger.debug("closeAlarm  deleteAlarmDataSql :  " + deleteAlarmDataSql);
		jdbc.execute(deleteAlarmDataSql);

		logger.debug("closeAlarm complete.."); 

	}

	/**
	 * 添加comments
	 * 
	 * @param alarmIdStr
	 * @param userId
	 * @param content
	 * @param contentType
	 */
	private void addAlarmComments(String alarmIdStr, final String userId,
			final String content, final String contentType, String where,
			String from) {
		logger.debug("addAlarmComments");

		if (alarmIdStr != null && alarmIdStr.length() > 0) {
			alarmIdStr = alarmIdStr.replaceAll("\\(", "");
			alarmIdStr = alarmIdStr.replaceAll("\\)", "");
			alarmIdStr = alarmIdStr.replaceAll("'", "");
		}
		final String alarmIds[] = alarmIdStr.split(",");
		String sysDate = "sysdate";
		String sql = "INSERT INTO TF_AVMON_ALARM_COMMENT ( ALARM_ID, CREATE_TIME, CREATE_BY, CONTENT, TYPE)";
		if (DBUtils.isPostgreSql()) {
			sysDate = "now()";
		}

		if (alarmIdStr.length() > 0) {
			sql += " VALUES (? ," + sysDate + ",? ,? ,? )";
			BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
				@Override
				public int getBatchSize() {
					return alarmIds.length;
				}

				@Override
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					ps.setString(1, alarmIds[i]);
					ps.setString(2, userId);
					ps.setString(3, content);
					ps.setString(4, contentType);
				}
			};
			jdbc.batchUpdate(sql, setter);
		} else {
			sql = "INSERT INTO TF_AVMON_ALARM_COMMENT (ALARM_ID, CREATE_TIME, CREATE_BY, CONTENT, TYPE)"
					+ "SELECT ALARM_ID,"
					+ sysDate
					+ ",'"
					+ userId
					+ "','"
					+ content + "','" + contentType + "'" + from + where;
			jdbc.execute(sql);
		}
		logger.debug("add comment done.");
	}
	
	  public List getAllBiz(HttpServletRequest request) {
	        Map map=new HashMap(); 
	    	Locale locale = request.getLocale();
	    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
	    	String others = bundle.getString("others");
	        String sql="select id as \"id\", businessname as \"name\" from tf_avmon_biz_dictionary";
	        List list=jdbc.queryForList(sql);
	        
	        Map otherMap=new HashMap();
	        otherMap.put("id", list.size()+1);
	        otherMap.put("name", "其他");
	       // otherMap.put("name", "others");
	        Map allMap=new HashMap();
	        allMap.put("id", 0);
	      //  allMap.put("name", "ALL");
	        allMap.put("name", "所有");
	        list.add(0, allMap);
	        list.add(otherMap);
	        return list;
	    }
}
