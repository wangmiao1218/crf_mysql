package com.gennlife.crf.utils.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.gennlife.crf.utils.JsonUtils;

public class TestJsonlUtils {
	
	private String path = "E:\\CRFLogic\\test\\New1.json";
	private String path2 = "E:\\CRFLogic\\test\\little.json";
	
	
	@Test
	public void test06() throws Exception {
		String patientDetailContent="visits.record.admissions_record.admissions_records_physical_examination";
		String insertContent="test";
		
		//用.分割后，转换成数组
		String[] strings = null;
		if (patientDetailContent.contains(".")) {
			strings = patientDetailContent.split("\\.");
			for (int i = 0; i < strings.length; i++) {
				strings[i] = strings[i].trim();
			}
		}
		
		JSONObject test01 = JsonUtils.test01(strings);
		System.out.println(test01);
		
	}
	
	
	@Test
	public void test05() throws Exception {
		org.json.JSONObject baseJson = JsonUtils.readFileContentReturnJson(path);
		//JsonUtils.analysisJson(baseJson);
		
	}
	
	@Test
	public void test04() throws Exception {
		org.json.JSONObject baseJson = JsonUtils.readFileContentReturnJson(path);
		
		Object object = JsonUtils.traveseJson(baseJson);
		System.out.println(object);
	}
	
	
	@Test
	public void test03() throws Exception {
		org.json.JSONObject baseJson = JsonUtils.readFileContentReturnJson(path2);
		String patientDetailContent="visits.record.admissions_record.admissions_records_physical_examination";
		String insertContent="test";
		
		//用.分割后，转换成数组
		String[] strings = null;
		if (patientDetailContent.contains(".")) {
			strings = patientDetailContent.split("\\.");
			for (int i = 0; i < strings.length; i++) {
				strings[i] = strings[i].trim();
			}
		}
		
		List<JSONObject> list = new ArrayList<JSONObject>();
		Map<Object,Object> map = new HashMap<Object, Object>();
		
		
		JSONObject middleJsonObject = new JSONObject();
		JSONArray jsonArray = baseJson.getJSONArray(strings[0]);
		//提取后的middleJsonObject
		middleJsonObject=(JSONObject) jsonArray.get(0);
		System.out.println(middleJsonObject);
		
		//String value = "{\"admissions_record\":[{\"admissions_records_physical_examination\" : \"test\"}]}";
		String value = "{\"record\":[{\"admissions_record\":[{\"admissions_records_physical_examination\" : \"test\"}]}]}";
		//转为jsonObject
		JSONObject jsonObject  = new JSONObject(value);
		System.out.println(jsonObject);
		
		//map.put("visits", middleJsonObject);
		map.put("visits", jsonObject);
		System.out.println(map);
		
		/*
		list.add(middleJsonObject);
		list.add(jsonObject);
		net.sf.json.JSONArray jsonObj=net.sf.json.JSONArray.fromObject(list);
		System.out.println(jsonObj);
		
		//添加所有
		baseJson.accumulate("visits", jsonObj);
		*/
		System.out.println(baseJson);
	}
	
	
	@Test
	public void test02() throws Exception {
		org.json.JSONObject baseJson = JsonUtils.readFileContentReturnJson(path2);
		String patientDetailContent="visits.record.admissions_record.admissions_records_physical_examination";
		String insertContent="test";
		
		//用.分割后，转换成数组
		String[] strings = null;
		if (patientDetailContent.contains(".")) {
			strings = patientDetailContent.split("\\.");
			for (int i = 0; i < strings.length; i++) {
				strings[i] = strings[i].trim();
			}
		}
		
		JSONObject middleJsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray(); 
		
		//添加到baseJson
		/*for (int i = 0; i < strings.length; i++) {
			//定义中间JSONObject
			jsonArray.put(value);
			jsonArray.put(strings[strings.length-i-1], insertContent);
			middleJsonObject.put(strings[strings.length-i-2], map);
			System.out.println(middleJsonObject);
		}*/
		
		System.out.println(baseJson);
		
		
	}
	

	
	@Test
	public void test01() throws Exception {
		String patientDetailContent="visits.record.admissions_record.admissions_records_physical_examination";
		if (!patientDetailContent.contains(";")) {
			System.out.println("ok");
		}
	}
	
	
	@Test
	public void getJSONode() throws Exception {
		org.json.JSONObject baseJson = JsonUtils.readFileContentReturnJson(path2);
		String patientDetailContent="visits.record.admissions_record.admissions_records_physical_examination";
		System.out.println(baseJson);
		
		JSONObject jsonObject = (JSONObject) baseJson.getJSONArray("visits").get(0);
	}
	
	
	@Test
	public void readFileContentReturnJson() throws Exception {
		//String path="D:\\我的文档\\Desktop\\crf工具\\New1.json";
		String path="E:\\CRFLogic\\test\\New1.json";
		org.json.JSONObject jsonObject = JsonUtils.readFileContentReturnJson(path);
		System.out.println(jsonObject);
	}
	

}