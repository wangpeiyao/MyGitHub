package com.quartz;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

public class JobDetailBeanProxy extends MethodInvokingJobDetailFactoryBean implements InitializingBean {

	private Object targetObject;
	private String targetMethod;
	private AutoJobProxy proxy = new AutoJobProxyImpl();

	public void afterPropertiesSet() throws ClassNotFoundException, NoSuchMethodException {
		System.out.println("开始加载1");
		this.proxy.setMethodName(this.targetMethod);
		this.proxy.setBean(this.targetObject);
		super.afterPropertiesSet();
	}

	public void setTargetObject(Object targetObject) {
		System.out.println("开始加载2");
		this.targetObject = targetObject;
		super.setTargetObject(this.proxy);	
	}

	public void setTargetMethod(String targetMethod) {
		System.out.println("开始加载3");
		this.targetMethod = targetMethod;
		super.setTargetMethod("execute");
	}

	public Object getTargetObject() {
		return targetObject;
	}

	public String getTargetMethod() {
		return targetMethod;
	}

}
