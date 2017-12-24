package com.gennlife.mengyujie.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.cglib.beans.BeanMap;
import net.sf.json.JSONObject;

import org.apache.commons.collections4.map.HashedMap;
import org.junit.Test;

import com.gennlife.crf.bean.CrfPackageJsonBean;
import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.ListAndStringUtils;
import com.gennlife.myujie.ConfiguredCrfPackageJson;

public class TestConfiguredCrfPackageJson {

	//private String filePath = "E:\\CRF重组\\自动工具\\工具";
	private String filePath = "D:\\我的文档\\Desktop\\json";
	private String fileName = "模版样例.xlsx";
	private String sheetName = "Sheet1";
	//private JSONObject detailJson = new JSONObject();
	//private JSONArray jsonArray = new JSONArray(); 
	
	@Test
	public void selectCondition_test(){
		String strs="A:attribute.NEG,,isnull;B:attribute.FAM,,isnull;C:label,medicalproblem,equal;D:normalized_value,(?<!肾)癌|淋巴瘤|白血病|黑色素瘤|肉瘤,regex;E:normalized_value,瘤,contain;F :attribute.PRO, 恶性, contain; ";
		Map<String,String> map = ListAndStringUtils.valueSpiltBySemicolonToStringMap(strs);
		
		String selectContent="FE&D||C&B&A&";
		
		JSONObject detailJson = new JSONObject();
				
		//表达式处理
		selectContent=selectContent.replaceAll("\\|\\|","\\|");
		System.out.println(selectContent);
		
		
		//selectContent表达式解析(没有括号情况)
		if (!selectContent.contains("(") || !selectContent.contains(")")) {
			
			//定义变量
			CrfPackageJsonBean jsonBean = new CrfPackageJsonBean();
			//List<Map> listMaps = new ArrayList<Map>();
			
			
			for (int i = 0; i < selectContent.length(); i++) {
				List<Map> listMaps = new ArrayList<Map>();
				
				String element =  String.valueOf(selectContent.charAt(i));
				//判断
				if (!"&".equals(element) && !"|".equals(element)) {
					//判断对象jsonBean是否为空
					//不为空，则封装成整个Object
					if (jsonBean.getDetail()!=null) {
						BeanMap beanMap = BeanMap.create(jsonBean);  
						
						listMaps.add(beanMap);
						System.out.println(listMaps);
					}
					
					//元素
					String[] strings = ListAndStringUtils.valueSpiltByCommaToStrings(map.get(element));
					if (strings.length==3) {
						Map<String,Object> mapJson = new HashedMap<String, Object>();
						mapJson.put("source", strings[0]);
						mapJson.put("target_value", strings[1]);
						mapJson.put("operator", strings[2]);
						
						//封装成list
						listMaps.add(mapJson);
					}else {
						continue;
					}
					
					jsonBean.setDetail(listMaps);
					
					
				}else if ("&".equals(element)) {
					jsonBean.setOperator("and");
					
					
				}else if ("|".equals(element)) {
					jsonBean.setOperator("or");
					
				}
				
			}
			detailJson.put("select-condition",jsonBean );
			System.out.println(detailJson);
			
		}else if (selectContent.contains("(") && selectContent.contains(")")) {
			//selectContent表达式解析(有括号情况)
			
			
		}
		
	}
	
	
	
	@Test
	public void selectCondition(){
		String strs="A:attribute.NEG,,isnull;B:attribute.FAM,,isnull;C:label,medicalproblem,equal;D:normalized_value,(?<!肾)癌|淋巴瘤|白血病|黑色素瘤|肉瘤,regex;E:normalized_value,瘤,contain;F :attribute.PRO, 恶性, contain; ";
		Map<String,String> map = ListAndStringUtils.valueSpiltBySemicolonToStringMap(strs);
		
		String selectContent="FE&D||C&B&A&";
		
		JSONObject detailJson = new JSONObject();
		JSONObject fromObject = null;
		
		
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
						
						//jsonArray.put(mapJson);
						//System.out.println(jsonArray);
						
						detailJson.accumulate("detail", mapJson);
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
					}else {
						continue;
					}
					
				}else if ("&".equals(element)) {
					fromObject = JSONObject.fromObject(detailJson);
					
					detailJson.accumulate("operator","and");
					detailJson.accumulate("detail",fromObject);
					
					
				}else if ("|".equals(element)) {
					fromObject = JSONObject.fromObject(detailJson);
					detailJson.accumulate("operator","or");
					detailJson.accumulate("detail",fromObject);
					
				}
				
				
			}
			System.out.println(detailJson);
			
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