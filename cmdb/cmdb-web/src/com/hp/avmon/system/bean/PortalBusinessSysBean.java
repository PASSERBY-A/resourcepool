package com.hp.avmon.system.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName = "TF_AVMON_BIZ_DICTIONARY")
public class PortalBusinessSysBean {
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.STRING, pk = true)
	private String id;

	@FieldAnnotation(fieldName = "businessname", fieldType = FieldType.STRING, pk = false)
	private String businessname;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessname() {
		return businessname;
	}

	public void setBusinessname(String businessname) {
		this.businessname = businessname;
	}

}