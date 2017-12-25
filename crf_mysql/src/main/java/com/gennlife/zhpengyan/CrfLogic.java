package com.gennlife.zhpengyan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import net.sf.json.JSONObject;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.ExcelUtils;
import com.gennlife.crf.utils.JsonUtils;

/**
 * @Description: 测试crf逻辑
 * @author: wangmiao
 * @Date: 2017年12月25日 下午5:31:22 
 */
public class CrfLogic {

	private static JSONObject lastJson=new JSONObject();
	private static String patPath = "patient_info.patient_info_patient_sn";
	//private static List<E> listJsons = new ArrayList<>();
	
	/**
	* @Title: getJsonFile 
	* @Description: 读excel相关配置，生成crf组装的json文件
	* @param: @param excel
	* @param: @param path
	* @return: void
	* @throws 
	*/
	public static void insertDatasIntoPatientDetail(Excel excel,String path) throws JSONException {
		System.out.println("start。。。");
		Integer isConfiguredCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "是否配置");
		Integer reusePatNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "reusePat");
		Integer patientDetailCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "patientDetail");
		Integer insertContentCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "输入文本");
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
				String reusePatContent = ExcelUtils.readContent(excel, isConfiguredRowNum, reusePatNum);
				String patientDetailContent = ExcelUtils.readContent(excel, isConfiguredRowNum, patientDetailCellNum);
				String insertContent = ExcelUtils.readContent(excel, isConfiguredRowNum, insertContentCellNum);
			
				//只有满足以下才进行计算(不服用pat，且数据源与输入文本不为空)
				if (reusePatContent==null && patientDetailContent!=null && insertContent!=null) {
					//对数据源patientDetail进行处理
					System.out.println(patientDetailContent);
					
					
					String patContent="pat_"+isConfiguredRowNum;
					
					
					//读取基础文本文件，并转为json
					org.json.JSONObject baseJson = JsonUtils.readFileContentReturnJson(path);
					
					//解析json，将pat、和输入文本插入到json中
					
					
					//list<json>
					
					
					
				}else {
					continue;
				}
				
			}else {
				System.out.println("非配置字段！");
			}
		}
		
		//将新的json插入mongodb的patientDetail中
		
		
		
		
		System.out.println("ok");
	}
	
	
	
	
	

}
