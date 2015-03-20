package com.hp.xo.uip.cmdb.dao.template;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Put;
import org.apache.log4j.Logger;

import com.hp.xo.uip.cmdb.domain.CiAttribute;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.domain.ViewCondition;
import com.hp.xo.uip.cmdb.domain.ViewDefine;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.service.impl.test.CmdbServiceImpl;
import com.hp.xo.uip.cmdb.service.impl.test.CmdbViewServiceImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlDaoTemplate {
	private static String xmlName="Model.xml";
	private static String relationXmlName="RelationModel.xml";
	private static Logger log=Logger.getLogger(XmlDaoTemplate.class);
    public static  String model_path="model";
    public static  String view_path="view";
    
    //类实例数据存储
    public static  String data_path="data";
    
	public  void writeXml(Map<String,Node> ma){
		writeXml(ma, null);
	}
	public  void writeXml(Map<String,Node> ma,String cluster){
		PrintWriter pw=null;
		try {
			if(cluster!=null && !"".equals(cluster)){
			   xmlName=cluster+"_Model.xml";
			}
			
			File f2=new File(model_path);
			if(!f2.exists()){
				log.info("make dir:"+f2.mkdirs());
			}
			File f=new File(model_path+"/"+xmlName);
			log.info("write node path:"+f.getAbsolutePath());
			if(!f.exists()){
				f.createNewFile();
			}
			pw=new PrintWriter(f,"UTF-8");
			XStream x=new XStream();
			x.alias("Node", Node.class);
			x.alias("Attribute", CiAttribute.class);
			x.toXML(ma, pw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}				
	}

	public  Map<String,Node> readXml(){
		return readXml(null);
	}
	public  Map<String,Node> readXml(String cluster){
		InputStream is=null;
		Map<String,Node> ma =new LinkedHashMap<String,Node>();
		try {
			if(cluster!=null && !"".equals(cluster)){
				   xmlName=cluster+"_Model.xml";
				}
			File f=new File(model_path+"/"+xmlName);
			log.info("read node path:"+f.getAbsolutePath());			
			if(!f.exists()){				
				f.createNewFile();
				List<Node> li_init=new ArrayList<Node>();
				Node n=new Node();
				n.setName("init");
				ma.put(n.getName(), n);
				writeXml(ma);
			}else{
				is=new FileInputStream(f);
				InputStreamReader ir=new InputStreamReader(is,"utf-8");
			 
			 XStream x=new XStream(new DomDriver());
			 x.alias("Node", Node.class);
			 x.alias("Attribute", CiAttribute.class);
			 ma=(Map<String,Node>)x.fromXML(ir); 
			}
		} catch (Exception e) {
			log.error(" "+e.getMessage());
		}finally{
				try {
					if(is!=null)is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}	
		return ma;
	}
//---------relation node------
	public  void writeXmlRelation(Map<String,RelationNode> ma){
		writeXmlRelation(ma, null);
	}
	public  void writeXmlRelation(Map<String,RelationNode> ma,String cluster){
		PrintWriter pw=null;
		try {
			if(cluster!=null && !"".equals(cluster)){
			   xmlName=cluster+"_RelationModel.xml";
			}
			
			File f2=new File(model_path);
			if(!f2.exists()){
				log.info("make dir:"+f2.mkdirs());
			}
			File f=new File(model_path+"/"+relationXmlName);
			log.info("write node path:"+f.getAbsolutePath());
			if(!f.exists()){
				f.createNewFile();
			}
			pw=new PrintWriter(f,"UTF-8");
			XStream x=new XStream();
			x.alias("RelationNode", RelationNode.class);			
			x.toXML(ma, pw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}				
	}

	public  Map<String,RelationNode> readXmlRelation(){
		return readXmlRelation(null);
	}
	public  Map<String,RelationNode> readXmlRelation(String cluster){
		InputStream is=null;
		Map<String,RelationNode> ma =new HashMap<String,RelationNode>();
		try {
			if(cluster!=null && !"".equals(cluster)){
				   xmlName=cluster+"_RelationModel.xml";
				}
			File f=new File(model_path+"/"+relationXmlName);
			log.info("read node path:"+f.getAbsolutePath());			
			if(!f.exists()){				
				f.createNewFile();
				List<RelationNode> li_init=new ArrayList<RelationNode>();
				RelationNode n=new RelationNode();
				n.setName("init");
				ma.put(n.getName(), n);
				writeXmlRelation(ma);
			}else{
				is=new FileInputStream(f);
				InputStreamReader ir=new InputStreamReader(is,"utf-8");
			 
			 XStream x=new XStream(new DomDriver());
			 x.alias("RelationNode", RelationNode.class);
			 ma=(Map<String,RelationNode>)x.fromXML(ir); 
			}
		} catch (Exception e) {
			log.error(" "+e.getMessage());
		}finally{
				try {
					if(is!=null)is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}	
		return ma;
	}
	//--view define-----------------------
	
	public  void writeViewDefXml(ViewDefine vd){
		PrintWriter pw=null;
		try {
			File f2=new File(view_path);
			if(!f2.exists()){
				log.info("make dir:"+f2.mkdirs());
			}
			File f=new File(view_path+"/"+vd.getName()+".xml");
			log.info("write view path:"+f.getAbsolutePath());
			if(!f.exists()){
				f.createNewFile();
			}
			pw=new PrintWriter(f,"UTF-8");
			XStream x=new XStream();
			x.alias("ViewDefine", ViewDefine.class);
			x.alias("ViewCondition", ViewCondition.class);
			x.omitField(ViewDefine.class,"nodes");
			x.omitField(ViewDefine.class,"nodesMap");
			x.omitField(ViewDefine.class,"relationNodes");
			x.omitField(ViewDefine.class,"relationNodesMap");
			x.toXML(vd, pw); 
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}				
	}
	public  List<ViewDefine> readViewDefXmlAll(){
		List<ViewDefine> li=new ArrayList<ViewDefine>();
		File f=new File(view_path);
		log.info("read view path:"+f.getAbsolutePath());
	   try{
		if(!f.exists()){
			log.error("view path not exists!");
			return li;
		}else{
			File[] ff=f.listFiles();
		   for(File file:ff){
			try{   
				if(!file.canRead()||!file.getName().endsWith(".xml")){
					continue;
				}
			InputStream is=new FileInputStream(file);
			InputStreamReader ir=new InputStreamReader(is,"utf-8");		 
		    XStream x=new XStream(new DomDriver());
		    x.alias("ViewDefine", ViewDefine.class);
			x.alias("ViewCondition", ViewCondition.class);
		    li.add((ViewDefine)x.fromXML(ir));
		    ir.close();
		    is.close();
			}catch(Exception e){
				log.error("",e);
			}
		   } 
		}
	   } catch (Exception e) {
		 log.error(" "+e.getMessage());
	   }
		return li;
	}
	public  ViewDefine readViewDefXml(String viewName){
		InputStream is=null;
		ViewDefine vd=null;
		// 临时处理Bug
		if(viewName.indexOf("viewName=")==0){
			viewName=viewName.replace("viewName=","");
		}
		try {
			File f=new File(view_path+"/"+viewName+".xml");
			log.info("get define read view path:"+f.getAbsolutePath());			
			if(!f.exists()){				
				return vd;
			}else{
				is=new FileInputStream(f);
				InputStreamReader ir=new InputStreamReader(is,"utf-8");
			 
			 XStream x=new XStream(new DomDriver());
			 x.alias("ViewDefine", ViewDefine.class);
		     x.alias("ViewCondition", ViewCondition.class);
			 vd=(ViewDefine)x.fromXML(ir);
			 ir.close();
			 is.close();
			}
		} catch (Exception e) {
			log.error(" "+e.getMessage());
		}finally{
				try {
					if(is!=null)is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}	
		return vd;
	}
	public boolean removeView(String viewName){
			File f=new File(view_path+"/"+viewName+".xml");
			log.info("read view path:"+f.getAbsolutePath());			
			if(!f.exists()){				
				return false;
			}else{
				File fr=new File(view_path+"/"+viewName+"_"+System.currentTimeMillis());
//				f.delete();
				return f.renameTo(fr);
			}
	}
	public Map<String,Node> initAttFromFile(Map<String,Node> nodes,File f) throws Exception{
		if(!f.exists()||!f.isFile()){
			log.error(" file["+f.getName()+"] is not exist! path:"+f.getAbsolutePath());
			return nodes;}
		FileReader fr=new FileReader(f);
		BufferedReader br=new BufferedReader(fr);
		String line="";
		Node node=null;
		int seq=10;
		while(line!=null){
		  
		  log.debug("line:["+line+"]");
		  if(line.startsWith("#")){
			  String k=line.substring(1);
			  node=nodes.get(k);
			  seq=10;
		  };
		  if(line.indexOf("=")>0&&!line.startsWith("--")){
			  String s[]=line.split("=");
			  CiAttribute ct=new CiAttribute();
			  ct.setName(s[0].toLowerCase());
			  ct.setLabel(s[1]);
			  ct.setDataType("string");
			  ct.setDefValue("");
			  ct.setOrder(seq);
			  if(node!=null){node.getAttributes().put(ct.getName(), ct);}
			  seq=seq+10;
		  }
		  line=br.readLine();
		}
		return nodes;
	}
	
	
//----------------------------------------------------------------------------
//数据 存储 xml
	
	public  void writeXmlRelationData(List<RelationNode> li,String ciType){
		PrintWriter pw=null;
		try {
			
		    xmlName=ciType+"_RelationData.xml";
			
			File f2=new File(data_path);
			if(!f2.exists()){
				log.info("make dir:"+f2.mkdirs());
			}
			File f=new File(data_path+"/"+xmlName);
			log.info("write node path:"+f.getAbsolutePath());
			if(!f.exists()){
				f.createNewFile();
			}
			pw=new PrintWriter(f,"UTF-8");
			XStream x=new XStream();
			x.alias("RelationNode", RelationNode.class);			
			x.toXML(li, pw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}				
	}

	public  void writeXmlData(List<Node> li,String ciType){
		PrintWriter pw=null;
		try {
			
		    xmlName=ciType+"_Data.xml";
			
			File f2=new File(data_path);
			if(!f2.exists()){
				log.info("make dir:"+f2.mkdirs());
			}
			File f=new File(data_path+"/"+xmlName);
			log.info("write node path:"+f.getAbsolutePath());
			if(!f.exists()){
				f.createNewFile();
			}
			pw=new PrintWriter(f,"UTF-8");
			XStream x=new XStream();
			x.alias("Node", RelationNode.class);			
			x.toXML(li, pw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}				
	}
	
	public  Map<String,Node> readXmlData(String ciType){
		InputStream is=null;
		Map<String,Node> ma =new LinkedHashMap<String,Node>();
		try {
            xmlName=ciType+"_Data.xml";			
			File f2=new File(data_path);
			if(!f2.exists()){
				log.info("make dir:"+f2.mkdirs());
			}
			File f=new File(data_path+"/"+xmlName);
			log.info("read node path:"+f.getAbsolutePath());			
			if(!f.exists()){
				f.createNewFile();
				List<Node> li_init=new ArrayList<Node>();
				Node n=new Node();
				n.setName("init");
				ma.put(n.getName(), n);
				writeXml(ma);
			}else{
				is=new FileInputStream(f);
				InputStreamReader ir=new InputStreamReader(is,"utf-8");
			 
			 XStream x=new XStream(new DomDriver());
			 x.alias("Node", Node.class);
			 x.alias("Attribute", CiAttribute.class);
			 ma=(Map<String,Node>)x.fromXML(ir); 
			}
		} catch (Exception e) {
			log.error(" "+e.getMessage());
		}finally{
				try {
					if(is!=null)is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}	
		return ma;
	}
	
	
	public static void main(String arg[]){
		XmlDaoTemplate xt=new XmlDaoTemplate();
		//node
		CmdbServiceImpl csi=new CmdbServiceImpl();
		List<Node> li= csi.getCiType();		
		Map<String,Node> ma=new HashMap<String,Node>();
		for(Node n:li){
			ma.put(n.getName(), n);
		}		
		File f_host=new File("conf/HostType.txt");
		File f_net=new File("conf/NetworkType.txt");
		try {			
			xt.initAttFromFile(ma, f_host);
//			xt.initAttFromFile(ma, f_net);
		} catch (Exception e) {
			e.printStackTrace();
		}
		xt.writeXml(ma);
//		Map<String,Node> ma_o=xt.readXml();
//		log.debug("node:"+ma+"-"+ma_o);
		
		//relation
//		List<RelationNode> li2=csi.getRelationType();
//		Map<String,RelationNode> ma2=new HashMap<String,RelationNode>();
//		for(RelationNode n:li2){
//			ma2.put(n.getName(), n);
//		}
//		xt.writeXmlRelation(ma2);
//		

//		
//		Map<String,RelationNode> ma2_o=xt.readXmlRelation();
//		log.debug("relationNode:"+ma2+"-"+ma2_o);
		
		//viewDefine
		
//		CmdbViewServiceImpl cv=new CmdbViewServiceImpl();
//		String name1="view-test-111";
//		String name2="view-test-222";
//		ViewDefine vd1= cv.getViewDefineByName("vd1");
//		vd1.setName(name1);
//		ViewDefine vd2= cv.getViewDefineByName("1l");
//		vd2.setName(name2);
//		xt.writeViewDefXml(vd1);
//		xt.writeViewDefXml(vd2);
//		
//		ViewDefine vd11=xt.readViewDefXml(name1);
//		ViewDefine vd22=xt.readViewDefXml(name2);
//		List<ViewDefine> vli=xt.readViewDefXmlAll();
		
		
	}
}
