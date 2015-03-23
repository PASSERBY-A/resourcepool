package com.hp.avmon.common.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



public class SystemInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //DataCenter dc=new DataCenter();
        //dc.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}