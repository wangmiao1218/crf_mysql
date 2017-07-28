package com.gennlife.info.controller;

import java.util.List;

import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gennlife.crf.bean.CrfTemplateAnzhen;
import com.gennlife.crf.service.CrfTemplateAnzhenService;
import com.gennlife.crf.utils.CreateWebDriver;
import com.gennlife.crf.utils.LoginCrfOfAnzhen;
import com.gennlife.crf.utils.LoginCrfOfYantai;
import com.gennlife.crf.utils.QuitWebDriver;
import com.gennlife.crf.utils.RandomValue;


/**
 * @Description:  安贞环境，添加单病种数据库中crf
 * @author: wangmiao
 * @Date: 2017年7月28日 上午8:54:50 
 */
@Controller
@RequestMapping("addCrfTemplateAnzhenController")
public class AddCrfTemplateAnzhenController{

	@Autowired
	private CrfTemplateAnzhenService crfTemplateAnzhenService;

	/** 
	* @Title: addCrfTemplateAnzhen_Mz_Jbxx 
	* @Description: 添加门诊_基本信息
	* @param: @throws Exception :
	* @return: String
	* @throws 
	*/
	@RequestMapping("addCrfTemplateAnzhen_Mz_Jbxx")
	public String addCrfTemplateAnzhen_Mz_Jbxx() throws Exception {
		// 获取所有基本信息list
		List<CrfTemplateAnzhen> list = crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("基本信息");

		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		String value = LoginCrfOfAnzhen.loginAndToAddOfMenZhenAndBasicInfoByPhantomJSDriver(driver);
		if ("添加页面".contains(value)) {
			// 循环list
			for (int i = 0; i < list.size(); i++) {
				if ("枚举型".contains(list.get(i).getVariableType())) {
					new Select(driver.findElementById(list.get(i).getIdXpath())).selectByValue(list.get(i).getInputValue());
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
			Thread.sleep(2000);
			
		}

		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);

		return "redirect:/page/ok.html";
	}
	
	
}
