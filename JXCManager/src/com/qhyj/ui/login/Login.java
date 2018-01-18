package com.qhyj.ui.login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.qhyj.Const;
import com.qhyj.controller.MainController;
import com.qhyj.domain.UserDo;

public class Login extends JFrame {
	private JLabel userLabel;
	private JLabel passLabel;
	private JButton exit;
	private JButton login;
	private static UserDo user;
	public Login() {
		setTitle("��ӭʹ��"+Const.TITILESTR+"��������ϵͳ");
		final JPanel panel = new LoginPanel();
		panel.setLayout(null);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/res/MyStock.png"));  

        // ���ñ�������ͼ��Ϊface.gif  
        this.setIconImage(imageIcon.getImage());  
        
		getContentPane().add(panel);
		setBounds(300, 200, panel.getWidth(), panel.getHeight());
		userLabel = new JLabel();
		userLabel.setText("�û�����");
		userLabel.setBounds(100, 135, 200, 18);
		panel.add(userLabel);
		final JTextField userName = new JTextField();
		userName.setBounds(150, 135, 200, 18);
		panel.add(userName);
		passLabel = new JLabel();
		passLabel.setText("��  �룺");
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
				user = MainController.getInstance().getUser(userName.getText(), userPassword.getText());
				if (user.getUsername() == null || user.getName() == null) {
					userName.setText(null);
					userPassword.setText(null);
					return;
				}
				setVisible(false);
				new LoginFrame();
			}
		});
		login.setText("��¼");
		login.setBounds(180, 195, 60, 18);
		panel.add(login);
		exit = new JButton();
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				System.exit(0);
			}
		});
		exit.setText("�˳�");
		exit.setBounds(260, 195, 60, 18);
		panel.add(exit);
		setVisible(true);
//		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
