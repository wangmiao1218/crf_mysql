package com.gennlife.selenium.test;

import org.junit.Test;

import com.gennlife.crf.bean.Excel;
import com.gennlife.selenium.CrfTemplateVerifyLinkageField;
import com.gennlife.selenium.CrfTemplateVerifyMinMaxAlertValueField;
import com.gennlife.selenium.CrfTemplateVerifySelectField;

public class TestCrfTemplateVerify {

	private String filePath = "E:\\安贞\\！安贞心血管\\test\\excel";
	private String fileName = "测试-模板结构-安贞心血管-自动.xlsx";
	
	private String fileName2 = "安贞心血管CRF1.0.8-2017_09_21_for研发 - 自动.xls";
	private String sheetName = "总体结构";
	
	@Test
	public void verifyLinkageField() throws Exception{
		Excel excelmb = new Excel(filePath, fileName, sheetName);
		Excel excel = new Excel(filePath, fileName2, sheetName);
		CrfTemplateVerifyLinkageField.verifyLinkageField(excelmb, excel);
		System.out.println("ok");
	}
	
	@Test
	public void verifySelectField() throws Exception{
		Excel excelmb = new Excel(filePath, fileName, sheetName);
		Excel excel = new Excel(filePath, fileName2, sheetName);
		CrfTemplateVerifySelectField.verifySelectField(excelmb, excel);
		System.out.println("ok");
	}
	
	@Test
	public void verifyMinMaxAlertValueField() throws Exception{
		Excel excelmb = new Excel(filePath, fileName, sheetName);
		Excel excel = new Excel(filePath, fileName2, sheetName);
		CrfTemplateVerifyMinMaxAlertValueField.verifyMinMaxAlertValueField(excelmb, excel);
		System.out.println("ok");
	}
	
}