package com.gennlife.crf.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gennlife.crf.bean.ResultBean;
import com.gennlife.crf.bean.SysOp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("linkagePathController")
public class LinkagePathController {
	
	@RequestMapping("uploadFile")
	@ResponseBody
	public ResultBean uploadFile(MultipartFile uploadFile) throws Exception{
		ResultBean result = new ResultBean();
		// 1、指定一个文件夹用来存放上传的文件
		String path = "F:\\test\\";
		File filePath = new File(path);
		// 2、如果不存在该文件夹，就创建
		if(!filePath.exists()){
			filePath.mkdirs();
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
	
	

}
