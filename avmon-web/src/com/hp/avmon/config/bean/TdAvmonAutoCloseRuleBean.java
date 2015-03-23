package com.hp.avmon.config.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName="TD_AVMON_AUTO_CLOSE_RULE")
public class TdAvmonAutoCloseRuleBean {
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.NUMBER, pk = true)
	private Long id;
	
	@FieldAnnotation(fieldName = "content", fieldType = FieldType.STRING, pk = false)
	private String content;
	
	@FieldAnnotation(fieldName = "grade", fieldType = FieldType.STRING, pk = false)
	private String grade;
	
	@FieldAnnotation(fieldName = "kpi", fieldType = FieldType.STRING, pk = false)
	private String kpi;
	
	@FieldAnnotation(fieldName = "mo", fieldType = FieldType.STRING, pk = false)
	private String mo;
	
	@FieldAnnotation(fieldName = "timeout", fieldType = FieldType.NUMBER, pk = false)
	private Long timeout;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}
	
}