package com.test;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: ֧��ר���̳߳ع�����</p>
 *
 * <p>Description: ר���̳߳ع�����,���ڴ�������֧��ʱָ��״̬Ϊ������Ȩ��δ���ͽ�����ˡ�����֧����ָ��.
 *                 �ر�ע�⣺���̳߳ع���Ϊר��,pay��֮��Ĵ��벻��ʹ�øù�����.
 * </p>
 *
 * <p>Company: �����ź��ǿƼ��ɷ����޹�˾</p>
 *
 * @author ���� xiaojf
 * 
 * @since��2016-5-9 ����02:48:07
 * 
 */
class PayThreadPool {
	/**
	 * �̳߳ر���
	 */
	private static PayThreadPool pool = new PayThreadPool();
	/**
	 * �̳߳ض���
	 */
	private static ExecutorService executorService;
	
	/**
	 * ��ʼ�ش�С
	 */
	public final static Integer CORE_THREAD_SIZE = 5;
	
	/**
	 * �̳߳�������߳���.
	 */
	public final static Integer MAX_THREAD_SIZE = 15;
	
	/**
	 * �ȴ�ִ�е��߳�������д�С
	 */
	public final static Integer MAX_QUEUE_SIZE = 1000;
	
	static {
		// �����н����
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
