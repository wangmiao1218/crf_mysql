package com.gennlife.crf.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.BSONObject;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.gennlife.crf.utils.MongodbJDBCUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.client.MongoCollection;

/**
 * @Description: 天津肿瘤mongodb的数据处理（crfdata表）
 * @author: wangmiao
 * @Date: 2017年12月18日 下午2:41:11 
 */
public class TianjinMongodbDataProcess {

	/**
	* @Title: insertDatasIntoPatientDetailMongodb 
	* @Description: 天津数据库，批量插入json
	* @param: @param listJsons :
	* @return: void
	* @throws 
	*/
	
	public static String queryDatasOfCrfdataMongodb(String pat,String crfdataContent) throws JSONException {
		//连接数据库
		DBCollection dbCollection = MongodbJDBCUtils.connectTianjinMongodbCrfdataReturnDBCollection();
		
		//大的查询条件
		BasicDBObject query = new BasicDBObject();  
		query.put("data.patient_info.PATIENT_BASICINFO.PATIENT_SN.value",pat);
		
		//只查询该值
		BasicDBObject edit = new BasicDBObject();
		edit.put(crfdataContent,"");
		
		DBCursor cursor=dbCollection.find(query,edit);
		//将想要的返回数据转成String，方便后期处理
        String returnStr =null;
        try {
            while (cursor.hasNext()) {
            	BSONObject dbObject = cursor.next();
            	dbObject= (BSONObject) ((BSONObject) dbObject.get("data")).get("visits");
            	System.out.println(dbObject.toString());
            	
            	//returnStr=cursor.next().toString();
            }
        } finally {
            cursor.close();
        }
       /* 
        //将returnStr字符串转换成json对象:JSONObject
        com.alibaba.fastjson.JSONObject jsonObject=(com.alibaba.fastjson.JSONObject) JSON.parse(returnStr);
        jsonObject= (com.alibaba.fastjson.JSONObject)jsonObject.get("data");
        System.out.println(jsonObject);
        jsonObject = (com.alibaba.fastjson.JSONObject)jsonObject.getJSONArray("visits").get(0);
      
        System.out.println(jsonObject);
          */
        
		return returnStr;  
	}
	
	
	/** 
	 * @Title: insertDatasIntoPatientDetailMongodb 
	 * @Description: 天津数据库，批量插入json
	 * @param: @param listJsons 
	 * @return: void
	 * @throws 
	 */
	public static void insertDatasIntoPatientDetailMongodb(List<JSONObject> listJsons) {
		//连接数据库
		MongoCollection<Document> mongoCollection = MongodbJDBCUtils.connectTianjinMongodbPatientDetailReturnMongoCollection();
		//转换
		List<Document> documents = new ArrayList<Document>();
		for (int i = 0; i < listJsons.size(); i++) {
			Document document = Document.parse(listJsons.get(i).toString());
			documents.add(document);
		}
		//插入时，可检查pat是否存在，存在则删除（后续加）
		//id重复也报错
		//mongoCollection.insertMany(documents, new InsertManyOptions().ordered(false));
		mongoCollection.insertMany(documents);
		
		System.out.println("插入数据完成。"+"insert "+listJsons.size()+" successfully");
	}
	
}
