package internalFrame;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
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
import javax.swing.tree.TreePath;

import com.lzw.dao.Dao;

import model.KHTreeNode;
import model.TbKhinfo;  
  
public class KeHuGuanLiTree extends JInternalFrame {  
  
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
  
    public KeHuGuanLiTree() {  
  
        try {  
            init();  
            treeInit();  
            popMenuInit();  
        } catch (Exception exception) {  
            exception.printStackTrace();  
        }  
  
        this.setSize(800, 600);  
        setClosable(true);
//        this.setResizable(true);  
        this.setMinimumSize(new Dimension(800, 400));  
//        this.setLocationRelativeTo(null);  
//        pack();
        this.setVisible(true);  
  
        //退出时需要终止当前jvm  
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }  
  
    //设置当前窗口的信息  
    private void init() {  
        getContentPane().setLayout(null);  
        setTitle("单位信息维护");  
    }  
  
    //初始化景点分类树  
    public void treeInit() {  
        if (jScrollPane1 != null) {  
            this.remove(jScrollPane1);  
        }  
        jScrollPane1.setBounds(new Rectangle(0, 0, 800, 600));  
        jScrollPane1.setAutoscrolls(true);  
        this.getContentPane().add(jScrollPane1);  
        expandTree();  
        tree.addMouseListener(new TreePopMenuEvent(this));  
        this.repaint();  
    }  
  
    //右键点击分类导航树的菜单  
    private void popMenuInit() {  
        popMenu = new JPopupMenu();  
        JMenuItem addItem = new JMenuItem("添加下级单位");  
        addItem.addActionListener(new TreeAddViewMenuEvent(this));  
        JMenuItem delItem = new JMenuItem("删除本单位");  
        delItem.addActionListener(new TreeDeleteViewMenuEvent(this));  
        JMenuItem modifyItem = new JMenuItem("修改本单位");  
        modifyItem.addActionListener(new TreeModifyViewMenuEvent(this));  
        popMenu.add(addItem);  
        popMenu.add(delItem);  
        popMenu.add(modifyItem);  
    }  
  
    /** 
     * 完全展开一个JTree 
     * 
     * @param tree JTree 
     */  
    public void expandTree() {  
    	KHTreeNode root = new KHTreeNode("0","全部单位信息");  
        initNode(root,null);
        tree = new JTree(root);  
  
        tree.addTreeSelectionListener(new TreeSelectionListener() {  
  
            public void valueChanged(TreeSelectionEvent e) { //选中菜单节点的事件  
            	KHTreeNode node = (KHTreeNode) tree.getLastSelectedPathComponent();  
            }  
        });  
        tree.updateUI();  
        jScrollPane1.getViewport().add(tree);  
    }  
    public KHTreeNode initNode(KHTreeNode root,String prtid) {
    	List list = null;
    	if("全部单位信息".equals(root.getUserObject())) {
    		list = Dao.getKhInfoList(null);
    	}else {
    		list = Dao.getKhInfoList(prtid);
    	}
    	if(list!=null) {
    		for(int i=0;i<list.size();i++) {
    			TbKhinfo info = (TbKhinfo) list.get(i);
    			KHTreeNode node = new KHTreeNode(info.getId(), info.getKhname());
    			initNode(node,info.getId());
    			root.add(node);
        	}
    	}
    	return root;
    	
    }
  
    /** 
     * 获取图片文件内容 
     * 
     * @param fileName 
     * @return 
     */  
    public Image getImage(String fileName) {  
        FileInputStream fis = null;  
        try {  
            fis = new FileInputStream(fileName);  
            BufferedInputStream bis = new BufferedInputStream(fis);  
            ByteBuffer bb = ByteBuffer.allocate(1024 * 1024);  
            byte[] buffer = new byte[1];  
            while (bis.read(buffer) > 0) {  
                bb.put(buffer);  
            }  
            ImageIcon icon = new ImageIcon(bb.array());  
            return icon.getImage();  
        } catch (IOException ex) {  
            Logger.getLogger(KeHuGuanLiTree.class.getName()).log(Level.SEVERE, null, ex);  
        } finally {  
            try {  
                fis.close();  
            } catch (IOException ex) {  
                Logger.getLogger(KeHuGuanLiTree.class.getName()).log(Level.SEVERE, null, ex);  
            }  
        }  
        return null;  
    }  
  
    public JTree getTree() {  
        return tree;  
    }  
  
    /** 
     * popmenu点击右键的增加处理 
     */  
    class TreeAddViewMenuEvent implements ActionListener {  
  
        private KeHuGuanLiTree adaptee;  
  
        public TreeAddViewMenuEvent(KeHuGuanLiTree adaptee) {  
            this.adaptee = adaptee;  
        }  
  
        public void actionPerformed(ActionEvent e) {  
            String name = JOptionPane.showInputDialog("请输入单位名称："); 
            
            ResultSet set = Dao.query("select max(id) from tb_khinfo");
			String id = null;
			try {
				if (set != null && set.next()) {
					String sid = set.getString(1);
					if (sid == null)
						id = "kh1001";
					else {
						String str = sid.substring(2);
						id = "kh" + (Integer.parseInt(str) + 1);
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			TbKhinfo khinfo = new TbKhinfo();
            khinfo.setId(id);
            khinfo.setKhname(name);
            String prentId = ((KHTreeNode)this.adaptee.getTree().getLastSelectedPathComponent()).getKey();
            khinfo.setPrentId(prentId);
            Dao.addKeHu(khinfo);
            KHTreeNode treenode = new KHTreeNode(id,name);  
            ((KHTreeNode) this.adaptee.getTree().getLastSelectedPathComponent()).add(treenode);  
            this.adaptee.getTree().expandPath(new TreePath(((KHTreeNode) this.adaptee.getTree().getLastSelectedPathComponent()).getPath()));  
            this.adaptee.getTree().updateUI();  
        }  
    }  
  
    /** 
     * popmenu点击右键的删除处理 
     */  
    class TreeDeleteViewMenuEvent implements ActionListener {  
  
        private KeHuGuanLiTree adaptee;  
  
        public TreeDeleteViewMenuEvent(KeHuGuanLiTree adaptee) {  
            this.adaptee = adaptee;  
        }  
  
        public void actionPerformed(ActionEvent e) {  
            int conform = JOptionPane.showConfirmDialog(null, "是否确认删除？", "删除景点确认", JOptionPane.YES_NO_OPTION);  
            if (conform == JOptionPane.YES_OPTION) {  
            	KHTreeNode parentNode = (KHTreeNode) (((KHTreeNode) this.adaptee.getTree().getLastSelectedPathComponent()).getParent());  
                ((KHTreeNode) this.adaptee.getTree().getLastSelectedPathComponent()).removeFromParent();  
                this.adaptee.getTree().updateUI();  
            }  
        }  
    }  
}  
  
/** 
 * popmenu点击右键的修改处理 
 */  
class TreeModifyViewMenuEvent implements ActionListener {  
  
    private KeHuGuanLiTree adaptee;  
  
    public TreeModifyViewMenuEvent(KeHuGuanLiTree adaptee) {  
        this.adaptee = adaptee;  
    }  
  
    public void actionPerformed(ActionEvent e) {  
        String name = JOptionPane.showInputDialog("请输入新单位名称：");  
  
        KHTreeNode node = (KHTreeNode) this.adaptee.getTree().getSelectionPath().getLastPathComponent();  
        //改名   
        node.setUserObject(name);  
        //刷新   
        this.adaptee.getTree().updateUI();  
    }  
}  
  
/** 
 * 菜单点击右键的事件处理 
 */  
class TreePopMenuEvent implements MouseListener {  
  
    private KeHuGuanLiTree adaptee;  
  
    public TreePopMenuEvent(KeHuGuanLiTree adaptee) {  
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
            KeHuGuanLiTree userframe = new KeHuGuanLiTree();  
        } catch (ClassNotFoundException ex) {  
            Logger.getLogger(KeHuGuanLiTree.class.getName()).log(Level.SEVERE, null, ex);  
        } catch (InstantiationException ex) {  
            Logger.getLogger(KeHuGuanLiTree.class.getName()).log(Level.SEVERE, null, ex);  
        } catch (IllegalAccessException ex) {  
            Logger.getLogger(KeHuGuanLiTree.class.getName()).log(Level.SEVERE, null, ex);  
        } catch (UnsupportedLookAndFeelException ex) {  
            Logger.getLogger(KeHuGuanLiTree.class.getName()).log(Level.SEVERE, null, ex);  
        }  
    }  
}  