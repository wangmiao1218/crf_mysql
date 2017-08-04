package com.gennlife.crf.anzhen.add;

import java.util.List;

import com.gennlife.crf.bean.CrfTemplateAnzhen;
import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.ExcelUtils;

/**
 * @Description: 安贞环境，根据数据库，写入excel
 * @author: wangmiao
 * @Date: 2017年8月4日 上午9:11:45 
 */
public class WriteExcelCrfTemplateAnzhen{

	/** 
	* @Title: writeExcelByCompareEnglishName 
	* @Description: 读取数据库列表的englishname，比较excel中某列是否存在，存在则从数据库取出相应的值填入到excel对应列
	* @param: @param excel： 需要写入的excel
	* @param: @param list ：查询数据库的list
	* @param: @param beginCell ：传入的列号
	* @param: @throws Exception :
	* @return: String
	* @throws 
	*/
	public static String writeExcelByCompareEnglishName(Excel excel,List<CrfTemplateAnzhen> list,Integer beginCell) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Integer beginRow = ExcelUtils.searchKeyWord(excel,1,list.get(i).getEnglishName());
			
			//判断是否为null
			if (beginRow!=null) {
				//设计excel时，要确定写入几列
				ExcelUtils.writeAndSaveContent(excel,list.get(i).getChineseName(),beginRow,beginCell);
				ExcelUtils.writeAndSaveContent(excel,list.get(i).getEnglishName(),beginRow,beginCell+1);
				ExcelUtils.writeAndSaveContent(excel,list.get(i).getInputValue(),beginRow,beginCell+2);
			}
		}
		return "写入完成。。。";
	}
	
	
}
