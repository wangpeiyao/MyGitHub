package com.qhyj.ui.login;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.qhyj.Const;
public class LoginFrame {
	private JPanel sysManagePanel;
	private JDesktopPane desktopPane;
	private JTabbedPane navigationPanel;
	private JFrame frame;
	private JLabel backLabel;
	// ���������Map���ͼ��϶���
	private Map<String, JInternalFrame> ifs = new HashMap<String, JInternalFrame>();
	public LoginFrame() {
		frame = new JFrame("��ӭʹ��"+Const.TITILESTR+"������ϵͳ");
		ImageIcon imageIcon = new ImageIcon("res/icon.jpg");  
        // ���ñ�������ͼ��Ϊface.gif  
		frame.setIconImage(imageIcon.getImage()); 
		frame.getContentPane().setBackground(Color.WHITE);
		frame.addComponentListener(new FrameListener());
		frame.getContentPane().setLayout(new BorderLayout());
//		frame.setBounds(100, 100, 800, 600);
//		frame.getGraphicsConfiguration().getDevice().setFullScreenWindow(frame); 
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		backLabel = new JLabel();// ������ǩ
		backLabel.setVerticalAlignment(SwingConstants.TOP);
		backLabel.setHorizontalAlignment(SwingConstants.CENTER);
		updateBackImage(); // ���»��ʼ������ͼƬ
		desktopPane = new JDesktopPane();
		desktopPane.add(backLabel, new Integer(Integer.MIN_VALUE));
		frame.getContentPane().add(desktopPane);
		navigationPanel = createNavigationPanel(); // ����������ǩ���
		frame.getContentPane().add(navigationPanel, BorderLayout.NORTH);
		frame.setVisible(true);
	}

	private JTabbedPane createNavigationPanel() { // ����������ǩ���ķ���
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setFocusable(false);
		tabbedPane.setBackground(new Color(223, 222, 224));
		tabbedPane.setBorder(new BevelBorder(BevelBorder.RAISED));

		JPanel baseManagePanel = new JPanel(); // ������Ϣ�������
		baseManagePanel.setBackground(new Color(223, 222, 224));
		baseManagePanel.setLayout(new BoxLayout(baseManagePanel,BoxLayout.X_AXIS));
		baseManagePanel.add(createFrameButton("�ͻ���Ϣ����", "CustomTreeFrame"));
		baseManagePanel.add(createFrameButton("��Ʒ��Ϣ����", "GoodsFrame"));
		baseManagePanel.add(createFrameButton("�����������", "RebateFrame"));
		
		
		
		JPanel depotManagePanel = new JPanel(); // ���������
		depotManagePanel.setBackground(new Color(223, 222, 224));
		depotManagePanel.setLayout(new BoxLayout(depotManagePanel,BoxLayout.X_AXIS));
		depotManagePanel.add(createFrameButton("����̵�", "KuCunPanDian"));

		JPanel sellManagePanel = new JPanel();// ���۹������
		sellManagePanel.setBackground(new Color(223, 222, 224));
		sellManagePanel.setLayout(new BoxLayout(sellManagePanel,BoxLayout.X_AXIS));
		sellManagePanel.add(createFrameButton("���۵�", "SellOrderFrame"));

		JPanel searchStatisticPanel = new JPanel();// ��ѯͳ�����
		searchStatisticPanel.setBounds(0, 0, 600, 41);
		searchStatisticPanel.setName("searchStatisticPanel");
		searchStatisticPanel.setBackground(new Color(223, 222, 224));
		searchStatisticPanel.setLayout(new BoxLayout(searchStatisticPanel,BoxLayout.X_AXIS));
		searchStatisticPanel.add(createFrameButton("��Ʒ��Ϣ��ѯ", "QryGoodsFrame"));
		searchStatisticPanel.add(createFrameButton("������Ϣ��ѯ", "XiaoShouChaXun"));
		searchStatisticPanel.add(createFrameButton("������Ϣ��ѯ", "RuKuChaXun"));
		searchStatisticPanel.add(createFrameButton("��������", "XiaoShouPaiHang"));

		JPanel stockManagePanel = new JPanel();// �����������
		stockManagePanel.setBackground(new Color(223, 222, 224));
		stockManagePanel.setLayout(new BoxLayout(stockManagePanel,BoxLayout.X_AXIS));
		stockManagePanel.add(createFrameButton("������", "BuyOrderFrame"));

		sysManagePanel = new JPanel();// ϵͳ�������
		sysManagePanel.setBackground(new Color(223, 222, 224));
		sysManagePanel.setLayout(new BoxLayout(sysManagePanel, BoxLayout.X_AXIS));
		sysManagePanel.add(createFrameButton("����Ա����", "CzyGL"));
		sysManagePanel.add(createFrameButton("��������", "GengGaiMiMa"));
		sysManagePanel.add(createFrameButton("Ȩ�޹���", "QuanManager"));

		tabbedPane.addTab("   ������Ϣ����   ", null, baseManagePanel, "������Ϣ����");
		tabbedPane.addTab("   ��������   ", null, stockManagePanel, "��������");
		tabbedPane.addTab("   ���۹���   ", null, sellManagePanel, "���۹���");
		tabbedPane.addTab("   ��ѯͳ��   ", null, searchStatisticPanel, "��ѯͳ��");
		tabbedPane.addTab("   ������   ", null, depotManagePanel, "������");
		tabbedPane.addTab("   ϵͳ����   ", null, sysManagePanel, "ϵͳ����");

		return tabbedPane;
	}
	/** *********************��������************************* */
	// Ϊ�ڲ��������Action�ķ���
	private JButton createFrameButton(String fName, String cname) {
		String imgUrl = "res/ActionIcon/" + fName + ".png";
		String imgUrl_roll = "res/ActionIcon/" + fName	+ "_roll.png";
		String imgUrl_down = "res/ActionIcon/" + fName	+ "_down.png";
		Icon icon = new ImageIcon(imgUrl);
		Icon icon_roll = null;
		if (imgUrl_roll != null)
			icon_roll = new ImageIcon(imgUrl_roll);
		Icon icon_down = null;
		if (imgUrl_down != null)
			icon_down = new ImageIcon(imgUrl_down);
		Action action = new openFrameAction(fName, cname, icon);
		JButton button = new JButton(action);
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setHideActionText(true);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		if (icon_roll != null)
			button.setRolloverIcon(icon_roll);
		if (icon_down != null)
			button.setPressedIcon(icon_down);
		return button;
	}
	// ��ȡ�ڲ������Ψһʵ������
	private JInternalFrame getIFrame(String frameName) {
		JInternalFrame jf = null;
		if (!ifs.containsKey(frameName)) {
			try {
				Class fClass = Class.forName("com.qhyj.ui.internalFrame." + frameName);
				Constructor constructor = fClass.getConstructor(null);
				jf = (JInternalFrame) constructor.newInstance(null);
				jf.setResizable(false);
				ifs.put(frameName, jf);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			jf = ifs.get(frameName);
		return jf;
	}
	// ���±���ͼƬ�ķ���
	private void updateBackImage() {
		if (backLabel != null) {
			int backw = LoginFrame.this.frame.getWidth();
			int backh = frame.getHeight();
			backLabel.setSize(backw, backh);
			backLabel.setText("<html><body><image width='" + backw
					+ "' height='" + (backh - 110) + "' src="
					+ LoginFrame.this.getClass().getResource("welcome.jpg")
					+ "'></img></body></html>");
		}
	}
	// ���������
	private final class FrameListener extends ComponentAdapter {
		public void componentResized(final ComponentEvent e) {
			updateBackImage();
		}
	}
	// ������˵���ĵ����¼�������
	protected final class openFrameAction extends AbstractAction {
		private String frameName = null;
		private openFrameAction() {
		}
		public openFrameAction(String cname, String frameName, Icon icon) {
			this.frameName = frameName;
			putValue(Action.NAME, cname);
			putValue(Action.SHORT_DESCRIPTION, cname);
			putValue(Action.SMALL_ICON, icon);
		}
		public void actionPerformed(final ActionEvent e) {
			JInternalFrame jf = getIFrame(frameName);
			// ���ڲ�����չ�ʱ�����ڲ���������ifs����������ô��塣
			jf.addInternalFrameListener(new InternalFrameAdapter() {
				public void internalFrameClosed(InternalFrameEvent e) {
					ifs.remove(frameName);
				}
			});
		
			if (jf.getDesktopPane() == null) {
				desktopPane.add(jf);
				jf.setVisible(true);
			}
			if(frameName.startsWith("Qry")) {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				int x = (int)(toolkit.getScreenSize().getWidth()-jf.getWidth())/2;
				int y = (int)(toolkit.getScreenSize().getHeight()-jf.getHeight()-navigationPanel.getHeight())/2;
				jf.setLocation(x, y);
			}
			try {
				jf.setSelected(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}