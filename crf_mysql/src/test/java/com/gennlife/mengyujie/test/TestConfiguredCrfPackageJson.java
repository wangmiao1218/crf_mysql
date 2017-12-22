package com.gennlife.mengyujie.test;

import org.junit.Test;

import com.gennlife.crf.bean.Excel;
import com.gennlife.myujie.ConfiguredCrfPackageJson;
import com.gennlife.myujie.ConfiguredLinkagePath;

public class TestConfiguredCrfPackageJson {

	private String filePath = "E:\\CRF重组\\自动工具\\工具";
	private String fileName = "模版样例.xlsx";
	private String sheetName = "Sheet1";
	
	@Test
	public void getJsonFile(){
		Excel excel = new Excel(filePath, fileName, sheetName);
		ConfiguredCrfPackageJson.getJsonFile(excel);
		System.out.println("ok");
	}
}