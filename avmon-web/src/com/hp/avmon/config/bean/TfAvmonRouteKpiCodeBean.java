package com.hp.avmon.config.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName="TF_AVMON_ROUTE_KPI_CODE")
public class TfAvmonRouteKpiCodeBean {
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.NUMBER, pk = true)
	private Long id;
	
	@FieldAnnotation(fieldName = "kpi_id", fieldType = FieldType.STRING, pk = false)
	private String kpi_id;
	
	@FieldAnnotation(fieldName = "kpi_name", fieldType = FieldType.STRING, pk = false)
	private String kpi_name;
	
	@FieldAnnotation(fieldName = "kpi_type", fieldType = FieldType.STRING, pk = false)
	private String kpi_type;
	
	@FieldAnnotation(fieldName = "other1", fieldType = FieldType.STRING, pk = false)
	private String other1;
	
	@FieldAnnotation(fieldName = "other2", fieldType = FieldType.NUMBER, pk = false)
	private Long other2;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKpi_id() {
		return kpi_id;
	}

	public void setKpi_id(String kpi_id) {
		this.kpi_id = kpi_id;
	}

	public String getKpi_name() {
		return kpi_name;
	}

	public void setKpi_name(String kpi_name) {
		this.kpi_name = kpi_name;
	}

	public String getKpi_type() {
		return kpi_type;
	}

	public void setKpi_type(String kpi_type) {
		this.kpi_type = kpi_type;
	}

	public String getOther1() {
		return other1;
	}

	public void setOther1(String other1) {
		this.other1 = other1;
	}

	public Long getOther2() {
		return other2;
	}

	public void setOther2(Long other2) {
		this.other2 = other2;
	}
	
}