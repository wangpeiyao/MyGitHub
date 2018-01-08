package com.qhyj.ui.internalFrame;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.qhyj.ui.panel.GoodsAddPanel;
import com.qhyj.ui.panel.GoodsModPanel;


public class GoodsFrame extends JInternalFrame {  

	public GoodsFrame() {
		setIconifiable(true);
		setClosable(true);
		setTitle("��Ʒ����");
		JTabbedPane tabPane = new JTabbedPane();
		final GoodsModPanel spxgPanel = new GoodsModPanel();
		final GoodsAddPanel sptjPanel = new GoodsAddPanel();
		tabPane.addTab("��Ʒ��Ϣ���", null, sptjPanel, "��Ʒ���");
		tabPane.addTab("��Ʒ��Ϣ�޸���ɾ��", null, spxgPanel, "�޸���ɾ��");
		getContentPane().add(tabPane);
		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				spxgPanel.initComboBox();
//				spxgPanel.initGysBox();
			}
		});
		//����Ʒ�����ڱ�����ʱ����ʼ����Ʒ��ӽ���Ĺ�Ӧ������ѡ���
//		addInternalFrameListener(new InternalFrameAdapter(){
//			public void internalFrameActivated(InternalFrameEvent e) {
//				super.internalFrameActivated(e);
//				sptjPanel.initGysBox();
//			}
//		});
		pack();
		setVisible(true);
	}
}
