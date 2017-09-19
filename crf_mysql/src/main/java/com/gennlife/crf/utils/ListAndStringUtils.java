package com.gennlife.crf.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebElement;

import com.gennlife.crf.bean.CrfTemplateAnzhenXinXueguan;


/**
 * @Description: 字符串及集合的处理的工具集
 * @author: wangmiao
 * @Date: 2017年6月9日 上午9:58:00
 */
public class ListAndStringUtils {
	
	/** 
	* @Title: searchCrfListReturnOneCrf 
	* @Description: 再list中搜索符合条件的对象
	* @param: @param crfList
	* @param: @param linkageEnglishName
	* @param: @return :
	* @return: CrfTemplateAnzhenXinXueguan
	* @throws 
	*/
	public static CrfTemplateAnzhenXinXueguan searchCrfListReturnOneCrf(
			List<CrfTemplateAnzhenXinXueguan> crfList,String linkageEnglishName) {
		CrfTemplateAnzhenXinXueguan crfTemplateAnzhenXinXueguan = new CrfTemplateAnzhenXinXueguan();
		for (int i = 0; i < crfList.size(); i++) {
			if (linkageEnglishName.equals(crfList.get(i).getEnglishName())) {
				crfTemplateAnzhenXinXueguan = crfList.get(i);
				break;
			}
		}
		return crfTemplateAnzhenXinXueguan;
	}
	
	
	/**
	 * @Title: displayMainValueToSelectByValue
	 * @Description: 若displayMainValueToSelectByValue有分号，则选择最后一个，没有直接返回值
	 * @param: @param value
	 * @return: String
	 * @throws
	 */
	public static String displayMainValueToSelectByValue(String displayMainValue) {
		if (displayMainValue.indexOf(";") != -1) {
			return displayMainValue.substring(displayMainValue.lastIndexOf(".")+1).trim();
		}else if (displayMainValue.indexOf("；") != -1) {
			return displayMainValue.substring(displayMainValue.lastIndexOf("；")+1).trim();
		}else{
			return displayMainValue.trim();
		}
	}
	
	/**
	 * @Title: displayMainKeyToEnglishName
	 * @Description: 将路径中去掉最后一个.之前，返回字段名称,若没有.，则直接返回传入的值
	 * @param: @param value
	 * @return: String
	 * @throws
	 */
	public static String displayMainKeyToEnglishName(String displayMainKey) {
		if (displayMainKey.indexOf(".") != -1) {
			return displayMainKey.substring(displayMainKey.lastIndexOf(".")+1).trim();
		}else{
			return displayMainKey.trim();
		}
	}
	

	/** 
	* @Title: sameListTransferToSequenceList 
	* @Description: 将list中重复的数据，加序号,返回带序号的List
	* @param: @param list :
	* @return: List<String>
	*/
	public static List<String> sameListTransferToSequenceList(List<String> list) {
		String[] indexArr;
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			String key = list.get(i);
			String old = map.get(key);
			if (old != null) {
				map.put(key, old + "," + (i + 1));
			} else {
				map.put(key, "" + (i + 1));
			}
		}
		Iterator<String> it = map.keySet().iterator();
		int index = -1;
		while (it.hasNext()) {
			String key = it.next();
			String value = map.get(key);
			if (value.indexOf(",") != -1) {
				System.out.println(key + " 重复,行： " + value);
				indexArr = value.split(",");

				for (int i = 0; i < indexArr.length; i++) {
					index = Integer.parseInt(indexArr[i]) - 1;
					list.set(index, list.get(index) + (1 + i));
				}
			}
		}

		return list;
	}

	/**
	 * @Title: chNamesListFilter
	 * @Description: 将chNamesList每个元素：1.去掉中英文:之后 2.去掉中英文？之后 3.去掉。之后
	 *               4.去掉中英文（）之间、5.删掉、 6.删掉空格， 7.删掉中英文，
	 * @param: List<String> chNamesList：中文list
	 * @return: List<String> 返回过滤后的list
	 * @throws
	 */
	public static List<String> chNamesListFilter(List<String> chNamesList) {
		List<String> returnList = new ArrayList<String>();

		for (int i = 0; i < chNamesList.size(); i++) {
			String value = chNamesList.get(i);
			// 去掉中英文:之后
			if (value.indexOf("：") != -1) {
				value = value.substring(0, value.indexOf("："));
			}
			if (value.indexOf(":") != -1) {
				value = value.substring(0, value.indexOf(":"));
			}

			// 去掉中英文？之后
			if (value.indexOf("？") != -1) {
				value = value.substring(0, value.indexOf("？"));
			}
			if (value.indexOf("?") != -1) {
				value = value.substring(0, value.indexOf("?"));
			}

			// 去掉。之后
			if (value.indexOf("。") != -1) {
				value = value.substring(0, value.indexOf("。"));
			}

			// 去掉中英文（）之间
			if (value.indexOf("（") != -1) {
				value = value.replaceAll(value.substring(value.indexOf("（"),
						value.indexOf("）") + 1), "");
			}
			if (value.indexOf("(") != -1) {
				value = value.replaceAll(value.substring(value.indexOf("("),
						value.indexOf(")") + 1), "");
			}

			// 删掉、
			if (value.indexOf("、") != -1) {
				value = value.replace("、", "");
			}

			// 删掉空格
			if (value.indexOf(" ") != -1) {
				value = value.replace(" ", "");
			}

			// 删掉中英文，
			if (value.indexOf("，") != -1) {
				value = value.replace("，", "");
			}
			if (value.indexOf(",") != -1) {
				value = value.replace(",", "");
			}

			returnList.add(value);
		}
		return returnList;
	}

	/**
	 * @Title: enNamesListFilter
	 * @Description: 将enNamesList每个元素：1.去掉中英文 ？之后 2.去掉.之后 3.去掉中英文()之内 4.去掉-
	 *               5.中英文，和、变成空格 6.空格变为_ 7.转为大写 8.trim
	 * @param: List<String> enNamesList：英文list
	 * @return: List<String> 返回过滤后的list
	 * @throws
	 */
	public static List<String> enNamesListFilter(List<String> enNamesList) {
		List<String> returnList = new ArrayList<String>();

		for (int i = 0; i < enNamesList.size(); i++) {
			String value = enNamesList.get(i);
			// 1.去掉中英文 ？之后
			if (value.indexOf("？") != -1) {
				value = value.substring(0, value.indexOf("？"));
			}
			if (value.indexOf("?") != -1) {
				value = value.substring(0, value.indexOf("?"));
			}

			// 2.去掉.之后
			if (value.indexOf(".") != -1) {
				value = value.substring(0, value.indexOf("."));
			}

			// 3.去掉中英文()之内
			if (value.indexOf("（") != -1) {
				value = value.replaceAll(value.substring(value.indexOf("（"),
						value.indexOf("）") + 1), "");
			}
			if (value.indexOf("(") != -1) {
				value = value.replaceAll(value.substring(value.indexOf("("),
						value.indexOf(")") + 1), "");
			}

			// 4.去掉-
			if (value.indexOf("-") != -1) {
				value = value.replace("-", "");
			}

			// 5.中英文,和、变成空格
			if (value.indexOf("，") != -1) {
				value = value.replace("，", " ");
			}
			if (value.indexOf(",") != -1) {
				value = value.replace(",", " ");
			}
			if (value.indexOf("、") != -1) {
				value = value.replace("、", " ");
			}

			// 6.空格变为_
			if (value.indexOf(" ") != -1) {
				value = value.replace(" ", "_");
			}

			// 7.转为大写
			value = value.toUpperCase();

			// 8.trim
			value = value.trim();

			returnList.add(value);
		}
		return returnList;
	}

	/**
	 * @Title: listToString
	 * @Description: 将List<WebElement> 转换成string，并用“；”分割
	 * @param: List<WebElement> list 下拉框内容
	 * @return: String
	 * @throws
	 */
	public static String listToString(List<WebElement> list) {
		StringBuilder sb = new StringBuilder();
		// 添加"；"
		for (int i = 1; i < list.size(); i++) {
			String attribute = list.get(i).getAttribute("value");
			sb.append(attribute + "；");
		}
		// 去掉最后的“；”,与excel中“取值范围”一致，方便后续校验
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	
	/** 
	* @Title: valueSpiltToStringList 
	* @Description: 将value用“；”分割,转成list 
	* @param: @param value
	* @return: List<String>
	* @throws 
	*/
	public static List<String> valueSpiltToStringList(String value) {
		List<String> list = new ArrayList<String>();
		String[] strings = value.split("；");
		for (int i = 0; i < strings.length; i++) {
			list.add(strings[i]);
		}
		return list;
	}
	

	/**
	 * @Title: trimString
	 * @Description: 将excel中字符串取出，以；分割后，去掉空格，并重新组装成String
	 * @param: String value
	 * @return: String
	 * @throws
	 */
	public static String trimString(String value) {
		String[] strings = value.split("；");
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < strings.length; i++) {
			sb.append(strings[i].trim()).append("；");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * @Title: trimStringOfEqualSign
	 * @Description: 将字符串用=分割，然后去掉空格，返回数组
	 * @param: @param value
	 * @return: String[]
	 * @throws
	 */
	public static String[] trimStringOfEqualSign(String value) {
		String[] strings = value.split("=");
		for (int i = 0; i < strings.length; i++) {
			strings[i] = strings[i].trim();
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
	public static String stringListReturnRandomString(String value) {
		// 将list取出分隔后，随机生成list
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

		String returnValue = null;
		// 遍历整个items数组
		for (int i = 0; i < size; i++) {
			// 任意取一个0~size的整数，注意此处的items.size()是变化的，所以不能用前面的size会发生数组越界的异常
			int myRand = rand.nextInt(items.size());
			// 将取出的这个元素放到存放结果的集合中
			returnValue = items.get(myRand);
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
		return filePath.replaceAll("\\\\", "\\\\\\\\");
	}

	/**
	 * @Title: stringToSubstringReturnFilePath
	 * @Description: 将\变成\\后，再将路径中去掉最后一个\\之后，返回filePath
	 * @param: @param value
	 * @return: String
	 * @throws
	 */
	public static String stringToSubstringReturnFilePath(String value) {
		value = value.replaceAll("\\\\", "\\\\\\\\");
		String fileName = value.substring(value.lastIndexOf("\\") + 1);
		String[] strings = value.split(fileName);
		strings[0] = strings[0].substring(0, strings[0].length() - 2);
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
		value = value.replaceAll("\\\\", "\\\\\\\\");
		return value.substring(value.lastIndexOf("\\") + 1);
	}

	/**
	 * @Title: arrayListFilesToStringList
	 * @Description: 将ArrayFiles中\变成\\后，存到数组中去
	 * @param: @param files
	 * @param: @return :
	 * @return: List<String> 返回String类型的list
	 * @throws
	 */
	public static List<String> arrayListFilesToStringList(ArrayList<File> files) {
		// 用来保存数据
		List<String> list = new ArrayList<String>();

		for (int i = 0; i < files.size(); i++) {
			String str = files.get(i).toString().replaceAll("\\\\", "\\\\\\\\");
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
		return value.substring(value.lastIndexOf("\\") + 1,
				value.lastIndexOf("."));
	}

}
