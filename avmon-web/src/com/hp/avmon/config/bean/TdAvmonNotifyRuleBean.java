package com.hp.avmon.config.bean;

import com.hp.avmon.common.sqlgenerator.FieldAnnotation;
import com.hp.avmon.common.sqlgenerator.FieldType;
import com.hp.avmon.common.sqlgenerator.TableAnnotation;

@TableAnnotation(tableName="td_avmon_notify_rule")
public class TdAvmonNotifyRuleBean {
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.NUMBER, pk = true)
	private Long id;
	
	@FieldAnnotation(fieldName = "user_id", fieldType = FieldType.STRING, pk = false)
	private String user_id;
	
	@FieldAnnotation(fieldName = "view_id", fieldType = FieldType.STRING, pk = false)
	private String view_id;
	
	@FieldAnnotation(fieldName = "alarm_level1", fieldType = FieldType.NUMBER, pk = false)
	private Long alarm_level1;
	
	@FieldAnnotation(fieldName = "alarm_level2", fieldType = FieldType.NUMBER, pk = false)
	private Long alarm_level2;
	
	@FieldAnnotation(fieldName = "alarm_level3", fieldType = FieldType.NUMBER, pk = false)
	private Long alarm_level3;
	
	@FieldAnnotation(fieldName = "alarm_level4", fieldType = FieldType.NUMBER, pk = false)
	private Long alarm_level4;
	
	@FieldAnnotation(fieldName = "alarm_level5", fieldType = FieldType.NUMBER, pk = false)
	private Long alarm_level5;
	
	@FieldAnnotation(fieldName = "email_flag", fieldType = FieldType.NUMBER, pk = false)
	private Long email_flag;
	
	@FieldAnnotation(fieldName = "sms_flag", fieldType = FieldType.NUMBER, pk = false)
	private Long sms_flag;
	
	@FieldAnnotation(fieldName = "max_sms_per_day", fieldType = FieldType.NUMBER, pk = false)
	private Long max_sms_per_day;
	
	@FieldAnnotation(fieldName = "enable_flag", fieldType = FieldType.NUMBER, pk = false)
	private Long enable_flag;
	
	@FieldAnnotation(fieldName = "sms_recv_time", fieldType = FieldType.NUMBER, pk = false)
	private Long sms_recv_time;
	
	@FieldAnnotation(fieldName = "last_update_time", fieldType = FieldType.SYSDATE, pk = false)
	private String last_update_time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getView_id() {
		return view_id;
	}

	public void setView_id(String view_id) {
		this.view_id = view_id;
	}

	public Long getAlarm_level1() {
		return alarm_level1;
	}

	public void setAlarm_level1(Long alarm_level1) {
		this.alarm_level1 = alarm_level1;
	}

	public Long getAlarm_level2() {
		return alarm_level2;
	}

	public void setAlarm_level2(Long alarm_level2) {
		this.alarm_level2 = alarm_level2;
	}

	public Long getAlarm_level3() {
		return alarm_level3;
	}

	public void setAlarm_level3(Long alarm_level3) {
		this.alarm_level3 = alarm_level3;
	}

	public Long getAlarm_level4() {
		return alarm_level4;
	}

	public void setAlarm_level4(Long alarm_level4) {
		this.alarm_level4 = alarm_level4;
	}

	public Long getAlarm_level5() {
		return alarm_level5;
	}

	public void setAlarm_level5(Long alarm_level5) {
		this.alarm_level5 = alarm_level5;
	}

	public Long getEmail_flag() {
		return email_flag;
	}

	public void setEmail_flag(Long email_flag) {
		this.email_flag = email_flag;
	}

	public Long getSms_flag() {
		return sms_flag;
	}

	public void setSms_flag(Long sms_flag) {
		this.sms_flag = sms_flag;
	}

	public Long getMax_sms_per_day() {
		return max_sms_per_day;
	}

	public void setMax_sms_per_day(Long max_sms_per_day) {
		this.max_sms_per_day = max_sms_per_day;
	}

	public Long getEnable_flag() {
		return enable_flag;
	}

	public void setEnable_flag(Long enable_flag) {
		this.enable_flag = enable_flag;
	}

	public Long getSms_recv_time() {
		return sms_recv_time;
	}

	public void setSms_recv_time(Long sms_recv_time) {
		this.sms_recv_time = sms_recv_time;
	}

	public String getLast_update_time() {
		return last_update_time;
	}

	public void setLast_update_time(String last_update_time) {
		this.last_update_time = last_update_time;
	}
	
}