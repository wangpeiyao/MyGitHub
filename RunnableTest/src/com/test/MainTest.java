package com.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {

	public static void main(String[] args) {
//		PersistTools.evict(obj);
//		new MainTest().getClass().getResource("spring-test.xml");
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"spring-test.xml"});
//			new Thread(new Runnable() {
//				public void run() {
//					System.out.println(Thread.currentThread().getName()+"����");
//					 IBSAutoJob job1 = new IBSAutoJob();
//					 job1.setWorkThreadNum(10);
//					 job1.execute();
//					 try {
//						 System.out.println(Thread.currentThread().getName()+"��Ϣ30��");
//						Thread.sleep(30000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					 System.out.println(Thread.currentThread().getName()+"����");
//				}
//
//			}) {
//			}.start();
		}

}
