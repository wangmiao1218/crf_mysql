package com.gennlife.crf.utils.test;

import org.junit.Test;

import com.gennlife.crf.utils.ListAndStringUtils;

public class TestListAndStringUtils {
	
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