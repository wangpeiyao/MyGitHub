package model;

import javax.swing.tree.DefaultMutableTreeNode;

public class KHTreeNode extends DefaultMutableTreeNode{

	private String key;
	
	public KHTreeNode() {
		
	}
	
	public KHTreeNode(String key,Object obj) {
		this.setUserObject(obj);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
