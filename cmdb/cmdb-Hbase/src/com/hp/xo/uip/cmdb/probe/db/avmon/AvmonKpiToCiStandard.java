package com.hp.xo.uip.cmdb.probe.db.avmon;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class AvmonKpiToCiStandard implements AvmonKpiToCi {
	private Logger log = Logger.getLogger(AvmonKpiToCiStandard.class);
	private IdGenerator idg = IdGenerator.createGenerator();
	private DBUtil du = new DBUtil();
	private DBConfig dbConfig;
	private CmdbService cmdbService;
	private AvmonSyncUtil as = new AvmonSyncUtil();

	List<String> tips = new ArrayList<String>();

	public DBConfig getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(DBConfig dbConfig) throws IOException {
		this.dbConfig = dbConfig;
		du.setDbConfig(dbConfig);
		log.info("paserConf avmonSync.properties ..... ");
		as.paserConf();
	}

	public CmdbService getCmdbService() {
		return cmdbService;
	}

	public void setCmdbService(CmdbService cmdbService) {
		this.cmdbService = cmdbService;
	}

	public List<AvmonKpi> getKpiByNodeId(String nodeId, String... kpiName) {
		return getKpi(null, kpiName, nodeId);
	}

	public List<AvmonKpi> getKpiByName(String... kpiName) {
		return getKpi(null, kpiName, null);
	}

	public List<AvmonKpi> getKpiByCode(String... kpiCode) {
		return getKpi(kpiCode, null, null);
	}

	public AvmonKpi getKpiCur(String kpiName, String nodeId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AvmonKpi> li = new ArrayList<AvmonKpi>();
		String avmonSql = "  select tm.mo_id,tm.type_id ,tm.caption ,tk.caption kpiCaption,"
				+ "  tk.kpi_name,tk.unit,tl.kpi_code,tl.instance,tc.value,tc.num_value,"
				+ "  tc.str_value,tc.kpi_time"
				+ "  from tf_avmon_kpi_value_current tc "
				+ "  left join tf_avmon_kpi_value_list tl on tc.value_key=tl.value_key"
				+ "  left join td_avmon_mo_info tm on tl.mo_id=tm.mo_id "
				+ "  left join  td_avmon_kpi_info tk on tk.kpi_code=tl.kpi_code "
				+ "  where tk.kpi_name = '"
				+ kpiName
				+ "'"
				+ "  and tm.mo_id= '"
				+ nodeId
				+ "'"
				+ "  and rownum=1 order by tc.kpi_time desc";
		AvmonKpi akpi = null;
		try {
			conn = du.getConnection();
			ps = conn.prepareStatement(avmonSql);
			log.debug(avmonSql);
			rs = ps.executeQuery();
			if (rs.next()) {
				akpi = new AvmonKpi();
				akpi.setNodeId(rs.getString("mo_id"));
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
		return akpi;
	}

	private List<AvmonKpi> getKpi(String[] kpiCode, String[] kpiName,
			String nodeId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AvmonKpi> li = new ArrayList<AvmonKpi>();
		String avmonSql = "  select tm.mo_id,tm.type_id ,tm.caption ,tk.caption kpiCaption,"
				+ "  tk.kpi_name,tk.unit,tl.kpi_code,tl.instance,tc.value,tc.num_value,"
				+ "  tc.str_value,tc.kpi_time"
				+ "  from tf_avmon_kpi_value_current tc "
				+ "  left join tf_avmon_kpi_value_list tl on tc.value_key=tl.value_key"
				+ "  left join td_avmon_mo_info tm on tl.mo_id=tm.mo_id "
				+ "  left join  td_avmon_kpi_info tk on tk.kpi_code=tl.kpi_code "
				+ " where 1=1 ";

		if (kpiCode != null) {
			String codes = "";
			for (String c : kpiCode) {
				codes = codes + "'" + c + "',";
			}
			avmonSql = avmonSql + "  and tk.kpi_code in (" + codes + ")";
		}
		if (kpiName != null) {
			String names = "";
			for (String c : kpiName) {
				names = names + "'" + c + "',";
			}
			names = names.substring(0, names.length() - 1);
			avmonSql = avmonSql + "  and tk.kpi_name in (" + names + ")";
		}
		if (nodeId != null && !"".equals(nodeId)) {
			avmonSql = avmonSql + " and tm.mo_id='" + nodeId + "'";
		}

		try {
			conn = du.getConnection();
			ps = conn.prepareStatement(avmonSql);
			log.debug(avmonSql);
			rs = ps.executeQuery();
			while (rs.next()) {
				AvmonKpi akpi = new AvmonKpi();
				akpi.setNodeId(rs.getString("mo_id"));
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

	public void syncAvmon(Map<String, Map<String, Node>> nodeMa,
			Map<String, Map<String, RelationNode>> reMa) throws CmdbException {
		// 不自动删除
		// 类扫描

		Iterator<String> ite = as.getClasKpiMa().keySet().iterator();
		while (ite.hasNext()) {
			String clas = ite.next();
			
				// 建立 类实例
				Node nodeT = cmdbService.getCiTypeByName(clas);
				List<AvmonKpi> avli = getKpiByName(as.getClasKpiMa().get(clas));
				
				for (AvmonKpi ak : avli) {
				  try {
					if (nodeMa.get(clas) == null) {
						nodeMa.put(clas, new HashMap<String, Node>());
					}
					Node node = nodeMa.get(clas).get(ak.getValue());
					if (node == null) {
						node = cmdbService.getCiByName(clas, ak.getValue());
						if (node == null) {
							node = nodeT.clone();
							node.setIsType(false);
							node.setDerivedFrom(nodeT.getName());
							node.setId(idg.generate());
							node.setParentId(nodeT.getId());
							node.setName(ak.getValue());
							// -- 可从某扩展属性中选取 todo
							node.setLabel(ak.getValue());
							// 从 avmon 采集 ExchangedId对应kpicode
							node.setExchangedId(ak.getCode());
							// 新建
							node.setSyncStatus(1);
						} else {
							// 更新
							node.setParentId(nodeT.getId());
							node.setSyncStatus(2);
//							node.setAttributes(nodeT.clone().getAttributes());
						}
						nodeMa.get(clas).put(node.getName(), node);
					}

					// 获得类属性
					Map<String, String> attMa = new HashMap<String, String>();
					List<String> attLi = as.getClasAttKpiMa().get(clas);
					String[] ks = new String[attLi.size()];
					int i = 0;
					for (String att : attLi) {
						String[] s = att.split("\\:");
						if (s.length >= 2) {
							attMa.put(s[1], s[0]);
							ks[i] = s[1];
						} else {
							attMa.put(att, att);
							ks[i] = att;
						}
						i++;
					}

					// 获得属性kpi
					List<AvmonKpi> li = getKpiByNodeId(ak.getNodeId(), ks);
					for (AvmonKpi akatt : li) {
						CiAttribute catt = node.getAttributes().get(
								attMa.get(akatt.getName()).toLowerCase());
						if (catt != null) {
							if(catt.getUpdateMode()!=1)
							   catt.setValue(akatt.getValue());
						} else {
							log.error(clas + " no attribute ["
									+ attMa.get(akatt.getName()) + "]");
						}
					}
				  } catch (Exception e) {
						log.error(e);
						e.printStackTrace();
				  }
				}
		}
		// 子类扫描
		Iterator<String> ite_sub = as.getSubClasKpiMa().keySet().iterator();
		while (ite_sub.hasNext()) {
			String clas = ite_sub.next();
				// 建立 子类实例
				Node nodeT = cmdbService.getCiTypeByName(clas);
				List<AvmonKpi> avli = getKpiByName(as.getSubClasKpiMa().get(clas));
				for (AvmonKpi ak : avli) {					
				 try {	
					if (nodeMa.get(clas) == null) {
						nodeMa.put(clas, new HashMap<String, Node>());
					}
					Node node = nodeMa.get(clas).get(ak.getNodeName()+"."+ak.getInstance());
					if (node == null) {
						node = cmdbService.getCiByName(clas, ak.getNodeName()+"."+ak.getInstance());
						if (node == null) {
							node = nodeT.clone();
							node.setIsType(false);
							node.setDerivedFrom(nodeT.getName());
							node.setId(idg.generate());
							node.setParentId(nodeT.getId());
							// 子类 用 Instance
							node.setName(ak.getNodeName()+"."+ak.getInstance());
							node.setLabel(ak.getNodeName()+"."+ak.getInstance());
							// 新建
							node.setSyncStatus(1);
						} else {
							// 更新
							node.setSyncStatus(2);
							node.setAttributes(nodeT.clone().getAttributes());
						}
						nodeMa.get(clas).put(node.getName(), node);
					}
					// 建立主类 - 子类关系
					String pClas = as.getSubReClas().get(clas);
					AvmonKpi akt = getKpiCur(as.getClasKpiMa().get(pClas), ak
							.getNodeId());
					// if(akt!=null)
					String pNodeName = akt.getValue();
					Node pNode = nodeMa.get(pClas).get(pNodeName);
					if(reMa.get(as.getClass_subclas())==null){
						reMa.put(as.getClass_subclas(), new HashMap<String,RelationNode>());
					}
					reMa.get(as.getClass_subclas()).put(
							pNode.getName() + "_" + node.getName(),
							getRelation(as.getClass_subclas(), pNode, node));

				
				// 获得子类属性 名称
				Map<String, String> attMa = new HashMap<String, String>();
				List<String> attLi = as.getSubClasAttKpiMa().get(clas);
				String[] ks = new String[attLi.size()];
				int i = 0;
				for (String att : attLi) {
					String[] s = att.split("\\:");
					if (s.length >= 2) {
						attMa.put(s[0], s[1]);
						ks[i] = s[1];
					} else {
						attMa.put(att, att);
						ks[i] = att;
					}
					i++;
				}
				// 获得属性kpi值 --子类
				List<AvmonKpi> li = getKpiByNodeId(ak.getNodeId(),ks);
				for (AvmonKpi akatt : li) {
					CiAttribute catt = node.getAttributes().get(
							attMa.get(akatt.getName()).toLowerCase());
					if (catt != null) {
						catt.setValue(akatt.getValue());
					} else {
						log.error(clas + " no attribute ["
								+ attMa.get(akatt.getName()) + "]");
					}
				}
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}
			}
		}
		// 关系扫描
		List<String> reLi = as.getRelations();
		for (String re : reLi) {
			// 0:kpi,1:relation,2:source,3:dest
			String s[] = re.split(",");
			List<AvmonKpi> kli = getKpiByName(s[0]);
			for (AvmonKpi ak : kli) {
				Node snode = null;
				if (nodeMa.get(s[2]) != null)
					snode = nodeMa.get(s[2]).get(ak.getInstance());
				if (snode == null) {
					snode = cmdbService.getCiByName(s[2], ak.getInstance());
				}
				Node dnode = null;
				if (nodeMa.get(s[3]) != null)
					dnode = nodeMa.get(s[3]).get(ak.getInstance());
				if (snode == null) {
					dnode = cmdbService.getCiByName(s[3], ak.getValue());
				}
				reMa.get(s[1]).put(snode.getName() + "_" + dnode.getName(),
						getRelation(s[1], snode, dnode));
			}
		}

	}

	public RelationNode getRelation(String reType, Node sour, Node dest) {
		String reName = sour.getName() + "_" + dest.getName();
		RelationNode rn = cmdbService.getRelationCiByName(reType, reName);
		try {

			if (rn == null) {
				RelationNode rnT = cmdbService.getRelationTypeByName(as.getClass_subclas());
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
		} catch (Exception e) {
			log.error("", e);
			e.printStackTrace();
		}
		return rn;
	}

	@Override
	public String syncAvmonCi(Boolean preView) throws CmdbException {
		String ren = "";
		String reu = "";
		String red = "";
		Map<String, Map<String, Node>> nodesMa = new HashMap<String, Map<String, Node>>();
		Map<String, Map<String, RelationNode>> relationsMa = new HashMap<String, Map<String, RelationNode>>();
		syncAvmon(nodesMa, relationsMa);
		Iterator<String> ite = nodesMa.keySet().iterator();
		while (ite.hasNext()) {
			String typeName = ite.next();
			Map<String, Node> ma = nodesMa.get(typeName);
			if (ma != null) {
				String re[] = insertNode(ma.values(), preView);
				ren = ren + "\r\n" + re[0];
				reu = reu + "\r\n" + re[1];
				// 自动不做删除，可标记失效 todo
				// String redt=deleteNode(typeName, ma, preView);
				// if(redt!=null&&!"".equals(redt)){
				// red=red+"\r\n"+redt;
				// }
			}
		}
		Iterator<String> iteRe = relationsMa.keySet().iterator();
		while (iteRe.hasNext()) {
			String typeName = iteRe.next();
			Map<String, RelationNode> ma = relationsMa.get(typeName);
			if (ma != null) {
				String re[] = insertNodeRe(ma.values(), preView);
				ren = ren + "\r\n" + re[0];
				reu = reu + "\r\n" + re[1];
				// 自动不做删除，可标记失效 todo
				// String redt=deleteNodeRe(typeName, ma, preView);
				// if(redt!=null&&!"".equals(redt)){
				// red=red+"\r\n"+redt;
				// }
			}
		}
		return red + "\r\n" + ren + "\r\n" + reu;
	}

	public String[] insertNode(Collection<Node> ch, Boolean preView)
			throws CmdbException {
		String ren = "";
		String reu = "";
		String tag_new = "";
		String tag_update = "";
		if (!preView) {
			tag_new = "[已新建]";
			tag_update = "[已更新]";
		} else {
			tag_new = "[新建]";
			tag_update = "[更新]";
		}
		for (Node n1 : ch) {
			if (n1.getSyncStatus() == 1) {
				ren = ren + "\r\n" + n1.getDerivedFrom() + ":" + n1.getName()
						+ ":" + n1.getId() + tag_new;
			} else {
				reu = reu + "\r\n" + n1.getDerivedFrom() + ":" + n1.getName()
						+ ":" + n1.getId() + tag_update;
			}
			log.debug(n1.getDerivedFrom() + ":" + n1.getName());
			Collection<CiAttribute> cas = n1.getAttributes().values();
			for (CiAttribute ca : cas) {
				String value = ca.getValue();
				if (value != null && value.length() > 21) {
					value = value.substring(0, 20);
				}
				log
						.debug("[" + n1.getName() + "]" + ca.getName() + ":"
								+ value);
			}
		}
		if (!preView) {
			List<Node> li = new ArrayList<Node>();
			li.addAll(ch);
			cmdbService.insertNodeCis(li);
		}
		String[] re = new String[2];
		re[0] = ren;
		re[1] = reu;
		return re;
	}

	public String[] insertNodeRe(Collection<RelationNode> rch, Boolean preView)
			throws CmdbException {
		String reu = "";
		String ren = "";
		for (RelationNode n1 : rch) {
			if (n1.getSyncStatus() == 1) {
				ren = ren + "\r\n" + n1.getDerivedFrom() + ":" + n1.getName()
						+ ":" + n1.getId() + "[新建]";
			} else {
				reu = reu + "\r\n" + n1.getDerivedFrom() + ":" + n1.getName()
						+ ":" + n1.getId() + "[更新]";
			}
			log.debug(n1.getDerivedFrom() + "：" + n1.getName());
			log.debug("-relation-from:" + n1.getSourceName() + ","
					+ n1.getSourceType() + n1.getSourceId());
			log.debug("-relation---to:" + n1.getTargetName() + ","
					+ n1.getTargetType() + n1.getTargetId());
		}

		if (!preView) {
			List<RelationNode> li = new ArrayList<RelationNode>();
			li.addAll(rch);
			cmdbService.insertRelationCis(li);
		}
		String re[] = new String[2];
		re[0] = ren;
		re[1] = reu;
		return re;
	}

	@Override
	public String getKpiByClass(String className) {
        String re="";
        re=as.getClasKpiCodeMa().get(className);
        if(re==null||"".equals(re)){
         re=as.getSubClasKpiCodeMa().get(className);
        }
		return re;
	}
	public String getClassByKpi(String kpiCode) {        
        Map<String,String> ma=as.getClasKpiCodeMa();
        for(String k:ma.keySet()){
        	if(kpiCode.equals(ma.get(k))){
        		return k;
        	}
        }        
        ma=as.getSubClasKpiCodeMa();
        for(String k:ma.keySet()){
        	if(kpiCode.equals(ma.get(k))){
        		return k;
        	}
        }
        return "";
	}
	public List<String> getClassViewKpi(String className) {
        List<String> re=null;
        re=as.getClasViewKpiMa().get(className);
        if(re==null){
         re=as.getSubclasViewKpiMa().get(className);
        }
		return re;
	}
	public String getViewFuncKpi(String func){
		return as.getViewFuncKpiMa().get(func);
	}
}
