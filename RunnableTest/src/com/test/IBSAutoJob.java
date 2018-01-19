package com.test;

/**
* <p>Title: 自动任务入口类</p>
*
* <p>Description: </p>
*
* <p>Company: 北京九恒星科技股份有限公司</p>
*
* @author 作者 xiaojf
* 
* @since：2016-9-23 下午04:17:45
* 
*/
public class IBSAutoJob {
	static {
		System.out.println("加载成功");
	}
	private Integer workThreadNum;
	
	public void setWorkThreadNum(Integer workThreadNum) {
		this.workThreadNum = workThreadNum;
	}
	
	public Boolean executeFlag = false;
	
	public void execute() {
		System.out.println(">>>>>>自动任务开始"+this.toString());
		if (!executeFlag) {
			System.out.println(">>>>>>进入执行"+this.toString());
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
			// 不执行
		}
		System.out.println(">>>>>>自动任务结束"+this.toString());
	}
}

