package com.gennlife.crf.utils.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gennlife.crf.utils.FileUtils;

public class TestFileUtils {

	@Test
	public void getFileNameList(){
		String delpath="F:\\uploadFile\\1\\";
		List<String> files = FileUtils.getFileNameList(delpath);
		System.out.println(files);
		for (int i = 0; i < files.size(); i++) {
			System.out.println(files.get(i));
		}
	}
	
	@Test
	public void getFilesArrayList(){
		String delpath="F:\\uploadFile\\out\\";
		ArrayList<File> files = FileUtils.getFilesArrayList(delpath);
		System.out.println(files);
		for (int i = 0; i < files.size(); i++) {
			System.out.println(files.get(i));
		}
	}
	
	@Test
	public void deleteFile() throws Exception {
		String delpath="F:\\uploadFile\\test\\";
		FileUtils.deleteFile(delpath);
	}
	
	@Test
	public void copyFile() throws Exception {
		String oldfile="F:\\uploadFile\\2\\3333.xlsx";
		String newfile="F:\\uploadFile\\out\\new_3333.xlsx";
		FileUtils.copyFile(oldfile, newfile);
	}
	
	@Test
	public void fortest() throws Exception {
		String fileName1="E:\\安贞\\_wm数据json&js\\安贞3w数据_上海交通_有图片_不同id\\1.json";
		File oldfile = new File(fileName1);
		String returnValue = FileUtils.readFileAndReturnValue(oldfile, "21100003");
		String returnValue2 = FileUtils.readFileAndReturnValue(oldfile, "50b3b83a-cb39-4406-8dd8-64d29747686615dbb8ab022");
		
		for (int i = 1; i < 30001; i++) {
			String substring = returnValue.replace("21100003", 21110000+i+"");
			File newFile = new File("E:\\安贞\\_wm数据json&js\\安贞3w数据_上海交通_有图片_不同id\\1_"+i+".json");
			FileUtils.readFileAndReplaceStrToNewFile(oldfile, returnValue, substring, newFile);
			
			String substring2 = returnValue2.replace("50b3b83a-cb39-4406-8dd8-64d29747686615dbb8ab022", 21110000+i+"-cb39-4406-8dd8-64d29747686615dbb8ab022");
			FileUtils.readFileAndReplaceStrToNewFile(newFile, returnValue2, substring2, newFile);
		}
		
	}
	
	@Test
	public void readFileAndReturnValue() throws Exception {
		String fileName="E:\\安贞\\_wm数据json&js\\安贞3w数据_不同id\\1.json";
		File file = new File(fileName);
		String returnValue = FileUtils.readFileAndReturnValue(file, "21166666");
		System.out.println(returnValue);
		
		String substring = returnValue.replace("21166666", "21177777");
		System.out.println(substring);
		
	}
	
	@Test
	public void readFileAndReplaceStrToNewFile() throws Exception {
		String fileName1="E:\\安贞\\_wm数据json&js\\安贞3w数据_不同id\\1.json";
		String fileName2="E:\\安贞\\_wm数据json&js\\1_1.json";
		
		File oldfile = new File(fileName1);
		File newFlie = new File(fileName2);
		
		String returnValue = FileUtils.readFileAndReturnValue(oldfile, "21166666");
		String substring = returnValue.replace("21166666", "21177777");
		
		FileUtils.readFileAndReplaceStrToNewFile(oldfile, returnValue, substring, newFlie);
		
	}
	
	
}
