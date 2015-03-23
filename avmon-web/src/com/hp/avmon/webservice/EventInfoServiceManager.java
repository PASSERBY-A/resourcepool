package com.hp.avmon.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.remoting.jaxrpc.ServletEndpointSupport;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.hp.avmon.alarm.service.AlarmService;
import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.common.jackjson.JackJson;

@SuppressWarnings("deprecation")
@Service
public class EventInfoServiceManager extends ServletEndpointSupport {
	
	private static final Log log = LogFactory.getLog(EventInfoServiceManager.class);
	
    @Autowired
    private AlarmService alarmService=null;
	
	public Map<String, Object> closeAlarm(Map<String, String> params)  {
	    Map<String, Object> retMap=new HashMap();
        //content为关闭时的处理意见
        String content = params.get("content");
        //contentType为意见类型，目前可选项为：知识、意见
        String contentType = "知识";
        String userId = "admin";

        //alarmIdList为关闭的id，以逗号分割，比如aaa,bbb,ccc
        String alarmId = params.get("alarmId");
        System.out.format("Process Tool Close Alarm (id=%s) through webservice.",alarmId);
        @SuppressWarnings("rawtypes")
        Map map=null;
        try{
            if(alarmService==null){
                alarmService=SpringContextHolder.getBean("alarmService");
            }
            map = alarmService.close(alarmId, userId, content, contentType);
        }catch(Exception e){
            map=new HashMap();
            map.put("result", "fault");
            map.put("errorMsg", e.getMessage());
            e.printStackTrace();
        }
        return map;
	}
}
