package com.hp.avmon.agentless.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.hp.avmon.common.Config;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.home.service.LicenseService;
import com.hp.avmon.ireport.util.StringUtil;
import com.hp.avmon.utils.ImportExcelResult;
import com.hp.avmon.utils.PoiExcel;
import com.hp.avmon.utils.PropUtil;

/**
 * @author muzh
 *
 */
@Service
@SuppressWarnings({ "unchecked"})
public class AgentlessService {

	private static final Log logger = LogFactory.getLog(AgentlessService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LicenseService licenseService;

	public String importAgentlessFile(HttpServletRequest request) throws IOException {
		
		String result = StringUtil.EMPTY;
		HashMap<String,ArrayList<Map<String,String>>> resultMap = new HashMap<String, ArrayList<Map<String,String>>>();
		ArrayList<Map<String,String>> files = new ArrayList<Map<String,String>>();
		Map<String,HashMap<String,List<String>>> map  = new HashMap<String,HashMap<String,List<String>>>();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		//获取路径
		String templatePath = Config.getInstance().getAgentlessfilesUploadPath();
		File dirPath = new File(templatePath);
	    if (!dirPath.exists()) {
	    	dirPath.mkdirs();
	    }
	    Map<String,String> fileMap;
    	try {
    		for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
		    	String fileName = (String)it.next(); 
		        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(fileName);
		        if(file!=null && file.getSize() != 0){
			        String sep = System.getProperty("file.separator");
			        String importFilePath = dirPath + sep + file.getOriginalFilename();
			        File uploadedFile = new File(importFilePath);
			        FileCopyUtils.copy(file.getBytes(), uploadedFile);
			        //文件上传成功后执行导入方法
			        String importStatus = importRmpInstance(importFilePath);
			        //boolean importStatus = false;
			        String name = file.getOriginalFilename();
			        fileMap = new HashMap<String, String>();
			        
			        if(importStatus.indexOf("success")>-1){
				        fileMap.put("status", "success");
				        String[] s = importStatus.split(":");
				        fileMap.put("msg", "成功导入："+s[1]+"，已存在未导入："+s[2]);
			        }else{
			        	fileMap.put("status", "failed");
			        	fileMap.put("msg", importStatus);
			        }
			        fileMap.put("url", "");
			        fileMap.put("thumbnailUrl", "");
			        fileMap.put("name", name);
			        fileMap.put("type", "image/jpeg");
			        fileMap.put("size", file.getSize()+"");
			        fileMap.put("deleteUrl",""); 
			        fileMap.put("deleteType", "DELETE");
			        files.add(fileMap);
		        }
		    }
    		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
    	resultMap.put("files", files);
    	result = JackJson.fromObjectToJson(resultMap);
    	logger.debug(result);
		return result;
	}
	
	private String importRmpInstance(String rmpInstanceFile) {
		try{
			ImportExcelResult rr=PoiExcel.importExcel(rmpInstanceFile,1);
	        System.out.format("column=%d,row=%d\n",rr.colCount,rr.rowCount);
	        if(true){
	       // if(licenseService.checkMoCount(rr.rowCount)){
	        	int importCount = 0;
	        	int existedCount = 0;
	        	if(rr!=null){
		            for(int i=1;i<rr.rowCount;i++){
		            	int createStatus = createRmpInstance(rr.rows.get(0),rr.rows.get(i));
		                if(createStatus==0){
		                	System.out.format("Error: Import %d line fault!\n",i);
		                	return "数据格式有问题！";
		                }
		                else if(createStatus==1){
		                	System.out.format("Import %d line OK!\n",i);
		                	importCount++;
		                }
		                else if(createStatus==2){
		                	System.out.format("Existed %d line OK!\n",i);
		                	existedCount++;
		                }
		            }
		        }
	        	return "success:"+importCount+":"+existedCount;
	        }else{
	        	return "License允许MO最大数量:"+licenseService.getMaxMoCount()+"，已有MO数量:"+licenseService.moCount()+"，本次需导入MO数量:"+rr.rowCount+"！";
	        }
		}catch(Exception e){
			logger.error(e);
			return "数据格式有问题！";
		}
	}

	private int createRmpInstance(ArrayList<String> header, ArrayList<String> row) {

        if(header.size()!=row.size()){
            System.out.println("column count not match the header count!");
            return 0;
        }
        String moId="";
        String rmpId="";
        String moType="";
        String moName="";
        Map<String,String> params=new HashMap();
        
        for(int i=0;i<header.size();i++){
            if("mo_id".equals(header.get(i))){
            	moId=row.get(i);
            }
            else if("rmp_id".equals(header.get(i))){
            	rmpId=row.get(i);
            }
            else if("mo_type".equals(header.get(i))){
            	moType=row.get(i);
            }
            else if("mo_name".equals(header.get(i))){
            	moName=row.get(i);
            }
            else if(header.get(i).startsWith("param:")){
            	String paramId=header.get(i).substring(6);
                String paramValue=row.get(i);
                params.put(paramId, paramValue);
            }
        }
        if(moId==null || rmpId.equals("") || moType.equals("")){
        	System.out.println("mo_id or rmp_id or mo_type not defined!");
            return 0;
        }
        if(moName.equals("")){
        	moName=moId;
        }
        
        System.out.format("Importing instance (moId=%s,rmpId=%s,moType=%s)...\n",moId,rmpId,moType);
        
        //insert params
        for(String paramId:params.keySet()){
        	String paramValue=params.get(paramId);
        	String sql=String.format("delete from td_avmon_al_rmp_params where mo_id='%s' and rmp_id='%s' and param_id='%s' ",
	        		moId,
	        		rmpId,
	        		paramId
        		);
        	jdbcTemplate.execute(sql);
        	sql=String.format("insert into td_avmon_al_rmp_params(mo_id,rmp_id,param_id,param_value) values('%s','%s','%s','%s')",
        			moId,
            		rmpId,
            		paramId,
            		paramValue
        		);
        	jdbcTemplate.execute(sql);
        }
        
        //insert schedule
        String sql=String.format("delete from td_avmon_al_schedule where mo_id='%s' and rmp_id='%s'",moId,rmpId);
        jdbcTemplate.execute(sql);
        sql=String.format("insert into td_avmon_al_schedule(mo_id,rmp_id,schedule_policy,command_line,schedule_id,run_at,output_encoding)" +
        		" select '%s',rmp_id,schedule_policy,command_line,'%s.'||id,run_at,output_encoding from TD_AVMON_AL_RMP_SCHD_DEFAULT where rmp_id='%s'",
	        		moId,
	        		moId,
	        		rmpId
        		);
        jdbcTemplate.execute(sql);
        
        //check and create mo
        String protocalMethod=PropUtil.getString("config", rmpId+".protocal-method");
        if(protocalMethod==null){
        	protocalMethod="AGENTLESS";
        }
        if(rmpId.equals("rmp-ilo-dell")){
        	String iloType=params.get("type");
        	if(iloType!=null){
        		if(iloType.equals("ilo")){
        			protocalMethod="ILO";
        		}else if(iloType.equals("oa")){
        			protocalMethod="ILO";
        		}else{
        			protocalMethod="IDRAC";
        		}
        	}
        }
        if (checkAndCreateMo(moId,moName,moType,protocalMethod)){//数据格式正确且导入成功
        	return 1;
        }else{//MO已存在，没有重新导入
        	return 2;
        }
//        return true;
    }


	private boolean checkAndCreateMo(String moId, String moName,
			String moType,String protocalMethod) {
		
        String sqlInsert=String.format("insert into TD_AVMON_MO_INFO(mo_id,type_id,caption,description,protocal_method) values('%s','%s','%s','%s','%s')",
                moId,moType,moName,moName,protocalMethod);
        String sqlQuery=String.format("select count(*) from TD_AVMON_MO_INFO where mo_id='%s'",moId);
        int n=jdbcTemplate.queryForInt(sqlQuery);
        if(n>0){
            //already exists, do nothing.
        	System.out.format("MO already exists (id=%s,type=%s).\n",moId,moType);
        	return false;
        }
        else{
            //insert new one
            System.out.format("Create new MO (id=%s,name=%s,type=%s).\n",moId,moName,moType);
            jdbcTemplate.update(sqlInsert);            
            return true;
        }
		
	}
}
