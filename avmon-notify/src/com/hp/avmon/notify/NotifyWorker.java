package com.hp.avmon.notify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hp.avmon.notify.utils.DBUtils;
import com.hp.avmon.notify.utils.SpringContainer;

public class NotifyWorker extends Thread {

    private static final Logger log = LoggerFactory.getLogger(NotifyWorker.class);
    
    public JdbcTemplate jdbc=null;
    
    public int interval=1;
    public String webserviceUrl;
    public String webserviceMethod;
    public SmsSender smsSender;
    
    public final String templateId="20001873";
    public final String sysId="2";
    
    public String getWebserviceMethod() {
        return webserviceMethod;
    }


    public SmsSender getSmsSender() {
        return smsSender;
    }


    public void setSmsSender(SmsSender smsSender) {
        this.smsSender = smsSender;
    }


    public void setWebserviceMethod(String webserviceMethod) {
        this.webserviceMethod = webserviceMethod;
    }


    public String getWebserviceUrl() {
        return webserviceUrl;
    }


    public void setWebserviceUrl(String webserviceUrl) {
        this.webserviceUrl = webserviceUrl;
    }


    public int getInterval() {
        return interval;
    }


    public void setInterval(int interval) {
        this.interval = interval;
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        
        log.warn("Avmon Notify Service Start ! url={},method={}",webserviceUrl,webserviceMethod);
        log.warn("  check interval={}",interval);
        smsSender.init(webserviceUrl, webserviceMethod);
        
        long start=System.currentTimeMillis();
        while(true){
            try {
            
                
                Thread.sleep(500);
                
                
                long curr=System.currentTimeMillis();
                
                if(curr-start>=interval*1000){
                    start=curr;
                    //System.out.println("scan");
                    checkTask();
                    //System.out.println("ok");
                }
            
                
                
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        
    }


    private void checkTask() {
        // TODO Auto-generated method stub
        if(jdbc==null){
            jdbc=(JdbcTemplate) SpringContainer.getBean("jdbcTemplate");
        }
        String sql="select to_char(create_time,'yyyymmddHH24mi') as sendtime,seq,phone_no,content,id,message_type,title from tf_avmon_notify where send_flag=0";
        List<Map<String,Object>> list=jdbc.queryForList(sql);
        for(Map map:list){
            String id=(String) map.get("ID");
            String phoneNo=(String) map.get("PHONE_NO");
            String content=(String) map.get("CONTENT");
            String seq=String.valueOf(DBUtils.toInt(map.get("SEQ")));
            String sendTime=(String)map.get("SENDTIME");
            String messageType=String.valueOf(DBUtils.toInt(map.get("MESSAGE_TYPE")));
            String title=(String)map.get("TITLE");
            Map params=new HashMap();
            params.put("SEQ", seq);
            params.put("TEMPLATEID", templateId);
            params.put("SYSID", sysId);
            params.put("SENDTIME", sendTime);
            params.put("TITLE", title);
            if(sendAlarm(messageType,phoneNo,title,content,params)){
                jdbc.execute(String.format("update TF_AVMON_NOTIFY set send_flag=1,sent_time=%s where id='%s'",DBUtils.getDBCurrentDateFunction(),id));
            }
            else{
                jdbc.execute(String.format("update TF_AVMON_NOTIFY set send_flag=2,sent_time=%s where id='%s'",DBUtils.getDBCurrentDateFunction(),id));
            }
        }
    }



	private boolean sendAlarm(String messageType, String phoneNo,String title,
			String content, Map params) {
		if(messageType.equals("1")){
			return sendEmail(phoneNo,title,content,params);
		}
		else{
			return sendSms(phoneNo,content,params);
		}
	}


	private boolean sendEmail(String emailAddress, String title,String content, Map params) {

        log.debug("send email emailAddress={},title={}",emailAddress,title);
        

        return EmailSender.send(emailAddress, title,content);
	}


	private boolean sendSms(String phoneNo, String content,Map params) {
        if(smsSender==null){
        	return false;
        }
        // TODO Auto-generated method stub
        log.debug("send sms phone={},content={}",phoneNo,content);
        /*
         *         String seqNo=(String) params.get("SEQ");
        String templateId=(String) params.get("TEMPLATEID");
        String sysId=(String) params.get("SYSID");
        String sendTime=(String) params.get("SENDTIME");
         */
        return smsSender.send(phoneNo, content,params);
    }

}
