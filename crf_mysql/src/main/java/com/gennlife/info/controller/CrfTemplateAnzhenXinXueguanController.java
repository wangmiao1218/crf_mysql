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
import com.gennlife.crf.utils.ListAndStringUtils;
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
	 * @Description: 验证安贞心血管，联动字段(适用于界面的方式，页面直接展示)
	 * @param: @throws Exception :
	 * @return: String
	 * @throws
	 */
	@RequestMapping("verifyLinkageField")
	public String verifyLinkageField() throws Exception {
		//获取CrfTemplateStructure
		List<CrfTemplateStructure> list = crfTemplateStructureService
					.getCrfTemplateStructureListByHospitalDepartment("安贞心血管");
		
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		String value = LoginCrfOfAnzhen.loginAndToAddOfXinxueguanByPhantomJSDriver(driver);
		
		if ("添加页面".contains(value) && list.size()>0) {
			//判断是否为组联动
			for (int i= 0; i< list.size(); i++) {
				//根据baseNamen，获取对应的list
				List<CrfTemplateAnzhenXinXueguan> listByBaseName = crfTemplateAnzhenXinXueguanService
						.getCrfTemplateAnzhenXinXueguanListByBaseName(list.get(i).getBaseName());
				//判断是否为组联动
				Boolean b = ListAndStringUtils.isCRFListLGroupLinkage(listByBaseName);
				if (b) {
					//System.out.println("组联动，人工测试");
					//测试前先清空联动字段：update 表 set 字段=null（不用delete）
					crfTemplateAnzhenXinXueguanService
							.updateCrfTemplateAnzhenXinXueguanListLinkageResultByBaseName(list.get(i).getBaseName());
					crfTemplateAnzhenXinXueguanService
							.verifyLinkageFieldGeneralServiceMethodWithGroupLinkage(list.get(i).getBaseName());
				}else {
					if (!"".equals(list.get(i).getBaseName().trim())
							&& "随访".equals(list.get(i).getBaseName())) {
						//随访
						driver.findElementByClassName("dropdown-toggle").click();
						driver.findElementById("add-followup").click();
						Thread.sleep(2000);
						//System.out.println("1:"+list.get(i).getBaseName());
					}else {
						driver.findElementById(list.get(i).getIdXpath()).click();
						Thread.sleep(2000);
						//System.out.println("2:"+list.get(i).getBaseName());
					}
					//测试前先清空联动字段：update 表 set 字段=null（不用delete）
					crfTemplateAnzhenXinXueguanService
							.updateCrfTemplateAnzhenXinXueguanListLinkageResultByBaseName(list.get(i).getBaseName());
					
					crfTemplateAnzhenXinXueguanService
							.verifyLinkageFieldGeneralServiceMethod(driver,list.get(i).getBaseName());
				}
			}//for
		}//if	
		
		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
		
		return "redirect:/page/ok.html";
	}

	
	/**
	 * @Title: verifyLinkageFieldReturnJSON
	 * @Description: 验证安贞心血管，联动字段(适用于接口测试方式，直接刚返回不成功的json)
	 * @param: @throws Exception :
	 * @return: List<CrfTemplateAnzhenXinXueguan>
	 * @throws
	 */
	@RequestMapping("verifyLinkageFieldReturnJSON")
	public List<CrfTemplateAnzhenXinXueguan> verifyLinkageFieldReturnJSON() throws Exception {
		//获取CrfTemplateStructure
		List<CrfTemplateStructure> list = crfTemplateStructureService
					.getCrfTemplateStructureListByHospitalDepartment("安贞心血管");
		
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		String value = LoginCrfOfAnzhen.loginAndToAddOfXinxueguanByPhantomJSDriver(driver);
		
		if ("添加页面".contains(value) && list.size()>0) {
			//判断是否为组联动
			for (int i= 0; i< list.size(); i++) {
				//根据baseNamen，获取对应的list
				List<CrfTemplateAnzhenXinXueguan> listByBaseName = crfTemplateAnzhenXinXueguanService
						.getCrfTemplateAnzhenXinXueguanListByBaseName(list.get(i).getBaseName());
				//判断是否为组联动
				Boolean b = ListAndStringUtils.isCRFListLGroupLinkage(listByBaseName);
				if (b) {
					//System.out.println("组联动，人工测试");
					//测试前先清空联动字段：update 表 set 字段=null（不用delete）
					crfTemplateAnzhenXinXueguanService
							.updateCrfTemplateAnzhenXinXueguanListLinkageResultByBaseName(list.get(i).getBaseName());
					crfTemplateAnzhenXinXueguanService
							.verifyLinkageFieldGeneralServiceMethodWithGroupLinkage(list.get(i).getBaseName());
				}else {
					if (!"".equals(list.get(i).getBaseName().trim())
							&& "随访".equals(list.get(i).getBaseName())) {
						//随访
						driver.findElementByClassName("dropdown-toggle").click();
						driver.findElementById("add-followup").click();
						Thread.sleep(2000);
						//System.out.println("1:"+list.get(i).getBaseName());
					}else {
						driver.findElementById(list.get(i).getIdXpath()).click();
						Thread.sleep(2000);
						//System.out.println("2:"+list.get(i).getBaseName());
					}
					//测试前先清空联动字段：update 表 set 字段=null（不用delete）
					crfTemplateAnzhenXinXueguanService
							.updateCrfTemplateAnzhenXinXueguanListLinkageResultByBaseName(list.get(i).getBaseName());
					
					crfTemplateAnzhenXinXueguanService
							.verifyLinkageFieldGeneralServiceMethod(driver,list.get(i).getBaseName());
				}
			}//for
		}//if	
		
		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
		
		List<CrfTemplateAnzhenXinXueguan> linkageFieldResultList = crfTemplateAnzhenXinXueguanService
						.getVerifyLinkageFieldResultList();
		
		System.out.println(linkageFieldResultList.size());
		
		return linkageFieldResultList;

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
		List<CrfTemplateStructure> list = crfTemplateStructureService
					.getCrfTemplateStructureListByHospitalDepartment("安贞心血管");
		
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		String value = LoginCrfOfAnzhen.loginAndToAddOfXinxueguanByPhantomJSDriver(driver);
		
		if ("添加页面".contains(value) && list.size()>0) {
			//判断是否为组联动
			for (int i= 0; i< list.size(); i++) {
				//根据baseNamen，获取对应的list
				List<CrfTemplateAnzhenXinXueguan> listByBaseName = crfTemplateAnzhenXinXueguanService
						.getCrfTemplateAnzhenXinXueguanListByBaseName(list.get(i).getBaseName());
				//判断是否为组联动
				Boolean b = ListAndStringUtils.isCRFListLGroupLinkage(listByBaseName);
				if (b) {
					System.out.println("组联动，人工测试");
					//测试前先清空联动字段：update 表 set 字段=null（不用delete）
					crfTemplateAnzhenXinXueguanService
							.updateCrfTemplateAnzhenXinXueguanListLinkageResultByBaseName(list.get(i).getBaseName());
					crfTemplateAnzhenXinXueguanService
							.verifyLinkageFieldGeneralServiceMethodWithGroupLinkage(list.get(i).getBaseName());
				}else {
					if (!"".equals(list.get(i).getBaseName().trim())
							&& "随访".equals(list.get(i).getBaseName())) {
						//随访
						driver.findElementByClassName("dropdown-toggle").click();
						driver.findElementById("add-followup").click();
						Thread.sleep(2000);
						System.out.println("1:"+list.get(i).getBaseName());
					}else {
						driver.findElementById(list.get(i).getIdXpath()).click();
						Thread.sleep(2000);
						System.out.println("2:"+list.get(i).getBaseName());
					}
					System.out.println("ok");
					
					//测试前先清空联动字段：update 表 set 字段=null（不用delete）
					crfTemplateAnzhenXinXueguanService
							.updateCrfTemplateAnzhenXinXueguanListLinkageResultByBaseName(list.get(i).getBaseName());
					
					crfTemplateAnzhenXinXueguanService
							.verifyLinkageFieldGeneralServiceMethod(driver,list.get(i).getBaseName());
				
				}
			}//for
		}//if	
		
		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
			
		List<CrfTemplateAnzhenXinXueguan> linkageFieldResultList = crfTemplateAnzhenXinXueguanService
						.getVerifyLinkageFieldResultList();
		
		System.out.println(linkageFieldResultList.size());
		
		return linkageFieldResultList;
	}
	
	
	
	/**失败   总是填no???????
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
