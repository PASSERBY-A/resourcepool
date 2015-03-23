package com.hp.avmon.snmp.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.hp.avmon.alarm.service.AlarmViewService;
import com.hp.avmon.alarm.web.AlarmController;
import com.hp.avmon.common.Config;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.deploy.store.AgentStore;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmon.utils.Utils;
import com.hp.avmonserver.entity.Agent;
import com.hp.avmonserver.entity.Alarm;
import com.hp.avmonserver.entity.MO;
import com.hp.avmonserver.api.AvmonServer;

@Service
public class SnmpService {
    
    private static final Logger logger = LoggerFactory.getLogger(SnmpService.class);

    //private static volatile List<HashMap> alarmList=null;
    
    //private static volatile Map<String,Alarm> alarmCache=null;
    
    //private final static Object lock = new Object();
    
    
    @Autowired
    private AvmonServer avmonServer;
    
    @Autowired
    private JdbcTemplate jdbc;

    public SnmpService(){

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
