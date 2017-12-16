package com.gennlife.crf.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
	* @Title: getJSONObjectAllKeys 
	* @Description: 获取jsonObject中所有的key（递归）
	* @param: @param jsonObject
	* @param: @return :
	* @return: List<String>
	* @throws 
	*/
	//public static List<String> getJSONObjectAllKeysList(JSONObject jsonObj){
	public static String getJSONObjectAllKeys(JSONObject jsonObj){
		//List<String> list = new ArrayList<String>();
		String result = null;
		Iterator keys = jsonObj.keys();
		while (keys.hasNext()) {
			try {
				String key = keys.next().toString();
				String value = jsonObj.optString(key);
				//判断value的值，是否是Object、Array
				int i = stringIsArrayORObject(value);
				
				//
				//if (list.size()==0 || list.isEmpty()) {
				if (result==null || result.equals("")) {
					if (i == 0) {
						//list.add(key);
						result = key + ",";
						System.out.println("i=0 | key=" + key + "| result="+ result);
					} else if (i == 1) {
						result = key + ",";
						System.out.println("i=1 | key=" + key + "| result="+ result);
						result = getJSONObjectAllKeys(new JSONObject(value)) + ",";
						//getJSONObjectAllKeysList(new JSONObject(value));
					} else if (i == 2) {
						result = key + ",";
						System.out.println("i=2 | key=" + key + "| result="+ result);
						JSONArray arrays = new JSONArray(value);
						for (int j = 0; j < arrays.length(); j++) {
							JSONObject array = new JSONObject(arrays.get(j));
							result = getJSONObjectAllKeys(array) + ",";
							//list.add(key);
							//getJSONObjectAllKeysList(array);
						}
					}
				} else {
					if (i == 0) {
						//list.add(key);
						result = result + key + ",";
						System.out.println("i=0 | key=" + key + "| result="+ result);
					} else if (i == 1) {
						result = result + key + ",";
						System.out.println("i=1 | key=" + key + "| result="+ result);
						result = result + getJSONObjectAllKeys(new JSONObject(value));
						//getJSONObjectAllKeysList(new JSONObject(value));
					} else if (i == 2) {
						result = result + key + ",";
						System.out.println("i=2 | key=" + key + "| result="+ result);		
						JSONArray arrays = new JSONArray(value);
						for (int k = 0; k < arrays.length(); k++) {
							JSONObject array = new JSONObject(arrays.get(k));
							result = result + getJSONObjectAllKeys(array) + ",";
							//list.add(key);
							//getJSONObjectAllKeysList(array);
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		//return list;
		return result;
	}

	
	/**
	 * @Title: stringIsArrayORObject
	 * @Description: 判断一个字符串是array类型还是object
	 * @param: @param jsonStr
	 * @param: @return : 0:既不是array也不是object 1：是object 2 ：是Array
	 * @return: int
	 * @throws
	 */
	public static int stringIsArrayORObject(String jsonStr) {
		try {
			JSONArray array = new JSONArray(jsonStr);
			return 2;
		} catch (JSONException e) {// 报错 说明JSON字符不是数组或根本就不是JSON
			try {
				JSONObject object = new JSONObject(jsonStr);
				return 1;
			} catch (JSONException e2) {// 报错  说明JSON字符根本就不是JSON
				//System.out.println("非法的JSON字符串");
				return 0;
			}
		}
	}

	/**
	 * @Title: JsonCompareJson
	 * @Description: 比较两个json字符串是否相等
	 * @param: @param str1
	 * @param: @param str2
	 * @param: @return :
	 * @return: boolean
	 * @throws
	 */
	public static boolean JsonCompareJson(String str1, String str2) {
		Gson gson1 = new GsonBuilder().create();// or new Gson()
		JsonElement e1 = gson1.toJsonTree(str1);// or new Gson()

		Gson gson2 = new GsonBuilder().create();
		JsonElement e2 = gson2.toJsonTree(str2);

		return e1.equals(e2);
	}

}
