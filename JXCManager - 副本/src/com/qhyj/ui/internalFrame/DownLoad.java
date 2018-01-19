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
	private List<Map<String,Object>> dataList;//数据集合
	private String[] fields;
	private String[] keys;
	private String fileName;
	JInternalFrame frame = new JInternalFrame("");// 框架布局
	JTabbedPane tabPane = new JTabbedPane();// 选项卡布局
	Container con = new Container();//
	JLabel label1 = new JLabel("文件目录");
	JLabel label2 = new JLabel("选择文件");
	JTextField text1 = new JTextField("./");// TextField 目录的路径
	JButton button1 = new JButton("...");// 选择
	JFileChooser jfc1 = new JFileChooser();// 文件选择器
	JFileChooser jfc2 = new JFileChooser();// 文件选择器
	JLabel label = new JLabel("文件名称："+fileName);
	JButton button3 = new JButton("确定");// 下载按钮
	JTextField jTextField1 = new JTextField();

	DownLoad( Container parentPanel,List<Map<String,Object>> dataList,String[] fields,String[] keys,String fileName) {
		this.fileName=fileName;
		this.keys=keys;
		this.dataList=dataList;
		this.fields=fields;
		double lx = parentPanel.getWidth();
		double ly = parentPanel.getHeight();
		frame.setLocation(new Point((int) (lx / 2) - 200, (int) (ly / 2) - 200));// 设定窗口出现位置
		frame.setSize(300, 200);// 设定窗口大小
//		frame.setLocationRelativeTo(null);
		frame.setContentPane(tabPane);// 设置布局
		
		label1.setBounds(10, 10, 70, 20);
		text1.setBounds(75, 10, 120, 20);
		label.setBounds(10, 30, 300, 20);
		button1.setBounds(210, 10, 50, 20);
		label2.setBounds(75, 35, 120, 20);
		button3.setBounds(10, 60, 60, 20);
		button1.addActionListener(this); // 添加事件处理
		button3.addActionListener(this); // 添加事件处理
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
//		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);// 使能关闭窗口，结束程序
		tabPane.add("导出页面", con);// 添加布局1
		
//		parentPanel.add(frame);
		parentPanel.getParent().add(frame);
		frame.setClosable(true);
		frame.setVisible(true);// 窗口可见
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
	     * 时间监听的方法 
	     */  
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(button1)) {// 判断触发方法的按钮是哪个
			jfc1.setFileSelectionMode(1);// 设定只能选择到文件夹
			int state = jfc1.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;
			} else {
				File f = jfc1.getSelectedFile();// f为选择到的目录
				text1.setText(f.getAbsolutePath());
			}
		}
		if (e.getSource().equals(button3)) {
			String path = jfc1.getSelectedFile().getAbsolutePath(); // 目标路径
			try {
				download(path);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	    /**
	     * 
	     * @param from_file_name  源文件名
	     * @param to_path 目标路径
	     * @throws IOException
	     */
	public void download(String to_path)throws IOException {
		OutputStream output = null;
		InputStream input = null;
		try {
			ExcelUtils.exportData(dataList, fields, keys, to_path, fileName);
			String msg = "下载成功<"+to_path+File.separator+fileName+">";
			JOptionPane.showMessageDialog(null, msg, "提示", 2);
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "下载失败！", "提示",JOptionPane.ERROR_MESSAGE);
			LogUtil.error(fileName+"下载失败", e1);
			e1.printStackTrace();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "下载失败！", "提示",JOptionPane.ERROR_MESSAGE);
			LogUtil.error(fileName+"下载失败", e1);
			e1.printStackTrace();
		}
	}

}

