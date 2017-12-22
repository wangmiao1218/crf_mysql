package com.gennlife.myujie;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.ExcelUtils;
import com.gennlife.crf.utils.ListAndStringUtils;

/**
 * @Description: 配置crf组装的json
 * @author: wangmiao
 * @Date: 2017年12月22日 下午3:08:37 
 */
public class ConfiguredCrfPackageJson {

	private static JSONObject lastJson=new JSONObject();
	
	/** 
	* @Title: getJsonFile 
	* @Description: 读excel相关配置，生成crf组装的json文件
	* @param: @param excel :
	* @return: void
	* @throws 
	*/
	public static void getJsonFile(Excel excel) {
		//System.out.println("start。。。");
		Integer isConfiguredCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "是否配置");
		Integer sourceCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "source");
		Integer chNameCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "属性中文名");
		Integer nlpCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "数据模型来源（NLP）");
		Integer blockCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "block");
		Integer patientDetailCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "PatientDetail");
		Integer conditionCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "condition");
		Integer selectCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "select");
		Integer inCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "in");
		Integer elementCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "element");
		Integer computeCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "compute");
		
		//获取isConfiguredCellNum一列（用readExcelOfListReturnListMap，因为有重复值）(除表头)
		List<Map<Integer,String>> list = ExcelUtils.readExcelOfListReturnListMap(excel, isConfiguredCellNum);
		for (int i = 1; i < list.size(); i++) {
			Map<Integer, String> map = list.get(i);
			//定义是否填的行号和内容
			Integer isConfiguredRowNum=null;
			String isConfiguredStr=null;
			for (Map.Entry<Integer, String> entry : map.entrySet()) {  
				isConfiguredRowNum=entry.getKey();
				isConfiguredStr=entry.getValue();
			}
			
			if ("是".equals(list.get(i))) {
				System.out.println("配置字段！正在配置");
				//判断source
				String fieldEnName = ExcelUtils.readContent(excel, isConfiguredRowNum, sourceCellNum);
				if ("nlp".equals(fieldEnName)) {
					//读取npl的source
					String nlpSource = ExcelUtils.readContent(excel, isConfiguredRowNum, nlpCellNum);
					String chNameContent = ExcelUtils.readContent(excel, isConfiguredRowNum, chNameCellNum);
					
					
					
					String computeContent = ExcelUtils.readContent(excel, isConfiguredRowNum, chNameCellNum);
					
					
					//获取对应的值，传入到构造nlp方法
					ConfiguredCrfPackageJson.createNlpJson(chNameContent,nlpSource);
				}else if ("block".equals(fieldEnName)) {
					
					
					
					//获取对应的值，传入到构造block方法
					ConfiguredCrfPackageJson.createBlockJson("");
				}else if ("PatientDetail".equals(fieldEnName)) {
					
					
					
					//获取对应的值，传入到构造PatientDetail方法
					ConfiguredCrfPackageJson.createPatientDetailJson("");
				}
				
				
			}else {
				System.out.println("非配置字段！");
			}
		}
		
		ConfiguredCrfPackageJson.jsonToJsonFile(lastJson);
		System.out.println("ok");
	}
	
	
	/** 
	* @Title: createNlpJson 
	* @Description: 生成Nlp的json
	* @param: @param tableName :传入的表名
	* @throws 
	*/
	public static void createNlpJson(String chNameContent,String nlpSource) {
		//转换
		JSONArray nlpJsonArray = ListAndStringUtils.valueSpiltBySemicolonToJSONArray(nlpSource);
		
		//sourceJson
		Object source = new JSONObject().put("source", new JSONObject().put("nlp", nlpJsonArray));

		//conditionJson
		JSONObject conditionJson=new JSONObject();
		
		//computerJson
		JSONObject computerJson=new JSONObject();
		
		
		//blockJson
		JSONObject blockJson=new JSONObject();
		new JSONObject().put("block_"+chNameContent, source);
		
		//最后的json
		lastJson.put("blocks", blockJson);
		
	}

	
	/** 
	* @Title: createBlockJson 
	* @Description: 生成Block的json
	* @param: @param tableName :传入的表名
	* @throws 
	*/
	public static void createBlockJson(String tableName) {
		
		
	}
	
	
	/** 
	 * @Title: createPatientDetailJson 
	 * @Description: 生成PatientDetail的json
	 * @param: @param tableName :传入的表名
	 * @throws 
	 */
	public static void createPatientDetailJson(String tableName) {
		
		
	}
	
	
	/** 
	 * @Title: jsonToJsonFile 
	 * @Description: 将json生成json文件
	 * @param: @param tableName :传入的表名
	 * @throws 
	 */
	public static void jsonToJsonFile(JSONObject lastJson) {
		String path="E:\\CRF重组\\自动工具\\工具\\test.json";
        File file=new File(path);
        
        try {
        	 Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        	 write.write(lastJson.toString());
        	 write.flush();
             write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
