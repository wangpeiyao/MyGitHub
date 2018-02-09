package com.qhyj.ui.internalFrame;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import com.qhyj.controller.MainController;
import com.qhyj.domain.BuyOrderDo;
import com.qhyj.domain.GoodsDo;
import com.qhyj.domain.UserDo;
import com.qhyj.model.GoodsCellEditor;
import com.qhyj.model.GoodsItem;
import com.qhyj.ui.login.Login;
import com.qhyj.util.DateUtil;
import com.qhyj.util.StringUtil;
public class BuyOrderFrame extends JInternalFrame {
	private final JTable table;
	private UserDo user = Login.getUser(); // 登录用户信息
	private final JTextField jhsj = new JTextField(); // 进货时间
	private final JTextField piaoHao = new JTextField(); // 票号
	private final JTextField pzs = new JTextField("0"); // 品种数量
	private final JTextArea memo = new JTextArea(2,10); // 品种数量
	private final JTextField hpzs = new JTextField("0"); // 货品总数
	private final JTextField hjje = new JTextField("0"); // 合计金额
//	private final JTextField czy = new JTextField(user.getName());// 操作员
	private Date jhsjDate;
	private GoodsTextField sp;
	private JPopupMenu popMenu;
	public BuyOrderFrame() {
		super();
//		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		getContentPane().setLayout(new GridBagLayout());
		setTitle("进货单");
		setBounds(50, 50, 700, 400);

		setupComponet(new JLabel("进货单号："), 0, 0, 1, 0, false);
//		piaoHao.setFocusable(false);
		setupComponet(piaoHao, 1, 0, 1, 140, true);
		piaoHao.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
			}
			@Override
			public void focusLost(FocusEvent e) {
				initBuyOrderInfo();
			}
		});



		setupComponet(new JLabel("进货日期："), 4, 0, 1, 0, false);
//		jhsj.setFocusable(false);
		setupComponet(jhsj, 5, 0, 1, 1, true);
		
		setupComponet(new JLabel("备注："), 0, 1, 1, 0, false);
		memo.setFont(getFont());
//		memo.setBorder(new Border());
		memo.setMargin(getInsets());
		memo.setSize(new Dimension(380, 200));
//		memo.setWrapStyleWord(true);  
		memo.setLineWrap(true);// 如果内容过长。
		setupComponet(new JScrollPane(memo), 1, 1, 5, 1, true);

//
//		sp = new JComboBox();
//		sp.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				GoodsTableInfo info = (GoodsTableInfo) sp.getSelectedItem();
//				// 如果选择有效就更新表格
//				if (info != null && info.getGid() != null) {
//					updateTable();
//				}
//			}
//		});
		sp = new GoodsTextField();
		sp.setAfterSetText(sp.new afterSetText() {
			public void actionPerformed() {
				if(null!=sp.getKey()){
					updateTable();
				}
			}
		});

		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(20);
		table.addMouseListener(new TableItemMenuEvent(this));
		initTable();
		// 添加事件完成品种数量、货品总数、合计金额的计算
		table.addContainerListener(new computeInfo());
		JScrollPane scrollPanel = new JScrollPane(table);
		scrollPanel.setPreferredSize(new Dimension(500, 200));
		setupComponet(scrollPanel, 0, 2, 6, 1, true);

		setupComponet(new JLabel("品种数量："), 0, 3, 1, 0, false);
		pzs.setFocusable(false);
		setupComponet(pzs, 1, 3, 1, 1, true);

		setupComponet(new JLabel("货品总数："), 2, 3, 1, 0, false);
		hpzs.setFocusable(false);
		setupComponet(hpzs, 3, 3, 1, 1, true);

		setupComponet(new JLabel("合计金额："), 4, 3, 1, 0, false);
		hjje.setFocusable(false);
		setupComponet(hjje, 5, 3, 1, 1, true);

		// 单击添加按钮在表格中添加新的一行
		JButton tjButton = new JButton("添加");
		tjButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 初始化票号
//				initPiaoHao();
				// 结束表格中没有编写的单元
				stopTableCellEditing();
				// 如果表格中还包含空行，就再添加新行
				for (int i = 0; i < table.getRowCount(); i++) {
					if (table.getValueAt(i, 1) == null)
						return;
				}
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Vector());
				GoodsCellEditor cellEditor = (GoodsCellEditor) table.getCellEditor(model.getRowCount(), 1);
				GoodsTextField goodsTextField = (GoodsTextField) cellEditor.getComponent();
				GoodsItem item = new GoodsItem();
                item.setId(null);
                item.setName("");
				goodsTextField.setItem(item);
			}
		});
		setupComponet(tjButton, 3, 4, 1, 1, false);
		
		// 单击添加按钮在表格中添加新的一行
		JButton delButton = new JButton("删除");
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 结束表格中没有编写的单元
				stopTableCellEditing();
				if(!piaoHao.getText().isEmpty()) {
					int op = JOptionPane.showConfirmDialog(BuyOrderFrame.this,
							"确认要删除该销售单？");
					if (op == JOptionPane.OK_OPTION) {
						try {
							   MainController.getInstance().delBuyOrderByBuyNum(piaoHao.getText().trim());
							}catch (Exception e1) {
								e1.printStackTrace();
								JOptionPane.showMessageDialog(BuyOrderFrame.this, "删除失败");
								return;
							}
							JOptionPane.showMessageDialog(BuyOrderFrame.this, "删除成功");
							 clearFrome();
					}
					
				}else {
					JOptionPane.showMessageDialog(BuyOrderFrame.this, "请输入销售单号");
				}
				
			}
		});
		setupComponet(delButton, 4, 4, 1, 1, false);
		

		// 单击销售按钮保存进货信息
		JButton sellButton = new JButton("保存");
		sellButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTableCellEditing();				// 结束表格中没有编写的单元
				clearEmptyRow();									// 清除空行
				String hpzsStr = hpzs.getText(); 					// 货品总数
				String pzsStr = pzs.getText(); 						// 品种数
				String jeStr = hjje.getText(); 						// 合计金额
//				String rkDate = jhsjDate.toLocaleString(); 			// 销售时间
				
				String id = piaoHao.getText();						// 票号
				String memoStr = memo.getText();						// 票号
				Date orderDate = DateUtil.fmtStrToDate(jhsj.getText(), "yyyy-MM-dd");
				if(null==orderDate) {
					JOptionPane.showMessageDialog(BuyOrderFrame.this, "日期格式不正确");
					return;
				}
				if (table.getRowCount() <= 0) {
					JOptionPane.showMessageDialog(BuyOrderFrame.this, "填加销售商品");
					return;
				}
				
				int rows = table.getRowCount();
				List list = new ArrayList();
				for (int i = 0; i < rows; i++) {
					GoodsItem soInfo = (GoodsItem) table.getValueAt(i, 1);
					BuyOrderDo buyOrderDo = new BuyOrderDo();
					buyOrderDo.setGid(soInfo.getId());
					Object slObj = table.getValueAt(i, 2);//数量
					Object djObj = table.getValueAt(i, 3);//单价
					Object zjObj = table.getValueAt(i, 4);//总价
					BigDecimal dj = new BigDecimal(null==djObj?"0":djObj.toString());
					BigDecimal sj = new BigDecimal(null==slObj?"0":slObj.toString());
					BigDecimal zj = dj.multiply(sj).setScale(2,BigDecimal.ROUND_HALF_UP);
					Integer sl = Integer.valueOf(slObj.toString());
					buyOrderDo.setAmount(Double.valueOf(djObj.toString()));
					buyOrderDo.setSumAmount(Double.valueOf(zj.toString()));
					buyOrderDo.setCount(sl);
					buyOrderDo.setMemo(memoStr);
					buyOrderDo.setOrderDate(orderDate);
					buyOrderDo.setBuyNum(piaoHao.getText());
					list.add(buyOrderDo);
				}
				try {
				   MainController.getInstance().selfTransaction("addBuyOrderList", list);
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(BuyOrderFrame.this, e1.getMessage());
					return;
				}
				JOptionPane.showMessageDialog(BuyOrderFrame.this, "录入完成");
				DefaultTableModel dftm = new DefaultTableModel();
				table.setModel(dftm);
				initTable();
				initPiaoHao();
				pzs.setText("0");
				hpzs.setText("0");
				hjje.setText("0");
				memo.setText("");
			}
		});
		setupComponet(sellButton, 5, 4, 1, 1, false);
		// 添加窗体监听器，完成初始化
		addInternalFrameListener(new initTasks());
		
	}
	// 初始化表格
	private void initTable() {
		String[] columnNames = {"","商品名称", "数量","单价","总价","产地", "规格", "备注"};
		((DefaultTableModel) table.getModel()).setColumnIdentifiers(columnNames);
		
		final GoodsCellEditor editor = new GoodsCellEditor(sp);
		editor.setClickCountToStart(2);
		
		TableColumn column = table.getColumnModel().getColumn(1);
		column.setCellEditor(editor);
		column.setPreferredWidth(150);
		
		TableColumn column1 = table.getColumnModel().getColumn(2);
		column1.setPreferredWidth(80);
		
		TableColumn column2 = table.getColumnModel().getColumn(3);
		column2.setPreferredWidth(80);//单价
		
		JTextField jt0 = new JTextField();
		jt0.setEditable(false);
		final DefaultCellEditor editor0 = new DefaultCellEditor(jt0);
		TableColumn column3 = table.getColumnModel().getColumn(4);
		column3.setCellEditor(editor0);
		column3.setPreferredWidth(80);//总价
		
		JTextField jt1 = new JTextField();
		jt1.setEditable(false);
		final DefaultCellEditor editor1 = new DefaultCellEditor(jt1);
		TableColumn column4 = table.getColumnModel().getColumn(5);
		column4.setCellEditor(editor1);
		column4.setPreferredWidth(100);
		
		JTextField jt2 = new JTextField();
		jt2.setEditable(false);
		final DefaultCellEditor editor2 = new DefaultCellEditor(jt2);
		TableColumn column5 = table.getColumnModel().getColumn(6);
		column5.setCellEditor(editor2);
		column5.setPreferredWidth(100);
		
		JTextField jt3 = new JTextField();
		jt3.setEditable(false);
		final DefaultCellEditor editor3 = new DefaultCellEditor(jt3);
		TableColumn column6 = table.getColumnModel().getColumn(7);
		column6.setCellEditor(editor3);
		column6.setPreferredWidth(100);
		
		hiddenCell(table,0);//把第一列隐藏
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
	// 初始化商品下拉选择框
	private void initSpBox() {
//		List list = new ArrayList();
//		List goodsList = MainController.getInstance().getAllGoodsList();
//		sp.removeAllItems();
//		sp.addItem(new GoodsTableInfo());
//		for (int i = 0; table != null && i < table.getRowCount(); i++) {
//			GoodsTableInfo tmpInfo = (GoodsTableInfo) table.getValueAt(i, 1);
//			if (tmpInfo != null && tmpInfo.getGid() != null)
//				list.add(tmpInfo.getGid());
//		}
//		for(int i=0;i<goodsList.size();i++) {
//			GoodsDo goodsDo= (GoodsDo) goodsList.get(i);
//			// 如果表格中以存在同样商品，商品下拉框中就不再包含该商品
//			if (list.contains(goodsDo.getGid())){
//				continue;
//			}
//			GoodsTableInfo info = new GoodsTableInfo();
//			info.setGid(goodsDo.getGid());
//			info.setGname(goodsDo.getGname());
//			info.setSpec(goodsDo.getSpec());
//			info.setPlace(goodsDo.getPlace());
//			sp.addItem(info);
//		}
		List goodsList = MainController.getInstance().getAllGoodsList();
		for(int i=0;null!=goodsList&&i<goodsList.size();i++) {
			GoodsDo goodsDo= (GoodsDo) goodsList.get(i);
			GoodsItem item = new GoodsItem();
			item.setId(goodsDo.getGid());
			item.setName(goodsDo.getGname());
			sp.addBoxItem(item);
			sp.putUniversityMap(goodsDo.getGid(), item);
		}
	}
	//根据销售单号初始化销售单信息
		private synchronized void initBuyOrderInfo() {
			if(!piaoHao.getText().isEmpty()) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				clearFrome();
				List<BuyOrderDo> list = MainController.getInstance().getBuyOrderListByBuyNum(piaoHao.getText().trim());
				if(null!=list&&!list.isEmpty()) {
					
					for(int i=0;i<list.size();i++) {
						BuyOrderDo buyOrderDoInfo = list.get(i);
						GoodsDo goodsDo = MainController.getInstance().getGoodsById(buyOrderDoInfo.getGid());
//						{"","商品名称", "数量","单价","总价","产地", "规格", "备注"};
						model.addRow(new Vector());
						GoodsItem item = new GoodsItem();
	                    item.setId(goodsDo.getGid());
	                    item.setName(goodsDo.getGname());
						table.setValueAt(item, i, 1);
						table.setValueAt(buyOrderDoInfo.getCount(), i, 2);
						table.setValueAt(StringUtil.getStrDouble(buyOrderDoInfo.getAmount()), i, 3);
						table.setValueAt(StringUtil.getStrDouble(buyOrderDoInfo.getSumAmount()), i, 4);
						table.setValueAt(StringUtil.getStrNotNull(goodsDo.getPlace()), i, 5);
						table.setValueAt(StringUtil.getStrNotNull(goodsDo.getSpec()) + "", i, 6);
						table.setValueAt(StringUtil.getStrNotNull(goodsDo.getMemo()) + "", i, 7);
						table.editCellAt(i, 8);
						
						if(i==0) {
							memo.setText(buyOrderDoInfo.getMemo());//备注
							jhsj.setText(DateUtil.fmtDateToYMD(buyOrderDoInfo.getOrderDate()));//日期
						}
					}
					calcAmount();
				}
			}
		}
	private void clearFrome() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int num = model.getRowCount();
		for (int i = 0; i < num; i++) {
			model.removeRow(0);
		}
		memo.setText("");//备注
		jhsj.setText(DateUtil.getToDayStr());//日期
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

	private void calcAmount() {
		// 清除空行
		clearEmptyRow();
		// 计算代码
		int rows = table.getRowCount();
		int count = 0;
		BigDecimal money = new BigDecimal(0);
		// 计算品种数量
		GoodsItem column = null;
		if (rows > 0)
			column = (GoodsItem) table.getValueAt(rows - 1, 1);
		if (rows > 0 && (column == null || null==column.getId()))
			rows--;
		// 计算货品总数和金额
		// String[] columnNames = {"","商品名称", "数量","单价","总价","产地", "规格", "备注"};
		for (int i = 0; i < rows; i++) {
			Object column7 = table.getValueAt(i, 2);// 数量
			Object column6 = table.getValueAt(i, 3);// 单价
			count = count + (null == column7 ? 0 : Integer.valueOf(column7.toString()));
			BigDecimal dj = new BigDecimal(null == column6 ? "0" : column6.toString());
			BigDecimal sj = new BigDecimal(null == column7 ? "0" : column7.toString());
			BigDecimal zj = dj.multiply(sj).setScale(2, BigDecimal.ROUND_HALF_UP);
			table.setValueAt(zj, i, 4);// 设置单个商品总价
			money = money.add(zj);
		}
		pzs.setText(rows + "");
		hpzs.setText(count + "");
		hjje.setText(money + "");
		// /////////////////////////////////////////////////////////////////
	}
	// 在事件中计算品种数量、货品总数、合计金额
	private final class computeInfo implements ContainerListener {
		public void componentRemoved(ContainerEvent e) {
			calcAmount();
		}
		public void componentAdded(ContainerEvent e) {
		}
	}
	// 窗体的初始化任务
	private final class initTasks extends InternalFrameAdapter {
		public void internalFrameActivated(InternalFrameEvent e) {
			super.internalFrameActivated(e);
			initTimeField();
			initPiaoHao();	
			initSpBox();
			popMenuInit();
		}
		private void initTimeField() {// 启动进货时间线程
			jhsjDate = DateUtil.getToDay();
			jhsj.setText(DateUtil.fmtDateToYMD(DateUtil.getToDay()));
		}
	}
	private void initPiaoHao() {
		Date date = DateUtil.fmtStrToDate(jhsj.getText());
		String maxId = MainController.getInstance().getBuyMainMaxId(date);
		piaoHao.setText(maxId);
	}
	// 根据商品下拉框的选择，更新表格当前行的内容
	private synchronized void updateTable() {
//		GoodsTableInfo spinfo = (GoodsTableInfo) sp.getSelectedItem();
		GoodsDo goodsDo = MainController.getInstance().getGoodsById(sp.getKey());
		int row = table.getSelectedRow();
	
		if (row >= 0 && goodsDo != null) {
			table.setValueAt(1, row, 2);
			table.setValueAt(0, row, 3);
			table.setValueAt(0, row, 4);
			table.setValueAt(StringUtil.getStrNotNull(goodsDo.getPlace()), row, 5);
			table.setValueAt(StringUtil.getStrNotNull(goodsDo.getSpec()) + "", row, 6);
			table.setValueAt(StringUtil.getStrNotNull(goodsDo.getMemo()) + "", row, 7);
			table.editCellAt(row, 8);
		}
	}
	// 清除空行
	private synchronized void clearEmptyRow() {
//		DefaultTableModel dftm = (DefaultTableModel) table.getModel();
//		for (int i = 0; i < table.getRowCount(); i++) {
//			GoodsTableInfo info2 = (GoodsTableInfo) table.getValueAt(i, 1);
//			if (info2 == null || info2.getGid() == null
//					|| null==info2.getGid()) {
//				dftm.removeRow(i);
//			}
//		}
		DefaultTableModel dftm = (DefaultTableModel) table.getModel();

		for (int i = 0; i < table.getRowCount(); i++) {
			GoodsItem item = (GoodsItem) table.getValueAt(i, 1);
			if (null == item || item.getId() == null) {
				dftm.removeRow(i);
			}
		}
	}
	// 停止表格单元的编辑
	private void stopTableCellEditing() {
		TableCellEditor cellEditor = table.getCellEditor();
		if (cellEditor != null)
			cellEditor.stopCellEditing();
	}
	
	public JTable getTable() {
		return table;
	}
	
	public JPopupMenu getPopMenu() {
		return popMenu;
	}
	public void setPopMenu(JPopupMenu popMenu) {
		this.popMenu = popMenu;
	}
	//右键点击分类导航树的菜单  
    private void popMenuInit() {  
        popMenu = new JPopupMenu();  
        JMenuItem addItem = new JMenuItem("删除");  
        addItem.addActionListener(new TreeAddViewMenuEvent(this));  
        popMenu.add(addItem);  
    }  
    class TreeAddViewMenuEvent implements ActionListener {  
    	  
        private BuyOrderFrame adaptee;  
  
        public TreeAddViewMenuEvent(BuyOrderFrame adaptee) {  
            this.adaptee = adaptee;  
        }  
  
        public void actionPerformed(ActionEvent e) {  
			int[] selectedRows = this.adaptee.getTable().getSelectedRows();// 获得选中行的索引
			for (int i=0;i<selectedRows.length;i++){ // 存在选中行
				DefaultTableModel model = (DefaultTableModel) this.adaptee.getTable().getModel();
				Integer rid = (Integer) this.adaptee.getTable().getValueAt(selectedRows[i]-i, 0);
				model.removeRow(selectedRows[i]-i);
			}
        }  
    }  
	/** 
	 * 菜单点击右键的事件处理 
	 */  
	class TableItemMenuEvent implements MouseListener {

		private BuyOrderFrame adaptee;

		public TableItemMenuEvent(BuyOrderFrame rebateFrame) {
			this.adaptee = rebateFrame;
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			if (e.getButton() == 3) {
				adaptee.getPopMenu().show(adaptee.getTable(), e.getX(), e.getY());
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
