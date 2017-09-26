package com.gennlife.info.controller;

import java.util.List;

import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gennlife.crf.bean.CrfTemplateAnzhenXinXueguan;
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
		System.out.println(list);
		
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		//String value = LoginCrfOfAnzhen.loginAndToAddOfXinxueguanByPhantomJSDriver(driver);
		//使用安贞高血压进行测试
		String value = LoginCrfOfAnzhen.loginAndToAddOfMenZhenAndBasicInfoByPhantomJSDriver(driver);

		if ("添加页面".contains(value) && list.size()>1 ) {
			//测试
			for (int i = 0; i < list.size(); i++) {
				//测试
				if (!"".equals(list.get(i).getBaseName()) && !" ".equals(list.get(i).getBaseName())) {
					driver.findElementById(list.get(i).getIdXpath()).click();
					Thread.sleep(2000);
					crfTemplateAnzhenXinXueguanService
							.verifyLinkageFieldGeneralServiceMethod(driver,list.get(i).getBaseName());
				}else if (!"".equals(list.get(i).getBaseName()) && !" ".equals(list.get(i).getBaseName()) 
						&& "随访".equals(list.get(i).getBaseName())) {
					driver.findElementByClassName("dropdown-toggle").click();
					driver.findElementById("add-followup").click();
					Thread.sleep(2000);
					crfTemplateAnzhenXinXueguanService
							.verifyLinkageFieldGeneralServiceMethod(driver,list.get(i).getBaseName());
				}else {
					continue;
				}
			}
		}

		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);

		return "redirect:/page/ok.html";
	}

	
	
	/**
	 * @Title: verifyLinkageField_test
	 * @Description: 验证安贞心血管，联动字段(测试返回json:直接加@ResponseBody注解，返回json格式的list)
	 * @param: @throws Exception :
	 * @return: List<CrfTemplateAnzhenXinXueguan>
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("verifyLinkageField_test")
	public List<CrfTemplateAnzhenXinXueguan> verifyLinkageField_test() throws Exception {
		//获取CrfTemplateStructure
		List<CrfTemplateStructure> list = crfTemplateStructureService.getCrfTemplateStructureListByHospitalDepartment("安贞心血管");
		
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		String value = LoginCrfOfAnzhen.loginAndToAddOfXinxueguanByPhantomJSDriver(driver);
		
		if ("添加页面".contains(value) && list.size()>1 ) {
			//测试
			for (int i = 0; i < list.size(); i++) {
				//测试
				if (!"".equals(list.get(i).getBaseName()) && !" ".equals(list.get(i).getBaseName())
						&& "就诊－超声检查".equals(list.get(i).getBaseName())) {
					driver.findElementById(list.get(i).getIdXpath()).click();
					Thread.sleep(2000);
					
					System.out.println(list.get(i).getBaseName());
					
					//测试前先清空联动字段：update 表 set 字段=null（不用delete）
					crfTemplateAnzhenXinXueguanService.
							updateCrfTemplateAnzhenXinXueguanListLinkageResultByBaseName(list.get(i).getBaseName());
					
					crfTemplateAnzhenXinXueguanService
							.verifyLinkageFieldGeneralServiceMethod(driver,list.get(i).getBaseName());
				}else {
					continue;
				}
			}
		}
		
		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
		
		List<CrfTemplateAnzhenXinXueguan> listByBaseName = crfTemplateAnzhenXinXueguanService.
					getCrfTemplateAnzhenXinXueguanListByBaseName("就诊－超声检查");
		
		for (int i = 0; i < listByBaseName.size(); i++) {
			System.out.println(listByBaseName.get(i).getLinkageResult());
		}
		
		return listByBaseName;
		
	}
	
	
	
	/**失败   总是填no
	 * @Title: verifyLinkageFieldWithBaseName
	 * @Description: 带baseName参数的，验证安贞心血管，联动字段验证(测试返回json:直接加@ResponseBody注解，返回json格式的list)
	 * @param: @throws Exception :
	 * @return: List<CrfTemplateAnzhenXinXueguan>
	 * @throws
	 */
	/*
	@ResponseBody
	@RequestMapping(value="/verifyLinkageFieldWithBaseName",method=RequestMethod.GET)
	public List<CrfTemplateAnzhenXinXueguan> verifyLinkageFieldWithBaseName(@RequestParam("baseName") String baseName) throws Exception {
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		String value = LoginCrfOfAnzhen.loginAndToAddOfXinxueguanByPhantomJSDriver(driver);
		
		if ("添加页面".contains(value)) {
			//测试前先清空联动字段：update 表 set 字段=null（不用delete）
			crfTemplateAnzhenXinXueguanService.updateCrfTemplateAnzhenXinXueguanListLinkageResultByBaseName(baseName);
			//测试
			crfTemplateAnzhenXinXueguanService.verifyLinkageFieldGeneralServiceMethod(driver,baseName);
		}
		
		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
		//返回
		List<CrfTemplateAnzhenXinXueguan> list = crfTemplateAnzhenXinXueguanService.getCrfTemplateAnzhenXinXueguanListByBaseName(baseName);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getLinkageResult());
		}
		return list;
		
	}
	*/
	
	
	
}
