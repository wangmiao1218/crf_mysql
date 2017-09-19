package com.gennlife.crf.utils;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

/**
 * @Description: selenium相关工具方法
 * @author: wangmiao
 * @Date: 2017年9月19日 上午10:43:18 
 */
public class SeleniumUtils {
	
	/** 
	* @Title: isElementPresent 
	* @Description: 判断页面元素是否存在
	* @param: @param driver
	* @param: @param idXpath  
	* @return: Boolean 返回布尔类型
	* @throws 
	*/
	public static Boolean isElementPresent(PhantomJSDriver driver, String idXpath) {
		Boolean status = false;
		try {
			driver.findElementById(idXpath);
			//System.out.println(idXpath + " is appeard!");
			status = true;
		//注意坑：是否为org.openqa.selenium.NoSuchElementException
		} catch (NoSuchElementException e) {
			status = false;
			//System.out.println("'" + idXpath + "' doesn't exist!");
		}
		return status;
	}
	
	
}
