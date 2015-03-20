package com.hp.xo.uip.cmdb.service.impl;

import java.util.List;

import com.hp.xo.uip.cmdb.dao.RepairRecordDao;
import com.hp.xo.uip.cmdb.domain.RepairRecord;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.service.RepairRecordService;

public class RepairRecordServiceImpl implements RepairRecordService{
    private RepairRecordDao repairRecordDao;
    
	public RepairRecordDao getRepairRecordDao() {
		return repairRecordDao;
	}

	public void setRepairRecordDao(RepairRecordDao repairRecordDao) {
		this.repairRecordDao = repairRecordDao;
	}

	@Override
	public int delete(String nodeType, Long nodeId, Long RepairRecordId)
			throws CmdbException {
		return repairRecordDao.delete(nodeType, nodeId, RepairRecordId);
	}

	@Override
	public int delete(List<String> rowKeyLi) throws CmdbException {
		return repairRecordDao.delete(rowKeyLi);
	}

	@Override
	public RepairRecord getRecord(String nodeKey, Long RepairRecordId)
			throws CmdbException {
		return repairRecordDao.getRecord(nodeKey, RepairRecordId);
	}

	@Override
	public List<RepairRecord> getRecordList(String nodeType, Long nodeId)
			throws CmdbException {
		return repairRecordDao.getRecordList(nodeType, nodeId);
	}

	@Override
	public List<RepairRecord> getRecordList(String nodeType, Long nodeId,
			Long RepairRecordId, int row) throws CmdbException {
		return repairRecordDao.getRecordList(nodeType, nodeId, RepairRecordId, row);
	}

	@Override
	public RepairRecord insert(RepairRecord cr) throws CmdbException {
		return repairRecordDao.insert(cr);
	}

	@Override
	public RepairRecord update(RepairRecord cr) throws CmdbException {
		return repairRecordDao.update(cr);
	}

}
