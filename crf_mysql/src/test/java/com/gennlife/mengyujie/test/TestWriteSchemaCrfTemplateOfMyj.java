package com.gennlife.mengyujie.test;

import org.junit.Test;

import com.gennlife.crf.bean.Excel;
import com.gennlife.mengyujie.WriteSchemaCrfTemplateOfMyj;

public class TestWriteSchemaCrfTemplateOfMyj {

	private String filePath = "E:\\yujie\\2";
	private String fileName = "模板结构-乳腺癌.xlsx";
	private String sheetName = "总体结构";
	
	
	private String fileName2 = "test.xlsx";
	private String sheetName2 = "患者信息";
	
	@Test
	public void writeSchemaOfTwoGroups(){
		Excel excel = new Excel(filePath, fileName2, sheetName2);
		
		WriteSchemaCrfTemplateOfMyj.writeSchemaOfTwoGroups(excel,"patient_info");
		
		System.out.println("ok");
	}
	
	@Test
	public void writeSchema(){
		Excel excelmb = new Excel(filePath, fileName, sheetName);
		Excel excel = new Excel(filePath, fileName2, sheetName);
		
		WriteSchemaCrfTemplateOfMyj.writeSchema(excelmb,excel);
		
		System.out.println("ok");
	}
	
	
	
}