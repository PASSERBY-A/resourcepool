package com.hp.avmonserver.api;

import java.rmi.Naming;
import java.util.List;

public class TestClient{    

	IRemoteService obj = null; 

	public void init() { 
		try {	    	   
			obj = (IRemoteService)Naming.lookup("rmi://localhost:9998/AvmonServer"); 
			//while(true){
    			//List list = obj.kpiListByMo("host01"); 
    			//System.out.println("message=" + list);
    			Thread.sleep(1);
			//}
		}catch (Exception e) { 
			System.out.println("HelloApplet exception: " + e.getMessage()); 
			e.printStackTrace(); 
		} 
	} 

	public static void main(String[] args) {
		new TestClient().init();
	}
}

