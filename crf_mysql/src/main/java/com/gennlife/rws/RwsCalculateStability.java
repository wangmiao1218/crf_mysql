package com.gennlife.rws;

import java.io.IOException;
import java.util.Map;

import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gennlife.interfaces.RwsInterface;


/**
 * @Description: rws计算稳定性测试
 * @author: wangmiao
 * @Date: 2018年8月17日 下午1:52:23 
 */
public class RwsCalculateStability {
	
	private static final Logger logger = LoggerFactory.getLogger(RwsCalculateStability.class);
	//定义返回结果
	private static final Map<String, String> map = new ConcurrentHashMap<>();
			
	
	public static String rwsCal(String loginRwsUrl) throws Exception{
		//第一步：请求登录获取session
		String response = RwsInterface.doGet(loginRwsUrl);
		//获取session
		JSONObject jsonObject = new JSONObject(response);
		String sessionId = ((JSONObject) jsonObject.get("data")).getString("uid");
				
		//第二步：读取json文件，请求计算接口
		
		
		//第三步：查看计算结果接口，知道有数值类型，保存到map中
		
		//读取基础文本文件，并转为json
		//org.json.JSONObject baseJson = JsonUtils.readFileContentReturnJson(path);
		
		
		
		return null;
		
	}
	
	
	
}
