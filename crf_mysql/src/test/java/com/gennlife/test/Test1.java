package com.gennlife.test;

import java.util.Arrays;

import org.junit.Test;


public class Test1 {

	@Test
	public void test(){
		//System.out.println((int)((Math.random()*9+1)*1000));  
		
		Arrays.asList("a","b").forEach(e->{
			System.out.print(e);
		});
		
	}
	
}