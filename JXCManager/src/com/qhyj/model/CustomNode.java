package com.qhyj.model;

import javax.swing.tree.DefaultMutableTreeNode;

public class CustomNode extends DefaultMutableTreeNode{

	private Integer key;
	
	public CustomNode() {
		
	}
	
	public CustomNode(Integer key,Object obj) {
		this.key=key;
		this.setUserObject(obj);
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	
	
}
