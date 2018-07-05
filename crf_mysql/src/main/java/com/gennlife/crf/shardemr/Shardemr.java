package com.gennlife.crf.shardemr;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.gennlife.interfaces.ShardemrAndOauthTokenInterface;


/**
 * @Description:测试shardemr接口
 * @author: wangmiao
 * @Date: 2018年7月5日 下午2:27:55 
 */
public class Shardemr { 
	
	/** 
	* @Title: getAccessToken 
	* @Description: 根据接口，解析接口获取AccessToken，因为有10min的显示，所以每次请求加上重新请求接口
	* @Date: 2018年7月5日 下午2:34:58 
	* @param: @param url
	* @param: @param map
	* @param: @return
	* @return: String
	* @throws 
	*/
	public static String getAccessToken(String url,Map<String, String> map) {
		String responses = ShardemrAndOauthTokenInterface.getOauthToken(url, map);
		//定义OauthToken返回的JSONObject
		JSONObject jsonObject=null;
		//定义返回的accessToken
		String accessToken=null;
		try {
			//转成JSONObject
			jsonObject = new JSONObject(responses);
			accessToken = (String) jsonObject.get("access_token");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return accessToken;
	}
	
	
	/** 
	* @Title: getShardemr 
	* @Description: 先请求oauthToken接口获取token，在请求shardemr，获取数据
	* @author: wangmiao
	* @Date: 2018年7月5日 下午2:46:13 
	* @param: @param url
	* @param: @param oauthTokenMap
	* @param: @param shardemrMap
	* @param: @return
	* @return: String
	* @throws 
	*/
	public static String getShardemr(String oauthURL,String shardemrURL,
			Map<String, String> oauthTokenMap,
			Map<String, String> shardemrMap) {
		//获取token
		String accessToken=getAccessToken(oauthURL, oauthTokenMap);
		
		//放置新的token放置过期
		shardemrMap.put("access_token", accessToken);
		//请求shardemr接口，返回数据
		String shardemr = ShardemrAndOauthTokenInterface.getShardemr(shardemrURL, shardemrMap);
		
		return shardemr;
	}

}
