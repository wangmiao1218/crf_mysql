package com.gennlife.selenium.test;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.CreateWebDriver;
import com.gennlife.crf.utils.LoginCrfOfAnzhen;
import com.gennlife.crf.utils.QuitWebDriver;
import com.gennlife.selenium.CrfTemplateVerifyMinMaxAlertValueField;

public class TestCrfTemplateVerifyMinMaxAlertValueField {

	private String filePath = "E:\\安贞\\！安贞心血管\\test\\excel";
	private String fileName = "test-模板结构-安贞高血压.xlsx";
	
	private String fileName2 = "test-安贞高血压CRF模版1.1.22-2017-07-17.xls";
	private String sheetName = "总体结构";
	
	@Test
	public void verifyMinMaxAlertValueField() throws Exception{
		Excel excelmb = new Excel(filePath, fileName, sheetName);
		Excel excel = new Excel(filePath, fileName2, sheetName);
		CrfTemplateVerifyMinMaxAlertValueField.verifyMinMaxAlertValueField(excelmb, excel);
		System.out.println("ok");
	}
	
	
	
	@Test
	public void placeholder() throws Exception{
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		//
		String value = LoginCrfOfAnzhen.loginByPhantomJSDriver(driver);
		Thread.sleep(2000);
		if ("登陆成功".equals(value)) {
			//到全字段的病例页面（目前是第一个病例）
			driver.findElementByXPath(".//*[@id='case-list-container']/tbody/tr[1]/td[2]/a").click();
			
			String currentWindow = driver.getWindowHandle();// 获取当前窗口句柄
		    Set<String> handles = driver.getWindowHandles();// 获取所有窗口句柄
		    Iterator<String> it = handles.iterator();
		    while (it.hasNext()) {
		        if (currentWindow == it.next()) {
		            continue;
		        }
		        driver = (PhantomJSDriver) driver.switchTo().window(it.next());// 切换到新窗口
		    }
			Thread.sleep(2000);
			//测试
			driver.findElementById("crf-data-tree_3_span").click();
			Thread.sleep(1500);
			//测试
			//处理页面中的范围的
			String idXpath ="u-crf-HYPERTENSION_HIGHEST_BLOOD_PRESSURE_LEVEL_DIASTOLIC_PRESSURE";
			String webMinMaxAlertValue =driver.findElementById(idXpath).getAttribute("placeholder");
			
			System.out.println(webMinMaxAlertValue);
		}
		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);

		System.out.println("ok");
	}
	
}