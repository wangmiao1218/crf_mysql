package com.gennlife.crf.service.impl;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.springframework.stereotype.Service;
import com.gennlife.crf.service.CreateRwsTaskSerive;
import com.gennlife.crf.utils.CreateWebDriver;
import com.gennlife.crf.utils.QuitWebDriver;
import com.gennlife.rws.RwsTask;

@Service
public class CreateRwsTaskSeriveImpl implements CreateRwsTaskSerive{
	
	public static final String rwsUrl="http://10.0.2.162/uranus/project_index.html";
	
	@Override
	public void createRwsTask() throws Exception {
		//开始执行定时任务（用三个线程,模拟三个用户）
		//=====================线程池方法====================================
		//创建线程池并返回ExecutorService实例 
		ExecutorService threadPool =Executors.newFixedThreadPool(3); 
		// 执行任务
		Future<String> futureTest1 = threadPool.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
				//执行登录并改条件执行计算
				String value = RwsTask.createRwsTask(driver, rwsUrl,"testrws001", "testrws001");
				// 关闭driver
				QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
				if ("保存成功".equals(value)) {
					System.out.println("testrws001_success_"+new Date());
					return "success";
				}else {
					return "error";
				}
			}
		});
		Future<String> futureTest2 = threadPool.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
				//执行登录并改条件执行计算
				String value = RwsTask.createRwsTask(driver, rwsUrl,"testrws002", "testrws002");
				// 关闭driver
				QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
				if ("保存成功".equals(value)) {
					System.out.println("testrws002_success_"+new Date());
					return "success";
				}else {
					return "error";
				}
			}
		});
		Future<String> futureTest3 = threadPool.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				PhantomJSDriver driver = CreateWebDriver.createWebDriverByPhantomJSDriver();
				//执行登录并改条件执行计算
				String value = RwsTask.createRwsTask(driver, rwsUrl,"testrws003", "testrws003");
				// 关闭driver
				QuitWebDriver.quitWebDriverByPhantomJSDriver(driver);
				if ("保存成功".equals(value)) {
					System.out.println("testrws003_success_"+new Date());
					return "success";
				}else {
					return "error";
				}
			}
		});
		
		try {
			futureTest1.get();  
			futureTest2.get();
			futureTest3.get(); 
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } 

		//关闭线程池
		if (futureTest1.isDone() && futureTest2.isDone() && futureTest3.isDone()) {
			 //关闭线程池和服务  
            threadPool.shutdown();
		} else {
        	System.out.println("Error");
		} 
	}
	
}
