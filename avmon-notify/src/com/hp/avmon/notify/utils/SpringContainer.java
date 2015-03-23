package com.hp.avmon.notify.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContainer {
	
	private static ApplicationContext applicationContext;
	private static final  String springXmlFile="classpath:avmon-notify-beans.xml";
	
//	private static SpringContainer container=new SpringContainer();
	
	static{
		applicationContext=new ClassPathXmlApplicationContext(springXmlFile);
	}
	
	private SpringContainer(){}
	
//	public static SpringContainer getSpringContainer(){
//		if(container==null){
//			container=new SpringContainer();
//		}
//		return container;
//	}
	
    public static Object getBean(String beanName){	
		
		return  applicationContext.getBean(beanName);
	}
}
