package com.test;

/**
* <p>Title: �Զ����������</p>
*
* <p>Description: </p>
*
* <p>Company: �����ź��ǿƼ��ɷ����޹�˾</p>
*
* @author ���� xiaojf
* 
* @since��2016-9-23 ����04:17:45
* 
*/
public class IBSAutoJob {
	static {
		System.out.println("���سɹ�");
	}
	private Integer workThreadNum;
	
	public void setWorkThreadNum(Integer workThreadNum) {
		this.workThreadNum = workThreadNum;
	}
	
	public Boolean executeFlag = false;
	
	public void execute() {
		System.out.println(">>>>>>�Զ�����ʼ"+this.toString());
		if (!executeFlag) {
			System.out.println(">>>>>>����ִ��"+this.toString());
			executeFlag = true;
			ComputeWorker worker = new ComputeWorker();
			Master master = new Master(worker, workThreadNum);
			
			ExecuteTask task1 = new ExecuteTask(master);
			QueryTask task2 = new QueryTask(master);
			
			new Thread(task1).start();
			new Thread(task2).start();
			//PayThreadPool.getInstance().add(task1);
			//PayThreadPool.getInstance().add(task2);
		} else {
			// ��ִ��
		}
		System.out.println(">>>>>>�Զ��������"+this.toString());
	}
}

