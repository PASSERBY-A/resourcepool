package com.hp.xo.uip.cmdb.probe.db.avmon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import com.hp.xo.uip.cmdb.domain.CiAttribute;
import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.probe.db.domain.DBConfig;
import com.hp.xo.uip.cmdb.service.CmdbService;
import com.hp.xo.uip.cmdb.util.DBUtil;
import com.hp.xo.uip.cmdb.util.IdGenerator;

public class AvmonKpiToCiImpl implements AvmonKpiToCi{
	private Logger log = Logger.getLogger(AvmonKpiToCiImpl.class);
	private IdGenerator idg = IdGenerator.createGenerator();
	private DBUtil du = new DBUtil();
	private DBConfig dbConfig;
	private CmdbService cmdbService;

	public DBConfig getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(DBConfig dbConfig) {
		this.dbConfig = dbConfig;
		du.setDbConfig(dbConfig);
	}

	public CmdbService getCmdbService() {
		return cmdbService;
	}

	public void setCmdbService(CmdbService cmdbService) {
		this.cmdbService = cmdbService;
	}

	public List<AvmonKpi> getKpi(String type) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AvmonKpi> li = new ArrayList<AvmonKpi>();
//		String avmonSql = "select tm.type_id ,tm.caption ,tk.caption kpiCaption,"
//				+ " tk.kpi_name,tk.unit,tl.kpi_code,tl.instance,tc.value,tc.num_value,"
//				+ " tc.str_value,tc.kpi_time"
//				+ " from tf_avmon_kpi_value_current tc "
//				+ " left join tf_avmon_kpi_value_list tl on tc.value_key=tl.value_key "
//				+ " left join td_avmon_mo_info tm on tl.mo_id=tm.mo_id "
//				+ " left join  td_avmon_kpi_info tk on tk.kpi_code=tl.kpi_code "
//				+ " where type_id=? ";//
		String avmonSql="  select tm.type_id ,tm.caption ,tk.caption kpiCaption,"
  +"  tk.kpi_name,tk.unit,tl.kpi_code,tl.instance,tc.value,tc.num_value,"
  +"  tc.str_value,tc.kpi_time"
  +"  from tf_avmon_kpi_value_current tc "
  +"  left join tf_avmon_kpi_value_list tl on tc.value_key=tl.value_key" 
  +"  left join td_avmon_mo_info tm on tl.mo_id=tm.mo_id "
  +"  left join  td_avmon_kpi_info tk on tk.kpi_code=tl.kpi_code" 
  +"  where type_id=? "
  +"    and kpi_name !='disk_storage_wwpn'"//单独处理磁盘和存储
  +"  order by caption,kpi_name";
		
		try {
			conn = du.getConnection();
			ps = conn.prepareStatement(avmonSql);
			ps.setString(1, type);
log.debug(avmonSql);
			rs = ps.executeQuery();
			while (rs.next()) {
				AvmonKpi akpi = new AvmonKpi();
				akpi.setCode(rs.getString("kpi_code"));
				// akpi.setDataType(dataType);
				akpi.setInstance(rs.getString("instance"));
				akpi.setKpiTime(rs.getTimestamp("kpi_Time"));
				akpi.setLabel(rs.getString("kpiCaption"));
				akpi.setName(rs.getString("kpi_name"));
				akpi.setNodeName(rs.getString("caption"));
				akpi.setNodeType(rs.getString("type_id"));
				akpi.setUnit(rs.getString("unit"));
				akpi.setValue(rs.getString("value"));
				li.add(akpi);
			}
		} catch (Exception e) {
			log.error("", e);
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				log.error("", e);
				e.printStackTrace();
			}
		}
		return li;
	}
	// 处理磁盘和存储 disk_storage_wwpn 5101106010
	public List<AvmonKpi> getKpiDiskStorage() {
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		List<AvmonKpi> li = new ArrayList<AvmonKpi>();

		String avmonSql="select type_id ,caption ,kpiCaption,  kpi_name,unit,kpi_code,instance,num_value,str_value,kpi_time,"
			+" (select value||';' from tf_avmon_kpi_value_current t1,tf_avmon_kpi_value_list tl1 " 
			+" where t1.value_key=tl1.value_key and tl1.kpi_code='5101106012' and "
			+" t1.value is not null and instance= a2.storage_p and rownum=1)||value value from( " 
			+"   select a.*, (select instance from tf_avmon_kpi_value_current t1,tf_avmon_kpi_value_list tl1" 
			+"    where t1.value_key=tl1.value_key and tl1.kpi_code='5101106014' and instance like '%0x%'"
			//rownum=1 或者用  and kpi_time=(select max(kpi_time) from tf_avmon_kpi_value where kpi_code='5101106014')
			+"     and t1.value is not null and value= a.value and rownum=1) storage_p from(  "
			+" select tm.type_id ,tm.caption ,tk.caption kpiCaption,"
			+"  tk.kpi_name,tk.unit,tkv.kpi_code,tkv.instance,tkv.value,tkv.num_value," 
			+"  tkv.str_value,tkv.kpi_time from tf_avmon_kpi_value tkv "
			+"  left join td_avmon_mo_info tm on tkv.mo_id=tm.mo_id  "
			+"  left join td_avmon_kpi_info tk on tk.kpi_code=tkv.kpi_code" 
			+"   where tkv.kpi_code=5101106010  and tkv.mo_id=?"
			+"     and kpi_time="
			+"     (select max(kpi_time)" 
			+"      from tf_avmon_kpi_value"
			+"      where kpi_code=5101106010  and mo_id=? "
			+"      ) "
			+"      )a)a2";
	String sqlMo="select mo_id from td_avmon_mo_info where type_id='HOST_HP-UX'";	
		try {
			conn = du.getConnection();
			st=conn.createStatement();
			rs=st.executeQuery(sqlMo);
			List<String> netMo=new ArrayList<String>();
			while(rs.next()){
				netMo.add(rs.getString("mo_id"));
			}
		 for(String mo:netMo){	
			ps = conn.prepareStatement(avmonSql);
			ps.setString(1, mo);
			ps.setString(2, mo);
log.debug(avmonSql);
			rs = ps.executeQuery();
			while (rs.next()) {
				AvmonKpi akpi = new AvmonKpi();
				akpi.setCode(rs.getString("kpi_code"));
				// akpi.setDataType(dataType);
				akpi.setInstance(rs.getString("instance"));
				akpi.setKpiTime(rs.getTimestamp("kpi_Time"));
				akpi.setLabel(rs.getString("kpiCaption"));
				akpi.setName(rs.getString("kpi_name"));
				akpi.setNodeName(rs.getString("caption"));
				akpi.setNodeType(rs.getString("type_id"));
				akpi.setUnit(rs.getString("unit"));
				akpi.setValue(rs.getString("value"));

				li.add(akpi);
			}
			}
		} catch (Exception e) {
			log.error("", e);
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				log.error("", e);
				e.printStackTrace();
			}
		}
		return li;
	}
	
	/**
	 * 
	 * @param wwpn
	 * @return
	 */
	public List<AvmonKpi> getKpiWWPN() {
		Connection conn = null;
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		List<AvmonKpi> li = new ArrayList<AvmonKpi>();
		//--kpi:HBA_WWPN 5101106008，storage_wwpn 9101001001
//		String avmonSql = "select * from (" +
// " select a.*, "+
// " (select caption||'_'||instance from tf_avmon_kpi_value_current t1,tf_avmon_kpi_value_list tl1,td_avmon_mo_info tm1 "+
// " where t1.value_key=tl1.value_key and tm1.mo_id=tl1.mo_id and tl1.kpi_code='5101106008' and t1.value is not null "+ 
// " and replace(value,'0x')=replace(a.value,':')) hba_key, "+
// " (select value from tf_avmon_kpi_value_current t1,tf_avmon_kpi_value_list tl1 " + 
//  " where t1.value_key=tl1.value_key and tl1.kpi_code='5101106014' and t1.value is not null "+ 
//  " and replace(value,'0x')=replace(a.value,':')) storage_wwpn "+ 
// " from ( "+
// " select tm.type_id ,tm.caption ,tk.caption kpiCaption, "+
// " tk.kpi_name,tk.unit,tl.kpi_code,tl.instance,tc.value,tc.num_value, "+
// " tc.str_value,tc.kpi_time "+
// " from tf_avmon_kpi_value_current tc "+ 
// "  left join tf_avmon_kpi_value_list tl on tc.value_key=tl.value_key "+ 
// "  left join td_avmon_mo_info tm on tl.mo_id=tm.mo_id "+ 
// "  left join  td_avmon_kpi_info tk on tk.kpi_code=tl.kpi_code "+ 
// "  where type_id='NETWORK'  "+
// "  order by caption,kpi_name "+
// "  )a "+
// "  )a2 ";
		//6101001001 wwpn_in_zone
		String avmonSql="   select a.*,"
+" (select caption||'_'||instance from tf_avmon_kpi_value_current t1,tf_avmon_kpi_value_list tl1,td_avmon_mo_info tm1 " 
+" where t1.value_key=tl1.value_key and tm1.mo_id=tl1.mo_id and tl1.kpi_code='5101106008' and t1.value is not null " 
+"  and rownum=1 and replace(value,'0x')=replace(a.value,':')) hba_key,  "
+" (select value from tf_avmon_kpi_value_current t1,tf_avmon_kpi_value_list tl1  "
+" where t1.value_key=tl1.value_key and tl1.kpi_code='5101106014' and t1.value is not null " 
+"  and rownum=1 and replace(value,'0x')=replace(a.value,':')) storage_wwpn " 
+"  from( "
+"  select tm.type_id ,tm.caption ,tk.caption kpiCaption, "
+"  tk.kpi_name,tk.unit,tkv.kpi_code,tkv.instance,tkv.value,tkv.num_value, "
+"  tkv.str_value,tkv.kpi_time from tf_avmon_kpi_value tkv "
+"    left join td_avmon_mo_info tm on tkv.mo_id=tm.mo_id  "
+"    left join td_avmon_kpi_info tk on tk.kpi_code=tkv.kpi_code "
+"    where tkv.kpi_code=6101001001  and tkv.mo_id=? "
+"      and kpi_time= "
+"      (select max(kpi_time) "
+"       from tf_avmon_kpi_value  "
+"       where kpi_code=6101001001  and mo_id=?) "
+"   )a";
	String sqlMo="select mo_id from td_avmon_mo_info where type_id='NETWORK'";	
		try {
			conn = du.getConnection();
			st=conn.createStatement();
			rs=st.executeQuery(sqlMo);
			List<String> netMo=new ArrayList<String>();
			while(rs.next()){
				netMo.add(rs.getString("mo_id"));
			}
		 for(String mo:netMo){	
			ps = conn.prepareStatement(avmonSql);
			ps.setString(1, mo);
			ps.setString(2, mo);
log.debug(avmonSql);
			rs = ps.executeQuery();
			while (rs.next()) {
				AvmonKpi akpi = new AvmonKpi();
				akpi.setCode(rs.getString("kpi_code"));
				// akpi.setDataType(dataType);
				akpi.setInstance(rs.getString("instance"));
				akpi.setKpiTime(rs.getTimestamp("kpi_Time"));
				akpi.setLabel(rs.getString("kpiCaption"));
				akpi.setName(rs.getString("kpi_name"));
				akpi.setNodeName(rs.getString("caption"));
				akpi.setNodeType(rs.getString("type_id"));
				akpi.setUnit(rs.getString("unit"));
				akpi.setValue(rs.getString("value"));
				//zone 关联的 hba name(host_name)关系
				akpi.setValue1(rs.getString("hba_key"));
				//zone 关联的storage name(wwpn)关系
				akpi.setValue2(rs.getString("storage_wwpn"));
				li.add(akpi);
			}
			}
		} catch (Exception e) {
			log.error("", e);
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				log.error("", e);
				e.printStackTrace();
			}
		}
		return li;
	}
    //得到 端口物理连接关系
	public List<AvmonKpi> getKpiPortConn() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AvmonKpi> li = new ArrayList<AvmonKpi>();
		//--kpi:HBA_WWPN 5101106008，storage_wwpn 9101001001
		String avmonSql = "select a2.* from ( "
+" select a.*, "
+" (select caption||'_'||instance from tf_avmon_kpi_value_current t1,tf_avmon_kpi_value_list tl1 " 
+" where t1.value_key=tl1.value_key and tl1.kpi_code='5101106008' and t1.value is not null " 
+" and replace(value,'0x')=replace(a.value,':')) hba_key, "
+" (select value from tf_avmon_kpi_value_current t1,tf_avmon_kpi_value_list tl1 " 
+" where t1.value_key=tl1.value_key and tl1.kpi_code='5101106014' and t1.value is not null " 
+" and replace(value,'0x')= replace(a.value,':')) storage_wwpn " 
+" from ( "
+" select tm.type_id ,tm.caption ,tk.caption kpiCaption, "
+"   tk.kpi_name,tk.unit,tl.kpi_code,tl.instance,tc.value,tc.num_value, "
+"   tc.str_value,tc.kpi_time "
+"   from tf_avmon_kpi_value_current tc " 
+"   left join tf_avmon_kpi_value_list tl on tc.value_key=tl.value_key " 
+"   left join td_avmon_mo_info tm on tl.mo_id=tm.mo_id " 
+"   left join  td_avmon_kpi_info tk on tk.kpi_code=tl.kpi_code " 
+"   where type_id='NETWORK' and kpi_name='PORT_CONNECT_WWPN' "
+"   order by caption,kpi_name "
+"   )a "
+"   )a2 --where a2.storage_wwpn is not null or a2.hba_wwpn is not null";
		try {
			conn = du.getConnection();
			ps = conn.prepareStatement(avmonSql);
//			ps.setString(1, type);

			rs = ps.executeQuery();
			while (rs.next()) {
				AvmonKpi akpi = new AvmonKpi();
				akpi.setCode(rs.getString("kpi_code"));
				// akpi.setDataType(dataType);
				akpi.setInstance(rs.getString("instance"));
				akpi.setKpiTime(rs.getTimestamp("kpi_Time"));
				akpi.setLabel(rs.getString("kpiCaption"));
				akpi.setName(rs.getString("kpi_name"));
				akpi.setNodeName(rs.getString("caption"));
				akpi.setNodeType(rs.getString("type_id"));
				akpi.setUnit(rs.getString("unit"));
				akpi.setValue(rs.getString("value"));
				//zone 关联的 hba name(host_name)关系
				akpi.setValue1(rs.getString("hba_key"));
				//zone 关联的storage name(wwpn)关系
				akpi.setValue2(rs.getString("storage_wwpn"));
				li.add(akpi);
			}
		} catch (Exception e) {
			log.error("", e);
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				log.error("", e);
				e.printStackTrace();
			}
		}
		return li;
	}
	
	// HOST_HP-UX: Host
	public void analysisHostKpi(Map<String,Map<String,Node>> nodesMa,Map<String,RelationNode> relationMa) throws CmdbException {
		// map<nodeName,List<Node>>
		Map<String, Node> hostMa = new HashMap<String, Node>();
		// Map<String,RelationNode> hostReMa=new HashMap<String,RelationNode>();
		Map<String, Node> hbaMa = new HashMap<String, Node>();
		Map<String, RelationNode> hbaReMa = new HashMap<String, RelationNode>();
		Map<String, Node> diskMa = new HashMap<String, Node>();
		Map<String, RelationNode> diskReMa = new HashMap<String, RelationNode>();
		
		Map<String, Node> vgMa = new HashMap<String, Node>();
		Map<String, RelationNode> vgReMa = new HashMap<String, RelationNode>();
		
		Map<String, String> diskVg = new HashMap<String, String>();
		Map<String, String> diskLv = new HashMap<String, String>();
		Map<String, String> pv_vg = new HashMap<String, String>();
		Map<String, String> vg_lv = new HashMap<String, String>();
		
		Map<String, Node> stoMa = new HashMap<String, Node>();
		Map<String, Node> portMa = new HashMap<String, Node>();
		Map<String, RelationNode> portReMa = new HashMap<String, RelationNode>();
		Map<String, String> pv_port = new HashMap<String, String>();
		Map<String, RelationNode> pvPortReMa = new HashMap<String, RelationNode>();
		
		try {
			List<AvmonKpi> li = getKpi("HOST_HP-UX");
			List<AvmonKpi> li2 = getKpiDiskStorage();
			if(li!=null)li.addAll(li2);
			log.debug(li.size());
			Node hostT = cmdbService.getCiTypeByName("Host");
			Node hbaT = cmdbService.getCiTypeByName("HostHba");
			Node diskT = cmdbService.getCiTypeByName("HostDisk");
			Node vgroupT = cmdbService.getCiTypeByName("VirtualGroup");
			Node stoT = cmdbService.getCiTypeByName("Storage");
			Node stoPortT = cmdbService.getCiTypeByName("StorageNetPort");
			for (AvmonKpi ak : li) {
				Node host = hostMa.get(ak.getNodeName());
				if (host == null) {
					host = cmdbService.getCiByName("Host", ak.getNodeName());
					if (host == null) {
						host=hostT.clone();
						host.setIsType(false);host.setDerivedFrom(hostT.getName());
						host.setId(idg.generate());
						host.setName(ak.getNodeName());
						host.setLabel(ak.getNodeName());
						host.setSyncStatus(1);
					} else {
						host.setSyncStatus(2);
						host.setAttributes(hostT.clone().getAttributes());
//						String t1=hostT.getAttributes().get("machine_set_nproc").getValue();
						
					}
					hostMa.put(host.getName(), host);
				}
				// Ci att
				CiAttribute hostTcit = hostT.getAttributes().get(
						ak.getName().toLowerCase());
				CiAttribute hbaTcit = hbaT.getAttributes().get(
						ak.getName().toLowerCase());
				CiAttribute diskTcit = diskT.getAttributes().get(
						ak.getName().toLowerCase());
				if (hostTcit != null) {
					CiAttribute hostcit = host.getAttributes().get(
							ak.getName().toLowerCase());
					
					String value ="";
					  if(ak.getInstance()!=null&&"ALL".equalsIgnoreCase(ak.getInstance())){
						 value=ak.getValue();
					  }else{
						 value=ak.getInstance() + "_" + ak.getValue(); 	
					  }
					
					if (hostcit == null) {
						CiAttribute ct = hostTcit.clone();
						ct.setValue(value);
						host.getAttributes().put(ct.getName(), ct);
					} else {
						if(hostcit.getValue()==null){
							hostcit.setValue(value);
						}else{
						    hostcit.setValue(value+";"+hostcit.getValue());
						}
						host.getAttributes().put(hostcit.getName(), hostcit);
					}
				} else if (hbaTcit != null) {
					// hbaKey=hostName_hbaName
					// hba的name的格式为 hostName_hbaname
					// hba的label的格式为hostName_hbaname
					// 在其他涉及hba卡的kpi中以instance出现
					
					Node hba = hbaMa.get(host.getName() + "_"
							+ ak.getInstance());
					if (hba == null) {
						hba = cmdbService.getCiByName("HostHba", host.getName()
								+ "_" + ak.getInstance());
						if (hba == null) {
							hba = hbaT.clone();
							hba.setIsType(false);hba.setDerivedFrom(hbaT.getName());
							hba.setId(idg.generate());
							hba.setName(host.getName() + "_"
											+ ak.getInstance());
							hba.setLabel(host.getName() + "_"
									+ ak.getInstance());
							hba.setSyncStatus(1);							
						} else {
							hba.setSyncStatus(2);
							hba.setAttributes(hbaT.clone().getAttributes());
						}
						hbaMa.put(hba.getName(), hba);
					}
					CiAttribute hbacit = hba.getAttributes().get(
							ak.getName().toLowerCase());
					// 不需instance
					String value = ak.getValue();
					if (hbacit == null) {
						CiAttribute ct = hbaTcit.clone();
						ct.setValue(value);
						hba.getAttributes().put(ct.getName(), ct);
					} else {
						if(hbacit.getValue()==null){
							hbacit.setValue(value);
						}else{
						  hbacit.setValue(value+";"+hbacit.getValue());
						}
						hba.getAttributes().put(hbacit.getName(), hbacit);
					}
					// 关系
					RelationNode rn = hbaReMa.get(host.getName() + "_"
							+ hba.getName());
					if (rn == null) {
						rn=getRelation("linked",host.getName()
								+ "_" + hba.getName() , host, hba);
						hbaReMa.put(rn.getName(), rn);
					}
				} else if (diskTcit != null) {
					if (ak.getName().startsWith("PV_")) {
						String dname=ak.getNodeName()+"-"+ak.getInstance();
						Node disk = diskMa.get(dname);
						if (disk == null) {
							disk = cmdbService.getCiByName("HostDisk", dname);
							if (disk == null) {
								disk = diskT.clone();
								disk.setIsType(false);disk.setDerivedFrom(diskT.getName());
								disk.setId(idg.generate());
								disk.setName(dname);
								disk.setLabel(dname);
								disk.setSyncStatus(1);
							} else {
								disk.setSyncStatus(2);
								disk.setAttributes(diskT.clone().getAttributes());
							}
							diskMa.put(disk.getName(), disk);
						}
						CiAttribute diskcit = disk.getAttributes().get(
								ak.getName().toLowerCase());
						String value = ak.getValue();
						if (diskcit == null) {
							CiAttribute ct = diskcit.clone();
							ct.setValue(value);
							disk.getAttributes().put(ct.getName(), ct);
						} else {
							diskcit.setValue(diskcit.getValue() + "; " + value);
						}
						// 关系 ? ?
						RelationNode rn = diskReMa.get(host.getName() + "_"
								+ disk.getName());
						if (rn == null) {
							rn=getRelation("linked",host.getName()
									+ "_" + disk.getName() , host, disk);
							diskReMa.put(rn.getName(), rn);
						}
						if (ak.getName().equals("PV_VG_NAME")) {
							pv_vg.put(dname, ak.getValue());
						}
					}
				} else if (ak.getName().startsWith("VG_")) {
					//ak.getNodeName()+"_"+
					String vgkey=ak.getInstance();
					String vg = diskVg.get(vgkey);
					if (vg != null && !vg.contains(ak.getValue()+";")){
						vg = ak.getLabel()+":"+ak.getValue()+";"+vg;
					}else{
						vg=ak.getLabel()+":"+ak.getValue();
					}
					diskVg.put(vgkey, vg);
				} else if (ak.getName().startsWith("LV_")) {
					String lv = diskLv.get(ak.getInstance());
					if (lv != null && !lv.contains(ak.getValue()+";")){
						lv = ak.getLabel()+":"+ak.getValue()+";"+lv;
						}else{
							lv=ak.getLabel()+":"+ak.getValue();
						}
					diskLv.put(ak.getInstance(), lv);
					
					if (ak.getName().equals("LV_VG_NAME")) {
						String lvs = vg_lv.get(ak.getValue());
						if (lvs != null && !lvs.contains(ak.getInstance()+";")){
							lvs = ak.getInstance()+";"+lvs;
						}else{
							lvs=ak.getInstance();
						}
						vg_lv.put(ak.getValue(), lvs);
					}
				}else if (ak.getName().startsWith("disk_storage_wwpn")) {
					//storage kpi 结构  
					//disk_storage_wwpn ,instance 为 PV_name,value为"storage(seq);wwpn(storageNetPort)"
					//host - disk(pv) - storage(seq)+ wwpn(storageNetPort)
					//storage 名称为序列号 ， storageNetPort名称为wwpn
				  String pv=ak.getNodeName()+"-"+ak.getInstance();
				  if(ak.getValue()!=null&&ak.getValue().contains(";")){
					String[] ss=ak.getValue().split(";");
					
					//pv - storagePort
					pv_port.put(pv, ss[1]);
					
					Node sto = stoMa.get(ss[0]);
					if (sto == null) {
						sto = cmdbService.getCiByName("Storage", ss[0]);
						if (sto == null) {
							sto = stoT.clone();
							sto.setIsType(false);sto.setDerivedFrom(stoT.getName());
							sto.setId(idg.generate());
							sto.setName(ss[0]);sto.setLabel(ss[0]);
							sto.setSyncStatus(1);
						} else {
							sto.setSyncStatus(2);
							sto.setAttributes(stoT.clone().getAttributes());
						}
						stoMa.put(sto.getName(), sto);
				    }
					Node stoPort = portMa.get(ss[1]);
					if (stoPort == null) {
						stoPort = cmdbService.getCiByName("StorageNetPort", ss[1]);
						if (stoPort == null) {
							stoPort = stoPortT.clone();
							stoPort.setIsType(false);stoPort.setDerivedFrom(stoPortT.getName());
							stoPort.setId(idg.generate());
							stoPort.setName(ss[1]);stoPort.setName(ss[1]);
							stoPort.setSyncStatus(1);							
						} else {
							stoPort.setSyncStatus(2);
							stoPort.setAttributes(stoPortT.clone().getAttributes());
						}
						portMa.put(stoPort.getName(), stoPort);
				    }
					//关系 
					RelationNode rn = portReMa.get(sto.getName() + "_"
							+ stoPort.getName());
					if (rn == null) {
						rn=getRelation("linked",sto.getName() + "_"
								+ stoPort.getName() , sto, stoPort);
						portReMa.put(rn.getName(), rn);
					}
				  }
				  }
			}
			// ---disk pv lv vg
			Iterator<String> ite = diskMa.keySet().iterator();
			while (ite.hasNext()) {
				String key = ite.next();
				Node n = diskMa.get(key);
				String vgName = pv_vg.get(key);
				String vgInfo = diskVg.get(vgName);
				CiAttribute ca = n.getAttributes().get(
						"PV_VG_INFO".toLowerCase());
				
				ca.setValue(vgInfo);
				n.getAttributes().put("PV_VG_INFO".toLowerCase(), ca);
				diskMa.put(key, n);
				
				//--- vg info
				Node vgroup = vgMa.get(vgName);
				if (vgroup == null) {
					vgroup = cmdbService.getCiByName("VirtualGroup", vgName);
					if (vgroup == null) {
						vgroup = vgroupT.clone();
						vgroup.setIsType(false);vgroup.setDerivedFrom(vgroupT.getName());
						vgroup.setId(idg.generate());
						vgroup.setName(vgName);
						vgroup.setLabel(vgName);
						vgroup.setSyncStatus(1);
					} else {
						vgroup.setSyncStatus(2);
						vgroup.setAttributes(vgroupT.clone().getAttributes());
					}
					vgMa.put(vgroup.getName(), vgroup);
				}
				CiAttribute ca_vg = vgroup.getAttributes().get(
						"VG_INFO".toLowerCase());
				ca_vg.setValue(vgInfo);
				vgroup.getAttributes().put("VG_INFO".toLowerCase(), ca_vg);
				
				String lvInfo = "";
				String lvs = vg_lv.get(vgName);
				if (lvs != null) {
					String[] lvss = lvs.split(";");
					for (String s : lvss) {
						if (diskLv.get(s) != null) {
							lvInfo = s+"["+diskLv.get(s)+"]"+ ";\r\n" + lvInfo;
						}
					}
					CiAttribute ca2 = vgroup.getAttributes().get(
							"VG_LV_INFO".toLowerCase());
					ca2.setValue(lvInfo);
					vgroup.getAttributes().put("VG_LV_INFO".toLowerCase(), ca2);
				}
				
				//vg 包含 pv 关系
				RelationNode vg_pv = vgReMa.get(vgroup.getName() + "_"
						+ n.getName());
				if (vg_pv == null) {
					vg_pv=getRelation("linked",vgroup.getName() + "_"
							+ n.getName() , vgroup, n);
					vgReMa.put(vg_pv.getName(), vg_pv);
				}
			}
			// pv - storageNetport 关系
			Iterator<String> pite= pv_port.keySet().iterator();
			while(pite.hasNext()){
				 String pvn=pite.next();
				 Node pv=diskMa.get(pvn);
				 Node stoPort=portMa.get(pv_port.get(pvn));
			   if(pv!=null&&stoPort!=null){
				 RelationNode rn = pvPortReMa.get(pv.getName() + "_"
							+ stoPort.getName());
					if (rn == null) {
						rn=getRelation("linked",pv.getName() + "_"
								+ stoPort.getName() , pv, stoPort);
						pvPortReMa.put(rn.getName(), rn);
					}
				}
			}

			nodesMa.put("Host", hostMa);
			nodesMa.put("HostHba", hbaMa);
			nodesMa.put("HostDisk", diskMa);
			nodesMa.put("Storage", stoMa);
			nodesMa.put("StorageNetPort", portMa);
			nodesMa.put("VirtualGroup",vgMa );
						
			relationMa.putAll(diskReMa);
			relationMa.putAll(hbaReMa);
			relationMa.putAll(portReMa);
			relationMa.putAll(pvPortReMa);
			relationMa.putAll(vgReMa);
			
		} catch (Exception e) {
			log.error("", e);
			e.printStackTrace();
			throw new CmdbException(e);
		}
	}
	public String deleteNode(String typeName,Map<String,Node> nodeMa,Boolean preView) throws CmdbException{
		List<Node> ns= cmdbService.getCiByTypeName(typeName);
		String re="";
		for(Node n:ns){
			if(nodeMa.get(n.getName())==null){
				re=re+"\r\n"+n.getDerivedFrom()+": "+n.getName()+":"+n.getId()+"[删除]";
				if(!preView)cmdbService.deleteNode(n.getDerivedFrom(), n.getId());
			}
		}
		return re;
	}
	public String deleteNodeRe(String typeName,Map<String,RelationNode> nodeMa,Boolean preView) throws CmdbException{
		List<RelationNode> ns= cmdbService.getRelationCiByTypeName(typeName);
		String re="";
		for(RelationNode n:ns){
			if(nodeMa.get(n.getName())==null){
				re=re+"\r\n"+n.getDerivedFrom()+": "+n.getName()+":"+n.getId()+"[删除]";
				if(!preView)cmdbService.deleteRelation(n.getDerivedFrom(), n.getId());
			}
		}
		return re;
	}
	public String[] insertNode(Collection<Node> ch,Boolean preView) throws CmdbException{
		String ren="";
		String reu="";
		String tag_new="";String tag_update="";
		if(!preView){
			tag_new="[已新建]"; tag_update="[已更新]";
		}else{
			tag_new="[新建]"; tag_update="[更新]";
		}
		for(Node n1:ch){
			if(n1.getSyncStatus()==1){
			  ren=ren+"\r\n"+n1.getDerivedFrom()+":"+n1.getName()+":"+n1.getId()+tag_new;
			}else{
			  reu=reu+"\r\n"+n1.getDerivedFrom()+":"+n1.getName()+":"+n1.getId()+tag_update;	
			}
			log.debug(n1.getDerivedFrom()+":"+n1.getName());
			Collection<CiAttribute> cas= n1.getAttributes().values();
			for(CiAttribute ca:cas){
				String value=ca.getValue();
				if(value!=null&&value.length()>21){
					value=value.substring(0,20);
				}
				log.debug("["+n1.getName()+"]"+ca.getName()+":"+value);
			}
		}
		if(!preView){
		 List<Node> li=new ArrayList<Node>();
		 li.addAll(ch);
		 cmdbService.insertNodeCis(li);
		}
		String[] re=new String[2];
		re[0]=ren;re[1]=reu;
		return re;
	}
	public String[] insertNodeRe(Collection<RelationNode> rch,Boolean preView) throws CmdbException{
		String reu="";
		String ren="";
		for(RelationNode n1:rch){
			if(n1.getSyncStatus()==1){
				  ren=ren+"\r\n"+n1.getDerivedFrom()+":"+n1.getName()+":"+n1.getId()+"[新建]";
				}else{
				  reu=reu+"\r\n"+n1.getDerivedFrom()+":"+n1.getName()+":"+n1.getId()+"[更新]";	
				}
			log.debug(n1.getDerivedFrom()+"："+n1.getName());
			log.debug("-relation-from:"+n1.getSourceName()+","+n1.getSourceType()+n1.getSourceId());
			log.debug("-relation---to:"+n1.getTargetName()+","+n1.getTargetType()+n1.getTargetId());
		}
		
		if(!preView){
			List<RelationNode> li=new ArrayList<RelationNode>();
			li.addAll(rch);
			cmdbService.insertRelationCis(li);
		}
		String re[]=new String[2];
		re[0]=ren;re[1]=reu;
		return re;
	}
	public RelationNode getRelation(String reType,String reName,Node sour,Node dest){
		RelationNode rn = cmdbService.getRelationCiByName(reType,reName);
		try{
		
		if (rn == null) {
			RelationNode rnT = cmdbService
					.getRelationTypeByName("linked");
			rn = rnT.clone();
			rn.setName(reName);
			rn.setIsType(false);			
			rn.setId(idg.generate());
			rn.setDerivedFrom(rnT.getName());
			rn.setSyncStatus(1);
			rn.setSourceId(sour.getId());
			rn.setSourceLable(sour.getLabel());
			rn.setSourceName(sour.getName());
			rn.setTargetId(dest.getId());
			rn.setTargetLable(dest.getLabel());
			rn.setTargetName(dest.getName());
			rn.setSourceType(sour.getDerivedFrom());
			rn.setTargetType(dest.getDerivedFrom());
		} else {
			rn.setSyncStatus(2);
		}
		}catch(Exception e){
			log.error("",e);
			e.printStackTrace();
		}
		return rn;
	}
	/**
	 * NETWORK -- 
	 * @throws CmdbException
	 */
	public void analysisNetworkKpi(Map<String,Map<String,Node>> nodesMa,Map<String,RelationNode> relationMa) throws CmdbException {
		// map<nodeName,List<Node>>
		Map<String, Node> sanMa = new HashMap<String, Node>();
		// Map<String,RelationNode> hostReMa=new HashMap<String,RelationNode>();
		Map<String, Node> portMa = new HashMap<String, Node>();
		Map<String, RelationNode> portReMa = new HashMap<String, RelationNode>();
		
		Map<String, Node> zoneMa = new HashMap<String, Node>();
		Map<String, RelationNode> zoneReMa = new HashMap<String, RelationNode>();
		Map<String, RelationNode> zoneHbaReMa = new HashMap<String, RelationNode>();
		Map<String, RelationNode> zonePortReMa = new HashMap<String, RelationNode>();

		try {
			List<AvmonKpi> li = getKpiWWPN();
			log.debug(li.size());
			Node sanT = cmdbService.getCiTypeByName("San");
//			Node portT = cmdbService.getCiTypeByName("SanPort");
			Node zoneT = cmdbService.getCiTypeByName("SanZoneSet");
			for (AvmonKpi ak : li) {
				Node san = sanMa.get(ak.getNodeName());
				if (san == null) {
					san = cmdbService.getCiByName("San", ak.getNodeName());
					if (san == null) {
						san = sanT.clone();
						san.setIsType(false);san.setDerivedFrom(sanT.getName());
						san.setId(idg.generate());
						san.setName(ak.getNodeName());san.setLabel(ak.getNodeName());
						san.setSyncStatus(1);						
					} else {
						san.setSyncStatus(2);
						san.setAttributes(sanT.clone().getAttributes());
					}
					sanMa.put(san.getName(), san);
				}
				Node zone = zoneMa.get(ak.getNodeName()+"_"+ak.getInstance());
				if (zone == null) {
					zone = cmdbService.getCiByName("SanZoneSet", ak.getNodeName()+"_"+ak.getInstance());
					if (zone == null) {
						zone = zoneT.clone();
						zone.setIsType(false);zone.setDerivedFrom(zoneT.getName());
						zone.setId(idg.generate());
						zone.setName(ak.getNodeName()+"_"+ak.getInstance());zone.setLabel(ak.getNodeName()+"_"+ak.getInstance());
						zone.setSyncStatus(1);
					} else {
						zone.setSyncStatus(2);
						zone.setAttributes(zoneT.clone().getAttributes());
					}
					zoneMa.put(zone.getName(), zone);
				}
				
				//关系
				RelationNode rn = zoneReMa.get(san.getName() + "_"
						+ zone.getName());
				if (rn == null) {
					rn=getRelation("linked", san.getName()+"_"+zone.getName(), san, zone);
					zoneReMa.put(rn.getName(), rn);
				}
				
				//关系 zone-storageNetport_wwpn  zone-hbawwpn 
				if(ak.getName().equals("WWPN_IN_ZONE")){
					//zone 关联的 hba name(host_name)关系
				    if(ak.getValue1()!=null&&!"".equals(ak.getValue1())){
				    	Node hba=cmdbService.getCiByName("HostHba", ak.getValue1());
				    	if(hba==null){hba=nodesMa.get("HostHba").get(ak.getValue1());}
				     if(hba!=null){
				    	RelationNode rnhba = zoneHbaReMa.get(zone.getName() + "_"
								+ hba.getName());
						if (rnhba == null) {
				    	  rnhba=getRelation("linked", zone.getName()+"_"+hba.getName(), zone, hba);
				    	  zoneHbaReMa.put(rnhba.getName(), rnhba);
				    	}
				     }
				    }
				    //zone 关联的storage port wwpn
				    if(ak.getValue2()!=null&&!"".equals(ak.getValue2())){				    	
				    	Node stp=cmdbService.getCiByName("StorageNetPort", ak.getValue2());
				    	if(stp==null){stp=nodesMa.get("StorageNetPort").get(ak.getValue2());}
				       if(stp!=null){	
				    	RelationNode rnp = zonePortReMa.get(zone.getName() + "_"
								+ stp.getName());
						if (rnp == null) {
				    	  rnp=getRelation("linked", zone.getName()+"_"+stp.getName(), zone, stp);
				    	  zonePortReMa.put(rnp.getName(), rnp);
				    	}
				       }
				    }
				    //将zone包含的port或wwpn,记录在属性中
				    CiAttribute att=zone.getAttributes().get("zoneconfig");
					if(att.getValue()!=null 
							&& !att.getValue().toString().contains(ak.getValue())){
						att.setValue(att.getValue()+ak.getValue()+";\r\n");
					}else{
						att.setValue(ak.getValue()+";\r\n");
					}
					zone.getAttributes().put(att.getName(),att);
				}
				
				// Ci att， san暂时没有采集属性
				CiAttribute sanTcit = sanT.getAttributes().get(
						ak.getName().toLowerCase());
//				CiAttribute portTcit = portT.getAttributes().get(
//						ak.getName().toLowerCase());
//				CiAttribute zoneTcit = zoneT.getAttributes().get(
//						ak.getName().toLowerCase());
				if (sanTcit != null) {
					CiAttribute hostcit = san.getAttributes().get(
							ak.getName().toLowerCase());
					String value = ak.getInstance() + "_" + ak.getValue();
					if (hostcit == null) {
						CiAttribute ct = sanTcit.clone();
						ct.setValue(value);
						san.getAttributes().put(ct.getName(), ct);
					} else {
						hostcit.setValue(hostcit.getValue() + "; " + value);
					}
				}
			}
		} catch (Exception e) {
			log.error("", e);
			e.printStackTrace();
			throw new CmdbException(e);
		}

//		log.debug(sanMa);
		nodesMa.put("San",sanMa);
		nodesMa.put("SanZoneSet",zoneMa);

		relationMa.putAll(zoneReMa);
		relationMa.putAll(zoneHbaReMa);
		relationMa.putAll(zonePortReMa);
				
	}
	//处理实际链接  ，问题: 多层交换机链接至核心san的 链路无法采集！ 暂时不处理
	
	public void analysisPortConn(Map<String,RelationNode> relationsMa) throws CmdbException{
		Map<String, RelationNode> sanp_hba = new HashMap<String, RelationNode>();
		Map<String, RelationNode> sanp_stofc = new HashMap<String, RelationNode>();
		
		try {
			List<AvmonKpi> li = getKpiPortConn();
			log.debug(li.size());
			for (AvmonKpi ak : li) {				
				//关系
				
			}
		} catch (Exception e) {
			log.error("", e);
			e.printStackTrace();
			throw new CmdbException(e);
		}

//		log.debug(sanMa);
		relationsMa.putAll(sanp_hba);
		relationsMa.putAll(sanp_stofc);				
	}
	
	public String syncAvmonCi(Boolean preView) throws CmdbException{
    	String ren="";
    	String reu="";
    	String red="";
    	Map<String,RelationNode> relationsMa=new HashMap<String,RelationNode>();
    	Map<String,Map<String,Node>> nodesMa=new HashMap<String,Map<String,Node>>();
		analysisHostKpi(nodesMa,relationsMa);
		analysisNetworkKpi(nodesMa,relationsMa);
		
		Iterator<String> ite= nodesMa.keySet().iterator();
		while(ite.hasNext()){
			String typeName=ite.next();
			Map<String,Node> ma=nodesMa.get(typeName);
			if(ma!=null){
			   String re[]=insertNode(ma.values(), preView);
			  ren=ren+"\r\n"+re[0];
			  reu=reu+"\r\n"+re[1];
			  String redt=deleteNode(typeName, ma, preView);
			  if(redt!=null&&!"".equals(redt)){
			    red=red+"\r\n"+redt;
			  }
			}
		}
		if(relationsMa!=null){
			String re[]=insertNodeRe(relationsMa.values(), preView);
		    ren=ren+"\r\n"+re[0];
		    reu=reu+"\r\n"+re[1];
		    red=red+"\r\n"+deleteNodeRe("linked", relationsMa, preView);
		}
		return red+"\r\n"+ren+"\r\n"+reu;
    }
	public static void main(String arg[]) {
		
		 AvmonKpiToCiImpl ak=new AvmonKpiToCiImpl();
		 DBConfig df=new DBConfig();
		 df.setDriver("oracle.jdbc.driver.OracleDriver");
		 df.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
		 df.setUser("avmon2");
		 df.setPassword("avmon2");
		 ak.setDbConfig(df);
		 //---
		 ak.getKpi("HOST_HP-UX");
	}
	public String getClasKpiCode(String className) {
		return "";
	}

	@Override
	public String getClassByKpi(String kpiCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKpiByClass(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getClassViewKpi(String className) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getViewFuncKpi(String func) {
		// TODO Auto-generated method stub
		return null;
	}
}
