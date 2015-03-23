package com.hp.avmon.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.logicalcobwebs.proxool.ProxoolFacade;

public class HouseKeeperServlet extends HttpServlet {

	/**
	  * 
	  */
	 private static final long serialVersionUID = 4829418704873725291L;

	 public void destroy() {
	  //此处添加处理 
	  ProxoolFacade.shutdown();
	 }

	 public void doPost(HttpServletRequest request, HttpServletResponse response)
	   throws ServletException, IOException {
	  doGet(request, response);
	 }

	 public void doGet(HttpServletRequest request, HttpServletResponse response)
	   throws ServletException, IOException {

	 }


}
