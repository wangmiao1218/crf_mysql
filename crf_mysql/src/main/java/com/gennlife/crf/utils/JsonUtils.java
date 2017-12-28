package com.gennlife.crf.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
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

	
	public static JSONObject  test01(String[]  strs) throws JSONException{  
		JSONObject jsonObject = new JSONObject().put(strs[strs.length-1], "test");
		
		for (int i = 0; i < strs.length; i++) {
			JSONArray objArray =new JSONArray(jsonObject.toString());
			jsonObject.put(strs[i-1], objArray);
			
		}
		
		return jsonObject;
		
	}
	
	
	public static void  analysisJson(Object objJson){  
        //如果obj为json数组  
        if(objJson instanceof JSONArray){  
        	net.sf.json.JSONArray objArray = (net.sf.json.JSONArray)objJson;  
            for (int i = 0; i < objArray.size(); i++) {  
                analysisJson(objArray.get(i));  
            }  
        }  
        //如果为json对象  
        else if(objJson instanceof JSONObject){  
        	net.sf.json.JSONObject jsonObject = (net.sf.json.JSONObject)objJson;  
            Iterator it = jsonObject.keys();  
            while(it.hasNext()){  
                String key = it.next().toString();  
                Object object = jsonObject.get(key);  
                //如果得到的是数组  
                if(object instanceof JSONArray){  
                    JSONArray objArray = (JSONArray)object;  
                    analysisJson(objArray);  
                }  
                //如果key中是一个json对象  
                else if(object instanceof JSONObject){  
                    analysisJson((JSONObject)object);  
                }  
                //如果key中是其他  
                else{  
                    System.out.println("["+key+"]:"+object.toString()+" ");  
                }  
            }  
        }  
    } 
	
	
	public static Object traveseJson(Object json) throws JSONException {
		if (json == null) {
			return null;
		}
		if (json instanceof JSONObject) {// json 是一个map
			// 创建一个json对象
			JSONObject jsonObj = new JSONObject();
			// 将json转换为JsonObject对象
			JSONObject jsonStr = (JSONObject) json;
			// 迭代器迭代 map集合所有的keys
			Iterator it = jsonStr.keys();
			while (it.hasNext()) {
				// 获取map的key
				String key = (String) it.next();
				// 得到value的值
				Object value = jsonStr.get(key);
				// System.out.println(value);
				// 递归遍历
				jsonObj.put(key, traveseJson(value));

			}
			return jsonObj;

		} else if (json instanceof JSONArray) {// if json 是 数组
			JSONArray jsonAry = new JSONArray();
			JSONArray jsonStr = (JSONArray) json;
			// 获取Array 的长度
			int length = jsonStr.length();
			for (int i = 0; i < length; i++) {
				jsonAry.put(traveseJson(jsonStr.get(i)));
			}

			return jsonAry;

		} else {// 其他类型

			return json;
		}

	}

	public static JSONObject test(String[] patientDetail, String insertContent)
			throws JSONException {
		JSONObject middleJsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		Map<Object, Object> map = new HashedMap<Object, Object>();

		for (int i = 0; i < patientDetail.length; i++) {
			// 定义中间JSONObject
			map.put(patientDetail[patientDetail.length - 1 - i], insertContent);
			jsonArray.put(map);
			middleJsonObject.put(
					patientDetail[patientDetail.length - 1 - i - 1], jsonArray);

		}

		return null;
	}

	/**
	 * @Title: insertPatAndValueReturnNewJSONObject
	 * @Description: 插入patContent和字段的值，返回新的JSONObject（使用少量的json时使用）
	 * @param: @param baseJson
	 * @param: @param patPath
	 * @param: @param patContent
	 * @param: @param strs
	 * @param: @param insertContent
	 * @param: @return
	 * @return: JSONObject
	 * @throws
	 */
	public static JSONObject insertPatAndValueReturnNewJSONObject(
			JSONObject baseJson, String patientDetail, String insertContent,
			String patPath, String patContent) throws JSONException {
		// 定义中间JSONObject
		JSONObject middleJsonObject = new JSONObject();
		// 用.分割后，转换成数组
		String[] strings = null;
		if (patientDetail.contains(".")) {
			strings = patientDetail.split("\\.");
			for (int i = 0; i < strings.length; i++) {
				strings[i] = strings[i].trim();
			}
		}
		// 添加到baseJson
		for (int i = 0; i < strings.length; i++) {
			// strings[i];

		}

		// 最后统一加pat
		String[] patSplit = patPath.split("\\.");
		((JSONObject) baseJson.get(patSplit[0])).put(patSplit[1], patContent);

		return baseJson;
	}

	/**
	 * @Title: updatePatAndValueReturnNewJSONObject
	 * @Description: 更新patContent和字段的值，返回新的JSONObject（使用全量的json时使用）
	 * @param: @param baseJson
	 * @param: @param patPath
	 * @param: @param patContent
	 * @param: @param strs
	 * @param: @param insertContent
	 * @param: @return
	 * @return: JSONObject
	 * @throws
	 */
	public static JSONObject updatePatAndValueReturnNewJSONObject(
			JSONObject baseJson, String patPath, String patContent,
			String[] strs, String insertContent) throws JSONException {
		// 定义中间JSONObject
		JSONObject middleJsonObject = new JSONObject();
		// 长度进行判断
		switch (strs.length) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			middleJsonObject = ((JSONObject) baseJson.getJSONArray(strs[0])
					.get(0));
			middleJsonObject.put(strs[1], insertContent);
			break;
		case 3:
			middleJsonObject = ((JSONObject) ((JSONObject) baseJson
					.getJSONArray(strs[0]).get(0)).getJSONArray(strs[1]).get(0));
			middleJsonObject.put(strs[2], insertContent);
			break;
		case 4:
			middleJsonObject = ((JSONObject) ((JSONObject) ((JSONObject) baseJson
					.getJSONArray(strs[0]).get(0)).getJSONArray(strs[1]).get(0))
					.getJSONArray(strs[2]).get(0));
			middleJsonObject.put(strs[3], insertContent);
			break;
		case 5:
			middleJsonObject = (JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) baseJson
					.getJSONArray(strs[0]).get(0)).getJSONArray(strs[1]).get(0))
					.getJSONArray(strs[2]).get(0)).getJSONArray(strs[3]).get(0);
			middleJsonObject.put(strs[4], insertContent);
			break;
		default:
			break;
		}
		// 最后统一加pat
		String[] patSplit = patPath.split("\\.");
		((JSONObject) baseJson.get(patSplit[0])).put(patSplit[1], patContent);

		return baseJson;
	}

	/**
	 * @Title: readFileContent
	 * @Description: 读取文件内容，返回JSONObject
	 * @param: @param Path
	 * @param: @return :
	 * @return: JSONObject
	 * @throws
	 */
	public static JSONObject readFileContentReturnJson(String path)
			throws JSONException {
		String laststr = "";
		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		try {
			fileInputStream = new FileInputStream(path);
			inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;

			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new JSONObject(laststr);
	}

	/**
	 * @Title: jsonToJsonFile
	 * @Description: 将json生成json文件
	 * @param: JSONObject lastJson
	 * @return: void
	 * @throws
	 */
	public static void jsonToJsonFile(JSONObject lastJson, String path) {
		File file = new File(path);
		try {
			Writer write = new OutputStreamWriter(new FileOutputStream(file),
					"UTF-8");
			write.write(lastJson.toString());
			write.flush();
			write.close();
		} catch (Exception e) {
			e.printStackTrace();
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
