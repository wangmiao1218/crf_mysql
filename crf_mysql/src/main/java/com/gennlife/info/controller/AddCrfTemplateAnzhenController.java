package com.gennlife.info.controller;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gennlife.crf.bean.CrfTemplateAnzhen;
import com.gennlife.crf.service.CrfTemplateAnzhenService;
import com.gennlife.crf.utils.AnzhenMethodByDriverAndIdXpath;
import com.gennlife.crf.utils.CreateWebDriver;
import com.gennlife.crf.utils.LoginCrfOfAnzhen;
import com.gennlife.crf.utils.QuitWebDriver;
import com.sun.tools.javac.util.Assert;


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
		List<CrfTemplateAnzhen> jbxxList = crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("基本信息");

		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		String value = LoginCrfOfAnzhen.loginAndToAddOfMenZhenAndBasicInfoByPhantomJSDriver(driver);
		
		if ("添加页面".contains(value)) {
			// 循环jbxxList
			for (int i = 0; i < jbxxList.size(); i++) {
				if ("枚举型".contains(jbxxList.get(i).getVariableType())) {
					new Select(driver.findElementById(jbxxList.get(i).getIdXpath())).selectByValue(jbxxList.get(i).getInputValue());
				}
				else {
					if ("调查时间".contains(jbxxList.get(i).getChineseName())) {
						driver.findElementById(jbxxList.get(i).getIdXpath()).click();
						driver.findElementByXPath(".//*[@id='ui-datepicker-div']/table/tbody/tr[1]/td[6]/a").click();
					}
					else if ("出生日期".contains(jbxxList.get(i).getChineseName())) {
						driver.findElementById(jbxxList.get(i).getIdXpath()).click();
						driver.findElementByXPath(".//*[@id='ui-datepicker-div']/div/div/select[1]").click();
						driver.findElementByXPath(".//*[@id='ui-datepicker-div']/div/div/select[1]/option[82]").click();//1998
						driver.findElementByXPath(".//*[@id='ui-datepicker-div']/table/tbody/tr[1]/td[3]/a").click();
					}
					else {
						driver.findElementById(jbxxList.get(i).getIdXpath()).clear();
						driver.findElementById(jbxxList.get(i).getIdXpath()).sendKeys(jbxxList.get(i).getInputValue());
					}
				}
			}
			
			//循环后保存
			driver.findElementById("input-save").click();
			Thread.sleep(1000);
			driver.findElementByClassName("u-btn").click();
			Thread.sleep(1000);
			
			//个人病史
			AnzhenMethodByDriverAndIdXpath.globalMethod(driver, "crf-data-tree_3_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("个人病史"));
			
			//家族史
			AnzhenMethodByDriverAndIdXpath.globalMethod(driver, "crf-data-tree_4_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("家族史"));
			
			//生活方式
			AnzhenMethodByDriverAndIdXpath.globalMethod(driver, "crf-data-tree_5_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("生活方式"));
			
			//本次入院前2周内药物治疗史
			AnzhenMethodByDriverAndIdXpath.globalMethod(driver, "crf-data-tree_6_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("本次入院前2周内药物治疗史"));
			
			
			
		}

		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);

		return "redirect:/page/ok.html";
	}
	
	
}
