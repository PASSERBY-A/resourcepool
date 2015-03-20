package com.hp.xo.uip.cmdb.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.driver.OracleDriver;

import com.hp.xo.uip.cmdb.probe.db.domain.DBConfig;

public class DBUtil {
	public DBConfig dbConfig;

	public DBConfig getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(DBConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

	public Connection getConnection() throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		Driver driver =null;	
  	    driver = (Driver) Class.forName(dbConfig.getDriver())
				.newInstance();
		
		Properties p = new Properties();
		p.setProperty("user", dbConfig.getUser());
		p.setProperty("password", dbConfig.getPassword());
		Connection conn = driver.connect(dbConfig.getUrl(), p);
		return conn;
	}

	public static void main(String arg[]) {
		DBUtil du=new DBUtil();
		DBConfig conf=new DBConfig();
		conf.setDriver("oracle.jdbc.driver.OracleDriver");
		conf.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
		conf.setUser("avmon2");
		conf.setPassword("avmon2");
		du.setDbConfig(conf);
		Connection conn=null;
		try {
			conn=du.getConnection();
			System.out.println(conn.getMetaData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
