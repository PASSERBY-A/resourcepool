package com.hp.avmon.ireport.util;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class EmailUtil {
	// smtp服务器
	private String host = "";
	// 授权信息
	private String authentication = "none";
	// 用户名
	private String user = "";
	// 密码
	private String pwd = "";
	// 收件人地址 
	private String to = "zhiyunl@hp.com";
	// 邮件标题
	private String subject = "";
    // 送信人
    private static final String MAIL_FROM = "ChinaTSKM@hp.com";
    // 邮件文本格式
    private boolean contentMode = true;
    // 登录系统link
    public static String LINK = "";
    
	public void setContentMode(boolean mode) {
		this.contentMode = mode;
	}

	public void setAddress(String to, String subject) {
		this.to = to;
		this.subject = subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}

    public void autoSend(String txt, HashMap<String, Object> map, Map<String,String> image) {
        this.host = (String)map.get("mail_host");
        Properties props = new Properties();
        // 邮件接收者 
        String MAIL_TO = (String)map.get("mail_email");
        // 抄送者
        String MAIL_CC = "";
        // 设置发送邮件的邮件服务器的属性（这里使用reportConfig.xml中配置的host）
        props.put("mail.smtp.host", map.get("mail_host"));
        // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.auth", authentication);
        // 用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(props);
        // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        // 用（你可以在控制台（console)上看到发送邮件的过程）
        session.setDebug(true);
        // 用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session);
        try {
            // 加载发件人地址
            message.setFrom(new InternetAddress(MAIL_FROM));
            // 加载收件人地址
            String[] receivers = null;
            if (MAIL_TO.length() != 0) {
                receivers = MAIL_TO.split(";");
            }

            Address[] tos = null;
            if (receivers != null){
                // 为每个邮件接收者创建一个地址
                tos = new InternetAddress[receivers.length];
                for (int i=0; i < receivers.length; i++){
                    tos[i] = new InternetAddress(receivers[i]);
                }
            }
            message.setRecipients(Message.RecipientType.TO, tos);
            
            // 添加cc地址列表
            receivers = null;
            if (MAIL_CC != null && MAIL_CC.length() != 0) {
                receivers = MAIL_CC.split(";");
            }
            Address[] ccs = null;
            if (receivers != null){
                // 为抄送者创建一个地址
                ccs = new InternetAddress[receivers.length];
                for (int i=0; i < receivers.length; i++){
                    ccs[i] = new InternetAddress(receivers[i]);
                }
            }
            message.setRecipients(Message.RecipientType.CC, ccs);
            
            // 加载标题
            message.setSubject((String)map.get("reportName"));
            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart("related");
    
            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            //contentPart.setText(txt);
            contentPart.setContent( " <meta   http-equiv=Content-Type   content=text/html;   charset=gb2312> "   +   txt,   "text/html;charset=GB2312 ");
            contentPart.setHeader("Content-ID", "<IMG1>");
            multipart.addBodyPart(contentPart);


            //创建代表邮件正文和附件的各个MimeBodyPart对象  
            MimeBodyPart contentpart=createContent(txt,image);  
            multipart.addBodyPart(contentpart);  
            
            // 将multipart对象放到message中
            message.setContent(multipart);

            // 保存邮件
            message.saveChanges();
            // 发送邮件
            Transport transport = session.getTransport("smtp");
            // 连接服务器的邮箱
            transport.connect(host, user, pwd);
            // 把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      
    @SuppressWarnings({ "rawtypes", "unchecked" })  
    public MimeBodyPart createContent(String body, Map<String,String> image)throws Exception{  
        //创建代表组合Mime消息的MimeMultipart对象，将该MimeMultipart对象保存到MimeBodyPart对象  
        MimeBodyPart contentPart=new MimeBodyPart();  
        MimeMultipart contentMultipart=new MimeMultipart("related");  
          
        //创建用于保存HTML正文的MimeBodyPart对象，并将它保存到MimeMultipart中  
        MimeBodyPart htmlbodypart=new MimeBodyPart();  
        htmlbodypart.setContent(body,"text/html;charset=UTF-8");  
        contentMultipart.addBodyPart(htmlbodypart);  
          
        if(image!=null && image.size()>0) {  
             Set<Entry<String, String>> set=image.entrySet();  
             for (Iterator iterator = set.iterator(); iterator.hasNext();) {  
                Entry<String, String> entry = (Entry<String, String>) iterator.next();  
                  
                //创建用于保存图片的MimeBodyPart对象，并将它保存到MimeMultipart中  
                MimeBodyPart gifBodyPart=new MimeBodyPart();  
                FileDataSource fds=new FileDataSource(entry.getValue());//图片所在的目录的绝对路径  
                  
                gifBodyPart.setDataHandler(new DataHandler(fds));  
                gifBodyPart.setContentID(entry.getKey());   //cid的值  
                contentMultipart.addBodyPart(gifBodyPart);  
            }  
        }  
          
        //将MimeMultipart对象保存到MimeBodyPart对象  
        contentPart.setContent(contentMultipart);  
        return contentPart;  
    }  
	
	/**
     * 判断文件及目录是否存在，若不存在则创建文件及目录
     * @param filepath
     * @return
     * @throws Exception
     */
	public static File checkExist(String filepath) throws Exception{
		File file = new File(filepath);
      
		if (file.exists()) {//判断文件目录的存在
        	//System.out.println("文件夹存在！");
        	if(file.isDirectory()){//判断文件的存在性      
            	//System.out.println("文件存在！");      
            }else{
            	file.createNewFile();//创建文件
            	//System.out.println("文件不存在，创建文件成功！"   );      
            }
		} else {
			//System.out.println("文件夹不存在！");
			File file2=new File(file.getParent());
			file2.mkdirs();
			//System.out.println("创建文件夹成功！");
			if(file.isDirectory()){      
            	//System.out.println("文件存在！");       
            }else{      
            	file.createNewFile();//创建文件 
            	//System.out.println("文件不存在，创建文件成功！"   );      
           	}
		}
		return file;
    }
	
	//jws add 
	public void sendAndCc(String txt,List<String> cclist) {
		Properties props = new Properties();
		// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.put("mail.smtp.host", host);
		// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
		props.put("mail.smtp.auth", "none");
		// 用刚刚设置好的props对象构建一个session
		Session session = Session.getDefaultInstance(props);
		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(true);
		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
		try {
			// 加载发件人地址
			message.setFrom(new InternetAddress(MAIL_FROM));
			// 加载收件人地址
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			// 添加cc地址列表
			Address[] ccs = null;
			if (cclist != null){
                // 为抄送者创建一个地址
				ccs = new InternetAddress[cclist.size()];
                for (int i=0; i < cclist.size(); i++){
                	ccs[i] = new InternetAddress(cclist.get(i));
                }
            }
			message.setRecipients(Message.RecipientType.CC, ccs);

			// 加载标题
			message.setSubject(subject);
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();
		
			// 设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();
			if (contentMode) {
				contentPart.setContent( " <meta   http-equiv=Content-Type   content=text/html;   charset=gb2312> "   +   txt,   "text/html;charset=GB2312 ");
			} else {
				contentPart.setText(txt);
			}
			multipart.addBodyPart(contentPart);
   
			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Transport transport = session.getTransport("smtp");
			// 连接服务器的邮箱
			transport.connect(host, user, pwd);
			// 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

