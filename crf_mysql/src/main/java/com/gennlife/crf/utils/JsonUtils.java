package com.gennlife.crf.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;


/**
 * @Description: json格式的处理工具类
 * @author: wangmiao
 * @Date: 2017年11月27日 下午4:32:42 
 */
public class JsonUtils {
	
	/** 
	* @Title: JsonCompareJson 
	* @Description: 比较两个json字符串是否相等
	* @param: @param str1
	* @param: @param str2
	* @param: @return :
	* @return: boolean
	* @throws 
	*/
	public static boolean JsonCompareJson(String str1,String str2){
		Gson gson1 = new GsonBuilder().create();//or new Gson()   
        JsonElement e1 = gson1.toJsonTree(str1);//or new Gson()   
          
        Gson gson2 = new GsonBuilder().create();  
        JsonElement e2 = gson2.toJsonTree(str2);  
        
        return e1.equals(e2);
	}
	

}
