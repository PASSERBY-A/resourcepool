package com.hp.avmon.performance.store;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.hp.avmonserver.entity.KpiEvent;

@Service
public abstract class KpiDataStore {
	
    private static final Log logger = LogFactory.getLog(KpiDataStore.class);
    
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	

	public List<KpiEvent> getKpiHistory(String moId, String ampInstId, String kpiCode, String instance, int dataPoints) {
	    List<KpiEvent> list=new LinkedList();
	    return list;
    }

    
    public String getCurrentKpiValue(String moId,String kpiCode){
        List<KpiEvent> list=getKpiList(moId,"",kpiCode);
        if(list.size()>0){
            return list.get(0).getValue();
        }
        else{
            return "";
        }
    }
    //add by muzh
    public String getCurrentKpiStringValue(String moId,String kpiCode){
        List<KpiEvent> list=getKpiList(moId,"",kpiCode);
        if(list.size()>0){
            return list.get(0).getStrValue();
        }
        else{
            return "";
        }
    }
    
    public float getCurrentKpiNumValue(String moId,String kpiCode){
        String val=getCurrentKpiValue(moId,kpiCode);
        try{
            return Float.parseFloat(val);
        }catch(Exception e){
            return 0;
        }
    }

    /**
     * 获取指定kpi的当前值
     * @param moId
     * @param ampInstId
     * @param kpiCode
     * @param instance
     * @return
     */
	public abstract KpiEvent getCurrentKpiEvent(String moId, String ampInstId, String kpiCode, String instance);

	/**
	 * 获取kpi指标值列表
	 * @param moId
	 * @param ampInstId
	 * @param kpiCode
	 * @return
	 */
	public abstract List<KpiEvent> getKpiList(String moId, String ampInstId, String kpiCode);
    
	/**
	 * 获取kpi指标值列表
	 * @param moId
	 * @return
	 */
	public abstract List<KpiEvent> getKpiList(String moId);
    
	
	/**
	 * Change the object type to String type
	 * 
	 * @param str
	 * @return
	 */
    public static String nullToString(Object str) {
		if (str == null) {
			return "";
		}
		return String.valueOf(str);
	}
    
	/**
	 * Change the object type to Float type
	 * 
	 * @param str
	 * @return
	 */
    public static Float nullToFloat(Object str) {
		if (str == null) {
			return 0f;
		}
		return Float.valueOf(nullToString(str));
	}
	/**
	 * 字符串类型转换
	 * 
	 * @param str
	 * @return
	 */
    public static Short nullToShort(Object str) {
		if (str == null) {
			return 0;
		}
		return Short.valueOf(nullToString(str));
	}
    
	/**
	 * 字符串类型转换
	 * 
	 * @param str
	 * @return
	 */
    public static Integer nullToInteger(Object str) {
		if (str == null) {
			return 0;
		}
		return Integer.valueOf(nullToString(str));
	}
    
	/**
	 * Get the date according to specific String content
	 * The default format is "yyyy-MM-dd"
	 * @param String
	 * @return Date
	 */
	public static Date stringToDate(String datecontent, String format) {
		if (format == null || format.equals(""))
			format = "yyyy-MM-dd";

		if (!StringUtils.isEmpty(datecontent)) {
			try {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);
				Date date = bartDateFormat.parse(datecontent);
				return date;
			}
			catch (ParseException pe) {
				String message = "Exception occurs in Parse progress.";
				logger.debug(message);
			}
			catch (Exception e) {
				String message =
					"Exception occurs during the string converting to Date.";
				logger.debug(message);
			}
		}
		
		return null;
	}
}