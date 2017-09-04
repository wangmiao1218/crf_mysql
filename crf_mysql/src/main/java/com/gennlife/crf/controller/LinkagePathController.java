package com.gennlife.crf.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	
	//列出所有文件
    @RequestMapping("/showFilesList")  
    public String showFilesList(HttpServletRequest request, HttpServletResponse response) {  
        // 获取上传文件的目录  
        //ServletContext sc = request.getSession().getServletContext();  
        // 上传位置  
        //String uploadFilePath = sc.getRealPath("/img") + "/"; // 设定文件保存的目录  
    	String path = "F:\\uploadFile\\test\\";
    	File file = new File(path);
    	
        // 存储要下载的文件名  
        Map<String, String> fileNameMap = new HashMap<String, String>(); 
        fileNameMap.put(file.toString(), path);
        List<String> filesList = new ArrayList<String>();
        
        // 递归遍历filepath目录下的所有文件和目录，将文件的文件名存储到map集合中  
        //filesList(new File(path), fileNameMap);// File既可以代表一个文件也可以代表一个目录
        
        // 列出该目录下的所有文件和目录
        File[] files = file.listFiles(); 
        if (files != null) {
        	for (int i = 0; i < files.length; i++) {
        		filesList.add(files[i].getName());
        	}
        }
        
        // 将Map集合发送到listfile.jsp页面进行显示  
        request.setAttribute("fileNameMap", fileNameMap);  
        return "showFilesList";  
       // return "redirect:page/tools/showFilesList";
        
    }  

	
	//处理文件
    @RequestMapping("dealFile")
	@ResponseBody
	public ResultBean dealFile(@RequestParam("files") MultipartFile[] files) throws Exception{
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

}
