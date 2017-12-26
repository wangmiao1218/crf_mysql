package com.gennlife.crf.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * @Description: 连接mongodb
 * @author: wangmiao
 * @Date: 2017年9月14日 下午4:27:55
 */
public class MongodbJDBCUtils {


	/** 
	* @Title: connectTianjinMongodbPatientDetailReturnMongoCollection 
	* @Description: 连接天津测试环境mongodb数据库（PatientDetail）,返回MongoCollection<Document>
	* @param: @return :
	* @return: MongoCollection<Document> 返回 MongoCollection<Document>
	* @throws 
	*/
	public static MongoCollection<Document> connectTianjinMongodbPatientDetailReturnMongoCollection() {
		MongoCollection<Document> mongoCollection = null;
		try {
			// 连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
			// ServerAddress()两个参数分别为 服务器地址 和 端口
			ServerAddress serverAddress = new ServerAddress("10.0.2.176", 27017);
			List<ServerAddress> addrs = new ArrayList<ServerAddress>();
			addrs.add(serverAddress);

			// MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
			MongoCredential credential = MongoCredential.createScramSha1Credential("Wangmiao", "CRF_Model","@Wangmiao2015".toCharArray());
			List<MongoCredential> credentials = new ArrayList<MongoCredential>();
			credentials.add(credential);

			// 通过连接认证获取MongoDB连接
			MongoClient mongoClient = new MongoClient(addrs, credentials);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("CRF_Model");
			System.out.println("Connect to database successfully");
			
			//获取集合
			mongoCollection = mongoDatabase.getCollection("patientDetail");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return mongoCollection;

	}
	
	/** 
	 * @Title: connectTianjinMongodbCrfdataReturnDBCollection （crfdata库）
	 * @Description: 连接天津测试环境mongodb数据库,返回DBCollection
	 * (官方文档和源代码均建议使用MongoClient类，而且，在不久的将来，会废弃Mongo类。)
	 * @param: @return :
	 * @return: DBCollection
	 * @throws 
	 */
	public static DBCollection connectTianjinMongodbCrfdataReturnDBCollection() {
		DBCollection dbCollection =null;
		try {
			MongoCredential credential = MongoCredential.createCredential("Wangmiao", "CRF_Model", "@Wangmiao2015".toCharArray()); 
			ServerAddress serverAddress = new ServerAddress("10.0.2.176", 27017);
			MongoClient mongoClient = new MongoClient(serverAddress, Arrays.asList(credential)); 
			
			// 连接到数据库
			DB db = mongoClient.getDB("CRF_Model");
			System.out.println("Connect to database successfully");
			
			//获取crfdata集合
			dbCollection = db.getCollection("crfdata");
			
		}catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return dbCollection;
	}
	
	
	/** 
	* @Title: connectYantaiMongodbReturnMongoCollection 
	* @Description: 连接烟台测试环境mongodb数据库,返回MongoCollection<Document>
	* @param: @return :
	* @return: MongoCollection<Document> 返回 MongoCollection<Document>
	* @throws 
	*/
	public static MongoCollection<Document> connectYantaiMongodbReturnMongoCollection() {
		MongoCollection<Document> mongoCollection = null;
		try {
			// 连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
			// ServerAddress()两个参数分别为 服务器地址 和 端口
			ServerAddress serverAddress = new ServerAddress("10.0.2.171", 27017);
			List<ServerAddress> addrs = new ArrayList<ServerAddress>();
			addrs.add(serverAddress);

			// MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
			MongoCredential credential = MongoCredential.createScramSha1Credential("wangmiao", "CRF_Model","@wangmiao2015".toCharArray());
			List<MongoCredential> credentials = new ArrayList<MongoCredential>();
			credentials.add(credential);

			// 通过连接认证获取MongoDB连接
			MongoClient mongoClient = new MongoClient(addrs, credentials);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("CRF_Model");
			System.out.println("Connect to database successfully");
			
			//获取集合
			mongoCollection = mongoDatabase.getCollection("patientDetail");
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return mongoCollection;

	}
	
	/** 
	* @Title: connectYantaiMongodbReturnDBCollection 
	* @Description: 连接烟台测试环境mongodb数据库,返回DBCollection
	* (官方文档和源代码均建议使用MongoClient类，而且，在不久的将来，会废弃Mongo类。)
	* @param: @return :
	* @return: DBCollection
	* @throws 
	*/
	public static DBCollection connectYantaiMongodbReturnDBCollection() {
		DBCollection dbCollection =null;
		try {
			MongoCredential credential = MongoCredential.createCredential("wangmiao", "CRF_Model", "@wangmiao2015".toCharArray()); 
			ServerAddress serverAddress = new ServerAddress("10.0.2.171", 27017);
			MongoClient mongoClient = new MongoClient(serverAddress, Arrays.asList(credential)); 
	
			// 连接到数据库
			DB db = mongoClient.getDB("CRF_Model");
			System.out.println("Connect to database successfully");
			
			//获取集合
			dbCollection = db.getCollection("patientDetail");
			
		}catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return dbCollection;
	}
	
}
