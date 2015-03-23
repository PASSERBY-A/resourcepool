package com.hp.avmon.snmp.common;

public class Global {
    
    //private static int avmonServerRole = PropUtil.getInt("server","avmon.server.role");
    public static String getJdbcDriver() {
    	String jdbcDriver = PropUtil.getString("jdbc","jdbc.connection.driverclass");
        return jdbcDriver;
    }

}
