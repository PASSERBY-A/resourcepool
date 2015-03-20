package com.hp.xo.uip.cmdb.probe.db.avmon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hp.xo.uip.cmdb.domain.Node;

public class AvmonSyncUtil {
	public static String pName = "avmonSync.properties";
    private static Logger log=Logger.getLogger(AvmonSyncUtil.class);
    //实例
    Map<String,String> clasKpiMa=new HashMap<String,String>();
	Map<String,String> subClasKpiMa=new HashMap<String,String>();
	//view 界面显示kpi ，类tooltip显示
	Map<String,List<String>> clasViewKpiMa=new HashMap<String,List<String>>();
	Map<String,List<String>> subclasViewKpiMa=new HashMap<String,List<String>>();
	//View 特定功能kpi
	Map<String,String> viewFuncKpiMa=new HashMap<String,String>();
	
	Map<String,String> clasKpiCodeMa=new HashMap<String,String>();
	Map<String,String> subClasKpiCodeMa=new HashMap<String,String>();
	//实例属性
	HashMap<String, List<String>> clasAttKpiMa=new HashMap<String,List<String>>();
	Map<String,List<String>> subClasAttKpiMa=new HashMap<String,List<String>>();
	//子类与主类关联
	Map<String,String> subReClas=new HashMap<String,String>();
	//主-子类关联 所用关系名称
	private String class_subclas="";
	//kpi,link,s-class,d-class 关系配置 ：kpi名称+关系名称+源类+目标类
    List<String> relations=new ArrayList<String>();
	
	public Map<String, String> getClasKpiMa() {
		return clasKpiMa;
	}
	public void setClasKpiMa(Map<String, String> clasKpiMa) {
		this.clasKpiMa = clasKpiMa;
	}
	public Map<String, String> getSubClasKpiMa() {
		return subClasKpiMa;
	}
	public void setSubClasKpiMa(Map<String, String> subClasKpiMa) {
		this.subClasKpiMa = subClasKpiMa;
	}
	public HashMap<String, List<String>> getClasAttKpiMa() {
		return clasAttKpiMa;
	}
	public void setClasAttKpiMa(HashMap<String, List<String>> clasAttKpiMa) {
		this.clasAttKpiMa = clasAttKpiMa;
	}
	
	public Map<String, String> getClasKpiCodeMa() {
		return clasKpiCodeMa;
	}
	public void setClasKpiCodeMa(Map<String, String> clasKpiCodeMa) {
		this.clasKpiCodeMa = clasKpiCodeMa;
	}
	public Map<String, String> getSubClasKpiCodeMa() {
		return subClasKpiCodeMa;
	}
	public void setSubClasKpiCodeMa(Map<String, String> subClasKpiCodeMa) {
		this.subClasKpiCodeMa = subClasKpiCodeMa;
	}
	public Map<String, List<String>> getSubClasAttKpiMa() {
		return subClasAttKpiMa;
	}
	public void setSubClasAttKpiMa(Map<String, List<String>> subClasAttKpiMa) {
		this.subClasAttKpiMa = subClasAttKpiMa;
	}

	public Map<String, String> getViewFuncKpiMa() {
		return viewFuncKpiMa;
	}
	public void setViewFuncKpiMa(Map<String, String> viewFuncKpiMa) {
		this.viewFuncKpiMa = viewFuncKpiMa;
	}
	public Map<String, String> getSubReClas() {
		return subReClas;
	}
	public void setSubReClas(Map<String, String> subReClas) {
		this.subReClas = subReClas;
	}
		
	public String getClass_subclas() {
		return class_subclas;
	}
	public void setClass_subclas(String classSubclas) {
		class_subclas = classSubclas;
	}
	
	public List<String> getRelations() {
		return relations;
	}
	public void setRelations(List<String> relations) {
		this.relations = relations;
	}
	
	public Map<String, List<String>> getClasViewKpiMa() {
		return clasViewKpiMa;
	}
	public void setClasViewKpiMa(Map<String, List<String>> clasViewKpiMa) {
		this.clasViewKpiMa = clasViewKpiMa;
	}
	public Map<String, List<String>> getSubclasViewKpiMa() {
		return subclasViewKpiMa;
	}
	public void setSubclasViewKpiMa(Map<String, List<String>> subclasViewKpiMa) {
		this.subclasViewKpiMa = subclasViewKpiMa;
	}
	public void paserConf() throws IOException {		
		
		String sp= this.getClass().getClassLoader().getResource(pName).getPath();
		File f=new File(sp);
		if (!f.exists() || !f.isFile()) {
			log.error(" file[" + f.getName() + "] is not exist! path:"
					+ f.getAbsolutePath());
		}
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		Node node = null;
		int seq = 10;
		while (line != null) {
			log.debug("line:[" + line + "]");
			if (line.startsWith("class.")) {
				String keyKpis = line.substring(line.indexOf("=")+1);
//				if(keyKpis.indexOf(":")>0){
				String[] keyKpi=keyKpis.split(":");
//				}
				String ss= line.substring(0,line.indexOf("="));
				String clas = ss.split("\\.")[1];
				clasKpiMa.put(clas,keyKpi[0]);
				if(keyKpi.length>1)clasKpiCodeMa.put(clas,keyKpi[1]);
				
			}else if(line.startsWith("classView.")){
				String viewKpis = line.substring(line.indexOf("=")+1);
				String ss= line.substring(0,line.indexOf("="));
				 String clas = ss.split("\\.")[1];
				if(viewKpis!=null){
				 String[] viewKpi=viewKpis.split("\\;");
				 List<String> li=new ArrayList<String>();
				 for(String v:viewKpi){
					String[] vs=v.split("\\:");
					if(vs!=null&&vs.length>1){
				       li.add(vs[1]);
					}
				 }
				 clasViewKpiMa.put(clas,li);
				}
			}else if(line.startsWith("classAtt.")){
				String[] attKpi=line.substring(line.indexOf("=")+1).split(",");
				String[] s=line.substring(0,line.indexOf("=")).split("\\.");
				if(clasAttKpiMa.get(s[1])!=null){
					clasAttKpiMa.get(s[1]).addAll(toList(attKpi));
				}else{
					clasAttKpiMa.put(s[1], toList(attKpi));
				}
			}else if(line.startsWith("subclass.")){
				String subKeyKpis = line.substring(line.indexOf("=")+1);
				String subKeyKpi[]=subKeyKpis.split("\\:");
				String[] s=line.substring(0,line.indexOf("=")).split("\\.");
				//目前处理 2级包含，例如：subclass.host.hosthba
			  if(s.length>=3){
				String clas = s[1];
				String subClas = s[2];
				subClasKpiMa.put(subClas, subKeyKpi[0]);
				if(subKeyKpi.length>1)subClasKpiCodeMa.put(subClas, subKeyKpi[1]);
				//子类和父类关系
				subReClas.put(s[2], s[1]);				
			  }
			}else if(line.startsWith("subclassView.")){
				String viewKpis = line.substring(line.indexOf("=")+1);
				String ss= line.substring(0,line.indexOf("="));
				 String clas = ss.split("\\.")[1];
				if(viewKpis!=null){
				 String[] viewKpi=viewKpis.split("\\;");
				 List<String> li=new ArrayList<String>();
				 for(String v:viewKpi){
					String[] vs=v.split("\\:");
					if(vs!=null&&vs.length>1){
				       li.add(vs[1]);
					}
				 }
				 subclasViewKpiMa.put(clas,li);
				}
			 }else if(line.startsWith("subclassAtt.")){
				String[] subAttKpi=line.substring(line.indexOf("=")+1).split(",");
				String[] s=line.substring(0,line.indexOf("=")).split("\\.");
				if(subClasAttKpiMa.get(s[s.length-1])==null){
				  subClasAttKpiMa.put(s[s.length-1], toList(subAttKpi));
				}else{
					subClasAttKpiMa.get(s[s.length-1]).addAll(toList(subAttKpi));	
				}
			}else if(line.startsWith("class_suclass")){
				class_subclas=line.substring(line.indexOf("=")+1);
			}else if(line.startsWith("relation.")){
                String s[]=line.substring(line.indexOf("=")+1).split("\\:");
                String cls[]=line.substring(0,line.indexOf("=")).split("\\.");
                //sourceClass,destClass,relationName,kpiName
                String re=cls[1]+","+cls[2]+","+s[0]+","+s[1];
                relations.add(re);
			}else if(line.startsWith("viewFuncKpi.")){
				String s=line.substring(line.indexOf("=")+1);
				String ss[]=line.substring(0,line.indexOf("=")).split("\\.");
				viewFuncKpiMa.put(ss[1], s);
			};
			line = br.readLine();
		}
        log.debug("--");
	}
	public static List<String> toList(String[] strs){
		List<String> li=new ArrayList<String>();
		for(String s:strs){
		   	li.add(s);
		}
		return li;
	}
	public static void main(String arg[]){
		try {
			AvmonSyncUtil as=new  AvmonSyncUtil();
			as.paserConf();
			String s="WWPB";
			String[] ss= s.split("\\:");
			System.out.print(ss);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
