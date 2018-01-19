package com.qhyj.util;

public class LogUtil {
	
	public static void info(String msg) {
		System.out.println(msg);
	}
	public static void error(String msg,Throwable e) {
		System.out.println(msg);
		e.printStackTrace();
	}

}
