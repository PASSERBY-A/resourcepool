package com.hp.avmon.performance.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.performance.store.KpiDataStore;
import com.hp.avmon.utils.DBUtils;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmonserver.api.AvmonServer;
import com.hp.avmonserver.entity.KpiEvent;
import com.hp.avmonserver.entity.MO;

@Service
public class DashboardService {
    
    private static final Log logger = LogFactory.getLog(DashboardService.class);

    private static volatile List<Map> kpiList=null;
    
    @Autowired
    private AvmonServer avmonServer;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private KpiDataStore kpiDataStore;
    
    private void init(){
        logger.info("init kpi performance data.");
        String sql=String.format("select a.target_id as \"mo\"" +
                ",a.caption as \"amp\"" +
                ",b.caption as \"kpiName\"" +
                ",b.name as \"kpiCode\" " +
                ",null as \"value\" " +
                ",'unknown' as \"status\" " +
                "from TD_AVMON_amp_INSTANCE a,TD_AVMON_KPI_INFO b where a.amp_id=b.amp_id");
        kpiList=jdbcTemplate.queryForList(sql);
        
    }
    
    public String getHostDashboardJson(String moId){
        Random r = new Random();
        Map map=new HashMap();
        
        map.put("basic",getHostBasicInfo(moId));
        map.put("cpu",getHostCpuInfo(moId));
        map.put("mem",getHostMemInfo(moId));
        map.put("newAlarm", getHostNewAlarm(moId));
        
        return JackJson.fromObjectToJson(map);
      
    }

    

    public Object getHostCpuInfo(String moId) {
        Random r=new Random();
        //int cpuCount=16;
        //get cpucount
        MO mo=avmonServer.getMoById(moId);
//        try{
//            cpuCount=Integer.valueOf(mo.getAttr("cpuAmount"));
//        }catch(Exception e){};
        //
        int dataPoints=10;
        Map map=new HashMap();
        //map.put("cpuCount", cpuCount);
        List totalHistory=new ArrayList();
        
        SimpleDateFormat df=new SimpleDateFormat("mm:ss");
        Date now=new Date();
        
        List history=new ArrayList();
        List<KpiEvent> eventList=kpiDataStore.getKpiHistory(moId,"wk_cpu_1001","sys_idle","/",dataPoints);
        if(eventList!=null && eventList.size()>=dataPoints){
            for(int j=eventList.size()-1;j>=0;j--){
                KpiEvent event=eventList.get(j);
                String stime=df.format(event.getKpiTime());
                int usage=(int) (100-event.getNumValue());
                Map m=new HashMap();
                m.put("time", stime);
                m.put("usage", usage);
                history.add(m);
            }
        }
        else{
            System.out.println("get wk_cpu_1001-sys_idle empty!");
            Calendar c1 = Calendar.getInstance();
            c1.setTime(now);
            for(int j=0;j<dataPoints;j++){
                c1.add(Calendar.SECOND, -30);
                Date time=c1.getTime();
                String stime=df.format(c1.getTime());
                int usage=0;
                Map m=new HashMap();
                m.put("time", stime);
                m.put("usage", usage);
                history.add(0,m);
            }
        }
        map.put("history", history);
        
        return map;
    }

    public Object getHostMemInfo(String moId) {
        int dataPoints=5;
        Map map=new HashMap();

        List totalHistory=new ArrayList();
        
        SimpleDateFormat df=new SimpleDateFormat("HH:mm");
        Date now=new Date();
        
        List rows=new ArrayList();
        
        map.put("rows", rows);
        List history=new ArrayList();
//        Calendar c1 = Calendar.getInstance();
//        c1.setTime(now);
        List<KpiEvent> eventList=kpiDataStore.getKpiHistory(moId,"wk_mem_1002","mem_usage","/",dataPoints);
        if(eventList!=null && eventList.size()>=dataPoints){
            for(int j=eventList.size()-1;j>=0;j--){
                KpiEvent event=eventList.get(j);
                String stime=df.format(event.getKpiTime());
                int usage=(int) (100-event.getNumValue());
                Map m=new HashMap();
                m.put("time", stime);
                m.put("usage", usage);
    
                int memUser=(int) (event.getNumValue()*0.85);
                int memSys=(int) (event.getNumValue()*0.15);
                int memFree=100-memUser-memSys;
                int swapUsage=0;
                int swapFree=0;
                m.put("mem_user", memUser);
                m.put("mem_sys", memSys);
                m.put("mem_free", memFree);
                m.put("swap_usage", swapUsage);
                m.put("swap_free", swapFree);
                history.add(m);
            }
        }
        else{
            Calendar c1 = Calendar.getInstance();
            c1.setTime(now);
            for(int j=0;j<dataPoints;j++){
                c1.add(Calendar.MINUTE, -1);
                Date time=c1.getTime();
                String stime=df.format(c1.getTime());
                int usage=0;
                Map m=new HashMap();
                m.put("time", stime);
                int memUser=0;
                int memSys=0;
                int memFree=0;
                int swapUsage=0;
                int swapFree=0;
                m.put("mem_user", memUser);
                m.put("mem_sys", memSys);
                m.put("mem_free", memFree);
                m.put("swap_usage", swapUsage);
                m.put("swap_free", swapFree);
                
                history.add(0,m);
            }
        }
        map.put("history", history);
        map.put("page_in", 0);
        map.put("page_out", 0);
        map.put("cache_read", 0);
        map.put("cache_write", 0);
        
        
        return map;
    }

    public Object getHostSwapInfo(String moId) {
        int dataPoints=5;
        Map map=new HashMap();

        List totalHistory=new ArrayList();
        
        SimpleDateFormat df=new SimpleDateFormat("HH:mm");
        Date now=new Date();
        
        List rows=new ArrayList();
        
        map.put("rows", rows);
        List history=new ArrayList();
        List<KpiEvent> eventList=kpiDataStore.getKpiHistory(moId,"wk_swap_1005","swap_usage","/",dataPoints);
        if(eventList!=null && eventList.size()>=dataPoints){
            for(int j=eventList.size()-1;j>=0;j--){
                KpiEvent event=eventList.get(j);
    //            c1.add(Calendar.SECOND, j);
    //            Date time=c1.getTime();
                String stime=df.format(event.getKpiTime());
                int usage=(int) (100-event.getNumValue());
                Map m=new HashMap();
                m.put("time", stime);
                m.put("usage", usage);
    

                int swapUsage=(int)event.getNumValue();
                int swapFree=100-swapUsage;
                m.put("swap_usage", swapUsage);
                m.put("swap_free", swapFree);
                history.add(m);
            }
        }
        else{
            Calendar c1 = Calendar.getInstance();
            c1.setTime(now);
            for(int j=0;j<dataPoints;j++){
                c1.add(Calendar.MINUTE, -1);
                Date time=c1.getTime();
                String stime=df.format(c1.getTime());
                int usage=0;
                Map m=new HashMap();
                m.put("time", stime);
                int swapUsage=0;
                int swapFree=0;
                m.put("swap_usage", swapUsage);
                m.put("swap_free", swapFree);
                
                history.add(0, m);
            }
        }
        map.put("history", history);
        
        
        return map;
    }
    
    public Object getHostBasicInfo(String moId) {
        
        Map map=new HashMap();
        
        //get ci
        String sql=String.format("select max(os) as os, max(osVersion) as osVersion, max(businessSystem) as businessSystem, max(position) as position, max(usage) as usage, max(owner) as owner, max(cpuModel) as cpuModel from ( select case when name='os' then value else '' end as os, case when name='osVersion' then value else '' end as osVersion, case when name='businessSystem' then value else '' end as businessSystem, case when name='position' then value else '' end as position, case when name='usage' then value else '' end as usage, case when name='owner' then value else '' end as owner, case when name='cpuModel' then value else '' end as cpuModel from TD_AVMON_MO_INFO_ATTRIBUTE where mo_id='%s' ) a", moId);
        List list=jdbcTemplate.queryForList(sql);
        if(list.size()>0){
            Map m=(Map) list.get(0);
            map.putAll(m);
        }
        //get run-rate,run-time,user-count,alarm-count
        map.put("RUNRATE", getKpiValue(moId,"wk_cfg_1035","cpu_speed","/") );
        map.put("RUNTIME", getKpiValue(moId,"wk_cfg_1035","live_time","/") );
        map.put("USERCOUNT", 0);
        map.put("ALARMCOUNT", getAlarmCount(moId));

        return map;
    }
    
    private int getAlarmCount(String moId) {
        String sql="select count(1) from TF_AVMON_ALARM_DATA where mo_id='"+moId+"'";
        return jdbcTemplate.queryForInt(sql);
    }

    private Object getKpiValue(String moId, String ampInstanceId, String kpiId, String instance) {
        KpiEvent event=kpiDataStore.getCurrentKpiEvent(moId,ampInstanceId,kpiId,instance);
        if(event!=null){
            return event.getStrValue();
        }
        else{
            return "";
        }
    }

    public Object getHostNewAlarm(String moId){

        String sql=String.format("select current_grade,to_char(first_occur_time,'HH24:MI MM-DD') as first_occur_time,title from TF_AVMON_ALARM_DATA where mo_id='%s' AND ROWNUM<10 ORDER BY FIRST_OCCUR_TIME DESC",moId);
        if(DBUtils.isPostgreSql()) {
        	sql=String.format("select current_grade,to_char(first_occur_time,'HH24:MI MM-DD') as first_occur_time,title from TF_AVMON_ALARM_DATA where mo_id='%s' ORDER BY FIRST_OCCUR_TIME DESC limit 9",moId);
        }
        List list=jdbcTemplate.queryForList(sql);
        return list;
    }

    public Object getHostKeyProcess(String moId) {
        String sql=String.format("select a.processName as NAME,b.cpu as CPU,c.mem,d.status from ( select value as processName,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='%s' and AMP_INST_ID='wk_pid_1104' and KPI_CODE='pro_name') a left join (select value as cpu,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='%s' and AMP_INST_ID='wk_pid_1104' and KPI_CODE='pro_cpu') b on a.instance=b.instance left join (select value||' Bytes' as mem,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='%s' and AMP_INST_ID='wk_pid_1104' and KPI_CODE='pro_mem') c on a.instance=c.instance left join (select value as status,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='%s' and AMP_INST_ID='wk_pid_1104' and KPI_CODE='pro_state') d on a.instance=d.instance where a.processName is not null order by b.cpu desc ",
                moId,moId,moId,moId);
        List list=jdbcTemplate.queryForList(sql);
        return list;
    }

    public Object getHostDiskIo(String moId) {

        String sql=String.format("select * from  (select value as BUSY,instance as DEVICE,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='%s' and KPI_CODE='disk_busy') a left join (select value as avque,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='%s' and KPI_CODE='disk_tran') b on a.instance=b.instance left join (select value as rwps,instance from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='%s' and KPI_CODE='disk_rw') c on a.instance=c.instance  ",
                moId,moId,moId);
        List list=jdbcTemplate.queryForList(sql);
        return list;
    }

    public Object getHostLvs(String moId) {
        String sql=String.format(SqlManager.getSql(this, "host_kpi_list_lv"),
                moId,moId,moId);
        List list=jdbcTemplate.queryForList(sql);
        return list;
    }

    public Object getHostNetworks(String moId) {
        String sql=String.format(SqlManager.getSql(this, "host_kpi_list_network"),
                moId,moId,moId);
        List list=jdbcTemplate.queryForList(sql);
        return list;
    }
    

}
