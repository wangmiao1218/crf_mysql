package com.gennlife.crf.yantai.mongodb.test;

import java.util.List;
import org.junit.Test;
import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.ExcelUtils;
import com.gennlife.crf.yantai.mongodb.YantaiMongodbDataProcess;

public class TestYantaiMongodbDataProcess {

	private String patient_sn = "pat_a171d95221c466284743f72c62957edd";
	private String rydate = "2015-06-01 12:08:00";
	
	@Test
	public void search() {
		String str = YantaiMongodbDataProcess.RysqkYongyaoqkReturnDataSources(patient_sn, rydate);
		
        //读取excel药一列
        String filePath = "E:\\烟台升级\\crf数据测试";
    	String fileName = "神内脑血管药物.xlsx";
    	String sheetName = "降血脂药物";
    	String sheetName2 = "精神科药";
    	String sheetName3 = "降糖药物";
    	
    	//降血脂药物
    	Excel excel = new Excel(filePath, fileName, sheetName);
        List<String> list = ExcelUtils.readExcelOfList(excel, 1);
        for (int i = 0; i < list.size(); i++) {
        	//str.contains(list.get(i) != null) ? System.out.println("存在的_"+sheetName+"："+list.get(i)):System.out.println("存在的_"+sheetName+"："+null);
        	
        	//str.contains(list.get(i));
        	/*if (str.contains(list.get(i))) {
				System.out.println("存在的_"+sheetName+"："+list.get(i));
			}else {
				System.out.println("存在的_"+sheetName+"："+null);
			}*/
		}
        
        //精神科药物
        Excel excel2 = new Excel(filePath, fileName, sheetName2);
        List<String> list2 = ExcelUtils.readExcelOfList(excel2, 0);
        for (int i = 0; i < list2.size(); i++) {
        	if (str.contains(list2.get(i))) {
				System.out.println("存在的_"+sheetName2+"："+list2.get(i));
			}else {
				System.out.println("存在的_"+sheetName2+"："+null);
			}
		}
		
        //降糖药物
        Excel excel3 = new Excel(filePath, fileName, sheetName3);
        List<String> list3 = ExcelUtils.readExcelOfList(excel3, 1);
        for (int i = 0; i < list3.size(); i++) {
        	if (str.contains(list3.get(i))) {
				System.out.println("存在的_"+sheetName3+"："+list3.get(i));
			}else {
				System.out.println("存在的_"+sheetName3+"："+null);
			}
		}
        
	}
	
	
	
}
