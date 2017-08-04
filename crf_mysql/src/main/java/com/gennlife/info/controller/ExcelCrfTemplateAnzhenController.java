package com.gennlife.info.controller;

import java.util.List;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gennlife.crf.bean.CrfTemplateAnzhen;
import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.service.CrfTemplateAnzhenService;
import com.gennlife.crf.utils.ExcelUtils;

/**
 * @Description: 数据库与excel之间判断
 * @author: wangmiao
 * @Date: 2017年8月4日 上午9:11:45 
 */
@Controller
@RequestMapping("excelCrfTemplateAnzhenController")
public class ExcelCrfTemplateAnzhenController{

	@Autowired
	private CrfTemplateAnzhenService crfTemplateAnzhenService;

	private String filePath = "E:\\安贞\\！安贞数据导出";
	private String fileName = "test.xlsx";
	private String sheetName = "Sheet1";
	
	
	/** 
	* @Title: writeExcelByCompareEnglishName 
	* @Description: 读取数据库列表的englishname，比较excel中某列是否存在，存在则从数据库取出相应的值填入到excel
	* @param: @throws Exception :
	* @return: String
	* @throws 
	*/
	@RequestMapping("writeExcelByCompareEnglishName")
	public String writeExcelByCompareEnglishName() throws Exception {
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
		
		return "redirect:/page/ok.html";
	}
	
	
	
}
