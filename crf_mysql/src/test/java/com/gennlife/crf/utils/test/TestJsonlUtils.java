package com.gennlife.crf.utils.test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.gennlife.crf.utils.JsonUtils;
import com.gennlife.crf.utils.ListAndStringUtils;
import com.gennlife.myujie.ConfiguredCrfPackageJson;

public class TestJsonlUtils {
	
	@Test
	public void readFileContentReturnJson() throws Exception {
		String path="D:\\我的文档\\Desktop\\crf工具\\New1.json";
		org.json.JSONObject jsonObject = JsonUtils.readFileContentReturnJson(path);
		System.out.println(jsonObject);
		
		
		
		
	}
	
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
	

}