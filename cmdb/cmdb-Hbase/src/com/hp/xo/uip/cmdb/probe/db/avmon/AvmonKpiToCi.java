package com.hp.xo.uip.cmdb.probe.db.avmon;

import java.util.List;

import com.hp.xo.uip.cmdb.exception.CmdbException;

public interface AvmonKpiToCi {
	public String syncAvmonCi(Boolean preView)  throws CmdbException;
	public String getKpiByClass(String className);
	public String getClassByKpi(String kpiCode);
	public List<String> getClassViewKpi(String className);
	public String getViewFuncKpi(String func);
}
