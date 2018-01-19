package com.test;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: 北京九恒星科技股份有限公司</p>
 *
 * @author 作者
 * 
 * @since：2016-9-22 下午05:33:56
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
