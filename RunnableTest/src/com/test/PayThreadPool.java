package com.test;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: 支付专用线程池工具类</p>
 *
 * <p>Description: 专用线程池工具类,用于处理网银支付时指令状态为：已授权且未发送结算记账、清算支付的指令.
 *                 特别注意：该线程池工具为专用,pay包之外的代码不可使用该工具类.
 * </p>
 *
 * <p>Company: 北京九恒星科技股份有限公司</p>
 *
 * @author 作者 xiaojf
 * 
 * @since：2016-5-9 下午02:48:07
 * 
 */
class PayThreadPool {
	/**
	 * 线程池本身
	 */
	private static PayThreadPool pool = new PayThreadPool();
	/**
	 * 线程池对象
	 */
	private static ExecutorService executorService;
	
	/**
	 * 初始池大小
	 */
	public final static Integer CORE_THREAD_SIZE = 5;
	
	/**
	 * 线程池子最大线程数.
	 */
	public final static Integer MAX_THREAD_SIZE = 15;
	
	/**
	 * 等待执行的线程任务队列大小
	 */
	public final static Integer MAX_QUEUE_SIZE = 1000;
	
	static {
		// 创建有界队列
		BlockingQueue queue = new ArrayBlockingQueue(MAX_QUEUE_SIZE);
		executorService = new ThreadPoolExecutor(MAX_THREAD_SIZE, MAX_THREAD_SIZE, 0L, TimeUnit.MILLISECONDS, queue);
	}
	
	private PayThreadPool() {
		
	}
	
	public static PayThreadPool getInstance() {
		return pool;
	}
	
	public synchronized void add(Runnable workTask) {
		executorService.submit(workTask);
	}
	
	public static ExecutorService getExecutorService() {
		return executorService;
	}
}
