package com.hp.avmon.config.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName="TD_AVMON_KPI_THRESHOLD")
public class TdAvmonKpiThresholdBean {
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.NUMBER, pk = true)
	private Long id;
	
	@FieldAnnotation(fieldName = "accumulate_count", fieldType = FieldType.NUMBER, pk = false)
	private Long accumulate_count;
	
	@FieldAnnotation(fieldName = "alarm_level", fieldType = FieldType.NUMBER, pk = false)
	private Long alarm_level;
	
	@FieldAnnotation(fieldName = "check_optr", fieldType = FieldType.NUMBER, pk = false)
	private Long check_optr;
	
	@FieldAnnotation(fieldName = "content", fieldType = FieldType.STRING, pk = false)
	private String content;
	
	@FieldAnnotation(fieldName = "kpi", fieldType = FieldType.STRING, pk = false)
	private String kpi;
	
	@FieldAnnotation(fieldName = "mo", fieldType = FieldType.STRING, pk = false)
	private String mo;
	
	@FieldAnnotation(fieldName = "amp_instance", fieldType = FieldType.STRING, pk = false)
	private String monitor_instance;
	
	@FieldAnnotation(fieldName = "threshold", fieldType = FieldType.STRING, pk = false)
	private String threshold;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccumulate_count() {
		return accumulate_count;
	}

	public void setAccumulate_count(Long accumulate_count) {
		this.accumulate_count = accumulate_count;
	}

	public Long getAlarm_level() {
		return alarm_level;
	}

	public void setAlarm_level(Long alarm_level) {
		this.alarm_level = alarm_level;
	}

	public Long getCheck_optr() {
		return check_optr;
	}

	public void setCheck_optr(Long check_optr) {
		this.check_optr = check_optr;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKpi() {
		return kpi;
	}

	public void setKpi(String kpi) {
		this.kpi = kpi;
	}

	public String getMo() {
		return mo;
	}

	public void setMo(String mo) {
		this.mo = mo;
	}

	public String getMonitor_instance() {
		return monitor_instance;
	}

	public void setMonitor_instance(String monitor_instance) {
		this.monitor_instance = monitor_instance;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	
}