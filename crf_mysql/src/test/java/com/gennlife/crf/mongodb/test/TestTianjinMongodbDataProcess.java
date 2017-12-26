package com.gennlife.crf.mongodb.test;

import org.json.JSONException;
import org.junit.Test;

import com.gennlife.crf.mongodb.TianjinMongodbDataProcess;
import com.google.gson.JsonObject;

public class TestTianjinMongodbDataProcess {

	
	@Test
	public void queryDatasOfCrfdataMongodb() throws JSONException {
		String pat="pat_7";
		String crfdataContent="data.visits.inpatientDetails.IP_PE.IP_HEIGHT.value";
		String str = TianjinMongodbDataProcess.queryDatasOfCrfdataMongodb(pat,crfdataContent);
		
		System.out.println(str);
		
	}
	
	
}
