package com.hp.xo.uip.cmdb.dao.impl.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.log4j.Logger;

import com.hp.xo.uip.cmdb.dao.RepairRecordDao;
import com.hp.xo.uip.cmdb.dao.template.HBaseDaoTemplate;
import com.hp.xo.uip.cmdb.domain.ChangeRecord;
import com.hp.xo.uip.cmdb.domain.RepairRecord;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.util.IConstants;
import com.hp.xo.uip.cmdb.util.IdGenerator;
import com.hp.xo.uip.cmdb.util.StringUtil;

public class RepairRecordDaoXmlImpl implements RepairRecordDao{
	private static Logger log = Logger.getLogger(RepairRecordDaoXmlImpl.class);
	private IdGenerator idg=IdGenerator.createGenerator();
	private String conn=IConstants.connectStr;
	private String tname=HBaseDaoTemplate.repairRecordTableName;
	private HBaseDaoTemplate hbaseDaoTemplate;
	public HBaseDaoTemplate getHbaseDaoTemplate() {
		return hbaseDaoTemplate;
	}

	public void setHbaseDaoTemplate(HBaseDaoTemplate hbaseDaoTemplate) {
		this.hbaseDaoTemplate = hbaseDaoTemplate;
	}

	@Override
	public int delete(String nodeType, Long nodeId, Long RepairRecordId) throws CmdbException {
		String rowKey=nodeType+conn+nodeId+conn+RepairRecordId;
		List<String> li=new ArrayList<String>();
		li.add(rowKey);
		hbaseDaoTemplate.delete(tname,li);
		return 1;
	}

	@Override
	public int delete(List<String> rowKeyLi) throws CmdbException {
		hbaseDaoTemplate.delete(tname,rowKeyLi);
		return rowKeyLi.size();
	}

	@Override
	public List<RepairRecord> getRecordList(String nodeType, Long nodeId) throws CmdbException {
		String key=nodeType+conn+nodeId;
		List<RepairRecord> li=new ArrayList<RepairRecord>();
		Map<String,List<Cell>> ma=hbaseDaoTemplate.getRowCellByKeyFilter(tname, key+ "_000000", key+"_999999", -1);
		Iterator<String> ite2= ma.keySet().iterator();
		while(ite2.hasNext()){
		  String k2=ite2.next();
		    li.add(transferCRecord(ma.get(k2)));
		}
		return li;
	}

	public RepairRecord transferCRecord(List<Cell> cells){
		RepairRecord cr=new RepairRecord();
		Map<String,String> properties=cr.getMapData();
		try{
		for (Cell c : cells) {
			String cName = StringUtil.newStr(CellUtil.cloneQualifier(c));
			String cValue = StringUtil.newStr(CellUtil.cloneValue(c));
			if (properties.containsKey(cName)) {
				if("repairDate".equals(cName)||"maDate".equals(cName)){
				    BeanUtils.setProperty(cr, cName, Long.parseLong(cValue));
				}else{
					BeanUtils.setProperty(cr, cName, cValue);
				}
			}
		}
	    } catch (Exception e) {
		 log.error("", e);
		 e.printStackTrace();
	    }
		return cr;
	}
	@Override
	public List<RepairRecord> getRecordList(String nodeType, Long nodeId,
			Long startRepairRecordId, int row) throws CmdbException {
		String key=nodeType+conn+nodeId;
		String startKey=key+ "_000000";
		int rrow=-1;
		if(row>0)rrow=row;
		if(startRepairRecordId!=null&&startRepairRecordId!=0){
			startKey=key+"_"+startRepairRecordId;
			if(row>0)rrow=row+1;
		}
			
		List<RepairRecord> li=new ArrayList<RepairRecord>();
		Map<String,List<Cell>> ma=hbaseDaoTemplate.getRowCellByKeyFilter(tname, startKey, key+"_999999", rrow);
		Iterator<String> ite2= ma.keySet().iterator();
		while(ite2.hasNext()){
		    String k2=ite2.next();
		    if(!k2.equals(startKey)){
		       li.add(transferCRecord(ma.get(k2)));
		    }
		}
		return li;
	}

	@Override
	public RepairRecord insert(RepairRecord cr) throws CmdbException {
		if(cr.getId()==null||cr.getId()==0){
			cr.setId(idg.generate());
		}
		String rowKey = cr.getNodeKey()+ "_" + cr.getId();
		hbaseDaoTemplate.save(tname,
				hbaseDaoTemplate.getDefColFamily(), rowKey, cr.getMapData());
		return cr;
	}

	@Override
	public RepairRecord update(RepairRecord cr) throws CmdbException {
		return insert(cr);
	}

	@Override
	public RepairRecord getRecord(String nodeKey, Long RepairRecordId)
			throws CmdbException {
		RepairRecord cr=new RepairRecord();
		String key=nodeKey+conn+RepairRecordId;
		List<Cell> li=hbaseDaoTemplate.getRowCellByKey(tname, key);
		cr=transferCRecord(li);
		return cr;
	}

}
