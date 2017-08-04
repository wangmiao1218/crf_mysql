package com.gennlife.crf.anzhen.add.test;

import java.util.List;

import org.apache.commons.collections4.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gennlife.crf.bean.CrfTemplateAnzhen;
import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.service.CrfTemplateAnzhenService;
import com.gennlife.crf.utils.ExcelUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class TestExcelCrfTemplateAnzhen {
	
	@Autowired
	private CrfTemplateAnzhenService crfTemplateAnzhenService;

	private String filePath = "E:\\安贞\\！安贞数据导出";
	private String fileName = "test01.xlsx";
	private String sheetName = "Sheet1";
	
	@Test
	public void test() throws Exception{
		List<CrfTemplateAnzhen> list = crfTemplateAnzhenService.getCrfTemplateAnzhenList(new HashedMap<String, Object>());
		Excel excel = new Excel(filePath, fileName, sheetName);
		
		for (int i = 0; i < list.size(); i++) {
			Integer beginRow = ExcelUtils.searchKeyWord(excel,1,list.get(i).getEnglishName());
			
			//判断是否为null
			if (beginRow!=null) {
				ExcelUtils.writeAndSaveContent(excel,list.get(i).getChineseName(),beginRow,3);
				ExcelUtils.writeAndSaveContent(excel,list.get(i).getEnglishName(),beginRow,4);
				ExcelUtils.writeAndSaveContent(excel,list.get(i).getInputValue(),beginRow,5);
			}
			
		}
		System.out.println("写入完成。。。");
	}
	
	
}