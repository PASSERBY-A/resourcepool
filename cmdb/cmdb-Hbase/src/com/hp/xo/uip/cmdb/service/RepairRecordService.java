package com.hp.xo.uip.cmdb.service;

import java.util.List;

import com.hp.xo.uip.cmdb.domain.RepairRecord;
import com.hp.xo.uip.cmdb.exception.CmdbException;

public interface RepairRecordService {
	public RepairRecord insert(RepairRecord cr) throws CmdbException ;
    public RepairRecord update(RepairRecord cr) throws CmdbException ;
    public int delete(String nodeType,Long nodeId,Long RepairRecordId) throws CmdbException ;
    /**
     * rowKey : nodeType_nodeId_RepairRecordId
     * @param rowKeyLi
     * @return
     * @throws CmdbException
     */
    public int delete(List<String> rowKeyLi) throws CmdbException ;
    /**
     * 单条变更记录
     * @param nodeKey: nodeType_nodeId
     * @param RepairRecordId
     * @return
     * @throws CmdbException
     */
    public RepairRecord getRecord(String nodeKey,Long RepairRecordId) throws CmdbException ;
    /**
     * 变更列表
     * @param nodeType
     * @param nodeId
     * @return
     * @throws CmdbException
     */
    public List<RepairRecord> getRecordList(String nodeType,Long nodeId) throws CmdbException ;
    /**
     * 分页变更列表， RepairRecordId为空
     * @param nodeType 节点类型
     * @param nodeId   节点id
     * @param RepairRecordId 起始变更记录ID
     * @param row 返回条数
     * @return
     */
    public List<RepairRecord> getRecordList(String nodeType,Long nodeId,Long RepairRecordId,int row) throws CmdbException ;

}
