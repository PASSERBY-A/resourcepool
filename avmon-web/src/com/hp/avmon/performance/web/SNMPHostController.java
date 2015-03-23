/**
 * 
 */
package com.hp.avmon.performance.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.avmon.performance.service.SnmpHostService;


/**
 * @author qinjie
 *
 */
@Controller
@RequestMapping("/pages/dashboard/snmpHost/*")
public class SNMPHostController {
	
	
	private static final Log logger = LogFactory.getLog(SNMPHostController.class);
	
	@Autowired
    private SnmpHostService snmpHostService;
	

	@RequestMapping(value = "/getSnmpHostBasicInfo")
	@ResponseBody
    public Map getSnmpHostBasicInfo(HttpServletRequest request) throws Exception {
       return snmpHostService.getSnmpBasicInfo(request);

    } 
	
	//右边栏Partition
	@RequestMapping(value = "/getSnmpPartitiontList")
	@ResponseBody
    public List getSnmpPartitiontList(HttpServletRequest request) throws Exception {

        String moId = request.getParameter("moId");
        return snmpHostService.getSnmpPartitionList(moId);

    } 
	
	//右边栏File
	@RequestMapping(value = "/getSnmpFileList")
	@ResponseBody
    public List getSnmpFileList(HttpServletRequest request) throws Exception {

        String moId = request.getParameter("moId");
        return snmpHostService.getSnmpFileList(moId);

    } 
	//右边栏Storage
	@RequestMapping(value = "/getSnmpStorageList")
	@ResponseBody
    public List getSnmpStorageList(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        return snmpHostService.getSnmpStorageList(moId);

    } 
	//右边栏Device
	@RequestMapping(value = "/getSnmpDeviceList")
	@ResponseBody
	public List getSnmpDeviceList(HttpServletRequest request) throws Exception {
	        String moId = request.getParameter("moId");
	        return snmpHostService.getSnmpDeviceList(moId);
	} 
	
	//右边栏正在运行软件
	@RequestMapping(value = "/getSnmpRunningSoftwareList")
	@ResponseBody
	public List getSnmpRunningSoftwareList(HttpServletRequest request) throws Exception {
	        String moId = request.getParameter("moId");
	        return snmpHostService.getSnmpDeviceList(moId);
	} 

	//右边栏安装运行软件
	@RequestMapping(value = "/getSnmpInstalledSoftwareList")
	@ResponseBody
	public List getSnmpInstalledSoftwareList(HttpServletRequest request) throws Exception {
		        String moId = request.getParameter("moId");
		        return snmpHostService.getSnmpInstalledSoftwareList(moId);
	} 
	

}
