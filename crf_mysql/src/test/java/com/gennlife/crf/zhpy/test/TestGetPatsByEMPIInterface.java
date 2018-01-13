package com.gennlife.crf.zhpy.test;

import org.junit.Test;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.zhpy.GetPatsByEMPIInterface;

public class TestGetPatsByEMPIInterface {

	private String filePath = "C:\\Users\\www\\Desktop";
	private String fileName = "淋巴瘤病人编号关联_0113.xlsx";
	private String sheetName = "Sheet1";
	
	@Test
	public void getResultsByPostMethod() {
		Excel excel = new Excel(filePath, fileName, sheetName);
		String results = GetPatsByEMPIInterface.getPatsByPostMethod(excel);
		System.out.println(results);
	}
}
