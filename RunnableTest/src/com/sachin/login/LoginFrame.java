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

		// ����
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

		studentIDLabel.setText("ѧ�ţ�");

		passwordLabel.setText("���룺");

		schoolNameLabel.setText("ѧУ��");

		classNameLabel.setText("�༶��");
		initStudentIDBox();
		initPasswordField();

		setupAutoComplete(schoolNameTextField, schoolBox);
		setupAutoComplete(classNameTextField, classNameBox);
		loginButton.setText("��½");

		cancelButton.setText("ȡ��");
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
				// ��JComboBox�ļ����¼�ʱ����ִ������,ԭ������:
				// ItemListener���еķ���itemStateChanged()�¼���itemState�йأ�itemState�������״̬��������Selected
				// �� deSelected����ѡ�к�δ��ѡ�У�
				// ���ԣ����ı������б��б�ѡ�е����ʱ����ʵ�Ǵ����������¼���
				// ��һ�����ϴα�ѡ�е���� State �� Selected ��Ϊ deSelected ����ȡ��ѡ��
				// �ڶ����Ǳ��α�ѡ�е���� State �� deSelected ��Ϊ Selected ������ѡ�У����ԣ���Ȼ��
				// ItemStateChanged �¼��еĴ���Ҫ��ִ�������ˡ�
				// �����������if��䣬�Ϳ��Խ��������⡣

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
					if (jBox.getSelectedItem() == null || jBox.getSelectedItem().toString().equals("-��ѡ��ѧУ-")) {
						return;
					}
					// ��ȡѧУid��name
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

						// ������������ sendRequest(AnyProtocolKind.pkGetClass);
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
	 * ��ʼ��ѧ������� �������ļ�����༶��Ϣ��Ϊ�����б���ѡ��Ĭ�ϵİ༶����
	 */
	private void initStudentIDBox() {

		studentIDBox.setBorder(BorderFactory.createLoweredBevelBorder());

		studentIDBox.setEditable(true);
		studentIDBox.setEnabled(true);
	}

	/**
	 * ��ʼ����������� û���޶���������ĳ���
	 */
	private void initPasswordField() {

		passwordField.setBorder(BorderFactory.createLoweredBevelBorder());

		passwordField.setEditable(true);
		passwordField.setEnabled(true);
	}

	private void initclassNameBox() {

		String iPItems = "����ѡ��ѧУ��";
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

		String iPItems = "������ѧ|�㶫��ҵ��ѧ|��������ѧ|�廪��ѧ|������ѧ|�����������ӣ�";
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
		// ����ѧУ�б�
		if (msg instanceof PkGetAllUniversitiesResponse) {

			schoolBox.removeAllItems();
			PkGetAllUniversitiesResponse msgpkun = (PkGetAllUniversitiesResponse) msg;
			universityMap = msgpkun.getUniversities();

			addNameToBox(schoolBox, null);

			// schoolBox.validate();
		}
		if (msg instanceof PkGetClassResponse) {

			PkGetClassResponse pkGetClassResponse = (PkGetClassResponse) msg;

			// ���°༶��Ϣ

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
			// ���͵�¼����
		}

	}

	/**
	 * ��������
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
