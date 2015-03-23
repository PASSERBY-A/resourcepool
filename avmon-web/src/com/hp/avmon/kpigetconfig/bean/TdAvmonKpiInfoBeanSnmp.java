package com.hp.avmon.kpigetconfig.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName="td_avmon_snmpkpi_info")
public class TdAvmonKpiInfoBeanSnmp {
	@FieldAnnotation(fieldName = "kpi_code", fieldType = FieldType.STRING, pk = false)
	private String kpi_code;
	
	@FieldAnnotation(fieldName = "caption", fieldType = FieldType.STRING, pk = false)
	private String caption;
	
	@FieldAnnotation(fieldName = "aggmethod", fieldType = FieldType.STRING, pk = false)
	private String aggmethod;
	
	@FieldAnnotation(fieldName = "precision", fieldType = FieldType.STRING, pk = false)
	private String precision;
	
	@FieldAnnotation(fieldName = "unit", fieldType = FieldType.STRING, pk = false)
	private String unit;
	
	@FieldAnnotation(fieldName = "iscalc", fieldType = FieldType.NUMBER, pk = false)
	private Long iscalc;
	
	@FieldAnnotation(fieldName = "calcmethod", fieldType = FieldType.STRING, pk = false)
	private String calcmethod;
	
	@FieldAnnotation(fieldName = "isstore", fieldType = FieldType.NUMBER, pk = false)
	private Long isstore;
	
	@FieldAnnotation(fieldName = "storeperiod", fieldType = FieldType.NUMBER, pk = false)
	private Long storeperiod;
	
	@FieldAnnotation(fieldName = "datatype", fieldType = FieldType.NUMBER, pk = false)
	private Long datatype;
	
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.NUMBER, pk = false)
	private Long id;

	public String getKpi_code() {
		return kpi_code;
	}

	public void setKpi_code(String kpi_code) {
		this.kpi_code = kpi_code;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getAggmethod() {
		return aggmethod;
	}

	public void setAggmethod(String aggmethod) {
		this.aggmethod = aggmethod;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getIscalc() {
		return iscalc;
	}

	public void setIscalc(Long iscalc) {
		this.iscalc = iscalc;
	}

	public String getCalcmethod() {
		return calcmethod;
	}

	public void setCalcmethod(String calcmethod) {
		this.calcmethod = calcmethod;
	}

	public Long getIsstore() {
		return isstore;
	}

	public void setIsstore(Long isstore) {
		this.isstore = isstore;
	}

	public Long getStoreperiod() {
		return storeperiod;
	}

	public void setStoreperiod(Long storeperiod) {
		this.storeperiod = storeperiod;
	}

	public Long getDatatype() {
		return datatype;
	}

	public void setDatatype(Long datatype) {
		this.datatype = datatype;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@FieldAnnotation(fieldName = "kpi_name", fieldType = FieldType.STRING, pk = false)
	private String kpi_name;

	public String getKpi_name() {
		return kpi_name;
	}

	public void setKpi_name(String kpi_name) {
		this.kpi_name = kpi_name;
	}
	
	@FieldAnnotation(fieldName = "recent_trend", fieldType = FieldType.NUMBER, pk = false)
	private Long recent_trend;

	public Long getRecent_trend() {
		return recent_trend;
	}

	public void setRecent_trend(Long recent_trend) {
		this.recent_trend = recent_trend;
	}
	
}