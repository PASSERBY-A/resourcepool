package com.hp.gdcc.tsportal.cmdb.domain;

import java.util.Collection;

public interface CiActionListener {
	void onAction(CiAction action);
	void onAction(Collection<CiAction> actions);
	void refresh();
}
