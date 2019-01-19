package com.gennlife.test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;


public class Test1 {

	@Test
	public void test(){
		System.out.println((int)((Math.random()*9+1)*1000));  
		
		List<String> staff = Arrays.asList(
               "mkyong",
               "jack",
               "lawrence"
        );
 
        // convert inside the map() method directly.
       /* List<String> result = staff.stream().map(temp -> {
        	
        	
        	
        }).collect(Collectors.toList());*/
		
		
		
	}
	
	
}