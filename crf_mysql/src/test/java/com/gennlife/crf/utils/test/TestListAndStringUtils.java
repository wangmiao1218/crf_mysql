package com.gennlife.crf.utils.test;

import java.util.List;

import org.junit.Test;

import com.gennlife.crf.utils.ListAndStringUtils;

public class TestListAndStringUtils {

	@Test
	public void valueSpiltToStringList(){
		String value="就诊.就诊基本信息.入院（就诊）时间；就诊.就诊基本信息.挂号日期";
		List<String> list = ListAndStringUtils.valueSpiltToStringList(value);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
	@Test
	public void chNamesListFilter(){
		String value=" 剂  量（mg/天）a，d ff,d sdf,dfdsf，fsf、bc：1.2.3.bcd？";
		//去掉中英文（）之间
		if (value.indexOf("（")!=-1) {
			value=value.replaceAll(value.substring(value.indexOf("（"),value.indexOf("）")+1),"");
		}
		if (value.indexOf("(")!=-1) {
			value=value.replaceAll(value.substring(value.indexOf("("),value.indexOf(")")+1),"");
		}
		//去掉中英文:之后
		if (value.indexOf("：")!=-1) {
			value = value.substring(0, value.indexOf("："));
		}
		if (value.indexOf(":")!=-1) {
			value = value.substring(0, value.indexOf(":"));
		}
		
		//去掉中英文？之后
		if (value.indexOf("？")!=-1) {
			value = value.substring(0, value.indexOf("？"));
		}
		if (value.indexOf("?")!=-1) {
			value = value.substring(0, value.indexOf("?"));
		}
		
		//删掉、
		if (value.indexOf("、")!=-1) {
			value = value.replace("、",""); 
		}
		
		//删掉空格
		if (value.indexOf(" ")!=-1) {
			value = value.replace(" ",""); 
		}
		
		//删掉中引文，
		if (value.indexOf("，")!=-1) {
			value = value.replace("，","");
		}
		if (value.indexOf(",")!=-1) {
			value = value.replace(",","");
		}
		
		System.out.println(value);
	}
	
	@Test
	public void trimStringOfEqualSign(){
		String value="有无疾病史=有";
		String[] strings = ListAndStringUtils.trimStringOfEqualSign(value);
		System.out.println(strings.length);
		System.out.println(strings[0]);
		System.out.println(strings[1]);
	}
	
	@Test
	public void stringToSubstringReturnFilePath(){
		String value="E:\\yujie\\2\\模板结构-乳腺癌.xlsx";
		String str = ListAndStringUtils.stringToSubstringReturnFilePath(value);
		System.out.println(str);
	}
	
	@Test
	public void stringToSubstringReturnFileName(){
		String value="E:\\yujie\\2\\test.xlsx";
		String str = ListAndStringUtils.stringToSubstringReturnFileName(value);
		System.out.println(str);
	}
	
	@Test
	public void StringListReturnRandomString(){
		String value="I型糖尿病;II型糖尿病";
		String string = ListAndStringUtils.stringListReturnRandomString(value);
		System.out.println(string);
	}
	
	
}