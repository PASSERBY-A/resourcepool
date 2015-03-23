package com.hp.avmon.kpigetconfig.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.config.service.AgentManageService;
import com.hp.avmon.utils.Constants;
import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.utils.TreeObject;
import com.hp.avmon.utils.Utils;
import com.hp.avmonserver.api.AvmonServer;

/**
 * Created with IntelliJ IDEA.
 * User: shiw
 * Date: 3/13/13
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/pages/kpigetconfig/agentManage/*")
public class KpigetconfigAgentController {

    private static final Log logger = LogFactory.getLog(KpigetconfigAgentController.class);

    @Autowired
    private AgentManageService agentManageService;
    @Autowired
    private AvmonServer avmonServer;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 查找Agent Grid信息
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/findAgentGridInfo")
    public void findAgentGridInfo(HttpServletRequest request, PrintWriter writer)  {
        Map map = null;
        try {
            map = agentManageService.findAgentGridInfo(request);
            String jsonData = JackJson.fromObjectToJson(map);
            //logger.debug("agent grid==="+jsonData);
            writer.write(jsonData);
            writer.flush();
            writer.close();
        } catch (Exception e) {
        	logger.error(e);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * 启动Agent
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/startAgent")
    public void startAgent(HttpServletRequest request, PrintWriter writer){
    	String json = "";
        try {
        	json = agentManageService.startAgent(request);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * 删除Agent
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/removeAgent")
    public void removeAgent(HttpServletRequest request, PrintWriter writer){
        String json = "";
        try {
            json = agentManageService.removeAgent(request);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    
    /**
     * 升级Agent
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/upgradeAgent")
    public void upgradeAgent(HttpServletRequest request, PrintWriter writer){
        String json = "";
        try {
            json = agentManageService.upgradeAgent(request);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    
    /**
     * 停止Agent
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/stopAgent")
    public void stopAgent(HttpServletRequest request, PrintWriter writer){
        String status = "";
        try {
            status = agentManageService.stopAgent(request);
            writer.write(status);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * 查找agent 所对应的AMP list
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/findAgentAmp")
    public void findAgentAmp(HttpServletRequest request, PrintWriter writer){
        Map map = null;
        try {
            map = agentManageService.findAgentAmp(request);
            String jsonData = JackJson.fromObjectToJson(map);
            //logger.debug("agent amp list==="+jsonData);
            writer.write(jsonData);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    /**
     * 获取AMP type list数据
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/getAmpTypeComboxValue")
    public void getAmpTypeComboxValue(HttpServletRequest request, PrintWriter writer){
    	String inOs = request.getParameter("os");
    	String os = inOs.split("\\*")[0];
    	Map<String,List<Map<String,String>>> map = new HashMap<String,List<Map<String,String>>>();
    	
    	if(!"null".equals(inOs)){
    		map = agentManageService.getAMPTypeInfo(os);	
    	}
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("amp type list==="+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }

    /**
     * 保存提交AMP数据信息
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/saveAgentAmp")
    public void saveAgentAmp(HttpServletRequest request,PrintWriter writer){
    	 String jsonData = "";
    	 Locale locale = request.getLocale();
         ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
    	try {
    		Map map = agentManageService.saveAgentAmp(request);
            jsonData = JackJson.fromObjectToJson(map);
        } catch (Exception e) {
            e.printStackTrace();  
            logger.error(this.getClass().getName() + "saveAgentAmp: "+e);
            if(e.toString().indexOf("ORA-00001")>-1){
            	jsonData="{success:false,msg:'"+bundle.getString("ampInstanceExisted")+"'}";
            }else{
            	jsonData="{success:false,msg:'"+bundle.getString("systemException")+"'}";
            }
            writer.write(jsonData);
            writer.flush();
            writer.close();
        }
    	writer.write(jsonData);
        writer.flush();
        writer.close();
    	
    }

    /**
     * 获取普通AMP 基本属性数据
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/getNormalAmpAttrList")
    public void getNormalAmpAttrList(HttpServletRequest request,PrintWriter writer){
        Map map = agentManageService.getNormalAmpAttrList(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("getNormalAmpAttrList: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }

    /**
     * 保存AMP属性信息
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/saveNormalAmpAttr")
    public void saveNormalAmpAttr(HttpServletRequest request,PrintWriter writer){
        Map map = agentManageService.saveNormalAmpAttr(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("saveNormalAmpAttr: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }

    /**
     * 卸载AMP信息
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/uinstallNormalAmp")
    public void uinstallNormalAmp(HttpServletRequest request,PrintWriter writer){
        Map map = agentManageService.uinstallNormalAmp(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("saveNormalAmpAttr: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }

    /**
     * 下发脚本（调度+配置一起下发）成功后为AMP状态为停止运行
     * @param request 包含agentAmpInfo一个json数组兼容单AMP与多AMP脚本下发
     * @param writer
     */
    @RequestMapping(value = "/pushAgentAmpScript")
    public void pushAgentAmpScript(HttpServletRequest request,PrintWriter writer){
        Map map = agentManageService.pushAgentAmpScript(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("pushAgentAmpScript: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }
    
    /**
     * 下发调度
     * @param request 
     * @param writer
     */
    @RequestMapping(value = "/pushAgentAmpSchedule")
    public void pushAgentAmpSchedule(HttpServletRequest request,PrintWriter writer){
        Map map = agentManageService.pushAgentAmpSchedule(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("pushAgentAmpSchedule: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }
    
    /**
     * 下发调度
     * @param request 
     * @param writer
     */
    @RequestMapping(value = "/batchPushAgentAmpSchedule")
    public void batchPushAgentAmpSchedule(HttpServletRequest request,PrintWriter writer){
        Map map = agentManageService.batchPushAgentAmpSchedule(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("pushAgentAmpSchedule: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }

    /**
     * 下发AMP配置
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/pushAgentAmpConfig")
    public void pushAgentAmpConfig(HttpServletRequest request,PrintWriter writer){
        Map map = agentManageService.pushAgentAmpConfig(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("pushAgentAmpConfig: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }

    
    @RequestMapping(value = "/pushAmpSchedule")
    public void pushAmpSchedule(HttpServletRequest request,PrintWriter writer){
        Map map = agentManageService.pushAmpSchedule(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("findAgentSchedue: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }

    /**
     *  AMP调度下发，支持多AMP实例同时下发
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/issuedAmpSchedule")
    public void findIssuedAmpSchedule(HttpServletRequest request,PrintWriter writer){
        Map map = agentManageService.findIssuedAmpSchedule(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("findAgentSchedue: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }

    @RequestMapping(value = "/pushIloHostSchedule")
    public void pushIloHostSchedule(HttpServletRequest request,PrintWriter writer){
        Map map = agentManageService.pushIloHostSchedule(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("pushIloHostSchedule: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "/pushSelectedIloHostSchedule")
    public void pushSelectedIloHostSchedule(HttpServletRequest request,PrintWriter writer){
        Map map = agentManageService.pushSelectedIloHostSchedule(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("pushIloHostSchedule: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }
    
    /**
     * 保存修改策略信息
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/saveAgentSchedue")
    public void saveAgentSchedue(HttpServletRequest request,PrintWriter writer){
        Map map = agentManageService.saveAgentSchedue(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("saveAgentSchedue: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }

    /**
     * 下发AMP脚本和调度
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/pushAgentAmp")
    public void pushAgentAmp(HttpServletRequest request,PrintWriter writer) {
          //TODO
    }

    /**
     * 停止普通AMP实例
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/pauseNormalAmp")
    public void pauseNormalAmp(HttpServletRequest request,PrintWriter writer){
        Map map = agentManageService.pauseNormalAmp(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("pauseNormalAmp: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "/removeAmp")
    public void removeAmp(HttpServletRequest request,PrintWriter writer){
        Map map = agentManageService.removeAmp(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("removeAmp: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }

    /**
     * 启动普通AMP实例
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/startNormalAmp")
    public void startNormalAmp(HttpServletRequest request, PrintWriter writer){
        Map map = agentManageService.startNormalAmp(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("startNormalAmp: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }

    /**
     * 获取AMP调度策略串
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/getNormalAmpSchedule")
    public void getNormalAmpSchedule(HttpServletRequest request, PrintWriter writer){
        Map map = agentManageService.getNormalAmpSchedule(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("getNormalAmpSchedule: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }
    
    /**
     * 查找AMP attr基本配置grid信息
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/findAmpInstAttrGridInfo")
    public void findAmpInstAttrGridInfo(HttpServletRequest request, PrintWriter writer){
    	List<Map<String,Object>> l = null;
        try {
            l = agentManageService.findAmpInstAttrGridInfo(request);
            
            Map<String, Object> map = new HashMap<String, Object>();
//            for(Map<String, Object> m : l){
//            	map.put(m.get("name").toString(), m.get("value")==null?"":m.get("value"));
//            }
            map.put("root", l);
            String jsonData = JackJson.fromObjectToJson(map);
            logger.debug("agent attr list==="+jsonData);
            writer.write(jsonData);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    
    /**
     * 保存amp 基本配置grid修改的信息    
     * @param request
     * @param writer
     * @throws Exception
     */
    @RequestMapping(value = "saveAmpInstAttr")
    public void saveAmpInstAttr(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
    	String data = "";
        try{
        	String agentId=request.getParameter("agentId");
            String ampInstId=request.getParameter("ampInstId");
            String attrs=request.getParameter("attrs");
            agentManageService.saveAmpInstAttr(agentId,ampInstId,attrs);
            
            data="{success:true,msg:'保存成功!'}";
            
            
        }catch(Exception e){
        	e.printStackTrace();
        	logger.error(this.getClass().getName()+" saveAmpInstAttr",e);
        }
        String json=JackJson.fromObjectToJson(data);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    /**
     * 获取虚拟主机tab页面tree grid的数据信息
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("findVmTreeStore")
    public String findVmTreeStore(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String isChecked = request.getParameter("isChecked");
			Boolean checked = false;
			if("true".equals(isChecked)){
				checked = true;
			}
			
			String agentId = request.getParameter("agentId");
			String ampInstId = request.getParameter("ampInstId");
			
			if("".equals(agentId)||"".equals(ampInstId)){
				return null;
			}
			
			List<Map<String, Object>> list = findVmList(agentId,ampInstId);
			
			json = ganerateTreeJson(list,checked);
			json = json.replace("\"children\":[]", "leaf:true");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" findVmTreeStore",e);
		}
		
		return Utils.responsePrintWrite(response,json,null);
	}
    
    /**
     * 根据agentId ampInstId查询所有虚拟主机
     * @param agentId
     * @param ampInstId
     * @return
     */
    private List<Map<String, Object>> findVmList(String agentId,String ampInstId){
    	String sql = "select obj_id as \"id\",obj_name as \"text\",obj_parent as \"pid\",obj_type as \"objType\"," +
				"hostname as \"hostName\",host_status as \"hostStatus\",enable_flag as \"enableFlag\",'2' as \"objStatus\" " +
				"from td_avmon_amp_vm_host " +
				"where agent_id='%s' and amp_inst_id='%s' AND OBJ_TYPE IN ('HostSystem','VirtualMachine','Datacenter')" +
				"order by obj_id desc ";
		sql = String.format(sql, agentId, ampInstId);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
    }
    
    private String ganerateTreeJson(List<Map<String, Object>> list,Boolean isCheck){
		Gson gson = new Gson();
		if(!isCheck){
			gson =new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		}
		
		List<TreeObject> treeList = new ArrayList<TreeObject>();
		
		// 填充到树形结构中
		TreeObject obj = new TreeObject();
		for(Map<String, Object> temp : list) {
			obj = new TreeObject();
			
			obj.setText(MyFunc.nullToString(temp.get("text")));
			obj.setId(MyFunc.nullToString(temp.get("id")));
			obj.setPid(MyFunc.nullToString(temp.get("pid")));
			obj.setExpanded("true");
			obj.setChecked(false);
			
			obj.setHostName(MyFunc.nullToString(temp.get("hostName")));
			obj.setHostStatus(MyFunc.nullToString(temp.get("hostStatus")));
			obj.setEnableFlag(MyFunc.nullToString(temp.get("enableFlag")));
			
			obj.setObjStatus(MyFunc.nullToString(temp.get("objStatus")));
			
			//设置不同图标
			String objType = MyFunc.nullToString(temp.get("objType"));
			setIcon(obj,objType);
			
			treeList.add(obj);
		}
		
		TreeObject tree = new TreeObject();
		// 构造监控对象树形菜单
		structureChildNode(tree, treeList, "-1");
		
		// 对树形结构进行遍历并构造树形json数据
		String objTreeJson = gson.toJson(tree);
		
		return objTreeJson;
	}
	
	private void structureChildNode(TreeObject tree, List<TreeObject> dataList, String stid) {
		
		for(int i=0; i < dataList.size(); i++) {
			
			if(stid.equals(dataList.get(i).getPid())) {
				structureChildNode(dataList.get(i), dataList, dataList.get(i).getId());
				tree.setChild(dataList.get(i));
			}
		}
	}
	
	private Map<String, TreeObject> ganerateVmMap(List<Map<String, Object>> list,String flag){
		Map<String, TreeObject> map = new HashMap<String, TreeObject>();
		
		TreeObject obj = new TreeObject();
		for(Map<String, Object> temp : list) {
			obj = new TreeObject();
			
			obj.setText(MyFunc.nullToString(temp.get("text")));
			obj.setId(MyFunc.nullToString(temp.get("id")));
			obj.setPid(MyFunc.nullToString(temp.get("pid")));
			obj.setExpanded("true");
			obj.setChecked(false);
			
			obj.setHostName(MyFunc.nullToString(temp.get("hostName")));
			obj.setHostStatus(MyFunc.nullToString(temp.get("hostStatus")));
			obj.setEnableFlag(MyFunc.nullToString(temp.get("enableFlag")));
			
			if("old".equalsIgnoreCase(flag)){
				obj.setObjStatus("2");
			}
			
			//设置不同图标
			String objType = MyFunc.nullToString(temp.get("objType"));
			obj.setObjType(objType);
			setIcon(obj,objType);
			
			map.put(obj.getId(), obj);
		}
		return map;
	}
	
	private TreeObject setIcon(TreeObject obj,String objType){
		//设置不同图标
		if(Constants.HostSystem.equalsIgnoreCase(objType)){
			obj.setIcon("../../../resources/images/vm/icon_ESX_Hosts.png");
    	}else if(Constants.Datacenter.equalsIgnoreCase(objType)){
			obj.setIcon("../../../resources/images/vm/icon_Datacenters.png");
		}else if(Constants.VirtualMachine.equalsIgnoreCase(objType)){
			obj.setIcon("../../../resources/images/vm/icon_Virtual_Machines.png");
		}else if(Constants.DataStore.equalsIgnoreCase(objType)){
			obj.setIcon("../../../resources/images/vm/icon_Datastores.png");
		}else if(Constants.ResourcePool.equalsIgnoreCase(objType)){
			obj.setIcon("../../../resources/images/vm/icon_Resource_Pools.png");
		}else if(Constants.Network.equalsIgnoreCase(objType)){
			obj.setIcon("../../../resources/images/vm/icon_Network.png");
		}else{
			obj.setIcon("../../../resources/images/vm/icon_Network.png");
		}
		return obj;
	}
	
	//调用接口获取最新vm数据后刷新数据
	/**
	 * 虚拟主机列表“刷新虚拟主机列表”按钮调用的方法
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("refreshVmTree")
    public String refreshVmTree(
			HttpServletResponse response,
			HttpServletRequest request){
		Gson gson = new Gson();
		String json = "";
		try {
			//1、获取数据库中的vmlist
			String agentId = request.getParameter("agentId");
			String ampInstId = request.getParameter("ampInstId");
			List<Map<String, Object>> list = findVmList(agentId,ampInstId);
			Map<String, TreeObject> oldVmMap = ganerateVmMap(list,"old");
			
			//2、调用接口获取新的vmList
//			String returnString = "[{id:'datacenter-2',name:'8F-TSLab',parent:'-1',type:'Datacenter'}," + 
//					"{vm:'vm-3264,vm-3272,vm-3278,vm-3306',connectionState:'CONNECTED',cpuMhz:'3000',memorySize:8585687040,vendor:'HP',parent:'datacenter-2',numCpuCores:8,type:'HostSystem',datastore:'datastore-901,datastore-3024',cpuModel:'Intel(R) Xeon(R) CPU E5450  @3.00GHz',network:'network-727',id:'host-898',numHBAs:3,bootTime:'2012-02-15T11:12:57.542926Z',powerState:'POWERED_ON',name:'16.157.88.222',numNics:4},"+
//					"{host:'host-722',memorySize:2048,cpuReservation:0,parent:'datacenter-2',numVirtualDisks:0,type:'VirtualMachine',datastore:'datastore-3024',resourcePool:'resgroup-394',id:'vm-2744',guestFullName:'Microsoft Windows Server 2003, Enterprise Edition (32-bit)',name:'FastTest-ZhaoXinJie-90.44',numEthernetCards:1,guestId:'winNetEnterpriseGuest',numCpu:2},"+
//					"{freeSpace:360073658368,id:'datastore-725',vm:'',name:'BAY4:storage1',capacity:432181084160,parent:'datacenter-2',type:'Network',url:'sanfs://vmfs_uuid:4b50a050-9b7efc44-b0ae-00215a4d62e6/'},"+
//					"{id:'resgroup-394',vm:'vm-1030,vm-1714,vm-2744,vm-2847,vm-2930,vm-2932,vm-2934,vm-3203,vm-3256,vm-3262,vm-3264,vm-3272,vm-3276,vm-3278,vm-3280,vm-3282,vm-3284,vm-3286,vm-3306,vm-3308,vm-3362,vm-3666,vm-3715,vm-3717,vm-3769,vm-3822,vm-4226,vm-695',name:'',parent:'datacenter-2',type:'ResourcePool',resourcePool:''},"+
//					"{id:'network-727',vm:'vm-1714,vm-2744,vm-2847,vm-2930,vm-2932,vm-2934,vm-3203,vm-3252,vm-3256,vm-3262,vm-3264,vm-3272,vm-3276,vm-3278,vm-3280,vm-3282,vm-3284,vm-3286,vm-3304,vm-3306,vm-3308,vm-3362,vm-3666,vm-3715,vm-3717,vm-3769,vm-3822,vm-4226',host:'host-722,host-898,host-1094,host-1099,host-1109',name:'VM Network',parent:'datacenter-2',type:'Network'}]";
			String returnString = avmonServer.queryVMHostList(agentId,ampInstId);
			
			if(returnString==null){
				return Utils.responsePrintWrite(response,"{success:'false'}",null);
			}
			
			JSONArray returnVmHosts = JSONArray.fromObject(returnString);
			
			List<Map<String, Object>> newVMList = new ArrayList();
			
			for(int i=0;i<returnVmHosts.size();i++){
				JSONObject obj = returnVmHosts.getJSONObject(i);
				
				//filter the network/datastore/group
				if(!Constants.VirtualMachine.equals(obj.get("type"))&& !Constants.HostSystem.equals(obj.get("type"))&& !Constants.Datacenter.equals(obj.get("type"))){
					continue;
				}

				String text = "";
				if(obj.get("name")!=null&&obj.get("name")!=""){
					text = obj.get("name").toString();
				}else{
					text = obj.get("id").toString();
				}
				Map<String,Object> m1 = new HashMap();
				m1.put("id", obj.get("id"));//obj_id
				m1.put("text", text);//obj_name
				m1.put("pid", obj.get("parent"));//obj_parent

				m1.put("objType", obj.get("type"));//obj_type
				m1.put("enableFlag", "0");//endable_flag
				if(Constants.VirtualMachine.equalsIgnoreCase(obj.get("type").toString())){
					m1.put("hostIp", obj.get("ip"));//ip
					m1.put("hostName", obj.get("host"));//host_name->runtime.host
					m1.put("hostStatus", "3");//host_status
					m1.put("pid", obj.get("runtime.host"));//obj_parent
				}
				newVMList.add(m1);
			}
			
			Map<String, TreeObject> newVmMap = ganerateVmMap(newVMList,"new");
			
			Set<String> oldSet = oldVmMap.keySet();
			Set<String> newSet = newVmMap.keySet();
			
			for (String oldId : oldSet) {
				if(!newVmMap.containsKey(oldId)){//若不包含说明被删除了
					TreeObject delObj = oldVmMap.get(oldId);
					delObj.setObjStatus("0");
					oldVmMap.put(oldId, delObj);
				}
			}
			
			for (String newId : newSet) {
				if(!oldVmMap.containsKey(newId)){//若不包含说明新增了
					TreeObject newObj = newVmMap.get(newId);
					newObj.setObjStatus("1");
					oldVmMap.put(newId, newObj);
					//同时保存数据库
					agentManageService.saveVmHost(newObj,agentId,ampInstId);
				}
			}
			
			List<TreeObject> newList = new ArrayList(); 
			Set<String> newObjSet = oldVmMap.keySet();
			for (String id : newObjSet) {
				newList.add(oldVmMap.get(id));
			}
			
			TreeObject tree = new TreeObject();
			// 构造监控对象树形菜单
			structureChildNode(tree, newList, "-1");
			
			// 对树形结构进行遍历并构造树形json数据
			json = gson.toJson(tree);
			
			json = json.replace("\"children\":[]", "leaf:true");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" refreshVmTree",e);
		}
		
		return Utils.responsePrintWrite(response,json,null);
		
	}
	
//	/**
//	 * 虚拟主机列表"保存并下发采集调度"按钮调用的方法
//	 * @param response
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("saveVmSchedule")
//	public String saveVmSchedule(
//			HttpServletResponse response,
//			HttpServletRequest request){
//		String data = "";
//		Locale locale = request.getLocale();
//        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
//		try {
//			String agentId = request.getParameter("agentId");
//			String ampInstId = request.getParameter("ampInstId");
//			String schedule = request.getParameter("schedule");
//			Map map = agentManageService.saveVmSchedule(agentId,ampInstId,schedule);
//			//data="{success:true,msg:'保存成功!'}";
//			data = JackJson.fromObjectToJson(map);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(this.getClass().getName()+" saveVmSchedule",e);
//			data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
//		}
//		
//		return Utils.responsePrintWrite(response,data,null);
//	}
	
	/**
	 * 虚拟主机列表"启动监控"\"停止监控"按钮调用的方法
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("updateVmMonitorStatus")
	public String updateVmMonitorStatus(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String agentId = request.getParameter("agentId");
			String ampInstId = request.getParameter("ampInstId");
			String flag = request.getParameter("flag");
			String objIds = request.getParameter("objIds");
			agentManageService.updateVmMonitorStatus(agentId,ampInstId,flag,objIds);
			if("start".equalsIgnoreCase(flag)){
				data="{success:true,msg:'"+bundle.getString("startSuccess")+"'}";
			}else{
				data="{success:true,msg:'"+bundle.getString("stopSuccess")+"'}";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" updateVmMonitorStatus",e);
			if(e.toString().indexOf("license count error!")>-1){
				data="{success:false,msg:'"+bundle.getString("monitoringObjectHasReachedTheMaximumLimit")+"'}";
			}else if(e.toString().indexOf("接口调用失败!")>-1){
				data="{success:false,msg:'"+bundle.getString("interfaceCallFail")+"'}";
			}else{
				data="{success:false,msg:'"+bundle.getString("operationFail")+"'}";
			}
			return Utils.responsePrintWrite(response,data,null);
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	/**
	 * 虚拟主机列表"移除"按钮调用的方法
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("deleteVmHost")
	public String deleteVmHost(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String agentId = request.getParameter("agentId");
			String ampInstId = request.getParameter("ampInstId");
			String objIds = request.getParameter("objIds");
			agentManageService.deleteVmHost(agentId,ampInstId,objIds);
			data="{success:true,msg:'"+bundle.getString("removeSuccess")+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" deleteVmHost",e);
			data="{success:false,msg:'"+bundle.getString("removeFail")+"'}";
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	@RequestMapping(value = "/findAmpInstPolicyGridInfo")
    public void findAmpInstPolicyGridInfo(HttpServletRequest request, PrintWriter writer)  {
        Map map = null;
        try {
            map = agentManageService.findAmpInstPolicyGridInfo(request);
            String jsonData = JackJson.fromObjectToJson(map);
            logger.debug("amp policy grid==="+jsonData);
            writer.write(jsonData);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();  
            logger.error(this.getClass().getName()+" findAmpInstPolicyGridInfo",e);
        }
    }
	
	@RequestMapping(value = "/findIloHostGridInfo")
    public void findIloHostGridInfo(HttpServletRequest request, PrintWriter writer)  {
        Map map = null;
        try {
            map = agentManageService.findIloHostGridInfo(request);
            String jsonData = JackJson.fromObjectToJson(map);
            logger.debug("ilo host grid==="+jsonData);
            writer.write(jsonData);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace(); 
            logger.error(this.getClass().getName()+" findIloHostGridInfo",e);
        }
    }
	
	@RequestMapping("saveIloHost")
	public String saveIloHost(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			data = agentManageService.saveIloHost(request);
			//data="{success:true,msg:" + msg + "}";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" saveIloHost",e);
			data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
	
	/**
     * 保存修改策略信息
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/saveIloSchedule")
    public void saveIloSchedule(HttpServletRequest request,PrintWriter writer){
        Map map = agentManageService.saveIloSchedule(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("saveIloSchedue: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping("deleteIloHostByIps")
	public String deleteIloHostByIps(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String agentId = request.getParameter("agentId");
			String ampInstId = request.getParameter("ampInstId");
			String ips = request.getParameter("ips");
			agentManageService.deleteIloHostByIps(agentId,ampInstId,ips);
			data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" deleteIloHostByIps",e);
			data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
    
    // modify by mark start 
    /**
     * 初始化添加agentwindows
     * (添加agent启动状态参数传递)
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("addAgentMo")
	public String addAgentMo(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		try {
			String agentId = request.getParameter("agentId");
			String moId = request.getParameter("moId");
			String result = agentManageService.addAgentMo(moId,agentId);
			if(result.equalsIgnoreCase("99")){
				result = bundle.getString("unknownError");
			}
			if(result.startsWith("00")){
				//更新agent表
				agentManageService.updateAgentMoId(agentId,moId);
				data="{success:true,msg:'"+bundle.getString("operationSuccess")+"'}";
			}else if("1".endsWith(result)){
				data="{success:false,msg:'"+bundle.getString("operationSuccess")+"'}";
			}else if(result.startsWith("03")){
				//更新agent表
				agentManageService.updateAgentMoId(agentId,moId);
				data="{success:true,msg:'"+bundle.getString("operationSuccess")+"'}";
			}else{
				data="{success:false,msg:'" + result + "'}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" addAgentMo",e);
			String s=e.getMessage();
			s=s.replaceAll("'", "");
			data="{success:false,msg:'"+bundle.getString("agentAddMonitorObjectFail")+"'}";
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
    // modify by mark end
}
