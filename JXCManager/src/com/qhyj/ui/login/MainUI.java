package com.qhyj.ui.login;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainUI {
	static {
		try {
			//UIManager.getSystemLookAndFeelClassName()
//			 org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			String lookAndFeel ="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Login();
			}
		});
	}
}
