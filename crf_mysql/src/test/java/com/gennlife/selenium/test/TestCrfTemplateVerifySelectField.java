package com.gennlife.selenium.test;

import org.junit.Test;

import com.gennlife.crf.bean.Excel;
import com.gennlife.selenium.CrfTemplateVerifySelectField;

public class TestCrfTemplateVerifySelectField {

	private String filePath = "E:\\安贞\\！安贞心血管\\test\\excel";
	private String fileName = "test-模板结构-安贞高血压.xlsx";
	
	private String fileName2 = "test-安贞高血压CRF模版1.1.22-2017-07-17.xls";
	private String sheetName = "总体结构";
	
	@Test
	public void verifySelectField() throws Exception{
		Excel excelmb = new Excel(filePath, fileName, sheetName);
		Excel excel = new Excel(filePath, fileName2, sheetName);
		CrfTemplateVerifySelectField.verifySelectField(excelmb, excel);
		System.out.println("ok");
	}
	
}