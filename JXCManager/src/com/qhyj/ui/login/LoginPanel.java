package com.qhyj.ui.login;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
public class LoginPanel extends JPanel {
	protected ImageIcon icon;
	public int width,height;
	public LoginPanel() {
		super();
		URL path = this.getClass().getResource("res/login.jpg");
		icon = new ImageIcon("res/login.jpg");
		width = icon.getIconWidth();
		height = icon.getIconHeight();
		setSize(width, height);
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Image img = icon.getImage();
		g.drawImage(img, 0, 0,getParent());
	}
}