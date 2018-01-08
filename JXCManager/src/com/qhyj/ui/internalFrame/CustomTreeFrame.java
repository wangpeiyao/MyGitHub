package com.qhyj.ui.internalFrame;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.qhyj.controller.MainController;
import com.qhyj.domain.CustomDo;
import com.qhyj.model.CustomNode;  
  
public class CustomTreeFrame extends JInternalFrame {  
  
    //���Ӵ�����������    
    private JScrollPane jScrollPane1 = new JScrollPane();  
    private JTree tree;  
    private JPopupMenu popMenu;  
  
    public JScrollPane getjScrollPane1() {  
        return jScrollPane1;  
    }  
  
    public void setjScrollPane1(JScrollPane jScrollPane1) {  
        this.jScrollPane1 = jScrollPane1;  
    }  
  
    public JPopupMenu getPopMenu() {  
        return popMenu;  
    }  
  
    public void setPopMenu(JPopupMenu popMenu) {  
        this.popMenu = popMenu;  
    }  
  
    public CustomTreeFrame() {  
  
        try {  
            init();  
            treeInit();  
            popMenuInit();  
        } catch (Exception exception) {  
            exception.printStackTrace();  
        }  
  
        setClosable(true);
        setIconifiable(true);
//        this.setResizable(true);  
        this.setSize(new Dimension(800, 550));  
//        this.setLocationRelativeTo(null);  
//        pack();
        this.setVisible(true);  
  
        //�˳�ʱ��Ҫ��ֹ��ǰjvm  
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }  
  
    //���õ�ǰ���ڵ���Ϣ  
    private void init() {  
//        getContentPane().setLayout(null);  
        setTitle("��λ��Ϣά��");  
    }  
  
    //��ʼ�����������  
    public void treeInit() {  
        if (jScrollPane1 != null) {  
            this.remove(jScrollPane1);  
        }  
        jScrollPane1.setBounds(new Rectangle(0, 0, 800, 700));  
//        jScrollPane1.setAutoscrolls(true);  
//        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        this.getContentPane().add(jScrollPane1);  
        expandTree();  
        tree.addMouseListener(new TreePopMenuEvent(this));  
        this.repaint();  
    }  
  
    //�Ҽ�������ർ�����Ĳ˵�  
    private void popMenuInit() {  
        popMenu = new JPopupMenu();  
        JMenuItem addItem = new JMenuItem("����¼��ͻ�");  
        addItem.addActionListener(new TreeAddViewMenuEvent(this));  
        JMenuItem delItem = new JMenuItem("ɾ���ͻ����¼��ͻ�");  
        delItem.addActionListener(new TreeDeleteViewMenuEvent(this));  
        JMenuItem modifyItem = new JMenuItem("�޸Ŀͻ���Ϣ");  
        modifyItem.addActionListener(new TreeModifyViewMenuEvent(this));  
        JMenuItem expandItem = new JMenuItem("չ��ȫ��");  
        expandItem.addActionListener(new ExpandEvent(this)); 
        JMenuItem collapsePathItem = new JMenuItem("�ϲ�ȫ��");  
        collapsePathItem.addActionListener(new CollapseEvent(this));
        popMenu.add(addItem);  
        popMenu.add(delItem);  
        popMenu.add(modifyItem);  
        popMenu.add(expandItem);
        popMenu.add(collapsePathItem);
    }  
  
    /** 
     * ��ȫչ��һ��JTree 
     * 
     * @param tree JTree 
     */  
    public void expandTree() {  
    	CustomNode root = new CustomNode(0,"ȫ���ͻ���Ϣ");  
        initNode(root,null);
        tree = new JTree(root);  
  
        tree.addTreeSelectionListener(new TreeSelectionListener() {  
  
            public void valueChanged(TreeSelectionEvent e) { //ѡ�в˵��ڵ���¼�  
            	CustomNode node = (CustomNode) tree.getLastSelectedPathComponent();  
            }  
        });  
        tree.updateUI();  
        jScrollPane1.getViewport().add(tree);  
    }  
    public CustomNode initNode(CustomNode root,Integer prtid) {
    	List list = null;
    	if("ȫ���ͻ���Ϣ".equals(root.getUserObject())) {
    		list = MainController.getInstance().getCustomListByPrtId(null);
    	}else {
    		list = MainController.getInstance().getCustomListByPrtId(prtid);
    	}
    	if(list!=null) {
    		for(int i=0;i<list.size();i++) {
    			CustomDo custom = (CustomDo) list.get(i);
    			CustomNode node = new CustomNode(custom.getCid(),custom.getCname());
    			initNode(node,custom.getCid());
    			root.add(node);
        	}
    	}
    	return root;
    	
    }
  
    public JTree getTree() {  
        return tree;  
    }  
  
    /** 
     * popmenu����Ҽ������Ӵ��� 
     */  
    class TreeAddViewMenuEvent implements ActionListener {  
  
        private CustomTreeFrame adaptee;  
  
        public TreeAddViewMenuEvent(CustomTreeFrame adaptee) {  
            this.adaptee = adaptee;  
        }  
  
        public void actionPerformed(ActionEvent e) {  
            String name = JOptionPane.showInputDialog("������ͻ����ƣ�"); 
			CustomDo customDo = new CustomDo();
			customDo.setCname(name);
			Integer parentId = ((CustomNode)this.adaptee.getTree().getLastSelectedPathComponent()).getKey();
			customDo.setParentId(parentId);
			MainController.getInstance().addCustome(customDo);
			CustomNode treenode = new CustomNode(customDo.getCid(), customDo.getCname());
			((CustomNode) this.adaptee.getTree().getLastSelectedPathComponent()).add(treenode);
			this.adaptee.getTree().expandPath(new TreePath(((CustomNode) this.adaptee.getTree().getLastSelectedPathComponent()).getPath()));
			this.adaptee.getTree().updateUI();
        }  
    }  
  
    /** 
     * popmenu����Ҽ���ɾ������ 
     */  
    class TreeDeleteViewMenuEvent implements ActionListener {  
  
        private CustomTreeFrame adaptee;  
  
        public TreeDeleteViewMenuEvent(CustomTreeFrame adaptee) {  
            this.adaptee = adaptee;  
        }  
  
        public void actionPerformed(ActionEvent e) {  
            int conform = JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ��ɾ���ÿͻ����¼���", "ɾ���ͻ�ȷ��", JOptionPane.YES_NO_OPTION);  
            if (conform == JOptionPane.YES_OPTION) {  
            	CustomNode currNode = ((CustomNode) this.adaptee.getTree().getLastSelectedPathComponent());
            	MainController.getInstance().deleteCustomeById(currNode.getKey());
            	CustomNode parentNode = (CustomNode) (((CustomNode) this.adaptee.getTree().getLastSelectedPathComponent()).getParent());  
            	parentNode.remove(currNode);
//                ((CustomNode) this.adaptee.getTree().getLastSelectedPathComponent()).removeFromParent();  
                this.adaptee.getTree().updateUI();  
            }  
        }  
    }  
}  
  
/** 
 * popmenu����Ҽ����޸Ĵ��� 
 */  
class TreeModifyViewMenuEvent implements ActionListener {  
  
    private CustomTreeFrame adaptee;  
  
    public TreeModifyViewMenuEvent(CustomTreeFrame adaptee) {  
        this.adaptee = adaptee;  
    }  
  
    public void actionPerformed(ActionEvent e) {  
        String name = JOptionPane.showInputDialog("�������¿ͻ����ƣ�");  
  
        CustomNode node = (CustomNode) this.adaptee.getTree().getSelectionPath().getLastPathComponent();  
        MainController.getInstance().updateCustomeName(node.getKey(),name);
        //����   
        node.setUserObject(name);  
        //ˢ��   
        this.adaptee.getTree().updateUI();  
    }  
}  
  
class ExpandEvent implements ActionListener {  
	  
    private CustomTreeFrame adaptee;  
  
    public ExpandEvent(CustomTreeFrame adaptee) {  
        this.adaptee = adaptee;  
    }  
  
    public void actionPerformed(ActionEvent e) {  
        CustomNode node = (CustomNode) this.adaptee.getTree().getSelectionPath().getLastPathComponent();  
        expandTree(this.adaptee.getTree(), this.adaptee.getTree().getSelectionPath());
    }  
    private void expandTree(JTree tree, TreePath parent) {
    	CustomNode node = (CustomNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
           for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
               TreeNode n = (TreeNode) e.nextElement();
               TreePath path = parent.pathByAddingChild(n);
               expandTree(tree, path);
           }
        }
        tree.expandPath(parent);
    }
}  

class CollapseEvent implements ActionListener {  
	  
    private CustomTreeFrame adaptee;  
  
    public CollapseEvent(CustomTreeFrame adaptee) {  
        this.adaptee = adaptee;  
    }  
  
    public void actionPerformed(ActionEvent e) {  
        CustomNode node = (CustomNode) this.adaptee.getTree().getSelectionPath().getLastPathComponent();  
        collapseTree(this.adaptee.getTree(), this.adaptee.getTree().getSelectionPath());
    }  
    private void collapseTree(JTree tree, TreePath parent) {
    	CustomNode node = (CustomNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
           for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
               TreeNode n = (TreeNode) e.nextElement();
               TreePath path = parent.pathByAddingChild(n);
               collapseTree(tree, path);
           }
        }
        tree.collapsePath(parent);
    }
}  
  
  
/** 
 * �˵�����Ҽ����¼����� 
 */  
class TreePopMenuEvent implements MouseListener {  
  
    private CustomTreeFrame adaptee;  
  
    public TreePopMenuEvent(CustomTreeFrame adaptee) {  
        this.adaptee = adaptee;  
    }  
  
    public void mouseClicked(MouseEvent e) {  
    }  
  
    public void mousePressed(MouseEvent e) {  
        TreePath path = adaptee.getTree().getPathForLocation(e.getX(), e.getY()); // �ؼ������������ʹ��  
        if (path == null) {  
            return;  
        }  
        adaptee.getTree().setSelectionPath(path);  
        if (e.getButton() == 3) {  
            adaptee.getPopMenu().show(adaptee.getTree(), e.getX(), e.getY());  
        }  
    }  
  
    public void mouseReleased(MouseEvent e) {  
    }  
  
    public void mouseEntered(MouseEvent e) {  
    }  
  
    public void mouseExited(MouseEvent e) {  
    }  
  
    public static void main(String[] args) {  
        try {  
            JFrame.setDefaultLookAndFeelDecorated(true);  
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  
            CustomTreeFrame userframe = new CustomTreeFrame();  
        } catch (ClassNotFoundException ex) {  
            Logger.getLogger(CustomTreeFrame.class.getName()).log(Level.SEVERE, null, ex);  
        } catch (InstantiationException ex) {  
            Logger.getLogger(CustomTreeFrame.class.getName()).log(Level.SEVERE, null, ex);  
        } catch (IllegalAccessException ex) {  
            Logger.getLogger(CustomTreeFrame.class.getName()).log(Level.SEVERE, null, ex);  
        } catch (UnsupportedLookAndFeelException ex) {  
			Logger.getLogger(CustomTreeFrame.class.getName()).log(Level.SEVERE, null, ex);  
        }  
    }  
}  