package com.hp.avmon.modelView.service.query.xj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.hp.xo.uip.cmdb.domain.CiAttribute;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.service.CmdbService;
import com.hp.xo.uip.cmdb.service.CmdbViewService;

@Service
//implements CmdbQueryService
public class CmdbQueryServiceImpl {
	Logger log=Logger.getLogger(CmdbQueryServiceImpl.class);
	private String conn=" -- ";
	@Autowired
    public CmdbViewService cmdbViewServiceProxy;
	@Autowired
	public CmdbService cmdbServiceProxy;
	
	public CmdbViewService getCmdbViewServiceProxy() {
		return cmdbViewServiceProxy;
	}

	public void setCmdbViewServiceProxy(CmdbViewService cmdbViewServiceProxy) {
		this.cmdbViewServiceProxy = cmdbViewServiceProxy;
	}

	
	public CmdbService getCmdbServiceProxy() {
		return cmdbServiceProxy;
	}

	public void setCmdbServiceProxy(CmdbService cmdbServiceProxy) {
		this.cmdbServiceProxy = cmdbServiceProxy;
	}

	/**
     * MACHINE_NET_IP=系统网络接口IP地址
     * CSComputerName=主机名
     * Host:10.238.159.70:7604875155558999
     * @return
     * @throws CmdbException 
     * 主机-磁盘
     * 主机-hba-zone-storageNetPort-storage
     */
	public List<Node> getHost(String cscomputername,String machine_net_ip) throws CmdbException {
		List<Map> conditions=new ArrayList<Map>();
		if(cscomputername!=null&&!"".equals(cscomputername)){
		   Map<String,Object> maf=new HashMap<String,Object>();
		   maf.put("family", "cf");
		   maf.put("qualifier","cscomputername");
		   maf.put("compareOp", CompareOp.EQUAL);
		   maf.put("value", cscomputername);
		   conditions.add(maf);
		}
		List<Node> li= this.cmdbServiceProxy.getCiByCondition("Host", conditions, false);
		List<Node> li_re=new ArrayList<Node>();
		if(machine_net_ip!=null&&!"".equals(machine_net_ip)){
		 for(Node n:li){
			String mni=n.getAttributes().get("machine_net_ip")==null?"":n.getAttributes().get("machine_net_ip").toString();
			if(mni.contains(machine_net_ip)){
				li_re.add(n);
			}
		 }
		}else{
			li_re=li;
		}
	for(Node n:li_re){
		 //补充磁盘信息
		 String d[]={"Host","HostDisk"};	
		 List<Node> lid=cmdbViewServiceProxy.getNodeByRelation("Host", n.getId(), "linked", false, 1, d);
		 String sd="";
		 for(Node nd:lid){
			 //PV_NAME=PV名称 nd.getAttributes().get("pv_name").getValue()
			 if("HostDisk".equals(nd.getDerivedFrom())){sd=nd.getName()+"; "+sd;}
		 }
		 log.debug(" HostDisk info: "+sd);
		 CiAttribute ca=new CiAttribute();
		 ca.setName("HostDisk");ca.setLabel("主机磁盘");ca.setValue(sd);
		 n.getAttributes().put(ca.getName(), ca);
		 
		 //补充hba卡信息
		 String hba[]={"Host","HostHba"};	
		 List<Node> lihba=cmdbViewServiceProxy.getNodeByRelation("Host", n.getId(), "linked", false, 1, hba);
		 String shba="";
		 for(Node nhba:lihba){
			 if(!"HostHba".equals(nhba.getDerivedFrom())){
				 continue;
			 }
			 //zone-storageNetPort-storage信息
			 shba=getHbaRelatedInfo(nhba)+shba;
		 }
		 log.debug(" HostHba related info: "+shba);
		 CiAttribute cahba=new CiAttribute();
		 cahba.setName("HostHba");cahba.setLabel("主机HBA关联");cahba.setValue(shba);
		 n.getAttributes().put(cahba.getName(), cahba);
	}
		return li_re;
	}
    /**
     * 得到hba卡的关联信息 -zone-storage
     * @param nhba
     * @return
     */
	public String getHbaRelatedInfo(Node nhba){
		//zone-storageNetPort-storage信息
		 String zone[]={"SanZoneSet","HostHba"};
		 List<Node> lizone=cmdbViewServiceProxy.getNodeByRelation("HostHba",nhba.getId(), "linked", true, 1, zone);
		 String szone="";
		 for(Node nzone:lizone){
			 if(!"SanZoneSet".equals(nzone.getDerivedFrom())){
				 continue;
			 }
			 String sport[]={"SanZoneSet","StorageNetPort"};
			 List<Node> lisport=cmdbViewServiceProxy.getNodeByRelation("SanZoneSet",nzone.getId(), "linked", false, 1, sport);
			 String ssport="";
			 for(Node nport:lisport){
				 if(!"StorageNetPort".equals(nport.getDerivedFrom())){
					 continue;
				 }
				 String sto[]={"StorageNetPort","Storage"};
				 List<Node> listo=cmdbViewServiceProxy.getNodeByRelation("StorageNetPort",nport.getId(), "linked", true, 1, sto);
				 String sst="";
				 for(Node nst:listo){
					 if(!"Storage".equals(nst.getDerivedFrom())){
						 continue;
					 }
					 sst="storage:"+nst.getName()+" "+sst;
				 }
				 ssport="storagePortWWPN:"+nport.getName()+conn+sst+"; "+ssport;
			 }
			 szone="zone:"+nzone.getName()+conn+ssport+"\r\n "+szone;
		 }
		 
		 //HBA_NAME=HBA卡名称 
		 //HBA_WWPN=HBA卡端口号
		 String conn1="";
		 if(szone!=null&&!"".equals(szone)){conn1=" \r\n "+szone;}
		 String shba="[HBA:"+nhba.getName()+",HBA_WWPN:"+nhba.getAttributes().get("hba_wwpn").getValue()+conn+conn1+"]\r\n";
		 return shba;
	}
	
	/**
     * 得到hba卡的关联信息 -host
     * @param nhba
     * @return
     */
	public String getHbaRelatedInfo_host(Node nhba){
		//hba- host 信息
		 String host[]={"Host","HostHba"};
		 List<Node> lihost=cmdbViewServiceProxy.getNodeByRelation("HostHba",nhba.getId(), "linked", true, 1, host);
		 String shost="";
		 for(Node n:lihost){
			 if(!"Host".equals(n.getDerivedFrom())){
				 continue;
			 }			 
			 shost="Host:"+n.getAttributes().get("cscomputername").getValue()+" "+shost;
		 }		 
		 //HBA_NAME=HBA卡名称 
		 //HBA_WWPN=HBA卡端口号
		 String shba="HBA_WWPN:"+nhba.getAttributes().get("hba_wwpn").getValue()+conn
		             +"HBA_NAME:"+nhba.getName()+shost;
		 return shba;
	}
	/**
     * 得到storageNetPort的关联信息 -storage
     * @param nhba
     * @return
     */
	public String getStoNetPortRelatedInfo(Node nsto){
		//storageNetPort-storage信息
		 String sto[]={"Storage","StorageNetPort"};
		 List<Node> listo=cmdbViewServiceProxy.getNodeByRelation("StorageNetPort",nsto.getId(), "linked", true, 1, sto);
		 String ssto="";
		 for(Node n:listo){
			 if(!"Storage".equals(n.getDerivedFrom())){
				 continue;
			 }			 
			 ssto="Storage:"+n.getName()+" "+ssto;
		 }
		 String sport="StorageNetPort:"+nsto.getName()+conn
		             +"Storage:"+ssto;
		 return sport;
	}
	
    /**
     * 获得zone 及关联信息
     * @param zoneName
     * @param zoneConfig
     * @return
     * @throws CmdbException
     */
	public List<Node> getSanZone(Long sanSwitchID,String zoneName,String zoneConfig) throws CmdbException{
		List<Map> conditions=new ArrayList<Map>();
//		if(zoneName!=null&&!"".equals(zoneName)){
//		   Map<String,Object> maf=new HashMap<String,Object>();
//		   maf.put("family", "cf");
//		   maf.put("qualifier","zoneName");
//		   maf.put("compareOp", CompareOp.EQUAL);
//		   maf.put("value", zoneName);
//		   conditions.add(maf);
//		}
 		List<Node> li=new ArrayList<Node>();
		if(sanSwitchID==null){
		   li= this.cmdbServiceProxy.getCiByCondition("SanZoneSet", conditions, false);
		}else{
		  String typeFilter[]={"San","SanZoneSet"};
		  li= cmdbViewServiceProxy.getNodeByRelation("San", sanSwitchID, "linked", false, 1, typeFilter);
		}
		List<Node> li_re=new ArrayList<Node>();
		if(zoneName!=null&&!"".equals(zoneName)){
			List<Node> li_1=new ArrayList<Node>();	
		 for(Node n:li){
			String mni=n.getName();
			if(mni.contains(zoneName)){
				li_1.add(n);
			}	
		 }
		 li=li_1;
		}else if(zoneConfig!=null&&!"".equals(zoneConfig)){
			 for(Node n:li){
					String mni=n.getAttributes().get("zoneconfig")==null?"":n.getAttributes().get("zoneconfig").getValue();
					if(mni.contains(zoneConfig)){
						li_re.add(n);
					}
				 }
		}else{
			li_re=li;
		}
		
		for(Node n:li_re){
			//补充hba-host storage
			 String hba[]={"SanZoneSet","HostHba","StorageNetPort"};
			 List<Node> lis=cmdbViewServiceProxy.getNodeByRelation("SanZoneSet", n.getId(), "linked", false, 1, hba);
			 String shba="";
			 String ssto="";
			 for(Node ns:lis){
				 if("HostHba".equals(ns.getDerivedFrom())){
					shba=getHbaRelatedInfo_host(ns)+"\r\n"+shba; 					
					log.debug(" HostHba-host related info: "+shba);
				 }
                 if("StorageNetPort".equals(ns.getDerivedFrom())){
                	ssto=getStoNetPortRelatedInfo(ns)+"\r\n"+ssto; 					
 					log.debug(" StoragePort-storage related info: "+ssto);
				 }				 
			 }
			 CiAttribute cahba=new CiAttribute();
		     cahba.setName("HostHba");cahba.setLabel("HBA关联");cahba.setValue(shba);
			 n.getAttributes().put(cahba.getName(), cahba);
			 
			 CiAttribute casto=new CiAttribute();
		     casto.setName("StorageNetPort");casto.setLabel("存储关联");casto.setValue(ssto);
			 n.getAttributes().put(casto.getName(), casto);
			 
		}
		return li_re;
	}
	
	/**
	 * 查询hba卡
	 * 系统属性  name:10.238.230.29_/dev/fcd2, id:7604875155349990, rowKey:HostHba_7604875155349990
	 * 扩展属性 举例：
	 * HBA卡名称	 HBA_NAME : /dev/fcd0
   	   HBA卡位置	HBA_LOCATION:4
	   HBA卡端口速率 HBA_SPEED:	2Gb
	   HBA卡所属主机名 HBA_HOSTNAME:	xjscm02
	   HBA卡端口号 HBA_WWPN:	0x5001438001312666
	 * @throws CmdbException 
	 */
	public List<Node> getHostHba(String hba_hostname,String hba_wwpn,String hba_name) throws CmdbException {
		List<Map> conditions=new ArrayList<Map>();
		if(hba_hostname!=null&&!"".equals(hba_hostname)){
		   Map<String,Object> maf=new HashMap<String,Object>();
		   maf.put("family", "cf");
		   maf.put("qualifier","hba_hostname");
		   maf.put("compareOp", CompareOp.EQUAL);
		   maf.put("value", hba_hostname);
		   conditions.add(maf);
		}
		if(hba_wwpn!=null&&!"".equals(hba_wwpn)){
			   Map<String,Object> maf=new HashMap<String,Object>();
			   maf.put("family", "cf");
			   maf.put("qualifier","hba_wwpn");
			   maf.put("compareOp", CompareOp.EQUAL);
			   maf.put("value", hba_wwpn);
			   conditions.add(maf);
			}
		if(hba_name!=null&&!"".equals(hba_name)){
			   Map<String,Object> maf=new HashMap<String,Object>();
			   maf.put("family", "cf");
			   maf.put("qualifier","hba_name");
			   maf.put("compareOp", CompareOp.EQUAL);
			   maf.put("value", hba_name);
			   conditions.add(maf);
			}
		 List<Node> li=this.cmdbServiceProxy.getCiByCondition("HostHba", conditions, false);
		 for(Node n:li){
			 String shba=getHbaRelatedInfo(n);
			 CiAttribute cahba=new CiAttribute();
			 cahba.setName("HostHba");cahba.setLabel("主机HBA关联");cahba.setValue(shba);
			 n.getAttributes().put(cahba.getName(), cahba);
		 }
		 return li;
	}

	public List<Node> getStorage(String storageName) throws CmdbException{
		List<Map> conditions=new ArrayList<Map>();
//		if(storageName!=null&&!"".equals(storageName)){
//			   Map<String,Object> maf=new HashMap<String,Object>();
//			   maf.put("family", "cf");
//			   maf.put("qualifier","storageName");
//			   maf.put("compareOp", CompareOp.EQUAL);
//			   maf.put("value", storageName);
//			   conditions.add(maf);
//		}
		List<Node> li_re=new ArrayList<Node>();
		List<Node> li=cmdbServiceProxy.getCiByCondition("Storage", conditions, false);
		//程序过滤
		if(storageName!=null&&!"".equals(storageName)){
		    for(Node n:li){
				if(n.getName().contains(storageName)){
					li_re.add(n);
				}
			}
		}else{
			li_re=li;
		}
		
		for(Node n:li_re){
			//补充 storage - host
			 String sto[]={"Storage","StorageNetPort"};
			 List<Node> lis=cmdbViewServiceProxy.getNodeByRelation("Storage", n.getId(), "linked", false, 1, sto);
			 String sport="";
			 Map<String,String> sto_hosts=new HashMap<String,String>(); 
			 for(Node ns:lis){
				 if("StorageNetPort".equals(ns.getDerivedFrom())){
					 String sdis[]={"StorageNetPort","HostDisk"};
					 List<Node> lid=cmdbViewServiceProxy.getNodeByRelation("StorageNetPort", ns.getId(), "linked", true, 1, sdis);
					 String ssdis="";
					 for(Node nd:lid){
						 if("HostDisk".endsWith(nd.getDerivedFrom())){
							 String sh[]={"Host","HostDisk"};
							 List<Node> lih=cmdbViewServiceProxy.getNodeByRelation("HostDisk", nd.getId(), "linked", true, 1, sh);							 
							 String ssh="";							 
							 for(Node nh:lih){
								 if("Host".equals(nh.getDerivedFrom())){
								   ssh="Host:"+nh.getAttributes().get("cscomputername").getValue()+"_"+nh.getName()+" "+ssh;
								   sto_hosts.put(nh.getAttributes().get("cscomputername").getValue()+"_"+nh.getName(),nh.getName());
								 }
							 }
							 ssdis="HostDisk:"+nd.getName()+conn+ssh +"\r\n ";
						 }
					 }
					sport="StorageNetPort:"+ns.getName()+"\r\n "+ssdis;
					log.debug(" StorageNetPort-pv-host related info: "+sport);
				 }
			 }
			 CiAttribute carh=new CiAttribute();
		     carh.setName("stohostre");carh.setLabel("存储关联信息");carh.setValue(sport);
			 n.getAttributes().put(carh.getName(), carh);
			 
			 Iterator<String> ite=sto_hosts.keySet().iterator();
			 String host="";
			 while(ite.hasNext()){
				 String k=ite.next();
				 host=k+";"+host;
			 }
			 CiAttribute cah=new CiAttribute();
		     cah.setName("stohost");cah.setLabel("存储关联主机");cah.setValue(host);
			 n.getAttributes().put(cah.getName(), cah);
		}
	    return li_re;	
	}

	public static void main(String arg[]){
//		 ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		 ApplicationContext context =  new ClassPathXmlApplicationContext("spring/cmdbClient.xml");
		 CmdbQueryServiceImpl cs=new CmdbQueryServiceImpl(); 
//		 cmdbService = (CmdbService) context.getBean("cmdbServiceProxy");
		 cs.setCmdbViewServiceProxy((CmdbViewService) context.getBean("cmdbViewServiceProxy"));
		 cs.setCmdbServiceProxy((CmdbService) context.getBean("cmdbServiceProxy"));
	     try {
	    	//测试hba 
//	    	String hba_hostname="xjscm02";
//	    	String hba_wwpn="0x5001438001312666";
//	    	String hba_name="/dev/fcd0";
//			List<Node> li= cs.getHostHba("", "", hba_name);
//			for(Node n:li){
//				Map<String,List<Node>> ma=cs.getHbaHostZoneInfo(n.getId());
//				System.out.println(ma.size()+" "+ma);
//				if(ma.size()>=3){
//					System.out.println(ma.size()+ma.get("SanZoneSet").get(0).getName());
//				}
//			}
			
			//测试host
//			String cscomputername="";
//			String machine_net_ip="";
//			List<Node> li_h=cs.getHost(cscomputername, machine_net_ip);
//			for(Node n:li_h){
//			  Map<String,List<Node>> lim= cs.getCmdbViewServiceProxy().getNodeRelationMapLv1("Host", n.getId(),"linked");
//			  System.out.println("-------------:"+lim);
//			}
			
			//测试zone
//			String zoneName="";
//			String zoneConfig="";
//			Long sanId=7604875152881999l;
//			List<Node> li_z=cs.getSanZone(sanId,zoneName, zoneConfig);
//			for(Node n:li_z){
//			  Map<String,List<Node>> lim= cs.getCmdbViewServiceProxy().getNodeRelationMapLv1("SanZoneSet", n.getId(),"linked");
//			  System.out.println("-------------:"+n+" -- "+lim.size());
//			}
			
			//测试存储
			String stName="";
			List<Node> li_s=cs.getStorage(stName);
			for(Node n:li_s){
			  Map<String,List<Node>> lim= cs.getCmdbViewServiceProxy().getNodeRelationMapLv1("Storage", n.getId(),"linked");
			  System.out.println("-------------:"+n+" -- "+lim.size());
			}

		} catch (CmdbException e) {
			e.printStackTrace();
		}
	}


}
