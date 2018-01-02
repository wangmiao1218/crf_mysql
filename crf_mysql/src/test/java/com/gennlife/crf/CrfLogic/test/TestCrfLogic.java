package com.gennlife.crf.CrfLogic.test;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.crfLogic.CrfLogic;
import com.gennlife.crf.mongodb.CrfdataOrPatientDetailMongodbDataProcess;
import com.gennlife.interfaces.ManualEMRAutoCRFV2OfCrfAutoInterface;

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