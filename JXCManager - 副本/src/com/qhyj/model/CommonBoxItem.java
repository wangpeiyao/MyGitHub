package com.qhyj.model;

public class CommonBoxItem {
	
	private Integer key;
	private String value;
	
	public CommonBoxItem() {
		
	}
	public CommonBoxItem(Integer key,String value) {
		this.key=key;
		this.value=value;
	}
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	public String toString() {
		return this.value;
	}

	
}
