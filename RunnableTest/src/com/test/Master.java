package com.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Logger;

/**
 * <p>Title: �����߳�</p>
 *
 * <p>Description: </p>
 *
 * <p>Company: �����ź��ǿƼ��ɷ����޹�˾</p>
 *
 * @author ���� xiaojf
 * 
 * @since��2016-9-22 ����02:37:50
 * 
 */
public class Master {
	
	/**
	 * ��������
	 */
	private Queue<Integer> workQueue = new ConcurrentLinkedQueue<Integer>();
	
	/**
	 * �̳߳�
	 */
	private PayThreadPool pool = PayThreadPool.getInstance();
	
	/**
	 * �����̸߳���
	 */
	private Integer threadCounter = pool.CORE_THREAD_SIZE;
	
	/**
	 * �����߳�����
	 */
	private ComputeWorker worker;

	/**
	 * ѭ��������
	 */
	private CyclicBarrier barrier;
	
	/**
	 * �����߳�ִ��ʱ��
	 */
	private volatile Boolean isDone = false;
	

	
	public Master(ComputeWorker worker, Integer threadCounter) {
		barrier = new CyclicBarrier(threadCounter, new Runnable(){
			public void run() {
				isDone = true;
			}
		});
		worker.setWorkQueue(workQueue, barrier);
		this.worker = worker;
		this.threadCounter = threadCounter;
		
	}
	
	/**
	 * 
	 * @Description: ִ�м�������
	 * @author ���� xiaojf
	 * @since��2016-9-24 ����12:20:15
	 */
	public synchronized void execute() {
		while (true) {
			try {
				System.out.println("execute�ȴ�"+Thread.currentThread().getName()); 
				wait();
				
			} catch (InterruptedException e) {
			}
			
			
			processTaskQueue();
			
			notify();
			System.out.println("executeִ��"+Thread.currentThread().getName()); 
		}
		
	}
	/**
	 * 
	 * @Description: ��ȡ�������񲢼��㴦������
	 * @author ���� xiaojf
	 * @since��2016-9-24 ����01:26:02
	 */
	private void processTaskQueue() {
		if (workQueue.size() > 0) {
			for (Integer i = 0; i < threadCounter; i++) {
				pool.add(this.worker);
			}
			while (!isDone) {
				// ����
			}
			// ״̬����
			isDone = false;
		}
	}
	
	/**
	 * 
	 * @Description: ִ�в�ѯ����
	 * @author ���� xiaojf
	 * @since��2016-9-24 ����12:20:37
	 */
	public synchronized void query() {
		while (true) {
			
			fillTaskToQueue();
			
			try {
				if (workQueue.size() > 0) {
					System.out.println("query����"+Thread.currentThread().getName()); 
					notify();
					// �ȴ�
					System.out.println("query�ȴ�"+Thread.currentThread().getName()); 
					wait();
		
				}
				
				if (workQueue.size() == 0) {
					Thread.sleep(5000);
				}
			} catch (InterruptedException e) {
			}
		}
	}
	
	/**
	 * 
	 * @Description: ��������д�����
	 * @author ���� xiaojf
	 * @since��2016-9-24 ����01:13:05
	 */
	private void fillTaskToQueue() {
		List<Integer> orderList = findOrderToProcessList();
		Integer count = (orderList == null ? 0 : orderList.size());
		
		if (count > 0) {
			workQueue.addAll(orderList);
			
			List<Integer> orderIdList = new ArrayList<Integer>();
			for (Integer order : orderList) {
				orderIdList.add(order);
	        }
			 System.out.println("����״̬");
			orderIdList.clear();
		}
	}

	/**
	 * 
	 * @Description: ��ѯ�����͵�ָ��
	 * @return
	 * @author ���� xiaojf
	 * @since��2016-9-23 ����02:59:53
	 */
	private List<Integer> findOrderToProcessList() {
		List<Integer> list = new ArrayList();
		list.add(new Integer(1));
		list.add(new Integer(2));
        return list;
	}
}
