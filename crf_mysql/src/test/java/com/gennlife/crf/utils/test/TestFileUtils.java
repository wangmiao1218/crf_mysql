package com.gennlife.crf.utils.test;

import java.io.File;

import org.junit.Test;

import com.gennlife.crf.utils.FileUtils;

public class TestFileUtils {
	private static String fileName="E:\\安贞\\_wm数据json&js\\21100003_test.json";
	private static String fileName1="E:\\安贞\\_wm数据json&js\\1.json";
	private static String fileName2="E:\\安贞\\_wm数据json&js\\1_3.json";

	@Test
	public void fortest() throws Exception {
		File oldfile = new File(fileName1);
		File newFlie = new File(fileName2);
		
		String returnValue = FileUtils.readFileAndReturnValue(oldfile, "21166666");
		String substring = returnValue.replace("21166666", "21177777");
		
		FileUtils.readFileAndReplaceStrToNewFile(oldfile, returnValue, substring, newFlie);
		
	}
	
	@Test
	public void readFileAndReturnValue() throws Exception {
		File file = new File(fileName);
		String returnValue = FileUtils.readFileAndReturnValue(file, "21166666");
		System.out.println(returnValue);
		
		String substring = returnValue.replace("21166666", "21177777");
		System.out.println(substring);
		
	}
	
	@Test
	public void readFileAndReplaceStrToNewFile() throws Exception {
		File oldfile = new File(fileName1);
		File newFlie = new File(fileName2);
		
		String returnValue = FileUtils.readFileAndReturnValue(oldfile, "21166666");
		String substring = returnValue.replace("21166666", "21177777");
		
		FileUtils.readFileAndReplaceStrToNewFile(oldfile, returnValue, substring, newFlie);
		
	}
	
	
}
