package com.gennlife.crf.service.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gennlife.crf.service.CreateRwsTaskSerive;

@Service
public class CreateRwsTaskSeriveImpl implements CreateRwsTaskSerive{

	@Autowired
	private CreateRwsTaskSerive createRwsTaskSerive;
	
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
				System.out.println("用户甲");
				//执行登录并改条件执行计算
				
				//CrfdataOrPatientDetailMongodbDataProcess.insertDatasIntoPatientDetailMongodb(mongodbIp,listMapJsons);
				return "success";
			}
		});
		
		Future<String> futureTest2 = threadPool.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("用户乙");
				//CrfdataOrPatientDetailMongodbDataProcess.insertDatasIntoPatientDetailMongodb(mongodbIp,listMapJsons);
				return "success";
			}
		});
		
		//
		Future<String> futureTest3 = threadPool.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("用户丙");
				//CrfdataOrPatientDetailMongodbDataProcess.insertDatasIntoPatientDetailMongodb(mongodbIp,listMapJsons);
				return "success";
			}
		});
		
		//
		if ("success".equals(futureTest1) && "success".equals(futureTest2) && "success".equals(futureTest3)) {
			System.out.println("ok");
			 //关闭线程池和服务  
            threadPool.shutdown();
		} else {
        	System.out.println("Error");
		} 
		
		
		//
		
		
		
	}

	
	
	
}
