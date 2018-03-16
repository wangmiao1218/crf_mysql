package com.gennlife.crf.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.gennlife.crf.service.CreateRwsTaskSerive;
import com.gennlife.crf.service.TriggerService;

/**
 * @Description: 定时任务的impl
 * 				spring.xml文件里面，定义TriggerServiceBean(手动引入，不需要@service注解) 
 * @author: wangmiao
 * @Date: 2018年3月14日 上午9:39:42 
 */
public class TriggerServiceImpl implements TriggerService{

	@Autowired
	private CreateRwsTaskSerive createRwsTaskSerive;
	
	@Override
	public void doIt() throws Exception {
		//测试定时任务
		//System.out.println("doIt:"+new Date());
		
		System.out.println("自动创建rws的定时任务，开始--->" + new Date());
		createRwsTaskSerive.createRwsTask();
		System.out.println("自动创建rws的定时任务，开始--->" + new Date());
	}
	

}
