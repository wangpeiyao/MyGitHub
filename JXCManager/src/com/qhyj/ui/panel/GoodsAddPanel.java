package com.qhyj.ui.panel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.qhyj.controller.MainController;
import com.qhyj.domain.GoodsDo;
public class GoodsAddPanel extends JPanel {
	private JComboBox gysQuanCheng;
	private JTextField guiGe;
	private JTextField  jiage;
	private JTextField quanCheng;
	private JTextField chanDi;
	private JTextField jianCheng;
	private JTextField beiZhu;
	private JRadioButton jrb1;
	private JRadioButton jrb2;
	private ButtonGroup group;
	private JButton resetButton;
	public GoodsAddPanel() {
		setLayout(new GridBagLayout());
		setBounds(10, 10, 550, 400);
		setupComponent(new JLabel("商品名称："), 0, 0, 1, 1, false);
		quanCheng = new JTextField();
		setupComponent(quanCheng, 1, 0, 3, 1, true);
		
		setupComponent(new JLabel("价格："), 0, 1, 1, 1, false);
		jiage = new JTextField();
		setupComponent(jiage, 1, 1, 3, 1, true);
		
	
		
		setupComponent(new JLabel("产地："), 0, 2, 1, 1, false);
		chanDi = new JTextField();
		setupComponent(chanDi, 1, 2, 3, 300, true);
		
		
		setupComponent(new JLabel("规格："), 0, 3, 1, 1, false);
		guiGe = new JTextField();
		setupComponent(guiGe, 1, 3, 3, 1, true);
		
		setupComponent(new JLabel("是否返利："), 0, 4, 1, 1, false);
		jrb1 = new JRadioButton("是",true);
		jrb2 = new JRadioButton("否");
		group = new ButtonGroup();
		group.add(jrb1);
        group.add(jrb2);
		setupComponent(jrb1, 1, 4, 1, 10, false);
		setupComponent(jrb2, 2, 4, 1, 10, false);
		
		setupComponent(new JLabel("备注："), 0, 5, 1, 1, false);
		beiZhu = new JTextField();
		setupComponent(beiZhu, 1, 5, 3, 1, true);
		
		final JButton tjButton = new JButton();
		tjButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (chanDi.getText().equals("")
						|| guiGe.getText().equals("")
						|| quanCheng.getText().equals("")
						|| jiage.getText().equals("")) {
					JOptionPane.showMessageDialog(GoodsAddPanel.this,
							"请完成未填写的信息。", "商品添加", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String sql = "SELECT * FROM T_GOODS WHERE GNAME='"+ quanCheng.getText().trim() + "'";
				ResultSet haveGood = MainController.getInstance().executeSql(sql);
				try {
					if (haveGood.next()) {
						System.out.println("error");
						JOptionPane.showMessageDialog(
								GoodsAddPanel.this, "商品信息添加失败，存在同名商品",
								"商品添加", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				} catch (Exception er) {
					er.printStackTrace();
				}
				GoodsDo goodsDo = new GoodsDo();
				goodsDo.setGname(quanCheng.getText().trim());
				goodsDo.setSpec(guiGe.getText().trim());
				if(jrb1.isSelected()) {
					goodsDo.setIsRebate(new Integer(1));
				}else if(jrb2.isSelected()) {
					goodsDo.setIsRebate(new Integer(0));
				}
				try {
				goodsDo.setAmount(Double.valueOf(jiage.getText().trim()));
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(
							GoodsAddPanel.this, "金额格式有误","商品添加", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				goodsDo.setPlace(chanDi.getText().trim());
				goodsDo.setMemo(beiZhu.getText().trim());
				MainController.getInstance().addGoods(goodsDo);
				JOptionPane.showMessageDialog(GoodsAddPanel.this,
						"商品信息已经成功添加", "商品添加", JOptionPane.INFORMATION_MESSAGE);
				resetButton.doClick();
			}
		});
		tjButton.setText("添加");
		setupComponent(tjButton, 1, 8, 1, 1, false);
		final GridBagConstraints gridBagConstraints_20 = new GridBagConstraints();
		gridBagConstraints_20.weighty = 1.0;
		gridBagConstraints_20.insets = new Insets(0, 65, 0, 15);
		gridBagConstraints_20.gridy = 8;
		gridBagConstraints_20.gridx = 1;
		// 重添按钮的事件监听类
		resetButton = new JButton();
		setupComponent(tjButton, 3, 8, 1, 1, false);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				chanDi.setText("");
				guiGe.setText("");
				beiZhu.setText("");
				quanCheng.setText("");
				jiage.setText("");
			}
		});
		resetButton.setText("重添");
	}
	// 设置组件位置并添加到容器中
	private void setupComponent(JComponent component, int gridx, int gridy,
			int gridwidth, int ipadx, boolean fill) {
		final GridBagConstraints gridBagConstrains = new GridBagConstraints();
		gridBagConstrains.gridx = gridx;
		gridBagConstrains.gridy = gridy;
		gridBagConstrains.insets = new Insets(5, 1, 3, 1);
		if (gridwidth > 1)
			gridBagConstrains.gridwidth = gridwidth;
		if (ipadx > 0)
			gridBagConstrains.ipadx = ipadx;
		if (fill)
			gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
		add(component, gridBagConstrains);
	}
//	// 初始化供应商下拉选择框
//	public void initGysBox() {
//		List gysInfo = Dao.getGysInfos();
//		List<Item> items = new ArrayList<Item>();
//		gysQuanCheng.removeAllItems();
//		for (Iterator iter = gysInfo.iterator(); iter.hasNext();) {
//			List element = (List) iter.next();
//			Item item = new Item();
//			item.setId(element.get(0).toString().trim());
//			item.setName(element.get(1).toString().trim());
//			if (items.contains(item))
//				continue;
//			items.add(item);
//			gysQuanCheng.addItem(item);
//		}
//	}
}