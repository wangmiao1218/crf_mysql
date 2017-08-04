package com.gennlife.crf.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.gennlife.crf.bean.Excel;

/**
 * @Description: 对excel的处理
 * @author: wangmiao
 * @Date: 2017年6月9日 上午10:08:05
 */
public class ExcelUtils {

    /** 
    * @Title: searchKeyWord 
    * @Description: 搜索某一个文件中，指定列，是否包含某个关键字 ,储存在返回行号，不存在返回null
    * @param: Excel excel：传入excel
    * @param: int beginCell：列号（从0 开始）
    * @param: String keyWord：要搜索的关键字
    * @return: String
    * @throws 
    */
    public static Integer searchKeyWord(Excel excel,int beginCell,String keyWord) {  
    	//先判断keyWord是否为null
    	if (keyWord ==null) {
			return null;
		}
    	
        // 构造Workbook
    	Workbook workbook = excel.getWorkbook();  
  
        if (workbook == null){
        	return null;  //不存在
        }  
        
        //获取sheet
		Sheet sheet = workbook.getSheet(excel.getSheetName());
		Integer returnNum = null;
		
       // 循环读取指定列数据
 		for ( int rowNum= 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
 			Row row = sheet.getRow(rowNum);
 			
 			//指定 列beginCell
 			Cell cell = row.getCell(beginCell);
 			String value = cell.getStringCellValue();
 			if (keyWord.contains(value)) {  
 				returnNum= rowNum;
 			}
 		}
		return returnNum;
 		
    }  
	
	
	/** 
	* @Title: readContent 
	* @Description: 读任意坐标数据
	* @param: Excel excel :传入excel
	* @param: int beginRow :行号（从 0 算起）
	* @param: int beginCell :列号（从 0 算起）
	* @return: String
	* @throws 
	*/
	public static String readContent(Excel excel, int beginRow, int beginCell) {
		Workbook workbook = excel.getWorkbook();
		Sheet sheet = workbook.getSheet(excel.getSheetName());
		Row row = sheet.getRow(beginRow);
		Cell cell = row.getCell(beginCell);
		return cell.toString();
	}

	
	/** 
	* @Title: readTwoContentAndJudge 
	* @Description: 读取某行中，指定的两列值，判断是否相等
	* @param: Excel excel :传入excel
	* @param: Integer beginRow :行号（从 0 算起）
	* @param: Integer beginCell :前面列号（从 0 算起）
	* @param: Integer endCell：后面列号（从 0 算起）
	* @return: String 都为null或比较相等，则返回pass，反之返回no
	* @throws 
	*/
	public static String readTwoContentAndJudge(Excel excel, Integer beginRow, Integer beginCell,Integer endCell) {
		Workbook workbook = excel.getWorkbook();
		Sheet sheet = workbook.getSheet(excel.getSheetName());
		//根据行获取row
		Row row = sheet.getRow(beginRow);
		
		//分别根据列号获取对应的cell
		Cell beginCellContent = row.getCell(beginCell);
		Cell endCellContent = row.getCell(endCell);
		
		String valueString=null;
		
		//判断：都为空，也为pass
		if (beginCellContent==null && endCellContent ==null) {
			valueString="pass";
		}
		
		//任意一个为空则返回no
		if ((beginCellContent==null && endCellContent !=null) || (beginCellContent!=null && endCellContent ==null) ) {
			valueString="no";
		}
				
		//都不为null时，进行比较
		if (beginCellContent != null && endCellContent != null) {
			String beginCellStr = ListAndStringUtils.trimString(beginCellContent.toString());
			String endCellStr = ListAndStringUtils.trimString(endCellContent.toString());
			
			//判断
			if (beginCellStr.equals(endCellStr)) {
				valueString="pass";
			}else {
				valueString="no";
			}
		}
		
		return valueString;
	}

	
	/**
	* @Title: writeAndSaveContent 
	* @Description: 用于操作Excel，在任意坐标处写入数据并保存
	* @param: Excel excel :传入excel
	* @param: String newContent :内容
	* @param: int beginRow :行号（从 0 算起）
	* @param: int beginCell :列号（从 0 算起）
	* @return: void
	* @throws 
	*/
	public static void writeAndSaveContent(Excel excel,String newContent, int beginRow, int beginCell) {
		//获取行
		Workbook workbook = excel.getWorkbook();
		Sheet sheet = workbook.getSheet(excel.getSheetName());
		Row row = sheet.getRow(beginRow);

		if (null == row) {
			// 如果不做空判断，你必须让你的模板文件画好边框，beginRow和beginCell必须在边框最大值以内
			// 否则会出现空指针异常
			row = sheet.createRow(beginRow);
		}
		
		Cell cell = row.getCell(beginCell);
		if (null == cell) {
			cell = row.createCell(beginCell);
		}
		// 设置存入内容为字符串
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);

		// 向单元格中放入值
		cell.setCellValue(newContent);

		// 保存
		// 根据参数传入的数据文件路径和文件名称，组合出Excel数据文件的绝对路径，声明一个File文件对象
		File file = new File(excel.getFilePath() + "\\" + excel.getFileName());
		//建立输出流
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			workbook.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	}

	
}
