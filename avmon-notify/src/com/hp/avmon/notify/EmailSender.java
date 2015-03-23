package com.hp.avmon.notify;

import java.util.Properties;
import java.util.Date;    
import javax.mail.Address;    
import javax.mail.Authenticator;
import javax.mail.BodyPart;    
import javax.mail.Message;    
import javax.mail.MessagingException;    
import javax.mail.Multipart;    
import javax.mail.PasswordAuthentication;
import javax.mail.Session;    
import javax.mail.Transport;    
import javax.mail.internet.InternetAddress;    
import javax.mail.internet.MimeBodyPart;    
import javax.mail.internet.MimeMessage;    
import javax.mail.internet.MimeMultipart;    

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.avmon.notify.utils.PropUtil;

public class EmailSender {

	 private static final Logger log = LoggerFactory.getLogger(EmailSender.class);
	 
	 public static class MailSenderInfo {    
		    // �����ʼ��ķ�������IP�Ͷ˿�    
		    private String mailServerHost;    
		    private String mailServerPort = "25";    
		    // �ʼ������ߵĵ�ַ    
		    private String fromAddress;    
		    // �ʼ������ߵĵ�ַ    
		    private String toAddress;    
		    // ��½�ʼ����ͷ��������û���������    
		    private String userName;    
		    private String password;    
		    // �Ƿ���Ҫ�����֤    
		    private boolean validate = false;    
		    // �ʼ�����    
		    private String subject;    
		    // �ʼ����ı�����    
		    private String content;    
		    // �ʼ��������ļ���    
		    private String[] attachFileNames;      
		    /**   
		      * ����ʼ��Ự����   
		      */    
		    public Properties getProperties(){    
		      Properties p = new Properties();    
		      p.put("mail.smtp.host", this.mailServerHost);    
		      p.put("mail.smtp.port", this.mailServerPort);    
		      p.put("mail.smtp.auth", validate ? "true" : "false");    
		      return p;    
		    }    
		    
		    public MailSenderInfo(){
		    	
		    }
		    public String getMailServerHost() {    
		      return mailServerHost;    
		    }    
		    public void setMailServerHost(String mailServerHost) {    
		      this.mailServerHost = mailServerHost;    
		    }   
		    public String getMailServerPort() {    
		      return mailServerPort;    
		    }   
		    public void setMailServerPort(String mailServerPort) {    
		      this.mailServerPort = mailServerPort;    
		    }   
		    public boolean isValidate() {    
		      return validate;    
		    }   
		    public void setValidate(boolean validate) {    
		      this.validate = validate;    
		    }   
		    public String[] getAttachFileNames() {    
		      return attachFileNames;    
		    }   
		    public void setAttachFileNames(String[] fileNames) {    
		      this.attachFileNames = fileNames;    
		    }   
		    public String getFromAddress() {    
		      return fromAddress;    
		    }    
		    public void setFromAddress(String fromAddress) {    
		      this.fromAddress = fromAddress;    
		    }   
		    public String getPassword() {    
		      return password;    
		    }   
		    public void setPassword(String password) {    
		      this.password = password;    
		    }   
		    public String getToAddress() {    
		      return toAddress;    
		    }    
		    public void setToAddress(String toAddress) {    
		      this.toAddress = toAddress;    
		    }    
		    public String getUserName() {    
		      return userName;    
		    }   
		    public void setUserName(String userName) {    
		      this.userName = userName;    
		    }   
		    public String getSubject() {    
		      return subject;    
		    }   
		    public void setSubject(String subject) {    
		      this.subject = subject;    
		    }   
		    public String getContent() {    
		      return content;    
		    }   
		    public void setContent(String textContent) {    
		      this.content = textContent;    
		    }    
		}   
	 


	public static boolean send(String emailAddress, String title, String content) {
	      MailSenderInfo mailInfo = new MailSenderInfo();    
	      mailInfo.setMailServerHost(PropUtil.getString("config", "mail.smtp.host"));    
	      mailInfo.setMailServerPort(PropUtil.getString("config", "mail.smtp.port"));    
	      mailInfo.setValidate(PropUtil.getString("config", "mail.smtp.auth").equals("true"));    
	      mailInfo.setUserName(PropUtil.getString("config", "mail.username"));    
	      mailInfo.setPassword(PropUtil.getString("config", "mail.password"));//������������    
	      mailInfo.setFromAddress(PropUtil.getString("config", "mail.from.address"));    
	      mailInfo.setToAddress(emailAddress);    
	      mailInfo.setSubject(title);    
	      mailInfo.setContent(content);    
	         //�������Ҫ�������ʼ�   
	      SimpleMailSender sms = new SimpleMailSender();   
	         //sms.sendTextMail(mailInfo);//���������ʽ    
	      return sms.sendHtmlMail(mailInfo);//����html��ʽ   
	}
	
	public static class MyAuthenticator extends Authenticator{   
	    String userName=null;   
	    String password=null;   
	        
	    public MyAuthenticator(){   
	    }   
	    public MyAuthenticator(String username, String password) {    
	        this.userName = username;    
	        this.password = password;    
	    }    
	    protected PasswordAuthentication getPasswordAuthentication(){   
	        return new PasswordAuthentication(userName, password);   
	    }   
	}   
	
	public static class SimpleMailSender  {    
		/**   
		  * ���ı���ʽ�����ʼ�   
		  * @param mailInfo �����͵��ʼ�����Ϣ   
		  */    
		    public boolean sendTextMail(MailSenderInfo mailInfo) {    
		      // �ж��Ƿ���Ҫ�����֤    
		      MyAuthenticator authenticator = null;    
		      Properties pro = mailInfo.getProperties();   
		      if (mailInfo.isValidate()) {    
		      // �����Ҫ�����֤���򴴽�һ��������֤��    
		        authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());    
		      }   
		      // �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session    
		      Session sendMailSession = Session.getDefaultInstance(pro,authenticator);    
		      try {    
		      // ����session����һ���ʼ���Ϣ    
		      Message mailMessage = new MimeMessage(sendMailSession);    
		      // �����ʼ������ߵ�ַ    
		      Address from = new InternetAddress(mailInfo.getFromAddress());    
		      // �����ʼ���Ϣ�ķ�����    
		      mailMessage.setFrom(from);    
		      // �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ��    
		      Address to = new InternetAddress(mailInfo.getToAddress());    
		      mailMessage.setRecipient(Message.RecipientType.TO,to);    
		      // �����ʼ���Ϣ������    
		      mailMessage.setSubject(mailInfo.getSubject());    
		      // �����ʼ���Ϣ���͵�ʱ��    
		      mailMessage.setSentDate(new Date());    
		      // �����ʼ���Ϣ����Ҫ����    
		      String mailContent = mailInfo.getContent();    
		      mailMessage.setText(mailContent);    
		      // �����ʼ�    
		      Transport.send(mailMessage);   
		      return true;    
		      } catch (MessagingException ex) {    
		          ex.printStackTrace();    
		      }    
		      return false;    
		    }    
		       
		    /**   
		      * ��HTML��ʽ�����ʼ�   
		      * @param mailInfo �����͵��ʼ���Ϣ   
		      */    
		    public static boolean sendHtmlMail(MailSenderInfo mailInfo){    
		      // �ж��Ƿ���Ҫ�����֤    
		      MyAuthenticator authenticator = null;   
		      Properties pro = mailInfo.getProperties();   
		      //�����Ҫ�����֤���򴴽�һ��������֤��     
		      if (mailInfo.isValidate()) {    
		        authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());   
		      }    
		      // �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session    
		      Session sendMailSession = Session.getDefaultInstance(pro,authenticator);    
		      try {    
		      // ����session����һ���ʼ���Ϣ    
		      Message mailMessage = new MimeMessage(sendMailSession);    
		      // �����ʼ������ߵ�ַ    
		      Address from = new InternetAddress(mailInfo.getFromAddress());    
		      // �����ʼ���Ϣ�ķ�����    
		      mailMessage.setFrom(from);    
		      // �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ��    
		      Address to = new InternetAddress(mailInfo.getToAddress());    
		      // Message.RecipientType.TO���Ա�ʾ�����ߵ�����ΪTO    
		      mailMessage.setRecipient(Message.RecipientType.TO,to);    
		      // �����ʼ���Ϣ������    
		      mailMessage.setSubject(mailInfo.getSubject());    
		      // �����ʼ���Ϣ���͵�ʱ��    
		      mailMessage.setSentDate(new Date());    
		      // MiniMultipart����һ�������࣬����MimeBodyPart���͵Ķ���    
		      Multipart mainPart = new MimeMultipart();    
		      // ����һ������HTML���ݵ�MimeBodyPart    
		      BodyPart html = new MimeBodyPart();    
		      // ����HTML����    
		      html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");    
		      mainPart.addBodyPart(html);    
		      // ��MiniMultipart��������Ϊ�ʼ�����    
		      mailMessage.setContent(mainPart);    
		      // �����ʼ�    
		      Transport.send(mailMessage);    
		      return true;    
		      } catch (MessagingException ex) {    
		          ex.printStackTrace();    
		      }    
		      return false;    
		    }    
		}   
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	      MailSenderInfo mailInfo = new MailSenderInfo();    
	      mailInfo.setMailServerHost(PropUtil.getString("config", "mail.smtp.host"));    
	      mailInfo.setMailServerPort(PropUtil.getString("config", "mail.smtp.port"));    
	      mailInfo.setValidate(PropUtil.getString("config", "mail.smtp.auth").equals("true"));    
	      mailInfo.setUserName(PropUtil.getString("config", "mail.username"));    
	      mailInfo.setPassword(PropUtil.getString("config", "mail.password"));//������������    
	      mailInfo.setFromAddress(PropUtil.getString("config", "mail.from.address"));    
	      mailInfo.setToAddress("yongqiang.ji@hp.com");    
	      mailInfo.setSubject("testing");    
	      mailInfo.setContent("������������ ��http://www.guihua.org �й����� ���й�������վ==");    
	         //�������Ҫ�������ʼ�   
	      SimpleMailSender sms = new SimpleMailSender();   
	          sms.sendTextMail(mailInfo);//���������ʽ    
	          sms.sendHtmlMail(mailInfo);//����html��ʽ   
	}
	


}
