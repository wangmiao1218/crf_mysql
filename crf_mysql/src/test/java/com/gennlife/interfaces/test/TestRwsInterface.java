package com.gennlife.interfaces.test;


import org.json.JSONObject;
import org.junit.Test;

import com.gennlife.interfaces.RwsInterface;


public class TestRwsInterface {
	
	final static String getLoginURL = "http://10.0.2.162/uranus/UI/user/Login?param={user:3333,pwd:123456}";
	final static String getLoginURL1 = "http://10.0.2.162/uranus/UI/user/Login?param=%7B%22user%22:%223333%22,%22pwd%22:%22123456%22%7D&timeStamp=1534478147831";
	final static String rwsURL = "http://10.0.2.162/uranus/UI/rws/SaveOrSearchActive";
	
	
	
	@Test
	public void doGet() throws Exception{
		//RwsInterface.doGet(getLoginURL);
		String doGet = RwsInterface.doGet(getLoginURL1);
		//System.out.println(doGet);
		
		//获取session
		JSONObject jsonObject = new JSONObject(doGet);
		String sessionId = ((JSONObject) jsonObject.get("data")).getString("uid");
		
		System.out.println(sessionId);
		
		//String string = RwsInterface.test001(sessionId);
		String string = RwsInterface.rws(rwsURL,sessionId);
		System.out.println(string);
		
		
	}
	
	
}