package com.gennlife.crf.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gennlife.crf.bean.ResultBean;
import com.gennlife.crf.utils.FileUtils;

@Controller
@RequestMapping("linkagePathController")
public class LinkagePathController {
	
	//上传单个文件
	@RequestMapping("uploadFile")
	@ResponseBody
	public ResultBean uploadFile(MultipartFile uploadFile) throws Exception{
		ResultBean result = new ResultBean();
		
		// linux上运行
		// 获得服务器应用程序所在的绝对路径
		
		
		
		// windows上运行
		//1、指定一个文件夹用来存放上传的文件
		String path = "F:\\uploadFile\\test\\";
		File filePath = new File(path);

		// 2、如果不存在该文件夹，就创建
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		// 如果存在该文件夹，就清空文件夹
		if(filePath.exists()){
			FileUtils.deleteFile(path);
		}
		
		// 3、新建一个文件对象
		File newfile = new File(path , uploadFile.getOriginalFilename());
		
		// 4、通过MultipartFile的transferTo方法传输文件
		try {
			uploadFile.transferTo(newfile);
		} catch (Exception e) {
			result.setResult(ResultBean.RESULT_FAILED);
			result.setMsg("上传文件失败");
		}
		
		result.setResult(ResultBean.RESULT_SUCCESS);
		result.setMsg("上传文件成功");
		return result;
	}
	
	
	//同时上传多个文件
	@RequestMapping("uploadFiles")
	@ResponseBody
	public ResultBean uploadFile2(@RequestParam("files") MultipartFile[] files) throws Exception{
		ResultBean result = new ResultBean();
		
		// windows上运行
		//1、指定一个文件夹用来存放上传的文件
		String path = "F:\\uploadFile\\test\\";
		File filePath = new File(path);
		// 2、如果不存在该文件夹，就创建
		if(!filePath.exists()){
			filePath.mkdirs();
		}
		// 如果存在该文件夹，就清空文件夹
		if(filePath.exists()){
			FileUtils.deleteFile(path);
		}
				
		//判断file数组不能为空并且长度大于0  
        if(files!=null && files.length>0){  
            //循环获取file数组中得文件  
            for(int i = 0;i<files.length;i++){  
                MultipartFile uploadFile = files[i];  
                //保存文件  
                // 3、新建一个文件对象
        		File newfile = new File(path , uploadFile.getOriginalFilename());
        		
        		// 4、通过MultipartFile的transferTo方法传输文件
        		try {
        			uploadFile.transferTo(newfile);
        		} catch (Exception e) {
        			result.setResult(ResultBean.RESULT_FAILED);
        			result.setMsg("上传文件失败");
        		}
				result.setResult(ResultBean.RESULT_SUCCESS);
				result.setMsg("上传文件成功");
            }  
        }  
        return result;
	}

	
	//处理文件
	
	

}
