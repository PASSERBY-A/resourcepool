package com.hp.avmon.config.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName="tf_avmon_route_inspection_dev")
public class TfAvmonRouteInspectionDevBean {
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.NUMBER, pk = true)
	private Long id;
	
	@FieldAnnotation(fieldName = "device_name", fieldType = FieldType.STRING, pk = false)
	private String device_name;
	
	@FieldAnnotation(fieldName = "device_type", fieldType = FieldType.STRING, pk = false)
	private String device_type;
	
	@FieldAnnotation(fieldName = "device_ip", fieldType = FieldType.STRING, pk = false)
	private String device_ip;
	
	@FieldAnnotation(fieldName = "device_desc", fieldType = FieldType.STRING, pk = false)
	private String device_desc;
	
	@FieldAnnotation(fieldName = "device_status", fieldType = FieldType.STRING, pk = false)
	private String device_status;
	
	@FieldAnnotation(fieldName = "device_othrer1", fieldType = FieldType.STRING, pk = false)
	private String device_othrer1;
	
	@FieldAnnotation(fieldName = "device_othrer2", fieldType = FieldType.STRING, pk = false)
	private String device_othrer2;
	
	@FieldAnnotation(fieldName = "report_date", fieldType = FieldType.STRING, pk = false)
	private String report_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public String getDevice_ip() {
		return device_ip;
	}

	public void setDevice_ip(String device_ip) {
		this.device_ip = device_ip;
	}

	public String getDevice_desc() {
		return device_desc;
	}

	public void setDevice_desc(String device_desc) {
		this.device_desc = device_desc;
	}

	public String getDevice_status() {
		return device_status;
	}

	public void setDevice_status(String device_status) {
		this.device_status = device_status;
	}

	public String getDevice_othrer1() {
		return device_othrer1;
	}

	public void setDevice_othrer1(String device_othrer1) {
		this.device_othrer1 = device_othrer1;
	}

	public String getDevice_othrer2() {
		return device_othrer2;
	}

	public void setDevice_othrer2(String device_othrer2) {
		this.device_othrer2 = device_othrer2;
	}

	public String getReport_date() {
		return report_date;
	}

	public void setReport_date(String report_date) {
		this.report_date = report_date;
	}
	
}