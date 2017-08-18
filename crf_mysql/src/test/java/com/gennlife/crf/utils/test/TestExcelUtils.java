package com.gennlife.crf.utils.test;

import java.util.List;

import org.junit.Test;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.ExcelUtils;

public class TestExcelUtils {
	
	private String filePath = "E:\\yujie";
	private String fileName = "英文版乳腺癌2.xlsx";
	private String sheetName = "患者信息";
	
	@Test
	public void readExcelOfLine(){
		Excel excel = new Excel(filePath, fileName, sheetName);
		List<String> list = ExcelUtils.readExcelOfLine(excel,1);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		System.out.println(list.size());
		
	}
	
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
	
	@Test
	public void readTwoContentAndJudge(){
		Excel excel = new Excel(filePath, fileName, sheetName);
		String string = ExcelUtils.readTwoContentAndJudge(excel, 8, 2, 5);
		System.out.println(string);
	}
}