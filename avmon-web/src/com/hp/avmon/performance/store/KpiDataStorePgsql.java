package com.hp.avmon.performance.store;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmonserver.entity.KpiEvent;

@Service
public class KpiDataStorePgsql extends KpiDataStoreOracle{
	
    private static final Log logger = LogFactory.getLog(KpiDataStorePgsql.class);
	
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 构造函数
     */
    public KpiDataStorePgsql() {
    	// 加载连接对象
    	if (jdbcTemplate == null) {
        	jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");
    	}
    }


}