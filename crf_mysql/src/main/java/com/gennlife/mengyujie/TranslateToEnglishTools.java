package com.gennlife.mengyujie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.CreateWebDriver;
import com.gennlife.crf.utils.ExcelUtils;
import com.gennlife.crf.utils.ListAndStringUtils;
import com.gennlife.crf.utils.QuitWebDriver;

/**
 * @Description: 将中文翻译成英文
 * @author: wangmiao
 * @Date: 2017年9月7日 下午2:37:08 
 */
public class TranslateToEnglishTools{

	/** 
	* @Title: getChNamesList 
	* @Description: 根据结构，在crf模板中，获取中文列表
	* @param: @param excelmb 结构的excel
	* @param: @param excel 要获取的excel
	* @return: List<String> 中文名称的列表
	* @throws 
	*/
	public static void getChNamesList(Excel excelmb,Excel excel) {
		Integer chNameCellNum = ExcelUtils.searchKeyWordOfOneLine(excelmb, 0, "中文名称");
		Integer groupInfoCellNum = ExcelUtils.searchKeyWordOfOneLine(excelmb, 0, "组结构信息");
		
		//获取中文名称一列
		List<String> list = ExcelUtils.readExcelOfList(excelmb, chNameCellNum);
		
		//除去表头开始遍历
		for (int i = 1; i < list.size(); i++) {
			//设置excel的sheet值
			excel.setSheetName(list.get(i));
			//判断excel是否存在该sheet
			if (ExcelUtils.checkSheetOfExcelExist(excel)) {
				//继续判断为两组还是三组
				Integer rowNum = ExcelUtils.searchKeyWordOfListReturnRowNum(excelmb, chNameCellNum, list.get(i));
				String groupInfo = ExcelUtils.readContent(excelmb, rowNum, groupInfoCellNum);
				
				if ("两组".equals(groupInfo)) {
					//转到第二组的逻辑,获取中文名称
					//getChNamesListOfTwoGroups(excel);
					//转换
					
					//写入
				}
				
				if ("三组".equals(groupInfo)) {
					//转到第三组的逻辑
					//getChNamesListOfThreeGroups(excel);
				}
			}
			
			//如果对应的sheet不存在则继续
			if (!ExcelUtils.checkSheetOfExcelExist(excel)) {
				continue;
			}
		}
	}
	
	/** 
	* @Title: getChNamesListOfTwoGroups 
	* @Description: 两组的情况，在crf模板中，获取中文列表
	* @param: @param excel 传入excel
	* @return: List<String> 中文名称的列表
	* @throws 
	*/
	public static List<String> getChNamesListOfTwoGroups(Excel excel) {
		Integer twoGroupCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "第二组");
		Integer chNameCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "中文名");
		Integer enNameCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "英文名");
		
		//获取中文名称一列（用readExcelOfListReturnListMap，因为有重复值）(除表头)
		List<Map<Integer,String>> list = ExcelUtils.readExcelOfListReturnListMap(excel, linkageCellNum);
		for (int i = 1; i < list.size(); i++) {
			Map<Integer, String> map = list.get(i);
			//定义“联动”列的行号和内容（即：写入值时对应的行号）
			Integer writeContentRowNum=null;
			String allString=null;
			for (Map.Entry<Integer, String> entry : map.entrySet()) {  
				writeContentRowNum=entry.getKey();
				allString=entry.getValue();
			}
			
			//分割
			String[] strings = ListAndStringUtils.trimStringOfEqualSign(allString);
			if (strings.length==1 || strings.length==0) {
				continue;
			}
			//获取=前字段名的行号及英文名(由于有重复值，所以使用searchKeyWordOfListByOrderDescReturnRowNum，逆序查找离着最近的值)
			Integer chNameRowNum = ExcelUtils.searchKeyWordOfListByOrderDescReturnRowNum(excel, writeContentRowNum, chNameCellNum, strings[0]);
			if (chNameRowNum==null) {
				continue;
			}
			String fieldEnName = ExcelUtils.readContent(excel, chNameRowNum, enNameCellNum);
			
			//获取对应第二组的行号英文名(从上面字段名所在行号，往上查找，查最近一个有值的)
			Integer twoGroupRowNum = ExcelUtils.searchValueOfListByOrderDescReturnRowNum(excel, chNameRowNum, twoGroupCellNum);
			if (twoGroupRowNum==null) {
				continue;
			}
			String twoGroupEnName = ExcelUtils.readContent(excel,twoGroupRowNum,enNameCellNum);
			
			//最终内容
			String newContent="schema."+tableName+"."+twoGroupEnName+"."+fieldEnName;
			
			ExcelUtils.writeAndSaveContent(excel, newContent, writeContentRowNum, mainKeyCellNum);
			ExcelUtils.writeAndSaveContent(excel, strings[1], writeContentRowNum, mainValueCellNum);
		}
		
	}
	

	/** 
	* @Title: getChNamesListOfThreeGroups 
	* @Description: 两组的情况，在crf模板中，获取中文列表
	* @param: @param excel 传入excel
	* @return: List<String> 中文名称的列表
	* @throws 
	*/
	public static void getChNamesListOfThreeGroups(Excel excel) {
		Integer twoGroupCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "第二组");
		Integer threeGroupCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "第三组");
		Integer chNameCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "中文名");
		Integer enNameCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "英文名");
		
		//获取中文名称一列（用readExcelOfListReturnListMap，因为有重复值）(除表头)
		List<Map<Integer,String>> list = ExcelUtils.readExcelOfListReturnListMap(excel, linkageCellNum);
		for (int i = 1; i < list.size(); i++) {
			Map<Integer, String> map = list.get(i);
			//定义“联动”列的行号和内容（即：写入值时对应的行号）
			Integer writeContentRowNum=null;
			String allString=null;
			for (Map.Entry<Integer, String> entry : map.entrySet()) {  
				writeContentRowNum=entry.getKey();
				allString=entry.getValue();
			}
			
			//分割
			String[] strings = ListAndStringUtils.trimStringOfEqualSign(allString);
			if (strings.length==1 || strings.length==0) {
				continue;
			}
			
			//获取=前字段名的行号及英文名(由于有重复值，所以使用searchKeyWordOfListByOrderDescReturnRowNum，逆序查找离着最近的值)
			Integer chNameRowNum = ExcelUtils.searchKeyWordOfListByOrderDescReturnRowNum(excel, writeContentRowNum, chNameCellNum, strings[0]);
			if (chNameRowNum==null) {
				continue;
			}
			String fieldEnName = ExcelUtils.readContent(excel, chNameRowNum, enNameCellNum);
			
			//获取对应第二组的行号、英文名(从上面字段名所在行号，往上查找，查最近一个有值的)
			Integer twoGroupRowNum = ExcelUtils.searchValueOfListByOrderDescReturnRowNum(excel, chNameRowNum, twoGroupCellNum);
			if (twoGroupRowNum==null) {
				continue;
			}
			String twoGroupEnName = ExcelUtils.readContent(excel,twoGroupRowNum,enNameCellNum);
			
			//获取对应第三组的行号、英文名(从上面字段名所在行号，往上查找，查最近一个有值的，直到到第二组名称的行号)
			Integer threeGroupRowNum = ExcelUtils.searchValueOfListBetweenTwoRowNumByOrderDescReturnRowNum(excel, twoGroupRowNum, chNameRowNum, threeGroupCellNum);
			
			//最终内容
			String newContent=null;
			//可能没有第三组情况
			if (threeGroupRowNum==null) {
				newContent="schema."+tableName+"."+twoGroupEnName+"."+fieldEnName;
			}else {
				String threeGroupEnName = ExcelUtils.readContent(excel,threeGroupRowNum,enNameCellNum);
				newContent="schema."+tableName+"."+twoGroupEnName+"."+threeGroupEnName+"."+fieldEnName;
			}
			
			ExcelUtils.writeAndSaveContent(excel, newContent, writeContentRowNum, mainKeyCellNum);
			ExcelUtils.writeAndSaveContent(excel, strings[1], writeContentRowNum, mainValueCellNum);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	public static String translateToEnglish(Excel excel) throws Exception{
		//先定义两个list，分别存放中文、翻译后的英文
		List<String> inputList = new ArrayList<String>();
		List<String> outputList = new ArrayList<String>();
		
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		driver.navigate().to("http://fanyi.baidu.com/translate#zh/en/");
		
		String output=null;
		//开始处理翻译，并返回list
		for (int i = 0; i < inputList.size(); i++) {
			driver.findElementById("baidu_translate_input").clear();
			driver.findElementById("baidu_translate_input").sendKeys(inputList.get(i));
			driver.findElementById("translate-button").click();
			Thread.sleep(500);
			//获取翻译后的值
			output=driver.findElementByXPath("//div[@class='output-bd']//p//span").getText();
			
			outputList.add(output);
		}
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
		
		//循环插入到excel中
		for (int i = 0; i < inputList.size(); i++) {
			
		}
		
		return "OK";
		
	}
	
	
	
}
