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
import com.qhyj.domain.BuyOrderDo;
import com.qhyj.domain.CustomDo;
import com.qhyj.domain.GoodsDo;
import com.qhyj.domain.SellOrderDo;
import com.qhyj.model.GoodsItem;
import com.qhyj.util.DateUtil;
import com.qhyj.util.LogUtil;
public class QryBuyOrderFrame extends JInternalFrame {
	private JTable table;
	private JTextField sDateField;
	private JTextField eDateField;
	private JTextField buyOrderNumField;
	private JComboBox goodBox;
	public QryBuyOrderFrame() {
		super();
		setIconifiable(true);
		setClosable(true);
		setTitle("��������ѯ");
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

		setupComponet(new JLabel(" �������ڣ�"), 0, 0, 1, 1, false);
		sDateField = new JTextField(DateUtil.getToDayStr());
		setupComponet(sDateField, 1, 0, 1, 30, true);
		setupComponet(new JLabel(" �� "), 2, 0, 1, 1, false);
		eDateField = new JTextField(DateUtil.getToDayStr());
		setupComponet(eDateField,3 , 0, 1, 30, false);
		
		setupComponet(new JLabel(" ��Ʒ��"), 4, 0, 1, 1, false);
		goodBox = new JComboBox(); // �ͻ�
		initGoodBox();
		goodBox.setPreferredSize(new Dimension(120, 21));
		goodBox.setMaximumSize(new Dimension(120,21));
		goodBox.setMinimumSize(new Dimension(120,21));
		setupComponet(goodBox,5, 0, 1, 1, false);
		
		setupComponet(new JLabel(" �������ţ�"), 6, 0, 1, 1, false);
		buyOrderNumField = new JTextField();
		setupComponet(buyOrderNumField,7 , 0, 1, 120, false);
		


		final JButton queryButton = new JButton();
		queryButton.addActionListener(new QueryAction((DefaultTableModel) table.getModel()));
		setupComponet(queryButton, 8, 0, 1, 1, false);
		queryButton.setText("��ѯ");

		final JButton exportButton = new JButton("����");
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				String[] keys = new String[]{"���","��������","��Ʒ","����", "����","���","��ע","����ʱ��","������ʱ��"};
				String[] files = new String[] {"id","buynum","gname","count","amount","sumamount","memo","createtime","lastupdatetime"};
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
				new DownLoad(getContentPane(),dataList,files,keys,"��������Ϣ"+ DateUtil.fmtDateToYyyyMMDD(DateUtil.getToDay())+".xls");
			}
		});
		
		setupComponet(exportButton, 9, 0, 1, 1, false);
//		pack();
	}
	private void initTableModel(JTable table) {
		table.setEnabled(false);
		table.setSize(new Dimension(850, 400));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		String[] tableHeads = new String[]{"���","��������", "��Ʒ","����", "����","���","��ע","����ʱ��","������ʱ��"};
		((DefaultTableModel) table.getModel()).setColumnIdentifiers(tableHeads);
		//�����п�
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(30);
		table.getColumnModel().getColumn(4).setPreferredWidth(20);
		table.getColumnModel().getColumn(5).setPreferredWidth(40);
		table.getColumnModel().getColumn(6).setPreferredWidth(150);
		table.getColumnModel().getColumn(7).setPreferredWidth(100);
		table.getColumnModel().getColumn(8).setPreferredWidth(100);
	}
	private void initGoodBox() {// ��ʼ����Ʒ�ֶ�
		List list = MainController.getInstance().getAllGoodsList();
		GoodsItem allitem = new GoodsItem();
		allitem.setId(0);
		allitem.setName("ȫ��");
		goodBox.addItem(allitem);
		if(null==list) {
			return ;
		}
		for (int i=0;i<list.size();i++) {                                                                                                                                                               
			GoodsDo goodsDo=(GoodsDo) list.get(i); 
			GoodsItem item = new GoodsItem();
			item.setId(goodsDo.getGid());
			item.setName(goodsDo.getGname());
			goodBox.addItem(item);
		}
	}
	private void updateTable(List list,final DefaultTableModel dftm) {
		int num = dftm.getRowCount();
		for (int i = 0; i < num; i++) {
			dftm.removeRow(0);
		}
		BuyOrderDo buyOrderDo;
		for(int i=0;null!=list&&i<list.size();i++) {
			buyOrderDo = (BuyOrderDo) list.get(i);
			Vector rowData = new Vector();
			rowData.add((i+1));
			rowData.add(buyOrderDo.getBuyNum());
			GoodsDo goodsDo = MainController.getInstance().getGoodsById(buyOrderDo.getGid());
			rowData.add(goodsDo.getGname());
			rowData.add(buyOrderDo.getCount());
			rowData.add(buyOrderDo.getAmount());
            rowData.add(buyOrderDo.getSumAmount());
            rowData.add(buyOrderDo.getMemo());
            rowData.add(DateUtil.formatLongDate(buyOrderDo.getCreatetime()));
            rowData.add(DateUtil.formatLongDate(buyOrderDo.getLastupdateTime()));
			dftm.addRow(rowData);
		}
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
			String sellOrderNum= null;
			Integer gid = null;
			try {
				sDate = DateUtil.fmtStrToDate(eDateField.getText(),"yyyy-MM-dd");
				eDate = DateUtil.fmtStrToDate(eDateField.getText(),"yyyy-MM-dd");
				sellOrderNum = buyOrderNumField.getText();
				gid = ((GoodsItem)goodBox.getSelectedItem()).getId();
			}catch (Exception e1) {
				e1.getMessage();
				LogUtil.error("��������ѯ", e1);
			}
			paraMap.put("sDate", sDate);
			paraMap.put("eDate", eDate);
			paraMap.put("gid", gid);
			paraMap.put("buyOrderNum", buyOrderNumField.getText());
			List list = searchInfo(paraMap);
			updateTable(list, dftm);
		}
		private List searchInfo(Map map) {
			List list = MainController.getInstance().getBuyOrderListByMap(map);
			return list;
		}
	}
}
