package com.hp.avmon.alarm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class ProcessToolWebService {
	
    private static final Log logger = LogFactory.getLog(ProcessToolWebService.class);
    
    private String wsUrl="ipaddress";
    private String wsMethod="method";
    
	public String getWsUrl() {
        return wsUrl;
    }

    public void setWsUrl(String wsUrl) {
        this.wsUrl = wsUrl;
    }

    public String getWsMethod() {
        return wsMethod;
    }

    public void setWsMethod(String wsMethod) {
        this.wsMethod = wsMethod;
    }

    /**
	 * @param ciType 默认process
	 * @param hostName 监控主机IP
	 * @param monitorObject 监控对象，目前为监控的文件名或文件名全路径
	 * @param processNewStatus 正常|繁忙|僵死|不存在
	 * @param processNewUpdateTime yyyy-MM-dd hh:mm:ss
	 * @param processNewSize 进程配置文件大小
	 * @return 方法返回值类型为Map，若key为"RETURN"的值为"SUCCESS"则代表调用webservice成功，否则标示失败，失败的信息为key="ERROR_MSG"对应的value值
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, String> sendAlarm(Map paramMap){
    	// modify by mark start 2014-1-16 修改告警前传无提示信息的bug
		Map<String, String> retMap = new HashMap<String, String>();
		// modify by mark end 2014-1-16
//		paramMap.put("ciType", ciType);
//		paramMap.put("hostName", hostIp);
//		paramMap.put("monitorObject", processObject);
//		paramMap.put("processNewStatus", processNewStatus);
//		paramMap.put("processNewUpdateTime", processNewUpdateTime);
//		paramMap.put("processNewSize", processNewSize);
		try {
			logger.warn(">>>>>>>>web service send map:"+paramMap);
			long _cnttime1 = System.currentTimeMillis();
			Service service = new Service();
			service.createCall();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(wsUrl);
			call.setOperationName(wsMethod);
			retMap = (Map<String,String>) call.invoke(new Object[] {paramMap});
			long _cnttime2 = System.currentTimeMillis();
			logger.warn(">>execute: ProcessToolWebService.sendAlarm:"+(_cnttime2 - _cnttime1)+"ms");
			logger.warn(">>>>>> CI returnmap:"+retMap+"\n");
		} catch (Exception e) {
			
			logger.error(">>> CI webservice error:"+e.getMessage());
			// add by mark start 2014-1-16
			retMap.put("RETURN", "FAILURE");
			// add by mark end 2014-1-16
		}
		return retMap;
	}
	
//	public static void main(String[] args) {
//		//Map<String, String> retMap = sendAlarm("process", "cvdevux1", "/tmp/hp/avmon/commands/cmd_filestatus.sh", "正常", "2012-04-09 11:55:01", "0");
//		//System.out.println(retMap);
//	}
}
