package com.hp.xo.uip.cmdb.service.impl;

import java.util.List;

import com.hp.xo.uip.cmdb.dao.ChangeRecordDao;
import com.hp.xo.uip.cmdb.domain.ChangeRecord;
import com.hp.xo.uip.cmdb.exception.CmdbException;
import com.hp.xo.uip.cmdb.service.ChangeRecordService;

public class ChangeRecordServiceImpl implements ChangeRecordService{
    private ChangeRecordDao changeRecordDao;
    
	public ChangeRecordDao getChangeRecordDao() {
		return changeRecordDao;
	}

	public void setChangeRecordDao(ChangeRecordDao changeRecordDao) {
		this.changeRecordDao = changeRecordDao;
	}

	@Override
	public int delete(String nodeType, Long nodeId, Long changeRecordId)
			throws CmdbException {
		return changeRecordDao.delete(nodeType, nodeId, changeRecordId);
	}

	@Override
	public int delete(List<String> rowKeyLi) throws CmdbException {
		return changeRecordDao.delete(rowKeyLi);
	}

	@Override
	public ChangeRecord getRecord(String nodeKey, Long changeRecordId)
			throws CmdbException {
		return changeRecordDao.getRecord(nodeKey, changeRecordId);
	}

	@Override
	public List<ChangeRecord> getRecordList(String nodeType, Long nodeId)
			throws CmdbException {
		return changeRecordDao.getRecordList(nodeType, nodeId);
	}

	@Override
	public List<ChangeRecord> getRecordList(String nodeType, Long nodeId,
			Long changeRecordId, int row) throws CmdbException {
		return changeRecordDao.getRecordList(nodeType, nodeId, changeRecordId, row);
	}

	@Override
	public ChangeRecord insert(ChangeRecord cr) throws CmdbException {
		return changeRecordDao.insert(cr);
	}

	@Override
	public ChangeRecord update(ChangeRecord cr) throws CmdbException {
		return changeRecordDao.update(cr);
	}
   
}
