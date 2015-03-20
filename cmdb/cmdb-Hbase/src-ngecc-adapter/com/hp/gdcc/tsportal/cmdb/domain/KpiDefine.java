package com.hp.gdcc.tsportal.cmdb.domain;

/**
 * KpiDefine定义
 * @author chengczh
 *
 */
public class KpiDefine extends SimpleNode implements Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int QUA_NA = -2;
	public static final int QUA_NORMAL = -1;
	public static final int QUA_INFO = 0;
	public static final int QUA_WARNING = 1;
	public static final int QUA_MINOR = 2;
	public static final int QUA_MAJOR = 3;
	public static final int QUA_CRITICAL  = 4;
	
	public static String getQualityName(int code) {
		switch(code) {
			case QUA_NORMAL: return "normal";
			case QUA_INFO: return "info";
			case QUA_WARNING: return "warning";
			case QUA_MINOR: return "minor";
			case QUA_MAJOR: return "major";
			case QUA_CRITICAL: return "critical";
			default: return "na";
		}
	}
	
	private ValueRange valueRange;

	public KpiDefine() {
		
	}
	
	public KpiDefine(SimpleNode sNode) {
		update(sNode);
	}
	
	public void update(SimpleNode sNode) {
		super.update(sNode);
	}
	
	public long getTypeId() {
		return getProperty("typeId", 0L);
	}
	public void setTypeId(long typeId) {
		setProperty("typeId", typeId);
	}
	public String getTypeName() {
		return getProperty("nodeType", (String)null);
	}
	public void setTypeName(String typeName) {
		setProperty("nodeType", typeName);
	}
	public long getKpiCode() {
		return getProperty("kpiCode", 0L);
	}
	public void setKpiCode(long kpiCode) {
		setProperty("kpiCode", kpiCode);
	}
	public String getDescription() {
		return getProperty("description", (String)null);
	}
	public void setDescription(String description) {
		setProperty("description", description);
	}
	public long getGranularity() {
		return getProperty("granularity", 0L);
	}
	public void setGranularity(long granularity) {
		setProperty("granularity", granularity);
	}
	public String getExpression() {
		return getProperty("expression", (String)null);
	}
	public void setExpression(String expression) {
		setProperty("expression", expression);
	}
	public String getUnit() {
		return getProperty("unit", (String)null);
	}
	public void setUnit(String unit) {
		setProperty("unit", unit);
	}
	public boolean isNumber() {
		return getProperty("isNumber", false);
	}
	public void setIsNumber(boolean isNumber) {
		setProperty("isNumber", isNumber);
	}
	public boolean isKpi() {
		return getProperty("isKpi", true);
	}
	public void setIsKpi(boolean isKpi) {
		setProperty("isKpi", isKpi);
	}
	public String getGranularityUnit() {
		return getProperty("granularityUnit", (String)null);
	}
	public void setGranularityUnit(String granularityUnit) {
		setProperty("granularityUnit", granularityUnit);
	}
	public long getTimeDeviation() {
		return getProperty("timeDeviation", 0L);
	}
	public void setTimeDeviation(long timeDeviation) {
		setProperty("timeDeviation", timeDeviation);
	}
	public String getTriggerTime() {
		return getProperty("triggerTime", (String)null);
	}
	public void setTriggerTime(String triggerTime) {
		setProperty("triggerTime", triggerTime);
	}
	/**
	 * KPI入库时间间隔
	 * 大于0时表示kpi入库的最小间隔时间（毫秒），
	 * 等于0时表示每次都入库，
	 * 等于-1时表示不入库
	 * @return
	 */
	public long getSaveInterval() {
		return getProperty("saveInterval", 0L);
	}
	public void setSaveInterval(long saveInterval) {
		setProperty("saveInterval", saveInterval);
	}
	/**
	 * KPI库中保留时长
	 * 大于0是表示在数据库中保留kpi的最小时间长度（毫秒），/cmsz 项目中时间单位为小时
	 * 等于0表示不保存，
	 * 等于-1表示永久保存
	 * @return
	 */
	public long getKeepPeriod() {
		return getProperty("keepPeriod", 0L);
	}
	public void setKeepPeriod(long keepPeriod) {
		setProperty("keepPeriod", keepPeriod);
	}
	
	public ValueRange valueRange() {
		if(null == valueRange)
			valueRange = new ValueRange(getRange(), isNumber());
		return valueRange;
	}
	
	public String getRange() {
		return getProperty("range", null);
	}
	
	public void setRange(String valueRange) {
		setProperty("range", valueRange);
		valueRange = null;
	}
	@Override
	public String toString() {
		return "<kpiCode:" + getKpiCode() + ">" + 
			"<kpiName:" + getName() + ">" +
			"<ref nodeid:" + getNodeId() + ">";
	}
	
	public KpiDefine clone() {
		return new KpiDefine(this);
	}
}