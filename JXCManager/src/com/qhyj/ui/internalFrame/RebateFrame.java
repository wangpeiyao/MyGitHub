package com.qhyj.ui.internalFrame;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import com.qhyj.controller.MainController;
import com.qhyj.domain.GoodsDo;
import com.qhyj.domain.RebateDo;
import com.qhyj.model.GoodsItem;
import com.qhyj.model.RabateTableModel;
import com.qhyj.util.JsonTools;


public class RebateFrame extends JInternalFrame {
	
	private JTable itemTable; 
	private JComboBox rebateGoods;
	private JPopupMenu popMenu;
	
	public RebateFrame() {
		super();
//		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		getContentPane().setLayout(new GridBagLayout());
		setTitle("返利规则管理");
		setBounds(50, 50, 450, 300);
		
		setupComponet(new JLabel("选择商品:"), 0, 0, 1, 0, false);
		rebateGoods = new JComboBox();
		rebateGoods.setPreferredSize(new Dimension(200, 21));
		// 处理客户信息的下拉选择框的选择事件
		rebateGoods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSpSelectAction();
			}
		});
		// 定位商品信息的下拉选择框
		setupComponet(rebateGoods, 1, 0, 2, 0, true);
		
		// 单击添加按钮在表格中添加新的一行
		JButton tjButton = new JButton("添加");
		tjButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 结束表格中没有编写的单元
				stopTableCellEditing();
				// 如果表格中还包含空行，就再添加新行
				for (int i = 0; i < itemTable.getRowCount(); i++) {
					if (itemTable.getValueAt(i, 1) == null)
						return;
				}
				RabateTableModel model = (RabateTableModel) itemTable.getModel();
				model.addRow(new Vector());
			}
		});
		setupComponet(tjButton, 3, 0, 1, 1, false);
		
		// 单击添加按钮在表格中添加新的一行
		JButton bcButton = new JButton("保存");
		bcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTableCellEditing();				// 结束表格中没有编写的单元
				clearEmptyRow();									// 清除空行
				saveInfo();
			}
		});
		setupComponet(bcButton, 4, 0, 1, 1, false);
				
		itemTable = new JTable();
		itemTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		itemTable.setRowHeight(20);
		itemTable.addMouseListener(new TableItemMenuEvent(this));  
		initTable();
		JScrollPane scrollPanel = new JScrollPane(itemTable);
		scrollPanel.setPreferredSize(new Dimension(400, 180));
		setupComponet(scrollPanel, 0, 2, 6, 1, true);
		
		initGoodsInfo();
		popMenuInit();
		setVisible(true);
	}
	
	private void saveInfo() {
		RabateTableModel dftm = (RabateTableModel) itemTable.getModel();
		List list = new ArrayList();
		for (int i = 0; i < itemTable.getRowCount(); i++) {
			RebateDo rebateDo = new RebateDo();
			String dw = (String) itemTable.getValueAt(i, 1);
			Map expMap = new HashMap();
			if("金额".equals(dw)) {
				expMap.put("bjtype", "amount");
			}else if("数量".equals(dw)){
				expMap.put("bjtype", "number");
			}else {
				JOptionPane.showMessageDialog(RebateFrame.this, "请将信息填写完整");
				return;
			}
			String bj = (String) itemTable.getValueAt(i, 2);
			if(">".equals(bj)) {
				expMap.put("bjsymbol", ">");
			}else if("=".equals(bj)){
				expMap.put("bjsymbol", "=");
			}else if("<".equals(bj)){
				expMap.put("bjsymbol", "<");
			}else {
				JOptionPane.showMessageDialog(RebateFrame.this, "请将信息填写完整");
				return;
			}
			String sl = (String) itemTable.getValueAt(i, 3);
			if(null!=sl&&!sl.isEmpty()) {
				try {
					expMap.put("bjnum", ""+Integer.valueOf(sl));
				}catch(Exception e) {
					JOptionPane.showMessageDialog(RebateFrame.this, "数量填写有误");
					return;
				}
			}else {
				JOptionPane.showMessageDialog(RebateFrame.this, "请将信息填写完整");
			}
			String fldw = (String) itemTable.getValueAt(i, 4);
			if("金额".equals(fldw)) {
				expMap.put("fltype", "amount");
			}else if("百分比".equals(fldw)){
				expMap.put("fltype", "rate");
			}else {
				JOptionPane.showMessageDialog(RebateFrame.this, "请将信息填写完整");
				return;
			}
			String flsl = (String) itemTable.getValueAt(i, 5);
			if(null!=flsl&&!flsl.isEmpty()) {
				try {
					expMap.put("flnum", ""+Double.valueOf(flsl));
				}catch(Exception e) {
					JOptionPane.showMessageDialog(RebateFrame.this, "金额填写有误");
					return;
				}
			}else {
				JOptionPane.showMessageDialog(RebateFrame.this, "请将信息填写完整");
			}
			rebateDo.setExpression(JsonTools.mapToJson(expMap));
			GoodsItem goodsItem = (GoodsItem) rebateGoods.getSelectedItem();
			rebateDo.setGid(goodsItem.getId());
			list.add(rebateDo);
		}
		try {
			MainController.getInstance().selfTransaction("addRebateList", list);
		}catch (Exception e) {
			JOptionPane.showMessageDialog(RebateFrame.this, "保存失败");
			return ;
		}
		JOptionPane.showMessageDialog(RebateFrame.this, "保存成功");
	}

	// 清除空行
	private synchronized void clearEmptyRow() {
		RabateTableModel dftm = (RabateTableModel) itemTable.getModel();
		for (int i = 0; i < dftm.getRowCount(); i++) {
			String info2 = (String) dftm.getValueAt(i, 1);
			if (null==info2||info2.length()<1) {
				dftm.removeRow(i);
			}
		}
	}
	private void initGoodsInfo() {// 初始化客户字段
		// List gysInfos = Dao.getKhInfos();
		List list = MainController.getInstance().getRebateGoodsList();
		for (int i = 0; i < list.size(); i++) {
			GoodsDo goodsDo = (GoodsDo) list.get(i);
			GoodsItem item = new GoodsItem();
			item.setId(goodsDo.getGid());
			item.setName(goodsDo.getGname());
			rebateGoods.addItem(item);
		}
	}


	// 停止表格单元的编辑
	private void stopTableCellEditing() {
		TableCellEditor cellEditor = itemTable.getCellEditor();
		if (cellEditor != null)
			cellEditor.stopCellEditing();
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
	// 隐藏列
	public void hiddenCell(JTable table, int column) {
		TableColumn tc = table.getTableHeader().getColumnModel().getColumn(column);
		tc.setMaxWidth(0);
		tc.setPreferredWidth(0);
		tc.setWidth(0);
		tc.setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(column).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(column).setMinWidth(0);
	}
	private void initTable() {
		String[] columnNames = { "","单位","比较","数量","返利单位", "数量"};
		itemTable.setModel(new RabateTableModel());
		((RabateTableModel) itemTable.getModel()).setColumnIdentifiers(columnNames);
		
		TableColumn column0 = itemTable.getColumnModel().getColumn(0);
		column0.setPreferredWidth(0);//单价
		
		JComboBox sp1 = new JComboBox();
		sp1.addItem("金额");
		sp1.addItem("数量");
		final DefaultCellEditor editor1 = new DefaultCellEditor(sp1);
		editor1.setClickCountToStart(2);
		TableColumn column = itemTable.getColumnModel().getColumn(1);
		column.setCellEditor(editor1);
		column.setPreferredWidth(80);
		
		JComboBox sp2 = new JComboBox();
		sp2.addItem(">");
		sp2.addItem("=");
		sp2.addItem("<");
		final DefaultCellEditor editor2= new DefaultCellEditor(sp2);
		TableColumn column1 = itemTable.getColumnModel().getColumn(2);
		column1.setCellEditor(editor2);
		column1.setPreferredWidth(80);
		
		TableColumn column2 = itemTable.getColumnModel().getColumn(3);
		column2.setPreferredWidth(80);//单价
		

		JComboBox sp3 = new JComboBox();
		sp3.addItem("金额");
		sp3.addItem("百分比");
		final DefaultCellEditor editor3= new DefaultCellEditor(sp3);
		TableColumn column3 = itemTable.getColumnModel().getColumn(4);
		column3.setCellEditor(editor3);
		column3.setPreferredWidth(80);//总价
		
		hiddenCell(itemTable,0);//把第一列隐藏
		
	}
	// 初始化商品下拉选择框
		public void initComboBox() {
//			List khInfo = Dao.getSpInfos();
			List goodsList = MainController.getInstance().getAllGoodsList();
			List<GoodsItem> items = new ArrayList<GoodsItem>();
			rebateGoods.removeAllItems();
			for (int i=0;i<goodsList.size();i++) {
				GoodsDo goodsDo = (GoodsDo) goodsList.get(i);
				GoodsItem item = new GoodsItem();
				item.setId(goodsDo.getGid());
				item.setName(goodsDo.getGname());
				if (items.contains(item))
					continue;
				items.add(item);
				rebateGoods.addItem(item);
			}
			doSpSelectAction();
		}
	
	    // 处理商品选择事件
		private void doSpSelectAction() {
			GoodsItem selectedItem;
			if (!(rebateGoods.getSelectedItem() instanceof GoodsItem)) {
				return;
			}
			RabateTableModel dftm = (RabateTableModel) itemTable.getModel();
			int num = dftm.getRowCount();
			for (int i = 0; i <num; i++) {
				dftm.removeRow(0);
			}
			selectedItem = (GoodsItem) rebateGoods.getSelectedItem();
			List<RebateDo> rebateDoList = MainController.getInstance().getRebateDoByGId(selectedItem.getId());
			if(null!=rebateDoList) {
				RabateTableModel model = (RabateTableModel) itemTable.getModel();
				for(int i=0;i<rebateDoList.size();i++) {
					RebateDo rebateDo = rebateDoList.get(i);
					Map rebateMap = JsonTools.parseJSON2Map(rebateDo.getExpression());
					
					List list = new ArrayList();
					
					Vector rowData = new Vector();
					
					rowData.add(rebateDo.getRid());
					model.setKey(rebateDo.getRid());
					String bjtype=(String) rebateMap.get("bjtype");
					if("amount".equals(bjtype)) {
						rowData.add("金额");
					}else if("number".equals(bjtype)) {
						rowData.add("数量");
					}
					String bjsymbol=(String) rebateMap.get("bjsymbol");
					if(">".equals(bjsymbol)) {
						rowData.add(">");
					}else if("=".equals(bjsymbol)) {
						rowData.add("=");
					}else if("<".equals(bjsymbol)) {
						rowData.add("<");
					}
					
					String bjnum=(String) rebateMap.get("bjnum");
					rowData.add(bjnum);
					
					String fltype=(String) rebateMap.get("fltype");
					if("amount".equals(fltype)) {
						rowData.add("金额");
					}else if("rate".equals(fltype)) {
						rowData.add("百分比");
					}
					
					String flnum=(String) rebateMap.get("flnum");
					rowData.add(flnum);
					
					
					
					model.addRow(rowData);
				}
			}
		}
		
		public JTable getItemTable() {
			return itemTable;
		}
		
		public JPopupMenu getPopMenu() {
			return popMenu;
		}

		//右键点击分类导航树的菜单  
	    private void popMenuInit() {  
	        popMenu = new JPopupMenu();  
	        JMenuItem addItem = new JMenuItem("删除");  
	        addItem.addActionListener(new TreeAddViewMenuEvent(this));  
	        popMenu.add(addItem);  
	    }  
	    class TreeAddViewMenuEvent implements ActionListener {  
	    	  
	        private RebateFrame adaptee;  
	  
	        public TreeAddViewMenuEvent(RebateFrame adaptee) {  
	            this.adaptee = adaptee;  
	        }  
	  
	        public void actionPerformed(ActionEvent e) {  
//	        	RabateTableModel model = (RabateTableModel) e.getSource();
//	        	if(null!=model.getKey()) {
//	        		
//	        	}
				int[] selectedRows = this.adaptee.getItemTable().getSelectedRows();// 获得选中行的索引
				for (int i=0;i<selectedRows.length;i++){ // 存在选中行
					RabateTableModel model = (RabateTableModel) this.adaptee.getItemTable().getModel();
					Integer rid = (Integer) this.adaptee.getItemTable().getValueAt(selectedRows[i]-i, 0);
					if(null!=rid) {
						MainController.getInstance().deleRebateDoByRid(rid);
					}
					model.removeRow(selectedRows[i]-i);
				}
	        }  
	    }  
		/** 
		 * 菜单点击右键的事件处理 
		 */  
		class TableItemMenuEvent implements MouseListener {
	
			private RebateFrame adaptee;
	
			public TableItemMenuEvent(RebateFrame rebateFrame) {
				this.adaptee = rebateFrame;
			}
	
			public void mouseClicked(MouseEvent e) {
			}
	
			public void mousePressed(MouseEvent e) {
//				TreePath path = adaptee.getItemTable().getde.getPathForLocation(e.getX(), e.getY()); // 关键是这个方法的使用
//				if (path == null) {
//					return;
//				}
//				adaptee.getTree().setSelectionPath(path);
				if (e.getButton() == 3) {
					adaptee.getPopMenu().show(adaptee.getItemTable(), e.getX(), e.getY());
				}
			}
	
			public void mouseReleased(MouseEvent e) {
			}
	
			public void mouseEntered(MouseEvent e) {
			}
	
			public void mouseExited(MouseEvent e) {
			}
	
		}
}
