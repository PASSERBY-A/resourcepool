package com.hp.avmon.kpigetconfig.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName="IREPORT_DATASOURCE")
public class IreportDatasourceBean {
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.NUMBER, pk = true)
	private Long id;
	
	@FieldAnnotation(fieldName = "title", fieldType = FieldType.STRING, pk = false)
	private String title;
	
	@FieldAnnotation(fieldName = "driver", fieldType = FieldType.STRING, pk = false)
	private String driver;
	
	@FieldAnnotation(fieldName = "url", fieldType = FieldType.STRING, pk = false)
	private String url;
	
	@FieldAnnotation(fieldName = "user_name", fieldType = FieldType.STRING, pk = false)
	private String user;
	
	@FieldAnnotation(fieldName = "password", fieldType = FieldType.STRING, pk = false)
	private String password;
	
	@FieldAnnotation(fieldName = "updated_dt", fieldType = FieldType.SYSDATE, pk = false)
	private String updated_dt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUpdated_dt() {
		return updated_dt;
	}

	public void setUpdated_dt(String updated_dt) {
		this.updated_dt = updated_dt;
	}

}