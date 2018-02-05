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
import javax.swing.JComboBox;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import com.qhyj.controller.MainController;
import com.qhyj.domain.CustomDo;
import com.qhyj.domain.GoodsDo;
import com.qhyj.domain.SellOrderDo;
import com.qhyj.domain.UserDo;
import com.qhyj.model.CommonBoxItem;
import com.qhyj.model.CustomItem;
import com.qhyj.model.GoodsItem;
import com.qhyj.model.GoodsCellEditor;
import com.qhyj.ui.login.Login;
import com.qhyj.ui.panel.DelUserPanel;
import com.qhyj.util.DateUtil;
import com.qhyj.util.StringUtil;

public class SellOrderFrame extends JInternalFrame {
	private volatile boolean isaddkehuNameComplete = true;	
	private final JTable table;
	private UserDo user = Login.getUser(); // ��¼�û���Ϣ
	private final JTextField jhsj = new JTextField(); // ����ʱ��
	private final CustomTextField customTextField = new CustomTextField();
	private final JTextField piaoHao = new JTextField(); // Ʊ��
	private final JTextField pzs = new JTextField("0"); // Ʒ������
	private final JTextArea memo = new JTextArea(2,10); // Ʒ������
	private final JTextField hpzs = new JTextField("0"); // ��Ʒ����
	private final JTextField hjje = new JTextField("0"); // �ϼƽ��
//	private final JTextField czy = new JTextField(user.getName());// ����Ա
	private Date jhsjDate;
	private GoodsTextField sp;
	private JComboBox payStateBox;
	private JPopupMenu popMenu;
	public SellOrderFrame() {
		super();
//		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		getContentPane().setLayout(new GridBagLayout());
		setTitle("���۵�");
		setBounds(50, 50, 700, 400);

		setupComponet(new JLabel("���۵��ţ�"), 0, 0, 1, 0, false);
		setupComponet(piaoHao, 1, 0, 1, 80, true);
		piaoHao.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
			}
			@Override
			public void focusLost(FocusEvent e) {
				initSellOrderInfo();
			}
		});
		

		setupComponet(new JLabel("�ͻ���"), 2, 0, 1, 0, false);
		setupComponet(customTextField, 3, 0, 1, 80, true);


		setupComponet(new JLabel("�������ڣ�"), 4, 0, 1, 0, false);
		setupComponet(jhsj, 5, 0, 1, 1, true);
		
		setupComponet(new JLabel("����״̬��"), 6, 0, 1, 0, false);
		payStateBox = new JComboBox();
		payStateBox.addItem(new CommonBoxItem(1,"�Ѹ���"));
		payStateBox.addItem(new CommonBoxItem(2,"Ƿ��"));
		setupComponet(payStateBox, 7, 0, 1, 0, false);
		
		setupComponet(new JLabel("��ע��"), 0, 1, 1, 0, false);
		memo.setFont(getFont());
		memo.setMargin(getInsets());
		memo.setSize(new Dimension(380, 200));
		memo.setLineWrap(true);// ������ݹ�����
		setupComponet(new JScrollPane(memo), 1, 1, 7, 1, true);


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
		// ����¼����Ʒ����������Ʒ�������ϼƽ��ļ���
		table.addContainerListener(new computeInfo());
		JScrollPane scrollPanel = new JScrollPane(table);
		scrollPanel.setPreferredSize(new Dimension(500, 200));
		setupComponet(scrollPanel, 0, 2, 8, 1, true);

		setupComponet(new JLabel("Ʒ��������"), 0, 3, 1, 0, false);
		pzs.setFocusable(false);
		setupComponet(pzs, 1, 3, 1, 1, true);

		setupComponet(new JLabel("��Ʒ������"), 2, 3, 1, 0, false);
		hpzs.setFocusable(false);
		setupComponet(hpzs, 3, 3, 2, 1, true);

		setupComponet(new JLabel("�ϼƽ�"), 4, 3, 1, 0, false);
		hjje.setFocusable(false);
		setupComponet(hjje, 6, 3, 2, 1, true);
		
		JButton tjButton = new JButton("���");
		tjButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��ʼ��Ʊ��
//				initPiaoHao();
				// ���������û�б�д�ĵ�Ԫ
				stopTableCellEditing();
				// �������л��������У������������
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
//				table.setValueAt(goodsTextField, model.getRowCount(), 1);
			}
		});
		setupComponet(tjButton, 5, 4, 1, 1, false);


		// ������Ӱ�ť�ڱ��������µ�һ��
		JButton delButton = new JButton("ɾ��");
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���������û�б�д�ĵ�Ԫ
				stopTableCellEditing();
				if(!piaoHao.getText().isEmpty()) {
					int op = JOptionPane.showConfirmDialog(SellOrderFrame.this,
							"ȷ��Ҫɾ�������۵���");
					if (op == JOptionPane.OK_OPTION) {
						try {
							   MainController.getInstance().delSellOrderBySellNum(piaoHao.getText().trim());
							}catch (Exception e1) {
								e1.printStackTrace();
								JOptionPane.showMessageDialog(SellOrderFrame.this, "ɾ��ʧ��");
								return;
							}
							JOptionPane.showMessageDialog(SellOrderFrame.this, "ɾ���ɹ�");
							 clearFrome();
					}
					
				}else {
					JOptionPane.showMessageDialog(SellOrderFrame.this, "���������۵���");
				}
				
			}
		});
		setupComponet(delButton, 6, 4, 1, 1, false);
		
		
		// �������۰�ť���������Ϣ
		JButton sellButton = new JButton("����");
		sellButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTableCellEditing();				// ���������û�б�д�ĵ�Ԫ
				clearEmptyRow();									// �������
				String hpzsStr = hpzs.getText(); 					// ��Ʒ����
				String pzsStr = pzs.getText(); 						// Ʒ����
				String jeStr = hjje.getText(); 						// �ϼƽ��
//				String rkDate = jhsjDate.toLocaleString(); 			// ����ʱ��
				
				String id = piaoHao.getText();						// Ʊ��
				String memoStr = memo.getText();						// Ʊ��
//				Integer kehuid = ((CustomItem)kehu.getSelectedItem()).getId();// ��Ӧ������
				Date orderDate = DateUtil.fmtStrToDate(jhsj.getText(), "yyyy-MM-dd");
				if(null==orderDate) {
					JOptionPane.showMessageDialog(SellOrderFrame.this, "���ڸ�ʽ����ȷ");
					return;
				}
				if (table.getRowCount() <= 0) {
					JOptionPane.showMessageDialog(SellOrderFrame.this, "���������Ʒ");
					return;
				}
				if(null==customTextField.getKey()) {
					JOptionPane.showMessageDialog(SellOrderFrame.this, "�ͻ���Ϣ������");
					return;
				}
				
				int rows = table.getRowCount();
				List list = new ArrayList();
				for (int i = 0; i < rows; i++) {
					GoodsItem item = (GoodsItem) table.getValueAt(i, 1);
					SellOrderDo sellOrderDo = new SellOrderDo();
					sellOrderDo.setCid(customTextField.getKey());
					sellOrderDo.setGid(item.getId());
					Object slObj = table.getValueAt(i, 2);//����
					Object djObj = table.getValueAt(i, 3);//����
					Object zjObj = table.getValueAt(i, 4);//�ܼ�
					BigDecimal dj = new BigDecimal(null==djObj?"0":djObj.toString());
					BigDecimal sj = new BigDecimal(null==slObj?"0":slObj.toString());
					BigDecimal zj = dj.multiply(sj).setScale(2,BigDecimal.ROUND_HALF_UP);
					Integer sl = Integer.valueOf(slObj.toString());
					sellOrderDo.setAmount(Double.valueOf(djObj.toString()));
					sellOrderDo.setSumAmount(Double.valueOf(zj.toString()));
					sellOrderDo.setCount(sl);
					sellOrderDo.setMemo(memoStr);
					sellOrderDo.setOrderDate(orderDate);
					sellOrderDo.setSellNum(piaoHao.getText());
					Integer payState = ((CommonBoxItem)payStateBox.getSelectedItem()).getKey();
					sellOrderDo.setPayState(payState);
					list.add(sellOrderDo);
				}
				try {
				    MainController.getInstance().selfTransaction("addSellOrderList", list);
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(SellOrderFrame.this, e1.getMessage());
					return;
				}
				JOptionPane.showMessageDialog(SellOrderFrame.this, "¼�����");
				DefaultTableModel dftm = new DefaultTableModel();
				table.setModel(dftm);
				initTable();
				initPiaoHao();
				clearFrome();
			}
		});
		setupComponet(sellButton, 7, 4, 1, 1, false);
		// ��Ӵ������������ɳ�ʼ��
		addInternalFrameListener(new initTasks());
		
	}
	// ��ʼ�����
	private void initTable() {
		String[] columnNames = {"","��Ʒ����", "����","����","�ܼ�","����", "���", "��ע"};
		((DefaultTableModel) table.getModel()).setColumnIdentifiers(columnNames);
		
		final GoodsCellEditor editor = new GoodsCellEditor(sp);
		editor.setClickCountToStart(2);
		TableColumn column = table.getColumnModel().getColumn(1);
		column.setCellEditor(editor);
		column.setPreferredWidth(150);
		
		TableColumn column1 = table.getColumnModel().getColumn(2);
		column1.setPreferredWidth(80);
		
		TableColumn column2 = table.getColumnModel().getColumn(3);
		column2.setPreferredWidth(80);//����
		
		JTextField jt0 = new JTextField();
		jt0.setEditable(false);
		final DefaultCellEditor editor0 = new DefaultCellEditor(jt0);
		TableColumn column3 = table.getColumnModel().getColumn(4);
		column3.setCellEditor(editor0);
		column3.setPreferredWidth(80);//�ܼ�
		
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
		
		hiddenCell(table,0);//�ѵ�һ������
	}

	// ������
	public void hiddenCell(JTable table, int column) {
		TableColumn tc = table.getTableHeader().getColumnModel().getColumn(column);
		tc.setMaxWidth(0);
		tc.setPreferredWidth(0);
		tc.setWidth(0);
		tc.setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(column).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(column).setMinWidth(0);
	}
	// ��ʼ����Ʒ����ѡ���
	private void initSpBox() {
		List goodsList = MainController.getInstance().getHavStockGoodsList();//��Ҫȡ������0����Ʊ
		for(int i=0;null!=goodsList&&i<goodsList.size();i++) {
			GoodsDo goodsDo= (GoodsDo) goodsList.get(i);
			GoodsItem item = new GoodsItem();
			item.setId(goodsDo.getGid());
			item.setName(goodsDo.getGname());
			sp.addBoxItem(item);
			sp.putUniversityMap(goodsDo.getGid(), item);
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
	// ���¼��м���Ʒ����������Ʒ�������ϼƽ��
	private final class computeInfo implements ContainerListener {
		public void componentRemoved(ContainerEvent e) {
			calcAmount();
		}
		public void componentAdded(ContainerEvent e) {
		}
	}

	private void clearFrome() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int num = model.getRowCount();
		for (int i = 0; i < num; i++) {
			model.removeRow(0);
		}
		memo.setText("");//��ע
		jhsj.setText(DateUtil.getToDayStr());//����
		payStateBox.setSelectedIndex(0);
		customTextField.clear();
	}
	//�������۵��ų�ʼ�����۵���Ϣ
	private synchronized void initSellOrderInfo() {
		if(!piaoHao.getText().isEmpty()) {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			clearFrome();
			List<SellOrderDo> list = MainController.getInstance().getSellOrderListBySellNum(piaoHao.getText().trim());
			if(null!=list&&!list.isEmpty()) {
				
				for(int i=0;i<list.size();i++) {
					SellOrderDo sellOrderDoInfo = list.get(i);
					GoodsDo goodsDo = MainController.getInstance().getGoodsById(sellOrderDoInfo.getGid());
					// {"","��Ʒ����", "����","����","�ܼ�","����", "���", "��ע"};
					
					model.addRow(new Vector());
					GoodsItem item = new GoodsItem();
                    item.setId(goodsDo.getGid());
                    item.setName(goodsDo.getGname());
					table.setValueAt(item, i, 1);
					table.setValueAt(sellOrderDoInfo.getCount(), i, 2);
					table.setValueAt(StringUtil.getStrDouble(sellOrderDoInfo.getAmount()), i, 3);
					table.setValueAt(StringUtil.getStrDouble(sellOrderDoInfo.getSumAmount()), i, 4);
					table.setValueAt(StringUtil.getStrNotNull(goodsDo.getPlace()), i, 5);
					table.setValueAt(StringUtil.getStrNotNull(goodsDo.getSpec()) + "", i, 6);
					table.setValueAt(StringUtil.getStrNotNull(goodsDo.getMemo()) + "", i, 7);
					table.editCellAt(i, 8);
					
					if(i==0) {
						memo.setText(sellOrderDoInfo.getMemo());//��ע
						jhsj.setText(DateUtil.fmtDateToYMD(sellOrderDoInfo.getOrderDate()));//����
						if(new Integer(2).equals(sellOrderDoInfo.getPayState())) {
							payStateBox.setSelectedIndex(1);
						}else {
							payStateBox.setSelectedIndex(0);
						}
						CustomDo customDo = MainController.getInstance().getCustomById(sellOrderDoInfo.getCid());
						customTextField.setText(customDo.getCname());
						customTextField.setKey(sellOrderDoInfo.getCid());
					}
				}
				calcAmount();
			}
		}
	}
	private void calcAmount() {
		// �������
		clearEmptyRow();
		// �������
		int rows = table.getRowCount();
		int count = 0;
		BigDecimal money = new BigDecimal(0);
		// ����Ʒ������
		GoodsItem column = null;
		if (rows > 0)
			column = (GoodsItem) table.getValueAt(rows - 1, 1);
		if (rows > 0 && (column == null || null==column.getId()))
			rows--;
		// �����Ʒ�����ͽ��
		// String[] columnNames = {"","��Ʒ����", "����","����","�ܼ�","����", "���", "��ע"};
		for (int i = 0; i < rows; i++) {
			Object column7 = table.getValueAt(i, 2);// ����
			Object column6 = table.getValueAt(i, 3);// ����
			count = count + (null == column7 ? 0 : Integer.valueOf(column7.toString()));
			BigDecimal dj = new BigDecimal(null == column6 ? "0" : column6.toString());
			BigDecimal sj = new BigDecimal(null == column7 ? "0" : column7.toString());
			BigDecimal zj = dj.multiply(sj).setScale(2, BigDecimal.ROUND_HALF_UP);
			table.setValueAt(zj, i, 4);// ���õ�����Ʒ�ܼ�
			money = money.add(zj);
		}
		pzs.setText(rows + "");
		hpzs.setText(count + "");
		hjje.setText(money + "");
	}
	// ����ĳ�ʼ������
	private final class initTasks extends InternalFrameAdapter {
		public void internalFrameActivated(InternalFrameEvent e) {
			super.internalFrameActivated(e);
			initTimeField();
			initKehuField();
			initPiaoHao();	
			initSpBox();
			popMenuInit();
		}
		private void initKehuField() {// ��ʼ���ͻ��ֶ�
//			List gysInfos = Dao.getKhInfos();
			List list = MainController.getInstance().getAllCustomList();
			for (int i=0;i<list.size();i++) {                                                                                                                                                               
				CustomDo customDo=(CustomDo) list.get(i); 
				CustomItem item = new CustomItem();
				item.setId(customDo.getCid());
				item.setName(customDo.getCname());
//				kehu.addItem(item);
				customTextField.addBoxItem(item);
				customTextField.putUniversityMap(customDo.getCid(), item);
			}
//			doKhSelectAction();
		}
		private void initTimeField() {// ��������ʱ���߳�
			jhsjDate = DateUtil.getToDay();
			jhsj.setText(DateUtil.fmtDateToYMD(DateUtil.getToDay()));
		}
	}
	private void initPiaoHao() {
		Date date = DateUtil.fmtStrToDate(jhsj.getText());
		String maxId = MainController.getInstance().getSellMainMaxId(date);
		piaoHao.setText(maxId);
	}
	// ������Ʒ�������ѡ�񣬸��±��ǰ�е�����
	private synchronized void updateTable() {
		GoodsDo goodsDo = MainController.getInstance().getGoodsById(sp.getKey());
		int row = table.getSelectedRow();

		if (row >= 0 && goodsDo != null) {
			table.setValueAt(1, row, 2);
			table.setValueAt(StringUtil.getStrDouble(goodsDo.getAmount()), row, 3);
			table.setValueAt(0, row, 4);
			table.setValueAt(StringUtil.getStrNotNull(goodsDo.getPlace()), row, 5);
			table.setValueAt(StringUtil.getStrNotNull(goodsDo.getSpec()) + "", row, 6);
			table.setValueAt(StringUtil.getStrNotNull(goodsDo.getMemo()) + "", row, 7);
			table.editCellAt(row, 8);
			calcAmount();
		}
	}
	// �������
	private synchronized void clearEmptyRow() {
		DefaultTableModel dftm = (DefaultTableModel) table.getModel();
		
		for (int i = 0; i < table.getRowCount(); i++) {
			GoodsItem item = (GoodsItem) table.getValueAt(i, 1);
			if ( null ==item || item.getId()==null) {
				dftm.removeRow(i);
			}
		}
	}
	// ֹͣ���Ԫ�ı༭
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
	//�Ҽ�������ർ�����Ĳ˵�  
    private void popMenuInit() {  
        popMenu = new JPopupMenu();  
        JMenuItem addItem = new JMenuItem("ɾ��");  
        addItem.addActionListener(new TreeAddViewMenuEvent(this));  
        popMenu.add(addItem);  
    }  
    class TreeAddViewMenuEvent implements ActionListener {  
    	  
        private SellOrderFrame adaptee;  
  
        public TreeAddViewMenuEvent(SellOrderFrame adaptee) {  
            this.adaptee = adaptee;  
        }  
  
        public void actionPerformed(ActionEvent e) {  
			int[] selectedRows = this.adaptee.getTable().getSelectedRows();// ���ѡ���е�����
			for (int i=0;i<selectedRows.length;i++){ // ����ѡ����
				DefaultTableModel model = (DefaultTableModel) this.adaptee.getTable().getModel();
				Integer rid = (Integer) this.adaptee.getTable().getValueAt(selectedRows[i]-i, 0);
				model.removeRow(selectedRows[i]-i);
			}
        }  
    }  
	/** 
	 * �˵�����Ҽ����¼����� 
	 */  
	class TableItemMenuEvent implements MouseListener {

		private SellOrderFrame adaptee;

		public TableItemMenuEvent(SellOrderFrame rebateFrame) {
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
