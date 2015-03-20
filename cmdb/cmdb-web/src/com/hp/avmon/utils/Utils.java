package com.hp.avmon.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hp.avmon.common.Config;

public class Utils {

    /**
     * @param args
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        // TODO Auto-generated method stub
        
        BASE64Encoder base64=new BASE64Encoder();
        String str=base64.encode("中文".getBytes());
        System.out.println(str);
        BASE64Decoder dec=new BASE64Decoder();
        String s=new String(dec.decodeBuffer(str));
        System.out.println(s);
    }
    
    /**
     * 将id/pid结构的数组转换为带层次结构的json，用于构建extjs的属性菜单
     * @param jsonArrayStr
     */
    public static String buildTreeJson(List<Map> nodeList){
        
        List result=new ArrayList();
        //为每一个节点寻找父亲
        for(Map<String,Object> map:nodeList){
            String pid=(String) map.get("pid");
            boolean hasParent=false;
            for(Map<String,Object> pm:nodeList){
                String id=(String) pm.get("id");
                if(id.equals(pid)){
                    //add to parent
                    List child=(List) pm.get("children");
                    if(child==null){
                        child=new ArrayList();
                        pm.put("children", child);
                    }
                    child.add(map);
                    hasParent=true;
                    break;
                }
            }
            if(!hasParent){
                result.add(map);
            }
        }
        
        for(Map<String,Object> map:nodeList){
            List child=(List) map.get("children");
            if(child==null){
                map.put("leaf", true);
            }
            else{
                map.put("expended", true);
            }
            map.remove("pid");
        }
        
        
        Gson gson = new Gson();

        //构造json
        String objTreeJson = gson.toJson(result);

        return objTreeJson;
    }
    
 
    /**
     * 获取当前用户ID
     * @param request
     * @return
     */
    public static String getCurrentUserId(HttpServletRequest request) {
        Map currUser=(Map)request.getSession().getAttribute(Config.CURRENT_USER);
        String userId="unknown";
        if(currUser!=null){
            userId=(String) currUser.get("USER_ID");
            if(userId==null){
                userId="unknown";
            }
        }
        return userId;
    }
    
    /**
     * 在调试期间为了防止浏览器读取缓存的js文件，在引用js文件时，请按如下方式引用：
     * <script src="myjs.js?_t=<%=com.hp.avmon.utils.Utils.debugRandomId()%>"></script>
     * 在生产环境时，该函数将返回固定值，以提高性能
     * @return
     */
    public static String debugRandomId(){
        if(Config.getInstance().getRunMode().equals("dev")){
            return String.valueOf(new java.util.Date().getTime());
        }
        return "";
    }
    
    public static String responsePrintWrite(HttpServletResponse response, String strRet,String returnValue) {
		try {
			
			PrintWriter writer;
			response.setHeader("Content-Encoding", "utf-8");
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			writer = response.getWriter();
			writer.write(strRet);
			writer.flush();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return returnValue;
	}
    
	public static String getUID() {
		return UUID.randomUUID().toString();
	}

    public static String decodeString(String str) {
        if(str==null){
            return null;
        }
        if(str.startsWith("BASE64")){
            String s=str.substring(6);
            BASE64Decoder base64=new BASE64Decoder();
            try {
                return new String(base64.decodeBuffer(s));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return str;
    }

    public static String encodeString(String pid) {
        if(pid==null){
            return null;
        }
        if(pid.getBytes().length==pid.length()){
            return pid;
        }
        else{
            BASE64Encoder base64=new BASE64Encoder();
            return "BASE64"+base64.encode(pid.getBytes());
        }
    }
}
