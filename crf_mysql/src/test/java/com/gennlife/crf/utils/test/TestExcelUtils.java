package com.gennlife.crf.utils.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.ExcelUtils;

public class TestExcelUtils {
	
	private String filePath = "E:\\yujie\\2";
	private String fileName = "1.xlsx";
	private String sheetName = "患者信息";
	
	
	@Test
	public void checkSheetOfExcelExist(){
		Excel excel = new Excel(filePath, fileName, sheetName);
		String value = ExcelUtils.checkSheetOfExcelExist(excel);
		System.out.println(value);
	}
	
	@Test
	public void searchValueOfListByOrderDesc(){
		Excel excel = new Excel(filePath, fileName, sheetName);
		Map<Integer, String> map = ExcelUtils.searchValueOfListByOrderDesc(excel,19, 0);
		
		for (Map.Entry<Integer, String> entry : map.entrySet()) {  
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
	}
	
	@Test
	public void searchValueOfListByOrder(){
		Excel excel = new Excel(filePath, fileName, sheetName);
		Map<Integer, String> map = ExcelUtils.searchValueOfListByOrder(excel, 7);
		
		for (Map.Entry<Integer, String> entry : map.entrySet()) {  
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}
	}
	
	@Test
	public void searchKeyWordOfOneLine(){
		Excel excel = new Excel(filePath, fileName, sheetName);
		Integer i = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "英文名");
		System.out.println(i);
	}
	
	@Test
	public void readExcelOfList(){
		Excel excel = new Excel(filePath, fileName, sheetName);
		List<String> list = ExcelUtils.readExcelOfList(excel,7);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		System.out.println(list.size());
	}
	
	@Test
	public void searchKeyWord(){
		Excel excel = new Excel(filePath, fileName, sheetName);
		Integer integer = ExcelUtils.searchKeyWordOfList(excel,0, "性别");
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