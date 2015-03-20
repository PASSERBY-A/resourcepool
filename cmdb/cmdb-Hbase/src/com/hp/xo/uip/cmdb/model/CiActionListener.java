package com.hp.xo.uip.cmdb.model;

import java.util.Collection;

public interface CiActionListener {
	void onAction(CiAction action);
	void onAction(Collection<CiAction> actions);
	void refresh();
}
