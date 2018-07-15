package com.qhyj.ui.internalFrame;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
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
import com.qhyj.domain.GoodsDo;
import com.qhyj.util.DateUtil;
public class QryGoodsFrame extends JInternalFrame {
	private JTable table;
	private JTextField conditionContent;
	private JComboBox conditionName;
	public QryGoodsFrame() {
		super();
		setIconifiable(true);
		setClosable(true);
		setTitle("��Ʒ�����Ϣ��ѯ");
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
		gridBagConstraints_6.gridwidth = 7;
		gridBagConstraints_6.gridheight = 1;
		gridBagConstraints_6.gridy = 1;
		gridBagConstraints_6.gridx = 0;
//		scrollPane.setSize(new Dimension(750, 400));
		getContentPane().add(scrollPane, gridBagConstraints_6);

		setupComponet(new JLabel(" ѡ���ѯ������"), 0, 0, 1, 1, false);
		conditionName = new JComboBox();
		conditionName.setModel(new DefaultComboBoxModel(new String[]{"��Ʒ����",	"�Ƿ���"}));
		setupComponet(conditionName, 1, 0, 1, 30, true);

		setupComponet(new JLabel("    ="), 2, 0, 1, 30, true);

		conditionContent = new JTextField();
		setupComponet(conditionContent, 3, 0, 1, 140, true);

		final JButton queryButton = new JButton();
		queryButton.addActionListener(new QueryAction((DefaultTableModel) table.getModel()));
		setupComponet(queryButton, 4, 0, 1, 1, false);
		queryButton.setText("ɸѡ");

		final JButton showAllButton = new JButton("��ʾȫ������");
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				conditionContent.setText("");
				List<GoodsDo> list = MainController.getInstance().getAllGoodsList();
				updateTable(list,(DefaultTableModel) table.getModel());
			}
		});
		setupComponet(showAllButton, 5, 0, 1, 1, false);
		
		final JButton exportButton = new JButton("��������");
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				conditionContent.setText("");
				String[] keys = new String[]{"���","��Ʒ����","��ǰ���", "����", "���","�Ƿ���","��ע","����ʱ��","������ʱ��"};
				String[] files = new String[] {"id","gname","lavenum","place","spec","isrebate","memo","createtime","lastupdatetime"};
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				int rowCount = tableModel.getRowCount();
				List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
				for(int i=0;i<rowCount;i++) {
					Map<String,Object> map = new HashMap<String,Object>() ;
					for(int j=0;j<files.length;j++) {
						map.put(files[j],tableModel.getValueAt(i,j));
					}
					dataList.add(map);
				}
				new DownLoad(getContentPane(),dataList,files,keys,"��Ʒ�����Ϣ"+ DateUtil.fmtDateToYyyyMMDD(DateUtil.getToDay())+".xls");
			}
		});
		
		setupComponet(exportButton, 6, 0, 1, 1, false);
//		pack();
	}

	private void initTableModel(JTable table) {
		table.setEnabled(false);
		table.setSize(new Dimension(850, 400));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		String[] tableHeads = new String[]{"���","��Ʒ����","��ǰ���", "����", "���","�Ƿ���","��ע","����ʱ��","������ʱ��"};
		((DefaultTableModel) table.getModel()).setColumnIdentifiers(tableHeads);
		//�����п�
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(20);
		table.getColumnModel().getColumn(5).setPreferredWidth(40);
		table.getColumnModel().getColumn(6).setPreferredWidth(150);
		table.getColumnModel().getColumn(7).setPreferredWidth(80);
		table.getColumnModel().getColumn(8).setPreferredWidth(80);
	}
	
	private void updateTable(List list,final DefaultTableModel dftm) {
		int num = dftm.getRowCount();
		for (int i = 0; i < num; i++) {
			dftm.removeRow(0);
		}
		GoodsDo goodsDo;
		for(int i=0;null!=list&&i<list.size();i++) {
			goodsDo = (GoodsDo) list.get(i);
			Vector rowData = new Vector();
			rowData.add((i+1));
			rowData.add(goodsDo.getGname());
			int currStock = MainController.getInstance().getCurrStockByGid(goodsDo.getGid());
			rowData.add(currStock);
			rowData.add(goodsDo.getPlace());
			rowData.add(goodsDo.getSpec());
			if(new Integer(1).equals(goodsDo.getIsRebate())) {
				rowData.add("��");
			}else if(new Integer(0).equals(goodsDo.getIsRebate())) {
				rowData.add("��");
			}
			rowData.add(goodsDo.getMemo());
			rowData.add(DateUtil.formatLongDate(goodsDo.getCreateTime()));
			rowData.add(DateUtil.formatLongDate(goodsDo.getLastUpdateTime()));
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
			conName = conditionName.getSelectedItem().toString().trim();
			content = conditionContent.getText().trim();
			List list = null;
			list = searchInfo(conName,content, list);
			updateTable(list, dftm);
		}
		private List searchInfo(String conName, String content, List list) {
			Map map = new HashMap();
			if (conName.equals("��Ʒ����")) {
				map.put("gname",content);
			}else if(conName.equals("�Ƿ���")){
				if("��".equals(content)) {
					map.put("isrebate",new Integer(1));
				}else {
					map.put("isrebate",new Integer(0));
				}
				
			}
			return list;
		}
	}
}
