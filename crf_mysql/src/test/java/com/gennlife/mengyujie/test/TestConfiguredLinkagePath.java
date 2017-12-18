package com.gennlife.mengyujie.test;

import org.junit.Test;

import com.gennlife.crf.bean.Excel;
import com.gennlife.myujie.ConfiguredLinkagePath;

public class TestConfiguredLinkagePath {

private String filePath = "E:\\yujie\\3";
	
	private String fileName = "模板结构.xlsx";
	private String fileName2 = "test.xlsx";
	
	private String sheetName = "总体结构";
	private String sheetName2 = "就诊－院内药物治疗";
	
	@Test
	public void writeLinkagePath(){
		Excel excelmb = new Excel(filePath, fileName, sheetName);
		Excel excel = new Excel(filePath, fileName2, sheetName);
		ConfiguredLinkagePath.writeLinkagePath(excelmb,excel);
		System.out.println("ok");
	}
	
	@Test
	public void writeSchemaOfThreeGroups(){
		Excel excel = new Excel(filePath, fileName2, sheetName2);
		ConfiguredLinkagePath.writeSchemaOfThreeGroups(excel,"Imageological_examination");
		System.out.println("ok");
	}
	
	@Test
	public void writeSchemaOfTwoGroups(){
		Excel excel = new Excel(filePath, fileName2, sheetName2);
		ConfiguredLinkagePath.writeSchemaOfTwoGroups(excel,"patient_info");
		System.out.println("ok");
	}
	
}