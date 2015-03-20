package com.hp.avmon.servlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.jfree.util.Log;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hp.avmon.utils.CsvUtil;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.ViewDefine;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.service.CmdbService;
import com.hp.xo.uip.cmdb.service.CmdbViewService;

public class CmdbDownServlet extends HttpServlet {
    private static Logger logger=Logger.getLogger(CmdbDownServlet.class);
	private static final long	serialVersionUID	= 1L;
	private String			contentType		= "application/x-msdownload";
	private String			encode			= "UTF-8";
    
	/**
	 * 初始化contentType，enc，fileRoot
	 */

	public void init(ServletConfig config) throws ServletException {
		String tempStr = config.getInitParameter("contentType");
		if (tempStr != null && !tempStr.equals("")) {
			contentType = tempStr;
		}
		tempStr = config.getInitParameter("encode");
		if (tempStr != null && !tempStr.equals("")) {
			encode = tempStr;
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//downtype: view表示视图数据, source表示资源数据 , direct表示已有文件名直接下载
		String downtype = request.getParameter("down_type");
		String viewname=request.getParameter("view_name");
		String nodetype=request.getParameter("node_type");
		String nodecolumn=request.getParameter("node_column");
		String filePath=request.getParameter("file_path");
		logger.debug("begin read....");
		try{
		/* 读取文件 */
		String path_c="";
		if("direct".equals(downtype)){
		  path_c=filePath;	
		}else{
		  path_c=getFile(request.getSession().getServletContext(),downtype,viewname,nodetype,nodecolumn);
		}
		if("".equals(path_c)||path_c==null){return;}
		
		File file =new File(path_c);
		Log.debug("download file path:"+ file.getAbsolutePath());
		if (file.exists()) {
			String filename = URLEncoder.encode(file.getName(), encode);
			response.reset();
			response.setContentType(contentType);
			response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			int fileLength = (int) file.length();
			response.setContentLength(fileLength);
			/* 如果文件长度大于0 */
			if (fileLength > 0) {
				/* 创建输入流 */
				logger.debug("create fileInputStream....");
				InputStream inStream = new FileInputStream(file);
				/* 创建输出流 */
				logger.debug("create response outputStream....");
				ServletOutputStream servletOS = response.getOutputStream();
				IOUtils.copy(inStream, servletOS);
				inStream.close();
				servletOS.close();
			}
		} else {
			response.getWriter().write(String.format("Download Error! File [%s] do not exist!", 
					"["+downtype+":"+viewname+"]"+"["+path_c+"]"));
			response.flushBuffer();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.debug("outputStream finish!");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
    public String getFile(ServletContext sc,String dtype,String viewname,String nodeType,String nodecolumn) throws CmdbException{
    	String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    	CsvUtil cu=new CsvUtil();
    	String path="";
    	if("view".equalsIgnoreCase(dtype)){
    		CmdbViewService cv= (CmdbViewService) getService(sc,"cmdbViewServiceProxy");    		
    		ViewDefine vd= cv.getViewDataByNameFilter(viewname);
    		if(vd!=null&&vd.getNodesMap()!=null){
    			List<Node> li= vd.getNodesMap().get(nodeType);
    			path=cu.writeXlsToFlie(cu.transNode(li,nodecolumn), viewname+"_"+dtype+"_"+time);
    		}
    	}else if("source".equalsIgnoreCase(dtype)){
    		//资源数据下载暂时不做过滤
    		nodecolumn="";
    		CmdbService cv= (CmdbService) getService(sc,"cmdbServiceProxy");
    		List<Node> li=cv.getCiByTypeName(nodeType);
    		path=cu.writeXlsToFlie(cu.transNode(li,nodecolumn), nodeType+"_"+dtype+"_"+time);
    	}
    	return path;
    }
    
	public Object getService(ServletContext sc,String bean){
		WebApplicationContext w= WebApplicationContextUtils.getWebApplicationContext(sc);
		return w.getBean(bean);
	}

}
