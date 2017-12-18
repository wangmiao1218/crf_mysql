package com.gennlife.crf.mongodb.test;

import org.bson.BSONObject;
import org.bson.Document;
import org.junit.Test;

import com.gennlife.crf.utils.MongodbJDBCUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class TestMongodbJDBCUtils {


	@Test
	public void connectTianjinMongodbReturnDBCollection() {
		DBCollection dbCollection = MongodbJDBCUtils.connectTianjinMongodbReturnDBCollection();
		
		//大的查询条件
		BasicDBObject query = new BasicDBObject();  
		query.put("data.patient_info.PATIENT_BASICINFO.PATIENT_SN.value","pat_661d40bd9532646310f9be2f92fc949a"); 
		/*
		//数组内查询条件
		BasicDBObject edit = new BasicDBObject();
		edit.put("visits", new BasicDBObject("$elemMatch", 
				new BasicDBObject("type.visit_info_admission_date","2016-08-05 10:59:00")
				.append("type.visit_info_admission_dept","神经内科")));
		
        DBCursor cursor=dbCollection.find(query,edit);
        */
        DBCursor cursor=dbCollection.find(query);
        try {
            while (cursor.hasNext()) {
            	BSONObject dbObject = cursor.next();
            	System.out.println(dbObject);
            }
        } finally {
            cursor.close();
        }
	}
	
	
	@Test
	public void connectYantaiMongodbReturnMongoCollection() {
		MongoCollection<Document> mongoCollection = MongodbJDBCUtils.connectYantaiMongodbReturnMongoCollection();
		//大的查询条件
		BasicDBObject query = new BasicDBObject();  
		query.put("patient_info.patient_info_patient_sn","pat_60fc1ba93ac2f03de9f2206e38b52669"); 
		
		//数组内查询条件
		BasicDBObject edit = new BasicDBObject();
		edit.put("type.visit_info_admission_date","2016-08-05 10:59:00");
		edit.put("type.visit_info_admission_dept","神经内科");
		
		query.put("visits", new BasicDBObject("$elemMatch",edit));
		
		FindIterable<Document> iterable = mongoCollection.find(query);
		
		iterable.forEach(new Block<Document>() { 
			@Override public void apply(final Document document) { 
				//System.out.println(document); 
				System.out.println(document.toJson());
			}
		});
		
	}
	
	
	@Test
	public void connectYantaiMongodbReturnDBCollection() {
		DBCollection dbCollection = MongodbJDBCUtils.connectYantaiMongodbReturnDBCollection();
		
		//大的查询条件
		BasicDBObject query = new BasicDBObject();  
		query.put("patient_info.patient_info_patient_sn","pat_60fc1ba93ac2f03de9f2206e38b52669"); 
		
		//数组内查询条件
		BasicDBObject edit = new BasicDBObject();
		edit.put("visits", new BasicDBObject("$elemMatch", 
				new BasicDBObject("type.visit_info_admission_date","2016-08-05 10:59:00")
				.append("type.visit_info_admission_dept","神经内科")));
		
        DBCursor cursor=dbCollection.find(query,edit);
        try {
            while (cursor.hasNext()) {
            	BSONObject dbObject = cursor.next();
            	System.out.println(dbObject);
            }
        } finally {
            cursor.close();
        }
	}
	
	
}
