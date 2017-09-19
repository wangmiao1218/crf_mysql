package com.gennlife.crf.anzhen.add.xinxueguan.test;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.Select;

import com.gennlife.crf.utils.CreateWebDriver;
import com.gennlife.crf.utils.LoginCrfOfAnzhen;
import com.gennlife.crf.utils.QuitWebDriver;
import com.gennlife.crf.utils.SeleniumUtils;

public class TestAnzhenXinxueguan {

	@Test
	public void test01(){
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		String value = LoginCrfOfAnzhen.loginAndToAddOfMenZhenAndBasicInfoByPhantomJSDriver(driver);
		System.out.println(value);

		driver.findElementById("crf-data-tree_3_span").click();

		boolean b = SeleniumUtils.isElementPresent(driver, "u-crf-HYPERTENSION_HISTORY_OF_HYPERTENSION");
		System.out.println(b);
		
		// 联动字段
		if(!b){
			new Select(driver.findElementById("u-crf-HYPERTENSION_HAS_HYPERTENSION")).selectByValue("有");

			WebElement element = driver.findElementById("u-crf-HYPERTENSION_HISTORY_OF_HYPERTENSION");
			System.out.println("存在");
	    }
		
		
		new Select(driver.findElementById("u-crf-HYPERTENSION_HAS_HYPERTENSION")).selectByIndex(0);

		boolean b2 = SeleniumUtils.isElementPresent(driver, "u-crf-HYPERTENSION_REGULAR_MEDICATION_HISTORY");
		System.out.println(b2);
		
		
		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
	}

	

}