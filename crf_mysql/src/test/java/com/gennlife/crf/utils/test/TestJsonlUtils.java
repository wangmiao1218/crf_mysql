package com.gennlife.crf.utils.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;
import org.junit.Test;

import com.gennlife.crf.utils.FileUtils;
import com.gennlife.crf.utils.JsonUtil;

public class TestJsonlUtils {
	
	@Test
	public void getJSONObjectAllKeys() throws Exception {
		String strs = FileUtils.readFileContent("C:\\Users\\www\\Desktop\\test.json");
		JSONObject obj=new JSONObject(strs);
		Map<String,Object> map=new HashMap<String,Object>();  
		map.put("OP_LESION",null);  
		
		JsonUtil.test2(obj, map);
		
		for (Entry entry : map.entrySet()) {
			System.out.println(entry.getKey() + "--" + entry.getValue());
		}

		
		//System.out.println(obj.get("OP_LESION").toString());
		
		//obj.g
		
		//System.out.println(object);  
		//String str = JsonUtils.getJSONObjectAllKeys(obj);
		//System.out.println(str);
		
		/*List<String> list = JsonUtils.getJSONObjectAllKeysList(jsonObject);
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		*/
		
		/*
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject1 = new JSONObject();
		String[] strings = "[0,1,2],[2]";
		try {
			jsonObject1.put("A", "1");  
			jsonObject1.put("B", "2");  
			
			jsonObject.put("a", "1");  
			jsonObject.put("A", "11");  
			jsonObject.put("c", jsonObject1);  
			jsonObject.put("b", "2");
			jsonObject.put("arr", ");
		} catch (JSONException e) {
			e.printStackTrace();
		}  
		*/
		
	}

}