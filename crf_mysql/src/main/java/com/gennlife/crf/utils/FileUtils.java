package com.gennlife.crf.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: 文件的处理工具集
 * @author: wangmiao
 * @Date: 2017年8月21日 上午11:39:18
 */
public class FileUtils {

	/**
	 * @Title: readFileAndReturnValue
	 * @Description: 按行读取文件，返回含有value的整行字符串
	 * @param: File file :文件路径
	 * @param: String value
	 * @return: String：返回含有value的整行字符串
	 * @throws
	 */
	public static String readFileAndReturnValue(File file, String value) {
		String str = null;
		String returnValue = null;
		// 读取文件
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			while ((str = br.readLine()) != null) {
				if (str.contains(value)) {
					returnValue = str;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnValue;

	}

	
	/** 
	* @Title: readFileAndReplaceToNewFile 
	* @Description: 按行读取文件，读取包含oldStr的行，替换为replaceStr，并另存为一个新文件
	* @param: File oldfile：旧文件
	* @param: String oldStr：旧的值
	* @param: String replaceStr：替换后的值
	* @param: File newFlie :新的文件
	* @return: void
	* @throws 
	*/
	public static void readFileAndReplaceStrToNewFile(File oldfile, String oldStr,String replaceStr,File newFlie) {
		String str = null;
		// 读取文件
		FileReader fr = null;
		BufferedReader br = null;
		
		//写入文件
		FileWriter fw = null;
		try {
			fr = new FileReader(oldfile);
			br = new BufferedReader(fr);

			//定义StringBuffer
			StringBuffer buf = new StringBuffer();
			
			// 保存该行前面的内容
			for (int i = 1; (str = br.readLine()) != null && !str.contains(oldStr); i++) {
				buf = buf.append(str);
				buf = buf.append(System.getProperty("line.separator"));
			}
			
			// 将内容插入
			buf = buf.append(replaceStr);
			
			// 保存该行后面的内容
			while ((str = br.readLine()) != null) {
				buf = buf.append(System.getProperty("line.separator"));
				buf = buf.append(str);
			}

			//保存另一个文件
			fw = new FileWriter(newFlie);
			PrintWriter pw = new PrintWriter(fw);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			pw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
			try {
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	
}
