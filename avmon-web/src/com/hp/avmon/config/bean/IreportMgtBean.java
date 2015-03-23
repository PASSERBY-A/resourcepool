package com.hp.avmon.config.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName="IREPORT_MGT")
public class IreportMgtBean {
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.NUMBER, pk = true)
	private Long id;
	
	@FieldAnnotation(fieldName = "report_id", fieldType = FieldType.STRING, pk = false)
	private String report_id;
	
	@FieldAnnotation(fieldName = "report_name", fieldType = FieldType.STRING, pk = false)
	private String report_name;
	
	@FieldAnnotation(fieldName = "datasource_id", fieldType = FieldType.STRING, pk = false)
	private String datasource_id;
	
	@FieldAnnotation(fieldName = "template", fieldType = FieldType.STRING, pk = false)
	private String template;
	
	@FieldAnnotation(fieldName = "menu", fieldType = FieldType.STRING, pk = false)
	private String menu;
	
	@FieldAnnotation(fieldName = "updated_dt", fieldType = FieldType.SYSDATE, pk = false)
	private String updated_dt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReport_id() {
		return report_id;
	}

	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}

	public String getReport_name() {
		return report_name;
	}

	public void setReport_name(String report_name) {
		this.report_name = report_name;
	}

	public String getDatasource_id() {
		return datasource_id;
	}

	public void setDatasource_id(String datasource_id) {
		this.datasource_id = datasource_id;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getUpdated_dt() {
		return updated_dt;
	}

	public void setUpdated_dt(String updated_dt) {
		this.updated_dt = updated_dt;
	}
	
	
}