package com.quartz;

import java.io.Serializable;

import org.springframework.util.MethodInvoker;

public class AutoJobProxyImpl implements AutoJobProxy, Serializable {

	private String methodName;
	private Object bean;

	@Override
	public void execute() throws Exception {

		try {
			System.out.println("JOB START: bean:" + this.bean + "methodName: " + this.methodName + " ��ʼִ��");

			MethodInvoker methodInvoker = new MethodInvoker();
			methodInvoker.setTargetObject(this.bean);
			methodInvoker.setTargetMethod(this.methodName);
			methodInvoker.prepare();

			methodInvoker.invoke();
			System.out.println("bean :" + this.bean + "methodName: " + this.methodName + " ִ�����");
		} catch (Throwable e) {
			System.out.println("bean :" + this.bean + "methodName: " + this.methodName + " ִ���쳣");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setMethodName(String paramString) {
		System.out.println("���÷���");
		this.methodName = paramString;

	}

	@Override
	public void setBean(Object paramObject) {
		System.out.println("����BEAN");
		this.bean =paramObject;
	}

}
