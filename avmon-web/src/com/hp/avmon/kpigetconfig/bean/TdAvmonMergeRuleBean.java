package com.hp.avmon.kpigetconfig.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName="TD_AVMON_MERGE_RULE")
public class TdAvmonMergeRuleBean {
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.NUMBER, pk = true)
	private Long id;
	
	@FieldAnnotation(fieldName = "content", fieldType = FieldType.STRING, pk = false)
	private String content;
	
	@FieldAnnotation(fieldName = "grade", fieldType = FieldType.STRING, pk = false)
	private String grade;
	
	@FieldAnnotation(fieldName = "kpi", fieldType = FieldType.STRING, pk = false)
	private String kpi;
	
	@FieldAnnotation(fieldName = "merge_time_window", fieldType = FieldType.NUMBER, pk = false)
	private Long merge_time_window;
	
	@FieldAnnotation(fieldName = "mo", fieldType = FieldType.STRING, pk = false)
	private String mo;

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

	public Long getMerge_time_window() {
		return merge_time_window;
	}

	public void setMerge_time_window(Long merge_time_window) {
		this.merge_time_window = merge_time_window;
	}

	public String getMo() {
		return mo;
	}

	public void setMo(String mo) {
		this.mo = mo;
	}
	
	
}