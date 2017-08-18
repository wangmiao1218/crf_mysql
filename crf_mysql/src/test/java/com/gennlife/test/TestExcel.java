package com.gennlife.test;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.CreateWebDriver;
import com.gennlife.crf.utils.ExcelUtils;
import com.gennlife.crf.utils.LoginCrfOfAnzhen;
import com.gennlife.crf.utils.QuitWebDriver;


public class TestExcel {

	
	@Test
	public void readTwoContentAndJudge(){
		String filePath = "C:\\Users\\www\\Desktop";
		String fileName = "testSelenium.xlsx";
		String sheetName = "Sheet1";
		Excel excel = new Excel(filePath, fileName, sheetName);
		
		String string = ExcelUtils.readTwoContentAndJudge(excel, 5, 5, 8);
		System.out.println(string);
	}
	
	@Test
	public void readContent(){
		String filePath = "C:\\Users\\www\\Desktop";
		String fileName = "testSelenium.xlsx";
		String sheetName = "Sheet1";
		Excel excel = new Excel(filePath, fileName, sheetName);
		
		String string = ExcelUtils.readContent(excel,5,5);
		System.out.println(string);
		String string2 = ExcelUtils.readContent(excel,5,7);
		System.out.println(string2);
		
		if (string.contains(string2) && string2.contains(string) ) {
			System.out.println("���");
		}else {
			System.out.println("�����");
		}
		
	}
	
	
	@Test
	public void searchKeyWord(){
		String filePath = "C:\\Users\\www\\Desktop";
		String fileName = "testSelenium.xlsx";
		String sheetName = "Sheet1";
		Excel excel = new Excel(filePath, fileName, sheetName);
		
		Integer integer = ExcelUtils.searchKeyWordOfList(excel,1, "�Ա�");
		
		System.out.println(integer);
		
	}
	
	@Test
	public void testwriteContent(){
		String filePath = "C:\\Users\\www\\Desktop";
		String fileName = "testSelenium.xlsx";
		String sheetName = "Sheet1";
		Excel excel = new Excel(filePath, fileName, sheetName);
		
		ExcelUtils.writeAndSaveContent(excel, "eeee", 5, 7);
		System.out.println("���");
		
	}
	
}