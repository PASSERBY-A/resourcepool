package com.hp.avmon.remote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import oracle.security.o3logon.C1;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.hp.avmonserver.api.IRemoteService;
import com.hp.avmonserver.entity.Alarm;
import com.hp.avmonserver.entity.KpiEvent;
import com.hp.avmonserver.entity.KpiInfo;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.ViewDefine;
import com.hp.xo.uip.cmdb.service.CmdbService;
import com.hp.xo.uip.cmdb.service.CmdbViewService;
/**
 * 其中CiName    CiKpiCode，是指唯一标示ci的kpi值和kpicode， 
例： 主机以kpi : hostName为标示主机唯一的kpi, 则cmdb扫描hostName, 用来建立主机实例，ciName即 kpi:hostName的值。
Avmon侧需要根据CiName    CiKpiCode查询kpi _ current表转换为avmon可识别的moid，进行下一步操作。

1、	得到指定ci列表的 未处理最高级别告警
                   public Alert getHighAlertByCiList(List<String> ciNameList,String ciKpiCode);          

2、	得到指定ci，指定时间段的告警列表 ，
      public List<Alert> getAlertByCi(String ciName,String ciKpiCode,Timestamp beginTime,Timestamp endTime);

3、	得到kpi定义列表
public List<KpiDefine> getKpiDefine(); 

4、	得到指定ci，指定kpi的当前kpi值
public KpiValue getCurKpi(String ciName,String ciKpiCode,String viewKpiCode);

5、	获得指定ci，指定kpi，指定时间段的kpi列表 ，
public List<KpiValue> getHisKpi(String ciName,String ciKpiCode,String viewKpiCode,Timestamp beginTime,Timestamp endTime);

 * @author qiaoju
 */
@Service
public class AvmonRemoteService implements IAvmonRemoteService{
	  private Logger log=Logger.getLogger(AvmonRemoteService.class);
	  public List<KpiDefine> kpis=null;
      @Autowired
      IRemoteService avmonRemoteServiceProxy;
      @Autowired
      CmdbService cmdbServiceProxy;
      @Autowired
      CmdbViewService cmdbViewServiceProxy;
          
      
      public KpiDefine getKpiDef(String kpiCode) throws RemoteException{
    	  if(kpis==null){
    		  kpis=getKpiDefine();    		    
    	  }
    	  for(KpiDefine k:kpis){
    		  if(kpiCode.equals(k.getCode())){
    			  return k;
    		  }
    	  }
    	  return null;
      }
      /**
       * 得到指定ci的最高级别告警, ciNames 需要是同一类型的ci
       */
      public List<Alert> getHighAlertByCiList(List<String> ciNames,String kpiCode) throws RemoteException{
    	  List<Alarm> li= avmonRemoteServiceProxy.getHighAlertByCiList(ciNames, kpiCode);
    	  List<Alert> lir=new ArrayList<Alert>();
    	  for(Alarm a:li){
    		  Alert at=new Alert();
    		  at.setContent(a.getContent());
    		  at.setFirstOccurTime(a.getFirstOccurTime());
    		  at.setGrade(a.getGrade());
    		  at.setLastOccurTime(a.getLastOccurTime());
    		  at.setNodeName(a.getMoId());
    		  at.setStatus(String.valueOf(a.getStatus()));
    		  at.setTitle(a.getTitle());
    		  at.setNodeType(cmdbServiceProxy.getClassByKpi(kpiCode));
    		  lir.add(at);
    	  }
    	  return lir;
      }
      public List<Alert> getHighAlertByCiList(List<String> ciNames,String kpiCode,String ciType) throws RemoteException{
    	  List<Alarm> li= avmonRemoteServiceProxy.getHighAlertByCiList(ciNames, kpiCode);
    	  List<Alert> lir=new ArrayList<Alert>();
    	  for(Alarm a:li){
    		  Alert at=new Alert();
    		  at.setContent(a.getContent());
    		  at.setFirstOccurTime(a.getFirstOccurTime());
    		  at.setGrade(a.getGrade());
    		  at.setLastOccurTime(a.getLastOccurTime());
    		  at.setNodeName(a.getMoId());
    		  at.setStatus(String.valueOf(a.getStatus()));
    		  at.setTitle(a.getTitle());
    		  at.setNodeType(ciType);
    		  lir.add(at);
    	  }
    	  return lir;
      }
      public List<Alert> getHighAlertByCiList_single(List<String> ciNames,String kpiCode) throws RemoteException{
    	  List<Alarm> li= avmonRemoteServiceProxy.getHighAlertByCiList(ciNames, kpiCode);
    	  List<Alert> lir=new ArrayList<Alert>();
    	  String names="";
    	  for(Alarm a:li){
    		  log.debug("----alarm:"+a.getMoId());
    		  if(names.indexOf("["+a.getMoId()+"]")<0){
    		   Alert at=new Alert();
    		   at.setContent(a.getContent());
    		   at.setFirstOccurTime(a.getFirstOccurTime());
    		   at.setGrade(a.getGrade());
    		   at.setLastOccurTime(a.getLastOccurTime());
    		   at.setNodeName(a.getMoId());
    		   at.setStatus(String.valueOf(a.getStatus()));
    		   at.setTitle(a.getTitle());
    		   at.setNodeType(cmdbServiceProxy.getClassByKpi(kpiCode));
    		   lir.add(at);
    		   log.debug("----alarm name:"+at.getNodeName()+", grade:"+at.getGrade());
    		   names=names+"["+a.getMoId()+"]";
    		  }
    	  }
    	  log.debug(lir.size());
    	  return lir;
    	  
      }
      public List<Alert> getHighAlertByCiList_single(List<String> ciNames,String kpiCode,String ciType) throws RemoteException{

    	  List<Alarm> li= avmonRemoteServiceProxy.getHighAlertByCiList(ciNames, kpiCode);
    	  List<Alert> lir=new ArrayList<Alert>();
    	  String names="";
    	  for(Alarm a:li){
    		  log.debug("----alarm:"+a.getMoId());
    		  if(names.indexOf("["+a.getMoId()+"]")<0){
    		   Alert at=new Alert();
    		   at.setContent(a.getContent());
    		   at.setFirstOccurTime(a.getFirstOccurTime());
    		   at.setGrade(a.getGrade());
    		   at.setLastOccurTime(a.getLastOccurTime());
    		   at.setNodeName(a.getMoId());
    		   at.setStatus(String.valueOf(a.getStatus()));
    		   at.setTitle(a.getTitle());
    		   at.setNodeType(ciType);
    		   lir.add(at);
    		   log.debug("----alarm name:"+at.getNodeName()+", grade:"+at.getGrade());
    		   names=names+"["+a.getMoId()+"]";
    		  }
    	  }
    	  log.debug(names);
    	  return lir;
    	  
      }
      public Map<String,List<String>> getViewContentCi(String viewName){
    	  Map<String,List<String>> mar=new HashMap<String,List<String>>();
    	try{
    	 ViewDefine vd= cmdbViewServiceProxy.getViewDataByName(viewName);
    	 String con= vd.getContent();
//    	 log.debug(con);
    	 Document doc = DocumentHelper.parseText(con);
    	 Element rel=  doc.getRootElement();
    	 rel.elements();
    	 
    	 Map<String,String> maci=new HashMap<String,String>(); 
    	 List<Element> li= rel.elements("element");
    	 for(Element e:li){
    		 if(e.attribute("isEdge")!=null &&"false".equals(e.attribute("isEdge").getValue())){
    		   if(e.attribute("ciid")!=null){
    			  String ciid=e.attribute("ciid").getValue();
    			  String citype=""; 
    			  //铁总 处理已修改label值 ，用id查ci, 铁总目前只处理 主机 交换机
    			  Node n=cmdbServiceProxy.getCiByCiId("Host", Long.parseLong(ciid));
  				  if(n!=null&&n.getId()!=null){
  					 citype=n.getDerivedFrom();		 
  				  }else{
  					 n=cmdbServiceProxy.getCiByCiId("EnetSwitch", Long.parseLong(ciid));
  					 if(n!=null)citype=n.getDerivedFrom();
  				  }
  				  if(n!=null){
    		          maci.put(ciid,n.getName());    		          
    		      }
//  				  else{
//    		    	 maci.put(ciid,e.attribute("label").getValue());
//    		     	 Element conf=rel.element("CiKpiConfig");
//    		     	 List<Element> lic=conf.elements("CiKpiSet");
//    		     	 for(Element e1:lic){
//    		     		 String ciid1=e1.attribute("ciid").getValue();
//    		     		 if(ciid.equals(ciid1)){
//    		     		 if(e.attribute("citype")!=null){
//    		     		     citype=e.attribute("citype").getValue();
//    		     		 }}
//    		     	 }
//    		      }
  				  
  				 List<String> cinames=mar.get(citype);
	    		 if(cinames!=null){
	    			 cinames.add(maci.get(ciid));
	    		 }else{
	    			 cinames=new ArrayList<String>();
	    			 cinames.add(maci.get(ciid));
	    			 mar.put(citype, cinames);
	    		 }
    		       
   	    		 log.debug(ciid+"--"+citype+"--"+maci.get(ciid));
    		     }    		   
    		 }
    	 }    	 
    	

    	  }catch(Exception e){
    		  log.error("",e);
    		  e.printStackTrace();
    	  }
     	 return mar;
      }
    	        
      public List<Alert> getHighAlertByView_single(String viewName) throws RemoteException{
    	  Map<String,List<String>> ma= getViewContentCi(viewName);
    	  List<Alert> lir=new ArrayList<Alert>();
    	  for(String k:ma.keySet()){
    		   String avmonKpi=cmdbServiceProxy.getKpiByClass(k);
    		   //须配置avmonSync.properties 中类唯一标示的kpi的code才可从avmon中查询告警
    		   log.debug(k+"--"+avmonKpi);
    		   if(avmonKpi!=null){
    		    List<Alert> lia=getHighAlertByCiList_single(ma.get(k),avmonKpi,k);
    		    log.debug(ma.get(k).toArray()+","+lia.size());
    		    lir.addAll(lia);
    		   }
    	  }
    	  return lir;    	  
      }
      /**
       * 得到指定ci的 时间段内告警
       */
      public List<Alert> getAlertByCi(String ciName,String kpiCode,Timestamp start,Timestamp end) throws RemoteException{
    	  List<Alarm> li= avmonRemoteServiceProxy.getAlertByCi(ciName, kpiCode,start,end);
    	  List<Alert> lir=new ArrayList<Alert>();
    	  for(Alarm a:li){
    		  Alert at=new Alert();
    		  at.setContent(a.getContent());
    		  at.setFirstOccurTime(a.getFirstOccurTime());
    		  at.setGrade(a.getGrade());
    		  at.setLastOccurTime(a.getLastOccurTime());
    		  at.setNodeName(a.getMoId());
    		  at.setStatus(String.valueOf(a.getStatus()));
    		  at.setTitle(a.getTitle());
    		  at.setNodeType(cmdbServiceProxy.getClassByKpi(kpiCode));
    		  lir.add(at);
    	  }
    	  return lir;  
      }
      /**
       * 得到指定ci的 时间段内告警
       */
      public List<Alert> getAlertByCi(String ciName,String kpiCode,int days) throws RemoteException{
    	  GregorianCalendar cl=new GregorianCalendar();
    	  Timestamp te=new Timestamp(cl.getTimeInMillis());
    	  cl.add(Calendar.DATE,days*-1);
    	  Timestamp ts=new Timestamp(cl.getTimeInMillis());
    	  return getAlertByCi(ciName,kpiCode,ts,te);
      }
      /**
       * 得到指定ci的 最近几条告警
       */
      public List<Alert> getRecentAlertByCi(String ciName,String kpiCode,int rows) throws RemoteException{
    	  //目前取7天里 最近几条数据
    	  List<Alert> lia= getAlertByCi(ciName, kpiCode, 7);
    	  List<Alert> li=new ArrayList<Alert>();
    	  if(lia!=null&&lia.size()<rows){
    		  li=lia;
    		  }else{
    		  li=lia.subList(0, rows);	  
    		  }
    	  return li;
      }
      
      public List<KpiDefine> getKpiDefine() throws RemoteException{
    	   List<KpiInfo> li= avmonRemoteServiceProxy.getKpiDefine();
    	   List<KpiDefine> lir=new ArrayList<KpiDefine>();
    	   for(KpiInfo ki:li){
    	   KpiDefine k=new KpiDefine();
    	   k.setAggMethod(ki.getAggMethod());
    	   k.setCaption(ki.getCaption());
    	   k.setCode(ki.getCode());
    	   k.setDateType(ki.getDataType());
    	   k.setIsStore(ki.getIsStore());
    	   k.setName(ki.getName());
    	   k.setCaption(ki.getCaption());
    	   k.setPrecision(ki.getPrecision());
    	   k.setStorePeriod(ki.getStorePeriod());
    	   k.setUnit(ki.getUnit());
    	   lir.add(k);
    	   }
    	   return lir;
      } 

      /**
       * 4、	得到指定ci，指定kpi的当前kpi值 
       *  ping主机通断（os ping） 指标值 ，  ALIVE：表示通，"":表示无指标，其他值表示不通
       *  
       * @param ciName
       * @param ciKpiCode
       * @param viewKpiCode
       * @return
     * @throws RemoteException 
       */
      public KpiValue getCurKpi(String ciName,String ciKpiCode,String viewKpiCode) throws RemoteException{
    	  KpiEvent ke=avmonRemoteServiceProxy.getCurKpi(ciName, ciKpiCode, viewKpiCode);    	  
    	  return getKpiValue(ke,ciKpiCode,ciName);
      }
      /**
       * 查询指定类型 显示kpi
       */
      public List<KpiValue> getViewCurKpi(String ciName,String ciKpiCode,String ciType) throws RemoteException{
    	  List<KpiValue> lir=new ArrayList<KpiValue>();
    	  List<String> li=cmdbServiceProxy.getClassViewKpi(ciType);
    	  //添加告警数显示
    	  Map<String ,Integer> ma=null;
    	  ma=getAlertLevelCountByCi(ciName, ciKpiCode);
    	  String high="0";
    	  Integer count=0;
    	 if(ma!=null){
    	  for(String key:ma.keySet()){
    		  if(key.compareTo(high)>0){
    			  high=key;
    		  }
    		  count=count+ma.get(key);
    	  }
    	  KpiValue alertK=new KpiValue();
    	  alertK.setKpiLabel(Alert.getLevelDes(high)+"告警");
    	  alertK.setValue(ma.get(high)+"["+count+"]");
    	  lir.add(alertK);
    	 }
    	 
    	 //cabinetcode:机柜 ， ustart：所在U 
    	 if("Host".equals(ciType)){
    	  Node n=cmdbServiceProxy.getCiByName(ciType, ciName);
    	  if(n!=null){
    	   String cab=n.getAttributes().get("cabinetcode").getValue();
    	   String u=n.getAttributes().get("ustart").getValue();
    	   KpiValue alertK=new KpiValue();
     	   alertK.setKpiLabel("位置");
     	   alertK.setValue(cab+"_"+u+"U");
     	   lir.add(alertK);
    	  }
    	 }
    	 
    	  for(String s:li){
//    		KpiEvent ke=avmonRemoteServiceProxy.getCurKpi(ciName, ciKpiCode, s);  
    	    List<KpiEvent> kel=avmonRemoteServiceProxy.getCurInstanceKpi(ciName, ciKpiCode, s);
    	   for(KpiEvent ke:kel){ 
    	    KpiValue kv=getKpiValue(ke,ciKpiCode,ciName);
    	    if(kv!=null){
     	       log.debug(kv.getNodeName()+"-"+kv.getKpiCode()+"-"+kv.getValue());
     	       lir.add(kv);
     	     }else{
     	    	 //设置默认值 
     	    	KpiValue kt=new KpiValue();
     	    	kt.setKpiCode(s);
     	    	kt.setKpiName(s);
     	    	kt.setKpiLabel(s);
     	    	kt.setNodeName(ciName);
     	    	kt.setNodeType(ciType);
     	    	kt.setValue("");
     	    	log.debug("kpiValue is null. ciName:"+ciName+",ciKpiCode:"+ciKpiCode+", viewKpi:"+s); 
     	     }
    	   }
    	  }
    	  return lir;
      }
      
      public List<KpiValue> getCurKpi(List<String> ciNames,String ciKpiCode,String viewKpiCode) throws RemoteException{
    	  List<KpiValue> li=new ArrayList<KpiValue>();
    	  log.debug("avmonRemoteServiceProxy:"+avmonRemoteServiceProxy);    	  
    	  for(String ciName:ciNames){    		  
    		 log.debug(ciName+","+ciKpiCode+","+viewKpiCode); 
    	     KpiEvent ke=avmonRemoteServiceProxy.getCurKpi(ciName, ciKpiCode, viewKpiCode);
    	     KpiValue kv=getKpiValue(ke,ciKpiCode,ciName);
    	     if(kv!=null){
    	       log.debug(kv.getNodeName()+"-"+kv.getKpiCode()+"-"+kv.getValue());
    	       li.add(kv);
    	     }else{
    	    	 //设置默认值 
      	    	KpiValue kt=new KpiValue();
      	    	kt.setKpiCode(viewKpiCode);
      	    	kt.setKpiName(viewKpiCode);
      	    	kt.setKpiLabel(viewKpiCode);
      	    	kt.setNodeName(ciName);
      	    	kt.setNodeType(cmdbServiceProxy.getClassByKpi(ciKpiCode));
//      	    	kt.setValue("DOWN");
//      	    	kt.setValue("-");
//      	    	kt.setValue("ALIVE");
      	    	li.add(kt);// 测试 
      	    	log.debug("kpiValue is null. ciName:"+ciName+",ciKpiCode:"+ciKpiCode+", viewKpi:"+viewKpiCode); 
      	     
    	     }    	     
    	      
    	  }    	  
    	  return li;
      }
      public KpiValue getKpiValue(KpiEvent ke,String ciKpiCode,String ciName) throws RemoteException{
    	  KpiValue kv=null; 
    	  if(ke!=null){
    		  kv=new KpiValue(); 
    	  kv.setId(ke.getId());
    	  kv.setCreateTime(ke.getCreateTime());
    	  kv.setDescription(ke.getDescription());
    	  kv.setHostName(ke.getHostName());
    	  kv.setInstance(ke.getInstance());
    	  kv.setIp(ke.getIp());
    	  kv.setKpiCode(ke.getKpiCode());
    	  KpiDefine k=getKpiDef(kv.getKpiCode());
    	  if(k!=null){
    		  kv.setKpiName(k.getName());
    		  kv.setKpiLabel(k.getCaption());
    		  kv.setUnit(k.getUnit());
    	  }
    	  kv.setKpiTime(ke.getKpiTime());
    	  if(!"".equals(ciName)&&ciName!=null){
    	     kv.setNodeName(ciName);
    	  }
    	  if(ke.getValue()!=null){
    	     kv.setValue(ke.getValue());
    	  }
    	  kv.setNodeType(cmdbServiceProxy.getClassByKpi(ciKpiCode));
    	  }
    	  return kv;
      }
      /**
       * 5、	获得指定ci，指定kpi，指定时间段的kpi列表 ，
       * @param ciName
       * @param ciKpiCode
       * @param viewKpiCode
       * @param beginTime
       * @param endTime
       * @return
     * @throws RemoteException 
       */
      public List<KpiValue> getHisKpi(String ciName,String ciKpiCode,String viewKpiCode,Timestamp beginTime,Timestamp endTime) throws RemoteException{
            List<KpiEvent> li=avmonRemoteServiceProxy.getHisKpi(ciName, ciKpiCode, viewKpiCode, beginTime, endTime);
            List<KpiValue> lik=new ArrayList<KpiValue>();
            for(KpiEvent ke:li){
            	lik.add(getKpiValue(ke,ciKpiCode,""));
            }
            return lik;
      }
      /**
      * 获得指定设备的告警总数
      **/
      public int getAlertCountByCi(String ciName, String kpiCode) throws RemoteException{
    	  return avmonRemoteServiceProxy.getAlertCountByCi(ciName, kpiCode);
      };
      /**
      * 获得指定设备的各级别 告警数量
         Map<级别，数量>
      **/
      public Map<String,Integer> getAlertLevelCountByCi(String ciName, String kpiCode) throws RemoteException{
    	  return avmonRemoteServiceProxy.getAlertLevelCountByCi(ciName, kpiCode);
      };
      /**
      * 获得指定设备的多实例kpi列表
     * @throws RemoteException 
      **/
      public List<KpiValue> getCurInstanceKpi(String ciName,String ciKpiCode,String viewKpiCode) throws RemoteException{
    	  List<KpiEvent> li=avmonRemoteServiceProxy.getCurInstanceKpi(ciName, ciKpiCode, viewKpiCode);
    	  List<KpiValue> lir=new ArrayList<KpiValue>();
    	  for(KpiEvent k:li){
    		  lir.add(getKpiValue(k,ciKpiCode,ciName));
    	  }
    	  return lir;
      };
 
      
      public List<KpiValue> getViewFuncCurKpi(List<String> ciNames,String ciKpiCode,String viewFunc) throws RemoteException{
    	  return getCurKpi(ciNames, ciKpiCode,cmdbServiceProxy.getViewFuncKpi(viewFunc));
      }
      /**
       * 
       */
      public void test() throws RemoteException{
    	  String ciName="simtest";
    	  List<String> ciNames=new ArrayList<String>();
    	  ciNames.add("simtest");
    	  ciNames.add("xovm08");
    	  String kpiCode="1002001001";
    	  List<Alarm> li1=avmonRemoteServiceProxy.getHighAlertByCiList(ciNames,kpiCode);
    	  
    	  GregorianCalendar cl=new GregorianCalendar();
    	  Timestamp te=new Timestamp(cl.getTimeInMillis());
    	  cl.add(Calendar.DATE,-7);
    	  Timestamp ts=new Timestamp(cl.getTimeInMillis());    	  
    	  List<Alarm> li2=avmonRemoteServiceProxy.getAlertByCi(ciName, kpiCode, ts, te);
    	  
    	  Alarm a=new Alarm();    	  
    	  a.getTitle();
    	  
// 	    public static final int GRADE_CRITICAL = 5;
//	    public static final int GRADE_MAJOR = 4;
//	    public static final int GRADE_MINOR = 3;
//	    public static final int GRADE_WARN = 2;
//	    public static final int GRADE_INFO = 1;

    	  a.getGrade();
    	  a.getFirstOccurTime();
    	  a.getLastOccurTime();
    	  a.getContent();
//    	    public static final int STATUS_UNKNOWN = -1;
//    	    public static final int STATUS_NEW = 0;
//    	    public static final int STATUS_AKNOWLEDGED = 1;
//    	    public static final int STATUS_FORWARD = 2;
//    	    public static final int STATUS_CLEAR = 9;
    	    
    	  a.getStatus();
    	  a.getMoId();
    	  
    	  
//    	  avmonRemoteServiceProxy.getCurKpi(arg0, arg1, arg2);
//    	  avmonRemoteServiceProxy.getHisKpi(arg0, arg1, arg2, arg3, arg4);
    	  avmonRemoteServiceProxy.getKpiDefine();
      }
      
      public static void main(String arg[]){
    	  ClassPathXmlApplicationContext cxa=new ClassPathXmlApplicationContext("applicationContext.xml");
    	  cxa.getBean("avmonRemoteServiceProxy");
    	  String[] ss=cxa.getBeanDefinitionNames();
    	  for(String s:ss){
    	    System.out.println(s);
    	  }
    	  IAvmonRemoteService as=(IAvmonRemoteService)cxa.getBean("avmonRemoteService");
    	  try {
//			as.test();
    		  String ciName="simtest";
        	  List<String> ciNames=new ArrayList<String>();
//        	  ciNames.add("simtest");
        	  ciNames.add("localhost.localdomain");
        	  ciNames.add("xovm08");
        	  String kpiCode="1002001001";
//			as.getAlertByCi(ciName, kpiCode, 3);
//			as.getHighAlertByCiList(ciNames, kpiCode);
//			as.getHighAlertByCiList_single(ciNames, kpiCode);
			
//			as.getKpiDefine();
			//"osPing" "1001022001" "ping主机网络通断状态"
			//"bakStatus" 9528001001 主机主备状态
			String viewKpiCode="1001022001";
//			List<KpiValue> li=as.getViewFuncCurKpi(ciNames, kpiCode, "osPing");
//			List<KpiValue> li2=as.getViewFuncCurKpi(ciNames, kpiCode, "bakStatus");
//			System.out.println(li.size()+"--"+li2.size());
//			as.getCurKpi(ciNames, kpiCode, kpiCode);
			
			
//			String viewName="topo";
//			as.getHighAlertByView_single("test");
//			as.getViewCurKpi("xovm08", kpiCode, "Host");
	        as.getAlertCountByCi(ciName, kpiCode);
	        as.getAlertLevelCountByCi(ciName, kpiCode);
	        as.getCurInstanceKpi(ciName, kpiCode, viewKpiCode);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	  
//    	 String rmiUrl="rmi://16.159.216.151:9998/AvmonServer";
//  		 IRemoteService remoteService;
//		try {
//		  remoteService = (IRemoteService) Naming.lookup(rmiUrl);
//  		  List<KpiInfo> kpiList=remoteService.getKpiDefine();
//          for(KpiInfo kpi:kpiList){
//          	System.out.format("code=%s,name=%s\n",kpi.getCode(),kpi.getName());
//          }
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
       }
}
