package com.hp.avmon.config.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName="TD_AVMON_UPGRADE_RULE")
public class TdAvmonUpgradeRuleBean {
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.NUMBER, pk = true)
	private Long id;
	
	@FieldAnnotation(fieldName = "alarm_count", fieldType = FieldType.NUMBER, pk = false)
	private Long alarm_count;
	
	@FieldAnnotation(fieldName = "alarm_count_duration", fieldType = FieldType.NUMBER, pk = false)
	private Long alarm_count_duration;
	
	@FieldAnnotation(fieldName = "content", fieldType = FieldType.STRING, pk = false)
	private String content;
	
	@FieldAnnotation(fieldName = "grade", fieldType = FieldType.STRING, pk = false)
	private String grade;
	
	@FieldAnnotation(fieldName = "kpi", fieldType = FieldType.STRING, pk = false)
	private String kpi;
	
	@FieldAnnotation(fieldName = "mo", fieldType = FieldType.STRING, pk = false)
	private String mo;
	
	@FieldAnnotation(fieldName = "new_grade", fieldType = FieldType.STRING, pk = false)
	private String new_grade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAlarm_count() {
		return alarm_count;
	}

	public void setAlarm_count(Long alarm_count) {
		this.alarm_count = alarm_count;
	}

	public Long getAlarm_count_duration() {
		return alarm_count_duration;
	}

	public void setAlarm_count_duration(Long alarm_count_duration) {
		this.alarm_count_duration = alarm_count_duration;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
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

	public String getNew_grade() {
		return new_grade;
	}

	public void setNew_grade(String new_grade) {
		this.new_grade = new_grade;
	}
	
}