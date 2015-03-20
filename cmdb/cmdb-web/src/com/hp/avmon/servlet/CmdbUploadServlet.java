package com.hp.avmon.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hp.avmon.utils.CsvUtil;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.service.CmdbService;

public class CmdbUploadServlet extends HttpServlet {

	private static final long serialVersionUID = -7684137521162417563L;
    private static Logger log=Logger.getLogger(CmdbUploadServlet.class);
	// 限制文件的上传大小
	private int maxPostSize = 100 * 1024 * 1024;
	
	public CmdbUploadServlet() {
		super();
	}

	public void init() throws ServletException {

	}

	public void destroy() {
		super.destroy();
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("FileUpload start ...");
		// 保存文件到服务器中
		response.setContentType("text/html; charset=UTF-8");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		factory.setSizeThreshold(4096);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		upload.setSizeMax(maxPostSize);
		log.info("FileUpload ...1");
		try {
			List fileItems = upload.parseRequest(request);
			Iterator iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				log.info("FileUpload ...2");
				if (!item.isFormField()) {
					String name = item.getName();
					if(name.indexOf(".csv")>0||name.indexOf(".CSV")>0){
					
					try {						
						log.info("KLBFileUpload ...3");
						String path_c=CsvUtil.dicu;
						File uploadFilePath = new File(path_c);
						log.info("FileUpload ...4, path="+path_c);
						// 如果该目录不存在,则创建
						if (uploadFilePath.exists() == false) {
							uploadFilePath.mkdirs();
							log.info("FileUpload ...4_1 filePath:"+uploadFilePath.exists());
						}
						SimpleDateFormat s=new SimpleDateFormat("yyyyMMddHHmmss"); 
						String transFileName=s.format(new Date())+"_"+item.getName();
						File file=new File(uploadFilePath+"/"+transFileName);
						item.write(file);
						String re=syncNode(request.getSession().getServletContext(),uploadFilePath+"/"+transFileName);
						PrintWriter out = response.getWriter();
						out.println("OK name="+name +"upload success!");
						out.flush();
						out.close();
						log.info("FileUpload ...end");
					} catch (Exception e) {
						log.error("", e);
						e.printStackTrace();
					}
					}else{
						String re=name+" 文件类型错误,必须是 csv文件。";
						log.error(re);
						PrintWriter out = response.getWriter();
						out.println(re);
						out.flush();
						out.close();
					}
				}
			}
		} catch (FileUploadException e) {
			log.error("",e);
			e.printStackTrace();
		}
	}
 
	public String syncNode(ServletContext sc,String path) throws CmdbException{
		CsvUtil cv=new CsvUtil();
	    List<String[]> 	li=null;//cv.readCsvFile(path);
	    if(li==null||li.size()<=3){ 
	    	return "文件缺少必要内容。[类型行，名称行，显示名称行]"; }
	    CmdbService cm=(CmdbService) getService(sc, "CmdbServiceProxy");
	    for(int i=0;i<li.size();i++){
	     String[] ss=li.get(i);
	     if(i==0){
	      if(cm.getCiTypeByName(ss[0])==null){
	    	return ss[0]+"类型不存在"; 
	      }}
	     if(i==1){
	       for(String s:ss){
	    	   
	       }	 
	     }
	    }
		return "";
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public Object getService(ServletContext sc,String bean){
		WebApplicationContext w= WebApplicationContextUtils.getWebApplicationContext(sc);
		return w.getBean(bean);
	}

}

