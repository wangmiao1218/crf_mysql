package com.gennlife.crf.utils;

import org.openqa.selenium.phantomjs.PhantomJSDriver;

/**
 * @Description: 登录rws系统相关方法（rws定时程序使用，动态传用户与密码）
 * @author: wangmiao
 * @Date: 2018年3月14日 下午2:35:41 
 */
public class LoginRws {
	
	public static final String rwsUrl="http://10.0.2.162/uranus/project_index.html";
	public static final String firstProjectXpath="//*[@id='item-list-container']/tbody/tr[1]/td[2]/a";
	public static final String projectId="rws_defined.html";
	public static final String firstIndexXpath=" //*[@id='app']/div/div/div/div[2]/div/div[1]/table/tbody/tr[1]/td[2]/a";
	public static final String indexToSaveButtonXpath="//*[@id='app']/div/div/div[2]/div/div[2]/button[2]";
	//public static final String eventToSaveButtonXpath=" //*[@id='app']/div/div/div[2]/div/button[2]";
	
	/** 
	* @Title: loginAndToRwsByPhantomJSDriver 
	* @Description: 仅登录,通过PhantomJSDriver，无页面显示的方式,到rws我的项目页面
	* @param: @param driver
	* @param: @param loginName
	* @param: @param pwd
	* @return: String 返回，登陆成功返回“登陆成功”
	* @throws 
	*/
	public static String loginAndToRwsByPhantomJSDriver(PhantomJSDriver driver,
			String loginName,String pwd) {
		driver.get(rwsUrl);
		// 等待
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 登录
		driver.findElementById("loginName").clear();
		driver.findElementById("loginName").sendKeys(loginName);
		driver.findElementById("pwd").clear();
		driver.findElementById("pwd").sendKeys(pwd);
		driver.findElementById("login").click();
		// 等待
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String returnString=null;
		//String text = driver.findElementByClassName("u-btn u-new").getText();
		//坑：WerbDriver中by.className() 不支持含有空格的class名称。采用通配符：
		String text = driver.findElementByClassName("u-new").getText();
		
		if ("新建项目".equals(text)) {
			returnString = "登陆成功";
		}
		
		return returnString;
	}

	
	/** 
	* @Title: loginAndToRwsProjectIndexAndModifyConditionByPhantomJSDriver 
	* @Description: 登录并到列表中第一个项目，且到列表中第一个指标的修改页面，并进行修改。
	* 				修改指标：为写死的 修改第一个当前时间，这样保证进行最新的计算
	* @param: @param driver
	* @param: @param loginName 传入的用户名
	* @param: @param pwd  传入的密码
	* @param: @return :返回，登录并到添加页面，成功返回“保存指标”,否则是“error”
	* @return: String
	* @throws 
	*/
	public static String loginAndToRwsProjectIndexAndModifyConditionByPhantomJSDriver(PhantomJSDriver driver,
			String loginName,String pwd) {
		String returnString=null;
		String loginValue = loginAndToRwsByPhantomJSDriver(driver, loginName, pwd);
		if ("登陆成功".equals(loginValue)) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//到列表中第一个项目界面
			driver.findElementByXPath(firstProjectXpath).click();
			//到列表中第一个指标或事件界面
			driver.findElementById(projectId).click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			driver.findElementByXPath(firstIndexXpath).click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			/*
			 * 页面没有切换，所以不用切换到新的窗口
			// 得到当前窗口的set集合,将set集合存入list对象, 切换到弹出的新窗口
			Set<String> winHandels = driver.getWindowHandles();
			List<String> it = new ArrayList<String>(winHandels);
			driver.switchTo().window(it.get(1));
			*/
			
			// 获取保存指标按钮，若存在则在添加页面
			String text = driver.findElementByXPath(indexToSaveButtonXpath).getText();
			//
			if ("保存指标".equals(text)) {
				returnString = "保存指标";
				//修改指标_修改第一个当前时间，这样保证进行最新的计算
				
				
				
				//
			}
		}else {
			returnString="error";
		}
		
		return returnString;
	}
	
}
