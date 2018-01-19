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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.qhyj.controller.MainController;
import com.qhyj.domain.CustomDo;
import com.qhyj.domain.GoodsDo;
import com.qhyj.domain.SellOrderDo;
import com.qhyj.model.CustomItem;
import com.qhyj.ui.panel.GoodsModPanel;
import com.qhyj.util.DateUtil;
import com.qhyj.util.LogUtil;
public class QrySellOrderFrame extends JInternalFrame {
	private JTable table;
	private JTextField sDateField;
	private JTextField eDateField;
	private JTextField sellOrderNumField;
	private JComboBox customBox; // 客户
	public QrySellOrderFrame() {
		super();
		setIconifiable(true);
		setClosable(true);
		setTitle("销售单查询");
		getContentPane().setLayout(new GridBagLayout());
//		setBounds(10, 10, 900, 600);
		setSize(new Dimension(900, 550));
		
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
		gridBagConstraints_6.gridwidth = 11;
		gridBagConstraints_6.gridheight = 1;
		gridBagConstraints_6.gridy = 7;
		gridBagConstraints_6.gridx = 0;
//		scrollPane.setSize(new Dimension(750, 400));
		getContentPane().add(scrollPane, gridBagConstraints_6);

		setupComponet(new JLabel(" 销售日期："), 0, 0, 1, 1, false);
		sDateField = new JTextField(DateUtil.getToDayStr());
		setupComponet(sDateField, 1, 0, 1, 30, true);
		setupComponet(new JLabel(" 至 "), 2, 0, 1, 1, false);
		eDateField = new JTextField(DateUtil.getToDayStr());
		setupComponet(eDateField,3 , 0, 1, 30, false);
		
		setupComponet(new JLabel(" 销售单号："), 4, 0, 1, 1, false);
		sellOrderNumField = new JTextField();
		setupComponet(sellOrderNumField,5 , 0, 1, 120, false);
		
		setupComponet(new JLabel(" 客户："), 6, 0, 1, 1, false);
		customBox = new JComboBox(); // 客户
		initCustomBox();
		customBox.setPreferredSize(new Dimension(120, 21));
		setupComponet(customBox,7 , 0, 1, 30, false);


		final JButton queryButton = new JButton();
		queryButton.addActionListener(new QueryAction((DefaultTableModel) table.getModel()));
		setupComponet(queryButton, 8, 0, 1, 1, false);
		queryButton.setText("查询");

//		final JButton showAllButton = new JButton("显示全部数据");
//		showAllButton.addActionListener(new ActionListener() {
//			public void actionPerformed(final ActionEvent e) {
//				conditionContent.setText("");
//				List<GoodsDo> list = MainController.getInstance().getAllSellOrderList();
//				updateTable(list,(DefaultTableModel) table.getModel());
//			}
//		});
//		setupComponet(showAllButton, 9, 0, 1, 1, false);
		
		final JButton exportButton = new JButton("导出数据");
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				String[] keys = new String[]{"序号","销售单号","客户", "数量", "单价","金额","备注","创建时间","最后更新时间"};
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
				new DownLoad(getContentPane(),dataList,files,keys,"销售单信息"+ DateUtil.fmtDateToYyyyMMDD(DateUtil.getToDay())+".xls");
			}
		});
		
		setupComponet(exportButton, 10, 0, 1, 1, false);
//		pack();
	}
	private void initCustomBox() {// 初始化客户字段
		List list = MainController.getInstance().getAllCustomList();
		CustomItem allitem = new CustomItem();
		allitem.setId(0);
		allitem.setName("全部");
		customBox.addItem(allitem);
		if(null==list) {
			return ;
		}
		for (int i=0;i<list.size();i++) {                                                                                                                                                               
			CustomDo customDo=(CustomDo) list.get(i); 
			CustomItem item = new CustomItem();
			item.setId(customDo.getCid());
			item.setName(customDo.getCname());
			customBox.addItem(item);
		}
	}
	private void initTableModel(JTable table) {
		table.setEnabled(false);
		table.setSize(new Dimension(850, 400));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		String[] tableHeads = new String[]{"序号","销售单号","客户", "数量", "单价","金额","备注","创建时间","最后更新时间"};
		((DefaultTableModel) table.getModel()).setColumnIdentifiers(tableHeads);
		//设置列宽
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(30);
		table.getColumnModel().getColumn(4).setPreferredWidth(20);
		table.getColumnModel().getColumn(5).setPreferredWidth(40);
		table.getColumnModel().getColumn(6).setPreferredWidth(150);
		table.getColumnModel().getColumn(7).setPreferredWidth(100);
		table.getColumnModel().getColumn(8).setPreferredWidth(100);
	}
	
	private void updateTable(List list,final DefaultTableModel dftm) {
		int num = dftm.getRowCount();
		for (int i = 0; i < num; i++) {
			dftm.removeRow(0);
		}
		SellOrderDo sellOrderDo;
		for(int i=0;null!=list&&i<list.size();i++) {
			sellOrderDo = (SellOrderDo) list.get(i);
			Vector rowData = new Vector();
			rowData.add((i+1));
			rowData.add(sellOrderDo.getSellNum());
			CustomDo customDo = MainController.getInstance().getCustomById(sellOrderDo.getCid());
			rowData.add(customDo.getCname());
			rowData.add(sellOrderDo.getCount());
			rowData.add(sellOrderDo.getAmount());
            rowData.add(sellOrderDo.getSumAmount());
            rowData.add(sellOrderDo.getMemo());
            rowData.add(DateUtil.formatLongDate(sellOrderDo.getCreateTime()));
            rowData.add(DateUtil.formatLongDate(sellOrderDo.getLastUpdateTime()));
			dftm.addRow(rowData);
		}
	}
	// 设置组件位置并添加到容器中
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
			String sellOrderNum= null;
			try {
				sDate = DateUtil.fmtStrToDate(sDateField.getText(),"YYYY-mm-dd");
				eDate = DateUtil.fmtStrToDate(eDateField.getText(),"YYYY-mm-dd");
				kehuid = ((CustomItem)customBox.getSelectedItem()).getId();
				sellOrderNum = sellOrderNumField.getText();
			}catch (Exception e1) {
				e1.getMessage();
				LogUtil.error("销售单查询", e1);
			}
			paraMap.put("sDate", sDate);
			paraMap.put("eDate", eDate);
			paraMap.put("cid", kehuid);
			paraMap.put("sellOrderNum", sellOrderNumField.getText());
			List list = searchInfo(paraMap);
			updateTable(list, dftm);
		}
		private List searchInfo(Map map) {
			List list = MainController.getInstance().getSellOrderListByMap(map);
			return list;
		}
	}
}
