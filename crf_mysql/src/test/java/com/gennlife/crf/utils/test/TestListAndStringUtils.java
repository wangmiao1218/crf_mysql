package com.gennlife.crf.utils.test;

import java.util.List;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.Select;
import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.CreateWebDriver;
import com.gennlife.crf.utils.ExcelUtils;
import com.gennlife.crf.utils.ListAndStringUtils;
import com.gennlife.crf.utils.LoginCrfOfAnzhen;
import com.gennlife.crf.utils.QuitWebDriver;

public class TestListAndStringUtils {
	
	private String filePath = "E:\\安贞\\！安贞心血管";
	private String fileName = "参会医院名称更改0923－添加上级科室.xlsx";
	private String sheetName = "Sheet1";
	
	@Test
	public void compareTwoListReturnDiffrent() throws Exception{
		Excel excel = new Excel(filePath, fileName, sheetName);
		List<String> list = ExcelUtils.readExcelOfList(excel, 0);
		List<String> listExcel = list.subList(1, list.size());
		
		
		System.out.println(listExcel.size());
		for (int i = 0; i < listExcel.size(); i++) {
			System.out.println(listExcel.get(i));
		}
		System.out.println("===============");
		
		
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		LoginCrfOfAnzhen.loginByPhantomJSDriver(driver);
		Thread.sleep(5000);
		//去页面 遍历下拉列表所有选项
		Select selall = new Select(driver.findElementByXPath(".//*[@id='crf-lab']/select"));
		//封装成List<WebElement>
		List<WebElement> lw= selall.getOptions();
		// 关闭driver
		//QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
		
		List<String> listWeb= ListAndStringUtils.listWebElementToListString(lw);
		System.out.println(listWeb.size());
		for (int i = 0; i < listWeb.size(); i++) {
			System.out.println(listWeb.get(i));
		}
		System.out.println("===============");
		
		List<String> returnDiffrent = ListAndStringUtils.compareTwoListReturnDiffrent(listExcel, listWeb);
		System.out.println(returnDiffrent.size());
		for (int i = 0; i < returnDiffrent.size(); i++) {
			System.out.println(returnDiffrent.get(i));
		}
		System.out.println("===============");
		
	}

	
	@Test
	public void displayMainKeyToEnglishName(){
		String displayMainKey="schema.Hospitalization.GENERAL_SITUATION.CONSOLIDATION";
		String value = ListAndStringUtils.displayMainKeyToEnglishName(displayMainKey);
		System.out.println(value);
	}
	
	@Test
	public void valueSpiltToStringList(){
		String value="就诊.就诊基本信息.入院（就诊）时间；就诊.就诊基本信息.挂号日期";
		List<String> list = ListAndStringUtils.valueSpiltToStringList(value);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
	@Test
	public void chNamesListFilter(){
		String value=" 剂  量（mg/天）a，d ff,d sdf,dfdsf，fsf、bc：1.2.3.bcd？";
		//去掉中英文（）之间
		if (value.indexOf("（")!=-1) {
			value=value.replaceAll(value.substring(value.indexOf("（"),value.indexOf("）")+1),"");
		}
		if (value.indexOf("(")!=-1) {
			value=value.replaceAll(value.substring(value.indexOf("("),value.indexOf(")")+1),"");
		}
		//去掉中英文:之后
		if (value.indexOf("：")!=-1) {
			value = value.substring(0, value.indexOf("："));
		}
		if (value.indexOf(":")!=-1) {
			value = value.substring(0, value.indexOf(":"));
		}
		
		//去掉中英文？之后
		if (value.indexOf("？")!=-1) {
			value = value.substring(0, value.indexOf("？"));
		}
		if (value.indexOf("?")!=-1) {
			value = value.substring(0, value.indexOf("?"));
		}
		
		//删掉、
		if (value.indexOf("、")!=-1) {
			value = value.replace("、",""); 
		}
		
		//删掉空格
		if (value.indexOf(" ")!=-1) {
			value = value.replace(" ",""); 
		}
		
		//删掉中引文，
		if (value.indexOf("，")!=-1) {
			value = value.replace("，","");
		}
		if (value.indexOf(",")!=-1) {
			value = value.replace(",","");
		}
		
		System.out.println(value);
	}
	
	@Test
	public void trimStringOfEqualSign(){
		String value="有无疾病史=有";
		String[] strings = ListAndStringUtils.trimStringOfEqualSign(value);
		System.out.println(strings.length);
		System.out.println(strings[0]);
		System.out.println(strings[1]);
	}
	
	@Test
	public void stringToSubstringReturnFilePath(){
		String value="E:\\yujie\\2\\模板结构-乳腺癌.xlsx";
		String str = ListAndStringUtils.stringToSubstringReturnFilePath(value);
		System.out.println(str);
	}
	
	@Test
	public void stringToSubstringReturnFileName(){
		String value="E:\\yujie\\2\\test.xlsx";
		String str = ListAndStringUtils.stringToSubstringReturnFileName(value);
		System.out.println(str);
	}
	
	@Test
	public void StringListReturnRandomString(){
		String value="I型糖尿病;II型糖尿病";
		String string = ListAndStringUtils.stringListReturnRandomString(value);
		System.out.println(string);
	}
	
	
}