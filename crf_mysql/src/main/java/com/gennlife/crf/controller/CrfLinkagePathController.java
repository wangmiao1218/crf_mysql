package com.gennlife.crf.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gennlife.crf.bean.Excel;
import com.gennlife.crf.bean.ResultBean;
import com.gennlife.crf.utils.FileUtils;
import com.gennlife.mengyujie.WriteSchemaCrfTemplateOfMyj;

@Controller
@RequestMapping("crfLinkagePathController")
public class CrfLinkagePathController {
	
	//上传单个文件
	@RequestMapping("uploadFile")
	@ResponseBody
	public ResultBean usploadFile(MultipartFile uploadFile) throws Exception{
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
	public ResultBean uploadFiles(@RequestParam("files") MultipartFile[] files) throws Exception{
		ResultBean result = new ResultBean();
		
		// windows上运行
		//1、指定一个文件夹用来存放上传的文件
		String[] paths = {"F:\\uploadFile\\1\\","F:\\uploadFile\\2\\"};
		for (int i = 0; i < paths.length; i++) {
			File filePath = new File(paths[i]);
			// 2、如果不存在该文件夹，就创建
			if(!filePath.exists()){
				filePath.mkdirs();
			}
			// 如果存在该文件夹，就清空文件夹
			if(filePath.exists()){
				FileUtils.deleteFile(paths[i]);
			}
		}
		
		//判断file数组不能为空并且长度大于0  
        if(files!=null && files.length>0){ 
        	String path=null;
            //循环获取file数组中得文件  
            for(int i = 0;i<files.length;i++){  
                MultipartFile uploadFile = files[i];  
                //保存文件  
                if (i==0) {
                	path="F:\\uploadFile\\1\\";
				}
                
                if (i==1) {
                	path="F:\\uploadFile\\2\\";
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
            }  
        }  
        return result;
	}

	//处理文件
    @RequestMapping(value="dealFile",method = RequestMethod.GET)
	@ResponseBody
	public ResultBean dealFile() throws Exception{
		ResultBean result = new ResultBean();
		String fileName1=null;
		String fileName2=null;
		//获取模板的文件名称
		List<String> list1 = FileUtils.getFileNameList("F:\\uploadFile\\1\\");
		//获取crf的文件名称
		List<String> list2 = FileUtils.getFileNameList("F:\\uploadFile\\2\\");
		if (list1.size()==0 || list2.size()==0) {
			result.setResult(ResultBean.RESULT_FAILED);
			result.setMsg("处理文件失败!");
			return result;
		}
		
		fileName1=list1.get(0);
		fileName2=list2.get(0);
		
		//调用方法开始
        //只copy要修改的文件，到输出路径,并重命名为前缀加new_
		String outFilePathString = "F:\\uploadFile\\out\\";
        try {
        	//FileUtils.copyFile("F:\\uploadFile\\1\\"+fileName1, "F:\\uploadFile\\out\\"+fileName1);
    		FileUtils.copyFile("F:\\uploadFile\\2\\"+fileName2, "F:\\uploadFile\\out\\"+"new_"+fileName2);
        } catch (Exception e) {
			result.setResult(ResultBean.RESULT_FAILED);
			result.setMsg("上传文件失败");
		}
        
        Excel excelmb = new Excel("F:\\uploadFile\\1\\",fileName1,"总体结构");
        Excel excel = new Excel(outFilePathString,"new_"+fileName2,"总体结构");
        
        WriteSchemaCrfTemplateOfMyj.writeSchema(excelmb, excel);
        //调用方法结束
		result.setResult(ResultBean.RESULT_SUCCESS);
		result.setMsg("处理文件成功!");
		
        return result;
	}

	//列出所有文件
    @RequestMapping("showFilesList")
    public String showFilesList(HttpServletRequest request, HttpServletResponse response) { 
    	//获取处理后的文件名称列表
    	List<String> list = FileUtils.getFileNameList("F:\\uploadFile\\out\\");
    	for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
    	request.setAttribute("list", list);  
    	
    	//跳转到文件列表页面，以便下载
        return "redirect:/page/tools/showFilesList.jsp";
        
    }  
	
    
    
}
