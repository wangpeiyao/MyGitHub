package com.test;


import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;


/**
 * <p>Title: 工作线程</p>
 *
 * <p>Description: </p>
 *
 * <p>Company: 北京九恒星科技股份有限公司</p>
 *
 * @author 作者 xiaojf
 * 
 * @since：2016-9-22 下午02:37:58
 * 
 */
public class ComputeWorker implements Runnable {
	
	private Queue<Integer> workQueue;
	/**
	 * 计数器
	 */
	private CountDownLatch cdl;
	
	/**
	 * 循环计算器
	 */
	private CyclicBarrier barrier;
	
	
	public void setWorkQueue(Queue<Integer> workQueue, CountDownLatch cdl) {
		this.workQueue = workQueue;
		this.cdl = cdl;
	}
	
	public void setWorkQueue(Queue<Integer> workQueue, CyclicBarrier barrier) {
		this.workQueue = workQueue;
		this.barrier = barrier;
	}

	public void run() {
		List<Integer> orderList = null;
		while (true) {
			Integer order = workQueue.poll();
			if (null == order) {
				try {
					this.barrier.await();
				} catch (Exception e) {
					
				} 
				break;
			}
			orderList = new ArrayList<Integer>(1);
			orderList.add(order);
			System.out.println("开始处理"+order+Thread.currentThread().getName());
		}
	}

}
