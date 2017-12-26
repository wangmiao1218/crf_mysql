package com.gennlife.crf.utils.test;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.gennlife.crf.utils.JsonUtils;

public class TestJsonlUtils {
	
	private String path = "E:\\CRFLogic\\test\\New1.json";
	private String path2 = "E:\\CRFLogic\\test\\little.json";
	
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