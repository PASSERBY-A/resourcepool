package com.hp.avmon.config.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName="tf_avmon_route_inspection_kpi")
public class TfAvmonRouteInspectionKpiBean {
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.NUMBER, pk = true)
	private Long id;
	
	@FieldAnnotation(fieldName = "device_ip", fieldType = FieldType.STRING, pk = false)
	private String device_ip;
	
	@FieldAnnotation(fieldName = "kpi_id", fieldType = FieldType.STRING, pk = false)
	private String kpi_id;
	
	@FieldAnnotation(fieldName = "kpi_threshold", fieldType = FieldType.STRING, pk = false)
	private String kpi_threshold;
	
	@FieldAnnotation(fieldName = "kpi_type", fieldType = FieldType.STRING, pk = false)
	private String kpi_type;
	
	@FieldAnnotation(fieldName = "ignore_value", fieldType = FieldType.STRING, pk = false)
	private String ignore_value;
	
	@FieldAnnotation(fieldName = "error_value", fieldType = FieldType.STRING, pk = false)
	private String error_value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDevice_ip() {
		return device_ip;
	}

	public void setDevice_ip(String device_ip) {
		this.device_ip = device_ip;
	}

	public String getKpi_id() {
		return kpi_id;
	}

	public void setKpi_id(String kpi_id) {
		this.kpi_id = kpi_id;
	}

	public String getKpi_threshold() {
		return kpi_threshold;
	}

	public void setKpi_threshold(String kpi_threshold) {
		this.kpi_threshold = kpi_threshold;
	}

	public String getKpi_type() {
		return kpi_type;
	}

	public void setKpi_type(String kpi_type) {
		this.kpi_type = kpi_type;
	}

	public String getIgnore_value() {
		return ignore_value;
	}

	public void setIgnore_value(String ignore_value) {
		this.ignore_value = ignore_value;
	}

	public String getError_value() {
		return error_value;
	}

	public void setError_value(String error_value) {
		this.error_value = error_value;
	}
	
	
}