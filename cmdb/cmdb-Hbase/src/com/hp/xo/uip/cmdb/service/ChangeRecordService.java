package com.hp.xo.uip.cmdb.service;

import java.util.List;

import com.hp.xo.uip.cmdb.domain.ChangeRecord;
import com.hp.xo.uip.cmdb.exception.CmdbException;

public interface ChangeRecordService {
	public ChangeRecord insert(ChangeRecord cr) throws CmdbException ;
    public ChangeRecord update(ChangeRecord cr) throws CmdbException ;
    public int delete(String nodeType,Long nodeId,Long changeRecordId) throws CmdbException ;
    /**
     * rowKey : nodeType_nodeId_changeRecordId
     * @param rowKeyLi
     * @return
     * @throws CmdbException
     */
    public int delete(List<String> rowKeyLi) throws CmdbException ;
    /**
     * 单条变更记录
     * @param nodeKey: nodeType_nodeId
     * @param changeRecordId
     * @return
     * @throws CmdbException
     */
    public ChangeRecord getRecord(String nodeKey,Long changeRecordId) throws CmdbException ;
    /**
     * 变更列表
     * @param nodeType
     * @param nodeId
     * @return
     * @throws CmdbException
     */
    public List<ChangeRecord> getRecordList(String nodeType,Long nodeId) throws CmdbException ;
    /**
     * 分页变更列表， changeRecordId为空
     * @param nodeType 节点类型
     * @param nodeId   节点id
     * @param changeRecordId 起始变更记录ID
     * @param row 返回条数
     * @return
     */
    public List<ChangeRecord> getRecordList(String nodeType,Long nodeId,Long changeRecordId,int row) throws CmdbException ;
}
