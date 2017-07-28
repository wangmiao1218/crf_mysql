package com.gennlife.crf.utils;

import java.util.List;

import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.Select;

import com.gennlife.crf.bean.CrfTemplateAnzhen;

/**
 * @Description: 安贞环境，录入固定数据的通用的方法
 * @author: wangmiao
 * @Date: 2017年7月28日 下午4:58:39 
 */
public class AnzhenMethodByDriverAndIdXpath {

	
	/** 
	* @Title: globalMethod 
	* @Description: 只需传入driver、左侧点击的路径，以及list，根据数据库中配置，录入固定的数据
	* @param: @param driver
	* @param: @param idXpath 前台点击的左侧路径
	* @param: @param list
	* @param: @throws Exception :
	* @return: void
	* @throws 
	*/
	public static void globalMethod(PhantomJSDriver driver,String idXpath,List<CrfTemplateAnzhen> list) throws Exception{
		driver.findElementById(idXpath).click();
		Thread.sleep(1500);
		
		// 循环list
		for (int i = 0; i < list.size(); i++) {
			if ("枚举型".contains(list.get(i).getVariableType())) {
				new Select(driver.findElementById(list.get(i).getIdXpath())).selectByValue(list.get(i).getInputValue());
			}
			else if ("多选".contains(list.get(i).getVariableType())) {
				//for checkBox
				driver.findElementByXPath(list.get(i).getIdXpath()).click();
				for (int j = 1; j < Integer.parseInt(list.get(i).getDateFormat()); j++) {
					driver.findElementByXPath(list.get(i).getInputValue()+j+"]/a/label").click();
				}
			}
			else {
				driver.findElementById(list.get(i).getIdXpath()).clear();
				driver.findElementById(list.get(i).getIdXpath()).sendKeys(list.get(i).getInputValue());
			}
		}
		
		//循环后保存
		driver.findElementById("input-save").click();
		Thread.sleep(1000);
		driver.findElementByClassName("u-btn").click();
		Thread.sleep(1000);
		
	}

}
