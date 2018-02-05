package com.qhyj.ui.internalFrame;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.qhyj.controller.MainController;
import com.qhyj.domain.CustomDo;
import com.qhyj.domain.SellOrderDo;
import com.qhyj.model.CommonBoxItem;
import com.qhyj.model.CustomItem;
import com.qhyj.model.SellRebateModel;
import com.qhyj.util.DateUtil;
import com.qhyj.util.LogUtil;

public class QryCustomOrderFrame extends JInternalFrame {
	private JTable table;
	private JComboBox orderBox;//���� 
	private JComboBox childeBox;//�Ƿ�����¼�
	private JComboBox orderUnit;//����λ
	private CustomTextField customBox; // �ͻ�
	private JTextField  showCount;//��ʾ��Ŀ
	private JTextField sDateField;
	private JTextField eDateField;
	public QryCustomOrderFrame() {
		super();
		setIconifiable(true);
		setClosable(true);
		setTitle("�ͻ�������ѯ");
		getContentPane().setLayout(new GridBagLayout());
		setSize(new Dimension(800, 550));
		
		table = new JTable();
		initTableModel(table);
		final JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.weighty = 1.0;
		gridBagConstraints_6.weightx = 1.0;
		gridBagConstraints_6.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_6.insets = new Insets(0, 10, 0, 10);
		gridBagConstraints_6.fill = GridBagConstraints.BOTH;
		gridBagConstraints_6.gridwidth =8;
		gridBagConstraints_6.gridheight = 1;
		gridBagConstraints_6.gridy = 7;
		gridBagConstraints_6.gridx = 0;
//		scrollPane.setSize(new Dimension(750, 400));
		getContentPane().add(scrollPane, gridBagConstraints_6);
		
		setupComponet(new JLabel("��ѯ���ڣ�"), 0, 0, 1, 30, false);
		sDateField = new JTextField(DateUtil.getToDayStr());
		setupComponet(sDateField, 1, 0, 1, 30, true);
		setupComponet(new JLabel("��"), 2, 0, 1, 10, false);
		eDateField = new JTextField(DateUtil.getToDayStr());
		setupComponet(eDateField,3 , 0, 1, 30, false);
		
		setupComponet(new JLabel(" �ͻ���"), 4, 0, 1, 1, false);
		customBox = new CustomTextField(); // �ͻ�
		initCustomBox();
		customBox.setPreferredSize(new Dimension(120, 21));
		setupComponet(customBox,5, 0, 1, 30, false);
		
		setupComponet(new JLabel(" �Ƿ�����¼���"), 6, 0, 1, 1, false);
		childeBox = new JComboBox(); 
		childeBox.addItem(new CommonBoxItem(1,"����"));
		childeBox.addItem(new CommonBoxItem(2,"������"));
		setupComponet(childeBox, 7, 0, 2, 30, false);
		
		setupComponet(new JLabel(" ��ʾ������"), 0, 1, 1, 1, false);
		showCount = new JTextField("10"); 
		setupComponet(showCount, 1, 1, 1, 30, true);
	
		
		
		setupComponet(new JLabel(" ����"), 2, 1, 1, 1, false);
		orderBox = new JComboBox(); 
		orderBox.addItem(new CommonBoxItem(1,"����"));
		orderBox.addItem(new CommonBoxItem(2,"����"));
		setupComponet(orderBox, 3, 1, 1, 30, true);
		
		setupComponet(new JLabel(" ����λ��"), 4, 1, 1, 1, false);
		orderUnit = new JComboBox(); 
		orderUnit.addItem(new CommonBoxItem(0,"���¼�"));
		orderUnit.addItem(new CommonBoxItem(1,"���"));
		orderUnit.addItem(new CommonBoxItem(2,"����"));
		setupComponet(orderUnit, 5, 1, 1, 30, true);
		
		final JButton queryButton = new JButton();
		queryButton.addActionListener(new QueryAction((DefaultTableModel) table.getModel()));
		queryButton.setText("��ѯ");
		setupComponet(queryButton, 6, 1, 1, 1, false);
		
		final JButton exportButton = new JButton("��������");
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				String[] keys = new String[]{"���","���۵���","�ͻ�", "����", "����","���","��ע","����ʱ��","������ʱ��"};
				String[] files = new String[] {"id","sumnum","cname","count","amount","sumamount","memo","createtime","lastupdatetime"};
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				int rowCount = tableModel.getRowCount();
				List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
				for(int i=1;i<rowCount;i++) {
					Map<String,Object> map = new HashMap<String,Object>() ;
					for(int j=0;j<files.length;j++) {
						map.put(files[j],tableModel.getValueAt(i,j));
					}
					dataList.add(map);
				}
				new DownLoad(getContentPane(),dataList,files,keys,"�ͻ�������Ϣ"+ DateUtil.fmtDateToYyyyMMDD(DateUtil.getToDay())+".xls");
			}
		});
		setupComponet(exportButton, 7, 1, 1, 1, false);
		
		
	}
	private void initCustomBox() {// ��ʼ���ͻ��ֶ�
		List list = MainController.getInstance().getAllCustomList();
//		CustomItem allitem = new CustomItem();
//		allitem.setId(0);
//		allitem.setName("ȫ��");
//		customBox.addItem(allitem);
		if(null==list) {
			return ;
		}
		for (int i=0;i<list.size();i++) {                                                                                                                                                               
			CustomDo customDo=(CustomDo) list.get(i); 
			CustomItem item = new CustomItem();
			item.setId(customDo.getCid());
			item.setName(customDo.getCname());
			customBox.addBoxItem(item);
			customBox.putUniversityMap(customDo.getCid(), item);
		}
	}
	// �������λ�ò���ӵ�������
	private void setupComponet(JComponent component, int gridx, int gridy, int gridwidth, int ipadx, boolean fill) {
		final GridBagConstraints gridBagConstrains = new GridBagConstraints();
		gridBagConstrains.gridx = gridx;
		gridBagConstrains.gridy = gridy;
		if (gridwidth > 1)
			gridBagConstrains.gridwidth = gridwidth;
		if (ipadx > 0)
			gridBagConstrains.ipadx = ipadx;
		gridBagConstrains.insets = new Insets(5, 1, 3, 1);
		if (fill)
			gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(component, gridBagConstrains);
	}
	private void initTableModel(JTable table) {
		table.setEnabled(false);
		table.setSize(new Dimension(550, 400));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		String[] tableHeads = new String[]{"����","�ͻ�","������", "�ܽ��","�������"};
		((DefaultTableModel) table.getModel()).setColumnIdentifiers(tableHeads);
		//�����п�
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(30);
		table.getColumnModel().getColumn(4).setPreferredWidth(20);
	}
	private void updateTable(List list,final DefaultTableModel dftm) {
		int num = dftm.getRowCount();
		for (int i = 0; i < num; i++) {
			dftm.removeRow(0);
		}
		SellRebateModel sellRebateModel;
		for(int i=0;null!=list&&i<list.size();i++) {
			sellRebateModel = (SellRebateModel) list.get(i);
			Vector rowData = new Vector();
			rowData.add((i+1));
			rowData.add(sellRebateModel.getCname());
			rowData.add(sellRebateModel.getSumCount());
			rowData.add(sellRebateModel.getSumAmount());
			rowData.add(sellRebateModel.getRebateAmount());
			dftm.addRow(rowData);
		}
	}
	private final class QueryAction implements ActionListener {
		private final DefaultTableModel dftm;
		private QueryAction(DefaultTableModel dftm) {
			this.dftm = dftm;
		}
		public void actionPerformed(final ActionEvent e) {
			String conName, conOperation, content;
			Map paraMap = new HashMap();
			Date sDate = null;
			Date eDate = null;
			Integer kehuid =null;
			String sellOrderNum= null;
			try {
				sDate = DateUtil.fmtStrToDate(sDateField.getText(),"yyyy-MM-dd");
				eDate = DateUtil.fmtStrToDate(eDateField.getText(),"yyyy-MM-dd");
			}catch (Exception e1) {
				e1.getMessage();
				LogUtil.error("�ͻ�������ѯ", e1);
			}
			paraMap.put("sDate", sDate);
			paraMap.put("eDate", eDate);
			paraMap.put("isInclude", ((CommonBoxItem)childeBox.getSelectedItem()).getKey());//�Ƿ�����¼�
			paraMap.put("orderUnit", ((CommonBoxItem)orderUnit.getSelectedItem()).getKey());//����λ
			paraMap.put("orderFlag", ((CommonBoxItem)orderBox.getSelectedItem()).getKey());//����˳��������
			paraMap.put("showCount", showCount.getText());//��ʾ����
			paraMap.put("cid",customBox.getKey());//��ʾ����
			List list = searchInfo(paraMap);
			updateTable(list, dftm);
		}
		private List searchInfo(Map map) {
			List list = MainController.getInstance().getSellRebateListByMap(map);
			return list;
		}
	}
}
