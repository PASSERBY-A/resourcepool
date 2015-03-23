package com.hp.xo.resourcepool.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.xo.resourcepool.model.ResourcePool;
import com.hp.xo.resourcepool.model.Response;
import com.hp.xo.resourcepool.service.ResourcePoolManager;


@Service(value = "resourcePoolManager")
public class ResourcePoolManagerImpl extends
		GenericManagerImpl<ResourcePool, Integer> implements
		ResourcePoolManager {

	@Autowired
	private GenericCloudServerManagerImpl genericCloudServerManager;

	public void setGenericCloudServerManager(
			GenericCloudServerManagerImpl genericCloudServerManager) {
		this.genericCloudServerManager = genericCloudServerManager;
	}


	public ResourcePoolManagerImpl() {
		super();
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
	public String computeResource(Map<String, Object[]> cloudStackParam, String resourcePoolId) {
		
		
		Map<String, Object[]> newMap = new HashMap<String, Object[]>();
		newMap.putAll(cloudStackParam);
		// 查询ZONES
		newMap.put("command", new Object[] { "listZones" });
		Map<String,JSONObject> finalMap = new HashMap<String,JSONObject>();
		
			cloudStackParam.put("command", new Object[] { "listCapacity" });
			
			Response response = genericCloudServerManager.get(cloudStackParam);
			if (response != null
					&& StringUtils.isNotBlank(response.getResponseString())) {
				JSONObject jsonObj = JSONObject.fromObject(response
						.getResponseString());
				if (jsonObj.containsKey("listcapacityresponse")) {
					jsonObj = JSONObject.fromObject(jsonObj.get(
							"listcapacityresponse").toString());
					if (jsonObj.containsKey("capacity")) {
						JSONArray array = jsonObj.getJSONArray("capacity");
						for(int k=0;k<array.size();++k){
							JSONObject job = array.getJSONObject(k);
							if(finalMap.containsKey(job.getString("type"))){
								JSONObject tempJson = finalMap.get(job.getString("type"));
								job.element("capacityused",tempJson.getLong("capacityused")+job.optLong("capacityused"));
								job.element("capacitytotal",tempJson.getLong("capacitytotal")+job.optLong("capacitytotal"));
								job.element("percentused",""+Math.round((job.optLong("capacityused")*1.0D*10000/job.optLong("capacitytotal")))*0.01);
								log.info("合并之后 zoneid:"+job.getString("zoneid")+",type:"+job.getInt("type")+",capacityused:"+job.getDouble("capacityused")+",capacitytotal:"+job.getDouble("capacitytotal")+",percentused:"+job.getString("percentused"));
								finalMap.put(job.getString("type"), job);
							}else{
								finalMap.put(job.getString("type"), job);
							}
						}
					}
				}
			}
			System.out.println("finalMap.values():::>>>"+finalMap.values());
		Map jsonMap = new HashMap();
		jsonMap.put("count",finalMap.size());
		jsonMap.put("capacity", finalMap.values());
		JSONObject jsonStr = new JSONObject();
		jsonStr.put("listcapacityresponse", JSONObject.fromObject(jsonMap).toString());
		System.out.println("listcapacityresponse.toString()>>"+jsonStr.toString());
		return jsonStr.toString();
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String accountResource(Map<String, Object[]> cloudStackParam,
			String resourcePoolId) {
		String str=getAccount(cloudStackParam);
		Map<String, Object[]> newMap = new HashMap<String, Object[]>();
		newMap.putAll(cloudStackParam);
		
		// 查询ZONES
		//newMap.put("command", new Object[] { "listZones" });
		String[] args=str.split(",");
		StringBuffer cstr=new StringBuffer();
		
		cstr.append("[");
		//Map<String,JSONObject> finalMap = new HashMap<String,JSONObject>();
		for(int i=0;i<args.length;i++){
			int memory=0;
			int cpunumber=0;
			int vmrunningcount=0;
			int vmstopedcount=0;
			//String accountName="";
			int xujish=0;
		    cloudStackParam.put("account", new Object[] {args[i]});
			cloudStackParam.put("command", new Object[] { "listVirtualMachines" });
			
			Response response = genericCloudServerManager.get(cloudStackParam);
			if (response != null
					&& StringUtils.isNotBlank(response.getResponseString())) {
				JSONObject jsonObj = JSONObject.fromObject(response
						.getResponseString());
				if (jsonObj.containsKey("listvirtualmachinesresponse")) {
					jsonObj = JSONObject.fromObject(jsonObj.get("listvirtualmachinesresponse").toString());
					if (jsonObj.containsKey("virtualmachine")) {
						JSONArray array = jsonObj.getJSONArray("virtualmachine");
						for(int k=0;k<array.size();++k){
							JSONObject job = array.getJSONObject(k);
							memory+=Integer.parseInt(job.getString("memory"));
							cpunumber+=Integer.parseInt(job.getString("cpunumber"));
							String state = job.getString("state");
							if(state.equalsIgnoreCase("Running"))
							{
								vmrunningcount++;
							}
							else if(state.equalsIgnoreCase("Stopped"))
							{
								vmstopedcount++;
							}
							
							/*	job.element("capacityused",1);
								job.element("capacitytotal",1);
								job.element("account",job.getString("account"));
								System.out.println("memory:"+job.getString("memory"));
								System.out.println("cpunumber:"+job.getString("cpunumber"));
								System.out.println("cpuused:"+job.getString("cpuused"));
								System.out.println("account:"+job.getString("account")+"job.getString----------------->>>"+job.getString("capacitytotal"));
								*/
							//accountName=job.getString("account");
							//finalMap.put(job.getString("account"), job);
							xujish=array.size();
						}
						
					}
				}
			}
			String getStr=combinJson(xujish,memory,cpunumber,args[i],vmrunningcount,vmstopedcount);
			cstr.append(getStr);
			if(i!=args.length-1){
				cstr.append(",");
			}
		}
		//cstr.deleteCharAt(str.toString().length()-2);
		cstr.append("]");
			
			//System.out.println("finalMap.values().toString()>>"+finalMap.values());
		Map jsonMap = new HashMap();
		jsonMap.put("count",10);
		jsonMap.put("capacity", cstr.toString());
		JSONObject jsonStr = new JSONObject();
		jsonStr.put("listcapacityresponse", JSONObject.fromObject(jsonMap).toString());
		System.out.println("jsonStr.toString()>>"+jsonStr.toString());
		return jsonStr.toString();
	}
	private String combinJson(int xunjicount,int memory,int capacitytotal,String account,int vmrunningcount,int vmstopedcount){
		float memoryG=memory/1024f;
		String str="{'memory': '"+memoryG+"GB', 'capacitytotal': '"+capacitytotal+"','account': '"+account+"','xunjicount': '"+xunjicount+"','vmrunningcount': '"+vmrunningcount+"','vmstopedcount':'"+vmstopedcount+"'}";
		return str;
	}
	
	//获取所有帐户
	private String getAccount(Map<String, Object[]> cloudStackParam) {
		Map<String, Object[]> newMap = new HashMap<String, Object[]>();
		newMap.putAll(cloudStackParam);
		
		Object[] obj = newMap.get("user");
		
		if(obj!=null)
		{
			return obj[0].toString();
		}	
			
		StringBuffer str=new StringBuffer();
			cloudStackParam.put("command", new Object[] { "listAccounts" });
			Response response = genericCloudServerManager.get(cloudStackParam);
			if (response != null
					&& StringUtils.isNotBlank(response.getResponseString())) {
				JSONObject jsonObj = JSONObject.fromObject(response
						.getResponseString());
				if (jsonObj.containsKey("listaccountsresponse")) {
					jsonObj = JSONObject.fromObject(jsonObj.get(
							"listaccountsresponse").toString());
					if (jsonObj.containsKey("account")) {
						JSONArray array = jsonObj.getJSONArray("account");
						for(int k=0;k<array.size();++k){
							JSONObject job = array.getJSONObject(k);
								System.out.println("name==>>"+job.getString("name"));
								str.append(job.getString("name")+",");
						}
					}
				}
			}
			//System.out.println("str==>>"+str.deleteCharAt(str.toString().length()-1));
		return str.deleteCharAt(str.toString().length()-1).toString();
	}


	@Override
	public String operationResource(Map<String, Object[]> cloudStackParam,
			String resourcePoolId) {
		Map<String, Object[]> newMap = new HashMap<String, Object[]>();
		newMap.putAll(cloudStackParam);
		// 查询ZONES
		Map<String,JSONObject> finalMap = new HashMap<String,JSONObject>();
		
			cloudStackParam.put("command", new Object[] { "listProjects" });
			
			Response response = genericCloudServerManager.get(cloudStackParam);
			if (response != null
					&& StringUtils.isNotBlank(response.getResponseString())) {
				JSONObject jsonObj = JSONObject.fromObject(response
						.getResponseString());
				if (jsonObj.containsKey("listprojectsresponse")) {
					jsonObj = JSONObject.fromObject(jsonObj.get(
							"listprojectsresponse").toString());
					if (jsonObj.containsKey("project")) {
						JSONArray array = jsonObj.getJSONArray("project");
						for(int k=0;k<array.size();++k){
							JSONObject job = array.getJSONObject(k);
								//JSONObject tempJson = finalMap.get(job.getString("type"));
							    job.element("name",job.getString("name"));
								job.element("networktotal",job.optLong("networktotal"));
								job.element("vpctotal",job.optLong("vpctotal"));
								job.element("cputotal",job.optLong("cputotal"));
								job.element("memorytotal",job.optLong("memorytotal"));
								job.element("vmtotal",job.optLong("vmtotal"));
								
								finalMap.put("ss"+k, job);
							
						}
					}
				}
			}
			//System.out.println("finalMap.values():::>>>"+finalMap.values());
		Map jsonMap = new HashMap();
		jsonMap.put("count",finalMap.size());
		jsonMap.put("capacity", finalMap.values());
		JSONObject jsonStr = new JSONObject();
		jsonStr.put("listcapacityresponse", JSONObject.fromObject(jsonMap).toString());
		System.out.println("listcapacityresponse.toString()>>"+jsonStr.toString());
		return jsonStr.toString();
	}


}
