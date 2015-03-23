package com.hp.avmonserver.entity;

import java.io.Serializable;


public class AvmonServerConfig implements Serializable{
    
   
    private String serverRoles;
    public String getServerRoles() {
		return serverRoles;
	}
	public void setServerRoles(String serverRoles) {
		this.serverRoles = serverRoles;
	}

	private int serverPort;
    private String serverRmi;
    private String serverIp;
    private String serverId;
    
    public String getServerIp() {
        return serverIp;
    }
    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
    public String getServerId() {
        return serverId;
    }
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public int getServerPort() {
        return serverPort;
    }
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
    public String getServerRmi() {
        return serverRmi;
    }
    public void setServerRmi(String serverRmi) {
        this.serverRmi = serverRmi;
    }
    
    public String toString(){
        return String.format("AvmonServer(Role=%s,dataPort=%d,rmi=%s)",serverRoles,serverPort,serverRmi);
    }
    
}
