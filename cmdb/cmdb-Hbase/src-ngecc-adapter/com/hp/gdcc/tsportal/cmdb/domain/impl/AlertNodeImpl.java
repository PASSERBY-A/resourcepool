package com.hp.gdcc.tsportal.cmdb.domain.impl;

import com.hp.gdcc.tsportal.cmdb.domain.AlertDefine;
import com.hp.gdcc.tsportal.cmdb.domain.AlertNode;
import com.hp.gdcc.tsportal.cmdb.domain.CiAttribute;
import com.hp.gdcc.tsportal.cmdb.domain.ConfigItem;
import com.hp.gdcc.tsportal.cmdb.model.NodeManager;
import com.hp.itis.core2.commdata.TypeCaster;

public class AlertNodeImpl extends NodeImpl implements AlertNode {
	private AlertDefine alertDefine = new AlertDefine();
	
	public AlertNodeImpl(NodeManager manager, ConfigItem entity) {
		super(manager, entity);
		initAlertDefine();
	}
	
	/**
	 * TODO 把属性名定义到AlertNode接口中
	 */
	private void initAlertDefine() {
		alertDefine.setNodeId(this.id());
		String name = null;
		//CI Name必需唯一，AlertName可以重复，所以AlertName应该以属性保存
		CiAttribute attr = this.getMyAttribute("name");
		if(attr != null && attr.value() != null)
			name = attr.value().toString();
		if(null == name)
			name = this.name();
		if(null == name)
			throw new RuntimeException("Alert name attribute is null");
		alertDefine.setName(name);
		alertDefine.setLabel(label());
		
		attr = this.getMyAttribute("alertCode");
		if(attr == null || attr.value() == null) 
			alertDefine.setAlertCode(this.id());
		else
			alertDefine.setAlertCode(TypeCaster.cast(Long.class, attr.value(), this.id()));
		
		attr = this.getMyAttribute("nodeType");
		if(attr == null || attr.value() == null)
			throw new RuntimeException("Alert(alertCode=" + alertDefine.getAlertCode() + ", name=" + alertDefine.getName() + ") nodeType attribute is null");
		alertDefine.setTypeName(attr.value().toString());
		
		attr = this.getMyAttribute("description");
		if(attr != null && attr.value() != null)
			alertDefine.setDescription(attr.value().toString());
		attr = this.getMyAttribute("enable");
		if(attr != null && attr.value() != null) 
			alertDefine.setEnable(attr.value().toString().equals("1"));
	}
	
	@Override
	public AlertDefine alertDefine() {
		return alertDefine;
	}
	
}
