package com.gennlife.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.gennlife.crf.utils.JsonUtils;


/**
 * @Description: rws相关接口
 * @author: wangmiao
 * @Date: 2018年8月17日 下午1:47:28 
 */
public class RwsInterface {

	private static Logger logger = Logger.getLogger(RwsInterface.class); 
	
	/** 
	* @Title: doGet 
	* @Description: 通过get请求，获取rws相关功能
	* @author: wangmiao
	* @Date: 2018年8月17日 下午1:47:47 
	* @param: @param url
	* @param: @return
	* @param: @throws IOException
	* @return: String
	* @throws 
	*/
	public static String doGet(String url) throws IOException {
		// 使用默认配置的httpclient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// get形式的访问
		HttpGet httpGet = new HttpGet(url);
		// 执行请求
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = httpClient.execute(httpGet);
			// 打印请求的状态码 请求成功为200
			logger.info("请求的状态码:"+response.getStatusLine().getStatusCode());
			// 打印请求的实体内容 返回json格式
			entity = response.getEntity();
			
			// 获取所有头信息 
			Header[] allHeaders = response.getAllHeaders(); 
			for(Header allHeader : allHeaders) {
				logger.info(allHeader.getName());
				logger.info(allHeader.getValue());
				logger.info(allHeader.toString());
			}
			 
			// 方法一 官方不推荐
			if (entity != null) {
				// 输出更详细的抓取内容(html格式)
				return EntityUtils.toString(entity, "utf-8");
			}
			
			/*
			// 方法二 官方推荐 使用流的形式处理请求结果
			if (entity != null) {
				InputStream content = entity.getContent();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					System.out.println(line);
				}
				bufferedReader.close();
			}
			*/
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			response.close();
		}
		return EntityUtils.toString(entity, "utf-8");
	}

	
	
	
	
	public static String doPost(String strURL,String sessionId) throws Exception {
		//读取基础文本文件，并转为json
		org.json.JSONObject params = JsonUtils.readFileContentReturnJson("C:\\Users\\www\\Desktop\\rws.json");
		
		try {
			// 创建连接
			URL url = new URL(strURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			// 设置请求方式
			connection.setRequestMethod("POST");
			// 设置接收数据的格式
			connection.setRequestProperty("Accept", "application/json");
			// 设置发送数据的格式
			connection.setRequestProperty("Content-Type", "application/json");
			//connection.setRequestProperty("session", sessionId);
			
			connection.connect();
			// utf-8编码
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			// 发送给接口的json格式内容
			out.append(params.toString());
			out.flush();
			out.close();
			// 读取响应
			// 获取长度
			int length = (int) connection.getContentLength();
			InputStream is = connection.getInputStream();
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				// utf-8编码
				String result = new String(data, "UTF-8");
				System.out.println(result);
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 自定义错误信息
		return "error";
	}
	
	
	
	public static String test001(String sessionId) {
		String strURL = "http://10.0.2.162/uranus/UI/rws/SaveOrSearchActive";
		//String params = "{\"UserId\":\"\",\"Uuids\":[" + patStrs
		//		+ "],\"Source\":[\"PatiName\",\"IDCard\",\"InPatientSn\"]}";
		
		String params="{\"active\":{\"isTmp\":0,\"dataGroup\":[\"patient_info\",\"visit_info\","
				+ "\"inspection_reports\"],\"sortKey\":\"visits.inspection_reports.REPORT_TIME\","
				+ "\"activeType\":1,\"confirmActiveId\":\"A6255F1B1034469A80C958075FAF446C\","
				+ "\"createTime\":1534471760000,\"name\":\"test\",\"updateTime\":1534489890000,"
				+ "\"id\":\"A52D44479F1343B8B2ED13BF91222352\",\"projectName\":\"稳定性\","
				+ "\"config\":[{\"activeIndexId\":\"A52D44479F1343B8B2ED13BF91222352\","
				+ "\"indexTypeDesc\":\"\",\"dateFormat\":\"\",\"searchScope\":\"\","
				+ "\"operator\":\"all\",\"activeResult\":\"visits.inspection_reports\","
				+ "\"indexType\":\"\",\"operatorNum\":\"\",\"indexColumn\":\"\","
				+ "\"indexColumnDesc\":\"\",\"indexResultValue\":\"\",\"function\":\"\","
				+ "\"activeResultDesc\":\"就诊.检验报告\",\"functionParam\":\"\","
				+ "\"id\":\"F77CAE17CC4445429D51F5FE175E6F53\",\"conditions\":[{\"level\":1,"
				+ "\"operatorSign\":\"and\",\"strongRef\":[],\"details\":[{\"operatorSign\":\"simpleDate#<\","
				+ "\"sourceTagName\":\"visits.visit_info.ADMISSION_DATE\",\"logicSing\":\"\","
				+ "\"operatorSignDesc\":\"早于\",\"strongRef\":[],\"targetTagName\":\"\","
				+ "\"targetTagNameDesc\":\"\",\"activeIndexConfigId\":\"F77CAE17CC4445429D51F5FE175E6F53\","
				+ "\"needPath\":\".\",\"refRelation\":\"direct\",\"type\":2,\"inner\":[],"
				+ "\"parentId\":\"6B1DFABBB0694CD5B0BC94EA4FDE449D\",\"refActiveId\":\"\","
				+ "\"sourceTagNameDesc\":\"就诊.就诊基本信息.入院（就诊）时间\",\"refActiveName\":\"\","
				+ "\"details\":[],\"detail\":[],\"id\":\"64973E8958DD4BB1982E3B2EAD1D44C9\","
				+ "\"value\":[\"2018-08-17 15:11:24\"]}],\"detail\":[{\"operatorSign\":\"simpleDate#<\","
				+ "\"sourceTagName\":\"visits.visit_info.ADMISSION_DATE\",\"logicSing\":\"\","
				+ "\"operatorSignDesc\":\"早于\",\"strongRef\":[],\"targetTagName\":\"\","
				+ "\"targetTagNameDesc\":\"\",\"activeIndexConfigId\":\"F77CAE17CC4445429D51F5FE175E6F53\","
				+ "\"needPath\":\".\",\"refRelation\":\"direct\",\"type\":2,\"inner\":[],"
				+ "\"parentId\":\"6B1DFABBB0694CD5B0BC94EA4FDE449D\",\"refActiveId\":\"\","
				+ "\"sourceTagNameDesc\":\"就诊.就诊基本信息.入院（就诊）时间\",\"refActiveName\":\"\","
				+ "\"details\":[],\"detail\":[],\"id\":\"64973E8958DD4BB1982E3B2EAD1D44C9\","
				+ "\"value\":[\"2018-08-17 15:11:24\"]}],\"id\":\"6B1DFABBB0694CD5B0BC94EA4FDE449D\","
				+ "\"activeIndexConfigId\":\"F77CAE17CC4445429D51F5FE175E6F53\",\"needPath\":\".\","
				+ "\"type\":1,\"inner\":[],\"uuid\":\"7beef6ea-3d08-46ae-b8c3-5d4fe2a6a33c\"}],"
				+ "\"indexResultValueIsEqual\":\"1\"}],\"projectId\":\"c5d9cb4d-1c53-40a9-8f3b-76373501ba7e\"},"
				+ "\"crfId\":\"EMR\",\"isSearch\":0}";
		
		try {
			// 创建连接
			URL url = new URL(strURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			// 设置请求方式
			connection.setRequestMethod("POST");
			// 设置接收数据的格式
			connection.setRequestProperty("Accept", "application/json");
			// 设置发送数据的格式
			connection.setRequestProperty("Content-Type", "application/json");
			connection.connect();
			// utf-8编码
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			// 发送给接口的json格式内容
			out.append(params);
			out.flush();
			out.close();
			// 读取响应
			// 获取长度
			int length = (int) connection.getContentLength();
			InputStream is = connection.getInputStream();
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				// utf-8编码
				String result = new String(data, "UTF-8");
				// System.out.println(result);
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 自定义错误信息
		return "error";
	}
	
	
	

	
	public static String rws(String url, String sessionId) throws JSONException {
		//读取基础文本文件，并转为json
		JSONObject json = JsonUtils.readFileContentReturnJson("C:\\Users\\www\\Desktop\\rws.json");
		
		String params="{\"active\":{\"isTmp\":0,\"dataGroup\":[\"patient_info\",\"visit_info\","
				+ "\"inspection_reports\"],\"sortKey\":\"visits.inspection_reports.REPORT_TIME\","
				+ "\"activeType\":1,\"confirmActiveId\":\"A6255F1B1034469A80C958075FAF446C\","
				+ "\"createTime\":1534471760000,\"name\":\"test\",\"updateTime\":1534489890000,"
				+ "\"id\":\"A52D44479F1343B8B2ED13BF91222352\",\"projectName\":\"稳定性\","
				+ "\"config\":[{\"activeIndexId\":\"A52D44479F1343B8B2ED13BF91222352\","
				+ "\"indexTypeDesc\":\"\",\"dateFormat\":\"\",\"searchScope\":\"\","
				+ "\"operator\":\"all\",\"activeResult\":\"visits.inspection_reports\","
				+ "\"indexType\":\"\",\"operatorNum\":\"\",\"indexColumn\":\"\","
				+ "\"indexColumnDesc\":\"\",\"indexResultValue\":\"\",\"function\":\"\","
				+ "\"activeResultDesc\":\"就诊.检验报告\",\"functionParam\":\"\","
				+ "\"id\":\"F77CAE17CC4445429D51F5FE175E6F53\",\"conditions\":[{\"level\":1,"
				+ "\"operatorSign\":\"and\",\"strongRef\":[],\"details\":[{\"operatorSign\":\"simpleDate#<\","
				+ "\"sourceTagName\":\"visits.visit_info.ADMISSION_DATE\",\"logicSing\":\"\","
				+ "\"operatorSignDesc\":\"早于\",\"strongRef\":[],\"targetTagName\":\"\","
				+ "\"targetTagNameDesc\":\"\",\"activeIndexConfigId\":\"F77CAE17CC4445429D51F5FE175E6F53\","
				+ "\"needPath\":\".\",\"refRelation\":\"direct\",\"type\":2,\"inner\":[],"
				+ "\"parentId\":\"6B1DFABBB0694CD5B0BC94EA4FDE449D\",\"refActiveId\":\"\","
				+ "\"sourceTagNameDesc\":\"就诊.就诊基本信息.入院（就诊）时间\",\"refActiveName\":\"\","
				+ "\"details\":[],\"detail\":[],\"id\":\"64973E8958DD4BB1982E3B2EAD1D44C9\","
				+ "\"value\":[\"2018-08-17 15:11:24\"]}],\"detail\":[{\"operatorSign\":\"simpleDate#<\","
				+ "\"sourceTagName\":\"visits.visit_info.ADMISSION_DATE\",\"logicSing\":\"\","
				+ "\"operatorSignDesc\":\"早于\",\"strongRef\":[],\"targetTagName\":\"\","
				+ "\"targetTagNameDesc\":\"\",\"activeIndexConfigId\":\"F77CAE17CC4445429D51F5FE175E6F53\","
				+ "\"needPath\":\".\",\"refRelation\":\"direct\",\"type\":2,\"inner\":[],"
				+ "\"parentId\":\"6B1DFABBB0694CD5B0BC94EA4FDE449D\",\"refActiveId\":\"\","
				+ "\"sourceTagNameDesc\":\"就诊.就诊基本信息.入院（就诊）时间\",\"refActiveName\":\"\","
				+ "\"details\":[],\"detail\":[],\"id\":\"64973E8958DD4BB1982E3B2EAD1D44C9\","
				+ "\"value\":[\"2018-08-17 15:11:24\"]}],\"id\":\"6B1DFABBB0694CD5B0BC94EA4FDE449D\","
				+ "\"activeIndexConfigId\":\"F77CAE17CC4445429D51F5FE175E6F53\",\"needPath\":\".\","
				+ "\"type\":1,\"inner\":[],\"uuid\":\"7beef6ea-3d08-46ae-b8c3-5d4fe2a6a33c\"}],"
				+ "\"indexResultValueIsEqual\":\"1\"}],\"projectId\":\"c5d9cb4d-1c53-40a9-8f3b-76373501ba7e\"},"
				+ "\"crfId\":\"EMR\",\"isSearch\":0}";
		
		
		System.out.println(json);
		//System.out.println(params);
		// 创建默认的httpClient实例
		CloseableHttpClient httpClient = null;
		
		HttpPost httpPost = null;
		// 定义返回内容
		String responses = null;
		try {
			httpClient = HttpClients.createDefault();
			//设置表头
			RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(sessionId).build();
			// 设置连接超时时间和获取数据超时时间
			/**
			 * RequestConfig requestConfig = RequestConfig.custom()
			 * .setConnectTimeout(20000)
			 * .setConnectionRequestTimeout(15000).setSocketTimeout(20000)
			 * .build();
			 */
			// 创建Post请求的实例，并传入待连接的地址
			httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);

			// 设置请求头
			httpPost.addHeader("content-type","application/json; charset=UTF-8");
			// 新增请求头的参数api-version：v1
			httpPost.addHeader("session", sessionId);
			/*
			// 创建Post请求参数队列
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			// 表单中的域用类NameValuePair来表示，该类的构造函数第一个参数是域名，第二参数是该域的值；
			for (String pKey : map.keySet()) {
				formparams.add(new BasicNameValuePair(pKey, map.get(pKey)));
			}
			*/
			// 设置表单的编码格式
			//httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		
			
			// 执行post请求，调用第一步中创建好的实例的execute方法来执行第二步中创建好的method实例
			HttpResponse response = httpClient.execute(httpPost);
			// 读response，获取响应实体
			HttpEntity entity = response.getEntity();
			// 读入内容流，并以字符串形式返回
			String postResult = EntityUtils.toString(entity, "UTF-8");
			// 把Unicode编码转中文
			responses = StringEscapeUtils.unescapeJava(postResult);
			if (responses != null) {
				// 打印响应内容
				System.out.println("接口响应：" + responses + "\n"); // 打印响应内容
				EntityUtils.consume(entity);// 关闭内容流
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpPost != null) {
					// 关闭连接
					httpPost.releaseConnection();
				}
				if (httpClient != null) {
					// 关闭连接
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return responses;
	}
	
	
	

}
