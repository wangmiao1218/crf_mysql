package com.gennlife.crf.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

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
	* @Title: insertPatAndValueReturnNewJSONObject 
	* @Description: 插入patContent和字段的值，返回新的JSONObject
	* @param: @param baseJson
	* @param: @param patPath
	* @param: @param patContent
	* @param: @param strs
	* @param: @param insertContent
	* @param: @return
	* @return: JSONObject
	* @throws 
	*/
	public static JSONObject insertPatAndValueReturnNewJSONObject(JSONObject baseJson,
			String patPath,String patContent,String[] strs,String insertContent) throws JSONException{
		//定义中间JSONObject
		JSONObject middleJsonObject = new JSONObject();
		//长度进行判断
		switch(strs.length){
		case 0:
			break;
	    case 1:
	        break;
	    case 2:
	    	middleJsonObject=((JSONObject) baseJson.getJSONArray(strs[0]).get(0));
	    	middleJsonObject.put(strs[1], insertContent);
	        break;
	    case 3:
	        middleJsonObject=((JSONObject) ((JSONObject) baseJson.getJSONArray(strs[0]).get(0)).getJSONArray(strs[1]).get(0));
	    	middleJsonObject.put(strs[2], insertContent);
	        break;
	    case 4:
	    	middleJsonObject=((JSONObject) ((JSONObject) ((JSONObject) baseJson.getJSONArray(strs[0]).get(0)).getJSONArray(strs[1]).get(0)).getJSONArray(strs[2]).get(0));
	    	middleJsonObject.put(strs[3], insertContent);
	    	break;
	    case 5:
	    	middleJsonObject=(JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) baseJson.getJSONArray(strs[0]).get(0)).getJSONArray(strs[1]).get(0)).getJSONArray(strs[2]).get(0)).getJSONArray(strs[3]).get(0);
	    	middleJsonObject.put(strs[4], insertContent);
	    	break;
	    default:
	        break;
		} 
		//最后统一加pat
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
	public static JSONObject readFileContentReturnJson(String path) throws JSONException{
		String laststr="";
		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		try{
			fileInputStream = new FileInputStream(path);
			inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			
			while((tempString = reader.readLine()) != null){
				laststr += tempString;
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(reader != null){
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
	public static void jsonToJsonFile(JSONObject lastJson,String path) {
        File file=new File(path);
        try {
        	 Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
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
