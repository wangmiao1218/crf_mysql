package com.gennlife.crf.utils.test;

import org.junit.Test;

import com.gennlife.crf.utils.ListAndStringUtils;

public class TestListAndStringUtils {
	
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
	public void trimStringOfEqualSign(){
		String value="有无疾病史=有";
		String[] strings = ListAndStringUtils.trimStringOfEqualSign(value);
		System.out.println(strings[0]);
		System.out.println(strings[1]);
	}
	
	@Test
	public void StringListReturnRandomString(){
		String value="I型糖尿病;II型糖尿病";
		String string = ListAndStringUtils.stringListReturnRandomString(value);
		System.out.println(string);
	}
	
	
}