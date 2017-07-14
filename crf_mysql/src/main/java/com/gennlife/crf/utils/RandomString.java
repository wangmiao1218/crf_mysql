package com.gennlife.crf.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @Description: 随机生成各种数据
 * @author: wangmiao
 * @Date: 2017年7月14日 上午8:37:00
 */
public class RandomString {

	/**
	 * @Title: UUIDString
	 * @Description: 随机生成uuid
	 * @param: @return :
	 * @return: String
	 * @throws
	 */
	public static String UUIDString() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 20);
	}

	/**
	 * @Title: randomRangeInt
	 * @Description: 随机生成min-max之间的数
	 * @param: @param max
	 * @param: @param min
	 * @param: @return :
	 * @return: int
	 * @throws
	 */
	public static String randomRangeInt(int min, int max) {
		Random random = new Random();
		int value=random.nextInt(max) % (max - min + 1)+min;
		return value+"";
	}

	
	/** 
	* @Title: randomDateyyyyMMdd 
	* @Description: 随机生成区间内的日期
	* @param: @return
	* @param: @throws Exception :
	* @return: String
	* @throws 
	*/
	public static String randomDateyyyyMMdd() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String beginDate = "1990-01-01";
		String endDate = "2000-01-01";
		
		Date start = format.parse(beginDate);// 开始日期
		Date end = format.parse(endDate);// 结束日期
		if (start.getTime() >= end.getTime()) {
			return null;
		}
		long date = random(start.getTime(), end.getTime());

		return format.format(new Date(date)) ;  
	}

	private static long random(long begin, long end) {
		long rtnn = begin + (long) (Math.random() * (end - begin));
		if (rtnn == begin || rtnn == end) {
			return random(begin, end);
		}
		return rtnn;
	}
	
	/** 
	* @Title: randomDateyyyyMMddHHmmss 
	* @Description: 随机生成区间内的日期
	* @param: @return
	* @param: @throws Exception :
	* @return: String
	* @throws 
	*/
	public static String randomDateyyyyMMddHHmmss() throws Exception {
		String beginDate = "2000-01-01 00:00:00";
		String endDate = "2016-01-01 00:00:00";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date start = format.parse(beginDate);// 开始日期
		Date end = format.parse(endDate);// 结束日期
		if (start.getTime() >= end.getTime()) {
			return null;
		}
		long date = random(start.getTime(), end.getTime());

		return format.format(new Date(date)) ;  
	}


}
