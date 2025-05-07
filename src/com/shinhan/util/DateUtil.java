package com.shinhan.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static String converToString(Date d1) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(d1); // 날짜 -> 문자
		return str;
	}
	
	public static Date converToDate(String str2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d2 = null;
		try {
			d2 = sdf.parse(str2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} return d2;
	}
	
	public static java.sql.Date converToSQLDate(Date d) {
		return new java.sql.Date(d.getTime());
	}
}
