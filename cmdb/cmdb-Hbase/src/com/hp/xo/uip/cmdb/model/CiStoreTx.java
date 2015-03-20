package com.hp.xo.uip.cmdb.model;

import java.util.Collection;

import com.hp.xo.uip.cmdb.domain.CiAttribute;
import com.hp.xo.uip.cmdb.domain.ConfigItem;


public interface CiStoreTx {
//	void addCiAction(CiAction action);
	
//	void addCiActions(Collection<CiAction> actions);
	
	void addCi(ConfigItem ci);
	
	void removeCi(long ciId);
	
	void addCiAttribute(long ciId, CiAttribute attribute);
	
	void removeCiAttribute(long ciId, CiAttribute attribute);
	
	void updateCiAttribute(long ciId, CiAttribute attribute, CiAttribute newAttribute);
	
	void commit();
	
	void cancel();
	
}
