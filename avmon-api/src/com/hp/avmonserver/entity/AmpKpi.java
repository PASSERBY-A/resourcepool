/**
 * 
 */
package com.hp.avmonserver.entity;

/**
 * @author jiyon
 *
 */
public class AmpKpi {
    private String kpiCode;
    private String schedule;
    private String kpiGroup;
    public String getKpiCode() {
        return kpiCode;
    }
    public void setKpiCode(String kpiCode) {
        this.kpiCode = kpiCode;
    }
    public String getSchedule() {
        return schedule;
    }
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    public String getKpiGroup() {
        return kpiGroup;
    }
    public void setKpiGroup(String kpiGroup) {
        this.kpiGroup = kpiGroup;
    }
    
}
