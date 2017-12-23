package com.gennlife.mengyujie.test;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONArray;
import org.junit.Test;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.ListAndStringUtils;
import com.gennlife.myujie.ConfiguredCrfPackageJson;

public class TestConfiguredCrfPackageJson {

	private String filePath = "E:\\CRF重组\\自动工具\\工具";
	private String fileName = "模版样例.xlsx";
	private String sheetName = "Sheet1";
	//private JSONObject detailJson = new JSONObject();
	//private JSONArray jsonArray = new JSONArray(); 
	
	@Test
	public void selectCondition(){
		String strs="A:attribute.NEG,,isnull;B:attribute.FAM,,isnull;C:label,medicalproblem,equal;D:normalized_value,(?<!肾)癌|淋巴瘤|白血病|黑色素瘤|肉瘤,regex;E:normalized_value,瘤,contain;F :attribute.PRO, 恶性, contain; ";
		Map<String,String> map = ListAndStringUtils.valueSpiltBySemicolonToStringMap(strs);
		
		String selectContent="FE&D||C&B&A&";
		JSONArray jsonArray = new JSONArray();
		JSONObject detailJson = new JSONObject();
		
		//表达式处理
		selectContent=selectContent.replaceAll("\\|\\|","\\|");
		System.out.println(selectContent);
		
		//selectContent表达式解析(没有括号情况)
		if (!selectContent.contains("(") || !selectContent.contains(")")) {
			for (int i = 0; i < selectContent.length(); i++) {
				String element =  String.valueOf(selectContent.charAt(i));
				
				if (!"&".equals(element) && !"|".equals(element)) {
					//元素
					String[] strings = ListAndStringUtils.valueSpiltByCommaToStrings(map.get(element));
					
					if (strings.length==3) {
						Map<String,Object> mapJson = new HashedMap<String, Object>();
						mapJson.put("source", strings[0]);
						mapJson.put("target_value", strings[1]);
						mapJson.put("operator", strings[2]);
						
						jsonArray.put(mapJson);
						System.out.println(jsonArray);
						
						detailJson.accumulate("detail", jsonArray);
						System.out.println(detailJson);
						
						/*
						JSONObject objJson=new JSONObject();
						objJson.put("source", strings[0]);
						objJson.put("target_value", strings[1]);
						objJson.put("operator", strings[2]);
						jsonArray.put(objJson);
						System.out.println(jsonArray);
						
						detailJson.put("detail", jsonArray);
						System.out.println(detailJson);
						
						*/
					}
					
				}else if ("&".equals(element)) {
					
					
					
				}else if ("|".equals(element)) {
					
					
					
				}
				
				
			}
			
			
		}else if (selectContent.contains("(") && selectContent.contains(")")) {
			//selectContent表达式解析(有括号情况)
			
			
		}
		
	}
	
	
	@Test
	public void getJsonFile(){
		Excel excel = new Excel(filePath, fileName, sheetName);
		ConfiguredCrfPackageJson.getJsonFile(excel);
	}
}