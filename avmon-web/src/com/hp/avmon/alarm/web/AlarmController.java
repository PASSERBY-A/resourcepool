package com.hp.avmon.alarm.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.hp.avmon.alarm.service.AlarmService;
import com.hp.avmon.alarm.service.AlarmViewService;
import com.hp.avmon.alarm.service.ProcessToolWebService;
import com.hp.avmon.common.Config;
import com.hp.avmon.performance.store.ResourceStore;
import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.ireport.util.StringUtil;
import com.hp.avmon.utils.TreeObject;
import com.hp.avmon.utils.Utils;
import com.hp.avmonserver.entity.Alarm;
import com.hp.avmonserver.entity.AlarmComment;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/pages/alarm/*")
public class AlarmController {
	private static final Log logger = LogFactory.getLog(AlarmController.class);
	@Autowired
	private AlarmService alarmService;

	@Autowired
	private ProcessToolWebService processToolWebService;

	@Autowired
	private AlarmViewService alarmViewService;
	/**
	 * 根据request信息获取用户信息
	 * 
	 * @param request
	 * @param writer
	 * @throws Exception
	 */
	private String getCurrentUserId(HttpServletRequest request) {
		@SuppressWarnings("rawtypes")
		Map currUser = (Map) request.getSession().getAttribute(
				Config.CURRENT_USER);

		String userId = "unknown";
		if (currUser != null) {
			userId = (String) currUser.get("USER_ID");
			if (userId == null) {
				userId = "unknown";
			}
		}

		return userId;
	}

	@RequestMapping(value = "/getAllBiz")
	@ResponseBody
	public List getAllBiz(HttpServletRequest request) throws Exception {
		List list = alarmService.getAllBiz(request);
		return list;
	}
	/**
	 * 获取某个告警的Comments
	 * 
	 * @param request
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value = "getAlarmComments")
	@ResponseBody
	public List getAlarmComments(HttpServletRequest request)
			throws Exception {
		String alarmId = request.getParameter("alarmId");
		@SuppressWarnings("unchecked")
		List<AlarmComment> list = alarmService.getAlarmComments(alarmId);
		return list; 
	}
	
	

	/**
	 * 获取告警详情，包括Comments
	 * 
	 * @param request
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAlarmDetail")
	@ResponseBody
	public Map getAlarmDetail(HttpServletRequest request)
			throws Exception {
		String alarmId = request.getParameter("alarmId");
		// logger.info("getAlarmDetail-alarmId:" + alarmId);
		Map<String, Object> map = new HashMap<String, Object>();
		// map.put("alarm",
		// alarmService.convertAlarm(alarmService.getAlarm(alarmId)));
		// map.put("comments", alarmService.getAlarmComments(alarmId));
		map.put("alarm", alarmService.getAlarmById(alarmId));
		map.put("comments", alarmService.getAlarmComments(alarmId));
		map.put("picture", alarmService.getMoTypeImageInfo(alarmId));
        return map;
	}

	/**
	 * 关闭一个或多个告警
	 * 
	 * @param request
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value = "close")
	public void close(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		// content为关闭时的处理意见
		String content = request.getParameter("content");
		// contentType为意见类型，目前可选项为：知识、意见
		String contentType = request.getParameter("contentType");
		String userId = getCurrentUserId(request);

		// alarmIdList为关闭的id，以逗号分割，比如aaa,bbb,ccc
		String alarmId = request.getParameter("alarmIdList");
		@SuppressWarnings("rawtypes")
		Map map = alarmService.close(alarmId, userId, content, contentType);
		String json = JackJson.fromObjectToJson(map);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	/**
	 * 关闭一个或多个告警
	 * 
	 * @param request
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchClose")
	@ResponseBody
	public Map batchClose(HttpServletRequest request)
			throws Exception {

		@SuppressWarnings("rawtypes")
		Map map = alarmService.batchClose(request);
		return map;
	}

	/**
	 * 告警认领，参数类似关闭时的参数，可对一个或多个告警进行关闭
	 * 
	 * @param request
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value = "/aknowledge")
	@ResponseBody
	public Map aknowledge(HttpServletRequest request)
			throws Exception {

		String alarmId = request.getParameter("alarmId");
		String userId = getCurrentUserId(request);

		String content = request.getParameter("content");

		//String contentType = request.getParameter("contentType");
		String contentType ="1";
		@SuppressWarnings("rawtypes")
		HashMap map = alarmService.aknowledge(alarmId, userId, content,
				contentType);
		
        return map;
        /*String json = JackJson.fromObjectToJson(map);
		writer.write(json);
		writer.flush();
		writer.close();*/
	}

	/**
	 * 告警前传，暂不支持，将来与流程工具集成
	 * 
	 * @param request
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value = "forward")
	public void forward(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		// 暂不支持，将来与流程工具集成
	}

	/**
	 * 取得48小时内清除告警信息
	 * 
	 * @param request
	 * @param tree
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping(value = "/getHistoryAlarmDataOld")
	public void getHistoryAlarmDataOld(HttpServletResponse response,
			HttpServletRequest request, PrintWriter writer) {
		try {
			@SuppressWarnings("rawtypes")
			Map map = alarmService.getHistoryAlarmList(request);
			String jsonData = JackJson.fromObjectToJson(map);
			// logger.info("HistoryAlarmData-" + jsonData);
			// 加入check
			response.setHeader("Content-Encoding", "utf-8");
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			writer = response.getWriter();
			writer.write(jsonData);
			writer.flush();
			writer.close();
		} catch (Throwable e) {
			
			logger.error(e.getMessage());
		}
	}

	/**
	 * 取得48小时内清除告警信息
	 * 
	 * @param request
	 * @param tree
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping(value = "/getHistoryAlarmData")
	@ResponseBody
	public List getHistoryAlarmData(HttpServletResponse response,
			HttpServletRequest request) {
		int count =0;
		try {
			@SuppressWarnings("rawtypes")
			List list = alarmService.getHistoryAlarmListFor48H(request);
			if(list.size()==0){
					 return list;
			}
			count=alarmService.getHistoryAlarmDataByCriteriaCount(request);
			Map<String, Object> map=new HashMap<String, Object>();
		    map.put("totalcount", count);
			list.add(map);
			return list;
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
		return null;
	
	}
	/**
	 * 取得活动告警列表
	 * 
	 * @param response
	 * @param request
	 * @param writer
	 * @return
	 */
	@RequestMapping(value = "/getActiveAlarmData")
	public void getActiveAlarmData(HttpServletResponse response,
			HttpServletRequest request, PrintWriter writer) {
		try {
			@SuppressWarnings("rawtypes")
			Map map = alarmService.getActiveAlarmList(request);
			String jsonData = JackJson.fromObjectToJson(map);
			// logger.debug("ActiveAlarmData-" + jsonData);
			// 加入check
			response.setHeader("Content-Encoding", "utf-8");
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			writer = response.getWriter();
			writer.write(jsonData);
			writer.flush();
			writer.close();
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 右键 取得历史告警列表
	 * 
	 * @param response
	 * @param request
	 * @param writer
	 * @return
	 */
	@RequestMapping(value = "/getPastRecordsData")
	@ResponseBody
	public List getPastRecordsData(HttpServletRequest request) {
		try {
			@SuppressWarnings("rawtypes")
			List list = alarmService.getPastRecordsList(request);
			return list;
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 获取告警顶部告警图标的数量
	 * 
	 * @param request
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value = "getMoStatus")
	public void getMoStatus(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String userId = getCurrentUserId(request);
		@SuppressWarnings("rawtypes")
		Map map = alarmService.getMoStatus(request, userId);
		String json = JackJson.fromObjectToJson(map);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	/**
	 * 获取变更节点集合
	 * 
	 * @param request
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value = "getChangedNode")
	public void getChangedNode(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		@SuppressWarnings("rawtypes")
		Map map = alarmService.getChangedNode(request);
		String json = JackJson.fromObjectToJson(map);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	/**
	 * 获取树节点的根节点id
	 * 
	 * @param request
	 * @param writer
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "getFirstTreeId")
	public void getFirstTreeId(HttpServletRequest request, PrintWriter writer)
			throws Exception {
		String userId = Utils.getCurrentUserId(request);
		String parentId = request.getParameter("id");

		List<Map> moTreeList = alarmViewService.getMoTree(userId, parentId);
		Map<String, Object> map = new HashMap<String, Object>();
		if (moTreeList != null && moTreeList.size() != 0) {
			for (Map temp : moTreeList) {
				map.put("treeId", temp.get("id"));
				map.put("treeText", temp.get("text"));
				map.put("alarm_count", temp.get("alarm_count"));
				map.put("path", temp.get("path"));
				break;
			}
		}

		String jsonData = JackJson.fromObjectToJson(map);
		// logger.debug("FirstTreeId: " + jsonData);
		writer.write(jsonData);
		writer.flush();
		writer.close();
	}

	/**
	 * 加载树形菜单
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 * @throws ServletException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "menuTree")
	public void getMenuTree(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String userId = Utils.getCurrentUserId(request);
		String parentId = request.getParameter("id");
		if (StringUtils.isEmpty(parentId) || parentId.equals("root")) {
			parentId = "root";
		} else {
			String a[] = parentId.split("\\*");
			if (a.length > 1) {
				parentId = a[1];
			}
		}

		// 根据父节点取得下属节点信息
		List<Map> moTreeList = alarmViewService.getMoTree(userId, parentId);
		if (!"true".equalsIgnoreCase(request.getParameter("viewOnly"))) {// add
																			// by
																			// muzh
																			// for
																			// 告警订阅
			for (Map<String, String> temp : moTreeList) {
				String alarm_count = "0";
				alarm_count = MyFunc.nullToString(temp.get("alarm_count"));
				if (StringUtil.isEmpty(alarm_count)) {
					alarm_count = "0";
				}
				// logger.debug("取得各个类型的告警数量moId=" + temp.get("id") + ":" +
				// alarm_count);

				temp.put("text", temp.get("text") + " (" + alarm_count + ")");
			}
		}

		String json = JackJson.fromObjectHasDateToJson(moTreeList);
		writer.write(json);
		writer.flush();
		writer.close();
	}

	private String targetNodeId = null;
	private String targetNodePid = null;
	private String targetNodePath = "";

	/**
	 * 检索树形菜单
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 * @throws ServletException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "searchTreeNode")
	public void searchTreeNode(HttpServletRequest request, PrintWriter writer)
			throws Exception {
		Gson gson = new Gson();

		// 检索条件
		String param = request.getParameter("searchParam");
		String searchTreeId = request.getParameter("searchTreeId");

		// 取出树的基础数据
		List<Map> allNodeDataList = alarmViewService.getAllTreeNodeData();
		if (allNodeDataList != null && allNodeDataList.size() != 0) {

			targetNodeId = null;
			targetNodePid = null;
			targetNodePath = "";

			boolean isExistLast = false;
			boolean isExist = false;
			int loc = 0;
			// 模糊匹配【查询关键字】
			for (Map temp : allNodeDataList) {
				loc++;
				String textStr = MyFunc.nullToString(temp.get("text"));
				if (!StringUtil.isEmpty(textStr)) {
					if (textStr.contains(param)) {
						if (StringUtil.isEmpty(searchTreeId)) {
							// 第一次检索时
							targetNodeId = MyFunc.nullToString(temp.get("id"));
							targetNodePid = MyFunc
									.nullToString(temp.get("pid"));
							isExistLast = false;
							break;
						} else {
							// 检索下一个节点

							String nodes[] = searchTreeId.split("\\/");
							for (int i = 0; i < nodes.length; i++) {
								if (nodes[i].equals(MyFunc.nullToString(temp
										.get("id")))) {
									isExist = true;
									break;
								}
							}

							// 已经存在于【历史检索列表】
							if (isExist) {
								isExist = false;
								isExistLast = false;

								if (loc == allNodeDataList.size()) {
									isExistLast = true;
								}
								continue;
							} else {
								// 第一次检索时
								targetNodeId = MyFunc.nullToString(temp
										.get("id"));
								targetNodePid = MyFunc.nullToString(temp
										.get("pid"));
								isExistLast = false;
								break;
							}
						}
					}
				}
			}

			// 模糊匹配成功的场合,构造树结构的节点path
			if (!StringUtil.isEmpty(targetNodeId) || isExistLast) {
				if (isExistLast) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("success", false);
					map.put("isExistLast", isExistLast);

					String json = gson.toJson(map);

					writer.write(json);
					writer.flush();
					writer.close();
				} else {
					// 取得数据
					List<TreeObject> treeObjDataList = getTreeObjVo(allNodeDataList);
					TreeObject tree = new TreeObject();
					// 传入匹配节点的父节点，递归找出上级目录结构
					structureChildNode(tree, treeObjDataList, targetNodePid);

					// String nodePath = "/root" + targetNodePath + "/" +
					// targetNodeId;
					String nodePath = "/root/" + targetNodePath + targetNodeId;
					// logger.info("targetNodePath: " + nodePath);

					Map<String, Object> map = new HashMap<String, Object>();
					// map.put("message",
					// "/root/root*V20121129114115/V20121129114115*10.209.83.121");
					map.put("message", nodePath);
					map.put("success", true);

					String json = gson.toJson(map);

					writer.write(json);
					writer.flush();
					writer.close();
				}
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("success", false);

				String json = gson.toJson(map);

				writer.write(json);
				writer.flush();
				writer.close();
			}
		}
	}

	/**
	 * 转换树形菜单基础数据
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<TreeObject> getTreeObjVo(List<Map> objData) {
		List<TreeObject> treeList = new ArrayList<TreeObject>();
		TreeObject obj = new TreeObject();

		for (Map temp : objData) {
			obj = new TreeObject();

			obj.setText(MyFunc.nullToString(temp.get("text")));
			obj.setId(MyFunc.nullToString(temp.get("id")));
			obj.setPid(MyFunc.nullToString(temp.get("pid")));
			obj.setMoId(MyFunc.nullToString(temp.get("moId")));

			treeList.add(obj);
		}

		return treeList;
	}

	/**
	 * 根据传入的子节点开始，遍历父节点
	 * 
	 * @param tree
	 * @param dataList
	 * @param stid
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public void structureChildNode(TreeObject tree, List<TreeObject> dataList,
			String stid) {

		for (int i = 0; i < dataList.size(); i++) {
			if (stid.equals(dataList.get(i).getMoId())) {
				// targetNodePath = targetNodePath + "/" +
				// dataList.get(i).getId();
				targetNodePath = dataList.get(i).getId() + "/" + targetNodePath;
				structureChildNode(dataList.get(i), dataList, dataList.get(i)
						.getPid());
			}
		}
	}

	/**
	 * 重新检查树形菜单节点数量是否有变化
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 * @throws ServletException
	 */
	@RequestMapping(value = "getTreeIdByMoId")
	public void getTreeIdByMoId(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String userId = Utils.getCurrentUserId(request);
		String parentId = request.getParameter("moId");

		@SuppressWarnings("rawtypes")
		Map result = alarmViewService.getTreeIdByMoId(userId, parentId);

		String json = JackJson.fromObjectHasDateToJson(result);
		writer.write(json);
		writer.flush();
		writer.close();
	}

	/**
	 * 取得设备类型
	 * 
	 * @param response
	 * @param request
	 * @param writer
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getRecipientData")
	public void getRecipientData(HttpServletRequest request, PrintWriter writer) {

		try {
			List<Map> data = alarmService.getRecipientData(request);
			String jsonData = JackJson.fromObjectToJson(data);
			// logger.debug("getRecipientData:" + jsonData);

			writer.write(jsonData);
			writer.flush();
			writer.close();
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 单击[保存]
	 * 
	 * @param request
	 * @param writer
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/forwardAlarm")
	@ResponseBody
	public void forwardAlarm(HttpServletRequest request)
			throws UnsupportedEncodingException {
		Map dataMap = new HashMap();

		// if (request.getParameter("kpiInfo") != null) {
		// dataMap.put("kpiInfo",
		// URLDecoder.decode(request.getParameter("kpiInfo"), "UTF-8"));
		// }
		String alarmId = MyFunc.nullToString(request.getParameter("alarmId"));
		Alarm alarm = alarmService.getAlarmObjById(alarmId);

		String moId = alarm.getMoId();
		int level = alarm.getGrade();
		String bizSystem = ResourceStore.getMoPropValue(moId,
				"processToolBizSys");
		String hostName = ResourceStore.getMoPropValue(moId, "hostName");
		String ip = ResourceStore.getMoPropValue(moId, "ip");

		if (bizSystem == null || "".equals(bizSystem) || "未知".equals(bizSystem)) {
			bizSystem = "inc_biz_sys_na";
		}
		if (!bizSystem.startsWith("inc_biz_sys")) {
			bizSystem = "inc_biz_sys_na";
		}
		String vendor = ResourceStore.getMoPropValue(moId, "processToolVendor");
		if (vendor == null || "".equals(vendor) || "未知".equals(vendor)) {
			vendor = "HW_VENDOR_NA";
		}
		if (!vendor.startsWith("HW_VENDOR_")) {
			vendor = "HW_VENDOR_NA";
		}
		dataMap.put("inc_biz_system", bizSystem);
		dataMap.put("inc_hardware_vendor", vendor);

		if (level == 5) {
			dataMap.put("incident_type", "INC_KIND_URGENT");
		} else {
			dataMap.put("incident_type", "INC_KIND_COMMON");
		}

		dataMap.put("inc_category", "SOFTWARE_OS");

		dataMap.put("title",
				hostName + " " + MyFunc.nullToString(alarm.getTitle()));
		dataMap.put("inc_desc", MyFunc.nullToString(alarm.getContent()));
		dataMap.put("occur_time", MyFunc.nullToString(MyFunc.formatTime(alarm
				.getFirstOccurTime())));
		dataMap.put("registime",
				MyFunc.getCurrentTimeString("yyyy-MM-dd HH:mm:ss"));
		dataMap.put("avmon_id", alarmId);
		// dataMap.put("Recipient",
		// MyFunc.nullToString(request.getParameter("Recipient")));

		dataMap.put("host_name", hostName + "/" + ip);
		dataMap.put("host_serial_no",
				ResourceStore.getMoPropValue(moId, "deviceSN"));

		boolean result = remoteForwardAlarm(dataMap);

		/*String json = JackJson.fromObjectHasDateToJson(result);
		writer.write(json);
		writer.flush();
		writer.close();*/
	}

	@SuppressWarnings("rawtypes")
	private boolean remoteForwardAlarm(Map dataMap) {

		// logger.debug("forwardAlarm Data:" + dataMap);
		Map<String, String> retMap = processToolWebService.sendAlarm(dataMap);
		if ("SUCCESS".equals(retMap.get("RETURN"))) {
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping(value = "/getActiveAllAlarmData")
	public void getActiveAllAlarmData(HttpServletResponse response,
			HttpServletRequest request, PrintWriter writer) {
		try {
			@SuppressWarnings("rawtypes")
			Map map = alarmService.getActiveAllAlarmList(request);
			String jsonData = JackJson.fromObjectToJson(map);
			// logger.debug("ActiveAlarmData-" + jsonData);
			// 加入check
			response.setHeader("Content-Encoding", "utf-8");
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			writer = response.getWriter();
			writer.write(jsonData);
			writer.flush();
			writer.close();
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
	}

	// Jay add new pages for version 1.3

	@RequestMapping(value = "/getAllAlertGrade")
	@ResponseBody
	public Map getAllAlertGrade(HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = alarmService.getAllAlertGrade(request);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return map;
	}

	// for chart1
	@RequestMapping(value = "/getAlarmLevelData")
	@ResponseBody
	public List<Map<String, Object>> getAlarmLevelData(HttpServletRequest request) {
		//List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		String typeId=request.getParameter("typeId");
		String period=request.getParameter("period");
		String bizName=request.getParameter("bizName");
		String s_start_date=request.getParameter("s_start_date");
		String s_end_date=request.getParameter("s_end_date");
		try {
			list1 = alarmService.getNoHandlerAlarmLevelData(typeId,period,s_start_date,s_end_date,bizName);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return list1;
	}
	@RequestMapping(value = "/getAlarmLevelData1")
	@ResponseBody
	public List<Map<String, Object>> getAlarmLevelData1(HttpServletRequest request) {
		return null;
		/*List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		String typeId=request.getParameter("typeId");
		String period=request.getParameter("period");
		String s_start_date=request.getParameter("s_start_date");
		String s_end_date=request.getParameter("s_end_date");
		try {
			list = alarmService.getHandlerAlarmLevelData(typeId,period,s_start_date,s_end_date);
			list1 = alarmService.getNoHandlerAlarmLevelData(typeId,period,s_start_date,s_end_date);
			if (list.size() >= list1.size()) {
				for (int i = 0; i < list.size(); i++) {
					  for(int j=0;j<list1.size();j++){
						  if(list.get(i).get("grade").equals(list1.get(j).get("grade"))){
							  list.get(i).put("nohandlercount",list1.get(j).get("nohandlercount")); 
							  break;
						  }else{
							  if(list.get(i).get("nohandlercount")!=null){
								  break;
							  }
							  else
						    	list.get(i).put("nohandlercount",null);	
						  }
					  }
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return list;*/
	}
	
	// for chart2
	@RequestMapping(value = "/getTopTenAlarmData")
	@ResponseBody
	public List<Map<String, Object>> getTopTenAlarmData1(HttpServletRequest request) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = alarmService.getTopTenAlarmData(request);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}
	
	// for chart2
	/*	@RequestMapping(value = "/getTopTenAlarmData1")
		@ResponseBody
		public List<Map<String, Object>> getTopTenAlarmData(HttpServletRequest request) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			try {
				list = alarmService.getTopTenAlarmData1(request);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			return list;
		}*/
	
	// for chart3
    /*	@RequestMapping(value = "/getTodayAndYesterdayAlarmData")
	@ResponseBody
	public List<Map<String, String>> getThisWeekAndLastWeekAlarmData(
			HttpServletResponse response, HttpServletRequest request) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			list = alarmService.getTodayAndYesterdayAlarmData();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}*/
	
	// for chart3
		@RequestMapping(value = "/getAlarmDataByType")
		@ResponseBody
	public List<Map<String, String>> getAlarmDataByType(HttpServletRequest request) {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			String typeId=request.getParameter("typeId");
			String bizName=request.getParameter("bizName");
			String period=request.getParameter("period");
			String s_start_date=request.getParameter("s_start_date");
			String s_end_date=request.getParameter("s_end_date");
			
			try {
				if("ALL".equals(typeId)||typeId==null){
					list = alarmService.getAlarmDataByAllType(period,s_start_date,s_end_date,bizName);//for all data
				}
				else{
					list = alarmService.getAlarmDataByTypeId(typeId,period,s_start_date,s_end_date,bizName);
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			return list;
	}
     
	//for chart4
	@RequestMapping(value = "/getTopTenKpiAlarmData")
	@ResponseBody
	public List<Map<String, String>> getTopTenKpiAlarmData(
			HttpServletResponse response, HttpServletRequest request) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String typeId = request.getParameter("typeId");
		String period = request.getParameter("period");
		String s_start_date=request.getParameter("s_start_date");
		String s_end_date=request.getParameter("s_end_date");
		String bizName = request.getParameter("bizName");
		if(typeId==null){
			typeId="ALL";
		}
		if(period==null){
			period="5";
		}
		try {
		list = alarmService.getTopTenKpiAlarmData(typeId,period,s_start_date,s_end_date,bizName);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}

	
	//for chart3 detail data grid chart3
		@RequestMapping(value = "/getAlarmDataTypeForCaption")
		@ResponseBody
		public List getAlarmDataTypeForCaption(
				HttpServletRequest request) {
			List list=null;
			try {
				 list = alarmService.getAlarmDataTypeForCaption(request);
				 if(list.size()==0){
					 return list;
				 }
				 int totalcount=alarmService.getAlarmDataTypeForCaptionCount(request);
				 Map<String, Object> map=new HashMap<String, Object>();
				 map.put("totalcount", totalcount);
				 list.add(map);
			} catch (Throwable e) {
				logger.error(e.getMessage());
			}
			return list;
		} 
	//for find alarm data
	@RequestMapping(value = "/getAllActiveAlarmDataList")
	@ResponseBody
	public List getAllActiveAlarmDataList(HttpServletResponse response,
			HttpServletRequest request) {
		List list=null;
		
		try {
			 list = alarmService.getAllActiveAlarmDataList(request);
			 if(list.size()==0){
				 return list;
			 }
			 int totalcount=alarmService.getActiveAlarmAllCount(request);
			 Map<String, Object> map=new HashMap<String, Object>();
			 map.put("totalcount", totalcount);
			 list.add(map);
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
		return list;
	} 
	
	//for chart2 detail data grid chart2
	@RequestMapping(value = "/getActiveAlarmDataListForCapation")
	@ResponseBody
	public List getActiveAlarmDataListForCapations(HttpServletResponse response,
			HttpServletRequest request) {
		List list=null;
		
		try {
			 list = alarmService.getActiveAlarmDataListByCaption(request);
			 if(list.size()==0){
				 return list;
			 }
			 int totalcount=alarmService.getActiveAlarmByCaptionCount(request);
			 Map<String, Object> map=new HashMap<String, Object>();
			 map.put("totalcount", totalcount);
			 list.add(map);
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
		return list;
	} 
	
	//for chart4 detail data grid chart4
		@RequestMapping(value = "/getAlarmDataForKpi")
		@ResponseBody
		public List getAlarmDataForKpi(
				HttpServletRequest request) {
			List list=null;
			
			try {
				 list = alarmService.getAlarmDataForKpi(request);
				 if(list.size()==0){
					 return list;
				 }
				 int totalcount=alarmService.getAlarmDataForKpiCount(request);
				 Map<String, Object> map=new HashMap<String, Object>();
				 map.put("totalcount", totalcount);
				 list.add(map);
			} catch (Throwable e) {
				logger.error(e.getMessage());
			}
			return list;
		} 
		
	
	   //for chart1 detail data grid chart1
		@RequestMapping(value = "/getActiveAlarmDataListForHandler")
		@ResponseBody
		public List getActiveAlarmDataListForHandler(
				HttpServletRequest request) {
			List list=null;
			try {
				 list = alarmService.getActiveAlarmDataListForHandler(request);
				 if(list.size()==0){
					 return list;
				 }
				 int totalcount=alarmService.getActiveAlarmByHandlerCount(request);
				 Map<String, Object> map=new HashMap<String, Object>();
				 map.put("totalcount", totalcount);
				 list.add(map);
			} catch (Throwable e) {
				logger.error(e.getMessage());
			}
			return list;
		} 
		
		 //for chart1 detail data grid chart1
		@RequestMapping(value = "/getActiveAlarmDataListForNoHandler")
		@ResponseBody
		public List getActiveAlarmDataListForNoHandler(
				HttpServletRequest request) {
			List list=null;
			
			try {
				 list = alarmService.getActiveAlarmDataListForNoHandler(request);
				 if(list.size()==0){
					 return list;
				 }
				 int totalcount=alarmService.getActiveAlarmByNoHandlerCount(request);
				 Map<String, Object> map=new HashMap<String, Object>();
				 map.put("totalcount", totalcount);
				 list.add(map);
			} catch (Throwable e) {
				logger.error(e.getMessage());
			}
			return list;
		} 
	
	
	@RequestMapping(value = "/getAllActiveAlarmDataByMoId")
	@ResponseBody
	public List getAllActiveAlarmDataByMoId(HttpServletResponse response,
			HttpServletRequest request) {
		List list=null;
		try {
			 list = alarmService.getAllActiveAlarmDataByMoId(request);
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
		return list;
	}
	
	//for find moid
	@RequestMapping(value = "/getAllMoData")
	@ResponseBody
	public List getAllMoData(
			HttpServletRequest request) {
		List list=null;
		String pagesize = request.getParameter("pagesize");
		String pagenum = request.getParameter("pagenum");
		try {
			 list = alarmService.getAllMoData(request);
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
		return list;
	} 

	//查询mo
	@RequestMapping(value = "/queryMoData")
	@ResponseBody
	public List queryMoData(
			HttpServletRequest request) {
		List list=null;
		try {
			 list = alarmService.queryMoData(request);
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
		return list;
	} 
	
	@RequestMapping(value = "/getOtherMoData")
	@ResponseBody
	public List getOtherMoData(HttpServletResponse response,
			HttpServletRequest request) {
		List list=null;
		try {
			 list = alarmService.getOtherMoData(request);
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
		return list;
	} 
	

	@RequestMapping(value = "/alermOverView")
	public String alermOverView(HttpServletRequest request) {
		return "alarm/overview";
	}

	@RequestMapping(value = "/alarm")
	public String goToAlarmDetailPage(HttpServletRequest request) {
		String bizName=request.getParameter("bizName");
		String caption=request.getParameter("caption");
		String level=request.getParameter("level");
		String isHandle=request.getParameter("isHandle");
		String typeId=request.getParameter("typeId");
		String period=request.getParameter("period");
		String s_start_date=request.getParameter("s_start_date");
		String s_end_date=request.getParameter("s_end_date");
		String kpi=request.getParameter("kpi");
		//for chart2
		if(caption!=null){
			request.setAttribute("sourceurl", "getActiveAlarmDataListForCapation?caption="+caption
					+"&period="+period+"&s_start_date="+s_start_date+"&s_end_date="+s_end_date+"&bizName="+bizName);
		}
		//for chart1
		else if(level!=null&&"1".equals(isHandle)){
			request.setAttribute("sourceurl", "getActiveAlarmDataListForHandler?level="+level+"&typeId="+typeId+"&period="+period+"&s_start_date="+s_start_date+"&s_end_date="+s_end_date+"&bizName="+bizName);
		}
		else if(level!=null&&"0".equals(isHandle)){
			request.setAttribute("sourceurl", "getActiveAlarmDataListForNoHandler?level="+level+"&typeId="+typeId+"&period="+period+"&s_start_date="+s_start_date+"&s_end_date="+s_end_date+"&bizName="+bizName);
		}
		//for chart4
		else if(typeId!=null&&typeId!=""){
			request.setAttribute("sourceurl", "getAlarmDataTypeForCaption?typeId="+typeId+"&period="+period+"&s_start_date="+s_start_date+"&s_end_date="+s_end_date+"&bizName="+bizName);
		}
		else if(kpi!=null){
			request.setAttribute("sourceurl", "getAlarmDataForKpi?kpi="+kpi+"&typeId="+typeId+"&period="+period+"&s_start_date="+s_start_date+"&s_end_date="+s_end_date+"&bizName="+bizName);
		}
		else{
			request.setAttribute("sourceurl", "getAllActiveAlarmDataList"); 
			//request.setAttribute("sourceurl", "getAllActiveAlarmDataList?period="+period+"&s_start_date="+s_start_date+"&s_end_date="+s_end_date);
		}
		return "alarm/alarm";
	}
	
	
	/**
	 * 取得活动告警列表
	 * 
	 * @param response
	 * @param request
	 * @param writer
	 * @return
	 */
	@RequestMapping(value = "/getActiveAlarmDataByCriteria")
	@ResponseBody
	public List<Map<String, Object>> getActiveAlarmDataByCriteria(
			HttpServletRequest request) {
		List<Map<String, Object>> list=null;
		int count=0;
		try {
		   list = alarmService.getActiveAlarmDataByCriteria(request);
		   if(list.size()==0){
				 return list;
		   }
		   count=alarmService.getActiveAlarmDataByCriteriaCount(request);
		   //
		   //System.out.println("############count is:"+count);
		   Map<String, Object> map=new HashMap<String, Object>();
		   map.put("totalcount", count);
		   list.add(map);
		   //
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
		return list;
	}
	
	@RequestMapping(value = "/getActiveAlarmDataByCriteriaForCount")
	@ResponseBody
	public int getActiveAlarmDataByCriteriaCount(HttpServletResponse response,
			HttpServletRequest request) {
		int count=0;
		try {
			count = alarmService.getActiveAlarmDataByCriteriaCount(request);
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
		return count;
	}
	// jay add end

	@RequestMapping(value = "/deleteallAlarm")
	public void deleteallAlarm(HttpServletResponse response,
			HttpServletRequest request, PrintWriter writer) {
		try {
			alarmService.deleteAllActiveAllAlarm(request);
		} catch (Throwable e) {
			logger.error(e.getMessage());
		}
	}

}
