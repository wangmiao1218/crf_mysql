package com.gennlife.crf.yantai.mongodb.test;

import java.util.List;

import org.junit.Test;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.ExcelUtils;
import com.gennlife.crf.yantai.mongodb.YantaiMongodbDataProcess;

public class TestYantaiMongodbDataProcess {

	private String patient_sn = "pat_1ec2235c192b896e7379f6edc00ab968";
	private String rydate = "2016-11-16 08:10:00";
	
	
	@Test
	public void search() {
		String str = YantaiMongodbDataProcess.RysqkYongyaoqk(patient_sn, rydate);
		System.out.println(str);
		
        //读取excel药一列
        String filePath = "E:\\烟台升级\\crf数据测试";
    	String fileName = "神内脑血管药物.xlsx";
    	String sheetName = "降血脂药物";
    	
    	Excel excel = new Excel(filePath, fileName, sheetName);
    	
        List<String> list = ExcelUtils.readExcelOfList(excel, 1);
        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++) {
        	if (str.contains(list.get(i))) {
				System.out.println(list.get(i));
			}
		}
        
		
	}
	
	
	
}
