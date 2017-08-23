package com.gennlife.mengyujie;

import java.util.List;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.ExcelUtils;


/**
 * @Description: 
 * @author: wangmiao
 * @Date: 2017年8月17日 下午5:44:20 
 */
public class Yujie{

	public static String testYujie(Excel excelrx,Integer beginCellrx,Excel exceldc,Integer beginCelldc){
		List<String> list = ExcelUtils.readExcelOfList(excelrx,beginCellrx);
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i)!=null) {
				//搜索exceldc返回的行号
				Integer exceldcRowNum = ExcelUtils.searchKeyWordOfList(exceldc, beginCelldc, list.get(i));
				
				String exceldcenglish = null;
				if (exceldcRowNum!=null) {
					//读英文名称，大肠英文列号3
					exceldcenglish = ExcelUtils.readContent(exceldc, exceldcRowNum, 3);
				}
				
				Integer excelrxRowNum = null;
				if (exceldcenglish!=null) {
					//读excelrx行号
					excelrxRowNum = ExcelUtils.searchKeyWordOfList(excelrx, beginCellrx, list.get(i));
				}
				
				//写入乳腺英文列号3
				if (excelrxRowNum!=null) {
					ExcelUtils.writeAndSaveContent(excelrx, exceldcenglish, excelrxRowNum, 3);
				}
				
			}
			
		}
		
		
		return "写入完成。。。";
	}
	
	
	/** 
	* @Title: readTwoContentAndJudgeAndWriteResult 
	* @Description: 读excel两列值，并比较是否相等，在对应列写入比较结果，pass为相等，no为不相等
	* @param: @param excel ：出入的excel
	* @param: @param allRow  ：总共需要比较的行数
	* @param: @param beginCell  ：需要比较的列1（从0开始）
	* @param: @param endCell  ：需要比较的列2（从0开始）
	* @param: @param writeCell  ：写入结果的列（从0开始）
	* @param: @return :
	* @return: String
	* @throws 
	*/
	public static String readTwoContentAndJudgeAndWriteResult(Excel excel, Integer allRow, Integer beginCell,Integer endCell,Integer writeCell ) {
		for (int i = 0; i < allRow; i++) {
			String result = ExcelUtils.readTwoContentAndJudge(excel,i, beginCell,endCell);
			if (result!=null) {
				ExcelUtils.writeAndSaveContent(excel,result,i,writeCell);
			}
		}
		return "比较完成。。。";
	}

	
}
