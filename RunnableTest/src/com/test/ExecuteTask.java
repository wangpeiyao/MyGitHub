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
 * @since��2016-9-22 ����05:33:27
 * 
 */
public class ExecuteTask implements Runnable {
	private Master master;
	
	public ExecuteTask(Master master) {
		this.master = master;
	}
	
	public void run() {
		master.execute();
	}

}
