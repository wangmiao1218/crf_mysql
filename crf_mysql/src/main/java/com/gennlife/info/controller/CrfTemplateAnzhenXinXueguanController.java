package com.gennlife.info.controller;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gennlife.crf.bean.CrfTemplateAnzhenXinXueguan;
import com.gennlife.crf.service.CrfTemplateAnzhenXinXueguanService;
import com.gennlife.crf.utils.AnzhenInputValueMethod;
import com.gennlife.crf.utils.CreateWebDriver;
import com.gennlife.crf.utils.ListAndStringUtils;
import com.gennlife.crf.utils.LoginCrfOfAnzhen;
import com.gennlife.crf.utils.QuitWebDriver;
import com.gennlife.crf.utils.SeleniumUtils;

/**
 * @Description: 安贞环境，心血管单病种，相关controller
 * @author: wangmiao
 * @Date: 2017年9月19日 上午11:24:41
 */
@Controller
@RequestMapping("crfTemplateAnzhenXinXueguanController")
public class CrfTemplateAnzhenXinXueguanController {

	@Autowired
	private CrfTemplateAnzhenXinXueguanService crfTemplateAnzhenXinXueguanService;

	/**
	 * @Title: verifyLinkageField
	 * @Description: 验证安贞心血管，联动字段
	 * @param: @throws Exception :
	 * @return: String
	 * @throws
	 */
	@RequestMapping("verifyLinkageField")
	public String verifyLinkageField() throws Exception {
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		//String value = LoginCrfOfAnzhen.loginAndToAddOfXinxueguanByPhantomJSDriver(driver);
		//使用安贞高血压进行测试
		String value = LoginCrfOfAnzhen.loginAndToAddOfMenZhenAndBasicInfoByPhantomJSDriver(driver);

		if ("添加页面".contains(value)) {
			//测试
			driver.findElementById("crf-data-tree_3_span").click();
			Thread.sleep(2000);
			crfTemplateAnzhenXinXueguanService
					.verifyLinkageFieldGeneralServiceMethod(driver,"个人病史");
			/*
			driver.findElementById("").click();
			Thread.sleep(2000);
			crfTemplateAnzhenXinXueguanService
					.verifyLinkageFieldGeneralServiceMethod(driver,"就诊－住院与诊断");
			
			driver.findElementById("").click();
			Thread.sleep(2000);
			crfTemplateAnzhenXinXueguanService
					.verifyLinkageFieldGeneralServiceMethod(driver,"就诊－体格检查");
			
			driver.findElementById("").click();
			Thread.sleep(2000);
			crfTemplateAnzhenXinXueguanService
					.verifyLinkageFieldGeneralServiceMethod(driver,"就诊－实验室检验");
			
			driver.findElementById("").click();
			Thread.sleep(2000);
			crfTemplateAnzhenXinXueguanService
					.verifyLinkageFieldGeneralServiceMethod(driver,"就诊－超声检查");
			
			driver.findElementById("").click();
			Thread.sleep(2000);
			crfTemplateAnzhenXinXueguanService
					.verifyLinkageFieldGeneralServiceMethod(driver,"就诊－其他诊断方法");
			
			driver.findElementById("").click();
			Thread.sleep(2000);
			crfTemplateAnzhenXinXueguanService
					.verifyLinkageFieldGeneralServiceMethod(driver,"就诊－生活质量");
			
			driver.findElementById("").click();
			Thread.sleep(2000);
			crfTemplateAnzhenXinXueguanService
					.verifyLinkageFieldGeneralServiceMethod(driver,"就诊－院内药物治疗");
			
			driver.findElementById("").click();
			Thread.sleep(2000);
			crfTemplateAnzhenXinXueguanService
					.verifyLinkageFieldGeneralServiceMethod(driver,"就诊－入院期间治疗");
			
			driver.findElementById("").click();
			Thread.sleep(2000);
			crfTemplateAnzhenXinXueguanService
					.verifyLinkageFieldGeneralServiceMethod(driver,"就诊－出院药物治疗");
			
			//随访
			driver.findElementByClassName("dropdown-toggle").click();
			driver.findElementById("add-followup").click();
			Thread.sleep(2000);
			crfTemplateAnzhenXinXueguanService
					.verifyLinkageFieldGeneralServiceMethod(driver,"随访");
			*/
		}

		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);

		return "redirect:/page/ok.html";
	}

	
	
	
}
