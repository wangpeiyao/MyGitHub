package com.lzw.util;

import javax.swing.tree.DefaultMutableTreeNode;

public class TreeUtil {
	public static DefaultMutableTreeNode getNodes() {
		DefaultMutableTreeNode nodes = new DefaultMutableTreeNode();
		for(int i=0;i<10;i++) {
			if(i%2==0) {
				nodes.add(new DefaultMutableTreeNode(i));
			}else {
				DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(4);
				subNode.add(new DefaultMutableTreeNode(i));
				nodes.add(subNode);
			}
			
		}
		return nodes;
	}

}
