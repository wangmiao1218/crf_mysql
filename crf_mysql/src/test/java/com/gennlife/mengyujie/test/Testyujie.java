package com.gennlife.mengyujie.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.ExcelUtils;
import com.gennlife.mengyujie.Yujie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class Testyujie {

	private String filePath = "E:\\yujie";
	private String fileName = "英文版乳腺癌2.xlsx";
	private String sheetName = "就诊-介入治疗(package)";
	
	private String fileName2 = "CRF表字段配置2.xlsx";
	
	@Test
	public void testYujie(){
		Excel excelrx = new Excel(filePath, fileName, sheetName);
		
		Excel exceldc = new Excel(filePath, fileName2, sheetName);
		
		String str = Yujie.testYujie(excelrx, 2, exceldc, 2);
		
		System.out.println(str);
	}
	
	
	
}