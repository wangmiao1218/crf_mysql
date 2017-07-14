package com.gennlife.test;

import org.junit.Test;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.ExcelUtils;
import com.gennlife.crf.utils.ListAndStringUtils;


public class TestListAndStringUtils {

	
	@Test
	public void trimString(){
		String filePath = "C:\\Users\\www\\Desktop";
		String fileName = "testSelenium.xlsx";
		String sheetName = "Sheet1";
		Excel excel = new Excel(filePath, fileName, sheetName);
		
		String string = ExcelUtils.readContent(excel,5,5);
		System.out.println(string);
		String string2 = ListAndStringUtils.trimString(string);
		System.out.println(string2);
		
	}
	
	
	@Test
	public void searchKeyWord(){
		String filePath = "C:\\Users\\www\\Desktop";
		String fileName = "testSelenium.xlsx";
		String sheetName = "Sheet1";
		Excel excel = new Excel(filePath, fileName, sheetName);
		
		Integer integer = ExcelUtils.searchKeyWord(excel,1, "�Ա�");
		
		System.out.println(integer);
		
	}
	
	@Test
	public void testwriteContent(){
		String filePath = "C:\\Users\\www\\Desktop";
		String fileName = "testSelenium.xlsx";
		String sheetName = "Sheet1";
		Excel excel = new Excel(filePath, fileName, sheetName);
		
		ExcelUtils.writeAndSaveContent(excel, "eeee", 5, 7);
		
	}
	
}