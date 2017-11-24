package com.gennlife.crf.yantai.mongodb.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.ExcelUtils;
import com.gennlife.crf.yantai.mongodb.InterfaceTools;

public class TestInterfaceTools {

	private String filePath = "C:\\Users\\www\\Desktop\\编号";
	private String fileName = "333.xlsx";
	private String sheetName = "Sheet1";
	
	
	@Test
	public void test1() {
		Excel excel = new Excel(filePath, fileName, sheetName);
		
		Integer patCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "编号");
		Integer patiNameCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "PatiName");
		Integer idCardCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "IDCard");
		Integer inPatientSnCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "InPatientSn");
		
		
		//获取中文名称一列（用readExcelOfListReturnListMap，因为有重复值）(除表头)
		List<Map<Integer,String>> list = ExcelUtils.readExcelOfListReturnListMap(excel, patCellNum);
		for (int i = 1; i < list.size(); i++) {
			Map<Integer, String> map = list.get(i);
			//定义“联动”列的行号和内容（即：写入值时对应的行号）
			Integer writeContentRowNum=null;
			String patString=null;
			for (Map.Entry<Integer, String> entry : map.entrySet()) {  
				//行号
				writeContentRowNum=entry.getKey();
				//pat
				patString=entry.getValue();
				System.out.println(writeContentRowNum+"--"+patString);
				
				//获取pat，并请求接口
				String allString = InterfaceTools.post(patString);
				//System.out.println("返回jsonStr："+allString);
				
				
				//返回String的json，并解析json，获取对应三个数值
				String patiNameString=null;
				String idCardString=null;
				String inPatientString=null;
				
				//将returnStr字符串转换成json对象:JSONObject
		        JSONObject jsonObject=(JSONObject) JSON.parse(allString);
		        JSONArray jsonArray = jsonObject.getJSONArray("Results");
		        //System.out.println(jsonArray);
		        
		        //返回Results的JSONObject
		        jsonObject = (JSONObject) jsonArray.get(0);
		        //System.out.println(jsonObject);
		        //{"Status":0,"IDCard":"370623197112014021","InPatientSn":["1288385"],"PatiName":"栾彩梅"}
		        
		    	//List<String> list1 = new ArrayList<String>();
		    	//处理jsonObject
		        Map IDCard_map = (Map) jsonObject;
		        for (Object map1 : IDCard_map.entrySet()){ 
		        	if ("IDCard"==((Map.Entry)map1).getKey()) {
		        		//list1.add(((Map.Entry)map1).getKey()+":"+((Map.Entry)map1).getValue());
		         		//System.out.println(((Map.Entry)map1).getKey()+":"+((Map.Entry)map1).getValue());
		         		idCardString=((Map.Entry)map1).getValue().toString();
		        	}
		        	
		        	if ("InPatientSn"==((Map.Entry)map1).getKey()) {
		        		//list1.add(((Map.Entry)map1).getKey()+":"+((Map.Entry)map1).getValue());
		         		//System.out.println(((Map.Entry)map1).getKey()+":"+((Map.Entry)map1).getValue());
		         		inPatientString=((Map.Entry)map1).getValue().toString();
		        	}
		        	
		        	if ("PatiName"==((Map.Entry)map1).getKey()) {
		        		//list1.add(((Map.Entry)map1).getKey()+":"+((Map.Entry)map1).getValue());
		         		//System.out.println(((Map.Entry)map1).getKey()+":"+((Map.Entry)map1).getValue());
		         		patiNameString=((Map.Entry)map1).getValue().toString();
		        	}
		        	
		        }
		        
				
				//写入对应excel
		        ExcelUtils.writeAndSaveContent(excel, idCardString, writeContentRowNum, idCardCellNum);
		        ExcelUtils.writeAndSaveContent(excel, inPatientString, writeContentRowNum, inPatientSnCellNum);
				ExcelUtils.writeAndSaveContent(excel, patiNameString, writeContentRowNum, patiNameCellNum);
				
			}
		}
		
		System.out.println("OK");
	}
	
	
	
	
}
