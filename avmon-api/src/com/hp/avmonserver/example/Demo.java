package com.hp.avmonserver.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.hp.avmonserver.api.AvmonServer;
import com.hp.avmonserver.entity.Alarm;
import com.hp.avmonserver.entity.KpiEvent;
import com.hp.avmonserver.entity.MO;
import com.hp.avmonserver.entity.MOStatus;


public class Demo {
    
    private static AvmonServer server;
    
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        server=new AvmonServer();
        server.init("rmi://16.159.216.181:9998/AvmonServer");

        System.out.println(server.checkRemoteService());
//        Map params=new HashMap();
//        Map map=server.execute("test",params);
//        System.out.println(map.get("result"));
//        testForKpiData();

    }
    
    private static void testStartAgent(){
    	String result = server.startAgent("127.0.0.1");
    	System.out.println("startAgent result : ----------->:"+result);
    	
    }
    
    private static void testStartAgent1(){
    	
//    	System.out.println("startAgent result : ----------->:"+result.length);//eclipse-jee-juno-SR1-win32.zip
    }

//    private static void testForKpiData(){
//        String moId="10.204.37.150";
//        String monitorInstanceId="wk_lv_1006";
//        String kpiId="lv_size";
//        Map filter=new HashMap();
//        filter.put("moId", moId);
//        String str=server.getKpiDataJson(filter);
//        System.out.println(str);
//        
//    }
    
//    private static void testForPerformanceDashboard(){
//        String moId="10.204.37.150";
//        SimpleDateFormat df=new SimpleDateFormat("HH:mm:ss");
//        List<KpiEvent> eventList=server.getKpiHistory(moId,"wk_cpu_1001","sys_idle","/",15);
//        if(eventList!=null && eventList.size()>0){
//            for(KpiEvent event:eventList){
//    //            c1.add(Calendar.SECOND, j);
//    //            Date time=c1.getTime();
//                String stime=df.format(event.getKpiTime());
//                int usage=(int) (100-event.getNumValue());
//                Map m=new HashMap();
//                m.put("time", stime);
//                m.put("usage", usage);
//
//    
//                System.out.format("%s=%s\n",stime,usage);
//            }
//        }
//        else{
//            System.out.println("empty");
//        }
//    }
//    private static void testForAlarmCount() {
//        // TODO Auto-generated method stub
//        //201209112222472247,20120911222303233
//        int n=server.getAlarmCount(new String[]{"201209112222472247","20120911222303233"}, new Integer[]{0,1,2,3,4},null);
//        
//        //System.out.format("total alarm count = %d\n",n);
//    }
//
//    private static void testForMoStatus(){
//        List<MOStatus> list=server.getMoStatus(null, "admin");
////        for(MOStatus m:list){
////            System.out.format("moId:%s alarmCount:%d level0:%d level1:%d level2:%d level3:%d level4:%d userAlarmCount:%d newAlarmCount:%d\n",
////                    m.getMoId(),m.getActiveAlarmCount(),m.getLevel0AlarmCount(),m.getLevel1AlarmCount(),
////                    m.getLevel2AlarmCount(),m.getLevel3AlarmCount(),m.getLevel4AlarmCount(),m.getUserAlarmCount(),
////                    m.getNewAlarmCount()
////                    );
////        }
//    }
}
