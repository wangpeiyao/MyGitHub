package com.qhyj.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;

import com.qhyj.controller.MainController;
import com.qhyj.ui.internalFrame.RebateFrame;

public class RabateTableModel extends DefaultTableModel{
	
	private Integer key;

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}
	
	

}
