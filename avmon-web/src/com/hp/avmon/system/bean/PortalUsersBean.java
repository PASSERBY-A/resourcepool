package com.hp.avmon.system.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName="PORTAL_USERS")
public class PortalUsersBean {
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.NUMBER, pk = true)
	private Long id;
	
//	@FieldAnnotation(fieldName = "USER_ID", fieldType = FieldType.STRING, pk = false)
//	private String account;
	
	@FieldAnnotation(fieldName = "dept_id", fieldType = FieldType.NUMBER, pk = false)
	private Long dept_id;
	
	@FieldAnnotation(fieldName = "email", fieldType = FieldType.STRING, pk = false)
	private String email;
	
	@FieldAnnotation(fieldName = "mobile", fieldType = FieldType.STRING, pk = false)
	private String mobile;
	
	@FieldAnnotation(fieldName = "office_phone", fieldType = FieldType.STRING, pk = false)
	private String office_phone;
	
	@FieldAnnotation(fieldName = "password", fieldType = FieldType.STRING, pk = false)
	private String password;
	
	@FieldAnnotation(fieldName = "real_name", fieldType = FieldType.STRING, pk = false)
	private String real_name;
	
	@FieldAnnotation(fieldName = "remark", fieldType = FieldType.STRING, pk = false)
	private String remark;
	
	@FieldAnnotation(fieldName = "user_id", fieldType = FieldType.STRING, pk = false)
	private String user_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public String getAccount() {
//		return account;
//	}
//
//	public void setAccount(String account) {
//		this.account = account;
//	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOffice_phone() {
		return office_phone;
	}

	public void setOffice_phone(String office_phone) {
		this.office_phone = office_phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
}