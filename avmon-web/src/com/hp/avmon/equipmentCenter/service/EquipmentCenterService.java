package com.hp.avmon.equipmentCenter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.utils.SqlManager;
import com.hp.avmonserver.api.AvmonServer;

@Service
public class EquipmentCenterService {
    
    private static final Logger logger = LoggerFactory.getLogger(EquipmentCenterService.class);

    @Autowired
    private AvmonServer avmonServer;
    
    @Autowired
    private JdbcTemplate jdbc;

    public EquipmentCenterService(){

    }
    
    public List<Map<String,String>> getBusinessTree(String userId){

      
        String sql=String.format(SqlManager.getSql(this, "getBusinessTree"));
        
        List<Map<String,String>> list = jdbc.queryForList(sql);
        return list;
    }

    /**
     * 查询业务系统下所有节点个数
     * @param bizName
     * @return
     */
	public int getBusinessNodeCount(String bizName) {
		String sql=String.format(SqlManager.getSql(this, "menu_tree_by_businessType_count"),bizName);
		int count = jdbc.queryForInt(sql);
		return count;
	}

	/**
	 * 查询所有其他业务系统下的设备总数
	 * @return
	 */
	public int getOtherBusinessNodeCount() {
		String sql=String.format(SqlManager.getSql(this, "getOtherBusinessNodeCount"));
		int count = jdbc.queryForInt(sql);
		return count;
	}
	
	/**
	 * 查询所有业务系统
	 * @return
	 */
	public List<Map<String,String>> getBusinessList(){
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String sql=String.format(SqlManager.getSql(this, "getBusinessList"));
		list = jdbc.queryForList(sql);
		logger.debug(list.toString());
		return list;
	}
	
	/**
	 * 根据ID从业务系统字典表中查找业务系统名称
	 * @param bizId
	 * @return
	 */
	public String getBusinessNameById(String bizId){
		String bizName = null;
		String sql=String.format(SqlManager.getSql(this, "getBusinessNameById"),bizId);
		List<Map<String,String>> list = jdbc.queryForList(sql);
		if(list.size()>0){
			bizName = list.get(0).get("BUSINESSNAME");
		}
		
		return bizName;
	}
}
