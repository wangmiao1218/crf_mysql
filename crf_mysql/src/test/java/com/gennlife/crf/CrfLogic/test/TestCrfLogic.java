package com.gennlife.crf.CrfLogic.test;

import org.json.JSONException;
import org.junit.Test;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.crfLogic.CrfLogic;

public class TestCrfLogic {

	private String filePath = "E:\\江苏肿瘤\\测试数据";
	private String fileName = "2test.xlsx";
	private String sheetName = "Sheet1";
	private String path = "E:\\江苏肿瘤\\测试数据\\all_info.json";
	private String mongodbIp = "10.0.2.185";
	private String auto = "http://10.0.2.184:6060/auto/ManualEMRAutoCRFV2";
	private String disease = "lymphoma_test";
	
	@Test
	public void insertDatasIntoPatientDetailAndPostAndWritePatIntoExcel() throws Exception{
		Excel excel = new Excel(filePath,fileName,sheetName);
		CrfLogic.insertDatasIntoPatientDetailAndPostAndWritePatIntoExcel(excel, 
				path, mongodbIp, auto, disease);
	}
	
	@Test
	public void queryCrfdataByPatAndWriteResults() throws JSONException{
		Excel excel = new Excel(filePath,fileName,sheetName);
		CrfLogic.queryCrfdataByPatAndWriteResults(excel, mongodbIp);
	}
	
}