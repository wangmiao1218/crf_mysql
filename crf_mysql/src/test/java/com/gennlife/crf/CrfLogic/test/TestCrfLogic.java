package com.gennlife.crf.CrfLogic.test;

import org.json.JSONException;
import org.junit.Test;
import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.crfLogic.CrfLogic;

public class TestCrfLogic {

	private String filePath = "E:\\CRFLogic\\test";
	private String fileName = "test_2_1.xlsx";
	private String sheetName = "Sheet1";
	private String path = "E:\\CRFLogic\\test\\all_info.json";
	private String mongodbIp = "10.0.2.185";
	private String auto = "http://10.0.2.184:6060/auto/ManualEMRAutoCRFV2";
	private String disease = "lymphoma";
	
	@Test
	public void insertDatasIntoPatientDetailAndPostAndWritePatIntoExcel() throws JSONException{
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