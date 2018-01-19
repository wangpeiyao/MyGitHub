package com.qhyj.util;

public class StringUtil {
	
	public static String getStrNotNull(String str) {
		if(null==str) {
			return "";
		}
		return str.trim();
	}
	public static String getStrDouble(Double d) {
		if(d==null) {
			return "";
		}
		return d.toString();
	}
	
	

}
