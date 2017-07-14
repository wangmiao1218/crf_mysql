package com.gennlife.crf.utils.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gennlife.crf.utils.RandomString;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class TestRandomString {
	
	@Test
	public void UUIDString(){
		String string = RandomString.UUIDString();
		System.out.println(string);
	}
	@Test
	public void randomRangeInt(){
		String string = RandomString.randomRangeInt(10,20);
		System.out.println(string);
	}
	
	@Test
	public void randomDateyyyyMMdd() throws Exception{
		String string = RandomString.randomDateyyyyMMdd();
		System.out.println(string);
	}
	
	@Test
	public void randomDateyyyyMMddHHmmss() throws Exception{
		String string = RandomString.randomDateyyyyMMddHHmmss();
		System.out.println(string);
	}
	
	
	
}