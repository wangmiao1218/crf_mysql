package com.gennlife.crf.utils.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gennlife.crf.utils.ListAndStringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class TestListAndStringUtils {
	
	@Test
	public void StringListReturnRandomString(){
		String value="I型糖尿病;II型糖尿病";
		String string = ListAndStringUtils.StringListReturnRandomString(value);
		System.out.println(string);
	}
	
	
}