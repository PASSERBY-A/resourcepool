package com.hp.avmon.equipmentCenter.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.deploy.service.DeployService;
import com.hp.avmon.equipmentCenter.service.EquipmentCenterService;
import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.utils.TreeObject;
import com.hp.avmon.utils.Utils;



/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/equipmentCenter/*")
public class EquipmentCenterController {

	private static final Logger logger = LoggerFactory
			.getLogger(EquipmentCenterController.class);

	@Autowired
	private DeployService deployService;
	
	@Autowired
	private EquipmentCenterService equipmentCenterService;
	
	
	@RequestMapping(value = "index")
	public String index(Locale locale, Model model) {

		return "/equipmentCenter/index";

	}

	@RequestMapping(value = "/batchdeploy/index")
	public String batchDeploy(Locale locale, Model model) {

		return "/batchdeploy/index";

	}

	@RequestMapping(value = "moDetail")
	public void getMoDetail(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("mo");
		// moId="201209112222472247";
		Map mo = deployService.getMoDetail(moId, request);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(mo);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "monitorProperty")
	public void getMonitorProperty(HttpServletRequest request,
			PrintWriter writer) throws Exception {

		String moId = request.getParameter("mo");
		String monitorInstanceId = request.getParameter("inst");
		// moId="201209112222472247";
		// monitorInstanceId="I201209251301310";
		Map props = deployService.getMonitorProperty(moId, monitorInstanceId);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(props);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "addMonitor")
	public void addMonitor(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("mo");
		String monitorType = request.getParameter("monitorType");

		Map map = deployService.addMonitor(moId, monitorType);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(map);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "addMo")
	public void addMo(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("id");
		String type = request.getParameter("type");
		String caption = request.getParameter("text");
		String ip = request.getParameter("ip");
		String os = request.getParameter("os");
		String osVersion = request.getParameter("osVersion");
		if (osVersion == null)
			osVersion = "";

		Map map = deployService.addMo(moId, moId, type, caption, ip, os,
				osVersion, false);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(map);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "deleteMonitor")
	public void deleteMonitor(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("mo");
		String instanceId = request.getParameter("instanceId");

		Map map = deployService.deleteMonitor(moId, instanceId);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(map);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "deployMonitor")
	public void deployMonitor(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("mo");
		String instanceIds = request.getParameter("instanceIds");

		Map map = deployService.deployMonitors(moId, instanceIds);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(map);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "batchDeployMonitor")
	public void batchDeployMonitor(HttpServletRequest request,
			PrintWriter writer) throws Exception {

		String items = request.getParameter("items");

		Map map = deployService.batchDeployMonitor(items);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(map);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "monitorTypeList")
	public void monitorTypeList(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		Map map = deployService.getAmpList();
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(map);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "deleteMo")
	public void deleteMo(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("mo");
		// moId="201209112222472247";
		Map mo = deployService.deleteMo(moId);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(mo);

		writer.write(json);
		writer.flush();
		writer.close();
	}
	
	@RequestMapping(value = "bitcodeMo")
	public void bitcodeMo(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("mo");
		
		String text = request.getParameter("text");
//		System.out.println("text : " + text);
		// moId="201209112222472247";
		Map mo = deployService.bitCodeMo(moId);
		
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();

		hints.put(EncodeHintType.CHARACTER_SET, "GBK");

		BitMatrix matrix = null;

		try {

			matrix = new MultiFormatWriter().encode(moId,
					BarcodeFormat.QR_CODE, 300, 300, hints);

		} catch (WriterException e) {

			logger.error(this.getClass().getName()+e.getMessage());

		}
		
		String path = getClass().getClassLoader().getResource("/").getPath().substring(0, getClass().getClassLoader().getResource("/").getPath().length()-17) + "/pages/equipmentCenter/images/"; 
		
		File f = new File(".");

		String absolutePath = f.getAbsolutePath();

		System.out.println("absolutePath  " + absolutePath);
		logger.debug(absolutePath);

		File file = new File(path + text + ".png");
//		file.createNewFile();
		
		System.out.println("new ver...........>>>>>>>");
		try {

			MatrixToImageWriter.writeToFile(matrix, "png", file);

		} catch (IOException e) {
			logger.error(this.getClass().getName()+e.getMessage());
		}

		String json = moId + "," + text;

		writer.write(json);
		writer.flush();
		writer.close();
	}
	
	
	@RequestMapping(value = "checkbitcodeFile")
	public void checkbitcodeFile(HttpServletRequest request, PrintWriter writer)
			throws Exception {
		String mo = request.getParameter("mo");
		
		String path = getClass().getClassLoader().getResource("/").getPath().substring(0, getClass().getClassLoader().getResource("/").getPath().length()-17) + "/pages/equipmentCenter/images/";
		
		File checkFile = new File(path + mo + ".png");
		
		String json = "";
		if(checkFile.exists())
		{
			json = "yes";
		}
		else
		{
			json = "no";
		}
		
		
		writer.write(json);
		writer.flush();
		writer.close();
		
	}
	
	@RequestMapping(value = "getAllbitcodeFile")
	public void getAllbitcodeFile(HttpServletRequest request, PrintWriter writer)
			throws Exception {
		String path = getClass().getClassLoader().getResource("/").getPath().substring(0, getClass().getClassLoader().getResource("/").getPath().length()-17) + "/pages/equipmentCenter/images/";
		
		String json = "";
		
		File file=new File(path);  
		String test[]; 
		test=file.list();  
		for(int i=0;i<test.length;i++) 
		{   
			if(test[i].substring(test[i].length()-3, test[i].length()).equalsIgnoreCase("png"))
			{
				json = json + test[i] + ",";
			}
			
		}
		
		json = json.substring(0, (json.length()-1));
		
//		URLEncoder.encode(json, "utf-8");
		
		writer.write(json);
		
//		writer.write(URLEncoder.encode(json, "utf-8"));
		writer.flush();
		writer.close();
		
	}
	
	@RequestMapping(value = "printAllImg")
	public void printAllImg(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String path = getClass().getClassLoader().getResource("/").getPath().substring(0, getClass().getClassLoader().getResource("/").getPath().length()-17) + "/pages/equipmentCenter/images/";
		
		String json = "";
		
		File file=new File(path);  
		String test[]; 
		test=file.list();
		
		
		for(int i=0;i<test.length;i++) 
		{
			PrintRequestAttributeSet pras =new HashPrintRequestAttributeSet();  
			
			pras.add(new Copies(1));
			//获取打印设备
			PrintService pss[] =
				PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.GIF,pras);
			if (pss.length == 0)
				throw new RuntimeException("No printer services available.");
			PrintService ps = pss[0];
			//获取打印对象
			DocPrintJob job = ps.createPrintJob();
			FileInputStream fin = new FileInputStream(path+test[i]);
			Doc doc =
				new SimpleDoc(
					fin,
					DocFlavor.INPUT_STREAM.GIF,
					null);
			//开始打印图片
			job.print(doc, pras);
			fin.close();
		}

	}

	@RequestMapping(value = "createMo")
	public void createMo(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("mo");
		String moType = request.getParameter("moType");
		String moCaption = request.getParameter("caption");
		String parentId = request.getParameter("parentId");
		String protocol = request.getParameter("protocol");
		String agentId = request.getParameter("agentId");
		String businessType = request.getParameter("businessType");
		
		//businessType = AgentTypes.BIZMAP.get(businessType);
		businessType = this.equipmentCenterService.getBusinessNameById(businessType);
		// moId="201209112222472247";
		Map mo = deployService.createMo(moId, moType, moCaption,parentId,protocol,agentId,businessType,request);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(mo);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "menuTree")
	public void getMenuTree(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String userId = Utils.getCurrentUserId(request);
		Locale locale = request.getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		String others = bundle.getString("others");
		String parentId = request.getParameter("parentId");
		String queryFlag = request.getParameter("queryFlag");
		String json = "{}";
		if ("1".equals(queryFlag)) {
			json = deployService.getMoTreeJsonByName(userId,parentId);
		}else{
			 String businessType = null;
		        
	        if(null!= parentId && parentId.indexOf("*") > 0){
	        	businessType = parentId.split("\\*")[1];
	        }
	        if (null!=businessType&&!"".equals(businessType)) {
	        	//businessType = AgentTypes.BIZMAP.get(businessType);
	        	if("666656".equals(businessType)){
	        		businessType = others;
	        	}else{
	        		businessType = this.equipmentCenterService.getBusinessNameById(businessType);	
	        	}
			}
			if (parentId == null || parentId.equals("")) {
				parentId = "root";
			}else{
				parentId = parentId.split("\\*")[0];
			}
			//String json = deployService.getMoTreeJson(userId, parentId);
			//add by mark start
			json = deployService.getMoTreeJson(userId, parentId,businessType,others);
			//add by mark end
		}
		
//       logger.debug(json);
		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "moTypeTree")
	public void getMoTypeTree(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String parentId = request.getParameter("node");
		if (parentId == null || parentId.equals("")) {
			parentId = "root";
		}
		String json = deployService.getMoTypeTree(parentId);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "saveMoProperty")
	public void saveMoProperty(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("mo");
		String props = request.getParameter("props");
		Map mo = deployService.saveMoProperty(moId, props);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(mo);

		writer.write(json);
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
	@RequestMapping(value = "sysMenuTree")
    public void getSysMenuTree(HttpServletRequest request, PrintWriter writer) throws Exception {
        
        String userId = Utils.getCurrentUserId(request);
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String other = bundle.getString("equipmentOther");
        
        // 根据父节点取得下属节点信息
        List<Map<String,String>> moTreeList = equipmentCenterService.getBusinessTree(userId);
        String id = request.getParameter("id");
        String pid = request.getParameter("pid");
        
        String businessType = request.getParameter("businessType");
        
        if (null!=businessType&&!"".equals(businessType)) {
        	businessType = new String(businessType.getBytes("ISO-8859-1"),"UTF-8");
		}
        
        String json = null;
        if ("businessTree".equals(id)) {
        	int nodeCount = 0;
        	String bizName = null;
        	
        	if(null != moTreeList && moTreeList.size() > 0){
        		for (Map<String, String> map : moTreeList) {
            		bizName = map.get("text");
            		map.put("id", map.get("id"));
            		map.put("pid", "businessTree");
            		map.put("expanded", "false");
            		map.put("iconCls", "icon-alarm-node");
            		//nodeCount = equipmentCenterService.getBusinessNodeCount(bizName);
            		//map.put("text", map.get("text")+"("+nodeCount+")");
            		map.put("text", map.get("text"));
    			}
        	}
        	
        	Map<String, String> otherMap = new HashMap<String, String>();
        	otherMap.put("id", "666656");
        	otherMap.put("pid", "businessTree");
        	otherMap.put("expanded", "false");
        	otherMap.put("iconCls", "icon-alarm-node");
    		//int otherNodeCount = equipmentCenterService.getOtherBusinessNodeCount();
    		//otherMap.put("text", other+"("+otherNodeCount+")");
        	otherMap.put("text", other);
    		moTreeList.add(otherMap);
    		
        	json = JackJson.fromObjectHasDateToJson(moTreeList);
		}else{
			//add by mark start
			json = deployService.getMoTypeTree(userId,id,pid,bundle);
			//add by mark end
		}
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
	

	/**
	 * 转换树形菜单基础数据
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<TreeObject> getTreeObjVo(List<Map> objData){
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
}
