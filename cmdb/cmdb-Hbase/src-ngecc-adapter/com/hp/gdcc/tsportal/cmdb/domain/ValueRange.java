package com.hp.gdcc.tsportal.cmdb.domain;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.hp.itis.core2.commdata.TypeCaster;
import com.hp.itis.core2.misc.StrUtil;

public class ValueRange {
	private boolean isNumber;
	private boolean isEnum;
	private String rangeDefine;
	private Double maxValue;
	private Double minValue;
	private Map<String, Object> enums = new LinkedHashMap<String, Object>();
	
	public ValueRange(String rangeDefine) {
		this(rangeDefine, false);
	}
	
	public ValueRange(String rangeDefine, boolean isNumber) {
		this.rangeDefine = rangeDefine;
		this.isNumber = isNumber;
		parse();
	}
	
	private void parse() {
		String rd = rangeDefine;
		if(rd.startsWith("[") && rd.endsWith("]")) {
			rd = rd.substring(1, rd.length()-1);
			isEnum = false;
			isNumber = true;
		}
		else if(rd.startsWith("{") && rd.endsWith("}")) {
			rd = rd.substring(1, rd.length()-1);
			isEnum = true;
		}
		else
			isEnum = true;
		if(isEnum) {
			Map<String, String> values = StrUtil.str2map(rd, ",", ":");
			for(Entry<String, String> pair : values.entrySet()) {
				Object v = pair.getKey();
				if(isNumber)
					v = (Double)TypeCaster.cast(v, Double.class);
				if(pair.getValue() == null)
					enums.put(pair.getKey(), v);
				else
					enums.put(pair.getValue(), v);
			}
		}
		else {
			String[] values = rd.split(",");
			minValue = (Double)TypeCaster.cast(values[0], Double.class);
			if(values.length>1)
				maxValue = (Double)TypeCaster.cast(values[1], Double.class);
			else
				maxValue = minValue;
		}
	}
	
	
	public boolean isEnum() {
		return isEnum;
	}
	
	public Double getMaxValue() {
		return maxValue;
	}
	
	public Double getMinValue() {
		return minValue;
	}
	
	public Object[] enumValues() {
		return enums.values().toArray();
	}
	
	public String[] enumNames() {
		return enums.keySet().toArray(new String[0]);
	}
	
	public Object getValue(String name) {
		return enums.get(name);
	}
	
	public String getName(Object value) {
		for(Entry<String, Object> pair : enums.entrySet()) {
			if(value.equals(pair.getValue()))
				return pair.getKey();
		}
		return null;
	}
	
	public String toString() {
		return rangeDefine;
	}
}
