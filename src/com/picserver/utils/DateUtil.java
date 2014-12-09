package com.picserver.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取时间的工具类
 * @author hadoop
 *
 */
public class DateUtil {

	/**
	 * 将时间按照格式进行转换
	 * @param date 时间
	 * @param format 时间格式
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (date != null) {
			result = sdf.format(date);
		}
		return result;
	}

	/**
	 * 将String时间转换成某时间格式
	 * @param str 时间
	 * @param format 格式
	 * @return
	 * @throws Exception
	 */
	public static Date formatString(String str, String format) throws Exception {
		if ((str == null)||(str.equals(""))){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(str);
	}

	/**
	 * 获取当前日期时间，24小时制
	 * @return String类型的时间
	 * @throws Exception
	 */
	public static String getCurrentDateStr() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}
	
	public static String getCurrentDateMS() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(date);
	}
}
