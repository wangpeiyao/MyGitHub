package com.qhyj.ui.internalFrame;

import java.awt.Container;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.qhyj.util.ExcelUtils;
import com.qhyj.util.LogUtil;


public class DownLoad implements ActionListener{
	private List<Map<String,Object>> dataList;//���ݼ���
	private String[] fields;
	private String[] keys;
	private String fileName;
	JInternalFrame frame = new JInternalFrame("");// ��ܲ���
	JTabbedPane tabPane = new JTabbedPane();// ѡ�����
	Container con = new Container();//
	JLabel label1 = new JLabel("�ļ�Ŀ¼");
	JLabel label2 = new JLabel("ѡ���ļ�");
	JTextField text1 = new JTextField("./");// TextField Ŀ¼��·��
	JButton button1 = new JButton("...");// ѡ��
	JFileChooser jfc1 = new JFileChooser();// �ļ�ѡ����
	JFileChooser jfc2 = new JFileChooser();// �ļ�ѡ����
	JLabel label = new JLabel("�ļ����ƣ�"+fileName);
	JButton button3 = new JButton("ȷ��");// ���ذ�ť
	JTextField jTextField1 = new JTextField();

	DownLoad( Container parentPanel,List<Map<String,Object>> dataList,String[] fields,String[] keys,String fileName) {
		this.fileName=fileName;
		this.keys=keys;
		this.dataList=dataList;
		this.fields=fields;
		double lx = parentPanel.getWidth();
		double ly = parentPanel.getHeight();
		frame.setLocation(new Point((int) (lx / 2) - 200, (int) (ly / 2) - 200));// �趨���ڳ���λ��
		frame.setSize(300, 200);// �趨���ڴ�С
//		frame.setLocationRelativeTo(null);
		frame.setContentPane(tabPane);// ���ò���
		
		label1.setBounds(10, 10, 70, 20);
		text1.setBounds(75, 10, 120, 20);
		label.setBounds(10, 30, 300, 20);
		button1.setBounds(210, 10, 50, 20);
		label2.setBounds(75, 35, 120, 20);
		button3.setBounds(10, 60, 60, 20);
		button1.addActionListener(this); // ����¼�����
		button3.addActionListener(this); // ����¼�����
		con.add(label1);
		con.add(label);
		con.add(text1);
		con.add(button1);
		con.add(button3);
		
		
//		try {
////			frame.setSelected(true);
//		} catch (PropertyVetoException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);// ʹ�ܹرմ��ڣ���������
		tabPane.add("����ҳ��", con);// ��Ӳ���1
		
//		parentPanel.add(frame);
		parentPanel.getParent().add(frame);
		frame.setClosable(true);
		frame.setVisible(true);// ���ڿɼ�
//		try {
////			frame.setIconifiable(true);
////			frame.setClosable(true);
////			frame.setSelected(true);
//		} catch (PropertyVetoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	    /** 
	     * ʱ������ķ��� 
	     */  
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(button1)) {// �жϴ��������İ�ť���ĸ�
			jfc1.setFileSelectionMode(1);// �趨ֻ��ѡ���ļ���
			int state = jfc1.showOpenDialog(null);// �˾��Ǵ��ļ�ѡ��������Ĵ������
			if (state == 1) {
				return;
			} else {
				File f = jfc1.getSelectedFile();// fΪѡ�񵽵�Ŀ¼
				text1.setText(f.getAbsolutePath());
			}
		}
		if (e.getSource().equals(button3)) {
			String path = jfc1.getSelectedFile().getAbsolutePath(); // Ŀ��·��
			try {
				download(path);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	    /**
	     * 
	     * @param from_file_name  Դ�ļ���
	     * @param to_path Ŀ��·��
	     * @throws IOException
	     */
	public void download(String to_path)throws IOException {
		OutputStream output = null;
		InputStream input = null;
		try {
			ExcelUtils.exportData(dataList, fields, keys, to_path, fileName);
			String msg = "���سɹ�<"+to_path+File.separator+fileName+">";
			JOptionPane.showMessageDialog(null, msg, "��ʾ", 2);
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "����ʧ�ܣ�", "��ʾ",JOptionPane.ERROR_MESSAGE);
			LogUtil.error(fileName+"����ʧ��", e1);
			e1.printStackTrace();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "����ʧ�ܣ�", "��ʾ",JOptionPane.ERROR_MESSAGE);
			LogUtil.error(fileName+"����ʧ��", e1);
			e1.printStackTrace();
		}
	}

}

