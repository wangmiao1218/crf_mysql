package com.gennlife.crf.CrfLogic.test;

import org.json.JSONException;
import org.junit.Test;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.crfLogic.CrfLogic;

public class TestCrfLogic {

	private String filePath = "E:\\CRFLogic\\test2";
	private String fileName = "2test.xlsx";
	private String sheetName = "Sheet1";
	private String path = "E:\\CRFLogic\\test2\\all_info.json";
	private String mongodbIp = "10.0.2.185";
	private String auto = "http://10.0.2.184:6060/auto/ManualEMRAutoCRFV2";
	private String disease = "lymphoma_test";
	//private String disease = "lymphoma_release_1.0";
	
	@Test
	public void insertDatasIntoPatientDetailAndPostAndWritePatIntoExcel() throws Exception{
		Excel excel = new Excel(filePath,fileName,sheetName);
		//CrfLogic.insertDatasIntoPatientDetailAndPostAndWritePatIntoExcel(excel, path, mongodbIp, auto, disease);
		
		CrfLogic.addFirstPat_insertDatasIntoPatientDetailAndPostAndWritePatIntoExcel(excel, 
				path, mongodbIp, auto, disease);
	}
	
	@Test
	public void queryCrfdataByPatAndWriteResults() throws JSONException{
		Excel excel = new Excel(filePath,fileName,sheetName);
		CrfLogic.queryCrfdataByPatAndWriteResults(excel, mongodbIp);
	}
	
}