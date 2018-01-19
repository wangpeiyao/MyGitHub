package com.sachin.login;

import java.util.HashMap;
import java.util.Map;

public class StartClinent {
	public volatile static LoginFrame loginFrame;

	public static void main(String[] args) {
		// TODO Auto-Sgenerated method stub

		loginFrame = new LoginFrame();
		start();

	}

	private static void start() {
		final Map<Integer, String> universityMap = new HashMap<Integer, String>();
		Map<Integer, String> classNameMap = new HashMap<>();

		universityMap.put(0, "香港大学");
		universityMap.put(1, "北京大学");
		universityMap.put(2, "广东工业大学");
		universityMap.put(3, "复旦大学大学");
		universityMap.put(4, "中国人民大学");
		universityMap.put(5, "中国人民银行");
		universityMap.put(5, "中国人民交通局");

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1500);
					loginFrame.onNetTouchEvent(new PkGetAllUniversitiesResponse(universityMap));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}
}
