package com.hp.xo.uip.cmdb.dao.impl.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.log4j.Logger;

import com.hp.xo.uip.cmdb.dao.ChangeRecordDao;
import com.hp.xo.uip.cmdb.dao.template.HBaseDaoTemplate;
import com.hp.xo.uip.cmdb.domain.ChangeRecord;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.util.IConstants;
import com.hp.xo.uip.cmdb.util.IdGenerator;
import com.hp.xo.uip.cmdb.util.StringUtil;

public class ChangeRecordDaoXmlImpl implements ChangeRecordDao{
	private static Logger log = Logger.getLogger(ChangeRecordDaoXmlImpl.class);
	private IdGenerator idg=IdGenerator.createGenerator();
	private String conn=IConstants.connectStr;
	private String tname=HBaseDaoTemplate.changeRecordTableName;
	private HBaseDaoTemplate hbaseDaoTemplate;
	public HBaseDaoTemplate getHbaseDaoTemplate() {
		return hbaseDaoTemplate;
	}

	public void setHbaseDaoTemplate(HBaseDaoTemplate hbaseDaoTemplate) {
		this.hbaseDaoTemplate = hbaseDaoTemplate;
	}

	@Override
	public int delete(String nodeType, Long nodeId, Long ChangeRecordId) throws CmdbException {
		String rowKey=nodeType+conn+nodeId+conn+ChangeRecordId;
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
	public List<ChangeRecord> getRecordList(String nodeType, Long nodeId) throws CmdbException {
		String key=nodeType+conn+nodeId;
		List<ChangeRecord> li=new ArrayList<ChangeRecord>();
		Map<String,List<Cell>> ma=hbaseDaoTemplate.getRowCellByKeyFilter(tname, key+ "_000000", key+"_999999", -1);
		Iterator<String> ite2= ma.keySet().iterator();
		while(ite2.hasNext()){
		  String k2=ite2.next();
		    li.add(transferCRecord(ma.get(k2)));
		}
		return li;
	}

	public ChangeRecord transferCRecord(List<Cell> cells){
		ChangeRecord cr=new ChangeRecord();
		Map<String,String> properties=cr.getMapData();
		try{
		for (Cell c : cells) {
			String cName = StringUtil.newStr(CellUtil.cloneQualifier(c));
			String cValue = StringUtil.newStr(CellUtil.cloneValue(c));
			if (properties.containsKey(cName)) {
				if("cdate".equals(cName)){
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
	/**
	 * nodeKey: nodeType_nodeId
	 * @param nodeKey
	 * @param startChangeRecordId
	 * @param row
	 * @return
	 * @throws CmdbException
	 */
	public List<ChangeRecord> getRecordList(String nodeKey,
			Long startChangeRecordId, int row) throws CmdbException {
		if(nodeKey!=null&&nodeKey.contains(IConstants.connectStr)){
		 String s[]= nodeKey.split(IConstants.connectStr);
		 return getRecordList(s[0], Long.parseLong(s[1]), startChangeRecordId, row);
		}
		return null;
	}
	@Override
	public List<ChangeRecord> getRecordList(String nodeType, Long nodeId,
			Long startChangeRecordId, int row) throws CmdbException {
		String key=nodeType+conn+nodeId;
		String startKey=key+ "_000000";
		int rrow=-1;
		if(row>0)rrow=row;
		if(startChangeRecordId!=null&&startChangeRecordId!=0){
			startKey=key+"_"+startChangeRecordId;
			if(row>0)rrow=row+1;
		}
			
		List<ChangeRecord> li=new ArrayList<ChangeRecord>();
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
	public ChangeRecord insert(ChangeRecord cr) throws CmdbException {
		if(cr.getId()==null||cr.getId()==0){
			cr.setId(idg.generate());
		}
		String rowKey = cr.getNodeKey()+ "_" + cr.getId();
		List<ChangeRecord> li= getRecordList(cr.getNodeKey(), null, 1);
		if(li!=null&&li.size()>0){
			cr.setSequence(li.get(0).getSequence()+1);
		}
		
		hbaseDaoTemplate.save(tname,
				hbaseDaoTemplate.getDefColFamily(), rowKey, cr.getMapData());
		return cr;
	}

	@Override
	public ChangeRecord update(ChangeRecord cr) throws CmdbException {
		return insert(cr);
	}

	@Override
	public ChangeRecord getRecord(String nodeKey, Long ChangeRecordId)
			throws CmdbException {
		ChangeRecord cr=new ChangeRecord();
		String key=nodeKey+conn+ChangeRecordId;
		List<Cell> li=hbaseDaoTemplate.getRowCellByKey(tname, key);
		cr=transferCRecord(li);
		return cr;
	}

}
