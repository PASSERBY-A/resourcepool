package com.hp.gdcc.tsportal.cmdb.domain.impl;

import com.hp.gdcc.tsportal.cmdb.domain.CiAttribute;
import com.hp.gdcc.tsportal.cmdb.domain.ConfigItem;
import com.hp.gdcc.tsportal.cmdb.domain.KpiDefine;
import com.hp.gdcc.tsportal.cmdb.domain.KpiNode;
import com.hp.gdcc.tsportal.cmdb.model.NodeManager;
import com.hp.itis.core2.commdata.TypeCaster;

public class KpiNodeImpl extends NodeImpl implements KpiNode {
	private KpiDefine kpiDefine = new KpiDefine();
	
	public KpiNodeImpl(NodeManager manager, ConfigItem entity){
		super(manager, entity);
		initKpiDefine();
	}
	
	@Override
	public KpiDefine kpiDefine() {
		return kpiDefine;
	}
	
	/**
	 * 
	 */
	private void initKpiDefine() {
		kpiDefine.setNodeId(this.id());
		String name = null;
		//CI Name必需唯一，KPIName可以重复，所以KPIName以属性保存
		CiAttribute attr = this.getMyAttribute("name");
		if(attr != null && attr.value() != null)
			name = attr.value().toString();
		if(null == name)
			name = this.name();
		if(null == name)
			throw new RuntimeException("KPI name attribute is null");
		kpiDefine.setName(name);
		kpiDefine.setLabel(this.label());
		
		attr = this.getMyAttribute("kpiCode");
		if(attr == null || attr.value() == null)
			kpiDefine.setKpiCode(this.id());
		else
			kpiDefine.setKpiCode(TypeCaster.cast(Long.class, attr.value(), this.id()));
		
		attr = this.getMyAttribute("nodeType");
		if(attr == null || attr.value() == null)
			throw new RuntimeException("KPI nodeType attribute is null");
		kpiDefine.setTypeName(attr.value().toString());
		
		attr = this.getMyAttribute("description");
		if(attr != null && attr.value() != null)
			kpiDefine.setDescription(attr.value().toString());
		attr = this.getMyAttribute("granularity");
		if(!isNull(attr)) 
			kpiDefine.setGranularity(parseLong(attr.value()));
		attr = this.getMyAttribute("expression");
		if(attr != null && attr.value() != null)
			kpiDefine.setExpression(attr.value().toString());
		attr = this.getMyAttribute("unit");
		if(attr != null && attr.value() != null)
			kpiDefine.setUnit(attr.value().toString());
		attr = this.getMyAttribute("isNumber");
		if(attr != null && attr.value() != null) 
			kpiDefine.setIsNumber(TypeCaster.toBoolean(attr.value()));
		attr = this.getMyAttribute("isKpi");
		if(attr != null && attr.value() != null) 
			kpiDefine.setIsKpi(TypeCaster.toBoolean(attr.value()));
		attr = this.getMyAttribute("granularityUnit");
		if(attr != null && attr.value() != null) 
			kpiDefine.setGranularityUnit(attr.value().toString());
		attr = this.getMyAttribute("timeDeviation");
		if(!isNull(attr)) 
			kpiDefine.setTimeDeviation(parseLong(attr.value()));
		attr = this.getMyAttribute("triggerTime");
		if(attr != null && attr.value() != null)
			kpiDefine.setTriggerTime(attr.value().toString());
		//by qiaoj, saveInterval初始化，-1:不入库，0：立即入库，默认为-1
		attr = this.getMyAttribute("saveInterval");
		if(!isNull(attr))
			kpiDefine.setSaveInterval(parseLong(attr.value()));
		//by qiaoj, keepPeriod初始化
		attr = this.getMyAttribute("keepPeriod");
		if(!isNull(attr))
			kpiDefine.setKeepPeriod(parseLong(attr.value()));
		
	}
	
	private long parseLong(Object inParam) {
		long ret = 0l;
		if(inParam == null) return ret;
		try {
			ret = Long.parseLong(String.valueOf(inParam));
		} catch(Exception ex) {
			
		}
		return ret;
	}
	
	
	private boolean isNull(CiAttribute attr) {
		if(attr == null || attr.value() == null || attr.value().equals("null"))
			return true;
		else
			return false;
	}
}
