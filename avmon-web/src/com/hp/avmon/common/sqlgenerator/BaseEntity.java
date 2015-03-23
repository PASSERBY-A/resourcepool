/**
 * 
 */
package com.hp.avmon.common.sqlgenerator;

import java.sql.Timestamp;

/**
 * @author muzh
 * 
 */
public class BaseEntity {
	@FieldAnnotation(fieldName = "id", fieldType = FieldType.NUMBER, pk = true)
	private Integer id;

	@FieldAnnotation(fieldName = "create_date", fieldType = FieldType.DATE, pk = false)
	private Timestamp createDate;

	@FieldAnnotation(fieldName = "modify_date", fieldType = FieldType.DATE, pk = false)
	private Timestamp modifyDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Timestamp modifyDate) {
		this.modifyDate = modifyDate;
	}

	public BaseEntity(Integer id, Timestamp createDate, Timestamp modifyDate) {

		super();
		this.id = id;
		this.createDate = createDate;
		this.modifyDate = modifyDate;

	}

	public BaseEntity() {
		super();
	}
}
