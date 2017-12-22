package com.gennlife.crf.utils.test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.gennlife.crf.utils.ListAndStringUtils;
import com.gennlife.myujie.ConfiguredCrfPackageJson;

public class TestJsonlUtils {
	
	
	@Test
	public void test01() throws Exception {
		String nlpSource="$.visits[*].record.admissions_record.admissions_records_past_medical_history;$.visits[*].record.course_record.fir.first_course_record_medical_feature";
		JSONArray nlpJsonArray = ListAndStringUtils.valueSpiltBySemicolonToJSONArray(nlpSource);
		System.out.println(nlpJsonArray);
		
		//nlp
		JSONObject nlpJson=new JSONObject();
		nlpJson.put("nlp", nlpJsonArray);
		System.out.println(nlpJson);
		
		//sourceJson
		JSONObject sourceJson=new JSONObject();
		sourceJson.put("source", nlpJson);
		System.out.println(sourceJson);
		
		//conditionJson
		JSONObject conditionJson=new JSONObject();
		
		//computerJson
		JSONObject computerJson=new JSONObject();
		
		//blockJson
		JSONObject blockJson=new JSONObject();
		blockJson.put("block_", sourceJson);
		System.out.println(blockJson);
		
		//最后的json
		JSONObject lastJson=new JSONObject();
		lastJson.put("blocks", blockJson);
		System.out.println(lastJson);
		
		//ConfiguredCrfPackageJson.jsonToJsonFile(lastJson);
		
	}
	
	
	@Test
	public void getJSONObjectAllKeys() throws Exception {
		/*String strs = FileUtils.readFileContent("C:\\Users\\www\\Desktop\\test.json");
		JSONObject obj=new JSONObject(strs);
		Map<String,Object> map=new HashMap<String,Object>();  
		map.put("OP_LESION",null);  
		
		JsonUtil.test2(obj, map);
		
		for (Entry entry : map.entrySet()) {
			System.out.println(entry.getKey() + "--" + entry.getValue());
		}
*/
		
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