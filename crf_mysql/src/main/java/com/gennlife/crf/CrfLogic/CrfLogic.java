package com.gennlife.crf.CrfLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.map.HashedMap;
import org.json.JSONException;
import org.json.JSONObject;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.mongodb.TianjinMongodbDataProcess;
import com.gennlife.crf.utils.ExcelUtils;
import com.gennlife.crf.utils.JsonUtils;
import com.gennlife.crf.utils.ListAndStringUtils;
import com.gennlife.interfaces.ManualEMRAutoCRFV2OfCrfAutoInterface;

/**
 * @Description: 测试crf逻辑
 * @author: wangmiao
 * @Date: 2017年12月25日 下午5:31:22 
 */
public class CrfLogic {

	private static final String patPath = "patient_info.patient_info_patient_sn";
	
	//存放批量的json，统一插入到mongodb(插入到数据库，要先判断pat是否存在  所以以map形式存储)
	private static List<Map<String, JSONObject>> listMapJsons = new ArrayList<Map<String,JSONObject>>();
	
	//将行号和pat号对应，存到map里，方便后续写入，和批量请求
	private static Map<Integer, String> cellNumAndPatMap = new HashedMap<Integer, String>();

	
	/**
	* @Title: queryCrfdataByPatAndWriteResults 
	* @Description:  去crfdata数据库，查询对应数据，返回结果和行号的map
	* @param: @param excel :
	* @return: void
	* @throws 
	*/
	public static void queryCrfdataByPatAndWriteResults(Excel excel) throws JSONException{
		System.out.println("start。。。");
		//因为key有重复，不用IdentityHashMap，则放到list中
		//将pat和crfdata路径，存到map里，再讲行号和map封装成map，方便查询
		Map<Integer,Map<String, String>> rowNumAndpatCrfdataMapMap = new HashedMap<Integer, Map<String,String>>();
		
		Integer isConfiguredCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "是否配置");
		Integer reusePatRowNumCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "reusePatRowNum");
		Integer crfdataCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "crfdata");
		Integer patCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "pat");

		//获取isConfiguredCellNum一列（用readExcelOfListReturnListMap，因为有重复值）(除表头)
		List<Map<Integer,String>> list = ExcelUtils.readExcelOfListReturnListMap(excel, isConfiguredCellNum);
		//获取是否配置的列，开始遍历
		for (int i = 1; i < list.size(); i++) {
			Map<Integer, String> map = list.get(i);
			//定义是否填的行号和内容
			Integer isConfiguredRowNum=null;
			String isConfiguredStr=null;
			for (Map.Entry<Integer, String> entry: map.entrySet()) {  
				isConfiguredRowNum=entry.getKey();
				isConfiguredStr=entry.getValue();
			}
			if ("是".equals(isConfiguredStr)) {
				//因为key可能相同，value可能不同，所以在for里新建map，最后再放到list中
				Map<String, String> cellNumAndCrfdataValueMap = new HashedMap<String, String>();
				//获取并行的三列，进行判断
				String reusePatRowNumContent = ExcelUtils.readContent(excel, isConfiguredRowNum, reusePatRowNumCellNum);
				String patContent = ExcelUtils.readContent(excel, isConfiguredRowNum, patCellNum);
				String crfdataContent = ExcelUtils.readContent(excel, isConfiguredRowNum, crfdataCellNum);
				//只有满足以下才进行查询
				if (reusePatRowNumContent==null && patContent!=null && crfdataContent!=null){
					//存pat和crfdata源
					cellNumAndCrfdataValueMap.put(patContent, crfdataContent);
					rowNumAndpatCrfdataMapMap.put(isConfiguredRowNum, cellNumAndCrfdataValueMap);
				}else if (reusePatRowNumContent!=null && crfdataContent!=null) {
					//不为空，则复用pat，直接存复用的pat和crfdata
					//根据reusePatRowNum，查pat（行号要减1，因为之前方便看，增加了1）
					String reusePatContent = ExcelUtils.readContent(excel, Integer.valueOf(reusePatRowNumContent)-1, patCellNum);
					//判断是否为空
					if (reusePatContent!=null) {
						cellNumAndCrfdataValueMap.put(reusePatContent, crfdataContent);
						rowNumAndpatCrfdataMapMap.put(isConfiguredRowNum, cellNumAndCrfdataValueMap);
					}
				}
			}else {
				System.out.println("非配置字段！");
			}
		}
		
		//传入查询crfdata的方法
		Map<Integer, org.bson.BSONObject> rowNumAndcrfdataMap = TianjinMongodbDataProcess.queryDatasOfCrfdataMongodb(rowNumAndpatCrfdataMapMap);
		
		//CrfLogic.writePatIntoExcel(excel, cellNumAndPatMap);
		System.out.println(rowNumAndcrfdataMap);
		for (Entry<Integer, org.bson.BSONObject> map: rowNumAndcrfdataMap.entrySet()) {  
			org.bson.BSONObject crfdata = map.getValue();
			System.out.println(crfdata);
		}
		
		//将结果写入excel
		CrfLogic.writeCrfdataIntoExcel(excel, rowNumAndcrfdataMap);
		
		System.out.println("ok");
	}
		

	/** 
	* @Title: writeCrfdataIntoExcel 
	* @Description: 将生成的crf结果写入到excel
	* @param: @param excel
	* @param: @param rowNumAndcrfdataMap :
	* @return: void
	* @throws 
	*/
	public static void writeCrfdataIntoExcel(Excel excel,Map<Integer, org.bson.BSONObject> rowNumAndcrfdataMap ){
		Integer outputCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "实际输出");
		Integer rowNum=null;
		org.bson.BSONObject crfdata=null;
		
		for (Entry<Integer, org.bson.BSONObject> entry: rowNumAndcrfdataMap.entrySet()) {  
			rowNum=entry.getKey();
			crfdata=entry.getValue();
			ExcelUtils.writeAndSaveContent(excel, crfdata.toString(), rowNum, outputCellNum);
		}
		System.out.println("crfdata写入excel完成...");
	}
	
	
	/** 
	* @Title: insertDatasIntoPatientDetailAndPostAndWritePatIntoExcel 
	* @Description: 读excel相关配置，根据组装规则，组装数据并插入数据库，请求接口，将pat写入excel
	* @param: @param excel
	* @param: @param path
	* @param: @throws JSONException :
	* @return: void
	* @throws 
	*/
	public static void insertDatasIntoPatientDetailAndPostAndWritePatIntoExcel(Excel excel,String path) throws JSONException {
		System.out.println("start。。。");
		Integer isConfiguredCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "是否配置");
		Integer reusePatRowNumCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "reusePatRowNum");
		Integer patientDetailCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "patientDetail");
		Integer insertContentCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "输入文本");
		
		//获取isConfiguredCellNum一列（用readExcelOfListReturnListMap，因为有重复值）(除表头)
		List<Map<Integer,String>> list = ExcelUtils.readExcelOfListReturnListMap(excel, isConfiguredCellNum);
		//获取是否配置的列，开始遍历
		for (int i = 1; i < list.size(); i++) {
			//放到上面设置变量，则不循环，但是每个值都保存了
			//读取基础文本文件，并转为json
			org.json.JSONObject baseJson = JsonUtils.readFileContentReturnJson(path);
			
			Map<Integer, String> map = list.get(i);
			//定义行号和内容
			Integer isConfiguredRowNum=null;
			String isConfiguredStr=null;
			for (Map.Entry<Integer, String> entry: map.entrySet()) {  
				isConfiguredRowNum=entry.getKey();
				isConfiguredStr=entry.getValue();
			}
			
			if ("是".equals(isConfiguredStr)) {
				//获取并行的三列，进行判断
				String reusePatContent = ExcelUtils.readContent(excel, isConfiguredRowNum, reusePatRowNumCellNum);
				String patientDetailContent = ExcelUtils.readContent(excel, isConfiguredRowNum, patientDetailCellNum);
				String insertContent = ExcelUtils.readContent(excel, isConfiguredRowNum, insertContentCellNum);
				
				//只有满足以下才进行计算(不服用pat，且数据源与输入文本不为空)
				if (reusePatContent==null && patientDetailContent!=null && insertContent!=null) {
					//pat编号
					String patContent="pat_"+(isConfiguredRowNum+1);
					//存行号和pat
					cellNumAndPatMap.put(isConfiguredRowNum, patContent);
					JSONObject newJSONObject = null;
					//============单个数据源处理============（目前是update方式，后续改成增加方式）
					if (!patientDetailContent.contains(";")) {
						//对数据源patientDetail进行处理
						String[] dealWithpatientDetailByDotToStrings = ListAndStringUtils.dealWithpatientDetailByDotToStrings(patientDetailContent);
						//解析json，将pat、和输入文本插入到json中
						newJSONObject = JsonUtils.updatePatAndValueReturnNewJSONObject(baseJson, patPath, patContent, dealWithpatientDetailByDotToStrings, insertContent);
					}else if (patientDetailContent.contains(";") && insertContent.contains(";")) {
						//============多个数据源处理============	
						//处理patientDetail，然后用；分割
						String byAsteriskToString = ListAndStringUtils.dealWithpatientDetailByAsteriskToString(patientDetailContent);
						List<String> patientDetailContents = ListAndStringUtils.dealWithpatientDetailBySemicolonToStrings(byAsteriskToString);
						//处理insertContent，然后用;分割
						List<String> insertContents = ListAndStringUtils.dealWithpatientDetailBySemicolonToStrings(insertContent);
						//循环处理json放入一个人的数据里
						for (int j = 0; j < patientDetailContents.size(); j++) {
							//=======================
							//后续会加是否重复的判断,第几次出现
							//int count = Collections.frequency(patientDetailContents, patientDetailContents.get(j));
							
							//=======================
							String[] dbyDotToStrings = ListAndStringUtils.dealWithpatientDetailByDotToStrings(patientDetailContents.get(j));
							newJSONObject = JsonUtils.updatePatAndValueReturnNewJSONObject(baseJson, patPath, patContent, dbyDotToStrings, insertContents.get(j));
						}
					} 
					
					//添加到listJsons（map只有一个值，方便后面遍历）
					Map<String,JSONObject> patAndJsonMap =new  HashedMap<String, JSONObject>();
					patAndJsonMap.put(patContent, newJSONObject);
					listMapJsons .add(patAndJsonMap);
				}
			}else {
				System.out.println("非配置字段！");
			}
		}
		//将新的json的list插入mongodb的patientDetail中
		TianjinMongodbDataProcess.insertDatasIntoPatientDetailMongodb(listMapJsons);
		
		//===============================
		//可优化为多线程，一个请求接口，一个将pat写入excel
		//批量请求接口
		CrfLogic.requestCrfAutoInterfaceByPat(cellNumAndPatMap);
		
		//将pat写入excel
		CrfLogic.writePatIntoExcel(excel, cellNumAndPatMap);
		
		System.out.println("ok");
	}
	
	
	/** 
	* @Title: requestCrfAutoInterfaceByPat 
	* @Description: 批量请求crf组装接口，返回接口处理的结果
	* @param: @param cellNumAndPatMap
	* @param: @return
	* @param: @throws JSONException :
	* @return: JSONObject
	* @throws 
	*/
	public static JSONObject requestCrfAutoInterfaceByPat(Map<Integer, String> cellNumAndPatMap) throws JSONException {
		//处理map,将pat封装成list
		String pat=null;
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, String> entry: cellNumAndPatMap.entrySet()) {  
			pat=entry.getValue();
			String str = "\""+pat+"\"";
			sb.append(str.trim()).append(",");
		}
		String patStrs = sb.deleteCharAt(sb.length() - 1).toString();
		String str = ManualEMRAutoCRFV2OfCrfAutoInterface.getResultsByPostMethod(patStrs);
		System.out.println("请求接口end...");
		return new JSONObject(str);
	}
	
	
	/** 
	* @Title: writePatIntoExcel 
	* @Description: 将生成的pat号写入到excel
	* @param: @param excel
	* @param: @param cellNumAndPatMap将pat写入excel
	* @return: void
	* @throws 
	*/
	public static void writePatIntoExcel(Excel excel,Map<Integer, String> cellNumAndPatMap){
		Integer patCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "pat");
		Integer rowNum=null;
		String pat=null;
		
		for (Map.Entry<Integer, String> entry: cellNumAndPatMap.entrySet()) {  
			rowNum=entry.getKey();
			pat=entry.getValue();
			ExcelUtils.writeAndSaveContent(excel, pat, rowNum, patCellNum);
		}
		System.out.println("pat写入excel完成...");
	}
	
	
	/** 
	 * @Title: readExcelReturnJsonMapList (测试用)
	 * @Description: 读excel相关配置，根据组装规则，返回json的list，方便后续插入数据库
	 * @param: @param excel
	 * @param: @param path
	 * @return: List<Map<String, JSONObject>>
	 * @throws 
	 */
	public static List<Map<String,JSONObject>> readExcelReturnJsonMapList(Excel excel,String path) throws JSONException {
		System.out.println("start。。。");
		//存放批量的json，统一插入到mongodb
		List<Map<String, JSONObject>> returnListMapJsons = new ArrayList<Map<String,JSONObject>>();
		Integer isConfiguredCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "是否配置");
		Integer reusePatNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "reusePatRowNum");
		Integer patientDetailCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "patientDetail");
		Integer insertContentCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "输入文本");
		
		//获取isConfiguredCellNum一列（用readExcelOfListReturnListMap，因为有重复值）(除表头)
		List<Map<Integer,String>> list = ExcelUtils.readExcelOfListReturnListMap(excel, isConfiguredCellNum);
		//获取是否配置的列，开始遍历
		for (int i = 1; i < list.size(); i++) {
			//放到上面设置变量，则不循环，但是每个值都保存了
			//读取基础文本文件，并转为json
			org.json.JSONObject baseJson = JsonUtils.readFileContentReturnJson(path);
			
			Map<Integer, String> map = list.get(i);
			//定义是否填的行号和内容
			Integer isConfiguredRowNum=null;
			String isConfiguredStr=null;
			for (Map.Entry<Integer, String> entry: map.entrySet()) {  
				isConfiguredRowNum=entry.getKey();
				isConfiguredStr=entry.getValue();
			}
			
			if ("是".equals(isConfiguredStr)) {
				//获取并行的三列，进行判断
				String reusePatContent = ExcelUtils.readContent(excel, isConfiguredRowNum, reusePatNum);
				String patientDetailContent = ExcelUtils.readContent(excel, isConfiguredRowNum, patientDetailCellNum);
				String insertContent = ExcelUtils.readContent(excel, isConfiguredRowNum, insertContentCellNum);
				
				//只有满足以下才进行计算(不服用pat，且数据源与输入文本不为空)
				if (reusePatContent==null && patientDetailContent!=null && insertContent!=null) {
					//pat编号
					String patContent="pat_"+(isConfiguredRowNum+1);
					JSONObject newJSONObject = null;
					//============单个数据源处理============（目前是update方式，后续改成增加方式）
					if (!patientDetailContent.contains(";")) {
						//对数据源patientDetail进行处理
						String[] dealWithpatientDetailByDotToStrings = ListAndStringUtils.dealWithpatientDetailByDotToStrings(patientDetailContent);
						//解析json，将pat、和输入文本插入到json中
						newJSONObject = JsonUtils.updatePatAndValueReturnNewJSONObject(baseJson, patPath, patContent, dealWithpatientDetailByDotToStrings, insertContent);
					}else if (patientDetailContent.contains(";") && insertContent.contains(";")) {
						//============多个数据源处理============	
						//处理patientDetail，然后用；分割
						String byAsteriskToString = ListAndStringUtils.dealWithpatientDetailByAsteriskToString(patientDetailContent);
						List<String> patientDetailContents = ListAndStringUtils.dealWithpatientDetailBySemicolonToStrings(byAsteriskToString);
						//处理insertContent，然后用;分割
						List<String> insertContents = ListAndStringUtils.dealWithpatientDetailBySemicolonToStrings(insertContent);
						//循环处理json放入一个人的数据里
						for (int j = 0; j < patientDetailContents.size(); j++) {
							//=======================
							//后续会加是否重复的判断
							
							
							//=======================
							String[] dbyDotToStrings = ListAndStringUtils.dealWithpatientDetailByDotToStrings(patientDetailContents.get(j));
							newJSONObject = JsonUtils.updatePatAndValueReturnNewJSONObject(baseJson, patPath, patContent, dbyDotToStrings, insertContents.get(j));
						}
					} 
					//添加到listJsons（map只有一个值，方便后面遍历）
					Map<String,JSONObject> patAndJsonMap =new  HashedMap<String, JSONObject>();
					patAndJsonMap.put(patContent, newJSONObject);
					returnListMapJsons.add(patAndJsonMap);
				}
			}else {
				System.out.println("非配置字段！");
			}
		}
		
		return returnListMapJsons;
	}
	
	
	/** 
	* @Title: readExcelReturnCellNumAndPatMap (测试用)
	* @Description: 读excel相关配置，返回行号和pat的
	* @param: @param excel
	* @param: @return
	* @param: @throws JSONException
	* @return: Map<Integer,String>
	* @throws 
	*/
	public static Map<Integer, String> readExcelReturnCellNumAndPatMap(Excel excel) throws JSONException {
		//将行号和pat号对应，存到map里，方便后续写入，和批量请求
		Map<Integer, String> returnCellNumAndPatMap = new HashedMap<Integer, String>();
		
		Integer isConfiguredCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "是否配置");
		Integer reusePatNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "reusePatRowNum");
		Integer patientDetailCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "patientDetail");
		Integer insertContentCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "输入文本");
	
		//获取isConfiguredCellNum一列（用readExcelOfListReturnListMap，因为有重复值）(除表头)
		List<Map<Integer,String>> list = ExcelUtils.readExcelOfListReturnListMap(excel, isConfiguredCellNum);
		//获取是否配置的列，开始遍历
		for (int i = 1; i < list.size(); i++) {
			Map<Integer, String> map = list.get(i);
			//定义是否填的行号和内容
			Integer isConfiguredRowNum=null;
			String isConfiguredStr=null;
			for (Map.Entry<Integer, String> entry: map.entrySet()) {  
				isConfiguredRowNum=entry.getKey();
				isConfiguredStr=entry.getValue();
			}
			
			if ("是".equals(isConfiguredStr)) {
				//获取并行的三列，进行判断
				String reusePatContent = ExcelUtils.readContent(excel, isConfiguredRowNum, reusePatNum);
				String patientDetailContent = ExcelUtils.readContent(excel, isConfiguredRowNum, patientDetailCellNum);
				String insertContent = ExcelUtils.readContent(excel, isConfiguredRowNum, insertContentCellNum);
				
				//只有满足以下才进行计算(不服用pat，且数据源与输入文本不为空)
				if (reusePatContent==null && patientDetailContent!=null && insertContent!=null) {
					String patContent="pat_"+(isConfiguredRowNum+1);
					//存行号和pat
					returnCellNumAndPatMap.put(isConfiguredRowNum, patContent);
				}
			}else {
				System.out.println("非配置字段！");
			}
		}
		return returnCellNumAndPatMap;
	}
	
	
}
