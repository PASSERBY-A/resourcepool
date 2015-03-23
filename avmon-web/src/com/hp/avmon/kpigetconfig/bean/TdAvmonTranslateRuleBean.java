package com.hp.avmon.kpigetconfig.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName="TD_AVMON_TRANSLATE_RULE")
public class TdAvmonTranslateRuleBean {
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.NUMBER, pk = true)
	private Long id;
	
	@FieldAnnotation(fieldName = "content", fieldType = FieldType.STRING, pk = false)
	private String content;
	
	@FieldAnnotation(fieldName = "mo", fieldType = FieldType.STRING, pk = false)
	private String mo;
	
	@FieldAnnotation(fieldName = "translated_content", fieldType = FieldType.STRING, pk = false)
	private String translated_content;

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

	public String getMo() {
		return mo;
	}

	public void setMo(String mo) {
		this.mo = mo;
	}

	public String getTranslated_content() {
		return translated_content;
	}

	public void setTranslated_content(String translated_content) {
		this.translated_content = translated_content;
	}
	
}