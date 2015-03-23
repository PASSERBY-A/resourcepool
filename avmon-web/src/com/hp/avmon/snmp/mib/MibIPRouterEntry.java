package com.hp.avmon.snmp.mib;

public class MibIPRouterEntry extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ipRouteDest;
	private int ipRouteIfIndex;
	private int ipRouteMetric1;
	private int ipRouteMetric2;
	private int ipRouteMetric3;
	private int ipRouteMetric4;
	private String ipRouteNextHop;
	private int ipRouteType;
	private int ipRouteProto;
	private int ipRouteAge;
	private String ipRouteMask;
	private int ipRouteMetric5;
	private String ipRouteInfo;

	public String toString() {
		return "ipRouteDest=" + this.ipRouteDest + "|" + "ipRouteIfIndex="
				+ this.ipRouteIfIndex + "|" + "ipRouteMetric1="
				+ this.ipRouteMetric1 + "|" + "ipRouteMetric2="
				+ this.ipRouteMetric2 + "|" + "ipRouteMetric3="
				+ this.ipRouteMetric3 + "|" + "ipRouteMetric4="
				+ this.ipRouteMetric4 + "|" + "ipRouteNextHop="
				+ this.ipRouteNextHop + "|" + "ipRouteType=" + this.ipRouteType
				+ "|" + "ipRouteProto=" + this.ipRouteProto + "|"
				+ "ipRouteAge=" + this.ipRouteAge + "|" + "ipRouteMask="
				+ this.ipRouteMask + "|" + "ipRouteMetric5="
				+ this.ipRouteMetric5 + "|" + "ipRouteInfo=" + this.ipRouteInfo
				+ "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.4.21.1";
	}

	public String getIpRouteDest() {
		return this.ipRouteDest;
	}

	public void setIpRouteDest(String paramString) {
		this.ipRouteDest = paramString;
	}

	public int getIpRouteIfIndex() {
		return this.ipRouteIfIndex;
	}

	public void setIpRouteIfIndex(int paramInt) {
		this.ipRouteIfIndex = paramInt;
	}

	public int getIpRouteMetric1() {
		return this.ipRouteMetric1;
	}

	public void setIpRouteMetric1(int paramInt) {
		this.ipRouteMetric1 = paramInt;
	}

	public int getIpRouteMetric2() {
		return this.ipRouteMetric2;
	}

	public void setIpRouteMetric2(int paramInt) {
		this.ipRouteMetric2 = paramInt;
	}

	public int getIpRouteMetric3() {
		return this.ipRouteMetric3;
	}

	public void setIpRouteMetric3(int paramInt) {
		this.ipRouteMetric3 = paramInt;
	}

	public int getIpRouteMetric4() {
		return this.ipRouteMetric4;
	}

	public void setIpRouteMetric4(int paramInt) {
		this.ipRouteMetric4 = paramInt;
	}

	public String getIpRouteNextHop() {
		return this.ipRouteNextHop;
	}

	public void setIpRouteNextHop(String paramString) {
		this.ipRouteNextHop = paramString;
	}

	public int getIpRouteType() {
		return this.ipRouteType;
	}

	public void setIpRouteType(int paramInt) {
		this.ipRouteType = paramInt;
	}

	public int getIpRouteProto() {
		return this.ipRouteProto;
	}

	public void setIpRouteProto(int paramInt) {
		this.ipRouteProto = paramInt;
	}

	public int getIpRouteAge() {
		return this.ipRouteAge;
	}

	public void setIpRouteAge(int paramInt) {
		this.ipRouteAge = paramInt;
	}

	public String getIpRouteMask() {
		return this.ipRouteMask;
	}

	public void setIpRouteMask(String paramString) {
		this.ipRouteMask = paramString;
	}

	public int getIpRouteMetric5() {
		return this.ipRouteMetric5;
	}

	public void setIpRouteMetric5(int paramInt) {
		this.ipRouteMetric5 = paramInt;
	}

	public String getIpRouteInfo() {
		return this.ipRouteInfo;
	}

	public void setIpRouteInfo(String paramString) {
		this.ipRouteInfo = paramString;
	}
}
