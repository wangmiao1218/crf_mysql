package com.gennlife.crf.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebElement;

/**
 * @Description: 字符串处理的工具集
 * @author: wangmiao
 * @Date: 2017年6月9日 上午9:58:00 
 */
public class ListAndStringUtils {
	
	/** 
	* @Title: listToString 
	* @Description: 将List<WebElement> 转换成string，并用“；”分割 
	* @param: List<WebElement> list 下拉框内容
	* @return: String
	* @throws 
	*/
	public static String listToString(List<WebElement> list) {
		StringBuilder sb = new StringBuilder();
		//天假"；"
		for (int i = 1; i < list.size(); i++) {
			String attribute = list.get(i).getAttribute("value");
			 sb.append(attribute+"；");
		}
		//去掉最后的“；”,与excel中“取值范围”一致，方便后续校验
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	
	/** 
	* @Title: trimString 
	* @Description: 将excel中字符串取出，以；分割后，去掉空格，并重新组装成String
	* @param: String value
	* @return: String
	* @throws 
	*/
	public static String trimString(String value){
		String[] strings = value.split("；");
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < strings.length; i++) {
			sb.append(strings[i].trim()).append("；");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	/** 
	* @Title: trimStringOfEqualSign 
	* @Description: 将字符串用=分割，然后去掉空格，返回数组
	* @param: @param value
	* @return: String[]
	* @throws 
	*/
	public static String[] trimStringOfEqualSign(String value){
		String[] strings = value.split("=");
		for (int i = 0; i < strings.length; i++) {
			strings[i]=strings[i].trim();
		}
		return strings;
	}
	
	
	/** 
	* @Title: stringListReturnRandomString 
	* @Description: 将string从数据库中取出，转为list，随机取出list中元素
	* @param: @param value
	* @return: String
	* @throws 
	*/
	public static String stringListReturnRandomString(String value){
		//将list取出分隔后，随机生成list
		String[] strings = value.split(";");
		// 存放内容的集合  
        ArrayList<String> items = new ArrayList<>();
		
		for (int i = 0; i < strings.length; i++) {
			items.add(strings[i].trim());
		}

		// 初始化随机数  
        Random rand = new Random();  
        // 取得集合的长度，for循环使用  
        int size = items.size();  
  
		String returnValue=null;
        // 遍历整个items数组  
        for (int i = 0; i < size; i++) {  
            // 任意取一个0~size的整数，注意此处的items.size()是变化的，所以不能用前面的size会发生数组越界的异常  
            int myRand = rand.nextInt(items.size());  
            //将取出的这个元素放到存放结果的集合中  
            returnValue=items.get(myRand);  
        }  
		return returnValue;
	}
	
	/** 
	* @Title: stringReplaceReturnValue 
	* @Description: 将\变成\\后，再将路径中去掉最后一个\\之后，返回filePath
	* @param: @param value
	* @return: String
	* @throws 
	*/
	public static String stringReplaceReturnValue(String filePath) {
		return filePath.replaceAll("\\\\","\\\\\\\\");
	}
	
	/** 
	 * @Title: stringToSubstringReturnFilePath 
	 * @Description: 将\变成\\后，再将路径中去掉最后一个\\之后，返回filePath
	 * @param: @param value
	 * @return: String
	 * @throws 
	 */
	public static String stringToSubstringReturnFilePath(String value) {
		value= value.replaceAll("\\\\","\\\\\\\\");
		String fileName = value.substring(value.lastIndexOf("\\")+1);
		String[] strings = value.split(fileName);
		strings[0]=strings[0].substring(0, strings[0].length()-2);
		return strings[0];
	}
	
	/** 
	 * @Title: stringToSubstringReturnFileName 
	 * @Description: 将\变成\\后，再将路径中去掉最后一个\\之前，只剩下文件名及后缀名,返回filename.后缀
	 * @param: @param value
	 * @return: String:只剩下文件名及后缀名,返回filename.后缀
	 * @throws 
	 */
	public static String stringToSubstringReturnFileName(String value) {
		value= value.replaceAll("\\\\","\\\\\\\\");
		return value.substring(value.lastIndexOf("\\")+1);
	}
	
	
	/** 
	* @Title: arrayListFilesToStringList 
	* @Description: 将ArrayFiles中\变成\\后，存到数组中去
	* @param: @param files
	* @param: @return :
	* @return: List<String> 返回String类型的list
	* @throws 
	*/
	public static List<String> arrayListFilesToStringList(ArrayList<File> files){
		// 用来保存数据
		List<String> list = new ArrayList<String>();

		for (int i = 0; i < files.size(); i++) {
	       String str= files.get(i).toString().replaceAll("\\\\","\\\\\\\\");
	       list.add(str);
		}
		return list;
	}
	
	/** 
	* @Title: stringToSubstring 
	* @Description: 将路径中去掉最后一个\\之前，以及后缀名，只剩下纯文件名
	* @param: @param value
	* @return: String
	* @throws 
	*/
	public static String stringToSubstring(String value) {
		return value.substring(value.lastIndexOf("\\")+1, value.lastIndexOf("."));
	}
	
}
