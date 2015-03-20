package com.hp.avmon.flex.service;

import java.util.List;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.ViewDefine;
import com.hp.xo.uip.cmdb.exception.CmdbException;


@Service("testSerivice")
@RemotingDestination(channels = { "my-amf" })
public class TestSerivice implements ITestService{

	 @RemotingInclude
	public String sayHello(String msg){
		System.out.print("++++++++++++++"+msg+"+++++++++++++++++=");
		return "Message from BlazeDs Java Backend!" + msg;
	}
	 
	 @RemotingInclude
	 public String getUserName()
	 {
		 return "administrator";
	 }
	 
	 @RemotingInclude
	 public List<Node> getNodesByFlex()
	 {
		 CmdbClientTest test = new CmdbClientTest();
		 List<Node> result = null;
		 result = test.getNodesByFlex();
		 return result;
	 }
	 
	 @RemotingInclude
	 public List<ViewDefine> getViewByFlex()
	 {
		 CmdbClientTest test = new CmdbClientTest();
		 List<ViewDefine> result = test.getViewByFlex();
		 return result;
	 }
	 
	 
}
