package com.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Logger;

/**
 * <p>Title: 主控线程</p>
 *
 * <p>Description: </p>
 *
 * <p>Company: 北京九恒星科技股份有限公司</p>
 *
 * @author 作者 xiaojf
 * 
 * @since：2016-9-22 下午02:37:50
 * 
 */
public class Master {
	
	/**
	 * 工作队列
	 */
	private Queue<Integer> workQueue = new ConcurrentLinkedQueue<Integer>();
	
	/**
	 * 线程池
	 */
	private PayThreadPool pool = PayThreadPool.getInstance();
	
	/**
	 * 启动线程个数
	 */
	private Integer threadCounter = pool.CORE_THREAD_SIZE;
	
	/**
	 * 计算线程任务
	 */
	private ComputeWorker worker;

	/**
	 * 循环计数器
	 */
	private CyclicBarrier barrier;
	
	/**
	 * 控制线程执行时点
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
	 * @Description: 执行计算任务
	 * @author 作者 xiaojf
	 * @since：2016-9-24 下午12:20:15
	 */
	public synchronized void execute() {
		while (true) {
			try {
				System.out.println("execute等待"+Thread.currentThread().getName()); 
				wait();
				
			} catch (InterruptedException e) {
			}
			
			
			processTaskQueue();
			
			notify();
			System.out.println("execute执行"+Thread.currentThread().getName()); 
		}
		
	}
	/**
	 * 
	 * @Description: 获取队列任务并计算处理任务
	 * @author 作者 xiaojf
	 * @since：2016-9-24 下午01:26:02
	 */
	private void processTaskQueue() {
		if (workQueue.size() > 0) {
			for (Integer i = 0; i < threadCounter; i++) {
				pool.add(this.worker);
			}
			while (!isDone) {
				// 空跑
			}
			// 状态重置
			isDone = false;
		}
	}
	
	/**
	 * 
	 * @Description: 执行查询任务
	 * @author 作者 xiaojf
	 * @since：2016-9-24 下午12:20:37
	 */
	public synchronized void query() {
		while (true) {
			
			fillTaskToQueue();
			
			try {
				if (workQueue.size() > 0) {
					System.out.println("query唤醒"+Thread.currentThread().getName()); 
					notify();
					// 等待
					System.out.println("query等待"+Thread.currentThread().getName()); 
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
	 * @Description: 生产任务并写入队列
	 * @author 作者 xiaojf
	 * @since：2016-9-24 下午01:13:05
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
			 System.out.println("更新状态");
			orderIdList.clear();
		}
	}

	/**
	 * 
	 * @Description: 查询待发送的指令
	 * @return
	 * @author 作者 xiaojf
	 * @since：2016-9-23 下午02:59:53
	 */
	private List<Integer> findOrderToProcessList() {
		List<Integer> list = new ArrayList();
		list.add(new Integer(1));
		list.add(new Integer(2));
        return list;
	}
}
