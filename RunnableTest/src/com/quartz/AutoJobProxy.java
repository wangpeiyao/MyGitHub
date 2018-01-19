package com.quartz;

public interface AutoJobProxy {
	public abstract void execute() throws Exception;

	public abstract void setMethodName(String paramString);

	public abstract void setBean(Object paramObject);
}
