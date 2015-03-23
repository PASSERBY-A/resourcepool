package com.hp.avmonserver.entity;

public class AmpType {
    private String id;
    private String caption;
    private String type;
    private String schedule;
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getSchedule() {
        return schedule;
    }
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
}
