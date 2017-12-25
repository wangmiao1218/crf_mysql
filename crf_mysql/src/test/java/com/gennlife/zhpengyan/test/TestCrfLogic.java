package com.gennlife.zhpengyan.test;

import java.util.Map;
import org.json.JSONException;
import org.junit.Test;
import com.gennlife.crf.utils.ListAndStringUtils;

public class TestCrfLogic {

	//private String filePath = "E:\\CRF重组\\自动工具\\工具";
	private String filePath = "D:\\我的文档\\Desktop\\json";
	private String fileName = "模版样例.xlsx";
	private String sheetName = "Sheet1";
	//private JSONObject detailJson = new JSONObject();
	//private JSONArray jsonArray = new JSONArray(); 
	
	
	@Test
	public void selectCondition_test3() throws JSONException{
		String strs="A:attribute.NEG,,isnull;B:attribute.FAM,,isnull;C:label,medicalproblem,equal;D:normalized_value,(?<!肾)癌|淋巴瘤|白血病|黑色素瘤|肉瘤,regex;E:normalized_value,瘤,contain;F :attribute.PRO, 恶性, contain; ";
		Map<String,String> elementMap = ListAndStringUtils.valueSpiltBySemicolonToStringMap(strs);
		String selectContent="A&(B&(C&(D||(E&F))))";
		//表达式处理
		selectContent=selectContent.replaceAll("\\|\\|","\\|");
		System.out.println(selectContent);
		
		String substring = selectContent.substring(selectContent.lastIndexOf("(")+1);
		System.out.println(selectContent.lastIndexOf("(")+1);
		System.out.println(substring);
		
		String substring2 = substring.substring(0,substring.indexOf(")"));
		System.out.println(substring2);
		
		System.out.println("===========");
		String substring3 = selectContent.substring(selectContent.lastIndexOf("(")).substring(0,substring.indexOf(")")+2);
		System.out.println(substring3);
		
		String replace = selectContent.replace(substring3, "");
		System.out.println(replace);
	}
	

	
}