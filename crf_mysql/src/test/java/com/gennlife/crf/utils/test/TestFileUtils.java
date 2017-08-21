package com.gennlife.crf.utils.test;

import java.io.File;

import org.junit.Test;

import com.gennlife.crf.utils.FileUtils;

public class TestFileUtils {

	@Test
	public void fortest() throws Exception {
		String fileName1="E:\\安贞\\_wm数据json&js\\安贞3w数据_北京长庚_无图片\\2.json";
		File oldfile = new File(fileName1);
		String returnValue = FileUtils.readFileAndReturnValue(oldfile, "13100002");
		String returnValue2 = FileUtils.readFileAndReturnValue(oldfile, "38cca9c1-0830-4518-99d4-b74a58c9b90515dbbc2e934");

		for (int i = 1; i < 30001; i++) {
			String substring = returnValue.replace("13100002", 13110000+i+"");
			File newFile = new File("E:\\安贞\\_wm数据json&js\\安贞3w数据_北京长庚_无图片\\2_"+i+".json");
			FileUtils.readFileAndReplaceStrToNewFile(oldfile, returnValue, substring, newFile);
			
			String substring2 = returnValue2.replace("38cca9c1-0830-4518-99d4-b74a58c9b90515dbbc2e934", 13110000+i+"-0830-4518-99d4-b74a58c9b90515dbbc2e934");
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
