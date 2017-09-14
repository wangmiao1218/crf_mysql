package com.gennlife.crf.yantai.mongodb.test;

import org.bson.BSONObject;
import org.bson.Document;
import org.junit.Test;

import com.gennlife.crf.utils.MongoDBJDBC;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestMongoDBJDBC {

	@Test
	public void testMongoDBConnection() {
		MongoDatabase yantaiMongodb = MongoDBJDBC.ConnectYantaiMongodb();
		MongoCollection<Document> collection = yantaiMongodb.getCollection("patientDetail");
		System.out.println("集合 patientDetail 选择成功");
		
		//打的查询条件
		BasicDBObject query = new BasicDBObject();  
		query.put("patient_info.patient_info_patient_sn","pat_60fc1ba93ac2f03de9f2206e38b52669"); 
		
		//数组内查询条件
		BasicDBObject edit = new BasicDBObject();
		edit.put("type.visit_info_admission_date","2016-08-05 10:59:00");
		edit.put("type.visit_info_admission_dept","神经内科");
		
		query.put("visits", new BasicDBObject("$elemMatch", edit));
		
		//query.put("visits", 1);

		
		FindIterable<Document> iterable = collection.find(query);
		iterable.forEach(new Block<Document>() { 
			@Override public void apply(final Document document) { 
				//System.out.println(document); 
				System.out.println(document.toJson());
			}
		});
		
	}
	
	
	@Test
	public void ConnectYantaiMongo() {
		DB db = MongoDBJDBC.ConnectYantaiMongo();
		DBCollection collection = db.getCollection("patientDetail");
		
        BasicDBObject query = new BasicDBObject();  
        query.put("patient_info.patient_info_patient_sn","pat_60fc1ba93ac2f03de9f2206e38b52669"); 

        DBCursor cursor = collection.find(query);   
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
