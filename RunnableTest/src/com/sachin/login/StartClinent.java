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

		universityMap.put(0, "��۴�ѧ");
		universityMap.put(1, "������ѧ");
		universityMap.put(2, "�㶫��ҵ��ѧ");
		universityMap.put(3, "������ѧ��ѧ");
		universityMap.put(4, "�й������ѧ");
		universityMap.put(5, "�й���������");
		universityMap.put(5, "�й�����ͨ��");

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
