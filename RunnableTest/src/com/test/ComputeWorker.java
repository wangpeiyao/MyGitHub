package com.test;


import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;


/**
 * <p>Title: �����߳�</p>
 *
 * <p>Description: </p>
 *
 * <p>Company: �����ź��ǿƼ��ɷ����޹�˾</p>
 *
 * @author ���� xiaojf
 * 
 * @since��2016-9-22 ����02:37:58
 * 
 */
public class ComputeWorker implements Runnable {
	
	private Queue<Integer> workQueue;
	/**
	 * ������
	 */
	private CountDownLatch cdl;
	
	/**
	 * ѭ��������
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
			System.out.println("��ʼ����"+order+Thread.currentThread().getName());
		}
	}

}
