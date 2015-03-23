package com.hp.avmon.performance.web;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.performance.service.AlarmSearchService;
import com.hp.avmon.performance.service.PerformanceService;
import com.hp.avmon.utils.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/performance/*")
public class PerformanceController {
	
	private static final Log logger = LogFactory.getLog(PerformanceController.class);
	

	@Autowired
	private PerformanceService performanceService;
	
    @Autowired
    private AlarmSearchService alarmSearchService;
	
	
    @RequestMapping(value = "index")
    public void index(@RequestParam("mo") String mo,Locale locale, Model model) {

        model.addAttribute("mo", mo );
        
    }
    
    @RequestMapping(value = "/overview")
    public String  overview(HttpServletRequest request) throws Exception {
    	return "performance/overview";
    }
    
    @RequestMapping(value = "menuTree")
    public void getMenuTree(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String userId=Utils.getCurrentUserId(request);
        String parentId=request.getParameter("id");
        String checkbox = request.getParameter("checkBox");
        if(parentId==null||parentId.equals("")){
            parentId="root";
        }
        String json=performanceService.getMoTreeJson(userId,parentId,checkbox,bundle);

        if("true".equalsIgnoreCase(checkbox)){
        	json = json.replace("\"leaf\":\"true\"", "\"leaf\":\"true\",\"checked\":false");
        }
//        logger.debug(json);
        writer.write(json);
        writer.flush();
        writer.close();
    }    
    
    @RequestMapping(value = "performanceSummaryList")
    public void performanceSummaryList(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String userId=Utils.getCurrentUserId(request);
        String parentId=request.getParameter("id");
        String businessSystem=request.getParameter("biz");
        if(businessSystem!=null){
            if(businessSystem.equals("null")){
                businessSystem="";
            }
            else{
                businessSystem=URLDecoder.decode(businessSystem,"UTF-8");
            }
        }
        if(parentId==null||parentId.equals("")){
            parentId="root";
        }
        Map map=performanceService.getPerformanceSummaryList(parentId,businessSystem,bundle);
        String json=JackJson.fromObjectToJson(map);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "getMoPath")
    public void getMoPath(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("moId");
        
        Map map=performanceService.getMoPath(moId);
        String json=JackJson.fromObjectToJson(map);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value="/test1")
    @ResponseBody
    public String test1(HttpServletRequest request) throws Exception {
        boolean list=performanceService.isSnmpMontiorObject(request);
        return "yes";
    }

    @RequestMapping(value = "totalViewData")
    @ResponseBody
    public List totalViewData(HttpServletRequest request) throws Exception {
        List list=performanceService.totalViewData(request);
        return list;
    }
    
    @RequestMapping(value = "goToHostPerformanceListView")
    public String goToHostPerformanceListView(HttpServletRequest request) throws Exception {
    	return "performance/hostlist";

    }
    
    @RequestMapping(value = "goToNetworkPerformanceListView")
    public String goToNetworkPerformanceListView(HttpServletRequest request) throws Exception {
    	return "performance/networklist";

    }
    
    @RequestMapping(value = "goToHardwarePerformanceListView")
    public String goToHardwarePerformanceListView(HttpServletRequest request) throws Exception {
    	return "performance/ilolist";

    }
    @RequestMapping(value = "goToOtherHardwarePerformanceListView")
    public String goToOtherHardwarePerformanceListView(HttpServletRequest request) throws Exception {
    	return "performance/otherhwlist";

    }
    
    @RequestMapping(value = "goToIPMIPerformanceListView")
    public String goToIPMIPerformanceListView(HttpServletRequest request) throws Exception {
    	return "performance/ipmilist";

    }
    
    @RequestMapping(value = "goToDBPerformanceListView")
    public String goToDBPerformanceListView(HttpServletRequest request) throws Exception {
    	return "performance/dblist";

    }
    @RequestMapping(value = "/getAllLevelAlarmDataByType")
    @ResponseBody
    public List getAllLevelAlarmDataByType(HttpServletRequest request) throws Exception{
    	 List list=performanceService.getAllLevelAlarmDataByType(request); 
         return list;
    }
    
    @RequestMapping(value = "/getAllLevelAlarmDataByOtherHWType")
    @ResponseBody
    public List getAllLevelAlarmDataByOtherHWType(HttpServletRequest request) throws Exception{
    	 List list=performanceService.getAllLevelAlarmDataByOtherHWType(request); 
         return list;
    }
    /**
	 * 取得活动告警列表
	 * 
	 * @param response
	 * @param request
	 * @param writer
	 * @return
     * @throws Exception 
	 */
	@RequestMapping(value = "/getAlarmData")
	@ResponseBody
	public List getActiveAlarmData(
			HttpServletRequest request) throws Exception{
		List<Map<String, Object>> list=null;
		int count=0;
   	   list=performanceService.getAlarmList(request); 
   	   if(list.size()==0){
			 return list;
	   }
	   count=performanceService.getAlarmListCount(request);
	   //
	   Map<String, Object> map=new HashMap<String, Object>();
	   map.put("totalcount", count);
	   list.add(map);
      return list;
	}
	
	@RequestMapping(value = "/getAlarmDataByCriteria")
	@ResponseBody
	public List getActiveAlarmByCriteria(
			HttpServletRequest request) throws Exception{
		List<Map<String, Object>> list=null;
		int count=0;
   	   list=performanceService.getAlarmListByCriteria(request); 
   	   if(list.size()==0){
			 return list;
	   }
	   count=performanceService.getAlarmListByCriteriaCount(request);
	   //
	   Map<String, Object> map=new HashMap<String, Object>();
	   map.put("totalcount", count);
	   list.add(map);
      return list;
	}
	
	
    
    @RequestMapping(value = "/basicInfo")
    @ResponseBody
    public Map getHostBasicInfo(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId"); 
        return performanceService.getMoBasicInfo(moId);
    }  
    
    
    
    @RequestMapping(value = "/getBasicInfoData")
    @ResponseBody
    public Map getBasicInfoData(
            HttpServletRequest request){
    	Map map =new HashMap();
        try {
             map = performanceService.getBasicInfoList(request);   
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return map;
    }
    
    //data performance for DB
    @RequestMapping(value = "/getBasicInfoDataForDB")
    @ResponseBody
    public Map getBasicInfoDataForDB(
            HttpServletRequest request){
    	Map map =new HashMap();
        try {
             map = performanceService.getBasicInfoForDBList(request);   
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return map;
    }
    
    @RequestMapping(value = "/getDBStatusInfoData")
    @ResponseBody
    public Map getDBStatusInfoData(
            HttpServletRequest request){
    	Map map =new HashMap();
        try {
             map = performanceService.getDBStatusInfoData(request);   
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return map;
    }
    
    //data performance for DB
    /**
     * 取得网络信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/networkInfo")
    @ResponseBody
    public Map getHostNetworks(HttpServletRequest request) throws Exception {
      String moId = request.getParameter("moId"/*"mo"*/);
      return performanceService.getMainEngineNetworks(moId);
    }
    
    /**
     * 进程  百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/courseBasic")
    @ResponseBody
    public Map getCourseBasicData(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        return performanceService.getMainEngineCourseInfo(moId);
    } 
    
    
    /**
     * memory  百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/memBasic")
    @ResponseBody
    public Map getMemBasicData(HttpServletRequest request) throws Exception {
       String moId = request.getParameter("moId");
       return  performanceService.getMainEngineMemInfo(moId);
    } 
    

    @RequestMapping(value = "/memUse")
    @ResponseBody
    public List getMemUseData(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        return performanceService.getMainEngineMem(moId);
    } 
    
    /**
     * CPU 百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/cpuUse")
    @ResponseBody
    public List getCpuUseData(HttpServletRequest request) throws Exception {
    	   String moId = request.getParameter("moId");
           return  performanceService.getMainEngineCpuInfo(moId);
    } 
    
    /**
     * 换页文件 百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/skipFileBasic")
    @ResponseBody
    public Map getSkipFileBasicData(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        return performanceService.getMainEngineSkipFileInfo(moId);
    } 
    
    /**
     * Disk 使用百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/diskUse")
    @ResponseBody
    public Map getDiskUseData(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        return (Map) performanceService.getMainEngineDisk(moId);
 
    } 
    
    
    /**
     * Disk 百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/diskBasic")
    @ResponseBody
    public Map getDiskBasicData(HttpServletRequest request) throws Exception {

        String moId = request.getParameter("moId");
       return  performanceService.getMainEngineDiskInfo(moId);
        
    } 
    
    /**
     * file system 百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/fileSysUse")
    @ResponseBody
    public Map getFileSysUseData(HttpServletRequest request) throws Exception {

        String moId = request.getParameter("moId");
        return performanceService.getMainEngineFileSys(moId);
       
    } 
    
    /**
     * 取得日志错误信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/getLogError")
    @ResponseBody
    public Map getLogError(HttpServletRequest request) throws Exception {  
        String moId = request.getParameter("moId");
        return performanceService.getLogError(moId);

    }
    @RequestMapping(value = "/getHostPerformanceList")
    @ResponseBody
    public List getHostPerformanceList(HttpServletRequest request) throws Exception{
    	List<Map<String, Object>> list=null;
		 int count=0;
    	 list=performanceService.getHostPerformanceList(request); 
    	 
    	 if(list.size()==0){
			 return list;
	   }
	   count=performanceService.getHostPerformanceListCount(request);
	   //
	   Map<String, Object> map=new HashMap<String, Object>();
	   map.put("totalcount", count);
	   list.add(map);
       return list;
    }
    
    @RequestMapping(value = "/queryPerformanceList")
    @ResponseBody
    public List queryPerformanceList(HttpServletRequest request) throws Exception{
    	List<Map<String, Object>> list=performanceService.queryPerformanceList(request); 
       return list;
    }
    
    
    @RequestMapping(value = "/getHostOverViewPerformanceList")
    @ResponseBody
    public List getHostOverViewPerformanceList(HttpServletRequest request) throws Exception{
    	List<Map<String, Object>> list=performanceService.getHostOverViewPerformanceList(request); 
       return list;
    }
    
    @RequestMapping(value = "/getDBOverViewPerformanceList")
    @ResponseBody
    public List getDBOverViewPerformanceList(HttpServletRequest request) throws Exception{
    	List<Map<String, Object>> list=performanceService.getDBOverViewPerformanceList(request); 
       return list;
    }
    
    @RequestMapping(value = "/getDBPerformanceList")
    @ResponseBody
    public List getDBPerformanceList(HttpServletRequest request) throws Exception{
    	List<Map<String, Object>> list=null;
		 int count=0;
    	 list=performanceService.getDBPerformanceList(request); 
    	 if(list.size()==0){
			 return list;
	   }
	   count=performanceService.getDBPerformanceListCount(request);
	   //
	   Map<String, Object> map=new HashMap<String, Object>();
	   map.put("totalcount", count);
	   list.add(map);
       return list;
    }
    
    @RequestMapping(value = "/getAllBiz")
    @ResponseBody
    public List getAllBiz(HttpServletRequest request) throws Exception {
        List list=performanceService.getAllBiz(request); 
        return list;
    }
    
    /**
     * 网络传输 发送包曲线图
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/networkSend")
    @ResponseBody
    public Map getNetworkSendData(HttpServletRequest request) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String moId = request.getParameter("moId");
        return performanceService.getMainEngineNetworkSend(moId,bundle);
       
    } 
    
    /**
     * 网络传输 接收包 曲线图
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/networkReceive")
    @ResponseBody
    public Map getNetworkReceiveData(HttpServletRequest request) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String moId = request.getParameter("moId");
        return  performanceService.getMainEngineNetworkReceive(moId,bundle);
    } 
    
    /**
     *磁盘读
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/diskRead")
    @ResponseBody
    public Map getDiskReadData(HttpServletRequest request) throws Exception {
       String moId = request.getParameter("moId");
       Locale locale = request.getLocale();
       ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
       return performanceService.getMainEngineDiskRead(moId,bundle);
       
    }
    
    /**
     * 磁盘写
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/diskWrite")
    @ResponseBody
    public Map getDiskWriteData(HttpServletRequest request) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String moId = request.getParameter("moId");
        return performanceService.getMainEngineDiskWrite(moId,bundle);
       
    } 
    /**
     * 换页文件传输 接受包
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/skipfilePagein")
    @ResponseBody
    public Map getSkipfilePageinData(HttpServletRequest request) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String moId = request.getParameter("moId");
        return performanceService.getMainEngineSkipfilePagein(moId,bundle);
    } 
    
    /**
     * 换页文件传输 发送包
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/skipfilePageout")
    @ResponseBody
    public Map getSkipfilePageoutData(HttpServletRequest request) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String moId = request.getParameter("moId");
        return performanceService.getMainEngineSkipfilePageout(moId,bundle);
       
    } 
    
    @RequestMapping(value = "kpiValueList")
    public void kpiValueList(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String userId=Utils.getCurrentUserId(request);
        String moId=request.getParameter("mo");
        String monitorInstanceId=request.getParameter("monitorInstanceId");
        String kpiId=request.getParameter("kpiId");
        
        String json= performanceService.kpiList(moId,monitorInstanceId,kpiId); 
        //String json=JackJson.fromObjectToJson(map);
        if(json==null)json="";
        writer.write(json);
        writer.flush();
        writer.close();
    }    

    /**
     * 获得kpi信息分类
     * @param request
     * @param writer
     * @throws Exception
     */
    @RequestMapping(value = "getGroups")
    @ResponseBody
    public List getGroups(HttpServletRequest request) throws Exception {
        
       return performanceService.getGroups(request); 
         //String json=JackJson.fromObjectToJson(map);
        /*if(json==null)json="";
        writer.write(json);
        writer.flush();*/
        //return json;

    }
    
    /**
     * 获得kpi信息分类
     * @param request
     * @param writer
     * @throws Exception
     */
    @RequestMapping(value = "getInnderGrid")
    @ResponseBody
    public Map getInnderGrid(HttpServletRequest request) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String userId=Utils.getCurrentUserId(request);
        String groupName=request.getParameter("groupName");
        return performanceService.getInnderGrid(groupName,userId,bundle);
       // String json= performanceService.getInnderGrid(groupName,userId,bundle); 
       /* logger.debug("getInnderGrid"+json);
        if(json==null)json="";
        writer.write(json);
        writer.flush();
*/
    }
    
    
}
