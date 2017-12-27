package com.gennlife.crf.mongodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	* @Title: queryDatasOfCrfdataMongodb 
	* @Description: 根据传入的mapMap传入到数据库进行查询，返回map封装行号和查询结果
	* @param: @param rowNumAndpatCrfdataMapMap
	* @param: @return
	* @param: @throws JSONException 
	* @return: Map<Integer,org.bson.BSONObject>
	* @throws 
	*/
	public static Map<Integer,org.bson.BSONObject> queryDatasOfCrfdataMongodb(Map<Integer,Map<String, String>> rowNumAndpatCrfdataMapMap) throws JSONException {
		if (!rowNumAndpatCrfdataMapMap.isEmpty()) {
			//定义返回结果
			Map<Integer,org.bson.BSONObject> rowNumAndQueryJsonMap = new HashMap<Integer,org.bson.BSONObject>();
			//连接数据库
			DBCollection dbCollection = MongodbJDBCUtils.connectTianjinMongodbCrfdataReturnDBCollection();
			//rowNumAndpatCrfdataMapMap
			for (Entry<Integer, Map<String, String>>  mapMap : rowNumAndpatCrfdataMapMap.entrySet()) {
				//行号
				Integer rowNum=mapMap.getKey();
				//需要查询的两个变量
				Map<String, String> patCrfdataMap = mapMap.getValue();
				
				//解析patCrfdataMap
				String pat = null;
				String crfdata = null;
				for (Entry<String, String> map: patCrfdataMap.entrySet()) {  
					pat = map.getKey();
					crfdata = map.getValue();
				}
				//大的查询条件
				BasicDBObject queryCondition = new BasicDBObject();  
				queryCondition.put("data.patient_info.PATIENT_BASICINFO.PATIENT_SN.value",pat);
				//查询后，值显示该查询字段的值
				BasicDBObject returnField = new BasicDBObject();
				returnField.put(crfdata,"");
				DBCursor cursor=dbCollection.find(queryCondition,returnField);
				//将想要的返回数据转成JSONObject，放入map中
				org.bson.BSONObject returnJsonObject = null;
		        try {
		            while (cursor.hasNext()) {
		            	org.bson.BSONObject jsonObject = (org.bson.BSONObject) cursor.next();
		            	//方便后续解析
		            	//jsonObject=(JSONObject) ((JSONObject) jsonObject.get("data")).getJSONArray("visits").get(0);
		            	//System.out.println(jsonObject);
		            	returnJsonObject=jsonObject;
		            }
		        } finally {
		            cursor.close();
		        }
		        rowNumAndQueryJsonMap.put(rowNum, returnJsonObject);
			}
			
			return rowNumAndQueryJsonMap;  
		}else {
			return null;
		}
	}
	
	
	/** 
	 * @Title: insertDatasIntoPatientDetailMongodb 
	 * @Description: 天津数据库，批量插入json
	 * @param: @param listJsons 
	 * @return: void
	 * @throws 
	 */
	public static void insertDatasIntoPatientDetailMongodb(List<Map<String, JSONObject>> listMapJsons) {
		//连接数据库
		MongoCollection<Document> mongoCollection = MongodbJDBCUtils.connectTianjinMongodbPatientDetailReturnMongoCollection();
		//转换
		List<Document> documents = new ArrayList<Document>();
		for (int i = 0; i < listMapJsons.size(); i++) {
			Map<String, JSONObject> map = listMapJsons.get(i);
			//由于封装的map只有一个值，所以不在下面for循环
			String pat=null;
			JSONObject json=null;
			for (Entry<String, JSONObject> entry: map.entrySet()) {  
				pat = entry.getKey();
				json = entry.getValue();
			}
			//删除//插入时，直接删除原有的pat
			BasicDBObject queryCondition = new BasicDBObject();  
			queryCondition.put("patient_info.patient_info_patient_sn",pat);
			mongoCollection.deleteOne(queryCondition);
			
			//放入到documents，方便批量插入
			Document document = Document.parse(json.toString());
			documents.add(document);
		}
		
		//批量插入
		mongoCollection.insertMany(documents);
		
		System.out.println("插入数据完成。"+"insert "+listMapJsons.size()+" successfully");
	}
	
}
