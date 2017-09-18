package com.gennlife.crf.anzhen.add.gaoxueya.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gennlife.crf.anzhen.add.gaoxueya.Anzhen_Hzxx_AddBcryq2znzlyw;
import com.gennlife.crf.utils.CreateWebDriver;
import com.gennlife.crf.utils.LoginCrfOfAnzhen;
import com.gennlife.crf.utils.QuitWebDriver;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class TestAnzhen_Hzxx_AddBcryq2znzlyw {
	
	//添加完基本信息后，单病种页面显示的病人编号
	public static final String xpath = "/html/body/div/div[3]/div/div[3]/table/tbody/tr[7]/td[2]/a";
		
/*	
	@Test
	public void hzxx_AddBcryq2znzlyw_JyywAndTzywAndKfjtywAndYds() throws Exception {
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		LoginCrfOfAnzhen.loginByPhantomJSDriver(driver);
		Thread.sleep(3000);
		Anzhen_Hzxx_AddBcryq2znzlyw.hzxx_AddBcryq2znzlyw_JyywAndTzywAndKfjtywAndYds(driver, xpath);
		
		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
	}
	
	@Test
	public void hzxx_AddBcryq2znzlyw_Kfknyw() throws Exception {
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		LoginCrfOfAnzhen.loginByPhantomJSDriver(driver);
		Thread.sleep(3000);
		Anzhen_Hzxx_AddBcryq2znzlyw.hzxx_AddBcryq2znzlyw_Kfknyw(driver, xpath);
		
		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
	}
	
	@Test
	public void hzxx_AddBcryq2znzlyw_Kxxbyw() throws Exception {
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		LoginCrfOfAnzhen.loginByPhantomJSDriver(driver);
		Thread.sleep(3000);
		Anzhen_Hzxx_AddBcryq2znzlyw.hzxx_AddBcryq2znzlyw_Kxxbyw(driver, xpath);
		
		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
	}
	
	@Test
	public void hzxx_AddBcryq2znzlyw_KxlscywAndZcy() throws Exception {
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		LoginCrfOfAnzhen.loginByPhantomJSDriver(driver);
		Thread.sleep(3000);
		Anzhen_Hzxx_AddBcryq2znzlyw.hzxx_AddBcryq2znzlyw_KxlscywAndZcy(driver, xpath);
		
		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
	}*/
	
	
}