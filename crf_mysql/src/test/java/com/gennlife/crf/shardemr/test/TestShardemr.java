package com.gennlife.crf.shardemr.test;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONObject;
import org.junit.Test;

import com.gennlife.crf.shardemr.Shardemr;
import com.gennlife.interfaces.ShardemrAndOauthTokenInterface;

public class TestShardemr {
	
	final static String oauthURL = "http://10.0.2.184:8072/oauth/oauth/token";
	final static String shardemrURL = "http://10.0.2.184:8072/shardingdata-service/vitark/sharding/shardemr";
	
	@Test
	public void getAccessToken() throws Exception{
		Map<String,String> map = new HashedMap<String, String>();
		map.put("grant_type", "password");
		map.put("username", "testshard");
		map.put("password", "123456");
		map.put("client_id", "webapp");
		map.put("client_secret", "web");
		String token = Shardemr.getAccessToken(oauthURL, map);
		System.out.println(token);
	}
	
	
	@Test
	public void getShardemr() throws Exception{
		Map<String,String> oauthTokenMap = new HashedMap<String, String>();
		oauthTokenMap.put("grant_type", "password");
		oauthTokenMap.put("username", "testshard");
		oauthTokenMap.put("password", "123456");
		oauthTokenMap.put("client_id", "webapp");
		oauthTokenMap.put("client_secret", "web");
		
		Map<String,String> shardemrMap = new HashedMap<String, String>();
		shardemrMap.put("inpatient_sn", "");
		shardemrMap.put("patient_id", "");
		shardemrMap.put("patient_sn", "pat_60d87d6c8b784976ef2dd0df2e541f1c");
		shardemrMap.put("scopes", "");
		
		String shardemr = Shardemr.getShardemr(oauthURL, shardemrURL, oauthTokenMap, shardemrMap);
		System.out.println(shardemr);
	}
	
	
}