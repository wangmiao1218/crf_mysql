package com.gennlife.crf.utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * @Description: 连接mysql
 * @author: wangmiao
 * @Date: 2017年12月18日 下午4:59:57 
 */
public class MysqlJDBCUtils {

	/**
	* @Title: connectTianjinMysqlReturnMysqlCollection 
	* @Description: 连接天津mysql数据库,返回MongoCollection<Document>
	* @param: @return :
	* @return: MongoCollection<Document> 返回 MongoCollection<Document>
	* @throws SQLException
	*/
	public static void connectTianjinMysqlReturnMysqlCollection() throws SQLException {
		Connection conn = null;
        String sql;
        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
        // 下面语句之前就要先创建javademo数据库
        String url = "jdbc:mysql://10.0.2.238:9003/gennlife_tjzl?"
                + "user=gennlife_tjzl_user&password=@Gennlife_tjzl2015&useUnicode=true&characterEncoding=UTF8";
        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            // or:
            // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
            // or：
            // new com.mysql.jdbc.Driver();
            System.out.println("成功加载MySQL驱动程序");
            // 一个Connection代表一个数据库连接
            conn = (Connection) DriverManager.getConnection(url);
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = (Statement) conn.createStatement();
            
            sql = "select PATIENT_SN,DISCUSSION_CONTENT from operation_pre_conference_records where PATIENT_SN=\"4200108167133570555\"";
            ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
            System.out.println(rs);
            while (rs.next()) {
                System.out.println(rs.getString(1));// 入如果返回的是int类型可以用getInt()
                System.out.println(rs.getString(2));// 入如果返回的是int类型可以用getInt()
            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
 
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
