package com.hp.avmon.system.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName = "TD_AVMON_MO_TYPE_ATTRIBUTE")
public class PortalMoPropertiesBean {
	@FieldAnnotation(fieldName = "type_id", fieldType = FieldType.STRING, pk = true)
	private String typeId;
	@FieldAnnotation(fieldName = "name", fieldType = FieldType.STRING, pk = true)
	private String name;
	@FieldAnnotation(fieldName = "caption", fieldType = FieldType.STRING, pk = false)
	private String caption;
	@FieldAnnotation(fieldName = "class_info", fieldType = FieldType.STRING, pk = false)
	private String classInfo;
	@FieldAnnotation(fieldName = "hide", fieldType = FieldType.NUMBER, pk = false)
	private String hide;
	@FieldAnnotation(fieldName = "passwd", fieldType = FieldType.NUMBER, pk = false)
	private String passwd;
	@FieldAnnotation(fieldName = "value_type", fieldType = FieldType.NUMBER, pk = false)
	private String valueType;
	@FieldAnnotation(fieldName = "order_index", fieldType = FieldType.STRING, pk = false)
	private String orderIndex;
	@FieldAnnotation(fieldName = "default_value", fieldType = FieldType.STRING, pk = false)
	private String defaultValue;
	@FieldAnnotation(fieldName = "nullable", fieldType = FieldType.STRING, pk = false)
	private String nullable;
	@FieldAnnotation(fieldName = "kpi_code", fieldType = FieldType.STRING, pk = false)
	private String kpiCode;
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getClassInfo() {
		return classInfo;
	}
	public void setClassInfo(String classInfo) {
		this.classInfo = classInfo;
	}
	public String getHide() {
		return hide;
	}
	public void setHide(String hide) {
		this.hide = hide;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(String orderIndex) {
		this.orderIndex = orderIndex;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	public String getKpiCode() {
		return kpiCode;
	}
	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

}