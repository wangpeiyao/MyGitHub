package com.qhyj.ui.panel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.lzw.dao.Dao;
import com.qhyj.controller.MainController;
import com.qhyj.domain.GoodsDo;
import com.qhyj.model.GoodsItem;

import internalFrame.guanli.Item;
import model.TbGysinfo;
import model.TbSpinfo;
public class GoodsModPanel extends JPanel {
	private JComboBox gysQuanCheng;
	private JTextField beiZhu;
	private JTextField wenHao;
	private JTextField piHao;
	private JTextField baoZhuang;
	private JTextField guiGe;
	private JTextField danWei;
	private JTextField chanDi;
	private JTextField jianCheng;
	private JTextField quanCheng;
	private JButton modifyButton;
	private JButton delButton;
	private JRadioButton jrb1;
	private JRadioButton jrb2;
	private ButtonGroup group;
	private JComboBox sp;
	public GoodsModPanel() {
		setLayout(new GridBagLayout());
		setBounds(10, 10, 550, 400);
		setupComponet(new JLabel("商品名称："), 0, 0, 1, 1, false);
		quanCheng = new JTextField();
		quanCheng.setEditable(false);
		setupComponet(quanCheng, 1, 0, 3, 1, true);


		setupComponet(new JLabel("产地："), 0, 1, 1, 1, false);
		chanDi = new JTextField();
		setupComponet(chanDi, 1, 1, 3, 300, true);


		setupComponet(new JLabel("规格："), 0, 2, 1, 1, false);
		guiGe = new JTextField();
		setupComponet(guiGe, 1, 2, 3, 1, true);
		
		setupComponet(new JLabel("是否返利："), 0, 3, 1, 1, false);
		jrb1 = new JRadioButton("是");
		jrb2 = new JRadioButton("否");
		group = new ButtonGroup();
		group.add(jrb1);
        group.add(jrb2);
        setupComponet(jrb1, 1, 3, 1, 10, false);
        setupComponet(jrb2, 2, 3, 1, 10, false);


		setupComponet(new JLabel("备注："), 0, 4, 1, 1, false);
		beiZhu = new JTextField();
		setupComponet(beiZhu, 1, 4, 3, 1, true);

		setupComponet(new JLabel("选择商品"), 0, 5, 1, 0, false);
		sp = new JComboBox();
		sp.setPreferredSize(new Dimension(230, 21));
		// 处理客户信息的下拉选择框的选择事件
		sp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSpSelectAction();
			}
		});
		// 定位商品信息的下拉选择框
		setupComponet(sp, 1, 5, 2, 0, true);
		modifyButton = new JButton("修改");
		delButton = new JButton("删除");
		JPanel panel = new JPanel();
		panel.add(modifyButton);
		panel.add(delButton);
		// 定位按钮
		setupComponet(panel, 3, 5, 1, 0, false);
		// 处理删除按钮的单击事件
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GoodsItem item = (GoodsItem) sp.getSelectedItem();
				if (item == null || !(item instanceof GoodsItem))
					return;
				int confirm = JOptionPane.showConfirmDialog(
						GoodsModPanel.this, "确认删除商品信息吗？");
				if (confirm == JOptionPane.YES_OPTION) {
					int rs = MainController.getInstance().deleteGoods(item.getId());
					if (rs > 0) {
						JOptionPane.showMessageDialog(GoodsModPanel.this,
								"商品：" + item.getName() + "。删除成功");
						sp.removeItem(item);
					}
				}
			}
		});
		// 处理修改按钮的单击事件
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GoodsItem item = (GoodsItem) sp.getSelectedItem();
				
				GoodsDo goodsDo = new GoodsDo();
				goodsDo.setGid(item.getId());
				goodsDo.setGname(quanCheng.getText().trim());
				goodsDo.setSpec(guiGe.getText().trim());
				goodsDo.setPlace(chanDi.getText().trim());
				goodsDo.setMemo(beiZhu.getText().trim());
				int res = MainController.getInstance().updateGoods(goodsDo);
				
				if (res == 1) {
					JOptionPane.showMessageDialog(GoodsModPanel.this,"修改完成");
				}else {
					JOptionPane.showMessageDialog(GoodsModPanel.this,"修改失败");
				}
			}
		});
	}
	// 初始化商品下拉选择框
	public void initComboBox() {
//		List khInfo = Dao.getSpInfos();
		List goodsList = MainController.getInstance().getAllGoodsList();
		List<GoodsItem> items = new ArrayList<GoodsItem>();
		sp.removeAllItems();
		for (int i=0;i<goodsList.size();i++) {
			GoodsDo goodsDo = (GoodsDo) goodsList.get(i);
			GoodsItem item = new GoodsItem();
			item.setId(goodsDo.getGid());
			item.setName(goodsDo.getGname());
			if (items.contains(item))
				continue;
			items.add(item);
			sp.addItem(item);
		}
		doSpSelectAction();
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
//		doSpSelectAction();
//	}
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
		add(component, gridBagConstrains);
	}
	// 处理商品选择事件
	private void doSpSelectAction() {
		GoodsItem selectedItem;
		if (!(sp.getSelectedItem() instanceof GoodsItem)) {
			return;
		}
		selectedItem = (GoodsItem) sp.getSelectedItem();
		GoodsDo goodsDo = MainController.getInstance().getGoodsById(selectedItem.getId());
		if(null!=goodsDo) {
			quanCheng.setText(goodsDo.getGname().trim());
			chanDi.setText(goodsDo.getPlace().trim());
			guiGe.setText(goodsDo.getSpec().trim());
			beiZhu.setText(goodsDo.getMemo().trim());
			if(new Integer(0).equals(goodsDo.getIsRebate())) {
				jrb2.setSelected(true);
			}else if(new Integer(1).equals(goodsDo.getIsRebate())) {
				jrb1.setSelected(true);
			}
		}
	}
}
