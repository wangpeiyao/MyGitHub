package com.test;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: �����ź��ǿƼ��ɷ����޹�˾</p>
 *
 * @author ����
 * 
 * @since��2016-9-22 ����05:33:56
 * 
 */
public class QueryTask implements Runnable {
	private Master master;
	
	public QueryTask(Master master) {
		this.master = master;
	}
	
	public void run() {	
		this.master.query();
	}

}
