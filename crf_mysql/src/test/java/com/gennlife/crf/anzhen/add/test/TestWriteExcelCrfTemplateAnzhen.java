package com.gennlife.crf.anzhen.add.test;

import java.util.List;

import org.apache.commons.collections4.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gennlife.crf.anzhen.add.WriteExcelCrfTemplateAnzhen;
import com.gennlife.crf.bean.CrfTemplateAnzhen;
import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.service.CrfTemplateAnzhenService;
import com.gennlife.crf.utils.ExcelUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class TestWriteExcelCrfTemplateAnzhen {
	
	@Autowired
	private CrfTemplateAnzhenService crfTemplateAnzhenService;

	private String filePath = "E:\\安贞\\！安贞数据导出";
	private String fileName = "test01.xlsx";
	private String fileNameSf = "test.xlsx";
	
	private String sheetName = "Sheet1";
	private String sheetNameSf = "随访3";
	
	
	@Test
	public void readTwoContentAndJudgeAndWriteResult() throws Exception{
		Excel excel = new Excel(filePath, fileName, sheetName);
		Integer allRow=10;
		Integer beginCell=2;
		Integer endCell=5;
		Integer writeCell=6;
		
		String str = WriteExcelCrfTemplateAnzhen.readTwoContentAndJudgeAndWriteResult(excel, allRow, beginCell, endCell, writeCell);
		System.out.println(str);
	}
	
	
	@Test
	public void writeExcelByCompareEnglishName() throws Exception{
		List<CrfTemplateAnzhen> list = crfTemplateAnzhenService.getCrfTemplateAnzhenList(new HashedMap<String, Object>());
		//Excel excel = new Excel(filePath, fileName, sheetName);
		Excel excel = new Excel(filePath, fileNameSf, sheetNameSf);
		Integer beginCell=3;
		
		String str = WriteExcelCrfTemplateAnzhen.writeExcelByCompareEnglishName(excel, list, beginCell);
		System.out.println(str);
	}
	
	
}