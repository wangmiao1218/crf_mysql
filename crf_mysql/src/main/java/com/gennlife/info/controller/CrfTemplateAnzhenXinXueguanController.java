package com.gennlife.info.controller;

import java.util.List;

import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gennlife.crf.bean.CrfTemplateStructure;
import com.gennlife.crf.service.CrfTemplateAnzhenXinXueguanService;
import com.gennlife.crf.service.CrfTemplateStructureService;
import com.gennlife.crf.utils.CreateWebDriver;
import com.gennlife.crf.utils.LoginCrfOfAnzhen;
import com.gennlife.crf.utils.QuitWebDriver;

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
	
	@Autowired
	private CrfTemplateStructureService crfTemplateStructureService;

	/**
	 * @Title: verifyLinkageField
	 * @Description: 验证安贞心血管，联动字段
	 * @param: @throws Exception :
	 * @return: String
	 * @throws
	 */
	@RequestMapping("verifyLinkageField")
	public String verifyLinkageField() throws Exception {
		//获取CrfTemplateStructure
		List<CrfTemplateStructure> list = crfTemplateStructureService.getCrfTemplateStructureListByHospitalDepartment("安贞心血管");
		
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		//String value = LoginCrfOfAnzhen.loginAndToAddOfXinxueguanByPhantomJSDriver(driver);
		//使用安贞高血压进行测试
		String value = LoginCrfOfAnzhen.loginAndToAddOfMenZhenAndBasicInfoByPhantomJSDriver(driver);

		if ("添加页面".contains(value)) {
			//测试
			for (int i = 0; i < list.size(); i++) {
				if ("随访".equals(list.get(i).getBaseName())) {
					driver.findElementByClassName("dropdown-toggle").click();
					driver.findElementById("add-followup").click();
					Thread.sleep(2000);
					crfTemplateAnzhenXinXueguanService
							.verifyLinkageFieldGeneralServiceMethod(driver,list.get(i).getBaseName());
				}else {
					driver.findElementById(list.get(i).getIdXpath()).click();
					Thread.sleep(2000);
					crfTemplateAnzhenXinXueguanService
							.verifyLinkageFieldGeneralServiceMethod(driver,list.get(i).getBaseName());
				}
			}
		}

		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);

		return "redirect:/page/ok.html";
	}

	
	
	
}
