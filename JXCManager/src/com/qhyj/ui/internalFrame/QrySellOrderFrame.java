package com.qhyj.ui.internalFrame;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
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
import com.qhyj.domain.GoodsDo;
import com.qhyj.domain.SellOrderDo;
import com.qhyj.model.CommonBoxItem;
import com.qhyj.model.CustomItem;
import com.qhyj.model.GoodsItem;
import com.qhyj.util.DateUtil;
import com.qhyj.util.LogUtil;
public class QrySellOrderFrame extends JInternalFrame {
	private JTable table;
	private JTextField sDateField;
	private JTextField eDateField;
	private JTextField sellOrderNumField;
	private CustomTextField customBox; // �ͻ�
	private GoodsTextField goodBox;
	private JComboBox childeBox; // �ͻ�
	private JComboBox payStateBox; // �ͻ�
	public QrySellOrderFrame() {
		super();
		setIconifiable(true);
		setClosable(true);
		setTitle("���۵���ѯ");
		getContentPane().setLayout(new GridBagLayout());
//		setBounds(10, 10, 900, 600);
		setSize(new Dimension(950, 550));
		
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
		gridBagConstraints_6.gridwidth = 8;
		gridBagConstraints_6.gridheight = 1;
		gridBagConstraints_6.gridy = 7;
		gridBagConstraints_6.gridx = 0;
//		scrollPane.setSize(new Dimension(750, 400));
		getContentPane().add(scrollPane, gridBagConstraints_6);

		setupComponet(new JLabel(" �������ڣ�"), 0, 0, 1, 1, false);
		sDateField = new JTextField(DateUtil.getToDayStr());
		setupComponet(sDateField, 1, 0, 1, 80, true);
		setupComponet(new JLabel(" �� "), 2, 0, 1, 1, false);
		eDateField = new JTextField(DateUtil.getToDayStr());
		setupComponet(eDateField,3 , 0, 1, 80, false);
		
		
		
	
		
		setupComponet(new JLabel(" ��Ʒ��"), 4, 0, 1, 1, false);
		goodBox = new GoodsTextField(); // �ͻ�
		initGoodBox();
		goodBox.setPreferredSize(new Dimension(120, 21));
		goodBox.setMaximumSize(new Dimension(120,21));
		goodBox.setMinimumSize(new Dimension(120,21));
		setupComponet(goodBox,5, 0, 1, 1, false);
		
		setupComponet(new JLabel(" ���۵��ţ�"), 6, 0, 1, 1, false);
		sellOrderNumField = new JTextField();
		setupComponet(sellOrderNumField,7 , 0, 1, 120, false);

		setupComponet(new JLabel(" �ͻ���"), 0, 1, 1, 1, false);
		customBox = new CustomTextField(); // �ͻ�
		customBox.setPreferredSize(new Dimension(120, 21));
		customBox.setMaximumSize(new Dimension(120,21));
		customBox.setMinimumSize(new Dimension(120,21));
		initCustomBox();
		setupComponet(customBox,1, 1, 1, 1, false);
		
		setupComponet(new JLabel(" �Ƿ�����¼���"), 2, 1, 1, 1, false);
		childeBox = new JComboBox(); 
		childeBox.addItem(new CommonBoxItem(1,"����"));
		childeBox.addItem(new CommonBoxItem(2,"������"));
		setupComponet(childeBox, 3, 1, 1, 30, false);
		
		setupComponet(new JLabel("����״̬��"), 4, 1, 1, 1, false);
		payStateBox = new JComboBox(); 
		payStateBox.addItem(new CommonBoxItem(0,"ȫ��"));
		payStateBox.addItem(new CommonBoxItem(1,"�Ѹ���"));
		payStateBox.addItem(new CommonBoxItem(2,"Ƿ��"));
		setupComponet(payStateBox, 5, 1, 1, 30, false);

		final JButton queryButton = new JButton();
		queryButton.addActionListener(new QueryAction((DefaultTableModel) table.getModel()));
		setupComponet(queryButton, 6, 1, 1, 1, false);
		queryButton.setText("��ѯ");

//		final JButton showAllButton = new JButton("��ʾȫ������");
//		showAllButton.addActionListener(new ActionListener() {
//			public void actionPerformed(final ActionEvent e) {
//				conditionContent.setText("");
//				List<GoodsDo> list = MainController.getInstance().getAllSellOrderList();
//				updateTable(list,(DefaultTableModel) table.getModel());
//			}
//		});
//		setupComponet(showAllButton, 9, 0, 1, 1, false);
		
		final JButton exportButton = new JButton("����");
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				String[] keys = new String[]{"���","���۵���","�ͻ�","����״̬","��Ʒ", "����", "����","���","��ע","����ʱ��","������ʱ��"};
				String[] files = new String[] {"id","sumnum","cname","paystate","gname","count","amount","sumamount","memo","createtime","lastupdatetime"};
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
				new DownLoad(getContentPane(),dataList,files,keys,"���۵���Ϣ"+ DateUtil.fmtDateToYyyyMMDD(DateUtil.getToDay())+".xls");
			}
		});
		
		setupComponet(exportButton, 7, 1, 1, 1, false);
//		pack();
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
//			customBox.addItem(item);
			customBox.addBoxItem(item);
			customBox.putUniversityMap(customDo.getCid(), item);
		}
	}
	private void initGoodBox() {// ��ʼ����Ʒ�ֶ�
		List list = MainController.getInstance().getAllGoodsList();
//		GoodsItem allitem = new GoodsItem();
//		allitem.setId(0);
//		allitem.setName("ȫ��");
//		goodBox.addItem(allitem);
		if(null==list) {
			return ;
		}
		for (int i=0;i<list.size();i++) {                                                                                                                                                               
			GoodsDo goodsDo=(GoodsDo) list.get(i); 
			GoodsItem item = new GoodsItem();
			item.setId(goodsDo.getGid());
			item.setName(goodsDo.getGname());
			goodBox.addBoxItem(item);
			goodBox.putUniversityMap(goodsDo.getGid(), item);
		}
	}
	private void initTableModel(JTable table) {
		table.setEnabled(false);
		table.setSize(new Dimension(850, 400));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		String[] tableHeads = new String[]{"���","���۵���","�ͻ�","����״̬","��Ʒ","����", "����","���","��ע","����ʱ��","������ʱ��"};
		((DefaultTableModel) table.getModel()).setColumnIdentifiers(tableHeads);
		//�����п�
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
		table.getColumnModel().getColumn(5).setPreferredWidth(30);
		table.getColumnModel().getColumn(6).setPreferredWidth(20);
		table.getColumnModel().getColumn(7).setPreferredWidth(40);
		table.getColumnModel().getColumn(8).setPreferredWidth(150);
		table.getColumnModel().getColumn(9).setPreferredWidth(100);
		table.getColumnModel().getColumn(10).setPreferredWidth(100);
	}
	
	private void updateTable(List list,final DefaultTableModel dftm) {
		int num = dftm.getRowCount();
		for (int i = 0; i < num; i++) {
			dftm.removeRow(0);
		}
		SellOrderDo sellOrderDo;
		int sumaccount = 0; 
		BigDecimal sumamount = new BigDecimal(0);
		for(int i=0;null!=list&&i<list.size();i++) {
			sellOrderDo = (SellOrderDo) list.get(i);
			sumaccount+=sellOrderDo.getCount();
			sumamount=sumamount.add(new BigDecimal(sellOrderDo.getSumAmount()));
			Vector rowData = new Vector();
			rowData.add((i+1));
			rowData.add(sellOrderDo.getSellNum());
			CustomDo customDo = MainController.getInstance().getCustomById(sellOrderDo.getCid());
			rowData.add(customDo.getCname());
			rowData.add(new Integer(2).equals(sellOrderDo.getPayState())?"Ƿ��":"�Ѹ���");
			GoodsDo goodsDo = MainController.getInstance().getGoodsById(sellOrderDo.getGid());
			rowData.add(goodsDo.getGname());
			rowData.add(sellOrderDo.getCount());
			rowData.add(sellOrderDo.getAmount());
            rowData.add(sellOrderDo.getSumAmount());
            rowData.add(sellOrderDo.getMemo());
            rowData.add(DateUtil.formatLongDate(sellOrderDo.getCreateTime()));
            rowData.add(DateUtil.formatLongDate(sellOrderDo.getLastUpdateTime()));
			dftm.addRow(rowData);
		}
		//����ϼ�
		Vector rowData = new Vector();
		rowData.add("");
		rowData.add("��      ��");
		rowData.add("");
		rowData.add("");
		rowData.add(sumaccount);
		rowData.add("");
		rowData.add(sumamount.setScale(2, BigDecimal.ROUND_HALF_UP));
		rowData.add("");
		rowData.add("");
		rowData.add("");
		dftm.addRow(rowData);
		
	}
	// �������λ�ò���ӵ�������
	private void setupComponet(JComponent component, int gridx, int gridy,
			int gridwidth, int ipadx, boolean fill) {
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
			Integer gid =null;
			String sellOrderNum= null;
			try {
				sDate = DateUtil.fmtStrToDate(sDateField.getText(),"yyyy-MM-dd");
				eDate = DateUtil.fmtStrToDate(eDateField.getText(),"yyyy-MM-dd");
				kehuid = customBox.getKey();
				gid = goodBox.getKey();
				sellOrderNum = sellOrderNumField.getText();
				
				
			}catch (Exception e1) {
				e1.getMessage();
				LogUtil.error("���۵���ѯ", e1);
			}
			paraMap.put("sDate", sDate);
			paraMap.put("eDate", eDate);
			paraMap.put("cid", kehuid);
			paraMap.put("sellOrderNum", sellOrderNumField.getText());
			paraMap.put("gid", gid);
			paraMap.put("isInclude", ((CommonBoxItem)childeBox.getSelectedItem()).getKey());//�Ƿ�����¼�
			paraMap.put("payState", ((CommonBoxItem)payStateBox.getSelectedItem()).getKey());//�Ƿ�����¼�
			List list = searchInfo(paraMap);
			updateTable(list, dftm);
		}
		private List searchInfo(Map map) {
			List list = MainController.getInstance().getSellOrderListByMap(map);
			return list;
		}
	}
}
