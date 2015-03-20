package com.hp.avmon.system.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName="PORTAL_MODULES")
public class PortalModulesBean {
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.NUMBER, pk = true)
	private Long id;
	
	@FieldAnnotation(fieldName = "display_flag", fieldType = FieldType.STRING, pk = false)
	private String display_flag;
	
	@FieldAnnotation(fieldName = "display_order", fieldType = FieldType.NUMBER, pk = false)
	private Long display_order;
	
	@FieldAnnotation(fieldName = "module_id", fieldType = FieldType.STRING, pk = false)
	private String module_id;
	
	@FieldAnnotation(fieldName = "module_name", fieldType = FieldType.STRING, pk = false)
	private String module_name;
	
	@FieldAnnotation(fieldName = "module_url", fieldType = FieldType.STRING, pk = false)
	private String module_url;
	
	@FieldAnnotation(fieldName = "parent_id", fieldType = FieldType.STRING, pk = false)
	private String parent_id;
	
	@FieldAnnotation(fieldName = "remark", fieldType = FieldType.STRING, pk = false)
	private String remark;
	
	@FieldAnnotation(fieldName = "icon_cls", fieldType = FieldType.STRING, pk = false)
	private String icon_cls;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDisplay_flag() {
		return display_flag;
	}

	public void setDisplay_flag(String display_flag) {
		this.display_flag = display_flag;
	}

	public Long getDisplay_order() {
		return display_order;
	}

	public void setDisplay_order(Long display_order) {
		this.display_order = display_order;
	}

	public String getModule_id() {
		return module_id;
	}

	public void setModule_id(String module_id) {
		this.module_id = module_id;
	}

	public String getModule_name() {
		return module_name;
	}

	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}

	public String getModule_url() {
		return module_url;
	}

	public void setModule_url(String module_url) {
		this.module_url = module_url;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIcon_cls() {
		return icon_cls;
	}

	public void setIcon_cls(String icon_cls) {
		this.icon_cls = icon_cls;
	}
	
	
}