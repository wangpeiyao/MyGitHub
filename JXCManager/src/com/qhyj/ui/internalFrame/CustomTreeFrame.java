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
  
    //增加带滚动条容器    
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
  
        //退出时需要终止当前jvm  
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }  
  
    //设置当前窗口的信息  
    private void init() {  
//        getContentPane().setLayout(null);  
        setTitle("单位信息维护");  
    }  
  
    //初始化景点分类树  
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
  
    //右键点击分类导航树的菜单  
    private void popMenuInit() {  
        popMenu = new JPopupMenu();  
        JMenuItem addItem = new JMenuItem("添加下级客户");  
        addItem.addActionListener(new TreeAddViewMenuEvent(this));  
        JMenuItem delItem = new JMenuItem("删除客户及下级客户");  
        delItem.addActionListener(new TreeDeleteViewMenuEvent(this));  
        JMenuItem modifyItem = new JMenuItem("修改客户信息");  
        modifyItem.addActionListener(new TreeModifyViewMenuEvent(this));  
        JMenuItem expandItem = new JMenuItem("展开全部");  
        expandItem.addActionListener(new ExpandEvent(this)); 
        JMenuItem collapsePathItem = new JMenuItem("合并全部");  
        collapsePathItem.addActionListener(new CollapseEvent(this));
        popMenu.add(addItem);  
        popMenu.add(delItem);  
        popMenu.add(modifyItem);  
        popMenu.add(expandItem);
        popMenu.add(collapsePathItem);
    }  
  
    /** 
     * 完全展开一个JTree 
     * 
     * @param tree JTree 
     */  
    public void expandTree() {  
    	CustomNode root = new CustomNode(0,"全部客户信息");  
        initNode(root,null);
        tree = new JTree(root);  
  
        tree.addTreeSelectionListener(new TreeSelectionListener() {  
  
            public void valueChanged(TreeSelectionEvent e) { //选中菜单节点的事件  
            	CustomNode node = (CustomNode) tree.getLastSelectedPathComponent();  
            }  
        });  
        tree.updateUI();  
        jScrollPane1.getViewport().add(tree);  
    }  
    public CustomNode initNode(CustomNode root,Integer prtid) {
    	List list = null;
    	if("全部客户信息".equals(root.getUserObject())) {
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
     * popmenu点击右键的增加处理 
     */  
    class TreeAddViewMenuEvent implements ActionListener {  
  
        private CustomTreeFrame adaptee;  
  
        public TreeAddViewMenuEvent(CustomTreeFrame adaptee) {  
            this.adaptee = adaptee;  
        }  
  
        public void actionPerformed(ActionEvent e) {  
            String name = JOptionPane.showInputDialog("请输入客户名称："); 
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
     * popmenu点击右键的删除处理 
     */  
    class TreeDeleteViewMenuEvent implements ActionListener {  
  
        private CustomTreeFrame adaptee;  
  
        public TreeDeleteViewMenuEvent(CustomTreeFrame adaptee) {  
            this.adaptee = adaptee;  
        }  
  
        public void actionPerformed(ActionEvent e) {  
            int conform = JOptionPane.showConfirmDialog(null, "是否确认删除该客户及下级？", "删除客户确认", JOptionPane.YES_NO_OPTION);  
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
 * popmenu点击右键的修改处理 
 */  
class TreeModifyViewMenuEvent implements ActionListener {  
  
    private CustomTreeFrame adaptee;  
  
    public TreeModifyViewMenuEvent(CustomTreeFrame adaptee) {  
        this.adaptee = adaptee;  
    }  
  
    public void actionPerformed(ActionEvent e) {  
        String name = JOptionPane.showInputDialog("请输入新客户名称：");  
  
        CustomNode node = (CustomNode) this.adaptee.getTree().getSelectionPath().getLastPathComponent();  
        MainController.getInstance().updateCustomeName(node.getKey(),name);
        //改名   
        node.setUserObject(name);  
        //刷新   
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
 * 菜单点击右键的事件处理 
 */  
class TreePopMenuEvent implements MouseListener {  
  
    private CustomTreeFrame adaptee;  
  
    public TreePopMenuEvent(CustomTreeFrame adaptee) {  
        this.adaptee = adaptee;  
    }  
  
    public void mouseClicked(MouseEvent e) {  
    }  
  
    public void mousePressed(MouseEvent e) {  
        TreePath path = adaptee.getTree().getPathForLocation(e.getX(), e.getY()); // 关键是这个方法的使用  
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