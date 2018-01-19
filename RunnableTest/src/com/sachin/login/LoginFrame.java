package com.sachin.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author lenovo
 */
public class LoginFrame extends javax.swing.JFrame {

	private volatile boolean isaddSchoolNameComplete = true;
	private volatile boolean isaddClassNameComplete = true;
	AnyviewJTextField schoolNameTextField = new AnyviewJTextField();
	AnyviewJTextField classNameTextField = new AnyviewJTextField();
	private Map<Integer, String> universityMap = new HashMap<Integer, String>();
	Map<Integer, String> classNameMap = new HashMap<>();
	private javax.swing.JButton cancelButton;
	private javax.swing.JComboBox<String> classNameBox;
	private javax.swing.JLabel classNameLabel;
	private javax.swing.JButton loginButton;
	private javax.swing.JPasswordField passwordField;
	private javax.swing.JLabel passwordLabel;
	private javax.swing.JComboBox<String> schoolBox;
	private javax.swing.JLabel schoolNameLabel;
	private javax.swing.JComboBox<String> studentIDBox;
	private javax.swing.JLabel studentIDLabel;

	public LoginFrame() {

		addWindowListener(new LogInFrameWindowListener());

		// 置中
		setLocationRelativeTo(null);
		initComponents();

		setResizable(false);
		setVisible(true);
		passwordField.requestFocusInWindow();
		loginButton.setEnabled(false);

	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		studentIDLabel = new javax.swing.JLabel();
		studentIDBox = new javax.swing.JComboBox<>();
		passwordLabel = new javax.swing.JLabel();
		passwordField = new javax.swing.JPasswordField();
		schoolNameLabel = new javax.swing.JLabel();
		classNameLabel = new javax.swing.JLabel();
		final DefaultComboBoxModel schoolBoxModel = new DefaultComboBoxModel();
		schoolBox = new JComboBox(schoolBoxModel) {
			public Dimension getPreferredSize() {
				return new Dimension(super.getPreferredSize().width, 0);
			}
		};
		initschoolBox();
		schoolNameTextField.setJBox(schoolBox);
		schoolNameTextField.setLayout(new BorderLayout());
		schoolNameTextField.add(schoolBox, BorderLayout.SOUTH);

		final DefaultComboBoxModel classNameBoxModel = new DefaultComboBoxModel();
		classNameBox = new JComboBox(classNameBoxModel) {
			public Dimension getPreferredSize() {
				return new Dimension(super.getPreferredSize().width, 0);
			}
		};
		initclassNameBox();
		classNameTextField.setJBox(classNameBox);
		classNameTextField.setLayout(new BorderLayout());
		classNameTextField.add(classNameBox, BorderLayout.SOUTH);

		loginButton = new javax.swing.JButton();
		cancelButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		studentIDLabel.setText("学号：");

		passwordLabel.setText("密码：");

		schoolNameLabel.setText("学校：");

		classNameLabel.setText("班级：");
		initStudentIDBox();
		initPasswordField();

		setupAutoComplete(schoolNameTextField, schoolBox);
		setupAutoComplete(classNameTextField, classNameBox);
		loginButton.setText("登陆");

		cancelButton.setText("取消");
		initLoginButton();
		initCancelButton();
		intiComponent2();

	}

	private static void setAdjusting(JComboBox jBox, boolean adjusting) {
		jBox.putClientProperty("is_adjusting", adjusting);
	}

	private static boolean isAdjusting(JComboBox cbInput) {
		if (cbInput.getClientProperty("is_adjusting") instanceof Boolean) {
			return (Boolean) cbInput.getClientProperty("is_adjusting");
		}
		return false;
	}

	public void setupAutoComplete(final JTextField txtInput, final JComboBox<String> jBox) {

		setAdjusting(jBox, false);

		jBox.setSelectedItem(null);

		jBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				// 在JComboBox的监听事件时总是执行两次,原因如下:
				// ItemListener类中的方法itemStateChanged()事件的itemState有关，itemState在这里的状态有两个，Selected
				// 和 deSelected（即选中和未被选中）
				// 所以，当改变下拉列表中被选中的项的时候，其实是触发了两次事件：
				// 第一次是上次被选中的项的 State 由 Selected 变为 deSelected ，即取消选择
				// 第二次是本次被选中的项的 State 由 deSelected 变为 Selected ，即新选中，所以，必然的
				// ItemStateChanged 事件中的代码要被执行两次了。
				// 加上最外面的if语句，就可以解决这个问题。

				if (jBox.equals(schoolBox)) {
					if (!isaddSchoolNameComplete) {
						return;
					}
				}
				if (jBox.equals(classNameBox)) {
					if (!isaddClassNameComplete)
						return;
				}
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (jBox.getSelectedItem() == null || jBox.getSelectedItem().toString().equals("-请选择学校-")) {
						return;
					}
					// 获取学校id和name
					if (!isAdjusting(jBox)) {
						if (jBox.equals(schoolBox)) {
							System.out.println(jBox.getSelectedItem() + "schoolJBox");
						}
						if (jBox.equals(classNameBox)) {
							System.out.println(jBox.getSelectedItem() + "classNmaeBox");

						}
						if (jBox.getSelectedItem() != null) {

							String str = jBox.getSelectedItem().toString();// aa
							if (jBox.equals(classNameBox)) {
								if (schoolNameTextField.getText().trim() != null)
									loginButton.setEnabled(true);
							}
							if(jBox.equals(schoolBox)){
								StartClinent.loginFrame.onNetTouchEvent(new PkGetClassResponse());
							}

							txtInput.setText(jBox.getSelectedItem().toString());

						} else {

							jBox.setPopupVisible(true);
						}
					}

				}
			}

		});

		txtInput.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				setAdjusting(jBox, true);
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (jBox.isPopupVisible()) {
						e.setKeyCode(KeyEvent.VK_ENTER);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == KeyEvent.VK_DOWN) {
					e.setSource(jBox);
					jBox.dispatchEvent(e);
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						if (jBox.getSelectedItem() != null)
							txtInput.setText(jBox.getSelectedItem().toString());
						jBox.setPopupVisible(false);
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					jBox.setPopupVisible(false);
				}
				setAdjusting(jBox, false);
			}
		});
		txtInput.getDocument().addDocumentListener(new DocumentListener() {

			public void insertUpdate(DocumentEvent e) {
				updateList();
			}

			public void removeUpdate(DocumentEvent e) {
				updateList();
			}

			public void changedUpdate(DocumentEvent e) {
				updateList();
			}

			private void updateList() {
				setAdjusting(jBox, true);
				JComboBox<String> jBox = (JComboBox<String>) txtInput.getComponent(0);
				if (jBox.equals(schoolBox)) {
				}
				if (jBox.equals(classNameBox)) {
				}

				jBox.removeAllItems();
				String containStr = txtInput.getText().trim();
				addNameToBox(jBox, containStr);

				jBox.setPopupVisible(jBox.getItemCount() > 0);
				System.out.println("count" + jBox.getItemCount());
				setAdjusting(jBox, false);
			}
		});

	}

	private void addNameToBox(JComboBox<String> jBox, String containStr) {

		if (jBox.equals(schoolBox)) {
			isaddSchoolNameComplete = false;
			if (containStr != null && containStr.trim() != "") {
				for (Entry<Integer, String> entry : universityMap.entrySet()) {
					if (entry.getValue().toLowerCase().contains(containStr.toLowerCase())) {
						jBox.addItem(entry.getValue());
					}
					if (entry.getValue().toLowerCase().equals(containStr.trim())) {

						// 发送网络请求 sendRequest(AnyProtocolKind.pkGetClass);
					}
				} // for

			} else {
				for (Entry<Integer, String> entry : universityMap.entrySet()) {

					jBox.addItem(entry.getValue());

				} // for
			} // selse

			isaddSchoolNameComplete = true;
			schoolBox.setSelectedItem(null);

		} // if schoolBox

		if (jBox.equals(classNameBox)) {

			isaddClassNameComplete = false;
			if (containStr != null && containStr.trim() != "") {
				for (Entry<Integer, String> entry : classNameMap.entrySet()) {
					if (entry.getValue().toLowerCase().contains(containStr.toLowerCase())) {
						jBox.addItem(entry.getValue());
					}
				} // for

			} else {
				for (Entry<Integer, String> entry : classNameMap.entrySet()) {
					jBox.addItem(entry.getValue());
				}

			} // selse

			isaddClassNameComplete = true;
			classNameBox.setSelectedItem(null);
		} // if classNamebox

	}

	/**
	 * 初始化学号输入框。 从配置文件导入班级信息作为下拉列表，并选中默认的班级代号
	 */
	private void initStudentIDBox() {

		studentIDBox.setBorder(BorderFactory.createLoweredBevelBorder());

		studentIDBox.setEditable(true);
		studentIDBox.setEnabled(true);
	}

	/**
	 * 初始化密码输入框。 没有限定输入密码的长度
	 */
	private void initPasswordField() {

		passwordField.setBorder(BorderFactory.createLoweredBevelBorder());

		passwordField.setEditable(true);
		passwordField.setEnabled(true);
	}

	private void initclassNameBox() {

		String iPItems = "请先选择学校！";
		boolean flag = false;
		classNameMap.put(0, iPItems);

		for (String str : iPItems.split("\\|")) {
			classNameBox.addItem(str);

		}

		classNameBox.setEditable(true);
		classNameBox.setEnabled(true);

	}

	private void initLoginButton() {
		ImageIcon icon = new ImageIcon("src/anyviewj/net/client/resoure/login.gif");

		loginButton.setIcon(icon);

		loginButton.addActionListener(new LoginButtonActionListener());
		loginButton.setEnabled(false);
	}

	private void initCancelButton() {
		ImageIcon icon = new ImageIcon("src/anyviewj/net/client/resoure/cancel.gif");

		cancelButton.setIcon(icon);

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	private void initschoolBox() {

		String iPItems = "复旦大学|广东工业大学|华南理工大学|清华大学|北京大学|请检查网络连接！";
		int i = 0;
		isaddSchoolNameComplete = false;
		for (String str : iPItems.split("\\|")) {
			schoolBox.addItem(str);
			universityMap.put(i, str);
			i++;
		}
		isaddSchoolNameComplete = true;
		schoolBox.setSelectedItem(null);

		schoolBox.setEditable(true);
		schoolBox.setEnabled(true);

	}

	public void onNetTouchEvent(Object msg) {
		// 更新学校列表
		if (msg instanceof PkGetAllUniversitiesResponse) {

			schoolBox.removeAllItems();
			PkGetAllUniversitiesResponse msgpkun = (PkGetAllUniversitiesResponse) msg;
			universityMap = msgpkun.getUniversities();

			addNameToBox(schoolBox, null);

			// schoolBox.validate();
		}
		if (msg instanceof PkGetClassResponse) {

			PkGetClassResponse pkGetClassResponse = (PkGetClassResponse) msg;

			// 更新班级信息

			classNameBox.removeAllItems();
			isaddClassNameComplete = false;
			classNameMap.clear();
			classNameMap.put(0, "class01");
			classNameMap.put(1, "class02");
			classNameMap.put(2, "class03");
			for (Map.Entry<Integer, String> entry : classNameMap.entrySet()) {
				classNameBox.addItem(entry.getValue());
			}

			isaddClassNameComplete = true;
			classNameBox.setSelectedItem(null);

		}

	}

	private class LogInFrameWindowListener extends WindowAdapter {
		@Override
		public void windowOpened(WindowEvent e) {

			LoginFrame.this.getRootPane().setDefaultButton(loginButton);
		}

		@Override
		public void windowClosing(WindowEvent e) {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			System.out.println("closing");
		}

		@Override
		public void windowClosed(WindowEvent e) {
			System.exit(0);
		}

	}

	private class LoginButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// 发送登录请求
		}

	}

	/**
	 * 布局设置
	 */
	private void intiComponent2() {

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(81, 81, 81).addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup().addComponent(passwordLabel)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
								.addGroup(
										layout.createSequentialGroup().addComponent(studentIDLabel).addGap(10, 10, 10)))
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
										.addComponent(studentIDBox, 0, 146, Short.MAX_VALUE).addComponent(
												passwordField))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(schoolNameLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addComponent(schoolNameTextField,
														javax.swing.GroupLayout.PREFERRED_SIZE, 132,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(classNameLabel))
										.addComponent(loginButton))
								.addGap(2, 2, 2)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(cancelButton).addComponent(classNameTextField,
												javax.swing.GroupLayout.PREFERRED_SIZE, 121,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(20, 20, 20)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(34, 34, 34)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(studentIDLabel).addComponent(studentIDBox, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(12, 12, 12)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(passwordLabel).addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(32, 32, 32)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(schoolNameLabel).addComponent(classNameLabel)
						.addComponent(schoolNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(classNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(loginButton).addComponent(cancelButton))
				.addContainerGap()));

		pack();
	}
}
