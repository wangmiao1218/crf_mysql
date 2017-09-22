package com.gennlife.selenium;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.utils.CreateWebDriver;
import com.gennlife.crf.utils.ExcelUtils;
import com.gennlife.crf.utils.LoginCrfOfAnzhen;
import com.gennlife.crf.utils.QuitWebDriver;
import com.gennlife.crf.utils.SeleniumUtils;

/**
 * @Description: 验证页面允许最大值最小值
 * （注意：因为有联动，所以在页面创建时候，需要传入一个全字段的病例。后期会把联动加上，只需要填写必填字段）
 * @author: wangmiao
 * @Date: 2017年9月22日 上午10:24:13 
 */
public class CrfTemplateVerifyMinMaxAlertValueField{

	/** 
	* @Title: verifyMinMaxAlertValueField 
	* @Description: 验证页面允许最大值最小值
	* @param: @param excelmb
	* @param: @param excel
	* @return: void
	* @throws 
	*/
	public static void verifyMinMaxAlertValueField(Excel excelmb,Excel excel) throws Exception {
		Integer chNameCellNum = ExcelUtils.searchKeyWordOfOneLine(excelmb, 0, "中文名称");
		Integer idXpathCellNum = ExcelUtils.searchKeyWordOfOneLine(excelmb, 0, "idXpath");
		//获取中文名称一列
		List<String> list = ExcelUtils.readExcelOfList(excelmb, chNameCellNum);
		
		//除去表头开始遍历
		for (int i = 1; i < list.size(); i++) {
			//设置excel的sheet值
			excel.setSheetName(list.get(i));
			//判断excel是否存在该sheet
			if (ExcelUtils.checkSheetOfExcelExist(excel)) {
				//获取模板中对应的idXpath，传入下方
				Integer rowNum = ExcelUtils.searchKeyWordOfListReturnRowNum(excelmb, chNameCellNum, list.get(i));
				String idXpath = ExcelUtils.readContent(excelmb, rowNum, idXpathCellNum);
				//存在则进行去验证
				verifyMinMaxAlertValueFieldOfWebDriver(excel,idXpath);
			}
			
			//如果对应的sheet不存在则继续
			if (!ExcelUtils.checkSheetOfExcelExist(excel)) {
				continue;
			}
		}
	}
	
	
	/** 
	* @Title: verifyMinMaxAlertValueFieldOfWebDriver 
	* @Description: 验证页面允许最大值最小值，去页面创建webDriver，相当于每个页面创建一个连接
	* @param: @param excel
	* @param: @param idXpath
	* @param: @throws Exception :
	* @return: void
	* @throws 
	*/
	public static void verifyMinMaxAlertValueFieldOfWebDriver(Excel excel,String idXpath) throws Exception {
		// 登录并到add页面
		PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
		//
		String value = LoginCrfOfAnzhen.loginByPhantomJSDriver(driver);
		Thread.sleep(2000);
		//使用安贞高血压进行测试

		if ("登陆成功".equals(value)) {
			//到全字段的病例页面（目前是第一个病例）
			driver.findElementByXPath(".//*[@id='case-list-container']/tbody/tr[1]/td[2]/a").click();
			
			String currentWindow = driver.getWindowHandle();// 获取当前窗口句柄
		    Set<String> handles = driver.getWindowHandles();// 获取所有窗口句柄
		    Iterator<String> it = handles.iterator();
		    while (it.hasNext()) {
		        if (currentWindow == it.next()) {
		            continue;
		        }
		        driver = (PhantomJSDriver) driver.switchTo().window(it.next());// 切换到新窗口
		    }
			Thread.sleep(2000);
			
			//测试
			driver.findElementById(idXpath).click();
			Thread.sleep(1500);
			//验证
			verifyMinMaxAlertValueFieldOfExcel(driver, excel);		
		}

		// 关闭driver
		QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
	}
		

	
	/** 
	* @Title: verifyMinMaxAlertValueFieldOfExcel 
	* @Description: 验证页面允许最大值最小值，去excel遍历所有字段
	* @param: @param driver
	* @param: @param excel
	* @param: @throws Exception :
	* @return: void
	* @throws 
	*/
	public static void verifyMinMaxAlertValueFieldOfExcel(PhantomJSDriver driver,Excel excel) throws Exception {
		Integer minAlertValueCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "__minAlertValue");
		Integer maxAlertValueCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "__maxAlertValue");
		Integer idXpathCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "idXpath");
		Integer minMaxAlertOutputValueCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "minMaxAlertOutputValue");
		Integer minMaxAlertResultCellNum = ExcelUtils.searchKeyWordOfOneLine(excel, 0, "minMaxAlertResult");
		
		//防止没有写字段的情况
		if (minAlertValueCellNum!=null && maxAlertValueCellNum!=null 
					&& minMaxAlertOutputValueCellNum!=null && minMaxAlertResultCellNum!=null) {
			//获取minAlertValueCellNum列
			Map<Integer, String> variableTypeMap = ExcelUtils.readExcelOfListReturnMap(excel, minAlertValueCellNum);
			for (Entry<Integer, String> entry: variableTypeMap.entrySet()) {
				//遍历
				Integer fieldRowNum = entry.getKey();
				String minAlertValue = entry.getValue();
				//当前的idXpath
				String idXpath = ExcelUtils.readContent(excel, fieldRowNum, idXpathCellNum);
				//当前的maxAlertValue
				String maxAlertValue = ExcelUtils.readContent(excel, fieldRowNum, maxAlertValueCellNum);
				//System.out.println(minAlertValue+"~"+maxAlertValue);
				
				//判断最小值和最大值，是否都有值，没值则直接写:模板中缺少最大值
				if (maxAlertValue!=null && !"".equals(maxAlertValue) && !" ".equals(maxAlertValue)) {
					//若都不为空，则取页面判断
					if (SeleniumUtils.isElementPresent(driver,idXpath)) {
						//存在，则继续获取：属性placeholder="110～210"
						//处理页面中的范围的
						String webMinMaxAlertValue =driver.findElementById(idXpath).getAttribute("placeholder");
						
						//处理页面结果，写入到excel中
						ExcelUtils.writeAndSaveContent(excel,webMinMaxAlertValue,fieldRowNum, minMaxAlertOutputValueCellNum);
						
						//比较后写入结果
						if (webMinMaxAlertValue.equals(minAlertValue+"～"+maxAlertValue)) {
							ExcelUtils.writeAndSaveContent(excel,"pass",fieldRowNum, minMaxAlertResultCellNum);
						}else {
							ExcelUtils.writeAndSaveContent(excel,"no",fieldRowNum, minMaxAlertResultCellNum);
						}
						
					}else {//不存在直接：页面中没找到对应的id
						ExcelUtils.writeAndSaveContent(excel,"页面中没找到对应的id",fieldRowNum, minMaxAlertResultCellNum);
					}
					
				}else {
					ExcelUtils.writeAndSaveContent(excel,"模板中缺少最大值",fieldRowNum, minMaxAlertResultCellNum);
				}
				
			}
		}
	}
	
	
	
}
