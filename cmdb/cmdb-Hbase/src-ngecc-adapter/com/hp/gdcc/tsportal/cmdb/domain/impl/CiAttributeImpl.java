package com.hp.gdcc.tsportal.cmdb.domain.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.hp.gdcc.tsportal.cmdb.domain.CiAttribute;

public class CiAttributeImpl implements CiAttribute {
	private long _ciId;
	private String _name;
	private String _label;
	private String _dataType;
	private boolean _isReferenece;
	private long _refCiId;
	private String _refType = "";
	private Object _value;
	private boolean allowEdit = true;
	private String fromDict = "";
	private String reserved1 = "";
	private String reserved2 = "";
	private String reserved3 = "";
	private String reserved4 = "";
	private Collection<Object> _values = new ArrayList<Object>(); 
	
	public CiAttributeImpl(long ciId) {
		_ciId = ciId;
	}
	
	public CiAttributeImpl(long ciId, String name, Object value) {
		_ciId = ciId;
		_name = name;
		addValue(value);
	}
		
	public CiAttributeImpl(long ciId, String name, String dataType, 
			boolean isReference, Object value) {
		_ciId = ciId;
		_name = name;
		_dataType = dataType;
		_isReferenece = isReference;
		addValue(value);
	}
	
	public CiAttributeImpl(long ciId, String name, String dataType, 
			boolean isReference, Object value, long refCiId, String refType) {
		_ciId = ciId;
		_name = name;
		_dataType = dataType;
		_isReferenece = isReference;
		_refCiId = refCiId;
		_refType = refType;
		addValue(value);
	}
	
	public void addValue(Object value) {
		if(_value == null) {
			_value = value;
		}
		_values.add(value);
	}
	
	@Override
	public String dataType() {
		return _dataType;
	}

	@Override
	public boolean isReference() {
		return _isReferenece;
	}
	
	public void setIsReference(boolean isReference) {
		_isReferenece = isReference;
	}
	
	@Override
	public String label() {
		return _label;
	}
	
	public void setLabel(String label) {
		this._label = label;
	}
	
	@Override
	public String name() {
		return _name;
	}

	@Override
	public long refCiId() {
		return _refCiId;
	}

	@Override
	public String refType() {
		return _refType;
	}

	@Override
	public Object value() {
		return _value;
	}

	@Override
	public Collection<Object> values() {
		return _values;
	}

	@Override
	public long ciId() {
		return _ciId;
	}
	
	public void setCiId(long id) {
		_ciId = id;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public void setDataType(String dataType) {
		_dataType = dataType;
	}
	
	public void setRefType(String refType) {
		_refType = refType;
	}
	
	public void setRefCiId(long refCiId) {
		_refCiId = refCiId;
	}
	public String toString() {
		return (_name + " <Type:" + _dataType + "> " + 
				"<Value:" + _value +">");
	}

	@Override
	public boolean allowEdit() {
		return allowEdit;
	}

	@Override
	public String fromDict() {
		return fromDict;
	}

	@Override
	public String reserved1() {
		return reserved1;
	}

	@Override
	public String reserved2() {
		return reserved2;
	}

	@Override
	public String reserved3() {
		return reserved3;
	}

	@Override
	public String reserved4() {
		return reserved4;
	}

	public void setAllowEdit(boolean allowEdit) {
		this.allowEdit = allowEdit;
	}
	
	public void setAllowEdit(String allowEdit) {
		try {
			this.allowEdit = Boolean.parseBoolean(allowEdit);
		} catch(Exception ex) {
			
		}
	}
	
	public void setFromDict(String fromDict) {
		this.fromDict = fromDict;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}

	public void setReserved4(String reserved4) {
		this.reserved4 = reserved4;
	}
	
}
