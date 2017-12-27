package com.gennlife.crf.CrfLogic.test;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.gennlife.crf.CrfLogic.CrfLogic;
import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.mongodb.TianjinMongodbDataProcess;
import com.gennlife.interfaces.ManualEMRAutoCRFV2OfCrfAutoInterface;

public class TestCrfLogic {

	private String filePath = "E:\\CRFLogic\\test";
	//private String filePath = "D:\\我的文档\\Desktop\\json";
	private String fileName = "测试字段.xlsx";
	private String sheetName = "Sheet2";
	private String path = "E:\\CRFLogic\\test\\New1.json";
	

	@Test
	public void queryCrfdataByPatAndWriteResults() throws JSONException{
		Excel excel = new Excel(filePath,fileName,sheetName);
		CrfLogic.queryCrfdataByPatAndWriteResults(excel);
	}
	
	
	@Test
	public void insertDatasIntoPatientDetailAndPostAndWritePatIntoExcel() throws JSONException{
		Excel excel = new Excel(filePath,fileName,sheetName);
		CrfLogic.insertDatasIntoPatientDetailAndPostAndWritePatIntoExcel(excel, path);
	}
	
	
	//测试
	@Test
	public void readExcelReturnJsonMapList() throws JSONException{
		Excel excel = new Excel(filePath,fileName,sheetName);
		List<Map<String, JSONObject>> list = CrfLogic.readExcelReturnJsonMapList(excel, path);
		TianjinMongodbDataProcess.insertDatasIntoPatientDetailMongodb(list);
		System.out.println("插入数据end。。。");
	}
	
	
}