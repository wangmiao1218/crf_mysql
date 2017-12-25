package com.gennlife.crf.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;

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

	/*
	public void getJSONode(Object obj) throws Exception {
		if (obj instanceof JSONObject) {
			JSONObject jo = (JSONObject) obj;
			String[] names = JSONObject.getNames(jo);
			for (String na : names) {
				try {
					getJSONode(jo.getJSONObject(na));
				} catch (JSONException e) {
					if (jo.get(na) instanceof JSONArray) {
						if (!jo.get(na).toString().contains(":")) {
							hm.put(na, jo.get(na).toString());
						} else {
							getJSONode(jo.get(na));
						}
					} else {
						if (hm.containsKey(na)) {
							// String k = na + new Random().nextInt();
							hm.put(na + "=>" + jo.getString(na),
									jo.getString(na));
							hm.put(jo.getString(na), na);
						} else {
							hm.put(na, jo.getString(na));
							hm.put(jo.getString(na), na);
						}
					}
				}
			}
		} else if (obj instanceof JSONArray) {
			JSONArray jarr = (JSONArray) obj;
			for (int i = 0; i < jarr.length(); i++) {
				JSONObject jso = jarr.getJSONObject(i);
				getJSONode(jso);
			}
		}
	}
	
	*/
	
	
	
	
	
	
	public void jsonPattern(String inFile,String outFile,String patt) throws IOException, JSONException{
		File fi = new File(inFile);
		if(fi.isFile() && fi.exists()){
			//read file
			InputStreamReader in = new InputStreamReader(new FileInputStream(fi));
			BufferedReader br = new BufferedReader(in);
			String jsonStr = br.readLine(); //read by line
			//write file
			File fo = new File(outFile);
			OutputStream out = new FileOutputStream(fo);
			
			String [] pats = patt.split(":");
			String [] pat_detail = pats[1].split(",");
			JSONArray ja = getJsonArray(jsonStr,pats[0]); //get target json array
			
			//get target items from json array via pattern
			int len = ja.length();
			for(int i=0;i<len;i++){
				Object seatID = ja.getJSONObject(i).get(pat_detail[0]);
				Object seatName = ja.getJSONObject(i).get(pat_detail[1]);
				//write to output file
				String outStr = "seatID: "+seatID+"\tseatName: "+seatName+"\r\n";
				byte[] by = outStr.getBytes();
				out.write(by);
			}
			br.close();
			in.close();
			out.close();
		}
	}

	
	public JSONArray getJsonArray(String jsonStr, String listKey){
		if(jsonStr.equals("")){
			return null;
		}
		Character c = jsonStr.charAt(0);
		if(c == '{'){
			try {
				JSONObject jo = new JSONObject(jsonStr);
				Iterator it = jo.keys();
				while(it.hasNext()){
					String key = (String)it.next();
					if(key.equals(listKey)){
						return jo.getJSONArray(key);
					}else{
						String value = jo.get(key).toString();
						JSONArray ja = getJsonArray(value,listKey); //recursion
						if(ja!=null){
							return ja;
						}
					}
				}//end while
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	/**
	* @Title: readFileContent 
	* @Description: 读取文件内容，返回json
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
