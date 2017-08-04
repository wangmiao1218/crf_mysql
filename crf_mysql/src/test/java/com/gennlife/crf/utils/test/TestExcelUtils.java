package com.gennlife.crf.utils.test;

import org.junit.Test;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.ExcelUtils;

public class TestExcelUtils {
	
	private String filePath = "E:\\安贞\\！安贞数据导出";
	private String fileName = "test01.xlsx";
	private String sheetName = "Sheet1";
	
	@Test
	public void searchKeyWord(){
		Excel excel = new Excel(filePath, fileName, sheetName);
		Integer integer = ExcelUtils.searchKeyWord(excel,0, "性别");
		System.out.println(integer);
	}
	
	@Test
	public void testwriteContent(){
		Excel excel = new Excel(filePath, fileName, sheetName);
		ExcelUtils.writeAndSaveContent(excel, "eeee", 5, 7);
		System.out.println("OK");
	}
	
	
}