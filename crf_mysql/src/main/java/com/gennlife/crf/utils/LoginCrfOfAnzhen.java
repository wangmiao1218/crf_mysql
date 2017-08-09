package com.gennlife.crf.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * @Description: 登录crf系统相关方法（安贞）
 * @author: wangmiao
 * @Date: 2017年6月9日 下午2:23:49
 */
public class LoginCrfOfAnzhen {
	public static final String xpathOfMenZhen=".//*[@id='modal-container']/div/div/div/div[2]/a[1]";
	public static final String xpathOfZhuYuan=".//*[@id='modal-container']/div/div/div/div[2]/a[2]";
	public static final String xpathOfTiJian=".//*[@id='modal-container']/div/div/div/div[2]/a[3]";
	public static final String xpathOfSheQu=".//*[@id='modal-container']/div/div/div/div[2]/a[4]";
	
	public static final String danbingzhongUrl="http://119.253.137.125/uranus/crf_case.html";

	public static final String loginName ="wangmiao@gennlife.com";
	public static final String pwd ="ls123456";
	
	/**
	 * @Title: loginByChromeWebDriver
	 * @Description: 仅登录,通过chrome驱动，有页面显示的方式
	 * @param: WebDriver driver :传入driver
	 * @return: String： 返回，登录成功返回“单病种数据库”
	 * @throws
	 */
	public static String loginByChromeWebDriver(WebDriver driver) {
		driver.navigate().to("http://119.253.137.125/uranus/login.html");

		// 获取 网页的 title
		System.out.println("title is: " + driver.getTitle());

		// 登录
		WebElement loginName = driver.findElement(By.xpath("/html/body/div[1]/form/div/input[1]"));
		WebElement pwd = driver.findElement(By.xpath("/html/body/div[1]/form/div/input[2]"));

		loginName.sendKeys("wangmiao@gennlife.com");
		pwd.sendKeys("ls123456");
		driver.findElement(By.xpath("/html/body/div[1]/form/div/button")).click();

		// 等待
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String returnValue = null;

		// 获取单病种标题
		String text = driver.findElement(By.xpath("/html/body/div/div[1]/nav/div[2]/ul/li[5]/a")).getText();
		//
		if ("单病种数据库".equals(text)) {
			returnValue = text;
		}
		return returnValue;

	}

	/**
	 * @Title: loginAndToAddByChromeWebDriver
	 * @Description: 登录并到添加页面，通过chrome驱动，有页面显示的方式
	 * @param: WebDriver driver :传入driver
	 * @return: String： 返回，登录并到添加页面，成功返回“添加页面”
	 * @throws
	 */
	public static String loginAndToAddByChromeWebDriver(WebDriver driver) {
		String returnValue = loginByChromeWebDriver(driver);

		if ("单病种数据库".equals(returnValue)) {
			// 获取添加按钮
			driver.findElement(By.xpath("/html/body/div/div[3]/div/div[2]/div/div[1]/button[1]")).click();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 获取基线_门诊
			driver.findElement(By.xpath("/html/body/div[1]/div[8]/div/div/div/div[2]/a[1]")).click();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 得到当前窗口的set集合
			Set<String> winHandels = driver.getWindowHandles();

			// 将set集合存入list对象
			List<String> it = new ArrayList<String>(winHandels);

			// 切换到弹出的新窗口
			driver.switchTo().window(it.get(1));
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String value = null;

		// 获取保存按钮，若存在则在添加页面
		String text = driver.findElement(By.xpath("html/body/div[2]/div[2]/div[1]/div[2]/button[2]")).getText();
		//
		if ("保存".equals(text)) {
			value = "添加页面";
		}
		
		return value;
	}

	
	/**
	 * @Title: loginByPhantomJSDriver
	 * @Description: 仅登录,通过PhantomJSDriver，无页面显示的方式
	 * @param: PhantomJSDriver driver :传入PhantomJSDriver
	 * @return: String： 返回，登陆成功返回“登陆成功”
	 * @throws
	 */
	public static String loginByPhantomJSDriver(PhantomJSDriver driver) {
		driver.get(danbingzhongUrl);

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
		
		//切换医院
		Select sel = new Select(driver.findElementByXPath(".//*[@id='crf-lab']/select"));
		//北京清华长庚医院
        //sel.selectByValue("anzhen-beijingqinghuazhanggengyiyuan"); 
        //sel.selectByValue("anzhen-nanfangyikedaxuenanfangyiyuan"); 
        //sel.selectByValue("anzhen-zhongguorenminjiefangjundisanjunyidaxue"); 
        //sel.selectByValue("anzhen-shoudouyikedaxuefushubeijingtiantanyiyuan"); 
        sel.selectByValue("anzhen-beijingshixinfeixueguanjibingyanjiusuo"); 
		
		// 等待
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String returnString=null;
		String text = driver.findElementByXPath(".//*[@id='action-container']/div[1]/button[1]").getText();
		
		//
		if ("添加".equals(text)) {
			returnString = "登陆成功";
		}
		
		return returnString;
	}

	
	/**
	 * @Title: loginAndToAddByPhantomJSDriver
	 * @Description: 登录并到添加页面,基本信息页面（门诊），通过PhantomJSDriver，无页面显示的方式
	 * @param: PhantomJSDriver driver :传入driver
	 * @return: String：返回，登录并到添加页面，成功返回“添加页面”
	 * @throws
	 */
	public static String loginAndToAddOfMenZhenAndBasicInfoByPhantomJSDriver(PhantomJSDriver driver) {
		String returnString = loginByPhantomJSDriver(driver);

		if ("登陆成功".equals(returnString)) {
			// 获取添加按钮
			driver.findElementByXPath(".//*[@id='action-container']/div[1]/button[1]").click();
						
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 获取基线_门诊
			driver.findElementByXPath(xpathOfMenZhen).click();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 得到当前窗口的set集合
			Set<String> winHandels = driver.getWindowHandles();
			// 将set集合存入list对象
			List<String> it = new ArrayList<String>(winHandels);
			// 切换到弹出的新窗口
			driver.switchTo().window(it.get(1));
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String value = null;

		// 获取保存按钮，若存在则在添加页面
		String text = driver.findElementById("input-save").getText();
		
		if ("保存".equals(text)) {
			value = "添加页面";
		}
		
		return value;
	}
	
	/**
	 * @Title: loginAndToAddByPhantomJSDriver
	 * @Description: 登录并到添加页面,基本信息页面（住院），通过PhantomJSDriver，无页面显示的方式
	 * @param: PhantomJSDriver driver :传入driver
	 * @return: String：返回，登录并到添加页面，成功返回“添加页面”
	 * @throws
	 */
	public static String loginAndToAddOfZhuYuanAndBasicInfoByPhantomJSDriver(PhantomJSDriver driver) {
		String returnString = loginByPhantomJSDriver(driver);

		if ("登陆成功".equals(returnString)) {
			// 获取添加按钮
			driver.findElementByXPath(".//*[@id='action-container']/div[1]/button[1]").click();
						
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 获取基线_门诊
			driver.findElementByXPath(xpathOfZhuYuan).click();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 得到当前窗口的set集合
			Set<String> winHandels = driver.getWindowHandles();
			// 将set集合存入list对象
			List<String> it = new ArrayList<String>(winHandels);
			// 切换到弹出的新窗口
			driver.switchTo().window(it.get(1));
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String value = null;

		// 获取保存按钮，若存在则在添加页面
		String text = driver.findElementById("input-save").getText();
		
		if ("保存".equals(text)) {
			value = "添加页面";
		}
		
		return value;
	}
	
	/**
	 * @Title: loginAndToAddByPhantomJSDriver
	 * @Description: 登录并到添加页面,基本信息页面（体检），通过PhantomJSDriver，无页面显示的方式
	 * @param: PhantomJSDriver driver :传入driver
	 * @return: String：返回，登录并到添加页面，成功返回“添加页面”
	 * @throws
	 */
	public static String loginAndToAddOfTiJianAndBasicInfoByPhantomJSDriver(PhantomJSDriver driver) {
		String returnString = loginByPhantomJSDriver(driver);

		if ("登陆成功".equals(returnString)) {
			// 获取添加按钮
			driver.findElementByXPath(".//*[@id='action-container']/div[1]/button[1]").click();
						
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 获取基线_门诊
			driver.findElementByXPath(xpathOfTiJian).click();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 得到当前窗口的set集合
			Set<String> winHandels = driver.getWindowHandles();
			// 将set集合存入list对象
			List<String> it = new ArrayList<String>(winHandels);
			// 切换到弹出的新窗口
			driver.switchTo().window(it.get(1));
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String value = null;

		// 获取保存按钮，若存在则在添加页面
		String text = driver.findElementById("input-save").getText();
		
		if ("保存".equals(text)) {
			value = "添加页面";
		}
		
		return value;
	}
	
	/**
	 * @Title: loginAndToAddByPhantomJSDriver
	 * @Description: 登录并到添加页面,基本信息页面（社区），通过PhantomJSDriver，无页面显示的方式
	 * @param: PhantomJSDriver driver :传入driver
	 * @return: String：返回，登录并到添加页面，成功返回“添加页面”
	 * @throws
	 */
	public static String loginAndToAddOfSheQuAndBasicInfoByPhantomJSDriver(PhantomJSDriver driver) {
		String returnString = loginByPhantomJSDriver(driver);

		if ("登陆成功".equals(returnString)) {
			// 获取添加按钮
			driver.findElementByXPath(".//*[@id='action-container']/div[1]/button[1]").click();
						
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 获取基线_门诊
			driver.findElementByXPath(xpathOfSheQu).click();

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 得到当前窗口的set集合
			Set<String> winHandels = driver.getWindowHandles();
			// 将set集合存入list对象
			List<String> it = new ArrayList<String>(winHandels);
			// 切换到弹出的新窗口
			driver.switchTo().window(it.get(1));
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String value = null;

		// 获取保存按钮，若存在则在添加页面
		String text = driver.findElementById("input-save").getText();
		
		if ("保存".equals(text)) {
			value = "添加页面";
		}
		
		return value;
	}
	
	
}
