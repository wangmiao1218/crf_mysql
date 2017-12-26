package com.gennlife.crf.CrfLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	private static String patPath = "patient_info.patient_info_patient_sn";
	//存放批量的json，统一插入到mongodb
	private static List<JSONObject> listJsons = new ArrayList<JSONObject>();
	//将行号和pat号对应，存到map里，方便后续写入，和批量请求
	private static Map<Integer, String> cellNumAndPatMap = new HashedMap<Integer, String>();
	
	//将行号和crf的查询结果，存到map里，方便查询，和后续写入
	private static Map<Integer, String> cellNumAndCrfdataValueMap = new HashedMap<Integer, String>();
		

	/** 
	* @Title: queryDataOfCrfdataByPatAndWriteResults 
	* @Description:  去crfdata数据库，查询对应数据，返回结果和行号的map
	* @param: @param excel :
	* @return: void
	* @throws 
	*/
	public static void queryDataOfCrfdataByPatAndWriteResults(Excel excel){
		System.out.println("start。。。");
		Integer isConfiguredCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "是否配置");
		Integer reusePatCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "reusePat");
		Integer crfdataCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "crfdata");
		Integer patCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "pat");
		Integer outputCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "预期输出");

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
				System.out.println("配置字段！正在配置");
				//获取并行的三列，进行判断
				String reusePatContent = ExcelUtils.readContent(excel, isConfiguredRowNum, reusePatCellNum);
				String patContent = ExcelUtils.readContent(excel, isConfiguredRowNum, patCellNum);
				String crfdataContent = ExcelUtils.readContent(excel, isConfiguredRowNum, crfdataCellNum);
				
				//只有满足以下才进行查询
				if (reusePatContent==null && patContent!=null && crfdataContent!=null){
					//不复用pat时逻辑
					//对crfdata进行处理
					String[] crfdataContentStrs = ListAndStringUtils.dealWithpatientDetailByDotToStrings(crfdataContent);
					//pat编号
					
					
					//存行号和查询结果
					cellNumAndPatMap.put(isConfiguredRowNum, "");
					
					
				}else if (reusePatContent!=null && crfdataContent!=null) {
					//不为空，则复用pat，直接根据crfdata查对应数据
					
					
				}
						
					
			}else {
				System.out.println("非配置字段！");
			}
		}
		
		
		System.out.println("ok");
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
		Integer reusePatCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "reusePat");
		Integer patientDetailCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "patientDetail");
		Integer insertContentCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "输入文本");
		
		//读取基础文本文件，并转为json
		org.json.JSONObject baseJson = JsonUtils.readFileContentReturnJson(path);
		
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
				System.out.println("配置字段！正在配置");
				//获取并行的三列，进行判断
				String reusePatContent = ExcelUtils.readContent(excel, isConfiguredRowNum, reusePatCellNum);
				String patientDetailContent = ExcelUtils.readContent(excel, isConfiguredRowNum, patientDetailCellNum);
				String insertContent = ExcelUtils.readContent(excel, isConfiguredRowNum, insertContentCellNum);
				
				//只有满足以下才进行计算(不服用pat，且数据源与输入文本不为空)
				if (reusePatContent==null && patientDetailContent!=null && insertContent!=null) {
					//对数据源patientDetail进行处理
					String[] dealWithpatientDetailByDotToStrings = ListAndStringUtils.dealWithpatientDetailByDotToStrings(patientDetailContent);
					//pat编号
					String patContent="pat_"+(isConfiguredRowNum+1);
					//存行号和pat
					cellNumAndPatMap.put(isConfiguredRowNum, patContent);
					
					//解析json，将pat、和输入文本插入到json中
					JSONObject newJSONObject = JsonUtils.insertPatAndValueReturnNewJSONObject(baseJson, patPath, patContent, dealWithpatientDetailByDotToStrings, insertContent);
					
					//添加到listJsons
					listJsons.add(newJSONObject);
				}else {
					continue;
				}
			}else {
				System.out.println("非配置字段！");
			}
		}
		//将新的json的list插入mongodb的patientDetail中
		TianjinMongodbDataProcess.insertDatasIntoPatientDetailMongodb(listJsons);
		
		//===============================
		//可优化为多线程，一个请求接口，一个将pat写入excel
		//批量请求接口
		CrfLogic.requireCrfAutoInterfaceByPat(cellNumAndPatMap);
		
		//将pat写入excel
		CrfLogic.writePatIntoExcel(excel, cellNumAndPatMap);
		
		System.out.println("ok");
	}
	
	/** 
	* @Title: requireCrfAutoInterfaceByPat 
	* @Description: 批量请求crf组装接口，返回接口处理的结果
	* @param: @param cellNumAndPatMap
	* @param: @return
	* @param: @throws JSONException :
	* @return: JSONObject
	* @throws 
	*/
	public static JSONObject requireCrfAutoInterfaceByPat(Map<Integer, String> cellNumAndPatMap) throws JSONException {
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
	 * @Title: readExcelReturnJsonList (测试用)
	 * @Description: 读excel相关配置，根据组装规则，返回json的list，方便后续插入数据库
	 * @param: @param excel
	 * @param: @param path
	 * @return: List<JSONObject>
	 * @throws 
	 */
	public static List<JSONObject> readExcelReturnJsonList(Excel excel,String path) throws JSONException {
		System.out.println("start。。。");
		//存放批量的json，统一插入到mongodb
		List<JSONObject> returnListJsons = new ArrayList<JSONObject>();
		Integer isConfiguredCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "是否配置");
		Integer reusePatNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "reusePat");
		Integer patientDetailCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "patientDetail");
		Integer insertContentCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "输入文本");
		
		//读取基础文本文件，并转为json
		org.json.JSONObject baseJson = JsonUtils.readFileContentReturnJson(path);
		
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
				System.out.println("配置字段！正在配置");
				//获取并行的三列，进行判断
				String reusePatContent = ExcelUtils.readContent(excel, isConfiguredRowNum, reusePatNum);
				String patientDetailContent = ExcelUtils.readContent(excel, isConfiguredRowNum, patientDetailCellNum);
				String insertContent = ExcelUtils.readContent(excel, isConfiguredRowNum, insertContentCellNum);
				
				//只有满足以下才进行计算(不服用pat，且数据源与输入文本不为空)
				if (reusePatContent==null && patientDetailContent!=null && insertContent!=null) {
					//对数据源patientDetail进行处理
					String[] dealWithpatientDetailByDotToStrings = ListAndStringUtils.dealWithpatientDetailByDotToStrings(patientDetailContent);
					//pat编号
					String patContent="pat_"+(isConfiguredRowNum+1);
					
					//解析json，将pat、和输入文本插入到json中
					JSONObject newJSONObject = JsonUtils.insertPatAndValueReturnNewJSONObject(baseJson, patPath, patContent, dealWithpatientDetailByDotToStrings, insertContent);
					
					//添加到listJsons
					returnListJsons.add(newJSONObject);
				}else {
					continue;
				}
			}else {
				System.out.println("非配置字段！");
			}
		}
		
		return returnListJsons;
	}
	
	/** 
	* @Title: readExcelReturnCellNumAndPatMap (测试用)
	* @Description: 读excel相关配置，返回行号和pat的
	* @param: @param excel
	* @param: @return
	* @param: @throws JSONException :
	* @return: Map<Integer,String>
	* @throws 
	*/
	public static Map<Integer, String> readExcelReturnCellNumAndPatMap(Excel excel) throws JSONException {
		//将行号和pat号对应，存到map里，方便后续写入，和批量请求
		Map<Integer, String> returnCellNumAndPatMap = new HashedMap<Integer, String>();
		
		Integer isConfiguredCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "是否配置");
		Integer reusePatNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "reusePat");
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
				}else {
					continue;
				}
			}else {
				System.out.println("非配置字段！");
			}
		}
		return returnCellNumAndPatMap;
	}
	
}
