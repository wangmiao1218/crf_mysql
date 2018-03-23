package com.gennlife.crf.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(TriggerServiceImpl.class);
	
	@Autowired
	private CreateRwsTaskSerive createRwsTaskSerive;
	
	@Override
	public void doIt() throws Exception {
		//测试定时任务
		//System.out.println("doIt:"+new Date());
		
		logger.info("自动创建rws的定时任务，开始--->" +new Date());
		createRwsTaskSerive.createRwsTask();
		logger.info("自动创建rws的定时任务，结束--->" +new Date());
	}

}
