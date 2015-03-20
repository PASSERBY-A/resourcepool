package com.hp.avmon.system.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName="PORTAL_DEPTS")
public class PortalDeptsBean {
	
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.NUMBER, pk = true)
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@FieldAnnotation(fieldName = "dept_id", fieldType = FieldType.STRING, pk = false)
	private String dept_id;
	
	
	@FieldAnnotation(fieldName = "dept_name", fieldType = FieldType.STRING, pk = false)
	private String dept_name;
	
	
	@FieldAnnotation(fieldName = "parent_id", fieldType = FieldType.STRING, pk = false)
	private String parent_id;
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	
}