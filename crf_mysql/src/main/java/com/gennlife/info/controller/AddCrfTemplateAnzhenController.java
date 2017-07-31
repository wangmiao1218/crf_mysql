package com.gennlife.info.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
	* @Title: addCrfTemplateAnzhen_Mz_Hzxx 
	* @Description: 添加门诊_患者信息(包括基本信息、个人病史、家族史、生活方式、本次入院前2周内药物治疗史)
	* @param: @throws Exception :
	* @return: String
	* @throws 
	*/
	@RequestMapping("addCrfTemplateAnzhen_Mz_Hzxx")
	public String addCrfTemplateAnzhen_Mz_Hzxx() throws Exception {
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
			AnzhenMethodByDriverAndIdXpath.globalMethod_Enum_MultiSelect(driver, "crf-data-tree_3_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("个人病史"));
			
			//家族史
			AnzhenMethodByDriverAndIdXpath.globalMethod_Enum_MultiSelect(driver, "crf-data-tree_4_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("家族史"));
			
			//生活方式
			AnzhenMethodByDriverAndIdXpath.globalMethod_Enum_MultiSelect(driver, "crf-data-tree_5_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("生活方式"));
			
			//本次入院前2周内药物治疗史
			AnzhenMethodByDriverAndIdXpath.globalMethod_Enum_MultiSelect(driver, "crf-data-tree_6_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("本次入院前2周内药物治疗史"));
		}

		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);

		return "redirect:/page/ok.html";
	}
	
	
	/** 
	* @Title: addCrfTemplateAnzhen_Mz_Jz 
	* @Description: 添加门诊_门诊-1（就诊-xxx等所有信息）
	* @param: @throws Exception :
	* @return: String
	* @throws 
	*/
	@RequestMapping("addCrfTemplateAnzhen_Mz_Jz")
	public String addCrfTemplateAnzhen_Mz_Jz() throws Exception {
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		String value = LoginCrfOfAnzhen.loginByPhantomJSDriver(driver);
		
		if ("登陆成功".contains(value)) {
			//已有基本信息的病例xpath
			driver.findElementByXPath(".//*[@id='case-list-container']/tbody/tr[3]/td[2]/a").click();
			// 得到当前窗口的set集合
			Set<String> winHandels = driver.getWindowHandles();
			// 将set集合存入list对象
			List<String> it = new ArrayList<String>(winHandels);
			// 切换到弹出的新窗口
			driver.switchTo().window(it.get(1));
			Thread.sleep(2000);
			
			//就诊－体格检查
			//AnzhenMethodByDriverAndIdXpath.globalMethod_Enum_MultiSelect(driver, "crf-data-tree_8_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("就诊－体格检查"));
			
			//就诊－24小时动态血压监测
			//AnzhenMethodByDriverAndIdXpath.globalMethod_Enum_MultiSelect(driver, "crf-data-tree_9_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("就诊－24小时动态血压监测"));
			
			//就诊－四肢血压及动脉弹性检查
			//AnzhenMethodByDriverAndIdXpath.globalMethod_Enum_MultiSelect(driver, "crf-data-tree_10_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("就诊－四肢血压及动脉弹性检查"));
			
			//就诊－电生理检查
			AnzhenMethodByDriverAndIdXpath.globalMethod_Enum_MultiSelect(driver, "crf-data-tree_11_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("就诊－电生理检查"));
			
			//就诊－超声检查
			//AnzhenMethodByDriverAndIdXpath.globalMethod_Enum_MultiSelect(driver, "crf-data-tree_12_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("就诊－超声检查"));
			
			//就诊－CT检查
			//AnzhenMethodByDriverAndIdXpath.globalMethod_Enum_MultiSelect(driver, "crf-data-tree_13_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("就诊－CT检查"));
			
			//就诊－眼底检查
			//AnzhenMethodByDriverAndIdXpath.globalMethod_Enum_MultiSelect(driver, "crf-data-tree_14_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("就诊－眼底检查"));
			
			//就诊－多导睡眠图(PSG)监测
			//AnzhenMethodByDriverAndIdXpath.globalMethod_Enum_MultiSelect(driver, "crf-data-tree_15_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("就诊－多导睡眠图(PSG)监测"));
			
			//就诊－实验室检验
			//AnzhenMethodByDriverAndIdXpath.globalMethod_Enum_MultiSelect(driver, "crf-data-tree_16_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("就诊－实验室检验"));
			
			//就诊－门诊带药
			//AnzhenMethodByDriverAndIdXpath.globalMethod_Enum_MultiSelect(driver, "crf-data-tree_17_span", crfTemplateAnzhenService.getCrfTemplateAnzhenListByBaseName("就诊－门诊带药"));
			
		}

		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);

		return "redirect:/page/ok.html";
	}
	
	
}
