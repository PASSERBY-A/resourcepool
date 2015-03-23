package com.hp.xo.resourcepool.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hp.xo.resourcepool.exception.BuessionException;
import com.hp.xo.resourcepool.model.Response;
import com.hp.xo.resourcepool.model.WorkOrderJob;
import com.hp.xo.resourcepool.schedule.service.impl.SAAsyncHelper;
import com.hp.xo.resourcepool.schedule.service.impl.SACapabilityHelper;
import com.hp.xo.resourcepool.service.impl.GenericCloudServerManagerImpl;
import com.hp.xo.resourcepool.service.impl.WorkOrderJobManagerImpl;
import com.hp.xo.resourcepool.utils.ServiceOptionUtil;

@Service(value = "saSchedule")
@Transactional(propagation=Propagation.REQUIRED)
public class SASchedule {
	private final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private GenericCloudServerManagerImpl genericCloudServerManager;
	@Autowired
	private SAAsyncHelper saAsyncHelper;
	@Autowired
	private SACapabilityHelper saCapabilityHelper;
	
	@Autowired
	private WorkOrderJobManagerImpl workOrderJobManager;
	
	public void work() {
		//System.out.println("-----------------------------------服务开通  begin--------------------------------------------------------");
		active();	
		System.out.println("-----------------------------------服务开通  end--------------------------------------------------------");
	}
	public void expire() throws BuessionException {
		saAsyncHelper.expire(getCloudStackParams());
	}
	private Response loginCloudStack() {
		Map<String, Object[]> param = new HashMap<String, Object[]>();
		param.put("command", new Object[] { "login" });
		param.put("username",new Object[] { ServiceOptionUtil.obtainCloudStackUsername() });
		param.put("password",new Object[] { ServiceOptionUtil.obtainCloudStackPassword() });
		param.put("response", new Object[] { "json" });
		Response loginResponse = genericCloudServerManager.post(param);
		return loginResponse;

	}
	
	public void active() {
		Response loginResponse = loginCloudStack();
		Map<String, Object[]> param = new HashMap<String, Object[]>();
		if(log.isDebugEnabled()){
			log.debug(loginResponse.toString());
		}
		JSONObject jo = JSONObject.fromObject(loginResponse.getResponseString());
		String userId = "";
		String sessionkey = "";
		try {
			jo = JSONObject.fromObject(jo.getString("loginresponse"));
			userId = jo.getString("userid");
			sessionkey = jo.getString("sessionkey");
		} catch (JSONException e) {
			log.error("login info is error, "+ loginResponse.getResponseString());
			return;
		}

		Map<String, Object[]> listUsersParams = new HashMap<String, Object[]>();
		listUsersParams.put("command", new Object[] { "listUsers" });
		listUsersParams.put("response", new Object[] { "json" });
		listUsersParams.put("id", new Object[] { userId });

		Response listUsersResponse = genericCloudServerManager.get(listUsersParams, false);

		String apikey = "";
		String secretkey = "";
		if (StringUtils.isNotBlank(listUsersResponse.getResponseString())) {
			jo = JSONObject.fromObject(listUsersResponse.getResponseString());
			jo = JSONObject.fromObject(jo.getString("listusersresponse"));
			JSONArray jos = jo.getJSONArray("user");
			jo = JSONObject.fromObject(jos.get(0));
			apikey = jo.getString("apikey");
			secretkey = jo.getString("secretkey");
		}
		Map<String, Object[]> cloudStackParams = new HashMap<String, Object[]>();
		cloudStackParams.put("apikey", new Object[] { apikey });
		cloudStackParams.put("secretkey", new Object[] { secretkey });
		cloudStackParams.put("sessionkey", new Object[] { sessionkey });
		cloudStackParams.put("response", new Object[] { "json" });

		saCapabilityHelper.active(cloudStackParams);
		saAsyncHelper.active(cloudStackParams);

	}
	public Map<String, Object[]> getCloudStackParams(){
		Response loginResponse = loginCloudStack();
		if(log.isDebugEnabled()){
			log.debug(loginResponse.toString());
		}
		JSONObject jo = JSONObject.fromObject(loginResponse.getResponseString());
		String userId = "";
		String sessionkey = "";
		try {
			jo = JSONObject.fromObject(jo.getString("loginresponse"));
			userId = jo.getString("userid");
			sessionkey = jo.getString("sessionkey");
		} catch (JSONException e) {
			log.error("login info is error, "+ loginResponse.getResponseString());
			return null;
		}

		Map<String, Object[]> listUsersParams = new HashMap<String, Object[]>();
		listUsersParams.put("command", new Object[] { "listUsers" });
		listUsersParams.put("response", new Object[] { "json" });
		listUsersParams.put("id", new Object[] { userId });

		Response listUsersResponse = genericCloudServerManager.get(listUsersParams, false);

		String apikey = "";
		String secretkey = "";
		if (StringUtils.isNotBlank(listUsersResponse.getResponseString())) {
			jo = JSONObject.fromObject(listUsersResponse.getResponseString());
			jo = JSONObject.fromObject(jo.getString("listusersresponse"));
			JSONArray jos = jo.getJSONArray("user");
			jo = JSONObject.fromObject(jos.get(0));
			apikey = jo.getString("apikey");
			secretkey = jo.getString("secretkey");
		}
		Map<String, Object[]> cloudStackParams = new HashMap<String, Object[]>();
		cloudStackParams.put("apikey", new Object[] { apikey });
		cloudStackParams.put("secretkey", new Object[] { secretkey });
		cloudStackParams.put("sessionkey", new Object[] { sessionkey });
		cloudStackParams.put("response", new Object[] { "json" });
		return cloudStackParams;
	}
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-service.xml");
		SASchedule saSchedule = (SASchedule) applicationContext.getBean("saSchedule");
		saSchedule.active();
	}
	public GenericCloudServerManagerImpl getGenericCloudServerManager() {
		return genericCloudServerManager;
	}
	public void setGenericCloudServerManager(
			GenericCloudServerManagerImpl genericCloudServerManager) {
		this.genericCloudServerManager = genericCloudServerManager;
	}
	public SAAsyncHelper getSaAsyncHelper() {
		return saAsyncHelper;
	}
	public void setSaAsyncHelper(SAAsyncHelper saAsyncHelper) {
		this.saAsyncHelper = saAsyncHelper;
	}
	public SACapabilityHelper getSaCapabilityHelper() {
		return saCapabilityHelper;
	}
	public void setSaCapabilityHelper(SACapabilityHelper saCapabilityHelper) {
		this.saCapabilityHelper = saCapabilityHelper;
	}
	public WorkOrderJobManagerImpl getWorkOrderJobManager() {
		return workOrderJobManager;
	}
	public void setWorkOrderJobManager(WorkOrderJobManagerImpl workOrderJobManager) {
		this.workOrderJobManager = workOrderJobManager;
	}

	
}
