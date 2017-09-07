package com.gennlife.mengyujie.test;

import org.junit.Test;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.CreateWebDriver;
import com.gennlife.crf.utils.QuitWebDriver;
import com.gennlife.mengyujie.WriteSchemaCrfTemplateOfMyj;

public class TestTranslateToEnglishTools {

	private String filePath = "E:\\yujie\\2";
	private String fileName = "模板结构-乳腺癌.xlsx";
	private String sheetName = "总体结构";
	
	private String fileName2 = "test.xlsx";
	private String sheetName2 = "影像学检查";
	
	@Test
	public void testSearch() throws Exception{
		String input="院内是否服用醛固酮拮抗剂";
		String output=null;
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		driver.navigate().to("http://fanyi.baidu.com/translate#zh/en/");
		driver.findElementById("baidu_translate_input").clear();
		driver.findElementById("baidu_translate_input").sendKeys(input);
		driver.findElementById("translate-button").click();
		Thread.sleep(500);
		//获取翻译后的值
		output=driver.findElementByXPath("//div[@class='output-bd']//p//span").getText();
		
		System.out.println(output);
		
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
	}
	
	
}