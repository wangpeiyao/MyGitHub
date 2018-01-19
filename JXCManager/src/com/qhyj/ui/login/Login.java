package com.qhyj.ui.login;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.qhyj.Const;
import com.qhyj.controller.MainController;
import com.qhyj.domain.CustomDo;
import com.qhyj.domain.UserDo;

public class Login extends JFrame {
	private JLabel userLabel;
	private JLabel passLabel;
	private JButton exit;
	private JButton login;
	private static UserDo user;
	public Login() {
		setTitle("欢迎使用"+Const.TITILESTR+"订单管理系统");
		final JPanel panel = new LoginPanel();
		panel.setLayout(null);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/res/MyStock.png"));  

        // 设置标题栏的图标为face.gif  
        this.setIconImage(imageIcon.getImage());  
        
//		getContentPane().add(panel);
		this.add(panel,BorderLayout.CENTER);
	
		setBounds(300, 200, panel.getWidth(), panel.getHeight());
		this.setLocationRelativeTo(null);
		userLabel = new JLabel();
		userLabel.setText("用户名：");
		userLabel.setBounds(100, 135, 200, 18);
		panel.add(userLabel);
		final JTextField userName = new JTextField();
		userName.setBounds(150, 135, 200, 18);
		panel.add(userName);
		passLabel = new JLabel();
		passLabel.setText("密  码：");
		passLabel.setBounds(100, 165, 200, 18);
		panel.add(passLabel);
		final JPasswordField userPassword = new JPasswordField();
		userPassword.addKeyListener(new KeyAdapter() {
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == 10)
					login.doClick();
			}
		});
		userPassword.setBounds(150, 165, 200, 18);
		panel.add(userPassword);
		login = new JButton();
		login.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
//				user = Dao.getUser(userName.getText(), userPassword.getText());
				try {
					user = MainController.getInstance().getUser(userName.getText(), userPassword.getText());
				}catch (Exception e1) {
				}
				if (null==user||user.getUname() == null ) {
					userName.setText(null);
					userPassword.setText(null);
					JOptionPane.showMessageDialog(Login.this, "用户名密码错误！");
					return;
				}
				setVisible(false);
				
				new LoginFrame();
				initNotiy();
				
			}
		});
		login.setText("登录");
		login.setBounds(180, 195, 60, 18);
		panel.add(login);
		exit = new JButton();
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				System.exit(0);
			}
		});
		exit.setText("退出");
		exit.setBounds(260, 195, 60, 18);
		panel.add(exit);
		setVisible(true);
//		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void initNotiy() {
		List<CustomDo> list = MainController.getInstance().getCustomListBirthday();
		TranslucentFrame frame = new TranslucentFrame(list);
		Thread thread = new Thread(frame);
		thread.start();
	}
	private void setClosable(boolean b) {
		// TODO Auto-generated method stub
		
	}
	private void setIconifiable(boolean b) {
		// TODO Auto-generated method stub
		
	}
	public static UserDo getUser() {
		return user;
	}
//	public void setUser(UserDo user) {
//		this.user = user;
//	}
	
}
